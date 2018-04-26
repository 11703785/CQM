/** DataTable Begin*/
var DataTable = new Object();
DataTable.create=function(id){
	$("#myModal").load("createDataTable.action?id="+id,function() {
		$("#myModal").modal('show');   
	});
	
}
DataTable.submit=function(){
	var status=true;
	var isKey=false;
	var num = $("#num").val();
	var arr = new Array();
	var n=0;
	var error="值 ";
	for(var i=65;i<num;i++){
		var nextChar = i;
		var valids = "";
	$("#"+nextChar+"5 option:selected").each(function(i){
		if(valids==""){
			valids = ""+$(this).val()+"";
        }else{
        	valids += ","+$(this).val()+"";
        }
    }); 
	$("#"+nextChar+"10").val(valids);
		if(arr.length>0){
			for(var j =0; j<arr.length; j++){
				var data=arr[j].split("@");
				if(data[0]==$("#"+nextChar+"0").val()){
					status=false;
					error=error+data[0]+";";
				}
				if(data[1]==$("#"+nextChar+"1").val()){
					status=false;
					error=error+data[1]+";";
				}
				if($("#"+nextChar+"1").val()=='mainId'){
					isKey=true;
				}
			}
		}
		var b = $("#"+nextChar+"0").val()+"@"+ $("#"+nextChar+"1").val();
		arr[n]=b;
		n=n+1;
	}
	
	if(!isKey){
		Confirm.show('提示', "字段名中mainId不存在，请修改");
		return false;
	}
	if(!status){
		Confirm.show('提示', error+"存在相同的值，请修改");
	}else{
		$('#DataTableForm').data('bootstrapValidator').validate();  
	    if($('#DataTableForm').data('bootstrapValidator').isValid()){  
	    	$.ajax({
				cache: true,
				type: "post",
				url: "saveCreateDataTable.action",
				data: $('#DataTableForm').serialize(),
				async: true,
				error: function(request) {
					alert("error");
				},
				success: function(result) {
					var rArr = result.split(";");
		            if (rArr[0]=="true"){
		            	$("#myModal").modal('hide');
		            	var zTree = $.fn.zTree.getZTreeObj("dataTableTree");
    					var node = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
    					zTree.reAsyncChildNodes(node, "refresh");
    					$('#dataTableContent').load("viewDataTable.action?DataTable.id="+node.id);
    					zTree.selectNode(node, false);
		            }else{
		            	Confirm.show('提示', rArr[1]);
		            }
				}
			});
	    }else{
			$('#DataTableForm').submit();
	    }
	}
}
DataTable.edit=function(id){
	
	$("#myModal").load("editDataTable.action",{"dataTable.id":id},function() {
		$("#myModal").modal('show');   
	});
}
DataTable.EditInfo=function(type,infoId){
	var url;
	
 if(type==2) url = "editPerson.action?person.personId=" + personId;  //编辑
	else url = "viewPerson.action?person.personId=" + personId;  //查看
	
	$("#myModal").load(url,function() {
		$("#myModal").modal('show');   
	});
	
}
DataTable.submitEdit=function(tempid){
	var status=true;
	var isKey=false;
	var num = parseInt($("#num").val());
	var arr = new Array();
	var n=0;
	var error="值 ";
	for(var i=65;i<num;i++){
		var nextChar = i;
		var valids = "";
	$("#"+nextChar+"5 option:selected").each(function(i){
		if(valids==""){
			valids = ""+$(this).val()+"";
        }else{
        	valids += ","+$(this).val()+"";
        }
    }); 
	$("#"+nextChar+"10").val(valids);
	
	if($("#"+nextChar+"1").val()=='mainId'){
			isKey=true;
	}
		if(arr.length>0){
			for(var j =0; j<arr.length; j++){
				var data=arr[j].split("@");
				if(data[0]==$("#"+nextChar+"0").val()){
					status=false;
					error=error+data[0]+";";
				}
				if(data[1]==$("#"+nextChar+"1").val()){
					status=false;
					error=error+data[1]+";";
				}
			}
		}
		var b = $("#"+nextChar+"0").val()+"@"+ $("#"+nextChar+"1").val();
		arr[n]=b;
		n=n+1;
	}
	if(!isKey){
		Confirm.show('提示', "字段名中mainId不存在，请修改");
		return false;
	}
	
	if(!status){
		Confirm.show('提示', error+"存在相同的值，请修改");
	}else{
		$('#DataTableForm').data('bootstrapValidator').validate();  
	    if($('#DataTableForm').data('bootstrapValidator').isValid()){  
	    	$.ajax({
				cache: true,
				type: "post",
				url: "saveEditDataTable.action",
				data: $('#DataTableForm').serialize(),
				async: true,
				error: function(request) {
					alert("error");
				},
				success: function(result) {
					var rArr = result.split(";");
		            if (rArr[0]=="true"){
		            	$("#myModal").modal('hide');
		            	/*var zTree = $.fn.zTree.getZTreeObj("dataTableTree");
	    				var node = zTree.getNodesByFilter(function (node) { return node.id == tempid }, true);
	    				zTree.reAsyncChildNodes(node, "refresh");
	    				$('#dataTableContent').load("viewDataTable.action?dataTable.id="+node.id);
	    				zTree.selectNode(node, false);*/
		            	 $('#dataListTable').DataTable().ajax.reload();
		            }else{
		            	Confirm.show('提示', rArr[1]);
		            }
				}
			});
	    }else{
			$('#DataTableForm').submit();
	    }
	}
}
DataTable.deleteAll=function(id){
	Confirm.show('提示', '是否确认要删除该表？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            $.post('deleteTableInfo.action',{'dataTable.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#dataListTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
}

/** DataTemplate Begin*/
var DataTemplate = new Object();
DataTemplate.create=function(){
	$("#myModal").load("createDataTemplate.action",function() {
		$("#myModal").modal('show');   
	});
	
}
DataTemplate.submit=function(){
	var status=true;
	var num = parseInt($("#num").val());
	var arr = new Array();
	var n=0;
	var error="值 ";
	for(var i=65;i<num;i++){
		var nextChar = String.fromCharCode(i);
		if(arr.length>0){
			for(var j =0; j<arr.length; j++){
				var data=arr[j].split("@");
				
				if($("#"+nextChar+"0").val()==""||$("#"+nextChar+"1").val()==""||$("#"+nextChar+"2").val()==""||$("#"+nextChar+"4").val()==""){
						Confirm.show('提示', "数据未填写完整，请补充完整！");
						return false;
				}
				
				if(data[0]==$("#"+nextChar+"0").val()){
					status=false;
					error=error+data[0]+";";
				}
				if(data[1]==$("#"+nextChar+"1").val()){
					status=false;
					error=error+data[1]+";";
				}
			}
		}
		var b = $("#"+nextChar+"0").val()+"@"+ $("#"+nextChar+"1").val()+"@"+$("#"+nextChar+"2").val();
		arr[n]=b;
		n=n+1;
	}
	
	if(!status){
		Confirm.show('提示', error+"存在相同的值，请修改");
	}else{

	$('#DataTemplateForm').data('bootstrapValidator').validate();  
    if($('#DataTemplateForm').data('bootstrapValidator').isValid()){  
    	$.ajax({
			cache: true,
			type: "post",
			url: "saveCreateDataTemplate.action",
			data: $('#DataTemplateForm').serialize(),
			async: true,
			error: function(request) {
				alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
	            if (rArr[0]=="true"){
	            	$("#myModal").modal('hide');
	            	$('#DataTemplateList').DataTable().ajax.reload();
	            }else{
	            	Confirm.show('提示', rArr[1]);
	            }
			}
		});
    }else{
		$('#DataTemplateForm').submit();
    }
	}
}
DataTemplate.edit=function(id,type){
	if(type==1){
		Confirm.show('提示', "已启用的模板无法进行编辑");
	}else{
		$("#myModal").load("editDataTemplate.action",{"dataTemplate.id":id},function() {
			$("#myModal").modal('show');   
		});
	}
	
}
DataTemplate.submitEdit=function(){
	var status=true;
	var num = parseInt($("#num").val());
	var arr = new Array();
	var n=0;
	var error="值 ";
	for(var i=65;i<num;i++){
		var nextChar = String.fromCharCode(i);
		if($("#"+nextChar+"0").val()==""||$("#"+nextChar+"1").val()==""||$("#"+nextChar+"2").val()==""||$("#"+nextChar+"4").val()==""){
						Confirm.show('提示', "数据未填写完整，请补充完整！");
						return false;
				}
		if(arr.length>0){
			for(var j =0; j<arr.length; j++){
				var data=arr[j].split("@");
				
				
					if(data[0]==$("#"+nextChar+"0").val()){
						status=false;
						error=error+data[0]+";";
					}
					if(data[1]==$("#"+nextChar+"1").val()){
						status=false;
						error=error+data[1]+";";
					}
			}
		}
		var b = $("#"+nextChar+"0").val()+"@"+ $("#"+nextChar+"1").val()+"@"+$("#"+nextChar+"2").val();
		arr[n]=b;
		n=n+1;
	}
	
	if(!status){
		Confirm.show('提示', error+"存在相同的值，请修改");
	}else{
		$('#DataTemplateForm').data('bootstrapValidator').validate();  
	    if($('#DataTemplateForm').data('bootstrapValidator').isValid()){  
	    	$.ajax({
				cache: true,
				type: "post",
				url: "saveEditDataTemplate.action",
				data: $('#DataTemplateForm').serialize(),
				async: true,
				error: function(request) {
					alert("error");
				},
				success: function(result) {
					var rArr = result.split(";");
		            if (rArr[0]=="true"){
		            	$("#myModal").modal('hide');
		            	$('#DataTemplateList').DataTable().ajax.reload();
		            }else{
		            	Confirm.show('提示', rArr[1]);
		            }
				}
			});
	    }else{
			$('#DataTemplateForm').submit();
	    }
	  }
}

DataTemplate.deleteData=function(id){
	Confirm.show('提示', '将删除模板sheet表及其中的数据是否确认？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteDataTemplate.action',{'dataTemplate.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	
                        $('#DataTemplateList').DataTable().ajax.reload();
                        Confirm.hide();
                    }
                });
            }
        }
    });
}

