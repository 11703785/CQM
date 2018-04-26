<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
$(document).ready(function(){
	var data1 = new Array();
	var data2 = new Array();
	<c:forEach items="${maps}" var="map">
		data1.push('${map.key}');
		data2.push('${map.value}');
	</c:forEach>
	
	var chart = echarts.init(document.getElementById('figure'),theme);
	
	chart.setOption({
    title : {
        text: '机构查询统计',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
   
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            magicType : {show: true, type: ['line', 'bar']},
            saveAsImage : {show: true}
        }
    },
    
    xAxis : [
        {
            type : 'category',
            axisTick: {length:5},
            boundaryGap:true,
            data : data1,
            axisLabel:{
	            	  interval: 0,  
                      formatter:function(value)  
                       {  
                       return value.split("").join("\n");  
                   } 
	         }
        }
    ],
   
    yAxis : [
        {
            type : 'value'
        }
    ],
     dataZoom: [
            {
                show: true,
                y:-5,
                start: 0,
                end: 1000
            },
            {
                type: 'inside',
                start: 0,
                end: 1000
            }
         
        ],
         grid:{
                   
                      y:45,   
                    y2:150,
                    borderWidth:1
                },
    
     visualMap: {
            top: 50,
            right: 10,
            pieces: [{
                gt: 0,
                lte: 50,
                color: '#9C8BCE'
            }, {
                gt: 50,
                lte: 80,
                color: '#FF7F27'
            },{
                gt: 80,
                color: 'red'
            }],
            outOfRange: {
                color: '#999'
            }
        },
    series : [
        {
            name:'查询次数',
            type:'bar',
            barWidth:8,//条柱状图的大小
            data:data2,
            itemStyle: {  
                    normal: {  
                        label: {  
                            show: false,//是否展示  
                            position : 'top',
                            textStyle: {  
                                fontWeight:'bolder',  
                                fontSize : '12',  
                                fontFamily : '微软雅黑', 
                            }  
                        }  
                    }  
                },
        }
    ],
    calculable : true
});
				 chart.hideLoading();
});

 function queryData(type){ 
	
	if (type==2){//重置
	 var chart = echarts.init(document.getElementById('figure'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		$('#beginTime').combo("clear");  
		$('#endTime').combo("clear"); 
		$('#queryOrgName').combotree("clear")	
		var x=null;
		var y=null;
		var q=null;
		var p=null;
		var  xx = document.getElementById('level');
		 xx[0].selected = true;//选中 
		$("#figure").load("qyqueryPersonal.action",{'beginTime':x,'endTime':y,'queryOrgName':q,'level':p});
	}else{
	 var chart = echarts.init(document.getElementById('figure'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		var x=$('#queryOrgName').combotree("getValues");//选中的机构数组
		var r;
		for(var i=0;i<x.length;i++){//转为字符串
			if(i!=x.length-1){
				r=r+'\''+x[i]+'\''+',';
			}else if(i==0){	
				r='\''+x[i]+'\''}
			else{
			r=r+'\''+x[i]+'\''	
			}
		}
		$("#figure").load("qyqueryPersonal.action",{'beginTime':$('#beginTime').datebox('getValue'),'endTime':$('#endTime').datebox('getValue'),'queryOrgName':r,'level':$('#level option:selected') .val()});
	}
};
</script>
 
<div style="width:auto;height:auto;padding-top:10px;overflow: auto">
	

		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构名称：   <input class="easyui-combotree" id="queryOrgName" name="queryOrgName" value="${department.name}" data-options="url:'loadDeptJson.action',method:'post',multiple:true" style="width:260px;"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	机构级别：  <select id='level' name='level'>
                                                  <option value ="">请选择</option>
                                                  <option value ="0">地市级机构</option>
                                                  <option value ="1">区/县级机构</option>
                                                </select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" class="easyui-datebox" editable="false" name="beginTime" id="beginTime" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束时间：<input type="text" class="easyui-datebox" editable="false" name="endTime" id="endTime" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryData(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="queryData(2)">重置</a>
	
		<div id="figure" class="chart"  style="height: 420px; width: 99%;padding-top: 10px;"></div>
</div>	




