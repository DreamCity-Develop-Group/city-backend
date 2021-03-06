<div class="sidebar" id="sidebar">
    <script type="text/javascript">
        try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
    </script>

    <div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="icon-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="icon-pencil"></i>
            </button>

            <button class="btn btn-warning">
                <i class="icon-group"></i>
            </button>

            <button class="btn btn-danger">
                <i class="icon-cogs"></i>
            </button>
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- #sidebar-shortcuts -->

    <ul class="nav nav-list">
        <li class="active">
            <a href="/admin/index">
                <i class="icon-dashboard"></i>
                <span class="menu-text"> 控制台 </span>
            </a>
        </li>

        <#--<li>
            <a href="typography.html">
                <i class="icon-text-width"></i>
                <span class="menu-text"> 文字排版 </span>
            </a>
        </li>-->

        <#--<li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-desktop"></i>
                <span class="menu-text"> UI 组件 </span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="elements.html">
                        <i class="icon-double-angle-right"></i>
                        组件
                    </a>
                </li>

                <li>
                    <a href="buttons.html">
                        <i class="icon-double-angle-right"></i>
                        按钮 &amp; 图表
                    </a>
                </li>

                <li>
                    <a href="treeview.html">
                        <i class="icon-double-angle-right"></i>
                        树菜单
                    </a>
                </li>

                <li>
                    <a href="jquery-ui.html">
                        <i class="icon-double-angle-right"></i>
                        jQuery UI
                    </a>
                </li>

                <li>
                    <a href="nestable-list.html">
                        <i class="icon-double-angle-right"></i>
                        可拖拽列表
                    </a>
                </li>

                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-double-angle-right"></i>

                        三级菜单
                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <li>
                            <a href="#">
                                <i class="icon-leaf"></i>
                                第一级
                            </a>
                        </li>

                        <li>
                            <a href="#" class="dropdown-toggle">
                                <i class="icon-pencil"></i>

                                第四级
                                <b class="arrow icon-angle-down"></b>
                            </a>

                            <ul class="submenu">
                                <li>
                                    <a href="#">
                                        <i class="icon-plus"></i>
                                        添加产品
                                    </a>
                                </li>

                                <li>
                                    <a href="#">
                                        <i class="icon-eye-open"></i>
                                        查看商品
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>-->

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-list"></i>
                <span class="menu-text"> 系统管理 </span>
                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="${ctx}/user/index">
                        <i class="icon-double-angle-right"></i>
                        用户管理
                    </a>
                </li>
                <li>
                    <a href="${ctx}/user/account/index">
                        <i class="icon-double-angle-right"></i>
                        平台账户
                    </a>
                </li>
                <li>
                    <a href="${ctx}/role/index">
                        <i class="icon-double-angle-right"></i>
                        角色管理
                    </a>
                </li>
                <#--<li>
                    <a href="${ctx}/menu/index">
                        <i class="icon-double-angle-right"></i>
                        资源管理
                    </a>
                </li>-->
                <li>
                    <a href="${ctx}/other/dic/index">
                        <i class="icon-double-angle-right"></i>
                        系统配置项
                    </a>
                </li>
            </ul>
        </li>

        <#--<li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-edit"></i>
                <span class="menu-text"> 表单 </span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="form-elements.html">
                        <i class="icon-double-angle-right"></i>
                        表单组件
                    </a>
                </li>

                <li>
                    <a href="form-wizard.html">
                        <i class="icon-double-angle-right"></i>
                        向导提示 &amp; 验证
                    </a>
                </li>

                <li>
                    <a href="wysiwyg.html">
                        <i class="icon-double-angle-right"></i>
                        编辑器
                    </a>
                </li>

                <li>
                    <a href="dropzone.html">
                        <i class="icon-double-angle-right"></i>
                        文件上传
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="widgets.html">
                <i class="icon-list-alt"></i>
                <span class="menu-text"> 插件 </span>
            </a>
        </li>

        <li>
            <a href="calendar.html">
                <i class="icon-calendar"></i>

                <span class="menu-text">
									日历
									<span class="badge badge-transparent tooltip-error" title="2&nbsp;Important&nbsp;Events">
										<i class="icon-warning-sign red bigger-130"></i>
									</span>
								</span>
            </a>
        </li>

        <li>
            <a href="gallery.html">
                <i class="icon-picture"></i>
                <span class="menu-text"> 相册 </span>
            </a>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-tag"></i>
                <span class="menu-text"> 更多页面 </span>

                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="profile.html">
                        <i class="icon-double-angle-right"></i>
                        用户信息
                    </a>
                </li>

                <li>
                    <a href="inbox.html">
                        <i class="icon-double-angle-right"></i>
                        收件箱
                    </a>
                </li>

                <li>
                    <a href="pricing.html">
                        <i class="icon-double-angle-right"></i>
                        售价单
                    </a>
                </li>

                <li>
                    <a href="invoice.html">
                        <i class="icon-double-angle-right"></i>
                        购物车
                    </a>
                </li>

                <li>
                    <a href="timeline.html">
                        <i class="icon-double-angle-right"></i>
                        时间轴
                    </a>
                </li>

                <li>
                    <a href="login.html">
                        <i class="icon-double-angle-right"></i>
                        登录 &amp; 注册
                    </a>
                </li>
            </ul>
        </li>-->

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
					玩家管理
				</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">
                <li>
                    <a href="${ctx}/player/account/index">
                        <i class="icon-double-angle-right"></i>
                        玩家账户列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/player/index">
                        <i class="icon-double-angle-right"></i>
                        玩家列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/friend/index">
                        <i class="icon-double-angle-right"></i>
                        好友列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/like/index">
                        <i class="icon-double-angle-right"></i>
                        玩家获赞列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/likelog/index">
                        <i class="icon-double-angle-right"></i>
                        点赞记录列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/game/setting/index">
                        <i class="icon-double-angle-right"></i>
                        玩家游戏设置列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/loginlog/index">
                        <i class="icon-double-angle-right"></i>
                        玩家登陆日志
                    </a>
                </li>
                <li>
                    <a href="${ctx}/player/genesis/index">
                        <i class="icon-double-angle-right"></i>
                        创世账号
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
					物业管理
				</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">
                <li>
                    <a href="${ctx}/property/index">
                        <i class="icon-double-angle-right"></i>
                        物业列表
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
					投资管理
				</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">
                <li>
                    <a href="${ctx}/invest/index?verify=0">
                        <i class="icon-double-angle-right"></i>
                        投资总列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/invest/index?verify=1&orderState=SUBSCRIBED">
                        <i class="icon-double-angle-right"></i>
                        预约待审核列表
                    </a>
                </li>
                <#--<li>
                    <a href="${ctx}/invest/index?verify=1&orderState=INVESTED">
                        <i class="icon-double-angle-right"></i>
                        投资待审核列表
                    </a>
                </li>-->
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
					交易管理
				</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">
                <li>
                    <a href="${ctx}/trade/index">
                        <i class="icon-double-angle-right"></i>
                        交易总列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/trade/recharge/index">
                        <i class="icon-double-angle-right"></i>
                        充值列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/trade/withdraw/index">
                        <i class="icon-double-angle-right"></i>
                        提现列表
                    </a>
                </li>
                <#--<li>
                    <a href="${ctx}/trade/withdraw/index">
                        <i class="icon-double-angle-right"></i>
                        提现预约待审核列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/trade/withdraw/index">
                        <i class="icon-double-angle-right"></i>
                        提现待审核列表
                    </a>
                </li>-->
                <li>
                    <a href="${ctx}/trade/transfer/index">
                        <i class="icon-double-angle-right"></i>
                        转账列表
                    </a>
                </li>
                <#--<li>
                    <a href="${ctx}/trade/transfer/index">
                        <i class="icon-double-angle-right"></i>
                        转账待审核列表（外部）
                    </a>
                </li>-->
                <li>
                    <a href="${ctx}/trade/buymt/index">
                        <i class="icon-double-angle-right"></i>
                        购买MT列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/trade/earning/index">
                        <i class="icon-double-angle-right"></i>
                        玩家收益
                    </a>
                </li>
                <li>
                    <a href="${ctx}/trade/detail/index">
                        <i class="icon-double-angle-right"></i>
                        交易明细列表
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
					任务管理
				</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">
                <li>
                    <a href="${ctx}/schedule/index">
                        <i class="icon-double-angle-right"></i>
                        任务列表
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
					设置管理
				</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">
                <li>
                    <a href="${ctx}/setting/rule/item/index">
                        <i class="icon-double-angle-right"></i>
                        规则项配置
                    </a>
                </li>
                <li>
                    <a href="${ctx}/setting/rule/invest/index">
                        <i class="icon-double-angle-right"></i>
                        投资规则配置
                    </a>
                </li>
            </ul>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-file-alt"></i>
                <span class="menu-text">
						其他页面
						<#--<span class="badge badge-primary ">5</span>-->
					</span>
                <b class="arrow icon-angle-down"></b>
            </a>
            <ul class="submenu">

                <li>
                    <a href="${ctx}/other/message/index">
                        <i class="icon-double-angle-right"></i>
                        通知列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/other/notice/index">
                        <i class="icon-double-angle-right"></i>
                        公告列表
                    </a>
                </li>
                <li>
                    <a href="${ctx}/other/help/index">
                        <i class="icon-double-angle-right"></i>
                        帮助列表
                    </a>
                </li>

                <#--<li>
                    <a href="error-404.html">
                        <i class="icon-double-angle-right"></i>
                        404错误页面
                    </a>
                </li>

                <li>
                    <a href="error-500.html">
                        <i class="icon-double-angle-right"></i>
                        500错误页面
                    </a>
                </li>

                <li>
                    <a href="grid.html">
                        <i class="icon-double-angle-right"></i>
                        网格
                    </a>
                </li>

                <li>
                    <a href="blank.html">
                        <i class="icon-double-angle-right"></i>
                        空白页面
                    </a>
                </li>-->
            </ul>
        </li>
    </ul><!-- /.nav-list -->

    <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
    </div>

    <script type="text/javascript">
        try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
    </script>
</div>
