package info.lzzy.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import info.lzzy.models.*;
import info.lzzy.models.ApiView.ApiCourse;
import info.lzzy.models.ApiView.TeacherQuestion;
import net.sf.json.*;
import org.apache.ibatis.binding.BindingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javafx.util.Pair;
import info.lzzy.connstants.ApiConstants;
import info.lzzy.models.view.TeacherValidType;
import info.lzzy.models.view.UserLogin;
import info.lzzy.service.scoket.SocketServer;
import info.lzzy.utils.ApiUtil;
import info.lzzy.utils.Encipher;
import info.lzzy.utils.UploadImage;
import info.lzzy.utils.VerificationInfo;
import info.lzzy.utils.WebKeyUtils;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/api") // 映射路径，一个参数时等同于@RequestMapping(value = "/api")， 如果 value=""，将映射项目根路径
public class ApiController extends BaceController {
	public static String ENCODERULES_KEY = "abc123";
	//region 注册 -> 学生、教师
	@PostMapping(value = "/post_register", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String register(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationRegister(servletContext,request,body, studentService,
					teacherService);
			return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion
	// region 登录 -> 学生、教师、管理员
	@PostMapping(value = "/login", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String studentLogin(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Gson gson = new Gson();
			// Pair<String, Long> datas = WebKeyUtils.convertBodyReqest(body);
			UserLogin userLogin = gson.fromJson(body, UserLogin.class);
			// userLogin.setLoginTime(datas.getValue());
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationLogin(userLogin, studentService,
					teacherService, courseService, enrollmentService, adminService);
			json = pair.getValue();
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "非法请求，请查看接口文档，\n" + e.getMessage());
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion
	// region 获取所有教师 -> 学生、教师、管理员
	@PostMapping(value = "/get_all_teacher", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String get_all_teacher(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				if (bodyObj.getString("user").equals("student")) {
					String studentId = bodyObj.getString("studentId");
					List<Teacher> teachers = teacherService.selectAllTeacher();
					JSONArray jsonArray = new JSONArray();
					for (Teacher teacher : teachers) {
						if (teacher.getValid() == 1) {
							Boolean isMyTeacher = false;
							List<Course> courses = courseService.selectByTeacherId(teacher.getTeacherId());
							for (Course course : courses) {
								Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(course.getId(),
										studentId);
								if (enrollment != null) {
									isMyTeacher = true;
								}
							}
							jsonArray.add(teacher.toJSON(isMyTeacher));
						}
					}
					json.put("size", jsonArray.size());
					json.put("teachers", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					List<Teacher> teachers = teacherService.selectAllTeacher();
					JSONArray jsonArray = new JSONArray();
					for (Teacher teacher : teachers) {
						if (teacher.getValid() == 1) {
							if (teacher.getTeacherId().equals(teacherId)) {
								jsonArray.add(teacher.toJSON(true));
							} else {
								jsonArray.add(teacher.toJSON(false));
							}
						}
					}
					json.put("size", jsonArray.size());
					json.put("teachers", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("admin")) {
					List<Teacher> teachers = teacherService.selectAllTeacher();
					JSONArray jsonArray = new JSONArray();
					for (Teacher teacher : teachers) {
						jsonArray.add(teacher.toTeacherJSON());
					}
					json.put("size", jsonArray.size());
					json.put("teachers", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非学生、非教师、非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 上传练习结果 -> 学生、教师、管理员
	@PostMapping(value = "/post_PracticeResult", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String post_PracticeResult(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = JSONObject.fromObject(bodyObj.getString("userBody"));
				Gson gson = new Gson();
				JSONArray jsonArray = userBody.getJSONArray("practiceResults");
				List<PracticeResult> practiceResults = new ArrayList<>();

				for (Object object : jsonArray) {
					PracticeResult practiceResult = gson.fromJson(object.toString(), PracticeResult.class);
					Integer maxId=practiceResultService.getIdByMax();
					practiceResult.setId(maxId==null?1:maxId+1);
					practiceResult.setStudentId(bodyObj.getString("studentId"));
					List<PracticeResult> practiceResults1=practiceResultService.selectBySIdAndPIdAndQId(practiceResult.getPracticeId(),
							practiceResult.getStudentId(), practiceResult.getQuestionId());
					if (practiceResults1==null || practiceResults1.size() == 0) {
						if (!ApiUtil.allFieldIsNULL(practiceResult)) {
							if (practiceResult.getStudentAnswer().split(",").length > 0) {
								if (practiceService.getPracticeById(practiceResult.getPracticeId()) != null) {
									practiceResults.add(practiceResult);
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "参数非法,不存在practice");
									break;
								}
							} else {
								practiceResults.clear();
								json.put("RESULT", "F");
								json.put("ERRMSG", "参数非法");
								break;
							}

						} else {
							practiceResults.clear();
							json.put("RESULT", "F");
							json.put("ERRMSG", "参数非法");
							break;
						}
					} else {
						practiceResults.clear();
						json.put("RESULT", "F");
						json.put("ERRMSG", "已提交练习结果");
						break;
					}

				}
				if (practiceResults.size() == jsonArray.size()) {
					int okSize = 0;
					for (PracticeResult practiceResult : practiceResults) {
						Integer maxId = 0;
						try {
							maxId = practiceResultService.getIdByMax();
						} catch (BindingException e) {

						}
						practiceResult.setId(maxId==null?1:maxId+1);
						if (practiceResultService.insert(practiceResult) == 1) {
							okSize++;
						}
					}
					if (okSize == practiceResults.size()) {
						json.put("RESULT", "S");
						json.put("ERRMSG", "提交成功");
					}
				}
				// endregion

			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion

	// region 获取课程 -> 学生、教师、管理员
	@PostMapping(value = "/get_course", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String get_course(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					String teacherId = userBody.getString("teacherId");
					String studentId = bodyObj.getString("studentId");
					List<Course> courses = courseService.selectByTeacherId(teacherId);
					JSONArray jsonArray = new JSONArray();
					for (Course course : courses) {
						Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(course.getId(), studentId);
						if (enrollment != null) {
							jsonArray.add(course.toJSON(enrollment.getTakeEffect()));
						} else {
							jsonArray.add(course.toJSON(-1));
						}

					}
					json.put("size", jsonArray.size());
					json.put("courses", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					List<Course> courses = courseService.selectByTeacherId(teacherId);
					JSONArray jsonArray = new JSONArray();
					for (Course course : courses) {
						jsonArray.add(course.toJSON(true));
					}
					json.put("size", jsonArray.size());
					json.put("courses", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("admin")) {
					String teacherId = bodyObj.getString("teacherId");
					List<Course> courses = courseService.selectByTeacherId(teacherId);
					JSONArray jsonArray = new JSONArray();
					for (Course course : courses) {
						jsonArray.add(course.toJSON(true));
					}
					json.put("size", jsonArray.size());
					json.put("courses", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非学生、非教师、非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 添加课程 -> 教师
	@PostMapping(value = "/post_add_course", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String add_course(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Gson gson = new Gson();
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限添加课程");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					Teacher teacher = teacherService.selectTeacherById(teacherId);
					if (teacher != null) {
						switch (teacher.getValid()) {
							case 0:
								json.put("RESULT", "F");
								json.put("ERRMSG", TeacherValidType.getInstance(0).toString());
								break;
							case 1:

								List<ApiCourse> apiCourses = gson.fromJson(userBody.getString("courses"),
										new TypeToken<List<ApiCourse>>() {
										}.getType());
								if(apiCourses.size()!=0){
									int s = 0;
									int f = 0;
									int t = 0;
									StringBuilder fBuilder = new StringBuilder();
									StringBuilder tBuilder = new StringBuilder();
									for (ApiCourse apiCourse : apiCourses) {
										Course course=new Course();
										course.setTeacherId(teacherId);
										course.setAddTime(new Date());
										course.setName(apiCourse.getName());
										course.setIntro(apiCourse.getIntro().equals("") ?"无":apiCourse.getIntro());
										course.setAge(apiCourse.getAge());
										course.setId(courseService.getIdByMax()+1);
										if (courseService.selectByName(course.getName()) == null) {
											if (courseService.insertSelective(course) == 1) {
												s++;
											} else {
												f++;
												fBuilder.append(course.getName() + ",");
											}
										} else {
											t++;
											tBuilder.append("课程" + course.getName() + "已存在,");
										}

									}
									if (s == apiCourses.size()) {
										json.put("RESULT", "S");
										json.put("successfulSize", s);
										json.put("failuresSize", f);
										json.put("illegalSize", t);
										json.put("ERRMSG", "添加成功：" + s + "个,添加失败：" + f + "个,不合法：" + t + "个");
									} else {
										json.put("RESULT", "F");
										json.put("successfulSize", s);
										json.put("failuresSize", f);
										json.put("illegalSize", t);
										json.put("ERRMSG", "添加成功：" + s + "个,添加失败：" + f + "个," + fBuilder.toString() + "不合法：" + t
												+ "个," + tBuilder.toString());
									}
								}else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "请求非法，参数不全");
								}
								break;
							case 2:
								json.put("RESULT", "F");
								json.put("ERRMSG", TeacherValidType.getInstance(2).toString());
								break;
							default:
								break;
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未注册注册教师");
					}
				} else if (bodyObj.getString("user").equals("admin")) {
					String teacherId = userBody.getString("teacherId");
					Teacher teacher = teacherService.selectTeacherById(teacherId);
					if (teacher != null) {
						switch (teacher.getValid()) {
							case 0:
								json.put("RESULT", "F");
								json.put("ERRMSG", TeacherValidType.getInstance(0).toString());
								break;
							case 1:
								List<Course> courses = gson.fromJson(userBody.getString("courses"),
										new TypeToken<List<Course>>() {
										}.getType());
								int s = 0;
								int f = 0;
								int t = 0;
								StringBuilder fBuilder = new StringBuilder();
								StringBuilder tBuilder = new StringBuilder();
								for (Course course : courses) {
									if (course.getTeacherId().equals(teacherId)) {
										course.setAddTime(new Date());
										if (courseService.insert(course) == 1) {
											s++;
										} else {
											f++;
											fBuilder.append(course.getName() + ",");
										}
									} else {
										t++;
										tBuilder.append(course.getName() + ",");
									}
								}
								if (s == courses.size()) {
									json.put("RESULT", "S");
									json.put("successfulSize", s);
									json.put("failuresSize", f);
									json.put("illegalSize", t);
									json.put("ERRMSG", "添加成功：" + s + "个\n" + "添加失败：" + f + "个\n" + "不合法：" + t + "个");
								} else {
									json.put("RESULT", "F");
									json.put("successfulSize", s);
									json.put("failuresSize", f);
									json.put("illegalSize", t);
									json.put("ERRMSG", "添加成功：" + s + "个\n" + "添加失败：" + f + "个\n" + fBuilder.toString()
											+ "不合法：" + t + "个\n" + tBuilder.toString());
								}
								break;
							case 2:
								json.put("RESULT", "F");
								json.put("ERRMSG", TeacherValidType.getInstance(2).toString());
								break;
							default:
								break;
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未注册注册教师");
					}
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非学生、非教师、非管理员");
				}
				// endregion

			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (

				Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 删除课程 -> 教师、管理员
	@PostMapping(value = "/post_delete_course", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String post_delete_course(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Gson gson = new Gson();
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限删除课程");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					Integer courseId = userBody.getInt("courseId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (course.getTeacherId().equals(teacherId)) {
							if (courseService.deleteByPrimaryKey(courseId) == 1) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "删除成功");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "删除失败,该课程可能已经不存在");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "删除失败，该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "删除失败，该课程不存在");
					}

				} else if (bodyObj.getString("user").equals("admin")) {
					Integer courseId = userBody.getInt("courseId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (courseService.deleteByPrimaryKey(courseId) == 1) {
							json.put("RESULT", "S");
							json.put("ERRMSG", "删除成功");
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "删除失败,该课程可能已经不存在");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "删除失败，该课程不存在");
					}
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非学生、非教师、非管理员");
				}
				// endregion

			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 更新课程 -> 教师、管理员
	@PostMapping(value = "/post_update_course", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String post_update_course(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Gson gson = new Gson();
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限编辑课程");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					Integer courseId = userBody.getInt("courseId");
					Course course1 = courseService.selectByPrimaryKey(courseId);
					if (teacherId.equals(course1.getTeacherId())) {
						Course course = gson.fromJson(userBody.getString("course"), Course.class);
						if (course.getName() != null && course.getIntro() != null
							|| course.getAge()!=null) {
							course1.setName(course.getName());
							course1.setIntro(course.getIntro());
							course1.setAge(course.getAge());
							if (courseService.updateByPrimaryKeySelective(course1) == 1) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "编辑课程成功");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "编辑课程失败，执行sql语句时失败");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "编辑课程失败，信息课程不能为null");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "编辑课程失败，该课程不是您的课程");
					}
				} else if (bodyObj.getString("user").equals("admin")) {
					Integer courseId = userBody.getInt("courseId");
					Course course1 = courseService.selectByPrimaryKey(courseId);
					Course course = gson.fromJson(userBody.getString("course"), Course.class);
					if (course.getName() != null && course.getIntro() != null) {
						course1.setName(course.getName());
						course1.setIntro(course.getIntro());
						if (courseService.updateByPrimaryKey(course) == 1) {
							json.put("RESULT", "S");
							json.put("ERRMSG", "编辑课程成功");
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "编辑课程失败");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "编辑课程失败，信息课程不能为null");
					}
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：user名称不存在");
				}
				// endregion

			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 获取练习 -> 学生、教师
	@PostMapping(value = "/get_practice", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String get_all_Practice(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					Integer courseId = userBody.getInt("courseId");
					String studentId = bodyObj.getString("studentId");
					Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId, studentId);
					if (enrollment == null) {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未申请该课程");
					} else {
						switch (enrollment.getTakeEffect()) {
							case 0:
								json.put("RESULT", "F");
								json.put("ERRMSG", "无权限,该课程正在等待审批中");
								break;
							case 1:
								List<Practice> pList = practiceService.getPracticeByCourseId(courseId);
								JSONArray jsonArray = new JSONArray();
								if (pList.size() > 0) {
									for (Practice practice : pList) {
										JSONObject jObject = new JSONObject();
										jObject.put("pId", practice.getId());
										jObject.put("name", practice.getName());
										jObject.put("questionCount", practice.getQuestionCount());
										jObject.put("upTime", format.format(practice.getUpTime()));
										jObject.put("isReady", practice.getIsReady());
										jObject.put("outlines", practice.getOutlines());
										jObject.put("courseId", practice.getCourseId());
										jsonArray.add(jObject);
									}
								}
								json = JSONObject.fromObject(RETURN_S_String);
								json.put("practices", jsonArray.toString());
								json.put("size", jsonArray.size());
								break;

							case 2:
								json.put("RESULT", "F");
								json.put("ERRMSG", "无权限,申请该课程未得到审批");
								break;
							default:
								break;
						}

					}
				} else {
					Integer courseId = userBody.getInt("courseId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (bodyObj.getString("teacherId").equals(course.getTeacherId())) {
							List<Practice> pList = practiceService.getPracticeByCourseId(courseId);
							json = JSONObject.fromObject(RETURN_S_String);
							JSONArray jsonArray = new JSONArray();
							if (pList.size() > 0) {
								for (Practice practice : pList) {
									JSONObject jObject = new JSONObject();
									jObject.put("id", practice.getId());
									jObject.put("name", practice.getName());
									jObject.put("questionCount", practice.getQuestionCount());
									jObject.put("upTime", format.format(practice.getUpTime()));
									jObject.put("isReady", practice.getIsReady());
									jObject.put("outlines", practice.getOutlines());
									jObject.put("courseId", practice.getCourseId());
									jsonArray.add(jObject);
								}
							}
							json.put("practices", jsonArray.toString());
							json.put("size", pList.size());
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程的练习不是您的练习");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未存在该课程，不存在练习");
					}

				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 添加练习 -> 教师
	@PostMapping(value = "/add_practice", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String add_practice(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限添加练习");
				} else{
					Integer courseId = userBody.getInt("courseId");
					String name = userBody.getString("name");
					String outlines = userBody.getString("outlines");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (bodyObj.getString("teacherId").equals(course.getTeacherId())) {
							Practice practice = practiceService.getByName(name);
							if (practice == null) {
								practice = new Practice();
								practice.setCourseId(courseId);
								practice.setOutlines(outlines);
								practice.setIsReady(0);
								practice.setUpTime(new Date());
								practice.setQuestionCount(0);
								practice.setName(name);
								practice.setId(practiceService.getIdByMax()+1);
								if (practiceService.addPractice(practice) == 1) {
									json.put("RESULT", "S");
									json.put("ERRMSG", "添加成功");
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "添加时失败");
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "已存在该名称的练习");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该课程");
					}

				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 删除练习 -> 教师
	@PostMapping(value = "/delete_practice", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String delete_practice(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限删除练习");
				} else {
					Integer practiceId = userBody.getInt("practiceId");
					Practice practice = practiceService.getPracticeById(practiceId);
					Course course = courseService.selectByPrimaryKey(practice.getCourseId());
					if (course != null) {
						if (bodyObj.getString("teacherId").equals(course.getTeacherId())) {
							if (practice != null) {
								if (practiceService.deletePracticeByKey(practice.getId()) == 1) {

									json.put("RESULT", "S");
									json.put("ERRMSG", "删除成功");
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "删除时失败");
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "不存在该练习");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该课程的练习");
					}

				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 更新练习 -> 教师
	@PostMapping(value = "/update_practice", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String update_practice(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限更新练习");
				} else {
					Integer practiceId = userBody.getInt("practiceId");
					Practice practice = practiceService.getPracticeById(practiceId);
					Course course = courseService.selectByPrimaryKey(practice.getCourseId());
					if (course != null) {
						if (bodyObj.getString("teacherId").equals(course.getTeacherId())) {
							if (practice != null) {
								String name = userBody.getString("name");
								String outlines = userBody.getString("outlines");
								//practice.setIsReady(practice2.getIsReady());
								practice.setName(name);
								practice.setOutlines(outlines);
								if (practiceService.updatePracticeSelective(practice) == 1) {
									json.put("RESULT", "S");
									json.put("ERRMSG", "更新成功");
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "更新时失败");
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "不存在该练习");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该课程的练习");
					}

				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 上线下线练习 -> 教师
	@PostMapping(value = "/update_practice_ready", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String update_practice_ready(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else if (bodyObj.getString("user").equals("teacher")) {
					Integer practiceId = userBody.getInt("practiceId");
					Integer isReady = userBody.getInt("isReady");
					if (isReady==1 || isReady==0){
						Practice practice = practiceService.getPracticeById(practiceId);
						practice.setIsReady(isReady);
						if (practiceService.updatePracticeSelective(practice) == 1) {
							json.put("RESULT", "S");
							json.put("ERRMSG", "更新成功");
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "更新时失败");
						}
					}
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 获取题目 -> 学生、教师
	@PostMapping(value = "/get_question", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String get_all_question(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				Integer practiceId = userBody.getInt("practiceId");
				Integer courseId = userBody.getInt("courseId");
				if (bodyObj.getString("user").equals("student")) {
					String studentId = bodyObj.getString("studentId");
					Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId, studentId);
					if (enrollment == null) {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未申请该课程");
					} else {
						switch (enrollment.getTakeEffect()) {
							case 0:
								json.put("RESULT", "F");
								json.put("ERRMSG", "无权限,该课程正在等待审批中");
								break;
							case 1:
								json = getQuestions(json, practiceId,studentId);
								break;

							case 2:
								json.put("RESULT", "F");
								json.put("ERRMSG", "无权限,申请该课程未得到审批");
								break;
							default:
								break;
						}

					}
				} else {
					String teacherId = bodyObj.getString("teacherId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (course.getTeacherId().equals(teacherId)) {
							json = teacherGetQuestions(json, practiceId);
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未创建该课程");
					}
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	public JSONObject getQuestions(JSONObject json, Integer practiceId,String studentId) {
		Practice practice = practiceService.getPracticeById(practiceId);
		if (practice != null) {
			if (practice.getIsReady() == 1) {
				List<Question> qList = questionsService.getQuestionByPracticeId(practiceId);
				JSONArray questionArray = new JSONArray();
				if (qList.size() > 0) {
					for (Question question : qList) {
						JSONObject questionjObject = new JSONObject();
						questionjObject.put("qId", question.getId());
						questionjObject.put("content", question.getContent());
						questionjObject.put("questionType", question.getQuestionType());
						questionjObject.put("number", question.getNumber());
						questionjObject.put("analysis", question.getAnalysis());
						//questionjObject.put("practiceId", practiceId);
						List<Option> options = optionService.getOptionByQuestionKey(question.getId());
						JSONArray optionArray = new JSONArray();
						for (Option option : options) {
							JSONObject optionjObject = new JSONObject();
							optionjObject.put("id", option.getId());
							optionjObject.put("content", option.getContent());
							optionjObject.put("label", option.getLabel());
							optionjObject.put("questionId", option.getQuestionId());
							optionjObject.put("isAnswer", option.getIsAnswer());
							optionArray.add(optionjObject);
						}
						questionjObject.put("options", optionArray.toString());
						if (studentId!=null){
							questionjObject.put("practiceResults",
									practiceResultService.selectBySIdAndPIdAndQId(practiceId,studentId,question.getId()));
						}
						questionArray.add(questionjObject);
					}
				}
				json = JSONObject.fromObject(RETURN_S_String);
				json.put("questions", questionArray.toString());
				json.put("size", questionArray);
			} else {
				json.put("RESULT", "F");
				json.put("ERRMSG", "练习: \"" + practiceId + "\"未开放");
			}
		} else {
			json.put("RESULT", "F");
			json.put("ERRMSG", "练习: \"" + practiceId + "\"不存在");
		}
		return json;
	}
	public JSONObject teacherGetQuestions(JSONObject json, Integer practiceId) {
		Practice practice = practiceService.getPracticeById(practiceId);
		if (practice != null) {
			if (practice.getIsReady() == 1) {
				List<Question> qList = questionsService.getQuestionByPracticeId(practiceId);
				JSONArray questionArray = new JSONArray();
				if (qList.size() > 0) {
					for (Question question : qList) {
						JSONObject questionjObject = new JSONObject();
						questionjObject.put("id", question.getId());
						questionjObject.put("content", question.getContent());
						questionjObject.put("questionType", question.getQuestionType());
						questionjObject.put("number", question.getNumber());
						questionjObject.put("analysis", question.getAnalysis());
						questionjObject.put("practiceId", practiceId);
						List<Option> options = optionService.getOptionByQuestionKey(question.getId());
						JSONArray optionArray = new JSONArray();
						for (Option option : options) {
							JSONObject optionjObject = new JSONObject();
							optionjObject.put("id", option.getId());
							optionjObject.put("content", option.getContent());
							optionjObject.put("label", option.getLabel());
							optionjObject.put("questionId", option.getQuestionId());
							optionjObject.put("isAnswer", option.getIsAnswer());
							optionArray.add(optionjObject);
						}
						questionjObject.put("options", optionArray.toString());
						questionArray.add(questionjObject);
					}
				}
				json = JSONObject.fromObject(RETURN_S_String);
				json.put("questions", questionArray.toString());
				json.put("size", questionArray);
			} else {
				json.put("RESULT", "F");
				json.put("ERRMSG", "练习: \"" + practiceId + "\"未开放");
			}
		} else {
			json.put("RESULT", "F");
			json.put("ERRMSG", "练习: \"" + practiceId + "\"不存在");
		}
		return json;
	}

	// endregion
	// region 添加题目 -> 教师
	@PostMapping(value = "/add_question", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String add_question(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				TeacherQuestion teacherQuestion = new Gson().fromJson(userBody.getString("question"),TeacherQuestion.class);
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else {
					Question question=new Question();
					question.setId(questionsService.getIdByMax()+1);
					question.setQuestionType(teacherQuestion.getQuestionType());
					question.setContent(teacherQuestion.getContent());
					question.setAnalysis(teacherQuestion.getAnalysis());
					question.setPracticeId(teacherQuestion.getPracticeId());
					try {
						question.setNumber(questionsService.getNumberByMax(question.getPracticeId())+1);
					}catch (Exception e){
						question.setNumber(1);
					}
					List<Option> options=teacherQuestion.getOptions();
					if (questionsService.insert(question) == 1) {
						Practice practice = practiceService.getPracticeById(question.getPracticeId());
						practice.setQuestionCount(questionsService.getQuestionByPracticeId(question.getPracticeId()).size());
						practiceService.updatePracticeSelective(practice);
						for (Option option:options) {
							option.setQuestionId(question.getId());
							option.setId(optionService.getIdByMax()+1);
							optionService.insert(option);
						}
						json.put("RESULT", "S");
						json.put("ERRMSG", "添加成功");
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "添加时失败");
					}
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 更新题目 -> 教师
	@PostMapping(value = "/update_question", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String update_question(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				String content = userBody.getString("content");
				int question_type = userBody.getInt("question_type");
				int number = userBody.getInt("number");
				String analysis = userBody.getString("analysis");
				Integer courseId = userBody.getInt("courseId");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else {
					String teacherId = bodyObj.getString("teacherId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (course.getTeacherId().equals(teacherId)) {
							Question question = new Question();
							question.setAnalysis(analysis);
							question.setContent(content);
							question.setNumber(number);
							question.setQuestionType(question_type);
							if (questionsService.updateByPrimaryKey(question) == 1) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "更新成功");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "更新时失败");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未创建该课程");
					}
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 删除题目 -> 教师
	@PostMapping(value = "/delete_question", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String delete_question(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				int questionId = userBody.getInt("questionId");
				Integer courseId = userBody.getInt("courseId");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else {
					String teacherId = bodyObj.getString("teacherId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						if (course.getTeacherId().equals(teacherId)) {
							Integer pId=questionsService.getById(questionId).getPracticeId();
							if (questionsService.deleteByPrimaryKey(questionId) == 1) {
								Practice practice2 = practiceService.getPracticeById(pId);
								practice2.setQuestionCount(questionsService.getQuestionByPracticeId(pId).size());
								practiceService.updatePracticeSelective(practice2);
								json.put("RESULT", "S");
								json.put("ERRMSG", "删除成功");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "删除时失败");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "未创建该课程");
					}
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 添加选项 -> 教师、管理员
	@PostMapping(value = "/add_option", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String add_option(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				Gson gson = new Gson();
				List<Option> options = gson.fromJson(userBody.toString(), new TypeToken<LinkedList<Option>>() {
				}.getType());
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else {
					int ok = 0;
					for (Option option2 : options) {
						if (optionService.insert(option2) == 1) {
							ok++;
						}
					}
					if (ok == options.size()) {
						json.put("RESULT", "S");
						json.put("ERRMSG", "添加成功");
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "添加失败");
					}

				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 删除选项 -> 教师、管理员
	@PostMapping(value = "/delete_option", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String delete_option(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				int optionId = userBody.getInt("optionId");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else {
					if (optionService.deleteByPrimaryKey(optionId) == 1) {
						json.put("RESULT", "S");
						json.put("ERRMSG", "删除成功");
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "删除失败");
					}

				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 更新选项 -> 教师、管理员
	@PostMapping(value = "/update_option", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String update_option(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				Gson gson = new Gson();
				Option option = gson.fromJson(userBody.getString("option"), Option.class);
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "无权限");
				} else {
					if (optionService.updateByPrimaryKey(option) == 1) {
						json.put("RESULT", "S");
						json.put("ERRMSG", "更新成功");
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "更新失败");
					}
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 申请课程 -> 学生
	@PostMapping(value = "/course_applied", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String course_applied(@RequestBody String body) {
		JSONObject json = new JSONObject();
		JSONArray ssArray = new JSONArray();
		JSONArray eeArray = new JSONArray();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				int takeEffect = -1;
				try {
					takeEffect = userBody.getInt("takeEffect");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (bodyObj.getString("user").equals("student")) {
					Integer courseId = userBody.getInt("courseId");
					String studentId = bodyObj.getString("studentId");
					Course course = courseService.selectByPrimaryKey(courseId);
					if (course != null) {
						Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId, studentId);
						if (enrollment == null) {
							enrollment = new Enrollment();
							enrollment.setCourseId(courseId);
							enrollment.setStudentId(studentId);
							enrollment.setTakeEffect(0);
							enrollment.setTeacherId(course.getTeacherId());
							if (enrollmentService.insertSelective(enrollment) == 1) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "以提交申请");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "提交申请失败");
							}
						} else {
							switch (enrollment.getTakeEffect()) {
								case 0:
									json.put("RESULT", "F");
									json.put("ERRMSG", "已申请该课程,等待老师授权");
									break;
								case 1:
									json.put("RESULT", "F");
									json.put("ERRMSG", "已授权课程申请");
									break;
								case 2:
									json.put("RESULT", "F");
									json.put("ERRMSG", "拒绝你对课程”" + course.getName() + "“的申请");
									break;
								default:
									break;
							}
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "没有该课程");
					}
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法："+"非学生");
				}
			} else {
				JSONObject j = pair.getValue();
				j.put("S", ssArray.toString());
				j.put("E", eeArray.toString());
				return WebKeyUtils.encryptionReturnRequest(j.toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
			json.put("S", ssArray.toString());
			json.put("E", eeArray.toString());
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion
	// region 授权学生 -> 教师、管理员
	@PostMapping(value = "/course_empower", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String course_empower(@RequestBody String body) {
		JSONObject json = new JSONObject();
		JSONArray ssArray = new JSONArray();
		JSONArray eeArray = new JSONArray();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				int takeEffect = -1;
				try {
					takeEffect = userBody.getInt("takeEffect");
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (takeEffect == 1 || takeEffect == 0 || takeEffect == -1 || takeEffect == 2) {
					if (bodyObj.getString("user").equals("teacher")) {
						// region teacher
						Integer courseId = userBody.getInt("courseId");
						String teacherId = bodyObj.getString("teacherId");
						JSONArray studentIds = userBody.getJSONArray("studentIds");
						// String[] studentIdss = (String[]) studentIds.toArray();
						Course course = courseService.selectByPrimaryKey(courseId);
						if (course != null) {
							if (teacherId.equals(course.getTeacherId())) {

								for (int i = 0; i < studentIds.size(); i++) {
									Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId,
											studentIds.getString(i));
									if (enrollment == null) {
										enrollment = new Enrollment();
										enrollment.setCourseId(courseId);
										enrollment.setStudentId(studentIds.getString(i));
										enrollment.setTakeEffect(takeEffect);
										if (enrollmentService.insertSelective(enrollment) == 1) {
											ssArray.add(studentIds.getString(i));
										} else {
											eeArray.add(studentIds.getString(i));
										}
									} else {
										if (takeEffect == -1) {
											if (enrollmentService.deleteByPrimaryKey(enrollment.getId()) == 1) {
												ssArray.add(studentIds.getString(i));
											} else {
												eeArray.add(studentIds.getString(i));
											}
										} else {
											switch (takeEffect) {
												case 0:
													enrollment.setTakeEffect(takeEffect);
													if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {

														ssArray.add(studentIds.getString(i));
													} else {
														eeArray.add(studentIds.getString(i));
													}
													break;
												case 1:
													enrollment.setTakeEffect(takeEffect);
													if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {

														ssArray.add(studentIds.getString(i));
													} else {
														eeArray.add(studentIds.getString(i));
													}
													break;
												case 2:
													enrollment.setTakeEffect(takeEffect);
													if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {

														ssArray.add(studentIds.getString(i));
													} else {
														eeArray.add(studentIds.getString(i));
													}
													break;
												default:
													break;
											}
										}

									}
								}
								if (ssArray.size() == studentIds.size()) {
									json.put("RESULT", "S");
									json.put("ERRMSG", "成功");
									json.put("S", ssArray.toString());
									json.put("E", eeArray.toString());
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "失败");
									json.put("S", ssArray.toString());
									json.put("E", eeArray.toString());
								}

							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "不是您的课程");
								json.put("S", ssArray.toString());
								json.put("E", eeArray.toString());
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "没有该课程");
							json.put("S", ssArray.toString());
							json.put("E", eeArray.toString());
						}
						// endregion
					} else if (bodyObj.getString("user").equals("admin")) {
						// region admin
						Integer courseId = userBody.getInt("courseId");
						JSONArray studentIds = userBody.getJSONArray("studentIds");
						Course course = courseService.selectByPrimaryKey(courseId);
						if (course != null) {
							for (int i = 0; i < studentIds.size()-1; i++) {
								Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId,
										studentIds.getString(i));
								if (enrollment == null) {
									enrollment = new Enrollment();
									enrollment.setCourseId(courseId);
									enrollment.setStudentId(studentIds.getString(i));
									enrollment.setTakeEffect(1);
									if (enrollmentService.insertSelective(enrollment) == 1) {
										JSONObject object = new JSONObject();
										object.put(studentIds.getString(i), "成功");
										ssArray.add(object);
									} else {
										JSONObject object = new JSONObject();
										object.put(studentIds.getString(i), "报错");
										eeArray.add(object);
									}
								} else {
									JSONObject object = new JSONObject();
									switch (enrollment.getTakeEffect()) {
										case -1:
											enrollmentService.deleteByPrimaryKey(enrollment.getId());
											json.put("RESULT", "S");
											json.put("ERRMSG", "删除成功");
											break;
										case 0:
											enrollment.setTakeEffect(takeEffect);
											if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {
												object.put(studentIds.getString(i), "成功");
												ssArray.add(object);
											} else {
												object.put(studentIds.getString(i), "报错");
												eeArray.add(object);
											}
											break;
										case 1:
											enrollment.setTakeEffect(takeEffect);
											if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {
												object.put(studentIds.getString(i), "成功");
												ssArray.add(object);
											} else {
												object.put(studentIds.getString(i), "报错");
												eeArray.add(object);
											}
											break;
										case 2:
											enrollment.setTakeEffect(takeEffect);
											if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {
												object.put(studentIds.getString(i), "成功");
												ssArray.add(object);
											} else {
												object.put(studentIds.getString(i), "报错");
												eeArray.add(object);
											}
											break;
										default:
											break;
									}
								}
							}
							if (ssArray.size() == studentIds.size()) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "添加成功");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "未全部添加到课程");
								json.put("S", ssArray.toString());
								json.put("E", eeArray.toString());
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "没有该课程");
							json.put("S", ssArray.toString());
							json.put("E", eeArray.toString());
						}
						// endregion
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "请求非法");
						json.put("S", ssArray.toString());
						json.put("E", eeArray.toString());
					}
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "参数:takeEffect非法");
					json.put("S", ssArray.toString());
					json.put("E", eeArray.toString());
					// endregion
				}
			} else {
				JSONObject j = pair.getValue();
				j.put("S", ssArray.toString());
				j.put("E", eeArray.toString());
				return WebKeyUtils.encryptionReturnRequest(j.toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
			json.put("S", ssArray.toString());
			json.put("E", eeArray.toString());
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion
	//region 获取所有学生 -> 教师、管理员
	@PostMapping(value = "/get_all_student", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String get_all_student(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				//region 验证成功
				JSONObject bodyObj = pair.getValue();
				String teacherId = bodyObj.getString("teacherId");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限获取");
				} else if (bodyObj.getString("user").equals("teacher")) {
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						boolean isMyStudent = false;
						List<Enrollment> enrollments=enrollmentService.getByTeacherIdAndStudentId(teacherId, student.getStudentId());
						for (Enrollment e:enrollments) {
							if ( e.getStudentId().equals(student.getStudentId())) {
								isMyStudent = true;
							}
							jsonArray.add(student.toTeacherJson(isMyStudent,e.getTakeEffect()));
						}
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.size());
					json.put("students", jsonArray.toString());
				} else if (bodyObj.getString("user").equals("admin")) {
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.add(student.toS());
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.size());
					json.put("students", jsonArray.toString());
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非教师、非管理员");
				}
				//endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	//region 获取教师的学生 -> 教师、管理员
	@PostMapping(value = "/getStudentsByTeacher", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String getStudentsByTeacher(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = JSONObject.fromObject(pair.getValue());
				JSONObject userBody = bodyObj.getJSONObject("userBody");


				int courseId = userBody.getInt("courseId");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限获取");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						List<Enrollment> enrollments=enrollmentService.getByTeacherIdAndStudentId(teacherId, student.getStudentId());
						for (Enrollment e:enrollments) {
							if ( e.getStudentId().equals(student.getStudentId())) {
								jsonArray.add(student.toTeacherJson(true,e.getTakeEffect()));
							}

						}
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("students", jsonArray.toString());
				} else if (bodyObj.getString("user").equals("admin")) {
					String teacherId = userBody.getString("teacherId");
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						List<Enrollment> enrollments=enrollmentService.getByTeacherIdAndStudentId(teacherId, student.getStudentId());
						for (Enrollment e:enrollments) {
							if ( e.getStudentId().equals(student.getStudentId())) {
								jsonArray.add(student.toS());
							}

						}
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("students", jsonArray.toString());
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非教师、非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion
	// region 获取教师课程下的学生 -> 教师、管理员
	@PostMapping(value = "/get_student_by_courseId", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String get_student_by_courseId(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限获取");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId= bodyObj.getString("teacherId");
					int courseId = userBody.getInt("courseId");
					if (courseService.selectByPrimaryKey(courseId).getTeacherId().equals(teacherId)){
						List<Student> students = studentService.selectStudentByCourseId(courseId);
						List<Enrollment> enrollments = enrollmentService.getByCourseId(courseId);
						JSONArray jsonArray = new JSONArray();
						for (Enrollment e : enrollments) {
							for (Student s : students) {
								if (e.getStudentId().equals(s.getStudentId())) {
									jsonArray.add(s.toTeacherJson(true,e.getTakeEffect()));
								}
							}
						}
						json.put("RESULT", "S");
						json.put("ERRMSG", "获取成功");
						json.put("students", jsonArray.toString());
						json.put("size", jsonArray.size());
					}else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "该课程不是您的课程");
					}
				} else if (bodyObj.getString("user").equals("admin")) {
					int courseId = userBody.getInt("courseId");
					List<Student> students = studentService.selectStudentByCourseId(courseId);
					List<Enrollment> enrollments = enrollmentService.getByCourseId(courseId);
					JSONArray jsonArray = new JSONArray();
					for (Enrollment e : enrollments) {
						for (Student s : students) {
							if (e.getStudentId().equals(s.getStudentId())) {
								jsonArray.add(s.toS());
							}
						}
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("students", jsonArray.toString());
					json.put("size", jsonArray.size());
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非教师、非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 搜索学生 -> 教师、管理员
	@PostMapping(value = "/search_student_by_all", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String search_student(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限获取");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId=bodyObj.getString("teacherId");
					String key = userBody.getString("searchKey");
					List<Student> students = studentService.inquireStudentByKey(key);
					JSONArray jsonArray = new JSONArray();
					for (Student s : students) {
						List<Enrollment> enrollments = enrollmentService.getByTeacherIdAndStudentId(teacherId,s.getStudentId());
						for (Enrollment e : enrollments) {
							if (e.getStudentId().equals(s.getStudentId())) {
								jsonArray.add(s.toTeacherJson(true,e.getTakeEffect()));
							}
						}
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.size());
					json.put("students", jsonArray.toString());
				} else if (bodyObj.getString("user").equals("admin")) {
					String key = userBody.getString("searchKey");
					List<Student> students = studentService.inquireStudentByKey(key);
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.add(student.toS());
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.size());
					json.put("students", jsonArray.toString());
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非教师、非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion

	// region 删除老师课程下的学生 -> 教师、管理员
	@PostMapping(value = "/delete_students_fo_teacher_course", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String delete_students_fo_teacher_course(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限");
				} else if (bodyObj.getString("user").equals("teacher")) {
					// region teacher
					String teachetId = bodyObj.getString("teachetId");
					int courseId = userBody.getInt("courseId");
					String studentId = userBody.getString("studentId");
					Course course = courseService.selectByPrimaryKey(courseId);
					Teacher teacher = teacherService.selectTeacherById(teachetId);
					if (teacher != null) {
						if (course != null) {
							Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId, studentId);
							if (enrollment != null) {
								if (teachetId.equals(course.getTeacherId())) {
									if (enrollmentService.deleteByPrimaryKey(enrollment.getId()) == 1) {
										json.put("RESULT", "S");
										json.put("ERRMSG", "成功");
									} else {
										json.put("RESULT", "F");
										json.put("ERRMSG", "课程中移除学生失败");
									}
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "该课程不归属于教师" + teachetId);
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "学生" + studentId + "不存在该课程中");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "不存在该课程id" + courseId);
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该老师id" + teachetId);
					}
					// endregion
				} else if (bodyObj.getString("user").equals("admin")) {
					// region admin
					String teachetId = userBody.getString("teachetId");
					int courseId = userBody.getInt("courseId");
					String studentId = userBody.getString("studentId");
					Course course = courseService.selectByPrimaryKey(courseId);
					Teacher teacher = teacherService.selectTeacherById(teachetId);
					if (teacher != null) {
						if (course != null) {
							Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId, studentId);
							if (enrollment != null) {
								if (teachetId.equals(course.getTeacherId())) {
									if (enrollmentService.deleteByPrimaryKey(enrollment.getId()) == 1) {
										json.put("RESULT", "S");
										json.put("ERRMSG", "成功");
									} else {
										json.put("RESULT", "F");
										json.put("ERRMSG", "课程中移除学生失败");
									}
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "该课程不归属于教师" + teachetId);
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "学生" + studentId + "不存在该课程中");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "不存在该课程id" + courseId);
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该老师id" + teachetId);
					}
					// endregion
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非教师、非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 授权老师 -> 管理员
	@PostMapping(value = "/authorization_teacher", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String authorization_teacher(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("admin")) {
					// region admin
					JSONArray teacherIds = userBody.getJSONArray("teacherIds");
					int valid = userBody.getInt("valid");
					if (valid == 1 || valid == 0 || valid == 2) {
						int s = 0;
						int f = 0;
						int e = 0;
						for (int i = 0; i < teacherIds.size(); i++) {
							Teacher teacher = teacherService.selectTeacherById(teacherIds.getString(i));
							if (teacher != null) {
								teacher.setValid(valid);
								if (teacherService.updateByPrimaryKeySelective(teacher) == 1) {
									s++;
								} else {
									e++;
								}
							} else {
								f++;
							}
						}
						if (s == teacherIds.size()) {
							json.put("RESULT", "S");
							json.put("ERRMSG", "成功：" + s + "  出错：" + e + "  失败：" + f);
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "成功：" + s + "  出错：" + e + "  失败：" + f);
						}

					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "请求非法：valid");
					}

					// endregion
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非管理员");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	// region 添加学生到老师课程下 -> 教师
	@PostMapping(value = "/add_students_fo_teacher_course", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String add_students_fo_teacher_course(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限");
				} else if (bodyObj.getString("user").equals("teacher")) {
					// region teacher
					String teacherId = bodyObj.getString("teacherId");
					int courseId = userBody.getInt("courseId");
					String studentId = userBody.getString("studentId");
					Course course = courseService.selectByPrimaryKey(courseId);
					Teacher teacher = teacherService.selectTeacherById(teacherId);
					if (teacher != null) {
						if (course != null) {
							Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId, studentId);
							if (enrollment != null) {
								json.put("RESULT", "F");
								json.put("ERRMSG", "学生" + studentId + "已添加");
							} else {
								if (teacherId.equals(course.getTeacherId())) {
									Enrollment enrollment1=new Enrollment();
									enrollment1.setTakeEffect(1);
									enrollment1.setCourseId(courseId);
									enrollment1.setStudentId(studentId);
									enrollment1.setTeacherId(teacherId);
									enrollment1.setId(enrollmentService.getIdByMax());
									if (enrollmentService.insert(enrollment) == 1) {
										json.put("RESULT", "S");
										json.put("ERRMSG", "成功");
									} else {
										json.put("RESULT", "F");
										json.put("ERRMSG", "添加学生失败");
									}
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "该课程不归属于教师" + teacherId);
								}

							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "不存在该课程id" + courseId);
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该老师id" + teacherId);
					}
					// endregion
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法：非教师");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	// endregion


	// region 上传图片
		@PostMapping(value = "/postImg", consumes = "application/json", produces = "text/html;charset=UTF-8")
		@ResponseBody // 返回给请求着仅仅body的内容
		public String postImg(@RequestBody String body ) {
			JSONObject json=new JSONObject();
			try {
				Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
						teacherService, adminService);
				if (pair.getKey()) {
					// region 验证成功
					JSONObject bodyObj = pair.getValue();
					JSONObject userBody = bodyObj.getJSONObject("userBody");
					String phone="";
					if (bodyObj.getString("user").equals("student")) {
						String studentId=bodyObj.getString("studentId");
						Student student=studentService.selectStudentById(studentId);
						if (student!=null){
							student.setImghead(student.getIphone()+".jpg");
							studentService.updateByPrimaryKeySelective(student);
							phone=student.getIphone();
						}
					}
					if (bodyObj.getString("user").equals("teacher")) {
						String teacherId=bodyObj.getString("teacherId");
						Teacher teacher=teacherService.selectTeacherById(teacherId);
						if (teacher!=null){
							teacher.setImghead(teacher.getIphone()+".jpg");
							teacherService.updateByPrimaryKeySelective(teacher);
							phone=teacher.getIphone();
						}

					}
					if (bodyObj.getString("user").equals("admin")) {
						String adminId=bodyObj.getString("adminId");
						Admin admin=adminService.selectByPrimaryKey(adminId);
						if (admin!=null){
							admin.setImghead(admin.getIphone()+".jpg");
							adminService.updateByPrimaryKeySelective(admin);
							phone=admin.getIphone();
						}
					}

					if (!phone.equals("")){
						String basePath = request.getSession().getServletContext().getRealPath("/");
						System.out.println(basePath);
						String strpath=basePath+"WEB-INF/resources/userImg/"+ phone+".jpg";
						System.out.println(strpath);
						//如果该文件存在，则删除。
						try {
							new File(servletContext.getRealPath("/userImg/"+phone) + ".jpg").delete();
							System.out.println("删除原图片");
						} catch (Exception e) {
							System.out.println("删除原图片失败");
						}

						UploadImage.convertStringtoImage(userBody.getString("image"), strpath);
						json.put("RESULT", "S");
						json.put("ERRMSG", "上传成功");
					}else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "请求非法");
					}
				}else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求格式非法");
				}
					// endregion
			} catch (Exception e) {
				json.put("RESULT", "F");
				json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
			}
			return WebKeyUtils.encryptionReturnRequest(json.toString());
		}

		// endregion

	// region 修改密码 -> 学生、教师
	@PostMapping(value = "/update_password", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String update_password(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = pair.getValue();
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					// region student
					String password = userBody.getString("password");
					if (!password.equals("")){
						Student student=studentService.selectStudentById(bodyObj.getString("studentId"));
						student.setPassword(Encipher.EncodePasswd(password));
						if (studentService.updateByPrimaryKeySelective(student)==1) {
							json.put("RESULT", "S");
							json.put("ERRMSG", "修改成功");
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "请求非法：valid");
						}
					}else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "请求非法：密码不能为空");
					}
					// endregion
				}else if (bodyObj.getString("user").equals("teacher")) {
					// region teacher
					String password = userBody.getString("password");
					Teacher teacher=teacherService.selectTeacherById(bodyObj.getString("teacherId"));
					teacher.setPassword(Encipher.EncodePasswd(password));
					if (teacherService.updateByPrimaryKeySelective(teacher)==1) {
						json.put("RESULT", "S");
						json.put("ERRMSG", "修改成功");
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "修改失败");
					}

					// endregion
				} else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "请求非法");
				}
				// endregion
			} else {
				return WebKeyUtils.encryptionReturnRequest(pair.getValue().toString());
			}
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}

	// endregion

	//region 手机号判断是否存在学生
	@GetMapping(value = "/exist_student/{phone}", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String exist_student(@PathVariable String phone) {
		JSONObject json = new JSONObject();
		try {
			json.put("RESULT","S");
			json.put("exist",studentService.selectStudentByIphone(phone)!=null);
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	//endregion

	//region 手机号判断是否存在教师
	@GetMapping(value = "/exist_teacher/{phone}", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String exist_teacher(@PathVariable String phone) {
		JSONObject json = new JSONObject();
		try {
			json.put("RESULT","S");
			json.put("exist",teacherService.selectTeacherByIphone(phone)!=null);
		} catch (Exception e) {
			json.put("RESULT", "F");
			json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
		}
		return WebKeyUtils.encryptionReturnRequest(json.toString());
	}
	//endregion

		@RequestMapping( "/startSocketServer" )
		@ResponseBody // 返回给请求着仅仅body的内容
		public String startSocketServer() {
			JSONObject json = new JSONObject();
			try {
				if (!SocketServer.isRuning()) {
					ApiConstants.socketServer=new SocketServer();
					json.put("RESULT", "S");
					json.put("ERRMSG", "开启成功");
				}else {
					json.put("RESULT", "F");
					json.put("ERRMSG", "服务正在运行");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				json.put("THIS_SCOKET_SIZE", SocketServer.getSockets());
				json.put("RESULT", "F");
				json.put("ERRMSG", "开启失败，" + e.toString());
			}

			return WebKeyUtils.encryptionReturnRequest(json.toString());
		}

		@RequestMapping("/stopSocketServer")
		@ResponseBody // 返回给请求着仅仅body的内容
		public String stopSocketServer() {
			JSONObject json = new JSONObject();
			try {
				//if (SocketServer.isRuning()) {
					SocketServer.stopSocketServer();
					json.put("RESULT", "S");
					json.put("ERRMSG", "停止线程成功");
//				}else {
//					json.put("RESULT", "F");
//					json.put("ERRMSG", "服务未运行");
//				}

			} catch (Exception e) {
				json.put("RESULT", "F");
				json.put("ERRMSG", "停止线程失败，" + e.toString());
			}

			return WebKeyUtils.encryptionReturnRequest(json.toString());
		}

		@RequestMapping("/IfSocketServer")
		@ResponseBody // 返回给请求着仅仅body的内容
		public String IfSocketServer() {
			JSONObject json = new JSONObject();
			try {
				json.put("RESULT", "S");
				json.put("ERRMSG", SocketServer.isRuning());
			} catch (Exception e) {
				json.put("RESULT", "F");
				json.put("ERRMSG", "error，" + e.toString());
			}

			return WebKeyUtils.encryptionReturnRequest(json.toString());
		}

}
