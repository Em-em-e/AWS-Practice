function addOut(){
    getproductList("productNameForm");
	getCustomerList("customerNameForm","1");
	$('#outForm').attr("action","out/addOut");
	$("#addOut").modal("show");
	$("#addOut").modal({
		  keyboard: false
	});
	$('#outForm')[0].reset();$("#productQuantity").text("");
	$("#outDateForm").val(new Date().format("yyyy-MM-dd"));
	$("#outNoForm").val("S-"+new Date().format("yyMMdd-hhmmss"));
	$("#productNameForm").removeAttr("readonly");
	$('#outForm .selectpicker').selectpicker('val', '');
	$("#quantity").removeAttr("readonly");
	$("#productNameForm").attr("disabled",false);
}
function getProductQuantity(){
	var productNo=$("#productNameForm").val();
	$.ajax({  
        url: "product/getProductQuantity",  
        type: "get",  
        data: {productNo:productNo},
        dataType: "json",  
        success: function (data){  
        	$("#productQuantity").text(""+data);
        }  
    });
}
function addOutSubmit(){	
	var form=$('#outForm');
	if($("#outDateForm").val()==""||$("#outNoForm").val()==""||$("#customerNameForm").val()==""||$("#productNameForm").val()==""
	||$("#quantity").val()==""||$("#salesTransport").val()==""||$("#purchasePrice").val()==""||$("#purchaseNo").val()==""){
		alert("带*号的为必填项，请确保所有必填项目都已填写");return;
	}
	if($('#outForm').attr("action").indexOf("add")>0)
	if(Number($("#productQuantity").text())!=undefined||!isNaN(Number($("#productQuantity").text()))){
		var pq=Number($("#productQuantity").text());
		if(pq<Number($("#quantity").val())){
			alert("库存不够！");return;}
	}
	$("#productNameForm").attr("disabled",false);
	$.ajax({  
        url: form.attr("action"),  
        type: form.attr("mathod"),  
        data: form.serialize(),  
        dataType: "json",  
        success: function (data){  
        	alert("操作成功！");
        	$("#outTable").bootstrapTable('refresh'); 
        	$("#addOut").modal("hide");
        }  
    });  
}
function editOut(){
	var row=$("#outTable").bootstrapTable("getSelections");
	if(row.length == 1){
		$('#outForm')[0].reset();
		$('#outForm').attr("action","out/updateOut");
		var r=row[0];
		$("#outId").val(r.id);
		var time=new Date(r.outDate);
		$("#outDateForm").val(r.outDate?time.format("yyyy-MM-dd"):"-");
		$("#outNoForm").val(r.outNo);
		$("#customerNameForm").selectpicker('val',r.customerNo);
		$("#length").val(r.length);
		$("#width").val(r.width);
		$("#height").val(r.height);
		$("#productNameForm").selectpicker('val',r.productNo);
		$("#productNameForm").attr("disabled",true);
		getProductQuantity();
		$("#purchaseNo").val(r.purchaseNo);
		$("#quantity").val(r.quantity);
		$("#quantity").attr("readonly","readonly");
		$("#cubeOut").val(r.cube);
		$("#salesPrice").val(r.salesPrice);
		$("#salesAmount").val(r.salesAmount);
		$("#salesTransport").val(r.salesTransport);
		$("#purchasePrice").val(r.purchasePrice);
		$("#purchaseAmount").val(r.purchaseAmount);
		$("#profit").val(r.profit);
		$("#remark").val(r.remark);
		
		$("#addOut").modal("show");
	}else{
		alert("请选择一条记录！");
	};
}
function changeExportData(){
	$("#outTable").bootstrapTable('destroy').bootstrapTable({
		exportDataType: $("#exportData").val()
	});
}
function deleteOut(){
	var row=$("#outTable").bootstrapTable("getSelections");
	if(row.length == 1){
		if(!confirm("确定要删除？"))
			return;
		$.ajax({url:'out/deleteOut',data:{id:row[0].id}});
		$("#outTable").bootstrapTable('refresh');
	}else{
		alert("请选择一条记录！");
	};
}
function doSearch(){
	var title=$("#outDate").val();
//	var create_time=$("#outNo").val();
	var news_type=$("#outNo").val();
	var productName=$("#productName").val();
	 $("#outTable").bootstrapTable('refresh', {url: 
		 'out/list.json?outDateStr='+title+'&outNo='+news_type+'&productName='+productName+'&limit=10&offset=0'});
}

