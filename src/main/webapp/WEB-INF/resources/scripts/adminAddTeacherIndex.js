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
function count(o){
    var t = typeof o;
    if(t == 'string'){
        return o.length;
    }else if(t == 'object'){
        var n = 0;
        for(var i in o){
            n++;
        }
        return n;
    }
    return false;
}
function addTeacher(){
    let teacherId=$("#teacherId").val().replace(/\s+/g,"");
    let iphone=$("#teacherIphone").val().replace(/\s+/g,"");
    let name=$("#teacherName").val().replace(/\s+/g,"");
    let email=$("#teacherEmail").val().replace(/\s+/g,"");
    let password=$("#teacherPassword").val().replace(/\s+/g,"");
    let password1=$("#teacherPassword1").val().replace(/\s+/g,"");
    let gender=$("#teacherGender").val().replace(/\s+/g,"");
    let valid=$("#teacherValid").val().replace(/\s+/g,"");
    let errorInfo="";
    if (teacherId!=""){
        if (checkPhone(iphone)){
            if (name!==""){
                if (checkMail(email)){
                    if (password.length>0 && password1.length>0){
                        if (password===password1){
                            if (gender!==""){
                                if (valid!==""){
                                    let requestUrl=getRootPath() + "/Admin/addTeacherUrl";
                                    $.ajax({
                                        type: 'POST',
                                        url: requestUrl,
                                        data: 'teacherId=' + teacherId +"&iphone="+iphone+"&name="+name+"&email="
                                            +email+"&password="+password+"&gender="+gender+"&valid="+valid,
                                        dataType: "json",
                                        global: false,
                                        success: function(data) {
                                            if (data.result==="succeed"){
                                                $("#teacherId").val("");
                                                $("#teacherIphone").val("");
                                                $("#teacherName").val("");
                                                $("#teacherEmail").val("");
                                                $("#teacherPassword").val("");
                                                $("#teacherPassword1").val("");
                                                $("#hint").html(getHint("提示","添加教师成功","succeed")).show(300).delay(3000).hide(300);
                                                return false;
                                            }else {
                                                $("#hint").html(getHint("提示",data.info,"error")).show(300).delay(3000).hide(300);
                                                return false;
                                            }
                                        }
                                    });
                                }else {
                                    errorInfo="授权获取失败";
                                }
                            }else {
                                errorInfo="姓别获取失败";
                            }
                        }else {
                            errorInfo="密码不一致，请确认密码";
                        }
                    }else {
                        errorInfo="密码不能为空";
                    }
                }else {
                    errorInfo="邮箱不合法";
                }
            }else {
                errorInfo="姓名不能为空";
            }
        }else {
            errorInfo="手机号码不合法";
        }
    }else {
        errorInfo="教工号不合法";
    }
    if (errorInfo!=""){
        $("#hint").html(getHint("提示：",errorInfo,"error")).show(300).delay(3000).hide(300);
    }
    return false;
}
function addTeachers() {
    let teacherPw=$("#teacherPw").val()+"";
    if (teacherPw!==""){
        let val=$("#teachersVal").text().replace(/\s+/g,",");;
        let vals=val.split(',');;
        var teacherIds=[];
        for (var i = 0; i < vals.length; i++) {
            if (vals[i]!==""){
                teacherIds.push(vals[i]);
            }
        }
        if (teacherIds.length>0){
            let requestUrl=getRootPath() + "/Admin/addTeachersUrl/"+teacherPw;
            $.ajax({
                type: "post",//注意不能用get
                dataType: 'json',
                url: requestUrl,
                contentType: 'application/json',//这个必须是这个格式
                data: JSON.stringify(teacherIds),//前台要封装成json格式
                success: function (data) {
                    if (data.result==="succeed"){
                        $("#hint").html(getHint("提示","添加成功\n"+
                            "成功："+data.succeed+"个；\n失败："+data.defeated+"个；\n以注册的教师id：\n"+
                            data.defeatedIds,"succeed")).show(300).delay(3000).hide(300);
                    }else {
                        $("#hint").html(getHint("提示",data.info,"error")).show(300).delay(3000).hide(300);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $("#hint").html(getHint("提示",errorThrown.toString(),"error")).show(300).delay(20000).hide(300);
                }
            });
        }else {
            $("#hint").html(getHint("提示","请输入教工号！","error")).show(300).delay(5000).hide(300);
        }
    }else {
        $("#hint").html(getHint("提示","请设置默认密码！","error")).show(300).delay(5000).hide(300);
    }

}
function getHint(title,body,sta) {
    if (sta=="succeed"){
        return "  <div class=\"alert alert-success alert-dismissible m-0\">\n" +
            "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n" +
            "    <strong >"+title+"</strong> <span>"+body+"</span>\n" +
            "  </div>";
    }else {
        return "  <div class=\"alert alert-danger alert-dismissible m-0\">\n" +
            "    <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n" +
            "    <strong >"+title+"</strong> <span>"+body+"</span>\n" +
            "  </div>";
    }
}
function checkPhone(phone){
    return /^1[3456789]\d{9}$/.test(phone);
}
function checkMail(email) {
    var reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/; //正则表达式
    return reg.test(email);
}

