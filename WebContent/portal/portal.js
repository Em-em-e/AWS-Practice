autodivheight();
function autodivheight(){ //函数：获取尺寸
    //获取浏览器窗口高度
    var winHeight=0;
    if (window.innerHeight)
        winHeight = window.innerHeight;
    else if ((document.body) && (document.body.clientHeight))
        winHeight = document.body.clientHeight;
    //通过深入Document内部对body进行检测，获取浏览器窗口高度
    if (document.documentElement && document.documentElement.clientHeight)
        winHeight = document.documentElement.clientHeight;
    //DIV高度为浏览器窗口的高度
    //document.getElementById("test").style.height= winHeight +"px";
    //DIV高度为浏览器窗口高度的一半
    document.getElementById("window").style.height= winHeight +"px";
    if(map){
    	map.resize();
    	chart1.resize();
    	chart2.resize();
    	chart3.resize();engerix.resize();
    }
}
window.onresize=autodivheight; //浏览器窗口发生变化时同时变化DIV高度


var map=echarts.init(document.getElementById("map"));
var chart1=echarts.init(document.getElementById("chart1"));
var chart2=echarts.init(document.getElementById("chart2"));
var chart3=echarts.init(document.getElementById("chart3"));
var engerix=echarts.init(document.getElementById("engerix"));
var warehouse=[{"lat": 40.1311,"lng": 116.5941,"value": 5000000,"type": 1,"info": "CN Vx - 3PL Kyuan Beijing"},
	{
    "lat": 38.1843,
    "lng": 114.5827,
    "value": 5,
    "type": 1,
    "info": "CN Vx - 3PL Sino Lerentang"
  },
	  {"lat": 28.6906,"lng": 115.9668,"value": 5000000,"type": 1,"info": "CN Vx - 3PL Jiangxi Pharm"},
	  {
	    "lat": 23.1812,
	    "lng": 113.414,
	    "value": 5000000,
	    "type": 1,
	    "info": "CN Vx - 3PL Sino Guangzhou"
	  },
	  {
	    "lat": 38.1843,
	    "lng": 114.5827,
	    "value": 5000000,
	    "type": 1,
	    "info": "CN Vx - 3PL Sino Lerentang"
	  },
	  {
	    "lat": 30.4862,
	    "lng": 114.5351,
	    "value": 5000000,
	    "type": 1,
	    "info": "CN Vx - 3PL Sino Hubei"
	  },
	  {
	    "lat": 29.5018,
	    "lng": 106.44,
	    "value": 5000000,
	    "type": 1,
	    "info": "CN Vx - 3PL Chongqing Pharm"
	  },
	  {
	    "lat": 22.7522,
	    "lng": 108.2917,
	    "value": 5000000,
	    "type": 1,
	    "info": "CN Vx - 3PL Guangxi Pharm"
	  },
	  {
	    "lat": 30.6224,
	    "lng": 103.9827,
	    "value": 5000000,
	    "type": 1,
	    "info": "CN Vx - 3PL Sino Xinan"
	  }];
function convertWarehouse(warehouseData){
	var wData=[];
	for(var i=0;i<warehouseData.length;i++){
		var d=warehouseData[i];
		var point={name:d.info,value:[d.lng,d.lat]};
		wData.push(point);
	}
	return wData;
}

	var convertData = function (data) {
		console.log(data);
	    var res = [];
	    for (var i = 0; i < data.length; i++) {
	        var geoCoord = geoCoordMap[data[i].name];
	        if (geoCoord) {
	            res.push({
	                name: data[i].name,
	                value: geoCoord.concat(data[i].value)
	            });
	        }
	    }
	    console.log(res);
	    return res;
	};
