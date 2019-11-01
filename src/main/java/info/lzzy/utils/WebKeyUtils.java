package info.lzzy.utils;

import javafx.util.Pair;
import info.lzzy.connstants.ApiConstants;
import info.lzzy.models.Student;
import info.lzzy.models.Teacher;
import info.lzzy.service.StudentService;
import info.lzzy.service.TeacherService;
import org.json.JSONException;
import org.json.JSONObject;

public class WebKeyUtils {
	private static final String ROLE_USER = "ROLE_USER";
	private static final String ROLE_USER_END = "ROLE_USER_END";
	private static String SEPARATOR = "FFFF";
	private static String CONTENT_END = "CONTENT_END";
	private static String TIME_END = "TIME_END";
	private static String key = "key";
	public static final String CONTENT = "content";

	/**
	 * 获取用户请求信息
	 * 
	 * @param bodyRequest 用户请求体
	 * @return Pair String[0]=请求参数。String[1]=请求时间；
	 * @throws Exception
	 */
	public static Pair<String, Long> convertBodyReqest(String bodyRequest) throws Exception {
		/*JSONObject object = new JSONObject(bodyRequest);
		String convertContent = object.getString(CONTENT);
		String[] chars = convertContent.split(SEPARATOR);
		StringBuilder sb = new StringBuilder();
		StringBuilder loginTimeSB = new StringBuilder();
		boolean contentEnd = false;
		boolean timeEnd = false;
		for (int i = 0; i < chars.length; i++) {
			String vi = chars[i];
			if (contentEnd) {
				loginTimeSB.append(vi);
			} else if (timeEnd) {
				sb.append((char) Integer.parseInt(vi));
			} else if (vi.contains(CONTENT_END)) {
				contentEnd = true;
				String[] strings = vi.split(CONTENT_END);
				sb.append((char) Integer.parseInt(strings[0]));
				loginTimeSB.append(strings[1]);
			} else if (vi.contains(TIME_END)) {
				timeEnd = true;
				String[] strings = vi.split(TIME_END);
				sb.append((char) Integer.parseInt(strings[0]));
				loginTimeSB.append(strings[1]);
			} else {
				String content = vi.substring(0, vi.length() - 1);
				String loginTime = vi.replace(content, "");
				sb.append((char) Integer.parseInt(content));
				loginTimeSB.append(loginTime);
			}
		}
		String content = sb.toString();
		Long time = Long.parseLong(loginTimeSB.toString());
		return new Pair<String, Long>(content, time);*/
		JSONObject object= new JSONObject(bodyRequest);
		//return new Pair<String, Long>(bodyRequest, object.getLong("loginTime"));
		return new Pair<String, Long>(bodyRequest, System.currentTimeMillis());
	}

	/**
	 * 返回请求专用加密
	 * 
	 * @param content 登录加密的json
	 * @return 已加密的json请求数据
	 */
	public static String encryptionReturnRequest(String content) {
		/*StringBuilder sb = new StringBuilder();
		String[] contents;
		char[] timeMillis1 = String.valueOf(System.currentTimeMillis()).toCharArray();
		String[] timeMillis = new String[timeMillis1.length];
		for (int i = 0; i < timeMillis1.length; i++) {
			timeMillis[i] = String.valueOf(timeMillis1[i]);
		}
		char[] ch1 = content.toCharArray();
		String[] ch = new String[ch1.length];
		for (int i = 0; i < ch1.length; i++) {
			ch[i] = String.valueOf(Integer.valueOf(ch1[i]).intValue());
		}
		if (timeMillis.length > ch.length) {
			contents = new String[timeMillis.length];
			for (int i = 0; i < timeMillis.length; i++) {
				if (i < ch.length - 1) {
					contents[i] = ch[i] + timeMillis[i];
				} else if (i == ch.length - 1) {
					contents[i] = ch[i] + CONTENT_END + timeMillis[i];
				} else {
					contents[i] = timeMillis[i];
				}
			}
		} else if (timeMillis.length < ch.length) {
			contents = new String[ch.length];
			for (int i = 0; i < ch.length; i++) {
				if (i < timeMillis.length - 1) {
					contents[i] = ch[i] + timeMillis[i];
				} else if (i == timeMillis.length - 1) {
					contents[i] = ch[i] + TIME_END + timeMillis[i];
				} else {
					contents[i] = ch[i];
				}
			}
		} else {
			contents = new String[ch.length];
			for (int i = 0; i < ch.length; i++) {
				contents[i] = ch[i] + timeMillis[i];
			}
		}
		for (int i = 0; i < contents.length; i++) {
			if (i != contents.length - 1) {
				sb.append(contents[i] + SEPARATOR);
			} else {
				sb.append(contents[i]);
			}
		}
		JSONObject object = new JSONObject();
		object.put(CONTENT, sb.toString());
		return object.toString();*/
		return content;
	}

