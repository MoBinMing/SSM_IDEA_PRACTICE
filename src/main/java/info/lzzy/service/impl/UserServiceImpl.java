package info.lzzy.service.impl;

import info.lzzy.dao.IUserDao;
import info.lzzy.model.User;
import info.lzzy.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements IUserService {

    @Resource
    private IUserDao userDao;

    public User selectUser(long userId) {
        return userDao.selectUser(userId);
    }
}
