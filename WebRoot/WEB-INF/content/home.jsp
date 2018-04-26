<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String webapp = request.getContextPath(); %>
<style>
.xx {
	float: left;
	margin-top: 10px;
}
</style>
<script>
$(document).ready(function() {
	/*$.get('/crms/resource/echart/json/gansu.json', function (chinaJson) {
			echarts.registerMap('甘肃', chinaJson);
			var chart = echarts.init(document.getElementById('mapCharts'));
			chart.setOption({
        	series: [{
            	type: 'map',
           		map: '甘肃'
       		 }]
   	 	});
	});*/
	tb2();//- 近五年收入与支出统计 -
	tb3();
  });
   //--------------
    //- 近五年收入与支出统计 -
    //--------------
 function tb2(){
 
    var charttb2 = echarts.init(document.getElementById('figure'));
	optiontb={
	    title: {
	        text: ''
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['年收入','年支出']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: ['0','0','0','0','0']
	    },
	    yAxis: {
	        type: 'value'
	    },
	    series: [
	        {
	            name:'年收入',
	            type:'line',
	            stack: '总量',
	            data:[0, 0, 0, 0, 0]
	        },
	        {
	            name:'年支出',
	            type:'line',
	            stack: '总量',
	            data:[0, 0, 0, 0, 0]
	        }
	    ]
	};
	
       var year=parseInt(new Date().getFullYear());
       var str='[\''+(year-4)+'\',\''+(year-3)+'\',\''+(year-2)+'\',\''+(year-1)+'\',\''+year+'\']';
       optiontb.xAxis.data=eval(str);
	   charttb2.setOption(optiontb);
	   $.post('getTBsrJSON.action',{},function(data){
	    		var str=data.split('@');
	    		optiontb.title.text=str[0];
	    		optiontb.series[0].data=eval(str[1]);
	    		optiontb.series[1].data=eval(str[2]);
	    		charttb2.setOption(optiontb);
	   });
}
function tb3(){
 	var charttb3 = echarts.init(document.getElementById('bingtu'));
 	optiontb3 = {
    title : {
        text: '',
        subtext: '',
        x:'left'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'right',
        data:['AAA','AA','A','B']
    },
   
    calculable : true,
    series : [
        {
            name:'信用等级',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:335, name:'AAA'},
                {value:310, name:'AA'},
                {value:234, name:'A'},
                {value:135, name:'B'}
            ]
        }
    ]
};

  charttb3.setOption(optiontb3);
  $.post('getTBpjJSON.action',{},function(data){
	    		var str=data.split('@');
	    		optiontb3.title.text=str[0];
	    		optiontb3.legend.data=eval(str[1]);
	    		optiontb3.series[0].data=eval(str[2]);
	    		charttb3.setOption(optiontb3);
	   });                 
}
</script>
<!-- 模块导航条 -->
<ol class="breadcrumb" style="margin-bottom: 0px;background-color: #ECF0F5;margin-top:0px;">
   <li class="active"></li>
