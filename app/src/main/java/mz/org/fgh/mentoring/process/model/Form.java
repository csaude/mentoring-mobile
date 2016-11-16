/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.process.model;

import mz.org.fgh.mentoring.config.model.ProgrammaticArea;
import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Form extends GenericEntity {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String description;

    private ProgrammaticArea programmaticArea;

    //private final Set<FormQuestion> formQuestions = new HashSet<>();
}
