package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.config.model.FormTarget;

public interface FormTargetService {

    void processFormTarget(final List<FormTarget> formTargets);

    FormTarget findFormTargetByFormUuid(final String formUuid);

}
