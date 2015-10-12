/*package com.ibm.smartcloud.openstack.neutron.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ibm.smartcloud.core.util.SpringHelper;
import com.ibm.smartcloud.openstack.keystone.service.IdentityService;
import com.ibm.smartcloud.openstack.neutron.service.FloatIpService;
import com.ibm.smartcloud.openstack.neutron.service.NetworkService;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;

*//**
 * 
 * @Title：NetworkController 
 * @Description:    网络模块
 * @Auth: ZhangLong
 * @CreateTime:Mar 25, 2015 5:13:46 PM     
 * @version V1.0
 *//*
@Controller
public class NetworkController extends BaseController {

	private static final Logger logger = Logger.getLogger(NetworkController.class);
	@Autowired
	private NetworkService networkService;
	@Autowired
	private InstanceService instanceServerService;
	@Autowired
	private FloatIpService floatIpService;
	@Autowired
	private IdentityService identityService;

	*//**
	 * 
	  * @Title: getAllNetwork
	  * @Description: 获取所有网络
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:18:56 PM
	 *//*
	@RequestMapping("/getAllNetwork")
	public String getAllNetwork(HttpServletRequest request) {
		
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
			List<NetworkBean> networkBeanLst = networkService.getNetworkList(tenantId, tokenId);
			List<SubnetBean> subnetBeanLst = networkService.getSubnetList(tenantId, tokenId);
			request.setAttribute("networks", networkBeanLst);
			request.setAttribute("subnets", subnetBeanLst);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		try {
			List<TenantBean> tenantList = identityService.getTenantsList(tokenId);
			request.setAttribute("tenantList", tenantList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
		} 
		return "home/ec2/ec2_network_list";
	}
	
	*//**
	 * 
	  * @Title: getNetworkDetail
	  * @Description: 根据Network ID查询指定网络的详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:19:26 PM
	 *//*
	@RequestMapping("/getNetworkDetail")
	public String getNetworkDetail(HttpServletRequest request,HttpServletResponse response) {
		
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
		
		String networkId = request.getParameter("networkId");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("networkId", networkId);
		
		try {
			request.setAttribute("network", networkService.getNetworkDetails(tenantId, tokenId, networkId));
			request.setAttribute("subnets", networkService.getSubnets(tenantId, tokenId, networkId));
			
			List<PortBean> portBeanLst = networkService.getPorts(tenantId, tokenId, networkId);
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> list = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			List<String> serverIdLst = new ArrayList<String>();
			for(ServerDetailBean sdb : list){
				String serverId = sdb.getId();
				serverIdLst.add(serverId);
			}
			
			List<ServerDetailBean> serverDetailBeanLst = new ArrayList<ServerDetailBean>();
			ServerDetailBean s = new ServerDetailBean();
			try {
				for(PortBean p : portBeanLst){
					if(p.getPortDevice_owner().equalsIgnoreCase("compute:nova")){
						if(serverIdLst.contains(p.getPort_device_id())){
							s = instanceServerService.instanceServerDetail(p.getPort_device_id(), tenantId, tokenId);
							serverDetailBeanLst.add(s);
						}else{
							response.getWriter().print("<script>alert('实例不完整!')</script>");
						}
					}
				}
			} catch (IOException e2) {
				e2.printStackTrace();
				throw new PopUpMessageException(e2.getMessage());
			}
			
			request.setAttribute("ports", portBeanLst);
			request.setAttribute("servers", serverDetailBeanLst);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_network_detail_new";
	}
	
	*//**
	 * 
	  * @Title: getNetworkDetailNew
	  * @Description: 根据Network ID查询指定网络的详细信息(New)
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:20:11 PM
	 *//*
	@RequestMapping("/getNetworkDetailNew")
	public String getNetworkDetailNew(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String networkId = request.getParameter("networkId");
		request.setAttribute("networkId", networkId);
		request.setAttribute("network", networkService.getNetworkDetails(tenantId, tokenId, networkId));
		request.setAttribute("subnets", networkService.getSubnets(tenantId, tokenId, networkId));
		request.setAttribute("ports", networkService.getPorts(tenantId, tokenId, networkId));
		return "home/ec2/ec2_network_detail_new";
	}
	
	*//**
	 * 
	  * @Title: toCreateNetwork
	  * @Description: 到创建网络页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:21:10 PM
	 *//*
	@RequestMapping("/toCreateNetwork")
	public String toCreateNetwork(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		//获取所有项目project 租户 的信息
		List<TenantBean> tenantList = null;
		try {
			tenantList = identityService.getTenantsList(tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("获取tenantList信息时发生"+e.getMessage()+"异常");
		}
		request.setAttribute("tenantList", tenantList);
		
		return "home/ec2/ec2_network_create";
	}
	
	*//**
	 * 
	  * @Title: createNetwork
	  * @Description: 创建网络及子网
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:21:45 PM
	 *//*
	@RequestMapping("/createNetwork")
	public String createNetwork(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		//获取网络名称
		String networkName = request.getParameter("netName");
		String netTenant_id = request.getParameter("netTenant_id");
		String netAdmin_state_up = request.getParameter("netAdmin_state_up");
		String netShared = request.getParameter("netShared");
		String netRouter_external = request.getParameter("netRouter_external");
		String createSubnet = request.getParameter("needCreateSubnet");
		String subnetName = request.getParameter("subnetName");
		String subnetCidr = request.getParameter("subnetCidr");
		String subnet_ip_version = request.getParameter("subnet_ip_version");
		String subnet_gateway_ip = request.getParameter("subnet_gateway_ip");
		
		//若禁用网关，则置空gateway_ip
		String subnetGatewayForbidden = request.getParameter("subnetGatewayForbidden");
		if ("checked".equals(subnetGatewayForbidden)){
			subnet_gateway_ip = null;
		}
		
		String subnetEnable_dhcp = request.getParameter("subnetActiveDHCP");
		String subnet_allocation_pools = request.getParameter("subnetAllocAddrPool");
		String subnet_dns_nameservers = request.getParameter("subnetDNS");
		String subnet_host_routes = request.getParameter("subnetRoute");
		
		Boolean isAdminRole = false;
		if (SpringHelper.getRequest().getSession() != null && SpringHelper.getRequest().getSession().getAttribute("isAdminRole") != null) {
			isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
		}
		CreateNetworkRequestBean networkBeanRequest = new CreateNetworkRequestBean();
		networkBeanRequest.setAdminRole(isAdminRole);
		//网络名称
		networkBeanRequest.setNetName(networkName);
		//Admin状态
		if ("checked".equals(netAdmin_state_up)) {
			networkBeanRequest.setNetAdmin_state_up(true);
		} else {
			networkBeanRequest.setNetAdmin_state_up(false);
		}
		
		if(isAdminRole) {
			//项目ID
			if (netTenant_id != null) {
				networkBeanRequest.setNetTenant_id(netTenant_id);
			}
			//共享
			if ("checked".equals(netShared)) {
				networkBeanRequest.setNetShared(true);
			} else {
				networkBeanRequest.setNetShared(false);
			}
			//外部网络
			if ("checked".equals(netRouter_external)) {
				networkBeanRequest.setNetRouter_external(true);
			} else {
				networkBeanRequest.setNetRouter_external(false);
			}
		}
		if ("checked".equals(createSubnet)) {
			networkBeanRequest.setCreateSubnet(true);
			if (subnetName != "") {
				networkBeanRequest.setSubnetName(subnetName);	
			}
			
			if (subnetCidr != "") {
				networkBeanRequest.setSubnetCidr(subnetCidr);
			}
			
			if (subnet_ip_version != "") {
				networkBeanRequest.setSubnet_ip_version(subnet_ip_version);
			}
			
			if (subnet_gateway_ip != "") {
				networkBeanRequest.setSubnet_gateway_ip(subnet_gateway_ip);
			}
		}
		if ("checked".equals(subnetEnable_dhcp)) {
			networkBeanRequest.setActiveDHCP(true);	
			if (subnet_allocation_pools != "") {
				String[] arrSubnet_allocation_pools = subnet_allocation_pools.split("[\n]");
				List<Map> poollist = new ArrayList<Map>();
				for(int i=0; i<arrSubnet_allocation_pools.length; i++){
					String[] arr = arrSubnet_allocation_pools[i].split(",");
					Map<String,String> map = new HashMap<String,String>();
					map.put("start", arr[0]);
					map.put("end", arr[1]);
					poollist.add(map);
				}
				networkBeanRequest.setSubnet_allocation_pools(poollist);
			}
			
			if (subnet_dns_nameservers != "") {
				String[] arrSubnet_dns_nameservers = subnet_dns_nameservers.split("[\n]");
				List<String> dnslist = new ArrayList<String>();
				for(String s : arrSubnet_dns_nameservers){
					dnslist.add(s);
				}
				networkBeanRequest.setSubnet_dns_nameservers(dnslist);
			}
			
			if (subnet_host_routes != "") {
				String[] arrSubnet_host_routes = subnet_host_routes.split("[\n]");
				List<String> routelist = new ArrayList<String>();
				for(String s : arrSubnet_host_routes){
					routelist.add(s);
				}
				networkBeanRequest.setSubnet_host_routes(routelist);
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			NetworkBean networkBean = networkService.createNetwork(tenantId, tokenId, networkBeanRequest);
			request.setAttribute("actionLog", "创建了一个名称为"+networkName+"的网络");
			response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getAllNetwork?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
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
	  * @Title: toUpdateNetwork
	  * @Description: 到更新网络页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:22:07 PM
	 *//*
	@RequestMapping("/toUpdateNetwork")
	public String toUpdateNetwork(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String networkId = request.getParameter("networkId");
		try {
			request.setAttribute("network", networkService.getNetworkDetails(tenantId, tokenId, networkId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_network_edit";
	}
	
	*//**
	 * 
	  * @Title: updateNetwork
	  * @Description: 更新网络
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:22:24 PM
	 *//*
	@RequestMapping("/updateNetwork")
	public String updateNetwork(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String updatenetId = request.getParameter("networkId");
		String updatenetName = request.getParameter("netName");
		String updatenetAdmin_state_up = request.getParameter("netAdmin_state_up");
		String updatenetShared = request.getParameter("netShared");
		String updatenetRouter_external = request.getParameter("netRouter_external");
		
		Boolean isAdminRole = false;
		if (SpringHelper.getRequest().getSession() != null && SpringHelper.getRequest().getSession().getAttribute("isAdminRole") != null) {
			isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
		}
		NetworkBean network = new NetworkBean();
		network.setAdminRole(isAdminRole);
		network.setNetId(updatenetId);
		network.setNetName(updatenetName);
		network.setNetAdmin_state_up(Boolean.parseBoolean(updatenetAdmin_state_up));
		if(isAdminRole) {
			//判断网络是否被浮动IP分配过，或者被路由设置了网关
//			if ((updatenetRouter_external == null) && ((networkService.getNetworkDetails(tenantId, tokenId, updatenetId).isNetRouter_external()) != (Boolean.parseBoolean(updatenetRouter_external)))) {
//				List<PortBean> portList = networkService.getPorts(tenantId, tokenId, updatenetId);
//				List<String> device_owner_nameLst = new ArrayList<String>();
//				for(int i=0; i<portList.size(); i++){
//					String device_owner_Name = portList.get(i).getPortDevice_owner();
//					if(!device_owner_nameLst.contains(device_owner_Name) || i==0){
//						device_owner_nameLst.add(device_owner_Name);
//					}
//				}
//				try {
//					if ((device_owner_nameLst.contains("network:router_gateway")) || (device_owner_nameLst.contains("network:floatingip"))){
//						response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=-1';</script>");
//						return null;
//					}
//				} catch (IOException e4) {
//					e4.printStackTrace();
//					throw new PopUpMessageException(e4.getMessage());
//				}
//			}
			network.setNetShared(Boolean.parseBoolean(updatenetShared));
			network.setNetRouter_external(Boolean.parseBoolean(updatenetRouter_external));
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			NetworkBean networkBean = networkService.updateNetwork(tenantId, tokenId, network);
			request.setAttribute("actionLog", "更新了一个名称为"+updatenetName+"的网络");
			response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deleteNetworkById
	  * @Description: 删除网络
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:22:42 PM
	 *//*
	@RequestMapping("/deleteNetworkById")
	public String deleteNetworkById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String networkId = request.getParameter("networkId");
		String[] netIds = networkId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		List<FloatIpBean> floatIpLst = floatIpService.getAllFloatIp();
		List<String> nameLst = new ArrayList<String>();
		for(int i=0; i<floatIpLst.size(); i++){
			String poolName = floatIpLst.get(i).getPool();
			if(!nameLst.contains(poolName) || i==0){
				nameLst.add(poolName);
			}
		}
		int count = 0;
		for (int j = 0; j< netIds.length; j++) {
			if (nameLst.contains((networkService.getNetworkDetails(netIds[j]).getNetName()))){
				count++;
//				response.getWriter().print("<script>alert('此网络被浮动IP分配过，不能删除!')</script>");
//				response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=-1';</script>");
//				break ;
			}else {
				String flag = networkService.deleteNetwork(netIds[j]);
				if(flag != null){
					count++;
//					response.getWriter().print("<script>alert('网络删除失败!')</script>");
//					response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=-1';</script>");
//					break ;
				}
			}
		}
//		int count = 0;
		try {
			for (int j = 0; j< netIds.length; j++) {
				String flag = networkService.deleteNetwork(tenantId, tokenId, netIds[j]);
//				if(flag != null){
//					count++;
//				} else{
					request.setAttribute("actionLog", "删除了一个ID为"+netIds[j]+"的网络");
//				}
			}
//			if(count==0){
				response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=1';</script>");
//			} else {
//				response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=-1';</script>");
//			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllNetwork?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: getSubnet
	  * @Description: 查询所有子网信息
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:23:07 PM
	 *//*
	@RequestMapping("/getSubnet")
	public String getSubnet(HttpServletRequest request) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		try {
			request.setAttribute("subnets", networkService.getSubnetList(tenantId, tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		} 
		
		return "home/ec2/ec2_subnet_list";
	}
	
	*//**
	 * 
	  * @Title: getSubnetDetail
	  * @Description: 根据Subnet ID查询指定子网的详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:23:25 PM
	 *//*
	@RequestMapping("/getSubnetDetail")
	public String getSubnetDetail(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String subnetId = request.getParameter("subnetId");
		try {
			request.setAttribute("subnet", networkService.getSubnetDetails(tenantId, tokenId, subnetId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		} 
		
		//return "home/ec2/ec2_subnet_detail";
		return "home/ec2/ec2_subnet_detail_new";
	}
	
	*//**
	 * 
	  * @Title: getSubnetsByNetId
	  * @Description: 根据Network ID查询子网列表
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:25:56 PM
	 *//*
	@RequestMapping("/getSubnetsByNetId")
	public String getSubnetsByNetId(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String networkId = request.getParameter("networkId");
		request.setAttribute("networkId", networkId);
		
		try {
			request.setAttribute("subnets", networkService.getSubnets(tenantId, tokenId, networkId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		} 
		
		return "home/ec2/ec2_subnet_list";
	}
	
	*//**
	 * 
	  * @Title: toCreateSubnet
	  * @Description: 到创建子网页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:30:04 PM
	 *//*
	@RequestMapping("/toCreateSubnet")
	public String toCreateSubnet(HttpServletRequest request,HttpServletResponse response) {
		 
		 String subnet_network_id = request.getParameter("networkId");
		 String type = request.getParameter("type");
		 request.setAttribute("type", type);
		 request.setAttribute("subnet_network_id", subnet_network_id);
		 return "home/ec2/ec2_subnet_create";
	}
	
	*//**
	 * 
	  * @Title: createSubnet
	  * @Description: 创建子网
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:30:19 PM
	 *//*
	@RequestMapping("/createSubnet")
	public String createSubnet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String type = request.getParameter("type");
		String subnet_network_id = request.getParameter("subnet_network_id");
		String subnetName = request.getParameter("subnetName");
		String subnetCidr = request.getParameter("subnetCidr");
		String subnet_ip_version = request.getParameter("subnet_ip_version");
		String subnet_gateway_ip = request.getParameter("subnet_gateway_ip");
		
		//若禁用网关，则置空gateway_ip
		String subnetGatewayForbidden = request.getParameter("subnetGatewayForbidden");
		if ("checked".equals(subnetGatewayForbidden)){
			subnet_gateway_ip = null;
		}
		
		String subnetEnable_dhcp = request.getParameter("subnetActiveDHCP");
		String subnet_allocation_pools = request.getParameter("subnetAllocAddrPool");
		String subnet_dns_nameservers = request.getParameter("subnetDNS");
		String subnet_host_routes = request.getParameter("subnetRoute");
		
		SubnetBean subnetBeanRequest = new SubnetBean();
		
		if (subnet_network_id != "") {
			subnetBeanRequest.setSubnet_network_id(subnet_network_id);	
		}
		
		if (subnetName != "") {
			subnetBeanRequest.setSubnetName(subnetName);	
		}
			
		if (subnetCidr != "") {
			subnetBeanRequest.setSubnetCidr(subnetCidr);
		}
			
		if (subnet_ip_version != "") {
			subnetBeanRequest.setSubnet_ip_version(subnet_ip_version);
		}
			
		if (subnet_gateway_ip != "") {
			subnetBeanRequest.setSubnet_gateway_ip(subnet_gateway_ip);
		}
		
		if ("checked".equals(subnetEnable_dhcp)) {
			subnetBeanRequest.setSubnetEnable_dhcp(true);	
			if (subnet_allocation_pools != "") {
				String[] arrSubnet_allocation_pools = subnet_allocation_pools.split("[\n]");
				List<Map> poollist = new ArrayList<Map>();
				for(int i=0; i<arrSubnet_allocation_pools.length; i++){
					String[] arr = arrSubnet_allocation_pools[i].split(",");
					Map<String,String> map = new HashMap<String,String>();
					map.put("start", arr[0]);
					map.put("end", arr[1]);
					poollist.add(map);
				}
				subnetBeanRequest.setSubnet_allocation_pools(poollist);
			}
			
			if (subnet_dns_nameservers != "") {
				String[] arrSubnet_dns_nameservers = subnet_dns_nameservers.split("[\n]");
				List<String> dnslist = new ArrayList<String>();
				for(String s : arrSubnet_dns_nameservers){
					dnslist.add(s);
				}
				subnetBeanRequest.setSubnet_dns_nameservers(dnslist);
			}
			
			if (subnet_host_routes != "") {
				String[] arrSubnet_host_routes = subnet_host_routes.split("[\n]");
				List<String> routelist = new ArrayList<String>();
				for(String s : arrSubnet_host_routes){
					routelist.add(s);
				}
				subnetBeanRequest.setSubnet_host_routes(routelist);
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			SubnetBean subnetBean = networkService.createSubnet(tenantId, tokenId, subnetBeanRequest);
			request.setAttribute("actionLog", "创建了一个名称为"+subnetName+"的子网");
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+subnet_network_id+"&type="+type+"&promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getNetworkDetail?networkId="+subnet_network_id+"&type="+type+"&promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		//response.getWriter().print("<script>parent.document.getElementById('mainiframe').contentWindow.document.getElementById('subnetListFrame').contentWindow.location.reload();</script>");
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: toUpdateSubnet
	  * @Description: 到更新子网页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:30:40 PM
	 *//*
	@RequestMapping("/toUpdateSubnet")
	public String toUpdateSubnet(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String subnet_network_id = request.getParameter("networkId");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("subnet_network_id", subnet_network_id);
		String subnetId = request.getParameter("subnetId");
		try {
			request.setAttribute("subnet", networkService.getSubnetDetails(tenantId, tokenId, subnetId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_subnet_edit";
	}
	
	*//**
	 * 
	  * @Title: updateSubnet
	  * @Description: 更新子网
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:30:58 PM
	 *//*
	@RequestMapping("/updateSubnet")
	public String updateSubnet(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String type = request.getParameter("type");
		String subnet_network_id = request.getParameter("subnet_network_id");
		String updatesubnetId = request.getParameter("subnetId");
		String updatesubnetName = request.getParameter("subnetName");
		//String updatesubnetCidr = request.getParameter("updatesubnetCidr");
		//String updatesubnet_ip_version = request.getParameter("subnet_ip_version");
		String updatesubnet_gateway_ip = request.getParameter("subnet_gateway_ip");
		
		//若禁用网关，则置空gateway_ip
		String updatesubnet_gateway_forbidden = request.getParameter("subnetGatewayForbidden");
		if ("checked".equals(updatesubnet_gateway_forbidden)){
			updatesubnet_gateway_ip = "";
		}  
		try {
			if (updatesubnet_gateway_forbidden == null && updatesubnet_gateway_ip == "") {
				response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+subnet_network_id+"&type="+type+"&promptBoxFlag=-1';</script>");
				return null;
			}
		} catch (IOException e4) {
			e4.printStackTrace();
			throw new PopUpMessageException(e4.getMessage());
		}
		
		String updatesubnetEnable_dhcp = request.getParameter("subnetActiveDHCP");
		//String updatesubnet_allocation_pools = request.getParameter("subnetAllocAddrPool");
		String updatesubnet_dns_nameservers = request.getParameter("subnetDNS");
		String updatesubnet_host_routes = request.getParameter("subnetRoute");

		SubnetBean subnet = new SubnetBean();
		subnet.setSubnetId(updatesubnetId);
		subnet.setSubnetName(updatesubnetName);
		//subnet.setSubnet_network_id(subnet_network_id);
		//subnet.setSubnetCidr(updatesubnetCidr);
		//subnet.setSubnet_ip_version(updatesubnet_ip_version);
		subnet.setSubnet_gateway_ip(updatesubnet_gateway_ip);
		
		if ("checked".equals(updatesubnetEnable_dhcp)) {
			subnet.setSubnetEnable_dhcp(true);	
			//subnet.setSubnetEnable_dhcp(Boolean.parseBoolean(updatesubnetEnable_dhcp));
			if (updatesubnet_allocation_pools != "") {
				String[] arrSubnet_allocation_pools = updatesubnet_allocation_pools.split("[\n]");
				List<Map> poollist = new ArrayList<Map>();
				for(int i=0; i<arrSubnet_allocation_pools.length; i++){
					String[] arr = arrSubnet_allocation_pools[i].split(",");
					Map<String,String> map = new HashMap<String,String>();
					map.put("start", arr[0]);
					map.put("end", arr[1]);
					poollist.add(map);
				}
				subnet.setSubnet_allocation_pools(poollist);
			}
			
			if (updatesubnet_dns_nameservers != "") {
				String[] arrSubnet_dns_nameservers = updatesubnet_dns_nameservers.split("[\n]");
				List<String> dnslist = new ArrayList<String>();
				for(String s : arrSubnet_dns_nameservers){
					dnslist.add(s);
				}
				subnet.setSubnet_dns_nameservers(dnslist);
			}
			
			if (updatesubnet_host_routes != "") {
				String[] arrSubnet_host_routes = updatesubnet_host_routes.split("[\n]");
				List<String> routelist = new ArrayList<String>();
				for(String s : arrSubnet_host_routes){
					routelist.add(s);
				}
				subnet.setSubnet_host_routes(routelist);
			}
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			SubnetBean subnetBean = networkService.updateSubnet(tenantId, tokenId, subnet);
			request.setAttribute("actionLog", "更新了一个名称为"+updatesubnetName+"的子网");
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+subnet_network_id+"&type="+type+"&promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+subnet_network_id+"&type="+type+"&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deleteSubnetById
	  * @Description: 删除子网
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:32:43 PM
	 *//*
	@RequestMapping("/deleteSubnetById")
	public String deleteSubnetById(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String subnetId = request.getParameter("subnetId");
		String type = request.getParameter("type");
		String networkId = request.getParameter("networkId");
		String[] subnetIds = subnetId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject object = new JSONObject();
		int count = 0;
		try {
			for (int i = 0; i< subnetIds.length; i++) {
				String flag = networkService.deleteSubnet(tenantId, tokenId, subnetIds[i]);
				if(flag != null){
					count++;
					break ;
				} else{
					request.setAttribute("actionLog", "删除了一个ID为"+subnetIds[i]+"的子网");
				}
			}
			if (count==0) {
				object.put("data", true);
			} else {
				object.put("data", false);
			}
			if(count==0){
				response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+networkId+"&type="+type+"&promptBoxFlag=1';</script>");
			} else {
				response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+networkId+"&type="+type+"&promptBoxFlag=-1';</script>");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			object.put("error", e.getMessage());
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: getPort
	  * @Description: 查询所有端口信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:33:34 PM
	 *//*
	@RequestMapping("/getPort")
	public String getPort(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
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
			List<PortBean> portBeanLst = networkService.getPortList(tenantId, tokenId);
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> list = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			List<String> serverIdLst = new ArrayList<String>();
			for(ServerDetailBean sdb : list){
				String serverId = sdb.getId();
				serverIdLst.add(serverId);
			}
			
			List<ServerDetailBean> serverDetailBeanLst = new ArrayList<ServerDetailBean>();
			ServerDetailBean s = new ServerDetailBean();
			for(PortBean p : portBeanLst){
				if(p.getPortDevice_owner().equalsIgnoreCase("compute:nova")){
					if(serverIdLst.contains(p.getPort_device_id())){
						s = instanceServerService.instanceServerDetail(tenantId, tokenId, p.getPort_device_id());
						serverDetailBeanLst.add(s);
					}else{
						response.getWriter().print("<script>alert('实例不完整!')</script>");
					}
				}
			}
			request.setAttribute("ports", portBeanLst);
			request.setAttribute("servers", serverDetailBeanLst);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		} 
		
		return "home/ec2/ec2_virtual_port_list";
	}
	
	*//**
	 * 
	  * @Title: getPortDetail
	  * @Description: 根据Port ID查询指定端口的详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:33:51 PM
	 *//*
	@RequestMapping("/getPortDetail")
	public String getPortDetail(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String portId = request.getParameter("portId");
		try {
			 request.setAttribute("port", networkService.getPortDetails(tenantId, tokenId, portId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		 
		return "home/ec2/ec2_port_detail";
	}
	
	*//**
	 * 
	  * @Title: getPortDetailNew
	  * @Description: 根据Port ID查询指定端口的详细信息(New)
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:34:29 PM
	 *//*
	@RequestMapping("/getPortDetailNew")
	public String getPortDetailNew(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String networkId = request.getParameter("networkId");
		String portId = request.getParameter("portId");
		String type = request.getParameter("type");
		request.setAttribute("networkId", networkId);
		request.setAttribute("type", type);
		try {
			 request.setAttribute("port", networkService.getPortDetails(tenantId, tokenId, portId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		 
		return "home/ec2/ec2_port_detail_new";
	}
	
	*//**
	 * 
	  * @Title: getPortsByNetId
	  * @Description: 根据Network ID查询端口列表
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:35:01 PM
	 *//*
	@RequestMapping("/getPortsByNetId")
	public String getPortsByNetId(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String networkId = (String) request.getParameter("networkId");
		try {
			 request.setAttribute("ports", networkService.getPorts(tenantId, tokenId, networkId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		
		return "home/ec2/ec2_port_list";
	}
	
	*//**
	 * 
	  * @Title: toCreatePort
	  * @Description: 到创建接口页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:36:02 PM
	 *//*
	@RequestMapping("/toCreatePort")
	public String toCreatePort(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String port_network_id = request.getParameter("networkId");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("port_network_id", port_network_id);
		request.setAttribute("network", networkService.getNetworkDetails(tenantId, tokenId, port_network_id));
		return "home/ec2/ec2_port_create";
	}
	
	*//**
	 * 
	  * @Title: createPort
	  * @Description: 创建接口
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:36:20 PM
	 *//*
	@RequestMapping("/createPort")
	public String createPort(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String type = request.getParameter("type");
		String port_network_id = request.getParameter("port_network_id");
		String portName = request.getParameter("portName");
		String portAdmin_state_up = request.getParameter("portAdmin_state_up");
		String portDeviceId = request.getParameter("portDeviceId");
		String portDeviceOwner = request.getParameter("portDeviceOwner");
		
		PortBean portBeanRequest = new PortBean();
		
		if (port_network_id != "") {
			portBeanRequest.setPort_network_id(port_network_id);	
		}
		
		if (portName != "") {
			portBeanRequest.setPortName(portName);
		}
		
		if ("checked".equals(portAdmin_state_up)) {
			portBeanRequest.setPortAdmin_state_up(true);
		}
		
		if (portDeviceId != "") {
			portBeanRequest.setPort_device_id(portDeviceId);
		}
			
		if (portDeviceOwner != "") {
			portBeanRequest.setPortDevice_owner(portDeviceOwner);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			PortBean portBean = networkService.createPort(tenantId, tokenId, portBeanRequest);
			request.setAttribute("actionLog", "创建了一个名称为"+portName+"的接口");
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+port_network_id+"&type="+type+"&promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+port_network_id+"&type="+type+"&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: toUpdatePort
	  * @Description: 到更新端口页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:36:39 PM
	 *//*
	@RequestMapping("/toUpdatePort")
	public String toUpdatePort(HttpServletRequest request,HttpServletResponse response) {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String port_network_id = request.getParameter("networkId");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		request.setAttribute("port_network_id", port_network_id);
		String portId = request.getParameter("portId");
		try {
			request.setAttribute("port", networkService.getPortDetails(tenantId, tokenId, portId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_port_edit";
	}
	
	*//**
	 * 
	  * @Title: updatePort
	  * @Description: 更新端口
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:37:30 PM
	 *//*
	@RequestMapping("/updatePort")
	public String updatePort(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String type = request.getParameter("type");
		String port_network_id = request.getParameter("port_network_id");
		
		String updateportId = request.getParameter("portId");
		String updateportName = request.getParameter("portName");
		String updateportAdmin_state_up = request.getParameter("portAdmin_state_up");
		String updateportDeviceId = request.getParameter("portDeviceId");
		String updateportDeviceOwner = request.getParameter("portDeviceOwner");
		
		PortBean port = new PortBean();
		port.setPortId(updateportId);
		//port.setPort_network_id(port_network_id);
		port.setPortName(updateportName);
		
		if ("checked".equals(updateportAdmin_state_up)) {
			port.setPortAdmin_state_up(true);
		} else {
			port.setPortAdmin_state_up(false);
		}
		
		port.setPort_device_id(updateportDeviceId);
		port.setPortDevice_owner(updateportDeviceOwner);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			PortBean portBean = networkService.updatePort(tenantId, tokenId, port);
			request.setAttribute("actionLog", "更新了一个名称为"+updateportName+"的接口");
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+port_network_id+"&type="+type+"&promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+port_network_id+"&type="+type+"&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deletePortById
	  * @Description: 删除端口（方法一）
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:37:50 PM
	 *//*
	@RequestMapping("/deletePortById")
	public String deletePortById(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String portId = request.getParameter("portId");
		String type = request.getParameter("type");
		String networkId = request.getParameter("networkId");
		String[] portIds = portId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		int count = 0;
		try {
			for (int i = 0; i < portIds.length; i++) {
				String flag = networkService.deletePort(tenantId, tokenId, portIds[i]);
				if(flag != null){
					count++;
					break;
				} else{
					request.setAttribute("actionLog", "删除了一个ID为"+portIds[i]+"的接口");
				}
			}
			if (count == 0) {
				response.getWriter().print("<script>window.location.href='getPort?promptBoxFlag=1';</script>");
			} 
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getPort?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deletePortById2
	  * @Description: 删除端口（方法二）
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:38:06 PM
	 *//*
	@RequestMapping("/deletePortById2")
	public String deletePortById2(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String portId=request.getParameter("portId");
		String type=request.getParameter("type");
		String networkId=request.getParameter("networkId");
		String[] portIds = portId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject object = new JSONObject();
		int count = 0;
		try {
			for (int i = 0; i< portIds.length; i++) {
				String flag = networkService.deletePort(tenantId, tokenId, portIds[i]);
				if(flag != null){
					count++;
					break ;
				} else{
					request.setAttribute("actionLog", "删除了一个ID为"+portIds[i]+"的接口");
				}
			}
			if (count==0) {
				object.put("data", true);
			} else {
				object.put("data", false);
			}
			
			if(count==0){
				response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+networkId+"&type="+type+"&promptBoxFlag=1';</script>");
			} else {
				response.getWriter().print("<script>window.location.href='getNetworkDetail?networkId="+networkId+"&type="+type+"&promptBoxFlag=-1';</script>");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			object.put("error", e.getMessage());
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		} 
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
		//response.getWriter().print("<script>parent.document.getElementById('mainiframe').contentWindow.document.getElementById('portListFrame').contentWindow.location.reload();</script>");
	}
}*/