package asia.sayateam.kiaclient.Imunisasi;

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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import asia.sayateam.kiaclient.Adapter.CustomRecylerViewAdapterStatusGizi;
import asia.sayateam.kiaclient.Adapter.RowItemStatusGizi;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.R;

/**
 * Created by Sigit Suryono on 05-Aug-17.
 */

public class StatusGizi extends AppCompatActivity {

    String[] semester1 = {"0", "1", "2", "3", "4", "5"};
    String[] semester2 = {"6", "7", "8", "9", "10", "11"};
    String[] semester3 = {"12", "13", "14", "15", "17", "17"};
    String[] semester4 = {"18", "19", "20", "21", "22", "23"};
    String[] semester5 = {"24", "25", "26", "27", "28", "29"};
    String[] semester6 = {"30", "31", "32", "33", "34", "53"};
    String[] semester7 = {"36", "37", "38", "39", "40", "41"};
    String[] semester8 = {"42", "43", "44", "45", "46", "47"};
    String[] semester9 = {"48", "49", "50", "51", "52", "53"};
    String[] semester10 = {"54", "55", "56", "57", "58", "59"};

    Toolbar toolbar;
    Spinner dropAnak;
    ListView recyclerView;
    SelectQuery selectQuery;
    List<String> primaryInfo;
    private List<RowItemStatusGizi> mListStatGizi;
    RowItemStatusGizi itemStatusGizi;
    CustomRecylerViewAdapterStatusGizi adapterStatusGizi;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    public AppBarLayout appbar;
    LinearLayout expand_activities_button;
    FrameLayout noData;
    Cursor c;
    int j = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_status_gizi);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Status Gizi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        selectQuery = new SelectQuery(StatusGizi.this);
        recyclerView = (ListView) findViewById(R.id.recycleView);
        dropAnak = (Spinner) findViewById(R.id.anakKe);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        noData = (FrameLayout) findViewById(R.id.noData);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        expand_activities_button = (LinearLayout) findViewById(R.id.expand_activities_button);

        dropAnak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (dropAnak.getSelectedItem().toString().equalsIgnoreCase("--Pilih Anak--")) {
                    recyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.GONE);
                } else {
                    String[] nama = dropAnak.getSelectedItem().toString().split("--");
                    c = selectQuery.getDetailAnakKe(nama[0].replaceAll("\\s+", ""));
                    while (c.moveToNext()) {
                        loadKeteranganGizi(c.getInt(0));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isVisible = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (dropAnak.getSelectedItem().toString().equals("--Pilih Anak--")) {
                    } else {
                        collapsingToolbarLayout.setTitle(dropAnak.getSelectedItem().toString());
                    }
                    isVisible = true;
                } else if (isVisible) {
                    collapsingToolbarLayout.setTitle(" ");
                    isVisible = false;
                }
            }
        });
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


        loadDataAnak();

        mListStatGizi = new ArrayList<RowItemStatusGizi>();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void loadDataAnak() {
        List<String> namaAnak = new ArrayList<>();
        c = selectQuery.getDataFor_Info_Anak();
        namaAnak.add("--Pilih Anak--");
        while (c.moveToNext()) {
            namaAnak.add(c.getString(0) + " -- " + c.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(StatusGizi.this, android.R.layout.simple_dropdown_item_1line, namaAnak);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dropAnak.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadKeteranganGizi(int id_anak) {
        primaryInfo = new ArrayList<>();
        c = selectQuery.getStatusGizi(id_anak);
        if (c.getCount() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            while (c.moveToNext()) {
                /*    public RowItemStatusGizi(
                String bulanPenimbangan,
                String bb,
                String normalOrNot,
                String statGizi,
                String keterangan,
                String semester,
                String bulan) {
*/
//                if(c.getString(2).equalsIgnoreCase("0000-00-00")){
//                    recyclerView.setVisibility(View.GONE);
//                    noData.setVisibility(View.VISIBLE);
//                }else {
                    itemStatusGizi =
                            new RowItemStatusGizi(
                                    c.getString(2),
                                    c.getString(3),
                                    c.getString(4),
                                    c.getString(5),
                                    c.getString(6),
                                    loadSemester(c.getString(1)),
                                    c.getString(1)
                            );
                    mListStatGizi.add(itemStatusGizi);
                    adapterStatusGizi = new CustomRecylerViewAdapterStatusGizi(StatusGizi.this,
                            R.layout.card_view_items_status_gizi, mListStatGizi);
                    recyclerView.setAdapter(adapterStatusGizi);
                    adapterStatusGizi.notifyDataSetChanged();
//                }
            }

        } else {
            recyclerView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
    }

    public String loadSemester(String bulan) {
        String semesterValue = null;
        for (int i = 0; i < semester1.length; i++) {
            if (bulan.equalsIgnoreCase(semester1[i])) {
                semesterValue = "1";
            }
        }
        for (int i = 0; i < semester2.length; i++) {
            if (bulan.equalsIgnoreCase(semester2[i])) {
                semesterValue = "2";
            }
        }
        for (int i = 0; i < semester3.length; i++) {
            if (bulan.equalsIgnoreCase(semester3[i])) {
                semesterValue = "3";
            }
        }
        for (int i = 0; i < semester4.length; i++) {
            if (bulan.equalsIgnoreCase(semester4[i])) {
                semesterValue = "4";
            }
        }
        for (int i = 0; i < semester5.length; i++) {
            if (bulan.equalsIgnoreCase(semester5[i])) {
                semesterValue = "5";
            }
        }
        for (int i = 0; i < semester6.length; i++) {
            if (bulan.equalsIgnoreCase(semester6[i])) {
                semesterValue = "6";
            }
        }
        for (int i = 0; i < semester7.length; i++) {
            if (bulan.equalsIgnoreCase(semester7[i])) {
                semesterValue = "7";
            }
        }
        for (int i = 0; i < semester8.length; i++) {
            if (bulan.equalsIgnoreCase(semester8[i])) {
                semesterValue = "8";
            }
        }
        for (int i = 0; i < semester9.length; i++) {
            if (bulan.equalsIgnoreCase(semester9[i])) {
                semesterValue = "9";
            }
        }
        for (int i = 0; i < semester10.length; i++) {
            if (bulan.equalsIgnoreCase(semester10[i])) {
                semesterValue = "10";
            }
        }
        return semesterValue;
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

}
