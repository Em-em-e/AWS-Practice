	getList('bu2','');
	//默认加载Rx数据
	var productName="All";
	var grid=echarts.init(document.getElementById("grid"));
	var planpie=echarts.init(document.getElementById("planpie"));
	var u2pie=echarts.init(document.getElementById("u2pie"));
	var drmpie=echarts.init(document.getElementById("drmpie"));
	var bar1=echarts.init(document.getElementById("bar1"));
	var brands1=echarts.init(document.getElementById("brands1"));
	var brands2=echarts.init(document.getElementById("brands2"));
	var brands3=echarts.init(document.getElementById("brands3"));
	var powerBrandsOption = {
		    title: {
		        text: 'Power\nBrands',textStyle:{align:'left',fontSize:'15',fontWeight:'normal'},top:'-2%'
		    },
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'shadow'
		        },
		        formatter:function(params, ticket, callback){
                	return params[0].name+":"+params[0].data+"%";
                },
		    },
//		    legend: {
//		        data: ['2011年', '2012年']
//		    },
		    grid: {
		    	top:'10%',
		        left: '2%',
		        right: '0%',
		        bottom: '5%',
		        containLabel: true
		    },
		    xAxis: {
		        show:false,
		        type: 'value',min:0,max:'auto',
		    },
		    yAxis: [{
		        type : 'category',
		        axisLine: {show: true,lineStyle:{color:'rgb(0,0,0)'}},
		        axisLabel: {show: true},//,fontWeight:'bold'
		        axisTick: {show: true},
		        splitLine: {show: false},
		        position:'left',inverse:true,
		        data : []
		    },{
		    	name:'                          Act vs LY',inverse:true,nameLocation:'start',
		    	nameTextStyle:{align:'right',fontSize:'15',fontWeight:'normal'},
		        type : 'category',position:'left'
		    }],
		    series: [
		        {
		            type: 'bar',
		            label: {
		                normal: {
		                    show: true,
		                    formatter:function(params, ticket, callback){
		                    	return params.data+"%";
		                    },
		                    fontSize:9,
		                    color:'rgb(0,0,0)',position:'right'
		                }
		            },z:100,
		            stack:'1',barWidth:'55%',
		            data: [],
		            itemStyle:{normal:{color: function (params){
                        var colorList = ['rgb(255,90,2)','rgb(201,82,2)','rgb(255,201,2)','rgb(255,255,2)',
                        	'rgb(201,82,2)','rgb(2,185,84)','rgb(153,217,84)','rgb(2,191,211)','rgb(199,2,126)'];
                        return colorList[params.dataIndex%9];
                    }}
                    }
		        },
		        {
		            type: 'bar',
		            z:99,
		            stack:'1',barWidth:'55%',
		            data: [],
		            itemStyle:{normal:{color: 'rgb(233, 233, 233)'}
                    }
		        }
		    ]
		};
	brands1.setOption(powerBrandsOption);
	brands2.setOption(powerBrandsOption);
	brands2.setOption({title:{show:false},grid:{left:'-20%',right:'0%'},yAxis: [{show:false},
		{name:'                            Act vs Plan'}]});
	brands3.setOption(powerBrandsOption);
	brands3.setOption({title:{show:false},grid:{left:'-20%',right:'0%'},yAxis: [{show:false},
		{name:'                            Act vs DRM'}]});
	var gridOption={
		title:{text:'Cumulative Monthly Sales Trend',textStyle:{fontSize:'15',fontWeight:'normal',color:'rgb(0,0,0)'},left:'center',top:-5},
		legend:{
			top:295,left:'center',
			z:100,
			textStyle:{},
			data:[{name:'Act'},{name:'Plan'},{name:'U2'},{name:'FC1'}],
			selected: {
			    'U2': false,
			    'FC1': false
			}
		},
		grid:{left:45,right:10,top:30,bottom:40},
		tooltip: {
	        trigger: 'axis',
	        formatter: function(params, ticket, callback){
	        	if(params.length>1 && params[0].data!=undefined)
	        		return params[0].seriesName+":"+params[0].data+"<br>"+params[1].seriesName+":"+params[1].data;
	        	else {
					return params[0].seriesName+":"+params[0].data;
				}
	        }
	    },
	    xAxis: {
	        type: 'category',axisLabel:{fontSize:10},boundaryGap:false,
	        axisTick:{alignWithLabel:true,interval:0},
	        data: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31]
	    },
	    yAxis: {
	    	name:'CNY\'k',nameTextStyle:{fontSize:10,backgroundColor:'rgb(127,224,171)'},splitNumber:6,type: 'value',axisLabel:{fontSize:10},
	    	triggerEvent:true
	    },
	    series: [
	        {
	            name:'Act',
	            type:'line',showAllSymbol:true,
	            itemStyle:{normal:{
	            	color:'rgb(255,51,0)',
	            	opacity:0
	            },emphasis:{
	            	opacity:0.8
	            }},
	            data:[]
	        },
	        {
	            name:'Plan',
	            type:'line',
	            itemStyle:{normal:{
	            	color:'rgb(79,129,189)',
	            	opacity:0
	            },emphasis:{
	            	opacity:0.8
	            }},lineStyle:{color:'blue'},
	            data:[]
	        },{
	            name:'U2',
	            type:'line',
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                }
	            },lineStyle:{color:'green'},
	            data:[]
	        },{
	            name:'FC1',
	            type:'line',
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                }
	            },lineStyle:{color:'red'},
	            data:[]
	        }
	    ]
	};

	var pieOption={
//			title:{text:'Rx',textStyle:{fontSize:'14',fontWeight:'normal',color:'rgb(89,89,89)'}
//				,left:'center',top:10,bottom:10},
	    series: [
	        {
	            name:productName,
	            type:'pie',
	            radius: ['60%', '90%'],
	            center: ['50%','50%'],
	            itemStyle:{normal:{color:'rgb(0,176,80)'}},
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                	fontSize:17,
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
	            	{value:75, name:'ACT',label:{normal:{show:false}}},
	                {value:25, name:'U2\nACH ...',itemStyle:{normal:{color:'rgba(0,176,80,0.5)',fontSize:20}}}
	            ]
	        }
	    ]
	};
	var bar1Option={
			legend:{
				left:'center',bottom:'5%',
				z:100,
				textStyle:{},
				data:[{name:'Value',icon:'roundRect'},{name:'Volumn',icon:'roundRect'}],
				selected: {
				    'Volumn': false
				}
			},
			grid:{left:'1%',width:'98%',top:'6%'},
			tooltip: {
		        trigger: 'item',
		        formatter: function(params, ticket, callback){
		        	if("MTD Act"==params.name){
		        		var op=bar1.getOption().series;
		        		var data;
		        		if(params.seriesName=="Value")
		        			data=op[0].data;
		        		else
							data=op[1].data;
		        		var str= params.seriesName+"<br>"+params.name+":<br>";
		        		if(data[2]!=0)
		        			str+="Plan Ach%:"+Math.round(params.data/data[2]*100)+"%<br>";
		        		if(data[3]!=0)
		        			str+="ACH Ach%:"+Math.round(params.data/data[3]*100)+"%<br>";
		        		if(data[4]!=0)
		        			str+="FC1 Ach%:"+Math.round(params.data/data[4]*100)+"%<br>";
		        		if(data[5]!=0)
		        			str+="LY Ach%:"+Math.round(params.data/data[5]*100)+"%<br>";
		        		if(data[6]!=0)
		        			str+="DRM Ach%:"+Math.round(params.data/data[6]*100)+"%<br>";
		        		return str;
		        	}else {
						return params.seriesName+"<br>"+params.name+":"+params.data;
					}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        data: ['Today\'s','MTD Act','Plan','U2','FC1','LY','DRM']
		    },
		    yAxis: [{
		        type: 'value',
		        splitLine:{show:false},
		        axisLine:{show:false},
		        axisTick:{show:false},
		        axisLabel:{show:false}
		    }],
		    series: [
		        {
		            name:'Value',
		            type:'bar',
		            barWidth:'30%',
		            itemStyle:{normal:{color:'rgb(228,108,10)'}},
		            label: {
		                normal: {
		                    show: true,
		                    position: 'top'
		                }
		            },
		            data:[23,30,40,43,50,89,45],
		            
		        },
		        {
		            name:'Volumn',
		            type:'bar',
		            barWidth:'30%',
		            itemStyle:{normal:{color:'rgb(228,108,10)'}},
		            label: {
		                normal: {
		                    show: true,
		                    position: 'top'
		                }
		            },
		            data:[23,30,40,43,50,89,45],
		            
		        }
		    ]
	};
	
	grid.setOption(gridOption);
	planpie.setOption(pieOption);
	u2pie.setOption(pieOption);
	drmpie.setOption(pieOption);
	bar1.setOption(bar1Option);
	//级联获取下拉列表
	function getList(type,query){
		$.ajax({
					type : "GET",
					async : false,
					url : "getSelectList",
					data : {
						type : type,
						query:query
					},
					success : function(data) {
						 var optionString = "<option value='' selected='selected'>All</option>";
						$.each(eval('('+data+')'),function(index,item){
							 optionString += "<option value=\'"+ item.key +"\'>" + item.value + "</option>";
						});
						 $("#"+type).html(optionString);
		                 $("#"+type).selectpicker('refresh');
					}//end-callback
				});//end-ajax
		}
	//刷新数据
	function refreshData(obj){
//		if(""!=$(obj).val()){
			$("#pieTitle").text(productName);
			if(!(Object.prototype.toString.call(obj) === "[object String]")){
				var bottonL=$("#lastMonth");
				var url="currentMonthAllData";
				if(bottonL.css("background-color")=="rgb(127, 215, 167)"){//选中状态
					url="lastMonthAllData";
				}
				obj=url;
			}
			setTimeout(function(){
				var bu1=$("#bu1").val();var bu2=$("#bu2").val();var bu3=$("#bu3").val();
				var brand=$("#brand").val();var sku_code=$("#sku_code").val();
				$.ajax({
					type : "GET",
					async : false,
					url : 'currentMonthAllData',
					data:{bu1:bu1,bu2:bu2,bu3:bu3,brand:brand,sku_code:sku_code,month:obj},
					success : function(data) {
						if('timeout'==data){
							window.location='../../../web/static/errorpage/error-session-timeout.jsp';
						}else{
							alldata=eval('('+data+')');
							//工作日
							var day=parseInt(alldata[0][0]);
							$(".green").css("width",(day/alldata[0][1]*100)+"%");
							$(".green span").text(day+"days "+Math.round(day/alldata[0][1]*100)+"%");
							$("#totalWorkDay").text(alldata[0][1]+"");
							$("#actDays").text(alldata[5]);
							//pie
							getPieData(alldata[1]);
							getGridData(alldata[2]);
							getBarData(alldata[3]);
							getPowerBrandData(alldata[4]);
						}
					}
				});
			},1);
//		}
	}
	
	function getPowerBrandData(rate){
//		rate=eval('('+data+')');
		brands1.setOption({yAxis:[{data:rate[0]}],series:[{data:rate[1],stack:'1'},{data:rate[4],stack:'1'}]});
		brands2.setOption({yAxis:[{data:rate[0]}],series:[{data:rate[2],stack:'1'},{data:rate[5],stack:'1'}]});
		brands3.setOption({yAxis:[{data:rate[0]}],series:[{data:rate[3],stack:'1'},{data:rate[6],stack:'1'}]});
	}
	function getGridData(rate){
//		rate=eval('('+rate+')');
		grid.setOption({series:[{name:'Act',data:rate[0]}]});
		grid.setOption({series:[{name:'Plan',data:rate[1]}]});
	}
	function getBarData(rate){
//		rate=eval('('+rate+')');
		bar1.setOption({
			series: [
		        {
		            name:'Value',
		            data:rate[0]
		        },{
		            name:'Volumn',
		            data:rate[1]
		        }
		    ]
		});
	}
	function getPieData(rate){
//		rate=eval('('+rate+')');
		var data;
		for(var i=0;i<rate.length;i++){
			if("N/A"==rate[i].value)
				data=[
					{value:0, name:'ACT',label:{normal:{show:false}}},
					{value:100, name:rate[i].key.toUpperCase()+'\n '+rate[i].value+'%',itemStyle:{normal:{color:'rgba(0,176,80,0.5)'}}}
					]
			else
				data=[
	            	{value:rate[i].value, name:'ACT',label:{normal:{show:false}}},
	                {value:(100-rate[i].value), name:(rate[i].key.toUpperCase()+'\nACH '+rate[i].value+'%'),itemStyle:{normal:{color:'rgba(0,176,80,0.5)'}}}
	            ]
			 var pie=echarts.getInstanceByDom(document.getElementById(rate[i].key+"pie"));
			pie.setOption({
				series:[{data:data}]
			});
		}
	}
	var str="<option value=''>All</option>";
	function getBu2(obj){
		getList('bu2',$("#bu1").val());
		$("#bu3").html(str);$("#bu3").selectpicker('refresh');
		$("#brand").html(str);$("#brand").selectpicker('refresh');
		$("#sku_code").html(str);$("#sku_code").selectpicker('refresh');
		var name=$("#bu1").find("option:selected").text();
		productName=name;
		refreshData(obj);
	}
	function getBu3(obj){
		getList('bu3',$("#bu2").val());
		$("#brand").html(str);$("#brand").selectpicker('refresh');
		$("#sku_code").html(str);$("#sku_code").selectpicker('refresh');
		if(productName!=$("#bu2").val()){
			if(""!=$("#bu2").val())
				productName=$("#bu2").val();
			else {
				productName=$("#bu1").find("option:selected").text();
			}
			refreshData(obj);
		}
	}
	function getBrand(obj){
		getList('brand',$("#bu3").val());
		$("#sku_code").html(str);$("#sku_code").selectpicker('refresh');
		if(productName!=$("#bu3").val()){
			if(""!=$("#bu3").val())
				productName=$("#bu3").val();
			else
				productName=$("#bu2").val();
			refreshData(obj);
		}
	}
	function getSKU(obj){
		getList('sku_code',$("#brand").val());
		var pro=$("#brand").val();
		if(""!=pro)
			productName=pro;
		else {
			productName=$("#bu3").val();
		}
		refreshData(obj);
	}
	function changeSku(obj){
		var pro=$("#sku_code").find("option:selected").text();
		if(""!=$("#sku_code").val())
			productName=pro;
		else
			productName=$("#brand").val();
		refreshData(obj);
	}
	
	refreshData();
	window.onresize = function(){
		grid.resize();
		planpie.resize();
		u2pie.resize();
		drmpie.resize();
		bar1.resize();
		brands1.resize();
		brands2.resize();
		brands3.resize();
	};
	
	grid.on('click', function (params) {
        if(!params.value){
        	if(params.targetType="axisName"){
        		var gridSeries=grid.getOption().series;
        		var barSeries=bar1.getOption().series;
        		var yAxis=grid.getOption().yAxis;
        		if(yAxis[0].name=="CNY\'k"){
        			for(var i=0;i<gridSeries.length;i++){
        				var data=gridSeries[i].data;
        				for(var j=0;j<data.length;j++){
        					data[j]=(data[j]/9).toFixed(2);
        				}
        				gridSeries[i].data=data;
        			}
        			for(var i=0;i<barSeries.length;i++){
        				var data=barSeries[i].data;
        				for(var j=0;j<data.length;j++){
        					data[j]=(data[j]/9).toFixed(2);
        				}
        				barSeries[i].data=data;
        			}
        			grid.setOption({yAxis:[{name:'GBP\'k'}],series:gridSeries});
        			bar1.setOption({series:barSeries});
        		}else{
        			for(var i=0;i<gridSeries.length;i++){
        				var data=gridSeries[i].data;
        				for(var j=0;j<data.length;j++){
        					data[j]=(data[j]*9).toFixed(0);
        				}
        				gridSeries[i].data=data;
        			}
        			for(var i=0;i<barSeries.length;i++){
        				var data=barSeries[i].data;
        				for(var j=0;j<data.length;j++){
        					data[j]=(data[j]*9).toFixed(0);
        				}
        				barSeries[i].data=data;
        			}
        			grid.setOption({yAxis:[{name:'CNY\'k'}],series:gridSeries});
        			bar1.setOption({series:barSeries});
        		}
        	}
        }
    });  
	
	function lastMonth(){
		var bottonL=$("#lastMonth");
		var bottonC=$("#currentMonth");
		if(bottonL.css("background-color")!="rgb(127, 215, 167)"){//选中状态
			bottonL.css("background-color","rgb(127, 215, 167)");
			bottonC.css("background-color","rgb(238, 238, 238)");
			refreshData('lastMonthAllData');
		}
	}
	function currentMonth(){
		var bottonL=$("#lastMonth");
		var bottonC=$("#currentMonth");
		if(bottonC.css("background-color")!="rgb(127, 215, 167)"){//选中状态
			bottonC.css("background-color","rgb(127, 215, 167)");
			bottonL.css("background-color","rgb(238, 238, 238)");
			refreshData('currentMonthAllData');
		}
	}
	