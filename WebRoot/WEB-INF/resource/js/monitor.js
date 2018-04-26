var Grzxcxmx = new Object();


Grzxcxmx.findData= function(type){ 
	if (type==2){//重置
		var  xx = document.getElementById('isQueried');
		$('#hhList').form('clear');
        xx[0].selected = true;//选中 
	}
	 var options=$("#isQueried option:selected");
	$('#GrzxcxmxListTab').datagrid('load',{
		'grzxcxmx.queryOrgName':$('#queryOrgName').val(),
		'grzxcxmx.queryOrgNo':$('#queryOrgNo').combotree("getValue"),
		'grzxcxmx.queryUserName':$('#queryUserName').combobox("getText"),
		'grzxcxmx.queriedUserName':$('#queriedUserName').val(),
		'grzxcxmx.isQueried':options.val(),
		'gryear':$('#gryear').val(),
		'grbeginTime':$('#grstartDate').val(),
	    'grendTime':$('#grendDate').val()
		});
};

Grzxcxmx.bb =function(data,type,row,meta){
	
	var rows=$('#GrzxcxmxListTab').datagrid('getRows');
	var row2=rows[row];
	var str = "<a href=\"#\" onclick=\"Grzxcxmx.view('"+row2.queryUserSysName+"','"+row2.queryTime+"')\">查看详细</a>";
	return str;
}

Grzxcxmx.bb2 =function(data,type,row,meta){
	
	var rows=$('#GrsummaryViewTab').datagrid('getRows');
	var row2=rows[row];
	var str = "<a href=\"#\" onclick=\"Grzxcxmx.view2('"+row2.queryUserSysName+"','"+row2.queryTime+"')\">查看详细</a>";
	return str;
}


