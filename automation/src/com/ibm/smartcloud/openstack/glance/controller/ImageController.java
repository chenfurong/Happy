package com.ibm.smartcloud.openstack.glance.controller;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.exception.BaseException;
import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;
import com.ibm.smartcloud.core.exception.handler.ReturnToLastPageException;
import com.ibm.smartcloud.core.util.OpenStackUtil;
import com.ibm.smartcloud.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.glance.bean.ImagesBean;
import com.ibm.smartcloud.openstack.glance.service.ImageService;

/**
 * @Title:ImageController     
 * @Description: 镜像模块
 * @Auth:LiangRui   
 * @CreateTime:2015年6月15日 下午3:15:16       
 * @version V1.0
 */
@Controller
public class ImageController extends BaseController {
	@Autowired
	private ImageService imageService;
	
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
    
    /**
      * @Title: getAllImage
      * @Description: 获取所有镜像
      * @param @param request
      * @param @return
      * @return String
      * @author ZhangLong
      * @throws
      * @Time Mar 25, 2015 6:20:09 PM
     */
	@RequestMapping("/getAllImage")
	public String getAllImage(HttpServletRequest request){
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}
		List<ImagesBean> imageList;
		try{
			imageList = imageService.getAllImageList(tenantId,tokenId);
		}catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}catch(BaseException e){
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		request.setAttribute("imageList", imageList);
		return "home/ec2/ec2_images_list";
	}

	/**
	 * 
	  * @Title: getImageDetails
	  * @Description: 根据ID获取镜像详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:20:35 PM
	 *//*
	@RequestMapping("/getImgDetails")
	public String getImageDetails(HttpServletRequest request,HttpServletResponse response){
		
		response.setContentType("text/html;charset=utf-8");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String hypervisor_type = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		request.setAttribute(OPSTHypervisorTypeConst.HYPERVISOR_TYPE,hypervisor_type);
		String imageId = request.getParameter("imageId");
		try{
			ImageBean imageBean = imageService.getImageDetails(imageId,tenantId,tokenId);
			 暂时隐藏所有者的信息  2014.9.22 
			if(imageBean.getOwner()!= null && !"".equals(imageBean.getOwner())){
				ProjectBean projectBean = null;
				try{
					projectBean = projectService.getProjectById(imageBean.getOwner());
				}catch (LoginTimeOutException e) {
					return this.setExceptionTologin(e, request);
				}catch(OpenStackOpException e){
					logger.error("获取镜像所有者信息时发生"+e.getMessage()+"异常");
				}
				imageBean.setProjectBean(projectBean);
			}
			request.setAttribute("imageBean", imageBean);
		}catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}catch(BaseException e){
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		return "home/ec2/ec2_images_detail";
	}
	
	*//**
	 * 
	  * @Title: getImageDetailsJson
	  * @Description: 根据ID获取镜像详细信息(返回json)
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:21:15 PM
	 *//*
	@RequestMapping("/getImageDetailsJson")
	public String getImageDetailsJson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
		String imageId = request.getParameter("imageId");
		ImageBean imageBean = null;
		try {
			imageBean = imageService.getImageDetails(imageId,tenantId,tokenId);
		}catch(BaseException e){
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		if (imageBean != null) {
			jsonObject.put("success", true);
			jsonObject.put("imageId", imageBean.getId());
			jsonObject.put("imageStatus", imageBean.getStatus());	
			if(imageBean.getStatus()!=null&&!imageBean.getStatus().equals(OPSTTaskstateConst.image_state_active)){
				jsonObject.put("taskStateProgress", imageBean.getTaskStateProgress());	
			}						
		}else{
			jsonObject.put("success", true);
		}
		response.getWriter().print(jsonObject);	
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: getImageCreateData
	  * @Description: 去创建镜像页面
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:21:54 PM
	 *//*
	@RequestMapping("getImageCreateData")
	public String getImageCreateData(HttpServletRequest request) {
		Map<String, String> mapType = FindHypervisorTypeUtil.getHypervisorType();
		request.setAttribute("mapHType", mapType);
		List<String> imageOsTypeList = FindHypervisorTypeUtil.getImageOsType();
		request.setAttribute("imageOsTypeList", imageOsTypeList);
		return "home/ec2/ec2_images_create";
	}

	*//**
	 * 
	  * @Title: createImage
	  * @Description: 创建镜像
	  * @param @param file
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:22:18 PM
	 *//*
	@RequestMapping("/createImage")
	public String createImage(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String hypervisor_type = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);

		String imgName = request.getParameter("imgName");
		String imgDescribe = request.getParameter("imgDescribe");
		String imgFormat = request.getParameter("imgFormat");// 格式化 类型
		String minImgDisk = request.getParameter("minImgDisk");
		String minImgMemory = request.getParameter("minImgMemory");
		String imgPublic = request.getParameter("imgPublic");
		String imgProtected = request.getParameter("imgProtected");
		String imgSource = request.getParameter("imgSource");// 镜像源
		String imgLocation = request.getParameter("imgLocation");// 镜像地址
		
		String os_type = request.getParameter("os_type");

		File localFile = null;
		if (imgSource.equals("t2")) {// t1 镜像地址
			// 判断镜像地址是否为空
			if (!imgLocation.trim().equals("")) {

			}
		} else if (imgSource.equals("t1")) {// t2 镜像文件
			// 判断文件是否为空
			if (!file.isEmpty()) {
				// 文件保存路径
				String filePath = request.getSession().getServletContext().getRealPath("/");
				// 2. 生成文件夹
				File dirFile = new File(filePath, "upload");
				// 判断文件夹是否存在
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				String uploadFileFileName = file.getOriginalFilename();
				// 转存文件
				localFile = new File(dirFile, uploadFileFileName);
				file.transferTo(localFile);
			}
		} else {
			// response.getWriter().print("<script>alert('上传镜像文件失败，请重新创建镜像!')</script>");
			// response.getWriter().print("<script>window.location.href='getImageCreateData';</script>");
			response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=-1&hypervisor_type="+ hypervisor_type + "';</script>");
			response.getWriter().flush();
			response.getWriter().close();
		}

		ImageBean image = new ImageBean();
		if ("imgProtected".equals(imgProtected)) {
			image.setImgProtected(true);
		} else {
			image.setImgProtected(false);
		}
		if ("imgPublic".equals(imgPublic)) {
			image.setIs_public(true);
		} else {
			image.setIs_public(false);
		}
		image.setName(imgName);
		// image.setFile(dirFile);
		image.setFile(localFile);
		image.setDisk_format(imgFormat);
		image.setDescription(imgDescribe);
		image.setLocation(imgLocation);
		image.setSource(imgSource);
		image.setMin_disk(minImgDisk);
		image.setMin_ram(minImgMemory);
		
		image.setHypervisor_type(hypervisor_type);
		image.setOs_type(os_type);	
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			imageService.createImage(image,tenantId,tokenId);
			request.setAttribute("actionLog", "创建了一个名称为"+imgName+"的镜像");
			response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=1&hypervisor_type="+hypervisor_type+"';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=-1&hypervisor_type="+hypervisor_type+"&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: toUpdateImage
	  * @Description: 去更新镜像页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:22:38 PM
	 *//*
	@RequestMapping("/toUpdateImage")
	public String toUpdateImage(HttpServletRequest request, HttpServletResponse response) {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String imageId = request.getParameter("imageId");
		try {
			ImageBean imageBean = imageService.getImageDetails(imageId,tenantId,tokenId);
			request.setAttribute("imageBean", imageBean);
			Map<String, String> mapType = FindHypervisorTypeUtil.getHypervisorType();
			request.setAttribute("mapHType", mapType);
			List<String> imageOsTypeList = FindHypervisorTypeUtil.getImageOsType();
			request.setAttribute("imageOsTypeList", imageOsTypeList);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch(BaseException e){
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		return "home/ec2/ec2_images_edit";
	}

	*//**
	 * 
	  * @Title: updateImage
	  * @Description: 更新镜像
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:23:00 PM
	 *//*
	@RequestMapping("/updateImage")
	public String updateImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String updateImgId = request.getParameter("updateImgId");
		String updateImgName = request.getParameter("updateImgName");
		String updateImgDescribe = request.getParameter("updateImgDescribe");
		String updateImgPublic = request.getParameter("updateImgPublic");
		String updateImgProtected = request.getParameter("updateImgProtected");
		
		String hypervisor_type = request.getParameter("hypervisor_type");
		String os_type = request.getParameter("os_type");
		String updateImgOsName = request.getParameter("updateImgOsName");
		String image_type = request.getParameter("image_type");
		
		ImageBean image = new ImageBean();
		image.setId(updateImgId);
		image.setName(updateImgName);
		image.setDescription(updateImgDescribe.trim());
		if ("checked".equals(updateImgPublic)) {
			image.setIs_public(true);
		} else {
			image.setIs_public(false);
		}
		if ("checked".equals(updateImgProtected)) {
			image.setImgProtected(true);
		} else {
			image.setImgProtected(false);
		}
		if(hypervisor_type!=null){
			image.setHypervisor_type(hypervisor_type.toLowerCase());
		}
		image.setOs_type(os_type);	
		image.setOs_name(updateImgOsName);
		if ("snapshot".equals(image_type)) {
			image.setImage_type(image_type);
		}
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			imageService.updateImage(image,tenantId,tokenId);
			request.setAttribute("actionLog", "更新了一个名称为"+updateImgName+"的镜像");
			response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: deleteAllImage
	  * @Description: 删除镜像
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:23:20 PM
	 *//*
	@RequestMapping("deleteAllImage")
	public String deleteAllImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
//		String hypervisor_type = request.getParameter(OPSTHypervisorTypeConst.HYPERVISOR_TYPE);
		String imageIds = request.getParameter("imageIds");
		String[] imgIds = imageIds.split(",");
		
		List<ServerDetailBean> serviceList = null;
		int count = 0;
		try {
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			serviceList = instanceService.instanceServerList(tenantId,tokenId,isAdminRole);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		try {
			String imgComStr = "" ;
			boolean d ;
			//将实例中包含的镜像
			for (int j = 0; j < serviceList.size(); j++) {
				String imgId = serviceList.get(j).getImgId();
				imgComStr += imgId;
			}
			for (int i = 0; i < imgIds.length; i++) {
//				如果d为TRUE，说明该镜像被关联了
				d = imgComStr.contains(imgIds[i]);
				if (d) {
					count++;
					break;
				} else {
					imageService.deleteImage(imgIds[i], tenantId, tokenId);
					request.setAttribute("actionLog", "删除一个ID为"+imgIds[i]+"的镜像");	
				}
			}
			if (count == 0) {
				response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=1';</script>");
			} else {
				response.getWriter().print("<script>window.location.href='getAllImage?promptBoxFlag=-1&errorMessage="+errorMessage+"';</script>");
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href=\"getAllImage?promptBoxFlag=-1&errorMessage="+e.getMessage()+"\";</script>");
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
	  * @Title: getDiskFormat
	  * @Description: 初始化"格式化"下拉框中的数据
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws IOException
	  * @return ModelAndView
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:23:43 PM
	 *//*
	@RequestMapping("/getDiskFormat")
	public ModelAndView getDiskFormat(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(0, "raw");
		jsonArray.add(1, "vhd");
		jsonArray.add(2, "vmdk");
		jsonArray.add(3, "vdi");
		jsonArray.add(4, "iso");
		jsonArray.add(5, "qcow2");
		jsonArray.add(6, "aki");
		jsonArray.add(7, "ari");
		jsonArray.add(8, "ami");

		response.getWriter().print(jsonArray);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: getImageMemberList
	  * @Description: 获取所有镜像成员
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:24:56 PM
	 *//*
	@RequestMapping("/getImageMemberList")
	public String getImageMemberList(HttpServletRequest request) {
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		try {
			List<ImageMemberBean> list = imageService.getImageMemberList(tenantId,tokenId);
			request.setAttribute("imageMemberList", list);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch(BaseException e){
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}		
		return "home/ec2/ec2_images_member_list";
	}
	
	*//**
	 * 
	  * @Title: getAllImageBackup
	  * @Description: 获取所有镜像备份
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:25:14 PM
	 *//*
	@RequestMapping("/getAllImageBackup")
	public String getAllImageBackup(HttpServletRequest request){
		
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
			List<ImageBean> imageList = imageService.getImageBackupList(tenantId,tokenId);
			List<InstanceBackupBean> backupList = instanceBackupService.getInstanceBackupList();
			for(InstanceBackupBean backup:backupList){
				List<ImageBean> iList = new ArrayList<ImageBean>();
				for(ImageBean ib:imageList){
					if(backup.getInstanceId().equals(ib.getInstance_uuid())){
						iList.add(ib);
					}
				}
				backup.setImageList(iList);
				
				List<String> netIds = new ArrayList<String>();
				String[] networkIds = backup.getSelNetwork().split(";");
				if (networkIds != null) {
					for (int j=0; j < networkIds.length; j++) {
						if (!netIds.contains(networkIds[j])) {
							netIds.add(networkIds[j]);
						}
					}
				}
				backup.setNetIds(netIds);
				
			}			
			request.setAttribute("backupImageList", backupList);
			//request.setAttribute("networks", networkService.getNetworkList(tenantId,tokenId));
			//request.setAttribute("subnets", networkService.getSubnetList(tenantId,tokenId));
			//request.setAttribute("images", imageService.getImageList(tenantId,tokenId));
			request.setAttribute("flavors", instanceService.getFlavorV2List(tenantId,tokenId));
			request.setAttribute("netWork",instanceInfoDBService.getAllNetWorkInfo());
			DbContextHolder.clearDbType();
		}catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}catch(BaseException e){
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		return "home/ec2/ec2_images_backup";
	}
	
	*//**
	 * 
	  * @Title: getImagebackupDetails
	  * @Description: 根据ID获取镜像备份详细信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:25:35 PM
	 *//*
	@RequestMapping("/getImgbackupDetails")
	public String getImagebackupDetails(HttpServletRequest request,HttpServletResponse response) {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String imageId = request.getParameter("imageId");
		String serId = request.getParameter("serId");
		try{
			ImageBean imageBean = imageService.getImageDetails(imageId, tenantId, tokenId);
			InstanceBackupBean backupBean = instanceBackupService.getInstanceBackupById(serId);
			String[] networkIds = backupBean.getSelNetwork().split(";");
			List<String> netIds = new ArrayList<String>();
			if (networkIds != null) {
				for (int i=0; i < networkIds.length; i++) {
					netIds.add(networkIds[i]);
				}
			}
			request.setAttribute("imageBean", imageBean);
			request.setAttribute("backupBean", backupBean);
			request.setAttribute("netIds", netIds);
			request.setAttribute("networks", networkService.getNetworkList(tenantId,tokenId));
			request.setAttribute("subnets", networkService.getSubnetList(tenantId,tokenId));
			request.setAttribute("images", imageService.getImageList(tenantId,tokenId));
			request.setAttribute("flavors", instanceService.getFlavorV2List(tenantId,tokenId));
		}catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		}catch(BaseException e){
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		return "home/ec2/ec2_image_backup_operate";
	}
	
	*//**
	 * 
	  * @Title: deleteImageBackup
	  * @Description: 删除镜像备份
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws IOException
	  * @return String
	  * @author ZhangLong
	  * @throws
	  * @Time Mar 25, 2015 6:26:01 PM
	 *//*
	@RequestMapping("deleteImageBackup")
	public String deleteImageBackup(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String imageIds = request.getParameter("imageIds");
		String serId = request.getParameter("serId");
		String[] imgIds = imageIds.split(",");
		List<ServerDetailBean> serviceList = null;
		try {
			boolean isAdminRole = (Boolean) SpringHelper.getRequest().getSession().getAttribute("isAdminRole");
			serviceList = instanceService.instanceServerList(tenantId,tokenId,isAdminRole);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		try {
			String imgComStr = "" ;
			boolean d ;
			//将实例中包含的镜像
			for (int j = 0; j < serviceList.size(); j++) {
				String imgId = serviceList.get(j).getImgId();
				imgComStr += imgId;
			}
			for (int i = 0; i < imgIds.length; i++) {
				//如果d为TRUE，说明该镜像被关联了
				d = imgComStr.contains(imgIds[i]);
				if(!d){
					imageService.deleteImage(imgIds[i],tenantId,tokenId);
					request.setAttribute("actionLog", "删除一个ID为"+imgIds[i]+"的镜像");
					//获取所有实例镜像的备份数据
					List<ImageBean> imageList = imageService.getImageBackupList(tenantId,tokenId);
					//循环 看是否  serid是否还有镜像备份数据
					Boolean flag = true;
					for(ImageBean ib:imageList ){
						if(ib.getInstance_uuid().equals(serId)){
							flag = false;
							break;
						}
					}
					//删除镜像备份,删除数据库中保存的实例镜像备份
					if(flag){
						instanceBackupService.deleteInstanceBackup(serId);
					}
				}
			}
			response.getWriter().print("<script>window.location.href='getAllImageBackup?promptBoxFlag=1';</script>");

		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllImageBackup?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}*/
}