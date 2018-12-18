package mz.org.fgh.mentoring.dto;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Willa on 12/27/18.
 */

public class SessionReportDataDTO {
    private Date dateUpdated;
    private String[] labels;
    private int[] targets;
    private int[] completed;
    private transient List<BarEntry> entries;

    public SessionReportDataDTO() {
    }

    public SessionReportDataDTO(String[] labels, int[] targets, int[] completed) {
        this.dateUpdated = new Date();
        this.labels = labels;
        this.targets = targets;
        this.completed = completed;
    }

    public SessionReportDataDTO(Date dateUpdated, String[] labels, int[] targets, int[] completed) {
        this.dateUpdated = dateUpdated;
        this.labels = labels;
        this.targets = targets;
        this.completed = completed;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(final String[] labels) {
        this.labels = labels;
    }

    public int[] getTargets() {
        return targets;
    }

    public void setTargets(@NonNull final int[] targets) {
        this.entries = null;
        this.targets = targets;
    }

    public int[] getCompleted() {
        return completed;
    }

    public void setCompleted(@NonNull final int[] completed) {
        this.entries = null;
        this.completed = completed;
    }

    @JsonIgnore
    public List<BarEntry> getEntries() {
        if(entries != null) return entries;

        if(completed.length != targets.length) {
            throw new IllegalStateException("The length of completed and targets should be equal");
        }

        entries = new ArrayList<>();
        float xValue = 0.5f;
        for(int index = 0; index < completed.length; index++, xValue = xValue + 1) {
            int target = targets[index];
            int complete = completed[index];
            entries.add(new BarEntry(xValue,
                    new float[] { (float)complete/target, (float)(target - complete)/target }));
        }
        return entries;
    }

}