Grzxcxmx.view = function(value1,value2){
	
    url ="viewGrzxcxmx.action?grzxcxmx.queryUserSysName=" + value1+"&grzxcxmx.queryTime="+value2;  
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 500,width: 700,title: "详情",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};
Grzxcxmx.view2 = function(value1,value2){
	
    url ="viewGrzxcxmx.action?grzxcxmx.queryUserSysName=" + value1+"&grzxcxmx.queryTime="+value2;  
	$("#second_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 500,width: 700,title: "详情",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#second_dlg').dialog('open');
	
};



var Qyzxcxmx = new Object();

Qyzxcxmx.bb = function(data,type,row,meta){
		
	var rows=$('#QyzxcxmxListTab').datagrid('getRows');
	var row2=rows[row];
	var str = "<a href=\"#\" onclick=\"Qyzxcxmx.view('"+row2.zzCode+"','"+row2.queryTime+"')\">查看详细</a>";
	return str;
};

Qyzxcxmx.bb2 = function(data,type,row,meta){
		
	var rows=$('#QysummaryViewTab').datagrid('getRows');
	var row2=rows[row];
	var str = "<a href=\"#\" onclick=\"Qyzxcxmx.view2('"+row2.zzCode+"','"+row2.queryTime+"')\">查看详细</a>";
	return str;
};

Qyzxcxmx.view = function(value1,value2){
    
    url ="viewQyzxcxmx.action?qyzxcxmx.zzCode=" + value1+"&qyzxcxmx.queryTime="+value2;  
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 500,width:700,title: "详情",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};
Qyzxcxmx.view2 = function(value1,value2){
    
    url ="viewQyzxcxmx.action?qyzxcxmx.zzCode=" + value1+"&qyzxcxmx.queryTime="+value2;  
	$("#second_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 500,width:700,title: "详情",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#second_dlg').dialog('open');
	
};

Qyzxcxmx.findData= function(type){ 

	 
	if (type==2){//重置
		var  xx = document.getElementById('isQueried');
		$('#qqList').form('clear');
        xx[0].selected = true;//选中 
	}
	 var options=$("#isQueried option:selected");
	$('#QyzxcxmxListTab').datagrid('load',{
		'qyzxcxmx.queryOrgName':$('#queryOrgName').val(),
		'qyzxcxmx.queryUserName':$('#queryUserName').combobox("getText"),
		'qyzxcxmx.queryOrgNo':$('#queryOrgNo').combotree("getValue"),
		'qyzxcxmx.companyName':$('#companyName').val(),
		'qyzxcxmx.isQueried':options.val(),
		'qyyear':$('#qyyear').val(),
		'qybeginTime':$('#qystartDate').val(),
        'qyendTime':$('#qyendDate').val()

	});
};

var Grsummary = new Object();

Grsummary.findData= function(type){ 
	if (type==2){//重置
		$('#hhList').form('clear');       
	}
	$('#GrsummaryListTab').datagrid('load',{
		'grsummary.queryOrgName':$('#queryOrgName').val(),
		'departmentId':$('#queryOrgNo').combotree("getValue"),
		'grsummary.queryUserName':$('#queryUserName').combobox("getText"),
		'grsbeginTime':$('#grsbeginTime').datebox('getValue'),
	    'grsendTime':$('#grsendTime').datebox('getValue')
	});
};

Grsummary.bb = function(value){

	var str = "<a href=\"#\" onclick=\"Grsummary.view('"+value+"')\">查看详细</a>";
	return str;
};


Grsummary.view = function(value1){
    url ="grsummaryView.action?grsummary.id=" + value1;  
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 400,width: 1200,title: "汇总详情",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};

Grsummary.ViewfindData= function(type){ 
	if (type==2){//重置
	    var  xx = document.getElementById('isQueried');
		document.getElementById("queriedUserName1").value='';
        xx[0].selected = true;//选中 
	}
	 var options=$("#isQueried option:selected");
	$('#GrsummaryViewTab').datagrid('load',{
		'grzxcxmx.queryOrgName':$('#queryOrgName1').val(),
		'grzxcxmx.queryOrgNo':$('#queryOrgNo1').val(),
		'grzxcxmx.queryUserName':$('#queryUserName1').val(),
		'grzxcxmx.queriedUserName':$('#queriedUserName1').val(),
		'grzxcxmx.isQueried':options.val(),
		'grzxcxmx.queryTime':$('#queryTime').val()
	});
};

var Qysummary = new Object();

Qysummary.findData= function(type){ 
	if (type==2){//重置
		$('#hhList').form('clear');       
	}
	
	$('#QysummaryListTab').datagrid('load',{
		'qysummary.queryOrgName':$('#queryOrgName').val(),
		'departmentId':$('#queryOrgNo').combotree("getValue"),
		'qysummary.queryUserName':$('#queryUserName').combobox("getText"),
		'qysbeginTime':$('#qysbeginTime').datebox('getValue'),
	    'qysendTime':$('#qysendTime').datebox('getValue')
	});
};

Qysummary.bb = function(value){

	var str = "<a href=\"#\" onclick=\"Qysummary.view('"+value+"')\">查看详细</a>";
	return str;
};


Qysummary.view = function(value1){
    url ="qysummaryView.action?qysummary.id=" + value1;  
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height:400,width: 1200,title: "汇总详情",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};

Qysummary.ViewfindData= function(type){ 
	if (type==2){//重置
	    var  xx = document.getElementById('isQueried');
		document.getElementById("queriedUserName1").value='';
        xx[0].selected = true;//选中 
	}
	 var options=$("#isQueried option:selected");
	$('#QysummaryViewTab').datagrid('load',{
		'qyzxcxmx.queryOrgName':$('#queryOrgName1').val(),
		'qyzxcxmx.queryOrgNo':$('#queryOrgNo1').val(),
		'qyzxcxmx.queryUserName':$('#queryUserName1').val(),
		'qyzxcxmx.companyName':$('#companyName').val(),
		'qyzxcxmx.isQueried':options.val(),
		'qyzxcxmx.queryTime':$('#queryTime').val()
	});
};



