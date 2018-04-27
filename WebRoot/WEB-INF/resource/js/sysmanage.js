/** Role Begin*/
var Role = new Object();
/*
 * 创建角色
 */

Role.create = function(){
	$("#common_dlg").dialog({
		href:"createRole.action",
		hrefMode:"iframe",resizable: true,
		height: 450,width: 570,title: "新建角色",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
	
};

/*
 * 编辑角色
 */
Role.edit = function(id){

	var row = $('#roleListTab').datagrid('getSelected');
	if (row){
		$("#common_dlg").dialog({
			href:"editRole.action?role.roleId="+row.roleId,
			hrefMode:"iframe",resizable: true,
			height: 450,width: 570,title: "编辑角色",
			modal: true,
			buttons: null
		});
		$('#common_dlg').dialog('open');
	}else{
		$.messager.alert('错误提示','请选择要编辑的角色行!','error');
	}
};
/*
 * 删除角色
 */
Role.deleteAll = function(){
	var row = $('#roleListTab').datagrid('getSelected');
	if (row){
		 $.messager.confirm('提示','是否确认要删除所选角色？',function(r){  
             if(r){
            	 $.post('deleteRole.action',{'role.roleId':row.roleId},function(result){
                	 var rArr = result.split(";");
                     if (rArr[0]=="false"){
                         $.messager.show({title: '错误提示：',msg: rArr[1]});
                     } else {
                         $('#roleListTab').datagrid('reload');
                     }
                 });
             }
         });
	}else{
		$.messager.alert('错误提示','请选择要删除的角色行!','error');
	}
};
/*
 * 保存角色
 */

Role.submit = function(){
	 $('#roleForm').form('submit',{
         url: "saveRole.action",
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
                 $('#roleListTab').datagrid('reload');
             }
         }
     });
};

Role.findData = function(type){ 
	if (type==2){//重置
		$('#roleList').form('clear');
	}
	$('#roleListTab').datagrid('load',{
		'role.roleName':$('#roleName').val()
	});
};




/*
 * 保存授权角色 
 */
Role.submitAuthorize = function(){

	var node = $('#roleTree').tree('getSelected');
	
	if(node!=null){
		var roleId = node.id;
		var nodes = $('#authorizeTree').tree('getChecked');
		var ids = "";
		$.each(nodes, function(key, val){
			if(key==0) ids += val.id;
			else ids += ","+val.id;
		});
		if(ids==""){
			$.messager.alert('错误提示','请为当前角色选择授权的操作资源!','error');
		}else{
			$.post('saveAuthorize.action',{'role.roleId':roleId,'resIds':ids},function(result){
				var rArr = result.split(";");
				if (rArr[0]=="false"){
					$.messager.show({
						title: '错误提示：',
						msg: rArr[1]
		            });
		        } else {
		        	$.messager.alert('信息提示',rArr[1],'info');
		        }
			});
		}
	}else{
		$.messager.alert('错误提示','请在角色列表中选择相应的角色!','error');
	}
};
/** Role End*/

/** Log Begin*/
var Log = new Object();


Log.findData = function(type){ 
	if (type==2){//重置
		$('#logList').form('clear');
	}
	$('#logListTab1').datagrid('load',{
		'log.name':$('#name').val(),
		'log.ip':$('#ip').val(),
		'log.deptName':$('#deptName').val(),
		'log.operContent':$('#operContent').val(),
		'beginDate':$('#logbeginDate').datebox('getValue'),
		'endDate':$('#logendDate').datebox('getValue')
	});
};

Log.findData1 = function(type){ 
	var x=$('#name').val();
    if (type==2){//重置
		$('#logList').form('clear');
	}
	$('#logListTab2').datagrid('load',{
		'optLog.name':$('#name').val(),
		'optLog.ip':$('#ip').val(),
		'queryOrgName':$('#queryOrgName').combotree("getValue"),
		'optLog.operContent':$('#operContent').val(),
		'beginDate':$('#logbeginDate').datebox('getValue'),
		'endDate':$('#logendDate').datebox('getValue')
	});
};



