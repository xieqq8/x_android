package com.xxx.service;

import com.xxx.mapper.PersonRepositoryJpa;
import com.xxx.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    PersonRepositoryJpa personRepository;

    // 事务

    @Transactional(rollbackFor = {IllegalArgumentException.class})
    @Override
    public Person savePersonWithRollBack(Person person) {
        Person p = personRepository.save(person);
        if (person.getName().equals("sang")) {
            throw new IllegalArgumentException("sang 已存在，数据将回滚");
        }
        return p;
    }

    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    @Override
    public Person savePersonWithoutRollBack(Person person) {
        Person p = personRepository.save(person);
        if (person.getName().equals("sang")) {
            throw new IllegalArgumentException("sang已存在，但数据不会回滚");
        }
        return p;
    }

    //1.@CachePut表示缓存新添加的数据或者更新的数据到缓存中，两个参数value表示缓存的名称为people，key表示缓存的key为person的id
    //2.@CacheEvict表示从缓存people中删除key为id的数据
    //3.@Cacheable表示添加数据到缓存中，缓存名称为people，缓存key为person的id属性。

    @CachePut(value = "people", key = "#person.id")
    @Override
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id、key为" + p.getId() + "数据做了缓存");
        return p;
    }

    @CacheEvict(value = "people")
    @Override
    public void remove(Long id) {
        System.out.println("删除了id、key为" + id + "的数据缓存");
        personRepository.delete(id);
    }

    @Cacheable(value = "people", key = "#person.id")
    @Override
    public Person findOne(Person person) {
        Person p = personRepository.findOne(person.getId());
        System.out.println("为id、key为" + p.getId() + "数据做了缓存");
        return p;
    }
}