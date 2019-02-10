package asia.sayateam.kiaadmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuTambah;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

import static android.graphics.Color.WHITE;

/**
 * Created by Sigit Suryono on 027, 27-Aug-17.
 */

public class SubMenuTambah extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_imunisasi_anak);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Menu Edit", WHITE));

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        AdapterSubMenuTambah adapterSubMenuTambah = new AdapterSubMenuTambah(SubMenuTambah.this);
        recyclerView.setAdapter(adapterSubMenuTambah);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            DeleteQuery deleteQuery = new DeleteQuery(SubMenuTambah.this);
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
        } catch (Exception e) {

        }
    }

}

