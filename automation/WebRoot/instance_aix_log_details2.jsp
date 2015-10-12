<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fasterxml.jackson.databind.*"%>
<%@ page import="com.fasterxml.jackson.databind.node.*"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<jsp:include page="header.jsp" flush="true"/>
	<title>云计算基础架构平台</title>
	<style>
	.bcg{
		background:#00ec00;
	}
	.bcr{
		background:#ff8080;
	}
	.bcb{
		background:#8080ff;
	}
	.bcy{
		background:yellow;
	}
	.input200{
	display:inline-block; width:200px; margin-top:2px; text-align:right;
	}
	</style>
	<script language="javascript" type="text/javascript">
		$(document).ready(function(){
			//给td添加颜色
			trObject = $("#logTable3").find("tr");
			for(var i=0; i<trObject.length; i++){
				var tdText = $(trObject[i]).find("td:last");
				if(tdText.html().trim() == "成功"){
					tdText.addClass("bcg");
				}
				if(tdText.html().trim() == "失败"){
					tdText.addClass("bcr");
					$("#progress_my").attr("class","progress progress-s progress-striped progress-danger ine-block w100");
				}
				if(tdText.html().trim() == "执行中"){
					tdText.addClass("bcb");
				}
				if(tdText.html().trim() == "手工运行"){
					tdText.addClass("bcy");
				}
			}
		});
	</script>
	<script language="JavaScript">
	var i = 1;
	function getInstallMsg(){
		//alert("已经获取数据。。。。。。");
		
		var uuid = $("#uuid").val().trim();
		
			$.ajax({
			url:'nodeInstall?uuid='+uuid,
			type:'post',
			cache:false,
			dataType: "json",
			error:function(){
				//alert("获取安装信息异常！");
			},
			success:function(data){
				//alert(data);
				//alert("已经获取数据。。。。。。");
			}
		}); 
		/* var msgt = "<span>" + i + "</span><br/>";
		$(".installMsg").last().append(msgt);
		$(".installMsg").last().append("<input id='input111'></input>");
		$("#input111").focus();
		$("#input111").remove();
		
		i = i + 1; */
	}
	
	//定时获取安装信息
	
	//setInterval('getInstallMsg()', 15000);
	</script>

<script language="JavaScript">
	function myrefresh() {

		
		if(($("#logmsg").attr("class") == 'tabcontent tabnow')  && ($("#status").val().trim() != '2')){
			window.location.reload();
			
		}
			
	}
	setTimeout('myrefresh()', 10000); //指定1秒刷新一次
	
</script>

</head>

<body>
<!--header start-->
  <div class="header">
  	<jsp:include page="topinfo.jsp" flush="true"/>
  </div>
<!--header end--> 

