package info.lzzy.service;

import java.util.List;

import info.lzzy.models.Teacher;

public interface TeacherService
{
	Teacher selectTeacherByIphoneAndPaw(String iphone, String password);
	Teacher selectTeacherByEmail(String email);
	int insert(Teacher teacher);
	int deleteTeacherByKey(String id);
	Teacher selectTeacherById(String id);
	Teacher selectTeacherByIphone(String iphone);
	List<Teacher> selectAllTeacher();
	int updateByPrimaryKeySelective(Teacher teacher);
	List<Teacher> selectByKw(String kw);
}
