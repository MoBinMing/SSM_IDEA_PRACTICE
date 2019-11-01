package info.lzzy.dao;

import java.util.List;


import info.lzzy.models.*;
import org.apache.ibatis.annotations.Param;

public interface PracticeMapper {
	List<Practice> getAll();
	List<Practice> getPracticeByCourseId(Integer courseId);
	List<Practice> inquirePracticeByNameByOutlinesByCourseId(@Param("courseId") Integer courseId,
                                                             @Param("key") String key);
	int addPractice(Practice practice);
	int deletePracticeByKey(Integer id);
	
	int updatePracticeSelective(Practice practice);
	int getIdByMax();
	Practice getPracticeById(Integer id);
	Practice getByName(String name);
}