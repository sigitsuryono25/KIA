package asia.sayateam.kiaadmin.DatabaseHandle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Sigit Suryono on 12-Aug-17.
 */

public class DatabaseHadler extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "sayateam.kiaadmin.db";
    private static final int DATABASE_VERSION = 13;

    public DatabaseHadler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase OpenDatabase() {
        return getWritableDatabase();
    }


}

