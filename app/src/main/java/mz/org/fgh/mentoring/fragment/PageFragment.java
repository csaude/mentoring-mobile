package mz.org.fgh.mentoring.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.MentoringActivity;
import mz.org.fgh.mentoring.adapter.SwipeAdapter;
import mz.org.fgh.mentoring.config.model.Question;

public class PageFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView textView;
    private Button saveBtn;
    private Spinner answerValue;
    private MentoringActivity activity;
    private Bundle activityBundle;
    private Question question;

    public PageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.page_frgament, container, false);
        textView = (TextView) view.findViewById(R.id.text_view);
        answerValue = (Spinner) view.findViewById(R.id.answer_value);

        activity = (MentoringActivity) getActivity();
        activityBundle = activity.getBundle();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity, R.array.question_answers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answerValue.setAdapter(adapter);

        final Bundle bundle = getArguments();
        question = (Question) bundle.get(SwipeAdapter.QUESTION);
        boolean isLastPage = bundle.getBoolean(SwipeAdapter.LAST_PAGE);
        textView.setText(question.getQuestion());

        saveBtn = (Button) view.findViewById(R.id.save_btn);

        if (!isLastPage) {
            saveBtn.setVisibility(View.GONE);
        }

        saveBtn.setOnClickListener(this);
        answerValue.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        activity.submitProcess();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value = (String) parent.getItemAtPosition(position);

        if (activityBundle != null)
            activityBundle.putString(question.getCode(), value);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
