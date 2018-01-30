package org.zoxweb.server.http;

import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPMethod;
import org.zoxweb.shared.http.HTTPResponseData;
import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.util.SharedUtil;

public class ProxyConnection {
    public static void main(String ...args)
    {
        try
        {
            int index = 0;
            String proxy = args[index++];
            String url = args[index++];
            InetSocketAddressDAO proxyAddress = new InetSocketAddressDAO(proxy);


            HTTPMessageConfigInterface hcc = HTTPMessageConfig.createAndInit(url, null, HTTPMethod.GET, false);
            hcc.setProxyAddress(proxyAddress);
            HTTPCall hc = new HTTPCall(hcc);
            long ts = System.currentTimeMillis();
            HTTPResponseData rd = hc.sendRequest();
            ts = System.currentTimeMillis() - ts;
            System.out.println(rd);

            System.out.println("Command:" + SharedUtil.toCanonicalID(' ', args) + " it took " + ts + " millis");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
