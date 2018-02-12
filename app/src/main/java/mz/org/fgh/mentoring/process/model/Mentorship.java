/*
 * Friends in Global Health - FGH © 2016
 */

package mz.org.fgh.mentoring.process.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.config.model.Tutor;
import mz.org.fgh.mentoring.model.GenericEntity;
import mz.org.fgh.mentoring.model.Tutored;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Mentorship extends GenericEntity {

    private Date startDate;

    private Date endDate;

    private Date performedDate;

    private Tutored tutored;

    private Form form;

    private HealthFacility healthFacility;

    private Tutor tutor;

    private Session session;

    private List<Answer> answers;

    public Mentorship() {
        this.answers = new ArrayList<>();
        this.startDate = new Date();
    }

    public String getStartDate() {
        return DateUtil.format(this.startDate, DateUtil.HOURS_PATTERN);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return DateUtil.format(this.endDate, DateUtil.HOURS_PATTERN);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPerformedDate(Date performedDate) {
        this.performedDate = performedDate;
    }

    public String getPerformedDate() {
        return DateUtil.format(this.performedDate, DateUtil.NORMAL_PATTERN);
    }

    public Tutored getTutored() {
        return tutored;
    }

    public void setTutored(Tutored tutored) {
        this.tutored = tutored;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public HealthFacility getHealthFacility() {
        return healthFacility;
    }

    public void setHealthFacility(HealthFacility healthFacility) {
        this.healthFacility = healthFacility;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Answer> getAnswers() {
        return Collections.unmodifiableList(this.answers);
    }

    public void addAnswer(Answer answer) {

        for (Answer oldAnswer : this.answers) {
            if (oldAnswer.getQuestion().getUuid().equals(answer.getQuestion().getUuid())) {
                oldAnswer.setValue(answer.getValue());
                return;
            }
        }

        this.answers.add(answer);
    }
}
