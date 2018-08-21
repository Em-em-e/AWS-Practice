var basePath=document.getElementById('templateList').getAttribute('data');
$(document).ready(function(){
	var $table = $('#templateTable');
	$table.bootstrapTable({
		url: basePath+"template/templateList.json", 
		method : 'get',
		dataType: "json",
		timeout: 5000,
		pagination: true, //分页
		singleSelect: true,
		clickToSelect : true,
		sidePagination: "server", //服务端处理分页
		      columns: [{title: '',field: 'state',align: 'center',checkbox:"true"},
		                {title: '消息类型',field: 'msgType',align: 'left',sortable:'true'},
		                //{title: '接收用户',field: 'toUser',align: 'left',sortable:'true'},
		                {title: '消息标题',field: 'title',align: 'left',sortable:'true'},
		                {title: '消息描述',field: 'description',align: 'center',sortable:'true'},
		                {title: '图片路径',field: 'picurl',align: 'left',sortable:'true'},
		                {title: '消息模板',field: 'template',align: 'left',sortable:'true',formatter:function(value,row,index){
		                	return value==""?"-":"<a href='javascript:;' onclick='viewTemplate("+row.id+")'>查看</a>";
		                }}
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
function update() {
var sel = $("#templateTable").bootstrapTable("getSelections");
if(sel.length<=0){
	alert("请选择一条数据");
	return;
}
$("#tempId").val(sel[0].id);
$("#msgType").text(sel[0].msgType);
$("#toUser").val(sel[0].toUser);
$("#title").val(sel[0].title);
$("#description").val(sel[0].description);
$("#picurl").val(sel[0].picurl);
$('#myModal').modal({backdrop: 'static', keyboard: false});
$('#myModal').modal("show");

}
function doUpdate(){
var sel = $("#templateTable").bootstrapTable("getSelections");
$.ajax({  
    url : basePath+"template/update",  
    type : 'post',  
    data : $("#updateTemplateForm").serialize(),
    success : function(data){
    	if(data=='ok'){
    		alert("修改成功");
    		$('#myModal').modal("hide");
			$("#templateTable").bootstrapTable("refresh");
    	}
    }  
});  
}
function viewTemplate(id){
var rows=$("#templateTable").bootstrapTable('getData');
var row;
for(var i=0;i<rows.length;i++){
	if(rows[i].id==id)
		row=rows[i];
}
$("#tempV").val(row.template);
//document.getElementById('tempV').innerHTML=row.template;   
$('#templateViewModal').modal("show");
}
function updateTemplate(){
var sel = $("#templateTable").bootstrapTable("getSelections");
if(sel.length<=0){
	alert("请选择一条数据");
	return;
}
$("#temp").val(sel[0].template);
console.log(sel[0].template);
//document.getElementById('temp').innerHTML=sel[0].template;
$('#templateUpdateModal').modal({backdrop: 'static', keyboard: false});
$('#templateUpdateModal').modal("show");

}
function doUpdateTemplate(){
var sel = $("#templateTable").bootstrapTable("getSelections");
console.log($("#temp").val());
$.ajax({  
    url : basePath+"template/updateTemplate",  
    type : 'post',  
    data:{id:sel[0].id,
		template : $("#temp").val()},
    success : function(data){
    	if(data=='ok'){
    		alert("修改成功");
    		$('#templateUpdateModal').modal("hide");
			$("#templateTable").bootstrapTable("refresh");
    	}else{
    		alert(data);
    	}
    }  
});  
}