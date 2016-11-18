package mz.org.fgh.mentoring.model;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */
public class Tutored extends GenericEntity {

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

    @Override
    public String toString() {
        return getId() + "-" + name + " " + surname;
    }
}

