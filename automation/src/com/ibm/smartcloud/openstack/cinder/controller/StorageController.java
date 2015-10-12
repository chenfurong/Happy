/*package com.ibm.smartcloud.openstack.cinder.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ibm.smartcloud.core.controller.BaseController;
import com.ibm.smartcloud.core.exception.BaseException;
import com.ibm.smartcloud.core.exception.ErrorMessageException;
import com.ibm.smartcloud.core.exception.handler.LoginTimeOutException;
import com.ibm.smartcloud.core.exception.handler.PopUpMessageException;
import com.ibm.smartcloud.core.exception.handler.ReturnToLastPageException;
import com.ibm.smartcloud.core.util.ListSortUtil;
import com.ibm.smartcloud.core.util.OpenStackUtil;
import com.ibm.smartcloud.core.util.PropertyUtil;
import com.ibm.smartcloud.openstack.cinder.service.StorageService;
import com.ibm.smartcloud.openstack.core.constants.OPSTPropertyKeyConst;
import com.ibm.smartcloud.openstack.core.constants.OPSTTaskstateConst;
import com.ibm.smartcloud.openstack.glance.service.ImageService;
import com.ibm.smartcloud.openstack.nova.service.InstanceService;

*//**
 * 
 * @Title：StorageController 
 * @Description: 存储管理   
 * @Auth: liwj
 * @CreateTime:2015年3月25日 下午7:00:08     
 * @version V1.0
 *//*
@Controller
public class StorageController extends BaseController {

	@Autowired
	private StorageService storageService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private InstanceService instanceServerService;
	Properties p = PropertyUtil.getResourceFile("config/properties/cloud.properties");
	
	*//**
	 * 
	  * @Title: getAllvolume
	  * @Description: 查询所有数据盘的信息
	  * @param @param request
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:00:33
	 *//*
	@RequestMapping("/getAllvolume")
	public String getAllvolume(HttpServletRequest request){
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
		
		String vtype = request.getParameter("vtype");
		request.setAttribute("vtype", vtype);
		try {
			//数据盘类型
			List<StorageTypeBean> sTypeList = storageService.getStorageTypeList(tenantId,tokenId);
			//按名字排序
			ListSortUtil<StorageTypeBean> sortTypeList = new ListSortUtil<StorageTypeBean>();
			sortTypeList.sort(sTypeList, "name", "asc"); 
			request.setAttribute("sTypeList", sTypeList);		
			    
			List<VolumeBean> list = storageService.getVolumeList(tenantId,tokenId); 
		    if(vtype!=null){
		    	List<VolumeBean> list1 = new ArrayList<VolumeBean>();
		    	for(VolumeBean v: list){
		    		if(v.getVolume_type()!=null&&v.getVolume_type().equals(vtype)){
		    			list1.add(v);
		    		}
		    	}
		    	 request.setAttribute("volumeList", list1);
		    }else{
				ListSortUtil<VolumeBean> sortlist = new ListSortUtil<VolumeBean>();
			    sortlist.sort(list, "volume_type", "asc"); 
		    	request.setAttribute("volumeList", list);
		    }	       
			//数据盘快照
			request.setAttribute("snapshotsList", storageService.getSnapshotsList(tenantId,tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}		
		return "home/ec2/ec2_volumes_list";
	}
	 *//**
	  * 
	   * @Title: getVolumeDetails
	   * @Description: 查看数据盘详情
	   * @param @param request
	   * @param @param response
	   * @param @return
	   * @return String
	   * @author liwj
	   * @throws
	   * @Time 2015年3月25日 下午7:00:43
	  *//*
	@RequestMapping("/getVolumeDetails")
	public String getVolumeDetails(HttpServletRequest request,HttpServletResponse response){
		String volumeId = request.getParameter("volumeId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		VolumeBean volumeBean =null;
		try {
			volumeBean = storageService.getVolumeDetails(volumeId,tenantId,tokenId);
			//数据盘快照
			request.setAttribute("snapshotsList", storageService.getSnapshotsList(tenantId,tokenId));
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		if(volumeBean!=null){
			request.setAttribute("volumeBean",volumeBean);
			return "home/ec2/ec2_volumes_detail";
		}else{
			return "redirect:/getAllvolume";
		}
		
	}


	*//**
	 * 
	  * @Title: getVolumeDetailsJson
	  * @Description: ajax请求  局部刷新  数据盘list页面的查询 用于进度条的查询   
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:01:20
	 *//*
	@RequestMapping("/getVolumeDetailsJson")
	public String getVolumeDetailsJson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String volumeId = request.getParameter("volumeId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		VolumeBean volumeBean = null;
		try {
			volumeBean = storageService.getVolumeDetails(volumeId,tenantId,tokenId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = new JSONObject();
		if(volumeBean!=null){
			jsonObject.put("success", true);
			jsonObject.put("volumeId", volumeBean.getId());
			jsonObject.put("status", volumeBean.getStatus());
			if(volumeBean.getStatus().equals(OPSTTaskstateConst.volume_state_error_deleting)){ 
				
			}else if(volumeBean.getStatus().equals(OPSTTaskstateConst.volume_state_creating)||
					volumeBean.getStatus().equals(OPSTTaskstateConst.volume_state_attaching)||
					volumeBean.getStatus().equals(OPSTTaskstateConst.volume_state_detaching)||
					volumeBean.getStatus().equals(OPSTTaskstateConst.volume_state_deleting)){
				jsonObject.put("taskStateProgress", volumeBean.getTaskStateProgress());
			}else{
				jsonObject.put("operationState", true);
			}			
		}else if(volumeBean==null){//&&"deleting".equals(oldStatus)
			jsonObject.put("success", true);
			jsonObject.put("operationState", true);
		}
		response.getWriter().print(jsonObject);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	  * @Title: goCreateVolume
	  * @Description: 跳转到数据盘的创建页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:02:56
	 *//*
	@RequestMapping("/goCreateVolume")
	public String goCreateVolume(HttpServletRequest request,HttpServletResponse response){
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String currentHypervisor = request.getParameter("hypervisor_type");
		//List<SnapshotsBean> sList = null;
		//List<ImageBean> imgList = null;
		List<StorageTypeBean> sTypeList = null;
		StorageLimitsBean volumeLimits = null;
		try {
			//数据盘快照
			//sList = storageService.getSnapshotsListByStatus(tenantId,tokenId);
			//镜像
			//imgList = imageService.getImageList(tenantId,tokenId);
			//数据盘的类型
			sTypeList =  storageService.getStorageTypeList(tenantId,tokenId);
			if(currentHypervisor.toLowerCase().equals("kvm")){
				for (int i = 0; i < sTypeList.size(); i++) {
					if(p.getProperty(OPSTPropertyKeyConst.VOLUME_TYPES_KVM).contains(sTypeList.get(i).getName())){
						sTypeList.get(i).setHypervisorType(currentHypervisor);
					}
				}
			}else if(currentHypervisor.toLowerCase().equals("vmware") || currentHypervisor.toLowerCase().equals("vcenter")){
				for (int i = 0; i < sTypeList.size(); i++) {
					if(p.getProperty(OPSTPropertyKeyConst.VOLUME_TYPES_VMVARE).contains(sTypeList.get(i).getName())){
						sTypeList.get(i).setHypervisorType(currentHypervisor);
					}
				}
			}else if(currentHypervisor.toLowerCase().equals("powervm") || currentHypervisor.toLowerCase().equals("powervc")){
				for (int i = 0; i < sTypeList.size(); i++) {
					if(p.getProperty(OPSTPropertyKeyConst.VOLUME_TYPES_POWERVM).contains(sTypeList.get(i).getName())){
						sTypeList.get(i).setHypervisorType(currentHypervisor);
					}
				}
			}
			//数据盘资源占用数据显示  调用不用API 
			//request.setAttribute("volumeLimits", storageService.getVolumeLimis());//23 数据不全
			volumeLimits = storageService.getVolumeTenantQuotasV2(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (BaseException e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		//数据盘快照
		//request.setAttribute("snapshotsList", sList);
		//镜像
		//request.setAttribute("imageList", imgList);
		//数据盘的类型
		request.setAttribute("storageTypeList", sTypeList);	
		request.setAttribute("currentHypervisor", currentHypervisor);	
		//数据盘资源占用数据显示  调用不用API 
		//request.setAttribute("volumeLimits", storageService.getVolumeLimis());//23 数据不全
		request.setAttribute("volumeLimits", volumeLimits);
		request.setAttribute("instanceId", request.getParameter("instanceId"));
		return "home/ec2/ec2_volumes_detail_create";
	}
	
	*//**
	  * @Title: createVolume
	  * @Description: 创建数据盘
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:03:15
	 *//*
	@RequestMapping("/createVolume")
	public String createVolume(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String volumeName =request.getParameter("volumeName");
		String volumeType=request.getParameter("volumeType");//数据盘类型
		String volumeSize=request.getParameter("volumeSize");
		String volumeSnoapID=request.getParameter("volumeSnoapID");
		String volumeDesc =request.getParameter("volumeDesc");
		String imageRef = request.getParameter("imageRef");
		String hypervisor_type = request.getParameter("hypervisor_type");
		VolumeBean volumeBean=new VolumeBean();
		volumeBean.setName(volumeName);
		volumeBean.setVolume_type(volumeType);
		volumeBean.setSize(volumeSize);
		volumeBean.setSnapshot_id(volumeSnoapID);
		volumeBean.setDescription(volumeDesc);
		volumeBean.setImageRef(imageRef);
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
		String instanceId = request.getParameter("instanceId");
		try {
			String volumeId = storageService.createBlockStorage(volumeBean,tenantId,tokenId);
			//构建bean
			InstanceVolumeBean  volumesBean = new InstanceVolumeBean();
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date createTime = sdf.parse(sdf.format(new Date()));
			volumesBean.setId(UUID.randomUUID().toString());
			volumesBean.setServerId(instanceId);
			volumesBean.setVolumeId(volumeId);
			volumesBean.setCreateTime(createTime);
			volumesBean.setVolumStauts(0);
			instanceInfoService.addInstanceVolume(volumesBean);
			request.setAttribute("actionLog", "创建了一个名称为"+volumeName+"的数据盘");
			response.getWriter().print("<script>window.location.href='getInstanceDetails?types=volume&hypervisor_type="+ hypervisor_type + "&instanceId="+instanceId+"';</script>");		
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getInstanceDetails?types=volume&instanceId="+instanceId+"';</script>");
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
	  * @Title: goUpdateVolume
	  * @Description: 跳转到数据盘的编辑页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:03:28
	 *//*
	@RequestMapping("/goUpdateVolume")
	public String goUpdateVolume(HttpServletRequest request,HttpServletResponse response){
		String instanceId = request.getParameter("instanceId");
		String volumeId = request.getParameter("volumeId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		VolumeBean volumeBean = null;
		try {
			volumeBean = storageService.getVolumeDetails(volumeId,tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("volumeBean",volumeBean);	
		request.setAttribute("instanceId",instanceId);	
		request.setAttribute("hypervisor_type",request.getParameter("hypervisor_type"));
		return "home/ec2/ec2_volumes_detail_update";
	}	
	
	*//**
	 * 
	  * @Title: updateVolume
	  * @Description: 更新数据盘
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:03:35
	 *//*
	@RequestMapping("/updateVolume")
	public String updateVolume(HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		String volumeId = request.getParameter("volumeId");
		String instanceId = request.getParameter("instanceId");
		String volumeName =request.getParameter("volumeName");
		String volumeDesc =request.getParameter("volumeDesc");
		String hypervisor_type =request.getParameter("hypervisor_type");
		
		VolumeBean volumeBean=new VolumeBean();
		volumeBean.setId(volumeId);
		volumeBean.setName(volumeName);
		volumeBean.setDescription(volumeDesc);
		
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
		try {
			storageService.updateBlockStorage(volumeBean,tenantId,tokenId);
			request.setAttribute("actionLog", "更新了一个名称为"+volumeName+"的数据盘");
			response.getWriter().print("<script>window.location.href='getInstanceDetails?types=volume&hypervisor_type="+ hypervisor_type + "&instanceId="+instanceId+"';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getInstanceDetails?types=volume&instanceId="+instanceId+"';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}	
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: deleteVolume
	  * @Description: 删除数据盘
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:03:43
	 *//*
	@RequestMapping("/deleteVolume")
	public String deleteVolume(HttpServletRequest request,HttpServletResponse response)throws Exception {
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String hypervisor_type =request.getParameter("hypervisor_type");
		String instanceId = request.getParameter("instanceId");
		String volumeId=request.getParameter("volumeId");
		String[] strId = volumeId.split(",");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		Boolean snapFlag = true;
		try {
			for (int i = 0; i < strId.length; i++) {			
				//删除数据盘前先查询是否有数据盘快照引用
				//先查询数据盘信息  判断是否有引用数据盘快照信息
				snapFlag = storageService.getSnapshotsListByVolumeId(strId[i],tenantId,tokenId);
				if(!snapFlag){//数据盘有数据盘快照信息
					//错误信息  页面不能正常显示
					//String error = "There are references, and cannot be deleted";
					response.getWriter().print("<script>parent.location.href='getInstanceDetails?types=volume&instanceId="+instanceId+"&promptBoxFlag=-1&errorMessage=There are references, and cannot be deleted';</script>");
					break;
				}else{				
					storageService.deteleVolume(strId[i],tenantId,tokenId);
					//删除数据库关联的数据盘信息
					instanceInfoService.deleteInstanceVolume(strId[i]);
					request.setAttribute("actionLog", "删除了一个ID为"+strId[i]+"的数据盘");
					response.getWriter().print("<script>window.location.href='getAllServerVolume?types=volume&instanceId="+instanceId+"&hypervisor_type="+ hypervisor_type + "';</script>");
				}
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllServerVolume?types=volume&instanceId="+instanceId+"&hypervisor_type="+ hypervisor_type + "';</script>");
		} catch (BaseException e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}	
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	//跳转到挂载数据盘页面
	@RequestMapping("/goVolumeMount")
	public String goVolumeMount(HttpServletRequest request,HttpServletResponse response) {
		String volumeId = request.getParameter("volumeId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		List<ServerDetailBean> serverList = null;
		try {
			serverList = instanceServerService.instanceServerListByStatus(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("volumeId",volumeId);
		//所有实例  有效的  可挂载的
		request.setAttribute("servers",serverList);
		return "home/ec2/ec2_volumes_detail_addition";
	}
	
	*//**
	 * 
	  * @Title: volumeMount
	  * @Description: 挂载数据盘
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月25日 下午7:03:53
	 *//*
	@RequestMapping("/volumeMount")
	public String volumeMount(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
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
		String instanceId =request.getParameter("instanceId");//实例id
		String volumeId=request.getParameter("volumeId");//数据盘id
		if(instanceId==null&&"".equals(instanceId)||volumeId==null&&"".equals(volumeId)){
			System.out.println("挂载数据盘时 有空值！！！");
			throw new NullPointerException();
		}
		String hypervisor_type = request.getParameter("hypervisor_type");
		ServerVolumeAttachmentBean svBean = new ServerVolumeAttachmentBean();
		svBean.setServerId(instanceId);
		svBean.setVolumeId(volumeId);
		try {
			String info = instanceServerService.createServerVolumeMount(svBean,tenantId,tokenId);
			request.setAttribute("actionLog", "挂载了一个ID为"+volumeId+"的数据盘");
			if(info != null && info != ""){
				response.getWriter().print("<script>parent.location.href=\"getInstanceDetails?types=volume&hypervisor_type="+ hypervisor_type + "&instanceId="+instanceId+"&promptBoxFlag=-1&errorMessage="+info+"\";</script>");
			}else{
				response.getWriter().print("<script>parent.location.href='getInstanceDetails?types=volume&hypervisor_type="+ hypervisor_type + "&instanceId="+instanceId+"';</script>");	
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>parent.location.href='getInstanceDetails?types=volume&instanceId="+instanceId+"';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}	
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	  * @Title: goVolumeSeparate
	  * @Description: 跳转到分离数据盘页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:26:49
	 *//*
	@RequestMapping("/goVolumeSeparate")
	public String goVolumeSeparate(HttpServletRequest request,HttpServletResponse response) {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String volumeId = request.getParameter("volumeId");
		List<ServerVolumeAttachmentBean> attachments = null;
		try {
			VolumeBean volumeBean = storageService.getVolumeDetails(volumeId,tenantId,tokenId);
			attachments = volumeBean.getAttachments();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("attachments",attachments);	
		request.setAttribute("hypervisor_type",request.getParameter("hypervisor_type"));	
		return "home/ec2/ec2_volumes_detail_separate";
	}
	
	*//**
	 * 
	  * @Title: volumeSeparate
	  * @Description: 分离数据盘
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:27:00
	 *//*
	@RequestMapping("/volumeSeparate")
	public String volumeSeparate(HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		String serverId =request.getParameter("serverId");//实例id
		String attachmentId=request.getParameter("attachmentId");//挂载数据盘id
		String hypervisor_type=request.getParameter("hypervisor_type");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			String infos = instanceServerService.deleteServerVolumeSeparate(serverId, attachmentId,tenantId,tokenId);
			request.setAttribute("actionLog", "分离了一个名称为"+attachmentId+"的数据盘");
			if(infos != null && infos != ""){
				response.getWriter().print("<script>parent.document.getElementById(\"mainiframe\").contentWindow.location.href=\"getInstanceDetails?types=volume&hypervisor_type="+ hypervisor_type + "&instanceId="+serverId+"&promptBoxFlag=-1&errorMessage="+infos+"\";</script>");
			}else{
				response.getWriter().print("<script>parent.document.getElementById(\"mainiframe\").contentWindow.location.href='getInstanceDetails?types=volume&hypervisor_type="+ hypervisor_type + "&instanceId="+serverId+"';</script>");	
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>parent.document.getElementById(\"mainiframe\").contentWindow.location.href='getAllServerVolume?types=volume&instanceId="+serverId+"';</script>");
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
	  * @Title: getAllSnapshots
	  * @Description: 查询数据盘快照list
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:27:17
	 *//*
	@RequestMapping("/getAllSnapshots")
	public String getAllSnapshots(HttpServletRequest request,HttpServletResponse response){
		String promptBoxFlag = request.getParameter("promptBoxFlag");
		request.setAttribute("promptBoxFlag", promptBoxFlag);
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		//数据盘快照
		List<SnapshotsBean> list = null;
		try {
			list = storageService.getSnapshotsList(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		request.setAttribute("snapshotsList", list);
		return "home/ec2/ec2_snapshots_list";
	}
	
	*//**
	 * 
	  * @Title: getSnapshotsDetails
	  * @Description: 获取数据盘快照详情信息
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:27:37
	 *//*
	@RequestMapping("/getSnapshotsDetails")
	public String getSnapshotsDetails(HttpServletRequest request,HttpServletResponse response){
		String snapshotsId = request.getParameter("snapshotsId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		SnapshotsBean bean = null;
		try {
			bean = storageService.getSnapshotsBeanDetails(snapshotsId,tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReturnToLastPageException(e.getMessage());
		}
		request.setAttribute("snapshotsBean",bean);
		return "home/ec2/ec2_snapshots_detail";
	}
	
	*//**
	 * 
	  * @Title: Snapshot
	  * @Description: ajax请求  局部刷新  数据盘快照list页面的查询 用于进度条的查询   
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:27:45
	 *//*
	@RequestMapping("/getSnapshotDetailsJson")
	public String Snapshot(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		String snapshotsId = request.getParameter("snapshotsId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		SnapshotsBean bean  = null;
		try {
			bean = storageService.getSnapshotsBeanDetails(snapshotsId,tenantId,tokenId);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		JSONObject jsonObject = new JSONObject();
		if(bean!=null){
			jsonObject.put("success", true);
			jsonObject.put("snapshotsId", bean.getId());
			jsonObject.put("status", bean.getStatus());
			if(bean.getStatus().equals(OPSTTaskstateConst.snapshot_state_error_deleting)){ 

			}else if(bean.getStatus().equals(OPSTTaskstateConst.snapshot_state_creating)||
					bean.getStatus().equals(OPSTTaskstateConst.snapshot_state_deleting)){
				jsonObject.put("taskStateProgress", bean.getTaskStateProgress());
			}else{
				jsonObject.put("operationState", true);
			}	
		}else if(bean==null){
			jsonObject.put("success", true);
			jsonObject.put("operationState", true);
		}
		response.getWriter().print(jsonObject);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: goCreateSnapshots
	  * @Description: 数据盘快照列表页面 创建数据盘快照按钮事件  跳转到数据盘快照创建页面
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:28:19
	 *//*
	@RequestMapping("/goCreateSnapshots")
	public String goCreateSnapshots(HttpServletRequest request,HttpServletResponse response){		
		String type = request.getParameter("type");
		String instanceId = request.getParameter("instanceId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		List<VolumeBean> volumeList = null;
		String volumeName ="";
		StorageLimitsBean limitBean = null;
		try {
			if(type!=null&&type.equals("snapshot")){
				//数据盘
				volumeList = storageService.getVolumeList(tenantId,tokenId);
			}else{			
				String volumeId = request.getParameter("volumeId");
				if(volumeId!=null&&!volumeId.equals("")){
					request.setAttribute("volumeId", volumeId);
					VolumeBean volumeBean = storageService.getVolumeDetails(volumeId,tenantId,tokenId);
					volumeName = volumeBean.getName();
				}
			}
			limitBean = storageService.getVolumeTenantQuotasV2(tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("volumeList", volumeList);
		request.setAttribute("instanceId", instanceId);
		request.setAttribute("volumeName",volumeName);
		request.setAttribute("type", type);
		//数据盘资源占用数据显示  调用不用API 
		//request.setAttribute("volumeLimits", storageService.getVolumeLimis());//23 数据不全
		request.setAttribute("volumeLimits",limitBean);
		return "home/ec2/ec2_snapshots_detail_create";
	}	
	
	*//**
	 * 
	  * @Title: createSnapshots
	  * @Description: 创建一个数据盘快照
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:28:29
	 *//*
	@RequestMapping("/createSnapshots")
	public String createSnapshots(HttpServletRequest request,HttpServletResponse response)throws Exception{	
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		SnapshotsBean snapshotsBean=new SnapshotsBean();
		String snapshotsName=request.getParameter("SnapshotsName");
		//String instanceId=request.getParameter("instanceId");
		String snapshotsDescription=request.getParameter("SnapshotsDescription");
		String snapVolumeID=request.getParameter("hiddenVolumeID");
		
		snapshotsBean.setName(snapshotsName);
		snapshotsBean.setDescription(snapshotsDescription);
		snapshotsBean.setVolume_id(snapVolumeID);
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		try {
			storageService.createSnapshots(snapshotsBean,tenantId,tokenId);
			request.setAttribute("actionLog", "创建了一个名称为"+snapshotsName+"的数据盘快照");
			response.getWriter().print("<script>window.location.href='getAllSnapshots?promptBoxFlag=1';</script>");		
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllSnapshots?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		response.getWriter().flush();
		response.getWriter().close();	
		return null;
	}
	
	*//**
	 * 
	  * @Title: goUpdateSnapshots
	  * @Description:跳转到数据盘快照的编辑页面 
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:28:37
	 *//*
	@RequestMapping("/goUpdateSnapshots")
	public String goUpdateSnapshots(HttpServletRequest request,HttpServletResponse response){
		String snapshotsId = request.getParameter("snapshotsId");
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		SnapshotsBean snapshotsBean = null;
		try {
			snapshotsBean = storageService.getSnapshotsBeanDetails(snapshotsId,tenantId,tokenId);
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}
		request.setAttribute("snapshotsBean",snapshotsBean);
		return "home/ec2/ec2_snapshots_detail_update";
	}
	
	*//**
	 * 
	  * @Title: updateSnapshots
	  * @Description: 更新一个数据盘快照
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:28:42
	 *//*
	@RequestMapping("/updateSnapshots")
	public String updateSnapshots(HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");		
		
		SnapshotsBean snapshotsBean=new SnapshotsBean();
		String snapshotsId = request.getParameter("snapshotsId"); 
		String snapshotsName=request.getParameter("SnapshotsName");
		String snapshotsDescription=request.getParameter("SnapshotsDescription");
		String snapVolumeID=request.getParameter("hiddenVolumeID");
		
		snapshotsBean.setId(snapshotsId);
		snapshotsBean.setName(snapshotsName);
		snapshotsBean.setDescription(snapshotsDescription);
		snapshotsBean.setVolume_id(snapVolumeID);
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		try {
			storageService.updateSnapshots(snapshotsBean,tenantId,tokenId);
			request.setAttribute("actionLog", "更新了一个名称为"+snapshotsName+"的数据盘快照");
			response.getWriter().print("<script>window.location.href='getAllSnapshots?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllSnapshots?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PopUpMessageException(e.getMessage());
		}		
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
	*//**
	 * 
	  * @Title: deleteSnapshots
	  * @Description:根据数据盘快照ID删除一个数据盘快照 
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:28:48
	 *//*
	@RequestMapping("/deleteSnapshots")
	public String deleteSnapshots(HttpServletRequest request,HttpServletResponse response)throws Exception {
		
		String tenantId = "";
		String tokenId = "";
		try{
			tenantId = OpenStackUtil.getTenantId();
			tokenId = OpenStackUtil.getTokenId();
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} 
		
		String snapshotsID=request.getParameter("SnapshotsID");
		String[] strId = snapshotsID.split(",");
		
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			for (int i = 0; i < strId.length; i++) {
				storageService.deteleSnapshots(strId[i],tenantId,tokenId);
				request.setAttribute("actionLog", "删除了一个ID为"+strId[i]+"的数据盘快照");
			}
			response.getWriter().print("<script>window.location.href='getAllSnapshots?promptBoxFlag=1';</script>");
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (ErrorMessageException e) {
			response.getWriter().print("<script>window.location.href='getAllSnapshots?promptBoxFlag=-1&errorMessage="+e.getMessage()+"';</script>");
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
	  * @Title: checkOpstUserNameByName
	  * @Description: 查看是否已存在相同的快照名称
	  * @param @param request
	  * @param @param response
	  * @param @return
	  * @param @throws Exception
	  * @return String
	  * @author liwj
	  * @throws
	  * @Time 2015年3月27日 上午10:28:55
	 *//*
	@RequestMapping("/checkSnapshotsByName")
	public String checkOpstUserNameByName(HttpServletRequest request,HttpServletResponse response) throws Exception { 
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
		String sid = request.getParameter("sid");
		String sname = request.getParameter("sname");	
		JSONObject object = new JSONObject();
		Boolean isExist = true; 
		try {
			List<SnapshotsBean> list = storageService.getSnapshotsList(tenantId,tokenId);
			for(SnapshotsBean s :list){
				if(s.getName().equals(sname)&&sid!=null&&!s.getId().equals(sid)){
					isExist = false;//存在相同的用户名
					break;
				}else if(s.getName().equals(sname)&&sid==null){
					isExist = false;//存在相同的用户名
					break;
				}
			}
		} catch (LoginTimeOutException e) {
			return this.setExceptionTologin(e, request);
		} catch (Exception e) {
			object.put("success", false);//发生异常按存在相同用户名处理
		}
		object.put("success",isExist);
		response.getWriter().print(object);
		response.getWriter().flush();
		response.getWriter().close();
		return null;
	}
	
}*/