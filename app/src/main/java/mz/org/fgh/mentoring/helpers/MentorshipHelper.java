package mz.org.fgh.mentoring.helpers;

import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.process.model.Mentorship;

/**
 * Created by Stélio Moiane on 4/11/17.
 */
public class MentorshipHelper {

    private Mentorship mentorship;

    private String startDate;

    private String endDate;

    private String performedDate;

    private List<AnswerHelper> answers;

    public MentorshipHelper() {
        answers = new ArrayList<>();
    }

    public List<AnswerHelper> getAnswers() {
        return answers;
    }

    public Mentorship getMentorship() {
        return mentorship;
    }

    public void setMentorship(Mentorship mentorship) {
        this.mentorship = mentorship;
    }

    public void setAnswers(List<AnswerHelper> answers) {
        this.answers = answers;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPerformedDate() {
        return performedDate;
    }

    public void setPerformedDate(String performedDate) {
        this.performedDate = performedDate;
    }

    public void prepareAnswerHelper(List<Answer> answers) {
        for (Answer answer : answers) {
            this.answers.add(new AnswerHelper(answer.getQuestion().getUuid(), answer.getUuid(), answer.getValue()));
        }
    }
}
