<%@page import="warehouse.model.SysUser"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="ch">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<title>天海木业仓库管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/flat-ui.min.css" />
<link href="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/js/bootstrap/bootstrap-select.min.css" rel="stylesheet" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jQuery.print.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table-zh-CN.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>

<script src="${pageContext.request.contextPath}/js/bootstrap/bootstrap-table-export.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap/tableExport.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/js/pagejs/index.js"></script>
</head>
<body>
	<div id="wrap" style="width: 100%">
		<!-- 左侧菜单栏目块 -->
		<div class="leftMeun" id="leftMeun" style="width: 14%">
			<div id="logoDiv">
				<p id="logoP" style="font-size: 1.2em;">
					<img id="logo" alt="仓库管理系统"
						src="${pageContext.request.contextPath}/images/logo.png"><span>仓库管理系统</span>
				</p>
			</div>
			<div id="personInfor">
				<p id="userName">你好：${sessionScope.loginedUser.name}</p>
				<p>
					上次登录时间：<span><fmt:formatDate
							value="${sessionScope.loginedUser.lastLoginTime}"
							pattern="MM月dd日  HH:mm" /></span>
				</p>
				<p>
					<a href="loginout">退出登录</a>
				</p>
			</div>
			<div class="meun-title">出入库管理</div>
			<div class="meun-item meun-item-active" href="#out"
				aria-controls="user" role="tab" data-toggle="tab">
				<img src="${pageContext.request.contextPath}/images/icon_user_grey.png">销售订单
			</div>
			<div class="meun-item" href="#in" aria-controls="chan" role="tab"
				data-toggle="tab">
				<img src="${pageContext.request.contextPath}/images/icon_change_grey.png">采购入库
			</div>
			<c:if test="${loginedUser.username =='admin'}">
				<div class="meun-title">库存管理</div>
				<div class="meun-item" href="#product" aria-controls="scho" role="tab"
					data-toggle="tab">
					<img src="${pageContext.request.contextPath}/images/icon_house_grey.png">产品库存
				</div>
				<div class="meun-item" href="#report" aria-controls="scho" role="tab"
					data-toggle="tab">
					<img src="${pageContext.request.contextPath}/images/icon_char_grey.png">客户对账
				</div>
				<div class="meun-title">主数据管理</div>
				<div class="meun-item" href="#customer" aria-controls="stud" role="tab"
					data-toggle="tab">
					<img
						src="${pageContext.request.contextPath}/images/icon_card_grey.png">客户管理
				</div>
				<div class="meun-item" href="#user" aria-controls="import"
					role="tab" data-toggle="tab">
					<img
						src="${pageContext.request.contextPath}/images/icon_chara_grey.png">用户管理
				</div>
			</c:if>

		</div>
		<!-- 右侧具体内容栏目 -->
		<div id="rightContent" style="width: 80%!important;">
			<a class="toggle-btn" id="nimei"> <i
				class="glyphicon glyphicon-align-justify"></i>
			</a>
			<!-- Tab panes -->
			<div class="tab-content">
				<%@include file="sysUser.jsp"%>
			</div>
		</div>
	</div>

</body>

</html>