DataTemplate.openTable=function(id,status){
	var ts="启用";
	if(status=='1'){
		ts="停用";
	}
	Confirm.show('提示', '是否'+ts+'该模板？',{
    	'确认': {
            'primary': true,
            'callback': function() {
            	 $.post('openDataTemplate.action',{'dataTemplate.id':id,'dataTemplate.status':status},function(result){
			            var rArr = result.split(";");
			            if (rArr[0]=="false"){
			                  Confirm.show('错误提示', rArr[1]);
			            } else {
			            	Confirm.hide();
			                  $('#DataTemplateList').DataTable().ajax.reload();
			            }
    				 });
            }
        }
    });
    
}
DataTemplate.whSheetY=function(id){
	
		$('#content-wrapper').load("loadTemplateSheetFrame.action?id="+id);
	
}

DataTemplate.checkTemplate=function(id){
	$("#myModal").load("openTemplateImport.action?id="+id,function() {
			$("#myModal").modal('show');   
		});
}
DataTemplate.importFile=function(){
	$('#excelFileForm').data('bootstrapValidator').validate();  
    if($('#excelFileForm').data('bootstrapValidator').isValid()){  
    	startBlockUI();//打开遮罩
    	$("#excelFileForm").ajaxSubmit({
	        type: "post",
	        url: "importExcelTemplate.action",
	       success: function(result) {
    			closeBlockUI();
				var rArr = result.split(";");
	            if (rArr[0]=="true"){
	            	Confirm.show('提示', rArr[1]);
	            	//$("#myModal").modal('hide');
	            }else{
	            	var form=$("<form>");//定义一个form表单
					form.attr("style","display:none");
					form.attr("target","");
					form.attr("method","post");
					form.attr("action","fileDownload.action");
					var input1=$("<input>");
					input1.attr("type","hidden");
					input1.attr("name","exportData");
					input1.attr("value",(new Date()).getMilliseconds());
					var input2=$("<input>");
					input2.attr("type","hidden");
					input2.attr("name","riziPath");
					input2.attr("value",rArr[1]);
					$("body").append(form);//将表单放置在web中
					form.append(input1);
					form.append(input2);
					form.submit();//表单提交 
	            }
			}
	    });
    }else{
		$('#excelFileForm').submit();
    }
}

