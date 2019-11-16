package info.lzzy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import info.lzzy.models.Student;
import info.lzzy.models.Teacher;

public interface TeacherMapper {
	int insert(Teacher teacher);
    int deleteTeacherByKey(String id);

    /*int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(String teacherId);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);*/
	Teacher selectTeacherByIphoneAndPaw(@Param("iphone") String iphone,
                                        @Param("password") String password);
	Teacher selectTeacherByEmail(String email);
	Teacher selectTeacherById(String id);
	Teacher selectTeacherByIphone(String iphone);
	List<Teacher> selectAllTeacher();

	List<Teacher> selectByKw(@Param("kw") String kw);
	int updateByPrimaryKeySelective(Teacher teacher);
}