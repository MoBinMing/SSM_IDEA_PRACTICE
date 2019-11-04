package info.lzzy.models;

import java.util.Date;
import info.lzzy.utils.DateTimeUtils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class Course {
    private Integer id;

    private String teacherId;

    private String name;

    private String intro;

    private Date addTime;
    
    private Integer age;
    
    @Override
    public String toString() {
    	JSONObject json=new JSONObject();
        try {
            json.put("id", id);
            json.put("name", name);
            json.put("age", age);
            json.put("teacherId", teacherId);
            json.put("intro", intro);
            json.put("addTime", DateTimeUtils.DATE_TIME_FORMAT.format(addTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return json.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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
		object.put("id", id);
		object.put("age", age);
    	object.put("teacherId", teacherId);
    	object.put("name", name);
    	object.put("intro", intro);
    	object.put("addTime",  DateTimeUtils.DATE_TIME_FORMAT.format(addTime));
    	object.put("applicationStatus", applicationStatus);
    	return object.toString();   
	}
	
	public String toJSON(Boolean isMyTeacher) {
		JSONObject object=new JSONObject();
		object.put("id", id);
		object.put("age", age);
    	object.put("teacherId", teacherId);
    	object.put("name", name);
    	object.put("intro", intro);
    	object.put("addTime",  DateTimeUtils.DATE_TIME_FORMAT.format(addTime));
    	object.put("isMyCourse", isMyTeacher);
    	return object.toString();   
	}
}