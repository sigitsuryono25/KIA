package asia.sayateam.kiaadmin.Services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import asia.sayateam.kiaadmin.AsyncTasks.GetDataToServerHandler;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.LoginActivity;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 002, 02-Sep-17.
 */

public class IDBroadcast extends WakefulBroadcastReceiver {
    SecurePreferences preferences;
    Context context;
    public static final String PREF_NAME = "asia.kiaclient.admin.nextid";
    LoginActivity la;

    @Override
    public void onReceive(Context context, Intent intent) {

        preferences =
                new SecurePreferences(context, PREF_NAME.toString(),
                        PrefencesTambahData.ENCRYPT_KEY, true);
        this.context = context;
        new GetNextID().execute();
    }

    public class GetNextID extends AsyncTask<String, JSONObject, String> {
        HashMap<String, String> params;
        String result;
        JSONHander jsonHander = new JSONHander(la);
        JSONObject jsonObject;

        @Override
        protected String doInBackground(String... strings) {
            String URL = "https://pkmpantailunci.com/generate_id.php";
            params = new HashMap<>();
            params.put("dusun", "dusun");
            try {
                result = jsonHander.makeHttpRequest(URL, "GET", params);
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONArray next_ID;
            super.onPostExecute(s);
            try {
                Log.i("s", s);
                if (!s.equalsIgnoreCase(null)) {
                    jsonObject = new JSONObject(s);
                    next_ID = jsonObject.getJSONArray("next_id");
                    for (int i = 0; i < next_ID.length(); i++) {
                        JSONObject next_id = next_ID.getJSONObject(i);
                        preferences.put(GetDataToServerHandler.NEXT_ID,
                                next_id.getString("id_selanjutnya"));
                    }
                } else {

                }
            } catch (Exception e) {

            }
        }


    }
}
