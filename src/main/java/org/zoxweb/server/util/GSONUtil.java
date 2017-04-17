/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.server.util;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.zoxweb.shared.db.QueryMarker;
import org.zoxweb.shared.db.QueryMatch;
import org.zoxweb.shared.db.QueryMatchLong;
import org.zoxweb.shared.db.QueryMatchString;
import org.zoxweb.shared.db.QueryRequest;
import org.zoxweb.shared.filters.FilterType;
import org.zoxweb.shared.filters.ValueFilter;
import org.zoxweb.shared.util.ArrayValues;
import org.zoxweb.shared.util.Const;
import org.zoxweb.shared.util.DynamicEnumMap;
import org.zoxweb.shared.util.DynamicEnumMapManager;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.MetaToken;
import org.zoxweb.shared.util.NVBase;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVEntity;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedStringUtil;
import org.zoxweb.shared.util.SharedUtil;
import org.zoxweb.shared.util.Const.LogicalOperator;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

/**
 * This utility class convert NVEnity Object to json and a json object to an NVEntity.
 * It uses Gson from google 
 * @author mnael
 */
final public class GSONUtil 
{

	private static final transient Logger log = Logger.getLogger(Const.LOGGER_NAME);
	
	private final static GSONUtil SINGLETON = new GSONUtil();
	
	private GsonBuilder builder = null;
	
	private GSONUtil()
    {
		builder = new GsonBuilder();
		builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		builder.setPrettyPrinting();
		log.info("Created");
	}
	
	public static Gson create(boolean pretty)
    {
		if (pretty)
		{
			return SINGLETON.builder.create();
		}
		else
        {
			return new Gson();
		}
	}
	
	public static String toJSON(NVEntity nve, boolean indent) 
        throws IOException
    {
		return toJSON(nve, indent, true, true);
	}
	
	public static String toJSON(NVEntity nve, boolean indent, boolean printNull, boolean printClassType) 
        throws IOException
    {
		StringWriter sw = new StringWriter();
		JsonWriter writer = new JsonWriter(sw);
		writer.setSerializeNulls(true);
		writer.setHtmlSafe(true);
		
		if (indent)
		{
			writer.setIndent("  ");
		}
		
		toJSON(writer, nve.getClass(), nve, printNull, printClassType);
		
		writer.close();
		
		return sw.toString();
	}
	
	public static String toJSONWrapper(String wrapName, NVEntity nve, boolean indent, boolean printNull, boolean printClassType) 
        throws IOException
    {
		StringWriter sw = new StringWriter();
		JsonWriter writer = new JsonWriter(sw);
		writer.setSerializeNulls(true);
		writer.setHtmlSafe(true);
		
		if (indent)
		{
			writer.setIndent("  ");
		}
		
		writer.beginObject();
		writer.name(wrapName);
		toJSON(writer, nve.getClass(), nve, printNull, printClassType);
		writer.endObject();
		writer.close();
		
		return sw.toString();
	}
	
	public static QueryRequest fromQueryRequest(String json)
    {
		JsonElement je = new JsonParser().parse(json);
		QueryRequest ret = null;

		if (je instanceof JsonObject)
		{
			ret = new QueryRequest();
			JsonObject jo = (JsonObject) je;
			ret.setCanonicalID(jo.get(MetaToken.CANONICAL_ID.getName()).getAsString());
			JsonElement batchSize = jo.get("batch_size");

			if (batchSize != null)
			{
				ret.setBatchSize(batchSize.getAsInt());
			}
			
			JsonArray jaFNs = (JsonArray) jo.get("field_names");

			if (jaFNs != null)
			{
				List<String> fieldNames = new ArrayList<String>();
				
				for (int i = 0; i < jaFNs.size(); i++)
				{
					fieldNames.add(jaFNs.get(i).getAsString());
				}
				
				ret.setFieldNames(fieldNames);
			}
			
			JsonArray jaQuery = (JsonArray) jo.get("query");
			if (jaQuery != null)
			{
				List<QueryMarker> qms = new ArrayList<QueryMarker>();
				for (int i = 0; i < jaQuery.size(); i++)
				{
					// get the query marker
					JsonObject joQM = (JsonObject) jaQuery.get(i);
					QueryMarker qm = null;
					
					JsonPrimitive lo = (JsonPrimitive) joQM.get(MetaToken.LOGICAL_OPERATOR.getName());
					
					if (lo != null)
					{
						qm = Const.LogicalOperator.valueOf(lo.getAsString());
					}
					else
                    {
						Const.RelationalOperator ro = null;
						
						JsonPrimitive jpRO = (JsonPrimitive) joQM.get(MetaToken.RELATIONAL_OPERATOR.getName());
						
						if (jpRO != null)
						{
							ro = Const.RelationalOperator.valueOf(jpRO.getAsString());
						}
						
						String name = null;
						JsonElement value = null;
						
						Set<Map.Entry<String, JsonElement>> allParams = joQM.entrySet();
						
						for (Map.Entry<String, JsonElement> e : allParams)
						{
							if (!e.getKey().equals(MetaToken.RELATIONAL_OPERATOR.getName()))
							{
								name = e.getKey();
								value = e.getValue();
								break;
							}
						}
						
						// try to guess the type
						if (value.isJsonPrimitive())
						{
							JsonPrimitive jp = (JsonPrimitive) value;
							
							if (jp.isString())
							{
								qm = new QueryMatchString(ro, jp.getAsString(), name);
							}
							else if (jp.isNumber())
							{
								qm = new QueryMatchLong(ro, jp.getAsLong(), name);
							}
						}
					}
					
					if (qm != null)
					{
						qms.add(qm);
					}
				}
				
				ret.setQuery(qms);
			}
		}
		
		return ret;
	}
	
