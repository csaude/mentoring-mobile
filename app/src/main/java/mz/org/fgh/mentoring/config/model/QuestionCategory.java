package mz.org.fgh.mentoring.config.model;

public enum QuestionCategory {

    ACCURACY(""),

    PUNCTUALITY(""),

    TOTALITY(""),

    PRECISION(""),

    RELIABILITY(""),

    INTEGRITY(""),

    CONFIDENTIALITY(""),

    PREPARATION(""),

    PROCEDURE(""),

    REGISTRATION_AND_MANAGEMENT(""),

    IMPREGNATION(""),

    PACKING(""),

    PATIENT_REFERENCE_FOR_TESTING("Referência de Pacientes para Testagem"),

    PRE_TEST_COUNSELING("Aconselhamento Pré-Teste"),

    POST_TEST_COUNSELING("Realização do Teste Rápido"),

    RAPID_TEST_EXECUTION("Aconselhamento Pós-Teste"),

    FAMILY_SCREENING_AND_INDEX_CASE_APPROACH("Rastreio familiar e Abordagem de Caso Indice"),

    LINKAGE_CLINICAL_CARE("Ligação à Cuidados Clínicos"),

    COMUNICATION("Comunicação"),

    FEEDBACK_QUESTIONS(""),

    NA("");

    private String value;

    QuestionCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