var mapOption={
		
	    backgroundColor: 'rgba(1,39,124,0)',
	    tooltip: {
	        trigger: 'item',
	        formatter: function (params) {
	            return params.seriesName+"<br>"+params.name;
	        }
	    },
	    visualMap: {
	    	right:'10%',bottom:'25%',
	    	min:0,
	    	max:600000,
	        calculable: true,
	        inRange: {
	            color: ['rgb(63,75,85)', '#eac736', '#d94e5d']
	        },
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    geo: {
	        map: 'china',
	        roam:true,
	        zoom:1.1,aspectScale:0.85,
	        center:[100.97, 38.71],
	        label: {
	            emphasis: {
	                show: false
	            }
	        },
	        itemStyle: {
	            normal: {
	                areaColor: 'rgb(35,125,210)',
	                borderColor: '#111'
	            },
	            emphasis: {
	                areaColor: 'rgb(24,136,240)'
	            }
	        }
	    },
	    series: [
	        {
	            name: 'Amount owed',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: convertpointData(pointData),
	            symbolSize: function (val) {
	            		return (val[2]+186700)/50000;
	            },
	            label: {
	                normal: {
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	                emphasis: {
	                    borderColor: '#fff',
	                    borderWidth: 1
	                }
	            }
	        },{
	            name: 'Top 5',
	            type: 'effectScatter',
	            coordinateSystem: 'geo',
	            data: convertpointData(pointData).sort(function (a, b) {
	                return b.value[2] - a.value[2];
	            }).slice(0, 6),
	            symbolSize: function (val) {
            		return val[2] / 45000;
	            },
	            showEffectOn: 'render',
	            rippleEffect: {
	                brushType: 'stroke'
	            },
	            hoverAnimation: true,
	            itemStyle: {
	                normal: {
	                    color: '#f4e925',
	                    shadowBlur: 10,
	                    shadowColor: '#333'
	                }
	            },
	            zlevel: 1
	        },{
	            name: 'Warehouse',
	            type: 'scatter',
	            coordinateSystem: 'geo',
	            data: convertWarehouse(warehouse),
	            symbol:'image://../portal/images/warehouse.png',
	            symbolSize:30,
	            zlevel: 2
	            
	        }
	    ]
};
var engerixData=[
	{name:'CN Vx - 3PL Kyuan Beijing',value:[[37,450,37,900,33,960,23,151,23,151,23,151],
		[39285,37385,39425,39702.05513,16551.11027,-6599.834602],
		[0.947739815,0.901902838,0.951117276,1.714921588,0.714921588,-0.285078412]]},
	{name:'CN Vx - 3PL Sino Guangzhou',value:[[17720,17920,16064,18090.57658,18090.57658,18090.57658],
		[19798,27878,37814,19723.42342,1632.846843,-16457.72973],
		[0.535660173,0.754274892,1.023106061,1.090259525,0.090259525,-0.909740475]]},
	{name:'CN Vx - 3PL Jiangxi Pharm',value:[[3000,3030,2715,3072.709398,3072.709398,3072.709398],
		[5745,2715,0,-274.7093976,-3347.418795,-6420.128193],
		[1.37584194,0.650202066,0,-0.089402987,-1.089402987,-2.089402987]]},
	{name:'CN Vx - 3PL Sino Lerentang',value:[[5010,5070,4529,3226.344867,3226.344867,3226.344867],
		[9599,4529,0,-3226.344867,-6452.689735,-9679.034602],
		[1.783413456,0.841450103,0,-1,-2,-3]]},
	{name:'CN Vx - 3PL Sino Hubei',value:[[7070,7160,6420,5185.197108,5185.197108,5185.197108],
		[20650,13580,6420,0,-969.1971084,-6154.394217],[2.175057931,1.43037708,0.676216558,0,-0.186916155,-1.186916155]]},
	{name:'CN Vx - 3PL Sino Xinan',value:[[4140,4180,3750,4839.517301,4839.517301,4839.517301],[16750,12570,8820,9219.482699,4379.965398,-459.5519036],
		[2.264201953,1.699165287,1.192254402,1.905041789,0.905041789,-0.094958211]]},
	{name:'CN Vx - 3PL Hefei Pharm',value:[[11740,11880,10640,5684.512386,5684.512386,5684.512386],
		[5890,4130,3610,10789.48761,5104.975229,-579.5371566],[0.662728551,0.464697609,0.406188467,1.898049803,0.898049803,-0.101950197]]},
	{name:'CN Vx - 3PL Chongqing Pharm',value:[[5090,5150,4618,3111.118265,3111.118265,3111.118265],[9768,4618,0,291.8817349,-2819.23653,-5930.354795
],[1.831013637,0.865645063,0,0.093818913,-0.906181087,-1.906181087]]},
	{name:'CN Vx - 3PL Guangxi Pharm',value:[[4040,4090,3670,3840.886747,3840.886747,3840.886747],[3600,4598,7283,3442.113253,-398.773494,-4239.660241
],[0.398505604,0.508980213,0.806198976,0.896176711,-0.103823289,-1.103823289]]}
];
map.on('click', function (params) {
    var opt=engerix.getOption();
    if(params.seriesName=='Warehouse'){
    	var wdata=[];
    	if(params.name!=opt.title[0].subtext){
    		if("CN Vx - 3PL Kyuan Beijing"==params.name){
    			engerix.setOption(engerixOption);
    		}else{
    			for(var i=0;i<engerixData.length;i++){
    				if(params.name==engerixData[i].name){
    					wdata.push(engerixData[i].value);
    				}
    			}
    			engerix.setOption({
    				title:{subtext:params.name},
    				series : [
    					{
    						name:'Prediction',
    						data:wdata[0][0]
    					},
    					{
    						name:'Inventory',
    						data:wdata[0][1]
    					},
    					{
    						name:'CDC',
    						data:wdata[0][2]
    					}]
    			});
    		}
    	}
    }
});

map.setOption(mapOption);

var pieOption = {
		title:{text:'Sales By Product',
			textStyle:{color:'rgb(255,255,255)'}
		},
//	    tooltip: {
//	        trigger: 'item',
//	        formatter: "{a} <br/>{b}: {c} ({d}%)"
//	    },
	    lengend:{left:'center',top:'10%',data:[{name:'Cervarix'},{name:'Rabipur'},{name:'Engerix'}]},
	    visualMap: {
	    	type:'piecewise',
	    	right:'center',bottom:0,
	        pieces:[{value:43064,label:'Cervarix'},
	        	{value:4435,label:'Rabipur'},
	        	{value:29676,label:'Engerix'}],
	        calculable: true,orient:'horizontal',
	        inRange: {
	            color: ['#50a3ba', '#eac736', '#d94e5d']
	        },
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    series: [
	        {
	            name:'Cervarix',
	            type:'pie',
	            selectedMode: 'single',
	            radius: [0, '40%'],

	            label: {
	                normal: {
	                	show: false
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            data:[
	            	{value:4435, name:'Rabipur'},
	                {value:43064, name:'Cervarix'},
	                {value:29676, name:'Engerix'}
	            ]
	        },
	        {
	            name:'Engerix',
	            type:'pie',
	            radius: ['50%', '75%'],
	            label: {
	                normal: {
	                    color:'rgb(255,255,255)',
	                    formatter: "{b}:{c}"
	                }
	            },
	            data:[
	            	{value:4435, name:'Rabipur'},
	            	{value:43064, name:'Cervarix'},
	                {value:29676, name:'Engerix'}
	            ]
	        },
	        {
	            name:'Rabipur',
	            type:'pie',
	            radius: ['50%', '75%'],
	            label: {
	                normal: {
	                    color:'rgb(255,255,255)',
	                    formatter: "{b}:{c}"
	                }
	            }
	        }
	    ]
	};
chart1.setOption(pieOption);
	var chart3Option = {
			title:{text:'Top10 Province',
				textStyle:{color:'rgb(255,255,255)'}
			},
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		        left: '0%',
		        right: '4%',
		        bottom: '5%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : [{value:'广东', textStyle:{color:'white',fontSize:10}},
		            	{value:'浙江', textStyle:{color:'white',fontSize:10}},
		            	{value:'四川', textStyle:{color:'white',fontSize:10}},
		            	{value:'山东', textStyle:{color:'white',fontSize:10}},
		            	{value:'湖北', textStyle:{color:'white',fontSize:10}},
		            	{value:'江苏', textStyle:{color:'white',fontSize:10}},
		            	{value:'湖南',textStyle:{color:'white',fontSize:10}},
		            	{value:'福建',textStyle:{color:'white',fontSize:10}},
		            	{value:'北京',textStyle:{color:'white',fontSize:10}},
		            	{value:'江西',textStyle:{color:'white',fontSize:10}}],
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
           {
               type: 'value',
               min: 0,
               max: 130,splitNumber:6,
               position: 'right',axisLabel:{textStyle:{fontSize:10,color:'rgb(255,255,255)'}},
		    	splitLine:{show:false}
           },
		        {
		            type : 'value',splitNumber:6,axisLabel:{textStyle:{fontSize:10,color:'rgb(255,255,255)'}},
			    	splitLine:{lineStyle:{type:'dotted',color:'#434343'}}
		        }
		    ],
		    series : [
		        {
		            name:'Sales',
		            type:'bar',yAxisIndex: 1,
		            barWidth: '35%',itemStyle:{normal:{color:'#52ABAA'}},
		            data:[9127,7304,6789,4429,3698,3685,3516,3353,3006,2814]
		        },
		        {
		            name:'CDC',
		            type:'line',yAxisIndex: 0,lineStyle:{normal:{color:'#92F718',type:'dotted'}},
		            data:[76,89,108,107,76,76,74,76,2,96]
		        }
		    ]
		    
		};
chart3.setOption(chart3Option);
var engerixOption = {
		title:{text:'Engerix Inventory Status',
			textStyle:{color:'rgb(255,255,255)'},subtext:'CN Vx - 3PL Kyuan Beijing'
		},
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend:{
			bottom:'-1%',left:'center',
			z:100,
			textStyle:{color:'white'},
			data:[{name:'Forecast',icon:'roundRect'},{name:'Inventory',icon:'roundRect'},{name:'Stock Cover Mth'}],
		},
	    grid: {
	        left: '0%',
	        right: '4%',
	        bottom: '8%',
	        top: '25%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : [{value:'201710', textStyle:{color:'white',fontSize:10}},
	            	{value:'201711', textStyle:{color:'white',fontSize:10}},
	            	{value:'201712', textStyle:{color:'white',fontSize:10}},
	            	{value:'201801', textStyle:{color:'white',fontSize:10}},
	            	{value:'201802', textStyle:{color:'white',fontSize:10}},
	            	{value:'201803', textStyle:{color:'white',fontSize:10}},
	            	],
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
       {
           type: 'value',
           min: -3,
           max: 3,splitNumber:1,
           position: 'right',axisLabel:{textStyle:{fontSize:10,color:'rgb(255,255,255)'}},
	    	splitLine:{interval:3,lineStyle:{color:'white',type:'dotted'}}
       },
	        {
	            type : 'value',splitNumber:6,axisLabel:{textStyle:{fontSize:10,color:'rgb(255,255,255)'}},
		    	splitLine:{show:false}
	        }
	    ],
	    series : [
	        {
	            name:'Forecast',
	            type:'bar',yAxisIndex: 1,
	            barWidth: '20%',itemStyle:{normal:{color:'#52ABAA'}},
	            data:[37450,37900,33960,23151,23151,23151]
	        },
	        {
	            name:'Inventory',
	            type:'bar',yAxisIndex: 1,
	            barWidth: '20%',itemStyle:{normal:{color:'green'}},
	            data:[39285,37385,39425,39702.05513,16551.11027,-6599.834602]
	        },
	        {
	            name:'Stock Cover Mth',
	            type:'line',yAxisIndex: 0,lineStyle:{normal:{color:'#92F718',type:'dotted'}},
	            data:[0.947739815,0.901902838,0.951117276,1.714921588,0.714921588,-0.285078412]
	        }
	    ]
	    
	};
engerix.setOption(engerixOption);
var chart2Option={
		title:{text:'Top10 CDC',textStyle:{color:'rgb(255,255,255)'}},
		legend:{
			top:'10%',left:'center',
			z:100,
			textStyle:{color:'white'},
			data:[{name:'Cervarix',icon:'roundRect'},{name:'Engerix',icon:'roundRect'},{name:'Rabipur',icon:'roundRect'}],
		},
		grid:{left:'10%',right:'10%',top:'22%',bottom:'18%'},
		tooltip: {
	        trigger: 'axis',
	        formatter: "{a0}:{c0}<br>{a1}:{c1}<br>{a2}:{c2}"
	    },
	    xAxis: {
	        type: 'category',axisLabel:{fontSize:10},boundaryGap:false,
	        axisTick:{alignWithLabel:true,interval:0},
	        data: [{value:'北京\n市',textStyle:{color:'white',fontSize:10}},
	        	{value:'广东\n省',textStyle:{color:'white',fontSize:10}},
	        	{value:'东莞\n市',textStyle:{color:'white',fontSize:10}},
	        	{value:'湖南\n省',textStyle:{color:'white',fontSize:10}},
	        	{value:'广州\n市白\n云区',textStyle:{color:'white',fontSize:10}},
	        	{value:'广州\n市天\n河区',textStyle:{color:'white',fontSize:10}},
	        	{value:'郑州\n市二\n七区',textStyle:{color:'white',fontSize:10}},
	        	{value:'重庆\n市渝\n北区',textStyle:{color:'white',fontSize:10}},
	        	{value:'昆明\n市',textStyle:{color:'white',fontSize:10}},
	        	{value:'晋江\n市',textStyle:{color:'white',fontSize:10}}]
	    },
	    yAxis: {
	    	splitNumber:6,type: 'value',
	    	splitLine:{lineStyle:{type:'dotted',color:'#434343'}},
	    	axisLabel:{textStyle:{fontSize:10,color:'rgb(255,255,255)'}}
	    },
	    series: [
	        {
	            name:'Cervarix',
	            type:'line',showAllSymbol:true,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                }
	            },
	            areaStyle:{normal:{
	            	color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0, color: 'RGBA(40,106,171,0.15)' // 0% 处的颜色
                    }, {
                        offset: 1, color: '#286AAB' // 100% 处的颜色
                    }], false)
                }},
	            data:[2088,0,574,260,360,442,377,240,0,0]
	        },
	        {
	            name:'Engerix',
	            type:'line',
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                }
	            },lineStyle:{color:'blue'},
                areaStyle:{normal:{
	            	color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0, color: 'RGBA(235,14,14,0.32)' // 0% 处的颜色
                    }, {
                        offset: 1, color: 'RGBA(200,43,43,0.92)' // 100% 处的颜色
                    }], false)
                }},
	            data:[3339,1607,1096,800,576,442,377,357,356,349]
	        },{
	            name:'Rabipur',
	            type:'line',
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                }
	            },lineStyle:{color:'green'},
	            areaStyle:{normal:{
	            	color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0, color: 'RGBA(236,243,12,0.48)' // 0% 处的颜色
                    }, {
                        offset: 1, color: '#ECF30C' // 100% 处的颜色
                    }], false)
                }},
	            data:[354,0,0,260,0,7,75,30,0,0]
	        }
	    ]
	};
chart2.setOption(chart2Option);