package asia.sayateam.kiaadmin.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.EditData.EditDataIbu;
import asia.sayateam.kiaadmin.EditData.EditDataPemilik;
import asia.sayateam.kiaadmin.EditDataInit;
import asia.sayateam.kiaadmin.EditData.EditAnakFragment;
import asia.sayateam.kiaadmin.FragmentTambah.TambahIbuFragment;
import asia.sayateam.kiaadmin.FragmentTambah.TambahPemilikFragment;
import asia.sayateam.kiaadmin.R;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class AdapterEditData extends RecyclerView.Adapter<RecyclerViewHolder> {
    //nama class RecyclerAdapter sama dengan nama method konstruktor

    AppCompatActivity activity;
    View view;
    Intent i;
    String[] nama_menu = {"Ubah Pemilik KIA", "Ubah Data Ibu", "Ubah Data Anak"};
    int[] icon = {R.mipmap.ic_edit_pemilik_kia, R.mipmap.ic_edit_ibu, R.mipmap.ic_edit_anak};

    LayoutInflater layoutInflater;
    SecurePreferences securePreferences;

    public AdapterEditData(AppCompatActivity activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        securePreferences = new SecurePreferences(activity, activity.getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY, true);

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
                        i = new Intent(activity, EditDataPemilik.class);
                        i.putExtra(EditDataInit.PENGENAL, EditDataInit.EDIT);
                        activity.startActivity(i);
                        break;
                    case 1:
                        i = new Intent(activity, EditDataIbu.class);
                        i.putExtra(EditDataInit.PENGENAL, EditDataInit.EDIT);
                        activity.startActivity(i);
                        break;
                    case 2:
                        i = new Intent(activity, EditAnakFragment.class);
                        i.putExtra(EditDataInit.PENGENAL, EditDataInit.EDIT);
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

