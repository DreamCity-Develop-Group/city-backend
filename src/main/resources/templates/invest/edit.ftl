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
                        <input type="hidden" id="orderId" name="orderId" value="${data.orderId}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">订单名称：</label>
                            <div class="col-sm-8">
                                <input id="orderName" name="orderName" class="form-control" type="text" readonly="readonly" value="${data.orderName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">订单编号：</label>
                            <div class="col-sm-8">
                                <input id="orderNum" name="orderNum" class="form-control" type="text" readonly="readonly" value="${data.orderNum}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">物业名称：</label>
                            <div class="col-sm-8">
                                <input id="inName" name="inName" class="form-control" type="text" readonly="readonly" value="${data.inName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">玩家：</label>
                            <div class="col-sm-8">
                                <input id="playerName" name="playerName" class="form-control" type="text" readonly="readonly" value="${data.playerName}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">投资金额：</label>
                            <div class="col-sm-8">
                                <input id="orderAmount" name="orderAmount" class="form-control" type="text" readonly="readonly" value="${data.orderAmount}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">收益倍数：</label>
                            <div class="col-sm-8">
                                <input id="inEarning" name="inEarning" class="form-control" type="text" readonly="readonly" value="${data.inEarning}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">个人所得税：</label>
                            <div class="col-sm-8">
                                <input id="personalInTax" name="personalInTax" class="form-control" type="text" readonly="readonly" value="${data.personalInTax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">企业所得税：</label>
                            <div class="col-sm-8">
                                <input id="enterpriseIntax" name="enterpriseIntax" class="form-control" type="text" readonly="readonly" value="${data.enterpriseIntax}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">投资状态：</label>
                            <div class="col-sm-8">
                                <input id="orderState" name="orderState" class="form-control" type="text" readonly="readonly" value="${data.orderState}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否复投：</label>
                            <div class="col-sm-8">
                                <select name="orderRepeat" class="form-control" readonly="readonly">
                                    <option value="0" <#if data.orderRepeat == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if data.orderRepeat == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">投资时间：</label>
                            <div class="col-sm-8">
                                <input id="createTime" name="createTime" readonly="readonly"
                                       class="laydate-icon form-control" value="${data.createTime}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">审核状态：</label>
                            <div class="col-sm-8">
                                <select name="verifyStatus" class="form-control">
                                    <option value="WAIT" <#if data.verifyStatus == "WAIT">selected="selected"</#if>>待审核</option>
                                    <#--<option value="verifying" <#if data.verifyStatus == "VERIFYING">selected="selected"</#if>>审核中</option>-->
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
                    url: "${ctx}/verify/subscribeOrderVerify",
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
