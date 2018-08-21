<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!--销售出库-->
            <script type="text/javascript" src="${pageContext.request.contextPath}/js/pagejs/product.js"></script>
            <div role="tabpanel" class="tab-pane" id="product">
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
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="addProduct()">新增产品</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="editProduct()">修改</button>
							<button class="btn btn-primary" style="height: 30px;padding-top: 5px" onclick="deleteProduct()">删除</button>
						</div>
                    </div>
					<div class="container-fluid">
						<div class="row-fluid">
							<div class="table-responsive">
								<table  id="productTable" data-toggle="table"
                                    data-url="product/list.json"
                                    data-show-export="true"
			            			data-export-types="['txt', 'excel']"
                                    data-toolbar="#inventoryViewtoolbar"
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
                                <span id="inventoryViewtoolbar" style="display: inline-block;right: 0px">
                                 <select class="form-control" onchange="changeExportDataProduct()">
                                    <option value="">导出当前页数据</option>
                                    <option value="all">导出全部数据</option>
                                 </select>
                                </span>
                                <thead>
                                     <tr>
                                        <th  data-checkbox="true"  data-select-item-name="选中" ></th>
                                        <th data-field="productNo" >产品编码</th>
                                        <th data-field="productName" >产品名称</th>
                                        <th data-field="cubeOrQuantity" >库存数</th>
                                        <th data-field="location"  >存放位置</th>
                                     </tr>
                                 </thead>
                             </table>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" data-backdrop="static" id="addProduct" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                <h4 class="modal-title" id="myModalLabel">添加/修改产品信息</h4>
				            </div>
				            <div class="modal-body">
				            	<div id="selectTree" class="ztree" style="height:300px;overflow:auto; ">   
									<div class="col-lg-8 col-lg-offset-2">
									<form method="post" action="product/addProduct" id="productForm" class="form-horizontal" onsubmit="return false">
				                        <fieldset>
				                        	<input type="text" hidden="hidden" id="productId" name="id">
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">产品编码*</label>
				                                <div class="col-lg-5">
				                                    <input  name="productNo" id="productNoP" class="form-control" type="text" placeholder="产品编码" required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">产品名称*</label>
				                                <div class="col-lg-5">
				                                    <input name="productName" id="productNameP" type="text" required="required" class="form-control" placeholder="产品名称">
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">库存数*</label>
				                                <div class="col-lg-5">
				                                    <input  name="cubeOrQuantity" id="cubeOrQuantityP" class="form-control" type="number" value="0" placeholder="库存数量" required="required"/>
				                                </div>
				                            </div>
				                            <div class="form-group">
				                                <label class="col-lg-3 control-label">存放位置</label>
				                                <div class="col-lg-5">
				                                	<input  name="location" id="locationP" class="form-control" type="text" placeholder="存放位置" />
				                                </div>
				                            </div>
				                        </fieldset>
				                    </form>
				                	</div>
								</div>
							</div>
				            <div class="modal-footer">
				            	<button type="button" class="btn btn-default" onclick="addProductSubmit()">提交</button>
				                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				            </div>
				        </div>
				    </div>
				</div>
                <!-- /.modal -->
            </div>
