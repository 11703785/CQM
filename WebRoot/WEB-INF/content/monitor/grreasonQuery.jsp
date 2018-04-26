<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script type="text/javascript">
$(document).ready(function(){
	var data1 = new Array();
	var data2 = new Array();
	
	var data1 = new Array();
	var data2 = new Array();
	<c:forEach items="${maps}" var="map">
		data1.push('${map.key}');
		data2.push('${map.value}');
	</c:forEach>

	var chart = echarts.init(document.getElementById('figure2'),theme);
	chart.setOption({
    title : {
        text: '查询原因统计',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['查询次数']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            magicType : {show: true, type: ['line', 'bar']},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : data1,
            axisLabel:{
	            	interval : 0,
	            	rotate:25 //倾斜度 -90 至 90 默认为0 
	            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    grid:{
                      y:45,   
                    y2:150,
                    borderWidth:1
                },
    series : [
        {
            name:'查询次数',
            type:'bar',
            barWidth:25,//条柱状图的大小
            data:data2,
            itemStyle: {  
                    normal: {  
                        label: {  
                            show: true,//是否展示  
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
    ]
});
});

 function queryData3(type){ 
	if (type==2){//重置
		$('#beginTime1').combo('setText','');  
		$('#endTime1').combo('setText','');  
		var x=null;
		var y=null;
		 var  ss = document.getElementById('grreason');
        ss[0].selected = true;//选中 
	   $("#figure2").load("grqueryReason.action",{'beginTime':x,'endTime':y});
		
	}else{
		
		$("#figure2").load("grqueryReason.action",{'beginTime':$('#beginTime1').datebox('getValue'),'endTime':$('#endTime1').datebox('getValue'),'grreason':$('#grreason').val()});
	}
};
</script>
 


<div style="width:auto;height:auto;padding-top:20px;">
        
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询原因：&nbsp;&nbsp;&nbsp;&nbsp;
 		 <select id="grreason" name="grreason" style="width:100px"> 
 		       <option value="" >请选择</option>		      
 		     <c:forEach items="${reason}" var="grreason">
 		       <option value="${grreason.id}">${grreason.name}</option>
 		     </c:forEach>
 		 </select>
        
        
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" class="easyui-datebox" editable="false" name="beginTime1" id="beginTime1" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束时间：<input type="text"  class="easyui-datebox" editable="false" name="endTime1" id="endTime1" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryData3(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="queryData3(2)">重置</a>
		<div id="figure2"  class="chart"  style="height: 420px; width: 99%; padding-top: 10px;"></div>
	
	
</div>	



