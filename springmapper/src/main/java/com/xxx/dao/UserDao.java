package com.xxx.dao;

import com.xxx.entity.User;
import com.xxx.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserDao {

    /** 根据ID查询用户信息*/
//    @Select("select * from t_user where id=#{id}")
//    @Select("select * from t_user where id={id}")
    public User findUserById(String id);

    public int insertUser(String username, String address);
//    /**根据用户名称模糊查询用户信息*/
//    public List<User> findUserByName(String username);
//
//    /** 添加用户*/
//    public void insertUser(User user);
//
//    /** 根据ID删除用户*/
//    public void deleteUser(Integer id);
//
//    /** 根据ID更新用户*/
//    public void updateUser(User user);
}