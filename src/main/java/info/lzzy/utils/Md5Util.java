package info.lzzy.utils;

public class Md5Util {
	/*private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    *//**
     * 用于获取一个String的md5值
     * @param string
     * @return
     *//*
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

    *//**
     * 加密密码
     *//*
    public static String PawEncrypted(String paw) {
    	String encrypted1=getMd5(paw);
    	String encrypted2=encrypted1.substring(0, 16);
    	String encrypted3=encrypted1.substring(16, 32);
    	return encrypted3+encrypted2;
    }
    
    public static void main(String[] args) {
        System.out.println(PawEncrypted("t"));
    }*/
}
