package asia.sayateam.kiaadmin.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.EditDataInit;
import asia.sayateam.kiaadmin.InputDataInit;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuTambah;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class AdapterMainMenu extends RecyclerView.Adapter<RecyclerViewHolder> {
    //nama class RecyclerAdapter sama dengan nama method konstruktor

    AppCompatActivity activity;
    View view;
    Intent i;
    String[] nama_menu = {"Tambah Data", "Edit Data", "Input Data"};
    int[] icon = {R.mipmap.ic_tambah_data, R.mipmap.ic_edit, R.mipmap.ic_input};

    LayoutInflater layoutInflater;
    SecurePreferences securePreferences;

    public AdapterMainMenu(AppCompatActivity activity) {
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
                        i = new Intent(activity, SubMenuTambah.class);
                        securePreferences.put(AdapterSubMenuTambah.STATUS, "");
                        securePreferences.put(AdapterSubMenuTambah.STATUS, AdapterSubMenuTambah.TAMBAH);
                        activity.startActivity(i);
                        break;
                    case 1:
                        i = new Intent(activity, EditDataInit.class);
                        securePreferences.put(AdapterSubMenuTambah.STATUS, "");
                        securePreferences.put(AdapterSubMenuTambah.STATUS, AdapterSubMenuTambah.EDIT);
                        activity.startActivity(i);
                        break;
                    case 2:
                        i = new Intent(activity, InputDataInit.class);
                        securePreferences.put(AdapterSubMenuTambah.STATUS, AdapterSubMenuTambah.INPUT);
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

