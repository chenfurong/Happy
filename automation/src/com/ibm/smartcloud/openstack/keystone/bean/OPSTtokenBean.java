package com.ibm.smartcloud.openstack.keystone.bean;

import java.io.Serializable;

/**
 * openstack token.
 * @author xucd
 *
 */
@SuppressWarnings("serial")
public class OPSTtokenBean implements Serializable{

	private String expires = "";
	private String id = "";
	private OPSTtenantBean tenant = null;
	
	/**
	 * @return the expires
	 */
	public String getExpires() {
		return expires;
	}
	/**
	 * @param expires the expires to set
	 */
	public void setExpires(String expires) {
		this.expires = expires;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the tenant
	 */
	public OPSTtenantBean getTenant() {
		return tenant;
	}
	/**
	 * @param tenant the tenant to set
	 */
	public void setTenant(OPSTtenantBean tenant) {
		this.tenant = tenant;
	}
	

}
