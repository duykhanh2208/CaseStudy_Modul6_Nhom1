package com.houserenting.dto;

public class ObjectWith2String {
    private Long id;
    private String string1;
    private String string2;

    public ObjectWith2String() {
    }

    public ObjectWith2String(Long id, String string1) {
        this.id = id;
        this.string1 = string1;
    }

    public ObjectWith2String(Long id, String string1, String string2) {
        this.id = id;
        this.string1 = string1;
        this.string2 = string2;
    }

    public ObjectWith2String(String string1) {
        this.string1 = string1;
    }

    public ObjectWith2String(String string1, String string2) {
        this.string1 = string1;
        this.string2 = string2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }
}

