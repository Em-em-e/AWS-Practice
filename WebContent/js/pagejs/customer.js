var m = new Map();
m.set("1", "客户");
m.set("2", "供应商");
m.set("3", "运输队");
function customerType(value,row){
	var arr=value.split(",");
	var str="";
	for(var i=0;i<arr.length;i++){
		str+=m.get(arr[i])+",";
	}
	return str.substring(0,str.length-1);
}
function addCustomer(){
	$('#customerForm').attr("action","customer/addCustomer");
	$("#addCustomer").modal("show");
	$("#addCustomer").modal({
		  keyboard: false
	});
	$('#customerForm')[0].reset();  
	$('#customerForm .selectpicker').selectpicker('val', '1');
}
function addCustomerSubmit(){
	var form=$('#customerForm');
	if($("#customerNoCForm").val()==""||$("#customerTypeC").val()==""||$("#customerNameC").val()==""){
		alert("带*号的为必填项，请确保所有必填项目都已填写");return;
	}
	$.ajax({  
        url: form.attr("action"),  
        type: form.attr("mathod"),  
        data: form.serialize(),  
        dataType: "json",  
        success: function (data){  
        	alert("操作成功！");
        	$("#customerTable").bootstrapTable('refresh'); 
        	$("#addCustomer").modal("hide");
        }  
    });  
}
function editCustomer(){
	var row=$("#customerTable").bootstrapTable("getSelections");
	console.log(row);
	if(row.length == 1){
		$('#customerForm')[0].reset();  
		$('#customerForm').attr("action","customer/updateCustomer");
		var r=row[0];
		$("#customerId").val(r.id);
		$("#customerNoCForm").val(r.customerNo);
		$("#customerTypeC").selectpicker('val',r.customerType.split(","));
		$("#customerNameC").val(r.customerName);
		$("#remarkC").val(r.remark1);
		
		$("#addCustomer").modal("show");
	}else{
		alert("请选择一条记录！");
	};
}
function changeExportDataC(){
	$("#customerTable").bootstrapTable('destroy').bootstrapTable({
		exportDataType: $("#exportDataC").val()
	});
}
function deleteCustomer(){
	var row=$("#customerTable").bootstrapTable("getSelections");
	if(row.length == 1){
		if(!confirm("确定要删除？"))
			return;
		$.ajax({url:'customer/deleteCustomer',data:{id:row[0].id}});
		$("#customerTable").bootstrapTable('refresh');
	}else{
		alert("请选择一条记录！");
	};
}
function doSearch(){
	var title=$("#outDate").val();
//	var create_time=$("#customerNo").val();
	var news_type=$("#customerNo").val();
	var productName=$("#productName").val();
	 $("#customerTable").bootstrapTable('refresh', {url: 
		 'customer/list.json?customerDateStr='+title+'&customerNo='+news_type+'&productName='+productName+'&limit=10&offset=0'});
}

function doClear(){
	$("#customerDate").val("");
	$("#customerNo").val("");
	$("#productName").val("");
}
