package info.lzzy.service;


import java.util.List;

import info.lzzy.models.Question;
import org.apache.ibatis.annotations.Param;

public  interface QuestionService
{
	List<Question> getQuestionByPracticeId(Integer practiceId);
	int getIdByMax();
	 int insert(Question question);
	 int updateByPrimaryKey(Question question);
	 int deleteByPrimaryKey(Integer id);
	Question getById(Integer id);
	int getCount();
	int getNumberByMax(int practiceId);
}
