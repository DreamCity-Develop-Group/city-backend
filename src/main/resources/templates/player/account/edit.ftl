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

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="updateForm">
                        <input type="hidden" id="accId" name="accId" value="${data.accId}">
                        <input type="hidden" id="playerId" name="playerId" value="${data.playerId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">玩家：</label>
                            <div class="col-sm-8">
                                <input id="username" name="username" class="form-control" type="text" value="${data.playerName}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">账户地址：</label>
                            <div class="col-sm-8">
                                <input id="accAddr" name="accAddr" class="form-control" type="text" value="${data.accAddr}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">USDT总额：</label>
                            <div class="col-sm-8">
                                <input id="accUsdt" name="accUsdt" class="form-control" type="text" value="${data.accUsdt}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">USDT可用：</label>
                            <div class="col-sm-8">
                                <input id="accUsdtAvailable" name="accUsdtAvailable" class="form-control" type="text" value="${data.accUsdtAvailable}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">USDT冻结：</label>
                            <div class="col-sm-8">
                                <input id="accUsdtFreeze" name="accUsdtFreeze" class="form-control" type="text" value="${data.accUsdtFreeze}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">MT总额：</label>
                            <div class="col-sm-8">
                                <input id="accMt" name="accMt" class="form-control" type="text" value="${data.accMt}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">MT可用：</label>
                            <div class="col-sm-8">
                                <input id="accMtAvailable" name="accMtAvailable" class="form-control" type="text" value="${data.accMtAvailable}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">MT冻结：</label>
                            <div class="col-sm-8">
                                <input id="accMtFreeze" name="accMtFreeze" class="form-control" type="text" value="${data.totalIncome}" readonly="readonly">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">收益：</label>
                            <div class="col-sm-8">
                                <input id="totalIncome" name="totalIncome" class="form-control" type="text" value="${data.totalIncome}" readonly="readonly">
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

            $("#updateForm").validate({
                rules: {
                    username: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },accUsdt: {
                        required: true,
                        minlength: 1,
                        maxlength: 60,
                    },accUsdtAvailable: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },accUsdtFreeze: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },accMt: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },accMtAvailable: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },accMtFreeze: {
                        required: true,
                        minlength: 1,
                        maxlength: 60
                    },totalIncome: {
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
