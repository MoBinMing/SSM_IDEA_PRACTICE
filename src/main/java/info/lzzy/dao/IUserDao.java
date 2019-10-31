package info.lzzy.dao;

import info.lzzy.model.User;

public interface IUserDao {

    User selectUser(long id);
}
