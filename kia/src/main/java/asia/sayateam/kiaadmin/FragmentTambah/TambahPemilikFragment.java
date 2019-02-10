package asia.sayateam.kiaadmin.FragmentTambah;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuTambah;
import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
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
import asia.sayateam.kiaadmin.SubMenuTambah;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by sigit on 01/08/17.
 */

public class TambahPemilikFragment extends AppCompatActivity {

    public static final String TANGGAL_LAHIR = "tgl_lahir";
    public static final String TANGGAL_REGISTRASI = "tgl_registrasi";

    Toolbar toolbar;
    EditText idPesertaValue, namaPesertaValue, tempatLahirPeserta,
            alamatPeserta, RT, RW, pekerjaanPeserta, noBPJS,
            jumlahAnak;
    EditText tanggalLahirPeserta, tanggalRegistrasi;
    Spinner pendidikanPeserta, idDusun, opsiBpjs;
    TextView idPesertaValueQr;
    ImageView qrCodeResult;
    Bitmap encoderResult;
    ImageButton getToday, getTanggalLahir;
    TextToImageEncoder encoder;
    Calendar calendar;
    Cursor c;
    public static String NowDate;
    Time now = new Time();

    SelectQuery selectQuery;
    InsertQuery insertQuery;
    UpdateQuery updateQuery;

    SecurePreferences preferences, securePreferences;
    GetDataToServerHandler getDataToServerHandler;

    String pendidikanPesertaSelected, dusunSelected;
    int year, month, day;

    LinearLayout bpjsContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tambah_data_pemilik);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Tambah Data", Color.WHITE));

        now.setToNow();
        NowDate = now.format("%Y-%m-%d");

//        encoder = new TextToImageEncoder();
//        getDataToServerHandler = new GetDataToServerHandler(TambahPemilikFragment.this);
        insertQuery = new InsertQuery(TambahPemilikFragment.this);
        selectQuery = new SelectQuery(TambahPemilikFragment.this);
        updateQuery = new UpdateQuery(TambahPemilikFragment.this);
        preferences = new SecurePreferences(TambahPemilikFragment.this, IDBroadcast.PREF_NAME, PrefencesTambahData.ENCRYPT_KEY, true);
        securePreferences = new SecurePreferences(TambahPemilikFragment.this, getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY, true);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        bpjsContainer = (LinearLayout) findViewById(R.id.bpjsContainer);


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
        tanggalRegistrasi.setText(NowDate);
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
                            new AlertDialog.Builder(TambahPemilikFragment.this)
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
                            new AlertDialog.Builder(TambahPemilikFragment.this)
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
//        loadDataOpsi();

//        opsiBpjs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(opsiBpjs.getSelectedItem().toString().equalsIgnoreCase("Ya")){
//                    bpjsContainer.setVisibility(View.VISIBLE);
//                }else if(opsiBpjs.getSelectedItem().toString().equalsIgnoreCase("Tidak")){
//                    bpjsContainer.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void loadDataOpsi(){
        List<String> opsi = new ArrayList<>();
        opsi.add("Ya");
        opsi.add("Tidak");

        ArrayAdapter arrayAdapter = new ArrayAdapter(TambahPemilikFragment.this, android.R.layout.simple_dropdown_item_1line, opsi);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        opsiBpjs.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.next:
                if (idPesertaValue.getText().toString().equalsIgnoreCase("") ||
                        namaPesertaValue.getText().toString().equalsIgnoreCase("") ||
                        tempatLahirPeserta.getText().toString().equalsIgnoreCase("") ||
                        tanggalLahirPeserta.getText().toString().equalsIgnoreCase("") ||
                        alamatPeserta.getText().toString().equalsIgnoreCase("") ||
                        RT.getText().toString().equalsIgnoreCase("") ||
                        RW.getText().toString().equalsIgnoreCase("") ||
                        pekerjaanPeserta.getText().toString().equalsIgnoreCase("") ||
                        noBPJS.getText().toString().equalsIgnoreCase("") ||
                        tanggalRegistrasi.getText().toString().equalsIgnoreCase("") ||
                        jumlahAnak.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "Silahkan Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.EDIT)) {
                        DatabaseVariable.tambahDataModelPemilik.setId_kia(idPesertaValue.getText().toString());
                        int i = updateQuery.updatePemilik();
                        saveImage(encoderResult);
                        if (i == DatabaseVariable.TRUE_VALUE) {
                            Intent intent = new Intent(this, TambahIbuFragment.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Terjadi kesalahan saat mengubah data", Toast.LENGTH_SHORT).show();
                        }
                    } else if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.TAMBAH)) {
                        int i = insertQuery.InsertDataInfoPemilik();
                        saveImage(encoderResult);
                        if (i == DatabaseVariable.TRUE_VALUE) {
                            Intent intent = new Intent(this, TambahIbuFragment.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Terjadi kesalahan saat mengubah data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.EDIT)) {
            Intent intent = new Intent(this, SubMenuEdit.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, SubMenuTambah.class);
            startActivity(intent);
            finish();
        }
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

                datePickerDialog = new DatePickerDialog(TambahPemilikFragment.this, onDateSetListener, year, month, day);
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

                datePickerDialog = new DatePickerDialog(TambahPemilikFragment.this, onDateSetListener, year, month, day);
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

        adapter = new ArrayAdapter(TambahPemilikFragment.this, android.R.layout.simple_dropdown_item_1line, dataPendidikan);
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
        adapter = new ArrayAdapter(TambahPemilikFragment.this, android.R.layout.simple_dropdown_item_1line, dusun);
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

                if (c.getString(7).equalsIgnoreCase("satu")) {
                    idDusun.setSelection(0);
                } else if (c.getString(7).equalsIgnoreCase("dua")) {
                    idDusun.setSelection(1);
                } else if (c.getString(7).equalsIgnoreCase("tiga")) {
                    idDusun.setSelection(2);
                } else if (c.getString(7).equalsIgnoreCase("empat")) {
                    idDusun.setSelection(3);
                } else if (c.getString(7).equalsIgnoreCase("lima")) {
                    idDusun.setSelection(4);
                } else if (c.getString(7).equalsIgnoreCase("enam")) {
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
                } else if (c.getString(10).equalsIgnoreCase("Strata 1 (S1)")) {
                    pendidikanPeserta.setSelection(3);
                } else if (c.getString(10).equalsIgnoreCase("Strata 2 (S2)")) {
                    pendidikanPeserta.setSelection(4);
                } else if (c.getString(10).equalsIgnoreCase("Strata 3 (S3)")) {
                    pendidikanPeserta.setSelection(5);
                }

                tanggalRegistrasi.setText(c.getString(11));
                jumlahAnak.setText(c.getString(12));
            }

        }
    }
}
