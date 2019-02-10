package asia.sayateam.kiaclient.library;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Sigit Suryono on 28/03/2017.
 */

public class JSONHander {

    String charset = "UTF-8";
    HttpURLConnection connection;
    DataOutputStream wr;
    StringBuilder result;
    URL urlAdd;
    StringBuilder sbParams;
    String paramString;
    Activity activity;

    public JSONHander(Activity activity) {
        this.activity = activity;
        setActivity(activity);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public JSONHander() {
        activity = getActivity();
    }

    public String makeHttpRequest(String url, String method, HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        if (method.equalsIgnoreCase("POST")) {
            try {
                urlAdd = new URL(url);
                connection = (HttpURLConnection) urlAdd.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept-Charset", charset);
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);

                connection.connect();

                paramString = sbParams.toString();

                wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(paramString);
                wr.flush();
                wr.close();
            } catch (java.net.UnknownHostException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Server Tidak diketahui", Toast.LENGTH_SHORT).show();
                    }
                });
                return "java.net.UnknownHostException";
            } catch (java.net.SocketTimeoutException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Koneksi Timeout", Toast.LENGTH_SHORT).show();
                    }
                });
                return "java.net.SocketTimeoutException";
            } catch (IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Kesalahan saat membaca data", Toast.LENGTH_SHORT).show();
                    }
                });
                return "IOException";
            }
        } else if (method.equalsIgnoreCase("GET")) {
            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
                Log.d("URL", url);
            }

            try {
                urlAdd = new URL(url);
                connection = (HttpURLConnection) urlAdd.openConnection();
                connection.setDoInput(true);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept-Charset", charset);
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.connect();
            } catch (java.net.UnknownHostException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Server Tidak diketahui", Toast.LENGTH_SHORT).show();
                    }
                });
                return "java.net.UnknownHostException";
            } catch (java.net.SocketTimeoutException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Koneksi Timeout", Toast.LENGTH_SHORT).show();
                    }
                });
                return "java.net.SocketTimeoutException";
            } catch (IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Kesalahan saat membaca data", Toast.LENGTH_SHORT).show();
                    }
                });
                return "IOException";
            }
        }

        try {
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            return "java.net.SocketTimeoutException";
        } catch (IOException e) {
            e.printStackTrace();
            return "IOException";
        }

        connection.disconnect();

        return result.toString();
    }

    public void InterruptReq() {
        connection.disconnect();
    }

}
