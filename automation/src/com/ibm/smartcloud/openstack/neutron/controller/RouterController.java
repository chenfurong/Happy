/*package com.ibm.smartcloud.openstack.neutron.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.exception.BaseException;
import com.ibm.smartcloud.core.exception.ErrorMessageException;
import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;
import com.ibm.smartcloud.core.exception.handler.PopUpMessageException;
import com.ibm.smartcloud.core.util.OpenStackUtil;
import com.ibm.smartcloud.openstack.neutron.service.NetworkService;

*//**
 * 
 * @Title：RouterController 
 * @Description:    路由模块
 * @Auth: ZhangLong
 * @CreateTime:Mar 25, 2015 6:04:42 PM     
 * @version V1.0
 *//*
@Controller
public class RouterController extends BaseController{
	
	@Autowired
	private NetworkService networkService;
	
	*//**
	 * 
	  * @Title: getAllRouter
	  * @Description: 获取所有路由
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:05:01 PM
	 *//*
	@RequestMapping("/getAllRouter")
	public String getAllRouter(HttpServletRequest request, HttpServletResponse response) {
		
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
		
		try{
			//查询所有路由
			request.setAttribute("routers", routerService.getRouterList(tenantId,tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_router_list";
		
	}
	
	*//**
	 * 
	  * @Title: getRouterDetails
	  * @Description: 根据ID获取路由详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:05:30 PM
	 *//*
	@RequestMapping("getRouterDetails")
	public String getRouterDetails(HttpServletRequest request, HttpServletResponse response){
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String type = request.getParameter("type");
		String errorMessage = request.getParameter("errorMessage");
		String routerId = request.getParameter("routerId");
		String routerName = request.getParameter("routerName");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("type", type);
		request.setAttribute("errorMessage", errorMessage);
		request.setAttribute("routerId", routerId);
		request.setAttribute("routerName", routerName);
		
		List<PortBean> portBeanList = new ArrayList<PortBean>();
		try{
			request.setAttribute("router", routerService.getRouterDetails(routerId,tenantId,tokenId));
			portBeanList = routerService.getAllRouterInterface(routerId,tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("portBeanList", portBeanList);
		
		return "home/ec2/ec2_router_detailAndInterface";
	}
	
	*//**
	 * 
	  * @Title: toCreateRouter
	  * @Description: 去创建路由页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:06:01 PM
	 *//*
	@RequestMapping("/toCreateRouter")
	public String toCreateRouter(HttpServletRequest request, HttpServletResponse response){
		
		return "home/ec2/ec2_router_create";
	}
	
	*//**
	 * 
	  * @Title: createRouter
	  * @Description: 创建路由
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:06:14 PM
	 *//*
	@RequestMapping("/createRouter")
	public String createRouter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String routerName = request.getParameter("routerName");
		RouterBean routerBean = new RouterBean();
		routerBean.setName(routerName);

		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			RouterBean router = routerService.createRouter(routerBean,tenantId,tokenId);
			request.setAttribute("actionLog", "创建一个名称为"+routerName+"的路由");
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: toGateway
	  * @Description: 去设置网关页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:06:34 PM
	 *//*
	@RequestMapping("/toGateway")
	public String toGateway(HttpServletRequest request, HttpServletResponse response){
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String routerId = request.getParameter("routerId");
		try{
			request.setAttribute("router", routerService.getRouterDetails(routerId,tenantId,tokenId));
			List<NetworkBean> listSrc = routerService.getNetworkList(tenantId,tokenId);
			List<NetworkBean> listDesc = new ArrayList<NetworkBean>();
			for(NetworkBean n : listSrc){
				if(n.isNetRouter_external()){
					listDesc.add(n);
				}
			}
			request.setAttribute("networkList", listDesc);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_router_gateway";
	}
	
	*//**
	 * 
	  * @Title: setGateway
	  * @Description: 设置网关
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:06:50 PM
	 *//*
	@RequestMapping("/setGateway")
	public String setGateway(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String routerId = request.getParameter("id");
		String routerName = request.getParameter("name");
		String networkId = request.getParameter("networkId");
		
		RouterBean routerBean = new RouterBean();
		try {
			routerBean = routerService.getRouterDetails(routerId, tenantId, tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
//		routerBean.setAdmin_state_up(true);
//		routerBean.setName(routerName);
		ExternalGatewayInfoBean externalGatewayInfo = new ExternalGatewayInfoBean();
		externalGatewayInfo.setNetwork_id(networkId);
		routerBean.setExternal_gateway_info(externalGatewayInfo);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		//根据网络设置网关
		try {
			RouterBean r = routerService.updateRouterSetGateWay(routerBean,tenantId,tokenId);
			request.setAttribute("actionLog", "给"+routerName+"设置网关");
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		//查询所有实例
		request.setAttribute("routers",routerService.getRouterList(tenantId,tokenId));
		
		return "home/ec2/ec2_router_list";
	}
	
	*//**
	 * 
	  * @Title: toCancelGateway
	  * @Description: 去取消网关页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:07:10 PM
	 *//*
	@RequestMapping("/toCancelGateway")
	public String toCancelGateway(HttpServletRequest request, HttpServletResponse response){
		
		String routerId = request.getParameter("routerId");
		request.setAttribute("routerId", routerId);
		String routerName = request.getParameter("routerName");
		request.setAttribute("routerName", routerName);
		
		return "home/ec2/ec2_router_gateway_delete";
	}
	
	*//**
	 * 
	  * @Title: cancelGateway
	  * @Description: 取消网关
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:07:30 PM
	 *//*
	@RequestMapping("/cancelGateway")
	public String cancelGateway(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String routerId = request.getParameter("routerId");
		
		//根据网络设置网关
		try {
			RouterBean r = routerService.updateRouterSetGateWay(routerId,tenantId,tokenId);
			request.setAttribute("actionLog", "取消网关"+routerId);
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deleteRouter
	  * @Description: 删除路由
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:07:49 PM
	 *//*
	@RequestMapping("/deleteRouter")
	public String deleteRouter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String params = (String) request.getParameter("routerIds");
		String[] arr = params.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			for(int i=0; i<arr.length; i++){
				String message = routerService.deleteRouter(arr[i], tenantId, tokenId);
				request.setAttribute("actionLog", "删除了一个ID为"+arr[i]+"路由");
			}
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllRouter?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: getAllRouterInterface
	  * @Description: 获取某一路由对应的所有接口
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:08:04 PM
	 *//*
	@RequestMapping("/getAllRouterInterface")
	public String getAllRouterInterface(HttpServletRequest request, HttpServletResponse response){
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String routerId = request.getParameter("routerId");
		String routerName = request.getParameter("routerName");
		List<PortBean> portBeanList = new ArrayList<PortBean>();
		try{
			portBeanList = routerService.getAllRouterInterface(routerId,tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("portBeanList", portBeanList);
		request.setAttribute("routerId", routerId);
		request.setAttribute("routerName", routerName);
		
		return "home/ec2/ec2_router_interface_list";
	}
	
	*//**
	 * 
	  * @Title: getRouterInterfaceDetails
	  * @Description: 获取路由接口的详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:09:34 PM
	 *//*
	@RequestMapping("/getRouterInterfaceDetails")
	public String getRouterInterfaceDetails(HttpServletRequest request, HttpServletResponse response){
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String routerInterfaceId = request.getParameter("routerInterfaceId");
		try{
			request.setAttribute("portBean", routerService.getRouterInterfaceDetails(routerInterfaceId,tenantId,tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		String routerName = request.getParameter("routerName");
		request.setAttribute("routerName", routerName);
		
		return "home/ec2/ec2_router_interface_details";
	}
	
	*//**
	 * 
	  * @Title: toCreateRouterInterface
	  * @Description: 去创建路由接口页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:09:59 PM
	 *//*
	@RequestMapping("/toCreateRouterInterface")
	public String toCreateRouterInterface(HttpServletRequest request, HttpServletResponse response) {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String routerId = request.getParameter("routerId");
		String routerName = request.getParameter("routerName");
		request.setAttribute("routerId", routerId);
		request.setAttribute("routerName", routerName);
		
		try{
			List<NetworkBean> networkList = networkService.getNetworkList(tenantId,tokenId);
			List<SubnetBean> subnetList = new ArrayList<SubnetBean>();
			for(NetworkBean network : networkList){
				if(network.getNetSubnets().size() > 0){
					List<String> subnetIds = network.getNetSubnets();
					for(String subnetId : subnetIds){
						SubnetBean subnet = networkService.getSubnetDetails(tenantId, tokenId, subnetId);
						subnet.setNetworkName(network.getNetName());
						subnetList.add(subnet);
					}
				}
			}
			request.setAttribute("subnetList", subnetList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_router_interface_create";
	}
	
	*//**
	 * 
	  * @Title: createRouterInterface
	  * @Description: 创建路由接口
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:10:28 PM
	 *//*
	@RequestMapping("/createRouterInterface")
	public String createRouterInterface(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String routerId = request.getParameter("routerId");
		String subnetId = request.getParameter("subnetId");
		String routerName = request.getParameter("routerName");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			String message = routerService.createRouterInterface(routerId, subnetId,tenantId,tokenId);
			request.setAttribute("actionLog", "给路由创建接口"+subnetId);
			response.getWriter().print("<script>window.location.href='getRouterDetails?routerId="+routerId+"&&routerName="+routerName+"&&type=port&&promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getRouterDetails?routerId="+routerId+"&&routerName="+routerName+"&&type=port&&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deleteRouterInterface
	  * @Description: 删除路由接口
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:10:52 PM
	 *//*
	@RequestMapping("/deleteRouterInterface")
	public String deleteRouterInterface(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String routerInterfaceId = (String) request.getParameter("portId");
		String subnetId =  request.getParameter("subnetId");
		String routerId =  request.getParameter("routerId");
		String routerName = request.getParameter("routerName");
		request.setAttribute("routerId", routerId);
		request.setAttribute("routerName", routerName);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String message = routerService.deleteRouterInterface(routerInterfaceId, subnetId, routerId, tenantId, tokenId);
			request.setAttribute("actionLog", "删除路由接口"+subnetId);
			response.getWriter().print("<script>window.location.href='getRouterDetailsAndAllInterface?routerId="+routerId+"&&routerName="+routerName+"&&type=port&&promptBoxFlag=1'</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getRouterDetailsAndAllInterface?routerId="+routerId+"&&routerName="+routerName+"&&type=port&&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
}*/