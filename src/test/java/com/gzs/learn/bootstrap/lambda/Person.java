package com.gzs.learn.bootstrap.lambda;

import java.time.LocalDate;

public class Person {
    public enum Sex {
        MALE, FEMALE
    }
    public int id;
    private String name;
    private LocalDate birthday;
    private Sex gender;
    private String emailAddress;

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
