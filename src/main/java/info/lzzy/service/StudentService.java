package info.lzzy.service;


import java.util.List;

import info.lzzy.models.Student;

public interface StudentService
{
	Student selectStudentByIphoneAndPaw(String iphone, String password);
	Student selectStudentByEmail(String email);
	int insert(Student student);
	Student selectStudentById(String id);
	List<Student> selectStudentByCourseId(int courseId);
	Student selectStudentByIphone(String iphone);
	List<Student> inquireStudentByKey(String key);
	List<Student> selectAll();
	int updateByPrimaryKeySelective(Student student);
}
