package asia.sayateam.kiaadmin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class CustomAdapterListView extends ArrayAdapter<ListViewModel> {

    Context context;

    public CustomAdapterListView(Context context, int resourceId, List<ListViewModel> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView tanggal;
        TextView NamaVaksin;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ListViewModel ListViewModel = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_list_view, null);
            holder = new ViewHolder();
            holder.NamaVaksin = (TextView) convertView.findViewById(R.id.header);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalAdapter);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.NamaVaksin.setText("Nama Vaksin\t\t: " + ListViewModel.getHeader());
        holder.tanggal.setText("Tanggal Pemberian\t: " + ListViewModel.getSequence() + "\t: " + ListViewModel.getChild());
        return convertView;
    }
}
