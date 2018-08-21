$(document).ready(function() {
		//菜单选择效果切换
	    $(".meun-item").click(function() {
	    	//刷新页面对应的table
	    	var pageId=$(this).attr("href");
	    	$.ajax({  
	            url: "isLogin",  
	            type: "get",  
	            dataType: "text",  
	            success: function (data){  
	            	if(data.indexOf("<script")==0){
	            		alert("网页过期，请重新登录！");
	            		window.top.location.href="/warehouse/login.jsp";
	            	}
	            }  
	        });
	    	$(pageId+"Table").bootstrapTable('refresh'); 
	        $(".meun-item").removeClass("meun-item-active");
	        $(this).addClass("meun-item-active");
	        var itmeObj = $(".meun-item").find("img");
	        itmeObj.each(function() {
	            var items = $(this).attr("src");
	            items = items.replace("_grey.png", ".png");
	            items = items.replace(".png", "_grey.png")
	            $(this).attr("src", items);
	        });
	        var attrObj = $(this).find("img").attr("src");
	        attrObj = attrObj.replace("_grey.png", ".png");
	        $(this).find("img").attr("src", attrObj);
	    });
	    $(".toggle-btn").click(function() {
	        $("#leftMeun").toggleClass("show");
	        $("#rightContent").toggleClass("pd0px");
	    });
	    refreshSelect();
});
function refreshSelect(){
	getproductList("productNameForm");
	getCustomerList("customerNameForm","1");
    getproductList("productNameFormIn");
	getCustomerList("customerNameFormIn","2");
	getCustomerList("transporterName","3");
}

function dateFormatter(value,row){
	return value?new Date(value).format("yyyy-MM-dd"):"";
}
function datetimeFormatter(value,row){
	return value?new Date(value).format("yyyy-MM-dd hh:mm:ss"):"";
}

function getproductList(id){
	$.ajax({  
        url: "product/selectList.json",  
        type: 'get',  
        success: function (datas){  
        	var select = $("#"+id);  
        	select.html("<option value=''>请选择</option>");
            for (var i = 0; i < datas.length; i++) {  
                select.append("<option value='"+datas[i].productNo+"'>"  
                        + datas[i].productName + "</option>");  
            }  
            $('.selectpicker').selectpicker('val', '');  
            $('.selectpicker').selectpicker('refresh'); 
        }  
    });  
}
function getCustomerList(id,type){
	if(type==undefined)
		type="";
	$.ajax({  
        url: "customer/selectList.json",  
        type: 'get',  
        data:{customerType:type},
        success: function (datas){  
        	var select = $("#"+id);  
        	select.html("<option value=''>请选择</option>");
            for (var i = 0; i < datas.length; i++) {  
                select.append("<option value='"+datas[i].customerNo+"'>"  
                        + datas[i].customerName + "</option>");  
            }  
            $('#'+id).selectpicker('val', '');  
            $('#'+id).selectpicker('refresh'); 
        }  
    });  
}

