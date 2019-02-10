package asia.sayateam.kiaclient.Imunisasi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import asia.sayateam.kiaclient.library.SecurePreferences;
import asia.sayateam.kiaclient.library.TitleToobarCustom;

import static android.graphics.Color.BLACK;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class ImunisasiAnak0To12 extends AppCompatActivity {

    TableLayout tableLayout;
    int year, month, day;
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
            "12+"
    };
    String[] headerTableVertical = {
            "Hb0 (0-7 hari)",
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

    public static final String dummyDate = "2016-10-16";

    Toolbar toolbar;

    DatabaseHadler databaseHadler;
    SQLiteDatabase database;
    SelectQuery selectQuery;
    Cursor c;
    Intent intent;
    int id_anak;
    int coordinateX, coordinateY;
    String[] date;
    String anakke;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_imunisasi_anak_0_12);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Imunisasi 0-12 Bulan", Color.WHITE));

        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        securePreferences = new SecurePreferences(getApplicationContext(),
                getPackageName().toLowerCase(), "NrGWse07", true);

        databaseHadler = new DatabaseHadler(getApplicationContext());
        database = databaseHadler.OpenDatabase();
        selectQuery = new SelectQuery(getApplicationContext());

        intent = getIntent();
        try {
            anakke = intent.getStringExtra(SubMenuImunisasi.ANAKKE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseHadler = new DatabaseHadler(ImunisasiAnak0To12.this);
        selectQuery = new SelectQuery(ImunisasiAnak0To12.this);

        c = selectQuery.getDetailAnakKe(anakke);
        while (c.moveToNext()) {
            id_anak = c.getInt(0);
        }

        createData();
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

            for (int j = 1; j < 14; j++) {
                final TextView tv12 = new TextView(getApplicationContext());
                tv12.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                //Set data

                //End of Set Data


                try {
//                    c = selectQuery.getImun0_12(id_anak);
                    for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                        String[] spliter = c.getString(4).split("\\.");
                        coordinateX = Integer.parseInt(spliter[0]);
                        coordinateY = Integer.parseInt(spliter[1]);
                        if (i == coordinateY &&  j== coordinateX) {
                            if (c.getString(2).equalsIgnoreCase(dummyDate) || c.getString(2).equalsIgnoreCase("")) {
                                for (int a = 2; a < 14; a++) {
                                    if (j == 1 && i == 0) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 0) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                }
                                for (int a = 3; a < 13; a++) {
                                    if ((j == 1 && i == 1) || (j == 2 && i == 1)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 1) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                    if (j == 13 && i == 1) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                }

                                for (int a = 3; a < 13; a++) {
                                    if ((j == 1 && i == 2) || (j == 2 && i == 2)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 2) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 4; a < 13; a++) {
                                    if ((j == 1 && i == 3) || (j == 2 && i == 3)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                    if (j == 3 && i == 3) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }

                                    if (j == a && i == 3) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }
                                for (int a = 4; a < 13; a++) {
                                    if ((j == 1 && i == 4) || (j == 2 && i == 4)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                    if (j == 3 && i == 4) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }

                                    if (j == a && i == 4) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 5; a < 13; a++) {
                                    if ((j == 1 && i == 5) || (j == 2 && i == 5) || (j == 3 && i == 5)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (j == 4 && i == 5) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 5) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 5; a < 13; a++) {
                                    if ((j == 1 && i == 6) || (j == 2 && i == 6) || (j == 3 && i == 6)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (j == 4 && i == 6) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 6) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 6; a < 13; a++) {
                                    if ((j == 1 && i == 7) || (j == 2 && i == 7) || (j == 3 && i == 7) || (j == 4 && i == 7)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (j == 5 && i == 7) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 7) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 6; a < 13; a++) {
                                    if ((j == 1 && i == 8) || (j == 2 && i == 8) || (j == 3 && i == 8) || (j == 4 && i == 8)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (j == 5 && i == 8) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 8) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }
                                for (int a = 6; a < 13; a++) {
                                    if ((j == 1 && i == 9) || (j == 2 && i == 9) || (j == 3 && i == 9) || (j == 4 && i == 9)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (j == 5 && i == 9) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 9) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 1; a < 10; a++) {
                                    if (j == a && i == 10) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                    if (j == 10 && i == 10) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if ((j == 11 && i == 10) || (j == 12 && i == 10)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 2; a <= 10; a++) {
                                    if (j == 13 && i == a) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_red));
                                        tv12.setTextColor(Color.parseColor("#FF0000"));

                                    }
                                }

                                tv12.setGravity(Gravity.CENTER);
                                tv12.setTextSize(18);
                                tv12.setPadding(10, 10, 10, 10);
                                tv12.setText(dummyDate);

                            } else {
                                for (int a = 2; a < 14; a++) {
                                    if (j == 1 && i == 0) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (j == a && i == 0) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                }
                                for (int a = 3; a < 13; a++) {
                                    if ((i == 1 && j == 1) || (i == 2 && i == j)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 1) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                    if (i == 13 && j == 1) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                }

                                for (int a = 3; a < 13; a++) {
                                    if ((i == 1 && j == 2) || (i == 2 && j == 2)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 2) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 4; a < 13; a++) {
                                    if ((i == 1 && j == 3) || (i == 2 && j == 3)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                    if (i == 3 && j == 3) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }

                                    if (i == a && j == 3) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }
                                for (int a = 4; a < 13; a++) {
                                    if ((i == 1 && j == 4) || (i == 2 && j == 4)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                    if (i == 3 && j == 4) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }

                                    if (i == a && j == 4) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 5; a < 13; a++) {
                                    if ((i == 1 && j == 5) || (i == 2 && j == 5) || (i == 3 && j == 5)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (i == 4 && j == 5) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 5) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 5; a < 13; a++) {
                                    if ((i == 1 && j == 6) || (i == 2 && j == 6) || (i == 3 && j == 6)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (i == 4 && j == 6) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 6) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 6; a < 13; a++) {
                                    if ((i == 1 && j == 7) || (i == 2 && j == 7) || (i == 3 && j == 7) || (i == 4 && j == 7)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (i == 5 && j == 7) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 7) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 6; a < 13; a++) {
                                    if ((i == 1 && j == 8) || (i == 2 && j == 8) || (i == 3 && j == 8) || (i == 4 && j == 8)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (i == 5 && j == 8) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 8) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }
                                for (int a = 6; a < 13; a++) {
                                    if ((i == 1 && j == 9) || (i == 2 && j == 9) || (i == 3 && j == 9) || (i == 4 && j == 9)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }

                                    if (i == 5 && j == 9) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if (i == a && j == 9) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 1; a < 10; a++) {
                                    if (i == a && j == 10) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_brown));
                                        tv12.setTextColor(Color.parseColor("#948A54"));
                                    }
                                    if (i == 10 && j == 10) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                                        tv12.setTextColor(Color.WHITE);
                                    }
                                    if ((i == 11 && j == 10) || (i == 12 && j == 10)) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_yellow));
                                        tv12.setTextColor(Color.parseColor("#FFFF00"));
                                    }
                                }

                                for (int a = 2; a <= 10; a++) {
                                    if (i == 13 && j == a) {
                                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape_red));
                                        tv12.setTextColor(Color.parseColor("#FF0000"));

                                    }
                                }

                                tv12.setGravity(Gravity.CENTER);
                                tv12.setTextSize(18);
                                tv12.setPadding(10, 10, 10, 10);
                                tv12.setTextColor(Color.BLACK);
                                tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
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