/** Log End */

/**Department begin*/
var Department = new Object();

Department.create = function(type){
	//alert(type);
	var parentId = $("#deptId").val();
	if(type==1) url = "createDepartment.action?department.deptId=" + parentId;    //创建子部门
	else url = "editDepartment.action?department.deptId=" + parentId;  //编辑部门
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 590,width: 600,title: "机构信息",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};


//保存提交
Department.submit=function(){
	var areaId = $("#areaId").val();
$('#deptForm').form('submit',{
		url: "saveDepartment.action",  
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
				var node = $('#deptTree').tree('getRoot');
				$('#deptTree').tree('reload',node.target);
			}
		}
	});
}

Department.removes = function (){
	var id = $("#deptId").val();
	var url = "deleteDepartment.action?department.deptId=" + id;
	$.messager.confirm('提示信息','您确定要删除吗？',function(data){
		if(data){
			$.ajax({
				url:url,
				type:'post',
				success:function(result){
					var rArr = result.split(";");
					$.messager.show({
						title: '提示：',
						msg: rArr[1]
					});
					if (rArr[0]=="true"){
						var node = $('#deptTree').tree('getRoot');
						$('#deptTree').tree('reload',node.target);
					}
				}
			});
		}
	});
}

//下载xml
Department.downloadXML = function(){
	
	location.href="departmentDownloadXML.action";


};
/** Department End*/

/** OptResource Begin*/
var OptResource = new Object();
OptResource.create = function(type){
    var url;
	if(type==1) url = "createOptResource.action";    //创建根资源
	else if(type==2) url = "createOptResource.action?optResource.parent.resId="+$('#resId').val();    //创建子资源
	else url = "editOptResource.action?optResource.resId="+$('#resId').val();    //编辑资源
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 520,width: 650,title: "新建操作资源",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};

OptResource.deleteAll = function(){
	$.messager.confirm('提示','是否确认要删除当前操作资源信息？',function(r){
		if(r){
			$.post('deleteOptResource.action',{'optResource.resId':$('#resId').val()},function(result){
				
				var rArr = result.split(";");
                if(rArr[0]=="false"){
                	$.messager.show({title: '错误提示：',msg: rArr[1]});
                }else{
                	$.messager.alert('信息提示',rArr[1],'info');
                	var node = $('#resourceTree').tree('getRoot');
                	$('#resourceTree').tree('reload',node.target);
                }
            });
         }
   });
};
OptResource.submit = function(){
 $('#resourceForm').form('submit',{
        url: "saveOptResource.action",
        onSubmit: function(){  
            return $(this).form('validate');
        },
        success: function(result){
            var rArr = result.split(";");
            if (rArr[0]=="false"){
                $.messager.show({
                    title: '错误提示：',
                    msg: rArr[1]
                });
            } else {
            	$.messager.alert('信息提示',rArr[1],'info');
                $('#common_dlg').dialog('close');
                var node = $('#resourceTree').tree('getRoot');
            	$('#resourceTree').tree('reload',node.target);
            }
        }
    });
};
/** OptResource End */

/** Person Begin */
var Person = new Object();
/*
 * 生成操作功能
 * @para deptId 所属部门ID
 */
Person.bb = function(value){

	var str = "<a href=\"#\" onclick=\"Person.view('"+value+"')\">查看详细</a>" + "　" +"<a href=\"#\" onclick=\"Person.create(2,'"+value+"')\">编辑</a>";
	return str;
};

/*
 * 添加用户
 * @para deptId 所属部门ID
 */
Person.create = function(type,personId){
	var deptId = $('#departmentId').val();
	if(type==1) url = "createPerson.action?person.department.deptId=" + deptId;   
	else url ="editPerson.action?person.personId=" + personId;  
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height:588,width: 700,title: "用户信息",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};

/*
 * 删除用户
 */
