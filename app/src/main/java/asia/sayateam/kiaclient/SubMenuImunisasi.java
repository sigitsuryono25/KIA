package asia.sayateam.kiaclient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import asia.sayateam.kiaclient.Adapter.SubMenuAdapter;
import asia.sayateam.kiaclient.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.Imunisasi.BulanImunisasiAnakSekolah;
import asia.sayateam.kiaclient.Imunisasi.ImunisasiAnakDiatas1Tahun;
import asia.sayateam.kiaclient.Imunisasi.NewImun012;
import asia.sayateam.kiaclient.Imunisasi.NewImunisasiTambahan;
import asia.sayateam.kiaclient.Imunisasi.NewImunisasiVaksinLain;

/**
 * Created by Sigit Suryono on 20-Aug-17.
 */

public class SubMenuImunisasi extends AppCompatActivity {

    public static final String ANAKKE = "anakke";
    String anakkeValue;
    GridView gridView;
    Toolbar toolbar;
    Spinner pilihAnak;
    DatabaseHadler databaseHadler;
    SelectQuery selectQuery;
    Cursor c;
    Intent intent;

    String[] nama_menu = {"Imunisasi 0-12 Bulan",
            "Imunisasi Diatas 1 Tahun",
            "Bulan Imunisasi Anak Sekolah",
            "Imunisasi Tambahan",
            "Vaksin Lain",
            "Kembali"
    };

    public CollapsingToolbarLayout collapsingToolbarLayout;
    public AppBarLayout appbar;
    LinearLayout expand_activities_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sub_menu_imunisasi);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseHadler = new DatabaseHadler(SubMenuImunisasi.this);
        selectQuery = new SelectQuery(SubMenuImunisasi.this);



        gridView = (GridView) findViewById(R.id.gridMenu);
        pilihAnak = (Spinner) findViewById(R.id.anakKe);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        appbar = (AppBarLayout) findViewById(R.id.appbar);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isVisible = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Menu Imunisasi");

                    isVisible = true;
                } else if (isVisible) {
                    collapsingToolbarLayout.setTitle(" ");
                    isVisible = false;
                }
            }
        });


        pilihAnak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (pilihAnak.getSelectedItem().toString().equalsIgnoreCase("--Pilih Anak--")) {
                    gridView.setVisibility(View.GONE);
                } else {
                    String[] id = pilihAnak.getSelectedItem().toString().split("--");
                    anakkeValue = id[0].replaceAll("\\s+", "");
                    gridView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridView.setAdapter(new SubMenuAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        intent = new Intent(SubMenuImunisasi.this, NewImun012.class);
                        intent.putExtra(ANAKKE, anakkeValue);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(SubMenuImunisasi.this, ImunisasiAnakDiatas1Tahun.class);
                        intent.putExtra(ANAKKE, anakkeValue);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(SubMenuImunisasi.this, BulanImunisasiAnakSekolah.class);
                        intent.putExtra(ANAKKE, anakkeValue);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(SubMenuImunisasi.this, NewImunisasiTambahan.class);
                        intent.putExtra(ANAKKE, anakkeValue);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(SubMenuImunisasi.this, NewImunisasiVaksinLain.class);
                        intent.putExtra(ANAKKE, anakkeValue);
                        startActivity(intent);
                        break;
                    case 5:
                        NavUtils.navigateUpFromSameTask(SubMenuImunisasi.this);
                        break;
                }
            }
        });

        loadDataAnak();

        expand_activities_button = (LinearLayout) findViewById(R.id.expand_activities_button);
        expand_activities_button.setOnClickListener(new View.OnClickListener() {
            boolean expand = false;

            @Override
            public void onClick(View view) {
                if (expand) {
                    appbar.setExpanded(true);
                    expand = false;
                } else {
                    appbar.setExpanded(false);
                    expand = true;
                }
            }
        });
    }

    public void loadDataAnak() {
        List<String> anak = new ArrayList<>();
        ArrayAdapter adapter;

        String valueOfList;
        c = selectQuery.getDataFor_Info_Anak();
        anak.add("--Pilih Anak--");
        while (c.moveToNext()) {
            valueOfList = c.getString(0) + " -- " + c.getString(1);
            anak.add(valueOfList);
        }

        adapter = new ArrayAdapter(SubMenuImunisasi.this, android.R.layout.simple_dropdown_item_1line, anak);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        pilihAnak.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
