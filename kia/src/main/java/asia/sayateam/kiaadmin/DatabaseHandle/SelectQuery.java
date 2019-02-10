package asia.sayateam.kiaadmin.DatabaseHandle;

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

    public Cursor getDataFor_Info_Pemilik() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_PEMILIK, null);
        return cursor;
    }

    public Cursor getDataFor_Info_Ibu() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_IBU, null);
        return cursor;
    }

    public Cursor getIbu(String id_ibu) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_IBU + " WHERE id_ibu='" + id_ibu + "'", null);
        return cursor;
    }

    public Cursor getDataFor_Info_Anak() {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK, null);
        return cursor;
    }

    public Cursor getJumlahAnak() {
        cursor = database.rawQuery("SELECT anak_ke, id_anak FROM " + DatabaseVariable.INFORMASI_ANAK, null);
        return cursor;
    }

    public Cursor getDetailAnakKe(String namaAnak) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " WHERE nama_anak='" + namaAnak + "'", null);
        return cursor;
    }

    public Cursor getDetailAnakKeKesehatan(String anak) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.INFORMASI_ANAK + " WHERE nama_anak LIKE '%" + anak + "%'", null);
        return cursor;
    }

    public Cursor getDusun() {
        cursor = database.rawQuery("SELECT * FROM dusun", null);
        return cursor;
    }

    public Cursor getJenisImunisasi() {
        cursor = database.rawQuery("SELECT * FROM jenis_imunisasi", null);
        return cursor;
    }

    public Cursor getIDDusun(String dusun) {
        Log.i("QUERY", "SELECT id_dusun FROM dusun WHERE nama_dusun='" + dusun + "'");
        cursor = database.rawQuery("SELECT id_dusun FROM dusun WHERE nama_dusun='" + dusun + "'", null);
        return cursor;
    }


    public Cursor getKesAnakTanggal(String waktu, String idAnak, String pemberian_ke) {
        cursor = database.rawQuery("SELECT tanggal_pemberian, dosis, keterangan FROM " +
                DatabaseVariable.KESEHATAN_ANAK + " WHERE waktu_pemberian='" + waktu +
                "' AND id_anak='" + idAnak + "' AND pemberian_ke='" + pemberian_ke + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getImun0_12(int id, int i, int j) {
        Log.i("SELECT", "SELECT * FROM " + DatabaseVariable.IMUN_0_12 + " WHERE id_anak='" + id + "' AND coordinate='" + i + "." + j + "' AND bulan_ke  ORDER BY tanggal ASC");
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUN_0_12 + " WHERE id_anak='" + id + "' AND coordinate='" + i + "." + j + "' ORDER BY tanggal ASC", null);
        return cursor;
    }

    public Cursor getBIAS(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.BIAS + " WHERE id_anak='" + id + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getImunDiatas1Tahun(int id) {
        cursor = database.rawQuery("SELECT * FROM " + DatabaseVariable.IMUN_SATU_TAHUN_PLUS + " WHERE id_anak='" + id + "' ORDER BY tanggal_pemberian ASC", null);
        return cursor;
    }

    public Cursor getKetImun(int id) {
        cursor = database.rawQuery("SELECT * FROM keterangan_imunisasi WHERE id_anak='" + id + "'", null);
        return cursor;
    }

    public Cursor getKetGizi(int id) {
        cursor = database.rawQuery("SELECT * FROM keterangan_gizi WHERE id_anak='" + id + "'", null);
        return cursor;
    }

    public Cursor getKesBumil() {
        cursor = database.rawQuery("SELECT * FROM kesehatan_ibu", null);
        return cursor;
    }
}
