package asia.sayateam.kiaadmin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;

import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sigitsuryono.kialibrary.ConnectionDetector;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Sigit Suryono on 11-Aug-17.
 */

public class InputDataInit extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    Toolbar toolbar;
    EditText searchValue;
    ImageView searchIcon;
    Button doScan;
    int i;
    private ZXingScannerView mScannerView;
    SecurePreferences preferences;
    public static final String FIRST_CALL = "firstCall";
    public static final String TRUE = "true";
    public static final String FALSE = "false";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_input_init);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Cari ID KIA", Color.WHITE));

        searchValue = (EditText) findViewById(R.id.searchValue);
        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        preferences.put(FIRST_CALL, TRUE);

        searchIcon = (ImageView) findViewById(R.id.doSearch);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchValue.getText().toString().equals("")) {
                    Toast.makeText(InputDataInit.this, "Tidak dapat mencari data. Isi Kolom terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    DeleteQuery deleteQuery = new DeleteQuery(InputDataInit.this);
                    deleteQuery.DeletDataPemilik();
                    deleteQuery.DeletDataIbu();
                    deleteQuery.DeletDataAnak();
                    deleteQuery.DeletImun012();
                    deleteQuery.DeletImun1TahunPlus();
                    deleteQuery.DeletImunLain();
                    deleteQuery.DeletImunBIAS();
                    deleteQuery.DeletImunTambahan();
                    deleteQuery.DeletStatusGizi();
                    deleteQuery.DeletKesehatanAnak();
                    deleteQuery.DeletKesehatanIbu();

                    GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(InputDataInit.this);
                    GetDataToServerHandler.GetPemilikKIA GetPemilikKIA = getDataToServerHandler.new GetPemilikKIA();
                    GetDataToServerHandler.GetAnak GetAnak = getDataToServerHandler.new GetAnak();
                    GetDataToServerHandler.GetIbu getIbu = getDataToServerHandler.new GetIbu();
                    if (new ConnectionDetector(InputDataInit.this).isConnectingToInternet()) {
                        GetPemilikKIA.execute(new String[]{searchValue.getText().toString()});
                        getIbu.execute(new String[]{searchValue.getText().toString()});
                        GetAnak.execute(new String[]{searchValue.getText().toString()});
                    } else {
                        Toast.makeText(InputDataInit.this, "Tidak ada koneksi Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        doScan = (Button) findViewById(R.id.doScan);

        doScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView = new ZXingScannerView(InputDataInit.this);   // Programmatically initialize the scanner view<br />
                setContentView(mScannerView);
                mScannerView.setResultHandler(InputDataInit.this); // Register ourselves as a handler for scan results.<br />
                mScannerView.startCamera();
            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            mScannerView.stopCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mScannerView.stopCamera();
            GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(InputDataInit.this);
            getDataToServerHandler.DismissProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mScannerView.stopCamera();
            GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(InputDataInit.this);
            getDataToServerHandler.DismissProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mScannerView.stopCamera();
            GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(InputDataInit.this);
            getDataToServerHandler.DismissProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResult(Result result) {
        if (new ConnectionDetector(InputDataInit.this).isConnectingToInternet()) {
            DeleteQuery deleteQuery = new DeleteQuery(InputDataInit.this);
            deleteQuery.DeletDataPemilik();
            deleteQuery.DeletDataIbu();
            deleteQuery.DeletDataAnak();
            deleteQuery.DeletImun012();
            deleteQuery.DeletImun1TahunPlus();
            deleteQuery.DeletImunLain();
            deleteQuery.DeletImunBIAS();
            deleteQuery.DeletImunTambahan();
            deleteQuery.DeletStatusGizi();
            deleteQuery.DeletKesehatanAnak();
            deleteQuery.DeletKesehatanIbu();

            GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(InputDataInit.this);
            GetDataToServerHandler.GetPemilikKIA GetPemilikKIA = getDataToServerHandler.new GetPemilikKIA();
            GetDataToServerHandler.GetAnak GetAnak = getDataToServerHandler.new GetAnak();
            GetDataToServerHandler.GetIbu getIbu = getDataToServerHandler.new GetIbu();
            if (new ConnectionDetector(InputDataInit.this).isConnectingToInternet()) {
                GetPemilikKIA.execute(new String[]{result.getText().toString()});
                getIbu.execute(new String[]{result.getText().toString()});
                GetAnak.execute(new String[]{result.getText().toString()});
            } else {
                Toast.makeText(InputDataInit.this, "Tidak ada koneksi Internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Tidak ada koneksi Internet", Toast.LENGTH_SHORT).show();
        }

    }
}
