var basePath=document.getElementById('jobList').getAttribute('data');
$(document).ready(function(){
	var $table = $('#businessTable');
	$table.bootstrapTable({
		url: basePath+"business/businessProvinceList.json", 
		method : 'get',
		dataType: "json",
		pagination: true, //分页
		singleSelect: true,
		clickToSelect : true,
		sidePagination: "server", //服务端处理分页
		      columns: [{title: '',field: 'state',align: 'center',checkbox:"true"},
		                {title: 'MUDID',field: 'mudid',align: 'left',sortable:'true'},
		                {title: '中文名',field: 'cname',align: 'left',sortable:'true'},
		                {title: '英文名',field: 'ename',align: 'left',sortable:'true'},
		                {title: '邮箱',field: 'mail',align: 'center',sortable:'true'},
		                {title: '手机',field: 'tel',align: 'left',sortable:'true'},
		                {title: '职务',field: 'chineseTitle',align: 'left',sortable:'true'},
		                {title: '负责省份',field: 'province',align: 'left',sortable:'true',
		                	formatter:function(value,row,index){
		                		return value.split("-")[1];
		                	}},
		                {title: '大区经理',field: 'remark',align: 'left',sortable:'true'}
		                ]
	  	});
});

function validateAdd() {
if ($.trim($('#mudid').val()) == '') {
	alert('mudid不能为空！');
	$('#mudid').focus();
	return false;
}
return true;
}
function selectP(){
	var proV="";
	var proD="";
	if($("#provinceD").val().length>0){
		proD=$("#provinceD").val().split("-")[0];
		proV=$("#provinceD").val().split("-")[1];
		proV=proV+","+$("#province").find("option:selected").text();
		proD=proD+","+$("#province").val();
	}else{
		proV=$("#province").find("option:selected").text();
		proD=$("#province").val();
	}
	$("#provinceV").val(proV);
	$("#provinceD").val(proD+"-"+proV);
}
function clearP(){
	$("#provinceV").val("");
	$("#provinceD").val("");
}
function add(){
	$("#mudid").val("");
	$('#mudid').attr("disabled",false);
	$("#cname").val("");
	$("#ename").val("");
	$("#mail").val("");
	$("#tel").val("");
	$("#provinceV").val("");
	loadProvince();
	$("#chineseTitle").val("");
	$("#remark").val("0");
	$('#myModal').modal({backdrop: 'static', keyboard: false});
	$('#myModal').modal('show');
	$("#doAdd").show();
	$("#doUpdate").hide();
}

function loadProvince(){
	//加载省份
	$.ajax({
		type : "POST",
		url : basePath+"business/getProvinceCode",
		success : function(data) {
			var p=eval(data);
			var txt='';
			for(var i = 0;i <p.length;i++){
				$("#province").append('<option value=\"'+p[i].codeId+'\">' + p[i].codeName +'</option>');
				txt += '<option value=\"'+p[i].codeId+'\">' + p[i].codeName +'</option>';
			}
			//document.getElementById('province').innerHTML=txt;
			//$("#province").html(txt);
		}
	});//end-ajax
}

function doAdd() {
	if(validateAdd()){
		$.ajax({
			type : "POST",
			dataType : "text",
			url : basePath+"business/add",
			data : $("#businessForm").serialize(),
			success : function(data) {
				if(data=="ok"){
					alert("新增成功");
					$('#myModal').modal('hide');
					$("#businessTable").bootstrapTable("refresh");
				}else{
					alert(data);
				}
			},
		});//end-ajax
	}
}
function del(){
	var sel = $("#businessTable").bootstrapTable("getSelections");
	if(sel.length>0){
		if(!confirm("确认删除？"))
			return;
		$.ajax({
			type : "POST",
			url : basePath+"business/delete",
			data : {id:sel[0].mudid},
			success : function(data) {
				if(data=="ok"){
					alert("删除成功");
					$("#businessTable").bootstrapTable("refresh");
				}else{
					alert(data);
				}
			}
		});
	}else{
		alert("请选择要删除的数据");
	}
}
function update(){
	var sel = $("#businessTable").bootstrapTable("getSelections");
	if(sel.length>0){
		$("#mudid").val(sel[0].mudid);
		$('#mudid').attr("disabled",true);
		$("#cname").val(sel[0].cname);
		$("#ename").val(sel[0].ename);
		$("#mail").val(sel[0].mail);
		$("#tel").val(sel[0].tel);
		loadProvince();
		$("#provinceV").val(sel[0].province.split("-")[1]);
		$("#provinceD").val(sel[0].province);
		$("#chineseTitle").val(sel[0].chineseTitle);
		$("#remark").val(sel[0].remark);
		$("#doAdd").hide();
		$("#doUpdate").show();
		$('#myModal').modal('show');
	}else
		alert("请选择一条数据");
}
function doUpdate(){
	$('#mudid').attr("disabled",false);
	$.ajax({
		type : "POST",
		dataType : "text",
		url : basePath+"business/update",
		data : $("#businessForm").serialize(),
		success : function(data) {
			if(data=="ok"){
				alert("修改成功");
				$('#myModal').modal('hide');
				$("#businessTable").bootstrapTable("refresh");
			}else{
				alert(data);
			}
		},
	});//end-ajax
}
