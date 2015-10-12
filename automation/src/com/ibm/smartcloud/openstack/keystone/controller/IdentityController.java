package com.ibm.smartcloud.openstack.keystone.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.exception.BaseException;
import com.ibm.smartcloud.core.exception.ErrorMessageException;
import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;
import com.ibm.smartcloud.core.exception.handler.PopUpMessageException;
import com.ibm.smartcloud.core.exception.handler.ReturnToLastPageException;
import com.ibm.smartcloud.core.util.OpenStackUtil;
import com.ibm.smartcloud.openstack.keystone.service.IdentityService;

/**
 * @Title：IdentityController 
 * @Description: 租户管理
 *  备注: openstack 中 项目project与租户tenants  是同一概念 
 * 	租户tenants是identity 中v2版本
 *  项目project是identity 中v3版本
 * @Auth: liwj
 * @CreateTime:2015年3月27日 上午10:29:52     
 * @version V1.0
 */
@Controller
public class IdentityController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(IdentityController.class);
	private static final String roleName = "owner";//默认角色
	
	@Autowired
	private IdentityService identityService;

	/**
	 * 
	  * @Title: getAllOpstUserList
	  * @Description: 查询所有openstack user信息
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:30:50
	 */
	/*@RequestMapping("/getAllOpstUserList")
	public String getAllOpstUserList(HttpServletRequest request){ 
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		List<UserBean> list = null;
		try {
			list = identityService.getUserList(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取所有用户信息时发生"+e.getMessage()+"异常");
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		request.setAttribute("opstuserList", list);
		return "home/user/sc_opstuser_list";
	}
	
	*//**
	 * 
	  * @Title: checkOpstUserNameByName
	  * @Description: 根据用户名查看是否已存在相同的用户名
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:31:02
	 *//*
	@RequestMapping("/checkOpstUserNameByName")
	public String checkOpstUserNameByName(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");	
		JSONObject object = new JSONObject();
		Boolean isExist = true; 
		try {
			//查询openstack用户表是否存在相同用户名
			Boolean flag = identityService.getOPSTUserByUserName(userName,tenantId,tokenId);
			if(!flag){
				isExist = false;//存在相同的用户名
			}	
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查看是否有相同的用户名时发生"+e.getMessage()+"异常");
			object.put("success", false);//发生异常按存在相同用户名处理
		}
		object.put("success",isExist);
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: goCreateOpstUser
	  * @Description: 跳转到创建用户的页面
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:31:09
	 *//*
	@RequestMapping("/goCreateOpstUser")
	public String goCreateOpstUser(HttpServletRequest request){ 
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		//获取所有项目project 租户 的信息
		List<TenantBean> tenantList = null;
		List<TenantBean> tenants = new ArrayList<TenantBean>();
		try {
			tenantList = identityService.getTenantsList(tokenId);
			for (TenantBean tenantBean : tenantList) {
				if (tenantBean.isEnabled()) {
					tenants.add(tenantBean);
				}
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
		}
		//获取所有角色role的信息
		List<RoleBean> roleList = null;
		try {
			roleList = identityService.getRolesList(tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取roleList信息时发生"+e.getMessage()+"异常");
		}
		request.setAttribute("tenantList", tenants); 
		request.setAttribute("roleList", roleList); 
		
		return "home/user/sc_opstuser_create";
	}

	*//**
	 * 
	  * @Title: createOpstUser
	  * @Description: 创建opst 用户 提交
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:31:15
	 *//*
	@RequestMapping("/createOpstUser")
	public String createOpstUser(HttpServletRequest request,HttpServletResponse response)throws Exception{ 
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8"); 
		String tokenId = "";
//		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
//			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String userName = request.getParameter("userName");
		String email=request.getParameter("email");	
		String password = request.getParameter("password");
		String isEnabled = request.getParameter("isEnabled");
		String tenantId = request.getParameter("tenantId");	
		String roleId = request.getParameter("roleId");
		
		UserBean user = new UserBean();
		user.setName(userName);
		user.setPassword(password);
		user.setEmail(email);
		if ("checked".equals(isEnabled)) {
			user.setEnabled("true");
		} else {
			user.setEnabled("false");	
		}
		user.setTenantId(tenantId);
		UserBean u = null;
		try {
			u = identityService.createUser(user,tenantId,tokenId);
			if(u != null){
				request.setAttribute("actionLog", "创建了一个名称为"+userName+"的用户");
			}		
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("新增用户时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		}
		try {
			if(u != null){
				identityService.createUserRole(u.getId(), tenantId, roleId,tokenId);//新增用户权限信息
				request.setAttribute("actionLog", "创建名称为"+userName+"的用户的角色和主项目权限");
			}			
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage=User created successfully! "+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("新增用户时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage=User created successfully! "+e.getMessage()+"';</script>");
		}
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: goUpdateOpstUser
	  * @Description: 跳转到更新用户的页面
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:31:41
	 *//*
	@RequestMapping("/goUpdateOpstUser")
	public String goUpdateOpstUser(HttpServletRequest request){ 
		
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String userId = request.getParameter("userId");
		String tenantId = request.getParameter("tenantId");
		UserBean userBean = null;
		ProjectBean projectBean = null;
		List<TenantBean> tenants = new ArrayList<TenantBean>();
		try {
			userBean = identityService.getUserById(userId,tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
		}
		try {
			projectBean = identityService.getProjectById(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
		}
		try {
			List<TenantBean> tenantList = identityService.getTenantsList(tokenId);
			for (TenantBean tenantBean : tenantList) {
				if (tenantBean.isEnabled()) {
					tenants.add(tenantBean);
				}
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
		}
		request.setAttribute("userBean", userBean );
		request.setAttribute("tenant", projectBean);
		request.setAttribute("tenantList", tenants);
		
		return "home/user/sc_opstuser_update";
	}
	
	*//**
	 * 
	  * @Title: updateOpstUser
	  * @Description:  更新用户信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:31:48
	 *//*
	@RequestMapping("/updateOpstUser")
	public String updateOpstUser(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8"); 
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String userId =request.getParameter("userId");
		String userName =request.getParameter("userName");
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String tenantId = request.getParameter("tenantId");
		
		UserBean user=new UserBean();
		user.setId(userId);		
		user.setName(userName);
		user.setEmail(email);
		user.setPassword(password);
		user.setTenantId(tenantId);
		
		try {
			identityService.updateOpstUser(user,tenantId,tokenId);
			request.setAttribute("actionLog", "更新了一个名称为"+userName+"的用户");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("更新用户信息时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		}
		
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	  * @Title: updateOpstUserStatus
	  * @Description: 更新用户状态   启用/禁用
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:31:56
	 *//*
	@RequestMapping("/updateOpstUserStatus")
	public String updateOpstUserStatus(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8"); 
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String userId = request.getParameter("userId");
		String status = request.getParameter("status");
		
		UserBean user = new UserBean();
		user.setId(userId);
		user.setEnabled(status);
		
		try {
			identityService.updateOpstUserStatus(user,tenantId,tokenId);
			request.setAttribute("actionLog", "更新了一个ID为"+userId+"的用户状态");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("更新用户信息时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		}
		
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: deleteOpstUserById
	  * @Description: 删除openstack user
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:32:07
	 *//*
	@RequestMapping("/deleteOpstUserById")
	public String deleteOpstUserById(HttpServletRequest request,HttpServletResponse response)throws Exception{ 		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String userId = request.getParameter("userId");		
		String[] instId = userId.split(",");
		try {
			for (int i = 0; i < instId.length; i++) {
				identityService.deleteUserById(instId[i],tenantId,tokenId);
				request.setAttribute("actionLog", "删除了一个ID为"+instId[i]+"的用户");
			}
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=1';</script>");	
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("删除用户时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstUserList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");	
		}
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: getAllOpstProjectList
	  * @Description:跳转到 openstack 项目  租户 的页面 
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:32:13
	 *//*
	@RequestMapping("/getAllOpstProjectList")
	public String getAllOpstProjectList(HttpServletRequest request){ 
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		//获取所有项目project 租户 的信息
		List<TenantBean> tenantList = null;
		try {
			tenantList = identityService.getTenantsList(tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		request.setAttribute("tenantList", tenantList ); 
		return "home/user/sc_opstproject_list";
	}
	
	*//**
	 * 
	  * @Title: checkOpstProjectNameByName
	  * @Description: 根据租户名称查看是否已存在相同的租户名称
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:32:20
	 *//*
	@RequestMapping("/checkOpstProjectNameByName")
	public String checkOpstProjectNameByName(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String name = request.getParameter("name");	
		JSONObject object = new JSONObject();
		Boolean isExist = true; 
		try {
			//查询openstack项目表是否存在相同项目名
			Boolean flag = identityService.getProjectNameByName(name,tokenId);
			if(!flag){
				isExist = false;//存在相同的项目名
			}	
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("查看是否有相同的项目名称时发生"+e.getMessage()+"异常");
			object.put("success", false);//发生异常按存在相同用户名处理
		}
		object.put("success",isExist);
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	*//**
	 * 
	  * @Title: goCreateProject
	  * @Description:  跳转到 租户 创建页面
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:32:26
	 *//*
	@RequestMapping("/goCreateProject")
	public String goCreateProject(HttpServletRequest request){ 
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		//获取所有的用户
		List<UserBean> list = null;
		try {
			list = identityService.getUserList(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取所有用户信息时发生"+e.getMessage()+"异常");
		}
		//获取所有角色role的信息
		List<RoleBean> roleList = null;
		String readRoleId = "";
		try {
			roleList = identityService.getRolesList(tokenId);
			for(RoleBean r: roleList ){
				if(r.getName().equals(roleName)){//默认角色
					readRoleId=r.getId();
				}
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取roleList信息时发生"+e.getMessage()+"异常");
		}
		//查询所有安全组
		try {
			List<SecurityGroupsBean> securityGroups = securityGroupsService.getSercurityGroupsList(tenantId,tokenId);
			request.setAttribute("securityGroups", securityGroups);
			request.setAttribute("sgsSize", securityGroups.size());
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("userAllList", list);
		request.setAttribute("roleList", roleList ); 
		request.setAttribute("readRoleId", readRoleId);//默认角色id
		request.setAttribute("readRoleName", roleName);//默认角色name
		return "home/user/sc_opstproject_create";
	}
	*//**
	 * 
	  * @Title: createProject
	  * @Description: 租户 创建
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:32:49
	 *//*
	@RequestMapping("/createProject")
	public String createProject(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String name = request.getParameter("project_name");
		String description = request.getParameter("project_description");
		String domainId = request.getParameter("project_domainId");
		String enabled = request.getParameter("project_enabled");
		
		ProjectBean projectBean = new ProjectBean();
		projectBean.setName(name);
		projectBean.setDomain_id(domainId);
		projectBean.setDescription(description);
		projectBean.setEnabled(Boolean.valueOf(enabled));	
		
		//项目人员
		String seluserId = request.getParameter("selNetwork");
		String[] userId = seluserId.split(";");
		
		//项目租户配额
		String metadata_items = request.getParameter("metadata_items");
		String cores = request.getParameter("vcpus");
		String instances = request.getParameter("instances");
		String injected_files = request.getParameter("injected_files");
		String injected_file_content_bytes = request.getParameter("file_content_bytes");
		String volumes = request.getParameter("volumes");
		String volume_snapshots = request.getParameter("volume_snapshots");
		String volume_snapshots_size = request.getParameter("volume_snapshots_size");
		String ram = request.getParameter("ram");
		String security_groups = request.getParameter("security_groups");
		String security_group_rules = request.getParameter("security_group_rules");
		String floating_ips = request.getParameter("floating_ips");
		String networks = request.getParameter("networks");
		String ports = request.getParameter("ports");
		String routers = request.getParameter("routers");
		String subnets = request.getParameter("subnets");
//		String fixed_ips=request.getParameter("fixed_ips");
//		String key_pairs=request.getParameter("key_pairs");
		String tenantName = request.getParameter("tenantName");
		
		String[] securityGroups = request.getParameterValues("securityGroups");
		List<String> sgIdList = null;
		if(securityGroups != null) {
			sgIdList = Arrays.asList(securityGroups);
		}
		String readRoleId = request.getParameter("readRoleId");
		
		TenantQuotasBean bean = new TenantQuotasBean();
		if(metadata_items!=null){
			bean.setMetadata_items(Integer.parseInt(metadata_items));
		}
		if (cores != null) {
			bean.setCores(Integer.parseInt(cores));
		}
		if (instances != null) {
			bean.setInstances(Integer.parseInt(instances));
		}
		if (injected_files != null){
			bean.setInjected_files(Integer.parseInt(injected_files));
		}
		if (injected_file_content_bytes != null){
			bean.setInjected_file_content_bytes(Integer.parseInt(injected_file_content_bytes));
		}
		if (volumes != null) {
			bean.setVolumes(Integer.parseInt(volumes));
		}
		if (volume_snapshots != null) {
			bean.setVolume_snapshots(Integer.parseInt(volume_snapshots));
		}
		if (volume_snapshots_size != null) {
			bean.setVolume_snapshots_size(Integer.parseInt(volume_snapshots_size));
		}
		if (ram != null) {
			bean.setRam(Integer.parseInt(ram));
		}
		if (security_groups != null) {
			bean.setSecurity_groups(Integer.parseInt(security_groups));
		}
		if (security_group_rules != null){
			bean.setSecurity_group_rules(Integer.parseInt(security_group_rules));
		}
		if (floating_ips != null) {
			bean.setFloating_ips(Integer.parseInt(floating_ips));
		}
		if (networks != null) {
			bean.setNetworks(Integer.parseInt(networks));
		}
		if (ports != null) {
			bean.setPorts(Integer.parseInt(ports));
		}
		if (routers != null) {
			bean.setRouters(Integer.parseInt(routers));
		}
		if (subnets != null) {
			bean.setSubnets(Integer.parseInt(subnets));
		}
//		if (fixed_ips != null) {
//			bean.setFixed_ips(Integer.parseInt(fixed_ips));
//		}
//		if (key_pairs != null) {
//			bean.setKey_pairs(Integer.parseInt(key_pairs));
//		}
		
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			ProjectBean resultbean = identityService.createProject(projectBean,tokenId);
			if(resultbean != null) {
				request.setAttribute("actionLog", "创建了一个名称为"+name+"的租户");
				String projectId = resultbean.getId();
				//配额
				bean.setTenant_id(projectId);
				identityService.createProjectQuotas(bean, tenantId, tokenId);
				request.setAttribute("actionLog", "创建名称为"+name+"的租户的配额");
				//项目人员
				if (userId != null) {
					for (int i = 1; i < userId.length; i++) {
						//勾选的用户角色
						String[] roleIds = request.getParameterValues("role"+userId[i]);
						if(roleIds!=null){
							for (int j = 0; j < roleIds.length; j++) {
								identityService.createUserRole(userId[i], projectId, roleIds[j],tokenId);
								request.setAttribute("actionLog", "创建名称为"+name+"的租户的项目成员角色");
							}
						}else{
							//如果没有获取到值   默认给它owner的角色
							if(readRoleId!=null){
								identityService.createUserRole(userId[i], projectId, readRoleId,tokenId);
								//System.out.println("更新名称为"+name+"的租户的项目成员"+userId[i]+"角色"+readRoleId);
								request.setAttribute("actionLog", "创建名称为"+name+"的租户的项目成员"+userId[i]+"角色"+readRoleId);
							}
						}
						
					}
				}
				if(sgIdList != null) {
					//复制安全组
					for (int j=0; j<sgIdList.size(); j++) {
						String orgSgId = sgIdList.get(j);
						SecurityGroupsBean securityGroup = securityGroupsService.getSercurityGroupsBeanById(orgSgId, tenantId, tokenId);
						String sgName = "";
						if (tenantName.toLowerCase().equals("admin")) {
							sgName = securityGroup.getName()+"-"+name;
						} else {
							sgName = securityGroup.getName().replace(tenantName, name);
						}
						
						SecurityGroupsBean sgBean = new SecurityGroupsBean();
						sgBean.setName(sgName);
						sgBean.setDescription(securityGroup.getDescription());
						sgBean.setTenant_id(projectId);
						
						SecurityGroupsBean securityGroupBean = securityGroupsService.createSercurityGroups(sgBean, tenantId, tokenId);
						request.setAttribute("actionLog", "创建了一个名称为"+sgName+"的安全组");
						String curSgId = securityGroupBean.getId();
						List<SecurityGroupsRuleBean> sgRuleList = securityGroup.getRuleList();
						for (int i=0; i<sgRuleList.size(); i++) {
							SecurityGroupsRuleBean sgRule = sgRuleList.get(i);
							if (sgRule.getProtocol() != null || sgRule.getRemoteStr() != "") {
								sgRule.setSecurityGroupsId(curSgId);
								sgRule.setTenant_id(projectId);
								securityGroupsService.createSercurityGroupRule(sgRule, tenantId, tokenId);
							}
						}
					}
				}
			}			
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("新增项目信息时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		}
		
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	*//**
	 * 
	  * @Title: goUpdateProjectById
	  * @Description: 跳转到租户修改页面
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:33:30
	 *//*
	@RequestMapping("/goUpdateProjectById")
	public String goUpdateProjectById(HttpServletRequest request){
		
		//要编辑租户的tenantId
		String tenantId = request.getParameter("projectId");
		//当前租户的tenantId
		String tenant_id = "";
		String tokenId = "";
		try{
			tenant_id = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		ProjectBean projectBean= null;
		try {
			projectBean = identityService.getProjectById(tenantId, tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("根据id获取项目信息时发生"+e.getMessage()+"异常");
		}
		//获取所有成员
		List<UserBean> userAllList = null;
		List<UserBean> userAllList2 = new ArrayList<UserBean>();
		try {
			userAllList = identityService.getUserList(tenantId, tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取所有成员信息时发生"+e.getMessage()+"异常");
		}
		//获取所有角色role的信息
		List<RoleBean> roleList = null;
		String readRoleId = "";
		try {
			roleList = identityService.getRolesList(tokenId);
			for(RoleBean r: roleList ){
				if(r.getName().equals(roleName)){//默认角色
					readRoleId=r.getId();
				}
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取roleList信息时发生"+e.getMessage()+"异常");
		}
		//项目成员  角色
		Map<UserBean,List<RoleBean>> userMap = null;
		Map<UserBean,List<RoleBean>> userMap2 = new HashMap<UserBean, List<RoleBean>>();
		String userIds = new String();
		String roleIds ="";
		try {
			userMap = identityService.getUserMapListByTenantId(tenantId, tokenId);
			//获取Map的所有key
			Set<UserBean> set = userMap.keySet();
			List<String> userNameList = new ArrayList<String>();
			for (UserBean u : set) {
				//获得该成员的所有角色
				List<RoleBean> rList = userMap.get(u);
				//获得该成员的角色的名字集合
				List<String> roleNameList = new ArrayList<String>();
				for (RoleBean roleBean : rList) {
					if (!roleNameList.contains(roleBean.getName())) {
						roleNameList.add(roleBean.getName());
					}
				}
				//获得该项目下成员的所有名字集合
				if (!userNameList.contains(u.getName())) {
					userNameList.add(u.getName());
					userIds = userIds+";"+u.getId();
				}
				//将成员本来的角色添加标志位，
				for (int i=0; i<rList.size(); i++) {
					RoleBean role = rList.get(i);
					role.setFlag("flag");
					roleIds = roleIds+";"+userIds+":"+role.getId();
				}
				//将不是该成员的角色添加入该成员，但未加标志位
				for (int j=0; j<roleList.size(); j++) {
					if (!roleNameList.contains(roleList.get(j).getName())) {
						rList.add(roleList.get(j));
					}
				}
				userMap2.put(u, rList);
			}
			//在所有成员中将已选择的成员过滤掉
			for (UserBean userBean : userAllList) {
				if (!userNameList.contains(userBean.getName())) {
					userAllList2.add(userBean);
				}
			}
		} catch (Exception e) {
			logger.error("根据id获取租户信息时发生"+e.getMessage()+"异常");
		}
		//项目 配额
		TenantQuotasBean tenantQuotasBean= null;
		try {
			tenantQuotasBean = identityService.getProjectQuotas(tenantId, tokenId, tenant_id);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("根据id获取租户信息时发生"+e.getMessage()+"异常");
		}
		request.setAttribute("projectBean", projectBean);
		request.setAttribute("tenantQuotas", tenantQuotasBean);
		request.setAttribute("roleList", roleList );
		request.setAttribute("userAllList", userAllList2);
		request.setAttribute("userIds", userIds);
		request.setAttribute("roleIds", roleIds);
		request.setAttribute("userMap2", userMap2);
		request.setAttribute("readRoleId", readRoleId);//默认角色id
		request.setAttribute("readRoleName", roleName);//默认角色name
		return "home/user/sc_opstproject_update";
	}
	*//**
	 * 
	  * @Title: updateProjectById
	  * @Description: 修改租户信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:33:58
	 *//*
	@RequestMapping("/updateProjectById")
	public String updateProjectById(HttpServletRequest request,HttpServletResponse response) throws Exception{ 
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String projectid =request.getParameter("projectid");
		String name =request.getParameter("project_name");
		String description=request.getParameter("project_description");
		String domainId=request.getParameter("project_domainId");
		String enabled=request.getParameter("project_enabled");
		
		
		//项目租户配额
		String metadata_items = request.getParameter("metadata_items");
		String cores = request.getParameter("cores");
		String instances = request.getParameter("instances");
		String injected_files = request.getParameter("injected_files");
		String injected_file_content_bytes = request.getParameter("file_content_bytes");
		String volumes = request.getParameter("volumes");
		String volume_snapshots = request.getParameter("volume_snapshots");
		String volume_snapshots_size = request.getParameter("volume_snapshots_size");
		String ram = request.getParameter("ram");
		String security_groups = request.getParameter("security_groups");
		String security_group_rules = request.getParameter("security_group_rules");
		String floating_ips = request.getParameter("floating_ips");
		String networks = request.getParameter("networks");
		String ports = request.getParameter("ports");
		String routers = request.getParameter("routers");
		String subnets = request.getParameter("subnets");
		
		ProjectBean projectBean = new ProjectBean();
		projectBean.setId(projectid);
		projectBean.setName(name);
		projectBean.setDomain_id(domainId);
		projectBean.setDescription(description);
		projectBean.setEnabled(Boolean.valueOf(enabled));
		
		TenantQuotasBean tenantQuotasbean = new TenantQuotasBean();
		if (projectid != null) {
			tenantQuotasbean.setTenant_id(projectid);
		}
		if (metadata_items!=null){
			tenantQuotasbean.setMetadata_items(Integer.parseInt(metadata_items));
		}
		if (cores != null) {
			tenantQuotasbean.setCores(Integer.parseInt(cores));
		}
		if (instances != null) {
			tenantQuotasbean.setInstances(Integer.parseInt(instances));
		}
		if (injected_files != null){
			tenantQuotasbean.setInjected_files(Integer.parseInt(injected_files));
		}
		if (injected_file_content_bytes != null){
			tenantQuotasbean.setInjected_file_content_bytes(Integer.parseInt(injected_file_content_bytes));
		}
		if (volumes != null) {
			tenantQuotasbean.setVolumes(Integer.parseInt(volumes));
		}
		if (volume_snapshots != null) {
			tenantQuotasbean.setVolume_snapshots(Integer.parseInt(volume_snapshots));
		}
		if (volume_snapshots_size != null) {
			tenantQuotasbean.setVolume_snapshots_size(Integer.parseInt(volume_snapshots_size));
		}
		if (ram != null) {
			tenantQuotasbean.setRam(Integer.parseInt(ram));
		}
		if (security_groups != null) {
			tenantQuotasbean.setSecurity_groups(Integer.parseInt(security_groups));
		}
		if (security_group_rules != null){
			tenantQuotasbean.setSecurity_group_rules(Integer.parseInt(security_group_rules));
		}
		if (floating_ips != null) {
			tenantQuotasbean.setFloating_ips(Integer.parseInt(floating_ips));
		}
		if (networks != null) {
			tenantQuotasbean.setNetworks(Integer.parseInt(networks));
		}
		if (ports != null) {
			tenantQuotasbean.setPorts(Integer.parseInt(ports));
		}
		if (routers != null) {
			tenantQuotasbean.setRouters(Integer.parseInt(routers));
		}
		if (subnets != null) {
			tenantQuotasbean.setSubnets(Integer.parseInt(subnets));
		}
		
		//项目人员 修改前的
		String oldselNetwork = request.getParameter("oldselNetwork");
		//项目人员 修改后的
		String seluserId = request.getParameter("selNetwork");
		String[] userId = seluserId.split(";");
		
		String readRoleId = request.getParameter("readRoleId");
		
		try {
			identityService.updateProjectById(projectBean,tokenId);
			request.setAttribute("actionLog", "更新名称为"+name+"的租户");
			if(oldselNetwork==null || !oldselNetwork.equals(seluserId)){//如果没有修改过租户成员就不处理
				//选中了的用户 处理
				if (userId != null && userId.length>1) {
					for (int i = 1; i < userId.length; i++) {
						//勾选的用户角色  页面disabled='true'则获取不到选中的角色
						String[] roleIds = request.getParameterValues("role"+userId[i]);
						if (roleIds != null) {
							//获得当前该用户下已存在的角色集合
							List<RoleBean> roleList = identityService.getRoleListByProjectIdAndUserId(projectid, userId[i],tokenId);
							//获得当前该用户下已存在的角色的id集合
							List<String> roleIdList = new ArrayList<String>();
							for (RoleBean roleBean : roleList) {
								if (!roleIdList.contains(roleBean.getId())) {
									roleIdList.add(roleBean.getId());
								}
							}
							//若当前用户不存在要求新增的角色，则给它先新增角色
							for (int j = 0; j < roleIds.length; j++) {
								if (!roleIdList.contains(roleIds[j])) {
									System.out.println("更新名称为"+name+"的租户的项目成员"+userId[i]+"角色"+roleIds[j]);
									identityService.createUserRole(userId[i], projectid, roleIds[j],tokenId);
									request.setAttribute("actionLog", "更新名称为"+name+"的租户的项目成员"+userId[i]+"角色"+roleIds[j]);
								}
							}
							//获取用户下已存在的角色集合 : 若一个用户没有一个角色，则移除这个项目中的此用户   || 删除不必要的角色
							List<RoleBean> rList = identityService.getRoleListByProjectIdAndUserId(projectid, userId[i],tokenId);
							for (int k = 0; k < rList.size(); k++) {
								if (userId.length<1 || !Arrays.asList(roleIds).contains(rList.get(k).getId())) {
									identityService.deleteUserRole(userId[i], projectid, rList.get(k).getId(),tokenId);
									request.setAttribute("actionLog", "删除名称为"+name+"的租户的项目成员"+userId[i]+"角色"+rList.get(k).getName());
								}
							}
						}else{//如果没有获取到值   默认给它owner的角色
							if(readRoleId!=null && oldselNetwork.indexOf(userId[i])==-1){
								identityService.createUserRole(userId[i], projectid, readRoleId,tokenId);
								//System.out.println("更新名称为"+name+"的租户的项目成员"+userId[i]+"角色"+readRoleId);
								request.setAttribute("actionLog", "更新名称为"+name+"的租户的项目成员"+userId[i]+"角色"+readRoleId);
							}
						}
						
					}
				}
				//如果之前选中的用户删除,现在取消选中,则需要删除用户的角色
				List<UserBean> list = identityService.getUserListByTenantId(projectid,tokenId);
				for(UserBean ub:list){
					if(userId==null || userId.length<=1 || !seluserId.contains(ub.getId())){
						List<RoleBean> rList = identityService.getRoleListByProjectIdAndUserId(projectid, ub.getId(),tokenId);
						for (int k = 0; k < rList.size(); k++) {
							//System.out.println("删除名称为"+name+"的租户的项目成员"+ub.getName()+"角色"+rList.get(k).getName());
							identityService.deleteUserRole(ub.getId(), projectid, rList.get(k).getId(),tokenId);
							request.setAttribute("actionLog", "删除名称为"+name+"的租户的项目成员"+ub.getName()+"角色"+rList.get(k).getName());
						}
					}
				}
			}
			
			//配额
			identityService.createProjectQuotas(tenantQuotasbean, tenantId, tokenId);
			request.setAttribute("actionLog", "更新了一个名称为"+name+"的租户的配额");
			
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("更新项目信息时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		}
		
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	*//**
	 * 
	  * @Title: deleteOpstProjectById
	  * @Description: 删除项目
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:34:27
	 *//*
	@RequestMapping("/deleteOpstProjectById")
	public String deleteOpstProjectById(HttpServletRequest request,HttpServletResponse response)throws Exception{ 		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String projectId = request.getParameter("projectId");		
		String[] instId = projectId.split(",");
		try {
			for (int i=0; i<instId.length; i++) {
				String tenantId = instId[i];
				identityService.deleteProjectById(tenantId, tokenId);				
				request.setAttribute("actionLog", "删除ID为"+tenantId+"的租户");
				//删除该租户下的所有安全组
				List<SecurityGroupsBean> sgList = securityGroupsService.getSercurityGroupsList(tenantId, tokenId);
				for (int j=0; j<sgList.size(); j++) {
					SecurityGroupsBean sg = sgList.get(j);
					securityGroupsService.deteleSecurityGroupsById(sg.getId(), tenantId, tokenId);
					request.setAttribute("actionLog", "删除了一个名称为"+sg.getName()+"的安全组");
				}
			}
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=1';</script>");	
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			logger.error("删除项目时发生"+e.getMessage()+"异常");
			response.getWriter().print("<script>window.location.href='getAllOpstProjectList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");	
		}
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: getRolesByProjectIdAndUserId
	  * @Description:根据用户ID获取角色 
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:34:34
	 *//*
	@RequestMapping("/getRolesByProjectIdAndUserId")
	public String getRolesByProjectIdAndUserId(HttpServletRequest request,HttpServletResponse response) throws Exception { 	
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String projectId = request.getParameter("projectId");		
		String userId = request.getParameter("userId");	
		JSONObject object = new JSONObject();		
		List<RoleBean> userRole = null;
		try {
			userRole = identityService.getRoleListByProjectIdAndUserId(projectId,userId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取用户拥有的角色时发生"+e.getMessage()+"异常");
		}
		object.put("userRole", userRole);		
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}	
	
	*//**
	 * 
	  * @Title: getMyTenantDetail
	  * @Description: 我的租户信息
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:34:41
	 *//*
	@RequestMapping("/getMyTenantDetail")
	public String getMyTenantDetail(HttpServletRequest request){ 
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		ProjectBean projectBean= null;
		try {
			projectBean = identityService.getProjectById(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("根据id获取项目信息时发生"+e.getMessage()+"异常");
		}
		//项目成员  角色
		Map<UserBean,List<RoleBean>> userMap = null;
		try {
			userMap = identityService.getUserMapListByTenantId(tenantId,tokenId);
		} catch (Exception e) {
			logger.error("根据id获取租户信息时发生"+e.getMessage()+"异常");
		}
		//项目 配额
		TenantQuotasBean tenantQuotasBean= null;
		try {
			tenantQuotasBean = identityService.getProjectQuotas(tenantId, tokenId, tenantId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("根据id获取租户信息时发生"+e.getMessage()+"异常");
		}
		request.setAttribute("projectBean", projectBean);
		request.setAttribute("tenantQuotas", tenantQuotasBean);
		request.setAttribute("userMap", userMap);
		return "home/user/sc_tenant_detail";
	}*/


}