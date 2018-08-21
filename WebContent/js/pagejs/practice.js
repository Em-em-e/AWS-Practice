
function changeLanguage(){
	if($("#changeLan").val()=='en')
		$("#outTable").bootstrapTable('refresh',{url:'listen.json'});
	else {
		$("#outTable").bootstrapTable('refresh',{url:'list.json'});
	}
}

function strlenFormatter(value,row){
	return value?(value.length>20?value.substring(0,20)+"...":value+"..."):"";
}

function showAns(){
	var selectAns="";
	$("input[type='checkbox']:checked").each(function(){
	      selectAns+=","+$(this).val();
	});
	if(selectAns!=""){
		selectAns=selectAns.substring(1);
	}else{
		alert("请选择至少一个选项");
		return;
	}
	$("#rightAns").html("正确答案:"+ans);
	if(exp!="")
		$("#explain").html("解释:"+exp);
	console.log(selectAns);
	$.ajax({
		type:'post',
		url:'ansDetail',
		data:{no:no,selectAns:selectAns,cnOrEn:cnOrEn},
		success:function(data){
		}
	});
	if(exp!="")
		translateS();
}
var apikey="7OSzOyBLS7OtPgiWiUooY3Yunt0uqveR";
var appid="458efd2e32c044cd";
function translateStr() {
	var salt=Math.random().toFixed(1)*10;
	var sign=hex_md5(appid+str+salt+apikey);
	sign=sign.toUpperCase();
	console.log(sign);
	$.ajax({
		type:'get',
		url:'translate',
		data:{q:str,from:'EN',to:'zh_CHS',appKey:appid,salt:salt,sign:sign},
		success:function(data){
			data=eval('('+data+')');
			console.log((data.translation)[0]);
			var strtrans=(data.translation)[0];
			$("#translateStr").html(strtrans.replace(/< br >/g,"<br>"));
		}
	});
}

function translateS() {
	var salt=Math.random().toFixed(1)*10;
	var sign=hex_md5(appid+exp+salt+apikey);
	sign=sign.toUpperCase();
	console.log(sign);
	$.ajax({
		type:'get',
		url:'translate',
		data:{q:exp,from:'EN',to:'zh_CHS',appKey:appid,salt:salt,sign:sign},
		success:function(data){
			data=eval('('+data+')');
			console.log(data);
			var strtrans=(data.translation)[0];
			$("#explainCn").html("翻译："+strtrans.replace(/< br >/g,"<br>"));
		}
	});
}