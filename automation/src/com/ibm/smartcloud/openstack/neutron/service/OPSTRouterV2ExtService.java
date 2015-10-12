/*package com.ibm.smartcloud.openstack.neutron.service;

import java.util.List;

import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTRouterBean;

*//**
 * 类名称:OPSTRouterV2ExtService
 * @author hanxinxing   创建时间：2014年6月5日 下午2:02
 * @version 1.0
 * @param <RouterBean>
 *//*

public interface OPSTRouterV2ExtService {
	
	//路由
	public List<OPSTRouterBean> getRouterListV2Ext(String tenantId,String tokenId) throws OPSTBaseException;
	public OPSTRouterBean getRouterByIdV2Ext(String argRouterId,String tenantId,String tokenId) throws OPSTBaseException;
	public OPSTRouterBean createRouterV2Ext(String argName,String tenantId,String tokenId) throws OPSTBaseException;
	public String deleteRouter(String argRouterId,String tenantId,String tokenId) throws OPSTBaseException;
	
	public OPSTRouterBean updateRouterSetGateWay(OPSTRouterBean argRequestBean, String tenantId,String tokenId) throws OPSTBaseException;
	public OPSTRouterBean updateRouterSetGateWay(String routerId,String tenantId,String tokenId) throws OPSTBaseException;
	
	*//**
	 * 给此路由创建接口
	 *//*
	public String createRouterInterface(String routerId, String subnetId,String tenantId,String tokenId) throws OPSTBaseException;
	public String deleteRouterInterface(String routerInterfaceId, String subnetId, String routerId,String tenantId,String tokenId) throws OPSTBaseException;
	
	
}
*/