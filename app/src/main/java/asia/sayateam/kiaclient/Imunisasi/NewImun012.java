package asia.sayateam.kiaclient.Imunisasi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.ScrollView;
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
 * Created by Sigit Suryono on 029, 29-Aug-17.
 */

public class NewImun012 extends AppCompatActivity {

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

    public static final String dummyDate = "0000-00-00";
    int id_anak;

    Toolbar toolbar;

    DatabaseHadler databaseHadler;
    SQLiteDatabase database;
    SelectQuery selectQuery;
    ScrollView layout;
    Cursor c;
    String anakke;
    Intent intent;
    TextView nama_anak, namaOrtu, ttl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_imunisasi_anak_0_12);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Imunisasi 0-12 Bulan", Color.WHITE));

        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        layout = (ScrollView) findViewById(R.id.layout);

        nama_anak = (TextView) findViewById(R.id.nama_anak);
        ttl = (TextView) findViewById(R.id.ttl);
        namaOrtu = (TextView) findViewById(R.id.namaOrtu);


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        securePreferences = new SecurePreferences(getApplicationContext(), getPackageName().toLowerCase(), "NrGWse07", true);

        databaseHadler = new DatabaseHadler(getApplicationContext());
        database = databaseHadler.OpenDatabase();
        selectQuery = new SelectQuery(getApplicationContext());

        intent = getIntent();
        try {
            anakke = intent.getStringExtra(SubMenuImunisasi.ANAKKE);
            id_anak = Integer.parseInt(anakke);
            loadData(String.valueOf(id_anak));
        } catch (Exception e) {
            e.printStackTrace();
        }

        createData();
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
                try {

                    //WHITE
                    if (j == 0 && i == 0) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
                        tv12.setGravity(Gravity.CENTER);
                        tv12.setTextSize(18);
                        tv12.setPadding(10, 10, 10, 10);
                        c = selectQuery.getImun0_12(id_anak, i, j);
                        if (c.getCount() > 0) {
                            while (c.moveToNext()) {
                                if (!c.getString(2).equalsIgnoreCase(dummyDate)) {
                                    tv12.setTextColor(Color.BLACK);
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 0 && i == 1) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 0 && i == 2) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 1 && i == 1) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 1 && i == 2) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 4 && i == 7) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 4 && i == 8) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }
                    if (j == 4 && i == 9) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

                        }
                    }

                    if (j == 9 && i == 10) {
                        tv12.setBackground(getResources().getDrawable(R.drawable.cell_shape));
                        tv12.setTextColor(Color.BLACK);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                tv12.setTextColor(Color.BLACK);
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
                                            tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                        } else {
                                            tv12.setTextColor(Color.TRANSPARENT);
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
                                tv12.setTextColor(Color.BLACK);
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
                                            tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                        } else {
                                            tv12.setTextColor(Color.TRANSPARENT);
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
                                tv12.setTextColor(Color.BLACK);
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
                                            tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                        } else {
                                            tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                        tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                    } else {
                                        tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
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
                                    tv12.setText(new PemberianVitaminFragment().dateSpliter(c.getString(2)));
                                } else {
                                    tv12.setTextColor(Color.TRANSPARENT);
                                    tv12.setText(dummyDate);
                                }
                            }
                        } else {

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
