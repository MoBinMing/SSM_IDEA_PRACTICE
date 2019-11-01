package info.lzzy.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.utils.EmailUtils;
import info.lzzy.utils.Encipher;
import info.lzzy.utils.PhoneUtils;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaceController{
	@GetMapping("/addUser")
	public String addUser() {
		return "register/registerIndex";
	}
	@PostMapping("/addStudentUrl")
	public String addStudent(Student user) {
				Student student=studentService.selectStudentByEmail(user.getEmail());
				Student student2=studentService.selectStudentById(user.getStudentId());
				if (student==null&&student2==null) {
					user.setPassword(Encipher.EncodePasswd(user.getPassword()));
					if (studentService.insert(user)==0) {
						//注册失败
						return "register/registrationFailed";
					}
				}
		//注册成功
		return "register/registeredSuccessfully";
	}
	
	@PostMapping("/addTeacherUrl")
	public String addTeacher(Teacher user) {
		Teacher teacher=teacherService.selectTeacherByEmail(user.getEmail());
		Teacher teacher2=teacherService.selectTeacherById(user.getTeacherId());
				if (teacher==null&&teacher2==null) {
					user.setPassword(Encipher.EncodePasswd(user.getPassword()));
					if (teacherService.insert(user)==0) {
						//注册失败
						return "register/registrationFailed";
					}
				}
		//注册成功
		return "register/registeredSuccessfully";
	}
	
	@PostMapping("/ealidationStudentEmail")
	@ResponseBody
	public Map<String, Object> ealidationStudentEmail(String email,String iphone,String studentId) {
		Map<String, Object> map=new HashMap<>();
		if (EmailUtils.isEmail(email)) {
			Student studentByEmail=studentService.selectStudentByEmail(email);
			if (studentByEmail==null) {
				if (studentId!=null||studentId!="") {
					Student studentById=studentService.selectStudentById(studentId);
					if (studentById==null) {
						if (PhoneUtils.isValidChinesePhone(iphone)) {
							Student studentByIphone=studentService.selectStudentByIphone(iphone);
							if (studentByIphone==null) {
								map.put("code", 1);
								return map;
							}else {
								map.put("info", "手机号"+iphone+"已注册");
							}
						}else {
							map.put("info", "手机号非法");
						}
					}else {
						map.put("info", "学号"+studentId+"已注册");
					}
				}else {
					map.put("info", "学号不合法");
				}
			}else {
				map.put("info", "邮箱："+email+"已注册");
			}
		}else {
			map.put("info", "邮箱不合法");
		}
		map.put("code", 0);
		return map;
	}
	@PostMapping("/ealidationTeacherEmail")
	@ResponseBody
	public Map<String, Object> ealidationTeacherEmail(String email,String iphone,String teacherId) {
		Map<String, Object> map=new HashMap<>();
		if (EmailUtils.isEmail(email)) {
			Teacher teacherByEmail=teacherService.selectTeacherByEmail(email);
			if (teacherByEmail==null) {
				if (teacherId!=null||teacherId!="") {
					Teacher teacherById=teacherService.selectTeacherById(teacherId);
					if (teacherById==null) {
						if (PhoneUtils.isValidChinesePhone(iphone)) {
							Teacher teacherByIphone=teacherService.selectTeacherByIphone(iphone);
							if (teacherByIphone==null) {
								map.put("code", 1);
								return map;
							}else {
								map.put("info", "手机号"+iphone+"已注册");
							}
						}else {
							map.put("info", "手机号非法");
						}
					}else {
						map.put("info", "学号"+teacherId+"已注册");
					}
				}else {
					map.put("info", "学号不合法");
				}
			}else {
				map.put("info", "邮箱："+email+"已注册");
			}
		}else {
			map.put("info", "邮箱不合法");
		}
		map.put("code", 0);
		return map;
	}
}
