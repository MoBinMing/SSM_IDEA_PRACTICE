package info.lzzy.service;

import java.util.List;

import info.lzzy.models.Course;

public interface CourseService {
	int getIdByMax();
	int deleteByPrimaryKey(Integer id);

	int insert(Course record);

	int insertSelective(Course record);

	Course selectByPrimaryKey(Integer id);

	Course selectByName(String name);

	int updateByPrimaryKeySelective(Course record);

	int updateByPrimaryKey(Course record);

	List<Course> selectByTeacherId(String teacherId);
	
	List<Course> searchThisTeacherCoursesByKey(String teacherId, String key);
}
