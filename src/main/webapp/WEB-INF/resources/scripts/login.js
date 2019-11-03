//很多时候我们需要使用当前项目路径，但是如果把项目路径写死，会带来很多不便，此时就需要自动获取项目路径。
//我们可以根据jquery来进行自动获取项目路径，获取方法如下
function getRootPath() {
    // 1、获取当前全路径，如： http://localhost:8080/springmvc/page/frame/test.html
    var curWwwPath = window.location.href;
    // 获取当前相对路径： /springmvc/page/frame/test.html
    var pathName = window.location.pathname;    // 获取主机地址,如： http://localhost:8080
    var local = curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    // 获取带"/"的项目名，如：/springmvc
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    var rootPath = local + projectName;
    return rootPath;
}
//记住密码
$(document).ready(function(){

    var strName = localStorage.getItem('keyName');
    var strPass = localStorage.getItem('keyPass');
    if(strName){
        $('#iphone').val(strName);
    }if(strPass){
        $('#password').val(strPass);
        var obj = document.getElementById("remember");
        obj.checked = true;
    }
});
//登录javascript
$(function() {
    $("#form1").submit(function() {
        var iphone = $("#iphone").val();
        var password = $("#password").val();
        if (iphone == "") {
            alert("请输入登陆手机号");
            return false;
        }
        if (password == "") {
            alert("请输入密码");
            return false;
        }
        localStorage.setItem('keyName', iphone);
        if ($('#remember').is(':checked')) {
            localStorage.setItem('keyPass', password);
        } else {
            localStorage.removeItem('keyPass');
        }
        var time = Date.parse(new Date());
        let url = getRootPath()+"/Login/LoginUrl";
        $.ajax({
            url: url,
            data: "iphone=" + iphone + "&password=" + password + "&time=" + time,
            type: 'post',
            dataType: 'json',
            beforeSend: function() {
                $("#btn-submit").attr("disabled", true);
                $("#btn-submit").val("正在登陆...");
            },
            success: function(msg) {
                if (msg.msg == "无权限登录，请联系管理员！") {
                    $("#btn-submit").attr("disabled", false);
                    $("#btn-submit").val("登陆");
                    alert(msg.msg);
                } else if (msg.msg == "账号或密码错误！") {
                    $("#btn-submit").attr("disabled", false);
                    $("#btn-submit").val("登陆");
                    alert(msg.msg);
                } else if (msg.msg == "非法请求！") {
                    $("#btn-submit").attr("disabled", false);
                    $("#btn-submit").val("登陆");
                    alert(msg.msg);
                } else {
                    let urlLocation = getRootPath() + msg.msg;
                    window.location.href = urlLocation;
                }
            }
        });
    });
});