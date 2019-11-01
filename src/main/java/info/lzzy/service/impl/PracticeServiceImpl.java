package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.PracticeMapper;
import info.lzzy.models.Practice;
import info.lzzy.service.PracticeService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class PracticeServiceImpl implements PracticeService{

	  @Resource
	  private PracticeMapper practiceMapper;


	@Override
	public List<Practice> getAll() {
		// TODO Auto-generated method stub
		return practiceMapper.getAll();
	}

	@Override
	public int addPractice(Practice practice) {
		// TODO Auto-generated method stub
		return practiceMapper.addPractice(practice);
	}

	@Override
	public int deletePracticeByKey(Integer id) {
		// TODO Auto-generated method stub
		return practiceMapper.deletePracticeByKey(id);
	}

	@Override
	public int updatePracticeSelective(Practice practices) {
		// TODO Auto-generated method stub
		return practiceMapper.updatePracticeSelective(practices);
	}

	@Override
	public Practice getPracticeById(Integer id) {
		// TODO Auto-generated method stub
		return practiceMapper.getPracticeById(id);
	}

	@Override
	public int getIdByMax() {
		// TODO Auto-generated method stub
		return practiceMapper.getIdByMax();
	}

	@Override
	public List<Practice> inquirePracticeByNameByOutlinesByCourseId(Integer courseId, String key) {
		// TODO Auto-generated method stub
		return practiceMapper.inquirePracticeByNameByOutlinesByCourseId(courseId, key);
	}

	@Override
	public List<Practice> getPracticeByCourseId(Integer courseId) {
		// TODO Auto-generated method stub
		return practiceMapper.getPracticeByCourseId(courseId);
	}

	@Override
	public Practice getByName(String name) {
		// TODO Auto-generated method stub
		return practiceMapper.getByName(name);
	}

	
}
