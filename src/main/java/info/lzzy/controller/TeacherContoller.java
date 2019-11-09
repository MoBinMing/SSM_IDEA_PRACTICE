package info.lzzy.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.lzzy.models.view.PracticeDao;
import info.lzzy.utils.DateTimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import info.lzzy.utils.BeanCopyUtil;

import info.lzzy.models.Course;
import info.lzzy.models.Option;
import info.lzzy.models.Practice;
import info.lzzy.models.Question;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.models.view.CourseDao;
import info.lzzy.utils.PracticesUtil;

@Controller
@RequestMapping("/Teacher")
public class TeacherContoller extends BaceController {
	// String projPath = System.getProperty("user.dir");
	// 获取到工程项目的根目录。D:\kirin\web\eclipseEE
	/*
	 * @GetMapping("/Index") public String LoginIndex(Map<String, String> map) {
	 * String iphone=(String) request.getSession().getAttribute("Iphone"); String
	 * role=(String) request.getSession().getAttribute("role"); if (iphone!=null &&
	 * role!=null) { if (role.equals("Student")) { Student student=(Student)
	 * request.getSession().getAttribute("Student"); if (student) {
	 * 
	 * }
	 * 
	 * }else if (role.equals("Teacher")) { String teacherId=(String)
	 * request.getSession().getAttribute("teacherId"); if (teacherId!=null) {
	 * List<Practice> pList=practiceService.getPracticeByTeacherId(teacherId);
	 * request.getSession().setAttribute("PracticesSize", pList.size());
	 * map.put("UserIphone", iphone); map.put("myPracticeSize",
	 * " ["+pList.size()+"] "); map.put("thisBody",
	 * PracticesUtil.getPracticesHtml(pList)); }
	 * 
	 * } }
	 * 
	 * return "/Teacher/Index"; }
	 */

//	@ResponseBody
//    @RequestMapping(value="/add",method = RequestMethod.POST)
//    public String insertGoods(@RequestParam("fileName") MultipartFile imageFile[], //同步上床 获取多张图片参数
//                              /*Goods goods,*/
//                              HttpServletRequest request){
//        System.out.println("hello world");
//
//        Goods goods = new Goods();
//        if(imageFile!=null){
//            String imgUrl="";
//            for(int k=0;k<imageFile.length;k++) {
//                 imgUrl += saveImageFile(imageFile[k], request)+",";
//            }
//
//            goods.setImgUrl(imgUrl);
//        }
//        Date date=new Date();
//        goods.setUploadTime(date);
//        GoodsEnum anEnum=goodsService.insertGoods(goods);
//        if(anEnum.equals(GoodsEnum.INSERT_GOODS_SUCCESS)){
//            return JSONUtil.toJSON("success");
//        }else{
//            return JSONUtil.toJSON("error");
//        }
//    }
	
//	private String saveImageFile(MultipartFile imageFile, HttpServletRequest request) {
//        //获取文件上传到服务器的路径
//        String uploadUrl=getRealPath(request)+"static/uploadImg/";
//        System.out.println("文件路径为："+uploadUrl);
//        //获取从客户端传过来的文件名
//        String filename=imageFile.getOriginalFilename();
//        //判断文件上传的路径是否存在，若不存在，则需要创建它
//        File dir=new File(uploadUrl);
//        if(!dir.exists()){
//            dir.mkdirs();
//        }
//        //targetFile最终上传的文件，先判断文件是否存在
//        File targetFile=new File(uploadUrl+filename);
//        if(!targetFile.exists()){
//            //如果文件不存在，我们尝试创建它
//            try {
//                targetFile.createNewFile();
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        //使用MultipartFile的transferTo方法保存文件
//
//        try {
//            imageFile.transferTo(targetFile);
//        }catch (IllegalStateException e){
//            e.printStackTrace();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return "img/"+filename;
//    }

