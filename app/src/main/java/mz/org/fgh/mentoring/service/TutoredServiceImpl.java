package mz.org.fgh.mentoring.service;

import java.util.List;

import javax.inject.Inject;

import mz.org.fgh.mentoring.config.dao.TutoredDAO;
import mz.org.fgh.mentoring.model.LifeCycleStatus;
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

    @Override
    public Tutored createTutored(Tutored tutored) {
        tutoredDAO.create(tutored);
        return tutored;
    }

    @Override
    public Tutored updateTutored(Tutored tutored) {
        tutoredDAO.update(tutored);
        return tutored;
    }

    @Override
    public List<Tutored> findTutoredsByLifeCycleStatus(final LifeCycleStatus lifeCycleStatus) {
        return tutoredDAO.findByLifeCycleStatus(lifeCycleStatus);
    }

    @Override
    public void deleteTutoredsByUuid(List<String> uuids) {
        tutoredDAO.delete("uuid IN (?)", uuids);
    }

}
