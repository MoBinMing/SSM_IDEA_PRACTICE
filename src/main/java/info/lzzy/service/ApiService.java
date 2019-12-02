package info.lzzy.service;


import info.lzzy.models.Api;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ApiService
{
    int deleteByPrimaryKey(String id);

    int insert(Api record);

    int insertSelective(Api record);

    Api selectByPrimaryKey(String id);

    List<Api> selectByRole(String role);

    List<Api> selectByRoleAndKw(String role,String kw);

    int updateByPrimaryKeySelective(Api record);

    int updateByPrimaryKey(Api record);
}
