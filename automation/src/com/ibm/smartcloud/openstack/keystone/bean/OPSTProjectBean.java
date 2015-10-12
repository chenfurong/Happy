package com.ibm.smartcloud.openstack.keystone.bean;

import com.ibm.smartcloud.openstack.core.bean.OPSTLinksBean;

public class OPSTProjectBean {

	private String domain_id = "";
	private String name = "";
	private boolean enabled = true;
	private String page = "";
	private String per_page = "";
	private OPSTLinksBean linkBean = null;
	private String id = "";
	//新追加字段
	private String description;
	
	/**
	 * @return the domain_id
	 */
	public String getDomain_id() {
		return domain_id;
	}
	/**
	 * @param domain_id the domain_id to set
	 */
	public void setDomain_id(String domain_id) {
		this.domain_id = domain_id;
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
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * @return the per_page
	 */
	public String getPer_page() {
		return per_page;
	}
	/**
	 * @param per_page the per_page to set
	 */
	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}
	/**
	 * @return the linkBean
	 */
	public OPSTLinksBean getLinkBean() {
		return linkBean;
	}
	/**
	 * @param linkBean the linkBean to set
	 */
	public void setLinkBean(OPSTLinksBean linkBean) {
		this.linkBean = linkBean;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
