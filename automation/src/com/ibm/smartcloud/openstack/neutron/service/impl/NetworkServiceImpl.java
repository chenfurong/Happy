/*package com.ibm.smartcloud.openstack.neutron.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTErrorMessageException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.service.impl.OPSTBaseServiceImpl;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.StringUtil;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTBindingVifDetailsBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTFixedIpsBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTNetworkBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTPortBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTSecurityGroupBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTSecurityGroupRuleBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTSubnetBean;
import com.ibm.smartcloud.openstack.neutron.constants.OPSTNetworkConst;
import com.ibm.smartcloud.openstack.neutron.service.NetworkService;
import com.ibm.smartcloud.openstack.nova.constants.OPSTFlavorsConst;

*//**
 * OpenStack 网络服务实现类
 * 
 * @author yuanhui
 *
 *//*
@Service("networkService")
public class NetworkServiceImpl extends OPSTBaseServiceImpl<Object> implements NetworkService {

	
	*//**
	 * 查询网络信息列表
	 * 
	 * @return List<OPSTNetworkBean> OPSTNetworkBean列表
	 *//*
	@Override
	public List<OPSTNetworkBean> getNetworkListV2(String tenantId, String tokenId) throws OPSTBaseException {
		
		//定义返回结果
		List<OPSTNetworkBean> networkBeanResultLst = new ArrayList<OPSTNetworkBean>();
		
		//获取Networking服务的主机地址(Neutron)
		//目前暂时通过Properties文件获取,以后是否改为从OpenStack 数据库读取访问API的Access数据,需要讨论.
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_NETWORK_LIST);

		//创建调用网络信息列表API的URL(V2)
		String strUrl = strNetworkHost + strNetworkAPI;
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的网络信息
			JSONArray networksJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.NETWORKBODY);
			for (int i = 0; i < networksJsonArray.size(); i++) {
				networkBeanResultLst.add(this.jsonObjToNetworkBeanV2(networksJsonArray.getJSONObject(i)));
			}
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取网络列表信息(getNetworkListV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return networkBeanResultLst;
	}
	
	*//**
	 * 根据Network ID查询指定网络的详细信息
	 * 
	 * @param argNetworkId Network ID
	 * @return OPSTNetworkBean 指定网络的详细信息
	 *//*
	@Override
	public OPSTNetworkBean getNetworkByIdV2(String tenantId, String tokenId, String argNetworkId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTNetworkBean networkBeanResult = null;
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);

		//获取调用指定网络信息的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_NETWORKBYID);

		//创建调用网络信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strNetworkAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_NETWORKID, argNetworkId);
		
		//调用OpenStack API获取指定网络信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.NETWORK);
			//获取返回结果中的网络信息
			networkBeanResult = this.jsonObjToNetworkBeanV2(jsonObj);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取网络详细信息(getNetworkByIdV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return networkBeanResult;
	}
	
	*//**
	 * 创建网络信息
	 * 
	 * @param argName 网络名称(可选)
	 * @param argAdminStateUp AdminStateUP(可选)
	 * @param argShare  是否共享(仅限 Admin-only, 可选)
	 * @param argTenantId 项目ID(仅限 Admin-only, 可选)
	 * 
	 * @return OPSTNetworkBean 已创建的网络详细信息
	 *//*
	@Override
	public OPSTNetworkBean createNetworkV2(String tenantId, String tokenId, OPSTNetworkBean argOPSTNetworkBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTNetworkBean networkBeanResult = new OPSTNetworkBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用创建网络信息的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATENETWORK);
		
		//创建调用创建网络信息列表API的URL(V2)
		String strUrl = strNetworkHost + strNetworkAPI;
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
   			"network":{
      			"name":"sample_network",
      			"admin_state_up":true
   			}
		}
		
		JSONObject networkRequestBody = new JSONObject();
		JSONObject networkJSONObj = new JSONObject();
				
		if (argOPSTNetworkBean.getName() != null) {
			//构建样例"name":"sample_network"
			networkJSONObj.element(OPSTNetworkConst.NETWORK_NAME, argOPSTNetworkBean.getName());
		}
		if (argOPSTNetworkBean.isAdmin_state_up()) {
			//构建样例"admin_state_up":true
			networkJSONObj.element(OPSTNetworkConst.NETWORK_ADMIN_STATE_UP, argOPSTNetworkBean.isAdmin_state_up());
		} else {
			networkJSONObj.element(OPSTNetworkConst.NETWORK_ADMIN_STATE_UP, false);
		}
		if (argOPSTNetworkBean.isAdminRole()) {
			networkJSONObj.element(OPSTNetworkConst.NETWORK_TENANT_ID, argOPSTNetworkBean.getTenant_id());
			networkJSONObj.element(OPSTNetworkConst.NETWORK_SHARED, argOPSTNetworkBean.isShared());
			networkJSONObj.element(OPSTNetworkConst.NETWORK_ROUTER_EXTERNAL, argOPSTNetworkBean.isRouter_external());
		}
		
		//构建样例{"network":{}}
		networkRequestBody.element(OPSTNetworkConst.NETWORK, networkJSONObj);

		//调用OpenStack API创建网络
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strUrl, networkRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.NETWORK)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的网络信息
			JSONObject networkResponse= jsonObj.getJSONObject(OPSTNetworkConst.NETWORK);
			
			networkBeanResult = this.jsonObjToNetworkBeanV2(networkResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建网络信息(createNetworkV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return networkBeanResult;
	}
	
	*//**
	 * 根据Network ID编辑网络
	 * 
	 * @param argNetworkId 网络ID
	 *//*
	@Override
	public OPSTNetworkBean updateNetworkV2(String tenantId, String tokenId, OPSTNetworkBean argOPSTNetworkBean) throws OPSTBaseException {
		//定义返回结果
		OPSTNetworkBean networkBeanResult = new OPSTNetworkBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
				
		//获取调用更新网络信息的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATENETWORKBYID);
		
		//创建调用网络信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strNetworkAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_NETWORKID, argOPSTNetworkBean.getId());
		
		JSONObject networkRequestBody = new JSONObject();
		JSONObject networkJSONObj = new JSONObject();

		networkJSONObj.element(OPSTNetworkConst.NETWORK_NAME, argOPSTNetworkBean.getName());
		networkJSONObj.element(OPSTNetworkConst.NETWORK_ADMIN_STATE_UP, argOPSTNetworkBean.isAdmin_state_up());
		if (argOPSTNetworkBean.isAdminRole()) {
			networkJSONObj.element(OPSTNetworkConst.NETWORK_SHARED, argOPSTNetworkBean.isShared());
			networkJSONObj.element(OPSTNetworkConst.NETWORK_ROUTER_EXTERNAL, argOPSTNetworkBean.isRouter_external());
		}
		
		networkRequestBody.element(OPSTNetworkConst.NETWORK, networkJSONObj);

		//调用OpenStack API编辑网络
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl, networkRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.NETWORK)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的网络信息
			JSONObject networkResponse= jsonObj.getJSONObject(OPSTNetworkConst.NETWORK);
			
			networkBeanResult = this.jsonObjToNetworkBeanV2(networkResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取更新网络信息(updateNetworkV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return networkBeanResult;
	}
	
	*//**
	 * 根据Network ID删除网络
	 * 
	 * @param argNetworkId 网络ID
	 *//*
	@Override
	public String deleteNetworkByIdV2(String tenantId, String tokenId, String argNetworkId) throws OPSTBaseException {
		
		//输入参数检查
		if(argNetworkId == null){
			throw new OPSTBaseException(argNetworkId);
		}
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取删除网络信息的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_NETWORK);
		
		//创建删除网络API的URL(V2)​​​
		String strOrgUrl = strNetworkHost + strNetworkAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_NETWORKID, argNetworkId);

		//调用OpenStack API删除指定的指定网络信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}
	
	*//**
	 * 查询子网信息列表
	 * 
	 *@return List<OPSTSubnetBean> OPSTSubnetBean列表
	 *//*
	@Override
	public List<OPSTSubnetBean> getSubnetListV2(String tenantId, String tokenId) throws OPSTBaseException {
		
		//定义返回结果
		List<OPSTSubnetBean> subnetBeanResultLst = new ArrayList<OPSTSubnetBean>();
		
		//获取Networking服务的主机地址(Neutron)
		//目前暂时通过Properties文件获取,以后是否改为从OpenStack 数据库读取访问API的Access数据,需要讨论.
		String strNetWorkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用子网信息列表的API调用命令
		String strSubnetAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SUBNETLIST);

		//创建调用子网信息列表API的URL(V2)
		String strUrl = strNetWorkHost + strSubnetAPI;
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的网络信息
			JSONArray subnetsJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.SUBNETBODY);
			for (int i = 0; i < subnetsJsonArray.size(); i++) {
				subnetBeanResultLst.add(this.jsonObjToSubnetBeanV2(subnetsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取子网列表信息(getSubnetListV2)的时候出现处理json数据异常，类型为："+e1.getMessage());
		}
		
		return subnetBeanResultLst;
	}

	*//**
	 * 根据Subnet ID查询指定子网的详细信息
	 * 
	 * @param argSubnetId Subnet ID
	 * @return OPSTSubnetBean 指定子网的详细信息
	 *//*
	@Override
	public OPSTSubnetBean getSubnetByIdV2(String tenantId, String tokenId, String argSubnetId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTSubnetBean subnetBeanResult = null;
		
		//获取Networking服务的主机地址(Neutron)
		String strNetWorkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);

		//获取调用指定子网信息的API调用命令
		String strSubnetAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SUBNETBYID);

		//创建调用网络信息列表API的URL(V2)
		String strOrgUrl = strNetWorkHost + strSubnetAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SUBNETID, argSubnetId);
		
		//调用OpenStack API获取指定网络信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.SUBNET);
			//获取返回结果中的网络信息
			subnetBeanResult = this.jsonObjToSubnetBeanV2(jsonObj);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取子网详细信息(getSubnetByIdV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return subnetBeanResult;
	}

	*//**
	 * 创建子网信息
	 * 
	 * @param argOPSTSubnetBean 创建子网DataBean
	 * 
	 * @return OPSTSubnetBean 已创建的子网详细信息
	 *//*
	@Override
	public OPSTSubnetBean createSubnetV2(String tenantId, String tokenId, OPSTSubnetBean argOPSTSubnetBean) throws OPSTBaseException {
		//创建用参数检查
		if (argOPSTSubnetBean == null) {
			//参数错误
			throw new OPSTBaseException();
		} 
		//network_id 和 cidr是必须提供项目
		if ("".equals(argOPSTSubnetBean.getNetwork_id()) || "".equals(argOPSTSubnetBean.getCidr())) {
			//参数错误
			throw new OPSTBaseException();
		}
		
		//定义返回结果
		OPSTSubnetBean subnetBeanResult = new OPSTSubnetBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用创建子网信息的API调用命令
		String strSubnetAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATESUBNET);
		
		//拼接创建子网信息列表API的URL(V2)
		String strUrl = strNetworkHost + strSubnetAPI;

		 按以下样例拼接:
			{
    		"subnet": {
        		"subnet": {
            		"network_id": "d32019d3-bc6e-4319-9c1d-6722fc136a22",
            		"ip_version": 4,
            		"cidr": "192.168.199.0/24"
        			}
    			}
			}
		 
		JSONObject subnetBodyObj = new JSONObject();
		JSONObject subnetRequest =  new JSONObject();
		JSONObject gatewayIpJSONObj = null;
		subnetRequest.element(OPSTNetworkConst.SUBNET_NETWORK_ID, argOPSTSubnetBean.getNetwork_id());
		subnetRequest.element(OPSTNetworkConst.SUBNET_NAME, argOPSTSubnetBean.getName());
		subnetRequest.element(OPSTNetworkConst.SUBNET_IP_VERSION, argOPSTSubnetBean.getIp_version());
		subnetRequest.element(OPSTNetworkConst.SUBNET_CIDR, argOPSTSubnetBean.getCidr());
		
		if (argOPSTSubnetBean.getGateway_ip() == null) {
			subnetRequest.element(OPSTNetworkConst.SUBNET_GATEWAY_IP, gatewayIpJSONObj);
		} else if (argOPSTSubnetBean.getGateway_ip() != ""){
			subnetRequest.element(OPSTNetworkConst.SUBNET_GATEWAY_IP, argOPSTSubnetBean.getGateway_ip());
		}
		
		if (argOPSTSubnetBean.getEnable_dhcp()) {
			subnetRequest.element(OPSTNetworkConst.SUBNET_ENABLE_DHCP, argOPSTSubnetBean.getEnable_dhcp());
		} else {
			subnetRequest.element(OPSTNetworkConst.SUBNET_ENABLE_DHCP, false);
		}
		
		if (argOPSTSubnetBean.getAllocation_pools() != null) {
			subnetRequest.element(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS, argOPSTSubnetBean.getAllocation_pools());
		}
		if (argOPSTSubnetBean.getDns_nameservers() != null) {
			subnetRequest.element(OPSTNetworkConst.SUBNET_DNS_NAMESERVERS, argOPSTSubnetBean.getDns_nameservers());
		}
		if (argOPSTSubnetBean.getHost_routes() != null) {
			subnetRequest.element(OPSTNetworkConst.SUBNET_HOST_ROUTES, argOPSTSubnetBean.getHost_routes());
		}
		subnetBodyObj.element(OPSTNetworkConst.SUBNET, subnetRequest);
		
		//调用OpenStack API创建子网
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strUrl, subnetBodyObj.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.SUBNET)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的子网信息
			JSONObject subnetResponse= jsonObj.getJSONObject(OPSTNetworkConst.SUBNET);
			
			subnetBeanResult = this.jsonObjToSubnetBeanV2(subnetResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建子网信息(createSubnetV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return subnetBeanResult;
	}

	*//**
	 * 根据Subnet ID编辑子网
	 * 
	 * @param argSubnetId 子网ID
	 *//*
	@Override
	public OPSTSubnetBean updateSubnetV2(String tenantId, String tokenId, OPSTSubnetBean argOPSTSubnetBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTSubnetBean subnetBeanResult = new OPSTSubnetBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
						
		//获取调用更新子网信息的API调用命令
		String strSubnetAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATESUBNETBYID);
				
		//创建调用子网信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strSubnetAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SUBNETID, argOPSTSubnetBean.getId());
				
		JSONObject subnetRequestBody = new JSONObject();
		JSONObject subnetJSONObj = new JSONObject();
		JSONObject gatewayIpJSONObj = null;

		subnetJSONObj.element(OPSTNetworkConst.SUBNET_NAME, argOPSTSubnetBean.getName());
		if (argOPSTSubnetBean.getGateway_ip() == null || argOPSTSubnetBean.getGateway_ip() == "") {
			subnetJSONObj.element(OPSTNetworkConst.SUBNET_GATEWAY_IP, gatewayIpJSONObj);
		} else {
			subnetJSONObj.element(OPSTNetworkConst.SUBNET_GATEWAY_IP, argOPSTSubnetBean.getGateway_ip());
		}
		//subnetJSONObj.element(OPSTNetworkConst.SUBNET_ID, argOPSTSubnetBean.getId());
		//subnetJSONObj.element(OPSTNetworkConst.SUBNET_NETWORK_ID, argOPSTSubnetBean.getNetwork_id());
		//subnetJSONObj.element(OPSTNetworkConst.SUBNET_IP_VERSION, argOPSTSubnetBean.getIp_version());
		//subnetJSONObj.element(OPSTNetworkConst.SUBNET_CIDR, argOPSTSubnetBean.getCidr());
		subnetJSONObj.element(OPSTNetworkConst.SUBNET_ENABLE_DHCP, argOPSTSubnetBean.getEnable_dhcp());
		//subnetJSONObj.element(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS, argOPSTSubnetBean.getAllocation_pools());
		if (argOPSTSubnetBean.getDns_nameservers() == null) {
			subnetJSONObj.element(OPSTNetworkConst.SUBNET_DNS_NAMESERVERS, gatewayIpJSONObj);
		} else {
			subnetJSONObj.element(OPSTNetworkConst.SUBNET_DNS_NAMESERVERS, argOPSTSubnetBean.getDns_nameservers());
		}
		
		if (argOPSTSubnetBean.getHost_routes() == null) {
			subnetJSONObj.element(OPSTNetworkConst.SUBNET_HOST_ROUTES, gatewayIpJSONObj);
		} else {
			subnetJSONObj.element(OPSTNetworkConst.SUBNET_HOST_ROUTES, argOPSTSubnetBean.getHost_routes());
		}
				
		subnetRequestBody.element(OPSTNetworkConst.SUBNET, subnetJSONObj);

		//调用OpenStack API更新子网
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl, subnetRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.SUBNET)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的网络信息
			JSONObject subnetResponse= jsonObj.getJSONObject(OPSTNetworkConst.SUBNET);
			
			subnetBeanResult = this.jsonObjToSubnetBeanV2(subnetResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取更新子网信息(updateSubnetV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return subnetBeanResult;
	}

	*//**
	 * 根据SubnetId 删除子网
	 * 
	 * @param argSubnetId 子网ID
	 *//*
	@Override
	public String deleteSubnetByIdV2(String tenantId, String tokenId, String argSubnetId) throws OPSTBaseException {
		
		//输入参数检查
		if(argSubnetId == null){
			throw new OPSTBaseException();
		}
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取删除子网信息的API调用命令
		String strSubnetAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_SUBNETBYID);
		
		//创建删除网络API的URL(V2)​​​
		String strOrgUrl = strNetworkHost + strSubnetAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SUBNETID, argSubnetId);

		//调用OpenStack API删除指定的子网信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}

	*//**
	 * 查询端口信息列表
	 * 
	 *@return List<OPSTPortBean> 端口信息列表
	 *//*
	@Override
	public List<OPSTPortBean> getPortListV2(String tenantId, String tokenId) throws OPSTBaseException {
		
		//定义返回结果
		List<OPSTPortBean> portBeanResultLst = new ArrayList<OPSTPortBean>();
				
		//获取Networking服务的主机地址(Neutron)
		String strNetWorkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
				
		//获取调用端口信息列表的API调用命令
		String strPortAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PORTLIST);

		//创建调用端口信息列表API的URL(V2)
		String strUrl = strNetWorkHost + strPortAPI;
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的网络信息
			JSONArray portsJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.PORTBODY);
			for (int i = 0; i < portsJsonArray.size(); i++) {
				portBeanResultLst.add(this.jsonObjToPortBeanV2(portsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取接口列表信息的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return portBeanResultLst;
	}

	*//**
	 * 根据Port ID查询指定端口的详细信息
	 * 
	 * @param argPortId Port ID
	 * @return OPSTPortBean 指定端口的详细信息
	 *//*
	@Override
	public OPSTPortBean getPortByIdV2(String tenantId, String tokenId, String argPortId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTPortBean portBeanResult = null;
				
		//获取Networking服务的主机地址(Neutron)
		String strNetWorkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);

		//获取调用指定子网信息的API调用命令
		String strPortAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PORTBYID);

		//创建调用网络信息列表API的URL(V2)
		String strOrgUrl = strNetWorkHost + strPortAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_PORTID, argPortId);
				
		//调用OpenStack API获取指定网络信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.PORT);
			//获取返回结果中的网络信息
			portBeanResult = this.jsonObjToPortBeanV2(jsonObj);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取接口详细信息(getPortByIdV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return portBeanResult;
	}

	*//**
	 * 创建端口信息
	 * 
	 * @param argOPSTPortBean 创建接口DataBean
	 * 
	 * @return OPSTPortBean 已创建的接口详细信息
	 *//*
	@Override
	public OPSTPortBean createPortV2(String tenantId, String tokenId, OPSTPortBean argOPSTPortBean) throws OPSTBaseException {
		
		//创建用参数检查
		if (argOPSTPortBean == null) {
			//参数错误
			throw new OPSTBaseException();
		} 
		//network_id 是必须提供项目
		if ("".equals(argOPSTPortBean.getNetwork_id())) {
			//参数错误
			throw new OPSTBaseException();
		}
		
		//定义返回结果
		OPSTPortBean portBeanResult = new OPSTPortBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用创建接口信息的API调用命令
		String strSubnetAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATEPORT);
		
		//拼接创建接口信息列表API的URL(V2)
		String strUrl = strNetworkHost + strSubnetAPI;

		 按以下样例拼接:
			{
			    "port": {
			        "network_id": "a87cc70a-3e15-4acf-8205-9b711a3531b7",
			        "name": "private-port",
			        "admin_state_up": true
			    }
			}
		 
		JSONObject portBodyObj = new JSONObject();
		JSONObject portRequest =  new JSONObject();
		//JSONObject gatewayIpJSONObj = null;
		portRequest.element(OPSTNetworkConst.PORT_NETWORK_ID, argOPSTPortBean.getNetwork_id());
		portRequest.element(OPSTNetworkConst.PORT_NAME, argOPSTPortBean.getName());
		
		if (argOPSTPortBean.isAdmin_state_up()) {
			portRequest.element(OPSTNetworkConst.PORT_ADMIN_STATE_UP, argOPSTPortBean.isAdmin_state_up());
		} else {
			portRequest.element(OPSTNetworkConst.PORT_ADMIN_STATE_UP, false);
		}
		
		portRequest.element(OPSTNetworkConst.PORT_DEVICE_ID, argOPSTPortBean.getDevice_id());
		portRequest.element(OPSTNetworkConst.PORT_DEVICE_OWNER, argOPSTPortBean.getDevice_owner());
		
		if (argOPSTPortBean.getGateway_ip() == null) {
			portRequest.element(OPSTNetworkConst.SUBNET_GATEWAY_IP, gatewayIpJSONObj);
		} else if (argOPSTPortBean.getGateway_ip() != ""){
			portRequest.element(OPSTNetworkConst.SUBNET_GATEWAY_IP, argOPSTPortBean.getGateway_ip());
		}

		if (argOPSTPortBean.getHost_routes() != null) {
			portRequest.element(OPSTNetworkConst.SUBNET_HOST_ROUTES, argOPSTPortBean.getHost_routes());
		}
		portBodyObj.element(OPSTNetworkConst.PORT, portRequest);
		
		//调用OpenStack API创建接口
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strUrl, portBodyObj.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.PORT)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的接口信息
			JSONObject portResponse= jsonObj.getJSONObject(OPSTNetworkConst.PORT);
			
			portBeanResult = this.jsonObjToPortBeanV2(portResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建接口信息(createPortV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return portBeanResult;
	}

	*//**
	 * 根据Port ID编辑端口
	 * 
	 * @param argPortId 端口ID
	 *//*
	@Override
	public OPSTPortBean updatePortV2(String tenantId, String tokenId, OPSTPortBean argOPSTPortBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTPortBean portBeanResult = new OPSTPortBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
								
		//获取调用更新端口信息的API调用命令
		String strPortAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATEPORTBYID);
						
		//创建调用端口信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strPortAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_PORTID, argOPSTPortBean.getId());
						
		JSONObject portRequestBody = new JSONObject();
		JSONObject portJSONObj = new JSONObject();

		portJSONObj.element(OPSTNetworkConst.PORT_NAME, argOPSTPortBean.getName());
		portJSONObj.element(OPSTNetworkConst.PORT_ADMIN_STATE_UP, argOPSTPortBean.isAdmin_state_up());
		portJSONObj.element(OPSTNetworkConst.PORT_DEVICE_ID, argOPSTPortBean.getDevice_id());
		portJSONObj.element(OPSTNetworkConst.PORT_DEVICE_OWNER, argOPSTPortBean.getDevice_owner());
						
		portRequestBody.element(OPSTNetworkConst.PORT, portJSONObj);

		//调用OpenStack API更新端口
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl, portRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.PORT)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的网络信息
			JSONObject portResponse= jsonObj.getJSONObject(OPSTNetworkConst.PORT);
							
			portBeanResult = this.jsonObjToPortBeanV2(portResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取更新端口信息(updatePortV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return portBeanResult;
	}

	*//**
	 * 根据PortId 删除端口
	 * 
	 * @param argPortId 端口ID
	 *//*
	@Override
	public String deletePortByIdV2(String tenantId, String tokenId, String argPortId) throws OPSTBaseException {
		
		//输入参数检查
		if(argPortId == null){
			throw new OPSTBaseException();
		}

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
				
		//获取删除子网信息的API调用命令
		String strPortAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_PORTBYID);
				
		//创建删除网络API的URL(V2)​​​
		String strOrgUrl = strNetworkHost + strPortAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_PORTID, argPortId);

		//调用OpenStack API删除指定的端口信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}
	
	*//**
	 * 查询安全组信息列表
	 * 
	 * @return List<OPSTSercurityGroupsBean> OPSTSercurityGroupsBean列表
	 *//*
	@Override
	public List<OPSTSecurityGroupBean> getSecurityGroupsListV2(String tenantId, String tokenId) throws OPSTBaseException {
		
		
			//定义返回结果
			List<OPSTSecurityGroupBean> sercurityGroups = new ArrayList<OPSTSecurityGroupBean>();
			//获取Networking服务的主机地址(Neutron)
			//目前暂时通过Properties文件获取,以后是否改为从OpenStack 数据库读取访问API的Access数据,需要讨论.
			String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
			//获取调用安全组信息列表的API调用命令
			String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SECURITYGROUPLIST);
			//创建调用安全组信息列表API的URL(V2)
			String strUrl = strNetworkHost + strSecurityGroupAPI;
			//调用OpenStack API获取安全组信息列表
			String strResponse = "";
			try {
				strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
			} catch (OPSTBaseException e) {
				throw e;
			}
			try {
				//解析Json结构
				JSONObject jsonObj = JSONObject.fromObject(strResponse);
				//获取返回结果中的安全组信息
				JSONArray securityGroupJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.SECURITYGROUPBODY);
				for (int i = 0; i < securityGroupJsonArray.size(); i++) {
					//解析获得的所有安全组信息
					//sercurityGroups.add(this.jsonObjToSecurityGroupBeanV2(securityGroupJsonArray.getJSONObject(i)));
					//
					//因为有不同的tenantId   在解析时  根据tenantId排查
					OPSTSecurityGroupBean bean = this.jsonObjToSecurityGroupBeanV2(securityGroupJsonArray.getJSONObject(i));
					if(bean.getTenant_id().equals(tenantId)){
						sercurityGroups.add(bean);
					}
				}
			} catch (JSONException e2) {
				throw new OPSTOperationException("获取安全组列表信息(getSecurityGroupsListV2)的时候出现解析json数据异常，类型为："+e2.getMessage());
			}
			return sercurityGroups;
		
		
	}
	
	*//**
	 * 根据ID查询指定安全组的详细信息
	 * 
	 * @param argSecurityGroupId 安全组ID
	 * @return OPSTSecurityGroupBean 安全组信息
	 *//*
	@Override
	public OPSTSecurityGroupBean getSecurityGroupsDetialByIdV2(String tenantId, String tokenId, String argSecurityGroupId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTSecurityGroupBean securityGroupBeanResult = null;
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);

		//获取调用指定安全组信息的API调用命令
		String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SECURITYGROUPBYID);

		//创建调用指定安全组信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strSecurityGroupAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SECURITYGROUPID, argSecurityGroupId);

		//调用OpenStack API获取指定安全组信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.SECURITYGROUP);
			//获取返回结果中的网络信息
			securityGroupBeanResult = this.jsonObjToSecurityGroupBeanV2(jsonObj);
			
			List<OPSTSecurityGroupRuleBean> list = new ArrayList<OPSTSecurityGroupRuleBean>();
			//获取返回结果中的安全组规则信息
			JSONArray securityGroupRuleJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.SECURITY_SECURITY_GROUP_RULES);
			for (int i = 0; i < securityGroupRuleJsonArray.size(); i++) {
				list.add(this.jsonObjToSecurityGroupRuleBeanV2(securityGroupRuleJsonArray.getJSONObject(i)));
			}
			securityGroupBeanResult.setSecurity_group_rules(list);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取安全组详细信息(getSecurityGroupsDetialByIdV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return securityGroupBeanResult;
	}
	
	*//**
	 * 创建安全组
	 * 
	 * @param argSercurityGroupsBean 安全组创建信息
	 * @return OPSTSecurityGroupBean 创建完成的安全组信息
	 *//*
	@Override
	public OPSTSecurityGroupBean createSecurityGroupV2(String tenantId, String tokenId, OPSTSecurityGroupBean argSercurityGroupsBean) throws OPSTBaseException {

		//输入检查
		if (argSercurityGroupsBean == null) {
			throw new OPSTBaseException();
		}
		
		//名称必须输入
		if ("".equals(argSercurityGroupsBean.getName().trim())) {
			throw new OPSTBaseException();
		}
		
		//定义返回结果
		OPSTSecurityGroupBean securityGroupBeanResult = new OPSTSecurityGroupBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用创建网络信息的API调用命令
		String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATESECURITYGROUP);
		
		//创建调用创建网络信息列表API的URL(V2)
		String strUrl = strNetworkHost + strSecurityGroupAPI;

		//拼接请求信息Json结构
		
		{
		    "security_group": {
		        "name": "new-webservers",
		        "description": "security group for webservers"
		    }
		}
		 
		JSONObject securityGroupRequestBody = new JSONObject();
		JSONObject securityGroupJSONObj = new JSONObject();
		
		//构建样例{"name": "new-webservers"}
		securityGroupJSONObj.element(OPSTNetworkConst.SECURITY_NAME, argSercurityGroupsBean.getName().trim());
		//构建样例{"description": "security group for webservers"}
		if (!"".equals(argSercurityGroupsBean.getDescription().trim())) {
			securityGroupJSONObj.element(OPSTNetworkConst.SECURITY_DESCRIPTION, argSercurityGroupsBean.getDescription().trim());
		}
		if (argSercurityGroupsBean.getTenant_id() != null && argSercurityGroupsBean.getTenant_id() != tenantId) {
			securityGroupJSONObj.element(OPSTNetworkConst.SECURITY_TENANT_ID, argSercurityGroupsBean.getTenant_id().trim());
		}

		//构建样例{"security_group":{}}
		securityGroupRequestBody.element(OPSTNetworkConst.SECURITYGROUP, securityGroupJSONObj);

		//调用OpenStack API创建安全组
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strUrl, securityGroupRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.SECURITYGROUP)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的安全组信息
			JSONObject securityGroupResponse= jsonObj.getJSONObject(OPSTNetworkConst.SECURITYGROUP);
			
			securityGroupBeanResult = this.jsonObjToSecurityGroupBeanV2(securityGroupResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建安全组信息(createSecurityGroupV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return securityGroupBeanResult;
	}
	
	@Override
	public OPSTSecurityGroupBean updateSecurityGroupV2(String tenantId, String tokenId, OPSTSecurityGroupBean argSercurityGroupsBean) throws OPSTBaseException {

		//输入检查
		if (argSercurityGroupsBean == null) {
			throw new OPSTBaseException();
		}

		//id必须存在
		if ("".equals(argSercurityGroupsBean.getId().trim())) {
			throw new OPSTBaseException();
		}
		
		//名称必须输入
		if ("".equals(argSercurityGroupsBean.getName().trim())) {
			throw new OPSTBaseException();
		}
		
		//定义返回结果
		OPSTSecurityGroupBean securityGroupBeanResult = new OPSTSecurityGroupBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用创建网络信息的API调用命令
		String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATESECURITYGROUP);
		
		//创建调用创建网络信息列表API的URL(V2)
		String strUrl = strNetworkHost + strSecurityGroupAPI;

		//拼接请求信息Json结构

		JSONObject securityGroupRequestBody = new JSONObject();
		JSONObject securityGroupJSONObj = new JSONObject();
		
		//构建样例{"name": "new-webservers"}
		securityGroupJSONObj.element(OPSTNetworkConst.SECURITY_NAME, argSercurityGroupsBean.getName().trim());
		//构建样例{"description": "security group for webservers"}
		if (!"".equals(argSercurityGroupsBean.getDescription().trim())) {
			securityGroupJSONObj.element(OPSTNetworkConst.SECURITY_DESCRIPTION, argSercurityGroupsBean.getDescription().trim());
		}

		//构建样例{"security_group":{}}
		securityGroupRequestBody.element(OPSTNetworkConst.SECURITYGROUP, securityGroupJSONObj);
		
		//调用OpenStack API编辑安全组
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strUrl, securityGroupRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.SECURITYGROUP)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的网络信息
			JSONObject securityGroupResponse= jsonObj.getJSONObject(OPSTNetworkConst.SECURITYGROUP);
			
			securityGroupBeanResult = this.jsonObjToSecurityGroupBeanV2(securityGroupResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取更新安全组信息(updateSecurityGroupV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return securityGroupBeanResult;
	}
	
	*//**
	 * 根据ID删除安全组
	 * 
	 * @param argSecurityGroupId 安全组ID
	 *//*
	@Override
	public String deleteSecurityGroupV2(String tenantId, String tokenId, String argSecurityGroupId) throws OPSTBaseException {
		
		//输入参数检查
		if(argSecurityGroupId == null){
			throw new OPSTBaseException();
		}
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取删除安全组信息的API调用命令
		String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_SECURITYGROUPBYID);
		
		//创建删除安全组API的URL(V2)​​​
		String strOrgUrl = strNetworkHost + strSecurityGroupAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SECURITYGROUPID, argSecurityGroupId);

		//调用OpenStack API删除指定的安全组信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}
	
	*//**
	 * 根据ID查询指定安全组的详细信息  
	 *      				包括 安全组规则信息
	 * @param argSGId 安全组ID
	 * @return OPSTSecurityGroupBean 安全组规则信息
	 *//*
	@Override
	public OPSTSecurityGroupRuleBean getSecurityGroupsRuleByIdV2(String tenantId, String tokenId, String argSGRuleId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTSecurityGroupRuleBean sgRuleBeanResult = null;
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);

		//获取调用指定安全组信息的API调用命令
		String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_SECURITYGROUPRULEBYID);

		//创建调用指定安全组信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strSecurityGroupAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SECURITYGROUPSRULESID, argSGRuleId);

		//调用OpenStack API获取指定安全组信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//解析Json结构
		JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.SECURITY_SECURITY_GROUP_RULE);
		//获取返回结果中的网络信息
		sgRuleBeanResult = this.jsonObjToSecurityGroupRuleBeanV2(jsonObj);
		
		return sgRuleBeanResult;
	}
	
	*//**
	 * 创建安全组规则
	 *//*
	@Override
	public OPSTSecurityGroupRuleBean createSecurityGroupRuleV2(String tenantId, String tokenId, OPSTSecurityGroupRuleBean argRuleBean) throws OPSTBaseException {

		//输入检查
		if (argRuleBean == null) {
			throw new OPSTBaseException();
		}
		
		//定义返回结果
		OPSTSecurityGroupRuleBean securityGroupRuleBeanResult = new OPSTSecurityGroupRuleBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用创建安全组规则信息的API调用命令
		String strSecurityGroupRuleAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATESECURITYGROUPRULE);
		
		//创建调用创建安全组规则信息列表API的URL(V2)
		String strUrl = strNetworkHost + strSecurityGroupRuleAPI;

		//拼接请求信息Json结构		
		JSONObject securityGroupRequestBody = new JSONObject();
		JSONObject securityGroupJSONObj = new JSONObject();
		//构建样例
		if (!"".equals(argRuleBean.getSecurity_group_id().trim())) {//安全组ID
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_SECURITY_GROUP_ID, argRuleBean.getSecurity_group_id().trim());
		}
		if (!"".equals(argRuleBean.getRemote_group_id().trim())) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_REMOTEGROUPID, argRuleBean.getRemote_group_id().trim());
		}
		if (!"".equals(argRuleBean.getRemote_ip_prefix().trim())) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_REMOTEIPPREFIX, argRuleBean.getRemote_ip_prefix().trim());
		}
		if (!"".equals(argRuleBean.getDirection().trim())) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_DIRECTION, argRuleBean.getDirection().trim());
		}
		if (argRuleBean.getProtocol() != null) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_PROTOCOL, argRuleBean.getProtocol().trim());
		}
		if (!"".equals(argRuleBean.getEthertype().trim())) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_ETHERTYPE, argRuleBean.getEthertype().trim());
		}
		if (!"".equals(argRuleBean.getPort_range_max().trim())) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_PORTRANGEMAX, argRuleBean.getPort_range_max().trim());
		}
		if (!"".equals(argRuleBean.getPort_range_min().trim())) {//
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_PORTRANGEMIN, argRuleBean.getPort_range_min().trim());
		}
		if (argRuleBean.getTenant_id() != null && argRuleBean.getTenant_id() != tenantId) {
			securityGroupJSONObj.element(OPSTNetworkConst.SGRULE_TENANT_ID, argRuleBean.getTenant_id().trim());
		}
		
		//构建样例{"security_group":{}}
		securityGroupRequestBody.element(OPSTNetworkConst.SECURITY_SECURITY_GROUP_RULE, securityGroupJSONObj);

		//调用OpenStack API创建安全组
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strUrl, securityGroupRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.SECURITY_SECURITY_GROUP_RULE)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的安全组规则信息
			JSONObject securityGroupRuleResponse= jsonObj.getJSONObject(OPSTNetworkConst.SECURITY_SECURITY_GROUP_RULE);
			
			securityGroupRuleBeanResult = this.jsonObjToSecurityGroupRuleBeanV2(securityGroupRuleResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建安全组规则信息(createSecurityGroupRuleV2)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return securityGroupRuleBeanResult;
	}
	
	*//**
	 * 根据ID删除安全组规则
	 * 
	 * @param argSecurityGroupRuleId 安全组规则ID
	 *//*
	@Override
	public String deleteSecurityGroupRuleV2(String tenantId, String tokenId, String argSecurityGroupRuleId) throws OPSTBaseException {
		
		//输入参数检查
		if(argSecurityGroupRuleId == null){
			throw new OPSTBaseException();
		}
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取删除安全组规则信息的API调用命令
		String strSecurityGroupAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_SECURITYGROUPRULEBYID);
		
		//创建删除安全组规则API的URL(V2)​​​
		String strOrgUrl = strNetworkHost + strSecurityGroupAPI;
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_SECURITYGROUPSRULESID, argSecurityGroupRuleId);

		//调用OpenStack API删除指定的安全组规则信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}
	
	
	 * JsonObj 转换成 OPSTNetworkBean 私有方法
	 
	private OPSTNetworkBean jsonObjToNetworkBeanV2(JSONObject argNetwork){
		
		OPSTNetworkBean oPSTNetworkBean = new OPSTNetworkBean();
		
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_ID)) {
			oPSTNetworkBean.setId(argNetwork.getString(OPSTNetworkConst.NETWORK_ID));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_NAME)) {
			oPSTNetworkBean.setName(argNetwork.getString(OPSTNetworkConst.NETWORK_NAME));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_STATUS)) {
			oPSTNetworkBean.setStatus(argNetwork.getString(OPSTNetworkConst.NETWORK_STATUS));
		}
		
		//获取subnet id list
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_SUBNETS)) {
			JSONArray subnetsObj = argNetwork.getJSONArray(OPSTNetworkConst.NETWORK_SUBNETS);
			@SuppressWarnings("unchecked")
			List<String> subnetIdlst = JSONArray.toList(subnetsObj);
			oPSTNetworkBean.setSubnets(subnetIdlst);
		}
		
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_TENANT_ID)) {
			oPSTNetworkBean.setTenant_id(argNetwork.getString(OPSTNetworkConst.NETWORK_TENANT_ID));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_SHARED)) {
			oPSTNetworkBean.setShared(Boolean.parseBoolean(argNetwork.getString(OPSTNetworkConst.NETWORK_SHARED)));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_ADMIN_STATE_UP)) {
			oPSTNetworkBean.setAdmin_state_up(Boolean.parseBoolean(argNetwork.getString(OPSTNetworkConst.NETWORK_ADMIN_STATE_UP)));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_ROUTER_EXTERNAL)) {
			oPSTNetworkBean.setRouter_external(Boolean.parseBoolean(argNetwork.getString(OPSTNetworkConst.NETWORK_ROUTER_EXTERNAL)));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_PROVIDER_NETWORK_TYPE)) {
			oPSTNetworkBean.setProvider_network_type(argNetwork.getString(OPSTNetworkConst.NETWORK_PROVIDER_NETWORK_TYPE));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_PROVIDER_PHYSICAL_NETWORK)) {
			oPSTNetworkBean.setProvider_physical_network(argNetwork.getString(OPSTNetworkConst.NETWORK_PROVIDER_PHYSICAL_NETWORK));
		}
		if(argNetwork.containsKey(OPSTNetworkConst.NETWORK_PROVIDER_SEGMENTATION_ID)) {
			oPSTNetworkBean.setProvider_segmentation_id(argNetwork.getString(OPSTNetworkConst.NETWORK_PROVIDER_SEGMENTATION_ID));
		}
		
		return oPSTNetworkBean;
	}
	
	
	 * JsonObj 转换成 OPSTSubnetBean 私有方法
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private OPSTSubnetBean jsonObjToSubnetBeanV2(JSONObject argSubnet) {
		
		OPSTSubnetBean subnet = new OPSTSubnetBean();
		
		//id
		subnet.setId(argSubnet.getString(OPSTNetworkConst.SUBNET_ID));
		//name
		subnet.setName(argSubnet.getString(OPSTNetworkConst.SUBNET_NAME));
		//enable_dhcp
		subnet.setEnable_dhcp(Boolean.parseBoolean(argSubnet.getString(OPSTNetworkConst.SUBNET_ENABLE_DHCP)));
		//network_id
		subnet.setNetwork_id(argSubnet.getString(OPSTNetworkConst.SUBNET_NETWORK_ID));
		//tenant_id
		subnet.setTenant_id(argSubnet.getString(OPSTNetworkConst.SUBNET_TENANT_ID));
		//dns_nameservers
		JSONArray dnsJsonArray = argSubnet.getJSONArray(OPSTNetworkConst.SUBNET_DNS_NAMESERVERS);
		List<String> dnsNameLst = JSONArray.toList(dnsJsonArray);
		subnet.setDns_nameservers(dnsNameLst);
		//allocation_pools
		JSONArray locationPoolJsonArray = argSubnet.getJSONArray(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS);
		JSONObject locationPoolJsonObj = null;
		Map<String, String> locationPoolMap = null;
		List<Map> locationPoolLst = new ArrayList<Map>();
		for (int i = 0; i < locationPoolJsonArray.size(); i++) {
			
			locationPoolJsonObj = locationPoolJsonArray.getJSONObject(i);
			locationPoolMap = new HashMap<String, String>();
			locationPoolMap.put(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS_START, locationPoolJsonObj.getString(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS_START));
			locationPoolMap.put(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS_END, locationPoolJsonObj.getString(OPSTNetworkConst.SUBNET_ALLOCATION_POOLS_END));
			locationPoolLst.add(locationPoolMap);
		}	
		subnet.setAllocation_pools(locationPoolLst);
		//host_routes
		JSONArray hostRouteJsonArray = argSubnet.getJSONArray(OPSTNetworkConst.SUBNET_HOST_ROUTES);
		错误List<String> hostRouteLst = new ArrayList<String>();
		String strHostRoute = "";
		JSONObject hostRouteJsonObj = null;
		for (int i = 0; i < hostRouteJsonArray.size(); i++) {
			hostRouteJsonObj = hostRouteJsonArray.getJSONObject(i);
			strHostRoute = hostRouteJsonObj.toString();
			hostRouteLst.add(strHostRoute);	
		}
		List<String> hostRouteLst = JSONArray.toList(hostRouteJsonArray);
		subnet.setHost_routes(hostRouteLst);
		//ip_version
		subnet.setIp_version(argSubnet.getString(OPSTNetworkConst.SUBNET_IP_VERSION));
		//gateway_ip
		if (!argSubnet.getString(OPSTNetworkConst.SUBNET_GATEWAY_IP).equals("null")){
			subnet.setGateway_ip(argSubnet.getString(OPSTNetworkConst.SUBNET_GATEWAY_IP));
		} else {
			subnet.setGateway_ip("");
		}
		//cidr
		subnet.setCidr(argSubnet.getString(OPSTNetworkConst.SUBNET_CIDR));
		
		return subnet;
	}
	
	
	 * JsonObj 转换成 OPSTPortBean 私有方法
	 
	@SuppressWarnings("unchecked")
	private OPSTPortBean jsonObjToPortBeanV2(JSONObject argPort) {
		
		OPSTPortBean port = new OPSTPortBean();
		
		//id
		if(argPort.containsKey(OPSTNetworkConst.PORT_ID)) {
			port.setId(argPort.getString(OPSTNetworkConst.PORT_ID));
		}
		//device_id
		if(argPort.containsKey(OPSTNetworkConst.PORT_DEVICE_ID)) {
			port.setDevice_id(argPort.getString(OPSTNetworkConst.PORT_DEVICE_ID));
		}
		//name
		if(argPort.containsKey(OPSTNetworkConst.PORT_NAME)) {
			port.setName(argPort.getString(OPSTNetworkConst.PORT_NAME));
		}
		//status
		if(argPort.containsKey(OPSTNetworkConst.PORT_STATUS)) {
			port.setStatus(argPort.getString(OPSTNetworkConst.PORT_STATUS));
		}
		//binging_host_id
		if(argPort.containsKey(OPSTNetworkConst.PORT_BINDING_HOST_ID)) {
			port.setBinding_host_id(argPort.getString(OPSTNetworkConst.PORT_BINDING_HOST_ID));
		}
		//allowed_address_pairs (List<String>)
		if(argPort.containsKey(OPSTNetworkConst.PORT_ALLOWED_ADRESS_PAIRS)) {
			JSONArray pairsJsonArray = argPort.getJSONArray(OPSTNetworkConst.PORT_ALLOWED_ADRESS_PAIRS);
			错误List<String> pairsLst = new ArrayList<String>();
			String strPairs = "";
			JSONObject pairsJsonObj = null;
			for (int i = 0; i < pairsJsonArray.size(); i++) {
				pairsJsonObj = pairsJsonArray.getJSONObject(i);
				strPairs = pairsJsonObj.toString();
				pairsLst.add(strPairs);
			}
			List<String> pairsLst = JSONArray.toList(pairsJsonArray);
			port.setAllowed_address_pairs(pairsLst);
		}
		//admin_state_up (boolean)
		if(argPort.containsKey(OPSTNetworkConst.PORT_ADMIN_STATE_UP)) {
			port.setAdmin_state_up(Boolean.parseBoolean(argPort.getString(OPSTNetworkConst.PORT_ADMIN_STATE_UP)));
		}
		//network_id
		if(argPort.containsKey(OPSTNetworkConst.PORT_NETWORK_ID)) {
			port.setNetwork_id(argPort.getString(OPSTNetworkConst.PORT_NETWORK_ID));
		}
		//tenant_id
		if(argPort.containsKey(OPSTNetworkConst.PORT_TENANT_ID)) {
			port.setTenant_id(argPort.getString(OPSTNetworkConst.PORT_TENANT_ID));
		}
		//extra_dhcp_opts (List<String>)
		if (argPort.containsKey(OPSTNetworkConst.PORT_EXTRA_DHCP_OPTS)) {
			JSONArray dhcpJsonArray = argPort.getJSONArray(OPSTNetworkConst.PORT_EXTRA_DHCP_OPTS);
			错误List<String> extraDhcpLst = new ArrayList<String>();
			String strExtraDhcp = "";
			JSONObject extraDhcpJsonObj = null;
			for (int i = 0; i < dhcpJsonArray.size(); i++) {
				extraDhcpJsonObj = dhcpJsonArray.getJSONObject(i);
				strExtraDhcp = extraDhcpJsonObj.toString();
				extraDhcpLst.add(strExtraDhcp);	
			}
			List<String> extraDhcpLst = JSONArray.toList(dhcpJsonArray);
			port.setExtra_dhcp_opts(extraDhcpLst);
		}
		//binding_vif_details (OPSTBindingVifDetailsBean)
		if (argPort.containsKey(OPSTNetworkConst.PORT_BINDING_VIF_DETAILS)) {
			JSONObject bindingVifDetailJsonObj = argPort.getJSONObject(OPSTNetworkConst.PORT_BINDING_VIF_DETAILS);
			if (!(bindingVifDetailJsonObj.isNullObject())) {
				OPSTBindingVifDetailsBean bvd = new OPSTBindingVifDetailsBean();
				if (bindingVifDetailJsonObj.containsKey(OPSTNetworkConst.BINDING_VIF_DETAILS_PORT_FILTER)) {
					bvd.setPort_filter(Boolean.parseBoolean(bindingVifDetailJsonObj.getString(OPSTNetworkConst.BINDING_VIF_DETAILS_PORT_FILTER)));
				}
				if (bindingVifDetailJsonObj.containsKey(OPSTNetworkConst.BINDING_VIF_DETAILS_OVS_HYBRID_PLUG)) {
					bvd.setOvs_hybrid_plug(Boolean.parseBoolean(bindingVifDetailJsonObj.getString(OPSTNetworkConst.BINDING_VIF_DETAILS_OVS_HYBRID_PLUG)));
				}
				port.setBinding_vif_details(bvd);
			} else {
				port.setBinding_vif_details(null);
			}
		}
		//binding_vif_type
		if(argPort.containsKey(OPSTNetworkConst.PORT_BINDING_VIF_TYPE)) {
			port.setBinding_vif_type(argPort.getString(OPSTNetworkConst.PORT_BINDING_VIF_TYPE));
		}
		//device_owner
		if(argPort.containsKey(OPSTNetworkConst.PORT_DEVICE_OWNER)) {
			port.setDevice_owner(argPort.getString(OPSTNetworkConst.PORT_DEVICE_OWNER));
		}
		//mac_address
		if(argPort.containsKey(OPSTNetworkConst.PORT_MAC_ADRESS)) {
			port.setMac_address(argPort.getString(OPSTNetworkConst.PORT_MAC_ADRESS));
		}
		//binding_profile (String)
		if (argPort.containsKey(OPSTNetworkConst.PORT_BINDING_PROFILE)) {
			JSONObject profileJsonObj = argPort.getJSONObject(OPSTNetworkConst.PORT_BINDING_PROFILE);
			错误List<String> profileLst = new ArrayList<String>();
			String strProfile = "";
			JSONObject profileJsonObj = null;
			for (int i = 0; i < profileJsonArray.size(); i++) {
				profileJsonObj = profileJsonArray.getJSONObject(i);
				strProfile = profileJsonObj.toString();
				profileLst.add(strProfile);	
			}	
			JSONObject json = new JSONObject();
			if (!profileJsonObj.equals(json)) {
				port.setBinding_profile(profileJsonObj.toString());
			}
		}
		//binding_vnic_type
		if(argPort.containsKey(OPSTNetworkConst.PORT_BINDING_VNIC_TYPE)) {
			port.setBinding_vnic_type(argPort.getString(OPSTNetworkConst.PORT_BINDING_VNIC_TYPE));
		}
		//fixed_ips (List<OPSTFixedIpsBean>)
		if(argPort.containsKey(OPSTNetworkConst.PORT_FIXED_IPS)) {
			JSONArray fixedIpsJsonArray = argPort.getJSONArray(OPSTNetworkConst.PORT_FIXED_IPS);
			List<OPSTFixedIpsBean> fixedipsLst = new ArrayList<OPSTFixedIpsBean>();
			JSONObject fixedIpsJsonObj = null;
			for (int i = 0; i < fixedIpsJsonArray.size(); i++) {
				OPSTFixedIpsBean ofi = new OPSTFixedIpsBean();
				fixedIpsJsonObj = fixedIpsJsonArray.getJSONObject(i);
				if(!(fixedIpsJsonObj.isNullObject())){
					ofi.setSubnet_id(fixedIpsJsonObj.getString(OPSTNetworkConst.FIXED_IPS_SUBNET_ID));
					ofi.setIp_address(fixedIpsJsonObj.getString(OPSTNetworkConst.FIXED_IPS_IP_ADRESS));
				}
				fixedipsLst.add(ofi);
			}
			port.setFixed_ips(fixedipsLst);
		}
		//security_groups (List<String>)
		if (argPort.containsKey(OPSTNetworkConst.PORT_SECURITY_GROUPS)) {
			JSONArray securityJsonArray = argPort.getJSONArray(OPSTNetworkConst.PORT_SECURITY_GROUPS);
			错误List<String> securityLst = new ArrayList<String>();
			String strSecurity = "";
			JSONObject securityJsonObj = null;
			for (int i = 0; i < securityJsonArray.size(); i++) {
				securityJsonObj = securityJsonArray.getJSONObject(i);
				strSecurity = securityJsonObj.toString();
				securityLst.add(strSecurity);	
			}
			List<String> securityLst = JSONArray.toList(securityJsonArray);
			port.setSecurity_groups(securityLst);
		}
		return port;
	}
	
	*//**
	 * JsonObj 转换成 OPSTSecurityGroupBean 私有方法
	 *//*
	private OPSTSecurityGroupBean jsonObjToSecurityGroupBeanV2(JSONObject argSercurity){
		OPSTSecurityGroupBean sercurity = new OPSTSecurityGroupBean();
		sercurity.setId(argSercurity.getString(OPSTNetworkConst.SECURITY_ID));
		sercurity.setName(argSercurity.getString(OPSTNetworkConst.SECURITY_NAME));
		sercurity.setDescription(argSercurity.getString(OPSTNetworkConst.SECURITY_DESCRIPTION));
		sercurity.setTenant_id(argSercurity.getString(OPSTNetworkConst.SECURITY_TENANT_ID));

		return sercurity;
	}
	
	*//**
	 * 安全组  规则
	 * JsonObj 转换成 OPSTSecurityGroupRuleBean 私有方法
	 *//*
	private OPSTSecurityGroupRuleBean jsonObjToSecurityGroupRuleBeanV2(JSONObject argSgRule){
		OPSTSecurityGroupRuleBean sgRule = new OPSTSecurityGroupRuleBean();

		sgRule.setId(argSgRule.getString(OPSTNetworkConst.SGRULE_ID));
		sgRule.setTenant_id(argSgRule.getString(OPSTNetworkConst.SGRULE_TENANT_ID));
		if(!argSgRule.getString(OPSTNetworkConst.SGRULE_REMOTEGROUPID).equals("null")){
			sgRule.setRemote_group_id(argSgRule.getString(OPSTNetworkConst.SGRULE_REMOTEGROUPID));
		}else{
			sgRule.setRemote_group_id("");
		}
		if(!argSgRule.getString(OPSTNetworkConst.SGRULE_REMOTEIPPREFIX).equals("null")){
			sgRule.setRemote_ip_prefix(argSgRule.getString(OPSTNetworkConst.SGRULE_REMOTEIPPREFIX));
		}else{
			sgRule.setRemote_ip_prefix("");
		}
		if(!argSgRule.getString(OPSTNetworkConst.SGRULE_PORTRANGEMAX).equals("null")){
			sgRule.setPort_range_max(argSgRule.getString(OPSTNetworkConst.SGRULE_PORTRANGEMAX));
		}else{
			sgRule.setPort_range_max("");
		}
		if(!argSgRule.getString(OPSTNetworkConst.SGRULE_PORTRANGEMIN).equals("null")){
			sgRule.setPort_range_min(argSgRule.getString(OPSTNetworkConst.SGRULE_PORTRANGEMIN));
		}else{
			sgRule.setPort_range_min("");
		}
		if(!argSgRule.getString(OPSTNetworkConst.SGRULE_PROTOCOL).equals("null")){
			sgRule.setProtocol(argSgRule.getString(OPSTNetworkConst.SGRULE_PROTOCOL));
		}else{
			sgRule.setProtocol("");
		}
		sgRule.setDirection(argSgRule.getString(OPSTNetworkConst.SGRULE_DIRECTION));
		sgRule.setEthertype(argSgRule.getString(OPSTNetworkConst.SGRULE_ETHERTYPE));
		
		return sgRule;
	}

	@Override
	public List<OPSTNetworkBean> getNetworkIdByServerId(String tenantId, String tokenId, String serverId) {
		//定义返回结果
		List<OPSTNetworkBean> networkBeantLst = new ArrayList<OPSTNetworkBean>();
		
		String strNovaHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_NetworkIdByServerId);
		String strUrl = strNovaHost + strNetworkAPI;
		String strDestUrl = strUrl.replace(OPSTFlavorsConst.PARAM_TENANTID,tenantId);
		String strDestUrl2 = strDestUrl.replace(OPSTFlavorsConst.PARAM_SERVERID,serverId);
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObj = JSONObject.fromObject(strResponse);
		JSONArray networksJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.INTERFACEBODY);
		for (int i = 0; i < networksJsonArray.size(); i++) {
			networkBeantLst.add(this.jsonObjToNetworkBeansV2(networksJsonArray.getJSONObject(i)));
		}
		return null;
	}
	
	private OPSTNetworkBean jsonObjToNetworkBeansV2(JSONObject argNetwork){
		//OPSTNetworkBean netWorks = new OPSTNetworkBean();
		if(null != argNetwork.getJSONObject("addresses")){
			List<OPSTInterfaceAttachmentsBean> attachment = new ArrayList<OPSTInterfaceAttachmentsBean>();
			for (int i = 0; i < obj2.size(); i++) {
				OPSTServerAddressBean address = new OPSTServerAddressBean();
				String netName = (String)obj2.get(i);
				JSONArray netInfo = obj1.getJSONArray(netName);
				JSONObject obj3 = netInfo.getJSONObject(0);
				address.setNetName(netName);
				address.setAddr(obj3.getString("addr"));
				address.setOS_EXT_IPS_MAC_mac_addr(obj3.getString("OS-EXT-IPS-MAC:mac_addr"));
				address.setOS_EXT_IPS_type(obj3.getString("OS-EXT-IPS:type"));
				address.setVersion(obj3.getString("version"));
				serAdd.add(address);
			}
			server.setAddresses(serAdd);
		}
		return null;
	}
}*/