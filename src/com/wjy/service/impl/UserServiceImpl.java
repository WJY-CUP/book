package com.wjy.service.impl;

import com.wjy.dao.UserDao;
import com.wjy.dao.impl.UserDaoImpl;
import com.wjy.pojo.User;
import com.wjy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // private UserDao userDao = new UserDaoImpl();
    @Autowired
    private UserDao userDao;

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {

        if (userDao.queryUserByUsername(username) == null) {
           // 等于null,说明没查到，没查到表示可用
           return false;
        }

        return true;

    }
}
