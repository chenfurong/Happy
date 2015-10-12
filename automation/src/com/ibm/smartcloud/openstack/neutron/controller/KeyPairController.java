/*package com.ibm.smartcloud.openstack.neutron.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.exception.BaseException;
import com.ibm.smartcloud.core.exception.ErrorMessageException;
import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;
import com.ibm.smartcloud.core.exception.handler.PopUpMessageAndReturnException;
import com.ibm.smartcloud.core.exception.handler.PopUpMessageException;
import com.ibm.smartcloud.core.exception.handler.ReturnToLastPageException;
import com.ibm.smartcloud.core.util.OpenStackUtil;
import com.ibm.smartcloud.openstack.keystone.service.KeyPairService;

*//** 
 * 类描述: 密钥对信息模块管理   
 * 类名称：KeyPairController     
 * 创建人：梁瑞   
 * 创建时间：2014年8月13日 下午2:08:18     
 * @version 1.0
 *//*
@Controller
public class KeyPairController extends BaseController {
	@Autowired
	private KeyPairService keyPairService;
	
	*//**
	  * @Title: getAllKeyPairsInfo
	  * @Description: Get All  KeyPairs
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:33:41
	 *//*
	@RequestMapping("/getAllKeyPairsInfo")
	public String getAllKeyPairsInfo(HttpServletRequest request){
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		// 查询所有密钥对
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		request.setAttribute("errorMessage", request.getParameter("errorMessage"));
		try {
			request.setAttribute("keypairList",keyPairService.getKeypairsList(tenantId, tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_key_pair_list";
	}
	
	*//**
	  * @Title: addKeyPairsInfo
	  * @Description: Create  KeyPairs
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:33:49
	 *//*
	@RequestMapping("/addKeyPairsInfo")
	public String addKeyPairsInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		// 创建密钥对
		KeypairsBean keypairsBean = new KeypairsBean();
		String keyName = request.getParameter("keyName");
		String publicKey = request.getParameter("publicKey");
		if(keyName != null){
			keypairsBean.setKeyName(keyName);
		}
		if(publicKey != null){
			keypairsBean.setPublicKey(publicKey);
		}
		if(publicKey != null){
			try {
				keyPairService.createKeypairs(tenantId, tokenId, keypairsBean);
				request.setAttribute("actionLog", "创建了一个名称为"+keyName+"的密钥对");
				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print("<script>window.location.href='getAllKeyPairsInfo?promptBoxFlag=1';</script>");
			} catch (ErrorMessageException e) {
				response.getWriter().print("<script>window.location.href='getAllKeyPairsInfo?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
			} catch (Exception e) {
				e.printStackTrace();
				throw new PopUpMessageAndReturnException(e.getMessage());
			}
		}else{
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			try {
				KeypairsBean serInfo = keyPairService.createKeypairs(tenantId, tokenId, keypairsBean);
				//文件写入.
				String path=request.getSession().getServletContext().getRealPath("/");
				String realpath=java.net.URLDecoder.decode(path,"utf-8");
				File file = new File(realpath+"templet\\keypair.pem");
			    if(!file.exists()) {
				  file.createNewFile();
				}
			    FileWriter fw = new FileWriter(file.getAbsoluteFile());
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(serInfo.getPrivateKey());
			    bw.close();
		        // 下载本地文件
		        try {
					String fileName = ""+keyName+".pem"; // 文件的默认保存名
					OutputStream output = response.getOutputStream();
					response.setCharacterEncoding("utf-8");  
					response.setContentType("application/octet-stream");
					response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
					response.setHeader("Content-Length", String.valueOf(file.length()));  
					FileInputStream fis = new FileInputStream(file);
					byte[] b = new byte[1024];
					int i = 0;
					while((i=fis.read(b))>0)
					output.write(b,0,i);
					output.flush();
					fis.close();
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("actionLog", "创建了一个名称为"+keyName+"的密钥对");
			} catch (LoginTimeOutException e) {
				return this.setExceptionTologin(e, request);
			} catch (BaseException e) {
				e.printStackTrace();
				throw new ReturnToLastPageException(e.getMessage());
			}
		}
		return null;
	}
	
	*//**
	  * @Title: deleteKeypairsById
	  * @Description: delete  KeyPairs
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author LiangRui
	  * @throws
	  * @Time 2015年3月25日 下午5:33:58
	 *//*
	@RequestMapping("/deleteKeypairsById")
	public String deleteKeypairsById(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		// 删除密钥对
		String keyId = request.getParameter("keyId");
		String[] instId = keyId.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			for (int i = 0; i < instId.length; i++) {
				keyPairService.deleteKeypairsById(tenantId, tokenId, instId[i]);
				request.setAttribute("actionLog", "删除了一个ID为"+instId[i]+"的密钥对");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllKeyPairsInfo?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		//返回页面
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("ok");
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
}*/