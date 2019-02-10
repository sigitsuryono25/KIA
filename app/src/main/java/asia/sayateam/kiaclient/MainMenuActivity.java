package asia.sayateam.kiaclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import asia.sayateam.kiaclient.Adapter.MainMenuGridAdapter;
import asia.sayateam.kiaclient.AsyncTask.GetDataToServerHandler;
import asia.sayateam.kiaclient.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.Imunisasi.StatusGizi;
import asia.sayateam.kiaclient.config.PrefencesTambahData;
import asia.sayateam.kiaclient.config.SecurePreferences;
import asia.sayateam.kiaclient.library.ConnectionDetector;

/**
 * Created by sigit on 29/07/17.
 */

public class MainMenuActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener
        , View.OnTouchListener {

    GridView gridView;
    SelectQuery selectQuery;
    Cursor cursor;
    LinearLayout reload, tentang;
    RelativeLayout header;
    DeleteQuery deleteQuery;
    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout more;
    SecurePreferences securePreferences;

    String[] nama_menu = {"Informasi Peserta",
            "Catatan Kes. Bumil",
            "Catatan Kes. Anak",
            "Status Imunisasi",
            "Status Gizi",
            "Lain-lain"
    };

    int[] icon = {R.mipmap.ic_info_peserta,
            R.mipmap.ic_kes_bumil,
            R.mipmap.ic_kes_anak,
            R.mipmap.ic_stat_imun,
            R.mipmap.ic_stat_gizi,
            R.mipmap.ic_lain_lain,
    };
    String[] color = {
            "#FFFFFF",
            "#FFFFFF",
            "#FFFFFF",
            "#FFFFFF",
            "#FFFFFF",
            "#FFFFFF"
    };

    GetDataToServerHandler getDataToServerHandler;
    GetDataToServerHandler.GetImunisasi0_12 getImunisasi0_12;
    GetDataToServerHandler.GetImunisasi1TahunPlus getImunisasi1TahunPlus;
    GetDataToServerHandler.GetImunisasiLain getImunisasiLain;
    GetDataToServerHandler.GetBIAS getBIAS;
    GetDataToServerHandler.GetImunisasiTambahan getImunisasiTambahan;
    GetDataToServerHandler.GetStatusGizi getStatusGizi;
    GetDataToServerHandler.GetCatatanKesehatanAnak getCatatanKesehatanAnak;
    GetDataToServerHandler.GetCatatanKesBuMil getCatatanKesBuMil;
    GetDataToServerHandler.GetTentang getTentang;
    GetDataToServerHandler.GetlainLain getlainLain;

    RelativeLayout enable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_menu);

        selectQuery = new SelectQuery(MainMenuActivity.this);
        deleteQuery = new DeleteQuery(this);
        getDataToServerHandler = new GetDataToServerHandler(MainMenuActivity.this);
        securePreferences = new SecurePreferences(this, GetDataToServerHandler.PREF_NAME, PrefencesTambahData.ENCRYPT_KEY, true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        enable = (RelativeLayout) findViewById(R.id.enable);
        header = (RelativeLayout) findViewById(R.id.header);
        more = (FrameLayout) findViewById(R.id.more);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_red_light),
                getResources().getColor(android.R.color.holo_orange_light));

        gridView = (GridView) findViewById(R.id.gridMenu);
        gridView.setAdapter(new MainMenuGridAdapter(MainMenuActivity.this, nama_menu, icon, color));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainMenuActivity.this, InformasiActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainMenuActivity.this, KesehatanBuMilActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainMenuActivity.this, CatatanKesehatanAnakActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainMenuActivity.this, SubMenuImunisasi.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainMenuActivity.this, StatusGizi.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainMenuActivity.this, Lain.class));
                        break;

                }
            }
        });

        reload = (LinearLayout) findViewById(R.id.reload);
        tentang = (LinearLayout) findViewById(R.id.tentang);

        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, TentangAplikasi.class));
            }
        });

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        if (new ConnectionDetector(MainMenuActivity.this).isConnectingToInternet()) {
                                            securePreferences.clear();
                                            deleteQuery.DeletKesehatanIbu();
                                            deleteQuery.DeletImun012();
                                            deleteQuery.DeletImun1TahunPlus();
                                            deleteQuery.DeletImunLain();
                                            deleteQuery.DeletImunBIAS();
                                            deleteQuery.DeletImunTambahan();
                                            deleteQuery.DeletStatusGizi();
                                            deleteQuery.DeletKesehatanAnak();

                                            loadData();
                                        } else {
                                            new AlertDialog.Builder(MainMenuActivity.this)
                                                    .setMessage("Tidak ada Koneksi Internet")
                                                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface
                                                                    .dismiss();
                                                        }
                                                    })
                                                    .create().show();
                                            swipeRefreshLayout.setRefreshing(false);
                                        }
                                    }
                                }
        );


        gridView.setOnTouchListener(this);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedBack();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainMenuActivity.this)
                .setMessage("keluar dari aplikasi ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteQuery deleteQuery = new DeleteQuery(MainMenuActivity.this);
                        deleteQuery.DeletImun1TahunPlus();
                        deleteQuery.DeletImun012();
                        deleteQuery.DeletImunBIAS();
                        deleteQuery.DeletImunLain();
                        deleteQuery.DeletImunTambahan();
                        deleteQuery.DeletKesehatanAnak();
                        deleteQuery.DeletKesehatanIbu();
                        deleteQuery.DeletKesehatanIbu();
                        deleteQuery.DeletStatusGizi();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public void loadData() {
        Log.i("This", "Executed");
        getTentang = getDataToServerHandler.new GetTentang();
        getTentang.execute();

        getlainLain = getDataToServerHandler.new GetlainLain();
        getlainLain.execute();

        cursor = selectQuery.getDataFor_Info_Anak();
        while (cursor.moveToNext()) {
            getImunisasi0_12 = getDataToServerHandler.new GetImunisasi0_12();
            getImunisasi0_12.execute(new String[]{cursor.getString(0)});

            getImunisasi1TahunPlus = getDataToServerHandler.new GetImunisasi1TahunPlus();
            getImunisasi1TahunPlus.execute(new String[]{cursor.getString(0)});

            getImunisasiLain = getDataToServerHandler.new GetImunisasiLain();
            getImunisasiLain.execute(new String[]{cursor.getString(0)});

            getBIAS = getDataToServerHandler.new GetBIAS();
            getBIAS.execute(new String[]{cursor.getString(0)});

            getImunisasiTambahan = getDataToServerHandler.new GetImunisasiTambahan();
            getImunisasiTambahan.execute(new String[]{cursor.getString(0)});

            getStatusGizi = getDataToServerHandler.new GetStatusGizi();
            getStatusGizi.execute(new String[]{cursor.getString(0)});

            getCatatanKesehatanAnak = getDataToServerHandler.new GetCatatanKesehatanAnak();
            getCatatanKesehatanAnak.execute(new String[]{cursor.getString(0)});

        }

        cursor = null;

        cursor = selectQuery.getDataFor_Info_Ibu();
        while (cursor.moveToNext()) {
            getCatatanKesBuMil = getDataToServerHandler.new GetCatatanKesBuMil(swipeRefreshLayout, this);
            getCatatanKesBuMil.execute(new String[]{cursor.getString(11)});
        }

        cursor.close();

    }

    @Override
    public void onRefresh() {
        if (new ConnectionDetector(MainMenuActivity.this).isConnectingToInternet()) {
            deleteQuery.DeletKesehatanIbu();
            deleteQuery.DeletImun012();
            deleteQuery.DeletImun1TahunPlus();
            deleteQuery.DeletImunLain();
            deleteQuery.DeletImunBIAS();
            deleteQuery.DeletImunTambahan();
            deleteQuery.DeletStatusGizi();
            deleteQuery.DeletKesehatanAnak();

            loadData();
        } else {
            new AlertDialog.Builder(MainMenuActivity.this)
                    .setMessage("Tidak ada Koneksi Internet")
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface
                                    .dismiss();
                        }
                    })
                    .create().show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            swipeRefreshLayout.setEnabled(false);
            return false;
        } else if (action == MotionEvent.ACTION_UP) {
            swipeRefreshLayout.setEnabled(true);
            return false;
        }

        return false;
    }


    public void feedBack() {
        RelativeLayout layout;
        View view;
        LayoutInflater inflater;

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenuActivity.this);
        inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.alert_dialog_custom, null);
        builder.setView(view);
        layout = view.findViewById(R.id.feed);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainMenuActivity.this, "Feedback", Toast.LENGTH_SHORT).show();

            }
        });

        builder.create().show();

    }
}