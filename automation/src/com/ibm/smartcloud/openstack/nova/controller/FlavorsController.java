package com.ibm.smartcloud.openstack.nova.controller;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.exception.BaseException;
import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;
import com.ibm.smartcloud.core.exception.handler.PopUpMessageException;
import com.ibm.smartcloud.core.util.OpenStackUtil;
import com.ibm.smartcloud.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.keystone.service.IdentityService;
import com.ibm.smartcloud.openstack.nova.bean.FlavorBean;
import com.ibm.smartcloud.openstack.nova.service.FlavorsService;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;

/**
 * @Title：FlavorsController 
 * @Description: 实例类型   
 * @Auth: LiangRui
 * @CreateTime:2015年3月25日 下午4:59:08     
 * @version 1.0
 */
@Controller
public class FlavorsController extends BaseController {
	@Autowired
	private FlavorsService flavorsService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private InstanceService instanceService;
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");

    /**
      * @Title: getAllFlavors
      * @Description: 查询所有实例类型
      * @param @param request
      * @param @return
      * @return String
      * @author LiangRui
      * @throws
      * @Time 2015年3月25日 下午5:08:58
     */
	@RequestMapping("/getAllFlavors")
	public String getAllFlavors(HttpServletRequest request)  {
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
		
		String htype = request.getParameter("htype");
		request.setAttribute("htype", htype);
		// 查询所有实例类型
		try {
			List<FlavorBean> list = flavorsService.getAllFlavorsList(tenantId,tokenId);
			request.setAttribute("flavorObj", list);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		return "home/ec2/ec2_flavor_list";
	}

	/**
	  * @Title: getFlavorDetails
	  * @Description: 查询单个实例类型的详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:09:13
	 *//*
	@RequestMapping("/getFlavorbyId")
	public String getFlavorDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String flavorId = request.getParameter("flavorId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		FlavorV2Bean flavorObj = null;		
		List<OPSTFlavorAccessBean> flavorAccess = null;
		List<FlavorExtraBean> flavorExtras = null;
		try {
			flavorObj = flavorsService.getFlavorsDetails(flavorId,tenantId,tokenId);
			if(flavorObj.getIsPublic()!=null&&flavorObj.getIsPublic()=="false"){
				// 查询实例类型的权限
				flavorAccess = flavorsService.listFlavorsAccess(flavorId,tenantId,tokenId);
			}
			flavorExtras = flavorsService.getFlavorMetaData(flavorId, tenantId, tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		request.setAttribute("flavorObj",flavorObj);
		request.setAttribute("flavorAccess", flavorAccess);
		request.setAttribute("pageStatus", "detailView");
		request.setAttribute("flavorExtras", flavorExtras);
		return "home/ec2/ec2_flavor_detail";
	}

	*//**
	  * @Title: createFlavorsData
	  * @Description:跳转到创建一个实例类型 
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:09:22
	 *//*
	@RequestMapping("/createFlavorsData")
	public String createFlavorsData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tokenId = "";
		try{
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		try {
			request.setAttribute("projectObj", identityService.getProjectList(tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 		
		request.setAttribute("pageStatus", "create");
		return "home/ec2/ec2_flavor_detail_create";
	}
	
	*//**
	  * @Title: createFlavor
	  * @Description: 创建一个实例类型
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:09:36
	 *//*
	@RequestMapping("/createFlavors")
	public String createFlavor(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
		String name = request.getParameter("name");
		String ram = request.getParameter("ram");
		String disk = request.getParameter("disk");
		String ephemeral = request.getParameter("ephemeral");
		String swap = request.getParameter("swap");
		String vcpus = request.getParameter("vcpus");
		String id = UUID.randomUUID().toString();//id 自动生成 uuid
		FlavorV2Bean flavorV2Bean = new FlavorV2Bean();
		flavorV2Bean.setId(id);
		flavorV2Bean.setName(name);
		flavorV2Bean.setDisk(disk);
		flavorV2Bean.setEphemeral(ephemeral);
		flavorV2Bean.setSwap(swap);
		flavorV2Bean.setVcpus(vcpus);
		flavorV2Bean.setRam(ram);	
		try {
			String selNetwork = request.getParameter("selNetwork");
			String[] networkId = selNetwork.split(";");
			if(networkId != null&&networkId.length>1){
				flavorV2Bean.setIsPublic("false");
				flavorsService.createFlavors(flavorV2Bean, tenantId, tokenId);
				request.setAttribute("actionLog", "创建名称为"+name+"的实例类型");
				for (int i = 1; i < networkId.length; i++) {
					flavorsService.createFlavorsAccess(id, networkId[i], tenantId, tokenId);
					request.setAttribute("actionLog", "创建名称为"+name+"的实例类型访问租户权限");
				}
			}else{
				flavorV2Bean.setIsPublic("true");
				flavorsService.createFlavors(flavorV2Bean, tenantId, tokenId);
				request.setAttribute("actionLog", "创建名称为"+name+"的实例类型");
			}
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}

	*//**
	  * @Title: deteleFlavor
	  * @Description: 根据ID删除实例类型
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:09:44
	 *//*
	@RequestMapping("/deteleFlavors")
	public String deteleFlavor(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String flavorId = request.getParameter("flavorId");
		String[] ids = flavorId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		List<ServerDetailBean> serviceList = null;
		int count = 0;
		try {
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			serviceList = instanceService.instanceServerList(tenantId,tokenId,isAdminRole);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}				
		try {
			String comStr = "" ;
			boolean d ;
			//将实例中包含的类型
			for (int j = 0; j < serviceList.size(); j++) {
				String fId = serviceList.get(j).getFlavor();
				comStr += fId;
			}
			for (int i = 0; i < ids.length; i++) {
				//如果d为TRUE，说明该类型被关联了
				d = comStr.contains(ids[i]);
				if (d) {
					count++;
					break;
				} else {
					flavorsService.deleteFlavors(ids[i], tenantId, tokenId);
					request.setAttribute("actionLog", "删除ID为"+ids[i]+"的实例类型");
				}
			}
			if (count == 0) {	
				response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=1';</script>");
			}else{
				response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=-1&errorMessage="+errorMessage+"';</script>");
			}
			
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}	
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}

	*//**
	  * @Title: updateFlavorAccessData
	  * @Description: 跳转到修改实例类型的权限
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:09:59
	 *//*
	@RequestMapping("/updateFlavorAccess")
	public String updateFlavorAccessData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String flavorId =request.getParameter("flavorId");		
		request.setAttribute("pageStatus", "modifyAccess");//编辑一个实例类型 状态类型
		List<ProjectBean> projectObj = null;
		try {
			FlavorV2Bean flavorObj = flavorsService.getFlavorsDetails(flavorId,tenantId,tokenId);
			request.setAttribute("flavorObj",flavorObj);
			projectObj = identityService.getProjectList(tokenId);		
			if(flavorObj.getIsPublic()!=null&&flavorObj.getIsPublic()=="false"){
				List<OPSTFlavorAccessBean> flavorAccessList = flavorsService.listFlavorsAccess(flavorId,tenantId,tokenId);
				String flavorAccessIds="";
				for(OPSTFlavorAccessBean fa:flavorAccessList){
					flavorAccessIds+=fa.getTenant_id()+";";
				}
				for(ProjectBean pb:projectObj){
					if(flavorAccessIds.contains(pb.getId()))
						projectObj.remove(pb);
				}
				request.setAttribute("flavorAccessIds",flavorAccessIds);
				// 查询实例类型的权限
				request.setAttribute("flavorAccess", flavorAccessList);	
			}else{
				request.setAttribute("flavorAccess", "");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		request.setAttribute("projectObj", projectObj);
		return "home/ec2/ec2_flavor_detail_create";
	}
	
	*//**
	  * @Title: listFlavorAccess
	  * @Description:获取实例访问队列
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:10:06
	 *//*
	@RequestMapping("/listFlavorsAccess")
	public String listFlavorAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String flavorId = request.getParameter("flavorId");	
		List<OPSTFlavorAccessBean> list = null;
		try {
			// 查询所有实例类型
			list = flavorsService.listFlavorsAccess(flavorId, tenantId, tokenId);			
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
		}	
		request.setAttribute("flavorObj",list);
		
		return "";
	}
	
	*//**
	  * @Title: createFlavorAccess
	  * @Description: 创建实例类型访问租户权限
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:10:12
	 *//*
	@RequestMapping("/createFlavorsAccess")
	public String createFlavorAccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String flavorId = request.getParameter("flavorId");
		String tenantName =request.getParameter("tenantName");
		try {
			flavorsService.createFlavorsAccess(flavorId, tenantName,tenantId,tokenId);
			request.setAttribute("actionLog", "创建ID为"+flavorId+"的实例类型访问租户权限");
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "";
	}
	
	*//**
	  * @Title: deteleFlavorAccess
	  * @Description:删除实例类型访问租户权限
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:10:22
	 *//*
	@RequestMapping("/deteleFlavorsAccess")
	public String deteleFlavorAccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		
		String flavorId = request.getParameter("flavorId");
		try {
			flavorsService.deleteFlavorsAccess(flavorId,tenantId,tokenId);
			request.setAttribute("actionLog", "删除ID为"+flavorId+"的实例类型访问租户权限");
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllFlavors?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}	
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}*/
}