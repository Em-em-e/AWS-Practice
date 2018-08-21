var userType=new Map();
userType.set("1","管理员");
userType.set("2","操作员");
function userTypeFormatter(value,row){
	return userType.get(value);
}
function checkUsername(){
	var username=$("#userNameU").val();
	console.log(username);
	if(username!="")
	$.ajax({  
        url: "user/isenable",  
        type: "get",  
        data: {uname:username},
        dataType: "json",  
        success: function (data){  
        	if(data=="1"){
        		$("#isenableUsername").text("用户名已存在！请重新输入");
        		$("#isenableUsername").css("color","red");
        	}else {
        		$("#isenableUsername").text("用户名可用");
        		$("#isenableUsername").css("color","green");
			}
        }  
    });  
}
function checkPassword(){
	if($("#passwordU2").val()!=""&&$("#passwordU1").val()!="")
	if($("#passwordU1").val()!=$("#passwordU2").val()){
		$("#passwordError").text("两次输入的密码不一致，请重新输入");
		$("#passwordError").css("color","red");
	}else{
		$("#passwordError").text("密码正确");
		$("#passwordError").css("color","green");
	}
}
function addUser(){
	$('#userForm').attr("action","user/addUser");
	$("#addUser").modal("show");
	$("#addUser").modal({
		  keyboard: false
	});
	$('#userForm')[0].reset();  
	$('#userForm .selectpicker').selectpicker('val', '2');
	$("#isenableUsername").text("");
	$("#passwordError").text("");
}
function addUserSubmit(){
	var form=$('#userForm');
	if($("#userTypeU").val()==""||$("#userNameU").val()==""||$("#nameU").val()==""||$("#passwordU2").val()==""||$("#passwordU1").val()==""){
		alert("带*号的为必填项，请确保所有必填项目都已填写");return;
	}
	if($("#isenableUsername").text()=="用户名已存在！请重新输入"){
		alert("用户名已存在！请重新输入");return;
	}
	if($("#passwordU1").val()!=$("#passwordU2").val()){
		alert("两次输入的密码不一致，请重新输入");return;
	}
	$.ajax({  
        url: form.attr("action"),  
        type: form.attr("mathod"),  
        data: form.serialize(),  
        dataType: "json",  
        success: function (data){  
        	alert("注册成功！");
        	$("#addUser").modal("hide");
        	$("#userTable").bootstrapTable('refresh'); 
        }  
    });  
}
function editUser(){
	var row=$("#userTable").bootstrapTable("getSelections");
	if(row.length == 1){
		$("#isenableUsername").text("");
		$("#passwordError").text("");
		$('#userForm')[0].reset();  
		$('#userForm').attr("action","user/updateUser");
		var r=row[0];
		$("#userId").val(r.id);
		$("#userTypeU").selectpicker('val',r.usertype);
		$("#userNameU").val(r.username);
		$("#passwordU1").val(r.password);
		$("#passwordU2").val(r.password);
		$("#nameU").val(r.name);
		$("#remarkU").val(r.remark1);
		
		$("#addUser").modal("show");
	}else{
		alert("请选择一条记录！");
	};
}
function deleteUser(){
	var row=$("#userTable").bootstrapTable("getSelections");
	if(row.length == 1){
		if(!confirm("确定要删除？"))
			return;
		$.ajax({url:'user/deleteUser',data:{id:row[0].id},success:function(data){
			$("#userTable").bootstrapTable('refresh');
		}});
	}else{
		alert("请选择一条记录！");
	};
}
function doSearchUser(){
	var userTypeQuery=$("#userTypeQuery").val();
	var usernameQuery=$("#usernameQuery").val();
	var nameQuery=$("#nameQuery").val();
	 $("#userTable").bootstrapTable('refresh', {url: 
		 'user/list.json?usertype='+userTypeQuery+'&username='+usernameQuery+'&name='+nameQuery+'&limit=10&offset=0'});
}

function doClearUser(){
	$("#userTypeQuery").selectpicker('val',"");
	$("#usernameQuery").val("");
	$("#nameQuery").val("");
}
