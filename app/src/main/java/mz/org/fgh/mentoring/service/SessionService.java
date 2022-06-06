package mz.org.fgh.mentoring.service;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 2/6/18.
 */

public interface SessionService {

    Session createSession(final Session session);

    List<Session> findAllSessions();

    List<Session> findAllSessionsToSync();

    void deleteSessionsByUuids(List<String> uuids);

    List<Session> findSessionsByUuids(String selectecdItemsUuid);
}
