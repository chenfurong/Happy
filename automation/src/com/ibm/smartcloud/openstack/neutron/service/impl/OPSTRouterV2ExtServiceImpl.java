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
import com.ibm.smartcloud.openstack.neutron.bean.OPSTExternalGatewayInfoBean;
import com.ibm.smartcloud.openstack.neutron.bean.OPSTRouterBean;
import com.ibm.smartcloud.openstack.neutron.constants.OPSTRoutersConst;
import com.ibm.smartcloud.openstack.neutron.service.OPSTRouterV2ExtService;

*//**
 * 类名称:OPSTRouterV2ExtServiceImpl
 * @author hanxinxing   创建时间：2014年6月5日 下午2:07
 * @version 1.0
 * @param <OPSTRouterBean>
 *//*
@Service("routerV2ExtService")
public class OPSTRouterV2ExtServiceImpl extends OPSTBaseServiceImpl<Object> implements OPSTRouterV2ExtService {

	String err = OPSTPropertyKeyConst.METHOD_ERROR;
	
	*//**
	 * 查询所有路由信息列表
	 *//*
	public List<OPSTRouterBean> getRouterListV2Ext(String tenantId,String tokenId) throws OPSTBaseException{
		
		//返回结果
		List<OPSTRouterBean> routerBeanResultList = new ArrayList<OPSTRouterBean>();
		
		String strNetworkHost = "";
		String strRouterApi = "";
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_RouterList);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strRouterUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try {
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的网络信息
			JSONArray routersJsonArray = jsonObj.getJSONArray(OPSTRoutersConst.ROUTERBODY);
			for(int i=0; i<routersJsonArray.size(); i++){
				routerBeanResultList.add(this.jsonToRouterBeanV2Ext(routersJsonArray.getJSONObject(i)));
			}
		} catch (JSONException e) {
			throw new OPSTOperationException("获取getRouterListV2Ext的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return routerBeanResultList;
	}
	
	*//**
	 * 根据路由Id获取路由信息
	 *//*
	public OPSTRouterBean getRouterByIdV2Ext(String argRouterId,String tenantId,String tokenId) throws OPSTBaseException{
		//定义返回结果
		OPSTRouterBean routerBeanResult = null;
		
		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_RouterDetails);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		strRouterUrl = strRouterUrl.replace(OPSTRoutersConst.PARAM_ROUTERID, argRouterId);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strRouterUrl);
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try{	
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTRoutersConst.ROUTER);
			//获取返回结果中的路由信息
			routerBeanResult = this.jsonToRouterBeanV2Ext(jsonObj);
		} catch (JSONException e) {
			throw new OPSTOperationException("获取getRouterByIdV2Ext的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		
		return routerBeanResult;
	}
	
	*//**
	 * 创建路由
	 *//*
	@Override
	public OPSTRouterBean createRouterV2Ext(String argName,String tenantId,String tokenId) throws OPSTBaseException{
		//定义返回结果
		OPSTRouterBean routerBeanResult = new OPSTRouterBean();
		
		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CreateRouter);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
   			"router":{
      			"name":"another_router",
//      			"external_gateway_info":{
//      				"network_id":"8ca37218-28ff-41cb-9b10-039601ea7e6b"
//      			},
      			"admin_state_up":true
   			}
		}
		
		JSONObject routerRequestBody = new JSONObject();
		JSONObject routerJSONObj = new JSONObject();
		
		if(argName != null){
			//构建样例"name":"another_router"
			routerJSONObj.element(OPSTRoutersConst.ROUTER_NAME, argName);
		}
//		if(argAdminStateUp != null){
//			//构建样例"admin_state_up":true
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_ADMIN_STATE_UP, argAdminStateUp);
//		}
		//external_gateway_info的设置
		
		//构建样式{"router":{}}
		routerRequestBody.element(OPSTRoutersConst.ROUTER, routerJSONObj);
		
		//调用OpenStack API创建网络
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.postMethod(tokenId, strRouterUrl, routerRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if(!jsonObj.containsKey(OPSTRoutersConst.ROUTER)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		
		try{
			//获取返回结果中的路由信息
			JSONObject routerResponse = jsonObj.getJSONObject(OPSTRoutersConst.ROUTER);
			routerBeanResult = this.jsonToRouterBeanV2Ext(routerResponse);
		} catch (JSONException e) {
			throw new OPSTOperationException("获取createRouterV2Ext的时候出现处理json数据异常，类型为："+e.getMessage());
		}
			
		return routerBeanResult;
	}

	*//**
	 * 更新路由设置网关
	 *//*
	@Override
	public OPSTRouterBean updateRouterSetGateWay(OPSTRouterBean argRequestBean,String tenantId,String tokenId) throws OPSTBaseException{

		//定义返回结果
		OPSTRouterBean routerBeanResult = new OPSTRouterBean();
		
		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UpdateRouter);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		strRouterUrl = strRouterUrl.replace(OPSTRoutersConst.PARAM_ROUTERID, argRequestBean.getId());
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
   			"router":{
   				"id":"8604a0de-7f6b-409a-a47-a1cc7bc77b2e",
      			"name":"another_router",
      			"admin_state_up":true,
      			"status":"ACTIVE",
      			"external_gateway_info":{
      				"network_id":"8ca37218-28ff-41cb-9b10-039601ea7e6b"
      			},
      			"tenant_id":"6b96ff0cb17a4b859e1e575b221683d3"
   			}
		}
		
		JSONObject routerRequestBody = new JSONObject();
		JSONObject routerJSONObj = new JSONObject();
		JSONObject externalGatewayInfoJSONObj = new JSONObject();
		