//    public void createData() {
//        for (int k = 0; k < 1; k++) {
//            TableRow rows1 = new TableRow(getApplicationContext());
//            for (int j = 0; j < 14; j++) {
//                TextView tv = new TextView(getApplicationContext());
//                LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
//                        LayoutParams.MATCH_PARENT);
//                rows1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                        LayoutParams.MATCH_PARENT));
//                if (j == 0) {
//                    tv.setGravity(Gravity.CENTER);
//                    tv.setPadding(5, 5, 5, 5);
//                } else {
//                    tv.setGravity(Gravity.LEFT);
//                    layoutParams.setMargins(0, 0, 0, 100 - 110);
//                    tv.setPadding(5, 5, 5, 10);
//                }
//                tv.setLayoutParams(layoutParams);
//                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.cell_shape_green));
//                tv.setTextSize(18);
//                tv.setTextColor(Color.RED);
//                tv.setText(headerTableHorizontal[j]);
//                rows1.addView(tv);
//            }
//            tableLayout.addView(rows1);
//        }
//
//        TableRow row;
//
//        for (int i = 0; i <= 10; i++) {
//            // inner for loop
//            row = new TableRow(getApplicationContext());
//            final TextView tv1 = new TextView(getApplicationContext());
//            tv1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                    LayoutParams.MATCH_PARENT));
//            tv1.setGravity(Gravity.CENTER);
//            tv1.setTextSize(18);
//            tv1.setTextColor(BLACK);
//            tv1.setBackground(getResources().getDrawable(R.drawable.cell_shape_blue));
//            tv1.setPadding(5, 5, 5, 5);
//            tv1.setText(headerTableVertical[i]);
//            row.addView(tv1);
//
//            for (int j = 1; j < 14; j++) {
//                final TextView tv12 = new TextView(getApplicationContext());
//                tv12.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//                        LayoutParams.MATCH_PARENT));
//
//                for (int a = 2; a < 14; a++) {
//                    if (j == 1 && i == 0) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 0) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//                }
//                for (int a = 3; a < 13; a++) {
//                    if ((j == 1 && i == 1) || (j == 2 && i == 1)) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 1) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                    if (j == 13 && i == 1) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//                }
//
//                for (int a = 3; a < 13; a++) {
//                    if ((j == 1 && i == 2) || (j == 2 && i == 2)) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 2) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 4; a < 13; a++) {
//                    if ((j == 1 && i == 3) || (j == 2 && i == 3)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//                    if (j == 3 && i == 3) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape));
//                    }
//
//                    if (j == a && i == 3) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//                for (int a = 4; a < 13; a++) {
//                    if ((j == 1 && i == 4) || (j == 2 && i == 4)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//                    if (j == 3 && i == 4) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape));
//                    }
//
//                    if (j == a && i == 4) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 5; a < 13; a++) {
//                    if ((j == 1 && i == 5) || (j == 2 && i == 5) || (j == 3 && i == 5)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//
//                    if (j == 4 && i == 5) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 5) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 5; a < 13; a++) {
//                    if ((j == 1 && i == 6) || (j == 2 && i == 6) || (j == 3 && i == 6)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//
//                    if (j == 4 && i == 6) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 6) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 6; a < 13; a++) {
//                    if ((j == 1 && i == 7) || (j == 2 && i == 7)
//                            || (j == 3 && i == 7) || (j == 4 && i == 7)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//
//                    if (j == 5 && i == 7) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 7) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 6; a < 13; a++) {
//                    if ((j == 1 && i == 8) || (j == 2 && i == 8)
//                            || (j == 3 && i == 8) || (j == 4 && i == 8)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//
//                    if (j == 5 && i == 8) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 8) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//                for (int a = 6; a < 13; a++) {
//                    if ((j == 1 && i == 9) || (j == 2 && i == 9)
//                            || (j == 3 && i == 9) || (j == 4 && i == 9)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//
//                    if (j == 5 && i == 9) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if (j == a && i == 9) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 1; a < 10; a++) {
//                    if (j == a && i == 10) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_brown));
//                    }
//                    if (j == 10 && i == 10) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                    }
//                    if ((j == 11 && i == 10) || (j == 12 && i == 10)) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_yellow));
//                    }
//                }
//
//                for (int a = 2; a <= 10; a++) {
//                    if (j == 13 && i == a) {
//                        tv12.setBackground(getResources().
//                                getDrawable(R.drawable.cell_shape_red));
//
//                    }
//                }
//
//                tv12.setGravity(Gravity.CENTER);
//                tv12.setTextSize(18);
//                tv12.setPadding(10, 10, 10, 10);
//                tv12.setText(dummyDate);
//                tv12.setTextColor(Color.TRANSPARENT);
//
//                try {
//                    c = selectQuery.getImun0_12(id_anak);
//                    while (c.moveToNext()) {
//                        String[] spliter = c.getString(4).split("\\.");
//                        coordinateX = Integer.parseInt(spliter[0]);
//                        coordinateY = Integer.parseInt(spliter[1]);
//                        date = c.getString(2).split("-");
//
//                    }
//                    if (i == coordinateX && j == coordinateY) {
//                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
//                        tv12.setTextColor(Color.BLACK);
//                        tv12.setText(date[2] + "-" + date[1] + "-" + date[0]);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    tv12.setTextColor(Color.TRANSPARENT);
//                }
//                row.addView(tv12);
//            }
//
//            tableLayout.addView(row);
//        }
//
//    }

}
