package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 4/20/17.
 */
public class ProgrammaticArea extends GenericEntity {

    private String name;

    private String description;

    public ProgrammaticArea() {
    }

    public ProgrammaticArea(String uuid) {
        this.setUuid(uuid);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