	@GetMapping("/indexUrl")
	public String indexUrl(ModelAndView mv) {
		try {
			String iphone = (String) request.getSession().getAttribute("Iphone");
			String role = (String) request.getSession().getAttribute("role");
			if (iphone != null && role != null) {
				if (role.equals("Student")) {
					Student student = (Student) request.getSession().getAttribute("Student");
					/*
					 * if (student) {
					 * 
					 * }
					 */

				} else if (role.equals("Teacher")) {
					Teacher teacher = (Teacher) request.getSession().getAttribute("Teacher");
					if (teacher != null) {
						List<Course> courses = courseService.selectByTeacherId(teacher.getTeacherId());
						List<Practice> pList = practiceService.getPracticeByCourseId(1);
						request.getSession().setAttribute("PracticesSize", pList.size());
						request.getSession().setAttribute("userName", teacher.getName());
						request.getSession().setAttribute("myCoursesSize", courses.size());
						//mv.setViewName("TeacherIndex");
						// mv.addObject("userName", teacher.getName());
						// mv.addObject("userEmail", teacher.getEmail());
						// mv.addObject("myCoursesSize", courses.size());
//						String portraitPath = "";
//						/* NetworkUtil net=new NetworkUtil(); */
//
//						try{
//							//URL urlStr = new URL(request.getSession().getServletContext().getRealPath("/")+"userImg/"+teacher.getImghead());
//
//							URL urlStr = new URL(request.getSession().getServletContext().getRealPath("/")+"userImg/"+teacher.getImghead());
//
//							HttpURLConnection connection = (HttpURLConnection) urlStr.openConnection();
//							int state = connection.getResponseCode();
//							if (state == 200) {
//								portraitPath = urlStr.getPath();
//							} else {
//								portraitPath= request.getSession().getServletContext().getRealPath("/")+"images/"+teacher.getGender()+".png";
//							}
//						}catch (Exception e) {
//							// TODO: handle exception
//						}
//						request.getSession().setAttribute("portraitPath",portraitPath);
						// mv.addObject("portrait", portraitPath);
						int size = 0;
						for (Practice practice : pList) {
							if (practice.getIsReady() == 1) {
								size++;
							}
						}
						//mv.addObject("dispark", size);
						//mv.addObject("unopen", pList.size() - size);
						List<CourseDao> cDaos = new ArrayList<>();
						for (int j = 0; j < courses.size(); j++) {
							Course course = courses.get(j);
							CourseDao cDao= new CourseDao();
							BeanCopyUtil.beanCopy(course, cDao);
							List<Practice> coursePList = practiceService.getPracticeByCourseId(course.getId());
							cDao.setPracticeSize(coursePList.size());
							cDaos.add(cDao);
						}
						request.getSession().setAttribute("courses", cDaos);
						//mv.addObject("courses", cDaos);
						//mv.addObject("HtmlContent", PracticesUtil.getCoursesHtml(practiceService, courses));
						// 获取练习mv.addObject("HtmlContent",PracticesUtil.getPracticesHtml(request,pList));
					}else {
						return "redirect:/Login/LoginIndexUrl";
					}
				}
			}else {
				return "redirect:/Login/LoginIndexUrl";
			}
		} catch (Exception e) {
//			mv.addObject("Exception", e.getMessage());
//			mv.setViewName("404");
			request.getSession().setAttribute("errorInfo",e.getMessage());
			return "/shared/error";
		}
		return "Teacher/TeacherCourseIndex";
	}
	@PostMapping("/searchCourses")
	@ResponseBody
	public Map<String, Object> searchCourses(String val) {
		Map<String, Object> map = new HashMap<>();
		String teacherId = (String) request.getSession().getAttribute("teacherId");
		if (!teacherId.isEmpty()) {
			List<Course> cList = new ArrayList<>();
			List<CourseDao> cDaos = new ArrayList<CourseDao>();
			if (!val.isEmpty()) {
				cList = courseService.searchThisTeacherCoursesByKey(teacherId, val);
			} else {
				cList = courseService.selectByTeacherId(teacherId);
			}
			for (int j = 0; j < cList.size(); j++) {
				Course course = cList.get(j);
				CourseDao cDao= new CourseDao();
				BeanCopyUtil.beanCopy(course, cDao);
				List<Practice> coursePList = practiceService.getPracticeByCourseId(course.getId());
				cDao.setPracticeSize(coursePList.size());
				cDaos.add(cDao);
			}
			List<Integer> ages=new ArrayList<>();
			for (CourseDao course:cDaos){
				if (!ages.contains(course.getAge())){
					ages.add(course.getAge());
				}
			}
			//request.getSession().setAttribute("courses", cDaos);
			map.put("courses", cDaos);
			map.put("ages", ages);
		} else {
			map.put("body", "登录超时，请重新登录！");
		}
		return map;
	}

	@PostMapping("/addCourses")
	public String addCourses(Course course) {
		Map<String, Object> map = new HashMap<>();
		course.setAddTime(new Date());
		course.setTeacherId((String) request.getSession().getAttribute("teacherId"));
		course.setId(courseService.getIdByMax() + 1);
		try {
			map.put("result",courseService.insert(course));
		}catch (Exception e){
			map.put("result",0);
			map.put("msg",e.getMessage());
		}
		return "redirect:/Teacher/indexUrl";
	}

