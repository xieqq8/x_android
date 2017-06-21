package com.xxx.controller;

import com.xxx.entity.Person;
import com.xxx.entity.PersonE;
import com.xxx.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

// 渲染到界面的
//@RestController  //把@RestController改成@Controller，认(以)为使用了Thymeleaf模板，*******1*******1*******1*******1*******1
// 它就会在/resources/templates中渲染对于return的hello.html。
@Controller
@EnableAutoConfiguration
public class Example {

    protected static Logger logger= LoggerFactory.getLogger(Example.class);
//
//    @RequestMapping("/")
//    String home() {
//        logger.debug("访问hello");
//
//        return "Hello World!123 first sping boot!";
//    }
//
//    @RequestMapping("/hello/{myName}")
//    String index(@PathVariable String myName) {
//        logger.debug("访问helloName,Name={}",myName);
//        return "Hello "+myName+"!!!";
//    }

    @RequestMapping("/")
    public String index(Model model) {
        PersonE single = new PersonE("aa", 11);
        List<PersonE> people = new ArrayList<>();
        PersonE p1 = new PersonE("zhangsan", 11);
        PersonE p2 = new PersonE("lisi", 22);
        PersonE p3 = new PersonE("wangwu", 33);
        people.add(p1);
        people.add(p2);
        people.add(p3);
        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);
        return "index";
    }




    // 以下两个方法是 HTTP自动转向HTTPS
//
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//    @Bean
//    public Connector httpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(8080);
//        connector.setSecure(false);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(8443);
//        return connector;
//    }


}