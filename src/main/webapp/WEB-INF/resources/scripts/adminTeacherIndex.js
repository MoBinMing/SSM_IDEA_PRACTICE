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
function deleteTeacher(id,name){
    var an = confirm("确定删除\""+name+"\"?");
    if (an == true) {
        if (id!=""){
            let deleteUrl = getRootPath() + "/Admin/deleteTeacher/"+id;

            $.get(deleteUrl, function(data){
                if (data.msg==="succeed"){
                    if (count(data.deleteTeachers)==0) {
                        var hint="<div class=\"alert alert-danger alert-dismissible\" id=\"teacherdangerHint\">\n" +
                            "        <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n" +
                            "        <strong id=\"teacherDangerHintTitle\">提示：</strong> <span id=\"teacherDangerHintBody\">当前数据库无教师!</span>\n" +
                            "      </div>";
                        $("#searchHint").html(hint).show(300);
                        $("#teachersTbody").html("");
                    }else {
                        $("#teachersTbody").html(createTeacherCellHtml(data.deleteTeachers));
                    }
                    $("#hint").html(getHint("提示","删除成功","succeed")).show(300).delay(2000).hide(300);
                }else {
                    $("#hint").html(getHint("提示",data.msg,"error")).show(300).delay(3000).hide(300);
                }
            });
        }
    } else {
        return false;
    }
}

function validTeacher(obj,teacherId) {
    let valid= $(obj).val();
    let url= getRootPath()+"/Admin/updateTeacherValid";
    switch (valid) {
        case "1":
        case "0":
        case "2":
            if (teacherId!=""){
                $.ajax({
                    type: 'POST',
                    url: url,
                    data: 'valid=' + valid+"&teacherId="+teacherId,
                    dataType: "json",
                    global: false,
                    success: function(data) {
                        //alert(data.practices);
                        if (data.msg=="succeed"){
                            // $("#hintModal").modal('show');
                            // var t1 = window.setTimeout(function() {
                            //     $("#hintModal").modal('hide');
                            // },500);

                            //window.clearTimeout(t1);  // 去除定时器

                            $("#hint").html(getHint("提示",data.info,"succeed")).show(300).delay(3000).hide(300);
                        }else {
                            $("#hint").html(getHint("提示",data.msg,"no")).show(300).delay(3000).hide(300);
                        }
                    }
                });
            }
            break;
        default:
            break;
    }

}
function keyup_submit(e){
    let evt = window.event || e;
    if (evt.keyCode === 13){
        searchTeachers("admin");
    }
}

