package com.nhs.trust.common;

public class TewvServiceException extends Exception{

	private static final long serialVersionUID = 1L;
	public TewvServiceException() { super(); }
	public TewvServiceException(String message) { super(message); }
	public TewvServiceException(String message, Throwable cause) { super(message, cause); }
	public TewvServiceException(Throwable cause) { super(cause); }

}
