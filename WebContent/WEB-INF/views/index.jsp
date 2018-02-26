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
  <title>Daily Sales Report</title>
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
		<div id="title" style="width: 30%;float:left;height: 50px;padding-top: 10px">
			<strong style="float: left;font-size: 4.5vmin;margin-left: 10%;color: rgb(255,102,0)">Daily Sales Report</strong>
		</div>
		<div style="float: left;width: 10%">
			<span id="actDays" style="padding-top: 20px;float: left;font-size: 3vmin;"></span>
		</div>
		<div id="selectItem" style="float: left;padding-top: 24px;width: 50% ">
			<span>BU1:</span>
			<select class="selectpicker col-xs-2" id="bu1" onchange="getBu2(this)">
				<option value="all" selected="selected">All</option>
				<option value="Rx" >Rx</option>
				<option value="Vx">Vx</option>
			</select>
			<span>BU2:</span>
			<select class="selectpicker col-xs-2" onchange="getBu3(this)" id="bu2">
				<option value="">All</option>
			</select>
			<span>BU3:</span>
			<select class="selectpicker col-xs-1" id="bu3" onchange="getBrand(this)">
				<option value="">All</option>
			</select>
			<span>Brand:</span>
			<select class="selectpicker col-xs-1" id="brand" onchange="getSKU(this)">
				<option value="">All</option>
			</select>
			<span>SKU:</span>
			<select class="selectpicker col-xs-2" id="sku_code" onchange="changeSku(this)">
				<option value="">All</option>
			</select>
			
		</div>
		<div style="float: left;margin-top: 1%;width: 5%; ">
			<a id="lastMonth" style="" class="button button-pill button-tiny" onclick="lastMonth()">
				&nbsp;Last&nbsp;Month&nbsp;&nbsp;&nbsp;</a><br>
			<a id="currentMonth" style="margin-top: 5%;background-color: rgb(127,215,167)" onclick="currentMonth()"
				class="button button-pill button-tiny">Current&nbsp;Month</a>
		</div>
	</div>
	<div style="width: 100%;float: left;height: 40px;margin-top: 0px"><hr style="height:3px;border:none;border-top:3px ridge rgb(255,102,0);"></div>
	<div id="mid">
		<div style="float: left;width: 50%;margin-top: 0px">
			<div id="timeBar" style="width: 90%;margin-left: 5%;">
				<div style="width: 100%;text-align: center">
					<span style="font-size: 15px;color: rgb(0,0,0);">Working Days</span>				
				</div>
				<div style="width: 100%;margin-top: 15px;">
					<div class="progress">
						<span class="green" style="width: 0%;"><span>0</span></span>
					</div>
					<div style="margin-top: -15px;color:rgb(89,89,89);">
						<span style="width: 20%;float: left">0<span style="float: right;">&nbsp;</span></span>
						<span style="width: 20%;float: left"><span style="float: right;">&nbsp;</span></span>
						<span style="width: 20%;float: left"><span style="float: right;">&nbsp;</span></span>
						<span style="width: 20%;float: left"><span style="float: right;">&nbsp;</span></span>
						<span style="width: 20%;float: left"><span id="totalWorkDay" style="float: right;">&nbsp;</span></span>
					</div>
				</div>
				<div style="width: 100%;text-align: center;">
					<span style="font-size: 15px;color: rgb(0,0,0);" id="pieTitle"></span>				
				</div>
			</div>
			<div id="pie" style="margin-top: 0px">
				<div id="planpie" class="pieCss" ></div>
				<div id="u2pie" class="pieCss"></div>
				<div id="drmpie" class="pieCss"></div>
			</div>
		</div>
		<!-- <div id="" style=";top: 10%;left: 10%;width:20%;position: relative;"><span>GBP'k</span></div> -->
		<div id="grid" style="float: left;width:50%;height: 315px"></div>
	</div>
	<div id="bottom">
		<div id="powerBrands" style="width: 50%;height: 330px;float: left;margin-left: 0%">
			<div id="brands1" style="width:35%;height: 100%;float: left">
			</div>
			<div id="brands2" style="width:30%;height: 100%;float: left">
			</div>
			<div id="brands3" style="width:30%;height: 100%;float: left">
			</div>
		</div>
		<div id="bar1" style="width: 50%;height: 330px;float: left;"></div>
	</div>
	<br><br/>
</div>
</div>
<script type="text/javascript" src="../dist/echarts.js"></script>
<script type="text/javascript" src="../docs/mycharts.js"></script>
</body>