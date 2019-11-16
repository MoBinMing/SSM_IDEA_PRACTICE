package info.lzzy.controller;

import info.lzzy.models.Admin;
import info.lzzy.models.Course;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.models.view.CourseDao;
import info.lzzy.utils.DateTimeUtils;
import info.lzzy.utils.EmailUtils;
import info.lzzy.utils.Encipher;
import info.lzzy.utils.PhoneUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/Admin")
public class AdminController extends BaceController {

    @GetMapping("/AdminTeacherIndexUrl")
	public String AdminTeacherIndexUrl() {
		List<Teacher> teachers=teacherService.selectAllTeacher();
		for (Teacher t:teachers) {
			t.setPassword(Encipher.DecodePasswd(t.getPassword()));
		}
		request.getSession().setAttribute("teachers",teachers);
    	return "Admin/AdminTeacherIndex";
	}


	@GetMapping("/AdminAddTeacherIndexUrl")
	public String AdminAddTeacherIndexUrl() {
		return "Admin/AdminAddTeacherIndex";
	}
	@GetMapping("/deleteTeacher/{id}")
	@ResponseBody
	public Map<String,Object> deleteTeacher(@PathVariable String id ) {
		Map<String,Object> map=new HashMap<>();
		if (!id.equals("")){
			if (teacherService.deleteTeacherByKey(id)==1){
				List<Teacher> teachers=teacherService.selectAllTeacher();
				for (Teacher t:teachers) {
					t.setPassword(Encipher.DecodePasswd(t.getPassword()));
				}
				map.put("msg","succeed");
				map.put("deleteTeachers",teachers);
			}else {
				map.put("msg","删除失败");
			}
		}else {
			map.put("msg","非法请求");
		}
		return map;
	}

	@PostMapping("/updateTeacherValid")
	@ResponseBody
	public Map<String, Object> updateTeacherValid(int valid,String teacherId) {
		Map<String, Object> map=new HashMap<>();
		try {
			Teacher teacher=teacherService.selectTeacherById(teacherId);
			if (teacher!=null) {
				teacher.setValid(valid);
				if (teacherService.updateByPrimaryKeySelective(teacher)==1){
					map.put("msg","succeed");
					switch (valid){
						case 1:
							map.put("info","授权教师：\""+teacher.getName()+"\"成功");
							break;
						case 0:
							map.put("info","取消授权教师：\""+teacher.getName()+"\"成功");
							break;
						case 2:
							map.put("info","拒绝授权教师\""+teacher.getName()+"\"成功");
							break;
						default:
							map.put("info","数据库插入非法字段");
							break;
					}
				}else {
					map.put("msg","更新失败");
				}
			}else {
				map.put("msg", "教师不存在或以删除");
			}
		}catch (Exception e){
			map.put("msg", e.getMessage());
		}
		return map;
	}


	@PostMapping("/updateTeacherUrl")
	@ResponseBody
	public Map<String, Object> updateTeacherUrl(Teacher teacher) {
		Map<String, Object> map=new HashMap<>();
		try {
			Teacher userTeacher=teacherService.selectTeacherById(teacher.getTeacherId());
			if (userTeacher!=null) {
				teacher.setValid(userTeacher.getValid());
				teacher.setImghead(userTeacher.getImghead());
				teacher.setPassword(Encipher.EncodePasswd(teacher.getPassword()));
				if (teacherService.updateByPrimaryKeySelective(teacher)==1){
					map.put("msg","succeed");
				}else {
					map.put("msg","更新失败");
				}
			}else {
				map.put("msg", "教师不存在或以删除");
			}
		}catch (Exception e){
			map.put("msg", e.getMessage());
		}
		return map;
	}

	@PostMapping("/searchTeachers")
	@ResponseBody
	public Map<String, Object> searchTeachers(String kw) {
		Map<String, Object> map = new HashMap<>();
		List<Teacher> teachers;
		try {
			if (!kw.equals("")) {
				teachers=teacherService.selectByKw(kw);
			} else {
				teachers=teacherService.selectAllTeacher();
			}
			for (Teacher t:teachers) {
				t.setPassword(Encipher.DecodePasswd(t.getPassword()));
			}
			map.put("result", "succeed");
			map.put("teachers", teachers);
		}catch (Exception e){
			map.put("result", "error");
			map.put("info", e.getMessage());
		}
		return map;
	}
	@PostMapping("/addTeacherUrl")
	@ResponseBody
	public Map<String, Object> addTeacherUrl(Teacher teacher) {
		Map<String, Object> map = new HashMap<>();
		try {
			Teacher teacher1=teacherService.selectTeacherById(teacher.getTeacherId());
			if (teacher1==null){
				if (teacherService.insert(teacher)==1) {
					map.put("result", "succeed");
				} else {
					map.put("result", "error");
				}
			}else {
				map.put("result", "error");
				map.put("info", "教师工号：\""+teacher.getTeacherId()+"\" 已经注册！");
			}
		}catch (Exception e){
			map.put("result", "error");
			map.put("info", e.getMessage());
		}
		return map;
	}

	@PostMapping("/addTeachersUrl/{pw}")
	@ResponseBody
	public Map<String, Object> addTeachersUrl(@RequestBody List<String> teacherIds, @PathVariable String pw) {
		Map<String, Object> map = new HashMap<>();
		try {
			int oks=0;
			//存在的教师
			List<String> exisTeacherIds=new ArrayList<>();
			for (String tid:teacherIds){
				if (teacherService.selectTeacherById(tid)!=null){
					exisTeacherIds.add(tid);
				}else if (tid.length()<12 && pw!=null){
					Teacher teacher=new Teacher();
					teacher.setTeacherId(tid);
					teacher.setPassword(Encipher.EncodePasswd(pw));
					if (teacherService.insert(teacher)==1){
						oks++;
					}
				}
			}
			map.put("result", "succeed");
			map.put("succeed", oks);
			map.put("defeated", exisTeacherIds.size());
			map.put("defeatedIds", exisTeacherIds.toString());
		}catch (Exception e){
			map.put("result", "error");
			map.put("info", e.getMessage());
		}
		return map;
	}
}
