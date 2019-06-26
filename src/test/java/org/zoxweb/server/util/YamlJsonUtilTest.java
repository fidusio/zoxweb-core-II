package org.zoxweb.server.util;

import java.io.IOException;
import org.junit.Test;
import org.zoxweb.server.io.IOUtil;

public class YamlJsonUtilTest {


  public String getYamlData() throws IOException
  {
    return IOUtil.inputStreamToString(getClass().getClassLoader().getResourceAsStream("TestData.yaml"), true);
  }

  @Test
  public void convertToJson() throws IOException {
    System.out.println(YamlJsonUtil.yamlToJson(getYamlData()));
  }

  @Test
  public void convertToYaml() throws IOException {
    String yamlString = getYamlData();
    String jsonData = YamlJsonUtil.yamlToJson(yamlString);
    System.out.println(YamlJsonUtil.jsonToYaml(jsonData));

  }

}
