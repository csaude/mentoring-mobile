package mz.org.fgh.mentoring.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import mz.org.fgh.mentoring.config.model.Career;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */
public class Tutored extends GenericEntity {

    private String name;

    private String surname;

    private String phoneNumber;

    private Career career;

    public Tutored() {
    }

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

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }


    @Override
    public String toString() {
        return name + " " + surname;
    }
}

