package mz.org.fgh.mentoring.service;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Stélio Moiane on 5/31/17.
 */
public class TutoredServiceImpl implements TutoredService {

    @Inject
    TutoredDAO tutoredDAO;

    @Inject
    public TutoredServiceImpl() {
    }

    @Override
    public void processFoundTutoredByUser(final List<Tutored> tutoreds) {

        if (tutoreds == null) {
            return;
        }

        for (Tutored tutored : tutoreds) {
            if (!tutoredDAO.exist(tutored.getUuid())) {
                tutoredDAO.create(tutored);
            }
        }

    }
}
