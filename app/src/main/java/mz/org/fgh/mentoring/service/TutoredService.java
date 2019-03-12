package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.model.LifeCycleStatus;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Stélio Moiane on 6/29/17.
 */
public interface TutoredService {

    void processFoundTutoredByUser(final List<Tutored> tutoreds);

    Tutored createTutored(Tutored tutored);

    Tutored updateTutored(Tutored Tutored);

    List<Tutored> findTutoredsByLifeCycleStatus(LifeCycleStatus lifeCycleStatus);

    void deleteTutoredsByUuid(List<String> uuids);
}
