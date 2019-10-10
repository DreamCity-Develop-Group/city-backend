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
                        <input type="hidden" id="ruleId" name="ruleId" value="${data.ruleId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规则项名称：</label>
                            <div class="col-sm-8">
                                <input id="itemName" name="itemName" class="form-control" type="text" value="${data.itemName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规则项类型：</label>
                            <div class="col-sm-8">
                                <input id="itemFlag" name="itemFlag" class="form-control" type="text" value="${data.itemFlag}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规则名称：</label>
                            <div class="col-sm-8">
                                <input id="ruleName" name="ruleName" class="form-control" type="text" value="${data.ruleName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规则类型：</label>
                            <div class="col-sm-8">
                                <input id="ruleFlag" name="ruleFlag" class="form-control" type="text" value="${data.ruleFlag}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">前置条件：</label>
                            <div class="col-sm-8">
                                <input id="ruleOptPre" name="ruleOptPre" class="form-control" type="text" value="${data.ruleOptPre}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">后置条件：</label>
                            <div class="col-sm-8">
                                <input id="ruleOpt" name="ruleOpt" class="form-control" type="text" value="${data.ruleOpt}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">前比率：</label>
                            <div class="col-sm-8">
                                <input id="ruleRatePre" name="ruleRatePre" class="form-control" type="text" value="${data.ruleRatePre}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">后比率：</label>
                            <div class="col-sm-8">
                                <input id="ruleRate" name="ruleRate" class="form-control" type="text" value="${data.ruleRate}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规则优先级别：</label>
                            <div class="col-sm-8">
                                <input id="ruleLevel" name="ruleLevel" class="form-control" type="text" value="${data.ruleLevel}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">规则描述：</label>
                            <div class="col-sm-8">
                                <input id="ruleDesc" name="ruleDesc" class="form-control" value="${data.ruleDesc}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">创建时间：</label>
                            <div class="col-sm-8">
                                <input id="createTime" name="" readonly="readonly"
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
                    itemName: {
                        required: true,
                        minlength: 1,
                        maxlength: 30
                    },itemFlag: {
                        required: true,
                        minlength: 1,
                        maxlength: 30
                    },ruleName: {
                        required: true,
                        minlength: 1,
                        maxlength: 30
                    },ruleFlag: {
                        required: true,
                        minlength: 1,
                        maxlength: 30
                    },ruleOptPre: {
                        required: true,
                        minlength: 1,
                        maxlength: 30
                    },ruleOpt: {
                        required: true,
                        minlength: 1,
                        maxlength: 30
                    },ruleRatePre: {
                        required: true,
                        minlength: 1,
                        maxlength: 11
                    },ruleRate: {
                        required: true,
                        minlength: 1,
                        maxlength: 11
                    },ruleLevel: {
                        required: true,
                        minlength: 1,
                        maxlength: 2
                    },ruleDesc: {
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
