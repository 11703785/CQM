<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

$(document).ready(function(){	
	var data1 = new Array();
	var data2 = new Array();
	var data3=new Array();
	<c:forEach items="${values}" var="values">
		data2.push('${values}');
	</c:forEach>
	var json=${json};
	var json2=${json2};
    <c:forEach items="${time}" var="time">
		data3.push('${time}');
	</c:forEach>
	var chart = echarts.init(document.getElementById('figure3'),theme);
	chart.setOption({
    tooltip : {
        trigger: 'axis'
    },
     legend: {
        data:json2
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : data3,
              axisLabel:{
	            	  interval: 0,  
                   rotate:-45
	         }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
     grid:{        
    	x:100,
    	x2:100,
    	           y:170,   
                    y2:80,
                    borderWidth:1
                },

    series :json
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
	    var type=null;
	    var time=null;
	    var area=null;
	    var dept=null;
	    var level=null;
		 $('#hhhList').form('clear');
		var  xx1 = document.getElementById('type');
		 xx1[0].selected = true;//选中 
		 var  xx = document.getElementById('time');
		 xx[0].selected = true;//选中 
		 	var  xx2 = document.getElementById('level2');
		 xx2[0].selected = true;//选中 
		$("#figure3").load("grcontrast.action",{'year1':year1,'year2':year2,'type':type,'time':time,'area':area,'dept':dept,'level':level});
	}else{	
		var year1=$('#grcontrast1') .val();//选中的值
	    var year2=$('#grcontrast2') .val();//选中的值
	    if($('#area1').combotree("getValues")!=null&&$('#area1').combotree("getValues")!=""&&$('#type option:selected') .val()!='1'){
	    	alert("请选择查询类型：辖区查询走势图！")
	    	return false;
	    }
	    if($('#queryOrgName1').combotree("getValues")!=null&&$('#queryOrgName1').combotree("getValues")!=""&&$('#type option:selected') .val()!='0'){
	    	alert("请选择查询类型：机构查询走势图！")
	    	return false;
	    }
	    if(year1!=null&&year1!=''&&(year2==null||year2=='')){
	    	alert("请选择结束时间！");
	    	return false;
	    }else{
	    	var chart = echarts.init(document.getElementById('figure3'),theme);
		
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		var x=$('#area1').combotree("getValues");//选中的机构数组
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
		var y=$('#queryOrgName1').combotree("getValues");//选中的机构数组
		var z;
		for(var i=0;i<y.length;i++){//转为字符串
			if(i!=y.length-1){
				z=z+'\''+y[i]+'\''+',';
			}else if(i==0){	
				z='\''+y[i]+'\''}
			else{
			z=z+'\''+y[i]+'\''	
			}
		}
		$("#figure3").load("grcontrast.action",{'year1':year1,'year2':year2,'type':$('#type option:selected') .val(),'time':$('#time option:selected') .val(),'area':r,'dept':z,'level':$('#level2 option:selected') .val()});
		}
	}
};


</script>
 
<div id="hhhList"  style="width:auto;height:auto;padding-top:20px; overflow: auto;" >
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 查询类型：  <select id='type' name='type'>
                                                  <option value ="">陕西省总走势图</option>
                                                  <option value ="2">各地市总走势图</option>
                                                  <option value ="0">机构查询走势图</option>
                                                  <option value ="1">辖区查询走势图</option>
                                                </select>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 时间刻度：  <select id='time' name='time'>
                                                  <option value ="">请选择</option>
                                                  <option value ="2">按年显示</option>
                                                  <option value ="1">按月显示</option>
                                                  <option value ="0">按日显示</option>
                                                </select>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	机构/辖区级别：  <select id='level2' name='level2'>
                                                  <option value ="">请选择</option>
                                                  <option value ="0">地市级机构/辖区</option>
                                                  <option value ="1">区/县级机构/辖区</option>
                                                </select>
 	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	机构名称：   <input class="easyui-combotree" id="queryOrgName1" name="queryOrgName1" value="${department.name}" data-options="url:'loadDeptJson.action',method:'post',multiple:true" style="width:270px;"></input>
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	辖区名称：   <input class="easyui-combotree" id="area1" name="area1" value="${area.name}" data-options="url:'loadArea.action',method:'post',multiple:true" style="width:130px;"></input>
     </br>
     </br>
 		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询时间:&nbsp;&nbsp;<input type="text" id="grcontrast1" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="grcontrast1" />&nbsp;&nbsp;--&nbsp;&nbsp;	    
        <input type="text" id="grcontrast2" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="grcontrast2" />
 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="findData2(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="findData2(2)">重置</a>
		<div id="figure3"  class="chart"  style="height: 420px; width: 99%; padding-top: 10px;overflow: auto;">

	
	
</div>	





