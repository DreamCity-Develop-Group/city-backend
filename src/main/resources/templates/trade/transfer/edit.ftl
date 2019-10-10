<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>${title}</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/css/font-awesome.min.css" />
    <link href="${ctx}/css/animate.css" rel="stylesheet">
    <#--<link href="${ctx}/backend/css/style.css" rel="stylesheet">-->


</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="updateForm">
                        <input type="hidden" id="tradeId" name="tradeId" value="${data.tradeId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">交易人：</label>
                            <div class="col-sm-8">
                                <input id="playerName" name="playerName" class="form-control" type="text" value="${data.playerName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">交易金额：</label>
                            <div class="col-sm-8">
                                <input id="tradeAmount" name="tradeAmount" class="form-control" type="text" value="${data.tradeAmount}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">个人所得税：</label>
                            <div class="col-sm-8">
                                <input id="personalTax" name="personalTax" class="form-control" type="text" value="${data.personalTax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">企业所得税：</label>
                            <div class="col-sm-8">
                                <input id="enterpriseTax" name="enterpriseTax" class="form-control" type="text" value="${data.enterpriseTax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">交易类型：</label>
                            <div class="col-sm-8">
                                <select id="tradeType" name="tradeType" class="">
                                    <option value=""></option>
                                    <#list tradeTypes as item>
                                        <option value="${item.getCode()}" <#if '${item.getCode()}' == '${data.tradeType}'>selected="selected"</#if>>${item.getDesc()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">交易状态：</label>
                            <div class="col-sm-8">
                                <select id="tradeStatus" name="tradeStatus" class="">
                                    <option value=""></option>
                                    <#list tradeStatuss as item>
                                        <option value="${item.getCode()}" <#if '${item.getCode()}' == '${data.tradeStatus}'>selected="selected"</#if>>${item.getDesc()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">入账还是出账：</label>
                            <div class="col-sm-8">
                                <select id="inOut" name="inOut" class="">
                                    <option value=""></option>
                                    <#list dynTypes as item>
                                        <option value="${item.getCode()}" <#if '${item.getCode()}' == '${data.inOutStatus}'>selected="selected"</#if>>${item.getDesc()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">审核状态：</label>
                            <div class="col-sm-8">
                                <select id="verifyStatus" name="verifyStatus" class="">
                                    <option value=""></option>
                                    <#list verifyStatuss as item>
                                        <option value="${item.getCode()}" <#if '${item.getCode()}' == '${data.verifyStatus}'>selected="selected"</#if>>${item.getDesc()}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">交易描述：</label>
                            <div class="col-sm-8">
                                <input id="tradeDesc" name="tradeDesc" class="form-control" value="${data.tradeDesc}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">交易时间：</label>
                            <div class="col-sm-8">
                                <input id="createTime" name="createTime" readonly="readonly"
                                       class="laydate-icon form-control"
                                       value="${data.createTime}">
                            </div>
                        </div>
                        <div class="form-group">
                            <#if edit>
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
                            </#if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<!-- 全局js -->
<#include "${ctx}/common.ftl">
    <script type="text/javascript">

        $(document).ready(function () {
            //外部js调用
            /*laydate({
                elem: '#sendTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus', //响应事件。如果没有传入event，则按照默认的click
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });*/

            $("#updateForm").validate({
                rules: {
                    playerName: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },tradeAmount: {
                        required: true
                    },personalTax: {
                        required: true
                    },enterpriseTax: {
                        required: true
                    },tradeType: {
                        required: true
                    },tradeStatus: {
                        required: true
                    },inOut: {
                        required: true
                    },tradeDesc: {
                        required: true,
                        minlength: 1,
                        maxlength: 600
                    }
                },
                messages: {},
                submitHandler:function(form){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        url: "${ctx}/${actionPath}/update",
                        data: $(form).serialize(),
                        success: function(msg){
                            layer.msg(msg.msg, {time: 2000},function(){
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index);
                            });
                        },
                        error: function(msg){
                            layer.msg(msg.msg, {time: 6000},function(){
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index);
                            });
                        }
                    });
                }
            });
        });


    </script>

</body>

</html>
