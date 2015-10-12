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
		function CheckInput() {
			$("#submits").submit();
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
      <a href="#" class="current">Was配置一览</a>
    </div>
    <div class="container-fluid">
      <div class="row-fluid">
            <div class="span12">
              <div class="widget-box collapsible">
                  <div class="widget-title">
                      <a data-toggle="collapse" href="#collapseOne">
                        <span class="icon"><i class="icon-arrow-right"></i></span>
                        <h5>说明：</h5>
                      </a>
                    </div>
                    <div id="collapseOne" class="collapse in">
                        <div class="widget-content">创建Was集群</div>
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
                      <b>资源组：</b><span class="mr10">${asName }</span>
                    </p>
                    <p>
                      <b>主机风格:</b><span class="column_txt"><c:forEach items="${flavors }" var="flav"><c:if test="${ser.flavor.id eq flav.id }">${flav.vcpus }C/${flav.ram }MB/${flav.disk }GB</c:if></c:forEach></span>
                      <b>挂卷:</b><span class="column_txtl">volume_HB , volume_Data</span>
                      <b>状态:</b><span class="column_txt"><img src="img/icons/common/icon_success.png"><em>${ser.status }</em></span>
                      <b>VG信息：</b><span class="mr10">${vgName }</span>
                    </p>
                  </div>
                </div>
                </c:forEach>
              </div>
              <div class="mainmodule">
                <form action="#" method="get" class="form-horizontal">
                  <div class="control-group">
                    <label class="control-label">WAS安装路径</label>
                    <div class="controls">
                      <span class="graytxt">${wasUrl }</span>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label">WAS数据安装路径</label>
                    <div class="controls">
                      <span class="graytxt">${wasDateUrl }</span>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label">WAS的cell节点</label>
                    <div class="controls">
                      <span class="graytxt">${wasCellNode }</span>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label">WAS的管理员账号</label>
                    <div class="controls">
                      <span class="graytxt">${wasAccount }</span>
                    </div>
                  </div>
                  <div class="control-group">
                    <label class="control-label">WAS的密码</label>
                    <div class="controls">
                      <span class="graytxt">${wasPss }</span>
                    </div>
                  </div>
                </form>
              </div>
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
    <a class="btn btn-info fr btn-next">
      <span>创建</span>
      <i class="icon-btn-next"></i>
    </a>
  </div>  
<!--footer end-->
</body>
</html>
