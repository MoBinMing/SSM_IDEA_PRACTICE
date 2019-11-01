package info.lzzy.controller;

import java.io.File;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.ibatis.binding.BindingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javafx.util.Pair;
import info.lzzy.connstants.ApiConstants;
import info.lzzy.models.Course;
import info.lzzy.models.Enrollment;
import info.lzzy.models.Option;
import info.lzzy.models.Practice;
import info.lzzy.models.PracticeResult;
import info.lzzy.models.Question;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.models.view.TeacherValidType;
import info.lzzy.models.view.UserLogin;
import info.lzzy.service.StudentService;
import info.lzzy.service.scoket.SocketServer;
import info.lzzy.utils.AESUtils;
import info.lzzy.utils.ApiUtil;
import info.lzzy.utils.Encipher;
import info.lzzy.utils.PhoneUtils;
import info.lzzy.utils.SymmetricEncoder;
import info.lzzy.utils.UploadImage;
import info.lzzy.utils.VerificationInfo;
import info.lzzy.utils.WebKeyUtils;

@Controller
@RequestMapping("/api") // 映射路径，一个参数时等同于@RequestMapping(value = "/api")， 如果 value=""，将映射项目根路径
public class ApiController<AppVersionDo> extends BaceController {
	public static String ENCODERULES_KEY = "abc123";

	// region 注册 OK
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

	// region 登录 OK
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