Person.deleteAll = function(){
	var selectRows = $('#personListTab').datagrid('getSelections');
	if(!selectRows || selectRows.length==0){
		$.messager.alert('提示','请选择你要删除的用户！','warning');
		return false;
	}else{
	    $.messager.confirm("提示", "是否确认要删除所选用户？", function(r) {
	    	if(r){
				var personIds = "";
				for(i=0;i <selectRows.length;i++){
					if(i==0) personIds += selectRows[i].personId;
					else personIds += "," + selectRows[i].personId;
				}
				$.post('deletePerson.action',{'ids':personIds},function(result){
					var rArr = result.split(";");
					$.messager.show({title: '信息提示：',msg: rArr[1]});
                    if (rArr[0]=="true") $('#personListTab').datagrid('reload');
                });
			}
		})
	}
};

/*
 * 在用户提交时调用
 */
Person.submit = function(){
	var obj=document.getElementsByName('ids');
	var s='';
    for(var i=0; i<obj.length; i++){
     if(obj[i].checked) s+=obj[i].value+','; //如果选中，将value添加到变量s中
    }
	 $('#personForm').form('submit',{
	     url: "savePerson.action?ids="+s,
	     onSubmit: function(){  
			 return $(this).form('validate');
	     },
	     success: function(result){
	         var rArr = result.split(";");
	         $.messager.show({
                 title: '信息提示：',
                 msg: rArr[1]
             });
	         if (rArr[0]=="true"){
	             $('#common_dlg').dialog('close');
	             $('#personListTab').datagrid('reload');
	         }
	     }
	 });
};

Person.view = function(personId){
	var deptId = $('#departmentId').val(); 
    url ="viewPerson.action?person.personId=" + personId;  
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 580,width: 700,title: "用户信息",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
	
};

/*
*重置密码
*/
Person.resetPassword = function(personId){
	var selectRows = $('#personListTab').datagrid('getSelections');
	if(!selectRows || selectRows.length==0){
		$.messager.alert('提示','请选择你要重置密码的用户！','warning');
		return false;
	}else{
	    $.messager.confirm("提示", "是否确认要重置所选用户的密码？", function(r) {
	    	if(r){
				var personIds = "";
				for(i=0;i <selectRows.length;i++){
					if(i==0) personIds += selectRows[i].personId;
					else personIds += "," + selectRows[i].personId;
				}
				$.post('resetPassword.action',{'ids':personIds},function(result){
					
					if(result){
						alert(result);
					var rArr = result.split(";");
					$.messager.show({title: '信息提示：',msg: rArr[1]});
					}
                });
			}
		})
	}
}

/** Person End */

/** UserAccount Begin*/
var UserAccount = new Object();
var flag;

UserAccount.updatePassword = function(){
	var s=$('#oldPwd').val();
	if(flag){
	$('#userForm').form('submit',{
		url: "modifyPassword.action?oldPwd="+s,
		onSubmit: function(){
			if($(this).form('validate')){
				if($('#userPwd').val()==$('#newPwd').val()){
					$.messager.alert('错误提示',"新旧密码一致，请重新输入！",'error');
					return false;
				}
				if($('#newPwd').val()!=$('#newPwd1').val()){
					$.messager.alert('错误提示',"两次新密码输入不一致，请重新输入！！",'error');
					return false;
				}
			}else{
				return false;
			}
		},
		success: function(result){
			var rArr = result.split(";");
			if (rArr[0]=="false"){
				$.messager.show({
					title: '错误提示：',
					msg: rArr[1]
				});
			} else {
				$.messager.alert('信息提示',rArr[1],'info');
				$('#common_dlg').dialog('close');
			}
		}
	});
}else{
	$.messager.alert('信息提示',"密码格式不对！",'info');
}
};

