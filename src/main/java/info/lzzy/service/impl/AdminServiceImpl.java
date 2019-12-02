package info.lzzy.service.impl;

import info.lzzy.dao.AdminMapper;
import info.lzzy.models.Admin;
import info.lzzy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AdminService")
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public int deleteByPrimaryKey(String adminId) {
		// TODO Auto-generated method stub
		return adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public int insert(Admin record) {
		// TODO Auto-generated method stub
		return adminMapper.insert(record);
	}

	@Override
	public int insertSelective(Admin record) {
		// TODO Auto-generated method stub
		return adminMapper.insertSelective(record);
	}

	@Override
	public Admin selectByPrimaryKey(String adminId) {
		// TODO Auto-generated method stub
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public Admin selectAmdinByIphoneAndPaw(String iphone, String password) {
		// TODO Auto-generated method stub
		return adminMapper.selectAmdinByIphoneAndPaw(iphone, password);
	}

	@Override
	public int updateByPrimaryKeySelective(Admin record) {
		// TODO Auto-generated method stub
		return updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Admin record) {
		// TODO Auto-generated method stub
		return adminMapper.updateByPrimaryKey(record);
	}

	@Override
	public Admin selectAdminByIphone(String iphone) {
		// TODO Auto-generated method stub
		return adminMapper.selectAdminByIphone(iphone);
	}
	
}
