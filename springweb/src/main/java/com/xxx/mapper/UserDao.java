package com.xxx.mapper;


import com.xxx.entity.User;
import com.xxx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;

    public int insertUser(String username, String password) {
        return userMapper.insertUser(username, password);
    }

    public int insertUserWithBackId(User user) {
        return userMapper.insertUserWithBackId(user);
    }

    // 根据ID查找用户
    public User findUserByID(String userid){
        return userMapper.findUserByID(userid);
    }
}