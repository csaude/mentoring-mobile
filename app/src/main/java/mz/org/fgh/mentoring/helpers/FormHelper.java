package mz.org.fgh.mentoring.helpers;

import mz.org.fgh.mentoring.config.model.Form;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class FormHelper {

    private String code;

    private String name;

    private ProgrammaticAreaHelper programmaticArea;

    public Form getForm() {

        Form form = new Form(code, name, programmaticArea.getName(), null);

        return form;
    }
}
