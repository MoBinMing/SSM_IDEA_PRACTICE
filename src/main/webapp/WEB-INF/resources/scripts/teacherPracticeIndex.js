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

function addPracticesTest() {
    var age = $("#age").val();
    var name = $("#name").val();
    if (name == "") {
        $("#myAlert2").css("visibility", "visible");
        $("#myAlert2").css("display", "inline");
        return false;
    }
    if (age == "" || age.length<4) {
        $("#ageAlert").css("visibility", "visible");
        $("#ageAlert").css("display", "inline");
        return false;
    }
    return true;
}

function searchPractices() {
    var kw = $("#searchPracticesVal").val()+"";
    var url = getRootPath() + "/Teacher/searchPractices";
    var practicesTbody = $("#practicesTbody");
    $.ajax({
        type: 'POST',
        url: url,
        data: 'val=' + kw,
        dataType: "json",
        global: false,
        success: function(data) {
            //alert(data.practices);
            if (data.practices!="登录超时"){
                createPracticeCellHtml(data.practices);
            }else {
                window.location.href = getRootPath();
            }
        }
    });
}
function createPracticeCellHtml(practices) {
    if (count(practices) !== 0){
        var tableBodyHtml = "";
        for (var j=0;j<practices.length;j++){
            tableBodyHtml = tableBodyHtml + '<tr>';
            // 遍历单条信息

            tableBodyHtml = tableBodyHtml +"<td>" +
                "<a href=\""+ getRootPath() +"/Teacher/QuestionForPractice/"+ practices[j].id
                +"\" class=\"\">"+ practices[j].name +"</a><br>" + practices[j].outlines + "<br>"
                +"</td>";
            tableBodyHtml = tableBodyHtml + '<td>' + practices[j].questionCount + '</td>';
            tableBodyHtml = tableBodyHtml + '<td>' + practices[j].strDate + '</td>';
            tableBodyHtml = tableBodyHtml +
                '<td>' +
                    '<div class="SwitchIcon p-0 m-0" >' +
                        '<div class="toggle-button-wrapper">' +
                            '<input type="checkbox" id="practice'+ practices[j].id +'" class="toggle-button" name="switch"';
            if (practices[j].isReady===1){
                tableBodyHtml = tableBodyHtml + "checked=\"checked\"";
            }
            tableBodyHtml = tableBodyHtml +  'onclick="event.stopPropagation();SwitchClick(this);">'+
                            '<label for="practice' + practices[j].id + '" class="button-label" >'+
                                '<span class="circle"></span>'+
                                '<span class="text on">ON</span>'+
                                '<span class="text off">OFF</span>'+
                            '</label>'+
                        '</div>'+
                    '</div>' +
                '</td>';
            tableBodyHtml = tableBodyHtml +
                '<td class="text-center">\n' +
                '     <div class="form-button-action">\n' +
                '         <button type="button" data-toggle="tooltip" title="修改练习" class="btn btn-link <btn-simple-primary"\n' +
                "                 onclick=\"showUpdatePracticeModal('" + practices[j].id + "','"+ practices[j].name + "','" +
                                    practices[j].outlines +"');event.stopPropagation();\">" +
                '             <i class="fa fa-pencil-square-o" aria-hidden="true">&nbsp;编辑</i>\n' +
                '         </button>\n' +
                '     </div>\n' +
                '</td>';
            tableBodyHtml = tableBodyHtml + '<td>\n' +
                '                    <div class="form-button-action">\n' +
                '                      <button type="button" data-toggle="tooltip" title="删除" class="btn btn-link btn-simple-danger"\n' +
                '                              onclick="deletePractice(\'' + practices[j].id+ '\');event.stopPropagation();">\n' +
                '                        <i class="fa fa-trash-o" aria-hidden="true">&nbsp;删除</i>\n' +
                '                      </button>\n' +
                '                    </div>\n' +
                '                  </td>';
            tableBodyHtml = tableBodyHtml + '</tr>';
        }
        $("#practicesTbody").html(tableBodyHtml);
    }else {
        $("#practicesTbody").html("<div class=\"alert alert-success alert-dismissable\">\n" +
            "\t<button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
            "\t\t\taria-hidden=\"true\">\n" +
            "\t\t&times;\n" +
            "\t</button>\n" +
            "\t当前搜索关键词无数据！!\n" +
            "</div>");
    }
}

function deletePractice(id) {
    var an = confirm("确定删除？");
    if (an) {
        location.href = getRootPath() + "/Teacher/deletePractice/" + id;
    }
    return false;
}


let updatePracticeId;

function showUpdatePracticeModal(id,name,outlines) {
    event.stopPropagation();
    updatePracticeId=id;
    $("#practiceId").val(id);
    $("#practiceName").val(name);
    $("#practiceOutlines").val(outlines);
    $('#updatePracticeModal').modal('show');
    return false;
}
function updatePractice() {
    if (updatePracticeId!=null && updatePracticeId!==""){
        var name=$("#practiceName").val();
        var outlines=$("#practiceOutlines").val();
        if (name!=null && name!=""){
            if (outlines!=null){
                return true;
            }
        }else {
            alert("练习名称不能为空");
        }
    }
    return false;
}

function SwitchClick(dom) {
   // dom.stopPropagation();
    var checked = dom.checked;
    var id=dom.id;
    var i="#"+id;
    if(checked===true) {
        $(i).removeAttr("checked");
        $.get(getRootPath()+"/Teacher/updatePracticeReadyToOn?pid="+id,
            function(data){
                if(data.updateOk){
                    $(i).attr("checked", "checked");
                    //location.reload();
                }else{
                    $(i).removeAttr("checked");
                    alert("开放失败！");
                }
            });
    } else {
        $(i).attr("checked", "checked");
        $.get(getRootPath()+"/Teacher/updatePracticeReadyToOff?pid="+id,function(data,status){
            if(data.updateOk){
                $(i).removeAttr("checked");
               // location.reload();
            }else{
                $(i).attr("checked", "checked");
                alert("停止开放失败！");
            }
        });
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
     $('.switch input').bootstrapSwitch();
}