function doClear(){
	$("#outDate").val("");
	$("#outNo").val("");
	$("#productName").val("");
}
function selectCard(){
	var row=$("#outTable").bootstrapTable("getSelections");
	if(row.length > 0){
		$("#customerNameBill").val("");
		$("#bankCardSelect").selectpicker('val',"1");
		$("#billBefor").modal("show");
	}else{
		alert("请选择至少一条记录！");
	};
}
function generateBill(){
	var row=$("#outTable").bootstrapTable("getSelections");
	if(row.length > 0){
		if($("#customerNameBill").val()==""){
			alert("请输入客户名称！");
			return;
		}
		//清除页面信息
		$("#billTable").html("<tr><th>送货日期</th><th>出仓单号</th><th>采购单号</th><th>物料号</th><th>长" +
				"</th><th>宽</th><th>厚</th><th>货品名称</th><th>数量</th><th>单价（元）</th><th>金额</th><th>备注</th></tr>" +
				"<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>"+
				"<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>合计</td><td>&nbsp;</td><td>&nbsp;</td><td id='amountSum'>&nbsp;</td><td>&nbsp;</td></tr>");
		var amountSum=0;
		for(var i=0;i<row.length;i++){
			var r=row[i];
			var d=new Date(r.outDate);
			$("#billTable tr").eq(0).after("<tr>" +
					"<td>"+d.format("yyyy-MM-dd")+"</td>" +
					"<td>"+r.outNo+"</td>" +
					"<td>"+(r.purchaseNo==undefined?"&nbsp;":r.purchaseNo)+"</td>" +
					"<td>"+r.productNo+"</td>" +
					"<td>"+(r.length==undefined?"&nbsp;":r.length)+"</td>" +
					"<td>"+(r.width==undefined?"&nbsp;":r.width)+"</td>" +
					"<td>"+(r.height==undefined?"&nbsp;":r.height)+"</td>" +
					"<td>"+r.productName+"</td>" +
					"<td>"+r.quantity+"</td>" +
					"<td>"+r.salesPrice+"</td>" +
					"<td>"+r.salesAmount+"</td>" +
					"<td>"+(r.remark==undefined?"&nbsp;":r.remark)+"</td>" +"</tr>");
			amountSum+=r.salesAmount;
		}
		$("#billCoustomerName").text($("#customerNameBill").val());
		$("#nowDate").text(new Date().format("yyyy/MM/dd"));
		$("#amountSum").text(amountSum);
		if($("#bankCardSelect").val()=="1"){
			$("#cardDetial").html("开户名称：佛山市天海木业有限公司<br>"+
            		"开户账号：*******0001<br>"+
            		"开户银行：中国邮政储蓄银行股份有限公司佛山市世纺城支行<br>"+
            		"联系人:李先生  139****** 固话：01556***** 传真：*******<br>");
		}else{
			$("#cardDetial").html("开户名称：佛山市天海木业有限公司<br>"+
            		"开户账号：*******0002<br>"+
            		"开户银行：中国邮政储蓄银行股份有限公司佛山市世纺城支行<br>"+
            		"联系人:李先生  139****** 固话：01556***** 传真：*******<br>");
		}
		$("#bill").modal("show");
	}else{
		alert("请选择至少一条记录！");
	};
}
function printBill(){
	$.print("#printDiv");
}
//自动计算
function calculate(){
	var quantity=$("#quantity").val();
	var price=$("#salesPrice").val();
	if(quantity==undefined||isNaN(quantity))
		quantity=0;
	if(price==undefined||isNaN(price))
		price=0;
	$("#salesAmount").val(quantity*price);
	var purchasePrice=$("#purchasePrice").val();
	if(purchasePrice==undefined||isNaN(purchasePrice))
		purchasePrice=0;
	$("#purchaseAmount").val(quantity*purchasePrice);
	var salesAmount=$("#salesAmount").val();
	var salesTransport=$("#salesTransport").val();
	var purchaseAmount=$("#purchaseAmount").val();
	if(salesAmount==undefined||isNaN(salesAmount))
		salesAmount=0;
	if(salesTransport==undefined||isNaN(salesTransport))
		salesTransport=0;
	if(purchaseAmount==undefined||isNaN(purchaseAmount))
		purchaseAmount=0;
	$("#profit").val(salesAmount-salesTransport-purchaseAmount);
	
	var lengthIn=$("#length").val();
	var widthIn=$("#width").val();
	var heightIn=$("#height").val();
	if(lengthIn==undefined||isNaN(lengthIn))
		lengthIn=0;
	if(widthIn==undefined||isNaN(widthIn))
		widthIn=0;
	if(heightIn==undefined||isNaN(heightIn))
		heightIn=0;
	$("#cubeOut").val(lengthIn*widthIn*heightIn);
}