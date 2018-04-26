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


$("#logListTab1").datagrid({
    onLoadSuccess : function () {
        $(this).datagrid("fixRownumber");
    }
});
</script>


<table id="logListTab1" fit="true" class="easyui-datagrid" title="您的位置 >> 系统管理 >> 系统日志" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'getLogsJSON.action',toolbar:'#tb'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>
		<th data-options="field:'deptName'" width="200">机构名称</th>
		<th data-options="field:'loginName'" width="200">登录名</th>
		<th data-options="field:'name'" width="200">姓名</th>
		<th data-options="field:'operContent'" width="300">操作内容</th>
		<th data-options="field:'operTime'" width="200">操作时间</th>
		<th data-options="field:'ip'" width="200">IP地址</th>
	</tr>
	</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	<div id="logList"  style="margin-bottom:5px">
	<div style=" text-align:center">
		&nbsp;&nbsp;&nbsp;&nbsp;姓名: &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:130px"  class="easyui-validatebox" type="text" name="log.name" id="name" onblur="vaCheckNull('name');" data-options="required:false"/>
		&nbsp;&nbsp;&nbsp;&nbsp;IP地址: &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:120px"  class="easyui-validatebox" type="text" name="log.ip" id="ip" onblur="vaCheckNull('ip');" data-options="required:false"/>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;操作内容: &nbsp;&nbsp;&nbsp;&nbsp;<input  class="easyui-validatebox" type="text" name="log.operContent" id="operContent" onblur="vaCheckNull('operContent');" data-options="required:false"/>		--%>
		&nbsp;&nbsp;&nbsp;&nbsp;机构名称: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text" name="log.deptName" id="deptName" onblur="vaCheckNull('deptName');" data-options="required:false"/>
		
		&nbsp;&nbsp;&nbsp;&nbsp;操作日期: &nbsp;&nbsp;&nbsp;&nbsp;
		<input style="width:130px" class="easyui-datebox" editable="false" name="beginDate" id="logbeginDate" />
		&nbsp;——&nbsp;<input style="width:130px" class="easyui-datebox" name="endDate" editable="false" id="logendDate" />
		</div>
		<br>
		<div style=" text-align:center">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="Log.findData(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="Log.findData(2)">重置</a>
	     </div>
	</div>
</div>