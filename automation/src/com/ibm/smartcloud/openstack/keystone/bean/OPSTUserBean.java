package com.ibm.smartcloud.openstack.keystone.bean;

public class OPSTUserBean {
	
	private String id;
	private String name;
	private String email;
	private String enabled;
	private String password;
	private String tenantId;
	
	private String siteIP;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSiteIP() {
		return siteIP;
	}
	public void setSiteIP(String siteIP) {
		this.siteIP = siteIP;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}