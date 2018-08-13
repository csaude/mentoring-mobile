package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.config.model.PerformedSession;
import mz.org.fgh.mentoring.holder.PerformedSessionViewHolder;

public class ReportAdapter extends RecyclerView.Adapter<PerformedSessionViewHolder> {

    private List<PerformedSession> performedSessions;

    private Context context;

    public ReportAdapter(Context context, List<PerformedSession> performedSessions) {
        this.context = context;
        this.performedSessions = performedSessions;
    }

    @NonNull
    @Override
    public PerformedSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.report_result_item, parent, false);
        return new PerformedSessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerformedSessionViewHolder holder, int position) {
        holder.populate(performedSessions.get(position));
    }

    @Override
    public int getItemCount() {
        return performedSessions.size();
    }
}
