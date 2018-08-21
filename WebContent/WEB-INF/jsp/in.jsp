<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!--销售出库-->
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/in.js"></script>
            <div role="tabpanel" class="tab-pane" id="in">
                <div class="check-div form-inline">
                    <div class="col-xs-3">
                        	入库日期：<input type="date" id="inDateStr" name="inDateStr" class="form-control input-sm" placeholder="注意日期格式">
                    </div>
                    <div class="col-xs-4">
                   		 入库单号：<input type="text" id="inNumber" name="inNumber" class="form-control input-sm" placeholder="出库单号">
                    </div>
                    <div class="col-xs-5">
                        	货品名称：<input type="text" id="inProductName" name="productName" class="form-control input-sm" placeholder="货品名称">
                        &nbsp;&nbsp;&nbsp;<button class="btn btn-white btn-xs " onclick="doSearch()">查 询 </button>
                        <button class="btn btn-white btn-xs " onclick="doClear()">清除 </button>
                    </div>
                </div>
                <div class="data-div">
                    <div class="row tableHeader">
						<div class="pull-right" style="right:100px;margin-top:0px">
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="addIn()">采购入库</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="editIn()">修改</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="deleteIn()">删除</button>
						</div>
                    </div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="table-responsive">
								<!-- <table id="outTable" style="overflow:hidden;white-space:nowrap; ">
								</table> -->
								<table  id="inTable" data-toggle="table"
                                    data-url="in/list.json"
                                    data-show-export="true"
			            			data-export-types="['txt', 'excel']"
                                    data-toolbar="#toolbarIn"
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
                                <span id="toolbarIn" style="display: inline-block;right: 0px">
                                 <select class="form-control" onchange="changeExportDataIn()" id="exportDataIn">
                                    <option value="">导出当前页数据</option>
                                    <option value="all">导出全部数据</option>
                                 </select>
                                </span>
                                <thead>
                                     <tr>
                                        <th  data-checkbox="true"  data-select-item-name="选中" ></th>
                                        <th data-field="inDate"  data-formatter="dateFormatter">入库时间</th>
                                        <th data-field="inNumber" >入库单号</th>
                                        <th data-field="supplierName" >供应商名称</th>
                                        <th data-field="length"  >长</th>
                                        <th data-field="width"  >宽</th>
                                        <th data-field="height"  >厚</th>
                                        <th data-field="cube"  >立方数</th>
                                        <th data-field="productName"  >货品名称</th>
                                        <th data-field="quantity" >数量</th>
                                        <th data-field="purchasePrice" >采购单价</th>
                                        <th data-field="purchaseAmount"  >采购金额</th>
                                        <th data-field="transporterName" >运输队名称</th>
                                        <th data-field="transCubePrice" >运输立方单价</th>
                                        <th data-field="transAmount" >运输费用</th>
                                        <th data-field="transPrice" >运输单价</th>
                                        <th data-field="purchaseCostPrice" >进货成本价</th>
                                        <th data-field="opTime"  data-formatter="datetimeFormatter">操作时间</th>
                                        <th data-field="remark"  >备注</th>
                                     </tr>
                                 </thead>
                             </table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" data-backdrop="static" id="addIn" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">新增入库订单</h4>
				            </div>
				            <div class="modal-body">
				            	<div class="ztree" style="height:500px;overflow:auto; ">   
									<div class="col-lg-8 col-lg-offset-2">
									<form method="post" action="in/addIn" id="inForm" class="form-horizontal">
				                        <fieldset>
				                        	<input type="text" hidden="hidden" id="inId" name="id">
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">入库单号</label>
				                                <div class="col-lg-5">
				                                    <input  name="inNumber" id="inNumberForm" class="form-control" type="text" placeholder="入库单号"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">入库日期</label>
				                                <div class="col-lg-5">
				                                    <input name="inDateStr" id="inDateStrForm" type="date" class="form-control" >
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">供应商名称*</label>
				                                <div class="col-lg-5">
				                                    <select class="selectpicker" data-live-search="true" id="customerNameFormIn" name="supplierNo">
				                                		<option value="">请选择</option>
													</select>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">货品名称*</label>
				                                <div class="col-lg-5">
				                                	<select class="selectpicker" data-live-search="true" id="productNameFormIn" name="productNo"
				                                	onchange="getProductQuantityIn()">
				                                		<option value="">请选择</option>
													</select>
				                                </div>
				                                <label class="control-label">库存数量：</label><span id="productQuantityIn"></span>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">规格</label>
				                                <div class="col-lg-2">
											       	<input name="length" id="lengthIn" class="form-control" type="number" placeholder="长" onkeyup="calculateIn()"/>
											    </div>
											    <div class="col-lg-2">
										       	<input name="width" id="widthIn" class="form-control" type="number" placeholder="宽" onkeyup="calculateIn()"/>
										       	</div>
										       	<div class="col-lg-2">
										       	<input name="height" id="heightIn" class="form-control" type="number" placeholder="高" onkeyup="calculateIn()"/>
										       	</div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">立方数</label>
				                                <div class="col-lg-5">
											       	<input name="cube" id="cubeIn" class="form-control" type="number" placeholder="立方数" readonly="readonly"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">数量</label>
				                                <div class="col-lg-5">
											       	<input name="quantity" id="quantityIn" class="form-control" type="number"
											       		onkeyup="calculateIn()" required="required" placeholder="提交后，会增加库存且不可更改"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">进货单价</label>
				                                <div class="col-lg-5">
											       	<input name="purchasePrice" id="purchasePriceIn" class="form-control" type="number" placeholder="进货单价" 
											       		onkeyup="calculateIn()" required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">进货金额</label>
				                                <div class="col-lg-5">
				                                    <input name="purchaseAmount" id="purchaseAmountIn" class="form-control" type="number" readonly="readonly"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-7 "></label>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">运输队名称</label>
				                                <div class="col-lg-5">
				                                	<select class="selectpicker" data-live-search="true" id="transporterName" name="transporterNo">
				                                		<option value="">请选择</option>
													</select>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">运输立方单价</label>
				                                <div class="col-lg-5">
				                                    <input  name="transCubePrice" id="transCubePrice" class="form-control" type="number" onkeyup="calculateIn()"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">运输金额</label>
				                                <div class="col-lg-5">
				                                    <input name="transAmount" id="transAmount" type="number" class="form-control" readonly="readonly">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">运输单价</label>
				                                <div class="col-lg-5">
				                                    <input name="transPrice" id="transPrice" type="number" class="form-control" readonly="readonly">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">进货成本价</label>
				                                <div class="col-lg-5">
				                                    <input name="purchaseCostPrice" id="purchaseCostPrice" type="number" class="form-control" readonly="readonly">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">备注</label>
				                                <div class="col-lg-5">
				                                    <input name="remark" id="remarkIn" type="text" class="form-control">
				                                </div>
				                            </div>
				                        </fieldset>
				                    </form>
				                	</div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="addInSubmit()">提交</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div><!-- /.modal-content -->
				    </div><!-- /.modal -->
				</div>
                <!-- /.modal -->
            </div>
