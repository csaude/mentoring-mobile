package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.Form;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by Stélio Moiane on 5/30/17.
 */
public class FormAdapter extends BaseAdapter {

    private Context context;
    private List<Form> forms;

    public FormAdapter(final Context context, final List<Form> forms) {

        this.context = context;
        this.forms = forms;
    }

    @Override
    public int getCount() {
        return forms.size();
    }

    @Override
    public Form getItem(int position) {
        return forms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return forms.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        Form form = forms.get(position);

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_forms, parent, false);
        }

        TextView formName = (TextView) view.findViewById(R.id.form_name);
        formName.setText(form.getName());

        TextView formArea = (TextView) view.findViewById(R.id.form_area);
        formArea.setText(form.getProgrammaticArea().getName());

        TextView formCreation = (TextView) view.findViewById(R.id.form_creation);
        formCreation.setText("( " + DateUtil.format(form.getCreatedAt(), "dd/MM/yyyy HH:mm") + " )");

        return view;
    }
}