	@PostMapping("/addPractices")
	public String addPractices(Practice practice) {
		if (practice.getName() != "") {
			String teacherId = (String) request.getSession().getAttribute("teacherId");
			if (!teacherId.isEmpty()) {
				practice.setUpTime(new Date());
				practice.setIsReady(0);
				practice.setQuestionCount(0);
				practice.setId(practiceService.getIdByMax() + 1);
				Course course= (Course) request.getSession().getAttribute("course");
				if (course!=null){
                    practice.setCourseId(course.getId());
                    practiceService.addPractice(practice);
                    return "redirect:/Teacher/getPracticeByCourseId/" + practice.getCourseId();
                }
			}
		}
        return "redirect:/Teacher/indexUrl";
	}

	@GetMapping("/deleteCourse/{id}")
	@ResponseBody
	public Map<String, Object> deleteCourse(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		String teacherId = (String) request.getSession().getAttribute("teacherId");
		if (!teacherId.isEmpty()) {
			Course course = courseService.selectByPrimaryKey(id);
			if (course!=null){
				if (course.getTeacherId().equals(teacherId)){
					try {
						map.put("result",courseService.deleteByPrimaryKey(id));
					}catch (Exception e){
						map.put("result",-1);
						map.put("e",e.getMessage());
					}
				}else {
					map.put("result",2);
					map.put("msg","无权限删除该课程");
				}
			}else {
				map.put("result",3);
				map.put("msg","课程不存在");
			}
		}else {
			map.put("result",4);
			map.put("msg","登录超时");
		}
		return map;

	}

	/*
	 * @GetMapping("deletePractice/{id}") public ModelAndView
	 * deletePractice(ModelAndView mv,@PathVariable int id) { int courseId=(int)
	 * request.getAttribute("thisCourseId");
	 * practiceService.deletePracticeByKey(id); return getPracticeByCourseId(mv,
	 * courseId);
	 * 
	 * }
	 */
	@GetMapping("deletePractice/{id}")
	public String deletePractice(@PathVariable int id) {
	    String teacherId= (String) request.getSession().getAttribute("teacherId");
		Course course = (Course) request.getSession().getAttribute("course");
		if (course.getTeacherId().equals(teacherId)){
            practiceService.deletePracticeByKey(id);
            return "redirect:/Teacher/getPracticeByCourseId/" + course.getId();
        }
        return "redirect:/Login/Exit";
	}

	@GetMapping("/updatePracticeReadyToOn")
	@ResponseBody
	public Map<String, Object> updatePracticeReadyToOn(String pid) {
		Integer id = Integer.valueOf(pid.replace("practice", ""));
		Map<String, Object> map = new HashMap<>();
		String teacherId = (String) request.getSession().getAttribute("teacherId");
		Practice practices = practiceService.getPracticeById(id);
		Course course = (Course) request.getSession().getAttribute("course");
		boolean updateOk = false;
		if (practices.getIsReady() != 1) {
			if (course.getTeacherId().equals(teacherId)) {
				practices.setIsReady(1);
				if (practiceService.updatePracticeSelective(practices) == 1) {
					updateOk = true;
				}
			}
		} else {
			updateOk = true;
		}
		map.put("updateOk", updateOk);
		return map;

	}

	@GetMapping("/updatePracticeReadyToOff")
	@ResponseBody
	public Map<String, Object> updatePracticeReadyToOff(String pid) {
		Integer id = Integer.valueOf(pid.replace("practice", ""));
		Map<String, Object> map = new HashMap<>();
		String teacherId = (String) request.getSession().getAttribute("teacherId");
		Practice practices = practiceService.getPracticeById(id);
		Course course = (Course) request.getSession().getAttribute("course");
		boolean updateOk = false;
		if (practices.getIsReady() != 0) {
			if (course.getTeacherId().equals(teacherId)) {
				practices.setIsReady(0);
				if (practiceService.updatePracticeSelective(practices) == 1) {
					updateOk = true;
				}
			}
		} else {
			updateOk = true;
		}
		map.put("updateOk", updateOk);
		return map;

	}


    @PostMapping("/updatePractice")
    public String updatePractice(Practice practice) {
        Course course = (Course) request.getSession().getAttribute("course");
        if (course!=null){
            try {
                practiceService.updatePracticeSelective(practice);
                return "redirect:/Teacher/getPracticeByCourseId/" + course.getId();
            }catch (Exception e){
                request.getSession().setAttribute("errorInfo",e.getMessage());
                return "shared/error";
            }

        }
        return "redirect:/Login/Exit";
    }

