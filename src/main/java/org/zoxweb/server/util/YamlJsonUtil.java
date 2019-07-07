package org.zoxweb.server.util;
import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class YamlJsonUtil {


  private YamlJsonUtil(){};

  /**
   * Convert a yaml string to a json string
   * @param yamlString
   * @return
   */
  public static String yamlToJson(String yamlString)
  {
    Yaml yaml = new Yaml();
    Map<String, Object> obj = yaml.load(yamlString);
    Gson gson = new Gson();
    return gson.toJson(obj);
  }

  /**
   * convert a json string to yaml string
   * @param jsonString
   * @return
   */
  public static String jsonToYaml(String jsonString)
  {
    @SuppressWarnings("unchecked")
    Map<String, Object> map = new Gson().fromJson(jsonString, LinkedHashMap.class);
    return new Yaml().dump(map);
  }


}