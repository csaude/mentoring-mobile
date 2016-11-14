package mz.org.fgh.mentoring.util;

import android.widget.EditText;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.TutoredActivity;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredUtil {
    private EditText nameValue;
    private EditText surnameValue;
    private EditText phoneNumberValue;
    private Tutored tutored;

    public TutoredUtil(TutoredActivity activity){
        nameValue = (EditText) activity.findViewById(R.id.tutored_name);
        surnameValue = (EditText) activity.findViewById(R.id.tutored_surname);
        phoneNumberValue = (EditText) activity.findViewById(R.id.tutored_phone_number);
        tutored = new Tutored();
    }
    public Tutored getTutored(){
        tutored.setName(nameValue.getText().toString());
        tutored.setSurname(surnameValue.getText().toString());
        tutored.setPhoneNumber(phoneNumberValue.getText().toString());
        return  tutored;
    }
    public void setTutored(Tutored tutored) {
        nameValue.setText(tutored.getName());
        surnameValue.setText(tutored.getSurname());
        phoneNumberValue.setText(tutored.getPhoneNumber());
        this.tutored=tutored;


    }



}
