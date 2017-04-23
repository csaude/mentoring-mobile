package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.dao.CareerDAO;
import mz.org.fgh.mentoring.config.dao.CareerDAOImpl;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 15-Nov-16.
 */

public class TutoredItemAdapter extends BaseAdapter {
    private List<Tutored> tutoreds;
    private Context context;
    private CareerDAO careerDAO;

    public TutoredItemAdapter(Context context, List<Tutored> tutoreds) {
        this.context = context;
        this.tutoreds = tutoreds;
        careerDAO = new CareerDAOImpl(context);
    }

    @Override
    public int getCount() {
        return tutoreds.size();
    }

    @Override
    public Tutored getItem(int position) {
        return tutoreds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tutoreds.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tutored tutored = tutoreds.get(position);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_items_tutoreds, parent, false);
        }

        TextView name = (TextView) view.findViewById(R.id.item_name);
        name.setText(tutored.getFullName());

        TextView phoneNumber = (TextView) view.findViewById(R.id.item_phone);
        TextView carrerText = (TextView) view.findViewById(R.id.item_carrer);
        carrerText.setText(tutored.getCareer().getPosition());
        phoneNumber.setText("( " + tutored.getPhoneNumber() + " )");

        TextView tutoredIcon = (TextView) view.findViewById(R.id.tutored_icon);

        if (!tutored.getName().isEmpty()) {
            tutoredIcon.setText(tutored.getName().substring(0, 1));
        }

        return view;
    }
}
