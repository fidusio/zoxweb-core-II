package org.zoxweb.shared.db;

import org.zoxweb.shared.util.Const.RelationalOperator;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.GetName;
import org.zoxweb.shared.util.GetNameValue;

@SuppressWarnings("serial")
public class QueryMatchString
		extends QueryMatch<String> {

	public QueryMatchString() {
		
	}
	
	public QueryMatchString(String name, String value, RelationalOperator operator) {
		super(name, value, operator);
	}
	
	public QueryMatchString(GetName gc, String value, RelationalOperator operator) {
		super(operator, value, gc);
	}
	
	public QueryMatchString(GetNameValue<String> gnv, RelationalOperator operator) {
		//super(gnv.getName(), gnv.getValue(), operator);
		super(operator, gnv.getValue(), gnv);
	}

	public QueryMatchString(RelationalOperator operator, String value, String... names) {
		super(operator, value, names);
	}
	
	
	public QueryMatchString(RelationalOperator operator, String value, GetName... names) {
		super(operator, value, names);
	}
	
	
	public QueryMatchString(RelationalOperator operator, String value, GetNVConfig... gnvs) {
		super(operator, value, gnvs);
	}
    

}