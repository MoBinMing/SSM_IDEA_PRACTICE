package info.lzzy.utils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ObjectUtils;

public class ApiUtil {

	/**
	 * 
	 * allFieldIsNULL(判断一个对象里面的属性是否为null) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程
	 * – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param name
	 * @param      @return 设定文件
	 * @return String DOM对象
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */

	public static boolean allFieldIsNULL(Object o) {
		// Object o = new Object(); //TODO o表示一个对象
		try {
			for (Field field : o.getClass().getDeclaredFields()) { // TODO 循环该类，取出类中的每个属性
				field.setAccessible(true);// TODO 把该类中的所有属性设置成 public

				Object object = field.get(o); // TODO object 是对象中的属性
				if (object instanceof CharSequence) { // TODO 判断对象中的属性的类型，是否都是CharSequence的子类
					if (ObjectUtils.isEmpty(object)) { // TODO 如果是他的子类，那么就可以用ObjectUtils.isEmpty进行比较
						return true;
					}
				} else { // TODO 如果不是那就直接用null判断
					if (null == object) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static boolean isEmail(String string) {
		if (string == null) {
			return false;
		}
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(string);
		if (m.matches()) {
			return true;
		}else {
			return false;
		}
	}
}
