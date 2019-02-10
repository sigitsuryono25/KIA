package asia.sayateam.kiaclient.Imunisasi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.Calendar;

import asia.sayateam.kiaclient.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.KesehatanAnakFragment.PemberianVitaminFragment;
import asia.sayateam.kiaclient.R;
import asia.sayateam.kiaclient.SubMenuImunisasi;
import asia.sayateam.kiaclient.library.TitleToobarCustom;

import static android.graphics.Color.BLACK;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class ImunisasiAnakDiatas1Tahun extends AppCompatActivity {

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
            "25+"
    };
    String[] headerTableVertical = {
            "DPT-HB-Hib 1",
            "Campak"
    };

    TableLayout tableLayout;
    Toolbar toolbar;
    String dummyDate = "0000-00-00";

        SelectQuery selectQuery;
        DatabaseHadler databaseHadler;
        int coordinateX, coordinateY;
        String anakke;
        String date;
        int id_anak;
        TextView nama_anak, namaOrtu, ttl;
        Intent intent;
        Cursor c;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_input_imunisasi_anak_diatas_1_tahun);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(
                    new TitleToobarCustom().TitleToobarCustom("Imunisasi Diatas 1 Tahun", Color.WHITE));
            tableLayout = (TableLayout) findViewById(R.id.tlGridTable);

            nama_anak = (TextView) findViewById(R.id.nama_anak);
            ttl = (TextView) findViewById(R.id.ttl);
            namaOrtu = (TextView) findViewById(R.id.namaOrtu);

            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            databaseHadler = new DatabaseHadler(ImunisasiAnakDiatas1Tahun.this);
            selectQuery = new SelectQuery(ImunisasiAnakDiatas1Tahun.this);

            intent = getIntent();
            try {
                anakke = intent.getStringExtra(SubMenuImunisasi.ANAKKE);
                id_anak = Integer.parseInt(anakke);
                loadData(String.valueOf(id_anak));
            } catch (Exception e) {
                e.printStackTrace();
            }

            CreateData();
        }

    public void loadData(String anakke) {
        Log.i("Ex", "Execute");

        String idIbu = "";
        c = selectQuery.getDetailAnakKeById(anakke);

        while (c.moveToNext()) {
            ttl.setText("Tanggal Lahir : " + new PemberianVitaminFragment().dateSpliter(c.getString(6)));
            nama_anak.setText("Nama Anak : " + c.getString(1));
            idIbu = c.getString(10);
        }

        c = selectQuery.getNamaIbu(idIbu);
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
                    TextView tv12 = new TextView(getApplicationContext());
                    tv12.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

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
                        }

                    }

                    tv12.setGravity(Gravity.CENTER);
                    tv12.setTextSize(18);
                    tv12.setPadding(10, 10, 10, 10);
                    tv12.setText(dummyDate);

                    try {
                        c = selectQuery.getImunDiatas1Tahun(id_anak);
                        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                            String[] spliter = c.getString(4).split("\\.");
                            Log.i("x,y", c.getString(4));
                            coordinateX = Integer.parseInt(spliter[0]);
                            coordinateY = Integer.parseInt(spliter[1]);
                            Log.i("x", String.valueOf(coordinateX));
                            Log.i("y", String.valueOf(coordinateY));
                            if (j == coordinateX && i == coordinateY) {
                                if (c.getString(3).equalsIgnoreCase(dummyDate) || c.getString(3).equalsIgnoreCase("")) {

                                } else {
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
                                        }

                                    }

                                    tv12.setGravity(Gravity.CENTER);
                                    tv12.setTextSize(18);
                                    tv12.setPadding(10, 10, 10, 10);
                                    tv12.setTextColor(Color.BLACK);
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(3)));
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

}
