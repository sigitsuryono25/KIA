package asia.sayateam.kiaadmin.FragmentTambah;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuTambah;
import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.EditDataInit;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuTambah;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by sigit on 02/08/17.
 */

public class TambahAnakFragment extends AppCompatActivity {

    EditText id_kia, id_ibu, id_anak, nama_anak, tempat_lahir, tanggal_lahir,
            alamat, rt, rw, no_bpjs;
    Spinner dusun;
    ImageButton getTanggalLahirAnak;
    Toolbar toolbar;
    Cursor c;
    Calendar calendar;
    SelectQuery selectQuery;
    InsertQuery insertQuery;
    int year, month, day;
    int anakke;
    int anakInit = 1;
    int countAnak;
    Intent intent;
    String pengenal, idKia;
    SecurePreferences securePreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tambah_data_anak);
        selectQuery = new SelectQuery(TambahAnakFragment.this);
        insertQuery = new InsertQuery(this);
        securePreferences = new SecurePreferences(this, getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY, true);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Tambah Data", Color.WHITE));


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        id_kia = (EditText) findViewById(R.id.id_kia_value);
        id_ibu = (EditText) findViewById(R.id.id_ibu_value);

        id_anak = (EditText) findViewById(R.id.id_anak_value);
        id_anak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setId_anak(String.valueOf(new Random().nextInt()));
            }
        });

        nama_anak = (EditText) findViewById(R.id.nama_anak_value);
        nama_anak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setNama_anak(editable.toString());
            }
        });

        tempat_lahir = (EditText) findViewById(R.id.tempat_lahir_anak_value);
        tempat_lahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setTempat_lahir(editable.toString());
            }
        });

        tanggal_lahir = (EditText) findViewById(R.id.tanggal_lahir_anak_value);
        tanggal_lahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setTanggal_lahir(editable.toString());
            }
        });

        alamat = (EditText) findViewById(R.id.alamat_anak_value);
        alamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setAlamat(editable.toString());
            }
        });

        rt = (EditText) findViewById(R.id.rt_anak);
        rt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setRt(editable.toString());
            }
        });

        rw = (EditText) findViewById(R.id.rw_anak);
        rw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setRw(editable.toString());
            }
        });

        dusun = (Spinner) findViewById(R.id.dusun_anak_value);
        dusun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                c = selectQuery.getIDDusun(dusun.getSelectedItem().toString());
                while (c.moveToNext()) {
                    DatabaseVariable.tambahModelAnak.setDusun(c.getString(0));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        no_bpjs = (EditText) findViewById(R.id.bpjs_anak_value);
        no_bpjs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setNo_bpjs(editable.toString());
            }
        });

        getTanggalLahirAnak = (ImageButton) findViewById(R.id.pickTanggalLahirAnak);
        getTanggalLahirAnak.setOnClickListener(new View.OnClickListener() {
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
                        DatabaseVariable.tambahModelAnak.setTanggal_lahir(String.valueOf(i) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                    }
                };

                datePickerDialog = new DatePickerDialog(TambahAnakFragment.this, onDateSetListener, year, month, day);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadIdKianIdIbu();
        loadDataDusun();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.finish:
                if (id_ibu.getText().toString().equalsIgnoreCase("") ||
                        id_kia.getText().toString().equalsIgnoreCase("") ||
                        id_anak.getText().toString().equalsIgnoreCase("") ||
                        nama_anak.getText().toString().equalsIgnoreCase("") ||
                        tempat_lahir.getText().toString().equalsIgnoreCase("") ||
                        tanggal_lahir.getText().toString().equalsIgnoreCase("") ||
                        alamat.getText().toString().equalsIgnoreCase("") ||
                        rt.getText().toString().equalsIgnoreCase("") ||
                        rw.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "Silahkan Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {


                    new AlertDialog.Builder(this)
                            .setMessage("Apa yang akan anda lakukan ?")
                            .setNegativeButton("Tambah Lagi Data Anak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseVariable.tambahModelAnak.setAnakke(String.valueOf(anakInit));
                                    int j = insertQuery.InsertDataInfoAnak();
                                    if (j == DatabaseVariable.TRUE_VALUE) {
                                        anakInit = countAnak;
                                        ++anakInit;
                                        id_anak.setText(idKia + anakInit);

                                        nama_anak.setText("");
                                        nama_anak.requestFocus();
                                        alamat.setText("");
                                        no_bpjs.setText("");
                                        rt.setText("");
                                        rw.setText("");
                                        tempat_lahir.setText("");
                                        tanggal_lahir.setText("");
                                    } else {
                                        Toast.makeText(TambahAnakFragment.this, "Terjadi kesalah saat memasukkan data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setPositiveButton("Kirim Data", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        if (anakInit == anakke) {
                                            DatabaseVariable.tambahModelAnak.setAnakke(String.valueOf(anakInit));
                                            int j = insertQuery.InsertDataInfoAnak();
                                            if (j == DatabaseVariable.TRUE_VALUE) {
                                                SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(TambahAnakFragment.this);
                                                SendDataToServerHandler.sendDataTambahToServer sendDataTambahToServer = sendDataToServerHandler.new sendDataTambahToServer("");
                                                sendDataTambahToServer.execute();
                                            } else {
                                                Toast.makeText(TambahAnakFragment.this, "Terjadi kesalah saat mengirimkan data", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(TambahAnakFragment.this, "Data anak yang dimasuk belum sesuai dengan jumlah anak", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(TambahAnakFragment.this);
                                        SendDataToServerHandler.sendDataTambahToServer sendDataTambahToServer = sendDataToServerHandler.new sendDataTambahToServer("");
                                        sendDataTambahToServer.execute();
                                    }

                                }
                            })
                            .create().show();
                }
                break;
            case android.R.id.home:
                Intent intent = new Intent(this, TambahIbuFragment.class);
                intent.putExtra(EditDataInit.PENGENAL, EditDataInit.EDIT);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.EDIT)) {
            Intent intent = new Intent(this, SubMenuTambah.class);
            startActivity(intent);
            finish();
        } else if (securePreferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.TAMBAH)) {
            Intent intent = new Intent(this, TambahIbuFragment.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void loadDataDusun() {
        List<String> dusunList = new ArrayList<>();
        ArrayAdapter adapter;

        c = selectQuery.getDusun();
        while (c.moveToNext()) {
            dusunList.add(c.getString(1));
        }

        adapter = new ArrayAdapter(TambahAnakFragment.this, android.R.layout.simple_dropdown_item_1line, dusunList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        dusun.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadIdKianIdIbu() {
        c = selectQuery.getDataFor_Info_Pemilik();
        while (c.moveToNext()) {
            id_kia.setText(c.getString(0));
            idKia = c.getString(0);

            anakke = c.getInt(12);
            id_anak.setEnabled(false);
            DatabaseVariable.tambahModelAnak.setId_kia(c.getString(0));
        }
        c.close();

        c = selectQuery.getJumlahAnak();
        if (c.getCount() > 0) {
            id_anak.setText(idKia + String.valueOf(c.getCount() + 1));
            countAnak = c.getCount();
        } else {
            id_anak.setText(idKia + String.valueOf(anakInit));
            countAnak = anakInit;
        }
        c = selectQuery.getDataFor_Info_Pemilik();
        String id_ibu_str = null;
        while (c.moveToNext()) {
            Log.i("id", c.getString(0));
            id_kia.setText(c.getString(0));
            id_ibu_str = c.getString(0);
        }

        id_ibu.setText(id_ibu_str.substring(0, 4) + "1" + id_ibu_str.substring(4));

        DatabaseVariable.tambahModelAnak.setId_ibu(id_ibu.getText().toString());
        c.close();
    }
}
