package org.zoxweb.server.http;

import java.util.Date;
import java.util.HashMap;

import org.zoxweb.server.http.HTTPCall;
import org.zoxweb.server.http.HTTPUtil;
import org.zoxweb.server.io.UByteArrayOutputStream;
import org.zoxweb.shared.http.HTTPHeaderName;
import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPMethod;
import org.zoxweb.shared.http.HTTPMessageConfig.Params;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;
import org.zoxweb.shared.util.NVBlob;
import org.zoxweb.shared.util.NVGetNameValueGenericMap;
import org.zoxweb.shared.util.NVPair;
import org.zoxweb.shared.util.SharedStringUtil;

public class HTTPUTF16Message {

	public static void main(String[] args) {
		
		try {
			String url = "https://secure-mrr.air2web.com";
			String uri = "a2w_preRouter/xmlApiRouter";
			HTTPMethod method = HTTPMethod.POST;
			int index = 0;
			String user = args[index++];
			String password = args[index++];
			
			HTTPMessageConfigInterface hmci = HTTPMessageConfig.createAndInit(url, uri, method);
			hmci.getHeaderParameters().add(new NVPair(HTTPHeaderName.CONTENT_TYPE, "text/xml"));

			NVGetNameValueGenericMap params = new NVGetNameValueGenericMap(Params.HEADER_PARAMETERS.getNVConfig().getName(), new HashMap<GetName, GetNameValue<?>>());

//			hmci.getParameters().add(new NVPair("reply_to","26161"));
//			hmci.getParameters().add(new NVPair("customer_id","1"));
//			hmci.getParameters().add(new NVPair("recipient","13109899969"));
			
			String reply_to = "6161";
			String customer_id = "1";
			params.add(new NVPair("reply_to", reply_to));
			params.add(new NVPair("customer_id",customer_id));
			params.add(new NVPair("recipient","13109899969"));
			
			hmci.setURLEncodingEnabled(true);
			
			//System.out.println(Charset.availableCharsets());
			
			String charset = null;
			//String body = new String(SharedStringUtil.hexToBytes("0X3DD802DE"), charset);
			String body = "hello:" + new Date();
			System.out.println(body);
			NVPair nvpBody = new NVPair("body", body);
			nvpBody.setCharset(charset);
			
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<router-api  customer_id=\""+customer_id+"\" version=\"2.0\">" +
					"<request>"+
			"<send_message return_ids=\"false\">" +
					"<channel type=\"sms\"/>"+
					"<carrier_message carrier=\"default\">"+
					"<message_content encoding=\"none\">" +
					"<body>"+ "hello" +"</body>" +
					"</message_content>" +
					"<subject />" + 
					"<reply_to>" + reply_to + "</reply_to>" +
						"<modifications>" +
							"<shorten type=\"split\" />" +
							"<globalization us_only=\"true\" />" +
							"<status_receipt>batata</status_receipt>"+
					
					
						"</modifications>" +
					"</carrier_message>"+
					"<recipient_list>"+
					"<recipient>"+ "13109899969" +"</recipient>" +
					"</recipient_list>" +
					"</send_message>"+
					"</request>"+
					"</router-api>"
					
					;
			
			NVBlob nvb = new NVBlob("body", SharedStringUtil.hexToBytes("0xD83DDE1E"));
			//NVBlob nvb = new NVBlob("body", body.getBytes());
			//hmci.getParameters().add(nvpBody);
			params.add(nvb);
			UByteArrayOutputStream baos = HTTPUtil.formatBinaryParameters(params, charset, true);
			hmci.setUser(user);
			hmci.setPassword(password);
			hmci.setContent(xml.getBytes());
			System.out.println(new String (baos.toByteArray()));
			System.out.println(xml);
			
			//hmci.setCharset("UTF-8");
			HTTPCall hc = new HTTPCall(hmci);
			System.out.println(hc.sendRequest());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}