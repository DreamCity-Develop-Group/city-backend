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
                            <label class="col-sm-3 control-label">名称：</label>
                            <div class="col-sm-8">
                                <input id="name" name="name" class="form-control" type="text" value="${data.name}" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">类别：</label>
                            <div class="col-sm-8">
                                <input id="type" name="type" class="form-control" type="text" value="${data.type}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">是否可用：</label>
                            <div class="col-sm-8">
                                <select name="isValid" class="form-control">
                                    <option value="0" <#if data.isValid == 0>selected="selected"</#if>>否</option>
                                    <option value="1" <#if data.isValid == 1>selected="selected"</#if>>是</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">内容：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" id="content" name="content" placeholder="Default Text">${data.content}</textarea>
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
                    name: {
                        required: true,
                        minlength: 1,
                        maxlength: 50
                    },
                    content: {
                        required: true,
                        minlength: 1,
                        maxlength: 600
                    },
                    type: {
                        required: true
                    },
                    isValid: {
                        required: true
                    },
                    descr: {
                        required: false,
                        maxlength: 60
                    }
                },
                messages: {},
                submitHandler:function(form){
                    $.ajax({
                        type: "POST",
                        dataType: "json",
                        //contentType: "application/json;charset=UTF-8",
                        url: "${ctx}/other/help/update",
                        //data: JSON.stringify($(form).serialize()),
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