UserAccount.valiNewPw=function(){

		var newPw =document.getElementById("newPwd").value;
		var Pw = document.getElementById("newPwd1").value;
		if(newPw == ''){
			document.getElementById("font2").innerHTML='<font color=red>新密码不能为空！</font>';
			window.parent.document.getElementById("input2").value="";
			flag=false;
			}
	
		else if(newPw.length<8){
			document.getElementById("font2").innerHTML='<font color=red>密码长度至少8位！</font>';
			window.parent.document.getElementById("input2").value="";
				flag=false;
			}
		else if(!hasCapital(newPw)||!hasLowercase(newPw)){
			document.getElementById("font2").innerHTML='';
			document.getElementById("StrongPass").innerHTML='<font color=red>密码必须是数字、大写字母、小写字母的组合！</font>';
			window.parent.document.getElementById("input2").value="";
				flag=false;
			}
		else{
			document.getElementById("font2").innerHTML='<font color=green>格式正确</font>';
			window.parent.document.getElementById("input2").value="格式正确";
				flag=true;
		}
	}
	
	//验证第二次输入新密码
UserAccount.valiPw=function(){

		var Pw = document.getElementById("newPwd1").value;
		var newPw =  document.getElementById("newPwd").value;
		var font2 = document.getElementById("font2").innerHTML;
		if(Pw == ''){
			document.getElementById("font3").innerHTML='<font color=red>新密码不能为空！</font>';
			window.parent.document.getElementById("input3").value="";
			flag=false;
			}
		else if(newPw != ''&& Pw != newPw){
			document.getElementById("font3").innerHTML='<font color=red>两次输入的密码不一致！</font>';
			window.parent.document.getElementById("input3").value="";
			flag=false;
			}
		else if(Pw.length<8){
			document.getElementById("font3").innerHTML='<font color=red>密码长度至少8位！</font>';
			window.parent.document.getElementById("input3").value="";
			flag=false;
			}
		else if(!hasCapital(Pw)||!hasLowercase(Pw)){
			document.getElementById("font3").innerHTML='';
			document.getElementById("StrongPass").innerHTML='<font color=red>密码必须是数字、大写字母、小写字母的组合！</font>';
			window.parent.document.getElementById("input3").value="";
			flag=false;
			}
		else if(font2=='两次输入的密码不一致！'){
			document.getElementById("font2").innerHTML='<font color=red>两次输入的密码不一致</font>';
			document.getElementById("font3").innerHTML='<font color=red>两次输入的密码不一致</font>';
			window.parent.document.getElementById("input2").value="";
			window.parent.document.getElementById("input3").value="";
			flag=false;
		}else{
			valiNewPw()
			document.getElementById("font3").innerHTML='<font color=green>格式正确</font>';
			window.parent.document.getElementById("input3").value="格式正确";
			flag=true;
			}
	}
	//验证密码中是否含有大写字母
	function hasCapital(str){
		var result = str.match(/^.*[A-Z]+.*$/);
		if(result == null)
			return false;
		return true;
	}
	
	//验证密码中是否含有小写字母
	function hasLowercase(str){
		var result = str.match(/^.*[a-z]+.*$/);
		if(result == null)
			return false;
		return true;
	}


/** UserAccount End */

/** Organization Begin*/
var Organization = new Object();

Organization.create=function(type){
	var url;
	var deptId = $('#parentorg').val();
	if(type==1) url = "createDept.action?parent.id=" + deptId;    //创建子部门
	else{ 
		 url = "editdept.action?organization.id=" + deptId;
		}//编辑部门
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 350,width: 800,title: "机构信息",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};


//新增保存提交
Organization.submit=function(){
	$('#orgForm').form('submit',{
		url: "saveDept.action",  
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
				var node = $('#deptmagTree').tree('getRoot');
				$('#deptmagTree').tree('reload',node.target);
			}
		}
	});
}
//编辑保存
Organization.submitedit=function(){
	$('#orgFormedit').form('submit',{
		url: "saveDept.action",  
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
				var node = $('#deptmagTree').tree('getRoot');
				$('#deptmagTree').tree('reload',node.target);
			}
		}
	});
}