	@PostMapping("/searchPractices")
	@ResponseBody
	public Map<String, Object> searchPractices(String val) {
		Map<String, Object> map = new HashMap<>();
		Course course = (Course) request.getSession().getAttribute("course");
		if (course!=null) {
			String teacherId = (String) request.getSession().getAttribute("teacherId");
			if (!teacherId.isEmpty()) {
				List<Practice> pList;
				if (!val.isEmpty()) {
					pList = practiceService.inquirePracticeByNameByOutlinesByCourseId(course.getId(), val);
				} else {
					pList = practiceService.getPracticeByCourseId(course.getId());
				}
				List<PracticeDao> pDaos = new ArrayList<>();
				for (int j = 0; j < pList.size(); j++) {
					Practice practice = pList.get(j);
					PracticeDao practiceDao= new PracticeDao();
					BeanCopyUtil.beanCopy(practice, practiceDao);
					practiceDao.setStrDate(DateTimeUtils.DATE_TIME_FORMAT.format(practice.getUpTime()));
					pDaos.add(practiceDao);
				}
				map.put("practices", pDaos);
			} else {
				map.put("practices", "登录超时");
			}
		}else {
			map.put("practices", "登录超时");
		}
		return map;
	}

	@GetMapping("/studentMsnagemrnt")
	@ResponseBody
	public Map<String, Object> studentMsnagemrnt() {
		Map<String, Object> map = new HashMap<>();
		return map;
	}

	// 获取所有练习
	@GetMapping("/getPracticeByCourseId/{id}")
	public String getPracticeByCourseId(@PathVariable int id) {
		Course course=courseService.selectByPrimaryKey(id);
		if (course!=null){
			request.getSession().setAttribute("course", course);
			List<Practice> practices = practiceService.getPracticeByCourseId(id);
            List<PracticeDao> pDaos = new ArrayList<>();
            for (int j = 0; j < practices.size(); j++) {
                Practice practice = practices.get(j);
                PracticeDao practiceDao= new PracticeDao();
                BeanCopyUtil.beanCopy(practice, practiceDao);
                practiceDao.setStrDate(DateTimeUtils.DATE_TIME_FORMAT.format(practice.getUpTime()));
                pDaos.add(practiceDao);
            }
			request.getSession().setAttribute("practices",pDaos);
			//mv.addObject("HtmlContent", PracticesUtil.getPracticeByCourseIdHtml(request, questionsService, practices, id));
			String javascriptHtml= "//测试开始\r\n" +
					"function SwitchClick(dom) {\r\n"
					+ "            	var checked = dom.checked;\r\n" + "				var id=dom.id;\r\n"
					+ "            	if(checked) {\r\n"
					+ "					$.get(\"/Practice/Teacher/updatePracticeReadyToOn?pid=\"+id,function(data,status){\r\n"
					+ "						if(data.updateOk){ \r\n" + "							alert(\"开放成功\"); \r\n"
					+ "							location.reload();" + "						}else{\r\n"
					+ "							alert(\"开放失败！\"); \r\n" + "						} \r\n"
					+ "    				});\r\n" + "            	} else {\r\n"
					+ "					$.get(\"/Practice/Teacher/updatePracticeReadyToOff?pid=\"+id,function(data,status){\r\n"
					+ "						if(data.updateOk){ \r\n" + "							alert(\"停止开放成功\"); \r\n"
					+ "							location.reload();" + "						}else{\r\n"
					+ "							alert(\"停止开放失败！\"); \r\n" + "						} \r\n"
					+ "    				});\r\n" + "            	}\r\n" + "        }";
			String cssHtml=".SwitchIcon {\r\n" +
					// " margin: 200px auto;\r\n" +
					"        }\r\n" + "\r\n" + "        .toggle-button {\r\n" + "            display: none;\r\n"
					+ "        }\r\n" + "\r\n" + "        .button-label {\r\n" + "            position: relative;\r\n"
					+ "            display: inline-block;\r\n" + "            width: 80px;\r\n"
					+ "            height: 30px;\r\n" + "            background-color: #ccc;\r\n"
					+ "            box-shadow: #ccc 0px 0px 0px 2px;\r\n" + "            border-radius: 30px;\r\n"
					+ "            overflow: hidden;\r\n" + "        }\r\n" + "\r\n" + "        .circle {\r\n"
					+ "            position: absolute;\r\n" + "            top: 0;\r\n" + "            left: 0;\r\n"
					+ "            width: 30px;\r\n" + "            height: 30px;\r\n"
					+ "            border-radius: 50%;\r\n" + "            background-color: #fff;\r\n" + "        }\r\n"
					+ "\r\n" + "        .button-label .text {\r\n" + "            line-height: 30px;\r\n"
					+ "            font-size: 18px;\r\n" + "            text-shadow: 0 0 2px #ddd;\r\n" + "        }\r\n"
					+ "\r\n" + "        .on {\r\n" + "            color: #fff;\r\n" + "            display: none;\r\n"
					+ "            text-indent: -45px;\r\n" + "        }\r\n" + "\r\n" + "        .off {\r\n"
					+ "            color: #fff;\r\n" + "            display: inline-block;\r\n"
					+ "            text-indent: 34px;\r\n" + "        }\r\n" + "\r\n"
					+ "        .button-label .circle {\r\n" + "            left: 0;\r\n"
					+ "            transition: all 0.3s;\r\n" + "        }\r\n" + "\r\n"
					+ "        .toggle-button:checked + label.button-label .circle {\r\n" + "            left: 50px;\r\n"
					+ "        }\r\n" + "\r\n" + "        .toggle-button:checked + label.button-label .on {\r\n"
					+ "            display: inline-block;\r\n" + "        }\r\n" + "\r\n"
					+ "        .toggle-button:checked + label.button-label .off {\r\n" + "            display: none;\r\n"
					+ "        }\r\n" + "\r\n" + "        .toggle-button:checked + label.button-label {\r\n"
					+ "            background-color: #19e236;\r\n" + "        }\r\n" + "\r\n" + "        .div {\r\n"
					+ "            height: 20px;\r\n" + "            width: 30px;\r\n"
					+ "            background: #51ccee;\r\n" + "        }";
			request.getSession().setAttribute("cssHtml",cssHtml);
			request.getSession().setAttribute("javascriptHtml",javascriptHtml);
			return "Teacher/TeacherPracticeIndex";
		}else {
			return "redirect:/Teacher/indexUrl";
		}
	}
	// region 当前练习管理


