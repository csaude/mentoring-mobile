/**
 *
 */
package mz.org.fgh.mentoring.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * @author Stélio Moiane
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserContext extends GenericEntity {

    public static final String USER_CONTEXT = "user";

    private String fullName;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private Tutor tutor;

    @JsonProperty("logged")
    private boolean isLogged;

    public UserContext() {
    }

    public UserContext(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public String getTutorName() {

        if (fullName == null) {
            return fullName;
        }

        String[] names = fullName.split(" ");
        StringBuilder builder = new StringBuilder();
        int size = names.length - 1;

        for (int count = 0; size > count; count++) {

            builder.append(names[count]);

            if (!(count + 1 == size)) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

    public String getTutorSurname() {

        if (fullName == null) {
            return fullName;
        }

        String[] names = fullName.split(" ");
        return names[names.length - 1];
    }
}
