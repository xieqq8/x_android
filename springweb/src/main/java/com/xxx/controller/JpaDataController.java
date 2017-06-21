package com.xxx.controller;

import com.xxx.mapper.PersonRepositoryJpa;
import com.xxx.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JpaDataController {
    @Autowired
    PersonRepositoryJpa personRepository;

    @RequestMapping("/save")
    public Person save(String name, String address, Integer age) {
        Person person = personRepository.save(new Person(null, name, age, address));
        return person;
    }

    @RequestMapping("/q1")  // post
    public List<Person> q1(String address) {
        List<Person> people = personRepository.findByAddress(address);
        return people;
    }

    @RequestMapping("/q2")
    public Person q2(String name, String address) {
        Person people = personRepository.findByNameAndAddress(name, address);
        return people;
    }

    @RequestMapping("/q3")
    public Person q3(String name, String address) {
        Person person = personRepository.withNameAndAddressQuery(name, address);
        return person;
    }

    @RequestMapping("/q4")
    public Person q4(String name, String address) {
        Person person = personRepository.withNameAndAddressNamedQuery(name, address);
        return person;
    }
    @RequestMapping("/sort")
    public List<Person> sort() {
        List<Person> people = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
//        List<Person> people = personRepository.findFirst10ByName(new Sort(Sort.Direction.ASC, "age"));

        return people;
    }
    @RequestMapping("/page")
    public Page<Person> page(int page, int size){
        Page<Person> all = personRepository.findAll(new PageRequest(page, size));
        return all;
    }
    @RequestMapping("/all")
    public List<Person> all(){
        return personRepository.findAll();
    }
}
