package info.lzzy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import info.lzzy.models.Student;

public interface StudentMapper {
	List<Student> selectStudentByCourseId(int courseId);
	int insert(Student student);
    /*int deleteByPrimaryKey(String studentId);

    

    int insertSelective(Student record);

    Student selectByPrimaryKey(String studentId);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);*/
	Student selectStudentByIphoneAndPaw(@Param("iphone") String iphone,
                                        @Param("password") String password);
	Student selectStudentByEmail(String email);
	Student selectStudentById(String id);
	Student selectStudentByIphone(String iphone);
	List<Student> inquireStudentByKey(String key);
	List<Student> selectAll();
	int updateByPrimaryKeySelective(Student student);
}