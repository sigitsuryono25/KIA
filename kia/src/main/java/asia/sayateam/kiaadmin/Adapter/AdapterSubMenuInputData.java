package asia.sayateam.kiaadmin.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.sayateam.kiaadmin.CatatanKesehatanIbuHamilActivity;
import asia.sayateam.kiaadmin.InputData.FragmentInputCatatanKesehatanAnak;
import asia.sayateam.kiaadmin.InputData.InputStatusGizi;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuImunisasi;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class AdapterSubMenuInputData extends RecyclerView.Adapter<RecyclerViewHolder> {
    //nama class RecyclerAdapter sama dengan nama method konstruktor

    AppCompatActivity activity;
    View view;
    Intent i;
    String[] nama_menu = {"Catatan Ibu Hamil", "Catatan Kesehatan Anak", "Status Gizi", "Imunisasi"};
    int[] icon = {R.mipmap.ic_bumil, R.mipmap.ic_kes_anak,
            R.mipmap.ic_gizi, R.mipmap.ic_imuni};

    LayoutInflater layoutInflater;


    public AdapterSubMenuInputData(AppCompatActivity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = layoutInflater.inflate(R.layout.list_item_menu, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, RecyclerViewHolder.VIEW_TYPE_MENU);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.titleList.setText(nama_menu[position]);
        holder.titleList.setTag(holder);
        holder.iconListItem.setImageResource(icon[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();

                switch (pos) {
                    case 0:
                        i = new Intent(activity, CatatanKesehatanIbuHamilActivity.class);
                        activity.startActivity(i);
                        activity.finish();
                        break;
                    case 1:
                        i = new Intent(activity, FragmentInputCatatanKesehatanAnak.class);
                        activity.startActivity(i);
                        activity.finish();
                        break;
                    case 2:
                        i = new Intent(activity, InputStatusGizi.class);
                        activity.startActivity(i);
                        activity.finish();
                        break;
                    case 3:
                        i = new Intent(activity, SubMenuImunisasi.class);
                        activity.startActivity(i);
                        activity.finish();
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return nama_menu.length;

    }
}

