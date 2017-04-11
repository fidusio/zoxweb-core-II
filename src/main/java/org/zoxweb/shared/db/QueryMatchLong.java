package org.zoxweb.shared.db;

import org.zoxweb.shared.util.Const.RelationalOperator;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetName;

@SuppressWarnings("serial")
public class QueryMatchLong
		extends QueryMatch<Long> {
	
	public QueryMatchLong() {
		
	}
	
	public QueryMatchLong(RelationalOperator operator, long value, String... names) {
		super(operator, value, names);
	}
	
	public QueryMatchLong(RelationalOperator operator, long value, GetName... names) {
		super(operator, value, names);
	}
	
	public QueryMatchLong(RelationalOperator operator, long value, GetNVConfig... gnvs) {
		super(operator, value, gnvs);
	}
}