	private static String toJSONValue(Enum <?> e)
    {
		if (e == null)
		{
			return null;
		}
		
		return e.name();
	}
	
	@SuppressWarnings("unchecked")
	private static JsonWriter toJSON(JsonWriter writer, Class<? extends NVEntity> clazz, NVEntity nve, boolean printNull, boolean printClassType) 
        throws IOException
    {

		NVConfigEntity nvce = (NVConfigEntity) nve.getNVConfig();
		
		writer.beginObject();
		
		if (clazz!= null  && clazz != nve.getClass() || (clazz != null && printClassType))
		{
			writer.name(MetaToken.CLASS_TYPE.getName()).value(nve.getClass().getName());
		}
		else if (nvce.getMetaType().isInterface() || Modifier.isAbstract(nvce.getMetaType().getModifiers()))
		{
			writer.name(MetaToken.CLASS_TYPE.getName()).value(nve.getClass().getName());
		}
	
		List <NVConfig> attributes = nvce.getAttributes();
		
		for (int i = 0; i < attributes.size(); i++)
		{
			NVConfig nvc = attributes.get( i);
			//Class<?> type = nvc.getMetaType();
			
			if (!printNull)
			{
				Object tempObj = nve.lookupValue(nvc);
				if (tempObj == null || 
					(tempObj instanceof List && ((List<?>)tempObj).size() == 0) ||
					(tempObj instanceof Map && ((Map<?,?>)tempObj).size() == 0))
				{
					continue;
				}
			}
			
			if (nvc.isArray())
			{
				writer.name(nvc.getName());
				
				if (byte[].class.equals(nvc.getMetaType()))
				{
					byte[] value = nve.lookupValue(nvc);				
					writer.value(value != null ?  new String(SharedBase64.encode(value)) : null);
				}
				else
                {
					writer.beginArray();
					
					if (nvc.isEnum())
					{
						List<Enum<?>> eAll = nve.lookupValue(nvc);
						
						for (Enum<?> e: eAll)
						{
							writer.value(toJSONValue(e));
						}
					}
					else if (nvc.getMetaTypeBase() == String.class)
					{
						ArrayValues<GetNameValue<String>> tempArray = (ArrayValues<GetNameValue<String>>) nve.lookup(nvc.getName());
						
						for (GetNameValue<String> nvp : tempArray.values())
						{
							toJSON(writer, nvp, true, printNull);
						}
//						
//						if (!nvc.isUnique())
//						{
//							List<NVPair> all = nve.lookupValue(nvc);
//							for ( NVPair nvp : all)
//							{
//								toJSON( writer, nvp, true, printNull);
//							}
//						}
//						else
//						{
//							NVPairGetNameMap nvpm = (NVPairGetNameMap) nve.lookup(nvc.getName());
//							for (NVPair nvp : nvpm.getValue().values())
//							{
//								toJSON( writer, nvp, true, printNull);
//							}
//						}
					}
					else if (nvc.getMetaTypeBase() == Long.class)
					{
						List<Long> values = nve.lookupValue(nvc);
						
						for (long v : values)
						{
							writer.value(v);
						}
					}

					else if (nvc.getMetaTypeBase() == Integer.class)
					{
						List<Integer> values = nve.lookupValue(nvc);
						
						for (Integer v : values)
						{
							writer.value(v);
						}
					}
					else if (nvc.getMetaTypeBase() == Float.class)
					{
						List<Float> values = nve.lookupValue(nvc);
						
						for (Float v : values)
						{
							writer.value(v);
						}
					}
					else if (nvc.getMetaTypeBase() == Double.class)
					{
						List<Double> values = nve.lookupValue(nvc);
						
						for (Double v : values)
						{
							writer.value(v);
						}
					}
					else if (nvc.getMetaTypeBase() == Boolean.class)
					{
						List<Boolean> values = nve.lookupValue(nvc);
						
						for (boolean b : values)
						{
							writer.value(b);
						}
					}
					else if (nvc instanceof NVConfigEntity)
					{
						ArrayValues<NVEntity> tempArray = (ArrayValues<NVEntity>) nve.lookup(nvc.getName());
						
						for (NVEntity value : tempArray.values())
						{
							toJSON( writer, (Class<? extends NVEntity>) nvc.getMetaTypeBase(), value, printNull, printClassType);
						}						
						
//						if (!nvc.isUnique())
//						{
//							List<NVEntity> values = nve.lookupValue(nvc);
//							for ( NVEntity value : values)
//							{
//								toJSON( writer, (Class<? extends NVEntity>) nvc.getMetaTypeBase(), value, printNull);
//							}
//						}
//						else
//						{
//							Map<GetName, NVEntity> values = nve.lookupValue(nvc);
//							for ( NVEntity value : values.values())
//							{
//								toJSON( writer, (Class<? extends NVEntity>) nvc.getMetaTypeBase(), value, printNull);
//							}
//						}
					}
					else if (nvc.getMetaTypeBase() == Date.class)
					{
						List<Long> values = nve.lookupValue(nvc);
						
						for (long v : values)
						{
							writer.value(v);
						}
					}
					else if (nvc.getMetaTypeBase() == BigDecimal.class)
					{
						List<BigDecimal> values = nve.lookupValue(nvc);
						
						for (BigDecimal v : values)
						{
							writer.value(v);
						}
					}
					
					writer.endArray();
				}
			}
			else
            {
				if (nvc.isEnum())
				{
					if (nvc.getMetaTypeBase().isAssignableFrom(DynamicEnumMap.class))
					{
						writer.name(nvc.getName()).value((String) nve.lookupValue(nvc));
					}
					else
                    {
						Enum<?> e = nve.lookupValue(nvc);
						writer.name( nvc.getName()).value( toJSONValue(e));
					}
				}
				else if (nvc.getMetaTypeBase() == String.class)
				{
					toJSON(writer, (NVPair)nve.lookup(nvc.getName()), false, printNull);
					//writer.name( nvc.getName()).value((String)nve.lookupValue(nvc));
				}
				else if (nvc.getMetaTypeBase() == Long.class )
				{
					if ((long)nve.lookupValue(nvc) != 0)
					{
						writer.name( nvc.getName()).value((long) nve.lookupValue(nvc));
					}
				}
				else if (nvc.getMetaTypeBase() == Integer.class)
				{
					if ((int) nve.lookupValue(nvc) != 0)
					{
						writer.name( nvc.getName()).value((int) nve.lookupValue(nvc));
					}
				}
				else if (nvc.getMetaTypeBase() == Double.class)
				{
					if ((Double) nve.lookupValue(nvc) != 0)
					{
						writer.name( nvc.getName()).value((double) nve.lookupValue(nvc));
					}
				}
				else if (nvc.getMetaTypeBase() == Float.class)
				{
					if ((Float)nve.lookupValue(nvc) != 0)
					{
						writer.name( nvc.getName()).value((float) nve.lookupValue(nvc));
					}
				}
				else if (nvc.getMetaTypeBase() == Boolean.class) {
					writer.name(nvc.getName()).value((boolean) nve.lookupValue(nvc));
				}
				else if (nvc.getMetaTypeBase() == Date.class)
				{
					if ((long) nve.lookupValue(nvc) != 0)
					{
						writer.name( nvc.getName()).value((long)nve.lookupValue(nvc));
					}
				}
				else if (nvc.getMetaTypeBase() == BigDecimal.class)
				{
					if ((BigDecimal) nve.lookupValue(nvc) != null)
					{
						writer.name(nvc.getName()).value((BigDecimal) nve.lookupValue(nvc));
					}
				} else if (nvc instanceof NVConfigEntity)
				{
					NVEntity tempNVE = (NVEntity)nve.lookupValue(nvc);
					// we need to write the class type if the current object is derived from nvc.getClass()
					// this is we important to accurately rebuild the object
					
					if (tempNVE != null)
					{
						writer.name(nvc.getName());
						toJSON( writer,  (Class<? extends NVEntity>) ((NVConfigEntity) nvc).getMetaType(), (NVEntity)nve.lookupValue(nvc), printNull, printClassType);
					}
					else if (printNull)
					{
						writer.name(nvc.getName());
						writer.nullValue();
					}
				}
			}
		}
	
		writer.endObject();
		
		return writer;
	}
	
