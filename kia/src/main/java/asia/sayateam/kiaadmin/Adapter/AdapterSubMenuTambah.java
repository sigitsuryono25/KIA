package asia.sayateam.kiaadmin.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.EditDataInit;
import asia.sayateam.kiaadmin.FragmentTambah.TambahPemilikFragment;
import asia.sayateam.kiaadmin.R;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class AdapterSubMenuTambah extends RecyclerView.Adapter<RecyclerViewHolder> {
    //nama class RecyclerAdapter sama dengan nama method konstruktor

    AppCompatActivity activity;
    View view;
    Intent i;
    String[] nama_menu = {
            "Tambah Data KIA (Lengkap)",
            "Tambah Data Hanya Anak"
    };
    int[] icon = {R.mipmap.ic_tambah_data, R.mipmap.ic_tambah_anak};

    SecurePreferences securePreferences;

    public static final String STATUS = "status";
    public static final String TAMBAH = "tambah";
    public static final String TAMBAH_ANAK = "tambah";
    public static final String EDIT = "edit";
    public static final String INPUT = "input";

    LayoutInflater layoutInflater;


    public AdapterSubMenuTambah(AppCompatActivity activity) {
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
                        i = new Intent(activity, TambahPemilikFragment.class);
                        securePreferences.put(STATUS, "");
                        securePreferences.put(STATUS, TAMBAH);
                        activity.startActivity(i);
                        break;
                    case 1:
                        i = new Intent(activity, EditDataInit.class);
                        securePreferences.put(STATUS, "");
                        securePreferences.put(STATUS, TAMBAH_ANAK);
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

