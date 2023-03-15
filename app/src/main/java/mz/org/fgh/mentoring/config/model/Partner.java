package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by TAMELE, Volóide on 3/10/23.
 */
public class Partner extends GenericEntity {

    private String name;
    private String description;

    public Partner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
