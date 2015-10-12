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
			//验证
			var infoId = [];
			$("input[name='servers']").each(function() {
				console.log(1);
				if ($(this).attr("checked")) {
					console.log(2);
					infoId.push($(this).val());
				}
			});
			
			console.log(infoId);
			
			
			if(infoId.length >= 0 && infoId.length < 2){
				ymPrompt.alert("请选择两条实例!");
			}else if (infoId.length > 2 ) {
				ymPrompt.alert("一次只能选择两条实例!");
			}else{
				location.href = "getInstanceDetial?serId="+infoId;
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
    <!-- <a href="getAllInstance" title="既有虚机+DB2HA" class="tip-bottom"><i class="icon-home"></i>既有虚机+DB2HA</a> -->
    <a href="loginAction" class="current"><i class="icon-home"></i>实例一览</a>
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
                <h5 style="weight:200px;float:left">PowerVC虚拟机列表</h5>
                <h5 style="weight:200px;float:right">共有 ${total } 台虚拟机</h5>
                    <table class="table table-bordered data-table with-check table-hover no-search no-select" >
                      <thead>
                          <tr >
                          	  <th style="text-align:center;">序号</th>
                              <th style="text-align:center;">虚机名</th>
                              <th style="text-align:center;">IP地址</th>
                              <!-- <th>挂卷</th> -->
                              <th style="text-align:center;">主机配置</th>
                              <th style="text-align:center;">镜像</th>
                              <th style="text-align:center;">状态</th>
                              <th style="text-align:center;">健康状态</th>
                              <th style="text-align:center;">操作系统</th>
                          </tr>
                      </thead>
                      <tbody>
                      <%int i=1;%>
                      	<c:forEach items="${servers }" var="ser">
                           <tr>
                           	   <td style="text-align:center;"><%=i++ %></td>	
                               <td style="text-align:center;">${ser.name }</td>
                               <td style="text-align:center;" name="addr"><c:forEach items="${ser.address}" var="addr">${addr.addr }<br/></c:forEach></td>
                               <!-- <td>volume_HB<br/>volume_Data</td> -->
                               <td style="text-align:center;">
                                  <c:forEach items="${flavors }" var="flav">
                                      <c:if test="${ser.flavor.id eq flav.id }">${flav.vcpus }C/${flav.ram }MB/${flav.disk }GB</c:if>
                                  </c:forEach>
                               </td>
                               <td style="text-align:center;">
                                  <c:forEach items="${imageList }" var="img">
                                      <c:if test="${ser.image.id eq img.id }">${img.name }</c:if>
                                  </c:forEach>
                               </td>
                               <td style="text-align:center;" name="state"><img src="img/icons/common/icon_success.png"><b>${ser.status }</b></td>
                          	   <td style="text-align:center;" name="hs">${ser.health_status }</td>
                          	   <td style="text-align:center;" name="os">${ser.operating_system }</td>
                           </tr>
                          </c:forEach>
                      </tbody>
                  </table>
              </div>
            </div>
            <div class="columnfoot">
<!--               <a class="btn btn-info fr btn-next" onclick="checkDB2Select();">
                <span>下一页</span>
                <i class="icon-btn-next"></i>
              </a> -->
            </div>  
          </div>
        </div>
      </div>
  </div>
</body>
</html>
