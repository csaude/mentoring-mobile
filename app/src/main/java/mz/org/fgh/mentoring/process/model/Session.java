package mz.org.fgh.mentoring.process.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Cabinet;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.model.GenericEntity;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by steliomo on 1/24/18.
 */

public class Session extends GenericEntity {

    public static final int FIRST_ITERACTION = 1;

    private Date startDate;

    private Date endDate;

    private Date performedDate;

    private SessionStatus status;

    private String reason;

    private Form form;

    private HealthFacility healthFacility;

    private List<Mentorship> mentorships;

    private Cabinet cabinet;

    public Session() {
        this.mentorships = new ArrayList<>();
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

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus() {

        if (this.form.getTarget() == this.mentorships.size()) {
            status = SessionStatus.COMPLETE;
            return;
        }

        this.status = SessionStatus.INCOMPLETE;
    }

    public void setStatus(SessionStatus sessionStatus) {
        this.status = sessionStatus;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public List<Mentorship> getMentorships() {
        return mentorships;
    }

    public Form getForm() {
        return this.form;
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

    public void addMentorship(Mentorship mentorship) {
        mentorships.add(mentorship);
    }

    public boolean isComplete() {

        if (form == null) {
            return false;
        }

        return mentorships.size() + FIRST_ITERACTION == form.getTarget();
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public Cabinet getCabinet() {
        return this.cabinet;
    }


    public int performedByIterationType(IterationType iterationType) {

        int performed = 0;

        for (Mentorship mentorship : this.getMentorships()) {
            if (iterationType.equals(mentorship.getIterationType())) {
                performed++;
            }
        }

        return performed;
    }
}
