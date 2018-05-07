<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="easyui-layout" id="area" style="width: 100%; height: 100%">
	<div data-options="region:'center'" title="辖区管理" id="areaContent"
		split="true">
		<div id="area_tb" style="padding: 5px; height: auto">
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="javascript:addArea()">新增</a>
			</div>
		</div>
		<table id="area_dg"
			style="width: 100%; height: 100%; min-height: 260px;">
			<thead>
				<tr>
					<th data-options="field:'areaId',halign:'center',align:'center'" width="8%">辖区代码</th>
					<th data-options="field:'name',halign:'center',align:'center'" width="10%">辖区名称</th>
					<th data-options="field:'upArea',halign:'center',align:'center'" width="12%">所属辖区</th>
					<th data-options="field:'levels',halign:'center',align:'center',formatter:areaLevelFormatter" width="20%">辖区级别</th>
					<th data-options="field:'description',halign:'center',align:'center'" width="14%">辖区说明</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	function areaLevelFormatter(value, row, index){
		var arr = appDicMap[appDicKey.areaLevels]
	    for (var i = 0; i < arr.length; i++) {
	        if (value === arr[i].key) {
	            return "<span style='color:green;'>" + arr[i].value + "</span>";
	        }
	    }
	    return "<span style='color:green;'>" + value + "</span>";
	}
	
	function addArea() {
		new rjhc.showDialog({
			id : 'area_newwin',
			modal : true,
			closed : true,
			iconCls : 'icon-add',
			draggable : false,
			width : 530,
			title : "新增辖区",
			height : 550,
			cache : false,
			href : 'area/showadd',
		}).window('open');
	}
	creategrid("area", "areaId");
</script>