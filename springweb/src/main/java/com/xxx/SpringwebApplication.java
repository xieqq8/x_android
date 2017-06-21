package com.xxx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringwebApplication.class, args);
		System.out.println("SpringwebApplication boot success");
	}

//	@RequestMapping("/")
//	public String index(Model model) {
//		Person single = new Person("aa", 11);
//		List<Person> people = new ArrayList<>();
//		Person p1 = new Person("zhangsan", 11);
//		Person p2 = new Person("lisi", 22);
//		Person p3 = new Person("wangwu", 33);
//		people.add(p1);
//		people.add(p2);
//		people.add(p3);
//		model.addAttribute("singlePerson", single);
//		model.addAttribute("people", people);
//		return "index";
//	}
//
//	@Bean
//	public EmbeddedServletContainerFactory servletContainer() {
//		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint constraint = new SecurityConstraint();
//				constraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				constraint.addCollection(collection);
//				context.addConstraint(constraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(httpConnector());
//		return tomcat;
//	}
//
//	@Bean
//	public Connector httpConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		//Connector监听的http的端口号
//		connector.setPort(8080);
//		connector.setSecure(false);
//		//监听到http的端口号后转向到的https的端口号
//		connector.setRedirectPort(8443);
//		return connector;
//	}
}
