package mz.org.fgh.mentoring.config.model;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public enum CareerType{

    HEALTH_TECHNICAL_ASSISTANT("Assistente Técnico de Saúde"),

    ADMINISTRATIVE_ASSISTANT("Auxiliar Administrativo"),

    HEALTH_TECHNICIAN_AUXILIARY("Auxiliar Técnico de Saúde"),

    HEALTH_SPECIALIST("Especialista de Saúde"),

    MEDICAL_PUBLIC_HEALTH("Médica de Saúde Pública"),

    MEDICAL_GENERALIST("Médica Generalista"),

    MEDICAL_HOSPITAL("Médica Hospitalar"),

    HEALTH_TECHNICIAN("Técnico de Saúde"),

    HEALTH_TECHNICIAL_SPECIALIST("Técnico Especializado de Saúde"),

    HEALTH_ASSOCIATE_DEGREE_N1("Técnico Superior de Saúde N1"),

    HEALTH_ASSOCIATE_DEGREE_N2("Técnico Superior de Saúde N2"),

    SERVICE_AGENT("Agente de Serviço");

    private String description;

    CareerType(final String description) {
        this.description = description;
    }



    @Override
    public String toString() {
        return description;
    }
}
