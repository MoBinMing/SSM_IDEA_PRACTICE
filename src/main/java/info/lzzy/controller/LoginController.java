package info.lzzy.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import info.lzzy.models.Admin;
import info.lzzy.utils.DateTimeUtils;
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

    private static final String SESSION_ROLE_KEY = "role";
    private static final String SESSION_ROLE_VALUE_ADMIN = "Admin";
    private static final String SESSION_ROLE_VALUE_TEACHER = "Teacher";
    private static final String SESSION_ROLE_VALUE_STUDENT = "Student";
    private static final String SESSION_USER_KEY = "user";

    @GetMapping("/LoginIndexUrl")
	public String LoginIndexUrl() {
		//request.getSession().setAttribute("read_file_role", true);
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
		long tome=((loginTime - time) / 1000);
		System.out.println("请求时间差"+tome + "当前时间："+ DateTimeUtils.DATE_TIME_FORMAT.format(new Date()));
		Map<String, Object> map = new HashMap<>();
		if (tome < 5) {
			Student thisStudent=studentService.selectStudentByIphone(iphone);
			if (thisStudent!=null) {
                String studentUserPaw=Encipher.DecodePasswd(thisStudent.getPassword());
                if (studentUserPaw.equals(password)) {
                    emptySession();
                    request.getSession().setAttribute(SESSION_ROLE_KEY, SESSION_ROLE_VALUE_STUDENT);
                    request.getSession().setAttribute(SESSION_USER_KEY, thisStudent);
                    map.put("msg", "ok");
                    map.put("link", "/Student/StudentIndex");
                }
			}else {
                Teacher teacher = teacherService.selectTeacherByIphone(iphone);
                if (teacher!=null) {
                    String teacherUserPaw=Encipher.DecodePasswd(teacher.getPassword());
                    if (teacherUserPaw.equals(password)) {
                        emptySession();
                        request.getSession().setAttribute(SESSION_ROLE_KEY, SESSION_ROLE_VALUE_TEACHER);
                        request.getSession().setAttribute(SESSION_USER_KEY, teacher);
                        map.put("msg", "ok");
                        map.put("link", "/Teacher/indexUrl");
                    } else {
                        map.put("msg", "error");
                        map.put("info", "账号或密码错误！");
                    }
                }else {
                    Admin admin=adminService.selectAdminByIphone(iphone);
                    if (admin!=null){
                        String adminUserPaw=Encipher.DecodePasswd(admin.getPassword());
                        if (adminUserPaw.equals(password)){
                            emptySession();
                            request.getSession().setAttribute(SESSION_ROLE_KEY, SESSION_ROLE_VALUE_ADMIN);
                            request.getSession().setAttribute(SESSION_USER_KEY, admin);
                        }else {
                            map.put("msg", "error");
                            map.put("info", "账号或密码错误！");
                        }
                    }else {
                        map.put("msg", "error");
                        map.put("info", "账号或密码错误！");
                    }
                }
            }

		}else {
            map.put("msg", "error");
			map.put("info", "请求超时！\n请求时间："+time+" -> var time = new Date().getTime();\n收到请求时间："+loginTime+"-> Long loginTime = System.currentTimeMillis();\n" +
                    "(（ 收到请求时间 - 请求时间 ）/ 1000 ) = "+((loginTime - time) / 1000)
                    +"\n连接时间超时（5秒） ");
		}
		return map;
	}

	@GetMapping("/Exit")
	public String exit() {
		/*
		 * SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 * String date=sFormat.format(new Date());
		 */
        emptySession();
        return "redirect:/Login/LoginIndexUrl";
	}

    public void emptySession() {
        Enumeration em = request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }
    }
}
