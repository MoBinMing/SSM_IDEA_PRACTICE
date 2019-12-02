package info.lzzy.models.ApiView;

import info.lzzy.utils.DateTimeUtils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.Date;

public class ApiCourse {

    private String name;

    private String intro;
    
    private Integer age;
    
    @Override
    public String toString() {
    	JSONObject json=new JSONObject();
        try {
            json.put("name", name);
            json.put("age", age);
            json.put("intro", intro);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return json.toString();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro == null ? null : intro.trim();
    }

    public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	/**
     * applicationStatus
     * -1:未申请该课程
     * 0:等待老师批准
     * 1:是我的课程
     * 2:拒绝了你的申请
     */
	public String toJSON(Integer applicationStatus) {
		JSONObject object=new JSONObject();

		object.put("age", age);
    	object.put("name", name);
    	object.put("intro", intro);
    	object.put("applicationStatus", applicationStatus);
    	return object.toString();   
	}
	
	public String toJSON(Boolean isMyTeacher) {
		JSONObject object=new JSONObject();
		object.put("age", age);
    	object.put("name", name);
    	object.put("intro", intro);
    	object.put("isMyCourse", isMyTeacher);
    	return object.toString();   
	}
}