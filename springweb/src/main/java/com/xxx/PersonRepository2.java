package com.xxx;

import com.xxx.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

// rest  http://localhost:8080/api/people  注意配置了 /api 头
@RepositoryRestResource(path = "people")
public interface PersonRepository2 extends JpaRepository<Person,Long> {
    @RestResource(path = "nameStartsWith",rel = "nameStartsWith")
    List<Person> findByNameStartsWith(@Param("name") String name);
}
