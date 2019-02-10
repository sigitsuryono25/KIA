package asia.sayateam.kiaadmin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import asia.sayateam.kiaadmin.Adapter.AdapterEditData;
import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuTambah;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

import static android.graphics.Color.WHITE;

/**
 * Created by Sigit Suryono on 027, 27-Aug-17.
 */

public class SubMenuEdit extends AppCompatActivity {

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

        AdapterEditData adapterEditData = new AdapterEditData(SubMenuEdit.this);
        recyclerView.setAdapter(adapterEditData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

