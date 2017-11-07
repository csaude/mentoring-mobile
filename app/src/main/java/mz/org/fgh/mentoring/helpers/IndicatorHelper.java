package mz.org.fgh.mentoring.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mz.org.fgh.mentoring.config.model.Answer;
import mz.org.fgh.mentoring.process.model.Indicator;

/**
 * Created by steliomo on 11/3/17.
 */

public class IndicatorHelper {

    private Indicator indicator;

    private List<AnswerHelper> answerHelpers;

    public IndicatorHelper() {
        this.answerHelpers = new ArrayList<>();
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public List<AnswerHelper> getAnswerHelpers() {
        return this.answerHelpers;
    }

    public void prepareAnswerHelpers(List<Answer> answers) {
        for (Answer answer : answers) {
            this.answerHelpers.add(new AnswerHelper(answer.getQuestion().getUuid(), answer.getUuid(), answer.getValue()));
        }
    }
}
