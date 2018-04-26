<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$('#ss').tabs({
    border:false,
    onSelect:function(title){
         if(title=="机构查询统计"){
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
		$("#figure").load("qyqueryPersonal.action",{'beginTime':x,'endTime':y,'queryOrgName':q});
         }else if(title=="历年数据查询统计"){
        		var chart = echarts.init(document.getElementById('figure3'),theme);
		
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		var year1=null;
	    var year2=null;
	    $('#grcontrast1').val("");
	    $('#grcontrast2').val(""); 
		$("#figure3").load("qycontrast.action",{'year1':year1,'year2':year2,});
		
         }else if(title=="辖区查询统计"){
        	var chart = echarts.init(document.getElementById('figure4'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
		$('#beginTime2').combo("clear");  
		$('#endTime2').combo("clear"); 
		$('#area').combotree("clear")	
		var x=null;
		var y=null;
		var q=null;
		$("#figure4").load("qyqueryArea.action",{'beginTime':x,'endTime':y,'area':q});
         }else if(title=="用户查询统计"){
        	 	var chart = echarts.init(document.getElementById('figure5'),theme);
		chart.showLoading({
                text : '数据获取中',
                effect: 'whirling'
            });
        	 	var x=null;
		        var y=null;
		        var q=null;
		$("#figure5").load("qyqueryPerson.action",{'beginTime':x,'endTime':y,'queryOrgName':q});
         }
     }
});
</script>
<div id="ss" class="easyui-tabs" style="width:auto;height:auto">
	<div title="机构查询统计" style="padding:10px" href="qyqueryPersonal.action">
	
	</div>	
	
	
	<div title="历年数据查询统计" href="qycontrast.action" >
	
	</div>	
	
	<div title="辖区查询统计" href="qyqueryArea.action" >
	
	</div>
	
	<div title="用户查询统计" href="qyqueryPerson.action" >
	
	</div>
	
	
</div>	




