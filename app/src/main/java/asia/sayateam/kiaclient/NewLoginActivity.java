package asia.sayateam.kiaclient;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;

import asia.sayateam.kiaclient.AsyncTask.GetDataToServerHandler;
import asia.sayateam.kiaclient.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaclient.config.SessionManager;
import asia.sayateam.kiaclient.library.ConnectionDetector;
import asia.sayateam.kiaclient.library.DialogAlert;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class NewLoginActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public final static int REQ_CODE_PICK_IMAGE = 1;
    ImageView scan;
    SessionManager sessionManager;
    DeleteQuery deleteQuery;
    private ZXingScannerView mScannerView;
    boolean preview = false;
    private SharedPreferences permissionStatus;

    private boolean sentToSettings = false;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
            CheckPermission();
        }

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.layout_activity_main);

        sessionManager = new SessionManager(this);
        deleteQuery = new DeleteQuery(NewLoginActivity.this);

        scan = (ImageView) findViewById(R.id.pindai);

        setTitle("Selamat Datang");
        deleteData();

        if (new ConnectionDetector(NewLoginActivity.this).isConnectingToInternet()) {
            Log.i("Connection", "Connection is available");
        } else {
            new DialogAlert(NewLoginActivity.this);
        }
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new ConnectionDetector(NewLoginActivity.this).isConnectingToInternet()) {
                    Log.i("Connection", "Connection is available");
                    mScannerView = new ZXingScannerView(NewLoginActivity.this);
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    setContentView(mScannerView);
                    mScannerView.setResultHandler(NewLoginActivity.this);
                    mScannerView.startCamera();
                    preview = true;
                } else {
                    new DialogAlert(NewLoginActivity.this);
                }

//                GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(NewLoginActivity.this);
//                GetDataToServerHandler.GetPemilikKIA GetPemilikKIA = getDataToServerHandler.new GetPemilikKIA();
//                GetDataToServerHandler.GetAnak GetAnak = getDataToServerHandler.new GetAnak();
//                GetDataToServerHandler.GetIbu getIbu = getDataToServerHandler.new GetIbu();
//                if (new ConnectionDetector(NewLoginActivity.this).isConnectingToInternet()) {
//                    Log.i("Connection", "Connection is available");
//                    GetPemilikKIA.execute(new String[]{"170900003"});
//                    getIbu.execute(new String[]{"170900003"});
//                    GetAnak.execute(new String[]{"170900003"});
//                } else {
//                    new DialogAlert(NewLoginActivity.this);
//                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        sessionManager.checkLogin();
    }

    public void onPause() {
        super.onPause();
        try {
            mScannerView.stopCamera();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteData() {
        try {
            deleteQuery.DeletDataAnak();
            deleteQuery.DeletImun012();
            deleteQuery.DeletImun1TahunPlus();
            deleteQuery.DeletImunTambahan();
            deleteQuery.DeletImunLain();
            deleteQuery.DeletKesehatanAnak();
            deleteQuery.DeletImunBIAS();
            deleteQuery.DeletDataPemilik();
            deleteQuery.DeletDataIbu();
            deleteQuery.DeletKesehatanIbu();
            deleteQuery.DeletStatusGizi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (preview) {
                mScannerView.stopCamera();
                preview = false;
                onStart();
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResult(Result result) {
        GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(NewLoginActivity.this);
        GetDataToServerHandler.GetPemilikKIA GetPemilikKIA = getDataToServerHandler.new GetPemilikKIA();
        GetDataToServerHandler.GetAnak GetAnak = getDataToServerHandler.new GetAnak();
        GetDataToServerHandler.GetIbu getIbu = getDataToServerHandler.new GetIbu();

        GetPemilikKIA.execute(new String[]{result.getText()});
        getIbu.execute(new String[]{result.getText()});
        GetAnak.execute(new String[]{result.getText()});
    }

    public void CheckPermission(){
        if(ActivityCompat.checkSelfPermission(NewLoginActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(NewLoginActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(NewLoginActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(NewLoginActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(NewLoginActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(NewLoginActivity.this,permissionsRequired[2])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoginActivity.this);
                builder.setTitle("Peringatan");
                builder.setMessage("Aplikasi ini membutuhkan izin untuk Mengakses Kamera dan Internal Memori");
                builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(NewLoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoginActivity.this);
                builder.setTitle("Peringatan");
                builder.setMessage("Aplikasi ini membutuhkan izin untuk Mengakses Kamera dan Internal Memori");
                builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(NewLoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }  else {
                //just request the permission
                ActivityCompat.requestPermissions(NewLoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0],true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]== PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(NewLoginActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(NewLoginActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(NewLoginActivity.this,permissionsRequired[2])){
                AlertDialog.Builder builder = new AlertDialog.Builder(NewLoginActivity.this);
                builder.setTitle("Peringatan");
                builder.setMessage("Aplikasi ini membutuhkan izin untuk Mengakses Kamera dan Internal Memori");
                builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(NewLoginActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getBaseContext(),"Tidak Dapat Mengakses Kamera dan Internal Memori",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(NewLoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void proceedAfterPermission() {
       Log.i("PERMISSION STATUS", "We got All Permissions");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(NewLoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}
