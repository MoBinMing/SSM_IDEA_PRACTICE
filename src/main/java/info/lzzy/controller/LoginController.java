package info.lzzy.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import info.lzzy.models.view.CourseDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.utils.Encipher;

@Controller
@RequestMapping("/Login")
public class LoginController extends BaceController {

	@GetMapping("/LoginIndexUrl")
	public String LoginIndexUrl(Map<String, String> map) {
		request.getSession().setAttribute("read_file_role", true);
		//String filePath = request.getSession().getServletContext().getRealPath("WEB-INF/resources/html/Index.html");
		//GetHtml html = new GetHtml();
		// filePath= System.getProperty("user.dir");
		//System.out.println(html.getBody(html.readfile(filePath)));
		return "Login/LoginIndex";
	}

	@RequestMapping("/LoginUrl")
	@ResponseBody
	public Map<String, Object> LoginUrl(String iphone, String password, Long time) {
		Long loginTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<>();
		if (((loginTime - time) / 1000) < 8) {
			Student thisStudent=studentService.selectStudentByIphone(iphone);
			String studentUserPaw="";
			if (thisStudent!=null) {
				studentUserPaw=Encipher.DecodePasswd(thisStudent.getPassword());
			}
			if (studentUserPaw.equals(password)) {
				request.getSession().setAttribute("Iphone", iphone);
				request.getSession().setAttribute("Email", thisStudent.getEmail());
				request.getSession().setAttribute("role", "Student");
				request.getSession().setAttribute("Student", thisStudent);
				map.put("msg", "/Student/StudentIndex");
			} else {
				Teacher teacher = teacherService.selectTeacherByIphone(iphone);
				String teacherUserPaw="";
				if (teacher!=null) {
					teacherUserPaw=Encipher.DecodePasswd(teacher.getPassword());
				}
				if (teacherUserPaw.equals(password)) {
					request.getSession().setAttribute("Iphone", iphone);
					request.getSession().setAttribute("Email", teacher.getEmail());
					request.getSession().setAttribute("role", "Teacher");
					request.getSession().setAttribute("teacherId", teacher.getTeacherId());
					request.getSession().setAttribute("Teacher", teacher);
					map.put("msg", "/Teacher/indexUrl");
				} else {
					map.put("msg", "账号或密码错误！");
				}
			}
		}else {
			map.put("msg", "请求超时！");
		}
		return map;
	}

	@GetMapping("/Exit")
	public String exit() {
		/*
		 * SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * String date=sFormat.format(new Date());
		 */
		Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}

		return "redirect:/Login/LoginIndexUrl";
	}
}
