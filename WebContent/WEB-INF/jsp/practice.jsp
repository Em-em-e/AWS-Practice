<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/practice.js"></script>
<script type="text/javascript">
	var str="${practice.str}";
	var ans="${practice.ans}";
	var no="${practice.no}";
	var oldAns="${oldAns}";
	var cnOrEn="${cnOrEn}";
	var exp="${practice.exp}";
	$(document).ready(function() {
		var ansList="";
		str=str.replace("A.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;A.");if(str.indexOf("A.")>0)ansList+=",A";
		str=str.replace("A．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;A．");if(str.indexOf("A．")>0)ansList+=",A";
		str=str.replace("B.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;B.");if(str.indexOf("B.")>0)ansList+=",B";
		str=str.replace("B．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;B．");if(str.indexOf("B．")>0)ansList+=",B";
		str=str.replace("C.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;C.");if(str.indexOf("C.")>0)ansList+=",C";
		str=str.replace("C．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;C．");if(str.indexOf("C．")>0)ansList+=",C";
		str=str.replace("D.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;D.");if(str.indexOf("D.")>0)ansList+=",D";
		str=str.replace("D．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;D．");if(str.indexOf("D．")>0)ansList+=",D";
		str=str.replace("E.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;E.");if(str.indexOf("E.")>0)ansList+=",E";
		str=str.replace("E．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;E．");if(str.indexOf("E．")>0)ansList+=",E";
		str=str.replace("F.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;F.");if(str.indexOf("F.")>0)ansList+=",F";
		str=str.replace("F．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;F．");if(str.indexOf("F．")>0)ansList+=",F";
		str=str.replace("G.","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;G.");if(str.indexOf("G.")>0)ansList+=",G";
		str=str.replace("G．","<br><br>&nbsp;&nbsp;&nbsp;&nbsp;G．");if(str.indexOf("G．")>0)ansList+=",G";
		$("#str").html(str);
		var anshtml="你的答案：";
		//选项
		var anss=ansList.substring(1).split(",");
		for(var i=0;i<anss.length;i++){
			if(oldAns!=""&& oldAns.indexOf(anss[i])>=0)
				anshtml+=anss[i]+"<input type='checkbox' checked style='zoom: 350%;' value='"+anss[i]+"'>&nbsp;&nbsp;&nbsp;&nbsp;";
			else
				anshtml+=anss[i]+"<input type='checkbox' style='zoom: 350%;' value='"+anss[i]+"'>&nbsp;&nbsp;&nbsp;&nbsp;";
		}
		console.log(oldAns);
		anshtml+="&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' onclick='showAns()'>确定</button>";
		$("#ansList").html(anshtml);
		$("#next").html("<a href='practice?non=${practice.no-1}&&cnOrEn=${cnOrEn}'>上一题</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
			"<a href='practice?non=${practice.no+1}&&cnOrEn=${cnOrEn}'>下一题</a>");
	});
</script>
<title>AWS Practice</title>
</head>
<body style="width: 100%">
<div style="width: 90%;float: left;padding-top: 5%;padding-left: 4%">
	<h3><a href="page">返回</a></h3>
	<h2>题号:${practice.no} <c:if test="${cnOrEn=='en'}">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="翻译" onclick="translateStr()"/></c:if></h2>
	<h3 id="str"></h3><br>
	<h3 id="ansList"></h3>
	<h3 id="next"></h3>
	<h3 id="rightAns"></h3>
	<h3 id="translateStr"></h3>
	<h3 id="explain"></h3>
	<h3 id="explainCn"></h3>
</div>
</body>
</html>