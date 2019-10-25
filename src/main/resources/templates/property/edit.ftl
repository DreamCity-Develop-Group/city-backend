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
                        <input type="hidden" id="inId" name="inId" value="${data.inId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input id="inName" name="inName" class="form-control" type="text" value="${data.inName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">限额：</label>
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
                            <label class="col-sm-3 control-label">收益倍数：</label>
                            <div class="col-sm-8">
                                <input id="inEarning" name="inEarning" class="form-control" type="text" value="${data.inEarning}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否可投：</label>
                            <div class="col-sm-8">
                                <select name="isValid" class="form-control">
                                    <option value="0" <#if data.isValid == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if data.isValid == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">投资结束时间：</label>
                            <div class="col-sm-8">
                                <input id="sendTime" name="inEnd"
                                       class="laydate-icon form-control"
                                       value="${data.inEnd}">
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
            laydate({
                elem: '#sendTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus', //响应事件。如果没有传入event，则按照默认的click
                format: 'YYYY-MM-DD hh:mm:ss',
                istime: true
            });

            $("#updateForm").validate({
                rules: {
                    inName: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },inLimit: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },inTax: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },inEarning: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },isValid: {
                        required: true
                    },inEnd: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
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
