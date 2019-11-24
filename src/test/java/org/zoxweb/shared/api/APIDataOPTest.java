package org.zoxweb.shared.api;

import org.junit.Test;
import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.data.AddressDAO;
import org.zoxweb.shared.data.DataConst;

import java.io.IOException;

public class APIDataOPTest {

    @Test
    public void testAPIDataOP() throws IOException {
        APIDataOP ado = new APIDataOP();
        ado.setDataOP(DataConst.DataOP.PATCH);
        ado.setNVEntity(new AddressDAO());
        ado.add("street");
        String json = GSONUtil.toJSON(ado, true);
        ado = GSONUtil.fromJSON(json);
        System.out.println(GSONUtil.toJSON(ado, true));
    }
}
