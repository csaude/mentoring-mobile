/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.config.model;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Form extends GenericEntity {

    private String name;

    private String programmaticArea;

    private String version;

    public Form(String code, String name, String programmaticArea, String version) {
        this.setCode(code);
        this.name = name;
        this.programmaticArea = programmaticArea;
        this.version = version;
    }

    public Form() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgrammaticArea() {
        return programmaticArea;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return name;
    }
}
