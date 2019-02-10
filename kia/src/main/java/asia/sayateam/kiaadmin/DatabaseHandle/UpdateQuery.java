package asia.sayateam.kiaadmin.DatabaseHandle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Sigit Suryono on 07-Aug-17.
 */

public class UpdateQuery {

    SQLiteDatabase database;
    DatabaseHadler databaseHadler;

    public UpdateQuery
            (Context context) {
        databaseHadler = new DatabaseHadler(context);
        database = databaseHadler.OpenDatabase();
    }

    public int updatePemilik() {
        try {
            database.execSQL("UPDATE pemilik_kia SET " +
                    "nama='" + DatabaseVariable.tambahDataModelPemilik.getNamaPesertaValue() + "'," +
                    "tempat_lahir='" + DatabaseVariable.tambahDataModelPemilik.getTempatLahirPeserta() + "'," +
                    "tanggal_lahir='" + DatabaseVariable.tambahDataModelPemilik.getTanggalLahirPeserta() + "'," +
                    "alamat='" + DatabaseVariable.tambahDataModelPemilik.getAlamatPeserta() + "'," +
                    "rt='" + DatabaseVariable.tambahDataModelPemilik.getRT() + "'," +
                    "rw='" + DatabaseVariable.tambahDataModelPemilik.getRW() + "'," +
                    "id_dusun='" + DatabaseVariable.tambahDataModelPemilik.getIdDusun() + "'," +
                    "pekerjaan='" + DatabaseVariable.tambahDataModelPemilik.getPekerjaanPeserta() + "'," +
                    "no_bpjs='" + DatabaseVariable.tambahDataModelPemilik.getNoBPJS() + "'," +
                    "pendidikan='" + DatabaseVariable.tambahDataModelPemilik.getPendidikan() + "'," +
                    "tanggal_registrasi='" + DatabaseVariable.tambahDataModelPemilik.getTanggalRegistrasi() + "'," +
                    "jumlah_anak='" + DatabaseVariable.tambahDataModelPemilik.getJumlahAnak() + "' " +
                    "WHERE id_kia='" + DatabaseVariable.tambahDataModelPemilik.getId_kia() + "'");
            Log.i("PEMILIK", "updatePemilik: " + "UPDATE pemilik_kia SET " +
                    "nama='" + DatabaseVariable.tambahDataModelPemilik.getNamaPesertaValue() + "'," +
                    "tempat_lahir='" + DatabaseVariable.tambahDataModelPemilik.getTempatLahirPeserta() + "'," +
                    "tanggal_lahir='" + DatabaseVariable.tambahDataModelPemilik.getTanggalLahirPeserta() + "'," +
                    "alamat='" + DatabaseVariable.tambahDataModelPemilik.getAlamatPeserta() + "'," +
                    "rt='" + DatabaseVariable.tambahDataModelPemilik.getRT() + "'," +
                    "rw='" + DatabaseVariable.tambahDataModelPemilik.getRW() + "'," +
                    "id_dusun='" + DatabaseVariable.tambahDataModelPemilik.getIdDusun() + "'," +
                    "pekerjaan='" + DatabaseVariable.tambahDataModelPemilik.getPekerjaanPeserta() + "'," +
                    "no_bpjs='" + DatabaseVariable.tambahDataModelPemilik.getNoBPJS() + "'," +
                    "pendidikan='" + DatabaseVariable.tambahDataModelPemilik.getPendidikan() + "'," +
                    "tanggal_registrasi='" + DatabaseVariable.tambahDataModelPemilik.getTanggalRegistrasi() + "'," +
                    "jumlah_anak='" + DatabaseVariable.tambahDataModelPemilik.getJumlahAnak() + "' " +
                    "WHERE id_kia='" + DatabaseVariable.tambahDataModelPemilik.getId_kia() + "'");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int updateIbu() {
        try {
            database.execSQL(
                    "UPDATE ibu SET " +
                            "nama='" + DatabaseVariable.tambahDataModelIbu.getNama_ibu() + "'," +
                            "tempat_lahir='" + DatabaseVariable.tambahDataModelIbu.getTempat_lahir() + "'," +
                            "tanggal_lahir='" + DatabaseVariable.tambahDataModelIbu.getTanggal_lahir() + "'," +
                            "alamat='" + DatabaseVariable.tambahDataModelIbu.getAlamat() + "'," +
                            "rt='" + DatabaseVariable.tambahDataModelIbu.getRt() + "'," +
                            "rw='" + DatabaseVariable.tambahDataModelIbu.getRw() + "'," +
                            "id_dusun='" + DatabaseVariable.tambahDataModelIbu.getDusun() + "'," +
                            "pekerjaan='" + DatabaseVariable.tambahDataModelIbu.getPekerjaan() + "'," +
                            "no_bpjs='" + DatabaseVariable.tambahDataModelIbu.getNo_bpjs() + "'," +
                            "pendidikan='" + DatabaseVariable.tambahDataModelIbu.getPendidikan() + "'" +
                            "WHERE id_kia='" + DatabaseVariable.tambahDataModelIbu.getId_kia() + "' AND " +
                            "id_ibu='" + DatabaseVariable.tambahDataModelIbu.getId_ibu() + "'");
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int updateAnak() {
        try {
            database.execSQL("UPDATE anak SET " +
                    "nama_anak='" + DatabaseVariable.tambahModelAnak.getNama_anak() + "', " +
                    "alamat='" + DatabaseVariable.tambahModelAnak.getAlamat() + "', " +
                    "rt='" + DatabaseVariable.tambahModelAnak.getRt() + "', " +
                    "rw='" + DatabaseVariable.tambahModelAnak.getRw() + "', " +
                    "tempat_lahir='" + DatabaseVariable.tambahModelAnak.getTempat_lahir() + "', " +
                    "tanggal_lahir='" + DatabaseVariable.tambahModelAnak.getTanggal_lahir() + "', " +
                    "id_dusun='" + DatabaseVariable.tambahModelAnak.getDusun() + "', " +
                    "anak_ke='" + DatabaseVariable.tambahModelAnak.getAnakke() + "' " +
                    "WHERE " +
                    "id_anak='" + DatabaseVariable.tambahModelAnak.getId_anak() + "'"
            );
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }


    public int updateImunTambahan() {
        try {
            String[] namaVaksin = DatabaseVariable.modelInputImunTambahan.getcoordinate().split("\\.");
            if (namaVaksin[0].equalsIgnoreCase("0")) {
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='0.0'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='0.1'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='0.2'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='0.3'");
            } else if (namaVaksin[0].equalsIgnoreCase("1")) {
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='1.0'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='1.1'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='1.2'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='1.3'");
            } else if (namaVaksin[0].equalsIgnoreCase("2")) {
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='2.0'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='2.1'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='2.2'");
                database.execSQL("UPDATE imunisasi_tambahan SET nama_vaksin='"
                        + DatabaseVariable.modelInputImunTambahan.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                        "' AND coordinate='2.3'");
            }

            database.execSQL("UPDATE imunisasi_tambahan SET tanggal_pemberian='"
                    + DatabaseVariable.modelInputImunTambahan.getTanggal_pemberian() + "' WHERE " +
                    "id_anak='" + DatabaseVariable.modelInputImunTambahan.getId_anak() +
                    "' AND coordinate='" + DatabaseVariable.modelInputImunTambahan.getcoordinate() + "'");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int updateImunLain() {
        try {
            String[] namaVaksin = DatabaseVariable.modelInputVaksinLain.getCoordinate().split("\\.");

            if (namaVaksin[0].equalsIgnoreCase("0")) {
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='0.0'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='0.1'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='0.2'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='0.3'");
            } else if (namaVaksin[0].equalsIgnoreCase("1")) {
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='1.0'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='1.1'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='1.2'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='1.3'");
            } else if (namaVaksin[0].equalsIgnoreCase("2")) {
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='2.0'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='2.1'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='2.2'");
                database.execSQL("UPDATE imunisasi_lain SET nama_vaksin='"
                        + DatabaseVariable.modelInputVaksinLain.getNama_vaksin() + "' WHERE " +
                        "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                        "' AND coordinate='2.3'");
            }

            database.execSQL("UPDATE imunisasi_lain SET tanggal_pemberian='"
                    + DatabaseVariable.modelInputVaksinLain.getTanggal_pemberian() + "' WHERE " +
                    "id_anak='" + DatabaseVariable.modelInputVaksinLain.getId_anak() +
                    "' AND coordinate='" + DatabaseVariable.modelInputVaksinLain.getCoordinate() + "'");

            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            e.printStackTrace();
            return DatabaseVariable.FALSE_VALUE;
        }
    }
}
