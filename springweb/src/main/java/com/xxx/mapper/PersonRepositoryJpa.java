package com.xxx.mapper;

import com.xxx.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepositoryJpa extends JpaRepository<Person, Long> {

    List<Person> findByName(String name);

    List<Person> findByAddress(String address);

    Person findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name=:name and p.address=:address")
    Person withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);

    Person withNameAndAddressNamedQuery(String name, String address);

//    当我们继承JpaRepository接口后，我们就自动具备了如下数据访问方法：
//    List<T> findAll();
//    List<T> findAll(Sort var1);
//    List<T> findAll(Iterable<ID> var1);
//    <S extends T> List<S> save(Iterable<S> var1);
//    void flush();
//    <S extends T> S saveAndFlush(S var1);
//    void deleteInBatch(Iterable<T> var1);
//    void deleteAllInBatch();
//    T getOne(ID var1);
//    <S extends T> List<S> findAll(Example<S> var1);
//    <S extends T> List<S> findAll(Example<S> var1, Sort var2);
}