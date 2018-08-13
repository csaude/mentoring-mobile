package mz.org.fgh.mentoring.validator;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import mz.org.fgh.mentoring.R;

public class TextInputLayoutValidator {

    private Context context;

    private TextInputLayout[] textInputLayouts;

    public TextInputLayoutValidator(Context context) {
        this.context = context;
    }

    public void addTextInputLayputs(TextInputLayout... textInputLayouts) {
        this.textInputLayouts = textInputLayouts;
    }

    public boolean isValid() {

        for (TextInputLayout textInputLayout : textInputLayouts) {
            EditText editText = textInputLayout.getEditText();

            if (editText.getText().toString().isEmpty()) {
                textInputLayout.setErrorEnabled(Boolean.TRUE);
                textInputLayout.setError(context.getString(R.string.required_field));
                textInputLayout.requestFocus();
                return Boolean.FALSE;
            }

            textInputLayout.setErrorEnabled(Boolean.FALSE);
            textInputLayout.setError(null);
        }

        return Boolean.TRUE;
    }
}
