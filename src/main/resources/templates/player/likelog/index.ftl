<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>${title}</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
        <#--<link rel="stylesheet" href="${ctx}/backend/css/font-awesome.min.css" />-->
		<!-- page specific plugin styles -->

		<!-- ace styles -->

		<link rel="stylesheet" href="${ctx}/css/ace.min.css" />
		<link rel="stylesheet" href="${ctx}/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${ctx}/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${ctx}/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="${ctx}/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${ctx}/js/html5shiv.js"></script>
		<script src="${ctx}/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
        <#include "${ctx}/head.ftl" />
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>
                <#include "${ctx}/menu.ftl"/>
				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="#">控制台</a>
							</li>
							<li><a href="#">玩家管理</a></li>
							<li class="active">${title}</li>
						</ul><!-- .breadcrumb -->

						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="icon-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- #nav-search -->
					</div>

					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<#--<div class="hr hr-18 dotted hr-double"></div>
								<h4 class="pink">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a href="#modal-table" role="button" class="green" data-toggle="modal"> Table Inside a Modal Box </a>
								</h4>-->

								<div class="hr hr-18 dotted hr-double"></div>

								<div class="row ">
                                    <div class="col-xs-12">
                                        <h3 class="header smaller lighter blue">${table}</h3>
										<div class="col-sm-3 form-group">
											点赞玩家：<input id="playerName" name="playerName" type="text"/>
										</div>
										<div class="col-sm-3 form-group">
											获赞玩家：<input id="friendName" name="friendName" type="text"/>
										</div>
										<div class="col-sm-3 form-group">
											物业：<input id="inName" name="inName" type="text"/>
										</div>
										<#--<div class="col-sm-3 form-group">
											<label class="col-sm-3 control-label">发送时间：</label>
											<div class="col-sm-8">
												<input id="inEnd" name="inEnd"
													   class="laydate-icon form-control"
													   value="">
											</div>
										</div>-->
										<button class="btn btn-xs btn-primary" onclick="search();"><i class="fa fa-search"></i>&nbsp;查询</button>
										<#--<button class="btn btn-xs btn-success " type="button" onclick="add();"><i class="fa fa-plus"></i>&nbsp;添加</button>-->
										<#--<@shiro.hasPermission name="system:user:add">
                                        </@shiro.hasPermission>-->
                                    </div>
								</div>
                                <div class="space-6"></div>
                                <div class="table-responsive">
                                    <table id="helpListTable"></table>
                                </div>

								<div id="modal-table" class="modal fade" tabindex="-1">
									<div class="modal-dialog">
										<div class="modal-content">
											<div class="modal-header no-padding">
												<div class="table-header">
													<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
														<span class="white">&times;</span>
													</button>
													Results for "Latest Registered Domains
												</div>
											</div>

											<div class="modal-body no-padding">
												<table class="table table-striped table-bordered table-hover no-margin-bottom no-border-top">
													<thead>
														<tr>
															<th>Domain</th>
															<th>Price</th>
															<th>Clicks</th>

															<th>
																<i class="icon-time bigger-110"></i>
																Update
															</th>
														</tr>
													</thead>

													<tbody>
														<tr>
															<td>
																<a href="#">ace.com</a>
															</td>
															<td>$45</td>
															<td>3,330</td>
															<td>Feb 12</td>
														</tr>
													</tbody>
												</table>
											</div>

											<div class="modal-footer no-margin-top">
												<button class="btn btn-sm btn-danger pull-left" data-dismiss="modal">
													<i class="icon-remove"></i>
													Close
												</button>

												<ul class="pagination pull-right no-margin">
													<li class="prev disabled">
														<a href="#">
															<i class="icon-double-angle-left"></i>
														</a>
													</li>

													<li class="active">
														<a href="#">1</a>
													</li>

													<li>
														<a href="#">2</a>
													</li>

													<li>
														<a href="#">3</a>
													</li>

													<li class="next">
														<a href="#">
															<i class="icon-double-angle-right"></i>
														</a>
													</li>
												</ul>
											</div>
										</div><!-- /.modal-content -->
									</div><!-- /.modal-dialog -->
								</div><!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

				<div class="ace-settings-container" id="ace-settings-container">
					<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
						<i class="icon-cog bigger-150"></i>
					</div>

					<div class="ace-settings-box" id="ace-settings-box">
						<div>
							<div class="pull-left">
								<select id="skin-colorpicker" class="hide">
									<option data-skin="default" value="#438EB9">#438EB9</option>
									<option data-skin="skin-1" value="#222A2D">#222A2D</option>
									<option data-skin="skin-2" value="#C6487E">#C6487E</option>
									<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
								</select>
							</div>
							<span>&nbsp; Choose Skin</span>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />
							<label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />
							<label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />
							<label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />
							<label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
						</div>

						<div>
							<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />
							<label class="lbl" for="ace-settings-add-container">
								Inside
								<b>.container</b>
							</label>
						</div>
					</div>
				</div><!-- /#ace-settings-container -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<#include "${ctx}/common.ftl"/>

		<!-- inline scripts related to this page -->

		<script type="text/javascript">
            $(document).ready(function () {

				//外部js调用
				/*laydate({
					elem: '#inEnd', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
					event: 'focus', //响应事件。如果没有传入event，则按照默认的click
					format: 'YYYY-MM-DD hh:mm:ss',
					istime: true
				});*/

                //初始化表格,动态从服务器加载数据
                $("#helpListTable").bootstrapTable({
                    //使用get请求到服务器获取数据
                    method: "GET",
                    //必须设置，不然request.getParameter获取不到请求参数
                    contentType: "application/x-www-form-urlencoded",
                    //获取数据的Servlet地址
                    url: "${ctx}/${actionPath}/getList",
                    //表格显示条纹
                    striped: true,
                    //启动分页
                    pagination: true,
                    //每页显示的记录数
                    pageSize: 10,
                    //当前第几页
					pageNum: 1,
					startRow: 1,
					endRow: 1,
					total: 1,
					pages: 1,
					count: true,
                    //记录数可选列表
                    pageList: [5, 10, 15, 20, 25],
                    //是否启用查询
                    search: false,
                    //是否启用详细信息视图
                    detailView:false,
                    detailFormatter:detailFormatter,
                    //表示服务端请求
                    sidePagination: "server",
                    //设置为undefined可以获取pageNum，pageSize，searchText，sortName，sortOrder
                    //设置为limit可以获取limit, offset, search, sort, other
                    queryParams:getQueryParams,
                    queryParamsType: "",
                    //json数据解析
                    responseHandler: function(res) {
                        return {
                            "rows": res.data.list,
                            "total": res.data.total,
							"pageNum": res.data.pageNum,
							"startRow": res.data.startRow,
							"endRow": res.data.endRow,
						    "pages": res.data.pages,
						    "count": res.data.count
                        };
                    },
                    //数据列
					columns: [{
						title: "ID",
						field: "logId",
						sortable: true
					},{
						title: "点赞玩家",
						field: "playerName"
					},{
						title: "获赞玩家",
						field: "friendName"
					},{
						title: "获赞物业",
						field: "inName"
					},/*{
						title: "可用状态",
						field: "itemState",
						formatter: function (value, row, index) {
							if (value === 1)
								return '<span class="label label-info">是</span>';
							return '<span class="label label-danger">否</span>';
						}
					},*/{
						title: "点赞时间",
						field: "createTime"
					},{
                        title: "操作",
                        field: "empty",
                        formatter: function (value, row, index) {
							var operateHtml = '';
                            /*operateHtml = '<button class="btn btn-danger btn-xs" type="button" onclick="edit(\''+row.ruleId+'\')"><i class="fa fa-edit"></i>&nbsp;修改</button> &nbsp;';
                            operateHtml = operateHtml + '<button class="btn btn-danger btn-xs" type="button" onclick="del(\''+row.ruleId+'\')"><i class="fa fa-remove"></i>&nbsp;删除</button> &nbsp;';*/
                            /*operateHtml = operateHtml + '<button class="btn btn-primary btn-xs" type="button" onclick="detail(\''+row.logId+'\')"><i class="fa fa-check"></i>&nbsp;详情</button> &nbsp;';*/
                            return operateHtml;
                        }
                    }]
                });
            });

            function detailFormatter(index, row) {
                var html = [];
                html.push('<p><b>描述:</b> ' + row.description + '</p>');
                return html.join('');
            }

            function getQueryParams(params){
                var params={
                    "pageNum":params.pageNum,
                    "pageSize":params.pageSize,
                    "startRow":params.startRow,
                    "endRow":params.endRow,
                    "total":params.total,
                    "pages":params.pages,
                    "count":params.count,
					"playerName":$("#playerName").val(),
					"friendName":$("#friendName").val()
                }
                return params;
            }
            function search() {
				var params={
					"playerName":$("#playerName").val(),
					"friendName":$("#friendName").val()
				}
                $('#helpListTable').bootstrapTable("refresh");
            }
            function add(){
                layer.open({
                    type: 2,
                    title: '添加${title}',
                    shadeClose: true,
                    shade: false,
                    area: ['800px', '600px'],
                    content: '${ctx}/${actionPath}/add/',
                    end: function(index){
                        $('#helpListTable').bootstrapTable("refresh");
                    }
                });
            }
            function edit(id){
                layer.open({
					type: 2,
                    title: '编辑${title}',
                    shadeClose: true,
                    shade: false,
                    area: ['800px', '600px'],
                    content: '${ctx}/${actionPath}/edit/'  + id,
                    end: function(index){
                        $('#helpListTable').bootstrapTable("refresh");
                    }
                });
            }
            function del(id){
                layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "${ctx}/${actionPath}/delete/" + id,
                        success: function(msg){
                            layer.msg(msg.msg, {time: 1500},function(){
                                $('#helpListTable').bootstrapTable("refresh");
                                layer.close(index);
                            });
                        }
                    });
                });
            }
			function detail(id) {

				layer.open({
					type: 2,
					title: '${title}详情',
					shadeClose: true,
					shade: false,
					area: ['800px', '600px'],
					content: '${ctx}/${actionPath}/get/'  + id,
					end: function(index){
						$('#helpListTable').bootstrapTable("refresh");
					}
				});
			}
		</script>
</body>
</html>
