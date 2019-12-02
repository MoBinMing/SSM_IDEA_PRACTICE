package info.lzzy.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.tomcat.util.codec.binary.Base64;

public class UploadImage {
	 
	 public static void convertStringtoImage(String encodedImageStr, String fileUrl) {
		 
		  try {
		   // Base64解码图片
		   byte[] imageByteArray = Base64.decodeBase64(encodedImageStr);
		 
		   ///Practice/WebContent/WEB-INF/resources/images/userImg
		// TODO 删除对应的app包
		  
		   FileOutputStream imageOutFile = new FileOutputStream(fileUrl);
		   imageOutFile.write(imageByteArray);
		 
		   imageOutFile.close();
		 
		   System.out.println("上传图片成功");
		  } catch (FileNotFoundException fnfe) {
		   System.out.println("Image Path not found" + fnfe);
		  } catch (IOException ioe) {
		   System.out.println("Exception while converting the Image " + ioe);
		  }
		 }
		}