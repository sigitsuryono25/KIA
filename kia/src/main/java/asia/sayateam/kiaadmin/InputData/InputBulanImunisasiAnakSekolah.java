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
 * Created by Sigit Suryono on 16-Aug-17.
 */

public class InputBulanImunisasiAnakSekolah extends AppCompatActivity {

    String[] headerTableHorizontal = {
            "Kelas",
            "1SD",
            "2SD",
            "3SD"
    };
    String[] headerTableVertical = {
            "Campak",
            "DT",
            "TD"
    };
    private static String datesWasPick = "";
    private static String coodinateX = "";
    private static String coodinateY = "";
    public static final String dummyDate = "0000-00-00";
    public static final String brown = "brown";

    Toolbar toolbar;
    Calendar calendar;
    TableLayout tableLayout;
    int year, month, day;
    FloatingActionButton saveData;

    SelectQuery selectQuery;
    Spinner anakke;
    ScrollView layout;
    String id_anak;
    Cursor c;
    int coordinateX, coordinateY, position;
    TextView nama_anak, namaOrtu, ttl;
    LinearLayout info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_bulan_imunisasi_anak_sekolah);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Bulan Imun. Anak Sekolah", Color.WHITE));

        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        calendar = Calendar.getInstance();
        anakke = (Spinner) findViewById(R.id.anakKe);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        selectQuery = new SelectQuery(this);
        layout = (ScrollView) findViewById(R.id.layout);
        info = (LinearLayout) findViewById(R.id.info);

        nama_anak = (TextView) findViewById(R.id.nama_anak);
        ttl = (TextView) findViewById(R.id.ttl);
        namaOrtu = (TextView) findViewById(R.id.namaOrtu);

        saveData = (FloatingActionButton) findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datesWasPick.equalsIgnoreCase("") && coodinateX.equalsIgnoreCase("") && coodinateY.equalsIgnoreCase("")) {
                    Toast.makeText(InputBulanImunisasiAnakSekolah.this, "Silahkan Isi kolom imunisasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(InputBulanImunisasiAnakSekolah.this);
                    if (new ConnectionDetector(InputBulanImunisasiAnakSekolah.this).isConnectingToInternet()) {
                        if (DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil().equals("")) {

                        } else {
                            SendDataToServerHandler.sendDataKeteranganImunisasi sendDataKeteranganImunisasi = sendDataToServerHandler.new sendDataKeteranganImunisasi();
                            sendDataKeteranganImunisasi.execute();
                        }
                        DatabaseVariable.modelInputBIAS.setId_anak(id_anak);
                        DatabaseVariable.modelInputBIAS.setTingkatan(headerTableHorizontal[Integer.parseInt(coodinateX) + 1]);
                        DatabaseVariable.modelInputBIAS.setTanggal_pemberian(datesWasPick);
                        DatabaseVariable.modelInputBIAS.setCoordinate_x(coodinateX);
                        DatabaseVariable.modelInputBIAS.setCoordinate_y(coodinateY);

                        SendDataToServerHandler.sendDataBias sendDataBias = sendDataToServerHandler.new sendDataBias();
                        sendDataBias.execute();
                    } else {
                        new AlertDialog.Builder(InputBulanImunisasiAnakSekolah.this)
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
                    info.setVisibility(View.GONE);
                    saveData.setVisibility(View.GONE);
                } else {
                    tableLayout.removeAllViews();
                    loadData(anakke.getSelectedItem().toString());
                    nama_anak.setText("Nama Anak : " + anakke.getSelectedItem().toString());
                    position = i;
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
                    if (id_anak.equalsIgnoreCase("") || id_anak.equalsIgnoreCase(null)) {
                        Toast.makeText(this, "Pilih Anak Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        new FragmentKeteranganImunisasi(this, String.valueOf(id_anak));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.refreshData:
                if (id_anak.equalsIgnoreCase("") || id_anak.equalsIgnoreCase(null)) {
                    Toast.makeText(this, "Pilih Anak Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                } else {
                    new GetBIAS().execute(new String[]{String.valueOf(id_anak)});
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
        c = selectQuery.getDataFor_Info_Anak();
        while (c.moveToNext()) {
            strings.add(c.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(InputBulanImunisasiAnakSekolah.this, android.R.layout.simple_dropdown_item_1line, strings);
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
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                rows1.setLayoutParams(layoutParams);
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(5, 5, 5, 5);

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
                tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tv1.setGravity(Gravity.CENTER);
                tv1.setTextSize(18);
                tv1.setTextColor(BLACK);
                tv1.setBackground(getResources().getDrawable(R.drawable.cell_shape_blue));
                tv1.setPadding(5, 5, 5, 5);
                tv1.setText(headerTableVertical[i]);
                row.addView(tv1);
                // inner for loop
                for (int j = 0; j < headerTableHorizontal.length - 1; j++) {
                    TextView tv12 = new TextView(getApplicationContext());
                    tv12.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                    final TextView finalTv1 = tv12;
                    final int finalI = i; //coodinateY
                    final int finalJ = j; //coodinateX
                    tv12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String dateSelected = finalTv1.getText().toString();
//                            if (String.valueOf(finalTv1.getTextColors()).equalsIgnoreCase("ColorStateList{mStateSpecs=[[]]mColors=[-7042476]mDefaultColor=-7042476}")) {
                            if (String.valueOf(finalTv1.getText()).equalsIgnoreCase(brown)) {

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(InputBulanImunisasiAnakSekolah.this);
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
                                                finalTv1.setText(String.valueOf(year) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, dayOfMonth));
                                                datesWasPick = String.valueOf(year) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, month + 1) + ConfigureApps.DATE_SEPARATOR + String.format(ConfigureApps.DATE_FORMATER, dayOfMonth);
                                                coodinateX = String.valueOf(finalJ);
                                                coodinateY = String.valueOf(finalI);
                                            }
                                        };

                                        DatePickerDialog datePickerDialog = new DatePickerDialog(InputBulanImunisasiAnakSekolah.this, dateSetListener, year, month, day);

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


                    if (j == 0 && i == 0) {
                        tv12.setBackgroundResource(R.drawable.cell_shape);
                        tv12.setTextColor(TRANSPARENT);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(dummyDate);

                    }
                    if (j == 0 && i == 1) {
                        tv12.setBackgroundResource(R.drawable.cell_shape);
                        tv12.setTextColor(TRANSPARENT);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(dummyDate);

                    }
                    if (j == 0 && i == 2) {
                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                        tv12.setTextColor(Color.parseColor("#948A54"));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(brown);

                    }

                    if ((j == 1 && i == 2) || (j == 2 && i == 2)) {
                        tv12.setBackgroundResource(R.drawable.cell_shape);
                        tv12.setTextColor(TRANSPARENT);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(dummyDate);

                    }
                    if ((j == 1 && i == 0) || (j == 2 && i == 0) || (j == 1 && i == 1) || (j == 2 && i == 1)) {
                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                        tv12.setTextColor(Color.parseColor("#948A54"));
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(5, 5, 5, 5);
                        tv12.setText(brown);

                    }
                    try {
                        c = selectQuery.getBIAS(Integer.parseInt(id_anak));
                        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                            String[] spliter = c.getString(4).split("\\.");
                            Log.i("x,y", c.getString(4));
                            coordinateX = Integer.parseInt(spliter[0]);
                            coordinateY = Integer.parseInt(spliter[1]);
                            Log.i("x", String.valueOf(coordinateX));
                            Log.i("y", String.valueOf(coordinateY));
                            if (j == coordinateX && i == coordinateY) {
                                if (c.getString(2).equalsIgnoreCase(dummyDate) || c.getString(2).equalsIgnoreCase("")) {
                                    if (j == 0 && i == 0) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setTextColor(Color.WHITE);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(dummyDate);
                                    }
                                    if (j == 0 && i == 1) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setTextColor(Color.WHITE);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(dummyDate);
                                    }
                                    if (j == 0 && i == 2) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(brown);
                                    }

                                    if ((j == 1 && i == 2) || (j == 2 && i == 2)) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setTextColor(Color.WHITE);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(dummyDate);
                                    }
                                    if ((j == 1 && i == 0) || (j == 2 && i == 0) || (j == 1 && i == 1) || (j == 2 && i == 1)) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(brown);
                                    }


                                } else {
                                    if (j == 0 && i == 0) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setTextColor(Color.BLACK);
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                    }
                                    if (j == 0 && i == 1) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setTextColor(Color.BLACK);
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                    }
                                    if (j == 0 && i == 2) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape_brown);
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setText(brown);
                                    }

                                    if ((j == 1 && i == 2) || (j == 2 && i == 2)) {
                                        tv12.setBackgroundResource(R.drawable.cell_shape);
                                        tv12.setGravity(Gravity.CENTER);
                                        tv12.setTextSize(18);
                                        tv12.setPadding(5, 5, 5, 5);
                                        tv12.setTextColor(Color.BLACK);
                                        tv12.setText(new DateSplitter().DateSplitter(c.getString(2)));
                                    }
                                    if ((j == 1 && i == 0) || (j == 2 && i == 0) || (j == 1 && i == 1) || (j == 2 && i == 1)) {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    row.addView(tv12);
                }
                tableLayout.addView(row);
            }
        }
    }

    public class GetBIAS extends AsyncTask<String, JSONObject, String> {

        InsertQuery insertQuery;
        JSONObject jsonObject;
        HashMap<String, String> params;
        String result;
        JSONHander jsonHander;
        DeleteQuery deleteQuery;
        ProgressDialog progressDialog;

        public GetBIAS() {
            insertQuery = new InsertQuery(InputBulanImunisasiAnakSekolah.this);
            jsonHander = new JSONHander(InputBulanImunisasiAnakSekolah.this);
            deleteQuery = new DeleteQuery(InputBulanImunisasiAnakSekolah.this);
            deleteQuery.DeletImunBIAS();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InputBulanImunisasiAnakSekolah.this);
            progressDialog.setMessage("Mengunduh data...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.BIAS);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_BIAS = "detail_imun_bias";
            JSONArray detail_imun_bias;
            String TAG_SUCCESS_IMUN_BIAS = "success_imun_bias";

            super.onPostExecute(s);

            progressDialog.dismiss();
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IMUN_BIAS).equalsIgnoreCase("0")) {
                        detail_imun_bias = jsonObject.getJSONArray(TAG_DETAIL_IMUN_BIAS);
                        for (int i = 0; i < detail_imun_bias.length(); i++) {
                            JSONObject anakDetail = detail_imun_bias.getJSONObject(i);

                            DatabaseVariable.modelInputBIAS.setId_anak(anakDetail.getString("id_anak"));

                            DatabaseVariable.modelInputBIAS.setId_jenis_imunisasi(anakDetail.getString("id_jenis_imunisasi"));

                            DatabaseVariable.modelInputBIAS.setTingkatan(anakDetail.getString("tingkatan_kelas"));

                            DatabaseVariable.modelInputBIAS.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");
                            DatabaseVariable.modelInputBIAS.setCoordinate_x(params[0]);
                            DatabaseVariable.modelInputBIAS.setCoordinate_y(params[1]);

                            insertQuery.insertImunBIAS();
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
