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

function addOption(questionId) {
    // var myLi = document.getElementById('2222');
    // var myInput = document.getElementById('P111_X');
    //myInput.value = myLi.id;
    //let type=$("#editQuestionType"+questionId).find("option:selected").attr("data-amount");
    temp = document.getElementById('editQuestion'+questionId);
    lis = temp.getElementsByTagName('li');
    var label=getLabel(lis.length+1);
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
                    "<div class=\"row m-0 p-1 \" style=\"align-items: center;\">\n" +
                    "<input hidden name=\"id\" value=\""+newOptionId+"\">\n" +
                    "<label class=\"ml-2 mr-2 mb-0\">\n" +
                    "<input type=\"radio\" value=\""+newOptionId+"\" name=\"optionIsAnswer\" >"+label+"\n" +
                    "</label>\n" +
                    "<input class=\"col p-0 m-0 \" name=\"optionContent"+label+"\" placeholder=\"请输入选项内容\" >\n" +
                    "\n" +
                    "<span class=\"p-0 m-0  text-right\">\n" +
                    "<i class=\"fa fa-trash-o ml-2 mr-2\" aria-hidden=\"true\" onclick=\"deleteOption('#optionItem"+questionId+newOptionId+"');\">&nbsp;删除</i>\n" +
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
                    "<input hidden name=\"id\" value=\""+newOptionId1+"\"<div class=\"row m-0 p-0 \" style=\"align-items: center;\">\n" +
                    "<label class=\"ml-2 mr-2 mb-0 pb-0\">\n" +
                    "<input type=\"checkbox\" name=\"optionIsAnswer\" value=\""+label+"\">"+label+"\n" +
                    "</label>\n" +
                    "<input name=\"optionContent"+label+"\" class=\"col p-0 m-0 \" placeholder=\"请输入选项内容\" >\n" +
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
                    "<div class=\"row m-0 p-1 \" style=\"align-items: center;\">\n" +
                    "<input hidden name=\"id\" value=\""+newOptionId2+"\">\n" +
                    "<label class=\"ml-2 mr-2 mb-0\">\n" +
                    "<input type=\"radio\" value=\""+newOptionId2+"\" name=\"optionIsAnswer\" >"+label+"\n" +
                    "</label>\n" +
                    "<input class=\"col p-0 m-0 \" name=\"optionContent"+label+"\" placeholder=\"请输入选项内容\" >\n" +
                    "\n" +
                    "<span class=\"p-0 m-0  text-right\">\n" +
                    "<i class=\"fa fa-trash-o ml-2 mr-2\" aria-hidden=\"true\" onclick=\"deleteOption('#optionItem"+questionId+newOptionId2+"');\">&nbsp;删除</i>\n" +
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
    var options={};
    var optionContents={};
    var t = $(formId).serializeJson();
    var optionIsAnswers=[];

    $.ajax({
        type:"POST",
        async:false,
        dataType:'json',
        contentType : "application/json",
        url: getRootPath()+"/Teacher/UpdateQuestionUrl",
        data: JSON.stringify(t),
        success:function (data) {
            if (data.result==="ok"){
                window.location.href=getRootPath()+data.link;
            }else {
                alert("更新失败："+data.result);
            }
        }
    });
    // $.ajax({
    //     url: getRootPath()+"/Teacher/UpdateQuestionUrl",
    //     data: {data:JSON.stringify(t)},
    //     type:"post",
    //     success: function () {
    //         alert("success");
    //     }
    // });
    //
    // $.each(t, function() {
    //     if (this.name==="optionIsAnswer"){
    //         optionIsAnswers.push(this.value);
    //     }else if (this.name.indexOf("optionContent") !== -1){
    //         optionContents[(this.name.replace("optionContent",""))] = this.value;
    //     }else {
    //         d[this.name] = this.value;
    //     }
    // });
    // // var arr = [];
    // // arr.push(optionContents);
    // var keys=Object.keys(optionContents);
    //
    // for (var i=0;i<count(keys);i=i+1){
    //     var key=keys[i];
    //     for (var k=0;k< count(optionIsAnswers);k=k+1){
    //         if (key === optionIsAnswers[k]){
    //             options[key] = {conten:optionContents[key],label:key,questionId:Number(d["questionId"]),id:Number(d["id"]),isAnswer:1};
    //         }else {
    //             options[key]={conten:optionContents[key],label:key,questionId:Number(d["questionId"]),id:Number(d["id"]),isAnswer:0};
    //         }
    //     }
    // }
    // if (options.length===0){
    //     return false;
    // }else {
    //     d["options"] = options;
    //     alert(JSON.stringify(d));
    // }
}
$(function() {

    //将form表单转换为json数据
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray(); //将form表单序列化数组对象
        var str = this.serialize();  //将form表单序列化字符串
        $(array).each(function () {  //遍历表单数组拼接json串
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };
});

function getLabel(index) {
    switch (index) {
        case 0:
            return "A";
        case 1:
            return "B";
        case 2:
            return "C";
        case 3:
            return "D";
        case 4:
            return "E";
        case 5:
            return "F";
        case 6:
            return "G";
        case 7:
            return "H";
        case 8:
            return "I";
        case 9:
            return "J";

        default:
            return null;
    }
}