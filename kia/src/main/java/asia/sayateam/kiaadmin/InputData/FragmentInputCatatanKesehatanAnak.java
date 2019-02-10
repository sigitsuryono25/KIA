package asia.sayateam.kiaadmin.InputData;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaadmin.Adapter.ExpandaleListAdapter;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.InputDataInit;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuInput;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 04-Aug-17.
 */

public class FragmentInputCatatanKesehatanAnak extends AppCompatActivity {
    Cursor cursor;
    SelectQuery selectQuery;
    Spinner anakke, waktuPemberian, pemberianKe;
    ExpandableListView pemberianVitaminExpandable;
    ExpandaleListAdapter vitaminAdapter;
    TextView noData, id_anak_tambah, dosisTxt;
    ImageView setTanggal;
    EditText tanggalValue, keterangan;
    LinearLayout containerpemberian;
    List<String> header;
    HashMap<String, List<String>> child;
    String id_anak, waktuPemberianValue, pemberianKeValue, tanggalPemberian, dosis;
    int a = 1;
    FloatingActionButton add;
    Toolbar toolbar;
    public static final String SELECTION = "selection";
    public static final String dummyDate = "0000-00-00";
    SecurePreferences securePreferences, preferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_catatan_kesehatan_anak);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Catatan Kes. Anak", Color.WHITE));

        securePreferences = new SecurePreferences(this, "asia.sayateam.kiaadmin.selection", PrefencesTambahData.ENCRYPT_KEY, true);
        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        preferences.put(InputDataInit.FIRST_CALL, InputDataInit.FALSE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SubMenuInput.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        selectQuery = new SelectQuery(FragmentInputCatatanKesehatanAnak.this);

        pemberianVitaminExpandable = (ExpandableListView) findViewById(R.id.expandable_detail_pemberian_vitamin);
        containerpemberian = (LinearLayout) findViewById(R.id.containerpemberian);
        noData = (TextView) findViewById(R.id.noData);

        anakke = (Spinner) findViewById(R.id.anakKe);
        anakke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    containerpemberian.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
                } else {
                    securePreferences.put(SELECTION, String.valueOf(i));
                    loadDataDetailPemberianVitamin(anakke.getSelectedItem().toString());
                    containerpemberian.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add = (FloatingActionButton) findViewById(R.id.saveData);
        add.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addDataKesehatanAnak(FragmentInputCatatanKesehatanAnak.this);
            }
        });


        loadDataSpinner();
    }

    public void loadDataSpinner() {
        cursor = selectQuery.getDataFor_Info_Pemilik();

        List<String> anak = new ArrayList<>();
        anak.add("--pilih anak ke--");
        cursor = selectQuery.getDataFor_Info_Anak();
        while (cursor.moveToNext()) {
            anak.add(cursor.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(FragmentInputCatatanKesehatanAnak.this, android.R.layout.simple_dropdown_item_1line, anak);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        anakke.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        cursor.close();

    }


    public void SetSelection() {
        anakke.setSelection(Integer.parseInt(securePreferences.getString(SELECTION)));
    }


    public void loadDataDetailPemberianVitamin(String anak) {
        String[] waktu = {
                "6-11 Bulan",
                "12-23 Bulan",
                "24-35 Bulan",
                "36-47 Bulan",
                "48-59 Bulan"
        };
        header = new ArrayList<>();
        child = new HashMap<>();
        List<String> childDataBulan6till11 = new ArrayList<>();
        List<String> childDataBulan12till23 = new ArrayList<>();
        List<String> childDataBulan24till35 = new ArrayList<>();
        List<String> childDataBulan36till47 = new ArrayList<>();
        List<String> childDataBulan48till59 = new ArrayList<>();


        cursor = selectQuery.getDetailAnakKeKesehatan(anak);
        while (cursor.moveToNext()) {
            id_anak = cursor.getString(0);
        }

        //fetch data for 6-11
        a = 1;
        cursor = selectQuery.getKesAnakTanggal(waktu[0], id_anak, String.valueOf(a));
        while (cursor.moveToNext()) {
            if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                childDataBulan6till11.add("Tidak ada data");
                break;
            } else {
                childDataBulan6till11.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                childDataBulan6till11.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                childDataBulan6till11.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
            }
        }
        //fetch data for 12-23
        a = 1;
        while (a <= 2) {
            cursor = selectQuery.getKesAnakTanggal(waktu[1], id_anak, String.valueOf(a));

            while (cursor.moveToNext()) {
                if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                    childDataBulan12till23.add("Tidak ada data");
                    break;
                } else {
                    childDataBulan12till23.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                    childDataBulan12till23.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                    childDataBulan12till23.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                }
                Log.i("i", String.valueOf(a));
            }
            a++;
        }

        //fetch data for 24-35
        a = 1;
        while (a <= 2) {
            cursor = selectQuery.getKesAnakTanggal(waktu[2], id_anak, String.valueOf(a));
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                    childDataBulan24till35.add("Tidak ada data");
                    break;
                } else {
                    childDataBulan24till35.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                    childDataBulan24till35.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                    childDataBulan24till35.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                }
            }
            a++;
        }
        //fetch data for 36-47
        a = 1;
        while (a <= 2) {
            cursor = selectQuery.getKesAnakTanggal(waktu[3], id_anak, String.valueOf(a));
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                    childDataBulan36till47.add("Tidak ada data");
                    break;
                } else {
                    childDataBulan36till47.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                    childDataBulan36till47.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                    childDataBulan36till47.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                }
            }
            a++;
        }
        //fetch data for 48-59

        a = 1;
        while (a <= 2) {
            cursor = selectQuery.getKesAnakTanggal(waktu[4], id_anak, String.valueOf(a));
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                    childDataBulan48till59.add("Tidak ada data");
                    break;
                } else {
                    childDataBulan48till59.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                    childDataBulan48till59.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                    childDataBulan48till59.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                }
            }
            a++;
        }
        if (childDataBulan6till11.size() > 0 ||
                childDataBulan12till23.size() > 0 ||
                childDataBulan12till23.size() > 0 ||
                childDataBulan24till35.size() > 0 ||
                childDataBulan36till47.size() > 0 ||
                childDataBulan48till59.size() > 0) {
            noData.setVisibility(View.GONE);
            pemberianVitaminExpandable.setVisibility(View.VISIBLE);
            for (int i = 0; i < waktu.length; i++) {
                header.add(waktu[i]);
                if (i == 0) {
                    child.put(header.get(0), childDataBulan6till11);
                } else if (i == 1) {
                    child.put(header.get(1), childDataBulan12till23);
                } else if (i == 2) {
                    child.put(header.get(2), childDataBulan24till35);
                } else if (i == 3) {
                    child.put(header.get(3), childDataBulan36till47);
                } else if (i == 4) {
                    child.put(header.get(4), childDataBulan48till59);
                }
            }

            vitaminAdapter = new ExpandaleListAdapter(FragmentInputCatatanKesehatanAnak.this, header, child);
            pemberianVitaminExpandable.setAdapter(vitaminAdapter);
            for (int i = 0; i < waktu.length; i++) {
                pemberianVitaminExpandable.expandGroup(i);
            }
        } else {
            pemberianVitaminExpandable.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        cursor.close();
    }

    public String dateSpliter(String value) {
        String[] spliter = value.split("-");
        return spliter[2] + "-" + spliter[1] + "-" + spliter[0];
    }

    private class addDataKesehatanAnak {
        int year, month, day;
        Calendar calendar;

        public addDataKesehatanAnak(final Activity activity) {

            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_tambah_kesehatan_anak, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setView(view);
            id_anak_tambah = view.findViewById(R.id.id_anak);
            id_anak_tambah.setText("ID Anak : " + id_anak);
            dosisTxt = view.findViewById(R.id.dosis);
            keterangan = view.findViewById(R.id.keterangan);
            waktuPemberian = view.findViewById(R.id.umurBulan);
            pemberianKe = view.findViewById(R.id.pemberianKe);
            setTanggal = view.findViewById(R.id.getTanggal);
            tanggalValue = view.findViewById(R.id.tanggalValue);

            loadDataUmurBulan();

            waktuPemberian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                List<String> pemberianKeList = new ArrayList<>();
                ArrayAdapter arrayAdapter;

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        pemberianKeList.removeAll(pemberianKeList);
                        pemberianKeList.add("1");
                        dosis = "1 Kapsul Biru di bulan Februari-Agustus";
                        dosisTxt.setText("Dosis : " + dosis);
                        arrayAdapter = new ArrayAdapter(FragmentInputCatatanKesehatanAnak.this, android.R.layout.simple_dropdown_item_1line, pemberianKeList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        pemberianKe.setAdapter(arrayAdapter);
                    } else {
                        pemberianKeList.removeAll(pemberianKeList);
                        pemberianKeList.add("1");
                        pemberianKeList.add("2");
                        dosis = "1 Kapsul Merah di bulan Februari-Agustus";
                        dosisTxt.setText("Dosis : " + dosis);
                        arrayAdapter = new ArrayAdapter(FragmentInputCatatanKesehatanAnak.this, android.R.layout.simple_dropdown_item_1line, pemberianKeList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        pemberianKe.setAdapter(arrayAdapter);
                    }
                    waktuPemberianValue = waktuPemberian.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            pemberianKe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    pemberianKeValue = pemberianKe.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            setTanggal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog.OnDateSetListener onDateSetListener;
                    DatePickerDialog datePickerDialog;

                    onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            tanggalValue.setText(String.valueOf(i) +
                                    ConfigureApps.DATE_SEPARATOR +
                                    String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                    ConfigureApps.DATE_SEPARATOR +
                                    String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                            tanggalPemberian = tanggalValue.getText().toString();
                        }
                    };

                    datePickerDialog = new DatePickerDialog(FragmentInputCatatanKesehatanAnak.this, onDateSetListener, year, month, day);
                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            tanggalValue.setText("");
                        }
                    });

                    datePickerDialog.show();
                }
            });


            builder.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    DatabaseVariable.modelInputKesehatanAnak.setId_anak(id_anak);
                    DatabaseVariable.modelInputKesehatanAnak.setWaktu_pemberian(waktuPemberianValue);
                    DatabaseVariable.modelInputKesehatanAnak.setDosis(dosis);
                    DatabaseVariable.modelInputKesehatanAnak.setId_vitamin("1");
                    DatabaseVariable.modelInputKesehatanAnak.setTanggal_pemberian(tanggalPemberian);
                    DatabaseVariable.modelInputKesehatanAnak.setPemberian_ke(pemberianKeValue);
                    DatabaseVariable.modelInputKesehatanAnak.setKeterangan(keterangan.getText().toString());
                    final InsertQuery insertQuery = new InsertQuery(FragmentInputCatatanKesehatanAnak.this);

                    dialogInterface.dismiss();
                    new sendDataKesehatanAnak(FragmentInputCatatanKesehatanAnak.this,
                            new sendDataKesehatanAnak.AsyncResponse() {
                                @Override
                                public void onProcessFinish(Boolean status, String message) {
                                    if (status) {
                                        insertQuery.insertKesehatanAnakUpdate();
                                        Toast.makeText(activity, "Data Berhasil Di Ubah", Toast.LENGTH_SHORT).show();
                                        onStart();
                                        SetSelection();
                                    } else {
                                        Toast.makeText(activity, "Kesalahan Saat Memasukkan data", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).execute();
                }
            })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
            ;

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        public void loadDataUmurBulan() {
            List<String> umurBulan = new ArrayList<>();
            umurBulan.add("6-11 Bulan");
            umurBulan.add("12-23 Bulan");
            umurBulan.add("24-35 Bulan");
            umurBulan.add("36-47 Bulan");
            umurBulan.add("48-59 Bulan");

            ArrayAdapter adapter = new ArrayAdapter(FragmentInputCatatanKesehatanAnak.this, android.R.layout.simple_dropdown_item_1line, umurBulan);
            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            waktuPemberian.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public static class sendDataKesehatanAnak extends AsyncTask<String, JSONObject, String> {

        ProgressDialog progressDialog;
        Activity context;
        HashMap<String, String> params;
        String result;
        JSONObject jsonObject;
        JSONHander jsonHander;
        AsyncResponse asyncResponse = null;

        public interface AsyncResponse {
            void onProcessFinish(Boolean status, String message);
        }

        public sendDataKesehatanAnak(Activity context, AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
            this.context = context;
            jsonHander = new JSONHander(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                if (progressDialog != null) {
                    progressDialog = null;
                }
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mengirimkan data...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.KES_ANAK);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputKesehatanAnak.getId_anak());
            params.put(ConfigureApps.ID_VITAMIN,
                    DatabaseVariable.modelInputKesehatanAnak.getId_vitamin());
            params.put(ConfigureApps.WAKTU_PEMBERIAN,
                    DatabaseVariable.modelInputKesehatanAnak.getWaktu_pemberian());
            params.put(ConfigureApps.DOSIS,
                    DatabaseVariable.modelInputKesehatanAnak.getDosis());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputKesehatanAnak.getTanggal_pemberian());
            params.put(ConfigureApps.PEMBERIAN_KE,
                    DatabaseVariable.modelInputKesehatanAnak.getPemberian_ke());
            params.put(ConfigureApps.KETERANGAN,
                    DatabaseVariable.modelInputKesehatanAnak.getKeterangan());

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
//                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "Pilih Kembali Nama Anak Untuk Melihat Hasil Input", Toast.LENGTH_SHORT).show();
                        asyncResponse.onProcessFinish(true, s);
                    } else {
                        asyncResponse.onProcessFinish(false, s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
