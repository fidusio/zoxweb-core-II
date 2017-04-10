package org.zoxweb;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.zoxweb.server.security.SSLCheckDisabler;

public class SSLSocketPropTest {

	public static void main(String[] args) {
		
		try {
			URL url = new URL("https://76.91.24.155:8443/index.jsp");
			URLConnection con = url.openConnection();
			SSLCheckDisabler.SINGLETON.updateURLConnection((HttpURLConnection) con);
			Reader reader = new InputStreamReader(con.getInputStream());
			while (true) {
				int ch = reader.read();
				if (ch == -1) {
					break;
				}
				System.out.print((char)ch);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
