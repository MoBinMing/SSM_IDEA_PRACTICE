package info.lzzy.controller;

import java.util.*;

import info.lzzy.models.Admin;
import info.lzzy.utils.DateTimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.utils.Encipher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/Login")
public class LoginController extends BaceController {

    private static final String SESSION_ROLE_KEY = "role";
    private static final String SESSION_ROLE_VALUE_ADMIN = "Admin";
    private static final String SESSION_ROLE_VALUE_TEACHER = "Teacher";
    private static final String SESSION_ROLE_VALUE_STUDENT = "Student";
    private static final String SESSION_USER_KEY = "user";

//    /**
//     * 发送短信验证码
//     */
//    @RequestMapping("/sendSms")
//    @ResponseBody
//    public Object sendSms(HttpServletRequest request, String number) {
//        try {
//            JSONObject json = null;
//            //生成6位验证码
//            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
//            //发送短信
//            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com/sms/send.do",
//                    "103355", "42a1e55b-a845-4931-a6b7-b331f9de9b4c");
//            String result = client.send("17777581901", "您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次！【短信签名】");
//            json = JSONObject.parseObject(result);
//            if(json.getIntValue("code") != 0)//发送短信失败
//                return "fail";
//            //将验证码存到session中,同时存入创建时间
//            //以json存放，这里使用的是阿里的fastjson
//            HttpSession session = request.getSession();
//            json = new JSONObject();
//            json.put("verifyCode", verifyCode);
//            json.put("createTime", System.currentTimeMillis());
//            // 将认证码存入SESSION
//            request.getSession().setAttribute("verifyCode", json);
//            return "success";
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static String appkey = "2d20b33f160ef";
    private static String appSecret = "3e4223750018ce3727bc5a54a6edb882";
    private static String token = "";
    private static String opToken = "opToken";
    private static String operator = "CMCC";

//    public static void main(String[] args) throws Exception {
//        Map<String,Object> map=new HashMap<>();
//        map.put("id","17777581901");
//        //token=JavaWebToken.createJavaWebToken(map);
//        String authHost = "http://identify.verify.mob.com/";
//        String url = authHost + "auth/auth/sdkClientFreeLogin";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("appkey", appkey);
//        request.put("token", token);
//        request.put("opToken", opToken);
//        request.put("operator", operator);
//        request.put("timestamp", System.currentTimeMillis());
//        request.put("sign", SignUtil.getSign(request, appSecret));
//        String response = postRequestNoSecurity(url, null, request);
//
//        JSONObject jsonObject = JSONObject.parseObject(response);
//        if (200 == jsonObject.getInteger("status")) {
//            String res = jsonObject.getString("res");
//            byte[] decode = DES.decode(Base64Utils.decode(res.getBytes()), appSecret.getBytes());
//            jsonObject.put("res", JSONObject.parseObject(new String(decode)));
//        }
//        System.out.println(jsonObject);
//    }


//    public static String postRequestNoSecurity(String url, Map<String, String> headers, Object data) throws Exception {
//        String securityReq = JSON.toJSONString(data);
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).build();
//        RequestBody body = RequestBody.create(MediaType.parse("application/json"), securityReq);
//        Request.Builder builder = new Request.Builder();
//        if (!BaseUtils.isEmpty(headers)) {
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                builder.addHeader(entry.getKey(), entry.getValue());
//            }
//        }
//        final Request request = builder.addHeader("Content-Length", String.valueOf(securityReq.length()))
//                .url(url)
//                .post(body)
//                .build();
//        Call call = okHttpClient.newCall(request);
//        Response response = call.execute();
//
//        String securityRes = response.body().string();
//        return securityRes;
//    }
    @GetMapping("/LoginIndexUrl")
	public String LoginIndexUrl() {
        emptySession();
//        Map<String,Object> map=new HashMap<>();
//        map.put("id","17777581901");
//        token= JavaWebToken.createJavaWebToken(map);
//        String authHost = "http://identify.verify.mob.com/";
//        String url = authHost + "auth/auth/sdkClientFreeLogin";
//        HashMap<String, Object> request = new HashMap<>();
//        request.put("appkey", appkey);
//        request.put("token", token);
//        request.put("opToken", opToken);
//        request.put("operator", operator);
//        request.put("timestamp", System.currentTimeMillis());
//        request.put("sign", SignUtil.getSign(request, appSecret));
//        String response = null;
//        try {
//            response = postRequestNoSecurity(url, null, request);
//            JSONObject jsonObject = JSONObject.parseObject(response);
//            if (200 == jsonObject.getInteger("status")) {
//                String res = jsonObject.getString("res");
//                byte[] decode = DES.decode(Base64Utils.decode(res.getBytes()), appSecret.getBytes());
//                jsonObject.put("res", JSONObject.parseObject(new String(decode)));
//            }
//            System.out.println(jsonObject);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


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
        emptySession();
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
                    map.put("link", "/ApiIndex/studentApiIndexUrl");
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
                            map.put("msg", "ok");
                            map.put("link", "/Admin/AdminTeacherIndexUrl");
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
