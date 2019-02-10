package asia.sayateam.kiaadmin.DatabaseHandle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sigit Suryono on 07-Aug-17.
 */

public class InsertQuery {
    DatabaseHadler databaseHadler;
    SQLiteDatabase database;
    Context c;

    public InsertQuery(Context context) {
        databaseHadler = new DatabaseHadler(context);
        database = databaseHadler.OpenDatabase();
        this.c = context;
    }

    public int InsertDataInfoPemilik() {
        database.execSQL("DELETE FROM " + DatabaseVariable.INFORMASI_PEMILIK);
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.INFORMASI_PEMILIK + " VALUES(" +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getId_kia() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getNamaPesertaValue() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getTempatLahirPeserta() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getTanggalLahirPeserta() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getAlamatPeserta() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getRT() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getRW() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getIdDusun() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getPekerjaanPeserta() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getNoBPJS() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getPendidikan() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getTanggalRegistrasi() + "'," +
                    "'" + DatabaseVariable.tambahDataModelPemilik.getJumlahAnak() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }

    }

    public int InsertDataInfoIbu() {

        database.execSQL("DELETE FROM " + DatabaseVariable.INFORMASI_IBU);
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.INFORMASI_IBU + " VALUES(" +
                    "'" + DatabaseVariable.tambahDataModelIbu.getNama_ibu() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getTempat_lahir() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getTanggal_lahir() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getAlamat() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getRt() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getRw() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getDusun() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getPekerjaan() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getNo_bpjs() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getPendidikan() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getId_kia() + "'," +
                    "'" + DatabaseVariable.tambahDataModelIbu.getId_ibu() + "'" +
                    ")");


            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int InsertDataInfoAnak() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.INFORMASI_ANAK + " VALUES(" +
                    "'" + DatabaseVariable.tambahModelAnak.getId_anak() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getNama_anak() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getAlamat() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getRt() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getRw() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getTempat_lahir() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getTanggal_lahir() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getNo_bpjs() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getDusun() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getId_kia() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getId_ibu() + "'," +
                    "'" + DatabaseVariable.tambahModelAnak.getAnakke() + "'" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertKesehatanIbuHamil() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.KESEHATAN_IBU + " VALUES(" +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getId_ibu() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getTanggalKunjungan() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getHphtValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getHtpValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getLingkarLenganValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getTinggiBadanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getPenggunaanKontrasepsiValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getRiwayatPenyakitValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getRiwayatAlergiValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getHamilKeValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahKeguguranValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakHidupValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakMatiValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakLahirKurangBulanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getStatusImunisasiTTValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getImunisasiTTValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getPenolongPersalinanTerakhirValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getCaraPersalinanTerakhirValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getTanggalPemeriksaanTerkahir() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getKeterangan() + "'" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertKesehatanIbuHamilInput() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.KESEHATAN_IBU + " VALUES(" +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getId_ibu() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getTanggalKunjungan() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getHphtValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getHtpValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getLingkarLenganValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getTinggiBadanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getPenggunaanKontrasepsiValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getRiwayatPenyakitValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getRiwayatAlergiValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getHamilKeValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahKeguguranValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakHidupValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakMatiValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahAnakLahirKurangBulanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getJumlahPersalinanValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getStatusImunisasiTTValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getImunisasiTTValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getPenolongPersalinanTerakhirValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getCaraPersalinanTerakhirValue() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getTanggalPemeriksaanTerkahir() + "'," +
                    "'" + DatabaseVariable.modelInputCatatanBuMil.getKeterangan() + "'" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertImun012() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.IMUN_0_12 + " VALUES(" +
                    "'" + DatabaseVariable.modelInputImunisasi0_12.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputImunisasi0_12.getBulan_ke() + "'," +
                    "'" + DatabaseVariable.modelInputImunisasi0_12.getTanggal_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputImunisasi0_12.getId_imunisasi() + "'," +
                    "'" + DatabaseVariable.modelInputImunisasi0_12.getCoordinate_x() + "." +
                    DatabaseVariable.modelInputImunisasi0_12.getCoordinate_y() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertImun1TahunPlus() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.IMUN_SATU_TAHUN_PLUS + " VALUES(" +
                    "'" + DatabaseVariable.modelInputImunDiatas1Tahun.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputImunDiatas1Tahun.getId_imunisasi() + "'," +
                    "'" + DatabaseVariable.modelInputImunDiatas1Tahun.getBulan_ke() + "'," +
                    "'" + DatabaseVariable.modelInputImunDiatas1Tahun.getTanggal_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputImunDiatas1Tahun.getCoordinate_x() + "." +
                    DatabaseVariable.modelInputImunDiatas1Tahun.getCoordinate_y() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertImunLain() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.IMUN_LAIN + " VALUES(" +
                    "'" + DatabaseVariable.modelInputVaksinLain.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "'," +
                    "'" + DatabaseVariable.modelInputVaksinLain.getTanggal_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputVaksinLain.getCoordinate() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertImunBIAS() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.BIAS + " VALUES(" +
                    "'" + DatabaseVariable.modelInputBIAS.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputBIAS.getId_jenis_imunisasi() + "'," +
                    "'" + DatabaseVariable.modelInputBIAS.getTanggal_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputBIAS.getTingkatan() + "'," +
                    "'" + DatabaseVariable.modelInputBIAS.getCoordinate_x() + "." +
                    DatabaseVariable.modelInputBIAS.getCoordinate_y() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertImunTambahan() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.IMUN_TAMBAHAN + "(id_anak, nama_vaksin, tanggal_pemberian, coordinate) VALUES(" +
                    "'" + DatabaseVariable.modelInputImunTambahan.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "'," +
                    "'" + DatabaseVariable.modelInputImunTambahan.getTanggal_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputImunTambahan.getcoordinate() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertStatusGizi() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.STAT_GIZI + " VALUES(" +
                    "'" + DatabaseVariable.modelInputStatusGizi.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputStatusGizi.getBulan_ke() + "'," +
                    "'" + DatabaseVariable.modelInputStatusGizi.getTanggal() + "'," +
                    "'" + DatabaseVariable.modelInputStatusGizi.getBerat_badan() + "'," +
                    "'" + DatabaseVariable.modelInputStatusGizi.getNormal_tidak() + "'," +
                    "'" + DatabaseVariable.modelInputStatusGizi.getStatus_gizi() + "'," +
                    "'" + DatabaseVariable.modelInputStatusGizi.getKeterangan() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertKesehatanAnak() {
        try {
            database.execSQL("INSERT INTO " + DatabaseVariable.KESEHATAN_ANAK + " VALUES(" +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getId_vitamin() + "'," +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getWaktu_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getDosis() + "'," +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getTanggal_pemberian() + "'," +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getPemberian_ke() + "'," +
                    "'" + DatabaseVariable.modelInputKesehatanAnak.getKeterangan() + "'" +
                    ")");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }

    }

    public int insertKesehatanAnakUpdate() {
        try {
            Cursor c = database.rawQuery("SELECT id_anak, waktu_pemberian FROM kesehatan_anak WHERE id_anak='" +
                    DatabaseVariable.modelInputKesehatanAnak.getId_anak() + "' " +
                    "AND waktu_pemberian='" + DatabaseVariable.modelInputKesehatanAnak.getWaktu_pemberian() + "' " +
                    "AND pemberian_ke='" + DatabaseVariable.modelInputKesehatanAnak.getPemberian_ke() + "'", null);
            if (c.getCount() > 0) {
                database.execSQL("UPDATE kesehatan_anak SET " +
                        "tanggal_pemberian='" + DatabaseVariable.modelInputKesehatanAnak.getTanggal_pemberian() + "'," +
                        "keterangan='" + DatabaseVariable.modelInputKesehatanAnak.getKeterangan() + "'" +
                        " WHERE id_anak='" + DatabaseVariable.modelInputKesehatanAnak.getId_anak() + "' " +
                        "AND waktu_pemberian='" + DatabaseVariable.modelInputKesehatanAnak.getWaktu_pemberian() + "'");
            } else {
                database.execSQL("INSERT INTO " + DatabaseVariable.KESEHATAN_ANAK + " VALUES(" +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getId_anak() + "'," +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getId_vitamin() + "'," +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getWaktu_pemberian() + "'," +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getDosis() + "'," +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getTanggal_pemberian() + "'," +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getTanggal_pemberian_terakhir() + "'," +
                        "'" + DatabaseVariable.modelInputKesehatanAnak.getKeterangan() + "'" +
                        ")");

            }
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }

    }


    public int insertDusun() {
        try {

            database.execSQL("INSERT INTO dusun VALUES(" +
                    "'" + DatabaseVariable.modelGetDusun.getId() + "'," +
                    "'" + DatabaseVariable.modelGetDusun.getNamaDusun() + "'" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertJenisImunisasi() {
        try {
            database.execSQL("INSERT INTO jenis_imunisasi (id_jenis_imunisasi, nama_imunisasi, keterangan) VALUES(" +
                    "'" + DatabaseVariable.modelGetJenisImunisasi.getId_jenis_imunisasi() + "'," +
                    "'" + DatabaseVariable.modelGetJenisImunisasi.getNama_imunisasi() + "', ''" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertKetImun() {
        try {
            database.execSQL("INSERT INTO keterangan_imunisasi  VALUES(" +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getId_anak() + "'," +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getTanggal() + "'," +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil() + "'," +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getKeterangan() + "'" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int insertKetGizi() {
        try {
            database.execSQL("INSERT INTO keterangan_gizi VALUES(" +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getTanggal() + "'," +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getKeterangan() + "'," +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil() + "'," +
                    "'" + DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getId_anak() + "'" +
                    ")");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }
}
