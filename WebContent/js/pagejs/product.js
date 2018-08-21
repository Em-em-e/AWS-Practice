$(document).ready(function() {
    getproductList("productNameForm");
	getCustomerList("customerNameForm");

});
function addProduct(){
	$('#productForm').attr("action","product/addProduct");
	$("#addProduct").modal("show");
	$("#addProduct").modal({
		  keyboard: false
	});
	$('#productForm')[0].reset();  
	$("#productNoP").removeAttr("readonly");
}
function addProductSubmit(){
	var form=$('#productForm');
	if($("#productNoP").val()==""||$("#productNameP").val()==""||$("#cubeOrQuantityP").val()==""){
		alert("带*号的为必填项，请确保所有必填项目都已填写");return;
	}
	$.ajax({  
        url: form.attr("action"),  
        type: form.attr("mathod"),  
        data: form.serialize(),  
        dataType: "json",  
        success: function (data){  
        	alert("操作成功！");
        	$("#productTable").bootstrapTable('refresh'); 
        	$("#addProduct").modal("hide");
        }  
    });  
}
function editProduct(){
	var row=$("#productTable").bootstrapTable("getSelections");
	console.log(row);
	if(row.length == 1){
		$('#productForm')[0].reset();  
		$('#productForm').attr("action","product/updateProduct");
		var r=row[0];
		$("#productNoP").attr("readonly","readonly");
		$("#productId").val(r.id);
		$("#productNoP").val(r.productNo);
		$("#productNameP").val(r.productName);
		$("#cubeOrQuantityP").val(r.cubeOrQuantity);
		$("#locationP").val(r.location);
		
		$("#addProduct").modal("show");
	}else{
		alert("请选择一条记录！");
	};
}
function changeExportDataProduct(){
	$("#productTable").bootstrapTable('destroy').bootstrapTable({
		exportDataType: $("#inventoryViewtoolbar").val()
	});
}
function deleteProduct(){
	var row=$("#productTable").bootstrapTable("getSelections");
	if(row.length == 1){
		if(!confirm("确定要删除？"))
			return;
		$.ajax({url:'product/deleteProduct',data:{id:row[0].id}});
		$("#productTable").bootstrapTable('refresh');
	}else{
		alert("请选择一条记录！");
	};
}
//function doSearch(){
//	var title=$("#outDate").val();
////	var create_time=$("#outNo").val();
//	var news_type=$("#outNo").val();
//	var productName=$("#productName").val();
//	 $("#outTable").bootstrapTable('refresh', {url: 
//		 'out/list.json?outDateStr='+title+'&outNo='+news_type+'&productName='+productName+'&limit=10&offset=0'});
//}
//
//function doClear(){
//	$("#outDate").val("");
//	$("#outNo").val("");
//	$("#productName").val("");
//}
