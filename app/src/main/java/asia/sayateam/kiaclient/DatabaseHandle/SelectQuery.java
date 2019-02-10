package asia.sayateam.kiaclient.DatabaseHandle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Sigit Suryono on 07-Aug-17.
 */

public class SelectQuery {

    Cursor cursor;
    DatabaseHadler databaseHadler;
    SQLiteDatabase database;

    public SelectQuery(Context context) {
        databaseHadler = new DatabaseHadler(context);
        database = databaseHadler.OpenDatabase();
    }

//    public Cursor getDataFor_0_12_bulan() {
//        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUNISASI_0_12_BULAN, null);
//        return cursor;
//    }

    public Cursor getDataFor_Info_Pemilik() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_PEMILIK, null);
        return cursor;
    }

    public Cursor getDataFor_Info_Ibu() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_IBU, null);
        return cursor;
    }

    public Cursor getNamaIbu(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_IBU + " WHERE id_ibu='" + id + "'", null);
        return cursor;
    }

    public Cursor getDataFor_Info_Anak() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " ORDER BY anak_ke ASC", null);
        return cursor;
    }

    public Cursor getJumlahAnak() {
        cursor = database.rawQuery("SELECT anak_ke, id_anak FROM " + DatabaseVariable.INFORMASI_ANAK, null);
        return cursor;
    }

    public Cursor getDetailAnakKe(String nama_anak) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " WHERE id_anak ='" + nama_anak + "'", null);

        return cursor;
    }

    public Cursor getDetailAnakKeByName(String nama_anak) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " WHERE nama_anak ='" + nama_anak + "'", null);

        return cursor;
    }


    public Cursor getDetailAnakKeById(String nama_anak) {
        Log.i("ID", "SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " WHERE id_anak ='" + nama_anak + "'");
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " WHERE id_anak ='" + nama_anak + "'", null);

        return cursor;
    }

    public Cursor getNamaIbu(String idIbu) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_IBU + " WHERE id_ibu='" + idIbu + "'", null);
        return cursor;
    }

    public Cursor getKesIbu() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.KESEHATAN_IBU + " ORDER BY tanggal_kunjungan", null);
        return cursor;
    }

    public Cursor getKesIbuCondition() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.KESEHATAN_IBU, null);
        return cursor;
    }

    public Cursor getKesAnak(String id_anak) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.KESEHATAN_ANAK + " WHERE id_anak='" + id_anak + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getKesAnakTanggal(String waktu, String id_anak, String pemberian_ke) {
        cursor = database.rawQuery("SELECT " +
                "tanggal_pemberian, dosis, keterangan FROM " +
                DatabaseVariable.KESEHATAN_ANAK + " " +
                "WHERE waktu_pemberian='" + waktu + "' " +
                "AND id_anak='" + id_anak + "' AND pemberian_ke='" +
                pemberian_ke + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getBIAS(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.BIAS + " WHERE id_anak='" + id + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getImun0_12(int id, int i, int j) {
        Log.i("SELECT", "SELECT * FROM " + DatabaseVariable.IMUN_0_12 + " WHERE id_anak='" + id + "' AND coordinate='" + i + "." + j + "' ORDER BY tanggal ASC");
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUN_0_12 + " WHERE id_anak='" + id + "' AND coordinate='" + i + "." + j + "' ORDER BY tanggal ASC", null);
        return cursor;
    }

    public Cursor getImunDiatas1Tahun(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUN_SATU_TAHUN_PLUS + " WHERE id_anak='" + id + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getImunLain(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUN_LAIN + " WHERE id_anak='" + id + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getImunTambahan(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUN_TAMBAHAN + " WHERE id_anak='" + id + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getStatusGizi(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.STAT_GIZI + " WHERE id_anak='" + id + "' ORDER BY umur_bulan ASC", null);
        return cursor;
    }
}
