package com.ibm.smartcloud.core.exception;

public class RequestErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8448694336080855041L;

	public RequestErrorException() {
	}

	public RequestErrorException(String arg0) {
		super(arg0);
	}

	public RequestErrorException(Throwable arg0) {
		super(arg0);
	}

	public RequestErrorException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
