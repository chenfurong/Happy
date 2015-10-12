package com.ibm.smartcloud.openstack.neutron.controller;


import java.util.ArrayList;
import java.util.List;

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
import com.ibm.smartcloud.core.util.OpenStackUtil;

/**
 * 
 * @Title：SecurityGroupsController 
 * @Description:    安全组模块
 * @Auth: ZhangLong
 * @CreateTime:Mar 25, 2015 6:11:27 PM     
 * @version V1.0
 */
@Controller
public class SecurityGroupsController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(SecurityGroupsController.class);
	
	private static final String SECURITYGROUPS_RULEDIRECTION_INGRESS = "ingress";           //入站
	private static final String SECURITYGROUPS_RULEDIRECTION_EGRESS = "egress";             //出站
	
	/**
	 * 
	  * @Title: getAllSecurityGroups
	  * @Description: 获取所有安全组
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:12:08 PM
	 */
	/*@RequestMapping("/getAllSecurityGroups")
	public String getAllSecurityGroups(HttpServletRequest request) {
		
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
		
		try {
			request.setAttribute("sercurityGroupsList",securityGroupsService.getSercurityGroupsList(tenantId,tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_securityGroups_list";
	}
	
	*//**
	 * 
	  * @Title: getSecurityGroupsDetails
	  * @Description: 根据ID获取安全组详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:12:31 PM
	 *//*
	@RequestMapping("/getSecurityGroupsDetails")
	public String getSecurityGroupsDetails(HttpServletRequest request, HttpServletResponse response) {
		
		String type = request.getParameter("type");
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		String errorMessage = request.getParameter("errorMessage");
		request.setAttribute("type", type);
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", errorMessage);
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String securityGroupsId = request.getParameter("securityGroupsId");
		if(securityGroupsId==null){
			throw new NullPointerException();
		}
		try {
			SecurityGroupsBean sgBean = securityGroupsService.getSercurityGroupsBeanById(securityGroupsId, tenantId, tokenId);
			request.setAttribute("sercurityGroupsBean", sgBean);
			List<SecurityGroupsRuleBean> ingressRuleList = new ArrayList<SecurityGroupsRuleBean>();
			List<SecurityGroupsRuleBean> egressRuleList = new ArrayList<SecurityGroupsRuleBean>();
			List<SecurityGroupsRuleBean> ruleList = sgBean.getRuleList();
			if(ruleList.size()>0){
				for(int i=0;i<ruleList.size();i++){
					if(ruleList.get(i).getDirection()!=null&&ruleList.get(i).getDirection().equals(SECURITYGROUPS_RULEDIRECTION_INGRESS)){
						ingressRuleList.add(ruleList.get(i));
					}else if(ruleList.get(i).getDirection()!=null&&ruleList.get(i).getDirection().equals(SECURITYGROUPS_RULEDIRECTION_EGRESS)){
						egressRuleList.add(ruleList.get(i));
					}else{					
					}
				}
			}
			sgBean.setIngressRuleList(ingressRuleList);
			sgBean.setEgressRuleList(egressRuleList);
			request.setAttribute("sercurityGroupsBean", sgBean);
			request.setAttribute("ingressRuleList", ingressRuleList);
			request.setAttribute("egressRuleList", egressRuleList);		
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "home/ec2/ec2_securityGroups_detail";
	}
	
	*//**
	 * 
	  * @Title: goCreateSecurityGroups
	  * @Description: 到创建安全组页面
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:13:03 PM
	 *//*
	@RequestMapping("/goCreateSecurityGroups")
	public String goCreateSecurityGroups(HttpServletRequest request) {
		
		return "home/ec2/ec2_securityGroups_create";
	}
	
	*//**
	 * 
	  * @Title: checkSgNameByName
	  * @Description: 根据安全组名称查看是否已存在相同名称的安全组
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:13:21 PM
	 *//*
	@RequestMapping("/checkSgNameByName")
	public String checkSgNameByName(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}  
		
		String sgName = request.getParameter("sgName");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JSONObject json = new JSONObject();
		Boolean isExist = true; 
		try {
			//查询安全组列表是否存在相同安全组名
			Boolean flag = securityGroupsService.checkSgName(sgName, tenantId, tokenId);
			if (!flag) {
				isExist = false;//存在相同的安全组名
			}	
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			logger.error("查看是否有相同的安全组名称时发生"+e.getMessage()+"异常");
			json.put("isExist", false);//发生异常按存在相同用户名处理
		}
		json.put("isExist", isExist);
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: createSecurityGroups
	  * @Description: 创建安全组
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:14:17 PM
	 *//*
	@RequestMapping("/createSecurityGroups")
	public String createSecurityGroups(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String sgName = request.getParameter("sgName");
		String sgDescription = request.getParameter("sgDescription");
		
		SecurityGroupsBean sgBean = new SecurityGroupsBean();
		sgBean.setName(sgName);
		sgBean.setDescription(sgDescription);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			SecurityGroupsBean securityGroupBean = securityGroupsService.createSercurityGroups(sgBean, tenantId, tokenId);
			request.setAttribute("actionLog", "创建了一个名称为"+sgName+"的安全组");
			response.getWriter().print("<script>window.location.href='getAllSecurityGroups?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getAllSecurityGroups?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
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
	  * @Title: goUpdateSecurityGroups
	  * @Description: 到更新安全组页面
	  * @param @param request
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:14:42 PM
	 *//*
	@RequestMapping("/goUpdateSecurityGroups")
	public String goUpdateSecurityGroups(HttpServletRequest request) throws Exception {
		String securityGroupsId = request.getParameter("securityGroupsId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		try {
			SecurityGroupsBean sgBean = securityGroupsService.getSercurityGroupsBeanById(securityGroupsId,tenantId,tokenId);
			request.setAttribute("sgBean", sgBean);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 		
		return "home/ec2/ec2_securityGroups_update";
	}
	
	*//**
	 * 
	  * @Title: updateSecurityGroups
	  * @Description: 更新安全组
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:15:27 PM
	 *//*
	@RequestMapping("/updateSecurityGroups")
	public String updateSecurityGroups(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
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
		String sgId=request.getParameter("sgId");
		String sgName=request.getParameter("sgName");
		String sgDescription=request.getParameter("sgDescription");
		
		SecurityGroupsBean sgBean = new SecurityGroupsBean();
		sgBean.setId(sgId);
		sgBean.setName(sgName);
		sgBean.setDescription(sgDescription);
		
		try {
			SecurityGroupsBean sgBeanResult = securityGroupsService.updateSercurityGroups(sgBean,tenantId,tokenId);
			request.setAttribute("actionLog", "更新了一个名称为"+sgName+"的安全组");
			response.getWriter().print("<script>window.location.href='getAllSecurityGroups?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllSecurityGroups?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: toCopySecurityGroup
	  * @Description: 到复制安全组页面
	  * @param @param request
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:16:06 PM
	 *//*
	@RequestMapping("/toCopySecurityGroup")
	public String toCopySecurityGroup(HttpServletRequest request) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String securityGroupId = request.getParameter("securityGroupId");
		try {
			SecurityGroupsBean securityGroup = securityGroupsService.getSercurityGroupsBeanById(securityGroupId, tenantId, tokenId);
			request.setAttribute("securityGroup", securityGroup);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 		
		return "home/ec2/ec2_securityGroups_copy";
	}
	
	*//**
	 * 
	  * @Title: copySecurityGroup
	  * @Description: 复制安全组
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:16:20 PM
	 *//*
	@RequestMapping("/copySecurityGroup")
	public String copySecurityGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String orgSgId = request.getParameter("orgSgId");
		String sgName = request.getParameter("sgName");
		String sgDescription = request.getParameter("sgDescription");
		
		SecurityGroupsBean sgBean = new SecurityGroupsBean();
		sgBean.setName(sgName);
		sgBean.setDescription(sgDescription);
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			SecurityGroupsBean securityGroupBean = securityGroupsService.createSercurityGroups(sgBean, tenantId, tokenId);
			String curSgId = securityGroupBean.getId();
			request.setAttribute("actionLog", "创建了一个名称为"+sgName+"的安全组");
			SecurityGroupsBean securityGroup = securityGroupsService.getSercurityGroupsBeanById(orgSgId, tenantId, tokenId);
			List<SecurityGroupsRuleBean> sgRuleList = securityGroup.getRuleList();
			for (int i=0; i<sgRuleList.size(); i++) {
				SecurityGroupsRuleBean sgRule = sgRuleList.get(i);
				if (sgRule.getProtocol() != null || sgRule.getRemoteStr() != "") {
					sgRule.setSecurityGroupsId(curSgId);
					securityGroupsService.createSercurityGroupRule(sgRule, tenantId, tokenId);
				}
			}
			response.getWriter().print("<script>window.location.href='getAllSecurityGroups?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getAllSecurityGroups?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
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
	  * @Title: deleteSecurityGroups
	  * @Description: 删除安全组
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:16:43 PM
	 *//*
	@RequestMapping("/deleteSecurityGroups")
	public String deleteSecurityGroups(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
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
		String securityGroupsID=request.getParameter("SecurityGroupsID");
		String[] sgId = securityGroupsID.split(",");
		
		int count = 0;
		try {
			for (int i = 0; i< sgId.length; i++) {
				String flag = securityGroupsService.deteleSecurityGroupsById(sgId[i],tenantId,tokenId);
				if(flag != null){
					count++;
					break ;
				}else{
					request.setAttribute("actionLog", "删除了一个ID为"+sgId[i]+"的安全组");
				}
			}
			if(count==0){
				response.getWriter().print("<script>window.location.href='getAllSecurityGroups?promptBoxFlag=1';</script>");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllSecurityGroups?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: goCreateSecurityGroupsRule
	  * @Description: 到创建安全组规则页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:17:08 PM
	 *//*
	@RequestMapping("/goCreateSecurityGroupsRule")
	public String goCreateSecurityGroupsRule(HttpServletRequest request,HttpServletResponse response) {
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String SecurityGroupsID=request.getParameter("SecurityGroupsID");
		String type=request.getParameter("type");//egress、ingress
		request.setAttribute("type", type);
		request.setAttribute("SecurityGroupsID", SecurityGroupsID);
		try {
			request.setAttribute("sercurityGroupsList",securityGroupsService.getSercurityGroupsList(tenantId,tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_securityGroups_rule_create";
	}
	
	*//**
	 * 
	  * @Title: createSecurityGroupRule
	  * @Description: 创建安全组规则
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:17:31 PM
	 *//*
	@RequestMapping("/createSecurityGroupRule")
	public String createSecurityGroupRule(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		//安全组规则 字段
		String sgId = request.getParameter("sgId");	
		String remoteGroupId = request.getParameter("remoteGroupId");
		String remoteIpPrefix = request.getParameter("remoteIpPrefix");
		String direction = request.getParameter("direction");	//egress、ingress	
		String protocol = request.getParameter("protocol");	
		String ethertype = request.getParameter("ethertype");
		String port = request.getParameter("port");
		String portRangeMax = request.getParameter("portRangeMax");
		String portRangeMin = request.getParameter("portRangeMin");
		
		SecurityGroupsRuleBean sgBean = new SecurityGroupsRuleBean();
		sgBean.setSecurityGroupsId(sgId);//安全组id
		sgBean.setDirection(direction);
		sgBean.setProtocol(protocol);
		sgBean.setEthertype(ethertype);
		sgBean.setRemoteGroupId(remoteGroupId);
		sgBean.setRemoteIpPrefix(remoteIpPrefix);
		
		if (port!=null && !port.equals("")) {
			sgBean.setPortRangeMax(port);
			sgBean.setPortRangeMin(port);
		} else {
			sgBean.setPortRangeMax(portRangeMax);
			sgBean.setPortRangeMin(portRangeMin);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			SecurityGroupsRuleBean securityGroupRuleBean = securityGroupsService.createSercurityGroupRule(sgBean,tenantId,tokenId);
			request.setAttribute("actionLog", "创建了一个"+direction+"的安全组规则");
			response.getWriter().print("<script>window.location.href='getSecurityGroupsDetails?securityGroupsId="+sgId+"&type="+direction+"&promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getSecurityGroupsDetails?securityGroupsId="+sgId+"&type="+direction+"&promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: toCopySecurityGroupsRule
	  * @Description: 到复制安全组规则页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:17:53 PM
	 *//*
	@RequestMapping("/toCopySecurityGroupsRule")
	public String toCopySecurityGroupsRule(HttpServletRequest request,HttpServletResponse response) {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String securityGroupId = request.getParameter("securityGroupId");
		String sgRuleId = request.getParameter("sgRuleId");
		String type = request.getParameter("type");//egress、ingress
		request.setAttribute("securityGroupId", securityGroupId);
		request.setAttribute("sgRuleId", sgRuleId);
		request.setAttribute("type", type);
		try {
			request.setAttribute("sgList", securityGroupsService.getSercurityGroupsList(tenantId, tokenId));
			request.setAttribute("sgRule", securityGroupsService.getSGRuleBeanByRuleId(sgRuleId, tenantId, tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_securityGroups_rule_copy";
	}
	
	*//**
	 * 
	  * @Title: deleteSecurityGroupRuleById
	  * @Description: 删除安全组规则
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:18:07 PM
	 *//*
	@RequestMapping("/deleteSecurityGroupRuleById")
	public String deleteSecurityGroupRuleById(HttpServletRequest request,HttpServletResponse response) throws Exception {
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
		
//		String type = request.getParameter("type");//egress、ingress
//		String SecurityGroupID = request.getParameter("SecurityGroupID");
		String SecurityGroupRuleID = request.getParameter("SecurityGroupRuleID");
		String[] sgId = SecurityGroupRuleID.split(",");
		
		JSONObject object = new JSONObject();
		int count = 0;
		try {
			for (int i = 0; i< sgId.length; i++) {
				String flag = securityGroupsService.deteleSecurityGroupRuleById(sgId[i],tenantId,tokenId);
				if(flag != null){
					count++;
					break ;
				}else{
					request.setAttribute("actionLog", "删除了一个ID为"+sgId[i]+"的安全组规则");
				}
			}
			if(count==0){
				object.put("data", true);
			} else {
				object.put("data", false);
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			object.put("error", e.getMessage());
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}
	
	*//**
	 * 
	  * @Title: portRangeRepeatCheck
	  * @Description: 创建安全组规则时，检查端口是否重复
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:18:32 PM
	 *//*
	@RequestMapping("/portRangeRepeatCheck")
	public String portRangeRepeatCheck(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String type = request.getParameter("type");//egress、ingress
		String securityGroupID = request.getParameter("securityGroupID");
		String portRangeStr = request.getParameter("portRangeStr");//端口
		String protocol = request.getParameter("protocol");//协议
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		JSONObject object = new JSONObject();
		try {
			if(securityGroupID!=null){
				SecurityGroupsBean sgBean = securityGroupsService.getSercurityGroupsBeanById(securityGroupID,tenantId,tokenId);
				List<SecurityGroupsRuleBean> ruleList = sgBean.getRuleList();
				boolean flg = true;
				if(ruleList.size()>0){
					for(int i=0;i<ruleList.size();i++){
						if(ruleList.get(i).getDirection()!=null && ruleList.get(i).getDirection().equals(type) && 
								ruleList.get(i).getPortRangeStr().equals(portRangeStr) && ruleList.get(i).getProtocol().equals(protocol)){
							flg = false;
							break;
						}
					}
				}
				if (flg) {
					object.put("success", true);
				} else {
					object.put("success", false);
				}
			} else {
				object.put("success", false);
				throw new NullPointerException();
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		} 
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		
		return null;
	}*/	
}