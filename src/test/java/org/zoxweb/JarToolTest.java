package org.zoxweb;

import org.zoxweb.server.util.JarTool;

public class JarToolTest {

	public static void main(String[] args) {
		try {
			String searchCriteria [] = new String[args.length -1];

			for (int i = 1; i < args.length; i++) {
				searchCriteria[i-1] = args[i];
			}

			System.out.println(JarTool.findFilesInJarAndExpandAsTempFiles(args[0], true, searchCriteria));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
