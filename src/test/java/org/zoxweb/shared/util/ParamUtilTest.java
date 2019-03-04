package org.zoxweb.shared.util;

import org.junit.Test;



public class ParamUtilTest {

    @Test
    public void parameterParsing()
    {
        ParamUtil.ParamMap result = ParamUtil.parse("-", "https://localhost", "-URI", "context", "-content", "hello", "5", "-uri", "/hello");
        System.out.println(result);
        result = ParamUtil.parse("-", false, "https://localhost", "-URI", "context", "-content", "hello", "5");
        System.out.println(result);
    }


    @Test
    public void defaultValues()
    {
        ParamUtil.ParamMap result = ParamUtil.parse("-","https://localhost", "-URI", "context", "-content", "hello", "5", "-uri", "/hello");

        System.out.println(result.intValue("-c", 5));
        System.out.println(result.longValue("-c", (long)5));
        System.out.println(result.stringValue("-c", "hrooo"));
        System.out.println(result.stringValue(0));


    }
    @Test(expected = IllegalArgumentException.class)
    public void errorTest()
    {
        ParamUtil.ParamMap result = ParamUtil.parse("-","https://localhost", "-URI", "context", "-content", "hello", "5", "-uri", "/hello");

        System.out.println(result.intValue("-c"));
    }
}
