package mz.org.fgh.mentoring.model;

import java.io.Serializable;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */
public class Tutored implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public  Tutored(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id+"-"+name+ " "+surname;
    }
}

