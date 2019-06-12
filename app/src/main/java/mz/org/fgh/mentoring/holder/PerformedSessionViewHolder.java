package mz.org.fgh.mentoring.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.PerformedSession;

public class PerformedSessionViewHolder extends RecyclerView.ViewHolder {

    private final TextView formName;
    private final TextView total;

    public PerformedSessionViewHolder(View view) {
        super(view);
        this.formName = view.findViewById(R.id.report_result_item_form);
        this.total = view.findViewById(R.id.report_result_item_total);
    }

    public void populate(PerformedSession performedSession) {
        formName.setText(performedSession.getFormName());
        total.setText(performedSession.getTotalPerformed() + "");
    }
}
