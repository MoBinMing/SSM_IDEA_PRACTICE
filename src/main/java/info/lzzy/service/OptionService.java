package info.lzzy.service;

import java.util.List;

import info.lzzy.models.Option;

public interface OptionService {
	int deleteByPrimaryKey(Integer id);

	int insert(Option record);

	int updateByPrimaryKey(Option record);

	List<Option> getOptionByQuestionKey(Integer id);

	Option getOptionById(Integer id);
}
