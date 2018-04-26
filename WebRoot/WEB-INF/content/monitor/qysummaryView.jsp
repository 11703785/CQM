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


$("#QysummaryViewTab").datagrid({
    onLoadSuccess : function () {
        $(this).datagrid("fixRownumber");
    }
});
  
</script>
<table id="QysummaryViewTab" fit="true" class="easyui-datagrid" title="您的位置 >> 动态监测>>企业征信数据查询汇总详情" width="auto" height="auto" iconCls="icon-edit" 
			data-options="rownumbers:true,singleSelect:true,url:'viewQysummary.action',toolbar:'#tqysv'" idField="id" fitColumns="true" pagination="true">
	<thead>
	<tr>     
	    <th data-options="field:'queryOrgNo'" width="15%">查询机构</th>
		<th data-options="field:'queryOrgName'" width="15%">查询网点名称</th>
		<th data-options="field:'queryUserName'" width="10%">查询用户姓名</th>
		<th data-options="field:'companyName'" width="15%">被查询企业姓名</th>
		<th data-options="field:'queryTime'" width="15%">查询时间</th>
		<th data-options="field:'queryComputerIP'" width="10%">查询机IP</th>
		<th data-options="field:'isQueried'" width="5%">是否查得</th>
		<th data-options="field:'row',formatter:Qyzxcxmx.bb2" width="12%">操作</th>
		</tr>
	</thead>
</table>
<%--<div id="tqysv" style="padding:5px;height:auto">--%>
<%--	<div id="qysvList"  style="margin-bottom:5px">--%>
<%--	   <div >--%>
<%--	   	&nbsp;&nbsp;&nbsp;&nbsp;查询机构名称: &nbsp;&nbsp;&nbsp;&nbsp; <input class="easyui-validatebox" type="text" id="queryOrgNo1" name="qyzxcxmx.queryOrgNo" readonly="readonly" value="${qysummary.queryOrgNo}" data-options="required:false" style="width:260px;" ></input>--%>
<%--	    &nbsp;&nbsp;&nbsp;&nbsp;查询用户姓名: &nbsp;&nbsp;&nbsp;&nbsp;<input class="easyui-validatebox" type="text"  name="qyzxcxmx.queryUserName" readonly="readonly" value="${qysummary.queryUserName}" id="queryUserName1" style="width:200px;" data-options="required:false"></input>--%>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询时间: &nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--		<input style="width:120px" class="easyui-validatebox" type="text"  name="qyzxcxmx.queryTime" id="queryTime" readonly="readonly" value="${qysummary.queryTime}" data-options="required:false" />--%>
<%--	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="Qysummary.ViewfindData(1)">查询</a>--%>
<%--	    --%>
<%--	    </div>--%>
<%--	    </br>--%>
<%--	    <div >--%>
<%--	    &nbsp;&nbsp;&nbsp;&nbsp;查询网点名称:&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:260px" class="easyui-validatebox" type="text" readonly="readonly" value="${qysummary.queryOrgName}" name="qyzxcxmx.queryOrgName" id="queryOrgName1"  data-options="required:false"/>--%>
<%--	   &nbsp;&nbsp;&nbsp;&nbsp;被查询企业名称: &nbsp;<input style="width:200px" class="easyui-validatebox" type="text" name="qyzxcxmx.companyName" id="companyName"  data-options="required:false"/>--%>
<%--		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否查得:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--	    <select style="width:80px" name="qyzxcxmx.isQueried" id="isQueried">--%>
<%--	      <option value="请选择" selected="selected">请选择</option>--%>
<%--	      <option value="1">查得</option>--%>
<%--	      <option value="0">未查得</option>--%>
<%--	    </select>	--%>
<%--	   	--%>
<%--	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--	   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="Qysummary.ViewfindData(2)">重置</a>--%>
<%--			</div>--%>
<%--	</div>--%>
<%--</div>--%>



