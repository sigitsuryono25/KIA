package asia.sayateam.kiaclient.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 29/07/17.
 */

public class CustomRecylerViewAdapterStatusGizi extends ArrayAdapter<RowItemStatusGizi> {
    Context context;

    public CustomRecylerViewAdapterStatusGizi(Context context, int resource, List<RowItemStatusGizi> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    private class ViewHolder {
        EditText bulanPenimbangan, bb, NormalOrNot, StatGizi, keterangan;
        TextView semesterdanbulan;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItemStatusGizi rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.card_view_items_status_gizi, null);
            holder = new ViewHolder();
            holder.bulanPenimbangan = convertView.findViewById(R.id.tgl_pemberian);
            holder.bb = convertView.findViewById(R.id.berat_badan_status_gizi);
            holder.NormalOrNot = convertView.findViewById(R.id.pilihNormalAtauTidak);
            holder.StatGizi = convertView.findViewById(R.id.pilihStatusGizi);
            holder.keterangan = convertView.findViewById(R.id.keteranganPerSemester);
            holder.semesterdanbulan = convertView.findViewById(R.id.semesterdanbulan);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.bulanPenimbangan.setText(rowItem.getBulanPenimbangan());
        holder.bb.setText(rowItem.getBb());
        holder.NormalOrNot.setText(getNormalOrNot(rowItem.getNormalOrNot()));
        holder.StatGizi.setText(rowItem.getStatGizi());
        holder.keterangan.setText(rowItem.getKeterangan());
        holder.semesterdanbulan.setText("Data semester ke - " + rowItem.getSemester() + " Bulan ke - " + rowItem.getBulan());
        return convertView;
    }

    public String getNormalOrNot(String value) {
        if (value.equalsIgnoreCase("0")) {
            return "Naik";
        } else {
            return "Turun";
        }
    }


}
