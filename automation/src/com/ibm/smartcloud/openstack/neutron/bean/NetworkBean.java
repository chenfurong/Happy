package com.ibm.smartcloud.openstack.neutron.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Title:NetworkBean     
 * @Description: 网络信息Bean
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 上午11:24:38       
 * @version V1.0
 */
public class NetworkBean implements Serializable{
	private static final long serialVersionUID = 2636611035438898651L;
	
	private String id = "";
	private String name = "";
	private String status = "";
	private List<String> subnets = null;
	private boolean admin_state_up = false;
	private boolean shared = false;
	private boolean router_external = false;
	private String tenant_id = "";
	private String provider_segmentation_id = "";
	private String provider_network_type = "";
	private String provider_physical_network = "";
	private Integer mtu = 0;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the subnets
	 */
	public List<String> getSubnets() {
		return subnets;
	}
	/**
	 * @param subnets the subnets to set
	 */
	public void setSubnets(List<String> subnets) {
		this.subnets = subnets;
	}
	/**
	 * @return the admin_state_up
	 */
	public boolean isAdmin_state_up() {
		return admin_state_up;
	}
	/**
	 * @param admin_state_up the admin_state_up to set
	 */
	public void setAdmin_state_up(boolean admin_state_up) {
		this.admin_state_up = admin_state_up;
	}
	/**
	 * @return the shared
	 */
	public boolean isShared() {
		return shared;
	}
	/**
	 * @param shared the shared to set
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	/**
	 * @return the router_external
	 */
	public boolean isRouter_external() {
		return router_external;
	}
	/**
	 * @param router_external the router_external to set
	 */
	public void setRouter_external(boolean router_external) {
		this.router_external = router_external;
	}
	/**
	 * @return the tenant_id
	 */
	public String getTenant_id() {
		return tenant_id;
	}
	/**
	 * @param tenant_id the tenant_id to set
	 */
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	/**
	 * @return the provider_segmentation_id
	 */
	public String getProvider_segmentation_id() {
		return provider_segmentation_id;
	}
	/**
	 * @param provider_segmentation_id the provider_segmentation_id to set
	 */
	public void setProvider_segmentation_id(String provider_segmentation_id) {
		this.provider_segmentation_id = provider_segmentation_id;
	}
	/**
	 * @return the provider_network_type
	 */
	public String getProvider_network_type() {
		return provider_network_type;
	}
	/**
	 * @param provider_network_type the provider_network_type to set
	 */
	public void setProvider_network_type(String provider_network_type) {
		this.provider_network_type = provider_network_type;
	}
	/**
	 * @return the provider_physical_network
	 */
	public String getProvider_physical_network() {
		return provider_physical_network;
	}
	/**
	 * @param provider_physical_network the provider_physical_network to set
	 */
	public void setProvider_physical_network(String provider_physical_network) {
		this.provider_physical_network = provider_physical_network;
	}
	/**
	 * @return the mtu
	 */
	public Integer getMtu() {
		return mtu;
	}
	/**
	 * @param mtu the mtu to set
	 */
	public void setMtu(Integer mtu) {
		this.mtu = mtu;
	}
	
}