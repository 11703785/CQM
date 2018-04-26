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


$("#QyzxcxmxListTab").datagrid({
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

<table id="QyzxcxmxListTab" fit="true" class="easyui-datagrid" title="您的位置 >> 动态监测>>企业征信数据查询" width="auto" height="auto" iconCls="icon-edit" 
			data-options="border:false,rownumbers:true,singleSelect:true,url:'getQyzxcxmxJSON.action',method:'post',toolbar:'#tb'" idField="zzCode" fitColumns="true" pagination="true">
	<thead>
	<tr>
	 	<th data-options="field:'queryOrgNo'" width="16%">查询机构名称</th>
		<th data-options="field:'queryOrgName'" width="15%">查询网点名称</th>
		<th data-options="field:'queryUserName'" width="8%">查询用户姓名</th>
		<th data-options="field:'queryTime'" width="12%">查询时间</th>
		<th data-options="field:'companyName'" width="15%">被查询企业名称</th>
		<th data-options="field:'zzCode'" width="11%">企业编码</th>
		<th data-options="field:'queryComputerIP'" width="8%">查询机IP</th>
		<th data-options="field:'isQueried'" width="4%">是否查得</th>
	    <th data-options="field:'row',formatter:Qyzxcxmx.bb" width="8%">操作</th>
		
	</tr>
	</thead>
</table>
<div id="tb" style="padding:5px;height:auto">
	<div id="qqList"  style="margin-bottom:5px">
		   <div >
	    &nbsp;&nbsp;&nbsp;&nbsp;查询机构名称: &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:260px" class="easyui-combotree"  name="qyzxcxmx.queryOrgNo" id="queryOrgNo" value="${department.name}" data-options="url:'loadDeptJson.action',method:'post'" />
	    &nbsp;&nbsp;&nbsp;&nbsp;查询用户姓名: &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:100px" class="easyui-combobox"   name="qyzxcxmx.queryUserName" id="queryUserName"  />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询年度:&nbsp;<input id="qyyear" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy',minDate:'2017',maxDate:'%y'})" name="qyyear" />&nbsp;&nbsp;&nbsp;&nbsp;
         查询日期:&nbsp;<input id="qystartDate" class="Wdate" onclick="WdatePicker({dateFmt:'MM-dd'})" name="qystartDate" />&nbsp;&nbsp;--&nbsp;&nbsp;	    
        <input id="qyendDate" class="Wdate" onclick="WdatePicker({dateFmt:'MM-dd'})" name="qyendDate" />
	    </div>
		</br>
	    <div > 
	    &nbsp;&nbsp;&nbsp;&nbsp;查询网点名称: &nbsp;&nbsp;&nbsp;&nbsp;<input style="width:253px" class="easyui-validatebox" type="text" name="qyzxcxmx.queryOrgName" id="queryOrgName"  data-options="required:false"/>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否查得:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <select style="width:100px" name="qyzxcxmx.isQueried" id="isQueried">
	      <option value="请选择" selected="selected">请选择</option>
	      <option value="1">查得</option>
	      <option value="0">未查得</option>
	    </select>	
  	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;被查询企业名称: &nbsp;<input style="width:200px" class="easyui-validatebox" type="text" name="qyzxcxmx.companyName" id="companyName"  data-options="required:false"/>     		
		
        &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;  	  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-search"  onclick="Qyzxcxmx.findData(1)">查询</a>
	   	&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-reload"  onclick="Qyzxcxmx.findData(2)">重置</a>
		</div>
	</div>
</div>