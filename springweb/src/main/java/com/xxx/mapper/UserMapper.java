package com.xxx.mapper;

import com.xxx.entity.Person;
import com.xxx.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public interface UserMapper {

//    一个普通插入：直接用注解搞定
//    一个插入返回主键：需要使用xml来搞定

    @Insert("INSERT INTO tb_user(username, password) VALUES(#{username},#{password})")
    public int insertUser(@Param("username") String username, @Param("password")  String password);

    /**
     * 插入用户，并将主键设置到user中
     * 注意：返回的是数据库影响条数，即1
     */
    public int insertUserWithBackId(User user);

    // 根据ID查找用户
    public User findUserByID(String userid);
}