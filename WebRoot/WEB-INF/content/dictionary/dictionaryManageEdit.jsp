<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(document).ready(function(){
	$('#dictionaryForm').bootstrapValidator({
      message: 'This value is not valid',
      feedbackIcons: {
          valid: 'glyphicon glyphicon-ok',
          invalid: 'glyphicon glyphicon-remove',
          validating: 'glyphicon glyphicon-refresh'
      },
      fields: {
    	  'dictionary.name' : {
              message: 'The rolename is not valid',
              validators: {
                  notEmpty: {
                      message: '字典名称不能够为空！'
                  },
                  stringLength: {
                      min: 1,
                      max: 50,
                      message: '字典信息名称字符长度不可超过50！'
                  }
              }
          },
          'dictionary.code' : {
              message: 'The rolename is not valid',
              validators: {
                  notEmpty: {
                      message: '字典编码不能够为空！'
                  },
                  stringLength: {
                      min: 1,
                      max: 10,
                      message: '字典编码字符长度不可超过10！'
                  }
              }
          },
          'dictionary.memo' : {
              message: 'The rolename is not valid',
              validators: {
                  stringLength: {
                      min: 0,
                      max: 50,
                      message: '字典说明字符长度不可超过50！'
                  }
              }
          },
           'dictionary.orderNum' : {
              message: 'The orderNum is not valid',
              validators: {
		            	notEmpty: {
		                    message: '序号不能够为空！'
		                },
		            	regexp: {
                            regexp: /^\d+$/,
                            message: '序号只能是正整数（含0）！'
                        }
		         }
          }
      }
  });
});
</script>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">
				字典信息编辑
				
			</h4>
		</div>
		<div class="modal-body">
			<form class="form-horizontal" id="dictionaryForm"> 
			  <input name="dictionary.id" value="${dictionary.id}" id="dictionary.id" hidden="true">
			  <input name="dicTypeId" value="${dicType.id}" id="dicTypeId" hidden="true">
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">字典类别：</label>
                  <div class="col-sm-9">
                    <input id="dicName" disabled="disabled" class="form-control" name="dicType.name" value="${dicType.name}" >
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">字典名称：</label>
                  <div class="col-sm-9">
                    <input id="name" name="dictionary.name" value="${dictionary.name}" class="form-control" onkeyup="this.value=this.value.replace(' ','')">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">字典编码：</label>
                  <div class="col-sm-9">
                    <input id="Code" name="dictionary.code" class="form-control" value="${dictionary.code}" >
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">字典序号：</label>
                  <div class="col-sm-9">
                    <input id="orderNum" name="dictionary.orderNum" class="form-control" value="${dictionary.orderNum}" >
                  </div>
                </div>
              	<div class="form-group">
                  <label class="col-sm-3 control-label">字典说明：</label>
                  <div class="col-sm-9">
                  	<textarea class="form-control" rows="3" placeholder="Enter ..." id="memo" name="dictionary.memo" >${dictionary.memo}</textarea>
                  </div>
              </div>
             </div>
           </form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			<button type="button" class="btn btn-primary" onclick="Dictionary.editSubmit()">提交</button>
		</div>
	</div>
</div>