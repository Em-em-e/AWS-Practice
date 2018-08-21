<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <META    HTTP-EQUIV="Pragma"    CONTENT="no-cache">    
   <META    HTTP-EQUIV="Cache-Control"    CONTENT="no-cache">    
   <META    HTTP-EQUIV="Expires"    CONTENT="0"> 
   <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'>
<meta name='apple-mobile-web-app-capable' content='yes'>
<meta name='apple-mobile-web-app-status-bar-style' content='black'>
<meta name='format-detection' content='telephone=no'>
<%
	String path = request.getContextPath();
	String port=":" + request.getServerPort();
	if(request.getServerPort()==80)
		port="";
	String basePath = request.getScheme() + "://"+ request.getServerName() +port+ path + "/";
	//basePath=basePath.replaceAll("http", "https");
	request.setAttribute("basePath", basePath);
%>
  <title>Vx LSP Portal</title>
  <link rel="stylesheet" href="../docs/bootstrap.min.css">
  <link rel="stylesheet" href="../portal/style.css">
  <link rel="stylesheet" href="../docs/bootstrap-select.min.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../docs/bootstrap-select.min.js"></script>

</head>
<body style="font-family: 'sans-serif'">
	<div id="window" >
		<!-- ==================左侧==================== -->
		<div style="float: left;width: 73%;height: 100%;color: rgb(255,255,255)">
			<div id="map" style="position: absolute;top: 0%;left: 0%;width: 73%;height: 100%;z-index: 0">
				
			</div>
			<div style="position: absolute;top: 0%;left: 0%;width: 73%;height: 20%;padding-top: 2%;padding-left: 3%">
				<img alt="" src="../portal/images/logo.png" style="width: 6%;height: 45%;float: left">
				<strong style="float: left;font-size: 6vmin;font-family: '楷体';margin-left: 1.5%;">Vx LSP Portal</strong>
			</div>
			<div style="position: absolute;top: 12%;left: 4%;width: 15%;height: 3%">
				<div id="show_time" style="font-size: 1.6vmin;color: RGB(93, 245, 244)">  
					<script>  
					setInterval("show_time.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);  
					</script>  
				</div>  
			</div>
			<div style="position: absolute;top: 18%;left: 9%;width: 50%;height: 20%;z-index: 0">
				<div style="float: left;width: 33%;z-index: 1">
					<div>
						<span style="font-size: 2.6vmin;">MTD Sales(Dec.)</span>
					</div>
					<div>
						<span style="font-size: 5vmin;color: #FFF600">42.04</span><span style="font-size: 3vmin;">million</span>
					</div>
				</div>
				<div style="float: left;width: 30%;z-index: 1">
					<div>
						<span style="font-size: 2.6vmin;">YTD Sales(2017)</span>
					</div>
					<div>
						<span style="font-size: 5vmin;color: #FFF600">705.13</span><span style="font-size: 3vmin;">million</span>
					</div>
				</div>
				<div style="float: left;width: 20%;z-index: 1">
					<div>
						<span style="font-size: 2.6vmin;">Active CDCs</span>
					</div>
					<div>
						<span style="font-size: 5vmin;color: #FFF600">1216</span><span style="font-size: 3vmin;"></span>
					</div>
				</div>
			</div>
				<div id="engerix" style="position: absolute;bottom: 1.6%;left: 1.8%;width: 28%;height: 30%;z-index: 1;">
				</div>
			
		</div>
		<!-- ==================右侧==================== -->
		<div style="float: left;width: 27%;height: 95%;margin-top:2%">
			<div id="chart1" style="width: 100%;height: 33%;"></div>
			<div id="chart2" style="width: 100%;height: 33%;"></div>
			<div id="chart3" style="width: 100%;height: 33%;"></div>
		</div>
	</div>
<script type="text/javascript" src="../portal/pointData.js"></script>
	<script type="text/javascript" src="../dist/echarts.min.js"></script>
<script type="text/javascript" src="../js/echarts3map/echarts3_map_js/china.js"></script>
<script type="text/javascript" src="../portal/portal.js"></script>
</body>