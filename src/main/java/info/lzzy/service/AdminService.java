package info.lzzy.service;



import info.lzzy.models.Admin;

public interface AdminService
{
	Admin selectAdminByIphone(String iphone);
	int deleteByPrimaryKey(String adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String adminId);
    
    Admin selectAmdinByIphoneAndPaw(String iphone, String password);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}
