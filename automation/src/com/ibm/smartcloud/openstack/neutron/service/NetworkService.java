/*package com.ibm.smartcloud.openstack.neutron.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTNetworkBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTPortBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTSecurityGroupBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTSecurityGroupRuleBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTSubnetBean;

*//**
 * @Title:NetworkService     
 * @Description:网络以及安全组
 * @Auth:LiangRui   
 * @CreateTime:2015年6月10日 下午4:39:15       
 * @version V1.0
 *//*
public interface NetworkService {
	
	//网络
	public List<OPSTNetworkBean> getNetworkListV2(String tenantId, String tokenId) throws OPSTBaseException;
	public OPSTNetworkBean getNetworkByIdV2(String tenantId, String tokenId, String argNetworkId) throws OPSTBaseException;
	public OPSTNetworkBean createNetworkV2(String tenantId, String tokenId, OPSTNetworkBean argOPSTNetworkBean) throws OPSTBaseException;
	public OPSTNetworkBean updateNetworkV2(String tenantId, String tokenId, OPSTNetworkBean argOPSTNetworkBean) throws OPSTBaseException;
	public String deleteNetworkByIdV2(String tenantId, String tokenId, String argNetworkId) throws OPSTBaseException;
	
	//子网
	public List<OPSTSubnetBean> getSubnetListV2(String tenantId, String tokenId) throws OPSTBaseException;
	public OPSTSubnetBean getSubnetByIdV2(String tenantId, String tokenId,String argSubnetId) throws OPSTBaseException;
	public OPSTSubnetBean createSubnetV2(String tenantId, String tokenId, OPSTSubnetBean argOPSTSubnetBean) throws OPSTBaseException;
	public OPSTSubnetBean updateSubnetV2(String tenantId, String tokenId, OPSTSubnetBean argOPSTSubnetBean) throws OPSTBaseException;
	public String deleteSubnetByIdV2(String tenantId, String tokenId, String argSubnetId) throws OPSTBaseException;
	
	//端口
	public List<OPSTPortBean> getPortListV2(String tenantId, String tokenId) throws OPSTBaseException;
	public OPSTPortBean getPortByIdV2(String tenantId, String tokenId, String argPortId) throws OPSTBaseException;
	public OPSTPortBean createPortV2(String tenantId, String tokenId, OPSTPortBean argOPSTPortBean) throws OPSTBaseException;
	public OPSTPortBean updatePortV2(String tenantId, String tokenId, OPSTPortBean argOPSTPortBean) throws OPSTBaseException;
	public String deletePortByIdV2(String tenantId, String tokenId, String argPortId) throws OPSTBaseException;
	
	//安全组
	public List<OPSTSecurityGroupBean> getSecurityGroupsListV2(String tenantId, String tokenId) throws OPSTBaseException;
	public OPSTSecurityGroupBean getSecurityGroupsDetialByIdV2(String tenantId, String tokenId, String argSecurityGroupId) throws OPSTBaseException;
	public OPSTSecurityGroupRuleBean getSecurityGroupsRuleByIdV2(String tenantId, String tokenId, String argSGId) throws OPSTBaseException;
	public OPSTSecurityGroupBean createSecurityGroupV2(String tenantId, String tokenId, OPSTSecurityGroupBean argSercurityGroupsBean) throws OPSTBaseException;
	public OPSTSecurityGroupBean updateSecurityGroupV2(String tenantId, String tokenId, OPSTSecurityGroupBean argSercurityGroupsBean) throws OPSTBaseException;
	public String deleteSecurityGroupV2(String tenantId, String tokenId, String argSecurityGroupId) throws OPSTBaseException;
	public OPSTSecurityGroupRuleBean createSecurityGroupRuleV2(String tenantId, String tokenId, OPSTSecurityGroupRuleBean argSercurityGroupRuleBean) throws OPSTBaseException;
	public String deleteSecurityGroupRuleV2(String tenantId, String tokenId, String argSecurityGroupRuleId) throws OPSTBaseException;
	
	//根据serverID查询网络ID
	public List<OPSTNetworkBean> getNetworkIdByServerId(String tenantId, String tokenId, String serverId);
}
*/