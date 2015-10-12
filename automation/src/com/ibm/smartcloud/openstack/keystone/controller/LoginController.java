package com.ibm.smartcloud.openstack.keystone.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.util.SpringHelper;
import com.ibm.smartcloud.openstack.core.exception.OPSTBaseException;
import com.ibm.smartcloud.openstack.glance.service.ImageService;
import com.ibm.smartcloud.openstack.keystone.bean.AccessBean;
import com.ibm.smartcloud.openstack.keystone.service.IdentityService;
import com.ibm.smartcloud.openstack.nova.bean.ServerBean;
import com.ibm.smartcloud.openstack.nova.service.FlavorsService;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;

/**
 * @Title:LoginController     
 * @Description:用户登录  
 * @Auth:LiangRui   
 * @CreateTime:2015年6月12日 下午2:48:47       
 * @version V1.0
 */
@Controller
public class LoginController extends BaseController{
	public static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private  IdentityService identityService;
	@Autowired
	private InstanceService instanceService;
	@Autowired
	private FlavorsService flavorsService;
	@Autowired
	private ImageService imageService;
	
	/**
	 * @Title: loginAction
	 * @Description: 用户登录
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @author LiangRui
	 * @throws OPSTBaseException 
	 * @throws
	 * @Time 2015年6月12日 下午2:48:10
	 */
	@RequestMapping("loginAction")
	public String loginAction(HttpServletRequest request, HttpServletResponse response) throws OPSTBaseException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if(SpringHelper.getRequest().getSession()!=null
		   &&SpringHelper.getRequest().getSession().getAttribute("tokenId")!=null
		   &&SpringHelper.getRequest().getSession().getAttribute("tenantId")!=null){
			List<ServerBean> ls = new ArrayList<ServerBean>();
			ls = instanceService.getServerListV2(SpringHelper.getRequest().getSession().getAttribute("tenantId").toString(), SpringHelper.getRequest().getSession().getAttribute("tokenId").toString());
			Collections.sort(ls);
			request.setAttribute("servers",ls);
			request.setAttribute("flavors", flavorsService.getAllFlavorsList(SpringHelper.getRequest().getSession().getAttribute("tenantId").toString(), SpringHelper.getRequest().getSession().getAttribute("tokenId").toString()));
			request.setAttribute("imageList", imageService.getAllImageList(SpringHelper.getRequest().getSession().getAttribute("tenantId").toString(), SpringHelper.getRequest().getSession().getAttribute("tokenId").toString()));
			request.setAttribute("total", ls.size());
			return "instance_aix_list";
		}else{
			AccessBean accessBean;
			try {
				accessBean = identityService.createAuthTokensV3(userName, password);
				if(accessBean != null){
					request.getSession().setAttribute("tenantId",accessBean.getTenantId());	
					request.getSession().setAttribute("accessBean",accessBean);	
					request.getSession().setAttribute("tokenId",accessBean.getTokenId());
					List<ServerBean> ls = new ArrayList<ServerBean>();
					ls = instanceService.getServerListV2(accessBean.getTenantId(),accessBean.getTokenId());
					Collections.sort(ls);
					request.setAttribute("servers",ls);
					request.setAttribute("flavors", flavorsService.getAllFlavorsList(accessBean.getTenantId(),accessBean.getTokenId()));
					request.setAttribute("imageList", imageService.getAllImageList(accessBean.getTenantId(),accessBean.getTokenId()));
					request.setAttribute("total", ls.size());
					return "instance_aix_list";
				}else{
					request.setAttribute("errorMessageFlag", "fail");
					return "login";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
	
	/**
	  * @Title: logout
	  * @Description: 退出
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:39:22
	 */
	@RequestMapping("/loginOut")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
        }
		return "redirect:/login.jsp";
	}
	
	/**
	 * @Title: gologin
	 * @Description: 跳转到登录页面
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @param @throws Exception
	 * @retrun String
	 * @author LiangRui
	 * @throws
	 * @Time 2015年6月15日 下午3:04:33
	 */
	@RequestMapping("/gologin")
	public String gologin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("<script>alert('登录超时,请重新登录!')</script>");
		response.getWriter().print("<script>parent.document.location='login.jsp'</script>");
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	
	/**
	 * 
	  * @Title: loginOpenstack
	  * @Description: 登陆 openstack 
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:38:07
	 */
	/*@RequestMapping("/login")
	public String loginOpenstack(Model model, HttpServletRequest request, HttpServletResponse response) {
		//每次登陆先清空Session
		//SpringHelper.getRequest().getSession().invalidate();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String platformval = request.getParameter("platformval");//选择跳转平台//如果session还未过期则不会倒退到login页面
		if(SpringHelper.getRequest().getSession()!=null
				&&SpringHelper.getRequest().getSession().getAttribute("tokenId")!=null
				&&SpringHelper.getRequest().getSession().getAttribute("tenantId")!=null
				&&SpringHelper.getRequest().getSession().getAttribute("uName")!=null
				&&SpringHelper.getRequest().getSession().getAttribute("uName").equals(userName)){
				if (platformval!=null&&platformval.equals("platform1")) {//IT标准服务平台
					return "redirect:/instance_aix.jsp";
				}
		}
		try {
			//是否是用户admin权限
			Boolean isAdminRole = false;
			List<ProjectBean> projectList = null;
			AccessBean bean = null;
			//认证 (根据用户名查询用户信息必须有tokenId,v3的认证返回无tokenId,而v2的认证必须要有tenantId,
			//暂时只想到先调用v3的认证API获取到projectId再调用v2的认证API)
			//先根据用户信息通过密码的认证方式获取项目id   v3
			AccessBean beanv3 = identityService.createAuthTokens(userName, password);
			if(beanv3!=null){
				//根据用户信息和项目id获取tokenId v2 
				bean = identityService.createAuthenticates(userName, password, beanv3.getTenantId());
				if(bean!=null&&bean.getTokenId()!=null&&bean.getUserId()!=null
						&&bean.getTenantId()!=null&&!bean.getTenantId().equals("")){
					request.getSession().setAttribute("tokenId",bean.getTokenId());						
					//登陆成功后  查询用户可查看项目的有哪些 
					projectList = identityService.getProjectListByUserId(bean.getUserId(),bean.getTokenId());	
					
				}else if(bean!=null&&bean.getTenantId().equals("")&&bean.getRoleNames().size()==0){//未分配用户角色  重新认证
					request.getSession().setAttribute("tokenId",bean.getTokenId());	
					//登陆成功后  查询用户可查看项目的有哪些 
					projectList = identityService.getProjectListByUserId(bean.getUserId(),bean.getTokenId());	
					if(projectList.size()>0){
						for(ProjectBean pb : projectList){
							if(pb.isEnabled()){
								bean = identityService.createAuthenticates(userName, password, pb.getId());
								if(bean!=null&&bean.getTokenId()!=null&&bean.getUserId()!=null
										&&bean.getTenantId()!=null&&!bean.getTenantId().equals("")){
									request.getSession().removeAttribute("tokenId");
									request.getSession().setAttribute("tokenId",bean.getTokenId());	
									break;
								}
							}
						}
					}else{
						model.addAttribute("errorMessageFlag", "fail");
						model.addAttribute("userName", userName);
						model.addAttribute("password", "");
						return "login";
					}
				}else{
					model.addAttribute("errorMessageFlag", "fail");
					model.addAttribute("userName", userName);
					model.addAttribute("password", "");
					return "login";
				}
				//用户角色
				for(int i=0;i<bean.getRoleNames().size();i++){
					if(bean.getRoleNames().get(i).equals("admin")){
						isAdminRole = true;
						break;
					}
				}
				List<ProjectBean> projects = new ArrayList<ProjectBean>();
				for(ProjectBean pb : projectList){
					if(pb.isEnabled()){
						projects.add(pb);
					}
				}
				request.getSession().setAttribute("isAdminRole",isAdminRole);
				request.getSession().setAttribute("sessionLoginInfo",bean);								
				request.getSession().setAttribute("tenantId",bean.getTenantId());	
				request.getSession().setAttribute("uName",userName);
				request.getSession().setAttribute("upwd",password);
				request.getSession().setAttribute("platformval",platformval);					
				request.getSession().setAttribute("sessionProjectList",projects);
				
				tokenIdMap.put(bean.getTenantId(), bean.getTokenId());
				request.getSession().setAttribute("tokenIdMap",tokenIdMap);
				
				Map<String,String> map = new HashMap<String, String>();
				request.getSession().setAttribute("sessionProgressMap",map);
			}else{
				model.addAttribute("errorMessageFlag", "fail");
				model.addAttribute("userName", userName);
				model.addAttribute("password", "");
				return "login";
			}				
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			logger.error("创建createAuthenticates信息时发生"+e.getMessage()+"异常");
			model.addAttribute("errorMessageFlag", "fail");
			model.addAttribute("userName", userName);
			model.addAttribute("password", "");
			return "login";
		}
		if (platformval!=null&&platformval.equals("platform1")) {//IT标准服务平台
			//return "redirect:/main.jsp";
			return "redirect:/home/ec2/main_ec2.jsp";
		}else {//自主服务平台
			return "redirect:/standardMain.jsp";
		}
	}*/
	
	/**
	 * 
	  * @Title: toLogin
	  * @Description: 重定向 跳转到登录页面
	  * @param @param request
	  * @param @param response
	  * @param @return 
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:38:19
	 *//*
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request,HttpServletResponse response){
		return "redirect:/login.jsp";
	}
	
	*//**
	 * 
	  * @Title: changeMainPage
	  * @Description: 切换窗口:IT标准服务平台/自主服务平台
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:39:01
	 *//*
	@RequestMapping("/changeMainPage")
	public String changeMainPage(HttpServletRequest request,HttpServletResponse response){
		String platformval = request.getParameter("platformval");//选择跳转平台
		if(SpringHelper.getRequest().getSession()==null
				&&SpringHelper.getRequest().getSession().getAttribute("tokenId")==null
				&&SpringHelper.getRequest().getSession().getAttribute("tenantId")==null
				&&SpringHelper.getRequest().getSession().getAttribute("uName")==null){
			return "redirect:/gologin";	
		}
		if (platformval.equals("platform1")) {//IT标准服务平台
			request.getSession().removeAttribute("platformval");
			request.getSession().setAttribute("platformval",platformval);
			return "redirect:/home/ec2/main_ec2.jsp";
		}else {//自主服务平台
			request.getSession().removeAttribute("platformval");
			request.getSession().setAttribute("platformval",platformval);
			return "redirect:/standardMain.jsp";
		}
	}
	*//**
	 * 
	  * @Title: logout
	  * @Description: 退出
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:39:22
	 *//*
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
        if(session!=null){
            session.invalidate();
        }
		return "redirect:/login.jsp";
	}
	
	*//**
	 * 
	  * @Title: goTenantById
	  * @Description: 用户的租户切换
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:39:36
	 *//*
	@RequestMapping("/goTenantById")
	public String goTenantById(HttpServletRequest request,HttpServletResponse response) {
		String tenantId = request.getParameter("tenantId"); 
		if(SpringHelper.getRequest().getSession()!=null){
			String userName = (String) request.getSession().getAttribute("uName");
			String password = (String) request.getSession().getAttribute("upwd");
			String platformval = (String) request.getSession().getAttribute("platformval");
			try {
				AccessBean bean = identityService.createAuthenticates(userName, password,tenantId);	
				Boolean isAdminRole = false;
				List<ProjectBean> projectList = null;
				if(bean!=null&&bean.getTokenId()!=null&&bean.getUserId()!=null
						&&bean.getTenantId()!=null&&!bean.getTenantId().equals("")){
					request.getSession().setAttribute("tokenId",bean.getTokenId());
					//登陆成功后  查询用户可查看项目的有哪些  分别的角色是什么
					projectList = identityService.getProjectListByUserId(bean.getUserId(),bean.getTokenId());
				}else if(bean!=null&&bean.getTenantId().equals("")&&bean.getRoleNames()==null){//未分配用户角色  重新认证
					request.getSession().setAttribute("tokenId",bean.getTokenId());	
					//登陆成功后  查询用户可查看项目的有哪些  分别的角色是什么
					projectList = identityService.getProjectListByUserId(bean.getUserId(),bean.getTokenId());	
					if(projectList.size()>0){
						for(ProjectBean pb : projectList){
							if(pb.isEnabled()){
								bean = identityService.createAuthenticates(userName, password,pb.getId());
								if(bean!=null&&bean.getTokenId()!=null&&bean.getUserId()!=null
										&&bean.getTenantId()!=null&&!bean.getTenantId().equals("")){
									request.getSession().removeAttribute("tokenId");
									request.getSession().setAttribute("tokenId",bean.getTokenId());	
									break;
								}
							}
						}
					}
				}
				//用户角色
				if(bean.getRoleNames()!=null){	
					for(int i=0;i<bean.getRoleNames().size();i++){
						if(bean.getRoleNames().get(i).equals("admin")){
							isAdminRole = true;
							break;
						}
					}
				}
				request.getSession().setAttribute("isAdminRole", isAdminRole);
				request.getSession().setAttribute("sessionLoginInfo", bean);								
				request.getSession().setAttribute("tenantId", bean.getTenantId());	
				request.getSession().setAttribute("uName", userName);
				request.getSession().setAttribute("upwd", password);
				request.getSession().setAttribute("platformval", platformval);		
			} catch (LoginTimeOutException e) {
				return this.setExceptionTologin(e, request);
			} catch (BaseException e) {
				logger.error("创建createAuthenticates信息时发生"+e.getMessage()+"异常");
			}
			if (platformval!=null&&platformval.equals("platform1")) {//IT标准服务平台
				return "redirect:/home/ec2/main_ec2.jsp";
			}else {//自主服务平台
				return "redirect:/standardMain.jsp";
			}
		}else{
			return "redirect:/gologin";	
		}
	}
	
	*//**
	 * 
	  * @Title: loginSystem
	  * @Description: 登录系统
	  * @param @param model
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:39:46
	 *//*
	@RequestMapping("/loginSystem")
	public String loginSystem(Model model, HttpServletRequest request, HttpServletResponse response){
		
		String intranetId = request.getParameter("intranetId");
		String password = request.getParameter("password");
		model.addAttribute("intranetId", intranetId);
		model.addAttribute("password", "");
		
		try {
			int status = BluepagesUtil.authenicte(intranetId, password);
			if(status != 0){
				model.addAttribute("errorMessageFlag", "fail");
				return "loginSystem";
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			throw new PopUpMessageException(e.getMessage());
		}
		
		return "index";
	}
	*//**
	 * 
	  * @Title: toWelcomePage
	  * @Description: 跳转到首页静态页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:39:59
	 *//*
	@RequestMapping("/toWelcomePage")
	public String toWelcomePage(HttpServletRequest request, HttpServletResponse response){
		return "home/ec2/ec2_welcome";
	}
	*//**
	 * 
	  * @Title: toIndexPage
	  * @Description: 跳转到首页 并判断session是否过期
	  * @param @param request
	  * @param @param response
	  * @param @return 
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:38:19
	 *//*
	@RequestMapping("/toIndexPage")
	public String toIndexPage(HttpServletRequest request, HttpServletResponse response){
		
		try {
			if(SpringHelper.getRequest().getSession()!=null
					&&SpringHelper.getRequest().getSession().getAttribute("tokenId")!=null
					&&SpringHelper.getRequest().getSession().getAttribute("tenantId")!=null
					&&SpringHelper.getRequest().getSession().getAttribute("uName")!=null){
				return "home/ec2/ec2_index";
			}else{
				
				throw new LoginTimeOutException("登陆超时");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			return "redirect:/login.jsp";
		}
		
	}*/
}