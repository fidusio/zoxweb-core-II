package org.zoxweb.shared.util;

public interface ExceptionReason 
{
	
	public enum Reason
	{
		NOT_FOUND,
		UNAUTHORIZED,
		ACCESS_DENIED,
		INCOMPLETE
	}
	
	
	Reason getReason();
	void setReason(Reason reason);
	
	String getMessage();
	
}
