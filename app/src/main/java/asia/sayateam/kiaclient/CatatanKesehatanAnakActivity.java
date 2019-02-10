package asia.sayateam.kiaclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import asia.sayateam.kiaclient.KesehatanAnakFragment.PemberianVitaminFragment;

/**
 * Created by sigit on 29/07/17.
 */

public class CatatanKesehatanAnakActivity extends AppCompatActivity {

    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public RelativeLayout info_dasar;

    public CollapsingToolbarLayout collapsingToolbarLayout;
    public AppBarLayout appbar;
    LinearLayout expand_activities_button;
    public Spinner anakke, jenisVitamin;
    public FrameLayout noData;
    public TextView namaanak, id_anak;
    public ImageButton icon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_kesehatan_anak);

        /*Set Toolbar*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        namaanak = (TextView) findViewById(R.id.namaanak);
        id_anak = (TextView) findViewById(R.id.id_anak);
        info_dasar = (RelativeLayout) findViewById(R.id.info_dasar);
        icon = (ImageButton) findViewById(R.id.icon_balita);
        noData = (FrameLayout) findViewById(R.id.noData);

        jenisVitamin = (Spinner) findViewById(R.id.jenis_vitamin);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);
        anakke = (Spinner) findViewById(R.id.anakKe);

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
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));

                    isVisible = true;
                } else if (isVisible) {
                    collapsingToolbarLayout.setTitle(" ");
                    isVisible = false;
                }
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentLayout, new PemberianVitaminFragment())
                .commit();

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
