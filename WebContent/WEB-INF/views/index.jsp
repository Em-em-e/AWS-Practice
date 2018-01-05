<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <META    HTTP-EQUIV="Pragma"    CONTENT="no-cache">    
   <META    HTTP-EQUIV="Cache-Control"    CONTENT="no-cache">    
   <META    HTTP-EQUIV="Expires"    CONTENT="0"> 
<%
	String path = request.getContextPath();
	String port=":" + request.getServerPort();
	if(request.getServerPort()==80)
		port="";
	String basePath = request.getScheme() + "://"+ request.getServerName() +port+ path + "/";
	//basePath=basePath.replaceAll("http", "https");
	request.setAttribute("basePath", basePath);
%>
  <title>demo</title>
  <link rel="stylesheet" href="../dist/amazeui.min.css"/>
  <link rel="stylesheet" href="../docs/demo.css"/>
  <link rel="stylesheet" href="../docs/bootstrap.min.css">
  <link rel="stylesheet" href="../docs/bootstrap-select.min.css">
<link rel="stylesheet" type="text/css" href="../docs/style.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../docs/bootstrap-select.min.js"></script>
</head>
<body style="left: 10%;width: 80%;height: 100%;background: white">
<div>
<div id="echarts-report" style="width: 100%;">
	<div id="head" style="top:15px">
		<div id="title" style="width: 35%;float:left;height: 75px;">
			<strong style="font-size: 35px;color: rgb(255,102,0);margin-left: 10%">Daily Sales report</strong>
		</div>
		<div id="selectItem" style="width: 45%;float: left;height: 75px;padding-top: 5px">
			BU1:
			<select class="selectpicker col-xs-3">
				<option value="Vx">Vx</option>
				<option value="Rx">Rx</option>
			</select>
			BU2:
			<select class="selectpicker col-xs-3">
				<option value="Vx" selected="selected">Vx</option>
				<option value="AIS">AIS</option>
				<option value="Specialty Care">Specialty Care</option>
				<option value="Others">Others</option>
				<option value="Respiratory">Respiratory</option>
				<option value="T&RD">T&RD</option>
			</select>
			BU3:
			<select class="selectpicker col-xs-3" id="bu3">
			</select>
			Brand:
			<select class="selectpicker col-xs-3" id="brand">
				<option value="">Brand</option>
			</select>
			SKU:
			<select class="selectpicker col-xs-3" id="sku_code" onchange="">
				<option value="">SKU</option>
			</select>
		</div>
		<div id="logo" style="float: left;height: 75px;">
			<img alt="" src="">
		</div>
	</div>
	<div id="mid">
		<div style="float: left;width: 50%">
			<div id="timeBar" style="width: 90%;margin-left: 5%">
				<div class="progress">
					<div class="progress-bar" style="width: 65%; background:#005394;">
					</div>
				</div>
			</div>
			<div id="pie">
				<div id="planpie" style="width: 33%;height: 130px;float: left"></div>
				<div id="u2pie" style="width: 33%;height: 130px;float: left"></div>
				<div id="drmpie" style="width: 33%;height: 130px;float: left"></div>
			</div>
		</div>
		<div id="grid" style="float: left;width:50%;height: 400px"></div>
	</div>
	<div id="bottom"></div>
</div>
</div>
<script type="text/javascript" src="../dist/echarts.js"></script>
<script type="text/javascript" src="../docs/mycharts.js"></script>
</body>