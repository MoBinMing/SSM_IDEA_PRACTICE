package info.lzzy.controller;

import java.util.*;

import info.lzzy.models.view.PracticeDao;
import info.lzzy.models.view.QuestionDao;
import info.lzzy.utils.DateTimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import info.lzzy.utils.BeanCopyUtil;

import info.lzzy.models.Course;
import info.lzzy.models.Practice;
import info.lzzy.models.Question;
import info.lzzy.models.Teacher;
import info.lzzy.models.view.CourseDao;

@Controller
@RequestMapping("/Teacher")
public class TeacherController extends BaceController {
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

	//region 1、成员方法
	private Teacher getSessionTeacher(){
		return (Teacher) request.getSession().getAttribute("user");
	}

	private String toCourseIndex() {
		return "Teacher/TeacherCourseIndex";
	}

	private String toLoginUrl() {
		return "redirect:/Login/LoginIndexUrl";
	}

	private Course getSessionCourse() {
		return (Course) request.getSession().getAttribute("course");
	}

	private String toError(Exception e) {
		Enumeration em = request.getSession().getAttributeNames();
		while (em.hasMoreElements()) {
			request.getSession().removeAttribute(em.nextElement().toString());
		}
		request.getSession().setAttribute("errorInfo",e.getMessage());
		return "/shared/error";
	}

	private void listForList(List<Course> courseList, List<CourseDao> courseDaoList) {
		for (Course course : courseList) {
			CourseDao cDao = new CourseDao();
			BeanCopyUtil.beanCopy(course, cDao);
			List<Practice> coursePList = practiceService.getPracticeByCourseId(course.getId());
			cDao.setPracticeSize(coursePList.size());
			courseDaoList.add(cDao);
		}
	}

	private String toExit() {
		return "redirect:/Login/Exit";
	}

	private String toCourseIndexUrl() {
		return "redirect:/Teacher/indexUrl";
	}

	//endregion

	@GetMapping("/indexUrl")
	public String indexUrl() {
		try {
			Teacher teacher = getSessionTeacher();
			if (teacher != null) {
				List<Course> courses = courseService.selectByTeacherId(teacher.getTeacherId());
				List<CourseDao> cDaos = new ArrayList<>();
				listForList(courses, cDaos);
//				for (int j = 0; j < courses.size(); j++) {
//					Course course = courses.get(j);
//
//				}
				request.getSession().setAttribute("courses", cDaos);
			}else {
				return toExit();
			}
		} catch (Exception e) {
			return toError(e);
		}
		return toCourseIndex();
	}

	@PostMapping("/searchCourses")
	@ResponseBody
	public Map<String, Object> searchCourses(String val) {
		Map<String, Object> map = new HashMap<>();
		Teacher teacher = getSessionTeacher();
		if (teacher!=null) {
			List<Course> cList;
			List<CourseDao> cDaos = new ArrayList<>();
			if (!val.isEmpty()) {
				cList = courseService.searchThisTeacherCoursesByKey(teacher.getTeacherId(), val);
			} else {
				cList = courseService.selectByTeacherId(teacher.getTeacherId());
			}
			listForList(cList, cDaos);
			List<Integer> ages=new ArrayList<>();
			for (CourseDao course:cDaos){
				if (!ages.contains(course.getAge())){
					ages.add(course.getAge());
				}
			}
			request.getSession().setAttribute("courses", cDaos);
			map.put("courses", cDaos);
			map.put("ages", ages);
		} else {
			map.put("body", "登录超时，请重新登录！");
		}
		return map;
	}

	@PostMapping("/addCourses")
	public String addCourses(Course course) {
		try {
			Teacher teacher=getSessionTeacher();
			if (teacher!=null){
				course.setAddTime(new Date());
				course.setTeacherId(teacher.getTeacherId());
				course.setId(courseService.getIdByMax() + 1);
				courseService.insert(course);
				return toCourseIndexUrl();
			}else {
				return toExit();
			}
		}catch (Exception e){
			return toError(e);
		}
	}

	@PostMapping("/addPractices")
	public String addPractices(Practice practice) {
		if (!practice.getName().equals("")) {
			Teacher teacher = getSessionTeacher();
			if (teacher!=null) {
				practice.setUpTime(new Date());
				practice.setIsReady(0);
				practice.setQuestionCount(0);
				practice.setId(practiceService.getIdByMax() + 1);
				Course course= getSessionCourse();
				if (course!=null){
                    practice.setCourseId(course.getId());
                    practiceService.addPractice(practice);
                    return "redirect:/Teacher/getPracticeByCourseId/" + practice.getCourseId();
                }
			}else {
				return toExit();
			}
		}
		return toCourseIndexUrl();
	}

