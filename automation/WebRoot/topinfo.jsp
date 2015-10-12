<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<a class="treelogo">系统自动化部署平台</a>
<div id="user-nav" class="navbar navbar_inverse">
  <ol class="viewbox">
  	 <li><a href="loginAction">首页</a></li>
     <li>
         <a href="#">自动化部署<i></i></a>
         <div class="viewcolumn">
            <h5 class="viewtit">自动化部署</h5>
            <ol class="viewlist">
              <li><a href="getAllInstance"><b><img src="img/icons/tree/virtual.png"/></b>既有虚机+DB2HA</a></li>
              <!-- <li><a href="getAllInstance?type=was"><b><img src="img/icons/tree/virtual.png"/></b>既有虚机+WAS集群</a></li> -->
            </ol>
      </div>
     </li>
  <li><a href="getLogInfo">历史执行记录</a></li>
  </ol>
  <ul class="nav btn-group">
      <li class="btn btn-help"><a title="用户名" ><i class="icon icon-user"></i> <span class="text">用户名：<b>${accessBean.userName }</b></span></a></li>
      <li class="btn btn-help"><a title="退出" href="loginOut"><i class="icon icon-share-alt"></i> <span class="text">退出</span></a></li>
  </ul>
</div>
<div class="clearfix"></div>