</ol>
<!-- Main content -->
<section class="content" style="padding-top: 0px;">
  <div class="row" style="margin-top: 0px;padding-top: 0px;">
  
   <div class="col-lg-3 col-xs-6" style="width: 16.6%;">
          <!-- small box -->
          <div class="small-box bg-aqua" >
            <div class="inner">
              <h3>${maps['1']}<sup style="font-size: 20px">万元</sup></h3>
            </div>
            <div class="icon">
              <i class="ion ion-bag"></i>
            </div>
            <a href="#" class="small-box-footer">种植收入<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
       
      
        <div class="col-lg-3 col-xs-6" style="width: 16.6%;">
          <!-- small box -->
          <div class="small-box bg-green">
            <div class="inner">
              <h3>${maps['2']}<sup style="font-size: 20px">万元</sup></h3>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="#" class="small-box-footer">养殖收入<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        
        
         <!-- ./col -->
        <div class="col-lg-3 col-xs-6" style="width: 16.6%;">
          <!-- small box -->
          <div class="small-box bg-blue">
            <div class="inner">
              <h3>${maps['3']}<sup style="font-size: 20px">万元</sup></h3>
            </div>
            <div class="icon">
              <i class="ion ion-stats-bars"></i>
            </div>
            <a href="#" class="small-box-footer">经营收入<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
       
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6" style="width: 16.6%;">
          <!-- small box -->
          <div class="small-box bg-red">
            <div class="inner">
             <h3>${maps['4']}<sup style="font-size: 20px">万元</sup></h3>
            </div>
            <div class="icon">
              <i class="ion ion-person-add"></i>
            </div>
            <a href="#" class="small-box-footer">务工收入<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        
       
        <!-- ./col -->
        <div class="col-lg-3 col-xs-6" style="width: 16.6%;">
          <!-- small box -->
          <div class="small-box bg-yellow">
            <div class="inner">
              <h3>${maps['5']}<sup style="font-size: 20px">万元</sup></h3>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a href="#" class="small-box-footer">惠农补贴收入<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
        
        <div class="col-lg-3 col-xs-6" style="width: 16.6%;">
          <!-- small box -->
          <div class="small-box bg-gray">
            <div class="inner">
              <h3>${maps['9']}<sup style="font-size: 20px">万元</sup></h3>
            </div>
            <div class="icon">
              <i class="ion ion-pie-graph"></i>
            </div>
            <a href="#" class="small-box-footer">其他收入<i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
  </div>

   <div class="row">
        <div class="col-md-6">
          <!-- AREA CHART -->
          <div class="box box-primary">
          <div class="box-header">
              <h3 class="box-title">信用户、村、乡占比</h3>

              <!--<div class="box-tools">
                <ul class="pagination pagination-sm no-margin pull-right">
                  <li><a href="#">&laquo;</a></li>
                  <li><a href="#">1</a></li>
                  <li><a href="#">2</a></li>
                  <li><a href="#">3</a></li>
                  <li><a href="#">&raquo;</a></li>
                </ul>
              </div>
            --></div>
            <!-- /.box-header -->
            <div class="box-body no-padding">
              <table class="table" style="height:270px;">
                <tr style="background-color: #f9f9f9" height="20px">
                  <th style="width: 10px">#</th>
                  <th style="width: 70px">统计项</th>
                  <th>占比</th>
                  <th style="width: 50px">数值</th>
                </tr>
                <c:forEach items="${rateMap}" var="ratemap" varStatus="order">
                <tr>
                  <td>${order.index+1}</td>
                  <td>${ratemap.key}</td>
                  <td>
                    <div class="progress progress-xs" style="height:7px;">
                      <div class="progress-bar progress-bar-danger" style="width: ${ratemap.value}"></div>
                    </div>
                  </td>
                  <td><span class="badge bg-red">${ratemap.value}</span></td>
                </tr>
                </c:forEach>
                
              </table>
            </div>
            <!-- /.box-body -->
            
          </div>
          <!-- /.box -->
          
           <!-- BAR CHART -->
          <div class="box box-success">
            <div class="box-header with-border">
              <h3 class="box-title">信息通知</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" onclick="Notice.loadView()">更多...</button>
              </div>
            </div>
            <div class="box-body">
              <div class="chart">
                <ul style="width: 100%;height: 250px">
                	<c:forEach var="notice" items="${noticeList}">
	                <li class="xx" style="width: 80%;"><a href="#" onclick="Notice.query('${notice[0]}')">${notice[1]}</a></li>
	                <li class="xx" style="width: 20%;display: block">${notice[2]}</li>
	                </c:forEach>
                </ul>
              </div>
            </div>
            <!-- /.box-body -->
          </div>


        </div>
        <!-- /.col (LEFT) -->
        <div class="col-md-6">
          <!-- LINE CHART -->
          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">近五年收入与支出对比</h3>

              <div class="box-tools pull-right">
               
              </div>
            </div>
            <div class="box-body">
              <div id="figure" class="chart" style="height:250px;">
                
              </div>
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
		
		<!-- DONUT CHART -->
          <div class="box box-danger">
            <div class="box-header with-border">
              <h3 class="box-title">农户信用评级等级占比</h3>
            </div>
            <div class="box-body">
                  <div class="chart" id="bingtu" style="height: 273px;">
                  </div>
            </div>
          </div>
		
        </div>
        
		<div style="width: 100%; float: left">
			<!--<div class="box box-danger" style="float: left;width: 32%;margin-left: 14px">
				 <div class="box-header with-border">
	              <h3 class="box-title">贷款类型占比统计图</h3>
	
	              <div class="box-tools pull-right">
	                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
	                </button>
	                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
	              </div>
	            </div>
	            <div class="box-body">
	              <canvas id="pieChart1" style="height: 255px; width: 389px;" height="250" width="389" ></canvas>
	            </div>
            </div>
            
            <div class="box box-danger" style="float: left;width: 32%;margin-left: 10px">
            <div class="box-header with-border">

              <h3 class="box-title">历年收入总额统计图</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body">
              <div id="bar-chart" style="height: 255px;"></div>
            </div>
             /.box-body
          </div>
            
		
            
		 <div class="box box-danger" style="float: left;width: 32%;margin-left: 10px;height: 319px;">
            <div class="box-header with-border">
              <h3 class="box-title">平均信用等级热点分布图</h3>

              <div class="box-tools pull-right">
                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                </button>
                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
              </div>
            </div>
            <div class="box-body">
              <div class="chart" id="mapCharts" style="width:589px; height:251px;">
              </div>
            </div>
             /.box-body 
          </div>
		--></div>        
      </div>

 </section>