//		if(argRequestBean.getName() != null){
//			//构建样例"name":"another_router"
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_NAME, argRequestBean.getName());
//		}
//		if(argRequestBean.isAdmin_state_up() != null){
//			//构建样例"admin_state_up":true
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_ADMIN_STATE_UP, argAdminStateUp);
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_ADMIN_STATE_UP, argRequestBean.isAdmin_state_up());
//		}
//		if(argRequestBean.getStatus() != null){
//			//构建样例"status":"ACTIVE"
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_STATUS, argRequestBean.getStatus());
//		}
//		if(argRequestBean.getTenant_id() != null){
//			//构建样例"tenant_id":"6b96ff0cb17a4b859e1e575b221683d3"
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_TENANT_ID, argRequestBean.getTenant_id());
//		}
		
		
		if(argRequestBean.getExternal_gateway_info() != null){
			
			 * 构建样例       "external_gateway_info":{
			 *			 	"network_id":"8ca37218-28ff-41cb-9b10-039601ea7e6b"
			 *		     }
			 
			if(argRequestBean.getExternal_gateway_info().getNetwork_id() != null){
				externalGatewayInfoJSONObj.element(OPSTRoutersConst.NETWORKID, argRequestBean.getExternal_gateway_info().getNetwork_id());
//				externalGatewayInfoJSONObj.element(OPSTRoutersConst.ENABLESNAT, false);
			}
			routerJSONObj.element(OPSTRoutersConst.ROUTER_EXTERNAL_GATEWAY_INFO, externalGatewayInfoJSONObj);
		}
		
		
		//构建样式{"router":{}}
		routerRequestBody.element(OPSTRoutersConst.ROUTER, routerJSONObj);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strRouterUrl, routerRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if(!jsonObj.containsKey(OPSTRoutersConst.ROUTER)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		try{	
			//获取返回结果中的路由信息
			JSONObject routerResponse = jsonObj.getJSONObject(OPSTRoutersConst.ROUTER);
			routerBeanResult = this.jsonToRouterBeanV2Ext(routerResponse);
		} catch (JSONException e) {
			throw new OPSTOperationException("获取updateRouterSetGateWay的时候出现处理json数据异常，类型为："+e.getMessage());
		}
			return routerBeanResult;
		}
	
	*//**
	 * 更新路由删除网关
	 *//*
	@Override
	public OPSTRouterBean updateRouterSetGateWay(String routerId,String tenantId,String tokenId) throws OPSTBaseException{
		
		//定义返回结果
		OPSTRouterBean routerBeanResult = new OPSTRouterBean();
		
		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UpdateRouter);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		strRouterUrl = strRouterUrl.replace(OPSTRoutersConst.PARAM_ROUTERID, routerId);
		
		//拼接请求信息Json结构		
		 按以下样例拼接:
		{
   			"router":{
      			"external_gateway_info":null
   			}
		}
		
		JSONObject routerRequestBody = new JSONObject();
		JSONObject routerJSONObj = new JSONObject();
		JSONObject externalGatewayInfoJSONObj = new JSONObject();
		externalGatewayInfoJSONObj = null;
		
		routerJSONObj.element(OPSTRoutersConst.ROUTER_EXTERNAL_GATEWAY_INFO, externalGatewayInfoJSONObj);
		
		//构建样式{"router":{}}
		routerRequestBody.element(OPSTRoutersConst.ROUTER, routerJSONObj);
		
		//调用OpenStack API获取网络信息列表
		String strResponse = "";
		JSONObject jsonObj = null;
		try {
			strResponse = HttpClientUtil.updateMethod(tokenId, strRouterUrl, routerRequestBody.toString());
			jsonObj = JSONObject.fromObject(strResponse);
			if(!jsonObj.containsKey(OPSTRoutersConst.ROUTER)){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		try{	
			//获取返回结果中的路由信息
			JSONObject routerResponse = jsonObj.getJSONObject(OPSTRoutersConst.ROUTER);
			routerBeanResult = this.jsonToRouterBeanV2Ext(routerResponse);
		} catch (JSONException e) {
			throw new OPSTOperationException("获取updateRouterSetGateWay的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return routerBeanResult;
		
	}

   *//**
	* 删除路由
	* @Title: deleteRouter
	* @param argRouterId    
	* @return void    
	* @throws
	*//*
	public String deleteRouter(String argRouterId,String tenantId,String tokenId) throws OPSTBaseException{

		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DeleteRouter);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		strRouterUrl = strRouterUrl.replace(OPSTRoutersConst.PARAM_ROUTERID, argRouterId);
	
		//调用OpenStack API删除指定的指定路由信息
		String strResponse;
		try {
			strResponse = HttpClientUtil.deleteMethod(tokenId, strRouterUrl);
			if(strResponse != null && !strResponse.equals("")){
				String error = StringUtil.jsonErrorToErrorMessage(strResponse);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		return null;
	}

	*//**
	 * 为路由创建接口
	 *//*
	@Override
	public String createRouterInterface(String routerId, String subnetId, String tenantId, String tokenId) throws OPSTBaseException{

		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_AddRouterInterface);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		strRouterUrl = strRouterUrl.replace(OPSTRoutersConst.PARAM_ROUTERID, routerId);
		
		JSONObject routerJSONObj = new JSONObject();
		
		if(subnetId != ""){
			//构建样例"subnet_id":"98fi-kdfj-dfg43-45645-fd5dff"
			routerJSONObj.element(OPSTRoutersConst.SUBNETID, subnetId);
		}
		//调用OpenStack API删除指定的指定路由信息
		String responseStr = "";
		JSONObject jsonObj = null;
		try {
			responseStr = HttpClientUtil.updateMethod(tokenId, strRouterUrl, routerJSONObj.toString());
			jsonObj = JSONObject.fromObject(responseStr);
			if(!jsonObj.containsKey(OPSTRoutersConst.PORTID)){
				String error = StringUtil.jsonErrorToErrorMessage(responseStr);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		
		return "";
	}

	*//**
	 * 删除路由接口
	 *//*
	@Override
	public String deleteRouterInterface(String routerInterfaceId, String subnetId, String routerId,String tenantId,String tokenId) throws OPSTBaseException{

		String strNetworkHost = "";
		String strRouterApi = "";
		//获取参数信息
		strNetworkHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.NETWORK_HOST);
		strRouterApi = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_RemoveRouterInterface);
		//拼接URL
		String strRouterUrl = strNetworkHost + strRouterApi;
		strRouterUrl = strRouterUrl.replace(OPSTRoutersConst.PARAM_ROUTERID, routerId);

		JSONObject RequestBody = new JSONObject();
//		JSONObject routerJSONObj = new JSONObject();
		
		if(routerInterfaceId != null){
			//构建样例"port_id":"98fi-kdfj-dfg43-45645-fd5dff"
			RequestBody.element(OPSTRoutersConst.PORTID, routerInterfaceId);
		}
//		if(subnetId != null){
//			//构建样例"subnet_id":"98fi-kdfj-dfg43-45645-fd5dff"
//			RequestBody.element(OPSTRoutersConst.SUBNETID, subnetId);
//		}
//		if(routerId != null){
//			//构建样例"id":"98fi-kdfj-dfg43-45645-fd5dff"
//			routerJSONObj.element(OPSTRoutersConst.ROUTER_ID, routerId);
//		}
//		RequestBody.element(OPSTRoutersConst.ROUTER, routerJSONObj);
		
	
		//调用OpenStack API删除指定的指定路由信息
		String responseStr = "";
		JSONObject jsonObj = null;
		try {
			responseStr = HttpClientUtil.updateMethod(tokenId, strRouterUrl, RequestBody.toString());
			jsonObj = JSONObject.fromObject(responseStr);
			if(!jsonObj.containsKey("port_id")){
				String error = StringUtil.jsonErrorToErrorMessage(responseStr);
				throw new OPSTErrorMessageException(error);
			}
		}catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	
	*//**
	* JsonObj转换成OPSTRouterBean私有方法
	* @Title: jsonToRouterBeanV2Ext
	* @param argRouter
	* @return OPSTRouterBean    
	* @throws
	*//*
	private OPSTRouterBean  jsonToRouterBeanV2Ext(JSONObject argRouter){
		OPSTRouterBean r = new OPSTRouterBean();
		r.setId(argRouter.getString(OPSTRoutersConst.ROUTER_ID));
		r.setName(argRouter.getString(OPSTRoutersConst.ROUTER_NAME));
		r.setStatus(argRouter.getString(OPSTRoutersConst.ROUTER_STATUS));
		r.setTenant_id(argRouter.getString(OPSTRoutersConst.ROUTER_TENANT_ID));
		r.setAdmin_state_up(argRouter.getBoolean(OPSTRoutersConst.ROUTER_ADMIN_STATE_UP));
		
		JSONObject externalGatewayInfoJsonObj = argRouter.getJSONObject(OPSTRoutersConst.ROUTER_EXTERNAL_GATEWAY_INFO);
		if(!externalGatewayInfoJsonObj.isNullObject()){
			OPSTExternalGatewayInfoBean egi = new OPSTExternalGatewayInfoBean();
			egi.setNetwork_id(externalGatewayInfoJsonObj.getString(OPSTRoutersConst.NETWORKID));
			r.setExternal_gateway_info(egi);
		}else{
			r.setExternal_gateway_info(null);
		}
		
		return r;
	}
}*/