package com.ibm.smartcloud.openstack.nova.bean;

import java.io.Serializable;
import java.util.List;

import com.ibm.smartcloud.openstack.core.bean.OPSTLinksBean;

/**
 * @Title:FlavorBean     
 * @Description:实例类型
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:09:11       
 * @version V1.0
 */
public class FlavorBean implements Serializable {
	private static final long serialVersionUID = -8828544645283234447L;
	
	private String id = "";//标识符
	private String name = "";//名字
	private String ram = "";//内存
	private String vcpus = "";//cpu数
	private String disk = "";//系统盘
	private String ephemeral = "";//临时盘
	private String swap = "";//交换磁盘
	private boolean protects = false;
	private String totalDisk="";//总磁盘
	
	
	
	public String getEphemeral() {
		return ephemeral;
	}
	public void setEphemeral(String ephemeral) {
		this.ephemeral = ephemeral;
	}
	public String getSwap() {
		return swap;
	}
	public void setSwap(String swap) {
		this.swap = swap;
	}
	private String isPublic = "";
	private List<OPSTLinksBean> links;
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
	 * @return the ram
	 */
	public String getRam() {
		return ram;
	}
	/**
	 * @param ram the ram to set
	 */
	public void setRam(String ram) {
		this.ram = ram;
	}
	/**
	 * @return the vcpus
	 */
	public String getVcpus() {
		return vcpus;
	}
	/**
	 * @param vcpus the vcpus to set
	 */
	public void setVcpus(String vcpus) {
		this.vcpus = vcpus;
	}
	/**
	 * @return the disk
	 */
	public String getDisk() {
		return disk;
	}
	/**
	 * @param disk the disk to set
	 */
	public void setDisk(String disk) {
		this.disk = disk;
	}
	
	
	public String getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	/**
	 * @return the links
	 */
	public List<OPSTLinksBean> getLinks() {
		return links;
	}
	/**
	 * @param links the links to set
	 */
	public void setLinks(List<OPSTLinksBean> links) {
		this.links = links;
	}
	public String getTotalDisk() {
		return totalDisk;
	}
	public void setTotalDisk(String totalDisk) {
		this.totalDisk = totalDisk;
	}
	public boolean isProtects() {
		return protects;
	}
	public void setProtects(boolean protects) {
		this.protects = protects;
	}
		
}
