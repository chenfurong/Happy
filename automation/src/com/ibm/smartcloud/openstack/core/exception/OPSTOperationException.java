package com.ibm.smartcloud.openstack.core.exception;

@SuppressWarnings("serial")
public class OPSTOperationException extends OPSTBaseException {

	public OPSTOperationException() {
	}

	public OPSTOperationException(String arg0) {
		super(arg0);
	}

	public OPSTOperationException(Throwable arg0) {
		super(arg0);
	}

	
	public OPSTOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
