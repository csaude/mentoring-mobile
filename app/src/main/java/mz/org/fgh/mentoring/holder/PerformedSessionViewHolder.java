package mz.org.fgh.mentoring.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.config.model.TextAnswer;

public class PerformedSessionViewHolder extends RecyclerView.ViewHolder {

    private final TextView distric;
    private final TextView healthFacility;
    private final TextView total;

    public PerformedSessionViewHolder(View view) {
        super(view);
        this.distric = view.findViewById(R.id.report_result_item_district);
        this.healthFacility = view.findViewById(R.id.report_result_item_health_facility);
        this.total = view.findViewById(R.id.report_result_item_total);
    }

    public void populate(PerformedSession performedSession) {
        distric.setText(performedSession.getDistrict());
        healthFacility.setText(performedSession.getHealthFacility());
        total.setText(performedSession.getTotalPerformed() + "");
    }
}
