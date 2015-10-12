package com.ibm.smartcloud.openstack.core.exception;

public class OPSTLoginTimeOutException extends OPSTBaseException {

	private static final long serialVersionUID = -5822026411566567622L;

	public OPSTLoginTimeOutException() {
		super();
	}

	public OPSTLoginTimeOutException(String message) {
		super(message);
	}

	public OPSTLoginTimeOutException(Throwable cause) {
		super(cause);
	}

	public OPSTLoginTimeOutException(String message, Throwable cause) {
		super(message, cause);
	}

}
