package info.lzzy.service;

import java.util.List;


import info.lzzy.models.Enrollment;

public interface EnrollmentService
{
	Enrollment getByCourseIdAndStudentId(Integer courseId,
                                         String studentId);
	int deleteByPrimaryKey(Integer id);

    int insert(Enrollment record);

    int insertSelective(Enrollment record);
    List<Enrollment> getByCourseId(Integer courseId);

    Enrollment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Enrollment record);

    int updateByPrimaryKey(Enrollment record);
}
