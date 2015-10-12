/*package com.ibm.smartcloud.openstack.neutron.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTErrorMessageException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.service.impl.OPSTBaseServiceImpl;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.StringUtil;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTSessionPersistenceBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTHealthMonitorBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTHealthMonitorsStatusBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTMemberBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTPoolBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTPoolOfHealthMonitorBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTVIPBean;
import com.ibm.smartcloud.openstack.neutron.constants.OPSTNetworkConst;
import com.ibm.smartcloud.openstack.neutron.service.OPSTLBaasV2ExtService;

*//**
 * 类描述:负载均衡服务实现类
 * 类名称：OPSTLBaasV2ExtServiceImpl
 * @author zhanglong && hanxinxing
 * @version 1.0   创建时间：2014年7月1日 下午2:49
 *//*

@Service("lBaasV2ExtService")
public class OPSTLBaasV2ExtServiceImpl extends OPSTBaseServiceImpl<Object> implements OPSTLBaasV2ExtService{
	
	String err = OPSTPropertyKeyConst.METHOD_ERROR;

	//获取Pool列表信息
	@Override
	public List<OPSTPoolBean> getPoolList(String tenantId, String tokenId) throws OPSTBaseException{
		
		//定义返回结果
		List<OPSTPoolBean> poolList = new ArrayList<OPSTPoolBean>();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PoolList);
		
		//拼接url
		String strPoolURL = strNetworkHost + strNetworkAPI;
//		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");//此版本没有租户ID
		
		String responseStr = "";
		//调用OpenStack API获取资源池的列表信息
		try {
			responseStr = HttpClientUtil.getMethod(tokenId, strPoolURL);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		//解析json结构
		JSONObject jsonobj = JSONObject.fromObject(responseStr);
		try{
			JSONArray jsonarr = jsonobj.getJSONArray(OPSTNetworkConst.POOLBODY);
			for(int i=0; i<jsonarr.size(); i++){
				poolList.add(this.jsonToOPSTPoolBean(jsonarr.getJSONObject(i)));
			}
		}catch (JSONException e) {
			throw new OPSTOperationException("获取getPoolList的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return poolList;
	}
	
	//获取Pool详细信息
	@Override
	public OPSTPoolBean getPoolDetails(String tenantId, String tokenId, String poolId) throws OPSTBaseException{
		
		//定义返回结果
		OPSTPoolBean poolResponse = new OPSTPoolBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PoolDetails);
		
		//拼接url
		String strPoolURL = strNetworkHost + strNetworkAPI;
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.POOL_POOL_ID, poolId);
		
		String responseStr = "";
		try {
			responseStr = HttpClientUtil.getMethod(tokenId, strPoolURL);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try{
			JSONObject jsonPool = JSONObject.fromObject(responseStr).getJSONObject(OPSTNetworkConst.POOL);
			poolResponse = this.jsonToOPSTPoolBean(jsonPool);
		}catch (JSONException e) {
			throw new OPSTOperationException("获取getPoolDetails的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return poolResponse;
	}
	
	//创建Pool
	@Override
	public OPSTPoolBean createPool(String tenantId, String tokenId, OPSTPoolBean pool) throws OPSTBaseException{
		
		//定义返回结果
		OPSTPoolBean poolResponse = new OPSTPoolBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CreatePool);
		
		//拼接url
		String strPoolURL = strNetworkHost + strNetworkAPI;
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		
		//拼接请求的json结构
//				{
//				    "pool": {
//				        "subnet_id": "8032909d-47a1-4715-90af-5153ffe39861",
//				        "lb_method": "ROUND_ROBIN",
//				        "protocol": "TCP",
//				        "name": "NewPool",
//				        "admin_state_up": true
//				    }
//				}
		JSONObject jsonPoolBoby = new JSONObject();
		JSONObject jsonPool = new JSONObject();
		jsonPoolBoby.element(OPSTNetworkConst.POOL_ADMIN_STATE_UP, pool.isAdmin_state_up());
		if("" != pool.getName()){
			jsonPoolBoby.element(OPSTNetworkConst.POOL_NAME, pool.getName());
		}
		if("" != pool.getDescription()){
			jsonPoolBoby.element(OPSTNetworkConst.POOL_DESCRIPTION, pool.getDescription());
		}
		if("" != pool.getProtocol()){
			jsonPoolBoby.element(OPSTNetworkConst.POOL_PROTOCOL, pool.getProtocol());
		}
		if("" != pool.getLb_method()){
			jsonPoolBoby.element(OPSTNetworkConst.POOL_LB_METHOD, pool.getLb_method());
		}
		if("" != pool.getSubnet_id()){
			jsonPoolBoby.element(OPSTNetworkConst.POOL_SUBNET_ID, pool.getSubnet_id());
		}
		if("" != pool.getProvider()){
			jsonPoolBoby.element(OPSTNetworkConst.POOL_PROVIDER, pool.getProvider());
		}
		jsonPool.element(OPSTNetworkConst.POOL, jsonPoolBoby);
		
		String strResponse = "";
		JSONObject j = new JSONObject();
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strPoolURL, jsonPool.toString());
			j = JSONObject.fromObject(strResponse);
			if(!j.containsKey(OPSTNetworkConst.POOL)){
				String error =  StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		//解析json结构
		try{
			JSONObject jobj = j.getJSONObject(OPSTNetworkConst.POOL);
			poolResponse = this.jsonToOPSTPoolBean(jobj);
		}catch (JSONException e) {
			throw new OPSTOperationException("获取createPool的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return poolResponse;
	}
	
	//更新Pool
	@Override
	public OPSTPoolBean updatePool(String tenantId, String tokenId, OPSTPoolBean pool) throws OPSTBaseException{
		
		//定义返回结果
		OPSTPoolBean poolResponse = new OPSTPoolBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UpdatePool);
		
		//拼接url
		String strPoolURL = strNetworkHost + strNetworkAPI;
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.POOL_POOL_ID, pool.getId());
		
		//拼接请求的json结构
//			{
//			    "pool": {
//			        "name": "NewPool",
//			    }
//			}
		JSONObject jsonPoolBoby = new JSONObject();
		JSONObject jsonPool = new JSONObject();
		jsonPoolBoby.element(OPSTNetworkConst.POOL_NAME, pool.getName());
		jsonPoolBoby.element(OPSTNetworkConst.POOL_DESCRIPTION, pool.getDescription());
		jsonPool.element(OPSTNetworkConst.POOL, jsonPoolBoby);
		
		String strResponse = "";
		JSONObject j = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strPoolURL, jsonPool.toString());
			j = JSONObject.fromObject(strResponse);
			if(!j.containsKey(OPSTNetworkConst.POOL)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		//解析json结构
		try{
			JSONObject jsonObj = j.getJSONObject(OPSTNetworkConst.POOL);
			poolResponse = this.jsonToOPSTPoolBean(jsonObj);
		}catch (JSONException e) {
			throw new OPSTOperationException("获取updatePool的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return poolResponse;
	}
	
	//删除Pool
	@Override
	public String deletePool(String tenantId, String tokenId, String poolId) throws OPSTBaseException{
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DeletePool);
		
		//拼接删除Pool API的URL
		String strPoolURL = strNetworkHost + strNetworkAPI;
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.POOL_POOL_ID, poolId);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strPoolURL);
			if(strResponse != null && !strResponse.equals("")){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return "";
	}
	
	//关联健康监控到资源池
	@Override
	public String AssociatesHealthMonitorToPool(String tenantId, String tokenId, String poolId, String healthMonitorId) throws OPSTBaseException{
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_AssociatesHealthMonitorToPool);
		
		//拼接url
		String strPoolURL = strNetworkHost + strNetworkAPI;
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.POOL_POOL_ID, poolId);
		
		//拼接请求的json结构
//			{
//			    "health_monitor": {
//			        "id": "b624decf-d5d3-4c66-9a3d-f047e7786181"
//			    }
//			}
		JSONObject jsonHealthMonitorBoby = new JSONObject();
		JSONObject jsonHealthMonitor = new JSONObject();
		jsonHealthMonitor.element(OPSTNetworkConst.HEALTHMONITOR_ID, healthMonitorId);
		jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR, jsonHealthMonitor);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strPoolURL, jsonHealthMonitorBoby.toString());
			JSONObject j = JSONObject.fromObject(strResponse);
			if(!j.containsKey(OPSTNetworkConst.HEALTHMONITOR)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return "";
	}
	
	//从资源池取消健康监控关联
	@Override
	public String DisassociatesHealthMoniterFromPool(String tenantId, String tokenId, String poolId, String healthMonitorId) throws OPSTBaseException{
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DisassociatesHealthMoniterFromPool);
		
		//拼接url
		String strPoolURL = strNetworkHost + strNetworkAPI;
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.POOL_POOL_ID, poolId);
		strPoolURL = strPoolURL.replace(OPSTNetworkConst.POOL_HEALTH_MONITOR_ID, healthMonitorId);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strPoolURL);
			if(strResponse != null){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return "";
	}
	
	//获取HealthMonitor列表信息
	@Override
	public List<OPSTHealthMonitorBean> getHealthMonitorList(String tenantId, String tokenId) throws OPSTBaseException{

		//定义返回结果
		List<OPSTHealthMonitorBean> healthMonitorList = new ArrayList<OPSTHealthMonitorBean>();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_HealthMonitorList);
		
		//拼接url
		String strHealthMonitorURL = strNetworkHost + strNetworkAPI;
//		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");//此版本没有租户ID
		System.out.println(strHealthMonitorURL);
		
		String responseStr = "";
		//调用OpenStack API获取资源池的列表信息
		try {
			responseStr = HttpClientUtil.getMethod(tokenId, strHealthMonitorURL);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		//解析json结构
		JSONObject jsonobj = JSONObject.fromObject(responseStr);
		try{
			JSONArray jsonarr = jsonobj.getJSONArray(OPSTNetworkConst.HEALTHMONITORBODY);
			for(int i=0; i<jsonarr.size(); i++){
				healthMonitorList.add(this.jsonToOPSTHealthMonitorBean(jsonarr.getJSONObject(i)));
			}
		}catch (JSONException e) {
			throw new OPSTOperationException("获取getHealthMonitorList的时候出现处理json数据异常，类型为："+e.getMessage());
		}	
		
		return healthMonitorList;
	}
	
	//获取HealthMonitor详细信息
	@Override
	public OPSTHealthMonitorBean getHealthMonitorDetails(String tenantId, String tokenId, String healthMoniterId) throws OPSTBaseException{

		//定义返回结果
		OPSTHealthMonitorBean healthMoniterResponse = new OPSTHealthMonitorBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_HealthMonitorDetails);
		
		//拼接url
		String strHealthMonitorURL = strNetworkHost + strNetworkAPI;
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.HEALTHMONITOR_HEALTH_MONITOR_ID, healthMoniterId);
		
		String responseStr = "";
		try {
			responseStr = HttpClientUtil.getMethod(tokenId, strHealthMonitorURL);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try{
			JSONObject jsonobj = JSONObject.fromObject(responseStr).getJSONObject(OPSTNetworkConst.HEALTHMONITOR);
			healthMoniterResponse = this.jsonToOPSTHealthMonitorBean(jsonobj);
		}catch (JSONException e) {
			throw new OPSTOperationException("获取getHealthMonitorDetails的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return healthMoniterResponse;
	}

	//创建HealthMonitor
	@Override
	public OPSTHealthMonitorBean createHealthMonitor(String tenantId, String tokenId, OPSTHealthMonitorBean healthMoniter) throws OPSTBaseException{

		//定义返回结果
		OPSTHealthMonitorBean healthMoniterResponse = new OPSTHealthMonitorBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用HealthMonitor信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CreateHealthMonitor);
		
		//拼接url
		String strHealthMonitorURL = strNetworkHost + strNetworkAPI;
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		
		//拼接请求的json结构
//			{
//			    "health_monitor": {
//			        "delay": "1",
//			        "max_retries": "1",
//			        "type": "HTTP",
//			        "timeout": "1",
//			        "admin_state_up": true
//			    }
//			}
		JSONObject jsonHealthMonitorBoby = new JSONObject();
		JSONObject jsonHealthMonitor = new JSONObject();
		jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_ADMIN_STATE_UP, healthMoniter.isAdmin_state_up());
		if("" != healthMoniter.getTimeout()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_TIMEOUT, healthMoniter.getTimeout());
		}
		if("" != healthMoniter.getType()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_TYPE, healthMoniter.getType());
		}
		if("" != healthMoniter.getMax_retries()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_MAX_RETRIES, healthMoniter.getMax_retries());
		}
		if("" != healthMoniter.getDelay()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_DELAY, healthMoniter.getDelay());
		}
		if("" != healthMoniter.getExpected_codes()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_EXPECTED_CODES, healthMoniter.getExpected_codes());
		}
		if("" != healthMoniter.getHttp_method()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_HTTP_METHOD, healthMoniter.getHttp_method());
		}
		if("" != healthMoniter.getUrl_path()){
			jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_URL_PATH, healthMoniter.getUrl_path());
		}
		
		jsonHealthMonitor.element(OPSTNetworkConst.HEALTHMONITOR, jsonHealthMonitorBoby);
		
		String strResponse = "";
		JSONObject j = new JSONObject();
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strHealthMonitorURL, jsonHealthMonitor.toString());
			j = JSONObject.fromObject(strResponse);
			if(j.containsKey("NeutronError")){
				String error = j.getString("NeutronError");
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		//解析json结构
		try{
			JSONObject jobj = j.getJSONObject(OPSTNetworkConst.HEALTHMONITOR);
			healthMoniterResponse = this.jsonToOPSTHealthMonitorBean(jobj);
		}catch (JSONException e) {
			throw new OPSTOperationException("获取createPool的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return healthMoniterResponse;
	}
	
	//更新HealthMonitor
	@Override
	public OPSTHealthMonitorBean updateHealthMonitor(String tenantId, String tokenId, OPSTHealthMonitorBean healthMoniter) throws OPSTBaseException{

		//定义返回结果
		OPSTHealthMonitorBean healthMoniterResponse = new OPSTHealthMonitorBean();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UpdateHealthMonitor);
		
		//拼接url
		String strHealthMonitorURL = strNetworkHost + strNetworkAPI;
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.HEALTHMONITOR_HEALTH_MONITOR_ID, healthMoniter.getId());
		
		//拼接请求的json结构
//				{
//				    "health_monitor": {
//				        "delay": "1",
//				        "max_retries": "1",
//				        "timeout": "1",
//				    }
//				}
		JSONObject jsonHealthMonitorBoby = new JSONObject();
		JSONObject jsonHealthMonitor = new JSONObject();
		jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_TIMEOUT, healthMoniter.getTimeout());
		jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_MAX_RETRIES, healthMoniter.getMax_retries());
		jsonHealthMonitorBoby.element(OPSTNetworkConst.HEALTHMONITOR_DELAY, healthMoniter.getDelay());
		jsonHealthMonitor.element(OPSTNetworkConst.HEALTHMONITOR, jsonHealthMonitorBoby);
		
		String strResponse = "";
		JSONObject j = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strHealthMonitorURL, jsonHealthMonitor.toString());
			j = JSONObject.fromObject(strResponse);
			if(!j.containsKey(OPSTNetworkConst.HEALTHMONITOR)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		//解析json
		try{
			JSONObject jsonObj = j.getJSONObject(OPSTNetworkConst.HEALTHMONITOR);
			healthMoniterResponse = this.jsonToOPSTHealthMonitorBean(jsonObj);
		}catch (JSONException e) {
			throw new OPSTOperationException("获取updateHealthMonitor的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return healthMoniterResponse;
	}
	
	//删除HealthMonitor
	@Override
	public String deleteHealthMonitor(String tenantId, String tokenId, String healthMoniterId) throws OPSTBaseException{

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用网络信息列表的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DeleteHealthMonitor);
		
		//拼接url
		String strHealthMonitorURL = strNetworkHost + strNetworkAPI;
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		strHealthMonitorURL = strHealthMonitorURL.replace(OPSTNetworkConst.HEALTHMONITOR_HEALTH_MONITOR_ID, healthMoniterId);
		
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strHealthMonitorURL);
			if(strResponse != null && !strResponse.equals("")){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return "";
	}
		
	//获取VIP列表信息
	@Override
	public List<OPSTVIPBean> getVipList(String tenantId, String tokenId) throws OPSTBaseException {

		//定义返回结果
		List<OPSTVIPBean> vipList = new ArrayList<OPSTVIPBean>();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		
		//获取调用VIP信息列表的API调用命令
		String strVipAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VIP_LIST);
		
		//拼接URL
		String strOrgUrl = strNetworkHost + strVipAPI;
		//String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		System.out.println(strDestUrl);
		
		//调用OpenStack API获取VIP信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析JSON结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的VIP信息
			JSONArray vipsJsonArray = jsonObj.getJSONArray(OPSTNetworkConst.VIPBODY);
			for (int i = 0; i < vipsJsonArray.size(); i++) {
				vipList.add(this.jsonObjToVipBean(vipsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取VIP列表信息(getVIPList)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return vipList;
	}
	
	*//**
	 * 根据VIP ID查询指定VIP的详细信息
	 * 
	 * @param argVipId
	 * @return OPSTVIPBean 指定VIP的详细信息
	 *//*
	@Override
	public OPSTVIPBean getVipById(String tenantId, String tokenId, String argVipId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTVIPBean vipBean = null;
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用指定VIP信息的API调用命令
		String strVipAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_VIPBYID);
		
		//创建调用VIP信息列表API的URL
		String strOrgUrl = strNetworkHost + strVipAPI;
//			String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		String strDestUrl2 = strDestUrl.replace(OPSTNetworkConst.PARAM_VIPID, argVipId);
		
		//调用OpenStack API获取指定VIP信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.VIP);
			//获取返回结果中的VIP信息
			vipBean = this.jsonObjToVipBean(jsonObj);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取VIP详细信息(getVIPDetails)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return vipBean;
	}
	
	*//**
	 * 创建VIP信息
	 * 
	 * @param argTenantId 项目ID(仅限 Admin-only, 可选)
	 * @param argName VIP名称(可选)
	 * @param argDescription VIP描述(可选)
	 * @param argAdress VIP的IP地址(可选)
	 * @param argProtocol VIP地址协议
	 * @param argProtocol_port VIP地址的协议端口
	 * @param argSession_persistence   VIP会话持久化
	 * @param argCookie Cookie名称
	 * @param argConnection_limit 连接限制
	 *//*
	@Override
	public OPSTVIPBean createVip(String tenantId, String tokenId, OPSTVIPBean argOPSTVIPBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTVIPBean vipBean = new OPSTVIPBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用创建VIP信息的API调用命令
		String strVipAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATEVIP);
		
		//调用创建VIP信息列表API的URL
		String strUrl = strNetworkHost + strVipAPI;
//			String strDestUrl = strUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestUrl = strUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
		    "vip": {
		        "protocol": "HTTP",
		        "name": "NewVip",
		        "admin_state_up": true,
		        "subnet_id": "8032909d-47a1-4715-90af-5153ffe39861",
		        "pool_id": "61b1f87a-7a21-4ad3-9dda-7f81d249944f",
		        "protocol_port": "80"
		    }
		}
		
		JSONObject vipRequestBody = new JSONObject();
		JSONObject vipJSONObj = new JSONObject();
		
		if (argOPSTVIPBean.getName() != null) {
			//构建样例"name": "NewVip"
			vipJSONObj.element(OPSTNetworkConst.VIP_NAME, argOPSTVIPBean.getName());
		}
		
		if (argOPSTVIPBean.getDescription() != null) {
			//构建样例"description": ""
			vipJSONObj.element(OPSTNetworkConst.VIP_DESCRIPTION, argOPSTVIPBean.getDescription());
		}
		
		if (argOPSTVIPBean.getSubnet_id() != null) {
			vipJSONObj.element(OPSTNetworkConst.VIP_SUBNET_ID, argOPSTVIPBean.getSubnet_id());
		}
		
		if (argOPSTVIPBean.getPool_id() != null) {
			vipJSONObj.element(OPSTNetworkConst.VIP_POOL_ID, argOPSTVIPBean.getPool_id());
		}
		
		if (argOPSTVIPBean.isAdmin_state_up()) {
			vipJSONObj.element(OPSTNetworkConst.VIP_ADMIN_STATE_UP, argOPSTVIPBean.isAdmin_state_up());
		} else {
			vipJSONObj.element(OPSTNetworkConst.VIP_ADMIN_STATE_UP, false);
		}
//			if (argOPSTVIPBean.getAddress() != "") {
//				//构建样例"address": "10.0.0.11"
//				vipJSONObj.element(OPSTNetworkConst.VIP_ADDRESS, argOPSTVIPBean.getAddress());
//			}
		
		if (argOPSTVIPBean.getProtocol() != null) {
			//构建样例"protocol": "HTTP"
			vipJSONObj.element(OPSTNetworkConst.VIP_PROTOCOL, argOPSTVIPBean.getProtocol());
		}
		
		if (argOPSTVIPBean.getProtocol_port() != null) {
			//构建样例"protocol_port": "80"
			vipJSONObj.element(OPSTNetworkConst.VIP_PROTOCOL_PORT, argOPSTVIPBean.getProtocol_port());
		}
		
		if (argOPSTVIPBean.getSession_persistence() != null) {
			//构建样例
			"session_persistence": {
	            "cookie_name": "MyAppCookie",
	            "type": "APP_COOKIE"
        	}
			vipJSONObj.element(OPSTNetworkConst.VIP_SESSION_PERSISTENCE, argOPSTVIPBean.getSession_persistence());
		}
		
		if (argOPSTVIPBean.getConnection_limit()!= null) {
			//构建样例"protocol_port": "80"
			vipJSONObj.element(OPSTNetworkConst.VIP_CONNECTION_LIMIT, argOPSTVIPBean.getConnection_limit());
		}
		
		//构建样例{"network":{}}
		vipRequestBody.element(OPSTNetworkConst.VIP, vipJSONObj);

		//调用OpenStack API创建网络
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl, vipRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.VIP)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的网络信息
			JSONObject vipResponse= jsonObj.getJSONObject(OPSTNetworkConst.VIP);
			
			vipBean = this.jsonObjToVipBean(vipResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建VIP信息(createVip)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return vipBean;
	}
	
	*//**
	 * 根据Vip ID编辑VIP
	 * 
	 * @param argVipId VIP ID
	 *//*
	@Override
	public OPSTVIPBean updateVipById(String tenantId, String tokenId, OPSTVIPBean argOPSTVIPBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTVIPBean vipBean = new OPSTVIPBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用更新VIP信息的API调用命令
		String strVipAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATEVIP);
		
		//获取更新VIP信息列表API的URL​​​
		String strOrgUrl = strNetworkHost + strVipAPI;
//		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		String strDestUrl2 = strDestUrl.replace(OPSTNetworkConst.PARAM_VIPID, argOPSTVIPBean.getId());
		
		//拼接请求的json结构
//				{
//				    "vip": {
//				        "connection_limit": "1000"
//				    }
//				}
		
		JSONObject vipRequestBody = new JSONObject();
		JSONObject vipJSONObj = new JSONObject();

//		vipJSONObj.element(OPSTNetworkConst.VIP_TENANT_ID, argOPSTVIPBean.getTenant_id());
		vipJSONObj.element(OPSTNetworkConst.VIP_NAME, argOPSTVIPBean.getName());
		vipJSONObj.element(OPSTNetworkConst.VIP_DESCRIPTION, argOPSTVIPBean.getDescription());
		vipJSONObj.element(OPSTNetworkConst.VIP_POOL_ID, argOPSTVIPBean.getPool_id());
		vipJSONObj.element(OPSTNetworkConst.VIP_ADMIN_STATE_UP, argOPSTVIPBean.isAdmin_state_up());
		vipJSONObj.element(OPSTNetworkConst.VIP_SESSION_PERSISTENCE, argOPSTVIPBean.getSession_persistence());
		vipJSONObj.element(OPSTNetworkConst.VIP_CONNECTION_LIMIT, argOPSTVIPBean.getConnection_limit());
		
		vipRequestBody.element(OPSTNetworkConst.VIP, vipJSONObj);

		//调用OpenStack API创建VIP
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl2, vipRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if(!jsonObj.containsKey(OPSTNetworkConst.VIP)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的VIP信息
			JSONObject vipResponse= jsonObj.getJSONObject(OPSTNetworkConst.VIP);
			
			vipBean = this.jsonObjToVipBean(vipResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取更新VIP信息(updateVipById)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return vipBean;
	}
	
	*//**
	 * 根据VIP ID删除VIP
	 * 
	 * @param argVipId
	 *//*
	@Override
	public String deleteVipByIdV2(String tenantId, String tokenId, String argVipId) throws OPSTBaseException {
		
		//输入参数检查
		if(argVipId == null){
			throw new OPSTBaseException(argVipId);
		}
				
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取删除VIP信息的API调用命令
		String strVipAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_VIPBYID);
		
		//创建删除VIP API的URL​​​
		String strOrgUrl = strNetworkHost + strVipAPI;
//			String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		String strDestUrl2 = strDestUrl.replace(OPSTNetworkConst.PARAM_VIPID, argVipId);
		
		//调用OpenStack API删除指定的VIP信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestUrl2);
			if (strResponse != null) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}
	
	//获取Member列表信息
	@Override
	public List<OPSTMemberBean> getMemberList(String tenantId, String tokenId) throws OPSTBaseException {
		
		//定义返回结果
		List<OPSTMemberBean> memberList = new ArrayList<OPSTMemberBean>();
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用Member信息列表的API调用命令
		String strMemberAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_MEMBER_LIST);
		
		//拼接url
		String strMemberURL = strNetworkHost + strMemberAPI;
		//strMemberURL = strMemberURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		strMemberURL = strMemberURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		System.out.println(strMemberURL);
		
		String responseStr = "";
		//调用OpenStack API获取资源池的列表信息
		try {
			responseStr = HttpClientUtil.getMethod(tokenId, strMemberURL);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析json结构
			JSONObject jsonobj = JSONObject.fromObject(responseStr);
			JSONArray membersJsonArray = jsonobj.getJSONArray(OPSTNetworkConst.MEMBERBODY);
			for(int i=0; i<membersJsonArray.size(); i++){
				memberList.add(this.jsonToOPSTMemberBean(membersJsonArray.getJSONObject(i)));
			}
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取Member列表信息(getMemberList)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return memberList;
	}

	*//**
	 * 根据Member ID查询指定Member的详细信息
	 * 
	 * @param argMemerId
	 * @return OPSTMemberBean 指定Member的详细信息
	 *//*
	@Override
	public OPSTMemberBean getMemberById(String tenantId, String tokenId, String argMemberId) throws OPSTBaseException {
		
		//定义返回结果
		OPSTMemberBean memberBean = null;
		
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用指定Member信息的API调用命令
		String strMemberAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_MEMBERBYID);
		
		//创建调用Member信息列表API的URL(V2)
		String strOrgUrl = strNetworkHost + strMemberAPI;
//			String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestUrl = strOrgUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		String strDestUrl2 = strDestUrl.replace(OPSTNetworkConst.PARAM_MEMBERID, argMemberId);
		
		//调用OpenStack API获取指定Member信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl2);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTNetworkConst.MEMBER);
			//获取返回结果中的Member信息
			memberBean = this.jsonToOPSTMemberBean(jsonObj);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取成员详细信息(getMemberDetails)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
		
		return memberBean;
	}
	
	//创建Member
	@Override
	public OPSTMemberBean createMember(String tenantId, String tokenId, OPSTMemberBean argOPSTMemberBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTMemberBean memberBean = new OPSTMemberBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用创建Member信息的API调用命令
		String strMemberAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CreateMember);
		
		//调用创建Member信息列表API的URL
		String strUrl = strNetworkHost + strMemberAPI;
//			String strDestUrl = strUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestUrl = strUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
		    "member": {
		        "protocol_port": "8080",
		        "address": "10.0.0.5",
		        "pool_id": "7803631d-f181-4500-b3a2-1b68ba2a75fd",
		        "admin_state_up": true
		    }
		}
		
		JSONObject memberRequestBody = new JSONObject();
		JSONObject memberJSONObj = new JSONObject();
		
//		memberJSONObj.element(OPSTNetworkConst.MEMBER_TENANT_ID, argOPSTMemberBean.getTenant_id());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_POOL_ID, argOPSTMemberBean.getPool_id());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_ADDRESS, argOPSTMemberBean.getAddress());
//		memberJSONObj.element(OPSTNetworkConst.MEMBER_PROTOCOL, argOPSTMemberBean.getProtocol());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_WEIGHT, argOPSTMemberBean.getWeight());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_PROTOCOL_PORT, argOPSTMemberBean.getProtocol_port());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_ADMIN_STATE_UP, argOPSTMemberBean.isAdmin_state_up());
		
		memberRequestBody.element(OPSTNetworkConst.MEMBER, memberJSONObj);

		//调用OpenStack API创建Member
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strDestUrl, memberRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.MEMBER)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		try {
			//获取返回结果中的网络信息
			JSONObject memberResponse= jsonObj.getJSONObject(OPSTNetworkConst.MEMBER);
			memberBean = this.jsonToOPSTMemberBean(memberResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取创建成员信息(createMember)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return memberBean;
	}
	
	@Override
	public OPSTMemberBean updateMemberById(String tenantId, String tokenId, OPSTMemberBean argOPSTMemberBean) throws OPSTBaseException {
		
		//定义返回结果
		OPSTMemberBean memberBean = new OPSTMemberBean();

		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取调用创建Member信息的API调用命令
		String strMemberAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UpdateMember);
	    
		//调用更新Member信息列表API的URL
		String strUrl = strNetworkHost + strMemberAPI;
//		String strDestUrl = strUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		String strDestUrl = strUrl.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		String strDestUrl2 = strDestUrl.replace(OPSTNetworkConst.PARAM_MEMBERID, argOPSTMemberBean.getId());
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
		    "member": {
		        "admin_state_up": false
		    }
		}
		
		JSONObject memberRequestBody = new JSONObject();
		JSONObject memberJSONObj = new JSONObject();
		
//		memberJSONObj.element(OPSTNetworkConst.MEMBER_TENANT_ID, argOPSTMemberBean.getTenant_id());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_POOL_ID, argOPSTMemberBean.getPool_id());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_WEIGHT, argOPSTMemberBean.getWeight());
		memberJSONObj.element(OPSTNetworkConst.MEMBER_ADMIN_STATE_UP, argOPSTMemberBean.isAdmin_state_up());
		
		memberRequestBody.element(OPSTNetworkConst.MEMBER, memberJSONObj);

		//调用OpenStack API创建Member
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strDestUrl2, memberRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if (!jsonObj.containsKey(OPSTNetworkConst.MEMBER)) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//获取返回结果中的成员信息
			JSONObject memberResponse= jsonObj.getJSONObject(OPSTNetworkConst.MEMBER);
			memberBean = this.jsonToOPSTMemberBean(memberResponse);
		} catch (JSONException e1) {
			throw new OPSTOperationException("获取更新成员信息(updateMemberById)的时候出现解析json数据异常，类型为："+e1.getMessage());
		}

		return memberBean;
	}
	
	@Override
	public String deleteMemberByIdV2(String tenantId, String tokenId, String argMemberId) throws OPSTBaseException {
		
		//输入参数检查
		if(argMemberId == null){
			throw new OPSTBaseException(argMemberId);
		}
				
		//获取Networking服务的主机地址(Neutron)
		String strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		//获取删除Member信息的API调用命令
		String strNetworkAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_MEMBERBYID);
		
		//拼接删除Member API的URL
		String strOrgURL = strNetworkHost + strNetworkAPI;
//			String strDestURL = strOrgURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, tenantId);
		//此版本无TenantId
		String strDestURL = strOrgURL.replace(OPSTNetworkConst.PARAM_TENANT_ID, "");
		String strDestURL2 = strDestURL.replace(OPSTNetworkConst.PARAM_MEMBERID, argMemberId);
		
		//调用OpenStack API删除指定的Member信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strDestURL2);
			if (strResponse != null && !strResponse.equals("")) {
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		return strResponse;
	}
	
	//json to OPSTPoolBean
	@SuppressWarnings({ "unchecked", "static-access" })
	private OPSTPoolBean jsonToOPSTPoolBean(JSONObject jsonobj){
		
		OPSTPoolBean p = new OPSTPoolBean();
		p.setAdmin_state_up(jsonobj.getBoolean(OPSTNetworkConst.POOL_ADMIN_STATE_UP));
		p.setDescription(jsonobj.getString(OPSTNetworkConst.POOL_DESCRIPTION));
		
		JSONArray jahm = jsonobj.getJSONArray(OPSTNetworkConst.POOL_HEALTH_MONITORS);
		List<String> hmList =  JSONArray.toList(jahm);
		p.setHealth_monitors(hmList);
		
		JSONArray jahms = jsonobj.getJSONArray(OPSTNetworkConst.POOL_HEALTH_MONITORS_STATUS);
		List<OPSTHealthMonitorsStatusBean> hmsList = new ArrayList<OPSTHealthMonitorsStatusBean>();
		for(int i=0; i<jahms.size(); i++){
			JSONObject hms = jahms.getJSONObject(i);
			OPSTHealthMonitorsStatusBean hmsb = this.jsonToOPSTHealthMonitorsStatusBean(hms);
			hmsList.add(hmsb);
		}
		p.setHealth_monitors_status(hmsList);
		
		p.setId(jsonobj.getString(OPSTNetworkConst.POOL_ID));
		p.setLb_method(jsonobj.getString(OPSTNetworkConst.POOL_LB_METHOD));
		
		JSONArray jam = jsonobj.getJSONArray(OPSTNetworkConst.POOL_MEMBERS);
		List<String> mList = jam.toList(jam);
		p.setMembers(mList);
		
		p.setName(jsonobj.getString(OPSTNetworkConst.POOL_NAME));
		p.setProtocol(jsonobj.getString(OPSTNetworkConst.POOL_PROTOCOL));
		p.setProvider(jsonobj.getString(OPSTNetworkConst.POOL_PROVIDER));
		p.setStatus(jsonobj.getString(OPSTNetworkConst.POOL_STATUS));
		p.setStatus_description(jsonobj.getString(OPSTNetworkConst.POOL_STATUS_DESCRIPTION));
		p.setSubnet_id(jsonobj.getString(OPSTNetworkConst.POOL_SUBNET_ID));
		p.setTenant_id(jsonobj.getString(OPSTNetworkConst.POOL_TENANT_ID));
		p.setVip_id(jsonobj.getString(OPSTNetworkConst.POOL_VIP_ID));
		
		return p;
	}
	
	//json to OPSTHealthMonitorBean
	private OPSTHealthMonitorBean jsonToOPSTHealthMonitorBean(JSONObject jsonobj){
		
		OPSTHealthMonitorBean hm = new OPSTHealthMonitorBean();
		hm.setAdmin_state_up(Boolean.parseBoolean(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_ADMIN_STATE_UP)));
		hm.setDelay(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_DELAY));
		hm.setExpected_codes(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_EXPECTED_CODES));
		hm.setHttp_method(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_HTTP_METHOD));
		hm.setId(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_ID));
		hm.setMax_retries(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_MAX_RETRIES));
		JSONArray japools = jsonobj.getJSONArray(OPSTNetworkConst.HEALTHMONITOR_POOLS);
		List<OPSTPoolOfHealthMonitorBean> pools =  new ArrayList<OPSTPoolOfHealthMonitorBean>();
		for(int i=0; i<japools.size(); i++){
			OPSTPoolOfHealthMonitorBean p = this.jsonToOPSTPoolOfHealthMonitorBean(japools.getJSONObject(i));
			pools.add(p);
		}
		hm.setPools(pools);
		hm.setTenant_id(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_TENANT_ID));
		hm.setTimeout(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_TIMEOUT));
		hm.setType(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_TYPE));
		hm.setUrl_path(jsonobj.getString(OPSTNetworkConst.HEALTHMONITOR_URL_PATH));
		
		return hm;
	}
	
	//json to OPSTHealthMonitorsStatusBean
	private OPSTHealthMonitorsStatusBean jsonToOPSTHealthMonitorsStatusBean(JSONObject jsonObj){
		
		OPSTHealthMonitorsStatusBean hmsb = new OPSTHealthMonitorsStatusBean();
		hmsb.setMonitor_id(jsonObj.getString(OPSTNetworkConst.HealthMonitorsStatus_MONITOR_ID));
		hmsb.setStatus(jsonObj.getString(OPSTNetworkConst.HealthMonitorsStatus_STATUS));
		hmsb.setStatus_description(jsonObj.getString(OPSTNetworkConst.HealthMonitorsStatus_STATUS_DESCRIPTION));
		return hmsb;
	}
	
	//json to OPSTPoolOfHealthMonitorBean
	private OPSTPoolOfHealthMonitorBean jsonToOPSTPoolOfHealthMonitorBean(JSONObject jsonObj){
		
		OPSTPoolOfHealthMonitorBean pohmb = new OPSTPoolOfHealthMonitorBean();
		pohmb.setPool_id(jsonObj.getString(OPSTNetworkConst.PoolOfHealthMonitor_POOL_ID));
		pohmb.setStatus(jsonObj.getString(OPSTNetworkConst.PoolOfHealthMonitor_STATUS));
		pohmb.setStatus_description(jsonObj.getString(OPSTNetworkConst.PoolOfHealthMonitor_STATUS_DESCRIPTION));
		return pohmb;
	}
	
	//Json to OPSTVIPBean
	private OPSTVIPBean jsonObjToVipBean(JSONObject argVip){
		
		OPSTVIPBean vip = new OPSTVIPBean();
		
		if (argVip.containsKey(OPSTNetworkConst.VIP_ID)) {
			vip.setId(argVip.getString(OPSTNetworkConst.VIP_ID));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_TENANT_ID)) {
			vip.setTenant_id(argVip.getString(OPSTNetworkConst.VIP_TENANT_ID));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_NAME)) {
			vip.setName(argVip.getString(OPSTNetworkConst.VIP_NAME));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_DESCRIPTION)) {
			vip.setDescription(argVip.getString(OPSTNetworkConst.VIP_DESCRIPTION));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_SUBNET_ID)) {
			vip.setSubnet_id(argVip.getString(OPSTNetworkConst.VIP_SUBNET_ID));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_ADDRESS)) {
			vip.setAddress(argVip.getString(OPSTNetworkConst.VIP_ADDRESS));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_STATUS_DESCRIPTION)) {
			vip.setStatus_description(argVip.getString(OPSTNetworkConst.VIP_STATUS_DESCRIPTION));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_PROTOCOL)) {
			vip.setProtocol(argVip.getString(OPSTNetworkConst.VIP_PROTOCOL));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_PROTOCOL_PORT)) {
			vip.setProtocol_port(Integer.parseInt(argVip.getString(OPSTNetworkConst.VIP_PROTOCOL_PORT)));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_POOL_ID)) {
			vip.setPool_id(argVip.getString(OPSTNetworkConst.VIP_POOL_ID));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_PORT_ID)) {
			vip.setPort_id(argVip.getString(OPSTNetworkConst.VIP_PORT_ID));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_SESSION_PERSISTENCE)) {
			JSONObject sessionPersistenceJsonObj = argVip.getJSONObject(OPSTNetworkConst.VIP_SESSION_PERSISTENCE);
			if(!(sessionPersistenceJsonObj.isNullObject())){
				OPSTSessionPersistenceBean spBean = new OPSTSessionPersistenceBean();
				spBean.setCookie_name(sessionPersistenceJsonObj.getString(OPSTNetworkConst.COOKIE_NAME));
				spBean.setType(sessionPersistenceJsonObj.getString(OPSTNetworkConst.TYPE));
				vip.setSession_persistence(spBean);
			}else{
				vip.setSession_persistence(null);
			}
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_CONNECTION_LIMIT)) {
			vip.setConnection_limit(Integer.parseInt(argVip.getString(OPSTNetworkConst.VIP_CONNECTION_LIMIT)));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_ADMIN_STATE_UP)) {
			vip.setAdmin_state_up(Boolean.parseBoolean(argVip.getString(OPSTNetworkConst.VIP_ADMIN_STATE_UP)));
		}
		if (argVip.containsKey(OPSTNetworkConst.VIP_STATUS)) {
			vip.setStatus(argVip.getString(OPSTNetworkConst.VIP_STATUS));
		}
		return vip;
	}
	
	//json to OPSTMemberBean
	private OPSTMemberBean jsonToOPSTMemberBean(JSONObject argMember){
		
		OPSTMemberBean member = new OPSTMemberBean();
		
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_ADMIN_STATE_UP)) {
			member.setAdmin_state_up(Boolean.parseBoolean(argMember.getString(OPSTNetworkConst.MEMBER_ADMIN_STATE_UP)));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_ADDRESS)) {
			member.setAddress(argMember.getString(OPSTNetworkConst.MEMBER_ADDRESS));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_ID)) {
			member.setId(argMember.getString(OPSTNetworkConst.MEMBER_ID));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_POOL_ID)) {
			member.setPool_id(argMember.getString(OPSTNetworkConst.MEMBER_POOL_ID));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_PROTOCOL)) {
			member.setProtocol(argMember.getString(OPSTNetworkConst.MEMBER_PROTOCOL));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_STATUS_DESCRIPTION)) {
			member.setStatus_description(argMember.getString(OPSTNetworkConst.MEMBER_STATUS_DESCRIPTION));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_PROTOCOL_PORT)) {
			member.setProtocol_port(Integer.parseInt(argMember.getString(OPSTNetworkConst.MEMBER_PROTOCOL_PORT)));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_STATUS)) {
			member.setStatus(argMember.getString(OPSTNetworkConst.MEMBER_STATUS));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_TENANT_ID)) {
			member.setTenant_id(argMember.getString(OPSTNetworkConst.MEMBER_TENANT_ID));
		}
		if (argMember.containsKey(OPSTNetworkConst.MEMBER_WEIGHT)) {
			member.setWeight(Integer.parseInt(argMember.getString(OPSTNetworkConst.MEMBER_WEIGHT)));
		}
		
		return member;
	}
}*/