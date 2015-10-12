/*package com.ibm.smartcloud.openstack.neutron.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTQuotaBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTFloatIpBean;

*//**
 * 类描述:弹性IP服务类
 * 类名称:OPSTFloatIpService
 * @author hanxinxing   创建时间：2014年6月11日 上午10:15
 * @version 1.0
 *//*

public interface FloatIpService {
	
	//获得弹性IP信息列表
	public List<OPSTFloatIpBean> getFloatIpList(String tenantId, String tokenId) throws OPSTBaseException;
	//获取弹性IP信息
	public OPSTFloatIpBean getFloatIpDetails(String tenantId, String tokenId, String id) throws OPSTBaseException;
	//分配弹性IP
	public String allocateFloatIp(String tenantId, String tokenId, String pool) throws OPSTBaseException;
	//释放弹性IP
	public String deallocateFloatIp(String tenantId, String tokenId, String id) throws OPSTBaseException;
	//关联虚机和弹性IP
	public String addFloatIpToInstance(String tenantId, String tokenId, String serverId, String address) throws OPSTBaseException;
	//取消关联虚机和弹性IP
	public String removeFloatIpFromInstance(String tenantId, String tokenId, String serverId, String address) throws OPSTBaseException;
	//获取quota信息
	public OPSTQuotaBean getQuota(String tenantId, String tokenId) throws OPSTBaseException;
	
}
*/