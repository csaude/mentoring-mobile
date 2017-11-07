package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.config.model.Form;

/**
 * Created by steliomo on 10/26/17.
 */

public class FormEvent {

    private Form form;

    public FormEvent(Form form) {
        this.form = form;
    }

    public Form getForm() {
        return form;
    }
}
