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
function searchApis(apiRole) {
    let kw;
    if (apiRole=="teacher"){
       kw = $("#searchTeacherApisVal").val()+"";
    }else if (apiRole=="student"){
        kw = $("#searchStudentApisVal").val()+"";
    }else if (apiRole=="admin"){
        kw = $("#searchAdminApisVal").val()+"";
    }
    let requestUrl = getRootPath() + "/ApiIndex/searchApis";
    var apiContainer;
    if (apiRole=="teacher"){
        apiContainer= $("#teacherApiContainer");
    }else if (apiRole=="student"){
        apiContainer= $("#studentApiContainer");
    }else if (apiRole=="admin"){
        apiContainer= $("#adminApiContainer");
    }
    $.ajax({
        type: 'POST',
        url: requestUrl,
        data: 'role=' + apiRole +"&kw="+kw,
        dataType: "json",
        global: false,
        success: function(data) {
            apiContainer.html(createApiCellHtml(data.apis,apiRole));
        }
    });
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
function createApiCellHtml(apis,role) {
    var html="";
    if (count(apis) != 0) {
        for (var j = 0; j < apis.length; j++) {
            var api=apis[j];
            html = html + '<div class="row mb-4" >\n' +
                '                      <div id="apiCol1'+api.id+'" class="col-md-7">\n' +
                '                        <div class="card">\n' +
                '                          <div class="row m-0 card-header">\n' +
                '                            <div class="col">\n' +
                '                              <font color="#212529">\n' +
                '                                <span style="background-color: rgba(0, 0, 0, 0.03);">'+api.name+'' +
                '                                        <span class="badge badge-light" style="">'+api.type+' </span></span>\n' +
                '                              </font>\n' +
                '                            </div>\n';
            if (api.type=="POST"){
                html = html + '<button onclick="$(\'#'+role+'ApiResult'+api.id+'\').html(\''+api.requestData+'\')"> 请求的json </button>\n';
            }else if (api.type=="GET"){
                html = html + '<button onclick="$(\'#'+role+'ApiResult'+api.id+'\').html(\''+api.requestData+'\')"> 请求的后缀 </button>\n';

            }
            html = html +
                '                            <button class="ml-2 mr-2" onclick="$(\'#'+role+'ApiResult'+api.id+'\').html(\''+api.succeedHtml+'\')"> 成功示例 </button>\n' +
                '\n' +
                '                            <button onclick="$(\'#'+role+'ApiResult'+api.id+'\').html(\''+api.defeatedHtml+'\')"> 失败示例 </button>\n' +
                '                          </div>\n' +
                '                          <div class="card-body">\n' +
                '                            <blockquote class="blockquote mb-0">\n' +
                '                              <span class="row m-0 p-0">\n' +
                '                                <p class="col m-0 p-0" >url:</p>' +
                '                                   <input type="button" value="复制链接" onclick=\''+"$(\"#"+role+"Url"+api.id+"\")"+'.select();document.execCommand(\"Copy\");\'>\n' +
                '                              </span>'+
                '                              <input class="type=text w-100" size="30" id="'+role+'Url'+api.id+'"  value="'+api.url+'" >\n' +
                '                              <p class="m-0"> 简介： </p>\n' +
                '                              <footer class="blockquote-footer"><cite title="info"> '+api.info+'</cite></footer>\n' +
                '                            </blockquote>\n' +
                '                          </div>\n' +
                '                        </div>\n' +
                '                      </div>\n' +
                '                      <div id="'+role+'ApiResult'+api.id+'" class="textarea col-md-5" contenteditable="true">\n' +
                '                        <br />\n' +
                '                      </div>\n' +
                '                    </div>';
        }
    }else {
            html="<div class=\"alert alert-success alert-dismissable\">\n" +
            "\t<button type=\"button\" class=\"close\" data-dismiss=\"alert\"\n" +
            "\t\t\taria-hidden=\"true\">\n" +
            "\t\t&times;\n" +
            "\t</button>\n" +
            "\t当前搜索关键词无数据！!\n" +
            "</div>";
    }
     return html;
}