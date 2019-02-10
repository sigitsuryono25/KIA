package asia.sayateam.kiaadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.Services.NextIDIntentServices;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 026, 26-Aug-17.
 */

public class LoginActivity extends AppCompatActivity {

    EditText user, password;
    Button login;
    HashMap<String, String> params;
    SecurePreferences preferences;
    GetDataToServerHandler getDataToServerHandler;
    GetDataToServerHandler.GetDusun getDusun;
    TextInputLayout inputLayoutUsername, inputLayoutPassword;
    DeleteQuery deleteQuery;

    private boolean sentToSettings = false;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE};
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
            CheckPermission();
        }

        inputLayoutUsername = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        deleteQuery = new DeleteQuery(this);

        new Analytics(this, LoginActivity.class.getSimpleName().toLowerCase(),
                "Login Activity for KIA Admin",
                LoginActivity.class.getSimpleName().toLowerCase());

        login = (Button) findViewById(R.id.doLogin);

        getDataToServerHandler = new GetDataToServerHandler(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user.getText().toString().equalsIgnoreCase("") || password.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Isi Semua kolom lalu login", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        deleteQuery.DeletDusun();
                        deleteQuery.DeletJenisImunisasi();
                        getDusun = getDataToServerHandler.new GetDusun();
                        getDusun.execute();
                        Intent i = new Intent(getApplicationContext(), NextIDIntentServices.class);
                        startService(i);
                        new doLogin().execute(new String[]{
                                user.getText().toString(),
                                password.getText().toString()
                        });
                    } catch (Exception e) {
                        Toast.makeText(LoginActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    class doLogin extends AsyncTask<String, JSONObject, String> {
        ProgressDialog progressDialog;
        String result;
        JSONHander jsonHander = new JSONHander(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("melakukan verifikasi data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.ADMIN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.USER, strings[0]);
            params.put(ConfigureApps.PASSWORD, strings[1]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, "GET", params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject jsonObject;
            JSONArray detail_admin;
            super.onPostExecute(s);
            progressDialog.cancel();
            try {
                if (!s.equalsIgnoreCase(null)) {
                    jsonObject = new JSONObject(s);
                    if (jsonObject.getString("success_admin").equalsIgnoreCase("0")) {
                        detail_admin = jsonObject.getJSONArray("detail_admin");
                        for (int i = 0; i < detail_admin.length(); i++) {
                            JSONObject admin_detail = detail_admin.getJSONObject(i);
                            preferences.put("userid", admin_detail.getString("username"));
                            preferences.put("nama_admin", admin_detail.getString("nama_admin"));
                            preferences.put("kode_admin", admin_detail.getString("kode_admin"));
                        }
                        Toast.makeText(LoginActivity.this, "Selamat Datang, " + user.getText().toString(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainMenu.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Tidak ada data ditemukan", Toast.LENGTH_SHORT).show();
                        this.cancel(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void CheckPermission() {
        if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[2])) {
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Peringatan");
                builder.setMessage("Aplikasi ini membutuhkan izin untuk Mengakses Kamera dan Internal Memori");
                builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Peringatan");
                builder.setMessage("Aplikasi ini membutuhkan izin untuk Mengakses Kamera dan Internal Memori");
                builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
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
                //just request the permission
                ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissionsRequired[2])) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Peringatan");
                builder.setMessage("Aplikasi ini membutuhkan izin untuk Mengakses Kamera dan Internal Memori");
                builder.setPositiveButton("Izinkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(LoginActivity.this, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
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
                Toast.makeText(getBaseContext(), "Tidak Dapat Mengakses Kamera dan Internal Memori", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
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
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}
