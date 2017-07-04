package org.zoxweb.shared.data;

import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;

import java.io.IOException;

/**
 * Created on 7/3/17
 */
public class AppIDDAOTest {

    @Test
    public void test() throws IOException {
        AppIDDAO appIDDAO = new AppIDDAO("propanexp", "propanexp.com");

        System.out.println(GSONUtil.toJSON(appIDDAO, true, false, false));

    }

}