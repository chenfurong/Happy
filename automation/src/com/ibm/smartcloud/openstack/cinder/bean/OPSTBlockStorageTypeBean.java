package com.ibm.smartcloud.openstack.cinder.bean;



/**
 * 类描述: 数据盘的类型    
 * 类名称：OPSTBlockStorageTypeBean     
 * 创建人：梁瑞   
 * 创建时间：2014年5月12日 上午9:56:42     
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OPSTBlockStorageTypeBean implements java.io.Serializable {
	private String id = "";
	private String name = "";
	private String extra_specs = "";
	
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
	 * @return the extra_specs
	 */
	public String getExtra_specs() {
		return extra_specs;
	}
	/**
	 * @param extra_specs the extra_specs to set
	 */
	public void setExtra_specs(String extra_specs) {
		this.extra_specs = extra_specs;
	}
	
}
