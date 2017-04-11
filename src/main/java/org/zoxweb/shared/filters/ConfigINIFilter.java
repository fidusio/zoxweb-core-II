package org.zoxweb.shared.filters;

import java.util.ArrayList;
import java.util.List;

import org.zoxweb.shared.util.SharedStringUtil;

@SuppressWarnings("serial")
public class ConfigINIFilter
        implements ValueFilter<String, List<String>> {

	public static final ConfigINIFilter SINGLETON = new ConfigINIFilter( true);
	
	private boolean trimEnabled;
	
	public final static String COMMENTS[] = {
		"#",
		"//"
	};
	
	/**
	 * The default constructor.
	 */
	public ConfigINIFilter() {
		
	}

	public ConfigINIFilter(boolean trimEnabled) {
		setLineTrimmingEnabled(trimEnabled);
	}

	@Override
	public String toCanonicalID() {
		return getClass().getName();
	}

	@Override
	public List<String> validate(String in) throws NullPointerException,
			IllegalArgumentException {
	
		String lines[] = in.split("\r\n|\n");
		ArrayList<String> goodLines = new ArrayList<String>();

		for (String line : lines) {
			int commentIndex = -1;
			
			for (String c : COMMENTS) {
				int temp = line.indexOf(c);
				if (temp != -1 && (commentIndex == -1 || commentIndex > temp)) {
					commentIndex = temp;
				}	
			}

			if (commentIndex  > 0) {
				line = line.substring(0, commentIndex);
			} else if (commentIndex == 0) {
				line ="";
			}
			
			if (trimEnabled) {
				line = line.trim();
			}
			
			if (!SharedStringUtil.isEmpty(line)) {
				goodLines.add(line);
			}
		}
		
		return goodLines;
	}

	@Override
	public boolean isValid(String in) {
		return !SharedStringUtil.isEmpty( in);
	}

	public boolean isLineTrimmingEnabled() {
		return trimEnabled;
	}

	public void setLineTrimmingEnabled(boolean trimEnabled) {
		this.trimEnabled = trimEnabled;
	}

}
