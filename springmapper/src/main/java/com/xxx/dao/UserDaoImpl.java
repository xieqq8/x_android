package com.xxx.dao;

import com.xxx.entity.User;
import com.xxx.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
//    private SqlSessionFactory sqlSessionFactory;
//
//    // 需要向dao实现类中注入SqlSessionFactory
//    // 通过构造方法注入
//    public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
//        this.sqlSessionFactory = sqlSessionFactory;
//    }
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(String id) {
        return userMapper.findUserByID(id); // 用mapper这里不需要DAO层

        // 这样写需要DAO层
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        User user = sqlSession.selectOne("test.findUserById", id);
//        // 释放资源
//        sqlSession.close();
//        return user;
    }

    @Override
    public int insertUser(String username, String address) {
        return userMapper.insertUser(username, address);
    }

//    @Override
//    public List<User> findUserByName(String username) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        List<User> list = sqlSession
//                .selectList("test.findUserByName", username);
//
//        // 提交事务
//        sqlSession.commit();
//        // 释放资源
//        sqlSession.close();
//        return list;
//    }
//
//    @Override
//    public void insertUser(User user) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        // 执行插入操作
//        sqlSession.insert("test.insertUser", user);
//        // 提交事务
//        sqlSession.commit();
//        // 释放资源
//        sqlSession.close();
//    }
//
//    @Override
//    public void deleteUser(Integer id) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        // 执行插入操作
//        sqlSession.delete("test.deleteUser", id);
//        // 提交事务
//        sqlSession.commit();
//        // 释放资源
//        sqlSession.close();
//    }
//
//    @Override
//    public void updateUser(User user) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        // 执行插入操作
//        sqlSession.update("test.updateUser", user);
//        // 提交事务
//        sqlSession.commit();
//        // 释放资源
//        sqlSession.close();
//    }

}