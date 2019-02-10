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
import asia.sayateam.kiaclient.library.JSONHander;

/**
 * Created by Sigit Suryono on 012, 12-09-2017.
 */

public class Lain extends AppCompatActivity {
    JSONHander jsonHander;
    TextView lain_lain;
    SecurePreferences securePreferences;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lain);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Lain-Lain");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        securePreferences = new SecurePreferences(this, GetDataToServerHandler.PREF_NAME, PrefencesTambahData.ENCRYPT_KEY, true);
        jsonHander = new JSONHander(this);

        lain_lain = (TextView) findViewById(R.id.lain_lain);

        lain_lain.setText(securePreferences.getString(GetDataToServerHandler.LAIN_LAIN));
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
