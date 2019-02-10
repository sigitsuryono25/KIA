package asia.sayateam.kiaadmin.EditData;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.UpdateQuery;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuEdit;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 015, 15-09-2017.
 */

public class EditDataIbu extends AppCompatActivity {

    EditText id_kia, id_ibu, nama_ibu, tempat_lahir, tanggal_lahir, rt, rw,
            pekerjaan, no_bpjs, alamat;
    Spinner pendidikanTerakhir, dusun;
    ImageButton getTanggalLahir;
    Toolbar toolbar;
    Cursor cursor;
    Calendar calendar;
    SelectQuery selectQuery;
    InsertQuery insertQuery;
    SecurePreferences securePreferences;
    TextView keteranganStep;
    RelativeLayout progressContainer;

    int year, month, day;

    UpdateQuery updateQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tambah_data_ibu);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Edit Data Ibu", Color.WHITE));


        insertQuery = new InsertQuery(this);
        selectQuery = new SelectQuery(EditDataIbu.this);
        updateQuery = new UpdateQuery(EditDataIbu.this);
        securePreferences = new SecurePreferences(this, getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY, true);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        progressContainer = (RelativeLayout) findViewById(R.id.progressContainer);
        progressContainer.setVisibility(View.INVISIBLE);
        keteranganStep = (TextView) findViewById(R.id.keteranganStep);
        keteranganStep.setVisibility(View.INVISIBLE);

        id_kia = (EditText) findViewById(R.id.id_kia_value_ibu);
        id_kia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setId_kia(editable.toString());
            }
        });

        id_ibu = (EditText) findViewById(R.id.id_ibu_value);
        id_ibu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setId_ibu(editable.toString());
            }
        });

        nama_ibu = (EditText) findViewById(R.id.nama_ibu_value);
        nama_ibu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setNama_ibu(editable.toString());
            }
        });

        tempat_lahir = (EditText) findViewById(R.id.tempat_lahir_ibu_value);
        tempat_lahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setTempat_lahir(editable.toString());
            }
        });

        tanggal_lahir = (EditText) findViewById(R.id.tanggal_lahir_ibu_value);
        tanggal_lahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setTanggal_lahir(editable.toString());
            }
        });

        alamat = (EditText) findViewById(R.id.alamat_ibu_value);
        alamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setAlamat(editable.toString());
            }
        });

        rt = (EditText) findViewById(R.id.rt_ibu);
        rt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setRt(editable.toString());
            }
        });

        rw = (EditText) findViewById(R.id.rw_ibu);
        rw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setRw(editable.toString());
            }
        });

        dusun = (Spinner) findViewById(R.id.dusun_ibu_value);
        dusun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cursor = selectQuery.getIDDusun(dusun.getSelectedItem().toString());
                while (cursor.moveToNext()) {
                    Log.i("ID DUSUN", cursor.getString(0));
                    DatabaseVariable.tambahDataModelIbu.setDusun(cursor.getString(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pekerjaan = (EditText) findViewById(R.id.pekerjaan_ibu_value);
        pekerjaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setPekerjaan(editable.toString());
            }
        });

        no_bpjs = (EditText) findViewById(R.id.bpjs_ibu_value);
        no_bpjs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelIbu.setNo_bpjs(editable.toString());
            }
        });

        pendidikanTerakhir = (Spinner) findViewById(R.id.pendidikan_terakhir_ibu);
        pendidikanTerakhir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DatabaseVariable.tambahDataModelIbu.setPendidikan(pendidikanTerakhir.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getTanggalLahir = (ImageButton) findViewById(R.id.pickTanggalLahirIbu);
        getTanggalLahir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener;
                DatePickerDialog datePickerDialog;

                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tanggal_lahir.setText(String.valueOf(i) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                    }
                };

                datePickerDialog = new DatePickerDialog(EditDataIbu.this, onDateSetListener, year, month, day);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tanggal_lahir.setText("");
                    }
                });

                datePickerDialog.show();
            }
        });


        loadDataPendidikan();
        loadDataDusun();
        loadData();
        loadIdKia();
    }

    public void loadIdKia() {
        cursor = selectQuery.getDataFor_Info_Pemilik();
        String id_ibu_str = null;
        while (cursor.moveToNext()) {
            Log.i("id", cursor.getString(0));
            id_kia.setText(cursor.getString(0));
            id_ibu_str = cursor.getString(0);
        }

        id_ibu.setText(id_ibu_str.substring(0, 4) + "1" + id_ibu_str.substring(4));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.next, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case R.id.next:
//                if (id_ibu.getText().toString().equalsIgnoreCase("") ||
//                        id_kia.getText().toString().equalsIgnoreCase("") ||
//                        tempat_lahir.getText().toString().equalsIgnoreCase("") ||
//                        tanggal_lahir.getText().toString().equalsIgnoreCase("") ||
//                        alamat.getText().toString().equalsIgnoreCase("") ||
//                        rt.getText().toString().equalsIgnoreCase("") ||
//                        rw.getText().toString().equalsIgnoreCase("") ||
//                        pekerjaan.getText().toString().equalsIgnoreCase("") ||
//                        no_bpjs.getText().toString().equalsIgnoreCase("") ||
//                        nama_ibu.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(this, "Silahkan Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equals(AdapterSubMenuTambah.EDIT)) {
//                        int i = updateQuery.updateIbu();
//                        if (i == DatabaseVariable.TRUE_VALUE) {
//                            Intent intent = new Intent(this, EditAnakFragment.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(this, "Terjadi kesalah saat memasukkan data", Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equals(AdapterSubMenuTambah.TAMBAH)) {
//                        int i = insertQuery.InsertDataInfoIbu();
//                        if (i == DatabaseVariable.TRUE_VALUE) {
//                            Intent intent = new Intent(this, TambahAnakFragment.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(this, "Terjadi kesalah saat memasukkan data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                break;
//            case android.R.id.home:
//                Intent intent = new Intent(this, TambahPemilikFragment.class);
//                intent.putExtra(EditDataInit.PENGENAL, EditDataInit.EDIT);
//                startActivity(intent);
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.finish:
                int i = updateQuery.updateIbu();
                if (i == DatabaseVariable.TRUE_VALUE) {
                    new EditDataIbuAsync(EditDataIbu.this).execute();
                } else {
                    Toast.makeText(this, "Terjadi kesalah saat memasukkan data", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, SubMenuEdit.class);
        startActivity(i);
        try {
            SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(this);
            sendDataToServerHandler.DismissDialog();
        } catch (Exception e) {

        }
        finish();
    }

    public void loadDataPendidikan() {
        List<String> dataPendidikan = new ArrayList<>();
        ArrayAdapter adapter;

        dataPendidikan.add("Tidak Sekolah");
        dataPendidikan.add("SD");
        dataPendidikan.add("SMP");
        dataPendidikan.add("SMA");
        dataPendidikan.add("Diploma 3 (D3)");
        dataPendidikan.add("Strata 1 (S1)");
        dataPendidikan.add("Strata 2 (S2)");
        dataPendidikan.add("Strata 3 (S3)");

        adapter = new ArrayAdapter(EditDataIbu.this, android.R.layout.simple_dropdown_item_1line, dataPendidikan);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        pendidikanTerakhir.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadDataDusun() {
        List<String> dusunList = new ArrayList<>();
        ArrayAdapter adapter;

        cursor = selectQuery.getDusun();
        while (cursor.moveToNext()) {
            dusunList.add(cursor.getString(1));
        }

        adapter = new ArrayAdapter(EditDataIbu.this, android.R.layout.simple_dropdown_item_1line, dusunList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        dusun.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadData() {
        cursor = selectQuery.getDataFor_Info_Ibu();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                nama_ibu.setText(cursor.getString(0));
                tempat_lahir.setText(cursor.getString(1));
                tanggal_lahir.setText(cursor.getString(2));
                alamat.setText(cursor.getString(3));
                rt.setText(cursor.getString(4));
                rw.setText(cursor.getString(5));

                if (cursor.getString(6).equalsIgnoreCase("satu")) {
                    dusun.setSelection(0);
                } else if (cursor.getString(6).equalsIgnoreCase("dua")) {
                    dusun.setSelection(1);
                } else if (cursor.getString(6).equalsIgnoreCase("tiga")) {
                    dusun.setSelection(2);
                } else if (cursor.getString(6).equalsIgnoreCase("empat")) {
                    dusun.setSelection(3);
                } else if (cursor.getString(6).equalsIgnoreCase("lima")) {
                    dusun.setSelection(4);
                } else if (cursor.getString(6).equalsIgnoreCase("enam")) {
                    dusun.setSelection(5);
                }
                pekerjaan.setText(cursor.getString(7));
                no_bpjs.setText(cursor.getString(8));

                if (cursor.getString(9).equalsIgnoreCase("SD")) {
                    pendidikanTerakhir.setSelection(0);
                } else if (cursor.getString(9).equalsIgnoreCase("SMP")) {
                    pendidikanTerakhir.setSelection(1);
                } else if (cursor.getString(9).equalsIgnoreCase("SMA")) {
                    pendidikanTerakhir.setSelection(2);
                } else if (cursor.getString(9).equalsIgnoreCase("Strata 1 (S1)")) {
                    pendidikanTerakhir.setSelection(3);
                } else if (cursor.getString(9).equalsIgnoreCase("Strata 2 (S2)")) {
                    pendidikanTerakhir.setSelection(4);
                } else if (cursor.getString(9).equalsIgnoreCase("Strata 3 (S3)")) {
                    pendidikanTerakhir.setSelection(5);
                }

                id_kia.setText(cursor.getString(10));
                id_ibu.setText(cursor.getString(11));
            }
            cursor.close();
        } else {
            nama_ibu.setText("");
            tempat_lahir.setText("");
            tanggal_lahir.setText("");
            alamat.setText("");
            rt.setText("");
            rw.setText("");
            dusun.setSelection(0);
            pekerjaan.setText("");
            no_bpjs.setText("");
            pendidikanTerakhir.setSelection(0);
        }
    }

    public class EditDataIbuAsync extends AsyncTask<String, JSONObject, String>{
        ProgressDialog progressDialog;
        Cursor c;
        SelectQuery  selectQuery;
        JSONHander jsonHander ;
        HashMap<String, String> params;
        String result;
        Activity context;
        JSONObject jsonObject;

        public EditDataIbuAsync(Activity context) {
            selectQuery = new SelectQuery(EditDataIbu.this);
            jsonHander = new JSONHander(EditDataIbu.this);
            this.context = context;
            params = new HashMap<>();
        }

        @Override
        protected void onPreExecute() {
            try {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mengirimkan data...");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(false);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            c = selectQuery.getDataFor_Info_Ibu();
            params = new HashMap<>();
            while (c.moveToNext()) {
                params.put(ConfigureApps.TYPE, ConfigureApps.TAMBAH_IBU);
                params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

                params.put(ConfigureApps.NAMA_IBU, c.getString(0));
                params.put(ConfigureApps.TEMPAT_LAHIR_IBU, c.getString(1));
                params.put(ConfigureApps.TANGGAL_LAHIR_IBU, c.getString(2));
                params.put(ConfigureApps.ALAMAT_IBU, c.getString(3));
                params.put(ConfigureApps.RT_IBU, c.getString(4));
                params.put(ConfigureApps.RW_IBU, c.getString(5));
                params.put(ConfigureApps.ID_DUSUN_IBU, c.getString(6));
                params.put(ConfigureApps.PEKERJAAN_IBU, c.getString(7));
                params.put(ConfigureApps.NO_BPJS_IBU, c.getString(8));
                params.put(ConfigureApps.PENDIDIKAN_IBU, c.getString(9));
                params.put(ConfigureApps.ID_KIA_IBU, c.getString(10));
                params.put(ConfigureApps.ID_IBU, c.getString(11));

                result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                if (!s.equals(null)) {
                    jsonObject = new JSONObject(s);

                    Log.i("s", s);

                    if (s.contains("\"success\":0")) {
                        progressDialog.cancel();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, SubMenuEdit.class);
                        context.startActivity(i);
                        context.finish();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

