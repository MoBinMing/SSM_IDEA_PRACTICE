package info.lzzy.dao;

import org.apache.ibatis.annotations.Param;

import info.lzzy.models.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String adminId);
    
    Admin selectAdminByIphone(String iphone);
    
    Admin selectAmdinByIphoneAndPaw(@Param("iphone") String iphone,
                                    @Param("password") String password);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}