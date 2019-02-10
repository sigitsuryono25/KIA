package asia.sayateam.kiaadmin.InputData;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 015, 15-09-2017.
 */

public class FragmentBumilLastRecord extends Fragment {
    EditText hphtValue, htpValue, lingkarLenganValue, tinggiBadanValue,
            penggunaanKontrasepsiValue, riwayatPenyakitValue, riwayatAlergiValue,
            hamilKeValue, jumlahPersalinanValue, jumlahKeguguranValue,
            jumlahAnakHidupValue, jumlahAnakMatiValue, jumlahAnakLahirKurangBulanValue,
            jumlahKehamilaniniValue, statusImunisasiTTValue, imunisasiTTValue,
            penolongPersalinanTerakhirValue, caraPersalinanTerakhirValue, tglKunjungan_value, nama_ibu_cat_bumil;
    EditText tanggalPemeriksaanTerkahir, keterangan;
    SelectQuery selectQuery;
    View view;
    Cursor c;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bumil_last_record, container, false);

        selectQuery = new SelectQuery(getActivity());

        nama_ibu_cat_bumil = view.findViewById(R.id.nama_ibu_cat_bumil);
        tglKunjungan_value = view.findViewById(R.id.tglKunjungan_value);
        hphtValue = view.findViewById(R.id.hpht_date_value);
        htpValue = view.findViewById(R.id.htp_date_value);
        lingkarLenganValue = view.findViewById(R.id.lingkar_lengan_atas_value);
        tinggiBadanValue = view.findViewById(R.id.tinggi_badan_bumil_value);
        penggunaanKontrasepsiValue = view.findViewById(R.id.penggunaan_kontrasepsi_bumil_value);
        riwayatPenyakitValue = view.findViewById(R.id.riawayat_penyakit_yang_diderita_bumil);
        riwayatAlergiValue = view.findViewById(R.id.riwayat_alergi_bumil);
        hamilKeValue = view.findViewById(R.id.hamil_ke);
        jumlahPersalinanValue = view.findViewById(R.id.jumlah_persalinan_bumil_value);
        jumlahKeguguranValue = view.findViewById(R.id.jumlah_keguguran_bumil_value);
        jumlahAnakHidupValue = view.findViewById(R.id.jumlah_anak_hidup_bumil_value);
        jumlahAnakMatiValue = view.findViewById(R.id.jumlah_anak_mati_bumil_value);
        jumlahAnakLahirKurangBulanValue = view.findViewById(R.id.jumlah_anak_lahir_kurang_bulan_bumil_value);
        jumlahKehamilaniniValue = view.findViewById(R.id.jumlah_kehamilan);
        statusImunisasiTTValue = view.findViewById(R.id.status_imunisasi_tt_bumil);
        imunisasiTTValue = view.findViewById(R.id.imunisasi_tt_terakhir);
        penolongPersalinanTerakhirValue = view.findViewById(R.id.penolong_persalinan);
        caraPersalinanTerakhirValue = view.findViewById(R.id.cara_persalinan_terakhir_bumil);
        tanggalPemeriksaanTerkahir = view.findViewById(R.id.tgl_pemeriksaan_value);
        keterangan = view.findViewById(R.id.keteranganTambahan_value);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
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
                tanggalPemeriksaanTerkahir.setText(c.getString(20));
                keterangan.setText(c.getString(21));
            }
        } else {

        }

        c = selectQuery.getDataFor_Info_Ibu();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                nama_ibu_cat_bumil.setText(c.getString(0));
            }
        }
    }
}
