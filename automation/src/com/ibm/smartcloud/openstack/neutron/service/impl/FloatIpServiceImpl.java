/*package com.ibm.smartcloud.openstack.neutron.service.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.ibm.smartcloud.openstack.keystone.bean.OPSTQuotaBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTFloatIpBean;
import com.ibm.smartcloud.openstack.neutron.constants.OPSTFloatingIpConst;
import com.ibm.smartcloud.openstack.neutron.constants.OPSTNetworkConst;
import com.ibm.smartcloud.openstack.neutron.service.FloatIpService;


*//**
 * 类描述:弹性IP服务实现类
 * 类名称:OPSTFloatIpServiceImpl
 * 创建时间：2014年6月11日 上午10:20
 * @version 1.0
 *//*
@Service("floatIpService")
public class FloatIpServiceImpl extends OPSTBaseServiceImpl<Object> implements FloatIpService {
	
	String err = OPSTPropertyKeyConst.METHOD_ERROR;

	*//**
	 * 查询所有弹性Ip信息列表
	 *//*
	@Override
	public List<OPSTFloatIpBean> getFloatIpList(String tenantId, String tokenId) throws OPSTBaseException{
	
			//返回结果
			List<OPSTFloatIpBean> oPSTFloatIpBeanResultList = new ArrayList<OPSTFloatIpBean>();
			//获取参数信息
			String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			String strFloatingIpApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_FloatingIpList);
			//拼接URL
			String strFloatingIpUrl = strNetworkHost + strFloatingIpApi;
			strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_TENANTID, tenantId);
			//调用OpenStack API获取网络信息列表
			String strResponse = "";
			try {
				strResponse = HttpClientUtil.getMethod(tokenId, strFloatingIpUrl);
			} catch (OPSTBaseException e) {
				throw e;
			}
			try{	
				//解析Json结构
				JSONObject jsonObj = JSONObject.fromObject(strResponse);
				//获取返回结果中的网络信息
				JSONArray floatingIpJsonArray = jsonObj.getJSONArray(OPSTFloatingIpConst.PARAM_FLOATINGIPS);
				for(int i=0; i<floatingIpJsonArray.size(); i++){
					oPSTFloatIpBeanResultList.add(this.jsonToOPSTFloatIpBean(floatingIpJsonArray.getJSONObject(i)));
				}
			} catch (JSONException e) {
				throw new OPSTOperationException("获取getFloatIpList的时候出现处理json数据异常，类型为："+e.getMessage());
			}
			return oPSTFloatIpBeanResultList;
		
	}
	
	*//**
	 * 获取弹性IP信息
	 *//*
	@Override
	public OPSTFloatIpBean getFloatIpDetails(String tenantId, String tokenId, String id) throws OPSTBaseException{
		
		//定义返回结果
		OPSTFloatIpBean oPSTFloatIpBean = null;
		
		String strNetworkHost = "";
		String strFloatingIpApi = "";
		try {
			//获取参数信息
			strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			strFloatingIpApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_FloatingIpDetails);
		} catch (Exception e) {
			throw new OPSTOperationException("获取getFloatIpDetails的时候读取properties文件出现异常，类型为：" + e.getMessage());
		}
		
		//拼接URL
		String strFloatingIpUrl = strNetworkHost + strFloatingIpApi;
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_TENANTID, tenantId);
		if(id == "" || id == null){
			throw new OPSTOperationException();
		}
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_ID, id);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strFloatingIpUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		try{	
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTFloatingIpConst.PARAM_FLOATINGIP);
			//获取返回结果中的弹性Ip信息
			oPSTFloatIpBean = this.jsonToOPSTFloatIpBean(jsonObj);
		} catch (JSONException e) {
			throw new OPSTOperationException("获取getFloatIpDetails的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return oPSTFloatIpBean;
	}

	*//**
	 * 分配弹性IP
	 *//*
	@Override
	public String allocateFloatIp(String tenantId, String tokenId, String pool) throws OPSTBaseException{
		
		String strNetworkHost = "";
		String strFloatingIpApi = "";
		try {
			//获取参数信息
			strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			strFloatingIpApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_AllocateFloatingIp);
		} catch (Exception e) {
			throw new OPSTOperationException("获取allocateFloatIp的时候读取properties文件出现异常，类型为：" + e.getMessage());
		}
		
		//拼接URL
		String strFloatingIpUrl = strNetworkHost + strFloatingIpApi;
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_TENANTID, tenantId);
		
		if(pool == "" || pool == null){
			throw new OPSTOperationException();
		}
		JSONObject floatingIpJSONObj = new JSONObject();
		
		floatingIpJSONObj.element(OPSTFloatingIpConst.FLOATINGIP_POOL, pool);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strFloatingIpUrl, floatingIpJSONObj.toString());
			JSONObject j = JSONObject.fromObject(strResponse);
			if(!j.containsKey(OPSTFloatingIpConst.PARAM_FLOATINGIP)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}

	*//**
	 * 释放弹性IP
	 *//*
	@Override
	public String deallocateFloatIp(String tenantId, String tokenId, String id) throws OPSTBaseException{
		
		String strNetworkHost = "";
		String strFloatingIpApi = "";
		try {
			//获取参数信息
			strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			strFloatingIpApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DeallocateFloatingIp);
		} catch (Exception e) {
			throw new OPSTOperationException("获取deallocateFloatIp的时候读取properties文件出现异常，类型为：" + e.getMessage());
		}
		
		//拼接URL
		String strFloatingIpUrl = strNetworkHost + strFloatingIpApi;
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_TENANTID, tenantId);
		if(id == "" || id == null){
			throw new OPSTOperationException();
		}
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_ID, id);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strFloatingIpUrl);
			if(strResponse.length() != 0){
				JSONObject j = JSONObject.fromObject(strResponse);
				if(!j.containsKey(OPSTFloatingIpConst.PARAM_FLOATINGIP)){
					String error = StringUtil.jsonErrorToErrorMessage(strResponse);
					throw new OPSTErrorMessageException(error);
				}
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		
		return "";
	}
	
	*//**
	 * 关联虚机和弹性IP
	 * @throws Exception 
	 *//*
	@Override
	public String addFloatIpToInstance(String tenantId, String tokenId, String serverId, String address) throws OPSTBaseException{
		
		String strNetworkHost = "";
		String strFloatingIpApi = "";
		try {
			//获取参数信息
			strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			strFloatingIpApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_AddFloatingIpToInstance);
		} catch (Exception e) {
			throw new OPSTOperationException("获取addFloatIpToInstance的时候读取properties文件出现异常，类型为：" + e.getMessage());
		}
		
		//拼接URL
		String strFloatingIpUrl = strNetworkHost + strFloatingIpApi;
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_TENANTID, tenantId);
		if(serverId == "" || serverId == null){
			throw new OPSTOperationException();
		}
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_SERVERID, serverId);

		JSONObject floatingIpJSONObj = new JSONObject();
		JSONObject addFloatingIpJSONObj = new JSONObject();
		
		addFloatingIpJSONObj.element(OPSTFloatingIpConst.FLOATINGIP_ADDRESS, address);
		floatingIpJSONObj.element(OPSTFloatingIpConst.PARAM_ADDFLOATINGIP, addFloatingIpJSONObj);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strFloatingIpUrl, floatingIpJSONObj.toString());
			if(strResponse != null){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		return strResponse;
	}
	
	*//**
	 * 取消关联虚机和弹性IP
	 *//*
	@Override
	public String removeFloatIpFromInstance(String tenantId, String tokenId, String serverId, String address) throws OPSTBaseException{
		
		String strNetworkHost = "";
		String strFloatingIpApi = "";
		try {
			//获取参数信息
			strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NOVA_HOST);
			strFloatingIpApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_RemoveFloatingIpFromInstance);
		} catch (Exception e) {
			throw new OPSTOperationException("获取removeFloatIpFromInstance的时候读取properties文件出现异常，类型为：" + e.getMessage());
		}
		
		//拼接URL
		String strFloatingIpUrl = strNetworkHost + strFloatingIpApi;
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_TENANTID, tenantId);
		if(serverId == "" || serverId == null){
			throw new OPSTOperationException();
		}
		strFloatingIpUrl = strFloatingIpUrl.replace(OPSTFloatingIpConst.PARAM_SERVERID, serverId);

		JSONObject floatingIpJSONObj = new JSONObject();
		JSONObject addFloatingIpJSONObj = new JSONObject();
		
		addFloatingIpJSONObj.element(OPSTFloatingIpConst.FLOATINGIP_ADDRESS, address);
		
		floatingIpJSONObj.element(OPSTFloatingIpConst.PARAM_REMOVEFLOATINGIP, addFloatingIpJSONObj);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strFloatingIpUrl, floatingIpJSONObj.toString());
			if(strResponse != null && !strResponse.equals("")){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	
   *//**
	* 将FloatIp的json对象装换成OPSTFloatIpBean
	* @Title: jsonToOPSTFloatIpBean
	* @param jsonObject
	* @return OPSTFloatIpBean    
	* @throws
	*//*
	private OPSTFloatIpBean jsonToOPSTFloatIpBean(JSONObject jsonObject) throws OPSTBaseException{
		
		OPSTFloatIpBean f = new OPSTFloatIpBean();
		f.setId(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_ID));
		f.setIp(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_IP));
		
//		JSONObject fixed_ipJsonObj = jsonObject.getJSONObject(OPSTFloatingIpConst.FLOATINGIP_FIXED_IP);
//		if(!fixed_ipJsonObj.isNullObject()){
//			OPSTFixedIpsBean oPSTFixedIpsBean = new OPSTFixedIpsBean();
//			oPSTFixedIpsBean.setIp_address(fixed_ipJsonObj.getString(OPSTNetworkConst.FIXED_IPS_IP_ADRESS));
//			oPSTFixedIpsBean.setSubnet_id(fixed_ipJsonObj.getString(OPSTNetworkConst.FIXED_IPS_SUBNET_ID));
//			f.setFixed_ip(oPSTFixedIpsBean);
//		}else{
//			f.setFixed_ip(null);
//		}
		
//		if(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INSTANCE_ID) == "" || jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INSTANCE_ID) == null){
//			System.out.println("instance_id=" + jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INSTANCE_ID));
//			f.setInstance_id(null);
//		}else{
//			System.out.println("instance_id=" + jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INSTANCE_ID));
			f.setInstance_id(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INSTANCE_ID));
//		}
		
		f.setPool(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_POOL));
//		f.setAddress(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_ADDRESS));
//		f.setInstance_uuid(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INSTANCE_UUID));
//		f.setInterFace(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_INTERFACE));
//		f.setProject_id(jsonObject.getString(OPSTFloatingIpConst.FLOATINGIP_PROJECT_ID));
		
		return f;
	}

	*//**
	 * 获取quota信息
	 *//*
	@Override
	public OPSTQuotaBean getQuota(String tenantId, String tokenId) throws OPSTBaseException{
		
		//定义返回结果
		OPSTQuotaBean oPSTQuotaBean = new OPSTQuotaBean();
		
		String strNetworkHost = "";
		String strQuotasApi = "";
		try {
			//获取参数信息
			strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
			strQuotasApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_QuotasList);
		} catch (Exception e) {
			throw new OPSTOperationException("获取getQuota的时候读取properties文件出现异常，类型为：" + e.getMessage());
		}
		
		//拼接URL
		String strQuotasUrl = strNetworkHost + strQuotasApi;
		strQuotasUrl = strQuotasUrl.replace(OPSTNetworkConst.QUOTAS_TENANT_ID, tenantId);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strQuotasUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}

		//解析Json结构
		JSONObject jsonObj = JSONObject.fromObject(strResponse);
		//获取返回结果中的网络信息
		JSONObject quotaBody = jsonObj.getJSONObject(OPSTNetworkConst.QUOTAS_QUOTA);
		oPSTQuotaBean = this.jsonToOPSTQuotaBean(quotaBody);
		
		return oPSTQuotaBean;
	}
	
   *//**
	* 将Quota的json对象转换成OPSTQuotaBean
	* @Title: jsonToOPSTQuotaBean
	* @param jsonObject
	* @return OPSTQuotaBean    
	* @throws
	*//*
	private OPSTQuotaBean jsonToOPSTQuotaBean(JSONObject jsonObject){
		
		OPSTQuotaBean q = new OPSTQuotaBean();
		
		q.setFloatingip(jsonObject.getInt(OPSTNetworkConst.QUOTAS_FLOATINTIP));
		q.setNetwork(jsonObject.getInt(OPSTNetworkConst.QUOTAS_NETWORK));
		q.setPort(jsonObject.getInt(OPSTNetworkConst.QUOTAS_PORT));
		q.setRouter(jsonObject.getInt(OPSTNetworkConst.QUOTAS_ROUTER));
		q.setSubnet(jsonObject.getInt(OPSTNetworkConst.QUOTAS_SUBNET));
		q.setSecurity_group(jsonObject.getInt(OPSTNetworkConst.QUOTAS_SECURITY_GROUP));
		q.setSecurity_group_rule(jsonObject.getInt(OPSTNetworkConst.QUOTAS_SECURITY_GROUP_RULE));
		
		return q;
	}
}*/