function searchTeachers(apiRole) {
    let kw;
    let teachersTbody;
    if (apiRole==="admin"){
       kw = $("#searchTeachersVal").val()+"";
        teachersTbody= $("#teachersTbody");
        let requestUrl = getRootPath() + "/Admin/searchTeachers";
        $.ajax({
            type: 'POST',
            url: requestUrl,
            data: "kw="+kw,
            dataType: "json",
            global: false,
            success: function(data) {
                if (data.result==="succeed"){
                    if (count(data.teachers)>0){
                        teachersTbody.html(createTeacherCellHtml(data.teachers));
                    }else {
                        teachersTbody.html("");
                    }
                    $("#hint").html(getHint("提示","获取到 \""+count(data.teachers)+"\" 条数据","succeed"))
                        .show(300).delay(3000).hide(300);
                }else {
                    $("#hint").html(getHint("提示",data.info,"error")).show(300).delay(10000).hide(300);
                }
            }
        });
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
function updateTeacher(teacherId) {
    let iphone=$("#teacherIphone"+teacherId).val().replace(/\s+/g,"");
    let name=$("#teacherName"+teacherId).val().replace(/\s+/g,"");
    let email=$("#teacherEmail"+teacherId).val().replace(/\s+/g,"");
    let password=$("#teacherPassword"+teacherId).val().replace(/\s+/g,"");
    let gender=$("#teacherGender"+teacherId).val().replace(/\s+/g,"");
    let errorInfo="";
    if (checkPhone(iphone)){
        if (name!==""){
            if (checkMail(email)){
                if (password.length>0 && password!==""){
                    if (gender === "男" || gender === "女"){
                        let requestUrl=getRootPath() + "/Admin/updateTeacherUrl";
                        $.ajax({
                            type: 'POST',
                            url: requestUrl,
                            data: 'teacherId=' + teacherId +"&iphone="+iphone+"&name="+name+"&email="
                                +email+"&password="+password+"&gender="+gender,
                            dataType: "json",
                            global: false,
                            success: function(data) {
                                if (data.msg==="succeed"){
                                    $("#teacherIphone"+teacherId).val(iphone);
                                    $("#teacherName"+teacherId).val(name);
                                    $("#teacherEmail"+teacherId).val(email);
                                    $("#teacherPassword"+teacherId).val(password);
                                    $("#teacherGender"+teacherId).val(gender);
                                    $("#hint").html(getHint("提示","更新教师信息成功","succeed")).show(300).delay(3000).hide(300);
                                    return false;
                                }else {
                                    $("#hint").html(getHint("提示","更新教师信息失败:\n"+data.msg,"error")).show(300).delay(3000).hide(300);
                                    return false;
                                }
                            }
                        });
                    }else {
                        errorInfo="姓名不合法";
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
    if (errorInfo!=""){
        $("#hint").html(getHint("提示：",errorInfo,"error")).show(300).delay(3000).hide(300);
    }
    return false;
}
function checkPhone(phone){
    return /^1[3456789]\d{9}$/.test(phone);
}
function checkMail(email) {
    var reg = /^\w+((.\w+)|(-\w+))@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+).[A-Za-z0-9]+$/; //正则表达式
    return reg.test(email);
}
function createTeacherCellHtml(teachers) {
    var html="";
        for (var j = 0; j < teachers.length; j++) {
            let teacher=teachers[j];
            html = html + "<tr>\n" +
                "        <td id=\"teacherId"+teacher.teacherId+"\">\n" +
                "          "+teacher.teacherId+"\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <input type=\"number\" class=\"\" id=\"teacherIphone"+teacher.teacherId+"\" style=\"max-width: 130px;\"\n" +
                "                    value=\""+teacher.iphone+"\" oninput=\"value=value.replace(/[^\\d]/g,'');\n" +
                "                         if(value.length>11)value=value.slice(0,11); \">\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <input type=\"text\" class=\"\" id=\"teacherName"+teacher.teacherId+"\" style=\"max-width: 60px;\"\n" +
                "                   value=\""+teacher.name+"\" oninput=\"\n" +
                "                         if(value.length>5)value=value.slice(0,5); \">\n" +
                "        </td>\n" +
                "        <td>\n" +
                "          <select id=\"teacherGender"+teacher.teacherId+"\" style=\"width: auto;\">\n";
                if (teacher.gender==="男"){
                    html=html+"<option value=\"女\" >女</option>\n";
                    html=html+"<option value=\"男\" selected>男</option>\n";
                }else{
                    html=html+"<option value=\"女\" selected>女</option>\n";
                    html=html+"<option value=\"男\" >男</option>\n";
                }
            html=html+
                "          </select>\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <input type=\"text\" class=\"\" id=\"teacherEmail"+teacher.teacherId+"\" style=\"max-width: 180px;\"\n" +
                "                   value=\""+teacher.email+"\">\n" +
                "        </td>\n" +
                "        <td>\n" +
                "            <input type=\"text\" class=\"\" id=\"teacherPassword"+teacher.teacherId+"\" style=\"max-width: 60px;\"\n" +
                "                   value=\""+teacher.password+"\">\n" +
                "        </td>\n" +
                "        <td>\n" +
                "          <button type=\"button\" class=\"btn btn-secondary btn-sm\"\n" +
                "                  onclick=\"return updateTeacher('"+teacher.teacherId+"');\">更新</button>\n" +
                "        </td>\n" +
                "        <td>\n" +
                "          <select id=\"teacherValid"+teacher.teacherId+"\" style=\"width: auto;\"\n" +
                "                  onchange=\"validTeacher(this,'"+teacher.teacherId+"')\">\n" ;
                if (teacher.valid==1){
                    html=html+"<option value=\"1\" selected>授权</option>\n";
                    html=html+"<option value=\"2\" >拒绝</option>\n";
                    html=html+"<option value=\"0\" >未授权</option>\n";
                }else if (teacher.valid==2){
                    html=html+"<option value=\"1\" >授权</option>\n";
                    html=html+"<option value=\"2\" selected>拒绝</option>\n";
                    html=html+"<option value=\"0\" >未授权</option>\n";
                }else if (teacher.valid == 0){
                    html=html+"<option value=\"1\" >授权</option>\n";
                    html=html+"<option value=\"2\" >拒绝</option>\n";
                    html=html+"<option value=\"0\" selected>未授权</option>\n";
                }
                html=html+
                "          </select>\n" +
                "        </td>\n" +
                "        <td>\n" +
                "          <button type=\"button\" class=\"btn btn-danger btn-sm\" " +
                "               onclick=\"deleteTeacher('"+teacher.teacherId+"','"+teacher.name+"');\">删除</button>\n" +
                "        </td>\n" +
                "      </tr>";
        }
     return html;
}

/* bootstrap开关控件，初始化 */
function onLi() {
    $('.switch input').bootstrapSwitch();
}