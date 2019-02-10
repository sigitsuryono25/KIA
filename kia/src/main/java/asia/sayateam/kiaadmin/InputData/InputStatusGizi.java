package asia.sayateam.kiaadmin.InputData;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.InputDataInit;
import asia.sayateam.kiaadmin.R;
import asia.sayateam.kiaadmin.SubMenuInput;
import asia.sigitsuryono.kialibrary.SecurePreferences;
import asia.sigitsuryono.kialibrary.TitleToobarCustom;

/**
 * Created by Sigit Suryono on 05-Aug-17.
 */

public class InputStatusGizi extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<String> semester, bulan, normalTidakList, statusGiziList, jumlahAnakList;
    Spinner pilihSemester, pilihBulan, normalTidak, jumlahAnak;
    ArrayAdapter SemesterAdapter, BulanadApter, normalTidakAdapter, statusGiziAdapter, jumlahAnakAdapter;
    TextView nama_anak_status_gizi, id_anak;
    LinearLayout containerBulan, containerEntryData, semesterdanbulancontainer;
    FloatingActionButton sendToServer;
    RelativeLayout infoAnakContainer;
    SelectQuery selectQuery;
    Cursor c;
    EditText tgl_pemberian, berat_badan_status_gizi, keteranganPerSemester, statusGizi;
    ImageButton getDatePemberian;

    int check = 0;
    Calendar calendar;
    int year, month, day;
    SecurePreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_input_status_gizi);

        setTitle(new TitleToobarCustom().TitleToobarCustom("Input Status Gizi", Color.WHITE));

        selectQuery = new SelectQuery(InputStatusGizi.this);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        pilihSemester = (Spinner) findViewById(R.id.pilihSemester);
        pilihBulan = (Spinner) findViewById(R.id.pilihBulan);
        containerBulan = (LinearLayout) findViewById(R.id.containerBulan);
        containerEntryData = (LinearLayout) findViewById(R.id.containerEntryData);
        semesterdanbulancontainer = (LinearLayout) findViewById(R.id.semesterdanbulancontainer);
        infoAnakContainer = (RelativeLayout) findViewById(R.id.infoAnakContainer);
        nama_anak_status_gizi = (TextView) findViewById(R.id.nama_anak_status_gizi);
        normalTidak = (Spinner) findViewById(R.id.pilihNormalAtauTidak);
        statusGizi = (EditText) findViewById(R.id.pilihStatusGizi);
        sendToServer = (FloatingActionButton) findViewById(R.id.sendToServer);
        jumlahAnak = (Spinner) findViewById(R.id.anakKe);
        tgl_pemberian = (EditText) findViewById(R.id.tgl_pemberian);
        berat_badan_status_gizi = (EditText) findViewById(R.id.berat_badan_status_gizi);
        keteranganPerSemester = (EditText) findViewById(R.id.keteranganPerSemester);
        id_anak = (TextView) findViewById(R.id.id_anak);
        getDatePemberian = (ImageButton) findViewById(R.id.getDatePemberian);
        sendToServer.setVisibility(View.GONE);

        preferences = new SecurePreferences(this, "asia.sayateam.kiaadmin", PrefencesTambahData.ENCRYPT_KEY, true);
        preferences.put(InputDataInit.FIRST_CALL, InputDataInit.FALSE);


        loadSemester();
        loadJumlahAnak();

        pilihSemester.setOnItemSelectedListener(this);

        pilihBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (++check > 0) {
                    loadNormalTidak();
                    DatabaseVariable.modelInputStatusGizi.
                            setBulan_ke(pilihBulan.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        normalTidak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DatabaseVariable.modelInputStatusGizi.
                        setNormal_tidak(String.valueOf(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        jumlahAnak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    infoAnakContainer.setVisibility(View.GONE);
                    semesterdanbulancontainer.setVisibility(View.GONE);
                    sendToServer.setVisibility(View.GONE);
                    containerEntryData.setVisibility(View.GONE);
                    sendToServer.setVisibility(View.GONE);
                } else {
                    c = selectQuery.getDetailAnakKe(jumlahAnak.getSelectedItem().toString());
                    while (c.moveToNext()) {
                        id_anak.setText(c.getString(0));
                        nama_anak_status_gizi.setText(c.getString(1));
                    }
                    infoAnakContainer.setVisibility(View.VISIBLE);
                    sendToServer.setVisibility(View.VISIBLE);
                    semesterdanbulancontainer.setVisibility(View.VISIBLE);
                    containerEntryData.setVisibility(View.VISIBLE);
                    sendToServer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getDatePemberian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener;
                DatePickerDialog datePickerDialog;

                onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        tgl_pemberian.setText(String.valueOf(i) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i1 + 1)) +
                                ConfigureApps.DATE_SEPARATOR +
                                String.valueOf(String.format(ConfigureApps.DATE_FORMATER, i2)));
                    }
                };

                datePickerDialog = new DatePickerDialog(InputStatusGizi.this, onDateSetListener, year, month, day);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        tgl_pemberian.setText("");
                    }
                });

                datePickerDialog.show();
            }
        });

        sendToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(InputStatusGizi.this);

                if (DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil().equals("")) {

                } else {
                    SendDataToServerHandler.sendDataKeteranganGizi sendDataKeteranganGizi =
                            sendDataToServerHandler.new sendDataKeteranganGizi();
                    sendDataKeteranganGizi.execute();
                }
                DatabaseVariable.modelInputStatusGizi.
                        setStatus_gizi(statusGizi.getText().toString());
                DatabaseVariable.modelInputStatusGizi.
                        setTanggal(tgl_pemberian.getText().toString());
                DatabaseVariable.modelInputStatusGizi.
                        setBerat_badan(berat_badan_status_gizi.getText().toString());
                DatabaseVariable.modelInputStatusGizi.
                        setKeterangan(keteranganPerSemester.getText().toString());
                DatabaseVariable.modelInputStatusGizi.setId_anak(id_anak.getText().toString());


                SendDataToServerHandler.sendDataStatusGiziToServer sendDataStatusGiziToServer = sendDataToServerHandler.new sendDataStatusGiziToServer();
                sendDataStatusGiziToServer.execute();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, SubMenuInput.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.keterangan_imunisasi, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            if (item.getItemId() == R.id.keteranganImunisasi) {
                new FragmentKeteranganGizi(this, id_anak.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadSemester() {
        semester = new ArrayList<>();
        semester.add("--Pilih Semester--");
        for (int i = 1; i <= 10; i++) {
            semester.add(String.valueOf(i));
        }

        SemesterAdapter = new ArrayAdapter(InputStatusGizi.this, android.R.layout.simple_dropdown_item_1line, semester);
        SemesterAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        pilihSemester.setAdapter(SemesterAdapter);
    }

    public void loadBulan(String semester) {
        bulan = new ArrayList<>();

        switch (semester) {
            case "1":
                for (int i = 0; i <= 5; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "2":
                for (int i = 6; i <= 11; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "3":
                for (int i = 12; i <= 17; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "4":
                for (int i = 18; i <= 23; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "5":
                for (int i = 24; i <= 29; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "6":
                for (int i = 30; i <= 35; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "7":
                for (int i = 36; i <= 41; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "8":
                for (int i = 42; i <= 47; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "9":
                for (int i = 48; i <= 53; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
            case "10":
                for (int i = 54; i <= 59; i++) {
                    bulan.add(String.valueOf(i));
                }
                break;
        }

        BulanadApter = new ArrayAdapter(InputStatusGizi.this, android.R.layout.simple_dropdown_item_1line, bulan);
        BulanadApter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        pilihBulan.setAdapter(BulanadApter);
        BulanadApter.notifyDataSetChanged();
    }

    public void loadNormalTidak() {
        normalTidakList = new ArrayList<>();
        normalTidakList.add("Naik");
        normalTidakList.add("Turun");

        normalTidakAdapter = new ArrayAdapter(InputStatusGizi.this, android.R.layout.simple_dropdown_item_1line, normalTidakList);
        normalTidakAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        normalTidak.setAdapter(normalTidakAdapter);
        normalTidakAdapter.notifyDataSetChanged();
    }

    public void loadJumlahAnak() {
        jumlahAnakList = new ArrayList<>();
        jumlahAnakList.add("--Pilih Anak--");
        c = selectQuery.getDataFor_Info_Anak();
        while (c.moveToNext()) {
            jumlahAnakList.add(c.getString(1));

        }
        jumlahAnakAdapter = new ArrayAdapter(InputStatusGizi.this, android.R.layout.simple_dropdown_item_1line, jumlahAnakList);
        jumlahAnakAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jumlahAnak.setAdapter(jumlahAnakAdapter);
        jumlahAnakAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (pilihSemester.getSelectedItem().toString().equalsIgnoreCase("--Pilih Semester--")) {

        } else {
            loadBulan(pilihSemester.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
