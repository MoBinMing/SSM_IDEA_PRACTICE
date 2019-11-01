package info.lzzy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import info.lzzy.models.PracticeResult;

public interface PracticeResultMapper {
    
	List<PracticeResult> selectBySIdAndPIdAndQId(@Param("practiceId") Integer practiceId,
                                                 @Param("studentId") String studentId, @Param("questionId") Integer questionId);
	int getIdByMax();
    int insert(PracticeResult practiceResult);
/*int deleteByPrimaryKey(Integer id);
    int insertSelective(PracticeResults record);

    PracticeResults selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PracticeResults record);

    int updateByPrimaryKey(PracticeResults record);*/
}