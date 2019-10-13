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
                            <label class="col-sm-3 control-label">转账人：</label>
                            <div class="col-sm-8">
                                <input id="playerName" name="playerName" class="form-control" readonly="readonly" type="text" value="${data.playerName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">转账金额：</label>
                            <div class="col-sm-8">
                                <input id="tradeAmount" name="tradeAmount" class="form-control" readonly="readonly" type="text" value="${data.tradeAmount}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">转账所得税：</label>
                            <div class="col-sm-8">
                                <input id="personalTax" name="personalTax" class="form-control" readonly="readonly" type="text" value="${data.personalTax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">转账描述：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" id="tradeDesc" name="tradeDesc" readonly="readonly"
                                          placeholder="Default Text">${data.tradeDesc}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">审核状态：</label>
                            <div class="col-sm-8">
                                <select name="verifyStatus" class="form-control">
                                    <option value="WAIT" <#if data.verifyStatus == "WAIT">selected="selected"</#if>>待审核</option>
                                    <option value="PASS" <#if data.verifyStatus == "PASS">selected="selected"</#if>>通过</option>
                                    <option value="NOTPASS" <#if data.verifyStatus == "NOTPASS">selected="selected"</#if>>不通过</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">审核意见：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" id="verifyDesc" name="verifyDesc" placeholder="Default Text">${data.verifyDesc}</textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <#if edit && data.verifyStatus == "WAIT">
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
                    verifyStatus: {
                        required: true
                    },verifyDesc: {
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
                        url: "${ctx}/verify/transferVerify",
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
