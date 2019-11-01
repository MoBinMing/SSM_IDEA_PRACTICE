package info.lzzy.models;


import org.json.JSONException;
import org.json.JSONObject;

public class Student {
    private String studentId;

    private String name;

    private String email;

    private String password;

    private String iphone;

    private String gender;

    private String imghead;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email ;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password ;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone ;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender ;
    }

    public String getImghead() {
        return imghead;
    }

    public void setImghead(String imghead) {
        this.imghead = imghead == null ? null : imghead.trim();
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	JSONObject json=new JSONObject();
        try {
            json.put("studentId", studentId);
            json.put("name", name);
            json.put("email", email);
            json.put("iphone", iphone);
            json.put("gender", gender);
            json.put("imgHead", imghead);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return json.toString();
    }
    
    public String toStudentJson(int takeEffect) {
    	// TODO Auto-generated method stub
    	JSONObject json=new JSONObject();
        try {
            json.put("studentId", studentId);
            json.put("name", name);
            json.put("email", email);
            json.put("iphone", iphone);
            json.put("gender", gender);
            json.put("imgHead", imghead);
            json.put("takeEffect", takeEffect);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return json.toString();
    }
    
    public String toTeacherJson(Boolean isMyStudent) {
    	// TODO Auto-generated method stub
    	JSONObject json=new JSONObject();
        try {
            json.put("studentId", studentId);
            json.put("name", name);
            json.put("email", email);
            json.put("iphone", iphone);
            json.put("gender", gender);
            json.put("imgHead", imghead);
            json.put("isMyStudent", isMyStudent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return json.toString();
    }
}