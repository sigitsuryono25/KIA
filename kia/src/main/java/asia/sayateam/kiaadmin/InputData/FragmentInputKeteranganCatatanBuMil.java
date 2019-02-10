package asia.sayateam.kiaadmin.InputData;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import asia.sayateam.kiaadmin.AsyncTasks.SendDataToServerHandler;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 11-Aug-17.
 */

public class FragmentInputKeteranganCatatanBuMil extends Fragment {
    EditText tanggalPemeriksaanTerkahir, keterangan;
    ImageButton getTanggalPemeriksaan;
    FloatingActionButton send;
    View layout;
    Calendar calendar;

    int year, month, day;

    Cursor c;
    SelectQuery selectQuery;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_input_keterangan_catatan_kesehatan_ibu_hamil, container, false);


        selectQuery = new SelectQuery(getActivity());
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        tanggalPemeriksaanTerkahir = layout.findViewById(R.id.tgl_pemeriksaan_value);
        tanggalPemeriksaanTerkahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setTanggalPemeriksaanTerkahir(editable.toString());
            }
        });

        keterangan = layout.findViewById(R.id.keteranganTambahan_value);
        keterangan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setKeterangan(editable.toString());
            }
        });


        loadData();

        getTanggalPemeriksaan = layout.findViewById(R.id.getDatePemeriksaan);
        getTanggalPemeriksaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tanggalPemeriksaanTerkahir.setText(String.valueOf(year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth));
                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                            tanggalPemeriksaanTerkahir.setText("");
                        }
                    }
                });
                datePickerDialog.show();
            }
        });


        send = layout.findViewById(R.id.finish);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectQuery selectQuery = new SelectQuery(getActivity());
                Cursor cursor = selectQuery.getDataFor_Info_Ibu();
                while (cursor.moveToNext()) {
                    DatabaseVariable.modelInputCatatanBuMil.setId_ibu(cursor.getString(11));
                }
                DatabaseVariable.modelInputCatatanBuMil.getTanggalPemeriksaanTerkahir();
                DatabaseVariable.modelInputCatatanBuMil.getHphtValue();
                DatabaseVariable.modelInputCatatanBuMil.getHtpValue();
                DatabaseVariable.modelInputCatatanBuMil.getLingkarLenganValue();
                DatabaseVariable.modelInputCatatanBuMil.getTinggiBadanValue();
                DatabaseVariable.modelInputCatatanBuMil.getPenggunaanKontrasepsiValue();
                DatabaseVariable.modelInputCatatanBuMil.getRiwayatPenyakitValue();
                DatabaseVariable.modelInputCatatanBuMil.getRiwayatAlergiValue();
                DatabaseVariable.modelInputCatatanBuMil.getHamilKeValue();
                DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue();
                DatabaseVariable.modelInputCatatanBuMil.getJumlahKeguguranValue();
                DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakHidupValue();
                DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakMatiValue();
                DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakLahirKurangBulanValue();
                DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue();
                DatabaseVariable.modelInputCatatanBuMil.getStatusImunisasiTTValue();
                DatabaseVariable.modelInputCatatanBuMil.getImunisasiTTValue();
                DatabaseVariable.modelInputCatatanBuMil.getPenolongPersalinanTerakhirValue();
                DatabaseVariable.modelInputCatatanBuMil.getCaraPersalinanTerakhirValue();
                DatabaseVariable.modelInputCatatanBuMil.getTanggalPemeriksaanTerkahir();
                DatabaseVariable.modelInputCatatanBuMil.getKeterangan();

                DeleteQuery deleteQuery = new DeleteQuery(getActivity());
                deleteQuery.DeletKesehatanIbu();
                InsertQuery insertQuery = new InsertQuery(getActivity());
                insertQuery.insertKesehatanIbuHamil();

                SendDataToServerHandler sendDataToServerHandler = new SendDataToServerHandler(getActivity());
                SendDataToServerHandler.sendDataKesehatanIbuHamilToServer sendDataKesehatanIbuHamilToServer = sendDataToServerHandler.new sendDataKesehatanIbuHamilToServer();
                sendDataKesehatanIbuHamilToServer.execute();


            }
        });

        return layout;
    }

//    public void LoadData() {
//        tanggalPemeriksaanTerkahir.setText(
//                DatabaseVariable.modelInputCatatanBuMil.getTanggalPemeriksaanTerkahir());
//        keterangan.setText(
//                DatabaseVariable.modelInputCatatanBuMil.getKeterangan());
//    }

    public void loadData() {
        c = selectQuery.getKesBumil();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                tanggalPemeriksaanTerkahir.setText(c.getString(20));
                keterangan.setText(c.getString(21));
            }
        } else {

        }
    }
}
