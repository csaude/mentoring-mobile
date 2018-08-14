package mz.org.fgh.mentoring.event;

import mz.org.fgh.mentoring.config.model.Answer;

/**
 * Created by steliomo on 10/26/17.
 */

public class AnswerEvent {

    private Answer answer;

    public AnswerEvent(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }
}
