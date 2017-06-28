package com.xxx.mapper;

import com.xxx.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.springframework.data.repository.query.Param;

public interface UserMapper {

//    一个普通插入：直接用注解搞定
//    一个插入返回主键：需要使用xml来搞定
    @Insert("INSERT INTO t_user(username, address) VALUES(#{arg0},#{arg1})")
    public int insertUser(@Param("username") String username, @Param("address") String address);

    // 根据ID查找用户
    public User findUserByID(String userid);
}