Organization.remove = function (){
	var id = $("#parentorg").val();
	var url = "deleteDept.action?organization.id=" + id;
	$.messager.confirm('提示信息','您确定要删除吗？',function(data){
		if(data){
			$.ajax({
				url:url,
				type:'post',
				success:function(result){
					var rArr = result.split(";");
					$.messager.show({
						title: '提示：',
						msg: rArr[1]
					});
					if (rArr[0]=="true"){
						var node = $('#deptmagTree').tree('getRoot');
						$('#deptmagTree').tree('reload',node.target);
					}
				}
			});
		}
	});
}
//导出为xml
Organization.toXML = function(){
	$.messager.confirm('提示','是否确认要导出为xml？',function(r){
		if(r){
			$.post('deptToXML.action',function(result){
				var rArr = result.split(";");
                if(rArr[0]=="false"){
                	$.messager.show({title: '错误提示：',msg: rArr[1]});
                }else{
                	$.messager.alert('信息提示',rArr[1],'info');
                	/*$('#AppStoreContent').panel('open').panel('refresh');*/
                }
            });
         }
   });
};
//下载xml
Organization.downloadXML = function(){
	
	location.href="deptDownloadXML.action";

};
/** Organization End*/

/**Holiday Start**/
var Holiday=new Object();
/**
 * 查询
 */
Holiday.findData=function(){
	$('#holidayListTab').datagrid('load',{
		'holidays.year':$('#holidayYear').val(),
		'holidays.content':$('#holidayContent').val()
		//'endDate':$('#logendDate').datebox('getValue')
	});
}


/**
 * 初始化节假日
 */
