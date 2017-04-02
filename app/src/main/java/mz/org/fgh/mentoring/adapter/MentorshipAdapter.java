package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by Stélio Moiane on 4/2/17.
 */
public class MentorshipAdapter extends BaseAdapter {

    private Context context;
    private List<Mentorship> mentorships;

    public MentorshipAdapter(Context context, List<Mentorship> mentorships) {
        this.context = context;
        this.mentorships = mentorships;
    }

    @Override
    public int getCount() {
        return mentorships.size();
    }

    @Override
    public Mentorship getItem(int position) {
        return mentorships.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mentorships.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Mentorship mentorship = mentorships.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_mentorship, parent, false);
        }

        TextView formName = (TextView) view.findViewById(R.id.mentorship_form_name);
        formName.setText(mentorship.getForm().getName());

        TextView tutoredName = (TextView) view.findViewById(R.id.tutored_full_name);
        tutoredName.setText(mentorship.getTutored().getFullName());

        TextView healthFacility = (TextView) view.findViewById(R.id.health_facility);
        healthFacility.setText(mentorship.getHealthFacility().getHealthFacility());

        TextView processDate = (TextView) view.findViewById(R.id.process_date);
        processDate.setText("( " + DateUtil.format(mentorship.getCreatedAt(), "dd/MM/yyyy HH:mm") + " )");

        return view;
    }
}
