package com.gzs.learn.bootstrap.lambda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.gzs.learn.bootstrap.lambda.Person.Sex;

public class LambdaTest {

    private List<Person> persons = new ArrayList<>();

    @Before
    public void before() {
        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            p.id = i;
            p.setBirthday(LocalDate.now());
            p.setEmailAddress("guanzhisong@gmail.com");
            p.setGender(i % 2 == 0 ? Sex.MALE : Sex.FEMALE);
            p.setName("name" + i);
            persons.add(p);
        }
    }

    @Test
    public void testPerson() {
        persons.stream().filter(p -> p.getGender() == Sex.MALE).filter(p -> p.id > 5)
                .forEach(p -> System.out.println(p.getName()));
    }
}
