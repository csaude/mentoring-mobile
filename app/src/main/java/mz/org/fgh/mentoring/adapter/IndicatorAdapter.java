package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.process.model.Indicator;
import mz.org.fgh.mentoring.process.model.Mentorship;
import mz.org.fgh.mentoring.util.DateUtil;

/**
 * Created by steliomo on 10/31/17.
 */

public class IndicatorAdapter extends BaseAdapter {

    private Context context;

    private List<Indicator> indicators;

    public IndicatorAdapter(Context context, List<Indicator> indicators) {
        this.context = context;
        this.indicators = indicators;
    }

    @Override
    public int getCount() {
        return indicators.size();
    }

    @Override
    public Indicator getItem(int position) {
        return indicators.get(position);
    }

    @Override
    public long getItemId(int position) {
        return indicators.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Indicator indicator = indicators.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_indicators, parent, false);
        }

        TextView formName = (TextView) view.findViewById(R.id.indicator_form_name);
        formName.setText(indicator.getForm().getName());

        TextView healthFacility = (TextView) view.findViewById(R.id.health_facility);
        healthFacility.setText(indicator.getHealthFacility().getHealthFacility());

        TextView processDate = (TextView) view.findViewById(R.id.process_date);
        processDate.setText("( " + DateUtil.format(indicator.getCreatedAt(), "dd/MM/yyyy HH:mm") + " )");

        return view;
    }
}
