package asia.sayateam.kiaadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import asia.sayateam.kiaadmin.Adapter.AdapterMainMenu;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class MainMenu extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SecurePreferences preferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        preferences = new SecurePreferences(this, getPackageName().toLowerCase().toString(), PrefencesTambahData.ENCRYPT_KEY, true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Menu Utama", Color.WHITE));

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        AdapterMainMenu adapterMainMenu = new AdapterMainMenu(MainMenu.this);
        recyclerView.setAdapter(adapterMainMenu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadDataFromPreferences();
    }

    public void loadDataFromPreferences() {
        TextView admin = (TextView) findViewById(R.id.admin);
        admin.setText(preferences.getString("nama_admin") + " (" + preferences.getString("kode_admin") + ")");
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Logout ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preferences.clear();
                        try {

                            DeleteQuery deleteQuery = new DeleteQuery(MainMenu.this);
                            deleteQuery.DeletDataPemilik();
                            deleteQuery.DeletDataIbu();
                            deleteQuery.DeletDataAnak();
                            deleteQuery.DeletImun012();
                            deleteQuery.DeletImun1TahunPlus();
                            deleteQuery.DeletImunLain();
                            deleteQuery.DeletImunBIAS();
                            deleteQuery.DeletImunTambahan();
                            deleteQuery.DeletStatusGizi();
                            deleteQuery.DeletKesehatanAnak();
                            deleteQuery.DeletKesehatanIbu();
                        }catch (Exception e){

                        }
                        finish();
                        startActivity(new Intent(MainMenu.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .create().show();
    }
}

