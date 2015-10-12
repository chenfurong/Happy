/*package com.ibm.smartcloud.openstack.neutron.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTHealthMonitorBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTMemberBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTPoolBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTVIPBean;

*//**
 * 类描述:负载均衡服务类
 * 类名称：OPSTLBaasV2ExtService
 * @author zhanglong
 * @version 1.0   创建时间：2014年7月1日 下午2:28
 *//*

public interface OPSTLBaasV2ExtService {
	
	//获取HealthMonitor列表信息
	public List<OPSTHealthMonitorBean> getHealthMonitorList(String tenantId, String tokenId) throws OPSTBaseException;
	//创建HealthMonitor
	public OPSTHealthMonitorBean createHealthMonitor(String tenantId, String tokenId, OPSTHealthMonitorBean healthMonitor) throws OPSTBaseException;
	//获取HealthMonitor详情
	public OPSTHealthMonitorBean getHealthMonitorDetails(String tenantId, String tokenId, String healthMonitorId) throws OPSTBaseException;
	//更新HealthMonitor
	public OPSTHealthMonitorBean updateHealthMonitor(String tenantId, String tokenId, OPSTHealthMonitorBean healthMonitor) throws OPSTBaseException;
	//删除HealthMonitor
	public String deleteHealthMonitor(String tenantId, String tokenId, String healthMonitorId) throws OPSTBaseException;
	
	//获取Pool列表信息
	public List<OPSTPoolBean> getPoolList(String tenantId, String tokenId) throws OPSTBaseException;
	//创建Pool
	public OPSTPoolBean createPool(String tenantId, String tokenId, OPSTPoolBean pool) throws OPSTBaseException;
	//获取Pool详情
	public OPSTPoolBean getPoolDetails(String tenantId, String tokenId, String poolId) throws OPSTBaseException;
	//更新Pool
	public OPSTPoolBean updatePool(String tenantId, String tokenId, OPSTPoolBean pool) throws OPSTBaseException;
	//删除Pool
	public String deletePool(String tenantId, String tokenId, String poolId) throws OPSTBaseException;
	//关联健康监控到资源池
	public String AssociatesHealthMonitorToPool(String tenantId, String tokenId, String poolId, String healthMonitorId) throws OPSTBaseException;
	//从资源池取消健康监控关联
	public String DisassociatesHealthMoniterFromPool(String tenantId, String tokenId, String poolId, String healthMonitorId) throws OPSTBaseException;
	
	//获取VIP列表信息
	public List<OPSTVIPBean> getVipList(String tenantId, String tokenId) throws OPSTBaseException;
	//创建VIP
	public OPSTVIPBean createVip(String tenantId, String tokenId, OPSTVIPBean argOPSTVIPBean) throws OPSTBaseException;
	//获取VIP详情
	public OPSTVIPBean getVipById(String tenantId, String tokenId, String vipId) throws OPSTBaseException;
	//更新VIP
	public OPSTVIPBean updateVipById(String tenantId, String tokenId, OPSTVIPBean argOPSTVIPBean) throws OPSTBaseException;
	//删除VIP
	public String deleteVipByIdV2(String tenantId, String tokenId, String vipId) throws OPSTBaseException;
	
	//获取Member列表信息
	public List<OPSTMemberBean> getMemberList(String tenantId, String tokenId) throws OPSTBaseException;
	//创建Member
	public OPSTMemberBean createMember(String tenantId, String tokenId, OPSTMemberBean member) throws OPSTBaseException;
	//获取Member详情
	public OPSTMemberBean getMemberById(String tenantId, String tokenId, String memberId) throws OPSTBaseException;
	//更新Member
	public OPSTMemberBean updateMemberById(String tenantId, String tokenId, OPSTMemberBean member) throws OPSTBaseException;
	//删除Member
	public String deleteMemberByIdV2(String tenantId, String tokenId, String memberId) throws OPSTBaseException;
}*/