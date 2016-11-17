/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Form extends GenericEntity {

    private String code;

    private String name;

    private String description;

    private String programmaticAreaName;

    public Form(String code, String name, String description, String programmaticAreaName) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.programmaticAreaName = programmaticAreaName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getProgrammaticAreaName() {
        return programmaticAreaName;
    }
}
