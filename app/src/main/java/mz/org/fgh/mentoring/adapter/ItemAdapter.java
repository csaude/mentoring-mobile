package mz.org.fgh.mentoring.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mz.org.fgh.mentoring.R;
import mz.org.fgh.mentoring.model.MentoringItem;

/**
 * Created by Stélio Moiane on 10/19/16.
 */
public class ItemAdapter extends BaseAdapter {

    private Context context;
    private List<MentoringItem> items;

    public ItemAdapter(Context context, List<MentoringItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MentoringItem item = items.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = convertView;


        if (view == null) {
            view = inflater.inflate(R.layout.mentoring_list, parent, false);
        }

        ImageView image = (ImageView) view.findViewById(R.id.list_image);
        image.setImageResource(item.getImageId());

        TextView title = (TextView) view.findViewById(R.id.list_title);
        title.setText(item.getItemTitle());

        return view;
    }
}
