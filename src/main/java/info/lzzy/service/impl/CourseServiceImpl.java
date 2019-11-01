package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.CourseMapper;
import info.lzzy.models.Course;
import info.lzzy.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl implements CourseService {

	  @Resource
	  private CourseMapper courseMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return courseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Course record) {
		// TODO Auto-generated method stub
		return courseMapper.insert(record);
	}

	@Override
	public int insertSelective(Course record) {
		// TODO Auto-generated method stub
		return courseMapper.insertSelective(record);
	}

	@Override
	public Course selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return courseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Course record) {
		// TODO Auto-generated method stub
		return courseMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Course record) {
		// TODO Auto-generated method stub
		return courseMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Course> selectByTeacherId(String teacherId) {
		// TODO Auto-generated method stub
		return courseMapper.selectByTeacherId(teacherId);
	}

	@Override
	public Course selectByName(String name) {
		// TODO Auto-generated method stub
		return courseMapper.selectByName(name);
	}

	@Override
	public int getIdByMax() {
		// TODO Auto-generated method stub
		return courseMapper.getIdByMax();
	}

	@Override
	public List<Course> searchThisTeacherCoursesByKey(String teacherId, String key) {
		// TODO Auto-generated method stub
		return courseMapper.searchThisTeacherCoursesByKey(teacherId, key);
	}

}
