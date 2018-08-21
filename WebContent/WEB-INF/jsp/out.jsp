<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!--销售出库-->
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/out.js"></script>
            <div role="tabpanel" class="tab-pane active" id="out">
                <div class="check-div form-inline">
                    <div class="col-xs-3">
                        	出库日期：<input type="date" id="outDate" name="outDate" class="form-control input-sm" placeholder="注意日期格式">
                        	
                    </div>
                    <div class="col-xs-4">
                   		 出库单号：<input type="text" id="outNo" name="outNo" class="form-control input-sm" placeholder="出库单号">
                    </div>
                    <div class="col-xs-5">
                        	货品名称：<input type="text" id="productName" name="productName" class="form-control input-sm" placeholder="货品名称">
                        &nbsp;&nbsp;&nbsp;<button class="btn btn-white btn-xs " onclick="doSearch()">查 询 </button>
                        <button class="btn btn-white btn-xs " onclick="doClear()">清除 </button>
                    </div>
                </div>
                <div class="data-div">
                    <div class="row tableHeader">
						<div class="pull-right" style="right:100px;margin-top:0px">
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="addOut()">销售出库</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="editOut()">修改</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="deleteOut()">删除</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="selectCard()">生成对账单</button>
						</div>
                    </div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="table-responsive">
								<!-- <table id="outTable" style="overflow:hidden;white-space:nowrap; ">
								</table> -->
								<table  id="outTable" data-toggle="table"
                                    data-url="out/list.json"
                                    data-show-export="true"
			            			data-export-types="['txt', 'excel']"
                                    data-toolbar="#toolbar"
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
                                <span id="toolbar" style="display: inline-block;right: 0px">
                                 <select class="form-control" onchange="changeExportData()" id="exportData">
                                    <option value="">导出当前页数据</option>
                                    <option value="all">导出全部数据</option>
                                 </select>
                                </span>
                                <thead>
                                     <tr>
                                        <th  data-checkbox="true"  data-select-item-name="选中" ></th>
                                        <th data-field="outDate"  data-formatter="dateFormatter">出库时间</th>
                                        <th data-field="outNo" >出库单号</th>
                                        <th data-field="customerName" >客户名称</th>
                                        <th data-field="purchaseNo" >采购单号</th>
                                        <th data-field="productName"  >货品名称</th>
                                        <th data-field="length"  >长</th>
                                        <th data-field="width"  >宽</th>
                                        <th data-field="height"  >厚</th>
                                        <th data-field="cube"  >立方数</th>
                                        <th data-field="quantity" >数量</th>
                                        <th data-field="salesPrice" >销售单价</th>
                                        <th data-field="salesAmount"  >销售金额</th>
                                        <th data-field="salesTransport"  >销售运费</th>
                                        <th data-field="purchasePrice"  >进货成本价</th>
                                        <th data-field="purchaseAmount"  >成本金额</th>
                                        <th data-field="profit"  >毛利</th>
                                        <th data-field="opTime"  data-formatter="datetimeFormatter">操作时间</th>
                                        <th data-field="remark"  >备注</th>
                                     </tr>
                                 </thead>
                             </table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" data-backdrop="static" id="addOut" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">新增销售出库订单</h4>
				            </div>
				            <div class="modal-body">
				            	<div id="selectTree" class="ztree" style="height:600px;overflow:auto; ">   
									<div class="col-lg-8 col-lg-offset-2">
									<form method="post" action="out/addOut" id="outForm" class="form-horizontal" onsubmit="return false">
				                        <fieldset>
				                        	<input type="text" hidden="hidden" id="outId" name="id">
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">出库单号*</label>
				                                <div class="col-lg-5">
				                                    <input  name="outNo" id="outNoForm" class="form-control" type="text" placeholder="出库单号"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">出库日期*</label>
				                                <div class="col-lg-5">
				                                    <input name="outDateStr" id="outDateForm" type="date" required="required" class="form-control" placeholder="客户名称">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">客户名称*</label>
				                                <div class="col-lg-5">
				                                    <select class="selectpicker" data-live-search="true" id="customerNameForm" name="customerNo">
				                                		<option value="">请选择</option>
													</select>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">采购单号*</label>
				                                <div class="col-lg-5">
											       	<input name="purchaseNo" id="purchaseNo" class="form-control" type="text" placeholder="采购单号"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">货品名称*</label>
				                                <div class="col-lg-5">
				                                	<select class="selectpicker" data-live-search="true" id="productNameForm" name="productNo"
				                                	onchange="getProductQuantity()">
				                                		<option value="">请选择</option>
													</select>
				                                </div>
				                                <label class="control-label">库存数量：</label><span id="productQuantity"></span>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">规格</label>
				                                <div class="col-lg-2">
											       	<input name="length" id="length" class="form-control" type="number" placeholder="长" onblur="calculate()"/>
											    </div>
											    <div class="col-lg-2">
										       	<input name="width" id="width" class="form-control" type="number" placeholder="宽" onblur="calculate()"/>
										       	</div>
										       	<div class="col-lg-2">
										       	<input name="height" id="height" class="form-control" type="number" placeholder="高" onblur="calculate()"/>
										       	</div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">立方数</label>
				                                <div class="col-lg-5">
											       	<input name="cube" id="cubeOut" class="form-control" type="number" placeholder="立方数" readonly="readonly"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">数量*</label>
				                                <div class="col-lg-5">
											       	<input name="quantity" id="quantity" class="form-control" type="number" placeholder="提交后，会扣除库存且不可更改" 
											       		onblur="calculate()" required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">销售单价*</label>
				                                <div class="col-lg-5">
											       	<input name="salesPrice" id="salesPrice" class="form-control" type="number" placeholder="销售单价" 
											       		onblur="calculate()" required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">销售金额</label>
				                                <div class="col-lg-5">
				                                    <input name="salesAmount" id="salesAmount" class="form-control" type="number" readonly="readonly"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">销售运费*</label>
				                                <div class="col-lg-5">
											       	<input  name="salesTransport" id="salesTransport" class="form-control" type="number" placeholder="销售运费"
											       		onblur="calculate()" required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">进货成本价*</label>
				                                <div class="col-lg-5">
				                                    <input name="purchasePrice" id="purchasePrice" type="number" class="form-control" placeholder="进货成本价"
														    	onblur="calculate()">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">成本金额</label>
				                                <div class="col-lg-5">
				                                    <input  name="purchaseAmount" id="purchaseAmount" class="form-control" type="number" readonly="readonly"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">毛利</label>
				                                <div class="col-lg-5">
				                                    <input name="profit" id="profit" type="number" class="form-control" readonly="readonly">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">备注</label>
				                                <div class="col-lg-5">
				                                    <input name="remark" id="remark" type="text" class="form-control">
				                                </div>
				                            </div>
				                        </fieldset>
				                    </form>
				                	</div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="addOutSubmit()">提交</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div><!-- /.modal-content -->
				    </div><!-- /.modal -->
				</div>
                <!-- /.modal -->
            </div>
            
            <!--   ---------对账单页面         --------->
            <style>
			        #billTable,#billTable tr th, #billTable tr td { border:1px solid black;text-align: center; }
			        #billTable { width: 98%; min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;}   
    		</style>
    		<div class="modal fade" data-backdrop="static" id="billBefor" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel1">生成对账单</h4>
				            </div>
				            <div class="modal-body">
				            	<div  class="ztree" style="height:150px;overflow:auto; ">
				            		客户名称：<input id="customerNameBill" > &nbsp;&nbsp;&nbsp;
				            		请选择收款卡号：<select id="bankCardSelect">
				            		<option value="1">尾号：***1，开户行：****1</option>
				            		<option value="2">尾号：***2，开户行：****2</option>
				            		</select>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="generateBill()">确定</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div><!-- /.modal-content -->
				    </div><!-- /.modal -->
				</div>
            <div class="modal fade" data-backdrop="static" id="bill" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">生成对账单</h4>
				            </div>
				            <div class="modal-body">
				            	<div id="printDiv" class="ztree" style="height:70%;overflow:auto; "> 
				            		<img alt="" src="images/top.jpg" style="margin: auto; left: 20px; right: 0;"><br>
				            		 <strong>客户名称：<span id="billCoustomerName"></span></strong><br>
				            		<table class="table-class" id="billTable">
				            			<tr><th>送货日期</th><th>出仓单号</th><th>采购单号</th><th>物料号</th><th>长</th><th>宽</th><th>厚</th><th>货品名称</th><th>数量</th><th>单价（元）</th><th>金额</th><th>备注</th></tr>
				            			<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
				            			<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>合计</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
				            		</table><strong>
				            		备注：以上来往明细烦请核对，如无误请盖章回传，如有问题请及时与我司联系。谢谢！<br>
				            		汇款明细如下：<br></strong>
				            		<strong id="cardDetial">
					            		开户名称：佛山市天海木业有限公司<br>
					            		开户账号：*******<br>
					            		开户银行：中国邮政储蓄银行股份有限公司佛山市世纺城支行<br>
					            		联系人:李先生  139****** 固话：01556***** 传真：*******<br>
				            		</strong>
				            		
				            		<div style="float: right;"><strong>佛山市天海木业有限公司</strong><br><strong id="nowDate"></strong></div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="printBill()">打印</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div><!-- /.modal-content -->
				    </div><!-- /.modal -->
				</div>
            
            