$(document).ready(function() {
    getproductList("productNameFormIn");
	getCustomerList("customerNameFormIn","2");
	getCustomerList("transporterName","3");
});

function getProductQuantityIn(){
	var productNo=$("#productNameFormIn").val();
	$.ajax({  
        url: "product/getProductQuantity",  
        type: "get",  
        data: {productNo:productNo},
        dataType: "json",  
        success: function (data){  
        	$("#productQuantityIn").text(""+data);
        }  
    });
}
function addIn(){
	$('#inForm').attr("action","in/addIn");
	$("#addIn").modal("show");
	$("#addIn").modal({
		  keyboard: false
	});
	$('#inForm')[0].reset();$("#productQuantityIn").text("");
	$("#inDateStrForm").val(new Date().format("yyyy-MM-dd"));
	$("#inNumberForm").val("P-"+new Date().format("yyMMdd-hhmmss"));
	$('.selectpicker').selectpicker('val', '');
	$("#quantityIn").removeAttr("readonly");
	$("#productNameFormIn").attr("disabled",false);
}
function addInSubmit(){
	var form=$('#inForm');
	if($("#inDateStrForm").val()==""||$("#inNumberForm").val()==""||$("#customerNameFormIn").val()==""||$("#productNameFormIn").val()==""
		||$("#quantityIn").val()==""||$("#purchasePriceIn").val()==""){
			alert("带*号的为必填项，请确保所有必填项目都已填写");return;
		}
	$("#productNameFormIn").attr("disabled",false);
	$.ajax({  
        url: form.attr("action"),  
        type: form.attr("mathod"),  
        data: form.serialize(),  
        dataType: "json",  
        success: function (data){  
        	alert("操作成功！");
        	$("#inTable").bootstrapTable('refresh'); 
        	$("#addIn").modal("hide");
        }  
    });  
}
function editIn(){
	var row=$("#inTable").bootstrapTable("getSelections");
	console.log(row);
	if(row.length == 1){
		$('#inForm')[0].reset(); 
		$('#inForm').attr("action","in/updateIn");
		var r=row[0];
		$("#inId").val(r.id);
		$("#inNumberForm").val(r.inNumber);
		var time=new Date(r.inDate);
		$("#inDateStrForm").val(r.inDate?time.format("yyyy-MM-dd"):"-");
		$("#customerNameFormIn").selectpicker('val',r.supplierNo);
		$("#lengthIn").val(r.length);
		$("#cubeIn").val(r.cube);
		$("#widthIn").val(r.width);
		$("#heightIn").val(r.height);
		$("#productNameFormIn").selectpicker('val',r.productNo);
		$("#productNameFormIn").attr("disabled",true);
		$("#transporterName").selectpicker('val',r.transporterNo);
		getProductQuantityIn();
		$("#quantityIn").val(r.quantity);
		$("#quantityIn").attr("readonly","readonly");
		$("#purchasePriceIn").val(r.purchasePrice);
		$("#purchaseAmountIn").val(r.purchaseAmount);
		$("#transCubePrice").val(r.transCubePrice);
		$("#transAmount").val(r.transAmount);
		$("#transPrice").val(r.transPrice);
		$("#purchaseCostPrice").val(r.purchaseCostPrice);
		$("#remarkIn").val(r.remark);
		
		$("#addIn").modal("show");
	}else{
		alert("请选择一条记录！");
	};
}
function changeExportDataIn(){
	$("#inTable").bootstrapTable('destroy').bootstrapTable({
		exportDataType: $("#exportDataIn").val()
	});
}
function deleteIn(){
	var row=$("#inTable").bootstrapTable("getSelections");
	if(row.length == 1){
		if(!confirm("确定要删除？"))
			return;
		$.ajax({url:'in/deleteIn',data:{id:row[0].id}});
		$("#inTable").bootstrapTable('refresh');
	}else{
		alert("请选择一条记录！");
	};
}
function doSearchIn(){
	var title=$("#inDateStr").val();
//	var create_time=$("#inNumber").val();
	var news_type=$("#inNumber").val();
	 $("#inTable").bootstrapTable('refresh', {url: 
		 'in/list.json?inDateStr='+title+'&inNumber='+news_type+'&limit=10&offset=0'});
}

function doClearIn(){
	$("#inDate").val("");
	$("#inNumber").val("");
	$("#inProductName").val("");
}

function calculateIn(){
	var quantity=$("#quantityIn").val();
	var purchasePrice=$("#purchasePriceIn").val();
	
	if(quantity==undefined||isNaN(quantity))
		quantity=0;
	if(purchasePrice==undefined||isNaN(purchasePrice))
		purchasePrice=0;
	$("#purchaseAmountIn").val(quantity*purchasePrice);
	
	var lengthIn=$("#lengthIn").val();
	var widthIn=$("#widthIn").val();
	var heightIn=$("#heightIn").val();
	if(lengthIn==undefined||isNaN(lengthIn))
		lengthIn=0;
	if(widthIn==undefined||isNaN(widthIn))
		widthIn=0;
	if(heightIn==undefined||isNaN(heightIn))
		heightIn=0;
	$("#cubeIn").val(lengthIn*widthIn*heightIn);
	
	var transCubePrice=$("#transCubePrice").val();
	if(transCubePrice==undefined||isNaN(transCubePrice))
		transCubePrice=0;
	$("#transAmount").val(Number($("#cubeIn").val())*transCubePrice);
	$("#transPrice").val((Number($("#transAmount").val())/quantity).toFixed(2));
	$("#purchaseCostPrice").val(""+(parseFloat(purchasePrice)+Number($("#transPrice").val())));
}

