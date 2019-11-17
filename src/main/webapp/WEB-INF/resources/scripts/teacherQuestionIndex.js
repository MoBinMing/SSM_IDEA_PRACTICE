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
function editSelectOnChange(qId){
    var html=$("#editQuestionType"+qId).find("option:selected").attr("data-amount");
    $("#editQuestion"+qId).html(html);
    //$("#editQuestion"+qId).html(optionVal);
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

function deleteOption(itemId) {
    $(itemId).remove();
}

function addOption(questionId) {
    // var myLi = document.getElementById('2222');
    // var myInput = document.getElementById('P111_X');
    //myInput.value = myLi.id;
    //let type=$("#editQuestionType"+questionId).find("option:selected").attr("data-amount");
    let type=$("#editQuestionType"+questionId).val();
    switch (type) {
        case "单项选择":
            let newOptionId;
            try {
                let temp = document.getElementById('radio_'+questionId);
                let lis = temp.getElementsByTagName('li');
                if (lis.length>0){
                    var id=lis[lis.length-1].id;
                    let id1=id.replace("optionItem"+questionId,"");
                    newOptionId=parseInt(id1)+1;
                }else {
                    newOptionId=1;
                }
            }catch (err) {
                newOptionId=1;
            }finally {
                $("#radio_"+questionId).append("<li id=\"optionItem"+questionId+newOptionId+"\">\n" +
                    "<div class=\"row m-0 p-0 \" style=\"align-items: center;\">\n" +
                    "<input class=\"col p-0 m-0 \" placeholder=\"请输入选项内容\" >\n" +
                    "<label class=\"ml-2 mr-2 mb-0\">"+
                    "<input type=\"radio\" name=\"optradio\">设为答案"+
                    "</label>" +
                    "<span class=\"p-0 m-0  text-right\">\n" +
                    "<i class=\"fa fa-trash-o\" aria-hidden=\"true\" onclick=\"deleteOption('#optionItem"+questionId+newOptionId+"');\">&nbsp;删除</i>\n" +
                    "</span>\n" +
                    "</div>\n" +
                    "</li>");
            }
            break;
        case "多项选择":
            let newOptionId1;
            try {
                let temp = document.getElementById('checkBox_'+questionId);
                let lis = temp.getElementsByTagName('li');
                if (lis.length>0){
                    var id=lis[lis.length-1].id;
                    let id1=id.replace("optionItem"+questionId,"");
                    newOptionId1=parseInt(id1)+1;
                }else {
                    newOptionId1=1;
                }
            }catch (err) {
                newOptionId1=1;
            }finally {
                $("#checkBox_"+questionId).append("<li id=\"optionItem"+questionId+newOptionId1+"\">\n" +
                    "<div class=\"row m-0 p-0 \" style=\"align-items: center;\">\n" +
                    "<input class=\"col p-0 m-0 \" placeholder=\"请输入选项内容\">\n" +
                    "<label class=\"ml-2 mr-2 mb-0 pb-0\">"+
                    "<label class=\"mb-0\"><input type='checkbox'>答案</label>"+
                    "</label>" +
                    "<span class=\"p-0 m-0  text-right\">\n" +
                    "<i class=\"fa fa-trash-o\" aria-hidden=\"true\" onclick=\"deleteOption('#optionItem"+questionId+newOptionId1+"');\">&nbsp;删除</i>\n" +
                "</span>\n" +
                "</div>\n" +
                "</li>");
            }
            break;
        case "判断题":
            let newOptionId2;
            try {
                let temp = document.getElementById('radio2_'+questionId);
                let lis = temp.getElementsByTagName('li');
                if (lis.length>0){
                    var id=lis[lis.length-1].id;
                    let id1=id.replace("optionItem"+questionId,"");
                    newOptionId2=parseInt(id1)+1;
                }else {
                    newOptionId2=1;
                }
            }catch (err) {
                newOptionId2=1;
            }finally {
                $("#radio2_"+questionId).append("<li id=\"optionItem"+questionId+newOptionId2+"\">\n" +
                    "<div class=\"row m-0 p-0 \" style=\"align-items: center;\">\n" +
                    "<input class=\"col p-0 m-0 \" placeholder=\"请输入选项内容\" >\n" +
                    "<label class=\"ml-2 mr-2 mb-0\">\n" +
                    "<input type=\"radio\" name=\"optradio\" >设为答案\n" +
                    "</label>\n" +
                    "<span class=\"p-0 m-0  text-right\">\n" +
                    "<i class=\"fa fa-trash-o\" aria-hidden=\"true\" onclick=\"deleteOption('#optionItem"+questionId+newOptionId2+"');\">&nbsp;删除</i>\n" +
                    "</span>\n" +
                    "</div>\n" +
                    "</li>");
            }
            break;
        default:
            break;
    }
}
function deleteQuestion(id) {
    var an = confirm("确定删除？");
    if (an == true) {
        location.href = getRootPath()+"/Teacher/deleteQuestion/" + id;
    } else {
        return false;
    }
}

function updateQuestion(formId) {
    var d = {};
    var t = $(formId).serializeArray();
    //t的值为[{name: "a1", value: "xx"},
    //{name: "a2", value: "xx"}...]
    $.each(t, function() {
        d[this.name] = this.value;
    });
    alert(JSON.stringify(d));
}