<!--content start-->
  <div class="content">
  	<div class="breadcrumb">
      <a href="getLogInfo" title="历史执行日志" class="tip-bottom"><i class="icon-home"></i>历史执行日志</a>
      <a href="#" class="current">任务信息</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
            <div class="span12">
              <div class="widget-box collapsible">
                  <div class="widget-title">
                      <a data-toggle="collapse" href="#collapseOne">
                          <span class="icon">
                              <i class="icon-arrow-right"></i>
                            </span>
                            <h5>说明：</h5>
                        </a>
                    </div>
                    <div id="collapseOne" class="collapse in">
                        <div class="widget-content">详细信息.</div>
                    </div>
                </div>
            </div>
      </div>
    </div>
		<div class="container-fluid">
        <div class="row-fluid">
          <div class="span12">
					<div class="columnauto">
						<div class="tabtitle">
							<ul class="tabnav">
								<li>虚机节点</li>
								<li>环境参数</li>
								<li class="active">执行日志</li>
							</ul>
						</div>

						<div class="tabcontent">
							<div class="mainmodule">

								<div class="mainmodule">
									<!-- <h5 class="stairtit" style="margin-left:10px;"><i class="icon-chevron-down"></i>拓扑结构</h5> -->
									<h5 class="stairtit swapcon">
										<!-- <i class="icon-chevron-down"></i> -->拓扑结构
									</h5>
									<c:forEach items="${servers }" var="ser" varStatus="num">
										<p class="twotit">
											<c:forEach items="${ser.address}" var="addr">
												<c:if test="${mainaddr==addr.addr}">
													<em class="majornode">主</em>
												</c:if>
												<c:if test="${mainaddr!=addr.addr}">
													<em class="vicenode">备</em>
												</c:if>
											</c:forEach>
											<!--  <em class="majornode">主</em> -->
											<!-- <em class="tube">管</em> -->
											节点
											<c:out value="${num.count }" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>serviceIP：</b><span>${ha_svcip }</span>
										</p>
										<div class="column">
											<div class="span12">
												<p>
													<b>主机名:</b><span class="column_txt">${ser.name }</span> 
													<b>IP地址:</b><span class="column_txtl"><c:forEach items="${ser.address}" var="addr">${addr.addr }&nbsp;&nbsp;</c:forEach></span>
													<b>镜像:</b><span span class="column_txt2"><c:forEach items="${imageList }" var="img"><c:if test="${ser.image.id eq img.id }">${img.name }</c:if></c:forEach></span>
													 <b>资源组：</b><span class="mr10">${ha_RGNmae }</span>
												</p>
												<p>
													<b>主机风格:</b><span class="column_txt"><c:forEach items="${flavors }" var="flav"><c:if test="${ser.flavor.id eq flav.id }">${flav.vcpus }C/${flav.ram }MB/${flav.disk }GB</c:if></c:forEach></span> 
													<b>挂卷:</b><span class="column_txtl">volume_HB ,volume_Data</span> 
													<b>状态:</b><span class="column_txt"><img src="img/icons/common/icon_success.png"><em>${ser.status }</em></span>
													<b>AS名称：</b><span class="mr10">${ha_ASName }</span>
												</p>
											</div>
										</div>
									</c:forEach>
								</div>


								<div class="mainmodule">
									<h5 class="swapcon">
										<!-- <i class="icon-chevron-down"></i> -->脚本已生成并上传至服务器路径：
									</h5>
									<div class="form-horizontal processInfo" >
										<textarea readonly="readonly" rows="6" style="background:#f7f7f7;font-size:13px;overflow-y:hidden;">
[username@hostname]# cd /script
[username@hostname script]# ls
audb.sh  ha_setup.ksh  hostname.helper.sh  hosts.helper.sh  importvg.ksh  install.db2.ksh  mkvg.ksh  prepare.db2.ksh  prepare.db2.lst  update.db2.ksh  mount.nfs.ksh
            
*注：主备机脚本路径相同
                      </textarea>
									</div>
								</div>

								<div class="mainmodule">
									<h5 class="swapcon">
										<!-- <i class="icon-chevron-down"></i> -->手工执行脚本方法如下：
									</h5>
									<div class="form-horizontal processInfo">
										<textarea readonly="readonly" rows="7" style="background:#f7f7f7;font-size:13px;overflow-y:hidden;">
