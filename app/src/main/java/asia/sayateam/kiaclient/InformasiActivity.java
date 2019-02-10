package asia.sayateam.kiaclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import asia.sayateam.kiaclient.InformationFragement.AnakFragment;
import asia.sayateam.kiaclient.InformationFragement.OrangTuaFragment;
import asia.sayateam.kiaclient.InformationFragement.ProfileFragment;

/**
 * Created by sigit on 25/07/17.
 */

public class InformasiActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    public CollapsingToolbarLayout collapsingToolbarLayout;
    public AppBarLayout appbar;
    LinearLayout expand_activities_button;
    Toolbar toolbar;
    public TextView nama, id;
    public ImageButton icon;
    public LinearLayout containerPilihAnak;
    public Spinner anakke;
    public String namaAnak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_informasi);


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nama = (TextView) findViewById(R.id.nama);
        id = (TextView) findViewById(R.id.id);
        icon = (ImageButton) findViewById(R.id.foto);

        containerPilihAnak = (LinearLayout) findViewById(R.id.containerPilihAnak);
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
                    collapsingToolbarLayout.setTitle(nama.getText().toString());

                    isVisible = true;
                } else if (isVisible) {
                    collapsingToolbarLayout.setTitle(" ");
                    isVisible = false;
                }
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentLayout, new ProfileFragment());
        fragmentTransaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomMenuInformation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.profile) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragmentLayout, new ProfileFragment());
                    fragmentTransaction.commit();


                } else if (item.getItemId() == R.id.parents) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragmentLayout, new OrangTuaFragment());
                    fragmentTransaction.commit();
                } else if (item.getItemId() == R.id.anak) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.replace(R.id.fragmentLayout, new AnakFragment());
                    fragmentTransaction.commit();
                }
                return true;
            }
        });

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
