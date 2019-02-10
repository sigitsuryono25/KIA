package asia.sayateam.kiaadmin.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.sayateam.kiaadmin.InputData.InputBulanImunisasiAnakSekolah;
import asia.sayateam.kiaadmin.InputData.InputImunisasiAnak0To12;
import asia.sayateam.kiaadmin.InputData.InputImunisasiAnakDiatas1Tahun;
import asia.sayateam.kiaadmin.InputData.NewImunisasiTambahan;
import asia.sayateam.kiaadmin.InputData.NewImunisasiVaksinLain;
import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class AdapterSubMenuImunisasi extends RecyclerView.Adapter<RecyclerViewHolder> {
    //nama class RecyclerAdapter sama dengan nama method konstruktor

    AppCompatActivity activity;
    View view;
    Intent i;
    String[] nama_menu = {
            "Imunisasi 0-12 Bulan",
            "Imunisasi Diatas 1 Tahun",
            "Bulan Imunisasi Anak Sekolah ( BIAS )",
            "Imunisasi Tambahan",
            "Pemberian Vaksin Lain"
    };
    int[] icon = {R.mipmap.ic_imun_0_12, R.mipmap.ic_1_tahun
            , R.mipmap.ic_bias, R.mipmap.ic_tambahan, R.mipmap.ic_vaksin_lain};

    LayoutInflater layoutInflater;


    public AdapterSubMenuImunisasi(AppCompatActivity activity) {
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
                        i = new Intent(activity, InputImunisasiAnak0To12.class);
                        activity.startActivity(i);
                        break;
                    case 1:
                        i = new Intent(activity, InputImunisasiAnakDiatas1Tahun.class);
                        activity.startActivity(i);
                        break;
                    case 2:
                        i = new Intent(activity, InputBulanImunisasiAnakSekolah.class);
                        activity.startActivity(i);
                        break;
                    case 3:
                        i = new Intent(activity, NewImunisasiTambahan.class);
                        activity.startActivity(i);
                        break;
                    case 4:
                        i = new Intent(activity, NewImunisasiVaksinLain.class);
                        activity.startActivity(i);
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