	@GetMapping("/deleteCourse/{id}")
	@ResponseBody
	public Map<String, Object> deleteCourse(@PathVariable int id) {
		Map<String, Object> map = new HashMap<>();
		Teacher teacher = getSessionTeacher();
		if (teacher!=null) {
			Course course = courseService.selectByPrimaryKey(id);
			if (course!=null){
				if (course.getTeacherId().equals(teacher.getTeacherId())){
					try {
						map.put("result",courseService.deleteByPrimaryKey(id));
						map.put("courses",courseService.selectByTeacherId(teacher.getTeacherId()));
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

	@GetMapping("deletePractice/{id}")
	public String deletePractice(@PathVariable int id) {
	    Teacher teacher= getSessionTeacher();
	    if (teacher!=null){
			Course course = getSessionCourse();
			if (course.getTeacherId().equals(teacher.getTeacherId())){
				practiceService.deletePracticeByKey(id);
				return "redirect:/Teacher/getPracticeByCourseId/" + course.getId();
			}
		}
		return toExit();
	}

	@GetMapping("/updatePracticeReadyToOn")
	@ResponseBody
	public Map<String, Object> updatePracticeReadyToOn(String pid) {
		boolean updateOk = false;
		Map<String, Object> map = new HashMap<>();
		Teacher teacher = getSessionTeacher();
		if (teacher!=null && pid.contains("practice")){
			Integer id = Integer.valueOf(pid.replace("practice", ""));
			Practice practices = practiceService.getPracticeById(id);
			Course course = getSessionCourse();
			if (practices.getIsReady() != 1) {
				if (course.getTeacherId().equals(teacher.getTeacherId())) {
					practices.setIsReady(1);
					if (practiceService.updatePracticeSelective(practices) == 1) {
						updateOk = true;
					}
				}
			} else {
				updateOk = true;
			}
		}
		map.put("updateOk", updateOk);
		return map;
	}

	@GetMapping("/updatePracticeReadyToOff")
	@ResponseBody
	public Map<String, Object> updatePracticeReadyToOff(String pid) {
		boolean updateOk = false;
		Map<String, Object> map = new HashMap<>();
		Teacher teacher = getSessionTeacher();
		if (teacher!=null && pid.contains("practice")){
			Integer id = Integer.valueOf(pid.replace("practice", ""));
			Practice practices = practiceService.getPracticeById(id);
			Course course = getSessionCourse();

			if (practices.getIsReady() != 0) {
				if (course.getTeacherId().equals(teacher.getTeacherId())) {
					practices.setIsReady(0);
					if (practiceService.updatePracticeSelective(practices) == 1) {
						updateOk = true;
					}
				}
			} else {
				updateOk = true;
			}
		}
		map.put("updateOk", updateOk);
		return map;

	}


    @PostMapping("/updatePractice")
    public String updatePractice(Practice practice) {
        Course course = getSessionCourse();
        if (course!=null){
            try {
                practiceService.updatePracticeSelective(practice);
                return "redirect:/Teacher/getPracticeByCourseId/" + course.getId();
            }catch (Exception e){
                return toError(e);
            }
        }
		return toExit();
	}

	@PostMapping("/searchPractices")
	@ResponseBody
	public Map<String, Object> searchPractices(String val) {
		Map<String, Object> map = new HashMap<>();
		Course course = getSessionCourse();
		if (course!=null) {
			String teacherId = getSessionTeacher().getTeacherId();
			if (!teacherId.isEmpty()) {
				List<Practice> pList;
				if (!val.isEmpty()) {
					pList = practiceService.inquirePracticeByNameByOutlinesByCourseId(course.getId(), val);
				} else {
					pList = practiceService.getPracticeByCourseId(course.getId());
				}
				List<PracticeDao> pDaos = new ArrayList<>();
				for (Practice practice : pList) {
					PracticeDao practiceDao = new PracticeDao();
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
			for (Practice practice : practices) {
				PracticeDao practiceDao = new PracticeDao();
				BeanCopyUtil.beanCopy(practice, practiceDao);
				practiceDao.setStrDate(DateTimeUtils.DATE_TIME_FORMAT.format(practice.getUpTime()));
				pDaos.add(practiceDao);
			}
			request.getSession().setAttribute("practices",pDaos);
			return "Teacher/TeacherPracticeIndex";
		}else {
			return toCourseIndexUrl();
		}
	}
	// region 当前练习管理


	@GetMapping("/QuestionForPractice/{id}")
	public String questionForPractice(@PathVariable int id) {
		//mv.setViewName("TeacherIndex");
		request.getSession().setAttribute("practice", practiceService.getPracticeById(id));
		List<Question> qList = questionsService.getQuestionByPracticeId(id);
		List<QuestionDao> questionDaoList =new ArrayList<>();
		for (Question q : qList) {
			questionDaoList.add(new QuestionDao(q,optionService.getOptionByQuestionKey(q.getId())));
		}
		request.getSession().setAttribute("questions", questionDaoList);
		return "Teacher/TeacherQuestionIndex";
	}

	@GetMapping("deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable int id) {
		Practice practice = (Practice) request.getSession().getAttribute("practice");
		questionsService.deleteByPrimaryKey(id);
		return "redirect:/Teacher/QuestionForPractice/" + practice.getId();

	}

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
