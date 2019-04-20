package org.zoxweb.server.util;

import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DateUtilTest {

    @Test
    public void testGetNormalizedMonth() {
        int month = DateUtil.getNormalizedMonth(System.currentTimeMillis());
        assertTrue(month > 0);
    }

    @Test
    public void testGetYearMonth() {
        int year = DateUtil.getNormalizedYear(System.currentTimeMillis());
        assertTrue(year > 0);
    }

    @Test
    public void today(){
        System.out.println(DateUtil.TODAY_LTZ.format(new Date()));
    }

}
