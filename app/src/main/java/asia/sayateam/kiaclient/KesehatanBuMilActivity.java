package asia.sayateam.kiaclient;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asia.sayateam.kiaclient.Adapter.CustomRecylerViewAdapter;
import asia.sayateam.kiaclient.Adapter.RowItem;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.KesehatanAnakFragment.PemberianVitaminFragment;

/**
 * Created by sigit on 29/07/17.
 */

public class KesehatanBuMilActivity extends AppCompatActivity {


    RecyclerView infoBumil;
    TextView namaBumil, id;
    CustomRecylerViewAdapter adapterBuMil;
    Toolbar toolbar;
    ImageButton icon;
    List<String> primary, secondary;
    SelectQuery selectQuery;
    Cursor cursor, cursors;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout image_name_container;
    LinearLayout expand_activities_button;
    RelativeLayout info_dasar;
    FrameLayout noData;
    TextView pilih;
    boolean expand = false;


//    String[] primaryInfo = {"Hari Pertama Haid Terakhir (HPHT), tanggal", "Lingkar Lengan Atas"};
//    String[] secondaryInfo = {"Senin, 22 Mei 2017", "45 cm"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_info_bumil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        selectQuery = new SelectQuery(KesehatanBuMilActivity.this);

        expand_activities_button = (LinearLayout) findViewById(R.id.expand_activities_button);
        info_dasar = (RelativeLayout) findViewById(R.id.info_dasar);
        infoBumil = (RecyclerView) findViewById(R.id.recycleView);

        namaBumil = (TextView) findViewById(R.id.nama_ibu_bumil);
        id = (TextView) findViewById(R.id.id_ibu);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);


        noData = (FrameLayout) findViewById(R.id.noData);

        icon = (ImageButton) findViewById(R.id.ibu_foto);


        image_name_container = (AppBarLayout) findViewById(R.id.image_name_container);
        image_name_container.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isVisible = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(namaBumil.getText().toString());
                    isVisible = true;
                    expand = true;
                } else if (isVisible) {
                    collapsingToolbarLayout.setTitle(" ");
                    isVisible = false;
                    expand = false;
                }
            }
        });


        expand_activities_button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (expand) {
                    image_name_container.setExpanded(true);
                    expand = false;
                } else {
                    image_name_container.setExpanded(false);
                    expand = true;
                }
            }
        });


        loadData();
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

    public void loadData() {

        primary = new ArrayList<>();
        secondary = new ArrayList<>();
        cursor = selectQuery.getKesIbuCondition();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if (i != 0 && i != 1) {
                    primary.add(cursor.getColumnName(i));
                }
            }

            while (cursor.moveToNext()) {
                cursors = selectQuery.getNamaIbu(cursor.getString(0));
                id.setText(cursor.getString(0));
                while (cursors.moveToNext()) {
                    namaBumil.setText(cursors.getString(0));
                }

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    if (i != 0 && i != 1) {
//                        if (cursor.getString(i).contains("-")) {
//                            secondary.add(dateFilter(cursor.getString(i)));
//                        } else {
                            secondary.add(cursor.getString(i));
//                        }
                    }
                }
            }
            adapterBuMil = new CustomRecylerViewAdapter(KesehatanBuMilActivity.this, primary, secondary, RowItem.KES_BUMIL);
            infoBumil.setAdapter(adapterBuMil);
            infoBumil.setHasFixedSize(true);
            infoBumil.setLayoutManager(new LinearLayoutManager(this));

        } else {

        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public String dateFilter(String date) {
        String dates;
        dates = new PemberianVitaminFragment().dateSpliter(date);
        return dates;
    }
}