	// region 获取所有老师 Ok
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
							jsonArray.put(teacher.toJSON(isMyTeacher));
						}
					}
					json.put("size", jsonArray.length());
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
								jsonArray.put(teacher.toJSON(true));
							} else {
								jsonArray.put(teacher.toJSON(false));
							}
						}
					}
					json.put("size", jsonArray.length());
					json.put("teachers", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("admin")) {
					List<Teacher> teachers = teacherService.selectAllTeacher();
					JSONArray jsonArray = new JSONArray();
					for (Teacher teacher : teachers) {
						jsonArray.put(teacher.toTeacherJSON());
					}
					json.put("size", jsonArray.length());
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
	// region 获取老师的课程 Ok
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
							jsonArray.put(course.toJSON(enrollment.getTakeEffect()));
						} else {
							jsonArray.put(course.toJSON(-1));
						}

					}
					json.put("size", jsonArray.length());
					json.put("courses", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("teacher")) {
					String teacherId = bodyObj.getString("teacherId");
					List<Course> courses = courseService.selectByTeacherId(teacherId);
					JSONArray jsonArray = new JSONArray();
					for (Course course : courses) {
						jsonArray.put(course.toJSON(true));
					}
					json.put("size", jsonArray.length());
					json.put("courses", jsonArray.toString());
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
				} else if (bodyObj.getString("user").equals("admin")) {
					String teacherId = bodyObj.getString("teacherId");
					List<Course> courses = courseService.selectByTeacherId(teacherId);
					JSONArray jsonArray = new JSONArray();
					for (Course course : courses) {
						jsonArray.put(course.toJSON(true));
					}
					json.put("size", jsonArray.length());
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
	// region 获取课程下的Practice OK
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
									jObject.put("id", practice.getId());
									jObject.put("name", practice.getName());
									jObject.put("questionCount", practice.getQuestionCount());
									jObject.put("upTime", format.format(practice.getUpTime()));
									jObject.put("isReady", practice.getIsReady());
									jObject.put("outlines", practice.getOutlines());
									jObject.put("courseId", practice.getCourseId());
									jsonArray.put(jObject);
								}
							}
							json = new JSONObject(RETURN_S_String);
							json.put("practices", jsonArray.toString());
							json.put("size", jsonArray.length());
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
									jsonArray.put(jObject);
								}
							}
							json = new JSONObject(RETURN_S_String);
							json.put("practices", jsonArray.toString());
							json.put("size", jsonArray.length());
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
	// region 获取Practice下的Question
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
							json = getQuestions(json, practiceId);
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
							json = getQuestions(json, practiceId);
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

	public JSONObject getQuestions(JSONObject json, Integer practiceId) {
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
							optionjObject.put("content", option.getId());
							optionjObject.put("label", option.getLabel());
							optionjObject.put("questionId", option.getQuestionId());
							optionjObject.put("isAnswer", option.getIsAnswer());
							optionArray.put(optionjObject);
						}
						questionjObject.put("options", optionArray.toString());
						questionArray.put(questionjObject);
					}
				}
				json = new JSONObject(RETURN_S_String);
				json.put("questions", questionArray.toString());
				json.put("size", questionArray.length());
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
	// region 上传 PracticeResult 练习结果 OK
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
				JSONObject userBody = new JSONObject(bodyObj.getString("userBody"));
				Gson gson = new Gson();
				JSONArray jsonArray = userBody.getJSONArray("Results");
				List<PracticeResult> practiceResults = new ArrayList<>();

				for (Object object : jsonArray) {
					PracticeResult practiceResult = gson.fromJson(object.toString(), PracticeResult.class);
					int maxId = 0;
					try {
						maxId = practiceResultService.getIdByMax();
					} catch (BindingException e) {

					}
					practiceResult.setId(maxId + 1);
					practiceResult.setStudentId(bodyObj.getString("studentId"));
					if (practiceResultService.selectBySIdAndPIdAndQId(practiceResult.getPracticeId(),
							practiceResult.getStudentId(), practiceResult.getQuestionId()).size() == 0) {
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
						json.put("ERRMSG", "存在重复结果");
						break;
					}

				}
				if (practiceResults.size() == jsonArray.length()) {
					int okSize = 0;
					for (PracticeResult practiceResult : practiceResults) {
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

	// region 添加课程 OK
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
							List<Course> courses = gson.fromJson(userBody.getString("courses"),
									new TypeToken<List<Course>>() {
									}.getType());
							int s = 0;
							int f = 0;
							int t = 0;
							StringBuilder fBuilder = new StringBuilder();
							StringBuilder tBuilder = new StringBuilder();
							for (Course course : courses) {
								course.setTeacherId(teacherId);
								course.setAddTime(new Date());
								if (courseService.selectByName(course.getName()) == null) {
									if (courseService.insert(course) == 1) {
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
							if (s == courses.size()) {
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
	// region 删除课程 Ok
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
	// region 更新课程 OK
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
						if (course.getName() != null && course.getIntro() != null) {
							course1.setName(course.getName());
							course1.setIntro(course.getIntro());
							if (courseService.updateByPrimaryKey(course1) == 1) {
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

	// region 添加章节
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
					json.put("ERRMSG", "无权限添加章节");
				} else {
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
	// region 删除章节
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
					json.put("ERRMSG", "无权限删除章节");
				} else {
					Integer practiceId = userBody.getInt("practiceId");
					Practice practice = practiceService.getPracticeById(practiceId);
					Course course = courseService.selectByPrimaryKey(practice.getCourseId());
					if (course != null) {
						if (bodyObj.getString("teacherId").equals(course.getTeacherId())) {
							if (practice == null) {
								if (practiceService.deletePracticeByKey(practice.getId()) == 1) {
									json.put("RESULT", "S");
									json.put("ERRMSG", "删除成功");
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "删除时失败");
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "不存在该章节");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该课程的章节");
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
	// region 更新章节
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
					json.put("ERRMSG", "无权限更新章节");
				} else {
					Integer practiceId = userBody.getInt("practiceId");
					Practice practice = practiceService.getPracticeById(practiceId);
					Course course = courseService.selectByPrimaryKey(practice.getCourseId());
					if (course != null) {
						if (bodyObj.getString("teacherId").equals(course.getTeacherId())) {
							if (practice == null) {
								Gson gson = new Gson();
								String practiceJson = userBody.getString("practice");
								Practice practice2 = gson.fromJson(practiceJson, Practice.class);
								practice.setIsReady(practice2.getIsReady());
								practice.setName(practice2.getName());
								practice.setOutlines(practice2.getOutlines());
								if (practiceService.updatePracticeSelective(practice) == 1) {
									json.put("RESULT", "S");
									json.put("ERRMSG", "更新成功");
								} else {
									json.put("RESULT", "F");
									json.put("ERRMSG", "更新时失败");
								}
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "不存在该章节");
							}
						} else {
							json.put("RESULT", "F");
							json.put("ERRMSG", "该课程不是您的课程");
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "不存在该课程的章节");
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

	// region 添加题目
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
				Integer practiceId = userBody.getInt("practiceId");
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
							question.setId(questionsService.getIdByMax());
							question.setNumber(number);
							question.setPracticeId(practiceId);
							question.setQuestionType(question_type);
							if (questionsService.insert(question) == 1) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "添加成功");
							} else {
								json.put("RESULT", "F");
								json.put("ERRMSG", "添加时失败");
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
	// region 更新题目
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
	// region 删除题目
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
							if (questionsService.deleteByPrimaryKey(questionId) == 1) {
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

	// region 添加题目选项
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
	// region 删除题目选项
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

	// region 更新题目选项
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

	// region 1：学生申请课程；2、教师：授权、取消授权学生申请的课程、3、 OK
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
							if (enrollmentService.insertSelective(enrollment) == 1) {
								json.put("RESULT", "S");
								json.put("ERRMSG", "已提交申请，等待老师同意");
							} else {
								String name = Thread.currentThread().getStackTrace()[1].getFileName();// 获取当前文件名；
								int line = Thread.currentThread().getStackTrace()[1].getLineNumber() - 5;// 获取当前行号。
								json.put("RESULT", "F");
								json.put("ERRMSG", "申请失败\nc错误位置：" + name + "\n错误所在行：" + line);
							}
						} else {
							switch (enrollment.getTakeEffect()) {
							case 0:
								json.put("RESULT", "F");
								json.put("ERRMSG", "该课程已经申请,等待老师同意");
								break;
							case 1:
								json.put("RESULT", "F");
								json.put("ERRMSG", "该课程已经申请通过");
								break;
							case 2:
								json.put("RESULT", "F");
								json.put("ERRMSG", "老师拒绝你对课程”" + course.getName() + "“的申请");
								break;
							/*
							 * case -1: enrollment.setTakeEffect(0); if (condition) {
							 *
							 * } enrollmentService.updateByPrimaryKey(enrollment)
							 */
							default:
								break;
							}
						}
					} else {
						json.put("RESULT", "F");
						json.put("ERRMSG", "没有该课程");
					}
				} else if (takeEffect == 1 || takeEffect == 0 || takeEffect == -1) {
					if (bodyObj.getString("user").equals("teacher")) {
						// region teacher
						Integer courseId = userBody.getInt("courseId");
						String teacherId = bodyObj.getString("teacherId");
						JSONArray studentIds = userBody.getJSONArray("studentIds");
						// String[] studentIdss = (String[]) studentIds.toArray();
						Course course = courseService.selectByPrimaryKey(courseId);
						if (course != null) {
							if (teacherId.equals(course.getTeacherId())) {

								for (int i = 0; i < studentIds.length()-1; i++) {
									Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(courseId,
											studentIds.getString(i));
									if (enrollment == null) {
										enrollment = new Enrollment();
										enrollment.setCourseId(courseId);
										enrollment.setStudentId(studentIds.getString(i));
										enrollment.setTakeEffect(takeEffect);
										if (enrollmentService.insertSelective(enrollment) == 1) {
											ssArray.put(studentIds.getString(i));
										} else {
											eeArray.put(studentIds.getString(i));
										}
									} else {
										if (takeEffect == -1) {
											if (enrollmentService.deleteByPrimaryKey(enrollment.getId()) == 1) {
												ssArray.put(studentIds.getString(i));
											} else {
												eeArray.put(studentIds.getString(i));
											}
										} else {
											switch (enrollment.getTakeEffect()) {
											case 0:
												enrollment.setTakeEffect(takeEffect);
												if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {

													ssArray.put(studentIds.getString(i));
												} else {
													eeArray.put(studentIds.getString(i));
												}
												break;
											case 1:
												enrollment.setTakeEffect(takeEffect);
												if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {

													ssArray.put(studentIds.getString(i));
												} else {
													eeArray.put(studentIds.getString(i));
												}
												break;
											case 2:
												enrollment.setTakeEffect(takeEffect);
												if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {

													ssArray.put(studentIds.getString(i));
												} else {
													eeArray.put(studentIds.getString(i));
												}
												break;
											default:
												break;
											}
										}

									}
								}
								if (ssArray.length() == studentIds.length()) {
									json.put("RESULT", "S");
									json.put("ERRMSG", "成功");
									json.put("S", ssArray.toString());
									json.put("E", eeArray.toString());
								} else {
									json.put("RESULT", "E");
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
							for (int i = 0; i < studentIds.length()-1; i++) {
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
										ssArray.put(object);
									} else {
										JSONObject object = new JSONObject();
										object.put(studentIds.getString(i), "报错");
										eeArray.put(object);
									}
								} else {
									JSONObject object = new JSONObject();
									switch (enrollment.getTakeEffect()) {
									case 0:
										enrollment.setTakeEffect(takeEffect);
										if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {
											object.put(studentIds.getString(i), "成功");
											ssArray.put(object);
										} else {
											object.put(studentIds.getString(i), "报错");
											eeArray.put(object);
										}
										break;
									case 1:
										enrollment.setTakeEffect(takeEffect);
										if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {
											object.put(studentIds.getString(i), "成功");
											ssArray.put(object);
										} else {
											object.put(studentIds.getString(i), "报错");
											eeArray.put(object);
										}
										break;
									case 2:
										enrollment.setTakeEffect(takeEffect);
										if (enrollmentService.updateByPrimaryKey(enrollment) == 1) {
											object.put(studentIds.getString(i), "成功");
											ssArray.put(object);
										} else {
											object.put(studentIds.getString(i), "报错");
											eeArray.put(object);
										}
										break;
									default:
										break;
									}
								}
							}
							if (ssArray.length() == studentIds.length()) {
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

	//region 获取所有学生 OK
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
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限获取");
				} else if (bodyObj.getString("user").equals("teacher")) {

					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.put(student.toString());
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.length());
					json.put("students", jsonArray.toString());
				} else if (bodyObj.getString("user").equals("admin")) {
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.put(student.toString());
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.length());
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
	//region 获取所有学生 （如果是老师id、课程id下的学生， student模型外多 isMyStudent ）OK
	@PostMapping(value = "/getAllStudentAndIsTeacherCoursesStudent", consumes = "application/json", produces = "text/html;charset=UTF-8")
	@ResponseBody // 返回给请求着仅仅body的内容
	public String getAllStudentAndIsTeacherCoursesStudent(@RequestBody String body) {
		JSONObject json = new JSONObject();
		try {
			Pair<Boolean, JSONObject> pair = VerificationInfo.verificationUserRequest(body, studentService,
					teacherService, adminService);
			if (pair.getKey()) {
				// region 验证成功
				JSONObject bodyObj = new JSONObject(pair.getValue());
				JSONObject userBody = bodyObj.getJSONObject("userBody");
				if (bodyObj.getString("user").equals("student")) {
					json.put("RESULT", "F");
					json.put("ERRMSG", "学生无权限获取");
				} else if (bodyObj.getString("user").equals("teacher")) {
					int courseId = userBody.getInt("courseId");
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						boolean isMyStudent = false;
						if (enrollmentService.getByCourseIdAndStudentId(courseId, student.getStudentId()) != null) {
							isMyStudent = true;
						}
						jsonArray.put(student.toTeacherJson(isMyStudent));
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("students", jsonArray.toString());
				} else if (bodyObj.getString("user").equals("admin")) {
					List<Student> students = studentService.selectAll();
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.put(student.toString());
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
	// region 获取课程下的学生 OK
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
					int courseId = userBody.getInt("courseId");
					List<Student> students = studentService.selectStudentByCourseId(courseId);
					List<Enrollment> enrollments = enrollmentService.getByCourseId(courseId);
					JSONArray jsonArray = new JSONArray();
					for (Enrollment e : enrollments) {
						for (Student s : students) {
							if (e.getStudentId().equals(s.getStudentId())) {
								jsonArray.put(s.toStudentJson(e.getTakeEffect()));
							}
						}

					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("students", jsonArray.toString());
					json.put("size", jsonArray.length());
				} else if (bodyObj.getString("user").equals("admin")) {
					int courseId = userBody.getInt("courseId");
					List<Student> students = studentService.selectStudentByCourseId(courseId);
					JSONArray jsonArray = new JSONArray();
					for (Student s : students) {
						jsonArray.put(s.toString());
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

	// region 搜索学生（全库） OK
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
					String key = userBody.getString("searchKey");
					List<Student> students = studentService.inquireStudentByKey(key);
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.put(student.toString());
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.length());
					json.put("students", jsonArray.toString());
				} else if (bodyObj.getString("user").equals("admin")) {
					String key = userBody.getString("searchKey");
					List<Student> students = studentService.inquireStudentByKey(key);
					JSONArray jsonArray = new JSONArray();
					for (Student student : students) {
						jsonArray.put(student.toString());
					}
					json.put("RESULT", "S");
					json.put("ERRMSG", "获取成功");
					json.put("size", jsonArray.length());
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

	// region 删除老师课程下的学生 off
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
	// region 授权老师 OK
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
						for (int i = 0; i < teacherIds.length()-1; i++) {
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
						if (s == teacherIds.length()) {
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
// region 添加学生到老师课程下 off
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
									if (enrollmentService.insert(enrollment) == 1) {
										json.put("RESULT", "S");
										json.put("ERRMSG", "成功");
									} else {
										json.put("RESULT", "F");
										json.put("ERRMSG", "课程中添加学生失败");
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
									if (enrollmentService.insert(enrollment) == 1) {
										json.put("RESULT", "S");
										json.put("ERRMSG", "成功");
									} else {
										json.put("RESULT", "F");
										json.put("ERRMSG", "课程中添加学生失败");
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


	// region 上传图片
		@PostMapping(value = "/postImg", consumes = "application/json", produces = "text/html;charset=UTF-8")
		@ResponseBody // 返回给请求着仅仅body的内容
		public String postImg(@RequestBody String body,AppVersionDo record ) {
			JSONObject json = new JSONObject(body);
			try {
						String teacherIphone = json.getString("teacherIphone");
						//Teacher teacher = teacherService.selectTeacherById(teacherId);
						//if (teacher != null) {

						String basePath = request.getSession().getServletContext().getRealPath("/");
						System.out.println(basePath);
						String strpath=basePath+"WEB-INF\\resources\\userImg\\"+ teacherIphone+".jpg";
						System.out.println(strpath);
						//如果该文件存在，则删除。
						try {
							new File(servletContext.getRealPath("/userImg/"+teacherIphone) + ".jpg").delete();
							System.out.println("删除原图片");
						} catch (Exception e) {
							System.out.println("删除原图片失败");
						}

						UploadImage.convertStringtoImage(json.getString("image"), basePath+"WEB-INF\\resources\\userImg\\"+ teacherIphone+".jpg");
						json=new JSONObject();
							json.put("RESULT", "S");
							json.put("ERRMSG", "上传成功");


					// endregion
			} catch (Exception e) {
				json=new JSONObject();
				json.put("RESULT", "F");
				json.put("ERRMSG", "请求格式非法，\n" + e.getMessage() + "");
			}
			return WebKeyUtils.encryptionReturnRequest(json.toString());
		}

		// endregion
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
