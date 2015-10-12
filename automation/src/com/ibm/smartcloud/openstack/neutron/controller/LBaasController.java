/*package com.ibm.smartcloud.openstack.neutron.controller;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.glance.service.ImageService;
import com.ibm.smartcloud.openstack.neutron.service.NetworkService;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;

*//**
 * 
 * @Title：LBaasController 
 * @Description:    负载均衡模块
 * @Auth: ZhangLong
 * @CreateTime:Mar 25, 2015 5:46:06 PM     
 * @version V1.0
 *//*
@Controller
public class LBaasController extends BaseController{
	
	@Autowired
	private NetworkService networkService;
	@Autowired
	private InstanceService instanceServerService;
	@Autowired
	private ImageService imageService;
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	
	*//**
	 * 
	  * @Title: getPoolList
	  * @Description: 获取所有资源池
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:46:32 PM
	 *//*
	@RequestMapping("/getPoolList")
	public String getPoolList(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		
		List<PoolBean> pools = new ArrayList<PoolBean>();
		try {
			pools = lBaasService.getPoolList(tenantId, tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		model.addAttribute("pools", pools);
		
		return "home/ec2/ec2_lbaas_pool_list";
	}
	
	*//**
	 * 
	  * @Title: getPoolDetails
	  * @Description: 根据ID获取资源池的详细信息
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:47:03 PM
	 *//*
	@RequestMapping("/getPoolDetails")
	public String getPoolDetails(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		try {
			PoolBean pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> servers = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			List<MemberBean> members = lBaasService.getMemberList(tenantId, tokenId);
			model.addAttribute("pool", pool);
			model.addAttribute("servers", servers);
			model.addAttribute("members", members);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_pool_details";
	}
	
	*//**
	 * 
	  * @Title: toCreatePool
	  * @Description: 去创建资源池页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:47:48 PM
	 *//*
	@RequestMapping("/toCreatePool")
	public String toCreatePool(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		try{
			List<SubnetBean> subnetList = networkService.getSubnetList(tenantId, tokenId);
			List<NetworkBean> networkList = networkService.getNetworkList(tenantId, tokenId);
			List<SubnetBean> subnets = new ArrayList<SubnetBean>();
			for(int j=0; j<networkList.size(); j++){
				NetworkBean n = networkList.get(j);
				if(n.isNetRouter_external() != true){
					for(int i=0; i<subnetList.size(); i++){
						SubnetBean s = subnetList.get(i);
						if(s.getSubnet_network_id().equals(n.getNetId())){
							subnets.add(s);
						}
					}
				}
			}
			model.addAttribute("subnets", subnets);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_pool_create";
	}

	*//**
	 * 
	  * @Title: createPool
	  * @Description: 创建资源池
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:48:07 PM
	 *//*
	@RequestMapping("/createPool")
	public String createPool(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String provider = request.getParameter("provider");
		String subnet_id = request.getParameter("subnet_id");
		String protocol = request.getParameter("protocol");
		String lb_method = request.getParameter("lb_method");
		String admin_state_up = request.getParameter("admin_state_up");
		PoolBean pool = new PoolBean();
		pool.setName(name);
		pool.setDescription(description);
		pool.setProvider(provider);
		pool.setSubnet_id(subnet_id);
		pool.setProtocol(protocol);
		pool.setLb_method(lb_method);
		if("true".equals(admin_state_up)){
			pool.setAdmin_state_up(true);
		}else{
			pool.setAdmin_state_up(false);
		}
		try{
			PoolBean poolBean = lBaasService.createPool(tenantId, tokenId, pool);
			request.setAttribute("actionLog", "创建了一个名称为"+name+"的资源池");
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getPoolList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return "";
	}
	
	*//**
	 * 
	  * @Title: toUpdatePool
	  * @Description: 去更新资源池页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:48:28 PM
	 *//*
	@RequestMapping("/toUpdatePool")
	public String toUpdatePool(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		try {
			PoolBean pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
			model.addAttribute("pool", pool);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_pool_update";
	}
	
	*//**
	 * 
	  * @Title: updatePool
	  * @Description: 更新资源池
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:48:45 PM
	 *//*
	@RequestMapping("/updatePool")
	public String updatePool(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		PoolBean pool = new PoolBean();
		pool.setId(poolId);
		pool.setName(name);
		pool.setDescription(description);
		try {
			PoolBean p = lBaasService.updatePool(tenantId, tokenId, pool);
			request.setAttribute("actionLog", "更新了一个名称为"+name+"的资源池");
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: deletePool
	  * @Description: 删除资源池
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:49:00 PM
	 *//*
	@RequestMapping("/deletePool")
	public String deletePool(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolIds = request.getParameter("poolIds");
		String[] poolArr = poolIds.split(",");
		try {
			for(int i=0; i<poolArr.length; i++){
				String flag = lBaasService.deletePool(tenantId, tokenId, poolArr[i]);
				request.setAttribute("actionLog", "删除了一个ID为"+poolArr[i]+"的资源池");
			}
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toAssociateHealthMonitorToPool
	  * @Description: 去关联健康监控页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:49:22 PM
	 *//*
	@RequestMapping("/toAssociateHealthMonitorToPool")
	public String toAssociateHealthMonitorToPool(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		List<HealthMonitorBean> healthMonitors = new ArrayList<HealthMonitorBean>();
		try {
//			PoolBean pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
			healthMonitors = lBaasService.getHealthMonitorList(tenantId, tokenId);
//			for(int i=0; i<healthMonitors.size(); i++){
//				for(int j=0; j<pool.getHealth_monitors_status().size(); j++){
//					if(healthMonitors.get(i).getId().equals(pool.getHealth_monitors_status().get(j).getMonitor_id())){
//						healthMonitors.remove(i);
//					}
//				}
//			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		model.addAttribute("poolId", poolId);
		model.addAttribute("healthMonitors", healthMonitors);
		
		return "home/ec2/ec2_lbaas_pool_associateHealthMonitor";
	}
	
	*//**
	 * 
	  * @Title: associateHealthMonitorToPool
	  * @Description: 资源池关联健康监控
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:50:57 PM
	 *//*
	@RequestMapping("/associateHealthMonitorToPool")
	public String associateHealthMonitorToPool(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		String healthMonitorId = request.getParameter("healthMonitorId");
		
		try{
			String flag = lBaasService.AssociatesHealthMonitorToPool(tenantId, tokenId, poolId, healthMonitorId);
			request.setAttribute("actionLog", "关联监控到资源池"+poolId);
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toDisassociateHealthMonitorFromPool
	  * @Description: 去解除健康监控页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:51:29 PM
	 *//*
	@RequestMapping("/toDisassociateHealthMonitorFromPool")
	public String toDisassociateHealthMonitorFromPool(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		List<HealthMonitorBean> healthMonitors = new ArrayList<HealthMonitorBean>();
		try {
			PoolBean pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
			for(int i=0; i<pool.getHealth_monitors_status().size(); i++){
				healthMonitors.add(lBaasService.getHealthMonitorDetails(tenantId, tokenId, pool.getHealth_monitors_status().get(i).getMonitor_id()));
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		model.addAttribute("poolId", poolId);
		model.addAttribute("healthMonitors", healthMonitors);
		
		return "home/ec2/ec2_lbaas_pool_disassociateHealthMonitor";
	}
	
	*//**
	 * 
	  * @Title: disassociateHealthMonitorFromPool
	  * @Description: 解除健康监控
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:52:18 PM
	 *//*
	@RequestMapping("/disassociateHealthMonitorFromPool")
	public String disassociateHealthMonitorFromPool(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		String healthMonitorId = request.getParameter("healthMonitorId");
		
		try{
			String flag = lBaasService.DisassociatesHealthMoniterFromPool(tenantId, tokenId, poolId, healthMonitorId);
			request.setAttribute("actionLog", "从资源池上取消监控"+poolId);
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getPoolList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: getHealthMonitorList
	  * @Description: 获取所有健康监控
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:52:40 PM
	 *//*
	@RequestMapping("/getHealthMonitorList")
	public String getHealthMonitorList(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		
		List<HealthMonitorBean> healthMonitors = new ArrayList<HealthMonitorBean>();
		try {
			healthMonitors = lBaasService.getHealthMonitorList(tenantId, tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		model.addAttribute("healthMonitors", healthMonitors);
		
		return "home/ec2/ec2_lbaas_healthMonitor_list";
	}

	*//**
	 * 
	  * @Title: getHealthMonitorDetails
	  * @Description: 根据ID获取健康监控详细信息
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:53:05 PM
	 *//*
	@RequestMapping("/getHealthMonitorDetails")
	public String getHealthMonitorDetails(Model model, HttpServletRequest request, HttpServletResponse response){

		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String healthMonitorId = request.getParameter("healthMonitorId");
		try {
			HealthMonitorBean healthMonitor = lBaasService.getHealthMonitorDetails(tenantId, tokenId, healthMonitorId);
			model.addAttribute("healthMonitor", healthMonitor);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_healthMonitor_details";
	}
	
	*//**
	 * 
	  * @Title: toCreateHealthMonitor
	  * @Description: 去创建健康监控页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:53:34 PM
	 *//*
	@RequestMapping("/toCreateHealthMonitor")
	public String toCreateHealthMonitor(Model model, HttpServletRequest request, HttpServletResponse response){
		
		return "home/ec2/ec2_lbaas_healthMonitor_create";
	}
	
	*//**
	 * 
	  * @Title: createHealthMonitor
	  * @Description: 创建健康监控
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:53:53 PM
	 *//*
	@RequestMapping("/createHealthMonitor")
	public String createHealthMonitor(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String delay = request.getParameter("delay");
		String max_retries = request.getParameter("max_retries");
		String type = request.getParameter("type");
		String timeout = request.getParameter("timeout");
		String admin_state_up = request.getParameter("admin_state_up");
		String expected_codes = request.getParameter("expected_codes");
		String http_method = request.getParameter("http_method");
		String url_path = request.getParameter("url_path");
		HealthMonitorBean healthMonitor = new HealthMonitorBean();
		healthMonitor.setDelay(delay);
		healthMonitor.setMax_retries(max_retries);
		healthMonitor.setType(type);
		healthMonitor.setTimeout(timeout);
		healthMonitor.setExpected_codes(expected_codes);
		healthMonitor.setHttp_method(http_method);
		healthMonitor.setUrl_path(url_path);

		if("true".equals(admin_state_up)){
			healthMonitor.setAdmin_state_up(true);
		}else{
			healthMonitor.setAdmin_state_up(false);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try{
			HealthMonitorBean healthMonitorBean = lBaasService.createHealthMonitor(tenantId, tokenId, healthMonitor);
			request.setAttribute("actionLog", "创建健康监控"+healthMonitor);
			response.getWriter().print("<script>window.location.href='getHealthMonitorList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getHealthMonitorList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toUpdateHealthMonitor
	  * @Description: 去更新健康监控页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:54:14 PM
	 *//*
	@RequestMapping("/toUpdateHealthMonitor")
	public String toUpdateHealthMonitor(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String healthMonitorId = request.getParameter("healthMonitorId");
		try {
			HealthMonitorBean healthMonitor = lBaasService.getHealthMonitorDetails(tenantId, tokenId, healthMonitorId);
			model.addAttribute("healthMonitor", healthMonitor);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_healthMonitor_update";
	} 
	
	*//**
	 * 
	  * @Title: updateHealthMonitor
	  * @Description: 更新健康监控
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:54:32 PM
	 *//*
	@RequestMapping("/updateHealthMonitor")
	public String updateHealthMonitor(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String healthMonitorId = request.getParameter("healthMonitorId");
		String delay = request.getParameter("delay");
		String timeout = request.getParameter("timeout");
		String max_retries = request.getParameter("max_retries");
		HealthMonitorBean hmb = new HealthMonitorBean();
		hmb.setId(healthMonitorId);
		hmb.setDelay(delay);
		hmb.setTimeout(timeout);
		hmb.setMax_retries(max_retries);
		try {
			HealthMonitorBean healthMonitorBean = lBaasService.updateHealthMonitor(tenantId, tokenId, hmb);
			request.setAttribute("actionLog", "更新健康监控"+healthMonitorId);
			response.getWriter().print("<script>window.location.href='getHealthMonitorList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getHealthMonitorList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}

	*//**
	 * 
	  * @Title: deleteHealthMonitor
	  * @Description: 删除健康监控
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:54:50 PM
	 *//*
	@RequestMapping("/deleteHealthMonitor")
	public String deleteHealthMonitor(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String healthMonitorIds = request.getParameter("healthMonitorIds");
		String[] healthMonitorArr = healthMonitorIds.split(",");
		try {
			for(int i=0; i<healthMonitorArr.length; i++){
				String flag = lBaasService.deleteHealthMonitor(tenantId, tokenId, healthMonitorArr[i]);
				request.setAttribute("actionLog", "删除了一个ID为"+healthMonitorArr[i]+"健康监控");
			}
			response.getWriter().print("<script>window.location.href='getHealthMonitorList?promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getHealthMonitorList?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: getAllVips
	  * @Description: 获取所有对外VIP
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:55:17 PM
	 *//*
	@RequestMapping("/getAllVips")
	public String getAllVips(HttpServletRequest request, HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		
		try {
			request.setAttribute("vips", lBaasService.getVipList(tenantId, tokenId));
			request.setAttribute("pools", lBaasService.getPoolList(tenantId, tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_vip_list";
	}

	*//**
	 * 
	  * @Title: getVipDetail
	  * @Description: 根据ID查获取VIP详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:55:51 PM
	 *//*
	@RequestMapping("/getVipDetail")
	public String getVipDetail(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String vipId = request.getParameter("vipId");
//		request.setAttribute("vipId", vipId);
		try {
			request.setAttribute("vip", lBaasService.getVipDetails(tenantId, tokenId, vipId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_vip_detail";
	}
	
	*//**
	 * 
	  * @Title: toCreateVip
	  * @Description: 到创建VIP页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:57:02 PM
	 *//*
	@RequestMapping("/toCreateVip")
	public String toCreateVip(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		try {
			List<PoolBean> poolList = lBaasService.getPoolList(tenantId, tokenId);
			List<PoolBean> poolList2 = new ArrayList<PoolBean>();
			for (PoolBean poolBean : poolList) {
				if (poolBean.getTenant_id().equals(tenantId)) {
					poolList2.add(poolBean);
				}
			}
			request.setAttribute("pools", poolList2);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_vip_create";
	}
	
	*//**
	 * 
	  * @Title: createVip
	  * @Description: 创建VIP
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:57:17 PM
	 *//*
	@RequestMapping("/createVip")
	public String createVip(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String name = request.getParameter("vipName");
		String description = request.getParameter("vipDescription");
		String pool_id = request.getParameter("poolId");
		String admin_statue_up = request.getParameter("vip_Admin_state_up");
//		String address = request.getParameter("vipAddress");
		String protocol_port = request.getParameter("vip_protocol_port");
		String protocol = request.getParameter("vip_protocol");
		String persistence_type = request.getParameter("vip_persistence_type");
		String persistence_cookie_name = request.getParameter("vip_persistence_cookie_name");
		String connection_limit = request.getParameter("vip_connection_limit");
		
		VIPBean vipBeanRequest = new VIPBean();
		SessionPersistenceBean spBean = new SessionPersistenceBean();
		
		if (name != "") {
			vipBeanRequest.setName(name);	
		}
		
		if (description != "") {
			vipBeanRequest.setDescription(description);
		}
		
		if (pool_id != "") {
			vipBeanRequest.setPool_id(pool_id);
			String subnet_id = lBaasService.getPoolDetails(tenantId, tokenId, pool_id).getSubnet_id();
			vipBeanRequest.setSubnet_id(subnet_id);
		}
		
		if ("checked".equals(admin_statue_up)) {
			vipBeanRequest.setAdmin_state_up(true);
		}
		
//			if (address != "") {
//				vipBeanRequest.setAddress(address);
//			}
			
		if (protocol_port != "") {
			vipBeanRequest.setProtocol_port(Integer.parseInt(protocol_port));
		}
			
		if (protocol != "") {
			vipBeanRequest.setProtocol(protocol);
		}
		
		if (persistence_type != "") {
			spBean.setType(persistence_type);
		}
		if (persistence_cookie_name != "") {
			spBean.setCookie_name(persistence_cookie_name);
		}
		vipBeanRequest.setSession_persistence(spBean);
		
		if (connection_limit != "") {
			vipBeanRequest.setConnection_limit(Integer.parseInt(connection_limit));
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			VIPBean vipBean = lBaasService.createVip(tenantId, tokenId, vipBeanRequest);
			request.setAttribute("actionLog", "创建了一个名称为"+name+"的VIP");
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getAllVips?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toUpdateVip
	  * @Description: 到更新VIP页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:57:34 PM
	 *//*
	@RequestMapping("/toUpdateVip")
	public String toUpdateVip(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		 
		String vipId = request.getParameter("vipId");
		try {
			VIPBean vipBean = lBaasService.getVipDetails(tenantId, tokenId, vipId);
			PoolBean poolBean = lBaasService.getPoolDetails(tenantId, tokenId, vipBean.getPool_id());
			List<PoolBean> poolList = lBaasService.getPoolList(tenantId, tokenId);
			List<PoolBean> poolList2 = new ArrayList<PoolBean>();
			for (PoolBean pool : poolList) {
				if ("null".equals(pool.getVip_id())) {
					poolList2.add(pool);
				}
			}
			request.setAttribute("vip", vipBean);
			request.setAttribute("pool", poolBean);
			request.setAttribute("pools", poolList2);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_vip_edit";
	}
	
	*//**
	 * 
	  * @Title: updateVip
	  * @Description: 更新VIP
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:57:51 PM
	 *//*
	@RequestMapping("/updateVip")
	public String updateVip(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String vipId = request.getParameter("vipId");
		String name = request.getParameter("vipName");
		String description = request.getParameter("vipDescription");
		String pool_id = request.getParameter("poolId");
		String admin_statue_up = request.getParameter("vip_Admin_state_up");
		String persistence_type = request.getParameter("vip_persistence_type");
		String persistence_cookie_name = request.getParameter("vip_persistence_cookie_name");
		String connection_limit = request.getParameter("vip_connection_limit");
		
		VIPBean vipBeanRequest = new VIPBean();
		SessionPersistenceBean spBean = new SessionPersistenceBean();
		
		vipBeanRequest.setId(vipId);
		
		if (name != "") {
			vipBeanRequest.setName(name);	
		}
		
		if (description != "") {
			vipBeanRequest.setDescription(description);
		}
		
		if (pool_id != "") {
			vipBeanRequest.setPool_id(pool_id);
			String subnet_id = lBaasService.getPoolDetails(tenantId, tokenId, pool_id).getSubnet_id();
			vipBeanRequest.setSubnet_id(subnet_id);
		}
		
		if ("checked".equals(admin_statue_up)) {
			vipBeanRequest.setAdmin_state_up(true);
		}
		
		if (persistence_type != "") {
			spBean.setType(persistence_type);
		}
		if (persistence_cookie_name != "") {
			spBean.setCookie_name(persistence_cookie_name);
		}
		vipBeanRequest.setSession_persistence(spBean);
		
		if (connection_limit == "") {
			vipBeanRequest.setConnection_limit(-1);
		} else {
			vipBeanRequest.setConnection_limit(Integer.parseInt(connection_limit));
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			VIPBean vipBean = lBaasService.updateVip(tenantId, tokenId, vipBeanRequest);
			request.setAttribute("actionLog", "更新了一个名称为"+name+"的VIP");
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: deleteVipById
	  * @Description: 删除VIP
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:58:08 PM
	 *//*
	@RequestMapping("/deleteVipById")
	public String deleteVipById(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String vipId = request.getParameter("vipId");
		String[] vipIds = vipId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		VIPBean vip = null;
		String poolId = null;
		PoolBean pool = null;
		
		try {
			for (int i = 0; i< vipIds.length; i++) {
				//[新增]   删除VIP时顺带要删除此VIP所对应的资源池里的成员
				vip = lBaasService.getVipDetails(tenantId, tokenId, vipId);
				String flag = lBaasService.deleteVip(tenantId, tokenId, vipIds[i]);
				request.setAttribute("actionLog", "删除了一个ID为"+vipIds[i]+"的VIP");
				//[新增]   删除VIP时顺带要删除此VIP所对应的资源池里的成员
				poolId = vip.getPool_id();
				pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
				List<String> members = pool.getMembers();
				for(int j=0; j<members.size(); j++) {
					lBaasService.deleteMember(tenantId, tokenId, members.get(j));
					request.setAttribute("actionLog", "删除了一个ID为"+members.get(j)+"成员");
				}
			}
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: getAllMembers
	  * @Description: 获取所有成员
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:58:29 PM
	 *//*
	@RequestMapping("/getAllMembers")
	public String getAllMembers(HttpServletRequest request, HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
//		String currentHypervisor = request.getParameter("hypervisor_type");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
//		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE, currentHypervisor);
		
		try {
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<MemberBean> memberList = lBaasService.getMemberList(tenantId, tokenId);
			List<ServerDetailBean> serverList = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			List<VIPBean> vipList = lBaasService.getVipList(tenantId, tokenId);
			request.setAttribute("members", memberList);
			request.setAttribute("servers", serverList);
			request.setAttribute("vips", vipList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_member_list";
	}
	
	*//**
	 * 
	  * @Title: getMemberDetail
	  * @Description: 根据ID获取成员详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:59:08 PM
	 *//*
	@RequestMapping("/getMemberDetail")
	public String getMemberDetail(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String memberId = request.getParameter("memberId");
		String instanceId = request.getParameter("instanceId");
//		request.setAttribute("memberId", memberId);
		try {
			MemberBean memberBean = lBaasService.getMemberDetails(tenantId, tokenId, memberId);
			ServerDetailBean serverBean = instanceServerService.instanceServerDetail(instanceId, tenantId, tokenId);
			PoolBean poolBean = lBaasService.getPoolDetails(tenantId, tokenId, memberBean.getPool_id());
			request.setAttribute("member", memberBean);
			request.setAttribute("server", serverBean);
			request.setAttribute("pool", poolBean);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_member_detail";
	}
	
	*//**
	 * 
	  * @Title: toCreateMember
	  * @Description: 到创建成员页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:59:37 PM
	 *//*
	@RequestMapping("/toCreateMember")
	public String toCreateMember(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,currentHypervisor);
		
		List<PoolBean> poolList = lBaasService.getPoolList(tenantId, tokenId);
		request.setAttribute("pools", poolList);
		return "home/ec2/ec2_lbaas_member_create";
	}
	
	*//**
	 * 
	  * @Title: createMember
	  * @Description: 创建成员
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:59:57 PM
	 *//*
	@RequestMapping("/createMember")
	public String createMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String pool_id = request.getParameter("poolId");
		String weight = request.getParameter("memberWeight");
		String address = request.getParameter("memberAddress");
		String protocol_port = request.getParameter("member_protocol_port");
		String admin_statue_up = request.getParameter("member_Admin_state_up");
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE, currentHypervisor);
		
		MemberBean memberBeanRequest = new MemberBean();
		
		if (pool_id != "") {
			memberBeanRequest.setPool_id(pool_id);
		}
		
		if (weight != "") {
			memberBeanRequest.setWeight(Integer.parseInt(weight));
		}
		
		if (address != "") {
			memberBeanRequest.setAddress(address);
		}
		
		if (protocol_port != "") {
			memberBeanRequest.setProtocol_port(Integer.parseInt(protocol_port));
		}
		
		if ("checked".equals(admin_statue_up)) {
			memberBeanRequest.setAdmin_state_up(true);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			MemberBean memberBean = lBaasService.createMember(tenantId, tokenId, memberBeanRequest);
			request.setAttribute("actionLog", "创建了一个地址为"+address+"的成员");
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=1&hypervisor_type="+currentHypervisor+"';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type="+currentHypervisor+"&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toCreateInstanceMember
	  * @Description: 去创建实例成员页面(根据实例IP地址)
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:00:16 PM
	 *//*
	@RequestMapping("/toCreateInstanceMember")
	public String toCreateInstanceMember(HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		List<VIPBean> vipList = lBaasService.getVipList(tenantId, tokenId);
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
//		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
//		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,currentHypervisor);
		try {
			if(SpringHelper.getRequest().getSession()!=null&&SpringHelper.getRequest().getSession().getAttribute("tenantId")!=null){
				tenantId = SpringHelper.getRequest().getSession().getAttribute("tenantId").toString();
			}else{
				throw new NullPointerException("登陆超时");
			}
		} catch (Exception e) {
			return "redirect:/gologin";
		}
		
		List<ServerDetailBean> list = null;

		try {
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			list = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		//查询已关联成员的实例的IP地址集合
		List<MemberBean> memberList = lBaasService.getMemberList(tenantId, tokenId);
		List<String> memberAddressList = new ArrayList<String>();
		if(memberList.size() > 0) {
			for (MemberBean memberBean : memberList) {
				if (!memberAddressList.contains(memberBean.getAddress())) {
					memberAddressList.add(memberBean.getAddress());
				}
			}
		}
		
		List<ServerDetailBean> list2 = new ArrayList<ServerDetailBean>();
		for (ServerDetailBean serverDetailBean : list) {
			if(serverDetailBean.getAddresses().size() > 0) {
				for (int j=0; j<serverDetailBean.getAddresses().size(); j++) {
					if (!memberAddressList.contains(serverDetailBean.getAddresses().get(j).getAddr())) {
//					serverDetailBean.getAddresses().remove(serverDetailBean.getAddresses().get(j));
//					j--;
						list2.add(serverDetailBean);
						break;
					} 
				}
			}
//			if (serverDetailBean.getAddresses().size() > 0) {
//				list2.add(serverDetailBean);
//			}
		}
		request.setAttribute("servers", list2);
		
		List<PortBean> portList = networkService.getPortList(tenantId, tokenId);
		List<HealthMonitorBean> healthMonitorList = lBaasService.getHealthMonitorList(tenantId, tokenId);
		
		request.setAttribute("vips", vipList);
		request.setAttribute("ports", portList);
		request.setAttribute("healthMonitors", healthMonitorList);
		return "home/ec2/ec2_lbaas_member_create_new";
	}
	
	*//**
	 * 
	  * @Title: createInstanceMember
	  * @Description: 创建实例成员(根据实例IP地址)
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:01:00 PM
	 *//*
	@RequestMapping("/createInstanceMember")
	public String createInstanceMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String pool_id = request.getParameter("poolId");
		String weight = request.getParameter("memberWeight");
		String protocol_port = request.getParameter("member_protocol_port");
		String admin_statue_up = request.getParameter("member_Admin_state_up");
		String[] addressArr = request.getParameterValues("memberAddress");
		List<String> addressList = new ArrayList<String>();
		for (int i=0; i<addressArr.length; i++) {
			String address = addressArr[i];
			address = address.substring(0, address.length()-1);
			if (address.contains("@")) {
				String[] addressArr2 = address.split("@");
				for (int j=0; j<addressArr2.length; j++) {
					addressList.add(addressArr2[j]);
				}
			} else {
				addressList.add(address);
			}
		}
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE, currentHypervisor);
		
		MemberBean memberBeanRequest = new MemberBean();
		
		if (addressList.size() > 0) {
			if (pool_id != "") {
				memberBeanRequest.setPool_id(pool_id);
			}
			if (weight != "") {
				memberBeanRequest.setWeight(Integer.parseInt(weight));
			}
			if (protocol_port != "") {
				memberBeanRequest.setProtocol_port(Integer.parseInt(protocol_port));
			}
			if ("checked".equals(admin_statue_up)) {
				memberBeanRequest.setAdmin_state_up(true);
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
//			String s = "";
			for(int k=0; k<addressList.size(); k++){
				if (addressList.get(k) != "") {
					String addr = addressList.get(k);
					memberBeanRequest.setAddress(addr);
					MemberBean memberBean = lBaasService.createMember(tenantId, tokenId, memberBeanRequest);
//					s = memberBean.getErrorMessage();
//					if(s != ""){
//						break;
//					} else{
						request.setAttribute("actionLog", "创建了一个地址为"+addr+"的成员");
//					}
				}
			}
//			if (s != "") {
//				response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type="+currentHypervisor+"';</script>");
//			} else {
				response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=1&hypervisor_type="+currentHypervisor+"';</script>");
//			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type="+currentHypervisor+"&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toUpdateMember
	  * @Description: 到更新成员页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:01:24 PM
	 *//*
	@RequestMapping("/toUpdateMember")
	public String toUpdateMember(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String memberId = request.getParameter("memberId");
		try {
			MemberBean memberBean = lBaasService.getMemberDetails(tenantId, tokenId, memberId);
			//此成员对应的资源池
			PoolBean poolBean = lBaasService.getPoolDetails(tenantId, tokenId, memberBean.getPool_id());
			//此资源池对应的绑定的VIP
			VIPBean vipBean = lBaasService.getVipDetails(tenantId, tokenId, poolBean.getVip_id());
			//查询所有VIP
			List<VIPBean> vipBeanList = lBaasService.getVipList(tenantId, tokenId);
			//查询所有虚机
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> serverBeanList = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			
			request.setAttribute("member", memberBean);
			request.setAttribute("vip", vipBean);
			request.setAttribute("vips", vipBeanList);
			request.setAttribute("servers", serverBeanList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_member_edit";
	}
	
	*//**
	 * 
	  * @Title: updateMember
	  * @Description: 更新成员
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:01:42 PM
	 *//*
	@RequestMapping("/updateMember")
	public String updateMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String memberId = request.getParameter("memberId");
		String pool_id = request.getParameter("poolId");
		String weight = request.getParameter("memberWeight");
		String admin_statue_up = request.getParameter("member_Admin_state_up");
		
		MemberBean memberBeanRequest = new MemberBean();
		
		memberBeanRequest.setId(memberId);
		
		if (pool_id != "") {
			memberBeanRequest.setPool_id(pool_id);
		}
		
		if (weight != "") {
			memberBeanRequest.setWeight(Integer.parseInt(weight));
		} else {
			memberBeanRequest.setWeight(0);
		}
		
		if ("checked".equals(admin_statue_up)) {
			memberBeanRequest.setAdmin_state_up(true);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			MemberBean memberBean = lBaasService.updateMember(tenantId, tokenId, memberBeanRequest);
			request.setAttribute("actionLog", "更新一个poolId为"+pool_id+"成员");
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: deleteMemberById
	  * @Description: 删除成员
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:01:59 PM
	 *//*
	@RequestMapping("/deleteMemberById")
	public String deleteMemberById(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String memberId = request.getParameter("memberId");
		String[] memberIds = memberId.split(",");
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,currentHypervisor);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			for (int i = 0; i < memberIds.length; i++) {
				String flag = lBaasService.deleteMember(tenantId, tokenId, memberIds[i]);
				request.setAttribute("actionLog", "删除了一个ID为"+memberIds[i]+"成员");
			}
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=1&hypervisor_type="+currentHypervisor+"';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type="+currentHypervisor+"&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: configLbaas
	  * @Description: 去配置负载均衡导航页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:02:18 PM
	 *//*
	@RequestMapping("/configLbaas")
	public String configLbaas(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,currentHypervisor);
		
		return "home/ec2/ec2_lbaas_member_configLbaas";
	}
	
	*//**
	 * 
	  * @Title: toAddInstanceMember
	  * @Description: 去自动增加虚机成员页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:02:35 PM
	 *//*
	@RequestMapping("/toAddInstanceMember")
	public String toAddInstanceMember(HttpServletRequest request, HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE, currentHypervisor);
		
		try {
			List<MemberBean> memberList = lBaasService.getMemberList(tenantId, tokenId);
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> serverList = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			request.setAttribute("members", memberList);
			request.setAttribute("servers", serverList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		
		return "home/ec2/ec2_lbaas_instance_member_add";
	}
	
	*//**
	 * 
	  * @Title: autoCreateMember
	  * @Description: 自动增加虚机成员(自动伸张)
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:03:01 PM
	 *//*
	@RequestMapping("/autoCreateMember")
	public String autoCreateMember(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String memberId = request.getParameter("memberId");
		String instanceId = request.getParameter("instanceId");
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,currentHypervisor);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			String address = instanceServerService.createServerById(instanceId, tenantId, tokenId);
			if(address != ""){
				MemberBean memberBeanRequest = lBaasService.getMemberDetails(tenantId, tokenId, memberId);
				memberBeanRequest.setId("");
				memberBeanRequest.setAddress(address);
				MemberBean memberBean = lBaasService.createMember(tenantId, tokenId, memberBeanRequest);
//				String s = memberBean.getErrorMessage();
//				if (s != "") {
//					response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type=" + currentHypervisor + "';</script>");
//				} else {
					request.setAttribute("actionLog", "自动伸张一个实例ID为"+instanceId+"成员");
					response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=1&hypervisor_type="+currentHypervisor+"';</script>");
//				}
//			} else {
//				response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type="+currentHypervisor+"';</script>");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllMembers?promptBoxFlag=-1&hypervisor_type="+currentHypervisor+"&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toDeleteInstanceMember
	  * @Description: 去自动缩减虚机成员页面
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:03:48 PM
	 *//*
	@RequestMapping("/toDeleteInstanceMember")
	public String toDeleteInstanceMember(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String currentHypervisor = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,currentHypervisor);
		
		try {
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> servers = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			List<MemberBean> members = lBaasService.getMemberList(tenantId, tokenId);
			model.addAttribute("servers", servers);
			model.addAttribute("members", members);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		
		return "home/ec2/ec2_lbaas_instance_member_delete";
	}
	
	*//**
	 * 
	  * @Title: getLoadBalancerDetail
	  * @Description: 根据ID查获取负载均衡器详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 14, 2015 6:51:49 PM
	 *//*
	@RequestMapping("/getLoadBalancerDetail")
	public String getLoadBalancerDetail(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		String type = request.getParameter("type");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("type", type);
		
		String vipId = request.getParameter("vipId");
		
		VIPBean vip = null;
		String poolId = null;
		PoolBean pool = null;
		HealthMonitorBean healthMonitor = null;
		String healthMonitorId = null;
		List<InstanceInfoVOBean> servers = new ArrayList<InstanceInfoVOBean>();
		int num = 0;
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("project_id", tenantId);
		params.put("hypervisorType", "KVM");
		
		try {
			vip = lBaasService.getVipDetails(tenantId, tokenId, vipId);
			poolId = vip.getPool_id();
			pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
			if(pool.getHealth_monitors().size() > 0) {
				healthMonitorId = pool.getHealth_monitors().get(0);
				healthMonitor = lBaasService.getHealthMonitorDetails(tenantId, tokenId, healthMonitorId);
			}
			List<InstanceInfoVOBean> networks = instanceInfoDBService.getAllNetWorkInfo();
			List<String> members = pool.getMembers();
			for(int i=0; i<members.size(); i++) {
				MemberBean member = lBaasService.getMemberDetails(tenantId, tokenId, members.get(i));
				for(int k=0; k<networks.size(); k++) {
					InstanceInfoVOBean network = networks.get(k);
					if(member != null && member.getAddress().equals(network.getIpAddress())) {
						InstanceInfoVOBean server = instanceInfoDBService.getInstanceInfoByIdDB(network.getProject_id());
						server.setIpAddress(member.getAddress());
						servers.add(server);
						num++;
					} else if(member != null && member.getAddress() != network.getIpAddress() && num == 0 && k == networks.size()-1) {
						InstanceInfoVOBean server = new InstanceInfoVOBean();
						server.setIpAddress(member.getAddress());
						server.setVm_state("此实例已删除");
						servers.add(server);
					}
				}
			}
			request.setAttribute("servers", servers);
			DbContextHolder.clearDbType();
			request.setAttribute("vip", vip);
			request.setAttribute("pool", pool);
			request.setAttribute("healthMonitor", healthMonitor);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_loadbalancer_detail";
	}
	
	*//**
	 * 
	  * @Title: toCreateLoadBalancer
	  * @Description: 去创建负载均衡器页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 13, 2015 2:48:04 PM
	 *//*
	@RequestMapping("/toCreateLoadBalancer")
	public String toCreateLoadBalancer(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("project_id", tenantId);
		params.put("hypervisorType", "KVM");
		
		try {
			List<PoolBean> poolList = lBaasService.getPoolList(tenantId, tokenId);
			List<PoolBean> poolList2 = new ArrayList<PoolBean>();
			for (PoolBean poolBean : poolList) {
				if (poolBean.getTenant_id().equals(tenantId) && poolBean.getVip_id() == "null") {
					poolList2.add(poolBean);
				}
			}
			List<MemberBean> members = lBaasService.getMemberList(tenantId, tokenId);
			List<HealthMonitorBean> healthMonitors = lBaasService.getHealthMonitorList(tenantId, tokenId);
			List<InstanceInfoVOBean> servers = instanceInfoDBService.getAllInstanceInfoListDB2(params);
			List<InstanceInfoVOBean> networks = instanceInfoDBService.getAllNetWorkInfo();
			StringBuffer stringBuffer = new StringBuffer();
			for(int i=0; i<servers.size(); i++) {
				InstanceInfoVOBean instanceInfo = servers.get(i);
				stringBuffer.append(";"+instanceInfo.getVm_state());
				if(p.getProperty(OPSTPropertyKeyConst.INSTANCE_PROTECT).contains(instanceInfo.getDisplay_name())){
					instanceInfo.setIsProtect(true);
				}else{
					instanceInfo.setIsProtect(false);
				}
				
				//匹配IP地址
				String ipAddress = "";
				for(int j=0; j<networks.size(); j++) {
					InstanceInfoVOBean network = networks.get(j);
					if(instanceInfo.getUuid().equals(network.getProject_id())) {
						ipAddress = ipAddress + network.getIpAddress() + ";";
					}
				}
				instanceInfo.setIpAddress(ipAddress);
			}
			DbContextHolder.clearDbType();
			List<DataBaseInfo> dataBases = chefDataBaseService.getAllDataBase();
			List<TomcatInfo> tomcats = chefTomcatService.getAllTomcat();
			List<InstanceInfoVOBean> delServers = new ArrayList<InstanceInfoVOBean>();
			for(int k=0; k<servers.size(); k++) {
				InstanceInfoVOBean instance = servers.get(k);
				//删除中间件管理->MySQL创建的实例
				for(DataBaseInfo db : dataBases){
					if(instance.getUuid().equals(db.getInstanceId())){
						delServers.add(instance);
						break;
					}
				}
				//删除中间件管理->Tomcat创建的实例
				for(TomcatInfo tomcat : tomcats) {
					if(instance.getUuid().equals(tomcat.getInstanceId())){
						delServers.add(instance);
						break;
					}
				}
				for(MemberBean member : members) {
					if(instance.getIpAddress().contains(member.getAddress())) {
						if(!delServers.contains(instance)) {
							delServers.add(instance);
							break;
						}
					}
				}
			}
			servers.removeAll(delServers);
			request.setAttribute("pools", poolList2);
			request.setAttribute("healthMonitors", healthMonitors);
			request.setAttribute("servers", servers);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_loadbalancer_create";
	}
	
	*//**
	 * 
	  * @Title: createLoadBalancer
	  * @Description: 创建负载均衡器
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 13, 2015 7:07:31 PM
	 *//*
	@RequestMapping("/createLoadBalancer")
	public String createLoadBalancer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		//健康监控
		String healthMonitorId = request.getParameter("healthMonitorId");
		String hm_delay = request.getParameter("hm_delay");
		String hm_max_retries = request.getParameter("hm_max_retries");
		String hm_type = request.getParameter("hm_type");
		String hm_timeout = request.getParameter("hm_timeout");
		String hm_admin_state_up = request.getParameter("hm_admin_state_up");
		String hm_expected_codes = request.getParameter("hm_expected_codes");
		String hm_http_method = request.getParameter("hm_http_method");
		String hm_url_path = request.getParameter("hm_url_path");
		
		//资源池
		String poolId = request.getParameter("poolId");
		
		//对外VIP
		String vipName = request.getParameter("vipName");
		String vip_admin_state_up = request.getParameter("vip_admin_state_up");
		String vip_protocol_port = request.getParameter("vip_protocol_port");
		String vip_protocol = request.getParameter("vip_protocol");
		String vip_persistence_type = request.getParameter("vip_persistence_type");
		String vip_persistence_cookie_name = request.getParameter("vip_persistence_cookie_name");
		String vip_connection_limit = request.getParameter("vip_connection_limit");
		String vip_lbaas_sample = request.getParameter("vip_lbaas_sample");
		
		//成员
		String[] addressArr = request.getParameterValues("address");
		
		String stackId = request.getParameter("taskStackId");
		AccessBean accBean = (AccessBean) request.getSession().getAttribute("sessionLoginInfo");
		
		//封装"健康监控"Bean
		HealthMonitorBean healthMonitor = new HealthMonitorBean();
		
		healthMonitor.setDelay(hm_delay);
		healthMonitor.setMax_retries(hm_max_retries);
		healthMonitor.setType(hm_type);
		healthMonitor.setTimeout(hm_timeout);
		healthMonitor.setExpected_codes(hm_expected_codes);
		healthMonitor.setHttp_method(hm_http_method);
		healthMonitor.setUrl_path(hm_url_path);
		if("checked".equals(hm_admin_state_up)){
			healthMonitor.setAdmin_state_up(true);
		}else{
			healthMonitor.setAdmin_state_up(false);
		}
		
		//封装"对外VIP"Bean
		VIPBean vipBean = new VIPBean();
		SessionPersistenceBean spBean = new SessionPersistenceBean();
		
		vipBean.setName(vipName);
		if (poolId != null) {
			vipBean.setPool_id(poolId);
			String subnet_id = lBaasService.getPoolDetails(tenantId, tokenId, poolId).getSubnet_id();
			vipBean.setSubnet_id(subnet_id);
		}
		if ("checked".equals(vip_admin_state_up)) {
			vipBean.setAdmin_state_up(true);
		} else {
			vipBean.setAdmin_state_up(false);
		}
		vipBean.setProtocol_port(Integer.parseInt(vip_protocol_port));
		vipBean.setProtocol(vip_protocol);
		spBean.setType(vip_persistence_type);
		spBean.setCookie_name(vip_persistence_cookie_name);
		vipBean.setSession_persistence(spBean);
		if(vip_connection_limit != "" && vip_connection_limit != null) {
			vipBean.setConnection_limit(Integer.parseInt(vip_connection_limit));
		}
		
		//封装"成员"Bean
		List<String> addressList = new ArrayList<String>();
		for (int i=0; i<addressArr.length; i++) {
			String address = addressArr[i];
			address = address.substring(0, address.length()-1);
			if (address.contains("@")) {
				String[] addressArr2 = address.split("@");
				for (int j=0; j<addressArr2.length; j++) {
					addressList.add(addressArr2[j]);
				}
			} else {
				addressList.add(address);
			}
		}
		
		MemberBean memberBean = new MemberBean();
		
		if (addressList.size() > 0) {
			memberBean.setPool_id(poolId);
			memberBean.setProtocol_port(Integer.parseInt(vip_protocol_port));
			if ("checked".equals(vip_admin_state_up)) {
				memberBean.setAdmin_state_up(true);
			} else {
				memberBean.setAdmin_state_up(false);
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			//关联资源池到此健康监控
			lBaasService.AssociatesHealthMonitorToPool(tenantId, tokenId, poolId, healthMonitorId);
			request.setAttribute("actionLog", "资源池" + poolId + "关联到健康监控" + healthMonitorId);
			//创建VIP
			lBaasService.createVip(tenantId, tokenId, vipBean);
			request.setAttribute("actionLog", "创建了一个名称为"+vipName+"的对外VIP");
			
			//启用负载均衡模板
			if ("checked".equals(vip_lbaas_sample)) {
				taskBoxOperateManager.setEntityClass(TaskBox.class);
				List<TaskBox> list = taskBoxOperateManager.createQuery(TaskStackHQLConstants.QUERY_TASKBOX_LIST_BY_STACKID, stackId);
				for (TaskBox taskBox : list) {
					taskParamOperateManager.setEntityClass(TaskParam.class);
					List<TaskParam> taskParamList = taskParamOperateManager.createQuery(TaskStackHQLConstants.QUERY_TASKPARAM_LIST_BY_TASKBOXID, taskBox.getId());
					if("restful请求".equals(taskBox.getName())) {
						for (TaskParam taskParam : taskParamList) {
							if (taskParam.getParamName().equals(TaskStackConstants.TASK_JSON_STRING)) {
								String taskJsonString = taskParam.getParamValue();
								if (addressList.size() == 3) {
									for(int j=0; j<addressList.size(); j++){
										if (addressList.get(j) != "") {
											taskJsonString = taskJsonString.replace("${"+(2+j)+".returnAddress}", addressList.get(j));
										}
									}
								}
								taskParam.setParamValue(StringEscapeUtils.escapeHtml(taskJsonString));
							} else {
								taskParam.setParamValue(StringEscapeUtils.escapeHtml(taskParam.getParamValue()));
							}
						}
					} else if("负载均衡".equals(taskBox.getName())) {
						for (TaskParam taskParam : taskParamList) {
							if (taskParam.getParamName().equals(TaskStackConstants.POOL_ID)) {
								taskParam.setParamValue(poolId);
							} else if (taskParam.getParamName().equals(TaskStackConstants.MEMBER_WEIGHT)) {
								taskParam.setParamValue(String.valueOf(3));
							} else if (taskParam.getParamName().equals(TaskStackConstants.MEMBER_PROTOCOL_PORT)) {
								taskParam.setParamValue(vip_protocol_port);
							} else if (taskParam.getParamName().equals(TaskStackConstants.MEMBER_ADMIN_STATE_UP)) {
								taskParam.setParamValue(vip_admin_state_up);
							} else if (taskParam.getParamName().equals(TaskStackConstants.MEMBER_ADDRESS)) {
								String paramValue = taskParam.getParamValue();
								if (addressList.size() == 3) {
									for(int j=0; j<addressList.size(); j++){
										if (addressList.get(j) != "") {
											paramValue = paramValue.replace("${"+(2+j)+".returnAddress}", addressList.get(j));
										}
									}
								}
								taskParam.setParamValue(paramValue);
							} else if (taskParam.getParamName().equals(TaskStackConstants.HEALTH_MONITOR_POOL_ID)) {
								taskParam.setParamValue(poolId);
							}
						}
					}
					taskBox.setTaskParam(taskParamList);
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("taskStackId", stackId);
				//第一个TaskBox
				map.put("1.requestType", "post");
				map.put("1.header", "Content-Type:application/json");
				map.put("1.apiInterface", "http://10.28.0.231:6080/api/v1/auth");
				map.put("1.taskJsonString", "{\"user\":\"admin\",\"pass\":\"{MD5}vtEoNlIWwBmYiRXtOt11+w==\"}");
				map.put("1.restfulReturnJsonTemplate", "{\"token\":\"auth.token\",\"user\":\"auth.user\"}");
				
				//第二个TaskBox
				map.put("2.header", "Content-Type:application/json,X-Auth-Token:${1.token}");
				map.put("2.requestType", "post");
				map.put("2.apiInterface", "http://10.28.0.231:6080/api/v1/apps");
				String address = "{\"node\":{\"os\":\"linux\",\"host\":\"lb0\",\"addr\":\"${2.returnAddress}\"},\"cred\":{\"verify\":\"keypair\",\"user\":\"root\"},\"appi\":{\"name\":\"loadbalance\",\"options\":\"create -s ${2.returnAddress},${3.returnAddress},${4.returnAddress} -i /usr/share/tomcat\"}}";
				for(int j=0; j<addressList.size(); j++){
					if (addressList.get(j) != "") {
						address = address.replace("${"+(2+j)+".returnAddress}", addressList.get(j));
					}
				}
				map.put("2.taskJsonString", address);
				map.put("2.restfulReturnJsonTemplate", "");
				
				//第三个TaskBox
				map.put("3.memberAdderss", addressList.get(0)+";"+addressList.get(1)+";"+addressList.get(2));
				map.put("3.memberWeight", String.valueOf(3));
				map.put("3.healthMonitorPoolId", poolId);
				map.put("3.member_Admin_state_up", vip_admin_state_up);
				map.put("3.poolId", poolId);
				map.put("3.member_protocol_port", vip_protocol_port);
				taskStackRun.runTaskStack(map, accBean.getUserName(), stackId, tokenId, tenantId, new TaskStackCallback() {
					
					@Override
					public void callbackAction(String messageJson) {
						// TODO Auto-generated method stub
						System.out.println(messageJson);
					}
				});
			} else {//不启用负载均衡模板
				//创建成员
				for(int j=0; j<addressList.size(); j++){
					if (addressList.get(j) != "") {
						String addr = addressList.get(j);
						memberBean.setAddress(addr);
						lBaasService.createMember(tenantId, tokenId, memberBean);
						request.setAttribute("actionLog", "创建了一个地址为"+addr+"的成员");
					}
				}
			}
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllVips?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageAndReturnException(e.getMessage());
		}
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: ajaxGetPoolDetail
	  * @Description: AJAX获取资源池信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws IOException
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 13, 2015 3:05:17 PM
	 *//*
	@RequestMapping("/ajaxGetPoolDetail")
	public String ajaxGetPoolDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String tenantId = "";
		String tokenId = "";
		try {
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String poolId = request.getParameter("poolId");
		
		PoolBean pool = null;
		VIPBean vip = null;
		JSONObject poolDetailJson = new JSONObject();
		try {
			pool = lBaasService.getPoolDetails(tenantId, tokenId, poolId);
			if(pool.getVip_id() != "null") {
				vip = lBaasService.getVipDetails(tenantId, tokenId, pool.getVip_id());
				poolDetailJson.element("vip", vip.getName());
			} else {
				poolDetailJson.element("vip", "");
			}
			poolDetailJson.element("tenant_id", pool.getTenant_id());
			poolDetailJson.element("protocol", pool.getProtocol());
			poolDetailJson.element("provider", pool.getProvider());
			poolDetailJson.element("subnetCidr", pool.getSubnetCidr());
			poolDetailJson.element("lb_method", pool.getLb_method());
			poolDetailJson.element("admin_state_up", pool.isAdmin_state_up());
			poolDetailJson.element("description", pool.getDescription());
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(poolDetailJson);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: ajaxGetHealthMonitorDetail
	  * @Description: AJAX获取健康监控信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws IOException
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 19, 2015 2:09:31 PM
	 *//*
	@RequestMapping("/ajaxGetHealthMonitorDetail")
	public String ajaxGetHealthMonitorDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String tenantId = "";
		String tokenId = "";
		try {
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String healthMonitorId = request.getParameter("healthMonitorId");
		
		HealthMonitorBean healthMonitor = null;
		String poolNames = "";
		JSONObject healthDetailJson = new JSONObject();
		try {
			healthMonitor = lBaasService.getHealthMonitorDetails(tenantId, tokenId, healthMonitorId);
			List<PoolOfHealthMonitorBean> phms = healthMonitor.getPools();
			for(int i=0; i<phms.size(); i++) {
				if(phms.get(i).getName() != null) {
					poolNames = poolNames + phms.get(i).getName() + "&nbsp;";
				}
			}
			healthDetailJson.element("type", healthMonitor.getType());
			healthDetailJson.element("delay", healthMonitor.getDelay());
			healthDetailJson.element("timeout", healthMonitor.getTimeout());
			healthDetailJson.element("max_retries", healthMonitor.getMax_retries());
			healthDetailJson.element("http_method", healthMonitor.getHttp_method());
			healthDetailJson.element("url_path", healthMonitor.getUrl_path());
			healthDetailJson.element("expected_codes", healthMonitor.getExpected_codes());
			healthDetailJson.element("poolNames", poolNames);
			healthDetailJson.element("tenant_id", healthMonitor.getTenant_id());
			healthDetailJson.element("admin_state_up", healthMonitor.isAdmin_state_up());
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(healthDetailJson);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: toAddLbaasInstance
	  * @Description: 为负载均衡器增加实例
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 19, 2015 6:53:40 PM
	 *//*
	@RequestMapping("/toAddLbaasInstance")
	public String toAddLbaasInstance(HttpServletRequest request, HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		String vipId = request.getParameter("vipId");
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("project_id", tenantId);
		params.put("hypervisorType", "KVM");
		
		try {
			VIPBean vip = lBaasService.getVipDetails(tenantId, tokenId, vipId);
			String poolId = vip.getPool_id();
			List<MemberBean> members = lBaasService.getMemberList(tenantId, tokenId);
			List<InstanceInfoVOBean> servers = instanceInfoDBService.getAllInstanceInfoListDB2(params);
			List<InstanceInfoVOBean> networks = instanceInfoDBService.getAllNetWorkInfo();
			StringBuffer stringBuffer = new StringBuffer();
			for (int i=0; i<servers.size(); i++) {
				InstanceInfoVOBean instanceInfo = servers.get(i);
				stringBuffer.append(";"+instanceInfo.getVm_state());
				if(p.getProperty(OPSTPropertyKeyConst.INSTANCE_PROTECT).contains(instanceInfo.getDisplay_name())){
					instanceInfo.setIsProtect(true);
				}else{
					instanceInfo.setIsProtect(false);
				}
				
				//匹配IP地址
				String ipAddress = "";
				for(int j=0; j<networks.size(); j++) {
					InstanceInfoVOBean network = networks.get(j);
					if(instanceInfo.getUuid().equals(network.getProject_id())) {
						ipAddress = ipAddress + network.getIpAddress() + ";";
					}
				}
				instanceInfo.setIpAddress(ipAddress);
			}
			DbContextHolder.clearDbType();
			List<DataBaseInfo> dataBases = chefDataBaseService.getAllDataBase();
			List<TomcatInfo> tomcats = chefTomcatService.getAllTomcat();
			List<InstanceInfoVOBean> delServers = new ArrayList<InstanceInfoVOBean>();
			for(int k=0; k<servers.size(); k++) {
				InstanceInfoVOBean instance = servers.get(k);
				//删除中间件管理->MySQL创建的实例
				for(DataBaseInfo db : dataBases){
					if(instance.getUuid().equals(db.getInstanceId())){
						delServers.add(instance);
						break;
					}
				}
				//删除中间件管理->Tomcat创建的实例
				for(TomcatInfo tomcat : tomcats) {
					if(instance.getUuid().equals(tomcat.getInstanceId())){
						delServers.add(instance);
						break;
					}
				}
				for(MemberBean member : members) {
					if(instance.getIpAddress().contains(member.getAddress())) {
						if(!delServers.contains(instance)) {
							delServers.add(instance);
							break;
						}
					}
				}
			}
			servers.removeAll(delServers);
			request.setAttribute("servers", servers);
			request.setAttribute("vipId", vipId);
			request.setAttribute("poolId", poolId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_lbaas_loadbalancer_detail_instance_add";
	}
	
	*//**
	 * 
	  * @Title: addLbaasInstance
	  * @Description: 为负载均衡器增加实例
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author Zhangl
	  * @throws
	  * @Time May 20, 2015 11:27:50 AM
	 *//*
	@RequestMapping("/addLbaasInstance")
	public String addLbaasInstance(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		//选择实例
		String[] addressArr = request.getParameterValues("address");
		
		String vipId = request.getParameter("vipId");
		String poolId = request.getParameter("poolId");
		
		//解析实例地址
		List<String> addressList = new ArrayList<String>();
		for (int i=0; i<addressArr.length; i++) {
			String address = addressArr[i];
			address = address.substring(0, address.length()-1);
			if (address.contains("@")) {
				String[] addressArr2 = address.split("@");
				for (int j=0; j<addressArr2.length; j++) {
					addressList.add(addressArr2[j]);
				}
			} else {
				addressList.add(address);
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			VIPBean vip = lBaasService.getVipDetails(tenantId, tokenId, vipId);
			//封装"成员"Bean
			MemberBean memberBean = new MemberBean();
			if (addressList.size() > 0) {
				memberBean.setPool_id(poolId);
				memberBean.setProtocol_port(vip.getProtocol_port());
				memberBean.setAdmin_state_up(vip.isAdmin_state_up());
				for(int j=0; j<addressList.size(); j++){
					if (addressList.get(j) != "") {
						String addr = addressList.get(j);
						memberBean.setAddress(addr);
						lBaasService.createMember(tenantId, tokenId, memberBean);
						request.setAttribute("actionLog", "创建了一个地址为"+addr+"的成员");
					}
				}
			}
			response.getWriter().print("<script>window.location.href='getLoadBalancerDetail?vipId="+vipId+"&type=instance&promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getLoadBalancerDetail?vipId="+vipId+"&type=instance&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageAndReturnException(e.getMessage());
		}
		
		return null;
	}
}*/