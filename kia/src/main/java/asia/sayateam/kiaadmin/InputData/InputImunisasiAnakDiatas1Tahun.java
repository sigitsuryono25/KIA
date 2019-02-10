package asia.sayateam.kiaadmin.InputData;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.DateSplitter;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.R;
import asia.sigitsuryono.kialibrary.ConnectionDetector;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class InputImunisasiAnakDiatas1Tahun extends AppCompatActivity implements View.OnClickListener {

    int year, month, day;
    Calendar calendar;
    String[] headerTableHorizontal = {
            "Umur(Bulan)",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23",
            "24",
            "25"
    };
    String[] headerTableVertical = {
            "DPT-HB-Hib 1",
            "Campak"
    };

    TableLayout tableLayout;
    Toolbar toolbar;
    FloatingActionButton saveData;
    String dummyDate = "0000-00-00";
    String brown = "brown";

    private static String datesWasPick = "";
    private static String coodinateX = "";
    private static String coodinateY = "";

    SelectQuery selectQuery;
    Spinner anakke;
    ScrollView layout;
    Cursor cursor;
    String id_anak;
    TextView nama_anak, namaOrtu, ttl;
    Cursor c;
    int coordinateX, coordinateY;
    LinearLayout info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_imunisasi_anak_diatas_1_tahun);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Imunisasi Diatas 1 Tahun", Color.WHITE));
        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        saveData = (FloatingActionButton) findViewById(R.id.saveData);
        layout = (ScrollView) findViewById(R.id.layout);
        saveData.setOnClickListener(this);
        anakke = (Spinner) findViewById(R.id.anakKe);
        nama_anak = (TextView) findViewById(R.id.nama_anak);
        ttl = (TextView) findViewById(R.id.ttl);
        namaOrtu = (TextView) findViewById(R.id.namaOrtu);
        info = (LinearLayout) findViewById(R.id.info);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        selectQuery = new SelectQuery(this);


        loadDataSpinner();
        anakke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (anakke.getSelectedItem().toString().equalsIgnoreCase("--Pilih Anak--")) {
                    layout.setVisibility(View.GONE);
                    info.setVisibility(View.GONE);
                    saveData.setVisibility(View.GONE);
                } else {
                    tableLayout.removeAllViews();
                    nama_anak.setText("Nama Anak : " + anakke.getSelectedItem().toString());
                    loadData(anakke.getSelectedItem().toString());
                    CreateData();
                    layout.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    saveData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.keterangan_imunisasi, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.keteranganImunisasi:
                try {
                    if (id_anak.contentEquals("") || id_anak.equalsIgnoreCase(null)) {
                        Toast.makeText(this, "Pilih Anak Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        new FragmentKeteranganImunisasi(this, String.valueOf(id_anak));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.refreshData:
                if (id_anak.contentEquals("") || id_anak.equalsIgnoreCase(null)) {
                    Toast.makeText(this, "Pilih Anak Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    new GetImunisasi1TahunPlus().execute(new String[]{String.valueOf(id_anak)});
                    anakke.setSelection(0);
                    info.setVisibility(View.GONE);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadDataSpinner() {

        List<String> strings = new ArrayList<>();
        strings.add("--Pilih Anak--");
        cursor = selectQuery.getDataFor_Info_Anak();
        while (cursor.moveToNext()) {
            strings.add(cursor.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(InputImunisasiAnakDiatas1Tahun.this, android.R.layout.simple_dropdown_item_1line, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        anakke.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void loadData(String anakke) {
        String idIbu = "";
        c = selectQuery.getDetailAnakKe(anakke);

        while (c.moveToNext()) {
            id_anak = c.getString(0);
            ttl.setText("Tanggal Lahir : " + new DateSplitter().DateSplitter(c.getString(6)));
            idIbu = c.getString(10);
        }

        c = selectQuery.getIbu(idIbu);
        while (c.moveToNext()) {
            namaOrtu.setText("Nama Ibu : " + c.getString(0));
        }

    }

    public void CreateData() {
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());
            for (int j = 0; j < headerTableHorizontal.length; j++) {
                TextView tv = new TextView(getApplicationContext());
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                rows1.setLayoutParams(layoutParams);
                if (j == 0) {
                    tv.setGravity(Gravity.CENTER);
                    tv.setPadding(5, 5, 5, 5);
                } else {
                    tv.setGravity(Gravity.LEFT);
                    layoutParams.setMargins(0, 0, 0, 100 - 110);
                    tv.setPadding(5, 5, 5, 10);
                }

                tv.setLayoutParams(layoutParams);
                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape_green));
                tv.setTextSize(18);
                tv.setTextColor(Color.RED);
                tv.setText(headerTableHorizontal[j]);
                rows1.addView(tv);
            }
            tableLayout.addView(rows1);

            for (int i = 0; i < headerTableVertical.length; i++) {
                TableRow row = new TableRow(getApplicationContext());

                final TextView tv1 = new TextView(getApplicationContext());
                tv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                tv1.setGravity(Gravity.CENTER);
                tv1.setTextSize(18);
                tv1.setTextColor(BLACK);
                tv1.setBackground(getResources().getDrawable(R.drawable.cell_shape_blue));
                tv1.setPadding(5, 5, 5, 5);
                tv1.setText(headerTableVertical[i]);
                row.addView(tv1);
                // inner for loop
                for (int j = 0; j < headerTableHorizontal.length - 1; j++) {
                    final TextView tv12 = new TextView(getApplicationContext());
                    tv12.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                    final TextView finalTv1 = tv12;
                    final int finalI = i; //coodinateY
                    final int finalJ = j; //coodinateX
                    tv12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv12.getText().toString();
//                            if (String.valueOf(finalTv1.getTextColors()).equalsIgnoreCase("ColorStateList{mStateSpecs=[[]]mColors=[-7042476]mDefaultColor=-7042476}")) {
                            if (String.valueOf(finalTv1.getText()).equalsIgnoreCase(brown)) {

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputImunisasiAnakDiatas1Tahun.this);
                                builder.setMessage("Silahkan Pilih :");
                                builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        coodinateX = String.valueOf(finalI);
                                        coodinateY = String.valueOf(finalJ);
                                        finalTv1.setTextColor(TRANSPARENT);
                                        finalTv1.setText(dummyDate);
                                        datesWasPick = dummyDate;
                                    }
                                });
                                builder.setPositiveButton("Masukan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                            @Override
                                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                                datesWasPick = "";
                                                finalTv1.setTextColor(BLACK);
                                                finalTv1.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) + ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                                datesWasPick = String.valueOf(year) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, dayOfMonth);
                                                coodinateX = String.valueOf(finalJ);
                                                coodinateY = String.valueOf(finalI);
                                            }
                                        };

                                        DatePickerDialog datePickerDialog = new DatePickerDialog(InputImunisasiAnakDiatas1Tahun.this, dateSetListener, year, month, day);
                                        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                                if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                    finalTv1.setTextColor(TRANSPARENT);
                                                    finalTv1.setText(dateSelected);
                                                } else {
                                                    finalTv1.setTextColor(BLACK);
                                                    finalTv1.setText(dateSelected);
                                                }
                                            }
                                        });
                                        datePickerDialog.show();
                                    }
                                });
                                builder.create().show();
                            }
                        }
                    });

                    if (j == 0 && i == 0) {
                        tv12.setBackgroundResource(R.drawable.cell_shape);
                        tv12.setTextColor(Color.WHITE);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(dummyDate);
                    }
                    if (j == 6 && i == 1) {
                        tv12.setBackgroundResource(R.drawable.cell_shape);
                        tv12.setTextColor(Color.WHITE);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(dummyDate);
                    }
                    if (j == 7 && i == 1) {
                        tv12.setBackgroundResource(R.drawable.cell_shape_yellow);
                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(dummyDate);

                    }

                    if ((j == 6 && i == 0) || (j == 7 && i == 0)) {
                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                        tv12.setTextColor(Color.parseColor("#948A54"));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(brown);
                    }

                    for (int a = 1; a <= 5; a++) {
                        if (j == a && i == 0) {
                            tv12.setBackgroundResource(R.drawable.cell_shape_yellow);
                            tv12.setTextColor(Color.parseColor("#FFFF00"));
                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(5, 5, 5, 5);
                            tv12.setText(dummyDate);
                        }

                    }

                    for (int a = 0; a <= 5; a++) {
                        if (j == a && i == 1) {
                            tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                            tv12.setTextColor(Color.parseColor("#948A54"));
                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(5, 5, 5, 5);
                            tv12.setText(brown);
                        }

                    }

                    try {
                        c = selectQuery.getImunDiatas1Tahun(Integer.parseInt(id_anak));
                        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                            String[] spliter = c.getString(4).split("\\.");
                            Log.i("x,y", c.getString(4));
                            coordinateX = Integer.parseInt(spliter[0]);
                            coordinateY = Integer.parseInt(spliter[1]);
                            Log.i("x", String.valueOf(coordinateX));
                            Log.i("y", String.valueOf(coordinateY));
                            if (j == coordinateX && i == coordinateY) {
                                if (c.getString(3).equalsIgnoreCase(dummyDate) || c.getString(3).equalsIgnoreCase("")) {
                                    if (j == 0 && i == 0) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == 6 && i == 1) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == 7 && i == 1) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_yellow);
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));

                                    }

                                    if ((j == 6 && i == 0) || (j == 7 && i == 0)) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(brown);
                                    }

                                    for (int a = 1; a <= 5; a++) {
                                        if (j == a && i == 0) {
                                            tv12.setBackgroundResource(R.drawable.cell_shape_yellow);
                                            tv12.setTextColor(Color.parseColor("#FFFF00"));
                                        }

                                    }

                                    for (int a = 0; a <= 5; a++) {
                                        if (j == a && i == 1) {
                                            tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                            tv12.setTextColor(Color.parseColor("#948A54"));
                                            tv12.setGravity(Gravity.CENTER);
                                            tv12.setTextSize(18);
                                            tv12.setPadding(5, 5, 5, 5);
                                            tv12.setText(brown);
                                        }

                                    }
                                } else {
                                    if (j == 0 && i == 0) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(10, 10, 10, 10);
                                        tv12.setTextColor(Color.BLACK);
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(3)));
                                    }
                                    if (j == 6 && i == 1) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(10, 10, 10, 10);
                                        tv12.setTextColor(Color.BLACK);
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(3)));
                                    }
                                    if (j == 7 && i == 1) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_yellow);
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(10, 10, 10, 10);
                                        tv12.setTextColor(Color.BLACK);
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(3)));

                                    }

                                    if ((j == 6 && i == 0) || (j == 7 && i == 0)) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(brown);
                                    }

                                    for (int a = 1; a <= 5; a++) {
                                        if (j == a && i == 0) {
                                            tv12.setBackgroundResource(R.drawable.cell_shape_yellow);
                                            tv12.setTextColor(Color.parseColor("#FFFF00"));
                                            tv12.setGravity(Gravity.CENTER);
                                            tv12.setTextSize(18);
                                            tv12.setPadding(10, 10, 10, 10);
                                            tv12.setTextColor(Color.BLACK);
                                            tv12.setText(new DateSplitter().DateSplitter(c.getString(3)));
                                        }

                                    }

                                    for (int a = 0; a <= 5; a++) {
                                        if (j == a && i == 1) {
                                            tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                            tv12.setTextColor(Color.parseColor("#948A54"));
                                            tv12.setGravity(Gravity.CENTER);
                                            tv12.setTextSize(18);
                                            tv12.setPadding(5, 5, 5, 5);
                                            tv12.setText(brown);
                                        }

                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    row.addView(tv12);
                }
                tableLayout.addView(row);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveData:
                if (datesWasPick.equalsIgnoreCase("") && coodinateX.equalsIgnoreCase("") && coodinateY.equalsIgnoreCase("")) {
                    Toast.makeText(InputImunisasiAnakDiatas1Tahun.this, "Silahkan isi kolom imunisasi yang dipilih", Toast.LENGTH_SHORT).show();
                } else {
                    SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(InputImunisasiAnakDiatas1Tahun.this);
                    if (new ConnectionDetector(InputImunisasiAnakDiatas1Tahun.this).isConnectingToInternet()) {
                        if (DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil().equals("")) {

                        } else {
                            SendDataToServerHandler.sendDataKeteranganImunisasi sendDataKeteranganImunisasi = sendDataToServerHandler.new sendDataKeteranganImunisasi();
                            sendDataKeteranganImunisasi.execute();
                        }
                        DatabaseVariable.modelInputImunDiatas1Tahun.setTanggal_pemberian(datesWasPick);
                        DatabaseVariable.modelInputImunDiatas1Tahun.setBulan_ke(headerTableHorizontal[Integer.parseInt(coodinateX) + 1]);
                        DatabaseVariable.modelInputImunDiatas1Tahun.setCoordinate_x(coodinateX);
                        DatabaseVariable.modelInputImunDiatas1Tahun.setCoordinate_y(coodinateY);
                        DatabaseVariable.modelInputImunDiatas1Tahun.setId_anak(id_anak);


                        SendDataToServerHandler.sendDataImunDiatas1Tahun sendDataImunDiatas1Tahun = sendDataToServerHandler.new sendDataImunDiatas1Tahun();
                        sendDataImunDiatas1Tahun.execute();
                    } else {
                        new AlertDialog.Builder(InputImunisasiAnakDiatas1Tahun.this)
                                .setMessage("Tidak ada koneksi Internet terdeteksi !" +
                                        "\nSilahkan aktifkan data selular atau hubungkan ke wifi untuk melanjutkan proses.")
                                .setPositiveButton("oke", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                })
                                .create().show();
                    }
                }
                break;
        }
    }

    public class GetImunisasi1TahunPlus extends AsyncTask<String, JSONObject, String> {

        InsertQuery insertQuery;
        JSONObject jsonObject;
        HashMap<String, String> params;
        String result;
        JSONHander jsonHander;
        DeleteQuery deleteQuery;
        ProgressDialog progressDialog;

        public GetImunisasi1TahunPlus() {
            insertQuery = new InsertQuery(InputImunisasiAnakDiatas1Tahun.this);
            jsonHander = new JSONHander(InputImunisasiAnakDiatas1Tahun.this);
            deleteQuery = new DeleteQuery(InputImunisasiAnakDiatas1Tahun.this);
            deleteQuery.DeletImun1TahunPlus();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InputImunisasiAnakDiatas1Tahun.this);
            progressDialog.setMessage("Mengunduh data...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_DIATAS_1_TAHUN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            String TAG_DETAIL_IMUN_1_TAHUN_PLUS = "detail_imun_imun_satu_tahun_plus";
            JSONArray detailimun_1_tahun_plus;
            String TAG_SUCCESS_IMUN_1_TAHUN_PLUS = "success_detail_imun_satu_tahun_plus";

            super.onPostExecute(s);
            progressDialog.dismiss();
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IMUN_1_TAHUN_PLUS).equalsIgnoreCase("0")) {
                        detailimun_1_tahun_plus = jsonObject.getJSONArray(TAG_DETAIL_IMUN_1_TAHUN_PLUS);
                        for (int i = 0; i < detailimun_1_tahun_plus.length(); i++) {
                            JSONObject anakDetail = detailimun_1_tahun_plus.getJSONObject(i);
                            DatabaseVariable.modelInputImunDiatas1Tahun.setId_anak(anakDetail.getString("id_anak"));

                            DatabaseVariable.modelInputImunDiatas1Tahun.setId_imunisasi(anakDetail.getString("id_jenis_imunisasi"));

                            DatabaseVariable.modelInputImunDiatas1Tahun.setBulan_ke(anakDetail.getString("bulan_ke"));

                            DatabaseVariable.modelInputImunDiatas1Tahun.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");
                            DatabaseVariable.modelInputImunDiatas1Tahun.setCoordinate_x(params[0]);
                            DatabaseVariable.modelInputImunDiatas1Tahun.setCoordinate_y(params[1]);

                            insertQuery.insertImun1TahunPlus();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
