/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.process.model;

/**
 * @author Stélio Moiane
 */

public enum Month {

    JANUARY("Janeiro"),

    FEBRUARY("Fevereiro"),

    MARCH("Março"),

    APRIL("Abril"),

    MAY("Maio"),

    JUNE("Junho"),

    JULY("Julho"),

    AUGUST("Agosto"),

    SEPTEMBER("Setembro"),

    OCTOBER("Outubro"),

    NOVEMBER("Novembro"),

    DECEMBER("Dezembro");

    private String description;

    Month(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
