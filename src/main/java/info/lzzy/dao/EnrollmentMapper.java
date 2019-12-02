package info.lzzy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import info.lzzy.models.Enrollment;

public interface EnrollmentMapper {
	Enrollment getByCourseIdAndStudentId(@Param("courseId") Integer courseId,
                                         @Param("studentId") String studentId);
    int deleteByPrimaryKey(Integer id);

    int insert(Enrollment record);
    
    List<Enrollment> getByCourseId(Integer courseId);

    List<Enrollment> getByTeacherIdAndStudentId(@Param("teacherId") String teacherId,
                                                @Param("studentId") String studentId);

    int insertSelective(Enrollment record);

    Enrollment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Enrollment record);

    int updateByPrimaryKey(Enrollment record);

    int getIdByMax();
}