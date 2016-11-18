package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.ListTutoredActivity;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.config.model.Career;
import mz.org.fgh.mentoring.dao.TutoredDao;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 15-Nov-16.
 */

public class TutoredItemAdapter extends BaseAdapter {
    private final List<Tutored> tutoreds;
    private final Context context;
    private ArrayList<Tutored> tutoredArrayList = new ArrayList<>();
    private CareerDAO careerDAO;

    public TutoredItemAdapter(Context context, List<Tutored> tutoreds) {
        this.context=context;
        this.tutoreds = tutoreds;
        careerDAO = new CareerDAOImpl(context);
    }

    @Override
    public int getCount() {
        return tutoreds.size();
    }

    @Override
    public Object getItem(int i) {
        return tutoreds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tutoreds.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Tutored tutored = tutoreds.get(i);
        Career career = careerDAO.findCareerById(tutored.getCarrerId());
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View newView = view;

        if(newView==null){
          newView =  layoutInflater.inflate(R.layout.list_items_tutoreds, viewGroup, false);
        }
        TextView name = (TextView) newView.findViewById(R.id.item_name);
        name.setText(tutored.getName().concat(" ").concat(tutored.getSurname()));
        TextView  phoneNumber  = (TextView) newView.findViewById(R.id.item_phone);
        TextView carrerText = (TextView) newView.findViewById(R.id.item_carrer);
        carrerText.setText(career.getPosition());
        phoneNumber.setText(tutored.getPhoneNumber());
        Button button = (Button) newView.findViewById(R.id.item_foto);

        if(!tutored.getName().isEmpty()) {
            button.setText(tutored.getName().substring(0, 1));
        }
        return newView;
    }
    public void filter(String text) {
        tutoredArrayList.clear();

        if (text.length() == 0) {
            tutoredArrayList.addAll(tutoreds);
        }
            for (Tutored t : tutoredArrayList) {
                if (t.getName().toLowerCase().contains(text)) {
                    tutoredArrayList.add(t);
                }
            }
            notifyDataSetChanged();
    }
}
