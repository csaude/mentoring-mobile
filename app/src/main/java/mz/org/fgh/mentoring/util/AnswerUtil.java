package mz.org.fgh.mentoring.util;

import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Question;

/**
 * Created by steliomo on 11/1/17.
 */

public class AnswerUtil {

    public static boolean wasQuestionAnswered(List<Answer> answers, Question question) {

        for (Answer answer : answers) {

            if (answer.getQuestion().getUuid().equals(question.getUuid())) {
                return true;
            }
        }

        return false;
    }

    public static void addAnswer(List<Answer> answers, Answer newAnswer) {

        for (Answer answer : answers) {

            if (answer.getQuestion().getUuid().equals(newAnswer.getQuestion().getUuid())) {
                answers.remove(answer);
                answers.add(newAnswer);
                return;
            }
        }

        answers.add(newAnswer);
    }
}
