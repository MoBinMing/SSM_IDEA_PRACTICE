package info.lzzy.service;


import java.util.List;

import info.lzzy.models.Question;

public  interface QuestionService
{
	List<Question> getQuestionByPracticeId(Integer practiceId);
	int getIdByMax();
	 int insert(Question question);
	 int updateByPrimaryKey(Question question);
	 int deleteByPrimaryKey(Integer id);
}
