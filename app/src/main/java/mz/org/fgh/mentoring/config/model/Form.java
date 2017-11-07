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

    private ProgrammaticArea programmaticArea;

    private String version;

    private FormType formType;

    public Form(String uuid, String name, ProgrammaticArea programmaticArea, String version, FormType formType) {
        this.setUuid(uuid);
        this.name = name;
        this.programmaticArea = programmaticArea;
        this.version = version;
        this.formType = formType;
    }

    public Form() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProgrammaticArea getProgrammaticArea() {
        return programmaticArea;
    }

    public String getVersion() {
        return version;
    }

    public FormType getFormType() {
        return formType;
    }

    @Override
    public String toString() {
        return name;
    }
}
