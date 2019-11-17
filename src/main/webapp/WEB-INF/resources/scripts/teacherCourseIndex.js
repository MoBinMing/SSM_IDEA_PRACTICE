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

function keyup_submit(e){
    let evt = window.event || e;
    if (evt.keyCode === 13){
        searchCourses();
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
//region 1、课程js

//region 1.1、添加课程信息不全提示框
$(function() {
    $('#addCourseModal').on('hidden.bs.modal', function() {
        $("#myAlert2").css("visibility", "hidden");
        $("#myAlert2").css("display", "none");
    });
});
//endregion

//region 1.2、监听课程搜索框，实时搜索
$(document).ready(function() {
    $("#searchCoursesVal").keyup(function() {
        searchCourses();
    });
});
//endregion

//region 1.3、搜索课程
function searchCourses() {
    let val = $("#searchCoursesVal").val();
    //var coursesTbody = $("#coursesTbody");
    let url = getRootPath() + "/Teacher/searchCourses";
    //alert(url);
    if (val == null && val == ""){
        return false;
    }
    $.ajax({
        type: 'POST',
        url: url,
        data: 'val=' + val,
        dataType: "json",
        global: false,
        success: function(data) {
            let ages = data.ages;
            let courses = data.courses;
            initHtml(ages,courses);
            if (count(courses)>0){
                $("#hint").html(getHint("提示","搜索到"+count(courses)+"条数据！",
                    "succeed")).show(300).delay(3000).hide(300);
            }else {
                $("#hint").html(getHint("提示","当前搜索关键词无数据！",
                    "error")).show(300).delay(3000).hide(300);
            }
            return false;
            //coursesTbody.append(data.tbody);
        }
    });
}

function initHtml(ages,courses) {
    if (count(ages) !== 0){
        var agesHtml = "<button class=\"btn btn-outline-dark dropdown-toggle p-1\" data-toggle=\"dropdown\">"+ ages[0] +"</button>";
        agesHtml = agesHtml + "<div class=\"dropdown-menu\">";
        for (var i=1;i<ages.length;i++){
            agesHtml = agesHtml + "<a class=\"dropdown-item\" href=\"#\">" + ages[i] + "</a>";
            if (i !== ages.length-1){
                agesHtml = agesHtml + "<div class=\"dropdown-divider\"></div>";
            }
        }
        agesHtml = agesHtml + "</div>";
        $("#courseAges").html(agesHtml);
    }
        var tableBodyHtml = "";
        for (var j=0;j<courses.length;j++){
            tableBodyHtml = tableBodyHtml + "<tr onclick=\"location.href='"+ getRootPath() +
                "/Teacher/getPracticeByCourseId/"+ courses[j].id + "';\">";
            // 遍历单条信息
            tableBodyHtml = tableBodyHtml + '<td>' + courses[j].age + '</td>';
            tableBodyHtml = tableBodyHtml + '<td><a href='+ getRootPath()+'/Teacher/getPracticeByCourseId/'+ courses[j].id + '>' + courses[j].name + '</a></td>';
            tableBodyHtml = tableBodyHtml + '<td>' + courses[j].intro + '</td>';
            tableBodyHtml = tableBodyHtml + '<td>' + courses[j].practiceSize + '</td>';
            tableBodyHtml = tableBodyHtml + '<td class="td-actions text-left">' +
                '<div class="form-button-action">' +
                '<button type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger">' +
                '<i class="fa fa-trash-o" aria-hidden="true" onclick="deleteCourse(' +courses[j].id + ');' +
                '       event.stopPropagation();">&nbsp;删除</i>' +
                '</button>' +
                '</div>' +
                '</td>';
            tableBodyHtml = tableBodyHtml + '</tr>';
        }
        $("#coursesTbody").html(tableBodyHtml);

}
//endregion


//endregion

//region 2、练习js
function toPractice(id){
    var url = getRootPath() + "/Teacher/getPracticeByCourseId/" + id;
    //$("#contentView").html(''); //加载页面的div先清空
    window.location.href = url;
}
//endregion

function deleteCourse(id) {
    if (id != null && id !== ""){
        var an = confirm("确定删除？");
        if (an) {
            $.get(getRootPath() + "/Teacher/deleteCourse/" + id, function(data){
                switch (data.result) {
                    case -1:
                        //删除报错
                        $("#hint").html(getHint("提示","删除失败"+data.e,
                            "error")).show(300).delay(3000).hide(300);
                        break;
                    case 0:
                        //删除失败
                        $("#hint").html(getHint("提示","删除失败",
                            "error")).show(300).delay(3000).hide(300);
                        break;
                    case 1:
                        //删除成功
                        $("#hint").html(getHint("提示","删除成功",
                            "succeed")).show(300).delay(3000).hide(300);
                        initHtml([],data.courses);
                        break;
                    case 2:
                        $("#hint").html(getHint("提示","无权限",
                            "error")).show(300).delay(3000).hide(300);
                        //无权限删除该课程
                        break;
                    case 3:
                        //课程不存在
                        $("#hint").html(getHint("提示","课程不存在，"+data.msg,
                            "error")).show(300).delay(3000).hide(300);
                        break;
                    case 4:
                        //登录超时
                        window.location.href = getRootPath();
                        break;
                    default:
                        break;
                }
            });
        }
    }
    return false;
}
