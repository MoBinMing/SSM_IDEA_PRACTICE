package info.lzzy.service;

import java.util.List;


import info.lzzy.models.PracticeResult;

public interface PracticeResultService
{
	List<PracticeResult> selectBySIdAndPIdAndQId(Integer practiceId,
                                                 String studentId, Integer questionId);
	int insert(PracticeResult practiceResult);
	int getIdByMax();
}
