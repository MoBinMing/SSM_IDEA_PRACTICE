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

function addPracticesTest() {
    var name = $("#name").val();
    if (name == "") {
        $("#myAlert2").css("visibility", "visible");
        $("#myAlert2").css("display", "inline");
        return false;
    }
    return true;
}
//添加练习信息不全提示框
$(function() {
    $('#addCourseModal').on('hidden.bs.modal', function() {
        $("#myAlert2").css("visibility", "hidden");
        $("#myAlert2").css("display", "none");
    });
});

//监听input框，实时渲染
$('#searchCoursesVal').on('input', function() {
    searchCourses();
});

function searchCourses() {
    let val = $("#searchCoursesVal").val();
    //var coursesTbody = $("#coursesTbody");
    if (val != null && val != ""){
        let url = getRootPath() + "Teacher/searchCourses";
        $.ajax({
            type: 'POST',
            url: url,
            data: 'val=' + val,
            dataType: "json",
            global: false,
            success: function(data) {
                let ages = data.ages;
                let courses = data.courses;
                renderHtml(ages,courses);
                //coursesTbody.append(data.tbody);
            }
        });
    }

}

function renderHtml(ages,courses) {
    var agesHtml = "<button class=\"btn btn-outline-dark dropdown-toggle p-1\" data-toggle=\"dropdown\">"+ ages[0] +"</button>";
    var tableBodyHtml = "";
    if (ages.length > 1){
        agesHtml = agesHtml + "<div class=\"dropdown-menu\">";
        for (var i=1;i<ages.length;i++){
            agesHtml = agesHtml + "<a class=\"dropdown-item\" href=\"#\">" + ages[i] + "</a>";
            if (i !== ages.length-1){
                agesHtml = agesHtml + "<div class=\"dropdown-divider\"></div>";
            }
        }
        agesHtml = agesHtml + "</div>";
    }

    $("#courseAges").html(agesHtml);
    var ll=courses.length;
    for (var i = 0 ; i < courses.length() ; i+=1){
        tableBodyHtml = tableBodyHtml + '<tr>';
        // 遍历单条信息
        tableBodyHtml = tableBodyHtml + '<td><a href='+ getRootPath()+'Teacher/getPracticeByCourseId/ >' + courses[i].name + '</a></td>';
        tableBodyHtml = tableBodyHtml + '<td>' + course.intro + '</a></td>';
        tableBodyHtml = tableBodyHtml + '<td>' + course.practiceSize + '</a></td>';
        tableBodyHtml = tableBodyHtml + '<td class="td-actions text-left">' +
            '<div class="form-button-action">' +
            '<button type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger">' +
            '<i class="la la-times" onclick="deleteCourse(' +cache.id + ')">删除</i>' +
            '</button>' +
            '</div>' +
            '</td>';
        tableBodyHtml = tableBodyHtml + '</tr>';
    }


    $("#coursesTbody").html(tableBodyHtml);
}

function searchPractices() {
    var val = $("#searchPracticesVal").val();
    var practiceTbody = $("#practiceTbody");
    $.ajax({
        type: 'POST',
        url: '/Practice/Teacher/searchPractices',
        data: 'val=' + val,
        dataType: "json",
        global: false,
        success: function(data) {
            alert(data.tbody);
            practiceTbody.append(data.tbody);
        }
    });
};

function deleteCourse(id) {
    var an = confirm("确定删除？");
    if (an == true) {
        location.href = "deleteCourse/" + id;
    } else {
        return false;
    }
}

function deletePractice(id) {
    var an = confirm("确定删除？");
    if (an == true) {
        location.href = "/Practice/Teacher/deletePractice/" + id;
    } else {
        return false;
    }
}

function deleteQuestion(id) {
    var an = confirm("确定删除？");
    if (an == true) {
        location.href = "/Practice/Teacher/deleteQuestion/" + id;
    } else {
        return false;
    }
}

function updateReady(id) {
    $.get("updateReady/" + id, function(data, status) {
        if (data.thisBody == (getRootPath() + "Login/LoginIndexUrl")) {
            location.href = data.thisBody;
        } else {
            $("#tbody").html(data.thisBody);
        }
    });
}

function getStudentManagementHtml() {
    $.get("getStudentManagementHtml", function(data, status) {
        if (data.ok != "ok") {
            $("#contentView").html(data.thisBody);
        } else {
            $("#contentView").html(data.thisBody);
        }
    });
}
/* bootstrap开关控件，初始化 */
function onLi() {
    $('#mySwitch input').bootstrapSwitch();
}