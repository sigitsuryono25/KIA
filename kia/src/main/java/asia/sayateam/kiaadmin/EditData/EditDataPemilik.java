package asia.sayateam.kiaadmin.EditData;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.UpdateQuery;
import asia.sayateam.kiaadmin.QrCodeGenerator.TextToImageEncoder;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.Services.IDBroadcast;
import asia.sayateam.kiaadmin.SubMenuEdit;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 015, 15-09-2017.
 */

public class EditDataPemilik extends AppCompatActivity {

    public static final String TANGGAL_LAHIR = "tgl_lahir";
    public static final String TANGGAL_REGISTRASI = "tgl_registrasi";

    Toolbar toolbar;
    EditText idPesertaValue, namaPesertaValue, tempatLahirPeserta,
            alamatPeserta, RT, RW, pekerjaanPeserta, noBPJS,
            jumlahAnak;
    EditText tanggalLahirPeserta, tanggalRegistrasi;
    Spinner pendidikanPeserta, idDusun;
    TextView idPesertaValueQr;
    TextView keteranganStep;
    ImageView qrCodeResult;
    Bitmap encoderResult;
    ImageButton getToday, getTanggalLahir;
    TextToImageEncoder encoder;
    Calendar calendar;
    Cursor c;
    RelativeLayout progressContainer;

    SelectQuery selectQuery;
    InsertQuery insertQuery;
    UpdateQuery updateQuery;

    SecurePreferences preferences, securePreferences;
    GetDataToServerHandler getDataToServerHandler;

    String pendidikanPesertaSelected, dusunSelected;
    int year, month, day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tambah_data_pemilik);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Edit Data Pemilik", Color.WHITE));

//        encoder = new TextToImageEncoder();
//        getDataToServerHandler = new GetDataToServerHandler(TambahPemilikFragment.this);
        insertQuery = new InsertQuery(EditDataPemilik.this);
        selectQuery = new SelectQuery(EditDataPemilik.this);
        updateQuery = new UpdateQuery(EditDataPemilik.this);
        preferences = new SecurePreferences(EditDataPemilik.this, IDBroadcast.PREF_NAME, PrefencesTambahData.ENCRYPT_KEY, true);
        securePreferences = new SecurePreferences(EditDataPemilik.this, getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY, true);


        progressContainer = (RelativeLayout) findViewById(R.id.progressContainer);
        progressContainer.setVisibility(View.INVISIBLE);
        keteranganStep = (TextView) findViewById(R.id.keteranganStep);
        keteranganStep.setVisibility(View.INVISIBLE);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        idPesertaValueQr = (TextView) findViewById(R.id.id_peserta_value_qr);
        qrCodeResult = (ImageView) findViewById(R.id.generatorResult);

        idPesertaValue = (EditText) findViewById(R.id.id_peserta_value);
        idPesertaValue.setEnabled(false);

        idPesertaValue.setText(preferences.getString(GetDataToServerHandler.NEXT_ID));
        qrCodeResult.setVisibility(View.GONE);
