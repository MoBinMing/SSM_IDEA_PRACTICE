package info.lzzy.dao;

import java.util.List;

import info.lzzy.models.*;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    List<Question> getQuestionByPracticeId(Integer practiceId);
    int getIdByMax();
    int insert(Question question);
    int updateByPrimaryKey(Question question);
    int deleteByPrimaryKey(Integer id);
    Question getById(Integer id);
    int getCount();
    int getNumberByMax(@Param("practiceId") int practiceId);
}