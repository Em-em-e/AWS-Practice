<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!--销售出库-->
            <%-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/report.js"></script> --%>
            <div role="tabpanel" class="tab-pane" id="report">
                <div class="check-div form-inline">
                    <div class="col-xs-3">
                        	<input type="date" id="" name="outDate" class="form-control input-sm" placeholder="注意日期格式">
                        	
                    </div>
                    <div class="col-xs-4">
                   		 <input type="text" id="" name="outNo" class="form-control input-sm" placeholder="">
                    </div>
                    <div class="col-xs-5">
                        &nbsp;&nbsp;&nbsp;<button class="btn btn-white btn-xs " onclick="doSearch()">查 询 </button>
                        <button class="btn btn-white btn-xs " onclick="doClear()">清除 </button>
                    </div>
                </div>
                <div class="data-div">
                    <div class="row tableHeader">
						<div class="pull-right" style="right:100px;margin-top:0px">
						</div>
                    </div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="table-responsive">
								<table  id="reportTable" data-toggle="table"
                                    data-url="report/list.json"
                                    data-show-export="true"
			            			data-export-types="['txt', 'excel']"
                                    data-toolbar="#reporttoolbar"
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
                                <span id="reporttoolbar" style="display: inline-block;right: 0px">
                                 <select class="form-control" onchange="changeExportDataReport()">
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
                                        <th data-field="salesAmount" >销售应收</th>
                                        <th data-field="purchaseAmount" >采购应付</th>
                                        <th data-field="transAmount" >运输应付</th>
                                        <th data-field="total" >总计</th>
                                     </tr>
                                 </thead>
                             </table>
							</div>
						</div>
					</div>
				</div>
            </div>
