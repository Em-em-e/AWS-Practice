<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/practice.js"></script>
<script type="text/javascript">
	var path="${pageContext.request.contextPath}";
	$(document).ready(function() {
		$("#outTable").on("check.bs.table", function (e, row, ele,field) { 
			var lan=$("#changeLan").val();
			window.location.href=path+"/aws/practice?non="+row.no+"&&cnOrEn="+lan;
		});
	});
</script>
<title>AWS Practice</title>
</head>
<body style="width: 100%">
<div style="width: 90%;height20%;padding-left:5%;padding-top: 5%;text-align: center;">
	<form action="">
		<a href="practice?cnOrEn=cn" style="font-size: 30px">中文练习</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="practice?cnOrEn=en" style="font-size: 30px">英文练习</a>&nbsp;&nbsp;&nbsp;&nbsp;
	</form>
</div>
<div style="width:90%;height:60%;padding-top: 5%;padding-left: 5%">
	<table  id="outTable" data-toggle="table" style="white-space:nowrap;font-size: 20px;"
                                    data-url="list.json"
                                    data-toolbar="#toolbar"
                                    data-click-to-select="true"
                                    data-checkbox="true"
                                    data-show-columns="true" data-single-select="true"
                                    data-data-type="json"
                                    data-show-refresh="true"
                                    data-show-toggle="true"
                                    data-sort-name="opTime"
                                    data-page-list="[10, 30, 50]"
                                    data-page-size="10"
                                 	data-side-pagination="server"
                                    data-pagination="true" data-show-pagination-switch="true">
                                <span id="toolbar" style="display: inline-block;right: 0px">
                                 <select class="form-control" onchange="changeLanguage()" id="changeLan">
                                    <option value="cn">中文</option>
                                    <option value="en">英文</option>
                                 </select>
                                </span>
                                <thead>
                                     <tr>
                                        <th  data-checkbox="true"  data-select-item-name="选中" ></th>
                                        <th data-field="no" >题号</th>
                                        <th data-field="str" data-formatter="strlenFormatter">题目内容</th>
                                        <th data-field="ans" >答案</th>
                                        <th data-field="exp" data-formatter="strlenFormatter">解释</th>
                                        <th data-field="type" >知识点</th>
                                     </tr>
                                 </thead>
                             </table>
                             </div>
</body>
</html>