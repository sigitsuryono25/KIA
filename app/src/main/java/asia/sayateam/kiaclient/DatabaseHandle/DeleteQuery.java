package asia.sayateam.kiaclient.DatabaseHandle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sigit Suryono on 07-Aug-17.
 */

public class DeleteQuery {

    SQLiteDatabase database;
    DatabaseHadler databaseHadler;

    public DeleteQuery(Context context) {
        databaseHadler = new DatabaseHadler(context);
        database = databaseHadler.OpenDatabase();
    }

    public int DeletDataPemilik() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.INFORMASI_PEMILIK);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletDataIbu() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.INFORMASI_IBU);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletDataAnak() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.INFORMASI_ANAK);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletImun012() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.IMUN_0_12);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletImun1TahunPlus() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.IMUN_SATU_TAHUN_PLUS);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletImunLain() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.IMUN_LAIN);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }
    public int DeletImunBIAS() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.BIAS);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }
    public int DeletImunTambahan() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.IMUN_TAMBAHAN);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletStatusGizi() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.STAT_GIZI);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletKesehatanAnak() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.KESEHATAN_ANAK);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }

    public int DeletKesehatanIbu() {
        try {
            database.execSQL("DELETE FROM " + DatabaseVariable.KESEHATAN_IBU);
            return DatabaseVariable.TRUE_VALUE;
        } catch (Exception e) {
            return DatabaseVariable.FALSE_VALUE;
        }
    }
}
