package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.activities.ListTutoredActivity;
import mz.org.fgh.mentoring.model.Tutored;

/**
 * Created by Eusebio Maposse on 15-Nov-16.
 */

public class TutoredItemAdapter extends BaseAdapter {
    private final List<Tutored> tutoreds;
    private final Context context;

    public TutoredItemAdapter(Context context, List<Tutored> tutoreds) {
        this.context=context;
        this.tutoreds = tutoreds;
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View newView = view;

        if(newView==null){
          newView =  layoutInflater.inflate(R.layout.list_items, viewGroup, false);
        }

        TextView name = (TextView) newView.findViewById(R.id.item_name);
        name.setText(tutored.getName().concat(" ").concat(tutored.getSurname()));
        TextView  phoneNumber  = (TextView) newView.findViewById(R.id.item_phone);
        phoneNumber.setText(tutored.getPhoneNumber());
        return newView;
    }
}