	@GetMapping("/toQuestionForPractice/{id}")
	public ModelAndView toQuestionForPractice(ModelAndView mv, @PathVariable int id) {
		mv.setViewName("TeacherIndex");
		request.getSession().setAttribute("thisPracticeId", id);
		List<Question> qList = questionsService.getQuestionByPracticeId(id);
		List<Option> options = new ArrayList<>();
		for (Question questions : qList) {
			options.addAll(optionService.getOptionByQuestionKey(questions.getId()));
		}
		Practice practices = practiceService.getPracticeById(id);
		mv.addObject("HtmlContent", PracticesUtil.getManagingCurrentExercises(practices, qList, options));
		return mv;
	}

	@GetMapping("deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable int id) {
		int practiceId = (int) request.getSession().getAttribute("thisPracticeId");
		questionsService.deleteByPrimaryKey(id);
		return "redirect:/Teacher/managingCurrentExercises/" + practiceId;

	}
//	@GetMapping("/managingCurrentExercises/{id}")
//	public String managingCurrentExercises(Map<String, String> map, @PathVariable int id) {
//		List<Question> qList = questionsService.getQuestionByPracticeId(id);
//		List<Option> options = new ArrayList<>();
//		for (Question questions : qList) {
//			options.addAll(optionService.getOptionByQuestionKey(questions.getId()));
//		}
//		Practice practices = practiceService.getPracticeById(id);
//		map.put("thisBody", PracticesUtil.getManagingCurrentExercises(practices, qList, options));
//		int size = request.getSession().getAttribute("PracticesSize") != null
//				? (int) request.getSession().getAttribute("PracticesSize")
//				: -1;
//		if (size == -1) {
//			return "redirect:/Login/LoginIndexUrl";
//		}
//		map.put("myPractice", "<a href=\"/Practice/Teacher/Index\" class=\"list-group-item\">我的练习<span class=\"badge\">"
//				+ size + "</span></a>");
//		return "/Teacher/managingCurrentExercises";
//	}
	// endregion

	// region 学生管理
	@GetMapping("/getStudentManagementHtml")
	@ResponseBody
	public Map<String, Object> getStudentManagementHtml() {
		Map<String, Object> map = new HashMap<>();
		String teacherId = (String) request.getSession().getAttribute("teacherId");
		/*
		 * List<Enrollment> enrollments=enrollmentService.getByCourseId(teacherId); if
		 * (enrollments.size()>0) {
		 * 
		 * }else { map.put("ok", "ok"); map.put("thisBody", "当前无学生"); }
		 */
		return map;

	}

}
