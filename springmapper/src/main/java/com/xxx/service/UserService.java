package com.xxx.service;


import com.xxx.dao.UserDao;
import com.xxx.entity.User;
import com.xxx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

//    @Autowired
//    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    public boolean addUser(String username, String password) {
        return userMapper.insertUser(username, password) == 1 ? true : false;
    }

    // 根据ID查找用户

    /**
     * 不用dao也行，直接用usermapper
     * @param userid
     * @return
     */
    public User findUserByID(String userid){
//        return  userDao.findUserById(userid);
        return userMapper.findUserByID(userid);
    }
}