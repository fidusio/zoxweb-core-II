package org.zoxweb.client.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.zoxweb.shared.data.NVEntityFactory;
import org.zoxweb.shared.db.QueryMarker;
import org.zoxweb.shared.db.QueryMatch;
import org.zoxweb.shared.db.QueryRequest;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.filters.ValueFilter;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.Const.LogicalOperator;
import org.zoxweb.shared.util.DynamicEnumMap;
import org.zoxweb.shared.util.DynamicEnumMapManager;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.MetaToken;
import org.zoxweb.shared.util.NVBase;
import org.zoxweb.shared.util.NVBigDecimalList;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVDoubleList;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVEntityGetNameMap;
import org.zoxweb.shared.util.NVEntityReferenceIDMap;
import org.zoxweb.shared.util.NVEntityReferenceList;
import org.zoxweb.shared.util.NVEnumList;
import org.zoxweb.shared.util.NVFloatList;
import org.zoxweb.shared.util.NVIntList;
import org.zoxweb.shared.util.NVLongList;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.NVBlob;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class JSONClientUtil 
{
	
	private JSONClientUtil()
	{
		
	}
	
	public static <V extends NVEntity> V fromJSON(V nve, String json, NVEntityFactory nveFactory)
	{
		JSONObject value = (JSONObject) JSONParser.parseLenient(json);
		
		return fromJSON(nve, value, nveFactory);
	}
	
	private static <V extends NVEntity> V classTypeToNVE(JSONObject value, NVEntityFactory nveFactory)
	{
		JSONValue className = value.get(MetaToken.CLASS_TYPE.getName());
		
		if (className != null && className instanceof JSONString)
		{
			return nveFactory.createNVEntity(((JSONString)className).stringValue());
		}
		
		return null;
	}
	
	public static List<NVEntity> fromJSONValues(String json, NVEntityFactory nveFactory) 
	{
		JSONObject jo = (JSONObject) JSONParser.parseLenient(json);
		JSONArray values = (JSONArray) jo.get(MetaToken.VALUES.getName());
		List<NVEntity> ret = new ArrayList<NVEntity>();
		
		for (int i = 0; i < values.size(); i++)
		{
			ret.add( fromJSON(null, (JSONObject)values.get(i), nveFactory));
		}
		
		return ret;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <V extends NVEntity> V fromJSON(V nve, JSONObject value, NVEntityFactory nveFactory)
	{
		// parse the class type
		if (nve == null)
		{
			nve = classTypeToNVE(value, nveFactory);
		}
		
		if (nve != null)
		{
			NVConfigEntity nvce = (NVConfigEntity) nve.getNVConfig();
		
			for (NVConfig nvc : nvce.getAttributes())
			{	
				JSONValue jsonValue = value.get(nvc.getName());
				
				if (jsonValue == null || jsonValue instanceof JSONNull)
				{
					continue;
				}
				
				if (nvc instanceof NVConfigEntity)
				{
					if (nvc.isArray())
					{
						// array NVEntity
						JSONArray jsonArray = (JSONArray) jsonValue;
						ArrayValues<NVEntity> avNVE = (ArrayValues<NVEntity>) nve.lookup(nvc.getName());
						for (int i = 0; i < jsonArray.size(); i++)
						{
							avNVE.add(fromJSON(null, (JSONObject) jsonArray.get(i), nveFactory));
						}
						
					}
					else
					{
						nve.setValue(nvc.getName(), fromJSON(null, (JSONObject)jsonValue, nveFactory));
					}
				}
				else
				{
					if (!nvc.isArray())
					{
						if (nvc.getMetaTypeBase().equals(String.class))
						{
							String val = ((JSONString) value.get(nvc.getName())).stringValue();
							nve.setValue(nvc.getName(), val);
						}
						else if (nvc.getMetaTypeBase().equals(Long.class) || nvc.getMetaType().equals(Date.class))
						{
							long val = (long) ((JSONNumber) value.get(nvc.getName())).doubleValue();
							nve.setValue(nvc.getName(), val);
						}
						else if (nvc.getMetaTypeBase().equals(Integer.class))
						{
							int val = (int) ((JSONNumber) value.get(nvc.getName())).doubleValue();
							nve.setValue(nvc.getName(), val);
						}
						else if (nvc.getMetaTypeBase().equals(Float.class))
						{
							float val = (float) ((JSONNumber) value.get(nvc.getName())).doubleValue();
							nve.setValue(nvc.getName(), val);
						}
						else if (nvc.getMetaTypeBase().equals(Double.class))
						{
							nve.setValue(nvc.getName(), ((JSONNumber) value.get(nvc.getName())).doubleValue());
						}
						else if (nvc.getMetaTypeBase().equals(Boolean.class))
						{
							nve.setValue(nvc.getName(), ((JSONBoolean) value.get(nvc.getName())).booleanValue());
						}
						else if (nvc.getMetaType().equals(BigDecimal.class))
						{
							double val = ((JSONNumber) value.get(nvc.getName())).doubleValue();
							BigDecimal bd = new BigDecimal(val);
							bd = bd.setScale(3, RoundingMode.HALF_EVEN);
							nve.setValue(nvc.getName(), bd);
						}
						else if (nvc.getMetaType().isEnum())
						{
							Enum<?> e = SharedUtil.lookupEnum((Enum<?>[]) nvc.getMetaType().getEnumConstants(), ((JSONString)jsonValue).stringValue());
							
							if (e!= null)
							{
								nve.setValue(nvc.getName(), e);
							}
						}
					}
					else if (nvc.getMetaType().equals(byte[].class))
					{
						nve.setValue(nvc.getName(), SharedBase64.decode(((JSONString) value.get(nvc.getName())).stringValue().getBytes()));
					}
					else
					{
						Class<?> metaBase = nvc.getMetaTypeBase();
						JSONArray jsonArray = (JSONArray) value.get(nvc.getName());
						NVBase<?> nvb = nve.lookup(nvc);
						
						if (jsonArray == null)
						{
							continue;
						}
						
						if (metaBase == String.class)
						{
							ArrayValues<NVPair> list = (ArrayValues<NVPair>) nvb;
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								list.add(toNVPair((JSONObject) jsonArray.get(i)));
							}
						}
						else if (metaBase == Long.class)
						{
							List<Long> list = new ArrayList<Long>();
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								list.add((long) ((JSONNumber) jsonArray.get(i)).doubleValue());
							}
							
							nve.setValue(nvc.getName(), list);
						}
						else if (metaBase == Integer.class)
						{
							List<Integer> list = new ArrayList<Integer>();
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								list.add((int) ((JSONNumber) jsonArray.get(i)).doubleValue());
							}
							
							nve.setValue(nvc.getName(), list);
						}
						else if (metaBase == Float.class)
						{
							List<Float> list = new ArrayList<Float>();
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								list.add((float) ((JSONNumber) jsonArray.get(i)).doubleValue());
							}
							
							nve.setValue(nvc.getName(), list);
						}
						else if (metaBase == Double.class)
						{
							List<Double> list = new ArrayList<Double>();
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								list.add((double) ((JSONNumber) jsonArray.get(i)).doubleValue());
							}
							
							nve.setValue(nvc.getName(), list);
						}
						else if (metaBase == BigDecimal.class)
						{
							List<BigDecimal> list = new ArrayList<BigDecimal>();
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								double val = ((JSONNumber) jsonArray.get(i)).doubleValue();
								BigDecimal bd = new BigDecimal(val);
								bd = bd.setScale(3, RoundingMode.HALF_EVEN);
								list.add(bd);
							}
							
							nve.setValue(nvc.getName(), list);
						}
						else if (metaBase.isEnum())
						{
							List<Enum<?>> list = new ArrayList<Enum<?>>();
							
							for (int i = 0; i < jsonArray.size(); i++)
							{
								Enum<?> e = SharedUtil.lookupEnum((Enum<?>[]) metaBase.getEnumConstants(), ((JSONString) jsonArray.get(i)).stringValue());
								
								if (e != null)
								{
									list.add(e);
								}
							}
							
							nve.setValue(nvc.getName(), list);
						}
					}	
				}
			}
		}
		
		return nve;
	}

	private static NVPair toNVPair(JSONObject jsonObject)
	{
		NVPair nvp = new NVPair();
	
		Set<String> keySet = jsonObject.keySet();
	
		if (keySet.contains(MetaToken.NAME.getName()) && keySet.contains(MetaToken.VALUE.getName()))
		{
			// Set up with name and value prefix
			// e.g. {"name":"last_name", "value":"Zebib"}
			
			JSONValue jsonValue = jsonObject.get(MetaToken.VALUE.getName());
			
			if (jsonValue != null && jsonValue instanceof JSONString)
			{
				nvp.setValue(((JSONString) jsonValue).stringValue());
			}
			
			JSONValue jsonName = jsonObject.get(MetaToken.NAME.getName());
			
			if (jsonName != null && jsonName instanceof JSONString)
			{
				nvp.setName(((JSONString) jsonName).stringValue());
			}
		}
		else
		{
			// Set up without name and value prefix
			// e.g. {"last_name":"Zebib"}
			
			String name = null;
			
			for (String str : keySet)
			{
				if (!str.equals(MetaToken.NAME.getName()) && !str.equals(MetaToken.VALUE.getName()) && !str.equals(MetaToken.VALUE_FILTER.getName()))
				{
					name = str;
					break;
				}
			}
			
			if (name != null)
			{
				nvp.setName(name);
				JSONValue jsonValue = jsonObject.get(name);
				
				if (jsonValue != null && jsonValue instanceof JSONString)
				{
					nvp.setValue(((JSONString) jsonValue).stringValue());
				}
			}
		}
		
		JSONString jsonVF = (JSONString) jsonObject.get(MetaToken.VALUE_FILTER.getName());
		
		if (jsonVF != null)
		{
			ValueFilter<String, String> vf = (FilterType) SharedUtil.lookupEnum(FilterType.values(), jsonVF.stringValue());
			
			if (vf == null)
			{
				vf = DynamicEnumMapManager.SINGLETON.lookup(jsonVF.stringValue());
			}

			if (vf != null)
			{
				nvp.setValueFilter(vf);
			}
			
		}
		
//		JSONValue jsonVF = jsonObject.get(MetaToken.VALUE_FILTER.getName());
//		
//		if (jsonVF != null)
//		{
//			if (jsonVF instanceof JSONString)
//			{
//				FilterType ft = (FilterType) SharedUtil.lookupEnum(FilterType.values(), ((JSONString) jsonVF).stringValue());
//				
//				if (ft != null)
//				{
//					nvp.setValueFilter(ft);
//				}
//			}
//			else if (jsonVF instanceof JSONObject)
//			{
//				DynamicEnumMap dem = fromJSONDynamicEnumMap(jsonVF.toString());
//				
//				if (dem != null)
//				{
//					nvp.setValueFilter(dem);
//				}
//			}
//		}
		
		return nvp;
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject toJSON(NVEntity nve)
	{
		SharedUtil.checkIfNulls("Null NVEntity", nve);
		JSONObject jsonObject = new JSONObject();
		
		NVConfigEntity nvce = (NVConfigEntity) nve.getNVConfig();
		
		jsonObject.put(MetaToken.CLASS_TYPE.getName(), new JSONString(nve.getClass().getName()));
		
		for (NVConfig nvc : nvce.getAttributes())
		{
//			if (nvc instanceof NVConfigEntity)
//			{
//				if (!nvc.isArray() && nve.lookupValue(nvc) != null)
//				{
//					jsonObject.put(nvc.getName(), toJSON((NVEntity) nve.lookupValue(nvc)));
//				}
//			}
//			else if (nvc instanceof NVConfig)
			{
				if (!nvc.isArray())
				{
					JSONValue jsonValue = null;
					Object value = nve.lookupValue(nvc);
					
					if (value != null)
					{
						if (value instanceof NVEntity)
						{
							jsonObject.put(nvc.getName(), toJSON((NVEntity) value));
						}
						
						else if (nvc.getMetaTypeBase().equals(String.class))
						{
							jsonValue = new JSONString((String) value);
						}
						else if (
								 nvc.getMetaTypeBase().equals(Long.class) ||
								 nvc.getMetaTypeBase().equals(Integer.class) ||
								 nvc.getMetaTypeBase().equals(Float.class) ||
								 nvc.getMetaTypeBase().equals(Double.class) ||
								 nvc.getMetaTypeBase().equals(BigDecimal.class)
								)
						{
							jsonValue = new JSONNumber(((Number) value).doubleValue());
						}
						else if (nvc.getMetaTypeBase().equals(Date.class))
						{
							jsonValue = new JSONNumber(((Number) value).doubleValue());
						}
						else if (nvc.getMetaTypeBase().equals(Boolean.class))
						{
							jsonValue = JSONBoolean.getInstance((Boolean) value);
						}
						else if (value instanceof Enum)
						{
							jsonValue = new JSONString(((Enum<?>)value).name());
						}
						
						if (jsonValue != null)
						{
							jsonObject.put(nvc.getName(), jsonValue);
						}
					}
				}
				else
				{
					JSONArray jsonArray = new JSONArray();
					int counter = 0;
					NVBase<?> nvb = nve.lookup(nvc);
					Class<?> metaBase = nvc.getMetaTypeBase();
					
					if (metaBase == String.class)
					{
						ArrayValues<NVPair> values = (ArrayValues<NVPair>) nvb;
						for (NVPair nvp : values.values())
						{
							JSONObject nvpJSON = toJSON(nvp);
							
							if (nvpJSON != null)
							{
								jsonArray.set(counter++, nvpJSON);
							}
						}
					}
					else if (nvb instanceof NVEntityReferenceList || nvb instanceof NVEntityReferenceIDMap || nvb instanceof NVEntityGetNameMap)
					{
						ArrayValues<NVEntity> values = (ArrayValues<NVEntity>) nvb;
						for (NVEntity nveTemp : values.values())
						{
							if (nveTemp != null)
							{
								jsonArray.set(counter++, toJSON(nveTemp));
							}
						}
					}
					else if (metaBase == Long.class)
					{
						NVLongList values = (NVLongList) nvb;
						for (Long val : values.getValue())
						{
							if (val != null)
							{
								jsonArray.set(counter++, new JSONNumber((double)val));
							}
						}
					}
					else if (metaBase == Integer.class)
					{
						NVIntList values = (NVIntList) nvb;
						for (Integer val : values.getValue())
						{
							if (val != null)
							{
								jsonArray.set(counter++, new JSONNumber((double)val));
							}
						}
					}
					else if (metaBase == Float.class)
					{
						NVFloatList values = (NVFloatList) nvb;
						for (Float val : values.getValue())
						{
							if (val != null)
							{
								jsonArray.set(counter++, new JSONNumber((double)val));
							}
						}
					}
					else if (metaBase == Double.class)
					{
						NVDoubleList values = (NVDoubleList) nvb;
						for (Double val : values.getValue())
						{
							if (val != null)
							{
								jsonArray.set(counter++, new JSONNumber((double)val));
							}
						}
					}
					else if (metaBase == BigDecimal.class)
					{
						NVBigDecimalList values = (NVBigDecimalList) nvb;
						for (BigDecimal val : values.getValue())
						{
							if (val != null)
							{
								jsonArray.set(counter++, new JSONNumber(val.doubleValue()));
							}
						}
					}
					else if (metaBase.isEnum())
					{
						NVEnumList values = (NVEnumList) nvb;
						for (Enum<?> e : values.getValue())
						{
							
							if (e != null)
							{
								jsonArray.set(counter++, new JSONString(e.name()));
							}
						}
					}
					
					else if (nvc.getMetaType() == byte[].class)
					{
						if (nvb.getValue() == null)
						{
							jsonObject.put(nvc.getName(), new JSONString(null));
						}
						else
						{
							byte base64[] = SharedBase64.encode( ((NVBlob)nvb).getValue());
							jsonObject.put(nvc.getName(), new JSONString(new String(base64)));
							
						}
						// so we don't add the array
						continue;
					}
					
					jsonObject.put(nvc.getName(), jsonArray);
					// we have a primitive array
				}
			}
		}
		
		return jsonObject;
	}
	
	public static JSONObject toJSON(NVPair nvp)
	{
		JSONObject ret = null;
		if (nvp != null)
		{
			if (nvp.getName() != null)
			{
				ret = new JSONObject();
				
				if (nvp.getValueFilter() == null || nvp.getValueFilter() == FilterType.CLEAR)
				{
					ret.put(nvp.getName(), nvp.getValue() != null ? new JSONString(nvp.getValue()) : JSONNull.getInstance());
				}
				else
				{
					ret.put(MetaToken.NAME.getName(), new JSONString(nvp.getName()));
					ret.put(MetaToken.VALUE.getName(), nvp.getValue() != null ? new JSONString(nvp.getValue()) : JSONNull.getInstance());
					
//					if (nvp.getValueFilter() instanceof DynamicEnumMap)
//					{
//						ret.put(MetaToken.VALUE_FILTER.getName(), toJSONDynamicEnumMap((DynamicEnumMap) nvp.getValueFilter()));
//					}
//					else
					{
						ret.put(MetaToken.VALUE_FILTER.getName(), new JSONString(nvp.getValueFilter().toCanonicalID()));
					}
				}
			}
			else
			{
				// name == null
			}
		}
		
		return ret;
	}	
	
	public static JSONObject toJSONQuery(String className, int batchSize, List<QueryMarker> markers)
	{
		return toJSONQuery(className, batchSize, markers != null ? markers.toArray(new QueryMarker[0]) : null);
	}
	
	public static JSONObject toJSONQuery(String className, int batchSize, QueryMarker ...markers)
	{
		QueryRequest qr = new QueryRequest();
		qr.setCanonicalID(className);
		qr.setBatchSize(batchSize);
		qr.setQuery(markers);
		
		return toJSONQuery(qr);
	}
	
	public static JSONObject toJSONQuery(QueryRequest qr)
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(MetaToken.CANONICAL_ID.getName(), new JSONString(qr.getCanonicalID()));
				
		jsonObject.put("batch_size", new JSONNumber(qr.getBatchSize()));
		
		if (qr.getFieldNames() != null)
		{
			JSONArray ja = new JSONArray();
			int i = 0;
			
			for (String fn : qr.getFieldNames())
			{
				if (!SharedStringUtil.isEmpty(fn))
				{
					ja.set(i++, new JSONString(fn));
				}
			}
			
			jsonObject.put("field_names", ja);
		}
		
		if (qr.getQuery() != null)
		{
			JSONArray ja = new JSONArray();
			int i = 0;
			for (QueryMarker qm : qr.getQuery())
			{
				if (qm != null)
				{
					JSONObject qmJSON = new JSONObject();
					if (qm instanceof GetNameValue)
					{
						if (qm instanceof QueryMatch)
						{
							QueryMatch<?> qMatch = (QueryMatch<?>) qm;
							Object value = qMatch.getValue();
							
							if (value instanceof Number)
							{
								qmJSON.put(qMatch.getName(), new JSONNumber(((Number)value).doubleValue()));
							}
							else if (value instanceof String)
							{
								qmJSON.put(qMatch.getName(), new JSONString((String) value));
							}
							else if (value instanceof Enum)
							{
								qmJSON.put(qMatch.getName(), new JSONString(((Enum<?>) value).name()));
							}
							
							if (qMatch.getOperator() != null)
								qmJSON.put(MetaToken.RELATIONAL_OPERATOR.getName(), new JSONString(qMatch.getOperator().name()));
						}
					}
					else if (qm instanceof LogicalOperator)
					{
						qmJSON.put(MetaToken.LOGICAL_OPERATOR.getName(), new JSONString(((LogicalOperator)qm).name()));
					}
					
					ja.set(i++, qmJSON);
				}
			}
			
			jsonObject.put("query", ja);
			
		}
		
		
		return jsonObject;
	}
	
	public static JSONObject toJSONWrapper(NVEntity nve)
	{
		JSONObject value = toJSON(nve);		
		JSONObject ret = new JSONObject();
		
		ret.put(MetaToken.JSON_CONTENT.getName(), new JSONString("" + value));
		
		return ret;		
	}
	
	public static JSONObject toJSONMap(Map<String, ?> map)
	{
		JSONObject ret = new JSONObject();
		
		if (map != null)
		{
			for (Entry<String, ?> entry :  map.entrySet())
			{
				if (entry.getValue() instanceof List)
				{
					List<?> list = (List<?>) entry.getValue();
					JSONArray jsonArray = new JSONArray();
					
					for (int i = 0; i < list.size(); i++)
					{
						if (list.get(i) instanceof String)
						{
							jsonArray.set(i, new JSONString((String) list.get(i)));
						}
						else if (list.get(i) instanceof NVEntity)
						{
							jsonArray.set(i, toJSON((NVEntity) list.get(i)));
						}
						else if (list.get(i) instanceof Boolean)
						{
							jsonArray.set(i, JSONBoolean.getInstance((Boolean) entry.getValue()));
						}
					}
					
					ret.put(entry.getKey(), jsonArray);
				}
				else
				{
					if (entry.getValue() instanceof String)
					{
						ret.put(entry.getKey(), new JSONString((String) entry.getValue()));
					}
					else if (entry.getValue() instanceof NVEntity)
					{
						ret.put(entry.getKey(), toJSON((NVEntity) entry.getValue()));
					}
					else if (entry.getValue() instanceof Boolean)
					{
						ret.put(entry.getKey(), JSONBoolean.getInstance((Boolean) entry.getValue()));
					}
					
				}
			}
			
		}
		
		return ret;
	}
	
	public static Map<String, ?> fromJSONMap(String json, NVEntityFactory nveFactory)
	{
		Map<String, Object> ret = new HashMap<String, Object>();
		
		JSONObject jsonObject = (JSONObject) JSONParser.parseLenient(json);
		
		if (jsonObject != null)
		{
			for (String key : jsonObject.keySet())
			{
				JSONValue jsonValue = jsonObject.get(key);
				
				if (jsonValue instanceof JSONArray)
				{
					JSONArray jsonArray = (JSONArray) jsonValue;
					
					List<Object> list = new ArrayList<Object>();
										
					for (int i = 0; i < jsonArray.size(); i++)
					{
						if (jsonArray.get(i) instanceof JSONObject)
						{
							NVEntity nve = fromJSON(null, (JSONObject) jsonArray.get(i), nveFactory);
							list.add(nve);
						}
						else if (jsonArray.get(i) instanceof JSONString)
						{
							list.add(((JSONString) jsonArray.get(i)).stringValue());
						}
						else if (jsonArray.get(i) instanceof JSONBoolean)
						{
							list.add(((JSONBoolean) jsonArray.get(i)).booleanValue());
						}
					}
					
					ret.put(key, list);
				}
				else if (jsonValue instanceof JSONObject)
				{
					NVEntity val = fromJSON(null, (JSONObject) jsonValue, nveFactory);
					ret.put(key, val);
				}
				else if (jsonValue instanceof JSONString)
				{
					String val = ((JSONString) jsonValue).stringValue();
					ret.put(key, val);
				}
				else if (jsonValue instanceof JSONBoolean)
				{
					boolean val = ((JSONBoolean) jsonValue).booleanValue();
					ret.put(key, val);
				}
			}
			
		}
		
		return ret;
	}
	
	public static JSONObject toJSONDynamicEnumMap(DynamicEnumMap dem)
	{
		JSONObject ret = new JSONObject();
		
		if (dem != null)
		{
			ret.put(MetaToken.REFERENCE_ID.getName(), new JSONString((String) dem.getReferenceID()));
			ret.put(MetaToken.USER_ID.getName(), new JSONString((String) dem.getUserID()));
			ret.put(MetaToken.ACCOUNT_ID.getName(), new JSONString((String) dem.getAccountID()));
			ret.put(MetaToken.NAME.getName(), new JSONString((String) dem.getName()));
			ret.put(MetaToken.DESCRIPTION.getName(), new JSONString((String) dem.getDescription()));
			ret.put(MetaToken.IS_FIXED.getName(), JSONBoolean.getInstance((Boolean) dem.isFixed()));
			ret.put(MetaToken.STATIC.getName(), JSONBoolean.getInstance((Boolean) dem.isStatic()));
			ret.put(MetaToken.IGNORE_CASE.getName(), JSONBoolean.getInstance((Boolean) dem.isIgnoreCase()));
			
			JSONArray jsonArray = new JSONArray();
			
			for (int i = 0; i < dem.size(); i++)
			{				
				jsonArray.set(i, toJSON(dem.getValue().get(i)));
			}
			
			ret.put(MetaToken.VALUE.getName(), jsonArray);
		}
		
		return ret;
	}
	
	public static DynamicEnumMap fromJSONDynamicEnumMap(String json)
	{
		DynamicEnumMap dem = new DynamicEnumMap();
		
		JSONObject jsonObject = (JSONObject) JSONParser.parseLenient(json);
		
		if (jsonObject != null)
		{
			if (jsonObject.get(MetaToken.REFERENCE_ID.getName()) != null && jsonObject.get(MetaToken.REFERENCE_ID.getName()) instanceof JSONString)
			{
				dem.setReferenceID(((JSONString) jsonObject.get(MetaToken.REFERENCE_ID.getName())).stringValue());
			}
			
			if (jsonObject.get(MetaToken.USER_ID.getName()) != null && jsonObject.get(MetaToken.USER_ID.getName()) instanceof JSONString)
			{
				dem.setUserID(((JSONString) jsonObject.get(MetaToken.USER_ID.getName())).stringValue());
			}
			
			if (jsonObject.get(MetaToken.ACCOUNT_ID.getName()) != null && jsonObject.get(MetaToken.ACCOUNT_ID.getName()) instanceof JSONString)
			{
				dem.setAccountID(((JSONString) jsonObject.get(MetaToken.ACCOUNT_ID.getName())).stringValue());
			}
			
			if (jsonObject.get(MetaToken.NAME.getName()) != null && jsonObject.get(MetaToken.NAME.getName()) instanceof JSONString)
			{
				dem.setName(((JSONString) jsonObject.get(MetaToken.NAME.getName())).stringValue());
			}
			
			if (jsonObject.get(MetaToken.DESCRIPTION.getName()) != null && jsonObject.get(MetaToken.DESCRIPTION.getName()) instanceof JSONString)
			{
				dem.setDescription(((JSONString) jsonObject.get(MetaToken.DESCRIPTION.getName())).stringValue());
			}
			
			if (jsonObject.get(MetaToken.IS_FIXED.getName()) != null && jsonObject.get(MetaToken.IS_FIXED.getName()) instanceof JSONBoolean)
			{
				dem.setFixed(((JSONBoolean) jsonObject.get(MetaToken.IS_FIXED.getName())).booleanValue());
			}
			
			if (jsonObject.get(MetaToken.STATIC.getName()) != null && jsonObject.get(MetaToken.STATIC.getName()) instanceof JSONBoolean)
			{
				dem.setStatic(((JSONBoolean) jsonObject.get(MetaToken.STATIC.getName())).booleanValue());
			}
			
			if (jsonObject.get(MetaToken.IGNORE_CASE.getName()) != null && jsonObject.get(MetaToken.IGNORE_CASE.getName()) instanceof JSONBoolean)
			{
				dem.setIgnoreCase(((JSONBoolean) jsonObject.get(MetaToken.IGNORE_CASE.getName())).booleanValue());
			}
			
			if (jsonObject.get(MetaToken.VALUE.getName()) != null && jsonObject.get(MetaToken.VALUE.getName()) instanceof JSONArray)
			{
				JSONArray jsonArray = (JSONArray) jsonObject.get(MetaToken.VALUE.getName());
													
				for (int i = 0; i < jsonArray.size(); i++)
				{
					dem.addEnumValue(toNVPair((JSONObject) jsonArray.get(i)));
				}
			}
			
		}
		
		return dem;
	}
	
	public static JSONObject toJSONDynamicEnumMapList(List<DynamicEnumMap> list)
	{
		JSONObject ret = new JSONObject();
		
		if (list != null && list.size() > 0)
		{
			JSONArray jsonArray = new JSONArray();
			
			for (int i = 0; i < list.size(); i++)
			{
				JSONObject jo = toJSONDynamicEnumMap(list.get(i));
				
				if (jo != null)
				{
					jsonArray.set(i, jo);
				}
			}
			
			ret.put(MetaToken.VALUES.getName(), jsonArray);
		}
		
		return ret;
	}
	
	public static List<DynamicEnumMap> fromJSONDynamicEnumMapList(String json) 
	{
		JSONObject jo = (JSONObject) JSONParser.parseLenient(json);
		JSONArray values = (JSONArray) jo.get(MetaToken.VALUES.getName());
		List<DynamicEnumMap> ret = new ArrayList<DynamicEnumMap>();
		
		for (int i = 0; i < values.size(); i++)
		{
			ret.add(fromJSONDynamicEnumMap(values.get(i).toString()));
		}
		
		return ret;
	}
	
}