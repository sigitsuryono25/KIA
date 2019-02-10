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
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

import asia.sayateam.kiaclient.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.KesehatanAnakFragment.PemberianVitaminFragment;
import asia.sayateam.kiaclient.R;
import asia.sayateam.kiaclient.SubMenuImunisasi;
import asia.sayateam.kiaclient.library.TitleToobarCustom;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;

/**
 * Created by Sigit Suryono on 009, 09-09-2017.
 */

public class NewImunisasiVaksinLain extends AppCompatActivity {

    String[] headerTableHorizontal = {"VAKSIN", dummyDate, "Tanggal", "Pemberian", dummyDate};
    DatabaseHadler databaseHadler;
    SQLiteDatabase database;

    Toolbar toolbar;
    Calendar calendar;
    TableLayout tableLayout;
    int year, month, day;
    SelectQuery selectQuery;
    ScrollView layout;
    TextView tvH1, tvH2, tvH0;
    Cursor c;
    String anakke;
    Intent intent;
    int id_anak;
    TextView nama_anak, namaOrtu, ttl;
    public static final String dummyDate = "0000-00-00";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_imunisasi_tambahan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(
                new TitleToobarCustom().TitleToobarCustom("Vaksin Lain", Color.WHITE));

        tableLayout = (TableLayout) findViewById(R.id.tlGridTable);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        selectQuery = new SelectQuery(this);
        layout = (ScrollView) findViewById(R.id.layout);


        nama_anak = (TextView) findViewById(R.id.nama_anak);
        ttl = (TextView) findViewById(R.id.ttl);
        namaOrtu = (TextView) findViewById(R.id.namaOrtu);

        databaseHadler = new DatabaseHadler(this);
        database = databaseHadler.OpenDatabase();

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
        String idIbu = "";
        c = selectQuery.getDetailAnakKeById(anakke);

        while (c.moveToNext()) {
            id_anak = Integer.parseInt(c.getString(0));
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
        String namaVaksin = "";
        for (int k = 0; k < 1; k++) {
            TableRow rows1 = new TableRow(getApplicationContext());

            //nama vaksin
            for (int j = 0; j < 1; j++) {
                c = database.rawQuery("SELECT `nama_vaksin` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.0' ", null);
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
                        namaVaksin = c.getString(0);
                        tvH0.setText(c.getString(0));
                    }

                    if (tvH0.getText().toString().equalsIgnoreCase(dummyDate)) {
                        tvH0.setTextColor(TRANSPARENT);
                    } else {
                        tvH0.setTextColor(BLACK);
                    }

                    rows1.addView(tvH0);
                }
            }

            //date
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.0' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.1' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.2' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));


                    rows1.addView(tv);
                }
            }

            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='0.3' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

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
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.0' ", null);
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
                        namaVaksin = c.getString(0);
                        tvH1.setText(c.getString(0));
                    }

                    if (tvH1.getText().toString().equalsIgnoreCase(dummyDate)) {
                        tvH1.setTextColor(TRANSPARENT);
                    } else {
                        tvH1.setTextColor(BLACK);
                    }

                    rows1.addView(tvH1);
                }
            }

            //date
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.0' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));


                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.1' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.2' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));
                    rows1.addView(tv);
                }
            }

            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='1.3' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

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
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.0' ", null);
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
                        namaVaksin = c.getString(0);
                        tvH2.setText(c.getString(0));
                    }

                    if (tvH2.getText().toString().equalsIgnoreCase(dummyDate)) {
                        tvH2.setTextColor(TRANSPARENT);
                    } else {
                        tvH2.setTextColor(BLACK);
                    }
                    rows1.addView(tvH2);
                }
            }

            //date
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.0' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.1' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

                    rows1.addView(tv);
                }
            }
            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.2' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));

                    rows1.addView(tv);
                }
            }

            for (int j = 1; j < 2; j++) {
                c = database.rawQuery("SELECT `tanggal_pemberian` FROM " +
                        "`imunisasi_lain` WHERE `id_anak`='" + id_anak + "' and `coordinate`='2.3' ", null);
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
                    tv.setText(new PemberianVitaminFragment().dateSpliter(c.getString(0)));


                    rows1.addView(tv);
                }
            }

            tableLayout.addView(rows1);
        }

    }
}

