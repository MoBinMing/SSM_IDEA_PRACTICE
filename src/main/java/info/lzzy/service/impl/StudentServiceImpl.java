package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.StudentMapper;
import info.lzzy.models.Student;
import info.lzzy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService
{
	@Autowired
	private StudentMapper studentMapper;
	@Override
	public Student selectStudentByIphoneAndPaw(String iphone, String password) {
		// TODO Auto-generated method stub
		return studentMapper.selectStudentByIphoneAndPaw(iphone, password);
	}
	@Override
	public Student selectStudentByEmail(String email) {
		// TODO Auto-generated method stub
		return studentMapper.selectStudentByEmail(email);
	}
	@Override
	public int insert(Student student) {
		// TODO Auto-generated method stub
		return studentMapper.insert(student);
	}
	@Override
	public Student selectStudentById(String id) {
		// TODO Auto-generated method stub
		return studentMapper.selectStudentById(id);
	}
	@Override
	public Student selectStudentByIphone(String iphone) {
		// TODO Auto-generated method stub
		return studentMapper.selectStudentByIphone(iphone);
	}
	@Override
	public List<Student> inquireStudentByKey(String key) {
		// TODO Auto-generated method stub
		return studentMapper.inquireStudentByKey(key);
	}
	@Override
	public List<Student> selectAll() {
		// TODO Auto-generated method stub
		return studentMapper.selectAll();
	}
	@Override
	public List<Student> selectStudentByCourseId(int courseId) {
		// TODO Auto-generated method stub
		return studentMapper.selectStudentByCourseId(courseId);
	}
	
}
