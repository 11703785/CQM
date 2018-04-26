<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">
				校验规则查看
			</h4>
		</div>
		<div class="modal-body">
			<form class="form-horizontal" id="valiRulesForm">
			  <input name="valiRules.id" value="${valiRules.id}" id="valiRules.id" hidden="true">
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">规则名称：</label>
                  <div class="col-sm-9">
                    <input class="form-control" id="name" name="valiRules.name" value="${valiRules.name}" readonly="readonly">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">存储过程名称：</label>
                   <div class="col-sm-9">
                  	<input class="form-control" id="rules" name="valiRules.rules"  readonly="readonly" value="${valiRules.rules}">
                  </div>
                </div>
                 <div class="form-group">
                  <label class="col-sm-3 control-label">描述：</label>
                   <div class="col-sm-9">
                  	<textarea class="form-control" rows="3" id="descrption" name="valiRules.descrption"  readonly="readonly">${valiRules.descrption}</textarea>
                  </div>
                </div>
             </div>
           </form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>