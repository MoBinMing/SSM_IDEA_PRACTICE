package info.lzzy.dao;

import java.util.List;

import info.lzzy.models.*;

public interface OptionMapper {
   int deleteByPrimaryKey(Integer id);

    int insert(Option record);

    int updateByPrimaryKey(Option record);
	
	List<Option> getOptionByQuestionKey(Integer id);
	
	Option getOptionById(Integer id);

    int getIdByMax();
}