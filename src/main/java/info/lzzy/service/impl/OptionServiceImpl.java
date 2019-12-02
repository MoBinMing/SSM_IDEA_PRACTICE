package info.lzzy.service.impl;

import java.util.List;

import info.lzzy.dao.OptionMapper;
import info.lzzy.models.Option;
import info.lzzy.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class OptionServiceImpl implements OptionService {


	@Autowired
	  private OptionMapper optionMapper;

	@Override
	public List<Option> getOptionByQuestionKey(Integer id) {
		// TODO Auto-generated method stub
		return optionMapper.getOptionByQuestionKey(id);
	}

	@Override
	public Option getOptionById(Integer id) {
		// TODO Auto-generated method stub
		return optionMapper.getOptionById(id);
	}

	@Override
	public int getIdByMax() {
		return optionMapper.getIdByMax();
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return optionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Option record) {
		// TODO Auto-generated method stub
		return optionMapper.insert(record);
	}

	@Override
	public int updateByPrimaryKey(Option record) {
		// TODO Auto-generated method stub
		return optionMapper.updateByPrimaryKey(record);
	}


	
}
