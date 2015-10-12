<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<jsp:include page="header.jsp" flush="true"/>
	<title>自动化部署平台</title>
	<script language="javascript" type="text/javascript">
		//操作
		function CheckInput() {
			var wasHostIps = [];
			$('.wasHostIp').each(function(){
				wasHostIps.push($(this).val());
			});
			console.log(wasHostIps);
			
			var wasHostNames = [];			
			$('.wasHostName').each(function(){
				if($(this).val().trim()==""){
					ymPrompt.alert("请输入HostName!");
			    	return;	
				}else if(wasHostNames.indexOf($(this).val().trim())>-1){
					ymPrompt.alert("HostName不能重复!");
			    	return;	
				}else{
					wasHostNames.push($(this).val());
				}
				
			});
			console.log(wasHostNames);			
			
			var wasHostTypes = '';			
			$("select[name='wasHostType']").each(function(){
				wasHostTypes = wasHostTypes +$(this).val()+",";				
			});
			console.log(wasHostTypes);			
			var find = "cell";
			var reg = new RegExp(find,"g");
			var c = wasHostTypes.match(reg);
			if(parseInt(c?c.length:0) > 1 || parseInt(c?c.length:0) < 1){
				ymPrompt.alert("cell有且只能出现一次!");
		    	return;
			}
			
			$("#submits").submit();
			
		    /* var listSize = $("#listSize").val();
		    if(listSize == 1){
		    	var wasHostName1 = $("#wasHostName1").val();
		    	if(wasHostName1 ==null || wasHostName1 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
		    } else if(listSize ==2){
				var wasHostName1 = $("#wasHostName1").val();
				var wasHostName2 = $("#wasHostName2").val();
				
				var wasHostType1 = $("#wasHostType1").val();
				var wasHostType2 = $("#wasHostType2").val();
				var hostType = wasHostType1+","+wasHostType2;
				if(wasHostName1 ==null || wasHostName1 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName2 ==null || wasHostName2 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName1 == wasHostName2){
					ymPrompt.alert("HostName不能重复!");
			    	return;
				}
				var find = "cell";
				var reg = new RegExp(find,"g");
				var c = hostType.match(reg);
				if(parseInt(c?c.length:0) > 1){
					ymPrompt.alert("cell只能出现一次!");
			    	return;
				}
			}else if(listSize ==3){
				var wasHostName1 = $("#wasHostName1").val();
				var wasHostName2 = $("#wasHostName2").val();
				var wasHostName3 = $("#wasHostName3").val();
				
				var wasHostType1 = $("#wasHostType1").val();
				var wasHostType2 = $("#wasHostType2").val();
				var wasHostType3 = $("#wasHostType3").val();
				var hostType = wasHostType1+","+wasHostType2+","+wasHostType3;
				if(wasHostName1 ==null || wasHostName1 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName2 ==null || wasHostName2 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName3 ==null || wasHostName3 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if((wasHostName1 == wasHostName2) || (wasHostName1 == wasHostName3)|| (wasHostName2 == wasHostName3)){
					ymPrompt.alert("HostName不能重复!");
			    	return;
				}
				var find = "cell";
				var reg = new RegExp(find,"g");
				var c = hostType.match(reg);
				if(parseInt(c?c.length:0) > 1){
					ymPrompt.alert("cell只能出现一次!");
			    	return;
				}
			}else if(listSize ==4){
				var wasHostName1 = $("#wasHostName1").val();
				var wasHostName2 = $("#wasHostName2").val();
				var wasHostName3 = $("#wasHostName3").val();
				var wasHostName4 = $("#wasHostName4").val();
				
				var wasHostType1 = $("#wasHostType1").val();
				var wasHostType2 = $("#wasHostType2").val();
				var wasHostType3 = $("#wasHostType3").val();
				var wasHostType4 = $("#wasHostType4").val();
				var hostType = wasHostType1+","+wasHostType2+","+wasHostType3+","+wasHostType4;
				if(wasHostName1 ==null || wasHostName1 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName2 ==null || wasHostName2 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName3 ==null || wasHostName3 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName4 ==null || wasHostName4 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if((wasHostName1 == wasHostName2) || (wasHostName1== wasHostName3)
				    || (wasHostName1== wasHostName4) || (wasHostName2== wasHostName3)
				    || (wasHostName2== wasHostName4) || (wasHostName3== wasHostName4)){
					ymPrompt.alert("HostName不能重复!");
			    	return;
				}
				var find = "cell";
				var reg = new RegExp(find,"g");
				var c = hostType.match(reg);
				if(parseInt(c?c.length:0) > 1){
					ymPrompt.alert("cell只能出现一次!");
			    	return;
				}
			}else if(listSize ==5){
				var wasHostName1 = $("#wasHostName1").val();
				var wasHostName2 = $("#wasHostName2").val();
				var wasHostName3 = $("#wasHostName3").val();
				var wasHostName4 = $("#wasHostName4").val();
				var wasHostName5 = $("#wasHostName5").val();
				
				var wasHostType1 = $("#wasHostType1").val();
				var wasHostType2 = $("#wasHostType2").val();
				var wasHostType3 = $("#wasHostType3").val();
				var wasHostType4 = $("#wasHostType4").val();
				var wasHostType5 = $("#wasHostType5").val();
				var hostType = wasHostType1+","+wasHostType2+","+wasHostType3+","+wasHostType4+","+wasHostType5;
				if(wasHostName1 ==null || wasHostName1 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName2 ==null || wasHostName2 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName3 ==null || wasHostName3 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName4 ==null || wasHostName4 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if(wasHostName5 ==null || wasHostName5 == ""){
					ymPrompt.alert("请输入HostName!");
			    	return;
				}
				if((wasHostName1 == wasHostName2) || (wasHostName1== wasHostName3) 
					|| (wasHostName1== wasHostName4) || (wasHostName1== wasHostName5) 
					|| (wasHostName2== wasHostName3) || (wasHostName2== wasHostName4)
					|| (wasHostName2== wasHostName5) || (wasHostName3== wasHostName4)
					|| (wasHostName3== wasHostName5) || (wasHostName4== wasHostName5)){
					ymPrompt.alert("HostName不能重复!");
			    	return;
				}
				var find = "cell";
				var reg = new RegExp(find,"g");
				var c = hostType.match(reg);
				if(parseInt(c?c.length:0) > 1){
					ymPrompt.alert("cell只能出现一次!");
			    	return;
				}
			}else{
				$("#submits").submit();
			} */
		}
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
      <a href="getAllInstance?type=was" title="既有虚机+WAS集群" class="tip-bottom"><i class="icon-home"></i>既有虚机+WAS集群</a>
      <a href="#" class="current">Was配置</a>
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
                        <div class="widget-content">配置当前虚拟机的WAS信息</div>
                    </div>
                </div>
            </div>
      </div>
    </div>
		<div class="container-fluid">
        <div class="row-fluid">
          <div class="span12">
            <div class="columnauto">
              <div class="mainmodule">
                <h5 class="stairtit" style="margin-left:10px;">拓扑结构</h5>
                <c:forEach items="${servers }" var="ser" varStatus="num">
                <p class="twotit"><em class="majornode">主</em>节点<c:out  value="${num.count }"/>&nbsp;&nbsp;&nbsp;<b>serviceIP1：</b><span>255.255.255.255</span></p>
                <div class="column">
                  <div class="span12">
                     <p>
                      <b>主机名:</b><span class="column_txt">${ser.name }</span>
                      <b>IP地址:</b><span class="column_txtl"><c:forEach items="${ser.address}" var="addr">${addr.addr }&nbsp;&nbsp;</c:forEach></span>
                      <b>镜像:</b><span span class="column_txt2"><c:forEach items="${imageList }" var="img"><c:if test="${ser.image.id eq img.id }">${img.name }</c:if></c:forEach></span>
                    </p>
                    <p>
                      <b>主机风格:</b><span class="column_txt"><c:forEach items="${flavors }" var="flav"><c:if test="${ser.flavor.id eq flav.id }">${flav.vcpus }C/${flav.ram }MB/${flav.disk }GB</c:if></c:forEach></span>
                      <b>挂卷:</b><span class="column_txtl">volume_HB , volume_Data</span>
                      <b>状态:</b><span class="column_txt"><img src="img/icons/common/icon_success.png"><em>${ser.status }</em></span>
                    </p>
                  </div>
                </div>
                </c:forEach>
              </div>
              
              <form action="installWasInfo" method="post" id="submits" class="form-horizontal">
              <input type="hidden" id="serId" name="serId" value="${serId }">
              <input type="hidden" id="listSize" name="listSize" value="${fn:length(servers)}">
              
              <div class="mainmodule">
	              <c:forEach items="${servers }" var="ser" varStatus="num">
	              <div class="control-group">
                        <label class="control-label">HOST<c:out  value="${num.count }"/></label>
                        <div class="controls">
	                        <div class="inputb4">
	                        	<input type="hidden" name="wasHostId" value="${ser.id }"/>
	                        	<input class="wasHostIp" type="text" id="wasHostIp${num.count }" name="wasHostIp" value="<c:forEach items="${ser.address}" var="addr">${addr.addr };</c:forEach>" readonly="readonly"/>
	                        </div>
	                        <div class="inputb4">
	                        	<input class="wasHostName" type="text" id="wasHostName${num.count }" name="wasHostName" placeholder="host名称" value="${ser.name }"/>
	                        </div>
	                        <div class="inputb4">
	                        	<select class="w80 wasHostType" id="wasHostType${num.count }" name="wasHostType">
                              	 	<option multiple="" value="stand">stand</option>
                              	 	<option value="cell">cell</option>
	                            </select>
	                        </div>
	                    </div>
                   </div>
                   </c:forEach>
              </div>
              
              <div class="mainmodule">
                  <div class="control-group">
                    <label class="control-label">WAS安装路径</label>
                    <div class="controls">
                      <input type="text" id="wasUrl" name="wasUrl" value="${wasUrl }"/>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label">WAS数据安装路径</label>
                    <div class="controls">
                      <input type="text" id="wasDateUrl" name="wasDateUrl"/>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label">WAS的管理员账号</label>
                    <div class="controls">
                      <input type="text" id="wasAccount" name="wasAccount"/>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label" >WAS的密码</label>
                    <div class="controls">
                      <input type="password" id="wasPss" name="wasPss"/>
                    </div>
                  </div>
              </div>
              
              </form>
            </div>
          </div>
        </div>
      </div>
  </div>
<!--content end-->

<!--footer start-->
  <div class="columnfoot">
    <a class="btn btn-info btn-up" onclick="javascript:history.go(-1);">
      <i class="icon-btn-up"></i>
      <span>上一页</span>
    </a>
    <a class="btn btn-info fr btn-next" onclick="CheckInput();">
      <span>创建</span>
      <i class="icon-btn-next"></i>
    </a>
  </div>   
<!--footer end-->
</body>
</html>
