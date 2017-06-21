package com.xxx.service;

import com.xxx.entity.Person;

public interface DemoService {
    public Person savePersonWithRollBack(Person person);

    public Person savePersonWithoutRollBack(Person person);

    public Person save(Person person);

    public void remove(Long id);

    public Person findOne(Person person);
}