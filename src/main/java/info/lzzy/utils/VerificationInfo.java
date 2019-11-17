package info.lzzy.utils;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import javafx.util.Pair;
import info.lzzy.connstants.ApiConstants;
import info.lzzy.models.Admin;
import info.lzzy.models.Course;
import info.lzzy.models.Enrollment;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.models.view.UserLogin;
import info.lzzy.service.AdminService;
import info.lzzy.service.CourseService;
import info.lzzy.service.EnrollmentService;
import info.lzzy.service.StudentService;
import info.lzzy.service.TeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class VerificationInfo {

	/**
	 * 
	 * verificationLogin(验证登录)
	 * 
	 * @param userLogin 用户登录信息
	 * @param           @return 设定文件
	 * @return String DOM对象
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static Pair<Boolean, JSONObject> verificationLogin(UserLogin userLogin, StudentService studentService,
															  TeacherService teacherService, CourseService courseService, EnrollmentService enrollmentService, AdminService adminService)
			throws Exception {
		Pair<Boolean, JSONObject> rPair = null;
		JSONObject object = new JSONObject();
		long time = System.currentTimeMillis();
		/*if (time - userLogin.getLoginTime() > ApiConstants.EFFICACY_OF_TIME) {
			object.put("RESULT", "F");
			object.put("ERRMSG", "请求超时");
			rPair = new Pair<Boolean, JSONObject>(false, object);
		} else {*/
			if ("student".equals(userLogin.getUser())) {
				Student student = studentService.selectStudentByIphone(userLogin.getIphone());
				if (student == null) {
					object.put("RESULT", "F");
					object.put("ERRMSG", "账户不存在");
					rPair = new Pair<Boolean, JSONObject>(false, object);
				} else if (!userLogin.getPassword().equals(Encipher.DecodePasswd(student.getPassword()))) {
					object.put("RESULT", "F");
					object.put("ERRMSG", "密码错误");
					rPair = new Pair<Boolean, JSONObject>(false, object);
				} else {
					List<Teacher> teachers = teacherService.selectAllTeacher();
					JSONArray jsonArray = new JSONArray();
					for (Teacher teacher : teachers) {
						if (teacher.getValid()==1) {
							Boolean isMyTeacher = false;
							List<Course> courses = courseService.selectByTeacherId(teacher.getTeacherId());
							for (Course course : courses) {
								Enrollment enrollment = enrollmentService.getByCourseIdAndStudentId(course.getId(),
										student.getStudentId());
								if (enrollment != null) {
									isMyTeacher = true;
								}
							}
							jsonArray.add(teacher.toJSON(isMyTeacher));
						}
					}
					object.put("key", WebKeyUtils.encryption(userLogin.toString()));
					object.put("userRole", userLogin.getUser());
					object.put("student", student.toString());
					object.put("teachers", jsonArray.toString());
					object.put("RESULT", "S");
					object.put("ERRMSG", "登录成功");
					rPair = new Pair<Boolean, JSONObject>(true, object);
				}

			} else if ("teacher".equals(userLogin.getUser())) {
				Teacher teacher = teacherService.selectTeacherByIphone(userLogin.getIphone());
				if (teacher == null) {
					object.put("RESULT", "F");
					object.put("ERRMSG", "账户不存在");
					rPair = new Pair<Boolean, JSONObject>(false, object);
				} else if (!userLogin.getPassword().equals(Encipher.DecodePasswd(teacher.getPassword()))) {
					object.put("RESULT", "F");
					object.put("ERRMSG", "密码错误");
					rPair = new Pair<Boolean, JSONObject>(false, object);
				} else {
					switch (teacher.getValid()) {
					case 0:
						object.put("RESULT", "F");
						object.put("ERRMSG", "未授权");
						rPair = new Pair<Boolean, JSONObject>(false, object);
						break;
					case 1:
						List<Course> courses = courseService.selectByTeacherId(teacher.getTeacherId());
						JSONArray jsonArray = new JSONArray();
						for (Course course : courses) {
							jsonArray.add(course.toString());
						}
						object.put("courses", jsonArray.toString());
						object.put("key", WebKeyUtils.encryption(userLogin.toString()));
						object.put("RESULT", "S");
						object.put("ERRMSG", "登录成功");
						object.put("userRole", userLogin.getUser());
						object.put("teacher", teacher.toJSON(false));
						rPair = new Pair<Boolean, JSONObject>(true, object);
						break;
					case 2:
						object.put("RESULT", "F");
						object.put("ERRMSG", "管理员拒绝您的使用");
						rPair = new Pair<Boolean, JSONObject>(false, object);
						break;
					default:
						break;
					}
				}
			} else if("admin".equals(userLogin.getUser())){
				Admin admin=adminService.selectAdminByIphone(userLogin.getIphone());
				String userPwa=Encipher.DecodePasswd(admin.getPassword());
				if (admin == null) {
					object.put("RESULT", "F");
					object.put("ERRMSG", "账户不存在");
					rPair = new Pair<Boolean, JSONObject>(false, object);
				} else if (!userLogin.getPassword().equals(userPwa)) {
					object.put("RESULT", "F");
					object.put("ERRMSG", "密码错误");
					rPair = new Pair<Boolean, JSONObject>(false, object);
				} else {
					object.put("key", WebKeyUtils.encryption(userLogin.toString()));
					object.put("RESULT", "S");
					object.put("ERRMSG", "登录成功");
					object.put("userRole", userLogin.getUser());
					object.put("admin", admin.toJSON());
					rPair = new Pair<Boolean, JSONObject>(true, object);
				}
				
			}else {
				object.put("RESULT", "F");
				object.put("ERRMSG", "非法请求，请查看接口文档");
				rPair = new Pair<Boolean, JSONObject>(false, object);
			}/*
		}*/
		return rPair;
	}

	public static Pair<Boolean, JSONObject> verificationRegister(ServletContext servletContext,HttpServletRequest request,String body, StudentService studentService,
			TeacherService teacherService) throws Exception {
		JSONObject jsonObject = new JSONObject();
		Pair<Boolean, JSONObject> rPair = null;
		try {
			// 获取请求内容
			String imghead;
			Pair<String, Long> contentPair = WebKeyUtils.convertBodyReqest(body);
			Long userRequest = contentPair.getValue();
			Gson gson = new Gson();
			long time = System.currentTimeMillis();
			if (time - userRequest > ApiConstants.EFFICACY_OF_TIME) {
				jsonObject.put("RESULT", "F");
				jsonObject.put("ERRMSG", "请求超时");
				rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
			} else {
				JSONObject userBody = JSONObject.fromObject(contentPair.getKey());
				if (userBody.getString("role").equals("student")) {
					// region 注册学生
					Student student = gson.fromJson(userBody.toString(), Student.class);
					imghead=student.getImghead();
					student.setImghead(student.getIphone()+".png");

					if (ApiUtil.allFieldIsNULL(student)) {
						jsonObject.put("RESULT", "F");
						jsonObject.put("ERRMSG", "参数不合法 存在参数为空");
						rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
					} else {
						boolean g = false;
						if ("男".equals(student.getGender())) {
							g = true;
						} else if ("女".equals(student.getGender())) {
							g = true;
						}
						if (g) {
							if (student.getEmail() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Email==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (student.getEmail().length() > 20) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "email格式不正缺");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (studentService.selectStudentByEmail(student.getEmail()) != null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "email：" + student.getEmail() + "已注册");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (student.getPassword() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Password==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (student.getStudentId() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "studentId==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (studentService.selectStudentById(student.getStudentId()) != null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "studentId：" + student.getStudentId() + "已注册");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (student.getName() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Name==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (student.getIphone() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Iphone==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (!PhoneUtils.isValidChinesePhone(student.getIphone())) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "手机号不合法");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (student.getGender() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Gender==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else {
								boolean postImg=postImage(servletContext, request,student.getIphone(),student.getImghead());
								//endregion
								student.setPassword(Encipher.EncodePasswd(student.getPassword()));
								if (studentService.insert(student) == 1) {
//									String basePath = request.getSession().getServletContext().getRealPath("/");
//									System.out.println(basePath);
//									String strpath=basePath+"WEB-INF\\resources\\userImg\\"+ student.getIphone()+".jpg";
//									System.out.println(strpath);
//									//如果该文件存在，则删除。
//									try {
//										new File(servletContext.getRealPath("/userImg/"+student.getIphone()) + ".jpg").delete();
//										System.out.println("删除原图片");
//									} catch (Exception e) {
//										System.out.println("删除原图片失败");
//									}
//
//									UploadImage.convertStringtoImage(imghead, basePath+"WEB-INF\\resources\\userImg\\"+ student.getIphone()+".jpg");
									jsonObject.put("RESULT", "S");
									jsonObject.put("POST_IMG", postImg);
									jsonObject.put("ERRMSG", "注册成功");
									rPair = new Pair<Boolean, JSONObject>(true, jsonObject);
								} else {
									jsonObject.put("RESULT", "F");
									jsonObject.put("ERRMSG", "尝试注册失败");
									rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
								}
							}
						} else {
							jsonObject.put("RESULT", "F");
							jsonObject.put("ERRMSG", "gender只能为男或女");
							rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
						}
					}
					// endregion
				} else if (userBody.getString("role").equals("teacher")) {
					// region 注册教师
					Teacher teacher = gson.fromJson(userBody.toString(), Teacher.class);
					imghead=teacher.getImghead();
					teacher.setImghead(teacher.getIphone()+".png");
					teacher.setValid(0);
					if (ApiUtil.allFieldIsNULL(teacher)) {
						jsonObject.put("RESULT", "F");
						jsonObject.put("ERRMSG", "参数不合法 存在参数为空");
					} else {
						boolean g = false;
						if ("男".equals(teacher.getGender())) {
							g = true;
						} else if ("女".equals(teacher.getGender())) {
							g = true;
						}
						if (g) {
							if (teacher.getEmail() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Email==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacher.getEmail().length() > 20) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "email格式不正缺");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (!ApiUtil.isEmail(teacher.getEmail())) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "email格式不正缺");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacherService.selectTeacherByEmail(teacher.getEmail()) != null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "email：" + teacher.getEmail() + "已注册");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacher.getPassword() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Password==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacher.getTeacherId() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "TeacherId==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacherService.selectTeacherById(teacher.getTeacherId()) != null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "teacherId：" + teacher.getTeacherId() + "已注册");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacher.getName() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Name==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacher.getIphone() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Iphone==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (!PhoneUtils.isValidChinesePhone(teacher.getIphone())) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "手机号不合法");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else if (teacher.getGender() == null) {
								jsonObject.put("RESULT", "F");
								jsonObject.put("ERRMSG", "Gender==null");
								rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
							} else {
								teacher.setPassword(Encipher.EncodePasswd(teacher.getPassword()));
								if (teacherService.insert(teacher) == 1) {
									String basePath = request.getSession().getServletContext().getRealPath("/");
									System.out.println(basePath);
									String strpath=basePath+"WEB-INF\\resources\\userImg\\"+ teacher.getIphone()+".jpg";
									System.out.println(strpath);
									//如果该文件存在，则删除。
									try {
										new File(servletContext.getRealPath("/userImg/"+teacher.getIphone()) + ".jpg").delete();
										System.out.println("删除原图片");
									} catch (Exception e) {
										System.out.println("删除原图片失败");
									}
									UploadImage.convertStringtoImage(imghead, basePath+"WEB-INF\\resources\\userImg\\"+ teacher.getIphone()+".jpg");
									jsonObject.put("RESULT", "S");
									jsonObject.put("ERRMSG", "注册成功");
									rPair = new Pair<Boolean, JSONObject>(true, jsonObject);
								}

							}
						} else {
							jsonObject.put("RESULT", "F");
							jsonObject.put("ERRMSG", "gender只能为男或女");
							rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
						}
					}
					// endregion
				}
			}
		} catch (Exception e) {
			jsonObject.put("RESULT", "F");
			jsonObject.put("ERRMSG", "非法请求，请查看接口文档");
			rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
		}

		return rPair;
	}

	private static boolean postImage(ServletContext servletContext, HttpServletRequest request,String iphioe,String image) {
		//region 上传图片
		try {
			String basePath = request.getSession().getServletContext().getRealPath("/");
			System.out.println(basePath);
			String strpath=basePath+"WEB-INF/resources/userImg/"+ iphioe+".jpg";
			System.out.println(strpath);
			//如果该文件存在，则删除。
			try {
				new File(servletContext.getRealPath("/userImg/"+iphioe) + ".jpg").delete();
				System.out.println("删除原图片");
			} catch (Exception e) {
				System.out.println("删除原图片失败");
			}

			UploadImage.convertStringtoImage(image, strpath);
			return true;
			// endregion
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * verificationUserRequest(二次请求验证)
	 * 
	 * @param studentService
	 * @param      @return 设定文件
	 * @return String DOM对象
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static Pair<Boolean, JSONObject> verificationUserRequest(String body, StudentService studentService,
			TeacherService teacherService,AdminService adminService) throws Exception {
		// 获取请求内容
		/*Pair<String, Long> contentPair = WebKeyUtils.convertBodyReqest(body);
		Long userRequest = contentPair.getValue();*/
		Gson gson = new Gson();
		// 解密key
		JSONObject userObj = JSONObject.fromObject(body);
		Pair<String, Long> pair = WebKeyUtils.decode(userObj.getString("key"));
		UserLogin userLogin = gson.fromJson(pair.getKey(), UserLogin.class);
		Pair<Boolean, JSONObject> rPair;
		JSONObject jsonObject = new JSONObject();
		/*long time = System.currentTimeMillis();
		if (time - userRequest > ApiConstants.EFFICACY_OF_TIME) {
			jsonObject.put("RESULT", "F");
			jsonObject.put("ERRMSG", "请求超时");
			rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
		} else if (time - pair.getValue() > ApiConstants.DIALOGUE_TIME) {
			jsonObject.put("RESULT", "F");
			jsonObject.put("ERRMSG", "登录超时");
			rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
		} else {*/
			if ("student".equals(userLogin.getUser())) {
				Student student = studentService.selectStudentByIphone(userLogin.getIphone());
				if (student == null) {
					jsonObject.put("RESULT", "F");
					jsonObject.put("ERRMSG", "账户不存在");
					rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
				} else if (!userLogin.getPassword().equals(Encipher.DecodePasswd(student.getPassword()))) {
					jsonObject.put("RESULT", "F");
					jsonObject.put("ERRMSG", "密码错误");
					rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
				} else {
					JSONObject object = new JSONObject();
					object.put("userBody", userObj);
					object.put("user", "student");
					object.put("studentId", student.getStudentId());
					rPair = new Pair<Boolean, JSONObject>(true, object);
				}

			} else if ("teacher".equals(userLogin.getUser())) {
				Teacher teacher = teacherService.selectTeacherByIphone(userLogin.getIphone());
				if (teacher == null) {
					jsonObject.put("RESULT", "F");
					jsonObject.put("ERRMSG", "账户不存在");
					rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
				} else if (!userLogin.getPassword().equals(Encipher.DecodePasswd(teacher.getPassword()))) {
					jsonObject.put("RESULT", "F");
					jsonObject.put("ERRMSG", "密码错误");
					rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
				} else {
					jsonObject.put("userBody", userObj);
					jsonObject.put("user", "teacher");
					jsonObject.put("teacherId", teacher.getTeacherId());
					rPair = new Pair<Boolean, JSONObject>(true, jsonObject);
				}
			}else if ("admin".equals(userLogin.getUser())) {
				Admin admin=adminService.selectAdminByIphone(userLogin.getIphone());
				if (admin == null) {
					jsonObject.put("RESULT", "F");
					jsonObject.put("ERRMSG", "账户不存在");
					rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
				} else if (!userLogin.getPassword().equals(Encipher.DecodePasswd(admin.getPassword()))) {
					jsonObject.put("RESULT", "F");
					jsonObject.put("ERRMSG", "密码错误");
					rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
				} else {
					jsonObject.put("userBody", userObj);
					jsonObject.put("user", "admin");
					jsonObject.put("adminId", admin.getAdminId());
					rPair = new Pair<Boolean, JSONObject>(true, jsonObject);
				}
			}else {
				jsonObject.put("RESULT", "F");
				jsonObject.put("ERRMSG", "非法请求，请查看接口文档");
				rPair = new Pair<Boolean, JSONObject>(false, jsonObject);
			}
		/*}*/
		return rPair;
	}
}
