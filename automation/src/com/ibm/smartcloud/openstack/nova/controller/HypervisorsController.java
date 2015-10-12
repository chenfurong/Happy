package com.ibm.smartcloud.openstack.nova.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.openstack.glance.constants.OPSTHypervisorTypeConst;

/**
 * @Title：HypervisorsController 
 * @Description: 获取 Hypervisors 到首页
 * @Auth: LiangRui
 * @CreateTime:2015年3月25日 下午5:16:35     
 * @version V1.0
 */
@Controller
public class HypervisorsController {
	/**
	 * 
	  * @Title: hypervisors
	  * @Description:获取 Hypervisors 到首页 
	  * @param @param request
	  * @param @param session
	  * @param @return
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年4月7日 上午10:39:56
	 */
	@RequestMapping("/main_ec2")
	public String hypervisors(HttpServletRequest request,HttpSession session) {
		String hypervisorTypeHostName = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE_HOST_NAME);
		String hypervisorType = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE_HOST_NAME, hypervisorTypeHostName);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE, hypervisorType);
		return "home/ec2/main_ec2";
	}
}