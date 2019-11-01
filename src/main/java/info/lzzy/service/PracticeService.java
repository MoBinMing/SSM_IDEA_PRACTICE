package info.lzzy.service;


import java.util.List;

import info.lzzy.models.Practice;

public  interface PracticeService
{
	List<Practice> getPracticeByCourseId(Integer courseId);
	List<Practice> getAll();
	List<Practice> inquirePracticeByNameByOutlinesByCourseId(Integer courseId, String key);
	int addPractice(Practice practice);
	int deletePracticeByKey(Integer id);
	int updatePracticeSelective(Practice practice);
	int getIdByMax();
	Practice getPracticeById(Integer id);
	Practice getByName(String name);
}
