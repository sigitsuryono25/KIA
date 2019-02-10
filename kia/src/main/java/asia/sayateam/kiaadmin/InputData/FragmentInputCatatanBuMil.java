package asia.sayateam.kiaadmin.InputData;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 04-Aug-17.
 */

public class FragmentInputCatatanBuMil extends Fragment implements View.OnClickListener {

    public final String HTP = "htp";
    public final String HPHT = "hpht";
    public final String KUNJUNGAN = "kunjungan";
    public final String TT = "tt";

    EditText hphtValue, htpValue, lingkarLenganValue, tinggiBadanValue,
            penggunaanKontrasepsiValue, riwayatPenyakitValue, riwayatAlergiValue,
            hamilKeValue, jumlahPersalinanValue, jumlahKeguguranValue,
            jumlahAnakHidupValue, jumlahAnakMatiValue, jumlahAnakLahirKurangBulanValue,
            jumlahKehamilaniniValue, statusImunisasiTTValue, imunisasiTTValue,
            penolongPersalinanTerakhirValue, caraPersalinanTerakhirValue, tglKunjungan_value;

    ImageButton getDateHthp, getDateHtp, gettglKunjungan, gettanggalTT;
    Calendar calendar;

    int year, month, day;

    Cursor c;
    SelectQuery selectQuery;
    boolean firstLoad = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_catatan_kesehatan_ibu_hamil, container, false);

        selectQuery = new SelectQuery(getActivity());


        tglKunjungan_value = view.findViewById(R.id.tglKunjungan_value);
        tglKunjungan_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setTanggalKunjungan(editable.toString());
            }
        });

        hphtValue = view.findViewById(R.id.hpht_date_value);
        hphtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setHphtValue(editable.toString());
            }
        });

        htpValue = view.findViewById(R.id.htp_date_value);
        htpValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setHtpValue(editable.toString());
            }
        });

        lingkarLenganValue = view.findViewById(R.id.lingkar_lengan_atas_value);
        lingkarLenganValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setLingkarLenganValue(editable.toString());
            }
        });

        tinggiBadanValue = view.findViewById(R.id.tinggi_badan_bumil_value);
        tinggiBadanValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setTinggiBadanValue(editable.toString());
            }
        });

        penggunaanKontrasepsiValue = view.findViewById(R.id.penggunaan_kontrasepsi_bumil_value);
        penggunaanKontrasepsiValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setPenggunaanKontrasepsiValue(editable.toString());
            }
        });

        riwayatPenyakitValue = view.findViewById(R.id.riawayat_penyakit_yang_diderita_bumil);
        riwayatPenyakitValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setRiwayatPenyakitValue(editable.toString());
            }
        });

        riwayatAlergiValue = view.findViewById(R.id.riwayat_alergi_bumil);
        riwayatAlergiValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setRiwayatAlergiValue(editable.toString());
            }
        });

        hamilKeValue = view.findViewById(R.id.hamil_ke);
        hamilKeValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setHamilKeValue(editable.toString());
            }
        });

        jumlahPersalinanValue = view.findViewById(R.id.jumlah_persalinan_bumil_value);
        jumlahPersalinanValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setJumlahPersalinanValue(editable.toString());
            }
        });

        jumlahKeguguranValue = view.findViewById(R.id.jumlah_keguguran_bumil_value);
        jumlahKeguguranValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setJumlahKeguguranValue(editable.toString());
            }
        });

        jumlahAnakHidupValue = view.findViewById(R.id.jumlah_anak_hidup_bumil_value);
        jumlahAnakHidupValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakHidupValue(editable.toString());
            }
        });

        jumlahAnakMatiValue = view.findViewById(R.id.jumlah_anak_mati_bumil_value);
        jumlahAnakMatiValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakMatiValue(editable.toString());
            }
        });

        jumlahAnakLahirKurangBulanValue = view.findViewById(R.id.jumlah_anak_lahir_kurang_bulan_bumil_value);
        jumlahAnakLahirKurangBulanValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakLahirKurangBulanValue(editable.toString());
            }
        });

        jumlahKehamilaniniValue = view.findViewById(R.id.jumlah_kehamilan);
        jumlahKehamilaniniValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setJumlahKehamilaniniValue(editable.toString());
            }
        });

        statusImunisasiTTValue = view.findViewById(R.id.status_imunisasi_tt_bumil);
        statusImunisasiTTValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setStatusImunisasiTTValue(editable.toString());
            }
        });

        imunisasiTTValue = view.findViewById(R.id.imunisasi_tt_terakhir);
        imunisasiTTValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setImunisasiTTValue(editable.toString());
            }
        });

        penolongPersalinanTerakhirValue = view.findViewById(R.id.penolong_persalinan);
        penolongPersalinanTerakhirValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setPenolongPersalinanTerakhirValue(editable.toString());
            }
        });

        caraPersalinanTerakhirValue = view.findViewById(R.id.cara_persalinan_terakhir_bumil);
        caraPersalinanTerakhirValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DatabaseVariable.modelInputCatatanBuMil.setCaraPersalinanTerakhirValue(editable.toString());
            }
        });

        getDateHthp = view.findViewById(R.id.getDateHpht);
        getDateHtp = view.findViewById(R.id.getDateHtp);
        gettglKunjungan = view.findViewById(R.id.gettglKunjungan);
        gettanggalTT = view.findViewById(R.id.gettanggal);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        getDateHthp.setOnClickListener(this);
        getDateHtp.setOnClickListener(this);
        gettglKunjungan.setOnClickListener(this);
        gettanggalTT.setOnClickListener(this);

        try {
            if(firstLoad) {
                loadData();
            }else{
                LoadData();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }


    public void showDatePicker(String pengenal) {
        if (pengenal.equals(HTP)) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    htpValue.setText(String.valueOf(year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth));
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.cancel();
                        htpValue.setText("");
                    }
                }
            });
            datePickerDialog.show();
        } else if (pengenal.equals(HPHT)) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    hphtValue.setText(String.valueOf(year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth));
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.cancel();
                        hphtValue.setText("");
                    }
                }
            });
            datePickerDialog.show();
        } else if (pengenal.equals(KUNJUNGAN)) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    tglKunjungan_value.setText(String.valueOf(year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth));
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.cancel();
                        tglKunjungan_value.setText("");
                    }
                }
            });
            datePickerDialog.show();
        } else if (pengenal.equals(TT)) {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    imunisasiTTValue.setText(String.valueOf(year) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth));
                }
            };

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);

            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        dialog.cancel();
                        imunisasiTTValue.setText("");
                    }
                }
            });
            datePickerDialog.show();
        }
    }

    public void LoadData() {
        hphtValue.setText(DatabaseVariable.modelInputCatatanBuMil.getHphtValue());
        htpValue.setText(DatabaseVariable.modelInputCatatanBuMil.getHtpValue());
        lingkarLenganValue.setText(DatabaseVariable.modelInputCatatanBuMil.getLingkarLenganValue());
        tinggiBadanValue.setText(DatabaseVariable.modelInputCatatanBuMil.getTinggiBadanValue());
        penggunaanKontrasepsiValue.setText(DatabaseVariable.modelInputCatatanBuMil.getPenggunaanKontrasepsiValue());
        riwayatPenyakitValue.setText(DatabaseVariable.modelInputCatatanBuMil.getRiwayatPenyakitValue());
        riwayatAlergiValue.setText(DatabaseVariable.modelInputCatatanBuMil.getRiwayatAlergiValue());

        hamilKeValue.setText(DatabaseVariable.modelInputCatatanBuMil.getHamilKeValue());
        jumlahPersalinanValue.setText(DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue());
        jumlahKeguguranValue.setText(DatabaseVariable.modelInputCatatanBuMil.getJumlahKeguguranValue());

        jumlahAnakHidupValue.setText(DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakHidupValue());
        jumlahAnakMatiValue.setText(DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakMatiValue());
        jumlahAnakLahirKurangBulanValue.setText(DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakLahirKurangBulanValue());

        jumlahKehamilaniniValue.setText(DatabaseVariable.modelInputCatatanBuMil.getJumlahKehamilaniniValue());
        statusImunisasiTTValue.setText(DatabaseVariable.modelInputCatatanBuMil.getStatusImunisasiTTValue());
        imunisasiTTValue.setText(DatabaseVariable.modelInputCatatanBuMil.getImunisasiTTValue());
        penolongPersalinanTerakhirValue.setText(DatabaseVariable.modelInputCatatanBuMil.getPenolongPersalinanTerakhirValue());
        caraPersalinanTerakhirValue.setText(DatabaseVariable.modelInputCatatanBuMil.getCaraPersalinanTerakhirValue());
        tglKunjungan_value.setText(DatabaseVariable.modelInputCatatanBuMil.getTanggalKunjungan());
    }

    public void loadData() {
        String idIbu;

        c = selectQuery.getKesBumil();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                idIbu = c.getString(0);
                hphtValue.setText(c.getString(2));
                htpValue.setText(c.getString(3));
                lingkarLenganValue.setText(c.getString(4));
                tinggiBadanValue.setText(c.getString(5));
                penggunaanKontrasepsiValue.setText(c.getString(6));
                riwayatPenyakitValue.setText(c.getString(7));
                riwayatAlergiValue.setText(c.getString(8));

                hamilKeValue.setText(c.getString(9));
                jumlahPersalinanValue.setText(c.getString(10));
                jumlahKeguguranValue.setText(c.getString(11));

                jumlahAnakHidupValue.setText(c.getString(12));
                jumlahAnakMatiValue.setText(c.getString(13));
                jumlahAnakLahirKurangBulanValue.setText(c.getString(14));

                jumlahKehamilaniniValue.setText(c.getString(15));
                statusImunisasiTTValue.setText(c.getString(16));
                imunisasiTTValue.setText(c.getString(17));
                penolongPersalinanTerakhirValue.setText(c.getString(18));
                caraPersalinanTerakhirValue.setText(c.getString(19));
                tglKunjungan_value.setText(c.getString(1));
            }
        } else {

        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getDateHpht:
                showDatePicker(HPHT);
                break;
            case R.id.getDateHtp:
                showDatePicker(HTP);
                break;
            case R.id.gettglKunjungan:
                showDatePicker(KUNJUNGAN);
                break;
            case R.id.gettanggal:
                showDatePicker(TT);
                break;

        }
    }
}
