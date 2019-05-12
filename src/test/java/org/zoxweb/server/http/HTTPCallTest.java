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
package org.zoxweb.server.http;


import org.zoxweb.server.util.GSONUtil;
import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPMimeType;
import org.zoxweb.shared.http.HTTPResponseData;


public class HTTPCallTest {

  public static void main(String[] args) {

//	  try 
//      {
//        HTTPMessageConfigInterface hmci = HTTPMessageConfig.createAndInit("http://localhost:8080", "/dog/imageby/10", HTTPMethod.GET);
//        HTTPResponseData hrd = (new HTTPCall(hmci).sendRequest());
//        System.out.println(hrd.getResponseHeaders());
//        
//      }
//      catch(Exception e)
//      {
//          e.printStackTrace();
//      }

    HTTPMessageConfigInterface hcc = new HTTPMessageConfig();
    //hcc.setURL("http://10.0.1.15");
    //hcc.setURI("amds/amds/amdsconfigservice");
    //ArrayList<GetNameValue<String>> headers = new ArrayList<GetNameValue<String>>();
    //headers.add( new NVPair("X-GWT-Module-Base", "https://amdspreview.arcot.com/amds/amds"));
    //headers.add( new NVPair("X-GWT-Permutation", "6856EC863BBBD61AF0CEB7D9DD174228"));
    //headers.add( new NVPair("Content-Type", "text/x-gwt-rpc; charset=utf-8"));
    //hcc.setHeaderParameters(headers);
    //hcc.setContent("Jello".getBytes());
    //hcc.setMethod(HTTPMethod.GET);

//		ArrayList<GetNameValue<String>> parameters = new ArrayList<GetNameValue<String>>();
//		parameters.add( new NVPair("com.ca.amds.client.rpc.config.AMDSConfigService", "reloadCache"));
//		hcc.setParameters(parameters);

    int index = 0;
    hcc.setURL(args[index++]);
//		hcc.setURI(args[index++]);
//		hcc.setURI("*");

    hcc.setMethod(args[index++]);
    //hcc.setHTTPVersion(HTTPVersion.HTTP_1_0);
    hcc.setRedirectEnabled(true);
    //hcc.setProxyAddress( new InetSocketAddressDAO("localhost", 8080));

    //hcc.getHeaderParameters().add(new NVPair("Authorization", "Basic YWRtaW46dzFyMmwzc3M="));
    //hcc.getHeaderParameters().add(new NVPair("Cookie", "ui_language=en_US"));
    //hcc.getParameters().add(new NVPair("Reboot", "Reboot"));

    HTTPCall call = new HTTPCall(hcc);
    long ts = System.currentTimeMillis();
    long delta = 0;

    try {
      HTTPResponseData rd = call.sendRequest();
      delta = System.currentTimeMillis() - ts;

      System.out.println(rd);
    } catch (Exception e) {
      delta = System.currentTimeMillis() - ts;
      e.printStackTrace();
    }

    System.out.println("send request took:" + (delta));
    //hcc.setContent("Jello".getBytes());

    try {
      String json = GSONUtil.toJSON((HTTPMessageConfig) hcc, true, true, true);
      System.out.println(json);

      HTTPMessageConfig hccFromJson = GSONUtil.fromJSON(json);
      System.out.println(hccFromJson);
      String json2 = GSONUtil.toJSON(hccFromJson, true, true, true);
      System.out.println("json equals:" + json.equals(json2));
    } catch (Exception e) {
      e.printStackTrace();
    }

    /// multi part test

    HTTPMessageConfig multiHCC = new HTTPMessageConfig();
    System.out.println("Empty:" + multiHCC.isMultiPartEncoding());

    multiHCC.setContentType(HTTPMimeType.TEXT_PLAIN.getValue());
    System.out.println("Text:" + multiHCC.isMultiPartEncoding());
    System.out.println(multiHCC);

    multiHCC.setContentType(HTTPMimeType.MULTIPART_FORM_DATA);
    System.out.println("MULTIPART:" + multiHCC.isMultiPartEncoding());

    System.out.println(HTTPMimeType.lookup("Application/JSon ; charset=utf-8"));
    System.out.println(multiHCC);


  }

}
