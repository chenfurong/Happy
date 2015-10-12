package com.ibm.smartcloud.openstack.keystone.bean;

import java.io.Serializable;

public class OPSTQuotaBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5217023286136206083L;
	private int subnet;
	private int network;
	private int floatingip;
	private int security_group_rule;
	private int security_group;
	private int router;
	private int port;
	
	public int getSubnet() {
		return subnet;
	}
	public void setSubnet(int subnet) {
		this.subnet = subnet;
	}
	public int getNetwork() {
		return network;
	}
	public void setNetwork(int network) {
		this.network = network;
	}
	public int getFloatingip() {
		return floatingip;
	}
	public void setFloatingip(int floatingip) {
		this.floatingip = floatingip;
	}
	public int getRouter() {
		return router;
	}
	public void setRouter(int router) {
		this.router = router;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getSecurity_group_rule() {
		return security_group_rule;
	}
	public void setSecurity_group_rule(int securityGroupRule) {
		security_group_rule = securityGroupRule;
	}
	public int getSecurity_group() {
		return security_group;
	}
	public void setSecurity_group(int securityGroup) {
		security_group = securityGroup;
	}
	
}
