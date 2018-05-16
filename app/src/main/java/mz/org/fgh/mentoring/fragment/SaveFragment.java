package mz.org.fgh.mentoring.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.adapter.QuestionAdapter;
import mz.org.fgh.mentoring.component.MentoringComponent;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.config.model.FormQuestion;
import mz.org.fgh.mentoring.config.model.QuestionCategory;
import mz.org.fgh.mentoring.event.EventType;
import mz.org.fgh.mentoring.event.ProcessEvent;
import mz.org.fgh.mentoring.infra.MentoringApplication;

public class SaveFragment extends BaseFragment {

    @BindView(R.id.feedback_questions)
    ListView questionsList;

    @Inject
    EventBus eventBus;

    private List<FormQuestion> formQuestions;

    @Override
    public int getResourceId() {
        return R.layout.fragment_save;
    }

    @Override
    public void onCreateView() {
        MentoringComponent component = application.getMentoringComponent();
        component.inject(this);

        Bundle bundle = getArguments();
        Form form = (Form) bundle.get("form");
        QuestionCategory questionCategory = (QuestionCategory) bundle.get("category");

        formQuestions = form.getFormQuestionsByCategory(questionCategory);

        QuestionAdapter adapter = new QuestionAdapter(getActivity(), formQuestions);
        adapter.setComponent(component);
        questionsList.setAdapter(adapter);
    }

    @OnClick(R.id.save_btn)
    public void onClickSave() {

        if (!isValid()) {
            Snackbar.make(getView(), getString(R.string.all_questions_must_be_answered), Snackbar.LENGTH_SHORT).show();
            return;
        }

        ProcessEvent processEvent = new ProcessEvent(EventType.SUBMIT);
        eventBus.post(processEvent);
    }

    private boolean isValid() {

        for (FormQuestion formQuestion : formQuestions) {
            if (formQuestion.getAnswer() == null) {
                return false;
            }
        }

        return true;
    }
}
