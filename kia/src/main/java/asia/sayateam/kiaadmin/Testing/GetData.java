package asia.sayateam.kiaadmin.Testing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 009, 09-09-2017.
 */

public class GetData extends AppCompatActivity {

    DatabaseHadler databaseHadler;
    SQLiteDatabase sqLiteDatabase;
    Cursor c;
    ArrayList<String> list;
    StableArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);

        databaseHadler = new DatabaseHadler(this);
        sqLiteDatabase = databaseHadler.OpenDatabase();

        Button imunLain = (Button) findViewById(R.id.imunisasi_lain);
        Button imunTambahan = (Button) findViewById(R.id.imunisasi_tambahan);
        Button jenisImun = (Button) findViewById(R.id.jenis_imun);

        sqLiteDatabase.execSQL("DELETE FROM " + DatabaseVariable.IMUN_LAIN);
        sqLiteDatabase.execSQL("DELETE FROM " + DatabaseVariable.IMUN_TAMBAHAN);
        sqLiteDatabase.execSQL("DELETE FROM jenis_imunisasi");

        list = new ArrayList<>();

        Button tampilkan_imunisasi_tambahan = (Button) findViewById(R.id.tampilkan_imunisasi_tambahan);
        Button tampilkan_imunisasi_lain = (Button) findViewById(R.id.tampilkan_imunisasi_lain);
        Button tampilkan_jenis_imun = (Button) findViewById(R.id.tampilkan_jenis_imun);

        final ListView result = (ListView) findViewById(R.id.result);

        imunLain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(GetData.this);
                GetDataToServerHandler.GetImunisasiLain getImunisasiLain = getDataToServerHandler.new GetImunisasiLain();
                getImunisasiLain.execute(new String[]{String.valueOf(2)});
            }
        });
        imunTambahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(GetData.this);
                GetDataToServerHandler.GetImunisasiTambahan getImunisasiTambahan = getDataToServerHandler.new GetImunisasiTambahan();
                getImunisasiTambahan.execute(new String[]{String.valueOf(2)});
            }
        });
        jenisImun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDataToServerHandler getDataToServerHandler = new GetDataToServerHandler(GetData.this);
                GetDataToServerHandler.GetJenisImunisasi getJenisImunisasi = getDataToServerHandler.new GetJenisImunisasi();
                getJenisImunisasi.execute();
            }
        });


        tampilkan_imunisasi_tambahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                c = sqLiteDatabase.rawQuery("SELECT * FROM imunisasi_tambahan", null);
                while (c.moveToNext()) {
                    list.add(c.getString(0) + ", " + c.getString(1)+ ", " + c.getString(2)+ ", " + c.getString(3));
                }

                arrayAdapter = new StableArrayAdapter(GetData.this, android.R.layout.simple_list_item_1, list);
                result.setAdapter(arrayAdapter);
            }
        });
        tampilkan_imunisasi_lain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                c = sqLiteDatabase.rawQuery("SELECT * FROM imunisasi_lain", null);
                while (c.moveToNext()) {
                    list.add(c.getString(0) + ", " + c.getString(1)+ ", " + c.getString(2)+ ", " + c.getString(3));
                }

                arrayAdapter = new StableArrayAdapter(GetData.this, android.R.layout.simple_list_item_1, list);
                result.setAdapter(arrayAdapter);
            }
        });
        tampilkan_jenis_imun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                c = sqLiteDatabase.rawQuery("SELECT * FROM jenis_imunisasi", null);
                while (c.moveToNext()) {
                    list.add(c.getString(0) + ", " + c.getString(1));
                }

                arrayAdapter = new StableArrayAdapter(GetData.this, android.R.layout.simple_list_item_1, list);
                result.setAdapter(arrayAdapter);
            }
        });

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
