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
	
	int getStatusCode();
	void setStatusCode(int code);
	
	String getMessage();
	
}
