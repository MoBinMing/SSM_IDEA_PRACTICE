package info.lzzy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    /**
     * 中国手机号码
     */
    private static String CHINESE_PHONE_PATTERN = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    
    /**
     * 是否是有效的中国手机号码
     * @param phone
     * @return
     */
    public static boolean isValidChinesePhone(String phone) {
    	          if(phone.length() == 11){
    	        	   Pattern p = Pattern.compile(CHINESE_PHONE_PATTERN);
    	             Matcher m = p.matcher(phone);
    	             boolean isMatch = m.matches();
    	             if(isMatch){
    	                 return true;
    	             }
    	          }
    	          return false;
    }
}
