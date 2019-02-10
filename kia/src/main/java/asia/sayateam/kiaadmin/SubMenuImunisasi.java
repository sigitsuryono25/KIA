package asia.sayateam.kiaadmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuImunisasi;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

import static android.graphics.Color.WHITE;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class SubMenuImunisasi extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SecurePreferences preferences;

    public static final String IS_SHOWING = "show";
    public static final String SHOW_FALSE = "false";
    public static final String SHOW_TRUE = "true";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan_imunisasi_anak);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Pilihan Menu Imunisasi", WHITE));


        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        preferences.put(InputDataInit.FIRST_CALL, InputDataInit.FALSE);


        AdapterSubMenuImunisasi adapterSubMenuImunisasi = new AdapterSubMenuImunisasi(SubMenuImunisasi.this);
        recyclerView.setAdapter(adapterSubMenuImunisasi);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (preferences.containsKey(IS_SHOWING) && preferences.getString(IS_SHOWING).equalsIgnoreCase(SHOW_FALSE)) {

        } else {
            showInstructions();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SubMenuInput.class);
        startActivity(intent);
        finish();
    }

    public void showInstructions() {
        CheckBox showThisTips;
        WebView webView;
        View view;

        LayoutInflater layoutInflater = getLayoutInflater();
        view = layoutInflater.inflate(R.layout.how_to_use, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(SubMenuImunisasi.this);
        builder.setView(view);

        showThisTips = view.findViewById(R.id.alwaysShowThis);
        webView = view.findViewById(R.id.showed);

        webView.loadUrl("file:///android_asset/how_to_use.html");

        showThisTips.setChecked(true);

        showThisTips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.put(IS_SHOWING, SHOW_FALSE);
            }
        });

        builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();

    }
}
