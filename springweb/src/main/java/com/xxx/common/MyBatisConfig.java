package com.xxx.common;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * springboot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 *  @EnableWebMvc表示开启SpringMVC中一些默认配置
 *
 * @Configuration可理解为用spring的时候xml里面的<beans>标签
 * @Bean可理解为用spring的时候xml里面的<bean>标签
 */
@Configuration    //该注解类似于spring配置文件
@MapperScan(basePackages="com.xxx.mapper")
public class MyBatisConfig {

    @Autowired
    private Environment env;

    /**
     * 创建数据源
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     */
    @Bean
    //@Primary
    public DataSource getDataSource() throws Exception{
        System.out.println("MyBatisConfig getDataSource success");
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("jdbc.driverClassName"));
        props.put("url", env.getProperty("jdbc.url"));
        props.put("username", env.getProperty("jdbc.username"));
        props.put("password", env.getProperty("jdbc.password"));
        return DruidDataSourceFactory.createDataSource(props); // 连接池 druid(阿里巴巴的框架)
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception{

        System.out.println("MyBatisConfig SqlSessionFactory sqlSessionFactory");

        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(ds);//指定数据源(这个必须有，否则报错)
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        fb.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));//指定基包
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));//指定xml文件位置


//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        fb.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

//        mybatis.typeAliasesPackage：指定domain类的基包，即指定其在*Mapper.xml文件中可以使用简名来代替全类名（看后边的UserMapper.xml介绍）
//        mybatis.mapperLocations：指定*Mapper.xml的位置
//  mybatis.typeAliasesPackage=com.xxx.firstboot.domain
//  mybatis.mapperLocations=classpath:mapper/*.xml

        return fb.getObject();
    }

}