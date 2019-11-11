package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.TeacherMapper;
import info.lzzy.models.Teacher;
import info.lzzy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class TeacherServiceImpl implements TeacherService
{
	@Autowired
	private TeacherMapper teacherMapper;
	@Override
	public Teacher selectTeacherByIphoneAndPaw(String iphone, String password) {
		// TODO Auto-generated method stub
		return teacherMapper.selectTeacherByIphoneAndPaw(iphone, password);
	}
	@Override
	public Teacher selectTeacherByEmail(String email) {
		// TODO Auto-generated method stub
		return teacherMapper.selectTeacherByEmail(email);
	}
	@Override
	public int insert(Teacher teacher) {
		// TODO Auto-generated method stub
		return teacherMapper.insert(teacher);
	}
	@Override
	public Teacher selectTeacherById(String id) {
		// TODO Auto-generated method stub
		return teacherMapper.selectTeacherById(id);
	}
	@Override
	public Teacher selectTeacherByIphone(String iphone) {
		// TODO Auto-generated method stub
		return teacherMapper.selectTeacherByIphone(iphone);
	}
	@Override
	public List<Teacher> selectAllTeacher() {
		// TODO Auto-generated method stub
		return teacherMapper.selectAllTeacher();
	}
	@Override
	public int updateByPrimaryKeySelective(Teacher teacher) {
		// TODO Auto-generated method stub
		return teacherMapper.updateByPrimaryKeySelective(teacher);
	}
}
