 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
$.extend($.fn.datagrid.methods, {
    fixRownumber : function (jq) {
        return jq.each(function () {
            var panel = $(this).datagrid("getPanel");
            //获取最后一行的number容器,并拷贝一份
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();
            //由于在某些浏览器里面,是不支持获取隐藏元素的宽度,所以取巧一下
            clone.css({
                "position" : "absolute",
                left : -1000
            }).appendTo("body");
            var width = clone.width("auto").width();
            //默认宽度是25,所以只有大于25的时候才进行fix
            if (width > 25) {
                //多加5个像素,保持一点边距
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);
                //修改了宽度之后,需要对容器进行重新计算,所以调用resize
                $(this).datagrid("resize");
                //一些清理工作
                clone.remove();
                clone = null;
            } else {
                //还原成默认状态
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");
            }
        });
    }
});


$("#QysummaryListTab").datagrid({
    onLoadSuccess : function () {
        $(this).datagrid("fixRownumber");
    }
});
//选择部门后部门下用户查出
$("#queryOrgNo").combotree({
     onSelect:function(node){
	var url="listPersonByDept.action?deptId="+node.id;
           $("#queryUserName").combobox({ 
	        url:url, 
            valueField : 'id',  
            textField : 'text'
        });  
    }  
});  
</script>

<table id="QysummaryListTab" fit="true" class="easyui-datagrid" title="您的位置 >> 动态监测>>企业征信数据查询汇总" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getQysummaryJSON.action',toolbar:'#tb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>     
	    <th data-options="field:'queryOrgNo'" width="18%">查询机构</th>
		<th data-options="field:'queryOrgName'" width="15%">查询网点名称</th>
		<th data-options="field:'queryUserName'" width="10%">查询用户姓名</th>
		<th data-options="field:'queryTime'" width="7%">查询时间</th>
	    <th data-options="field:'total'" width="10%">当天查询总数</th>
		<th data-options="field:'id',formatter:Qysummary.bb" width="35%">操作</th>
	</tr>
	</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	<div id="hhList"  style="margin-bottom:5px">
	   <div >
	   	&nbsp;&nbsp;&nbsp;&nbsp;查询机构名称: &nbsp;&nbsp;&nbsp;&nbsp; <input class="easyui-combotree" id="queryOrgNo" name="queryOrgNo" value="${department.name}" data-options="url:'loadDeptJson.action',method:'post'" style="width:260px;" ></input>
	    &nbsp;&nbsp;&nbsp;&nbsp;查询用户姓名: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-combobox"  name="qysummary.queryUserName" id="queryUserName" style="width:200px;"></input>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询时间: &nbsp;&nbsp;&nbsp;&nbsp;
		<input style="width:120px" class="easyui-datebox" editable="false" name="qysbeginTime" id="qysbeginTime" />
		&nbsp;&nbsp; —— &nbsp;&nbsp;
		<input style="width:120px" class="easyui-datebox" editable="false" name="qysendTime" id="qysendTime" />
	    </div>
	    </br>
	    <div >
	    &nbsp;&nbsp;&nbsp;&nbsp;查询网点名称:&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:253px" class="easyui-validatebox" type="text" name="qysummary.queryOrgName" id="queryOrgName"  data-options="required:false"/>	
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="Qysummary.findData(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="Qysummary.findData(2)">重置</a>
			</div>
	</div>
</div>