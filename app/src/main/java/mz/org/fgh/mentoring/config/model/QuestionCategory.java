package mz.org.fgh.mentoring.config.model;

public enum QuestionCategory {

    ACCURACY("Exatidão"),

    PUNCTUALITY("Pontualidade"),

    TOTALITY("Totalidade"),

    PRECISION("Precisão"),

    RELIABILITY("Confiabilidade"),

    INTEGRITY("Integridade"),

    CONFIDENTIALITY("Confidencialidade"),

    PREPARATION("Preparação"),

    PROCEDURE("Procedimentos"),

    REGISTRATION_AND_MANAGEMENT("Registo de informação"),

    IMPREGNATION("Impregnação"),

    PACKING("Empacotamento"),

    PATIENT_REFERENCE_FOR_TESTING("Referência de Pacientes para Testagem"),

    PRE_TEST_COUNSELING("Aconselhamento Pré-Teste"),

    POST_TEST_COUNSELING("Realização do Teste Rápido"),

    RAPID_TEST_EXECUTION("Aconselhamento Pós-Teste"),

    FAMILY_SCREENING_AND_INDEX_CASE_APPROACH("Rastreio familiar e Abordagem de Caso Indice"),

    LINKAGE_CLINICAL_CARE("Ligação à Cuidados Clínicos"),

    COMUNICATION("Comunicação"),

    FEEDBACK_QUESTIONS(""),

    AMANMESE_AND_FISICAL_EXERCISE("Anamnese e ex.físico"),

    NUTRITION("Nutrição"),

    TB_HIV("TB/HIV"),

    ITS_TRACKING("Rastreio de ITS"),

    WHO_STAGE("Estadio OMS"),

    LAB("Laboratório"),

    DIAGNOSTIC_HYPOTHESES_AND_CONDUCT("Hipóteses diagnósticas e conduta"),

    CLINICAL_FAILURE("Falência Terapêutica"),

    CTZ_INH("CTZ/INH"),

    TARV("TARV"),

    ADHERENCE_COUNSELING("Aconselhamento e Adesão"),

    DIFERENTIATED_CARE_MODELS("Modelos Diferenciados de Cuidados"),

    ADVERSE_REACTIONS("Reações Adversas"),

    POSITIVE_PREVENTION("Prevenção Positiva"),

    GENDER_BASED_VIOLENCE("Violência Baseada no Género"),

    FOLLOW_UP_PLAN("Plano de Seguimento"),

    NA("");

    private String value;

    QuestionCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
