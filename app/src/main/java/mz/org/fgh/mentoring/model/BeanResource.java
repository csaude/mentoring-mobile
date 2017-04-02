package mz.org.fgh.mentoring.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public abstract class BeanResource {

    @SerializedName("userContext")
    private UserContext userContext;

    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    public UserContext getUserContext() {
        return userContext;
    }
}
