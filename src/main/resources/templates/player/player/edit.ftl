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
                        <input type="hidden" id="id" name="id" value="${data.id}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>
                            <div class="col-sm-8">
                                <input id="playerName" name="playerName" class="form-control" type="text" value="${data.playerName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">昵称：</label>
                            <div class="col-sm-8">
                                <input id="playerNick" name="playerNick" class="form-control" type="text" value="${data.playerNick}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">等级：</label>
                            <div class="col-sm-8">
                                <input id="playerLevel" name="playerLevel" class="form-control" type="text" value="${data.playerLevel}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否有效：</label>
                            <div class="col-sm-8">
                                <select name="isValid" class="form-control">
                                    <option value="0" <#if data.isValid == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if data.isValid == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <#--<div class="form-group">
                            <label class="col-sm-3 control-label">注册时间：</label>
                            <div class="col-sm-8">
                                <input id="createTime" name="createTime"
                                       class="laydate-icon form-control"
                                       value="${data.createTime}">
                            </div>
                        </div>-->
                        <#--<div class="form-group">
                            <label class="col-sm-3 control-label">头像：</label>
                            <div class="col-sm-8">
                                <#if data.imgur? exists>
                                    <img id="imgurl" class="pull-left" src="${ctx}/${data.imgurl}">
                                <#else>
                                    <img id="imgurl" class="pull-left" src="${ctx}/avatars/avatar5.png">
                                </#if>
                            </div>
                        </div>-->
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button class="btn btn-primary" type="submit">提交</button>
                            </div>
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
                        maxlength: 60
                    },itemState: {
                        required: true
                    },itemDesc: {
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