	/**
	 * 
	 * encryption(加密)
	 * 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static String encryption(String content) {
		StringBuilder sb = new StringBuilder();
		String[] contents;
		char[] timeMillis1 = String.valueOf(System.currentTimeMillis()).toCharArray();
		String[] timeMillis = new String[timeMillis1.length];
		for (int i = 0; i < timeMillis1.length; i++) {
			timeMillis[i] = String.valueOf(timeMillis1[i]);
		}
		char[] ch1 = content.toCharArray();
		String[] ch = new String[ch1.length];
		for (int i = 0; i < ch1.length; i++) {
			ch[i] = String.valueOf(Integer.valueOf(ch1[i]).intValue());
		}
		if (timeMillis.length > ch.length) {
			contents = new String[timeMillis.length];
			for (int i = 0; i < timeMillis.length; i++) {
				if (i < ch.length - 1) {
					contents[i] = ch[i] + timeMillis[i];
				} else if (i == ch.length - 1) {
					contents[i] = ch[i] + CONTENT_END + timeMillis[i];
				} else {
					contents[i] = timeMillis[i];
				}
			}
		} else if (timeMillis.length < ch.length) {
			contents = new String[ch.length];
			for (int i = 0; i < ch.length; i++) {
				if (i < timeMillis.length - 1) {
					contents[i] = ch[i] + timeMillis[i];
				} else if (i == timeMillis.length - 1) {
					contents[i] = ch[i] + TIME_END + timeMillis[i];
				} else {
					contents[i] = ch[i];
				}
			}
		} else {
			contents = new String[ch.length];
			for (int i = 0; i < ch.length; i++) {
				contents[i] = ch[i] + timeMillis[i];
			}
		}
		for (int i = 0; i < contents.length; i++) {
			if (i != contents.length - 1) {
				sb.append(contents[i] + SEPARATOR);
			} else {
				sb.append(contents[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 解密key
	 * 
	 * @param convertContent 用户请求体
	 * @return Pair String[0]=返回的参数。String[1]=返回的时间；
	 * @throws Exception
	 */
	public static Pair<String, Long> decode(String convertContent) {
		String[] chars = convertContent.split(SEPARATOR);
		StringBuilder sb = new StringBuilder();
		StringBuilder loginTimeSB = new StringBuilder();
		boolean contentEnd = false;
		boolean timeEnd = false;
		for (int i = 0; i < chars.length; i++) {
			String vi = chars[i];
			if (contentEnd) {
				loginTimeSB.append(vi);
			} else if (timeEnd) {
				sb.append((char) Integer.parseInt(vi));
			} else if (vi.contains(CONTENT_END)) {
				contentEnd = true;
				String[] strings = vi.split(CONTENT_END);
				sb.append((char) Integer.parseInt(strings[0]));
				loginTimeSB.append(strings[1]);
			} else if (vi.contains(TIME_END)) {
				timeEnd = true;
				String[] strings = vi.split(TIME_END);
				sb.append((char) Integer.parseInt(strings[0]));
				loginTimeSB.append(strings[1]);
			} else {
				String content = vi.substring(0, vi.length() - 1);
				String loginTime = vi.replace(content, "");
				sb.append((char) Integer.parseInt(content));
				loginTimeSB.append(loginTime);
			}
		}
		String content = sb.toString();
		Long time = Long.parseLong(loginTimeSB.toString());
		return new Pair<String, Long>(content, time);
	}

	public static String getKey(String body) throws JSONException {
		return new JSONObject(body).getJSONObject(CONTENT).getString(key);
	}
}