	private static JsonWriter toJSON(JsonWriter writer, GetNameValue<String> nvp, boolean isObject, boolean printNull)
        throws IOException
    {

		if (nvp != null && (printNull || nvp.getValue() != null))
		{
			String referenceID = null;
			ValueFilter<String, String> vf = null;
			
			if (nvp instanceof NVPair)
			{
				 referenceID = ((NVPair)nvp).getReferenceID();
				 vf = ((NVPair)nvp).getValueFilter();
			}
			
			//if ( object && isObject)
			if (isObject)
			{
				//writer.name(nvp.getName());
				writer.beginObject();
			
				if (referenceID != null)
				{
					writer.name(MetaToken.REFERENCE_ID.getName()).value(referenceID);
				}
			
				if (nvp.getName() == null)
				{
					writer.name(MetaToken.NAME.getName()).value(nvp.getName());
					writer.name(MetaToken.VALUE.getName()).value(nvp.getValue());
				}
				else
                {
					writer.name(nvp.getName()).value(nvp.getValue());
				}
					
				if (vf != null && FilterType.CLEAR != vf)
				{
					writer.name(MetaToken.VALUE_FILTER.getName()).value(vf.toCanonicalID());
				}
				
				writer.endObject();
			}
			else
            {
				writer.name(nvp.getName()).value(nvp.getValue());
			}
			
		}
		
		return writer;
	}	
	
