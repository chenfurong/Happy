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
import com.ibm.smartcloud.core.util.SpringHelper;
import com.ibm.smartcloud.openstack.neutron.service.FloatIpService;
import com.ibm.smartcloud.openstack.neutron.service.NetworkService;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;

*//**
 * 
 * @Title：FloatIpController 
 * @Description: 浮动IP模块
 * @Auth: ZhangLong
 * @CreateTime:Mar 25, 2015 5:39:05 PM     
 * @version V1.0
 *//*
@Controller
public class FloatIpController extends BaseController{
	
	@Autowired
	private FloatIpService floatIpService;
	@Autowired
	private NetworkService networkService;
	@Autowired
	private InstanceService instanceServerService;
	
	*//**
	 * 
	  * @Title: getAllFloatIp
	  * @Description: 获取所有浮动IP
	  * @param @param request
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:40:20 PM
	 *//*
	@RequestMapping("/getAllFloatIp")
	public String getAllFloatIp(HttpServletRequest request) throws Exception {
		
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
			List<FloatIpBean> floatIpList = floatIpService.getAllFloatIp(tenantId, tokenId);
			request.setAttribute("floatIpList",floatIpList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_floatIp_list";
	}
	
	*//**
	 * 
	  * @Title: getFloatIpDetails
	  * @Description: 根据ID查询浮动IP详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:40:55 PM
	 *//*
	@RequestMapping("getFloatIpDetails")
	public String getFloatIpDetails(HttpServletRequest request,HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String id = request.getParameter("id");
		FloatIpBean floatIpBean;
		try {
			floatIpBean = floatIpService.getFloatIpById(tenantId, tokenId, id);
			request.setAttribute("floatIpBean", floatIpBean);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_floatIp_detail";
	}
	
	*//**
	 * 
	  * @Title: toAllocateFloatIp
	  * @Description: 去分配浮动IP页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:42:25 PM
	 *//*
	@RequestMapping("toAllocateFloatIp")
	public String toAllocateFloatIp(HttpServletRequest request,HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		try {
			List<NetworkBean> networkList = networkService.getNetworkList(tenantId, tokenId);
			List<NetworkBean> networks = new ArrayList<NetworkBean>();
			for(NetworkBean network : networkList){
				if(network.isNetRouter_external() && network.getNetSubnets().size()>0){
					networks.add(network);
				}
			}
			request.setAttribute("networks", networks);
			//浮动Ip数量
			int floatingIpCount = floatIpService.getAllFloatIp(tenantId, tokenId).size();
			request.setAttribute("floatingIpCount", floatingIpCount);
			//浮动Ip总数
			int floatingIpTotal = floatIpService.getQuota(tenantId, tokenId).getFloatingip();
			request.setAttribute("floatingIpTotal", floatingIpTotal);
			//浮动Ip数量与浮动Ip总数的百分比值
			int percentage = floatingIpCount*100 / floatingIpTotal;
			request.setAttribute("percentage", percentage);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_floatIp_allocateAddress";
	}
	
	*//**
	 * 
	  * @Title: allocateFloatIp
	  * @Description: 分配浮动IP
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:42:53 PM
	 *//*
	@RequestMapping("allocateFloatIp")
	public String allocateFloatIp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String pool = request.getParameter("floatIpPool");
		
		try {
			String message = floatIpService.allocateFloatIp(tenantId, tokenId, pool);
			request.setAttribute("actionLog", "分配浮动IP给项目"+pool);
			response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deallocateFloatIp
	  * @Description: 释放浮动IP (删除浮动IP)
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:43:14 PM
	 *//*
	@RequestMapping("deallocateFloatIp")
	public String deallocateFloatIp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String id=request.getParameter("id");
		String[] ids = id.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
//			int count = 0;
			for (int i = 0; i < ids.length; i++) {
				String message = floatIpService.deleteFloatIp(tenantId, tokenId, ids[i]);
//				if (message != ""){
//					count++;
//				} else {
					request.setAttribute("actionLog", "释放了一个ID为"+ids[i]+"的浮动IP");
//				}
			}
//			if(count == 0){
				response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=1';</script>");
//			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: toAddFloatIpToInstance
	  * @Description: 去关联浮动IP到实例页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:43:49 PM
	 *//*
	@RequestMapping("toAddFloatIpToInstance")
	public String toAddFloatIpToInstance(HttpServletRequest request,HttpServletResponse response){
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		try {
			request.setAttribute("floatIpList", floatIpService.getAllFloatIp(tenantId, tokenId));
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			List<ServerDetailBean> instanceList = instanceServerService.instanceServerList(tenantId, tokenId,isAdminRole);
			List<ServerDetailBean> instances = new ArrayList<ServerDetailBean>();
			for(ServerDetailBean server : instanceList){
				if(server.getStatus().equalsIgnoreCase("ACTIVE") || server.getStatus().equalsIgnoreCase("SHUTOFF")){
					instances.add(server);
				}
			}
			request.setAttribute("instanceList", instances);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_floatIp_addToInstance";	
	}
	
	*//**
	 * 
	  * @Title: addFloatIpToInstance
	  * @Description: 关联浮动IP到实例
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:44:39 PM
	 *//*
	@RequestMapping("addFloatIpToInstance")
	public String addFloatIpToInstance(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String serverId = request.getParameter("instanceId");
		String address = request.getParameter("floatIp");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String message = floatIpService.addFloatIpToInstance(tenantId, tokenId, serverId, address);
			request.setAttribute("actionLog", "关联浮动IP,ID为"+address);
			response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: removeFloatingIp
	  * @Description: 将浮动IP分离出实例
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 5:45:02 PM
	 *//*
	@RequestMapping("removeFloatingIp")
	public String removeFloatingIp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tokenId = "";
		String tenantId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
			tenantId = OpenStackUtil.getTenantId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		
		String id = request.getParameter("id");
		String instanceId = request.getParameter("instanceId");
		String ip = request.getParameter("ip");
//		request.setAttribute("id", id);
//		request.setAttribute("instanceId", instanceId);
//		request.setAttribute("ip", ip);
		
		String[] arrId = id.split(",");
		String[] arrInstanceId = instanceId.split(",");
		String[] arrIp = ip.split(",");
		
		try {
//			int count = 0;
			for(int i=0; i<arrId.length; i++){
				String oneInstanceId = arrInstanceId[i];
				String oneIp = arrIp[i];
				String message = floatIpService.removeFloatIpFromInstance(tenantId, tokenId, oneInstanceId, oneIp);
//				if(message != ""){
//					count++;
//				} else{
					request.setAttribute("actionLog", "分离弹性IP, 地址为"+ip);
//				}
			}
//			if(count == 0){
				response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=1';</script>");
//			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFloatIp?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}	
}*/