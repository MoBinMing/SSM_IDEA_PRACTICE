package info.lzzy.dao;

import java.util.List;

import info.lzzy.models.*;

public interface QuestionMapper {
    List<Question> getQuestionByPracticeId(Integer practiceId);
    int getIdByMax();
    int insert(Question question);
    int updateByPrimaryKey(Question question);
    int deleteByPrimaryKey(Integer id);
}