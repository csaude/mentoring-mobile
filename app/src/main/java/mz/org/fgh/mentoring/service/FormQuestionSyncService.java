package mz.org.fgh.mentoring.service;

import java.util.List;

import mz.org.fgh.mentoring.config.model.FormQuestion;

/**
 * Created by Stélio Moiane on 6/29/17.
 */
public interface FormQuestionSyncService {

    void processFormQuestions(List<FormQuestion> formQuestions);
}
