package info.lzzy.service.impl;

import info.lzzy.dao.AdminMapper;
import info.lzzy.dao.ApiMapper;
import info.lzzy.models.Admin;
import info.lzzy.models.Api;
import info.lzzy.service.AdminService;
import info.lzzy.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ApiService")
public class ApiServiceImpl implements ApiService
{
	@Autowired
	private ApiMapper apiMapper;


	@Override
	public int deleteByPrimaryKey(String id) {
		return apiMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Api record) {
		return apiMapper.insert(record);
	}

	@Override
	public int insertSelective(Api record) {
		return apiMapper.insertSelective(record);
	}

	@Override
	public Api selectByPrimaryKey(String id) {
		return apiMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Api> selectByRole(String role) {
		return apiMapper.selectByRole(role);
	}

	@Override
	public List<Api> selectByRoleAndKw(String role, String kw) {
		return apiMapper.selectByRoleAndKw(role,kw);
	}

	@Override
	public int updateByPrimaryKeySelective(Api record) {
		return apiMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Api record) {
		return apiMapper.updateByPrimaryKey(record);
	}
}
