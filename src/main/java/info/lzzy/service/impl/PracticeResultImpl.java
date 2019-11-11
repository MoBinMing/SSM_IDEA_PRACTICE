package info.lzzy.service.impl;


import java.util.List;

import info.lzzy.dao.PracticeResultMapper;
import info.lzzy.models.PracticeResult;
import info.lzzy.service.PracticeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PracticeResultImpl implements PracticeResultService{

	@Autowired
	  private PracticeResultMapper practiceResultMapper;

	@Override
	public int insert(PracticeResult practiceResult) {
		// TODO Auto-generated method stub
		return practiceResultMapper.insert(practiceResult);
	}

	@Override
	public int getIdByMax() {
		// TODO Auto-generated method stub
		return practiceResultMapper.getIdByMax();
	}

	@Override
	public List<PracticeResult> selectBySIdAndPIdAndQId(Integer practiceId, String studentId, Integer questionId) {
		// TODO Auto-generated method stub
		return practiceResultMapper.selectBySIdAndPIdAndQId(practiceId, studentId, questionId);
	}

	
}
