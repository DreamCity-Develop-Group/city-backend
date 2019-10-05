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
                        <input type="hidden" id="orderId" name="inId" value="${data.orderId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input id="inName" name="inName" class="form-control" type="text" value="${data.inName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">玩家：</label>
                            <div class="col-sm-8">
                                <input id="payerName" name="payerName" class="form-control" type="text" value="${data.payerName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">投资金额：</label>
                            <div class="col-sm-8">
                                <input id="inLimit" name="inLimit" class="form-control" type="text" value="${data.inLimit}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">税金：</label>
                            <div class="col-sm-8">
                                <input id="inTax" name="inTax" class="form-control" type="text" value="${data.inTax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">状态：</label>
                            <div class="col-sm-8">
                                <input id="orderState" name="orderState" class="form-control" type="text" value="${data.orderState}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否复投：</label>
                            <div class="col-sm-8">
                                <select name="orderRepeat" class="form-control">
                                    <option value="0" <#if data.orderRepeat == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if data.orderRepeat == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">投资时间：</label>
                            <div class="col-sm-8">
                                <input id="createTime" name="createTime"
                                       class="laydate-icon form-control"
                                       value="${data.createTime}">
                            </div>
                        </div>
                        <#--<div class="form-group">
                            <label class="col-sm-3 control-label">内容：</label>
                            <div class="col-sm-8">
                                <input id="content" name="content" class="form-control" value="${data.content}">
                            </div>
                        </div>-->
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

</body>

</html>
