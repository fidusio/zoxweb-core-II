package org.zoxweb.shared.util;





import org.zoxweb.shared.data.ParamInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParamUtil {
    private ParamUtil(){}

    public static class ParamInfoList
    {
        Map<String, ParamInfo> list = new LinkedHashMap<String, ParamInfo>();

        public void add(ParamInfo pi)
        {
            list.put(pi.getName().toLowerCase(), pi);
        }
        public ParamInfo lookup(String paramName)
        {
            return list.get(paramName.toLowerCase());
        }


    }

    public static class ParamMap
    {
        private Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        private boolean ignoreCase = false;
        private int counter;
        private ParamMap(boolean ignoreCase, Map<String, List<String>> map, int  length)
        {
            this.map = map;
            this.ignoreCase = ignoreCase;
            this.counter = length;
        }

        public int intValue(int index)
        {
            return intValue(""+index);
        }

        public int intValue(String name)
        {
            return intValue(name, null);
        }

        public int intValue(String name, Integer defaultValue)
        {
           List<String> ret = lookup(name);
           if(ret == null)
           {
               if(defaultValue == null)
                throw new IllegalArgumentException("Parameter " + name + " not found");
               return defaultValue;
           }

           if (ret.size() == 1)
           {
               return Integer.parseInt(ret.get(0));
           }

           throw new IllegalArgumentException(name + " value not found or no valid");
        }

        /**
         * @return the number of parameters without name
         */
        public int namelessCount()
        {
            return counter;
        }

        public long longValue(int index)
        {
            return intValue(""+index);
        }

        public long longValue(String name)
        {
            return longValue(name, null);
        }
        public long longValue(String name, Long defaultValue)
        {
            List<String> ret = lookup(name);
            if(ret == null)
            {
                if(defaultValue == null)
                    throw new IllegalArgumentException("Parameter " + name + " not found");
                return defaultValue;
            }
            if (ret.size() == 1)
            {
                return Long.parseLong(ret.get(0));
            }

            throw new IllegalArgumentException(name + " value not found or no valid");
        }


        public double doubleValue(int index)
        {
            return intValue(""+index);
        }

        public double doubleValue(String name)
        {
            return doubleValue(name, null);
        }
        public double doubleValue(String name, Double defaultValue)
        {
            List<String> ret = lookup(name);
            if(ret == null)
            {
                if(defaultValue == null)
                    throw new IllegalArgumentException("Parameter " + name + " not found");
                return defaultValue;
            }

            if (ret.size() == 1)
            {
                return Double.parseDouble(ret.get(0));
            }

            throw new IllegalArgumentException(name + " value not found or no valid");
        }



        public boolean booleanValue(String name)
        {
            return booleanValue(name, true);
        }

        public boolean booleanValue(String name, boolean notFoundOk)
        {
            List<String> ret = lookup(name);
            if(ret == null)
            {
                if(!notFoundOk)
                    throw new IllegalArgumentException("Parameter " + name + " not found");
                return false;
            }

            if (ret.size() == 1)
            {
                return Const.Bool.lookupValue(ret.get(0));

            }

            throw new IllegalArgumentException(name + " value not found or no valid");
        }

        public String stringValue(int index)
        {
            return stringValue(""+index);
        }
        public String stringValue(String name)
        {
            return stringValue(name, null, false);
        }

        public String stringValue(String name, boolean nullOk)
        {
            return stringValue(name, null, nullOk);
        }

        public String stringValue(String name, String defaultValue)
        {
            return stringValue(name, defaultValue, false);
        }

        private String stringValue(String name, String defaultValue, boolean nullOk)
        {
            List<String> ret = lookup(name);
            if(ret == null)
            {
                if(defaultValue == null && !nullOk)
                    throw new IllegalArgumentException("Parameter " + name + " not found");
                return defaultValue;
            }

            if (ret.size() == 1)
            {
                return ret.get(0);
            }

            throw new IllegalArgumentException(name + " value not found or no valid");
        }


        public List<String> lookup(int index)
        {
            return lookup(""+index);
        }

        public List<String> lookup(String name)
        {
            if(ignoreCase)
            {
                name = name.toLowerCase();
            }
            return map.get(name);
        }

        @Override
        public String toString() {
            return "ParamMap{" +
                    "ignoreCase=" + ignoreCase +
                    ", map=" + map +

                    '}';
        }
    }


    public static ParamMap parse(String nvTag, String ...args)
    {
        return parse(nvTag, true, args);
    }

    public static ParamMap parse(String nvTag, boolean ignoreCase, String ...args)
    {
        Map<String, List<String>> retMap = new LinkedHashMap<String, List<String>>();

        int index = 0;
        int counter = 0;
        for(; index < args.length; index++)
        {
            if (!SharedStringUtil.isEmpty(args[index]))
            {
                String name = null;
                String value = null;
                if(args[index].startsWith(nvTag))
                {
                    name = args[index++];
                    if (index < args.length)
                    {
                        value = args[index];
                    }
                }
                else
                {
                    value = args[index];
                }

                if(name == null)
                {
                    name = "" + counter++;
                }

                if(ignoreCase)
                {
                    name = name.toLowerCase();
                }


                if(retMap.get(name) == null)
                {
                    retMap.put(name, new ArrayList<String>());
                }

                retMap.get(name).add(value);
            }

        }

        return new ParamMap(ignoreCase, retMap, counter);
    }


    public static ParamMap parse(ParamInfoList piList, String ...args)
    {
        Map<String, List<String>> retMap = new LinkedHashMap<String, List<String>>();

        int index = 0;
        int counter = 0;
        for(; index < args.length; index++)
        {
            if (!SharedStringUtil.isEmpty(args[index]))
            {
                String name = null;
                String value = null;


                ParamInfo pi = piList.lookup(args[index]);
                if(pi==null)
                {
                    name = "" + counter++;
                    value = args[index];
                }
                else
                {
                    name = pi.getName();
                    if (pi.getValueType() == ParamInfo.ValueType.NONE)
                    {
                        value = name;
                    }

                    else if (index < args.length)
                    {
                        value = args[index];
                    }
                    else
                    {
                        // error
                    }

                }
                
                if(retMap.get(name) == null)
                {
                    retMap.put(name, new ArrayList<String>());
                }

                retMap.get(name).add(value);
            }

        }

        return new ParamMap(true, retMap, counter);
    }


}