/** DataImport Begin*/
var DataImport = new Object();
DataImport.openImport=function(ids){
	
		$("#myModal").load("openImport.action?id="+ids,function() {
			$("#myModal").modal('show');   
		});
}


DataImport.importFile=function(){
	$('#excelFileForm').data('bootstrapValidator').validate();  
    if($('#excelFileForm').data('bootstrapValidator').isValid()){
    	
    	startBlockUI();//打开遮罩
    	$("#excelFileForm").ajaxSubmit({
	        type: "post",
	        url: "excelImportData.action",
	       success: function(result) {
				var rArr = result.split(";");
				closeBlockUI();
				$("#myModal").modal('hide');
            	Confirm.show('信息提示', rArr[1]);

			}
	    });
    }else{
		$('#excelFileForm').submit();
    }
}
DataImport.DataView=function(id){
		$('#content-wrapper').load("initOnlineDataEdit.action?id="+id);
}
DataImport.DataEdit=function(id){
		var tempid=$('#templateid').val();
		$('#content-wrapper').load("onlineDataEdit.action?id="+id);
}
DataImport.DataEditView=function(id){
		$('#content-wrapper').load("onlineDataEdit.action?id="+id);
}

//批量导入数据校验
DataImport.dataCheck=function(id){

	startBlockUI();//打开遮罩
	$.post('batchDataCheck.action',{'tempId':id},function(result){
        var rArr = result.split(";");
        closeBlockUI();
	    if (rArr[0]=="false"){
              Confirm.show('错误提示', rArr[1]);
        } else {
        	 loadContent('dataLoadLog.action');//跳转到日志列表
        }

	});
}
//数据校验结果
DataImport.dataCheckResult=function(id){
	
}


