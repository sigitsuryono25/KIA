package asia.sayateam.kiaclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import asia.sayateam.kiaclient.AsyncTask.GetDataToServerHandler;
import asia.sayateam.kiaclient.config.PrefencesTambahData;
import asia.sayateam.kiaclient.config.SecurePreferences;

/**
 * Created by Sigit Suryono on 22-Aug-17.
 */

public class TentangAplikasi extends AppCompatActivity {


    TextView penjelasan;

    SecurePreferences securePreferences;

    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tentang);

         /*Set Toolbar*/
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tentang Aplikasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        securePreferences = new SecurePreferences(this, GetDataToServerHandler.PREF_NAME, PrefencesTambahData.ENCRYPT_KEY, true);
        penjelasan = (TextView) findViewById(R.id.penjelasan);

        penjelasan.setText(securePreferences.getString(GetDataToServerHandler.TENTANG));
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
