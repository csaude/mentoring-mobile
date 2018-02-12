package mz.org.fgh.mentoring.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.process.model.Mentorship;

/**
 * Created by Stélio Moiane on 4/11/17.
 */
public class MentorshipHelper {

    private Mentorship mentorship;

    private List<AnswerHelper> answers;

    public MentorshipHelper() {
        answers = new ArrayList<>();
    }

    public Mentorship getMentorship() {
        return mentorship;
    }

    public void setMentorship(Mentorship mentorship) {
        this.mentorship = mentorship;
    }

    public void prepareAnswerHelper(List<Answer> answers) {
        for (Answer answer : answers) {
            AnswerHelper answerHelper = new AnswerHelper(answer.getQuestion().getUuid(), answer.getUuid(), answer.getValue());
            answerHelper.setQuestion(answer.getQuestion());
            this.answers.add(answerHelper);
        }
    }

    public List<AnswerHelper> getAnswers() {
        return Collections.unmodifiableList(this.answers);
    }
}
