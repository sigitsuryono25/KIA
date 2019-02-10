package asia.sayateam.kiaclient.config;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

import asia.sayateam.kiaclient.MainMenuActivity;


public class SessionManager {
    public static final String KEY_USER = "user";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_SERVICE_STATE = "state";

    // store user and email. Make this public supaya bisa diakses dari luar
    public static final String KEY_DAYS = "days";
    public static final String KEY_HOURS = "hours";
    public static final String KEY_MINUTE = "minutes";
    public static final String KEY_DAYS_POSITION = "days_position";
    public static final String KEY_HOURS_POSITION = "hours_position";
    public static final String KEY_MINIMUM_STOK = "stok_minimum";
    // public static final SharedPreferences PREF_NAME =
    // PreferenceManager.getDefaultSharedPreferences(_context);
    private static final String IS_LOGIN = "isLoggedIn";
    Editor editor;
    Context _context;
    SecurePreferences preferences;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        // TODO Auto-generated constructor stub
        this._context = context;
        preferences = new SecurePreferences(_context, context.getPackageName().toString(), "SometopSecretKey1235", true);
//        pref = PreferenceManager.getDefaultSharedPreferences(_context);
//        Log.e("", pref.toString());
//        editor = pref.edit();
    }

    public void createSession(String userID, String nama, String address) {

        // menyimpan username
        preferences.put(KEY_USER, userID);
        // menyimpan nama user
        preferences.put(KEY_NAME, nama);
        // menyimpan password
        // menyimpan email
        preferences.put(KEY_ADDRESS, address);

    }

    public void saveStateSettings(Boolean state, int hours, int minute, int days, int positionDays, int positionTime,
                                  String stok) {
        editor.putBoolean(KEY_SERVICE_STATE, state);
        editor.putInt(KEY_HOURS, hours);
        editor.putInt(KEY_MINUTE, minute);
        editor.putInt(KEY_DAYS, days);
        editor.putInt(KEY_HOURS_POSITION, positionTime);
        editor.putInt(KEY_DAYS_POSITION, positionDays);
        editor.putString(KEY_MINIMUM_STOK, stok);
        editor.commit();

    }

    // menghapus data ketika logout
    public void logoutUser() {
        preferences.clear();

//        Intent i = new Intent(_context, LoginActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        _context.startActivity(i);
    }

    // pengecekkan status login

    public void checkLogin() {
        if ((preferences.containsKey(KEY_NAME))) {
            if (preferences.containsKey(KEY_USER)) {
                Intent i = new Intent(_context, MainMenuActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                _context.startActivity(i);
            }
        }
    }
}
