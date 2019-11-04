package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.EnrollmentMapper;
import info.lzzy.models.Enrollment;
import info.lzzy.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	  @Autowired
	  private EnrollmentMapper enrollmentMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return enrollmentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Enrollment record) {
		// TODO Auto-generated method stub
		return enrollmentMapper.insert(record);
	}

	@Override
	public int insertSelective(Enrollment record) {
		// TODO Auto-generated method stub
		return enrollmentMapper.insertSelective(record);
	}

	@Override
	public Enrollment selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return enrollmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Enrollment record) {
		// TODO Auto-generated method stub
		return enrollmentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Enrollment record) {
		// TODO Auto-generated method stub
		return enrollmentMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Enrollment> getByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		return enrollmentMapper.getByCourseId(courseId);
	}

	@Override
	public Enrollment getByCourseIdAndStudentId(Integer courseId, String studentId) {
		// TODO Auto-generated method stub
		return enrollmentMapper.getByCourseIdAndStudentId(courseId, studentId);
	}

	
	
}
