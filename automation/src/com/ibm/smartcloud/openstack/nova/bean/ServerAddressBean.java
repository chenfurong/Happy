package com.ibm.smartcloud.openstack.nova.bean;
/**
 * @Title:OPSTServerAddressBean     
 * @Description:实例网络信息
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午12:06:38       
 * @version V1.0
 */
public class ServerAddressBean {
	
	private String netName;
	private String addr;
	private String OS_EXT_IPS_type;
	private String version;
	
	public String getNetName() {
		return netName;
	}
	public void setNetName(String netName) {
		this.netName = netName;
	}
	public String getOS_EXT_IPS_type() {
		return OS_EXT_IPS_type;
	}
	public void setOS_EXT_IPS_type(String oS_EXT_IPS_type) {
		OS_EXT_IPS_type = oS_EXT_IPS_type;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
}
