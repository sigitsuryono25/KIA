package asia.sayateam.kiaadmin.EditData;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.UpdateQuery;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuEdit;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by sigit on 02/08/17.
 */

public class EditAnakFragment extends AppCompatActivity {

    public static final String TAMBAH_ANAK = "tambah_anak";
    EditText id_kia, id_ibu, id_anak, nama_anak, tempat_lahir, tanggal_lahir,
            alamat, rt, rw, no_bpjs, anakkeValue;
    Spinner dusun;
    Spinner anakkeSpinner;
    ImageButton getTanggalLahirAnak;
    FloatingActionButton updateData;
    Toolbar toolbar;
    Cursor c;
    Calendar calendar;
    SelectQuery selectQuery;
    InsertQuery insertQuery;
    UpdateQuery updateQuery;
    int year, month, day;
    int anakke;
    ScrollView layout;
    String anak_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_data_anak);
        selectQuery = new SelectQuery(EditAnakFragment.this);
        insertQuery = new InsertQuery(this);
        updateQuery = new UpdateQuery(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(new TitleToobarCustom().TitleToobarCustom("Edit Data Anak", Color.WHITE));

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        anakkeValue = (EditText) findViewById(R.id.anakKeValue);
        anakkeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.tambahModelAnak.setAnakke(editable.toString());
            }
        });

        anakkeSpinner = (Spinner) findViewById(R.id.anakKe);
        layout = (ScrollView) findViewById(R.id.container);

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
                DatabaseVariable.tambahModelAnak.setId_anak(editable.toString());
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
                String dusunSelected = dusun.getSelectedItem().toString();
                c = selectQuery.getIDDusun(dusunSelected);
                while (c.moveToNext()) {
                    Log.i("ID DUSUN", c.getString(0));
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

                datePickerDialog = new DatePickerDialog(EditAnakFragment.this, onDateSetListener, year, month, day);
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


        loadDataSpinner();
        anakkeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (anakkeSpinner.getSelectedItem().toString().equalsIgnoreCase("--Pilih Anak--")) {
                    layout.setVisibility(View.GONE);
                } else {
                    loadData(anakkeSpinner.getSelectedItem().toString());
                    layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateData = (FloatingActionButton) findViewById(R.id.updateData);
        updateData.setVisibility(View.GONE);
//        updateData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    DatabaseVariable.tambahModelAnak.setId_anak(anak_id);
//                    DatabaseVariable.tambahModelAnak.setId_kia(id_kia.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setId_ibu(id_ibu.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setNama_anak(nama_anak.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setTanggal_lahir(tanggal_lahir.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setTempat_lahir(tempat_lahir.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setRt(rt.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setRw(rw.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setDusun(dusun.getSelectedItem().toString());
//                    DatabaseVariable.tambahModelAnak.setNo_bpjs(no_bpjs.getText().toString());
//                    DatabaseVariable.tambahModelAnak.setAnakke(anakkeValue.getText().toString());
//                    updateQuery.updateAnak();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
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
                new AlertDialog.Builder(this)
                        .setMessage("Apa yang akan anda lakukan ?")
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setPositiveButton("Kirim Data", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int a = updateQuery.updateAnak();
                                if (a == DatabaseVariable.TRUE_VALUE) {
                                    new EditDataAnakAsync(EditAnakFragment.this).execute();
                                } else {
                                    Toast.makeText(EditAnakFragment.this, "Terjadi Kesalahan Saat Mengubah Data", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .create().show();
                break;
        }
        return super.
                onOptionsItemSelected(item);

    }


    public void loadDataDusun() {
        List<String> dusunList = new ArrayList<>();
        ArrayAdapter adapter;

        c = selectQuery.getDusun();
        while (c.moveToNext()) {
            dusunList.add(c.getString(1));
        }

        adapter = new ArrayAdapter(EditAnakFragment.this, android.R.layout.simple_dropdown_item_1line, dusunList);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        dusun.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void loadIdKianIdIbu() {
        c = selectQuery.getDataFor_Info_Pemilik();
        while (c.moveToNext()) {
            id_kia.setText(c.getString(0));
            DatabaseVariable.tambahModelAnak.setId_kia(c.getString(0));
        }
        c.close();
        c = selectQuery.getDataFor_Info_Ibu();
        while (c.moveToNext()) {
            id_ibu.setText(c.getString(11));
            DatabaseVariable.tambahModelAnak.setId_ibu(c.getString(11));
        }
        c.close();
    }

    public void loadDataSpinner() {

        List<String> strings = new ArrayList<>();
        strings.add("--Pilih Anak--");

        c = selectQuery.getDataFor_Info_Anak();
        while (c.moveToNext()) {
            strings.add(c.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(EditAnakFragment.this, android.R.layout.simple_dropdown_item_1line, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        anakkeSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void loadData(String anakke) {
        c = selectQuery.getDetailAnakKe(anakke);
        while (c.moveToNext()) {
            id_anak.setText(c.getString(0));
            anak_id = c.getString(0);
            nama_anak.setText(c.getString(1));
            alamat.setText(c.getString(2));
            rt.setText(c.getString(3));
            rw.setText(c.getString(4));
            tempat_lahir.setText(c.getString(5));
            tanggal_lahir.setText(c.getString(6));

//            if (c.getString(7).equalsIgnoreCase("satu")) {
//                dusun.setSelection(0);
//            } else if (c.getString(7).equalsIgnoreCase("dua")) {
//                dusun.setSelection(1);
//            } else if (c.getString(7).equalsIgnoreCase("tiga")) {
//                dusun.setSelection(2);
//            } else if (c.getString(7).equalsIgnoreCase("empat")) {
//                dusun.setSelection(3);
//            } else if (c.getString(7).equalsIgnoreCase("lima")) {
//                dusun.setSelection(4);
//            } else if (c.getString(7).equalsIgnoreCase("enam")) {
//                dusun.setSelection(5);
//            }
            id_kia.setText(c.getString(9));
            id_ibu.setText(c.getString(10));
            anakkeValue.setText(c.getString(11));
            no_bpjs.setText(c.getString(7));
        }
    }

    public class EditDataAnakAsync extends AsyncTask<String, JSONObject, String> {
        String result;
        JSONObject jsonObject;
        JSONHander jsonHander;
        HashMap<String, String> params;
        Activity activity;
        ProgressDialog progressDialog;

        public EditDataAnakAsync(Activity activity) {
            jsonHander = new JSONHander(activity);
            this.activity = activity;
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
            params.put(ConfigureApps.TYPE, ConfigureApps.TAMBAH_ANAK);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK, DatabaseVariable.tambahModelAnak.getId_anak());
            params.put(ConfigureApps.NAMA_ANAK, DatabaseVariable.tambahModelAnak.getNama_anak());
            params.put(ConfigureApps.ALAMAT_ANAK, DatabaseVariable.tambahModelAnak.getAlamat());
            params.put(ConfigureApps.RT_ANAK, DatabaseVariable.tambahModelAnak.getRt());
            params.put(ConfigureApps.RW_ANAK, DatabaseVariable.tambahModelAnak.getRw());
            params.put(ConfigureApps.TEMPAT_LAHIR_ANAK, DatabaseVariable.tambahModelAnak.getTempat_lahir());
            params.put(ConfigureApps.TANGGAL_LAHIR_ANAK, DatabaseVariable.tambahModelAnak.getTanggal_lahir());
            params.put(ConfigureApps.NO_BPJS, DatabaseVariable.tambahModelAnak.getNo_bpjs());
            params.put(ConfigureApps.ID_DUSUN_ANAK, DatabaseVariable.tambahModelAnak.getDusun());
            params.put(ConfigureApps.ID_KIA_IBU, DatabaseVariable.tambahModelAnak.getId_kia());
            params.put(ConfigureApps.ID_IBU, DatabaseVariable.tambahModelAnak.getId_ibu());
            params.put(ConfigureApps.ANAK_KE, DatabaseVariable.tambahModelAnak.getAnakke());

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
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
//                        Intent i = new Intent(activity, SubMenuEdit.class);
//                        activity.startActivity(i);
//                        activity.finish();
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
