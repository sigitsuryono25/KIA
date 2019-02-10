package asia.sayateam.kiaclient.config;

import android.content.Context;

/**
 * Created by Sigit Suryono on 10-Aug-17.
 */

public class PrefencesTambahData {

    public static final String ENCRYPT_KEY = "!@#$^&*()";
    public static final String ID_KIA = "id_kia";
    public static final String NAMA_PEMILIK = "nama_pemilik";
    public static final String TEMPAT_LAHIR_PEMILIK = "tempat_lahir_pemilik";
    public static final String TANGGAL_LAHIR_PEMILIK = "tanggal_lahir_pemilik";
    public static final String ALAMAT_PEMILIK = "alamat_pemilik";
    public static final String RT_PEMILIK = "rt_pemilik";
    public static final String RW_PEMILIK = "rw_pemilik";
    public static final String DUSUN = "id_dusun";
    public static final String PEKERJAAN_PEMILIK = "pekerjaan_pemilik";
    public static final String PENDIDIKAN_PEMILIK = "pendidikan_pemilik";
    public static final String NO_BPJS_PEMILIK = "no_bpjs_pemilik";
    public static final String JUMLAH_ANAK_PEMILIK = "jumlah_anak";
    public static final String TANGGAL_REG = "tanggal_reg";
    SecurePreferences preferences;

    public PrefencesTambahData(Context context) {
        preferences = new SecurePreferences(context, context.getPackageName().toString(), ENCRYPT_KEY, true);
    }

    public void initPrefencesPemilik() {
        preferences.clear();
    }


    public void initPrefencesIbu() {

    }

    public void initPrefencesAnak() {

    }
}
