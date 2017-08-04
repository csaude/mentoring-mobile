package mz.org.fgh.mentoring.service;

import mz.org.fgh.mentoring.infra.UserContext;

/**
 * Created by Stélio Moiane on 7/10/17.
 */
public interface UserService {

    void changeUserPassword(UserContext context);
}