//===============================================================
var OnlineData = new Object();
OnlineData.loadInsertFrame=function(id){
	$('#content-wrapper').load("onlineDataInsert.action");
}
OnlineData.submitInsert=function(){
	var datastr="";
	
	var tb = document.getElementById('DataList');

   	var tbs = tb.getElementsByTagName('table');
   	var areaName=$('#county').val();
   	var proName=$('#towns').val();
   	var roadName=$('#village').val();
   	var currYear=$('#currYear').val();
   	var areaVal=$('#county').val();
   	var proVal=$('#towns').val();
   	var roadVal=$('#village').val();
   	if(currYear==''||areaVal=='a'||proVal=='a'||roadVal=='a'){
   		Confirm.show('错误提示', '请选择年度及归属地信息');
   		return false;
   	}
	for(var i = 0, len = tbs.length; i < len; i++) {
		//var obj=$('#'+tbs[i].id).parent().parent();	//获取父节点
		var tname=$('#'+tbs[i].id).attr("summary");
			datastr+=tname+"=[";
			var ztb= document.getElementById(tbs[i].id);
			var inps =ztb.getElementsByTagName('input');
			var sels =ztb.getElementsByTagName('select');
			for(var j = 0, len1 = inps.length; j < len1; j++){
				datastr+=inps[j].id+":";
				if(inps[j].id=='HZXM'&&$.trim(inps[j].value)==''){
					 Confirm.show('错误提示', "户主姓名不能为空");
					 return false;
				}
				
				if(inps[j].id=='mainId'&&isSfz(inps[j].value)==false){
					 Confirm.show('错误提示', "证件号码("+inps[j].value+")不符合规则");
					 return false;
				}
				if(inps[j].value==''){
					datastr+="NULL;";
				}else{
					datastr+=inps[j].value+";";
				}
				
			}
			for(var k = 0, len2 = sels.length; k < len2; k++){
				datastr+=sels[k].id+":"+sels[k].value+";";
				
			}
			datastr+="],"
		
	}
	$.post('saveOnlineInsertData.action',{'str':datastr,'xx':currYear+"@"+areaName+"@"+proName+"@"+roadName},function(result){
			            var rArr = result.split(";");
			            if (rArr[0]=="false"){
			                  Confirm.show('错误提示', rArr[1]);
			            } else {
			            	 Confirm.show('提示', rArr[1]);
			            }
    				 });
}
OnlineData.submitEdit=function(){
	var datastr="";
	
	var tb = document.getElementById('DataList');

   	var tbs = tb.getElementsByTagName('table');
   	var areaName=$('#county').val();
   	var proName=$('#towns').val();
   	var roadName=$('#village').val();
   	var currYear=$('#currYear').val();
   	var areaVal=$('#county').val();
   	var proVal=$('#towns').val();
   	var roadVal=$('#village').val();
   	if(currYear==''||areaVal=='a'||proVal=='a'||roadVal=='a'){
   		Confirm.show('错误提示', '请选择年度及归属地信息');
   		return false;
   	}
   	
   	var did=$('#did').val();
   	
	for(var i = 0, len = tbs.length; i < len; i++) {
		var obj=$('#'+tbs[i].id).parent().parent();	//获取父节点
		var temp1= obj.is(":visible");
		if(temp1){
		var tname=$('#'+tbs[i].id).attr("summary");
			datastr+=tname+"=[";
			var ztb= document.getElementById(tbs[i].id);
			var inps =ztb.getElementsByTagName('input');
			var sels =ztb.getElementsByTagName('select');
			for(var j = 0, len1 = inps.length; j < len1; j++){
				datastr+=inps[j].id+":";
				if(inps[j].id=='HZXM'&&$.trim(inps[j].value)==''){
					 Confirm.show('错误提示', "户主姓名不能为空");
					 return false;
				}
				
				if(inps[j].id=='mainId'&&isSfz(inps[j].value)==false){
					 Confirm.show('错误提示', "证件号码("+inps[j].value+")不符合规则");
					 return false;
				}
				if(inps[j].value==''){
					datastr+="NULL;";
				}else{
					datastr+=inps[j].value+";";
				}
				
			}
			for(var k = 0, len2 = sels.length; k < len2; k++){
				datastr+=sels[k].id+":"+sels[k].value+";";
				
			}
			datastr+="],"
		}
	}
	$.post('saveOnlineEditData.action',{'str':datastr,'id':did},function(result){
			            var rArr = result.split(";");
			            if (rArr[0]=="false"){
			                  Confirm.show('错误提示', rArr[1]);
			            } else {
			            	 $('#content-wrapper').load("initBasicDataMain.action");
			            }
    				 });

}
OnlineData.changeTableJson=function(){
	var tid =$('#tableNameSelect').val();
	$.ajax({
				cache: true,
				type: "post",
				url: "changeTableJson.action",
				data:  {"id":tid},
				async: true,
				error: function(request) {
					alert("error");
				},
				success: function(result) {
		            	$('#DataList').append(result);
				}
			});
}
DataImport.DeleteData=function(id){
	Confirm.show('提示', '是否确认删除该农户数据？',{
    	'确认': {
            'primary': true,
            'callback': function() {
				Confirm.hide();
				startBlockUI();
            	 $.post('PersonDataDelete.action',{'nid':id},function(result){
            		 	closeBlockUI();
			            var rArr = result.split(";");
			            if (rArr[0]=="false"){
			                  Confirm.show('错误提示', rArr[1]);
			            } else {
			            	Confirm.hide();
			            	$('#personListTable').DataTable().ajax.reload();
			            }
    				 });
            }
        }
    });
}
var PigeonholeData=new Object();
PigeonholeData.onSubmit=function(){
	var ye=$("#currYear").val();
	Confirm.show('提示', '是否归档'+ye+'年度的数据？此操作将把农户数据转移到归档表',{
    	'确认': {
            'primary': true,
            'callback': function() {
				Confirm.hide();
				startBlockUI();
            	 $.post('PigeonholeData.action',{'ye':ye},function(result){
            		 	closeBlockUI();
			            var rArr = result.split(";");
			            if (rArr[0]=="false"){
			                  Confirm.show('错误提示', rArr[1]);
			            } else {
			            	Confirm.hide();
			            	$('#DataLogtList').DataTable().ajax.reload();
			            }
    				 });
            }
        }
    });
}
var DataYiYi=new Object();
DataYiYi.DataView=function(id){
		$('#content-wrapper').load("initYiYiDataEdit.action?id="+id);
}
DataYiYi.DataEdit=function(id){
	$('#content-wrapper').load("onlineDataYIYIEdit.action?id="+id);
}
DataYiYi.submitEdit=function(){
	var datastr="";
	var yybz=$('#yybz').val();
	
	if(yybz==""){
		Confirm.show('错误提示', "异议修改备注为必填项！");
		return false;
	}
	
	var tb = document.getElementById('DataList');

   	var tbs = tb.getElementsByTagName('table');
   	
   	var did=$('#did').val();
   	
	for(var i = 0, len = tbs.length; i < len; i++) {
		var obj=$('#'+tbs[i].id).parent().parent();	//获取父节点
		var temp1= obj.is(":visible");
		var nid="";
		if(temp1){
		var tname=$('#'+tbs[i].id).attr("summary");
			datastr+=tname+"=[";
			var ztb= document.getElementById(tbs[i].id);
			var inps =ztb.getElementsByTagName('input');
			var sels =ztb.getElementsByTagName('select');
			for(var j = 0, len1 = inps.length; j < len1; j++){
				datastr+=inps[j].id+":";
				if(inps[j].id=='HZXM'&&$.trim(inps[j].value)==''){
				 	Confirm.show('错误提示', "户主姓名不能为空");
		 			return false;
				}
				
				if(inps[j].id=='mainId'&&isSfz(inps[j].value)){
					 Confirm.show('错误提示', "证件号码("+inps[j].value+")不符合规则");
					 return false;
				}
				if(inps[j].id=='mainId'){
				nid=inps[j].value;
				}
				if(inps[j].value==''){
					datastr+="NULL;";
				}else{
					datastr+=inps[j].value+";";
				}
				
			}
			for(var k = 0, len2 = sels.length; k < len2; k++){
				datastr+=sels[k].id+":"+sels[k].value+";";
				
			}
			datastr+="],"
		}
	}
	$.post('saveYYEditData.action',{'str':datastr,'id':did,'yybz':yybz,'nid':nid},function(result){
			            var rArr = result.split(";");
			            if (rArr[0]=="false"){
			                  Confirm.show('错误提示', rArr[1]);
			            } else {
			            	RateTask.personalPerformTask(nid);
			            	// $('#content-wrapper').load("initObjectionHanding.action");
			            }
    				 });

}

function startBlockUI(){
	$('#loading').on('show.bs.modal', centerModals);
	$('#loading').modal({
		backdrop: 'static',
		keyboard: false
	});
}
function closeBlockUI(){
	 $("#loading").modal('hide');
}
function centerModals() {   
　　$('#loading').each(function(i) {   
　　　　var $clone = $(this).clone().css('display','block').appendTo('body');
　　　　var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
　　　　top = top > 0 ? top : 0; 
		if(top>150)
			top=top-80
　　　　$clone.remove();   
　　　　$(this).find('.modal-content').css("margin-top", top);   
　　});   
};  
function isSfz(value){
    if (value.length == 18 && 18 != value.length) return false;
    var number = value.toLowerCase();
    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
    if (re == null || a.indexOf(re[1]) < 0) return false;
    if (re[2].length == 9) {
        number = number.substr(0, 6) + '19' + number.substr(6);
        d = ['19' + re[4], re[5], re[6]].join('-');
    } else d = [re[9], re[10], re[11]].join('-');
    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
	
}
function isDateTime(format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};
function isNotNull(val){
	if($.trim(val)==''){
		return false;
	}else{
		return true;
	}
}