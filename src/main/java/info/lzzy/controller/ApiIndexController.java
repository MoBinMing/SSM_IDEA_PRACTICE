package info.lzzy.controller;

import info.lzzy.models.*;
import info.lzzy.models.view.CourseDao;
import info.lzzy.utils.DateTimeUtils;
import info.lzzy.utils.Encipher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/apiIndex")
public class ApiIndexController extends BaceController {



    @GetMapping("/apiIndexUrl")
	public String apiIndexUrl() {
		List<Api> teacherApis=apiService.selectByRole("teacher");
		List<Api> studentApis=apiService.selectByRole("student");
		request.getSession().setAttribute("teacherApis",teacherApis);
		request.getSession().setAttribute("studentApis",studentApis);
		return "Api/TeacherApiIndex";
	}
	@GetMapping("/apiIndexUrl1")
	public String apiIndexUrl1() {
		apiService.insert(new Api(UUID.randomUUID().toString(),"student",
				"测试接口", "接口测试", "http://www.baidu.com",
				"GET", 1, "成功html",
				"失败html", new Date(),"这是请求的数据"));
		apiService.insert(new Api(UUID.randomUUID().toString(),"teacher",
				"测试接口", "接口测试", "http://www.baidu.com",
				"GET", 1, "成功html",
				"失败html", new Date(),"这是请求的数据"));
		return "Api/TeacherApiIndex";
	}

	@PostMapping("/searchApis")
	@ResponseBody
	public Map<String, Object> searchCourses(String kw,String role) {
		Map<String, Object> map = new HashMap<>();
		List<Api> apis = new ArrayList<>();
		try {
			if (!kw.equals("")) {
				apis=apiService.selectByRoleAndKw(role,kw);
			}else {
				apis=apiService.selectByRole(role);
			}
		}catch (Exception e){
			System.out.println("查询Api时错误："+e.getMessage());
		}
		map.put("apis", apis);
		return map;
	}
}
