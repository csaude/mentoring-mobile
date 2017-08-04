package mz.org.fgh.mentoring.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.TutoredActivity;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 14-Nov-16.
 */

public class TutoredHelper {

    private EditText nameValue;
    private EditText surnameValue;
    private EditText phoneNumberValue;
    private Spinner careerValue;
    private Tutored tutored;
    private ImageView photo;

    public TutoredHelper(TutoredActivity activity) {
        nameValue = (EditText) activity.findViewById(R.id.tutored_name);
        surnameValue = (EditText) activity.findViewById(R.id.tutored_surname);
        phoneNumberValue = (EditText) activity.findViewById(R.id.tutored_phone_number);
        careerValue = (Spinner) activity.findViewById(R.id.tutored_carrer);
        tutored = new Tutored();
    }

    public Tutored getTutored() {
        tutored.setName(nameValue.getText().toString());
        tutored.setSurname(surnameValue.getText().toString());
        tutored.setPhoneNumber(phoneNumberValue.getText().toString());
        return tutored;
    }

    public void setTutored(Tutored tutored) {
        nameValue.setText(tutored.getName());
        surnameValue.setText(tutored.getSurname());
        phoneNumberValue.setText(tutored.getPhoneNumber());
        this.tutored = tutored;
    }

    public void getImage(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Bitmap smallBitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
        photo.setImageBitmap(smallBitmap);
        photo.setScaleType(ImageView.ScaleType.FIT_XY);
        photo.setTag(path);
    }
}
