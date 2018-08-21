<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!--销售出库-->
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/sysUser.js"></script>
            <div role="tabpanel" class="tab-pane" id="user">
                <div class="check-div form-inline">
                    <div class="col-xs-3">
                      	用户类型：<select class="selectpicker" id="userTypeQuery" name="usertype">
                      			<option value="">请选择</option>
                        		<option value="2">操作员</option>
                        		<option value="1">管理员</option>
						</select>
                    </div>
                    <div class="col-xs-4">
                   		用户名：<input type="text" id="usernameQuery" name="username" class="form-control input-sm">
                    </div>
                    <div class="col-xs-5">
                        	姓名：<input type="text" id="nameQuery" name="name" class="form-control input-sm">
                        &nbsp;&nbsp;&nbsp;<button class="btn btn-white btn-xs " onclick="doSearchUser()">查 询 </button>
                        <button class="btn btn-white btn-xs " onclick="doClearUser()">清除 </button>
                    </div>
                </div>
                <div class="data-div">
                    <div class="row tableHeader">
						<div class="pull-right" style="right:100px;margin-top:0px">
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="addUser()">新增用户</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="editUser()">修改&修改密码</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="deleteUser()">删除</button>
						</div>
                    </div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="table-responsive">
								<table  id="userTable" data-toggle="table" style="white-space:nowrap"
                                    data-url="user/list.json"
                                    data-show-export="true"
			            			data-export-types="['txt', 'excel']"
                                    data-toolbar="#sysUsertoolbar"
                                    data-click-to-select="true"
                                    data-checkbox="true" data-single-select="true"
                                    data-show-columns="true"
                                    data-search="true"
                                    data-data-type="json"
                                    data-show-refresh="true"
                                    data-show-toggle="true"
                                    data-page-list="[10, 30, 50]"
                                    data-page-size="10"
                                 	data-side-pagination="server"
                                    data-pagination="true" data-show-pagination-switch="true">
                                <thead>
                                     <tr>
                                        <th  data-checkbox="true"  data-select-item-name="选中" ></th>
                                        <th data-field="usertype" data-formatter="userTypeFormatter">用户类型</th>
                                        <th data-field="username" >用户名</th>
                                        <th data-field="name" >姓名</th>
                                        <th data-field="lastLoginTime" data-formatter="datetimeFormatter">最后登录时间</th>
                                        <th data-field="remark1" >备注</th>
                                     </tr>
                                 </thead>
                             </table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" data-backdrop="static" id="addUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">新增/修改用户</h4>
				            </div>
				            <div class="modal-body">
				            	<div id="selectTree" class="ztree" style="height:400px;overflow:auto; ">   
									<div class="col-lg-8 col-lg-offset-2">
									<form method="post" action="user/addUser" id="userForm" class="form-horizontal" onsubmit="return false">
				                        <fieldset>
				                        	<input type="text" hidden="hidden" id="userId" name="id">
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">用户类型*</label>
				                                <div class="col-lg-5">
				                                    <select class="selectpicker" data-live-search="true" id="userTypeU" name="usertype">
				                                		<option value="2">操作员</option>
				                                		<option value="1">管理员</option>
													</select>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">用户名*</label>
				                                <div class="col-lg-5">
				                                    <input  name="username" id="userNameU" class="form-control" type="text" placeholder="必须唯一" onblur="checkUsername()"/>
				                                </div>
				                                <span id="isenableUsername" style="color: red"></span>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">密码*</label>
				                                <div class="col-lg-5">
				                                    <input id="passwordU1" class="form-control" type="password" placeholder="密码" onblur="checkPassword()"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">确认密码*</label>
				                                <div class="col-lg-5">
				                                    <input  name="password" id="passwordU2" onblur="checkPassword()" class="form-control" type="password" placeholder="再次输入密码"/>
				                                </div>
				                                <span id="passwordError" style="color: red"></span>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">姓名*</label>
				                                <div class="col-lg-5">
											       	<input name="name" id="nameU" class="form-control" type="text" placeholder="姓名" 
											       		 required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">备注</label>
				                                <div class="col-lg-5">
				                                    <input name="remark1" id="remarkU" type="text" class="form-control">
				                                </div>
				                            </div>
				                        </fieldset>
				                    </form>
				                	</div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="addUserSubmit()">提交</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div>
				    </div>
				</div> 
                <!-- /.modal -->
            </div>
