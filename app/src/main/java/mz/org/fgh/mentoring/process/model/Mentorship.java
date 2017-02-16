/*
 * Friends in Global Health - FGH © 2016
 */

package mz.org.fgh.mentoring.process.model;

import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.HealthFacility;
import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Mentorship extends GenericEntity {

    private String code;

//    private LocalDateTime startDate;
//
//    private LocalDateTime endDate;

//    private Tutor tutor;
//
//    private Tutored tutored;

    private Form form;

    private HealthFacility healthFacility;

    public Mentorship() {
    }
}