	public static <V extends NVEntity> V fromJSON(String json) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
		return fromJSON(json, null);
	}
	
	public static Map<String, ?> fromJSONMap(String json) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		JsonElement je = new JsonParser().parse(json);
		
		log.info("JSONElement created from json (String): " + je);
		
		if (je instanceof JsonObject)
		{
			JsonObject jo = (JsonObject) je;
			
			for (Entry<String, JsonElement> element : jo.entrySet())
			{
				if (!element.getValue().isJsonNull())
				{
					if (element.getValue().isJsonArray())
					{
						List<Object> list = new ArrayList<Object>();
						
						JsonArray jsonArray = element.getValue().getAsJsonArray();
						
						for (int i = 0; i < jsonArray.size(); i++)
						{
							if (jsonArray.get(i).isJsonObject())
							{
								NVEntity nve = fromJSON(jsonArray.get(i).getAsJsonObject(), null);
								list.add(nve);
							}
							else if (jsonArray.get(i).isJsonPrimitive())
							{
								JsonPrimitive jsonPrimitive = jsonArray.get(i).getAsJsonPrimitive();
								
								if (jsonPrimitive.isString())
								{
									list.add(jsonArray.get(i).getAsString());
								}
								else if (jsonPrimitive.isBoolean())
								{
									list.add(jsonArray.get(i).getAsBoolean());
								}
							}
						}

						ret.put(element.getKey(), list);
					}
					else if (element.getValue().isJsonObject())
					{
						NVEntity nve = fromJSON(element.getValue().getAsJsonObject(), null);
						ret.put(element.getKey(), nve);
					}
					else if (element.getValue().isJsonPrimitive())
					{
						JsonPrimitive jsonPrimitive = element.getValue().getAsJsonPrimitive();
						
						if (jsonPrimitive.isString())
						{
							ret.put(element.getKey(), jsonPrimitive.getAsString());
						}
						else if (jsonPrimitive.isBoolean())
						{
							ret.put(element.getKey(), jsonPrimitive.getAsBoolean());
						}
					}
				}
				else
				    {
					ret.put(element.getKey(), null);
				}
			}
		}
		
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static String toJSONMap(Map<String, ?> map) 
        throws IOException
    {
		StringWriter sw = new StringWriter();
		JsonWriter writer = new JsonWriter(sw);
		writer.setSerializeNulls(true);
		writer.setHtmlSafe(true);
		writer.setIndent("  ");
		
		writer.beginObject();

		if (map != null && map.size() > 0)
		{
			for (Entry<String, ?> entry : map.entrySet())
			{
				Object value = entry.getValue();
				
				writer.name(entry.getKey());
				
				if (value != null)
				{
					if (value instanceof List)
					{
						List<?> list = (List<?>) value;
						
						writer.beginArray();
						
						for (Object val : list)
						{
							if (val instanceof NVEntity)
							{
								toJSON(writer, (Class<? extends NVEntity>) val.getClass(), (NVEntity) val, false, true);
							}
							else if (val instanceof String)
							{
								writer.value((String) val);
							}
							else if (val instanceof Boolean)
							{
								writer.value((boolean) val);
							}
						}
						
						writer.endArray();
					}
					else if (value instanceof NVEntity)
					{
						toJSON(writer, (Class<? extends NVEntity>) value.getClass(), (NVEntity) value, false, true);
					}
					else if (value instanceof String)
					{
						writer.value((String) value);
					}
					else if (value instanceof Boolean)
					{
						writer.value((boolean) value);
					}
				}
				else
                {
				    writer.nullValue();
				}
			}
		}
		
		writer.endObject();
		writer.close();
		
		return sw.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <V extends NVEntity> V fromJSON(String json, Class<? extends NVEntity> clazz) 
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
		JsonElement je = new JsonParser().parse(json);
		
		if (je instanceof JsonObject)
		{
			return (V) fromJSON((JsonObject)je, clazz);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static NVEntity fromJSON(JsonObject jo, Class<? extends NVEntity> clazz)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {

		// check if the jo has class name setup
		// before creating the new instance
		JsonElement classType = jo.get(MetaToken.CLASS_TYPE.getName());

		if (classType != null)
		{
			if (!classType.isJsonNull())
			{
				clazz = (Class<? extends NVEntity>) Class.forName( classType.getAsString());
			}
		}
		
		NVEntity nve = null;
		
		try
        {
			nve = clazz.newInstance();
		}
		catch(InstantiationException ie)
        {
		    ie.printStackTrace();
			log.info("Error class:" + clazz);
			log.info("" + jo.toString());
			throw ie;
		}
		
		if (jo.get(MetaToken.REFERENCE_ID.getName()) != null && !jo.get(MetaToken.REFERENCE_ID.getName()).isJsonNull())
		{
			nve.setReferenceID(jo.get(MetaToken.REFERENCE_ID.getName()).getAsString());
		}	
		
		NVConfigEntity mcEntity = (NVConfigEntity) nve.getNVConfig();
	
		List<NVConfig> nvconfigs = mcEntity.getAttributes();

		for (NVConfig nvc: nvconfigs)
		{
			Class<?> metaType = nvc.getMetaType();
			JsonElement je = jo.get(nvc.getName());
			
			if (je != null && !je.isJsonNull())
			{
				NVBase<?> nvb = nve.lookup(nvc.getName());
				
				if (nvc.isArray())
				{
					//if ( nvb instanceof NVBase<List<NVEntity>>)
					
					//if ( NVEntity.class.isAssignableFrom( metaType.getComponentType()))
					if (NVEntity.class.isAssignableFrom(nvc.getMetaTypeBase()))
					{
						ArrayValues<NVEntity> tempArray = (ArrayValues<NVEntity>) nvb;
						JsonArray jsonArray = je.getAsJsonArray();
						for (int i = 0; i< jsonArray.size(); i++)
						{
							JsonObject jobj = jsonArray.get(i).getAsJsonObject();
//							try
							{
								tempArray.add(fromJSON(jobj, (Class<? extends NVEntity>) nvc.getMetaTypeBase()));
							}
//							catch (InstantiationException ie)
//							{
//								log.info("nvc:" + nvc.getName() + ":" + nvc.getMetaTypeBase());
//								throw ie;
//							}
							//nvl.getValue().add( toNVPair( jobj));		
						}
					}
					// enum must be checked first
					else if (metaType.getComponentType().isEnum())
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<List<Enum<?>>> nel = (NVBase<List<Enum<?>>>) nvb;
						
						for (int i = 0; i< jsonArray.size(); i++)
						{
							String jobj = jsonArray.get(i).getAsString();
							nel.getValue().add(SharedUtil.enumValue(metaType.getComponentType(), jobj));
						}
					}
					else if (String[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						ArrayValues<NVPair> nvpm = (ArrayValues<NVPair>) nvb;
						
						for (int i = 0; i< jsonArray.size(); i++)
						{
							JsonObject jobj = jsonArray.get(i).getAsJsonObject();
							nvpm.add(toNVPair( jobj));	
						}
					}
					else if (Long[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<ArrayList<Long>> nval = (NVBase<ArrayList<Long>>) nvb;
						
						for (int i = 0; i< jsonArray.size(); i++)
						{
							nval.getValue().add( jsonArray.get(i).getAsLong());
						}	
					}
					else if (byte[].class.equals(metaType))
					{
						String byteArray64 = je.getAsString();
						
						if (byteArray64 != null)
						{
							nve.setValue(nvc, SharedBase64.decode(byteArray64.getBytes()));
						}
					}
					else if (Integer[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<ArrayList<Integer>> nval = (NVBase<ArrayList<Integer>>) nvb;
						
						for (int i = 0; i< jsonArray.size(); i++)
						{
							nval.getValue().add((int) jsonArray.get(i).getAsLong());
						}	
					}
					else if (Float[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<ArrayList<Float>> nval = (NVBase<ArrayList<Float>>) nvb;
						
						for (int i = 0; i< jsonArray.size(); i++)
						{
							nval.getValue().add((float) jsonArray.get(i).getAsDouble());
						}	
					}
					else if (Double[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<ArrayList<Double>> nval = (NVBase<ArrayList<Double>>) nvb;
						
						for (int i = 0; i < jsonArray.size(); i++)
						{
							nval.getValue().add(jsonArray.get(i).getAsDouble());
						}	
					}
					else if (Date[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<ArrayList<Long>> nval = (NVBase<ArrayList<Long>>) nvb;
						
						for (int i = 0; i< jsonArray.size(); i++)
						{
							JsonPrimitive jp = (JsonPrimitive) jsonArray.get(i);
							long tempDate = 0;
							
							if (jp.isString() && nvc.getValueFilter() != null)
							{
								tempDate = (Long) nvc.getValueFilter().validate(jp.getAsString());
							}
							else
                            {
								tempDate = jp.getAsLong();
							}
							
							nval.getValue().add(tempDate);
						}	
					}
					else if (BigDecimal[].class.equals(metaType))
					{
						JsonArray jsonArray = je.getAsJsonArray();
						NVBase<ArrayList<BigDecimal>> nval = (NVBase<ArrayList<BigDecimal>>) nvb;
						
						for (int i = 0; i < jsonArray.size(); i++)
						{
							nval.getValue().add(jsonArray.get(i).getAsBigDecimal());
						}	
					}
					
				}
				else
                {
				    // not array
					if (nvc instanceof NVConfigEntity)
					{
						if (!(je instanceof JsonNull))
						{
							((NVBase<NVEntity>) nvb).setValue(fromJSON(je.getAsJsonObject(), (Class<? extends NVEntity>) nvc.getMetaType()));
						}
					}
					else if (nvc.isEnum())
					{
						if (!(je instanceof JsonNull))
						{
//							if (metaType.isAssignableFrom( DynamicEnumMap.class))
//							{
//								
//								((NVDynamicEnum)nvb).setValue(je.getAsString());
//							}
//							else
							{
								((NVBase<Enum<?>>)nvb).setValue(SharedUtil.enumValue(metaType, je.getAsString()));
							}
						}
					}
					else if (String.class.equals(metaType))
					{
						if (!(je instanceof JsonNull))
						{
							((NVPair) nvb).setValue(je.getAsString());
						}
					}
					else if (Long.class.equals(metaType))
					{
						((NVBase<Long>) nvb).setValue(je.getAsLong());
					}
					else if (Boolean.class.equals(metaType))
					{
						 ((NVBase<Boolean>) nvb).setValue(je.getAsBoolean());
					}
					else if ( Integer.class.equals(metaType))
					{
						 ((NVBase<Integer>) nvb).setValue((int)je.getAsLong());
					}
					else if (Float.class.equals(metaType))
					{
						 ((NVBase<Float>) nvb).setValue((float)je.getAsDouble());
					}
					else if (Double.class.equals(metaType))
					{
						 ((NVBase<Double>) nvb).setValue(je.getAsDouble());
					}
					else if (Date.class.equals(metaType))
					{
						JsonPrimitive jp = (JsonPrimitive) je;

						if (jp.isString() && nvc.getValueFilter() != null)
						{
							((NVBase<Long>) nvb).setValue((Long) nvc.getValueFilter().validate(jp.getAsString()));
						}
						else
						    {
							((NVBase<Long>) nvb).setValue(jp.getAsLong());
						}
					}
					else if (BigDecimal.class.equals(metaType))
					{
						((NVBase<BigDecimal>) nvb).setValue(je.getAsBigDecimal());
					}
					
				}
			}
				
		}
		
		return nve;
	}
	
	private static NVPair toNVPair(JsonObject jo)
    {
		NVPair nvp = new NVPair();
	
		if (jo.get(MetaToken.NAME.getName())!= null && !jo.get(MetaToken.NAME.getName()).isJsonNull())
		{
			nvp.setName(jo.get(MetaToken.NAME.getName()).getAsString());
		}
		
		if (jo.get(MetaToken.VALUE.getName()) != null && !jo.get(MetaToken.VALUE.getName()).isJsonNull())
		{
			nvp.setValue(jo.get(MetaToken.VALUE.getName()).getAsString());
		}
		
		if (jo.get(MetaToken.REFERENCE_ID.getName()) != null)
		{
			nvp.setReferenceID(jo.get(MetaToken.REFERENCE_ID.getName()).getAsString());
		}
		
		if (jo.get(MetaToken.VALUE_FILTER.getName()) != null)
		{
			ValueFilter<String, String> vf = (FilterType) SharedUtil.enumValue(FilterType.class, jo.get(MetaToken.VALUE_FILTER.getName()).getAsString());
			
			if (vf == null)
			{
				vf = DynamicEnumMapManager.SINGLETON.lookup(jo.get(MetaToken.VALUE_FILTER.getName()).getAsString());
			}
			
			if (vf != null)
			{
				nvp.setValueFilter(vf);
			}
		}
		
		if (nvp.getName() == null && nvp.getValue() == null)
		{
			// we might have "name" : "value"
			Iterator<Entry<String, JsonElement>> it = jo.entrySet().iterator();
			while (it.hasNext())
            {
				Entry<String, JsonElement> nv = it.next();
				String name = nv.getKey();

				if (!MetaToken.REFERENCE_ID.getName().equals(name) && !MetaToken.VALUE_FILTER.getName().equals(name))
				{
					nvp.setName(name);

					if (!nv.getValue().isJsonNull())
					{
						nvp.setValue(nv.getValue().getAsString());
					}
					
					break;
				}
			}
		}
		
		return nvp;
	}
	
	public static String toJSONs(List<? extends NVEntity> list, boolean indent, boolean printNull)
        throws IOException
    {
		StringBuilder sb = new StringBuilder();
		
		for (NVEntity nve : list)
		{
			if (sb.length() > 0)
			{
				sb.append('\n');
			}
			
			sb.append(toJSON(nve, indent, printNull, true));
		}
		
		return sb.toString();
	}
	
	public static String toJSONValues(NVEntity[] list, boolean indent, boolean printNull) 
        throws IOException
    {
		StringWriter sw = new StringWriter();
		JsonWriter writer = new JsonWriter( sw);
		writer.setSerializeNulls(true);
		writer.setHtmlSafe(true);

		if (indent)
		{
			writer.setIndent("  ");
		}

		writer.beginObject();
		writer.name(MetaToken.VALUES.getName());
		writer.beginArray();
		
		for (NVEntity nve : list)
		{
			if (nve != null)
			toJSON(writer, nve.getClass(), nve, printNull, true);
		}
		
		writer.endArray();
		writer.endObject();
		writer.close();
		
		return sw.toString();
	}
	
	public static List<NVEntity> fromJSONValues(String json) 
        throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
		JsonElement je = new JsonParser().parse(json);
		
		if (je instanceof JsonObject)
		{
			List<NVEntity> ret = new ArrayList<NVEntity>();
		
			JsonArray ja = 	(JsonArray) ((JsonObject) je).get(MetaToken.VALUES.getName());
			
			for (int i = 0; i < ja.size(); i++)
			{
				ret.add(fromJSON((JsonObject)ja.get(i), null));
			}
			
			return ret;
		}
		
		return null;
	}

	@SafeVarargs
	@SuppressWarnings("unchecked")
	public static <V extends NVEntity> List<V> fromJSONs(String json, Class<? extends NVEntity>... classes)
    {
		List<V> ret = new ArrayList<V>();
		
		List<CharSequence> tokens = SharedStringUtil.parseGroup(json, "{", "}", true);
		
		for (CharSequence token : tokens)
		{
			for (Class<? extends NVEntity> c : classes)
			{
				try
                {
					NVEntity nve = fromJSON((String) token, c);
					ret.add((V) nve);
				}
				catch (Exception e)
                {
					
				}
			}
		}
		
		return ret;
	}
	
	public static DynamicEnumMap fromJSONDynamicEnumMap(String json)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
		DynamicEnumMap ret = new DynamicEnumMap();
	
		JsonElement je = new JsonParser().parse(json);

		if (je instanceof JsonObject)
		{
			JsonObject jo = (JsonObject) je;
			
			if (jo.get(MetaToken.REFERENCE_ID.getName()) != null
                    && !jo.get(MetaToken.REFERENCE_ID.getName()).isJsonNull())
			{
				ret.setReferenceID(jo.get(MetaToken.REFERENCE_ID.getName()).getAsString());
			}
			
			if (jo.get(MetaToken.USER_ID.getName()) != null
                    && !jo.get(MetaToken.USER_ID.getName()).isJsonNull())
			{
				ret.setUserID(jo.get(MetaToken.USER_ID.getName()).getAsString());
			}
			
			if (jo.get(MetaToken.ACCOUNT_ID.getName()) != null
                    && !jo.get(MetaToken.ACCOUNT_ID.getName()).isJsonNull())
			{
				ret.setAccountID(jo.get(MetaToken.ACCOUNT_ID.getName()).getAsString());
			}
			
			if (jo.get(MetaToken.NAME.getName()) != null
                    && !jo.get(MetaToken.NAME.getName()).isJsonNull())
			{
				ret.setName(jo.get(MetaToken.NAME.getName()).getAsString());
			}
			
			if (jo.get(MetaToken.DESCRIPTION.getName()) != null
                    && !jo.get(MetaToken.DESCRIPTION.getName()).isJsonNull())
			{
				ret.setDescription(jo.get(MetaToken.DESCRIPTION.getName()).getAsString());
			}
			
			if (jo.get(MetaToken.IS_FIXED.getName()) != null
                    && !jo.get(MetaToken.IS_FIXED.getName()).isJsonNull())
			{
				ret.setFixed(jo.get(MetaToken.IS_FIXED.getName()).getAsBoolean());
			}

			if (jo.get(MetaToken.STATIC.getName()) != null
                    && !jo.get(MetaToken.STATIC.getName()).isJsonNull())
			{
				ret.setStatic(jo.get(MetaToken.STATIC.getName()).getAsBoolean());
			}
			
			if (jo.get(MetaToken.IGNORE_CASE.getName()) != null
                    && !jo.get(MetaToken.IGNORE_CASE.getName()).isJsonNull())
			{
				ret.setStatic(jo.get(MetaToken.IGNORE_CASE.getName()).getAsBoolean());
			}
			
			if (jo.get(MetaToken.VALUE.getName()) != null && !jo.get(MetaToken.VALUE.getName()).isJsonNull())
			{
				List<NVPair> list = new ArrayList<NVPair>();
				JsonArray jsonArray = jo.getAsJsonArray(MetaToken.VALUE.getName());
				
				for (int i = 0; i< jsonArray.size(); i++)
				{
					list.add(toNVPair(jsonArray.get(i).getAsJsonObject()));
				}
				
				ret.setValue(list);
			}
		}
		
		return ret;
	}
	
	public static String toJSONDynamicEnumMap(DynamicEnumMap dem)
        throws IOException
    {
		StringWriter sw = new StringWriter();
		JsonWriter writer = new JsonWriter(sw);
		writer.setSerializeNulls(true);
		writer.setHtmlSafe(true);
		writer.setIndent("  ");

		toJSONDynamicEnumMap(writer, dem);
		
		writer.close();
		
		return sw.toString();
	}
	
	private static JsonWriter toJSONDynamicEnumMap(JsonWriter writer, DynamicEnumMap dem) 
        throws IOException
    {
		writer.beginObject();
		
		if (dem != null)
		{
			writer.name(MetaToken.REFERENCE_ID.getName()).value(dem.getReferenceID());
			writer.name(MetaToken.USER_ID.getName()).value(dem.getUserID());
			writer.name(MetaToken.ACCOUNT_ID.getName()).value(dem.getAccountID());
			writer.name(MetaToken.NAME.getName()).value(dem.getName());
			writer.name(MetaToken.DESCRIPTION.getName()).value(dem.getDescription());
			writer.name(MetaToken.IS_FIXED.getName()).value(dem.isFixed());
			writer.name(MetaToken.STATIC.getName()).value(dem.isStatic());
			writer.name(MetaToken.IGNORE_CASE.getName()).value(dem.isIgnoreCase());
			
			writer.name(MetaToken.VALUE.getName());
			writer.beginArray();
			
			for (NVPair nvp : dem.getValue())
			{
				toJSON(writer, nvp, true, true);
			}
			
			writer.endArray();
		}
		
		writer.endObject();	
		
		return writer;
	}
	
	public static List<DynamicEnumMap> fromJSONDynamicEnumMapList(String json)
        throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
		JsonElement je = new JsonParser().parse(json);
		
		if (je instanceof JsonObject)
		{
			List<DynamicEnumMap> ret = new ArrayList<DynamicEnumMap>();
		
			JsonArray ja = 	(JsonArray) ((JsonObject) je).get(MetaToken.VALUES.getName());
			
			for (int i = 0; i < ja.size(); i++)
			{
				ret.add(fromJSONDynamicEnumMap(ja.get(i).toString()));
			}
			
			return ret;
		}
		
		return null;
	}
	
	public static String toJSONQuery(QueryRequest qr)
    {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty(MetaToken.CANONICAL_ID.getName(), qr.getCanonicalID());
				
		jsonObject.addProperty("batch_size", qr.getBatchSize());
		
		if (qr.getFieldNames() != null)
		{
			JsonArray ja = new JsonArray();

			for (String fn : qr.getFieldNames())
			{
				if (!SharedStringUtil.isEmpty(fn))
				{
					ja.add(fn);
				}
			}
			
			jsonObject.add("field_names", ja);
		}
		
		if (qr.getQuery() != null)
		{
			JsonArray ja = new JsonArray();
			
			for (QueryMarker qm : qr.getQuery())
			{
				if (qm != null)
				{
					JsonObject qmJSON = new JsonObject();

					if (qm instanceof GetNameValue)
					{
						if (qm instanceof QueryMatch)
						{
							QueryMatch<?> qMatch = (QueryMatch<?>) qm;
							Object value = qMatch.getValue();
							
							if (value instanceof Number)
							{
								qmJSON.addProperty(qMatch.getName(),(Number)value);
							}
							else if (value instanceof String)
							{
								qmJSON.addProperty(qMatch.getName(), (String) value);
							}
							else if (value instanceof Enum)
							{
								qmJSON.addProperty(qMatch.getName(), ((Enum<?>) value).name());
							}
							
							if (qMatch.getOperator() != null)
							{
                                qmJSON.addProperty(MetaToken.RELATIONAL_OPERATOR.getName(), qMatch.getOperator().name());
                            }
						}
					}
					else if (qm instanceof LogicalOperator)
					{
						qmJSON.addProperty(MetaToken.LOGICAL_OPERATOR.getName(), ((LogicalOperator)qm).name());
					}
					
					ja.add(qmJSON);
				}
			}
			
			jsonObject.add("query", ja);
		}

		return jsonObject.toString();
	}

	public static String toJSONDynamicEnumMapList(List<DynamicEnumMap> list)
        throws IOException
    {
		StringWriter sw = new StringWriter();
		JsonWriter writer = new JsonWriter( sw);
		writer.setSerializeNulls(true);
		writer.setHtmlSafe(true);
		writer.setIndent("  ");
		
		writer.beginObject();
		writer.name(MetaToken.VALUES.getName());
		writer.beginArray();
		
		for (DynamicEnumMap dem : list)
		{
			if (dem != null)
			{
				toJSONDynamicEnumMap(writer, dem);
			}
		}
		
		writer.endArray();
		writer.endObject();
		writer.close();
		
		return sw.toString();
	}
	
}