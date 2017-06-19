package mz.org.fgh.mentoring.model;

/**
 * Created by Stélio Moiane on 6/19/17.
 */
public enum QuestionAnswer {

    COMPETENT("COMPETENTE"),

    UNSATISFATORY("NAO SATISFATRIO"),

    NON_APPICABLE("NA");


    private String value;

    QuestionAnswer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
