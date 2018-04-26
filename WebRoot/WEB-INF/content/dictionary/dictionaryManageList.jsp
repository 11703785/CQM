<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String webapp = request.getContextPath(); %>
<SCRIPT language="javascript" src="<%=webapp%>/resource/js/dictionary.js"></SCRIPT>
<script language="JavaScript">
$(document).ready(function(){
	var typeid = $("#typeId").val();
	$('#dictionaryManageList').DataTable({
		//"paging": true,
		
        "lengthChange": false,  
        //"aLengthMenu":[10,20,30],
        
        "searching": false, 
        
        "ordering": false,  
        //"aaSorting" : [[0, "desc"]], //默认的排序方式，第1列，升序排列  
        //"info": true,  
        
        "autoWidth": false,  
        "destroy":true,  
        //"processing":true,  
        //"scrollX": false,   //水平新增滚动轴  
        "serverSide":true,    //true代表后台处理分页，false代表前台处理分页  
        "pagingType" : "full_numbers", //定义翻页组件的样式(有4个选择)  
        //当处理大数据时，延迟渲染数据，有效提高Datatables处理能力  
        "deferRender": true,
		
		language: {
               "sProcessing":   "处理中...",
               "sLengthMenu":   "每页 _MENU_ 项",
               "sZeroRecords":  "没有匹配结果",
               "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
               "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
               "sInfoFiltered": "",//(由 _MAX_ 项结果过滤，需要由后台拼接json字段recordsTotal)
               "sInfoPostFix":  "",
               "sSearch":       "搜索:",
               "sUrl":          "",
               "sEmptyTable":     "表中数据为空",
               "sLoadingRecords": "载入中...",
               "sInfoThousands":  ",",
               "oPaginate": {
                   "sFirst":    "首页",
                   "sPrevious": "上页",
                   "sNext":     "下页",
                   "sLast":     "末页",
                   "sJump":     "跳转"
               },
               "oAria": {
                   "sSortAscending":  ": 以升序排列此列",
                   "sSortDescending": ": 以降序排列此列"
               }
           },
		ajax : {
            url: "getDictionaryManageListJSON.action",
            type: "POST",
            dataType: "Json",
            //data : {"x":x}, //向后台传值
            data: {typeid:typeid}
        },
        aoColumns:[
        	{"data":"name", width:"20%"},
			{"data":"code", width:"10%"},
			{"data":"memo", width:"20%"},
			{"data":"id", width:"20%", render:function(data,type,row,meta) {
				return  "<a href='#' onclick=\"Dictionary.query('"+data+"')\">查看</a>&nbsp;&nbsp;"+
						"<a href='#' onclick=\"Dictionary.edit('"+data+"')\">编辑</a>&nbsp;&nbsp;"+
						"<a href='#' onclick=\"Dictionary.deleteAll('"+data+"')\">删除</a>";
			},"bSortable": false}
		]
	});
});
</script>

<div class="box box-danger">
	<div  style="padding-left:5px;padding-top:8px;padding-bottom:0px;">
         <button type="button" class="btn btn-primary btn-sm" onclick="Dictionary.create()">新增</button>
         <input id="typeId" name="typeId" value="${dicType.id}" hidden="true">
	</div>
	<div class="box-body" style="padding:0px;">
		<div style="overflow:auto;padding-top:0px;padding-left:5px;padding-right:5px;padding-bottom:5px;">
			<table id="dictionaryManageList" class="uk-table uk-table-hover table-bordered">
				<thead>
					<tr>
			            <th>字典名称</th>
			            <th>字典编码</th>
			            <th>字典说明</th>
			            <th>操作</th>
			        </tr>
				</thead>
			</table>
		</div>
	</div>
</div>
