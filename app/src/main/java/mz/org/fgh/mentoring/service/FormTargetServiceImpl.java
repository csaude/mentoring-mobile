package mz.org.fgh.mentoring.service;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.config.dao.FormTargetDAO;
import mz.org.fgh.mentoring.config.model.FormTarget;

public class FormTargetServiceImpl implements FormTargetService {

    @Inject
    FormTargetDAO formTargetDAO;

    @Inject
    public FormTargetServiceImpl() {
    }

    @Override
    public void processFormTarget(List<FormTarget> formTargets) {

        if (formTargets == null) {
            return;
        }

        for (FormTarget formTarget : formTargets) {

            FormTarget foundFormTarget = formTargetDAO.findByUuid(formTarget.getUuid());

            if (foundFormTarget == null) {
                formTargetDAO.create(formTarget);
            } else {
                formTargetDAO.update(formTarget);
            }
        }
    }

    @Override
    public FormTarget findFormTargetByFormUuid(String formUuid) {
        return formTargetDAO.findByFormUuid(formUuid);
    }
}
