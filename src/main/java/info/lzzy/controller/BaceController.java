package info.lzzy.controller;

import java.text.SimpleDateFormat;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import info.lzzy.service.AdminService;
import info.lzzy.service.CourseService;
import info.lzzy.service.EnrollmentService;
import info.lzzy.service.OptionService;
import info.lzzy.service.PracticeResultService;
import info.lzzy.service.PracticeService;
import info.lzzy.service.QuestionService;
import info.lzzy.service.StudentService;
import info.lzzy.service.TeacherService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class BaceController {
	protected static String RETURN_S_String = "{\"RESULT\":\"S\",\"ERRMSG\":\"成功\"}";
	protected static String RETURN_F_String = "{\"RESULT\":\"F\",\"ERRMSG\":\"失败\"}";
	protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected HttpSession session;
	protected HttpServletRequest request;
	/*String PROJECT_PATH=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();*/
	protected HttpServletResponse response;
	protected ServletContext servletContext;
	@Autowired
	protected PracticeService practiceService;
	@Autowired
	protected QuestionService questionsService;
	@Autowired
	protected OptionService optionService;
	@Autowired
	protected StudentService studentService;
	@Autowired
	protected TeacherService teacherService;
	@Autowired
	protected PracticeResultService practiceResultService;
	@Autowired
	protected EnrollmentService enrollmentService;
	@Autowired
	protected CourseService courseService;
	@Autowired
	protected AdminService adminService;
	public BaceController() {
		super();
		
	}
	
	@ModelAttribute
	private void setAttribtes(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		this.servletContext = request.getSession().getServletContext();		
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public HttpSession getSession() {
		return this.session;
	}

	public ServletContext grtServletContext() {
		return this.servletContext;
	}
}