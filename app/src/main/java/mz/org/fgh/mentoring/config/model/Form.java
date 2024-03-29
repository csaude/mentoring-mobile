/*
 * Friends in Global Health - FGH © 2016
 */
package mz.org.fgh.mentoring.config.model;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mz.org.fgh.mentoring.model.GenericEntity;

/**
 * Created by Stélio Moiane on 11/16/16.
 */
public class Form extends GenericEntity {

    private String code;

    private String name;

    private ProgrammaticArea programmaticArea;

    private String version;

    private FormType formType;

    private List<FormQuestion> formQuestions;

    private List<String> questionCategories;

    private Integer targetPatient;

    private Integer targetFile;

    public Form(String uuid, String name, ProgrammaticArea programmaticArea, String version, FormType formType) {
        this.setUuid(uuid);
        this.name = name;
        this.programmaticArea = programmaticArea;
        this.version = version;
        this.formType = formType;

        this.questionCategories = new ArrayList<>();
        this.formQuestions = new ArrayList<>();
    }

    public Form() {
        this.questionCategories = new ArrayList<>();
        this.formQuestions = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProgrammaticArea getProgrammaticArea() {
        return programmaticArea;
    }

    public String getVersion() {
        return version;
    }

    public FormType getFormType() {
        return formType;
    }

    public List<FormQuestion> getFormQuestionsByCategory(String questionCategory) {

        List<FormQuestion> formQuestions = new ArrayList<>();

        for (FormQuestion formQuestion : this.formQuestions) {

            if (questionCategory.equals(formQuestion.getQuestion().getQuestionsCategory().getCategory())) {
                formQuestions.add(formQuestion);
            }
        }

        return Collections.unmodifiableList(formQuestions);
    }

    public void addFormQuestion(FormQuestion formQuestion) {
        this.formQuestions.add(formQuestion);
    }

    public List<String> getQuestionCategories() {
        Collections.sort(this.formQuestions, getComparator());

        for (FormQuestion formQuestion : formQuestions) {

            if (!questionCategories.contains(formQuestion.getQuestion().getQuestionsCategory().getCategory())) {
                questionCategories.add(formQuestion.getQuestion().getQuestionsCategory().getCategory());
            }
        }

        questionCategories.remove(QuestionCategory.FEEDBACK_QUESTIONS);

        return questionCategories;
    }

    @NonNull
    private Comparator<FormQuestion> getComparator() {
        return new Comparator<FormQuestion>() {
            @Override
            public int compare(FormQuestion formQuestion1, FormQuestion formQuestion2) {
                return formQuestion1.getSequence().compareTo(formQuestion2.getSequence());
            }
        };
    }

    public int size() {

        if (FormType.MENTORING_CUSTOM.equals(this.getFormType())) {
            return this.getQuestionCategories().size() + 1;
        }

        return this.questionCategories.size();
    }

    public String getQuestionCategoryByPosition(int position) {

        if (FormType.MENTORING_CUSTOM.equals(this.getFormType())) {
            return this.questionCategories.get(position - 1);
        }

        return this.questionCategories.get(position);
    }

    public void clearAnswers() {
        for (FormQuestion formQuestion : formQuestions) {
            formQuestion.setAnswer(null);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public void clearQuestions() {
        this.formQuestions = new ArrayList<>();
    }

    public Integer getTargetPatient() {
        return targetPatient;
    }

    public void setTargetPatient(Integer targetPatient) {
        this.targetPatient = targetPatient;
    }

    public Integer getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(Integer targetFile) {
        this.targetFile = targetFile;
    }

    public int getTarget() {
        return this.targetPatient + this.targetFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Form form = (Form) o;

        return new EqualsBuilder()
                .append(code, form.code)
                .append(name, form.name)
                .append(version, form.version)
                .append(formType, form.formType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(name)
                .append(version)
                .append(formType)
                .toHashCode();
    }
}