//        try {
//            encoderResult = encoder.textToImageEncode(preferences.getString(GetDataToServerHandler.NEXT_ID));
//            if (encoderResult != null) {
//                qrCodeResult.setImageBitmap(encoderResult);
//                qrCodeResult.setVisibility(View.VISIBLE);
//                idPesertaValueQr.setVisibility(View.VISIBLE);
//            }
//            idPesertaValueQr.setText("No. Peserta : " + preferences.getString(GetDataToServerHandler.NEXT_ID) + "\n");
        DatabaseVariable.tambahDataModelPemilik.setId_kia(preferences.getString(GetDataToServerHandler.NEXT_ID));
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        namaPesertaValue = (EditText) findViewById(R.id.nama_peserta_value);
        namaPesertaValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setNamaPesertaValue(editable.toString());
            }
        });

        tempatLahirPeserta = (EditText) findViewById(R.id.tempat_lahir_peserta);
        tempatLahirPeserta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setTempatLahirPeserta(editable.toString());
            }
        });

        tanggalLahirPeserta = (EditText) findViewById(R.id.tanggal_lahir_peserta);

        alamatPeserta = (EditText) findViewById(R.id.alamat_peserta);
        alamatPeserta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setAlamatPeserta(editable.toString());
            }
        });

        RT = (EditText) findViewById(R.id.rt);
        RT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setRT(editable.toString());
            }
        });

        RW = (EditText) findViewById(R.id.rw);
        RW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setRW(editable.toString());
            }
        });

        pekerjaanPeserta = (EditText) findViewById(R.id.pekerjaan_peserta);
        pekerjaanPeserta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setPekerjaanPeserta(editable.toString());
            }
        });

        noBPJS = (EditText) findViewById(R.id.no_bpjs_peserta);
        noBPJS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setNoBPJS(editable.toString());
            }
        });

        pendidikanPeserta = (Spinner) findViewById(R.id.pendidikan_peserta);
        pendidikanPeserta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pendidikanPesertaSelected = pendidikanPeserta.getSelectedItem().toString();
                DatabaseVariable.tambahDataModelPemilik.setPendidikan(pendidikanPesertaSelected);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        idDusun = (Spinner) findViewById(R.id.dusun_peserta);
        idDusun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dusunSelected = idDusun.getSelectedItem().toString();
                c = selectQuery.getIDDusun(dusunSelected);
                while (c.moveToNext()) {
                    Log.i("ID DUSUN", c.getString(0));
                    DatabaseVariable.tambahDataModelPemilik.setIdDusun(c.getString(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jumlahAnak = (EditText) findViewById(R.id.jumlah_anak_peserta);
        jumlahAnak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahDataModelPemilik.setJumlahAnak(editable.toString());
            }
        });

        tanggalRegistrasi = (EditText) findViewById(R.id.tgl_registrasi_peserta);
        tanggalRegistrasi.addTextChangedListener(new TextWatcher() {

            int length;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                length = tanggalRegistrasi.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    if (editable.length() == 4) {
                        if (editable.toString().equalsIgnoreCase(String.valueOf(year))) {
                            editable.append("-");
                        } else if ((Integer.parseInt(editable.subSequence(0, 4).toString()) > year) || (Integer.parseInt(editable.subSequence(0, 4).toString()) < year)) {
                            new AlertDialog.Builder(EditDataPemilik.this)
                                    .setMessage("Tahun harus saat ini")
                                    .setNegativeButton("Oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                        }
                    }
                    if (editable.length() == 7) {
                        editable.append("-");
                    }
                    if (editable.length() == 10) {
                        if ((Integer.parseInt(editable.subSequence(8, 10).toString()) < 0) || (Integer.parseInt(editable.subSequence(8, 10).toString()) > 31)) {
                            new AlertDialog.Builder(EditDataPemilik.this)
                                    .setMessage("Tanggal tidak valid")
                                    .setNegativeButton("Oke", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tanggalRegistrasi.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i) {
                    case KeyEvent.KEYCODE_DEL:
                        tanggalRegistrasi.setText("");
                        return true;
                }
                return false;
            }
        });


        getToday = (ImageButton) findViewById(R.id.getToday);
        getToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDatePicker(TANGGAL_REGISTRASI);

            }
        });

        getTanggalLahir = (ImageButton) findViewById(R.id.pickTanggalLahir);
        getTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDatePicker(TANGGAL_LAHIR);
            }
        });

        loadDataPendidikan();
        loadDataDusun();
        loadDataFromDatabase();
    }

    private void saveImage(Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/android/data/" + getPackageName() + "/");
        myDir.mkdirs();
        String extension = "png";
        String imageName =
                String.valueOf(idPesertaValue.getText().toString()) + "." + extension;
        File file = new File(myDir, imageName);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Log.i("status", "Gambar Berhasil Disimpan");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.next, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case R.id.next:
//                if (idPesertaValue.getText().toString().equalsIgnoreCase("") ||
//                        namaPesertaValue.getText().toString().equalsIgnoreCase("") ||
//                        tempatLahirPeserta.getText().toString().equalsIgnoreCase("") ||
//                        tanggalLahirPeserta.getText().toString().equalsIgnoreCase("") ||
//                        alamatPeserta.getText().toString().equalsIgnoreCase("") ||
//                        RT.getText().toString().equalsIgnoreCase("") ||
//                        RW.getText().toString().equalsIgnoreCase("") ||
//                        pekerjaanPeserta.getText().toString().equalsIgnoreCase("") ||
//                        noBPJS.getText().toString().equalsIgnoreCase("") ||
//                        tanggalRegistrasi.getText().toString().equalsIgnoreCase("") ||
//                        jumlahAnak.getText().toString().equalsIgnoreCase("")) {
//                    Toast.makeText(this, "Silahkan Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.EDIT)) {
//                        DatabaseVariable.tambahDataModelPemilik.setId_kia(idPesertaValue.getText().toString());
//                        int i = updateQuery.updatePemilik();
//                        saveImage(encoderResult);
//                        if (i == DatabaseVariable.TRUE_VALUE) {
//                            Intent intent = new Intent(this, TambahIbuFragment.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(this, "Terjadi kesalahan saat mengubah data", Toast.LENGTH_SHORT).show();
//                        }
//                    } else if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.TAMBAH)) {
//                        int i = insertQuery.InsertDataInfoPemilik();
//                        saveImage(encoderResult);
//                        if (i == DatabaseVariable.TRUE_VALUE) {
//                            Intent intent = new Intent(this, TambahIbuFragment.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(this, "Terjadi kesalahan saat mengubah data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.finish:
                DatabaseVariable.tambahDataModelPemilik.setId_kia(idPesertaValue.getText().toString());
                int i = updateQuery.updatePemilik();
                if (i == DatabaseVariable.TRUE_VALUE) {
                    new EditDataPemilikAsync(EditDataPemilik.this).execute();
                } else {
                    Toast.makeText(this, "Terjadi kesalahan saat mengubah data", Toast.LENGTH_SHORT).show();
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

    public void openDialogDatePicker(String params) {
        DatePickerDialog.OnDateSetListener onDateSetListener;
        DatePickerDialog datePickerDialog;

        switch (params) {
            case TANGGAL_LAHIR:
                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tanggalLahirPeserta.setText(String.valueOf(i) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                        DatabaseVariable.tambahDataModelPemilik.setTanggalLahirPeserta(String.valueOf(i) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                    }
                };

                datePickerDialog = new DatePickerDialog(EditDataPemilik.this, onDateSetListener, year, month, day);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                datePickerDialog.show();

                break;

            case TANGGAL_REGISTRASI:
                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tanggalRegistrasi.setText(String.valueOf(i) + ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));

                        DatabaseVariable.tambahDataModelPemilik.setTanggalRegistrasi(String.valueOf(i) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                    }
                };

                datePickerDialog = new DatePickerDialog(EditDataPemilik.this, onDateSetListener, year, month, day);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                datePickerDialog.show();

                break;
        }
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

        adapter = new ArrayAdapter(EditDataPemilik.this, android.R.layout.simple_dropdown_item_1line, dataPendidikan);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        pendidikanPeserta.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public void loadDataDusun() {
        List<String> dusun = new ArrayList<>();
        ArrayAdapter adapter;

        c = selectQuery.getDusun();
        while (c.moveToNext()) {
            dusun.add(c.getString(1));
        }
        adapter = new ArrayAdapter(EditDataPemilik.this, android.R.layout.simple_dropdown_item_1line, dusun);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        idDusun.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadDataFromDatabase() {
        c = selectQuery.getDataFor_Info_Pemilik();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                idPesertaValue.setText(c.getString(0));
                namaPesertaValue.setText(c.getString(1));
                tempatLahirPeserta.setText(c.getString(2));
                tanggalLahirPeserta.setText(c.getString(3));
                alamatPeserta.setText(c.getString(4));
                RT.setText(c.getString(5));
                RW.setText(c.getString(6));

                if (c.getString(6).equalsIgnoreCase("satu")) {
                    idDusun.setSelection(0);
                } else if (c.getString(6).equalsIgnoreCase("dua")) {
                    idDusun.setSelection(1);
                } else if (c.getString(6).equalsIgnoreCase("tiga")) {
                    idDusun.setSelection(2);
                } else if (c.getString(6).equalsIgnoreCase("empat")) {
                    idDusun.setSelection(3);
                } else if (c.getString(6).equalsIgnoreCase("lima")) {
                    idDusun.setSelection(4);
                } else if (c.getString(6).equalsIgnoreCase("enam")) {
                    idDusun.setSelection(5);
                }

                pekerjaanPeserta.setText(c.getString(8));
                noBPJS.setText(c.getString(9));

                if (c.getString(10).equalsIgnoreCase("SD")) {
                    pendidikanPeserta.setSelection(0);
                } else if (c.getString(10).equalsIgnoreCase("SMP")) {
                    pendidikanPeserta.setSelection(1);
                } else if (c.getString(10).equalsIgnoreCase("SMA")) {
                    pendidikanPeserta.setSelection(2);
                } else if (c.getString(10).equalsIgnoreCase("Diploma 3 (D3)")) {
                    pendidikanPeserta.setSelection(3);
                } else if (c.getString(10).equalsIgnoreCase("Strata 1 (S1)")) {
                    pendidikanPeserta.setSelection(4);
                } else if (c.getString(10).equalsIgnoreCase("Strata 2 (S2)")) {
                    pendidikanPeserta.setSelection(5);
                } else if (c.getString(10).equalsIgnoreCase("Strata 3 (S3)")) {
                    pendidikanPeserta.setSelection(6);
                }

                tanggalRegistrasi.setText(c.getString(11));
                jumlahAnak.setText(c.getString(12));
            }

        }
    }

    public class EditDataPemilikAsync extends AsyncTask<String, JSONObject, String> {
        String result, id_kia;
        HashMap<String, String> params;
        JSONObject jsonObject;
        JSONHander jsonHander;
        ProgressDialog progressDialog;
        Activity activity;
        SelectQuery selectQuery;
        Cursor c;

        public EditDataPemilikAsync(Activity activity) {
            this.activity = activity;
            selectQuery = new SelectQuery(activity);
            jsonHander = new JSONHander(activity);
            params = new HashMap<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressDialog = new ProgressDialog(activity);
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
            c = selectQuery.getDataFor_Info_Anak();
            String jumlahAnak = String.valueOf(c.getCount());
            Log.i("COUNT", String.valueOf(c.getCount()));

            c = selectQuery.getDataFor_Info_Pemilik();
            while (c.moveToNext()) {
                Log.d("id", c.getString(0));
                params.put(ConfigureApps.TYPE, ConfigureApps.TAMBAH_PEMILIK);
                params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);
                params.put(ConfigureApps.ID_KIA, c.getString(0));
                id_kia = c.getString(0);
                params.put(ConfigureApps.NAMA_PEMILIK, c.getString(1));
                params.put(ConfigureApps.TEMPAT_LAHIR, c.getString(2));
                params.put(ConfigureApps.TANGGAL_LAHIR, c.getString(3));
                params.put(ConfigureApps.ALAMAT, c.getString(4));
                params.put(ConfigureApps.RT, c.getString(5));
                params.put(ConfigureApps.RW, c.getString(6));
                params.put(ConfigureApps.ID_DUSUN, c.getString(7));
                params.put(ConfigureApps.PEKERJAAN, c.getString(8));
                params.put(ConfigureApps.NO_BPJS, c.getString(9));
                params.put(ConfigureApps.PENDIDIKAN, c.getString(10));
                params.put(ConfigureApps.TANGGAL_REG, c.getString(11));
                params.put(ConfigureApps.JUMLAH_ANAK, jumlahAnak);

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
                        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(activity, SubMenuEdit.class);
                        activity.startActivity(i);
                        activity.finish();
                    } else {
                        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
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
