package org.zoxweb.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.zoxweb.server.http.HTTPCall;
import org.zoxweb.server.http.HTTPUtil;
import org.zoxweb.server.io.IOUtil;
import org.zoxweb.server.io.UByteArrayOutputStream;
import org.zoxweb.server.security.SSLCheckDisabler;
import org.zoxweb.shared.http.HTTPMessageConfig;
import org.zoxweb.shared.http.HTTPMessageConfigInterface;
import org.zoxweb.shared.http.HTTPMethod;

import org.zoxweb.shared.http.HTTPVersion;
import org.zoxweb.shared.net.InetSocketAddressDAO;
import org.zoxweb.shared.util.Const.TimeInMillis;

import org.zoxweb.shared.util.SharedUtil;

public class HTTP2ProxyTest {
	
	public static void sendRequest(HTTPMessageConfigInterface hci) throws IOException {
		if (hci.getURL() == null) {
			hci.setURL(hci.getURI());
			hci.setURI(null);
		}
		
		HTTPCall hc = new HTTPCall(hci, SSLCheckDisabler.SINGLETON);
		System.out.println(hc.sendRequest());
	}

	public static void main(String[] args) {

		try {
			int index = 0;
			long delta1 = 0;

			//long delta2 = 0;
			InetSocketAddressDAO proxyAddress = new InetSocketAddressDAO (args[index++]);
			HTTPMethod hMethod = SharedUtil.lookupTypedEnum(HTTPMethod.values(), args[index++]);
			
			HTTPMessageConfigInterface hmci = HTTPMessageConfig.createAndInit(null, args[index++], hMethod);
			hmci.setHTTPVersion(HTTPVersion.HTTP_1_1);
			//hmci.setReadTimeout((int) (TimeInMillis.SECOND.MILLIS*5));
			hmci.setProxyAddress(proxyAddress);
			
			sendRequest(hmci);
			System.exit(0);
			//hmci.getHeaderParameters().add(new NVPair("Accept", "text/html"));
			
			UByteArrayOutputStream ubaos = new UByteArrayOutputStream();
			InetSocketAddressDAO remoteAddress = HTTPUtil.parseHost(hmci.getURI());
			HTTPUtil.formatRequest(hmci, false, ubaos);
			System.out.println(remoteAddress);
			
			System.out.println(new String(ubaos.toByteArray()));
			
			delta1 = System.nanoTime();
			Socket s = new Socket(proxyAddress.getInetAddress(), proxyAddress.getPort());
			OutputStream  os = s.getOutputStream();
			InputStream is = s.getInputStream();
			os.write(ubaos.getInternalBuffer(), 0, ubaos.size());
			os.flush();
			//os.close();
			byte readBuffer[] = new byte[4096];
			int read = 0;
			ubaos.reset();
			while((read = is.read(readBuffer)) != -1)
			{
				ubaos.write(readBuffer, 0, read);
			}
			is.close();
			
			
			
			//for (int i=0; i < 5; i++)
			{
				delta1 = System.nanoTime() - delta1;
	//			hmci.setProxyAddress(null);
	//			delta2 = System.nanoTime();
	//			HTTPResponseData rdNoProxy = hc.sendRequest();
	//			delta2 = System.nanoTime() - delta2;
	//
	//			System.out.println(SharedUtil.slowEquals(rdProxy.getData(), rdNoProxy.getData()));
	//			System.out.println("Proxy time:" + TimeInMillis.nanosToString(delta1) + " no proxy time:" + TimeInMillis.nanosToString(delta2));
				System.out.println(new String(ubaos.toByteArray()));
				System.out.println("Proxy time:" + TimeInMillis.nanosToString(delta1));

				IOUtil.close(s);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
