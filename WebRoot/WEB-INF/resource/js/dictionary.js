/** DictionaryManage -------------------------------Begin*/
var Dictionary = new Object();
Dictionary.create = function(){
	$("#common_dlg").dialog({
		href:"createDictionary.action",
		hrefMode:"iframe",resizable: true,
		height: 500,width: 700,title: "维护字典信息",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};

Dictionary.submit = function(){
      $('#dictionaryForm').form('submit',{
         url: "saveDictionary.action",
         onSubmit: function(){  
             return $(this).form('validate');
         },
         success: function(result){
             var rArr = result.split(";");
             $.messager.show({
                 title: '提示：',
                 msg: rArr[1]
             });
             if (rArr[0]=="true"){
                 $('#common_dlg').dialog('close');
                 $('#dictionaryManageList').datagrid('reload');
             }
         }
     });
};
	
Dictionary.edit	= function(){
	var row = $('#dictionaryManageList').datagrid('getSelected');
	if (row){
		$("#common_dlg").dialog({
			href:"editDictionary.action?dictionary.id="+row.id,
			hrefMode:"iframe",resizable: true,
			height: 450,width: 700,title: "维护字典信息",
			modal: true,
			buttons: null
		});
		$('#common_dlg').dialog('open');
	}else{
		$.messager.alert('错误提示','请选择要编辑的字典信息!','error');
	}
};
Dictionary.deleteAll=function(){
	var row = $('#dictionaryManageList').datagrid('getSelected');
	if (row){
		 $.messager.confirm('提示','是否确认要删除所选字典信息？',function(r){  
             if(r){
            	 $.post('deleteDictionary.action',{'dictionary.id':row.id},function(result){
                	 var rArr = result.split(";");
                     if (rArr[0]=="false"){
                         $.messager.show({title: '错误提示：',msg: rArr[1]});
                     } else {
                         $('#dictionaryManageList').datagrid('reload');
                     }
                 });
             }
         });
	}else{
		$.messager.alert('错误提示','请选择要删除的字典信息!','error');
	}
};

Dictionary.findData = function(type){ 
	if (type==2){//重置
		$('#dictorytb').form('clear');
	}
	$('#dictionaryManageList').datagrid('load',{
		'dicType.name':$('#dicname').val()
	});
};

/** DictionaryManage --------------------------end*/


/** DicType---------------------------------- start*/
var DicType = new Object();
//新增
DicType.create = function(){
	$("#common_dlg").dialog({
		href:"createDicType.action",
		hrefMode:"iframe",resizable: true,
		height: 300,width: 570,title: "维护字典类型",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};	
	
DicType.edit = function(){
	var row = $('#dicTypeList').datagrid('getSelected');
	if (row){
		$("#common_dlg").dialog({
			href:"editDicType.action?dicType.id="+row.id,
			hrefMode:"iframe",resizable: true,
			height: 300,width: 570,title: "维护字典类型",
			modal: true,
			buttons: null
		});
		$('#common_dlg').dialog('open');
	}else{
		$.messager.alert('错误提示','请选择要编辑的字典类型!','error');
	}
};
DicType.submit = function(){
	 $('#dicTypeForm').form('submit',{
         url: "saveDicType.action",
         onSubmit: function(){  
             return $(this).form('validate');
         },
         success: function(result){
             var rArr = result.split(";");
             $.messager.show({
                 title: '提示：',
                 msg: rArr[1]
             });
             if (rArr[0]=="true"){
                 $('#common_dlg').dialog('close');
                 $('#dicTypeList').datagrid('reload');
             }
         }
     });
};	

DicType.deleteAll=function(){
	var row = $('#dicTypeList').datagrid('getSelected');
	if (row){
		 $.messager.confirm('提示','是否确认要删除所选字典类型？',function(r){  
             if(r){
            	 $.post('deleteDicType.action',{'dicType.id':row.id},function(result){
                	 var rArr = result.split(";");
                     if (rArr[0]=="false"){
                         $.messager.show({title: '错误提示：',msg: rArr[1]});
                     } else {
                         $('#dicTypeList').datagrid('reload');
                     }
                 });
             }
         });
	}else{
		$.messager.alert('错误提示','请选择要删除的字典类型!','error');
	}
	

};
/** DicType --------------------------------------end*/

var ValiRules = new Object();
//新增
ValiRules.create = function(){
	$("#common_dlg").dialog({
		href:"createValiRules.action",
		hrefMode:"iframe",resizable: true,
		height: 350,width: 570,title: "新建校验规则",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};	
ValiRules.submit = function(){
	
	$('#valiRulesForm').form('submit',{
         url: "saveValiRules.action",
         onSubmit: function(){  
             return $(this).form('validate');
         },
         success: function(result){
             var rArr = result.split(";");
             $.messager.show({
                 title: '提示：',
                 msg: rArr[1]
             });
             if (rArr[0]=="true"){
                 $('#common_dlg').dialog('close');
                 $('#valiRulesList').datagrid('reload');
             }
         }
     });
};
ValiRules.edit = function(id){
	var row = $('#valiRulesList').datagrid('getSelected');
	if (row){
		$("#common_dlg").dialog({
			href:"editValiRules.action?valiRules.id="+row.id,
			hrefMode:"iframe",resizable: true,
			height: 350,width: 570,title: "新建校验规则",
			modal: true,
			buttons: null
		});
		$('#common_dlg').dialog('open');
	}else{
		$.messager.alert('错误提示','请选择要编辑的校验规则!','error');
	}
};

ValiRules.deleteAll=function(id){
	
	var row = $('#valiRulesList').datagrid('getSelected');
	if (row){
		 $.messager.confirm('提示','是否确认要删除所选规则信息？',function(r){  
             if(r){
            	 $.post('deleteValiRules.action',{'valiRules.id':row.id},function(result){
                	 var rArr = result.split(";");
                     if (rArr[0]=="false"){
                         $.messager.show({title: '错误提示：',msg: rArr[1]});
                     } else {
                         $('#valiRulesList').datagrid('reload');
                     }
                 });
             }
         });
	}else{
		$.messager.alert('错误提示','请选择要删除的校验规则!','error');
	}
};
/** Dictionary End */