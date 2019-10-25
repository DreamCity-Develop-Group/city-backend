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
                    <form class="form-horizontal m-t" id="updateForm" name="myForm">
                        <input type="hidden" id="id" name="id" value="${data.id}">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">playerId：</label>
                            <div class="col-sm-8">
                                <input id="playerId" name="playerId" class="form-control" type="text" value="${data.playerId}">
                            </div>
                        </div>
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
                            <label class="col-sm-3 control-label">是否有效：</label>
                            <div class="col-sm-8">
                                <select name="isValid" class="form-control">
                                    <option value="0" <#if data.isValid == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if data.isValid == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">拨币：</label>
                            <div class="col-sm-8">
                                <input id="bi" name="bi"
                                       class="form-control"
                                       value="">
                            </div>
                        </div>
                        <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary"  type="button" onclick="edit()">提交</button>
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

     function edit(){
         var elementById = document.getElementById("playerId").value;
         var money = document.getElementById("bi").value;
         // 常用
         $.ajax({
             type:"post",
             //url: "http://localhost:8001/player/player/postAccount",
             url: "${ctx}/${actionPath}/player/postAccount",
             data:{playerId : elementById,money : money},
             dataType:"json",
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
         })

     }



         /*$.ajax({
             type:"get",// get或者post
             url:"abc.php",// 请求的url地址
             data:{},//请求的参数
             dataType:"json",//json写了jq会帮我们转换成数组或者对象 他已经用JSON.parse弄好了
             timeout:3000,//3秒后提示错误
             beforeSend:function(){
                 // 发送之前就会进入这个函数
                 // return false 这个ajax就停止了不会发 如果没有return false 就会继续
             },
             success:function(data){ // 成功拿到结果放到这个函数 data就是拿到的结果
             },
             error:function(){//失败的函数
             },
             complete:function(){//不管成功还是失败 都会进这个函数
             }
         })*/





         /*$.ajax({
             type: "POST",
             dataType: "json",
             //url: '${ctx}/${actionPath}/postAccount/'+elementById,
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
         });*/


    //}

    $(document).ready(function (playerId) {
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
                    url: '${ctx}/${actionPath}/postAccount'+playerId,
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
