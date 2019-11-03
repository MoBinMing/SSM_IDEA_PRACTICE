package info.lzzy.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.file.Matcher;

import info.lzzy.models.Course;
import info.lzzy.models.Option;
import info.lzzy.models.Practice;
import info.lzzy.models.Question;
import info.lzzy.service.PracticeService;
import info.lzzy.service.QuestionService;

public class PracticesUtil {
	// region 教师首页章节html
	public static String getCoursesHtml(PracticeService practiceService, List<Course> courses) {
		StringBuilder bodyHtml=new StringBuilder();
		 bodyHtml.append("<div class=\"container-fluid\">\r\n"
		 		+ "<div class=\"row\">\r\n" + 
		   		"            <h4 class=\"col page-title\" >课程管理</h4>\r\n" + 
		   		"            <div class=\"col text-right\">\r\n" + 
		   		"              <button type=\"button\" class=\"btn btn-success\" data-target=\"#addCourseModal\" data-toggle=\"modal\">添加课程 </button>\r\n" + 
		   		"            </div>\r\n" + 
		   		"          </div>");
		   bodyHtml.append("<div class=\"container-fluid\">\r\n" + 
		   		"		          <div class=\"tlinks\">Collect from <a href=\"http://www.cssmoban.com/\">企业网站模板</a></div>\r\n" + 
		   		"		          <div class=\"row\">\r\n" + 
		   		"		            <div class=\"col-md-12\" style=\"padding: 0;\">\r\n" + 
		   		"		              <div class=\"card card-tasks\">\r\n" + 
		   		"		                <div class=\"card-body \" >\r\n" + 
		   		"		                  <div class=\"table-full-width\">\r\n" + 
		   		"		                    <table class=\"table\">\r\n" + 
		   		"		                      <thead>\r\n" + 
		   		"		                        <tr>\r\n" + 
		   		"		                          <th>课程</th>\r\n" + 
		   		"		                          <th>课程介绍</th>\r\n" + 
		   		"		                          <th> 练习数</th>\r\n" + 
		   		"		                          <th> 操作 </th>\r\n" + 
		   		"		                        </tr>\r\n" + 
		   		"		                      </thead>\r\n" + 
		   		"		                      <tbody>");
		   for (int j = 0; j < courses.size(); j++) {
			   Course course=courses.get(j);
			   List<Practice> pList=practiceService.getPracticeByCourseId(course.getId());
			   bodyHtml.append("<tr>");
			   String Intro=course.getIntro().equals("")?"课程无介绍信息":course.getIntro();
			   bodyHtml.append("<td><a href=\"<%=basePoth%>Teacher/getPracticeByCourseId/"+course.getId()+"\">"+course.getName()+"</a></td>");
			   bodyHtml.append("<td>"+Intro+"</td>");
			   bodyHtml.append("<td>"+pList.size()+"</td>");
			   bodyHtml.append("<td class=\"td-actions text-left\">\r\n" + 
			   		"		        <div class=\"form-button-action\">\r\n" + 
			   		"		             <button type=\"button\" data-toggle=\"tooltip\" title=\"删除\" class=\"btn btn-link btn-simple-danger\"><i class=\"la la-times\""
			   		+ "onclick=\"deleteCourse('"+course.getId()+"')\">删除</i></button>\r\n" + 
			   		"		        </div>\r\n" + 
			   		"		   </td>");
			   bodyHtml.append("</tr>");
			
		   }
		   bodyHtml.append("</tbody>\r\n" + 
		   		"		                    </table>\r\n" + 
		   		"		                  </div>\r\n" + 
		   		"		                </div>\r\n" + 
		   		"		              </div>\r\n" + 
		   		"		            </div>\r\n" + 
		   		"		          </div>\r\n" + 
		   		"		        </div>");
		   bodyHtml.append(" <div class=\"modal\" id=\"addCourseModal\">\r\n" + 
		   		"        <div class=\"modal-dialog\" role=\"document\">\r\n" + 
		   		"          <div class=\"modal-content\">\r\n" + 
		   		"            <div class=\"modal-header\" >\r\n" + 
		   		"              <h5 class=\"modal-title bg-light\">添加课程</h5> <button type=\"button\" class=\"close\" data-dismiss=\"modal\"> <span>×</span> </button>\r\n" + 
		   		"            </div>\r\n" + 
		   		"            <form id=\"addCourses\" name=\"addCourses\" method=\"post\" action=\"<%=basePoth%>Teacher/addCourses\" class=\"form-horizontal\" role=\"添加练习\" onsubmit=\"return addPracticesTest();\">\r\n" +
		   		"              <div class=\"modal-body\" style=\"\">\r\n" + 
		   		"                <div class=\"form-group\">\r\n" + 
		   		"                  <label for=\"name\">课程名称</label>\r\n" + 
		   		"                  <div id=\"myAlert2\" class=\"alert alert-warning\" style=\"visibility: hidden; display: none;\">\r\n" + 
		   		"                    <a href=\"#\" class=\"close\" data-dismiss=\"alert\">×</a>\r\n" + 
		   		"                    <strong>提示！</strong>课程名称输入不完整！。 </div> <input id=\"name\" name=\"name\" type=\"text\" class=\"form-control\" placeholder=\"请输入课程名称\">\r\n" + 
		   		"                </div>\r\n" + 
		   		"         		<div class=\"form-group\">\r\n" + 
		   		"                  <label for=\"outlines\">课程描述</label>\r\n" + 
		   		"                  <textarea id=\"intro\" name=\"intro\" class=\"form-control\" placeholder=\"请输入课程描述\" rows=\"3\" ></textarea>\r\n" + 
		   		"                </div>"+
		   		"              </div>\r\n" + 
		   		"              <div class=\"modal-footer\">\r\n" + 
		   		"                <button type=\"submit\" class=\"btn btn-primary\">确认添加</button>\r\n" + 
		   		"                <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">取消</button>\r\n" + 
		   		"              </div>\r\n" + 
		   		"            </form>\r\n" + 
		   		"          </div>\r\n" + 
		   		"        </div>\r\n" + 
		   		"      </div>"
		   		+ "</div>");
		return bodyHtml.toString();
	}
	//endregion
	//region 获取课程下的练习
	public static String getPracticeByCourseIdHtml(HttpServletRequest request,QuestionService questionService, List<Practice> practice,int courseid) {
		StringBuilder bodyHtml=new StringBuilder();
		int unopen = 0;
		int dispark = 0;
		for (Practice p : practice) {
			if (p.getIsReady() == 1) {
				dispark++;
			} else {
				unopen++;
			}
		}
		   bodyHtml.append("<div class=\"container-fluid\">\r\n" + 
		   		"          <div class=\"row\">\r\n" + 
		   		"            <h4 class=\"col page-title\">练习管理</h4>\r\n" + 
		   		"            <div class=\"col text-right\">\r\n" + 
		   		"              <button type=\"button\" class=\"btn btn-success\" data-target=\"#addPracticesModal\" data-toggle=\"modal\">添加练习 </button>\r\n" + 
		   		"            </div>\r\n" + 
		   		"          </div>\r\n" + 
		   		"          <div class=\"row\">\r\n" + 
		   		"            <div class=\"col-md-4\">\r\n" + 
		   		"              <div class=\"card card-stats card-success\">\r\n" + 
		   		"                <div class=\"card-body \">\r\n" + 
		   		"                  <div class=\"row\">\r\n" + 
		   		"                    <div class=\"col-5\">\r\n" + 
		   		"                      <div class=\"icon-big text-center\">\r\n" + 
		   		"                        <i class=\"la la-bar-chart\"></i>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                    <div class=\"col-7 d-flex align-items-center\">\r\n" + 
		   		"                      <div class=\"numbers\">\r\n" + 
		   		"                        <p class=\"card-category\">我的练习</p>\r\n" + 
		   		"                        <h4 class=\"card-title\">"+practice.size()+"个</h4>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                  </div>\r\n" + 
		   		"                </div>\r\n" + 
		   		"              </div>\r\n" + 
		   		"            </div>\r\n" + 
		   		"            <div class=\"col-md-4\">\r\n" + 
		   		"              <div class=\"card card-stats card-danger\">\r\n" + 
		   		"                <div class=\"card-body\" style=\"\">\r\n" + 
		   		"                  <div class=\"row\">\r\n" + 
		   		"                    <div class=\"col-5\">\r\n" + 
		   		"                      <div class=\"icon-big text-center\">\r\n" + 
		   		"                        <i class=\"la la-upload\"></i>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                    <div class=\"col-7 d-flex align-items-center\">\r\n" + 
		   		"                      <div class=\"numbers\">\r\n" + 
		   		"                        <p class=\"card-category\">待开发</p>\r\n" + 
		   		"                        <h4 class=\"card-title\">"+unopen+"个</h4>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                  </div>\r\n" + 
		   		"                </div>\r\n" + 
		   		"              </div>\r\n" + 
		   		"            </div>\r\n" + 
		   		"            <div class=\"col-md-4\">\r\n" + 
		   		"              <div class=\"card card-stats card-primary\">\r\n" + 
		   		"                <div class=\"card-body \">\r\n" + 
		   		"                  <div class=\"row\">\r\n" + 
		   		"                    <div class=\"col-5\">\r\n" + 
		   		"                      <div class=\"icon-big text-center\">\r\n" + 
		   		"                        <i class=\"la la-check-circle\"></i>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                    <div class=\"col-7 d-flex align-items-center\">\r\n" + 
		   		"                      <div class=\"numbers\">\r\n" + 
		   		"                        <p class=\"card-category\">已开放</p>\r\n" + 
		   		"                        <h4 class=\"card-title\">"+dispark+"个</h4>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                  </div>\r\n" + 
		   		"                </div>\r\n" + 
		   		"              </div>\r\n" + 
		   		"            </div>\r\n" + 
		   		"          </div>\r\n" +
		   		"          <div class=\"row\">\r\n" + 
		   		"            <div class=\"col-md-12\">\r\n" + 
		   		"              <div class=\"card card-tasks\">\r\n" + 
		   		"                <div class=\"card-header \">\r\n" + 
		   		"                  <form id=\"searchPracticesform\" class=\"col-md-4 card-title navbar-left navbar-form nav-search mr-md-3\" onsubmit=\"return false;\" action=\"\">\r\n" + 
		   		"                    <div class=\"input-group\">\r\n" + 
		   		"                      <input id=\"searchPracticesVal\" name=\"searchPracticesVal\" type=\"text\" placeholder=\"Search ...\" class=\"form-control\">\r\n" + 
		   		"                      <div class=\"input-group-append\">\r\n" + 
		   		"                        <span class=\"input-group-text\">\r\n" + 
		   		"                          <i class=\"la la-search search-icon\"></i>\r\n" + 
		   		"                        </span>\r\n" + 
		   		"                      </div>\r\n" + 
		   		"                    </div>\r\n" + 
		   		"                  </form>\r\n" + 
		   		"                </div>\r\n" + 
		   		"                <div class=\"card-body \">\r\n" + 
		   		/*"                  <div class=\"btn-group btn-group-toggle\" data-toggle=\"buttons\">\r\n" + 
		   		"                    <label class=\"btn btn-primary active\">\r\n" + 
		   		"                      <input type=\"radio\" name=\"options\" id=\"option1\" autocomplete=\"off\" checked=\"\"> Radio On </label>\r\n" + 
		   		"                    <label class=\"btn btn-primary\">\r\n" + 
		   		"                      <input type=\"radio\" name=\"options\" id=\"option2\" autocomplete=\"off\"> Radio Off </label>\r\n" + 
		   		"                  </div>\r\n" + */
		   		"                  <div class=\"table-full-width\">\r\n" + 
		   		//"                    <div class=\"btn-group\"> <a href=\"#\" class=\"btn btn-outline-success\">Btn A</a> <a href=\"#\" class=\"btn btn-primary\">Btn B</a> </div>\r\n" + 
		   		"                    <table class=\"table\">\r\n" + 
		   		"                      <thead>\r\n" + 
		   		"                        <tr>\r\n" + 
		   		"                          <th> 练习名称 </th>\r\n" + 
		   		"                          <th> 题目数 </th>\r\n" + 
		   		"                          <th> 添加时间 </th>\r\n" + 
		   		"                          <th> 是否开放 </th>\r\n" + 
		   		"                          <th class=\"td-actions text-center\"> 操作 </th>\r\n" + 
		   		"                        </tr>\r\n" + 
		   		"                      </thead>\r\n" + 
		   		"                      <tbody id=\"practiceTbody\">\r\n"); 
		   		for (int i = 0; i < practice.size(); i++) {
		   			Practice practice2=practice.get(i);
		   			String isReady=practice2.getIsReady()==1?"checked=\"checked\"":"";
					bodyHtml.append("        <tr>\r\n" + 
		   		"                          <td><a href=\"<%=basePoth%>Teacher/managingCurrentExercises/"+practice2.getId()+"\" class=\"\">"+practice2.getName()+"</a><br>"+practice2.getOutlines()+"<br></td>\r\n" +
		   		"                          <td>"+questionService.getQuestionByPracticeId(practice2.getId()).size()+"</td>\r\n" + 
		   		"                          <td>"+DateTimeUtils.DATE_TIME_FORMAT.format(practice2.getUpTime())+"</td>\r\n" + 
		   		"                          <td>"
		   		+ "							 <div class=\"SwitchIcon\">\r\n" + 
		   		"                              <div class=\"toggle-button-wrapper\">\r\n" + 
		   		"                                <input type=\"checkbox\" id=\"practice"+practice2.getId()+"\" class=\"toggle-button\" name=\"switch\" onclick=\"SwitchClick(this)\" "+isReady+">\r\n" + 
		   		"                                <label for=\"practice"+practice2.getId()+"\" class=\"button-label\" >\r\n" + 
		   		"                                  <span class=\"circle\"></span>\r\n" + 
		   		"                                  <span class=\"text on\">ON</span>\r\n" + 
		   		"                                  <span class=\"text off\">OFF</span>\r\n" + 
		   		"                                </label>\r\n" + 
		   		"                              </div>\r\n" + 
		   		"                             </div>"
		   		+ "						   </td>\r\n" + 
		   		"                          <td class=\"td-actions text-center\">\r\n" + 
		   		"                            <div class=\"form-button-action\">\r\n" + 
		   		"                              <button type=\"button\" data-toggle=\"tooltip\" title=\"修改练习\" class=\"btn btn-link <btn-simple-primary\">\r\n" + 
		   		"                                <i class=\"la la-edit\">编辑</i>\r\n" + 
		   		"                              </button>\r\n" + 
		   		"                              <button type=\"button\" data-toggle=\"tooltip\" title=\"删除\" class=\"btn btn-link btn-simple-danger\">\r\n" + 
		   		"                                <i class=\"la la-times\" onclick=\"deletePractice("+practice2.getId()+")\">删除</i>\r\n" + 
		   		"                              </button>\r\n" + 
		   		"                            </div>\r\n" + 
		   		"                          </td>\r\n" + 
		   		"                        </tr>\r\n" );
				}
		   		bodyHtml.append("      </tbody>\r\n" + 
		   		"                    </table>\r\n" + 
		   		"                  </div>\r\n" + 
		   		"                </div>\r\n" + 
		   		"                <div class=\"modal\" id=\"addPracticesModal\">\r\n" + 
					   		"        <div class=\"modal-dialog\" role=\"document\">\r\n" + 
					   		"          <div class=\"modal-content\">\r\n" + 
					   		"            <div class=\"modal-header\" >\r\n" + 
					   		"              <h5 class=\"modal-title bg-light\">添加练习</h5> <button type=\"button\" class=\"close\" data-dismiss=\"modal\"> <span>×</span> </button>\r\n" + 
					   		"            </div>\r\n" + 
					   		"            <form id=\"addPractices\" name=\"addPractices\" method=\"post\" action=\"<%=basePoth%>Teacher/addPractices\" class=\"form-horizontal\" role=\"添加练习\" onsubmit=\"return addPracticesTest();\">\r\n" +
					   		"              <div class=\"modal-body\" style=\"\">\r\n" + 
					   		"                <div class=\"form-group\">\r\n" + 
					   		"                  <label for=\"name\">练习名称</label>\r\n" + 
					   		"                  <div id=\"myAlert2\" class=\"alert alert-warning\" style=\"visibility:hidden;display:none;\">\r\n" + 
					   		"                    <a href=\"#\" class=\"close\" data-dismiss=\"alert\">×</a>\r\n" + 
					   		"                    <strong>提示！</strong>练习名称输入不完整！。 </div> <input id=\"name\" name=\"name\" type=\"text\" class=\"form-control\" placeholder=\"请输入练习名称\">\r\n" + 
					   		"                </div>\r\n" + 
					   		"                <div class=\"form-group\">\r\n" + 
					   		"					<input type=\"hidden\" id=\"courseId\" name=\"courseId\" value=\""+courseid+"\">"+
					   		"                  <label for=\"outlines\">练习描述</label>\r\n" + 
					   		"                  <textarea id=\"outlines\" name=\"outlines\" class=\"form-control\" placeholder=\"请输入练习描述\" rows=\"3\"></textarea>\r\n" + 
					   		"                </div>\r\n" + 
					   		"              </div>\r\n" + 
					   		"              <div class=\"modal-footer\">\r\n" + 
					   		"                <button type=\"submit\" class=\"btn btn-primary\">确认添加</button>\r\n" + 
					   		"                <button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">取消</button>\r\n" + 
					   		"              </div>\r\n" + 
					   		"            </form>\r\n" + 
					   		"          </div>\r\n" + 
					   		"        </div>\r\n" + 
					   		"      </div>"+
		   		"                <div class=\"card-footer \">\r\n" + 
		   		"                  <div class=\"stats\">\r\n" + 
		   		"                    <i class=\"now-ui-icons loader_refresh spin\"></i> Updated 3 minutes ago </div>\r\n" + 
		   		"                </div>\r\n" + 
		   		"              </div>\r\n" + 
		   		"            </div>\r\n" + 
		   		"          </div>\r\n" + 
		   		"        </div>");
		  
		return bodyHtml.toString();
	}
	// region 首页练习html
	public static String getPracticesHtml(HttpServletRequest request, List<Practice> pList) {
		String filePath = request.getSession().getServletContext().getRealPath("WEB-INF/views/Teacher/myPractice.html");
		GetHtml htmlDao = new GetHtml();
		String html = htmlDao.readfile(filePath);
		// filePath= System.getProperty("user.dir");
		String bodyHtml = htmlDao.getBody(html);
		int unopen = 0;
		int dispark = 0;
		for (Practice practice : pList) {
			if (practice.getIsReady() == 1) {
				dispark++;
			} else {
				unopen++;
			}
		}
		return bodyHtml.replace("${myPracticeSize}", "" + pList.size()).replace("${unopen}", "" + unopen)
				.replace("${dispark}", "" + dispark);
	}
	// endregion