Holiday.initHoliday = function(){
	$("#common_dlg").dialog({
		href:'initHoliday.action',
		hrefMode:"iframe",
		resizable: true,
		height: 251,
		width: 650,
		title: "初始化节假日",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};


/** 
 * 新增节假日
 */
Holiday.createHoliday = function(){
	$("#common_dlg").dialog({
		href:'createHoliday.action',
		hrefMode:"iframe",
		resizable: true,
		height: 250,
		width: 650,
		title: "添加节假日",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};
/**
 * 删除节假日
 * @return
 */
Holiday.deleteHoliday = function(){
	var selectRows = $('#holidayListTab').datagrid('getSelected');
	if(!selectRows || selectRows.length==0){
		$.messager.alert('提示','请选择您要删除的记录！','warning');
		return;
	}else{
		$.messager.confirm('提示','是否确认要删除当前记录吗？',function(r){
		if(r){
			$.post('deleteHoliday.action',{'holidays.id':selectRows.id},function(result){
				var rArr = result.split(";");
                if(rArr[0]=="false"){
                	$.messager.show({title: '错误提示：',msg: rArr[1]});
                }else{
                	$.messager.alert('信息提示',rArr[1],'info');
                	$('#holidayListTab').datagrid('reload');
                }
            });
         }
		});
	}
};
/**
 * 初始化节假日的保存
 *
 */

Holiday.initSubmit = function(){
	 $('#holidayInitForm').form('submit',{
       url: "saveInitHoliday.action",
       onSubmit: function(){  
           return $(this).form('validate');
       },
       success: function(result){
           var rArr = result.split(";");
           if (rArr[0]=="false"){
               $.messager.show({
                   title: '错误提示：',
                   msg: rArr[1]
               });
           } else {
               $('#common_dlg').dialog('close');
               $('#holidayListTab').datagrid('reload');
           }
       }
   });
};


/**
 * 新增节假日的保存
 */
Holiday.createSubmit = function(){
	 $('#holidayCreateForm').form('submit',{
        url: "saveCreateHoliday.action",
        onSubmit: function(){  
            return $(this).form('validate');
        },
        success: function(result){
            var rArr = result.split(";");
            if (rArr[0]=="false"){
                $.messager.show({
                    title: '错误提示：',
                    msg: rArr[1]
                });
            } else {
                $('#common_dlg').dialog('close');
                $.messager.show({
                    title: '成功提示：',
                    msg: rArr[1]
                });
                $('#holidayListTab').datagrid('reload');
            }
        }
    });
};

/**Holiday End**/

var sysRegist=new Object();

sysRegist.regist=function(){
	 $('#sysRegist').form('submit',{
        url: "submitsystemRegist_anonymous.action",
        onSubmit: function(){  
            return $(this).form('validate');
        },
        success: function(result){
            var rArr = result.split(";");
            if (rArr[0]=="false"){
                $.messager.show({
                    title: '错误提示：',
                    msg: rArr[1]
                });
            } else {
            	alert("成功提示：注册成功，请重新登录！");
            	//location.replace("goon.action");
            	location.replace("logout.action");
            }
        }
    });
};

sysRegist.submitCode=function(){
	 $('#sysRegistForm').form('submit',{
        url: "submitsystemRegist.action",
        onSubmit: function(){  
            return $(this).form('validate');
        },
        success: function(result){
            var rArr = result.split(";");
            if (rArr[0]=="false"){
                $.messager.show({
                    title: '错误提示：',
                    msg: rArr[1]
                });
            } else {
            	addTabs("系统注册","systemRegist.action"); 
                $.messager.show({
                    title: '成功提示：',
                    msg: rArr[1]
                });
                
            }
        }
    });
};

/**Area begin*/
var Area = new Object();

Area.create = function(type){
     var url;
	var areaId = $('#areaid').val();
	if(type==2) url = "createArea.action?area.id=" + areaId;    //创建辖区
	else url = "editArea.action?id=" + areaId;  //编辑辖区
	$("#common_dlg").dialog({
		href:url,
		hrefMode:"iframe",resizable: true,
		height: 450,width: 580,title: "辖区信息",
		cache: false,
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};

Area.deleteAll = function(){
	$.messager.confirm('提示','是否确认要删除该辖区？',function(r){
		if(r){
			$.post('deleteArea.action',{'area.id':$('#areaid').val()},function(result){
				
				var rArr = result.split(";");
                if(rArr[0]=="false"){
                	$.messager.show({title: '错误提示：',msg: rArr[1]});
                }else{
                	$.messager.alert('信息提示',rArr[1],'info');
                	var node = $('#areaTree').tree('getRoot');
                	$('#areaTree').tree('reload',node.target);
                }
            });
         }
   });
};

Area.submit = function(){
	$('#areaForm').form('submit',{
		url: "saveArea.action",  
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
				$.messager.alert('信息提示',rArr[1],'info');
				$('#common_dlg').dialog('close');
				var node = $('#areaTree').tree('getRoot');
				$('#areaTree').tree('reload',node.target);
			}
		}
	});
};
/** Area End*/

/**Notice Start**/
var Notice=new Object();
/*
 * 新增公告信息
 */
Notice.create = function(){
	$("#myModal").load("createNotice.action",function() {
		$("#myModal").modal('show');   
	});
};
/*
 * 保存信息公告
 */
Notice.submit = function(){
	$('#noticeForm').data('bootstrapValidator').validate();  
    if($('#noticeForm').data('bootstrapValidator').isValid()){  
    	$.ajax({
			cache: true,
			type: "post",
			url: "saveNotice.action",
			data: $('#noticeForm').serialize(),
			async: true,
			error: function(request) {
				alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
	            if (rArr[0]=="true"){
	            	$("#myModal").modal('hide');
	            	$('#noticeListTable').DataTable().ajax.reload();
	            }else{
	            	Confirm.show('提示', rArr[1]);
	            }
			}
		});
    }else{
		$('#noticeForm').submit();
    }
};
/*
 * 修改公告信息
 */
Notice.edit = function(id){
	$("#myModal").load("editNotice.action",{"notice.id":id},function() {
		$("#myModal").modal('show');    
	}); 
};

Notice.deleteAll=function(id){
	Confirm.show('提示', '是否确认要删除所选公告？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteNotice.action',{'notice.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#noticeListTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
};
Notice.query = function(id){
	$("#myModal").load("queryNotice.action",{"notice.id":id},function() {
		$("#myModal").modal('show');    
	}); 
};

Notice.loadView=function(){
	$('#content-wrapper').load("loadNoticeView.action");
}

/**Notice end**/

/**导航菜单跳转**/
function loadContent(url){
	$('#content-wrapper').load(url);
}


//接入点 start
var DataFromManager = new Object();
/*
 * 创建接入点
 */
DataFromManager.create = function(){
   $("#common_dlg").dialog({
		href:"createSourceOrg.action",
		hrefMode:"iframe",resizable: true,
		height: 500,width: 570,title: "新建接入点",
		modal: true,
		buttons: null
	});
	$('#common_dlg').dialog('open');
};


DataFromManager.findData = function(type){ 
	if(type==2){
		$('#sourceOrgList').form('clear');
	}
	$('#sourceOrgListTab').datagrid('load',{
		'sourceOrg.name':$('#sourceOrgName').val(),
	    'sourceOrg.code':$('#sourceOrgCode').val()

	  
	});
};

/*
 * 编辑接入点
 */
DataFromManager.edit = function(id){
	var row = $('#sourceOrgListTab').datagrid('getSelected');
	if (row){
		$("#common_dlg").dialog({
			href:"editSourceOrg.action?sourceOrg.id="+row.id,
			hrefMode:"iframe",resizable: true,
			height: 520,width: 570,title: "编辑接入点",
			modal: true,
			buttons: null
		});
		$('#common_dlg').dialog('open');
	}else{
		$.messager.alert('错误提示','请选择要编辑的接入点!','error');
	}
};

/*
 * 删除接入点
 */
DataFromManager.deleteAll = function(id){
	var row = $('#sourceOrgListTab').datagrid('getSelected');
	if (row){
		 $.messager.confirm('提示','是否确认要删除所选接入点？',function(r){  
             if(r){
            	 $.post('deleteSourceOrg.action',{'sourceOrg.id':row.id},function(result){
                	 var rArr = result.split(";");
                     if (rArr[0]=="false"){
                         $.messager.show({title: '错误提示：',msg: rArr[1]});
                     } else {
                    	             	 	$.messager.alert('信息提示',rArr[1],'info');

                         $('#sourceOrgListTab').datagrid('reload');
                     }
                 });
             }
         });
	}else{
		$.messager.alert('错误提示','请选择要删除的接入点!','error');
	}
};
/*
 * 保存接入点
 */
DataFromManager.submit = function(){
	var areaId=$('#areaId').val();
	 $('#sourceOrgForm').form('submit',{
         url: "saveSourceOrg.action",
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
            	 	$.messager.alert('信息提示',rArr[1],'info');
                 $('#common_dlg').dialog('close');
                 $('#sourceOrgListTab').datagrid('reload');
             }
         }
     });
};
/** rjhc Begin creat by wyl*/
var rjhc = rjhc || {};
$.ajaxSetup({
    contentType: 'application/json'
});
/**
 * 动态生成加载窗口
 *
 * @param options
 * @returns
 */
rjhc.showDialog = function (options) {
    var dialog = $("<div/>").appendTo("body").attr("id", options.id);
    $(dialog).data("key", options);
    var cfg = {
        border: false,
        resizable: false,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        cache: false,
        closable: false,
        /** 关闭面板之后 */
        onClose: function () {
            $(dialog).window('destroy');
        },
        close: function () {
            $(dialog).window('destroy');
        }
    };
    $.extend(cfg, options);
    $(dialog).window(cfg);
    return $(dialog);
};
$(function () {
    $.extend($.fn.window.defaults, {closable: true});
    $.extend($.fn.combobox.defaults, {
        formatter: function (row) {
            var _5f = $(this).combobox("options");
            if (row[_5f.textField] == "") {
                return "&nbsp;";
            } else {
                return row[_5f.textField];
            }
        },
        loadFilter: function (_65) {
            var opts = $(this).combobox("options");
            if (opts.required) {
                return _65;
            } else {
                var obj = {};
                obj[opts.valueField] = "";
                obj[opts.textField] = "";
                var objArr = $.makeArray(obj);
                objArr = $.merge(objArr, _65);
                return objArr;
            }
        }
    });
})
/** rjhc end*/