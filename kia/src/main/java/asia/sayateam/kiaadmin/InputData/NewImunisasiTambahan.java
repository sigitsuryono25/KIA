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
import android.view.LayoutInflater;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.RED;
import static android.graphics.Color.TRANSPARENT;

/**
 * Created by Sigit Suryono on 009, 09-09-2017.
 */

public class NewImunisasiTambahan extends AppCompatActivity {

    public static final String dummyDate = "0000-00-00";
    String[] headerTableHorizontal = {"VAKSIN", dummyDate, "Tanggal", "Pemberian", dummyDate};
    private static String datesWasPick = "";
    private static String coodinateX = "";
    private static String coodinateY = "";
    private static String NamaVaksin = "";
    TextView tvH0, tvH1, tvH2;
    DatabaseHadler databaseHadler;
    SQLiteDatabase database;

    List<String> vaksinSelected;

    Toolbar toolbar;
    Calendar calendar;
    TableLayout tableLayout;
    int year, month, day;
    FloatingActionButton saveData;

    SelectQuery selectQuery;
    Spinner anakke, namaVaksinSpinner;
    ScrollView layout;
    Cursor cursor;
    String id_anak;
    Cursor c;
    int position;
    TextView nama_anak, namaOrtu, ttl;
    LinearLayout info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_imunisasi_tambahan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Imunisasi Tambahan", Color.WHITE));

        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        calendar = Calendar.getInstance();
        anakke = (Spinner) findViewById(R.id.anakKe);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        selectQuery = new SelectQuery(this);
        info = (LinearLayout) findViewById(R.id.info);
        layout = (ScrollView) findViewById(R.id.layout);


        nama_anak = (TextView) findViewById(R.id.nama_anak);
        ttl = (TextView) findViewById(R.id.ttl);
        namaOrtu = (TextView) findViewById(R.id.namaOrtu);

        databaseHadler = new DatabaseHadler(this);
        database = databaseHadler.OpenDatabase();

        vaksinSelected = new ArrayList<>();

        saveData = (FloatingActionButton) findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(NewImunisasiTambahan.this);
                SendDataToServerHandler.sendDataImunTambahan sendDataImunTambahan = sendDataToServerHandler.new sendDataImunTambahan();
                SendDataToServerHandler.sendDataKeteranganImunisasi sendDataKeteranganImunisasi = sendDataToServerHandler.new sendDataKeteranganImunisasi();
                if (datesWasPick.equalsIgnoreCase("") && coodinateX.equalsIgnoreCase("") && coodinateY.equalsIgnoreCase("")) {
                    Toast.makeText(NewImunisasiTambahan.this, "Silahkan Isi kolom imunisasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    if (new ConnectionDetector(NewImunisasiTambahan.this).isConnectingToInternet()) {
                        if (DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil().equalsIgnoreCase("")) {
                            sendDataImunTambahan.execute();
                        } else {
                            sendDataImunTambahan.execute();
                            sendDataKeteranganImunisasi.execute();
                        }

                    } else {
                        new AlertDialog.Builder(NewImunisasiTambahan.this)
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
                    tableLayout.removeAllViews();
                    loadData(anakke.getSelectedItem().toString());
                    nama_anak.setText("Nama Anak : " + anakke.getSelectedItem().toString());
                    CreateData();
                    info.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
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
                    new GetImunisasiTambahan().execute(new String[]{String.valueOf(id_anak)});
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

        ArrayAdapter adapter = new ArrayAdapter(NewImunisasiTambahan.this, android.R.layout.simple_dropdown_item_1line, strings);
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
            namaOrtu.setText("Nama Orang Tua : " + c.getString(0));
        }
    }

