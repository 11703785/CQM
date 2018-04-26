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
	var chart = echarts.init(document.getElementById('figure3'),theme);
	chart.setOption({
    title : {
        text: '年度统计对比',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['查询量']
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
     //y轴位置
     grid:{
                      y:45,   
                    y2:150,
                    borderWidth:1
                },
    series : [
        {
            name:'查询量',
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
    ]
});
	 chart.hideLoading();

});


 
     function findData2(type){ 
	if (type==2){//重置
			var chart = echarts.init(document.getElementById('figure3'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		var year1=null;
	    var year2=null;
	   $('#qycontrast1').val("");
	    $('#qycontrast2').val(""); 
		$("#figure3").load("qycontrast.action",{'year1':year1,'year2':year2,});
	}else{
			var chart = echarts.init(document.getElementById('figure3'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		var year1=$('#qycontrast1') .val();
	    var year2=$('#qycontrast2') .val();
		$("#figure3").load("qycontrast.action",{'year1':year1,'year2':year2,});
	}
};


</script>
 
<div style="width:auto;height:auto;padding-top:20px;">
 		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对比年度:&nbsp;&nbsp;<input type="text" id="qycontrast1" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy',minDate:'2017',maxDate:'%y'})" name="qycontrast1" />&nbsp;&nbsp;--&nbsp;&nbsp;	    
        <input type="text" id="qycontrast2" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy',minDate:'2017',maxDate:'%y'})" name="qycontrast2" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="findData2(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="findData2(2)">重置</a>
		
		
		<div id="figure3"  class="chart"  style="height: 420px; width: 99%;padding-top: 10px;">

	
	
</div>	





