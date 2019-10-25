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
                        <form class="form-horizontal m-t" id="addForm">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">玩家ID：</label>
                                <div class="col-sm-8">
                                    <input id="playerId" name="playerId" class="form-control" type="text" value="">
                                    <#--<select id="itemName" name="itemName" class="form-control">
                                        <#list items as item>
                                            <option value="${item.itemName}">${item.itemName}</option>
                                        </#list>
                                    </select>-->
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">玩家昵称：</label>
                                <div class="col-sm-8">
                                    <input id="playerName" name="playerName" class="form-control" value="">
                                </div>
                                <#--<div class="col-sm-8">
                                    <select id="itemFlag" name="itemFlag" class="form-control">
                                        <#list items as item>
                                            <option value="${item.itemFlag}">${item.itemName}(${item.itemFlag})</option>
                                        </#list>
                                    </select>
                                </div>-->
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">钱包地址(可修改)：</label>
                                <div class="col-sm-8">
                                    <input id="accAddr" name="accAddr" class="form-control" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">MT：</label>
                                <div class="col-sm-8">
                                    <input id="accMt" name="accMt" class="form-control" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">MT总量：</label>
                                <div class="col-sm-8">
                                    <input id="mtQuantum" name="mtQuantum" class="form-control" value="0(初始数)">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">顶级：</label>
                                <div class="col-sm-8">
                                    <input id="superior" name="superior" class="form-control" value="System(默认)">
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="col-sm-8 col-sm-offset-3">
                                    <button class="btn btn-primary" type="button"  onclick="addGenesis()">生成创世号</button>
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



        function addGenesis(){
            var playerIds = document.getElementById("playerId").value;
            var playerNames = document.getElementById("playerName").value;
            var accAddrs = document.getElementById("accAddr").value;
            var accMts = document.getElementById("accMt").value;
            $.ajax({
                type:"post",
                //url: "http://localhost:8001/player/genesis/addGenesis",
                url: "${ctx}/${actionPath}/addGenesis",
                data:{
                    playerId : playerIds,
                    playerName : playerNames,
                    accAddr:accAddrs,
                    accMt : accMts,
                    accAddr : accAddrs
                },
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




    $(document).ready(function () {
        //外部js调用
        /*laydate({
            elem: '#sendTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
            event: 'focus', //响应事件。如果没有传入event，则按照默认的click
            format: 'YYYY-MM-DD hh:mm:ss',
            istime: true
        });*/

	    $("#addForm").validate({
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
   	    		   url: "${ctx}/${actionPath}/insert",
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
