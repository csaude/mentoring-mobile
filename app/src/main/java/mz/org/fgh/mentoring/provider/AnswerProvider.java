package mz.org.fgh.mentoring.provider;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.process.model.Session;

/**
 * Created by steliomo on 11/2/17.
 */

public interface AnswerProvider {

    List<Answer> getAnswers();
}
