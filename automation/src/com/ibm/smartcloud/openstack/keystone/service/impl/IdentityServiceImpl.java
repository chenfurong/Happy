package com.ibm.smartcloud.openstack.keystone.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.core.exception.OPSTOperationException;
import com.ibm.smartcloud.openstack.core.service.impl.OPSTBaseServiceImpl;
import com.ibm.smartcloud.openstack.core.util.HttpClientUtil;
import com.ibm.smartcloud.openstack.core.util.StringUtil;
import com.ibm.smartcloud.openstack.keystone.bean.AccessBean;
import com.ibm.smartcloud.openstack.keystone.bean.OPSTRoleBean;
import com.ibm.smartcloud.openstack.keystone.service.IdentityService;

@Service("identityService")
public class IdentityServiceImpl extends OPSTBaseServiceImpl<Object> implements IdentityService  {
	private static final Logger logger = Logger.getLogger(IdentityServiceImpl.class);
	
	/**
	 * 用户登录
	 */
	public AccessBean createAuthTokensV3(String userName,String password)throws OPSTBaseException{
		//获取参数信息	https://10.28.0.230
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_AUTHHOST);
		// /v3/auth/tokens     /powervc/openstack/identity/v3/auth/tokens
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_AUTHTOKENS);
		String strUrl = strHost + strCreateAPI;
		String json = "{\"auth\": { \"scope\": {\"project\": {\"domain\": {\"name\": \"Default\"},\"name\": \"ibm-default\"}},"
				+ "\"identity\": {\"methods\": [\"password\"],"
			    + "\"password\": {\"user\": {\"domain\": {\"name\": \"Default\"},\"name\": \""+userName+"\",\"password\": \""+password+"\"}}}"
			    + "}}";
		Map<String, Object> response =null;
		String responseBody=null;
		String tokenId="";
		try {
			//Date beginString = new Date();
			//System.out.println("begin:"+beginString.toGMTString());
			response = HttpClientUtil.postMethodReturnWithHeader(strUrl,json);
		/*	System.out.println("end:"+new Date().toGMTString());
			System.out.println(new Date().getTime()-beginString.getTime());*/
			responseBody = (String) response.get("response");
			tokenId = (String) response.get("tokenId");
		} catch (OPSTBaseException e) {
			return null;
		}
		try {
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(responseBody);
				JSONObject jsonObject= (JSONObject) jsonObj.get("token");
				jsonObject.put("tokenId", tokenId);
				AccessBean accessBean = this.jsonObjectToAccessBean(jsonObject);
				return accessBean;
			}else{
				String str = StringUtil.jsonErrorToErrorMessage(responseBody);
				logger.error("创建createAuthenticatesV2信息时发生"+str+"异常");
				return null;
			}
		} catch (JSONException e1) {
			throw new OPSTOperationException("创建createAuthenticatesV2的时候出现解析json数据异常，类型为："+e1.getMessage());
		}
	}
	/**
	 * @Title: jsonObjectToAccessBean
	 * @Description: 数据转换为bean
	 * @param @param object
	 * @param @return
	 * @retrun OPSTAccessBean
	 * @author LiangRui
	 * @throws
	 * @Time 2015年6月12日 下午5:41:11
	 */
	private AccessBean jsonObjectToAccessBean(JSONObject object){
		AccessBean accessBean = new AccessBean();
		if(object.containsKey("project")){
			JSONObject tenant = object.getJSONObject("project");
			if(tenant.containsKey("id")){
				accessBean.setTenantId(tenant.getString("id"));
			}
			if(tenant.containsKey("name")){
				accessBean.setTenantName(tenant.getString("name"));
			}
		}			
		if(object.containsKey("user")){
			JSONObject user = object.getJSONObject("user");
			if(user.containsKey("id")){
				accessBean.setUserId(user.getString("id"));
			}
			if(user.containsKey("name")){
				accessBean.setUserName(user.getString("name"));
			}
		}
		if(object.containsKey("roles")){
			JSONArray jsonArray = object.getJSONArray("roles");
			List<OPSTRoleBean> roles = new ArrayList<OPSTRoleBean>();
			for(int c = 0;c<jsonArray.size(); c++){
				OPSTRoleBean roleBean = new OPSTRoleBean();
				roleBean.setId(jsonArray.getJSONObject(c).getString("id"));
				roleBean.setName(jsonArray.getJSONObject(c).getString("name"));
				roles.add(roleBean);
			}
			accessBean.setRoleList(roles);
		}
		if (object.containsKey("tokenId")) {
			accessBean.setTokenId(object.getString("tokenId"));
		}
		return accessBean;
	}
	
	
	
	/*@Override
	public List<OPSTProjectBean> getProjectListV3(String tokenId) throws OPSTBaseException {		
		//定义返回结果
		List<OPSTProjectBean> projectBeanResultLst = new ArrayList<OPSTProjectBean>();
		//获取Project服务的主机地址()
		String strIdentityHost;
		String strProjectAPI;
		//// 目前暂时通过Properties文件获取,以后是否改为从OpenStack 数据库读取访问API的Access数据,需要讨论.
		strIdentityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY);			
		//获取调用项目列表的API调用命令
		strProjectAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PROJECTLIST);
		//创建调用项目列表API的URL(V3)
		String strUrl = strIdentityHost + strProjectAPI;
		//调用OpenStack API获取项目列表
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray projectJsonArray = jsonObj.getJSONArray(OPSTIdentityConst.PROJECTBODY);
			for (int i = 0; i < projectJsonArray.size(); i++) {
				projectBeanResultLst.add(this.jsonObjToProjectBeanV3(projectJsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getProjectListV3的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return projectBeanResultLst;
	}

	@Override
	public OPSTProjectBean getProjectByIdV2(String argProjectId,String tokenId) throws OPSTBaseException {
		//定义返回结果
		OPSTProjectBean projectBeanResult = null;		
		//获取Identity服务的主机地址()
		String strIdentityHost ;
		//获取调用指定Project的API调用命令
		String strProjectAPI ;
		strIdentityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY);
		strProjectAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PROJECTDETAIL);
		//创建调用网络信息列表API的URL(V2)
		String strOrgUrl = strIdentityHost + strProjectAPI;
		String strDestUrl = strOrgUrl.replace(OPSTIdentityConst.PARAM_PROJECTID, argProjectId);
		
		//调用OpenStack API获取指定网络信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			if(strResponse!=""){
				//解析Json结构
				JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTIdentityConst.PROJECT);
				//获取返回结果中的网络信息
				projectBeanResult = this.jsonObjToProjectBeanV3(jsonObj);
				
				return projectBeanResult;
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getProjectByIdV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return null;
	}
	*//**
	 * 创建project
	 *//*
	public OPSTProjectBean createProjectV3(OPSTProjectBean bean,String tokenId) throws OPSTBaseException{
		//获取参数信息	
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v3/projects
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_PROJECTADD);
		String strUrl = strHost + portStr + strCreateAPI;
		{
		    "project": {
		        "description": "...",
		        "domain_id": "...",
		        "enabled": "...",
		        "name": "..."
		    }
		}
		JSONObject obj = new JSONObject();
		JSONObject requestObj =  new JSONObject();	
		if(!"".equals(bean.getName())){
			requestObj.element(OPSTIdentityConst.PROJECT_NAME, bean.getName());
		}if(!"".equals(bean.getDomain_id())){
			requestObj.element(OPSTIdentityConst.PROJECT_DOMAIN_ID, bean.getDomain_id());
		}if(!bean.isEnabled()){
			requestObj.element(OPSTIdentityConst.PROJECT_ENABLED, bean.isEnabled());
		}if(!"".equals(bean.getDescription())){
			requestObj.element(OPSTIdentityConst.PROJECT_DESCRIPTION, bean.getDescription());
		}
		obj.element(OPSTIdentityConst.PROJECT, requestObj);
		String response ="";
		OPSTProjectBean projectBeanResult = null;		
		try {
			response = HttpClientUtil.postMethod(tokenId, strUrl, obj.toString());
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				if(jsonObj.containsKey(OPSTIdentityConst.PROJECT)){
					projectBeanResult = this.jsonObjToProjectBeanV3(jsonObj.getJSONObject(OPSTIdentityConst.PROJECT));					
					return projectBeanResult;					
				}else{
					String error = StringUtil.jsonErrorToErrorMessage(response);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return null;
	}
	public OPSTProjectBean updateProjectByIdV3(OPSTProjectBean bean,String tokenId) throws OPSTBaseException{
		//获取参数信息	
		String identityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v3/projects/{project_id}
//		String strCreateUserAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PATCH_PROJECTUPDATEBYID);   //不可用
		// /v2.0/tenants/​{tenantId}​
		String strCreateUserAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_UPDATETENANTBYID);
		String strUrl = identityHost + portStr + strCreateUserAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_TENANTID, bean.getId());
		JSONObject obj = new JSONObject();
		JSONObject requestObj =  new JSONObject();
		{
		    "project": {
		        "description": "my updated project",
		        "domain_id": "...",
		        "enabled": true,
		        "name": "myUpdatedProject"
		    }
		}
		if(!"".equals(bean.getName())){
			requestObj.element(OPSTIdentityConst.TENANTS_NAME, bean.getName());
		}
//		if(!"".equals(bean.getDomain_id())){
			//requestObj.element(OPSTIdentityConst.PROJECT_DOMAIN_ID, "default");//bean.getDomain_id()
//		}
		if(!"".equals(bean.isEnabled())){
			requestObj.element(OPSTIdentityConst.TENANTS_ENABLED, bean.isEnabled());
		}
		
		requestObj.element(OPSTIdentityConst.TENANTS_DESCRIPTION,bean.getDescription());
		
		obj.element(OPSTIdentityConst.TENANT, requestObj);
		String response ="";
		try {
			response = HttpClientUtil.updateMethod(tokenId, strDestUrl, obj.toString());
		} catch (OPSTBaseException e) {
			throw e;
		}
		OPSTProjectBean opstBean = null;
		try {
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				if(jsonObj.containsKey(OPSTIdentityConst.TENANT)){
					opstBean = jsonObjToProjectBeanV3(jsonObj);				
				}else{
					String str = StringUtil.jsonErrorToErrorMessage(response);
					System.out.println("更新project,返回response出现异常:"+str);
				}	
			}
		} catch(JSONException e){
			throw new OPSTOperationException("更新project的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return opstBean;
	}
	@Override
	public Boolean deleteProjectByIdV3(String projectId,String tokenId) throws OPSTBaseException {
		//获取参数信息	
		String identityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		///v3/projects/{project_id} 
		String strCreateUserAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_PROJECTDELETEBYID);
		String strUrl = identityHost + portStr + strCreateUserAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_PROJECTID, projectId);
		String response = "";
		try {
			response = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (response!=""&&response != null) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return true;
	}
	
	
	 * JsonObj 转换成 OPSTProjectBean 私有方法
	 
	private OPSTProjectBean jsonObjToProjectBeanV3(JSONObject argProjectObj){
		OPSTProjectBean projectBean = new OPSTProjectBean();
		if(argProjectObj.containsKey(OPSTIdentityConst.PROJECT_ID)){
			projectBean.setId(argProjectObj.getString(OPSTIdentityConst.PROJECT_ID));
		}
		if(argProjectObj.containsKey(OPSTIdentityConst.PROJECT_NAME)){
			projectBean.setName(argProjectObj.getString(OPSTIdentityConst.PROJECT_NAME));
		}
		if(argProjectObj.containsKey(OPSTIdentityConst.PROJECT_DOMAIN_ID)){
			projectBean.setDomain_id(argProjectObj.getString(OPSTIdentityConst.PROJECT_DOMAIN_ID));
		}
		if(argProjectObj.containsKey(OPSTIdentityConst.PROJECT_ENABLED)){
			projectBean.setEnabled(Boolean.parseBoolean(argProjectObj.getString(OPSTIdentityConst.PROJECT_ENABLED)));
		}
		if(argProjectObj.containsKey(OPSTIdentityConst.PROJECT_DESCRIPTION)){
			projectBean.setDescription(argProjectObj.getString(OPSTIdentityConst.PROJECT_DESCRIPTION));
		}
		if(argProjectObj.containsKey(OPSTIdentityConst.PROJECT_LINKS)){
			JSONObject linksObj = argProjectObj.getJSONObject(OPSTIdentityConst.PROJECT_LINKS);
			OPSTLinksBean linksBean = new OPSTLinksBean();
			linksBean.setSelf(linksObj.getString(OPSTIdentityConst.PROJECT_SELF));
			projectBean.setLinkBean(linksBean);
		}		
		return projectBean;
	}
	*//**
	 * 根据 用户id  获取用户拥有的项目
	 *//*
	public List<OPSTProjectBean> getProjectListByUserIdV3(String userId,String tokenId)throws OPSTBaseException{
		List<OPSTProjectBean> beanResultLst = new ArrayList<OPSTProjectBean>();
		String hostStr = "";
		String portStr = "";
		String strAPI = "";
		hostStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_PROJECTSLISTBYUSERID);
		String strUrl = hostStr + portStr + strAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_USER_ID, userId);
		//调用OpenStack API获取信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray jsonArray = jsonObj.getJSONArray(OPSTIdentityConst.PROJECTBODY);
			for (int i = 0; i < jsonArray.size(); i++) {
				beanResultLst.add(this.jsonObjToProjectBeanV3(jsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getProjectListByUserId的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return beanResultLst;		
	}
	*//**
	 * 根据 项目id 与 用户id  获取项目下用户拥有的角色
	 *//*
	public List<OPSTRoleBean> getRoleListByProjectIdAndUserIdV3(String projectId,String userId,String tokenId)throws OPSTBaseException{
		List<OPSTRoleBean> beanResultLst = new ArrayList<OPSTRoleBean>();
		String hostStr = "";
		String portStr = "";
		String strAPI = "";
		hostStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_GETROLESBYUSERID);
		String strUrl = hostStr + portStr + strAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_TENANTID, projectId).replace(OPSTIdentityConst.PARAM_USERID, userId);
		//调用OpenStack API获取信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray jsonArray = jsonObj.getJSONArray(OPSTIdentityConst.ROLES);
			for (int i = 0; i < jsonArray.size(); i++) {
				beanResultLst.add(this.jsonObjToOPSTRoleBeanV2(jsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getRoleListByProjectIdAndUserIdV3的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return beanResultLst;		
	}
	//创建用户角色和项目权限
	public String createUserRoleV3(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException{
		//获取参数信息	
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v3/projects/{project_id}/users/{user_id}/roles/{role_id}
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_PROJECTSBYUSERROLE_GRANTS);
		String strUrl = strHost + portStr + strCreateAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_PROJECTID, bean.getTenantId()).
				replace(OPSTIdentityConst.PARAM_USER_ID, bean.getUserId()).
				replace(OPSTIdentityConst.PARAM_ROLE_ID, bean.getRoleId()).trim();
		String response ="";
		try {
			response = HttpClientUtil.putMethod(tokenId, strDestUrl);
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				if(!jsonObj.containsKey(OPSTIdentityConst.ROLE)){
					String error = StringUtil.jsonErrorToErrorMessage(response);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	@Override
	public String deleteUserRoleV3(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException {
		//获取参数信息	
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v2.0/tenants/{tenantId}/users/{userId}/roles/OS-KSADM/{roleId}
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_PROJECTSBYUSERROLE_REVOKES);
		String strUrl = strHost + portStr + strCreateAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_PROJECTID, bean.getTenantId()).
				replace(OPSTIdentityConst.PARAM_USER_ID, bean.getUserId()).
				replace(OPSTIdentityConst.PARAM_ROLE_ID, bean.getRoleId()).trim();
		String response ="";
		try {
			response = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (response!=""&&response != null) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	//JSONObject转换为role bean
	private OPSTRoleBean jsonObjToOPSTRoleBeanV2(JSONObject jsonObj){
		OPSTRoleBean opstBean = new OPSTRoleBean();
		if(jsonObj.containsKey(OPSTIdentityConst.ROLES_ID)){
			opstBean.setId(jsonObj.getString(OPSTIdentityConst.ROLES_ID));
		}
		if(jsonObj.containsKey(OPSTIdentityConst.ROLES_NAME)){
			opstBean.setName(jsonObj.getString(OPSTIdentityConst.ROLES_NAME));
		}
		if (jsonObj.containsKey(OPSTIdentityConst.ROLES_ENABLED)) {
			opstBean.setEnabled(jsonObj.getString(OPSTIdentityConst.ROLES_ENABLED));
		}
		if (jsonObj.containsKey(OPSTIdentityConst.ROLES_DESCRIPTION)) {
			opstBean.setDescription(jsonObj.getString(OPSTIdentityConst.ROLES_DESCRIPTION));
		}
		return opstBean;
				
	}
	//获取所有openstack用户
	public List<OPSTUserBean> getUserListV2(String tenantId,String tokenId) throws OPSTBaseException{
		List<OPSTUserBean> beanResultLst = new ArrayList<OPSTUserBean>();
		String hostStr = "";
		String portStr = "";
		String strAPI = "";
		hostStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_USERLIST);
		String strUrl = hostStr + portStr + strAPI;
		//调用OpenStack API获取信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray jsonArray = jsonObj.getJSONArray(OPSTIdentityConst.USERS);
			for (int i = 0; i < jsonArray.size(); i++) {
				beanResultLst.add(this.jsonObjToOPSTUserBeanV2(jsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getUserListV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return beanResultLst;
	}
	//创建openstack用户
	public OPSTUserBean createUserV2(OPSTUserBean argUserBean,String tenantId,String tokenId) throws OPSTBaseException{
		//获取参数信息	
		String identityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v2.0/users
		String strCreateUserAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_CREATEUSER);
		String strUrl = identityHost + portStr + strCreateUserAPI;

		JSONObject obj = new JSONObject();
		JSONObject requestObj =  new JSONObject();
		{
		    "user": {
		        "name": "jqsmith",
		        "email": "john.smith@example.org",
		        "enabled": true,
		        "OS-KSADM:password": "secrete"
		    }
		}		
		if(!"".equals(argUserBean.getName())){
			requestObj.element(OPSTUserConst.USER_NAME, argUserBean.getName());
		}if(!"".equals(argUserBean.getEmail())){
			requestObj.element(OPSTUserConst.USER_EMAIL, argUserBean.getEmail());
		}if(!"".equals(argUserBean.getEnabled())){
			requestObj.element(OPSTUserConst.USER_ENABLED, Boolean.valueOf(argUserBean.getEnabled()));
		}if(!"".equals(argUserBean.getPassword())){
			requestObj.element(OPSTUserConst.USER_PASSWORD, argUserBean.getPassword());
		}if(!"".equals(argUserBean.getTenantId())){
			requestObj.element(OPSTUserConst.USER_TENANTID, argUserBean.getTenantId());
		}
		obj.element(OPSTUserConst.USER, requestObj);
		String response ="";
		try {
			response = HttpClientUtil.postMethod(tokenId, strUrl, obj.toString());
		} catch (OPSTBaseException e) {
			throw e;
		}
		OPSTUserBean opstUserBean = null;
		try {
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				if(jsonObj.containsKey(OPSTUserConst.USER)){
					opstUserBean = jsonObjToOPSTUserBeanV2(jsonObj.getJSONObject(OPSTIdentityConst.USER));				
				}else{
					String error = StringUtil.jsonErrorToErrorMessage(response);
					throw new OPSTErrorMessageException(error);
				}	
			}
		} catch(JSONException e){
			throw new OPSTOperationException("创建user的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return opstUserBean;
	}
	
	public OPSTUserBean updateUserByIdV2(OPSTUserBean argUserBean,String tenantId,String tokenId) throws OPSTBaseException{
		//获取参数信息	
		String identityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v2.0/users
		String strCreateUserAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_UPDATEUSERBYID);
		String strUrl = identityHost + portStr + strCreateUserAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_USERID, argUserBean.getId());
		JSONObject obj = new JSONObject();
		JSONObject requestObj =  new JSONObject();
		{
		    "user": {
		        "id": "u1000",
		        "name": "jqsmith",
		        "email": "john.smith@example.org",
		        "enabled": true
		       // "OS-KSADM:password": "secrete"
		    }
		}	
		
		requestObj.element(OPSTUserConst.USER_ID, argUserBean.getId());
		requestObj.element(OPSTUserConst.USER_NAME, argUserBean.getName());
		requestObj.element(OPSTUserConst.USER_EMAIL, argUserBean.getEmail());
		if (argUserBean.getEnabled() != null) {
			requestObj.element(OPSTUserConst.USER_ENABLED, Boolean.valueOf(argUserBean.getEnabled()));
		}
		requestObj.element(OPSTUserConst.USER_PASSWORD, argUserBean.getPassword());
		requestObj.element(OPSTUserConst.USER_TENANTID, argUserBean.getTenantId());
		obj.element(OPSTUserConst.USER, requestObj);
		String response ="";
		try {
			response = HttpClientUtil.updateMethod(tokenId, strDestUrl, obj.toString());
		} catch (OPSTBaseException e) {
			throw e;
		}
		OPSTUserBean opstUserBean = null;
		try {
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				if(jsonObj.containsKey(OPSTUserConst.USER)){
					opstUserBean = jsonObjToOPSTUserBeanV2(jsonObj);				
				}else{
					String error = StringUtil.jsonErrorToErrorMessage(response);
					throw new OPSTErrorMessageException(error);
				}	
			}
		} catch(JSONException e){
			throw new OPSTOperationException("更新user的时候出现处理json数据异常，类型为："+e.getMessage());
		}		
		return opstUserBean;
	}
	
	@Override
	public Boolean deleteUserByIdV2(String userId,String tenantId,String tokenId) throws OPSTBaseException {
		//获取参数信息	
		String identityHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v2.0/users/{userId}
		String strCreateUserAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DELETEUSERBYID);
		String strUrl = identityHost + portStr + strCreateUserAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_USERID, userId);
		String response = "";
		try {
			response = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (response!=""&&response != null) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return true;
	}
	*//**
	 * 根据用户id查询用户信息
	 * @param userId
	 * @return
	 * @throws OPSTBaseException
	 *//*
	@Override
	public OPSTUserBean getUserByIdV2(String userId,String tenantId,String tokenId) throws OPSTBaseException{		
		//定义返回结果
		OPSTUserBean opstUserBean = null;		
		String strHost ;
		String portStr ;
		String strAPI ;
		strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_GETUSERBYID);
		String strOrgUrl = strHost + portStr + strAPI;
		String strDestUrl = strOrgUrl.replace(OPSTIdentityConst.PARAM_USER_ID, userId);
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			if(strResponse!=""){
				JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTIdentityConst.USER);
				opstUserBean = this.jsonObjToOPSTUserBeanV2(jsonObj);
				return opstUserBean;
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getUserByIdV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return null;
	}
	*//**
	 * 根据用户name查询用户信息
	 * @param userId
	 * @return
	 * @throws OPSTBaseException
	 *//*
	@Override
	public OPSTUserBean getUserByNameV2(String userName,String tenantId,String tokenId) throws OPSTBaseException{
		//定义返回结果
		OPSTUserBean opstUserBean = null;		
		String strHost ;
		String portStr ;
		String strAPI ;
		strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_GETUSERBYNAME);
		String strOrgUrl = strHost + portStr + strAPI;
		String strDestUrl = strOrgUrl.replace(OPSTIdentityConst.PARAM_NAME, userName);
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			if(strResponse!=""){
				JSONObject jsonObj = JSONObject.fromObject(strResponse).getJSONObject(OPSTIdentityConst.USER);
				opstUserBean = this.jsonObjToOPSTUserBeanV2(jsonObj);
				return opstUserBean;
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getUserByNameV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return null;
	}
	//JSONObject转换为用户bean
	private OPSTUserBean jsonObjToOPSTUserBeanV2(JSONObject jsonObj){
		OPSTUserBean opstUserBean = new OPSTUserBean();
		if(jsonObj.containsKey(OPSTUserConst.USER_ID)){
			opstUserBean.setId(jsonObj.getString(OPSTUserConst.USER_ID));
		}else{
			opstUserBean.setId(null);
		}
		if(jsonObj.containsKey(OPSTUserConst.USER_NAME)){
			opstUserBean.setName(jsonObj.getString(OPSTUserConst.USER_NAME));
		}else{
			opstUserBean.setName(null);
		}
		if(jsonObj.containsKey(OPSTUserConst.USER_EMAIL)){
			opstUserBean.setEmail(jsonObj.getString(OPSTUserConst.USER_EMAIL));
		}else{
			opstUserBean.setEmail(null);
		}
		if(jsonObj.containsKey(OPSTUserConst.USER_ENABLED)){
			opstUserBean.setEnabled(jsonObj.getString(OPSTUserConst.USER_ENABLED));
		}else{
			opstUserBean.setEnabled(null);
		}
		if(jsonObj.containsKey(OPSTUserConst.USER_TENANTID)){
			opstUserBean.setTenantId(jsonObj.getString(OPSTUserConst.USER_TENANTID));
		}else{
			opstUserBean.setTenantId(null);
		}
		return opstUserBean;
				
	}
	//创建用户角色和项目权限
	public String createUserRoleV2(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException{
		//获取参数信息	
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v2.0/tenants/{tenantId}/users/{userId}/roles/OS-KSADM/{roleId}
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.PUT_USERROLEADD);
		String strUrl = strHost + portStr + strCreateAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_TENANTID, bean.getTenantId()).
				replace(OPSTIdentityConst.PARAM_USERID, bean.getUserId()).
				replace(OPSTIdentityConst.PARAM_ROLEID, bean.getRoleId()).trim();
		String response ="";
		try {
			response = HttpClientUtil.putMethod(tokenId, strDestUrl);
			if(!response.equals("")){
				JSONObject jsonObj = JSONObject.fromObject(response);
				if(!jsonObj.containsKey(OPSTIdentityConst.ROLE)){
					String error = StringUtil.jsonErrorToErrorMessage(response);
					throw new OPSTErrorMessageException(error);
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	@Override
	public String deleteUserRoleV2(OPSTOSKSADMExtBean bean,String tokenId) throws OPSTBaseException {
		//获取参数信息	
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		String portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		// /v2.0/tenants/{tenantId}/users/{userId}/roles/OS-KSADM/{roleId}
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.DELETE_DELETESUSERROLE);
		String strUrl = strHost + portStr + strCreateAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_TENANTID, bean.getTenantId()).
				replace(OPSTIdentityConst.PARAM_USERID, bean.getUserId()).
				replace(OPSTIdentityConst.PARAM_ROLEID, bean.getRoleId()).trim();
		String response ="";
		try {
			response = HttpClientUtil.deleteMethod(tokenId, strDestUrl);
			if (response!=""&&response != null) {
				String error = StringUtil.jsonErrorToErrorMessage(response);
				throw new OPSTErrorMessageException(error);
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return "";
	}
	//获取所有roles 信息
	public List<OPSTOSKSADMExtBean> getRolesListV2(String tokenId) throws OPSTBaseException{
		List<OPSTOSKSADMExtBean> beanResultLst = new ArrayList<OPSTOSKSADMExtBean>();
		String strHost;
		String portStr;
		String strAPI;
		strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_ROLESLIST);
		String strUrl = strHost + portStr +strAPI;
		//调用OpenStack API获取信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray jsonArray = jsonObj.getJSONArray(OPSTIdentityConst.ROLES);
			for (int i = 0; i < jsonArray.size(); i++) {
				beanResultLst.add(this.jsonObjToRolesBeanV2(jsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getRolesListV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return beanResultLst;
	}
	private OPSTOSKSADMExtBean jsonObjToRolesBeanV2(JSONObject object){
		OPSTOSKSADMExtBean bean = new OPSTOSKSADMExtBean();
		if(object.containsKey(OPSTIdentityConst.ROLES_ID)){
			bean.setRolesId(object.getString(OPSTIdentityConst.ROLES_ID));			
		}
		if(object.containsKey(OPSTIdentityConst.ROLES_NAME)){
			bean.setRolesName(object.getString(OPSTIdentityConst.ROLES_NAME));			
		}
		if(object.containsKey(OPSTIdentityConst.ROLES_DESCRIPTION)){
			bean.setRolesDescrip(object.getString(OPSTIdentityConst.ROLES_DESCRIPTION));			
		}
		return bean;
	}
	//获取所有项目信息
	public List<OPSTOSKSADMExtBean> getTenantsListV2(String tokenId) throws OPSTBaseException{
		List<OPSTOSKSADMExtBean> beanResultLst = new ArrayList<OPSTOSKSADMExtBean>();
		String strHost;
		String portStr;
		String strAPI;
		strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_TENANTSLIST);
		String strUrl = strHost + portStr + strAPI;
		//调用OpenStack API获取信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray jsonArray = jsonObj.getJSONArray(OPSTIdentityConst.TENANTS);
			for (int i = 0; i < jsonArray.size(); i++) {
				beanResultLst.add(this.jsonObjToOPSTTenantsBeanV2(jsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getTenantsListV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return beanResultLst;
	}
	
	public List<OPSTUserBean> getUserListByTenantIdV2(String tenantId,String tokenId)throws OPSTBaseException{
		List<OPSTUserBean> beanResultLst = new ArrayList<OPSTUserBean>();
		String hostStr = "";
		String portStr = "";
		String strAPI = "";
		hostStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_HOST);
		portStr = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.IDENTITY_PORT);
		strAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.GET_USERLISTBYTENANTID);
		String strUrl = hostStr + portStr + strAPI;
		String strDestUrl = strUrl.replace(OPSTIdentityConst.PARAM_TENANTID, tenantId);
		//调用OpenStack API获取信息
		String strResponse = "";
		try {
			strResponse = HttpClientUtil.getMethod(tokenId, strDestUrl);
		} catch (OPSTBaseException e1) {
			throw e1;
		}
		try{
			//解析Json结构
			JSONObject jsonObj = JSONObject.fromObject(strResponse);
			//获取返回结果中的项目信息
			JSONArray jsonArray = jsonObj.getJSONArray(OPSTIdentityConst.USERS);
			for (int i = 0; i < jsonArray.size(); i++) {
				beanResultLst.add(this.jsonObjToOPSTUserBeanV2(jsonArray.getJSONObject(i)));
			}
		}catch(JSONException e){
			throw new OPSTOperationException("获取getUserListByTenantIdV2的时候出现处理json数据异常，类型为："+e.getMessage());
		}
		return beanResultLst;
	}
	private OPSTOSKSADMExtBean jsonObjToOPSTTenantsBeanV2(JSONObject jsonObj){
		OPSTOSKSADMExtBean opstBean = new OPSTOSKSADMExtBean();
		if(jsonObj.containsKey(OPSTIdentityConst.TENANTS_ID)){
			opstBean.setTenantsId(jsonObj.getString(OPSTIdentityConst.TENANTS_ID));
		}else{
			opstBean.setTenantsId(null);
		}
		if(jsonObj.containsKey(OPSTIdentityConst.TENANTS_NAME)){
			opstBean.setTenantsName(jsonObj.getString(OPSTIdentityConst.TENANTS_NAME));
		}else{
			opstBean.setTenantsName(null);
		}
		if(jsonObj.containsKey(OPSTIdentityConst.TENANTS_DESCRIPTION)){
			opstBean.setTenantsDescrip(jsonObj.getString(OPSTIdentityConst.TENANTS_DESCRIPTION));
		}else{
			opstBean.setTenantsDescrip(null);
		}
		if(jsonObj.containsKey(OPSTIdentityConst.TENANTS_ENABLED)){
			opstBean.setTenantsEnabled(jsonObj.getString(OPSTIdentityConst.TENANTS_ENABLED));
		}else{
			opstBean.setTenantsEnabled(null);
		}
		return opstBean;				
	}	

	//认证
	public OPSTAccessBean createAuthenticatesV2(String userName,String password,String tenantId) throws OPSTBaseException{
		//获取参数信息	
		String strHost = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.OPENSTACK_AUTHHOST);
		// /v2.0/tokens
		String strCreateAPI = this.getPropertyUtil().getProperty(OPSTPropertyKeyConst.POST_TOKENAUTH);
		String strUrl = strHost + strCreateAPI;
		JSONObject obj = new JSONObject();
		JSONObject jsonObj =  new JSONObject();
		JSONObject requestObj =  new JSONObject();
//		{
//		    "auth": {
//		        "tenantName": "demo",
////		        tenantId":"d4a5bf77321046fc94a0c63a064a7de5
//		        "passwordCredentials": {
//		            "username": "demo",
//		            "password": "devstack"
//		        }
//		    }
//		}
//			
		
		jsonObj.element("username", userName);
		jsonObj.element("password", password);
		
		requestObj.element("tenantId", tenantId);
		requestObj.element("passwordCredentials", jsonObj);
		
		obj.element("auth", requestObj);
		String response ="";
		try {
			response = HttpClientUtil.postMethod(strUrl,obj.toString());
			if(!response.equals("")){
				OPSTAccessBean accessBean = new OPSTAccessBean();
				JSONObject object = JSONObject.fromObject(response);
				if(object.containsKey("access")){
					JSONObject access=object.getJSONObject("access");//获取对象
					if(access.containsKey("token")){
						JSONObject token = access.getJSONObject("token");
						if(token.containsKey("id")){
							accessBean.setTokenId(token.getString("id"));
						}
						if(token.containsKey("tenant")){
							JSONObject tenant = token.getJSONObject("tenant");
							if(tenant.containsKey("id")){
								accessBean.setTenantId(tenant.getString("id"));
							}
							if(tenant.containsKey("name")){
								accessBean.setTenantName(tenant.getString("name"));
							}
							if(tenant.containsKey("enabled")){
								accessBean.setTenantEnabled(Boolean.valueOf(tenant.getString("enabled")));
							}
							if(tenant.containsKey("description")){
								accessBean.setTenantDescription(tenant.getString("description"));
							}
						}
					}
					if(access.containsKey("user")){
						JSONObject user = access.getJSONObject("user");
						if(user.containsKey("id")){
							accessBean.setUserId(user.getString("id"));
						}
						if(user.containsKey("name")){
							accessBean.setUserName(user.getString("name"));
						}
						if(user.containsKey("roles")){
							List<String> roleNames = new ArrayList<String>();
							JSONArray jsonArray = user.getJSONArray("roles");
							for(int c = 0;c<jsonArray.size(); c++){
								JSONObject jc = jsonArray.getJSONObject(c);
								roleNames.add(jc.getString("name"));
							}
							accessBean.setRoleNames(roleNames);
						}
					}
					if(access.containsKey("metadata")){
						JSONObject metadata = access.getJSONObject("metadata");
						if(metadata.containsKey("roles")){
							List<String> roleIds = new ArrayList<String>();
							JSONArray jsonArray = metadata.getJSONArray("roles");
							for(int c = 0;c<jsonArray.size(); c++){
								roleIds.add(jsonArray.getString(c));
							}
							accessBean.setRoleIds(roleIds);
						}
					}
					return accessBean;
				}else{
					String str = StringUtil.jsonErrorToErrorMessage(response);
					logger.error("创建createAuthenticatesV2信息时发生"+str+"异常");
					return null;
				}
			}
		} catch (OPSTBaseException e) {
			throw e;
		}
		return null;
	}*/
	
	
	

	
}
