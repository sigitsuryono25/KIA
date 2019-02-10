package asia.sayateam.kiaadmin.InputData;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.R;
import asia.sigitsuryono.kialibrary.ConnectionDetector;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class InputImunisasiAnak0To12 extends AppCompatActivity {

    TableLayout tableLayout;
    int year, month, day;
    FloatingActionButton saveData;
    String[] headerTableHorizontal = {
            "Umur(Bulan)",
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
    };
    String[] headerTableVertical = {
            "Hb0",
            "BCG",
            "Polio 1",
            "DPT-HB-Hib-1",
            "Polio 2",
            "HPT-HB-Hib-2",
            "Polio 3",
            "DPT-HB-Hib",
            "Polio 4",
            "IPV",
            "Campak"
    };

    Calendar calendar;
    SecurePreferences securePreferences;

    private static String datesWasPick = "";
    private static String dates = "";
    private static String coodinateX = "";
    private static String coodinateY = "";
    public static final String dummyDate = "0000-00-00";
    public static final String brown = "brown";
    int id_anak = 0;

    Toolbar toolbar;

    DatabaseHadler databaseHadler;
    SQLiteDatabase database;
    SelectQuery selectQuery;
    Spinner anakke;
    ScrollView layout;
    Cursor c;
    TextView nama_anak, namaOrtu, ttl;
    LinearLayout info;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_imunisasi_anak_0_12);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Imunisasi 0-12 Bulan", Color.WHITE));

        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        anakke = (Spinner) findViewById(R.id.anakKe);
        layout = (ScrollView) findViewById(R.id.layout);
        info = (LinearLayout) findViewById(R.id.info);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        nama_anak = (TextView) findViewById(R.id.nama_anak);
        ttl = (TextView) findViewById(R.id.ttl);
        namaOrtu = (TextView) findViewById(R.id.namaOrtu);


        securePreferences = new SecurePreferences(getApplicationContext(), getPackageName().toLowerCase(), "NrGWse07", true);

        databaseHadler = new DatabaseHadler(getApplicationContext());
        database = databaseHadler.OpenDatabase();
        selectQuery = new SelectQuery(getApplicationContext());

        saveData = (FloatingActionButton) findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(InputImunisasiAnak0To12.this);
                SendDataToServerHandler.sendDataImun_0_12 sendDataImun_0_12 = sendDataToServerHandler.new sendDataImun_0_12();
                SendDataToServerHandler.sendDataKeteranganImunisasi sendDataKeteranganImunisasi = sendDataToServerHandler.new sendDataKeteranganImunisasi();
                try {
                    if (datesWasPick.equalsIgnoreCase("") && coodinateX.equalsIgnoreCase("") && coodinateY.equalsIgnoreCase("")) {
                        Toast.makeText(InputImunisasiAnak0To12.this, "Silahkan Isi kolom imunisasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseVariable.modelInputImunisasi0_12.setId_anak(String.valueOf(id_anak));
                        DatabaseVariable.modelInputImunisasi0_12.setBulan_ke(coodinateY);
                        DatabaseVariable.modelInputImunisasi0_12.setTanggal_pemberian(datesWasPick);
                        DatabaseVariable.modelInputImunisasi0_12.setCoordinate_x(coodinateX);
                        DatabaseVariable.modelInputImunisasi0_12.setCoordinate_y(coodinateY);

                        if (new ConnectionDetector(InputImunisasiAnak0To12.this).isConnectingToInternet()) {
                            if (DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil().equals("")) {
                                sendDataImun_0_12.execute();
                            } else {
                                sendDataKeteranganImunisasi.execute();
                                sendDataImun_0_12.execute();
                            }
                        } else {
                            new AlertDialog.Builder(InputImunisasiAnak0To12.this)
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadDataSpinner();
        anakke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (anakke.getSelectedItem().toString().equalsIgnoreCase("--Pilih Anak--")) {
                    layout.setVisibility(View.GONE);
                    saveData.setVisibility(View.GONE);
                    info.setVisibility(View.GONE);
                } else {
                    loadData(anakke.getSelectedItem().toString());
                    nama_anak.setText("Nama Anak : " + anakke.getSelectedItem().toString());
                    layout.setVisibility(View.VISIBLE);
                    saveData.setVisibility(View.VISIBLE);
                    info.setVisibility(View.VISIBLE);
                    tableLayout.removeAllViews();
                    createData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void loadDataSpinner() {
        List<String> strings = new ArrayList<>();
        strings.add("--Pilih Anak--");
        c = selectQuery.getDataFor_Info_Anak();
        while (c.moveToNext()) {
            strings.add(c.getString(1));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(InputImunisasiAnak0To12.this, android.R.layout.simple_dropdown_item_1line, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        anakke.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
                    if (id_anak == 0) {
                        Toast.makeText(this, "Pilih Anak Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        new FragmentKeteranganImunisasi(this, String.valueOf(id_anak));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.refreshData:
                if (id_anak == 0) {
                    Toast.makeText(this, "Pilih Anak Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    new GetImunisasi0_12().execute(new String[]{String.valueOf(id_anak)});
                    anakke.setSelection(0);
                    info.setVisibility(View.GONE);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadData(String anakke) {
        String idIbu = "";
        c = selectQuery.getDetailAnakKe(anakke);

        while (c.moveToNext()) {
            id_anak = Integer.parseInt(c.getString(0));
            ttl.setText("Tanggal Lahir : " + new DateSplitter().DateSplitter(c.getString(6)));
            idIbu = c.getString(10);
        }

        c = selectQuery.getIbu(idIbu);
        while (c.moveToNext()) {
            namaOrtu.setText("Nama Ibu : " + c.getString(0));
        }
    }

    public void createData() {
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());
            for (int j = 0; j < 14; j++) {
                TextView tv = new TextView(getApplicationContext());
                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                rows1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
        }

        TableRow row;

        for (int i = 0; i <= 10; i++) {
            // inner for loop
            row = new TableRow(getApplicationContext());
            final TextView tv1 = new TextView(getApplicationContext());
            tv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            tv1.setGravity(Gravity.CENTER);
            tv1.setTextSize(18);
            tv1.setTextColor(BLACK);
            tv1.setBackground(getResources().getDrawable(R.drawable.cell_shape_blue));
            tv1.setPadding(5, 5, 5, 5);
            tv1.setText(headerTableVertical[i]);
            row.addView(tv1);

            for (int j = 0; j < 13; j++) {
                final TextView tv12 = new TextView(getApplicationContext());
                tv12.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                final TextView finalTv1 = tv12;
                final int finalI = i;
                final int finalJ = j;
                tv12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String dateSelected = tv12.getText().toString();
//                        if (String.valueOf(finalTv1.getTextColors()).equalsIgnoreCase("ColorStateList{mStateSpecs=[[]]mColors=[-7042476]mDefaultColor=-7042476}")) {
                        if (String.valueOf(finalTv1.getText()).equalsIgnoreCase(brown)) {

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InputImunisasiAnak0To12.this);
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

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            finalTv1.setTextColor(BLACK);
                                            finalTv1.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) + ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            coodinateX = String.valueOf(finalI);
                                            coodinateY = String.valueOf(finalJ);
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(InputImunisasiAnak0To12.this, dateSetListener, year, month, day);

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
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    }
                });

                //IF TABLE IS NULL


                //IF TABLE HAVE CONTENT
                try {
                    //WHITE
                    if (j == 0 && i == 0) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        c = selectQuery.getImun0_12(id_anak, 0, 0);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                Log.i("0.0", c.getString(2));
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 0 && i == 1) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setTextColor(Color.BLACK);
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 0 && i == 2) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 1 && i == 1) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 1 && i == 2) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 4 && i == 7) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 4 && i == 8) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 4 && i == 9) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 9 && i == 10) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    //YELLOW
                    for (int a = 1; a <= 4; a++) {
                        for (int b = 2; b <= 11; b++) {
                            if (j == b && i == a) {
                                tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                tv12.setGravity(Gravity.CENTER);
                                tv12.setTextSize(18);
                                tv12.setPadding(10, 10, 10, 10);
                                tv12.setTextColor(Color.TRANSPARENT);
                                tv12.setText(dummyDate);
                                c = selectQuery.getImun0_12(id_anak, i, j);
                                if (c.getCount() > 0) {
                                    while (c.moveToNext()) {
                                        if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                            tv12.setTextColor(Color.BLACK);
                                            dates = new DateSplitter().DateSplitter(c.getString(2));
                                            tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                        } else {
                                            tv12.setTextColor(Color.TRANSPARENT);
                                            dates = dummyDate;
                                            tv12.setText(dummyDate);
                                        }
                                    }
                                } else {

                                }

                            }
                        }
                    }

                    for (int a = 5; a <= 6; a++) {
                        for (int b = 3; b <= 11; b++) {
                            if (j == b && i == a) {
                                tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                tv12.setGravity(Gravity.CENTER);
                                tv12.setTextSize(18);
                                tv12.setPadding(10, 10, 10, 10);
                                tv12.setTextColor(Color.TRANSPARENT);
                                tv12.setText(dummyDate);
                                c = selectQuery.getImun0_12(id_anak, i, j);
                                if (c.getCount() > 0) {
                                    while (c.moveToNext()) {
                                        if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                            tv12.setTextColor(Color.BLACK);
                                            dates = new DateSplitter().DateSplitter(c.getString(2));
                                            tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                        } else {
                                            tv12.setTextColor(Color.TRANSPARENT);
                                            dates = dummyDate;
                                            tv12.setText(dummyDate);
                                        }
                                    }
                                } else {

                                }
                            }
                        }
                    }

                    for (int a = 7; a <= 9; a++) {
                        for (int b = 5; b <= 11; b++) {
                            if (j == b && i == a) {
                                tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                tv12.setGravity(Gravity.CENTER);
                                tv12.setTextSize(18);
                                tv12.setPadding(10, 10, 10, 10);
                                tv12.setTextColor(Color.TRANSPARENT);
                                tv12.setText(dummyDate);
                                c = selectQuery.getImun0_12(id_anak, i, j);
                                if (c.getCount() > 0) {
                                    while (c.moveToNext()) {
                                        if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                            tv12.setTextColor(Color.BLACK);
                                            dates = new DateSplitter().DateSplitter(c.getString(2));
                                            tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                        } else {
                                            tv12.setTextColor(Color.TRANSPARENT);
                                            dates = dummyDate;
                                            tv12.setText(dummyDate);
                                        }
                                    }
                                } else {

                                }
                            }
                        }
                    }

                    if (j == 10 && i == 10) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 11 && i == 10) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    //RED
                    for (int a = 2; a <= 10; a++) {
                        if (j == 12 && i == a) {
                            tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_red));

                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(10, 10, 10, 10);
                            tv12.setTextColor(Color.TRANSPARENT);
                            tv12.setText(dummyDate);
                            c = selectQuery.getImun0_12(id_anak, i, j);
                            if (c.getCount() > 0) {
                                while (c.moveToNext()) {
                                    if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                        tv12.setTextColor(Color.BLACK);
                                        dates = new DateSplitter().DateSplitter(c.getString(2));
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                    } else {
                                        tv12.setTextColor(Color.TRANSPARENT);
                                        dates = dummyDate;
                                        tv12.setText(dummyDate);
                                    }
                                }
                            } else {

                            }
                        }

                    }

                    if (j == 2 && i == 3) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 2 && i == 4) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 3 && i == 5) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 3 && i == 6) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 4 && i == 7) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 4 && i == 8) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 4 && i == 9) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.TRANSPARENT);
                        tv12.setText(dummyDate);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    dates = new DateSplitter().DateSplitter(c.getString(2));
                                    tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    dates = dummyDate;
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    //brown
                    for (int a = 3; a <= 10; a++) {
                        for (int b = 0; b <= 1; b++) {
                            if (j == b && i == a) {
                                tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                tv12.setGravity(Gravity.CENTER);
                                tv12.setTextSize(18);
                                tv12.setPadding(10, 10, 10, 10);
                                tv12.setTextColor(Color.parseColor("#948A54"));
                                tv12.setText(brown);
                            }
                        }

                    }
                    for (int a = 5; a <= 10; a++) {
                        if (j == 2 && i == a) {
                            tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(10, 10, 10, 10);
                            tv12.setTextColor(Color.parseColor("#948A54"));
                            tv12.setText(brown);
                        }
                    }

                    for (int a = 7; a <= 10; a++) {
                        if (j == 3 && i == a) {
                            tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(10, 10, 10, 10);
                            tv12.setTextColor(Color.parseColor("#948A54"));
                            tv12.setText(brown);
                        }
                    }

                    for (int a = 4; a <= 8; a++) {
                        if (j == a && i == 10) {
                            tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(10, 10, 10, 10);
                            tv12.setTextColor(Color.parseColor("#948A54"));
                            tv12.setText(brown);
                        }
                    }

                    for (int a = 1; a <= 12; a++) {
                        if (j == a && i == 0) {
                            tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                            tv12.setGravity(Gravity.CENTER);
                            tv12.setTextSize(18);
                            tv12.setPadding(10, 10, 10, 10);
                            tv12.setTextColor(Color.parseColor("#948A54"));
                            tv12.setText(brown);
                        }
                    }

                    if (j == 12 && i == 1) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTextColor(Color.parseColor("#948A54"));
                        tv12.setText(brown);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                row.addView(tv12);
            }

            tableLayout.addView(row);
        }

    }

    public class GetImunisasi0_12 extends AsyncTask<String, JSONObject, String> {
        InsertQuery insertQuery;
        JSONObject jsonObject;
        HashMap<String, String> params;
        String result;
        JSONHander jsonHander;
        DeleteQuery deleteQuery;
        ProgressDialog progressDialog;

        public GetImunisasi0_12() {
            insertQuery = new InsertQuery(InputImunisasiAnak0To12.this);
            jsonHander = new JSONHander(InputImunisasiAnak0To12.this);
            deleteQuery = new DeleteQuery(InputImunisasiAnak0To12.this);
            deleteQuery.DeletImun012();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InputImunisasiAnak0To12.this);
            progressDialog.setMessage("Mengunduh data...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_0_12);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_0_12 = "detail_imun_0_12";
            JSONArray detailimun_0_12;
            String TAG_SUCCESS_IMUN_0_12 = "success_detail_imun_0_12";

            super.onPostExecute(s);

            progressDialog.dismiss();
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IMUN_0_12).equalsIgnoreCase("0")) {
                        detailimun_0_12 = jsonObject.getJSONArray(TAG_DETAIL_IMUN_0_12);
                        for (int i = 0; i < detailimun_0_12.length(); i++) {
                            JSONObject anakDetail = detailimun_0_12.getJSONObject(i);
                            DatabaseVariable.modelInputImunisasi0_12.setId_anak(anakDetail.getString("id_anak"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");

                            DatabaseVariable.modelInputImunisasi0_12.setCoordinate_x(params[0]);
                            DatabaseVariable.modelInputImunisasi0_12.setCoordinate_y(params[1]);
                            DatabaseVariable.modelInputImunisasi0_12.setBulan_ke(anakDetail.getString("bulan_ke"));
                            DatabaseVariable.modelInputImunisasi0_12.setTanggal_pemberian(anakDetail.getString("tanggal"));
                            DatabaseVariable.modelInputImunisasi0_12.setId_imunisasi(anakDetail.getString("id_imunisasi"));
                            insertQuery.insertImun012();
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
