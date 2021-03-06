package asia.sayateam.kiaclient.DatabaseHandle;

import asia.sayateam.kiaclient.Model.ModelGetKeteranganImunisasiDanGizi;
import asia.sayateam.kiaclient.Model.ModelInputBIAS;
import asia.sayateam.kiaclient.Model.ModelInputCatatanBuMil;
import asia.sayateam.kiaclient.Model.ModelInputImunDiatas1Tahun;
import asia.sayateam.kiaclient.Model.ModelInputImunTambahan;
import asia.sayateam.kiaclient.Model.ModelInputImunisasi0_12;
import asia.sayateam.kiaclient.Model.ModelInputKesehatanAnak;
import asia.sayateam.kiaclient.Model.ModelInputStatusGizi;
import asia.sayateam.kiaclient.Model.ModelInputVaksinLain;
import asia.sayateam.kiaclient.Model.TambahDataModelIbu;
import asia.sayateam.kiaclient.Model.TambahDataModelPemilik;
import asia.sayateam.kiaclient.Model.TambahModelAnak;

/**
 * Created by Sigit Suryono on 07-Aug-17.
 */

public class DatabaseVariable {

    public static final String INFORMASI_ANAK = "anak";
    public static final String INFORMASI_IBU = "ibu";
    public static final String BIAS = "imunisasi_anak_sekolah";
    public static final String IMUN_LAIN = "imunisasi_lain";
    public static final String IMUN_0_12 = "imunisasi_balita";
    public static final String IMUN_SATU_TAHUN_PLUS = "imunisasi_satu_tahun_keatas";
    public static final String IMUN_TAMBAHAN = "imunisasi_tambahan";
    public static final String KESEHATAN_ANAK = "kesehatan_anak";
    public static final String KESEHATAN_IBU = "kesehatan_ibu";
    public static final String INFORMASI_PEMILIK = "pemilik_kia";
    public static final String STAT_GIZI = "status_gizi";


    public static final int TRUE_VALUE = 0x00000;
    public static final int FALSE_VALUE = 0x00001;

    public static TambahDataModelPemilik tambahDataModelPemilik = new TambahDataModelPemilik();
    public static TambahDataModelIbu tambahDataModelIbu = new TambahDataModelIbu();
    public static TambahModelAnak tambahModelAnak = new TambahModelAnak();
    public static ModelInputCatatanBuMil modelInputCatatanBuMil = new ModelInputCatatanBuMil();
    public static ModelInputStatusGizi modelInputStatusGizi = new ModelInputStatusGizi();
    public static ModelInputImunisasi0_12 modelInputImunisasi0_12 = new ModelInputImunisasi0_12();
    public static ModelInputImunDiatas1Tahun modelInputImunDiatas1Tahun = new ModelInputImunDiatas1Tahun();
    public static ModelInputBIAS modelInputBIAS = new ModelInputBIAS();
    public static ModelInputVaksinLain modelInputVaksinLain = new ModelInputVaksinLain();
    public static ModelInputImunTambahan modelInputImunTambahan = new ModelInputImunTambahan();
    public static ModelInputKesehatanAnak modelInputKesehatanAnak = new ModelInputKesehatanAnak();
    public static ModelGetKeteranganImunisasiDanGizi modelGetKeteranganImunisasiDanGizi = new ModelGetKeteranganImunisasiDanGizi();
}
