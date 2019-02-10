package asia.sayateam.kiaclient.Imunisasi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaclient.Adapter.CustomRecylerViewAdapter;
import asia.sayateam.kiaclient.Adapter.RowItem;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.KesehatanAnakFragment.PemberianVitaminFragment;
import asia.sayateam.kiaclient.R;
import asia.sayateam.kiaclient.SubMenuImunisasi;
import asia.sayateam.kiaclient.library.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class ImunisasiTambahan extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView noData;
    SelectQuery selectQuery;
    Cursor cursor;
    Intent intent;
    int id_anak;
    int i = 0;
    String anakke;
    CustomRecylerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_imunisasi_tambahan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().
                TitleToobarCustom("Imunisasi Vaksin Lain", Color.WHITE));

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);


        noData = (TextView) findViewById(R.id.noData);

        selectQuery = new SelectQuery(ImunisasiTambahan.this);

        intent = getIntent();
        try {
            anakke = intent.getStringExtra(SubMenuImunisasi.ANAKKE);
            Log.i("ANAK", anakke);
            cursor = selectQuery.getDetailAnakKe(anakke);
            while (cursor.moveToNext()) {
                id_anak = cursor.getInt(0);
            }
            Log.i("ID", String.valueOf(id_anak));
            loadData(id_anak);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadData(int id_anak) {
        List<String> headerData = new ArrayList<>();
        HashMap<String, List<String>> child = new HashMap<>();
        List<String> childData = new ArrayList<>();

        cursor = selectQuery.getImunTambahan(id_anak);
        if (cursor.getCount() == 0) {
            noData.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                headerData.add("Pemberian ke " + cursor.getString(2));
                childData.add("Jenis Vaksin\t\t: " + cursor.getString(1)
                        + "\nTanggal Pemberian\t:" + new PemberianVitaminFragment().dateSpliter(cursor.getString(3)));
                child.put(headerData.get(i), childData);
                i++;
            }
        }
        adapter = new CustomRecylerViewAdapter(ImunisasiTambahan.this, headerData, childData, RowItem.KES_BUMIL);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
