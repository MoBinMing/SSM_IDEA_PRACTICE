package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.QuestionMapper;
import info.lzzy.models.Question;
import info.lzzy.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class QuestionsServiceImpl implements QuestionService{

	@Autowired
	  private QuestionMapper questionMapper;
	  @Override
	public List<Question> getQuestionByPracticeId(Integer practiceId) {
		return questionMapper.getQuestionByPracticeId(practiceId);
	}
	@Override
	public int getIdByMax() {
		// TODO Auto-generated method stub
		return questionMapper.getIdByMax();
	}
	@Override
	public int insert(Question question) {
		// TODO Auto-generated method stub
		return questionMapper.insert(question);
	}
	@Override
	public int updateByPrimaryKey(Question question) {
		// TODO Auto-generated method stub
		return questionMapper.updateByPrimaryKey(question);
	}
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return questionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Question getById(Integer id) {
		return questionMapper.getById(id);
	}

	@Override
	public int getCount() {
		return questionMapper.getCount();
	}

	@Override
	public int getNumberByMax(int practiceId) {
		return questionMapper.getNumberByMax(practiceId);
	}
}