    public void CreateData() {
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());
            for (int j = 0; j < 5; j++) {
                TextView tv = new TextView(getApplicationContext());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                rows1.setLayoutParams(layoutParams);
                tv.setPadding(5, 5, 5, 5);

                tv.setLayoutParams(layoutParams);
                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape_green));
                tv.setTextSize(18);
                if (headerTableHorizontal[j].equalsIgnoreCase(dummyDate)) {
                    tv.setTextColor(Color.TRANSPARENT);
                } else if (headerTableHorizontal[j].equalsIgnoreCase("Tanggal")) {
                    tv.setGravity(Gravity.RIGHT);
                    tv.setTextColor(Color.RED);
                } else if (headerTableHorizontal[j].equalsIgnoreCase("Pemberian")) {
                    tv.setGravity(Gravity.LEFT);
                    tv.setTextColor(Color.RED);
                } else {
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(Color.RED);
                }
                tv.setText(headerTableHorizontal[j]);
                rows1.addView(tv);
            }
            tableLayout.addView(rows1);
        }

        //content
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());

            //nama vaksin
            for (int j = 0; j < 1; j++) {
                c = database.rawQuery("SELECT `nama_vaksin` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.0' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    tvH0 = new TextView(getApplicationContext());
                    tvH0.setPadding(5, 5, 5, 5);
                    tvH0.setLayoutParams(layoutParams);
                    tvH0.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape_blue));
                    tvH0.setTextSize(18);
                    tvH0.setGravity(Gravity.CENTER);

                    if (c.getString(0).equalsIgnoreCase("")) {
                        tvH0.setText(dummyDate);
                    } else {
                        NamaVaksin = c.getString(0);
                        tvH0.setText(c.getString(0));
                    }

                    if (tvH0.getText().toString().equalsIgnoreCase(dummyDate)) {
                        tvH0.setTextColor(TRANSPARENT);
                    } else {
                        tvH0.setTextColor(BLACK);
                    }


                    tvH0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LayoutInflater layoutInflater = getLayoutInflater();
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            View views = layoutInflater.inflate(R.layout.layout_nama_vaksin, null);
                            builder.setView(views);
                            namaVaksinSpinner = views.findViewById(R.id.namaVaksin);
                            loadNamaVaksin();

                            builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NamaVaksin = namaVaksinSpinner.getSelectedItem().toString();
                                    tvH0.setTextColor(RED);
                                    tvH0.setText(namaVaksinSpinner.getSelectedItem().toString());
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }
                    });
                    rows1.addView(tvH0);
                }
            }

            //date
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.0' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "0.0");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) + ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "0.0");

                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();
                        }
                    });
                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.1' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "0.1");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);

                                            ShowSelected(datesWasPick, "0.1");
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.2' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "0.2");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "0.2");
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }

            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.3' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "0.3");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "0.3");
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }

            tableLayout.addView(rows1);
        }


        //ROW 1
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());

            //nama vaksin
            for (int j = 0; j < 1; j++) {
                c = database.rawQuery("SELECT `nama_vaksin` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.0' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    tvH1 = new TextView(getApplicationContext());
                    tvH1.setPadding(5, 5, 5, 5);
                    tvH1.setLayoutParams(layoutParams);
                    tvH1.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape_blue));
                    tvH1.setTextSize(18);
                    tvH1.setGravity(Gravity.CENTER);
                    if (c.getString(0).equalsIgnoreCase("")) {
                        tvH1.setText(dummyDate);
                    } else {
                        NamaVaksin = c.getString(0);
                        tvH1.setText(c.getString(0));
                    }

                    if (tvH1.getText().toString().equalsIgnoreCase(dummyDate)) {
                        tvH1.setTextColor(TRANSPARENT);
                    } else {
                        tvH1.setTextColor(BLACK);
                    }

                    tvH1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            LayoutInflater layoutInflater = getLayoutInflater();
                            View views = layoutInflater.inflate(R.layout.layout_nama_vaksin, null);
                            builder.setView(views);
                            namaVaksinSpinner = views.findViewById(R.id.namaVaksin);
                            loadNamaVaksin();

                            builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NamaVaksin = namaVaksinSpinner.getSelectedItem().toString();
                                    tvH1.setTextColor(RED);
                                    tvH1.setText(namaVaksinSpinner.getSelectedItem().toString());
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                    rows1.addView(tvH1);
                }
            }

            //date
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.0' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);

                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "1.0");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "1.0");

                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.1' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "1.1");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "1.1");

                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.2' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "1.2");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "1.2");

                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }

            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.3' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "1.3");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);

                                            ShowSelected(datesWasPick, "1.3");
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }

            tableLayout.addView(rows1);
        }


        //ROW 2
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());

            //nama vaksin
            for (int j = 0; j < 1; j++) {
                c = database.rawQuery("SELECT `nama_vaksin` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.0' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    tvH2 = new TextView(getApplicationContext());
                    tvH2.setPadding(5, 5, 5, 5);
                    tvH2.setLayoutParams(layoutParams);
                    tvH2.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape_blue));
                    tvH2.setTextSize(18);
                    tvH2.setGravity(Gravity.CENTER);
                    if (c.getString(0).equalsIgnoreCase("")) {
                        tvH2.setText(dummyDate);
                    } else {
                        NamaVaksin = c.getString(0);
                        tvH2.setText(c.getString(0));
                    }

                    if (tvH2.getText().toString().equalsIgnoreCase(dummyDate)) {
                        tvH2.setTextColor(TRANSPARENT);
                    } else {
                        tvH2.setTextColor(BLACK);
                    }

                    tvH2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            LayoutInflater layoutInflater = getLayoutInflater();
                            View views = layoutInflater.inflate(R.layout.layout_nama_vaksin, null);
                            builder.setView(views);
                            namaVaksinSpinner = views.findViewById(R.id.namaVaksin);
                            loadNamaVaksin();

                            builder.setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NamaVaksin = namaVaksinSpinner.getSelectedItem().toString();
                                    tvH2.setTextColor(RED);
                                    tvH2.setText(namaVaksinSpinner.getSelectedItem().toString());
                                }
                            });

                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                    rows1.addView(tvH2);
                }
            }

            //date
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.0' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "2.0");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "2.0");

                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.1' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "2.1");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);

                                            ShowSelected(datesWasPick, "2.1");
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.2' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "2.2");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                                            ShowSelected(datesWasPick, "2.2");

                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }

            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_tambahan` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.3' ", null);
                while (c.moveToNext()) {
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    rows1.setLayoutParams(layoutParams);
                    final TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(5, 5, 5, 5);
                    tv.setLayoutParams(layoutParams);
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape));
                    tv.setTextSize(18);
                    if (c.getString(0).equalsIgnoreCase(dummyDate)) {
                        tv.setTextColor(TRANSPARENT);
                    } else {
                        tv.setTextColor(BLACK);
                    }
                    tv.setText(new DateSplitter().DateSplitter(c.getString(0)));

                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = tv.getText().toString();

                            AlertDialog.Builder builder = new AlertDialog.Builder(NewImunisasiTambahan.this);
                            builder.setMessage("Silahkan Pilih :");
                            builder.setNegativeButton("Hapus Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tv.setTextColor(TRANSPARENT);
                                    tv.setText(dummyDate);
                                    datesWasPick = dummyDate;
                                    ShowSelected(datesWasPick, "2.3");
                                }
                            });
                            builder.setPositiveButton("Masukkan Tanggal Imunisasi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            tv.setTextColor(BLACK);
                                            tv.setText(String.format(ConfigureApps.DATE_FORMATER, dayOfMonth) +
                                                    ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) +
                                                    ConfigureApps.DATE_SEPARATOR + String.valueOf(year));
                                            datesWasPick = String.valueOf(year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);

                                            ShowSelected(datesWasPick, "2.3");
                                        }
                                    };


                                    DatePickerDialog datePickerDialog = new DatePickerDialog(NewImunisasiTambahan.this, dateSetListener, year, month, day);

                                    datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                            if (dateSelected.equalsIgnoreCase(dummyDate)) {
                                                tv.setTextColor(TRANSPARENT);
                                                tv.setText(dateSelected);
                                            } else {
                                                tv.setTextColor(BLACK);
                                                tv.setText(dateSelected);
                                            }
                                        }
                                    });
                                    datePickerDialog.show();
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.create().show();

                        }
                    });
                    rows1.addView(tv);
                }
            }

            tableLayout.addView(rows1);
        }

    }

    public void loadNamaVaksin() {
        c = selectQuery.getJenisImunisasi();

        List<String> namavaksinList = new ArrayList<>();
        while (c.moveToNext()) {
            namavaksinList.add(c.getString(1));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(NewImunisasiTambahan.this, android.R.layout.simple_dropdown_item_1line, namavaksinList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        namaVaksinSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
    }

    public void ShowSelected(String date, String coordinate) {
        String vaksin = "";
        String[] x = coordinate.split(Pattern.quote("."));

        if (x[0].equals("0")) {
            vaksin = tvH0.getText().toString();
        } else if (x[0].equals("1")) {
            vaksin = tvH1.getText().toString();
        } else if (x[0].equals("2")) {
            vaksin = tvH2.getText().toString();
        }

        DatabaseVariable.modelInputImunTambahan.setId_anak(id_anak);
        DatabaseVariable.modelInputImunTambahan.setNama_vaksin(vaksin);
        DatabaseVariable.modelInputImunTambahan.setTanggal_pemberian(date);
        DatabaseVariable.modelInputImunTambahan.setcoordinate(coordinate);
    }


    public class GetImunisasiTambahan extends AsyncTask<String, JSONObject, String> {

        InsertQuery insertQuery;
        JSONObject jsonObject;
        HashMap<String, String> params;
        String result;
        JSONHander jsonHander;
        DeleteQuery deleteQuery;
        ProgressDialog progressDialog;

        public GetImunisasiTambahan() {
            insertQuery = new InsertQuery(NewImunisasiTambahan.this);
            jsonHander = new JSONHander(NewImunisasiTambahan.this);
            deleteQuery = new DeleteQuery(NewImunisasiTambahan.this);
            deleteQuery.DeletImunTambahan();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NewImunisasiTambahan.this);
            progressDialog.setMessage("Mengunduh data...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_TAMBAHAN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_TAMBAHAN = "detail_imun_tambahan";
            JSONArray detailimun_tambahan;
            String TAG_SUCCESS_IMUN_TAMBAHAN = "success_detail_imun_tambahan";

            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.d("s", s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    if (s.contains("\"success_detail_imun_tambahan\":0,\"message\":\"imun_tambahan found\"")) {
                        detailimun_tambahan = jsonObject.getJSONArray("detail_imun_tambahan");
                        for (int i = 0; i < detailimun_tambahan.length(); i++) {
                            JSONObject anakDetail = detailimun_tambahan.getJSONObject(i);
                            DatabaseVariable.modelInputImunTambahan.setId_anak(anakDetail.getString("id_anak"));
                            DatabaseVariable.modelInputImunTambahan.setNama_vaksin(anakDetail.getString("nama_vaksin"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");

                            DatabaseVariable.modelInputImunTambahan.setcoordinate(coodinate);
                            DatabaseVariable.modelInputImunTambahan.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));
                            insertQuery.insertImunTambahan();
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