	// region 搜索练习html
	public static String searchPracticesTbodyHtml(QuestionService questionService,List<Practice> practices) {
		StringBuffer bodyHtml = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (int i = 0; i < practices.size(); i++) {
   			Practice practice2=practices.get(i);
   			String isReady=practice2.getIsReady()==1?"checked=\"checked\"":"";
			bodyHtml.append("        <tr>\r\n" + 
   		"                          <td><a href=\"<%=basePoth%>Teacher/managingCurrentExercises/"+practice2.getId()+"\" class=\"\">"+practice2.getName()+"</a><br>"+practice2.getOutlines()+"<br></td>\r\n" +
   		"                          <td>"+questionService.getQuestionByPracticeId(practice2.getId()).size()+"</td>\r\n" + 
   		"                          <td>"+DateTimeUtils.DATE_TIME_FORMAT.format(practice2.getUpTime())+"</td>\r\n" + 
   		"                          <td>"
   		+ "							 <div class=\"SwitchIcon\">\r\n" + 
   		"                              <div class=\"toggle-button-wrapper\">\r\n" + 
   		"                                <input type=\"checkbox\" id=\"practice"+practice2.getId()+"\" class=\"toggle-button\" name=\"switch\" onclick=\"SwitchClick(this)\" "+isReady+">\r\n" + 
   		"                                <label for=\"practice"+practice2.getId()+"\" class=\"button-label\" >\r\n" + 
   		"                                  <span class=\"circle\"></span>\r\n" + 
   		"                                  <span class=\"text on\">ON</span>\r\n" + 
   		"                                  <span class=\"text off\">OFF</span>\r\n" + 
   		"                                </label>\r\n" + 
   		"                              </div>\r\n" + 
   		"                             </div>"
   		+ "						   </td>\r\n" + 
   		"                          <td class=\"td-actions text-center\">\r\n" + 
   		"                            <div class=\"form-button-action\">\r\n" + 
   		"                              <button type=\"button\" data-toggle=\"tooltip\" title=\"修改练习\" class=\"btn btn-link <btn-simple-primary\">\r\n" + 
   		"                                <i class=\"la la-edit\">编辑</i>\r\n" + 
   		"                              </button>\r\n" + 
   		"                              <button type=\"button\" data-toggle=\"tooltip\" title=\"删除\" class=\"btn btn-link btn-simple-danger\">\r\n" + 
   		"                                <i class=\"la la-times\" onclick=\"deletePractice("+practice2.getId()+")\">删除</i>\r\n" + 
   		"                              </button>\r\n" + 
   		"                            </div>\r\n" + 
   		"                          </td>\r\n" + 
   		"                        </tr>\r\n" );
		}
		String html=bodyHtml.toString();
		return html.equals("")?"搜索结果:无数据！":html;
	}
	// endregion

	




// region 当前练习管理html
	public static String getManagingCurrentExercises(Practice practices, List<Question> qList, List<Option> options) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<div class=\"container-fluid\" style=\"background-color: aliceblue;\">\r\n" + 
				"	     <div class=\"row p-2\" >\r\n" + 
				"	       <h4 class=\"col page-title text-left p-2\">"+practices.getName()+"</h4>\r\n" + 
				"	       <div class=\"col text-right p-2\">\r\n" + 
				"	         <button type=\"button\" class=\"btn btn-success\" data-target=\"#addPracticesModal\" data-toggle=\"modal\">添加题目</button>\r\n" + 
				"	       </div>\r\n" + 
				"	     </div>");
		int i=0;
		for (int j=0;j<qList.size(); j++) {
			Question q=qList.get(j);
			if(i==0) {
				stringBuffer.append("<div class=\"row\">");
			}
			String type="";
			if (q.getQuestionType()==1) {
				type="单选题";
			}else {
				type="多选题";
			}
			stringBuffer.append("<div class=\"col-md-4\">\r\n" + 
					"		         <div class=\"card bg-light text-dark \">\r\n" + 
					"		           <div class=\"card-header\">\r\n" + 
					"		             <h6 class=\"text-left\">"+(j+1)+".（"+type+"）"+q.getContent()+"</h6>\r\n" + 
					"		           </div>\r\n" + 
					"		           <div class=\"card-body\">\r\n" + 
					"		             <ol class=\"text-left\" type=\"A\">\r\n"); 
			List<Option> options3 = new ArrayList<>();
			for (Option options2 : options) {
				if (options2.getQuestionId().equals(q.getId())) {
					options3.add(options2);
				}
			}
			Collections.sort(options3, new Comparator<Option>() {
				@Override
				public int compare(Option o1, Option o2) {
					return o1.getId() - o2.getId();
				}
			});
			for (Option options2 : options3) {
				if (options2.getIsAnswer()==1) {
					stringBuffer.append("<li style=\"\r\n" + 
							"						    background-color: #68d46e;\r\n" + 
							"						\">"+options2.getContent()+"</li>\r\n");
				}else {
					stringBuffer.append("<li>"+options2.getContent()+"</li>\r\n");
				}
			} 
					stringBuffer.append("		             </ol>\r\n" + 
					"		           </div>\r\n" + 
					"		           <div class=\"card-footer\">\r\n" + 
					"		             <div class=\"form-button-action pull-right\">\r\n" + 
					"		               <button type=\"button\" data-toggle=\"tooltip\" title=\"修改题目\" class=\"btn btn-link <btn-simple-primary\">\r\n" + 
					"		                 <i class=\"la la-edit\">编辑</i></button>\r\n" + 
					"                              <button type=\"button\" data-toggle=\"tooltip\" title=\"删除\" class=\"btn btn-link btn-simple-danger\">\r\n" + 
			   		"                                <i class=\"la la-times\" onclick=\"deleteQuestion("+q.getId()+")\">删除</i>\r\n" +
					"		               </button>\r\n" + 
					"		             </div>\r\n" + 
					"		           </div>\r\n" + 
					"		         </div>\r\n" + 
					"		       </div>");
		       if(i==3) {
					stringBuffer.append("</div>");
					i=0;
				}else if(i<3){
					i++;
				}
		}
		stringBuffer.append("	</div>\r\n");
		return stringBuffer.toString();
	}
	// endregion
}
