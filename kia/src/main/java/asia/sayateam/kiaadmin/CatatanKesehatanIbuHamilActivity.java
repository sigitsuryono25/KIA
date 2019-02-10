package asia.sayateam.kiaadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.InputData.FragmentBumilLastRecord;
import asia.sayateam.kiaadmin.InputData.FragmentInputCatatanBuMil;
import asia.sayateam.kiaadmin.InputData.FragmentInputKeteranganCatatanBuMil;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by sigit on 02/08/17.
 */

public class CatatanKesehatanIbuHamilActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView catatanBumilBottomMenu;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    boolean doubleBackToExit = false;
    SecurePreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_kesehatan_ibu_hamil);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        preferences.put(InputDataInit.FIRST_CALL, InputDataInit.FALSE);

        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Catatan Kes. ibu Hamil", Color.WHITE));

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, new FragmentBumilLastRecord());
        fragmentTransaction.commit();


        catatanBumilBottomMenu = (BottomNavigationView) findViewById(R.id.bottomNavigationCatatanBumil);
        catatanBumilBottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bumil_input:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, new FragmentInputCatatanBuMil());
                        fragmentTransaction.commit();
                        break;
                    case R.id.bumil_keterangan:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, new FragmentInputKeteranganCatatanBuMil());
                        fragmentTransaction.commit();
                        break;
                    case R.id.bumil_last_record:
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content, new FragmentBumilLastRecord());
                        fragmentTransaction.commit();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SubMenuInput.class);
        startActivity(intent);
        finish();
    }
}

