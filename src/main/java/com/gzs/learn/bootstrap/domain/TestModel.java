package com.gzs.learn.bootstrap.domain;

import java.io.Serializable;
import java.util.Date;

public class TestModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
