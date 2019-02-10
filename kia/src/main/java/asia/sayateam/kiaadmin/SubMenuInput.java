package asia.sayateam.kiaadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuInputData;
import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class SubMenuInput extends AppCompatActivity {

    DeleteQuery deleteQuery;
    SelectQuery selectQuery;
    Toolbar toolbar;
    GetDataToServerHandler getDataToServerHandler;
    GetDataToServerHandler.GetImunisasi0_12 getImunisasi0_12;
    GetDataToServerHandler.GetImunisasi1TahunPlus getImunisasi1TahunPlus;
    GetDataToServerHandler.GetImunisasiLain getImunisasiLain;
    GetDataToServerHandler.GetBIAS getBIAS;
    GetDataToServerHandler.GetImunisasiTambahan getImunisasiTambahan;
    GetDataToServerHandler.GetStatusGizi getStatusGizi;
    GetDataToServerHandler.GetCatatanKesehatanAnak getCatatanKesehatanAnak;
    GetDataToServerHandler.GetCatatanKesBuMil getCatatanKesBuMil;
    GetDataToServerHandler.GetJenisImunisasi getJenisImunisasi;
    GetDataToServerHandler.GetKeteranganImunisasi getKeteranganImunisasi;
    GetDataToServerHandler.GetKeteranganGizi getKeteranganGizi;
    Cursor cursor;

    SecurePreferences preferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sub_menu_input_data);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Pilihan Menu Input", Color.WHITE));
        deleteQuery = new DeleteQuery(SubMenuInput.this);

        selectQuery = new SelectQuery(SubMenuInput.this);

        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        getDataToServerHandler = new GetDataToServerHandler(SubMenuInput.this);

        if(preferences.getString(InputDataInit.FIRST_CALL).equalsIgnoreCase(InputDataInit.TRUE)) {
            loadData();
        }else{

        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        AdapterSubMenuInputData adapterSubMenuInputData = new AdapterSubMenuInputData(this);
        recyclerView.setAdapter(adapterSubMenuInputData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadData() {
        Log.i("This", "Executed");
        getJenisImunisasi = getDataToServerHandler.new GetJenisImunisasi();
        getJenisImunisasi.execute();

        cursor = selectQuery.getDataFor_Info_Anak();
        while (cursor.moveToNext()) {


            getKeteranganImunisasi = getDataToServerHandler.new GetKeteranganImunisasi();
            getKeteranganImunisasi.execute(new String[]{cursor.getString(0)});
//
            getKeteranganGizi = getDataToServerHandler.new GetKeteranganGizi();
            getKeteranganGizi.execute(new String[]{cursor.getString(0)});

            getImunisasi0_12 = getDataToServerHandler.new GetImunisasi0_12();
            getImunisasi0_12.execute(new String[]{cursor.getString(0)});

            getImunisasi1TahunPlus = getDataToServerHandler.new GetImunisasi1TahunPlus();
            getImunisasi1TahunPlus.execute(new String[]{cursor.getString(0)});

            getImunisasiLain = getDataToServerHandler.new GetImunisasiLain();
            getImunisasiLain.execute(new String[]{cursor.getString(0)});

            getBIAS = getDataToServerHandler.new GetBIAS();
            getBIAS.execute(new String[]{cursor.getString(0)});

            getImunisasiTambahan = getDataToServerHandler.new GetImunisasiTambahan();
            getImunisasiTambahan.execute(new String[]{cursor.getString(0)});

            getStatusGizi = getDataToServerHandler.new GetStatusGizi();
            getStatusGizi.execute(new String[]{cursor.getString(0)});

            getCatatanKesehatanAnak = getDataToServerHandler.new GetCatatanKesehatanAnak();
            getCatatanKesehatanAnak.execute(new String[]{cursor.getString(0)});

        }

        cursor = null;

        cursor = selectQuery.getDataFor_Info_Ibu();
        while (cursor.moveToNext()) {
            getCatatanKesBuMil = getDataToServerHandler.new GetCatatanKesBuMil();
            getCatatanKesBuMil.execute(new String[]{cursor.getString(11)});
        }

        cursor.close();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SubMenuInput.this)
                .setMessage("Anda keluar dari proses penginput data dan kembali " +
                        "ke proses awal (input ID Kia dan lakukan penarikkan data). " +
                        "Yakin ingin melakukannya ?")
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            deleteQuery.DeletDataAnak();
                            deleteQuery.DeletImun012();
                            deleteQuery.DeletImun1TahunPlus();
                            deleteQuery.DeletImunTambahan();
                            deleteQuery.DeletImunLain();
                            deleteQuery.DeletKesehatanAnak();
                            deleteQuery.DeletImunBIAS();
                            deleteQuery.DeletDataPemilik();
                            deleteQuery.DeletDataIbu();
                            deleteQuery.DeletKesehatanIbu();
                            deleteQuery.DeletStatusGizi();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(SubMenuInput.this, InputDataInit.class));
                        finish();
                    }
                }).create().show();
    }
}