1.使用root用户分别登录两台主机
2.分别进入两台目标主机的目录/script中
3.首先在主机端执行./prepare.db2.ksh ${ha_primaryNode }
4.等待主机执行完成后，在备机端执行./prepare.db2.ksh ${ha_subNode }
5.等待主备机全部执行完prepare.db2.ksh脚本后，在主机端执行./install.db2.ksh ${ha_clusterName }
6.若挂载了NFS文件系统，在主备机两端分别执行mount.nfs.ksh挂载NFS文件系统
                      </textarea>
									</div>
								</div>

							</div>
						</div>

						<div class="tabcontent">
							<div class="mainmodule">
							
								<div class="mainmodule">
									<h5 class="swapcon"><i class="icon-chevron-down"></i>HA参数</h5>
									<div class="form-horizontal">
									
									<div class="control-group">
											<label class="control-label">HA名称</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_clusterName }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20">资源组主节点</span> <span
														class="graytxt">${ha_primaryNode }</span>
												</div>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label">节点 1 IP</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_ip1 }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20">节点 1 主机名</span> <span
														class="graytxt">${ha_hostname1 }</span>
												</div>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label">节点 1 Boot IP</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_bootip1 }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20">节点 1 Boot主机别名</span> <span
														class="graytxt">${ha_bootalias1 }</span>
												</div>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label">节点 2 IP</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_ip2 }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20">节点 2 主机名</span> <span
														class="graytxt">${ha_hostname2 }</span>
												</div>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label">节点 2 Boot IP</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_bootip2 }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20">节点 2 Boot主机别名</span> <span
														class="graytxt">${ha_bootalias2 }</span>
												</div>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label">HA Service IP</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_svcip }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20">Service主机别名</span> <span
														class="graytxt">${ha_svcalias }</span>
												</div>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label">资源组名称</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${ha_RGNmae }</span>
												</div>
												<div class="inputb2l">
													<span class="input200 mr20" >Application Server(Controller)</span>
													 <span class="graytxt">${ha_ASName }</span>
												</div>
											</div>
										</div>
					
					
					<!-- <div class="control-group groupborder">
                      <label class="control-label c-lmini"></label>
                      <div class="controls controls-mini">
                        <div class="inputb4"><input style="margin-top:-20px;" type="text" name="ha_vgcaap" value="VG" readonly="readonly"/></div>
                        <div class="inputb2">
                        	<input style="margin-top:-20px;" type="text" name="ha_vgcaap" value="PV" readonly="readonly"/>
                        </div>
                        <div class="inputb4">
                          <input style="margin-top:-20px;" type="text" name="ha_caapmode" value="VG创建方式" readonly="readonly"/>
                        </div>
                      </div>
                    </div>	 -->				
					
					
					 <div class="control-group groupborder divnfs" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">VG名称</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_vgdb2home }</span></div>
                        <span class="input140 mr20" style="width:128px;">包含PV</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_db2homepv }</span></div>
                        <span class="input140 mr20" style="width:128px;">VG创建方式</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_db2homemode }</span></div>
                      </div>
                    </div>
                    
                     <div class="control-group groupborder divnfs" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">VG名称</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_vgdb2log }</span></div>
                        <span class="input140 mr20" style="width:128px;">包含PV</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_db2logpv }</span></div>
                        <span class="input140 mr20" style="width:128px;">VG创建方式</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_db2logmode }</span></div>
                      </div>
                    </div>
                    
                     <div class="control-group groupborder divnfs" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">VG名称</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_vgdb2archlog }</span></div>
                        <span class="input140 mr20" style="width:128px;">包含PV</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_db2archlogpv }</span></div>
                        <span class="input140 mr20" style="width:128px;">VG创建方式</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_db2archlogmode }</span></div>
                      </div>
                    </div>
                    
                     <div class="control-group groupborder divnfs" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">VG名称</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_vgdataspace }</span></div>
                        <span class="input140 mr20" style="width:128px;">包含PV</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_dataspacepv }</span></div>
                        <span class="input140 mr20" style="width:128px;">VG创建方式</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_dataspacemode }</span></div>
                      </div>
                    </div>
                    
                     <div class="control-group groupborder divnfs" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">VG名称</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_vgcaap }</span></div>
                        <span class="input140 mr20" style="width:128px;">包含PV</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_caappv }</span></div>
                        <span class="input140 mr20" style="width:128px;">VG创建方式</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_caapmode }</span></div>
                      </div>
                    </div>
                    					
                    
                    
                   <%--  <div class="control-group groupborder">
                      <label class="control-label c-lmini"></label>
                      <div class="controls controls-mini">
                        <div class="inputb4">
                        <span class="graytxt">${ha_vgcaap }</span>
                        </div>
                        <div class="inputb2">
                        	<span class="graytxt">${ha_caappv }</span>
                        </div>
                        <div class="inputb4">
                          <span class="graytxt">${ha_caapmode }</span>
                        </div>
                      </div>
                    </div> --%>
										
										
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">启动HA策略</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_startpolicy }</span></div>
                        <span class="input140 mr20" style="width:128px;">HA切换策略</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_fopolicy }</span></div>
                        <span class="input140 mr20" style="width:128px;">HA回切策略</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_fbpolicy }</span></div>
                      </div>
                    <c:if test = "${ha_nfsON ne ison }" >   
	                <div class="controls controls-mini" style="margin-left:0.7%;">
	               		<span class="input140 mr20" style="width:142px;">是否挂载NFS文件系统</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsON }</span></div>
	                </div>
	              </c:if>  
	             <c:if test = "${ha_nfsON eq ison }" >   
	             	 <c:if test = "${ha_nfsIP1 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址1</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP1 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录1</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint1 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点1</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint1 }</span></div>
                      </div>
                       </c:if>
                   <c:if test = "${ha_nfsIP2 != '' }" >   
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址2</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP2 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录2</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint2 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点2</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint2 }</span></div>
                      </div>
                    </c:if>
                    
                     <c:if test = "${ha_nfsIP3 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址3</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP3 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录3</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint3 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点3</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint3 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP4 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址4</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP4 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录4</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint4 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点4</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint4 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP5 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址5</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP5 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录5</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint5 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点5</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint5 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP6 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址6</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP6 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录6</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint6 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点6</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint6 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP7 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址7</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP7 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录7</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint7 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点7</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint7 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP8 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址8</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP8 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录8</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint8 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点8</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint8 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP9 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址9</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP9 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录9</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint9 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点9</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint9 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP10 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址10</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP10 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录10</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint10 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点10</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint10 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP11 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址11</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP11 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录11</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint11 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点11</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint11 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP12 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址12</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP12 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录12</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint12 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点12</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint12 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP13 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址13</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP13 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录13</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint13 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点13</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint13 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP14 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址14</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP14 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录14</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint14 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点14</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint14 }</span></div>
                      </div>
                       </c:if>
                      
                       <c:if test = "${ha_nfsIP15 != '' }" >
                      <div class="controls controls-mini" style="margin-left:0.7%;">
                        <span class="input140 mr20" style="width:142px;">NFS服务端 IP地址15</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsIP15 }</span></div>
                        <span class="input140 mr20" style="width:128px;">服务端共享目录15</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsSPoint15 }</span></div>
                        <span class="input140 mr20" style="width:128px;">NFS客户端挂载点15</span>
                        <div class="inputb4" style="width:20%;"><span class="graytxt">${ha_nfsCPoint15 }</span></div>
                      </div>
                       </c:if>
               </c:if>     
                    
                    
									</div>
								</div>
								
								
								<div class="mainmodule">
									<h5 class="swapcon"><i class="icon-chevron-down icon-chevron-right"></i>基本信息</h5>
									<div class="form-horizontal" style="display: none;">
										<div class="control-group">
											<label class="control-label">DB2安装版本</label>
											<div class="controls">
												<span class="graytxt">${db2_version }</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DB2安装版本</label>
											<div class="controls">
												<span class="graytxt">${db2_db2base }</span>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label">DB2实例目录</label>
											<div class="controls">
												<span class="graytxt">${db2_dbpath }</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DB2实例名</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_db2insusr }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">监听端口</span> <span
														class="graytxt">${db2_svcename }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DB2数据库名</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_dbname }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">字符集</span> <span
														class="graytxt">${db2_codeset }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DB2数据目录</label>
											<div class="controls">
												<span class="graytxt">${db2_dbdatapath }</span>
											</div>
										</div>
									</div>
								</div>

								<div class="mainmodule">
									<h5 class="swapcon"><i class="icon-chevron-down icon-chevron-right"></i>实例高级属性</h5>
									<div class="form-horizontal" style="display: none;">

										<div class="control-group">
											<label class="control-label">实例用户</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_db2insusr }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">实例用户组</span> <span
														class="graytxt">${db2_db2insgrp }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">fence用户</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_db2fenusr }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">fence用户组</span> <span
														class="graytxt">${db2_db2fengrp }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DB2COMM</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_db2comm }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">DB2CODEPAGE</span> <span
														class="graytxt">${db2_db2codepage }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">NUM_INITAGENI</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_initagents }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">NUM_POOLAGENTS</span> <span
														class="graytxt">${db2_poolagents }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">MAX_COORDAGENTS</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_max_coordagents }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">MAX_CONNECTIONS</span> <span
														class="graytxt">${db2_max_connectings }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DIAGSIZE</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_diagsize }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">DFT_MON_BUFPOOL</span> <span
														class="graytxt">${db2_mon_buf }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DFT_MON_LOCK</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_mon_lock }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">DFT_MON_SORT</span> <span
														class="graytxt">${db2_mon_sort }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DFT_MON_STMT</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_mon_stmt }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">DFT_MON_TABLE</span> <span
														class="graytxt">${db2_mon_table }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DFT_MON_UOW</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_mon_uow }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">HEACTH_MON</span> <span
														class="graytxt">${db2_health_mon }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">MON_HEAP_SZ</label>
											<div class="controls">
												<span class="graytxt">${db2_mon_heap }</span>
											</div>
										</div>
									</div>
								</div>

								<div class="mainmodule">
									<h5 class="swapcon"><i class="icon-chevron-down icon-chevron-right"></i>数据库高级属性</h5>
									<div class="form-horizontal" style="display: none;">
										<div class="control-group">
											<label class="control-label">DB2日志路径</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_db2log }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">归档日志路径</span> <span
														class="graytxt">${db2_logarchpath }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">DB2备份路径</label>
											<div class="controls">
												<span class="graytxt">${db2_backuppath }</span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">SELF_TUNING_MEM</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_stmm }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">LOCKLIST</span> <span
														class="graytxt">${db2_locklist }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">MAXLOCKS</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_maxlocks }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">LOCKTIMEOUT</span> <span
														class="graytxt">${db2_locktimeout }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">SORTHEAP</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_sortheap }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">LOGFILESIZ</span> 
													<span class="graytxt">${db2_logfilesize }  MB</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">LOGPRIMARY</label>
											<div class="controls">
												<div class="inputb2l">
													<!-- <input type="text" class="w45" value="30" /> -->
													<span class="graytxt">${db2_logprimary }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">LOGSECOND</span> <span
														class="graytxt">${db2_logsecond }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">LOGBUFSZ</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_logbuff } MB</span> 
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">SOFTMAX</span> <span
														class="graytxt">${db2_softmax }</span>
												</div>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label">TRACKMOD</label>
											<div class="controls">
												<div class="inputb2l">
													<span class="graytxt">${db2_trackmod }</span>
												</div>
												<div class="inputb2l">
													<span class="input140 mr20">PAGESIZE</span> <span
														class="graytxt">${db2_pagesize }</span>
												</div>
											</div>
										</div>

									</div>
								</div>



							</div>
						</div>
						<div id="logmsg" class="tabcontent tabnow">
							<%-- <input type="text" id="uuid" name="uuid" value="${uuid}"> --%>
							<p class="columntxt2">主机安装进度 :&nbsp;&nbsp;&nbsp;${progress }</p>
							<div id = "progress_my"
								class="progress progress-s progress-striped progress-success ine-block w100">
								<div class="bar" name="progress" style="width: ${percent};"></div>
								<input type="hidden" id="percent"  value="${percent}">
								<input type="hidden" id="status"  value="${status}">
							</div>
							<h5>详细任务执行状态：</h5>
							<!-- <div class="column installMsg"style="height: 500px; background: #ccc; overflow: scroll;"> -->
							<%--  <center>  --%>
								<table id="logTable3" align="center" width="100%" cellspacing="0" cellpadding="0" border="1" style="height:1000px;">
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第一步：目标虚拟机上创建脚本存放目录</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虚拟机${prepare_scripts_addr_0 }上生成脚本存放目录/script</td>
										<td style="text-align:center;">${prepare_scripts_0 } </td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虚拟机${prepare_scripts_addr_1 }上生成脚本存放目录/script</td>
										<td style="text-align:center;">${prepare_scripts_1 }</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第二步：向目标虚拟机推送脚本文件</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_put_hosts_addr_0 } /script路径下推送脚本文件hosts.helper.sh</td>
										<td style="text-align:center;">${prepare_put_hosts_0 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_put_hosts_addr_1 } /script路径下推送脚本文件hosts.helper.sh</td>
										<td style="text-align:center;">${prepare_put_hosts_1 }</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第三步：向目标虚拟机推送脚本文件</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_put_hostname_addr_0 } /script路径下推送脚本文件hostname.helper.sh</td>
										<td style="text-align:center;">${prepare_put_hostname_0 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_put_hostname_addr_1 } /script路径下推送脚本文件hostname.helper.sh</td>
										<td style="text-align:center;">${prepare_put_hostname_1 }</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第四步：向目标虚拟机推送脚本文件</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_0 } /script路径下推送脚本文件audb.sh</td>
										<td style="text-align:center;">${prepare_files_0 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_1 } /script路径下推送脚本文件audb.sh</td>
										<td style="text-align:center;">${prepare_files_1 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_2 } /script路径下推送脚本文件ha_setup.sh</td>
										<td style="text-align:center;">${prepare_files_2 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_3 } /script路径下推送脚本文件ha_setup.sh</td>
										<td style="text-align:center;">${prepare_files_3 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_4 } /script路径下推送脚本文件importvg.sh</td>
										<td style="text-align:center;">${prepare_files_4 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_5 } /script路径下推送脚本文件importvg.sh</td>
										<td style="text-align:center;">${prepare_files_5 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_6 } /script路径下推送脚本文件install.db2.sh</td>
										<td style="text-align:center;">${prepare_files_6 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_7 } /script路径下推送脚本文件install.db2.sh</td>
										<td style="text-align:center;">${prepare_files_7 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_8 } /script路径下推送脚本文件mkvg.sh</td>
										<td style="text-align:center;">${prepare_files_8 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_9 } /script路径下推送脚本文件mkvg.sh</td>
										<td style="text-align:center;">${prepare_files_9 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_10 } /script路径下推送脚本文件prepare.db2.sh</td>
										<td style="text-align:center;">${prepare_files_10 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_11 } /script路径下推送脚本文件prepare.db2.sh</td>
										<td style="text-align:center;">${prepare_files_11 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_12 } /script路径下推送脚本文件update.db2.sh</td>
										<td style="text-align:center;">${prepare_files_12 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_13 } /script路径下推送脚本文件update.db2.sh</td>
										<td style="text-align:center;">${prepare_files_13 }</td>
									</tr>
									
									
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_14 } /script路径下推送脚本文件mount.nfs.ksh</td>
										<td style="text-align:center;">${prepare_files_14 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;向虚拟机${prepare_files_addr_15 } /script路径下推送脚本文件mount.nfs.ksh</td>
										<td style="text-align:center;">${prepare_files_15 }</td>
									</tr>
									
									
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第五步：目标虚拟机上生成参数文件</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虚拟机${prepare_prepare_db2_lst_addr_0 } /script路径下生成参数文件prepare.db2.lst</td>
										<td style="text-align:center;">${prepare_prepare_db2_lst_0 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虚拟机${prepare_prepare_db2_lst_addr_1 } /script路径下生成参数文件prepare.db2.lst</td>
										<td style="text-align:center;">${prepare_prepare_db2_lst_1 }</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第六步：赋予脚本文件执行权限</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为虚拟机${prepare_chmod_addr_0 } /script路径下脚本文件添加执行权限</td>
										<td style="text-align:center;">${prepare_chmod_0 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为虚拟机${prepare_chmod_addr_1 } /script路径下脚本文件添加执行权限</td>
										<td style="text-align:center;">${prepare_chmod_1 }</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第七步：设置虚拟机主机名与添加互信</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运行虚拟机${prepare_set_hostname_addr_0 } /script路径下脚本文件hostname.helper.sh</td>
										<td style="text-align:center;">${prepare_set_hostname_0 }</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;运行虚拟机${prepare_set_hostname_addr_1 } /script路径下脚本文件hostname.helper.sh</td>
										<td style="text-align:center;">${prepare_set_hostname_1 }</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第八步：创建DB2 HA环境</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为虚拟机${install_prepare_db2_ksh_addr_0 }上运行脚本文件prepare.db2.ksh，创建VG、安装DB2、配置数据库参数等</td>
										<td style="text-align:center;">
										<c:if test="${install_prepare_db2_ksh_0 != null}">
											${install_prepare_db2_ksh_0 }
										</c:if>
										<c:if test="${install_prepare_db2_ksh_0 == null}">
											手工运行
										</c:if>
										
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为虚拟机${install_prepare_db2_ksh_addr_1 }上运行脚本文件prepare.db2.ksh，创建VG、安装DB2、配置数据库参数等</td>
										<td style="text-align:center;">
										<c:if test="${install_prepare_db2_ksh_1 != null}">
											${install_prepare_db2_ksh_1 }
										</c:if>
										<c:if test="${install_prepare_db2_ksh_1 == null}">
											手工运行
										</c:if>
										
										</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第九步：配置DB2 HA环境</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;虚拟机主机${cluster_install_db2_ksh_addr_0 }上运行脚本文件install.db2.ksh，创建配置DB2 HA环境</td>
										<td style="text-align:center;">
										<c:if test="${cluster_install_db2_ksh_0 != null}">
											${cluster_install_db2_ksh_0 }
										</c:if>
										<c:if test="${cluster_install_db2_ksh_0 == null}">
											手工运行
										</c:if>
										</td>
									</tr>
									<tr>
										<td colspan="2"><b>&nbsp;&nbsp;&nbsp;第十步：挂载NFS文件系统</b></td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为虚拟机${nfs_mount_nfs_ksh_addr_0 }上运行脚本文件mount.nfs.ksh,挂载NFS文件系统</td>
										<td style="text-align:center;">
										<c:if test="${nfs_mount_nfs_ksh_0 != null}">
											${nfs_mount_nfs_ksh_0 }
										</c:if>
										<c:if test="${nfs_mount_nfs_ksh_0 == null}">
											手工运行
										</c:if>
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为虚拟机${nfs_mount_nfs_ksh_addr_1 }上运行脚本文件mount.nfs.ksh,挂载NFS文件系统</td>
										<td style="text-align:center;">
										<c:if test="${nfs_mount_nfs_ksh_1 != null}">
											${nfs_mount_nfs_ksh_1 }
										</c:if>
										<c:if test="${nfs_mount_nfs_ksh_1 == null}">
											手工运行
										</c:if>
										</td>
									</tr>
								</table>
								<%--  </center>  --%>
							<!-- </div> -->
						</div>
					</div>
				</div>
        </div>
      </div>
  </div>
<!--content end-->

<!--footer start-->
<!--   <div class="columnfoot">
  	<a class="btn btn-info btn-up" onclick="javascript:history.go(-1);">
      <i class="icon-btn-up"></i>
      <span>上一页</span>
    </a>
    <a class="btn btn-info fr btn-next">
      <span>下一页</span>
      <i class="icon-btn-next"></i>
    </a>
  </div>  --> 
<!--footer end-->
</body>
</html>
