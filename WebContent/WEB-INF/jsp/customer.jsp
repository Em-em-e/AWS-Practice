<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!--销售出库-->
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/customer.js"></script>
            <div role="tabpanel" class="tab-pane" id="customer">
                <div class="check-div form-inline">
                    <div class="col-xs-3">
                        	<input type="date" id="" name="outDate" class="form-control input-sm" placeholder="注意日期格式">
                        	
                    </div>
                    <div class="col-xs-4">
                   		<input type="text" id="" name="outNo" class="form-control input-sm" placeholder="">
                    </div>
                    <div class="col-xs-5">
                        	货品名称：<input type="text" id="" name="customerName" class="form-control input-sm" placeholder="货品名称">
                        &nbsp;&nbsp;&nbsp;<button class="btn btn-white btn-xs " onclick="doSearch()">查 询 </button>
                        <button class="btn btn-white btn-xs " onclick="doClear()">清除 </button>
                    </div>
                </div>
                <div class="data-div">
                    <div class="row tableHeader">
						<div class="pull-right" style="right:100px;margin-top:0px">
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="addCustomer()">新增客户</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="editCustomer()">修改</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="deleteCustomer()">删除</button>
						</div>
                    </div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="table-responsive">
								<table  id="customerTable" data-toggle="table"
                                    data-url="customer/list.json"
                                    data-show-export="true"
			            			data-export-types="['txt', 'excel']"
                                    data-toolbar="#customertoolbar"
                                    data-click-to-select="true"
                                    data-checkbox="true"
                                    data-show-columns="true"
                                    data-search="true"
                                    data-data-type="json"
                                    data-show-refresh="true"
                                    data-show-toggle="true"
                                    data-sort-name="opTime"
                                    data-page-list="[10, 30, 50]"
                                    data-page-size="10"
                                 	data-side-pagination="server"
                                    data-pagination="true" data-show-pagination-switch="true">
                                <span id="customertoolbar" style="display: inline-block;right: 0px">
                                 <select class="form-control" onchange="changeExportDataC()" id="exportDataC">
                                    <option value="">导出当前页数据</option>
                                    <option value="all">导出全部数据</option>
                                 </select>
                                </span>
                                <thead>
                                     <tr>
                                        <th  data-checkbox="true"  data-select-item-name="选中" ></th>
                                        <th data-field="customerType" data-formatter="customerType">客户类型</th>
                                        <th data-field="customerNo" >客户编码</th>
                                        <th data-field="customerName" >客户名称</th>
                                        <th data-field="remark1" >备注</th>
                                     </tr>
                                 </thead>
                             </table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" data-backdrop="static" id="addCustomer" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">新增/修改客户信息</h4>
				            </div>
				            <div class="modal-body">
				            	<div id="selectTree" class="ztree" style="height:300px;overflow:auto; ">   
									<div class="col-lg-8 col-lg-offset-2">
									<form method="post" action="customer/addCustomer" id="customerForm" class="form-horizontal" onsubmit="return false">
				                        <fieldset>
				                        	<input type="text" hidden="hidden" id="customerId" name="id">
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">客户编号*</label>
				                                <div class="col-lg-5">
				                                    <input  name="customerNo" id="customerNoCForm" class="form-control" type="text" placeholder="唯一编号，不可更改"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">客户类型*</label>
				                                <div class="col-lg-5">
				                                    <select class="selectpicker" data-live-search="true" id="customerTypeC" name="customerType" multiple>
				                                		<option value="1">客户</option>
				                                		<option value="2">供应商</option>
				                                		<option value="3">运输队</option>
													</select>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">客户名称*</label>
				                                <div class="col-lg-5">
											       	<input name="customerName" id="customerNameC" class="form-control" type="text" placeholder="客户名称" 
											       		 required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">备注</label>
				                                <div class="col-lg-5">
				                                    <input name="remark1" id="remarkC" type="text" class="form-control">
				                                </div>
				                            </div>
				                        </fieldset>
				                    </form>
				                	</div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="addCustomerSubmit()">提交</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div>
				    </div>
				</div>
                <!-- /.modal -->
            </div>
