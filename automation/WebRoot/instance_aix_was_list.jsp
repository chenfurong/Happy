<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<jsp:include page="header.jsp" flush="true"/>
	<title>自动化部署平台</title>
	<script language="javascript" type="text/javascript">
		//操作
		function checkDB2Select() {
			var infoId = [];
			$("input[name='servers']").each(function() {
				if ($(this).attr("checked")) {
					infoId.push($(this).val());
				}
			});
			if(infoId.length < 1){
				ymPrompt.alert("最少选择1条实例!");
			}else if(infoId.length > 5){
				ymPrompt.alert("暂时只能选择5条实例!");
			}else{
				location.href = "getInstanceDetial?serId="+infoId+"&type=was";
			}
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
    <a class="current">实例一览</a>
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
                      <div class="widget-content">所有实例信息.</div>
                  </div>
              </div>
          </div>
    </div>
  </div>
  
	<div class="container-fluid">
        <div class="row-fluid">
          <div class="span12">
            <div class="columnauto">
              <div class="widget-box nostyle">
                <h5>PowerVC虚拟机列表</h5>
                    <table class="table table-bordered data-table with-check table-hover no-search no-select">
                      <thead>
                          <tr>
                              <th><span class="icon"><input type="checkbox" id="checkedAll" name="checkedAll" /></span></th>
                              <th>主机名</th>
                              <th>IP地址</th>
                              <!-- <th>挂卷</th> -->
                              <th>主机风格</th>
                              <th>镜像</th>
                              <th>状态</th>
                              <th>健康状态</th>
                              <th>操作系统</th>
                          </tr>
                      </thead>
                      <tbody>
                      	<c:forEach items="${servers }" var="ser">
                           <tr>
                               <td>
                               	  <c:if test="${ser.isdisabled == true }">
	                               	<input type="checkbox"  name="servers" value="${ser.id }"  />
	                               </c:if>
                              </td>
                               <td>${ser.name }</td>
                               <td><c:forEach items="${ser.address}" var="addr">${addr.addr }<br/></c:forEach></td>
                               <!-- <td>volume_HB<br/>volume_Data</td> -->
                               <td>
                                  <c:forEach items="${flavors }" var="flav">
                                      <c:if test="${ser.flavor.id eq flav.id }">${flav.name }(${flav.vcpus }C/${flav.ram }MB/${flav.disk }GB)</c:if>
                                  </c:forEach>
                               </td>
                               <td>
                                  <c:forEach items="${imageList }" var="img">
                                      <c:if test="${ser.image.id eq img.id }">${img.name }</c:if>
                                  </c:forEach>
                               </td>
                               <td><img src="img/icons/common/icon_success.png"><b>${ser.status }</b></td>
                               <td>${ser.health_status }</td>
                          	   <td>${ser.operating_system }</td>
                           </tr>
                          </c:forEach>
                      </tbody>
                  </table>
              </div>
            </div>
            <div class="columnfoot">
              <a class="btn btn-info fr btn-next" onclick="checkDB2Select();">
                <span>下一页</span>
                <i class="icon-btn-next"></i>
              </a>
            </div>  
          </div>
        </div>
      </div>
  </div>
</body>
</html>
