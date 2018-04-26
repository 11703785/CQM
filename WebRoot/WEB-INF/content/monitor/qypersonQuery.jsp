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
	
	var chart = echarts.init(document.getElementById('figure5'),theme);
	
	chart.setOption({
    title : {
        text: '用户查询统计',
        subtext: ''
    },
    tooltip : {
        trigger: 'axis'
    },
    //右侧小图标设置
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
        	//倾斜度
	            	  interval: 'auto',  
                      formatter:function(value)  
                       {  
                       return value.split("").join("\n");  
                   },
<%--                 textStyle: {--%>
<%--                                --%>
<%--                                fontSize:'10'--%>
<%--                            }--%>
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
      //根据数值变色
     visualMap: {
            top: 50,
            right: 10,
            pieces: [{
                gt: 0,
                lte: 3000,
                color: '#9C8BCE'
            }, {
                gt: 3000,
                lte: 6000,
                color: '#FF7F27'
            },{
                gt: 6000,
                color: 'red'
            }],
            outOfRange: {
                color: '#999'
            }
        },
        //滚动条
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
        //柱状图设置
    series : [
        {
            name:'查询次数',
            type:'bar',
            barWidth:8,//条柱状图的大小
            data:data2,
            itemStyle: {  
                    normal: {  
                        label: {  
        	                //图顶数据设置
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
//条件查询
 function queryData5(type){ 
	if (type==2){//重置
	 var chart = echarts.init(document.getElementById('figure5'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		$('#beginTime3').combo("clear");  
		$('#endTime3').combo("clear"); 
		$('#queryOrgName1').combotree("clear")	
		var x=null;
		var y=null;
		var q=null;
		$("#figure5").load("qyqueryPerson.action",{'beginTime':x,'endTime':y,'queryOrgName':q});
	}else{
	 var chart = echarts.init(document.getElementById('figure5'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		var x=$('#queryOrgName1').combotree("getValues");//选中的机构数组
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
		$("#figure5").load("qyqueryPerson.action",{'beginTime':$('#beginTime3').datebox('getValue'),'endTime':$('#endTime3').datebox('getValue'),'queryOrgName':r});
	}
};
</script>

<div style="width:auto;height:auto;padding-top:20px;overflow: auto;">
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	机构名称：   <input class="easyui-combotree" id="queryOrgName1" name="queryOrgName1" value="${department.name}" data-options="url:'loadDeptJson.action',method:'post',multiple:true" style="width:260px;"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" class="easyui-datebox" editable="false" name="beginTime3" id="beginTime3" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束时间：<input type="text" class="easyui-datebox" editable="false" name="endTime3" id="endTime3" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="queryData5(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="queryData5(2)">重置</a>
		<div id="figure5" class="chart"  style="height: 420px; width: 99%;padding-top: 10px;"></div>
</div>	
  




