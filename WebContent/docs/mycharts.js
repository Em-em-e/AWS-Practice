getList('bu3');
getList('brand');
getList('sku_code');

var grid=echarts.init(document.getElementById("grid"));
var planpie=echarts.init(document.getElementById("planpie"));
var u2pie=echarts.init(document.getElementById("u2pie"));
var drmpie=echarts.init(document.getElementById("drmpie"));
	
	var option={
			title:{text:'产品名称',textStyle:{fontSize:'15'},left:'center',top:0,bottom:20},
	    tooltip: {
	        trigger: 'item',
	        formatter: "{a} <br/>{b}: {c} ({d}%)"
	    },
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['45%', '65%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: true,
	                    position: 'center'
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	                {value:100, name:'Plan\nACH 76%'},
	                {value:76, name:'ACH',label:{normal:{show:false}}}
	            ]
	        }
	    ]
	};
	
	planpie.setOption(option);
	u2pie.setOption(option);
	drmpie.setOption(option);
	planpie.setOption({title:{text:'NGix'}});
	
	function getList(type){
		$.ajax({
					type : "POST",
					async : false,
					url : "getSelectList",
					data : {
						type : type
					},
					success : function(data) {
						console.log(data);
						 var optionString = "";
						$.each(eval('('+data+')'),function(index,item){
							 optionString += "<option value=\'"+ item.key +"\'>" + item.value + "</option>";
						});
						 $("#"+type).html(optionString);
		                 $("#"+type).selectpicker('refresh');
					}//end-callback
				});//end-ajax
		}