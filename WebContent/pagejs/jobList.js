var basePath=document.getElementById('jobList').getAttribute('data');
$(document).ready(function(){
	var $table = $('#jobTable');
	$table.bootstrapTable({
		url: basePath+"quartz/list.json", 
		method : 'get',
		dataType: "json",
		pagination: true, //分页
		singleSelect: true,
		clickToSelect : true,
		sidePagination: "server", //服务端处理分页
		      columns: [{title: '',field: 'state',align: 'center',checkbox:"true"},
		                {title: 'name',field: 'jobname',align: 'left',sortable:'true'},
		                {title: 'group',field: 'jobgroup',align: 'left',sortable:'true'},
		                {title: '状态',field: 'jobstatus',align: 'left',sortable:'true',
		                	formatter:function(value,row,index){
		                		if(value=="1"){return "运行中";}else{return "停止";}
		                	}
		                },
		                {title: 'cron表达式',field: 'cronexpression',align: 'center',sortable:'true'},
		                {title: '描述',field: 'description',align: 'left',sortable:'true'},
		                {title: '是否并发',field: 'isconcurrent',align: 'left',sortable:'true'},
		                {title: '类路径',field: 'beanclass',align: 'left',sortable:'true'},
		                {title: 'SpringId',field: 'springid',align: 'left',sortable:'true'},
		                {title: '方法名',field: 'methodname',align: 'left',sortable:'true'}
		                ]
	  	});
});


function validateAdd() {
if ($.trim($('#jobname').val()) == '') {
	alert('name不能为空！');
	$('#jobname').focus();
	return false;
}
if ($.trim($('#jobgroup').val()) == '') {
	alert('group不能为空！');
	$('#jobgroup').focus();
	return false;
}
if ($.trim($('#cronexpression').val()) == '') {
	alert('cron表达式不能为空！');
	$('#cronexpression').focus();
	return false;
}
if ($.trim($('#beanclass').val()) == '' && $.trim($('#springid').val()) == '') {
	$('#beanclass').focus();
	alert('类路径和spring id至少填写一个');
	return false;
}
if ($.trim($('#methodname').val()) == '') {
	$('#methodname').focus();
	alert('方法名不能为空！');
	return false;
}
return true;
}
function add() {
if (validateAdd()) {
	$.ajax({
		type : "POST",
		url : basePath+"quartz/add",
		data : $("#addForm").serialize(),
		success : function(data) {
			if (data.flag) {
				$("#jobTable").bootstrapTable("refresh");
			} else {
				alert(data.msg);
			}

		}//end-callback
	});//end-ajax
}
}

function changeJobStatus(status) {
	var sel = $("#jobTable").bootstrapTable("getSelections");
	var op="";
	if(sel.length<=0){
		alert("请选择一条数据");
		return;
	}
	if(sel[0].jobstatus!=status){
		alert("任务状态未更改");
		return;
	}
	$.ajax({
		type : "POST",
		url : basePath+"quartz/changeJobStatus",
		data : {
			jobId : sel[0].jobid,
			cmd : status==0?"start":"stop"
		},
		success : function(data) {
			if (data=="ok") {
				$("#jobTable").bootstrapTable("refresh");
			} else {
				alert(data);
			}

		}
	});
}
function updateCron() {
	var sel = $("#jobTable").bootstrapTable("getSelections");
	if(sel.length<=0){
		alert("请选择一条数据");
		return;
	}
	var cron = prompt("输入cron表达式！", "")
	if (cron) {
		$.ajax({
			type : "POST",
			async : false,
			url : basePath+"quartz/updateCron",
			data : {
				jobId : sel[0].jobid,
				cron : cron
			},
			success : function(data) {
				if (data=="ok") {
					$("#jobTable").bootstrapTable("refresh");
				} else {
					alert(data);
				}

			}//end-callback
		});//end-ajax
	}

}

function runNow(){
	var sel = $("#jobTable").bootstrapTable("getSelections");
	if(sel.length<=0){
		alert("请选择一条数据");
		return;
	}
	$.ajax({
		type : "POST",
		async : false,
		url : basePath+"quartz/runNow",
		data : {
			jobId : sel[0].jobid,
		},
		success : function(data) {
			if (data=="ok") {
				alert("执行成功");
				$("#jobTable").bootstrapTable("refresh");
			} else {
				alert(data);
			}

		}//end-callback
	});//end-ajax
}
