package info.lzzy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import info.lzzy.models.Course;

public interface CourseMapper {
	
	
	int getIdByMax();
	
    int deleteByPrimaryKey(Integer id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer id);
    
    Course selectByName(String name);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
    
    List<Course> selectByTeacherId(String teacherId);
    
    @Select("select *\r\n" + 
    		"		from course\r\n" + 
    		"		where teacher_id = #{teacherId}\r\n" + 
    		"		and name	like concat(\"%\",#{key},\"%\")\r\n" + 
    		"		or intro like concat(\"%\",#{key},\"%\")  order by add_time asc")
    List<Course> searchThisTeacherCoursesByKey(@Param("teacherId") String teacherId, @Param("key") String key);
}