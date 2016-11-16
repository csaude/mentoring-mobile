package mz.org.fgh.mentoring.model;

import java.io.Serializable;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public abstract class GenericEntity implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
