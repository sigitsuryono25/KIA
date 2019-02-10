package asia.sayateam.kiaadmin.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.UpdateQuery;
import asia.sayateam.kiaadmin.EditDataInit;
import asia.sigitsuryono.kialibrary.JSONHander;

/**
 * Created by Sigit Suryono on 09-Aug-17.
 */

public class SendDataToServerHandler {

    public static final String SUCCESS = "success";

    ProgressDialog progressDialog;
    Activity context;
    JSONHander jsonHander;
    JSONObject jsonObject;
    String result;
    HashMap<String, String> params;
    DatabaseHadler databaseHadler;
    SQLiteDatabase database;
    Cursor c;
    String id_kia;
    SelectQuery selectQuery;
    DeleteQuery deleteQuery;
    InsertQuery insertQuery;
    int i = 1;

    public SendDataToServerHandler(Activity context) {
        this.context = context;
        jsonHander = new JSONHander(context);
        databaseHadler = new DatabaseHadler(context);
        database = databaseHadler.OpenDatabase();
        selectQuery = new SelectQuery(context);
        insertQuery = new InsertQuery(context);
        deleteQuery = new DeleteQuery(context);
    }

    public void DismissDialog() {
        progressDialog.dismiss();
        progressDialog = null;
    }

    public class sendDataTambahToServer extends AsyncTask<String, JSONObject, String> {
        String pengenal;

        public sendDataTambahToServer(String pengenal) {
            this.pengenal = pengenal;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan Data...");
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            c = selectQuery.getDataFor_Info_Anak();
            String jumlahAnak = String.valueOf(c.getCount());
            Log.i("COUNT", String.valueOf(c.getCount()));


            c = null;
            c = selectQuery.getDataFor_Info_Pemilik();
            while (c.moveToNext()) {
                Log.d("id", c.getString(0));
                params.put(ConfigureApps.TYPE, ConfigureApps.TAMBAH_PEMILIK);
                params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);
                params.put(ConfigureApps.ID_KIA, c.getString(0));
                id_kia = c.getString(0);
                params.put(ConfigureApps.NAMA_PEMILIK, c.getString(1));
                params.put(ConfigureApps.TEMPAT_LAHIR, c.getString(2));
                params.put(ConfigureApps.TANGGAL_LAHIR, c.getString(3));
                params.put(ConfigureApps.ALAMAT, c.getString(4));
                params.put(ConfigureApps.RT, c.getString(5));
                params.put(ConfigureApps.RW, c.getString(6));
                params.put(ConfigureApps.ID_DUSUN, c.getString(7));
                params.put(ConfigureApps.PEKERJAAN, c.getString(8));
                params.put(ConfigureApps.NO_BPJS, c.getString(9));
                params.put(ConfigureApps.PENDIDIKAN, c.getString(10));
                params.put(ConfigureApps.TANGGAL_REG, c.getString(11));
                params.put(ConfigureApps.JUMLAH_ANAK, jumlahAnak);

                result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            }
            c = null;

            c = selectQuery.getDataFor_Info_Ibu();
            params = new HashMap<>();
            while (c.moveToNext()) {
                params.put(ConfigureApps.TYPE, ConfigureApps.TAMBAH_IBU);
                params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

                params.put(ConfigureApps.NAMA_IBU, c.getString(0));
                params.put(ConfigureApps.TEMPAT_LAHIR_IBU, c.getString(1));
                params.put(ConfigureApps.TANGGAL_LAHIR_IBU, c.getString(2));
                params.put(ConfigureApps.ALAMAT_IBU, c.getString(3));
                params.put(ConfigureApps.RT_IBU, c.getString(4));
                params.put(ConfigureApps.RW_IBU, c.getString(5));
                params.put(ConfigureApps.ID_DUSUN_IBU, c.getString(6));
                params.put(ConfigureApps.PEKERJAAN_IBU, c.getString(7));
                params.put(ConfigureApps.NO_BPJS_IBU, c.getString(8));
                params.put(ConfigureApps.PENDIDIKAN_IBU, c.getString(9));
                params.put(ConfigureApps.ID_KIA_IBU, c.getString(10));
                params.put(ConfigureApps.ID_IBU, c.getString(11));

                result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            }

            c = null;
            c = selectQuery.getDataFor_Info_Anak();
            params = new HashMap<>();
            while (c.moveToNext()) {
                params.put(ConfigureApps.TYPE, ConfigureApps.TAMBAH_ANAK);
                params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

                params.put(ConfigureApps.ID_ANAK, c.getString(0));
                params.put(ConfigureApps.NAMA_ANAK, c.getString(1));
                params.put(ConfigureApps.ALAMAT_ANAK, c.getString(2));
                params.put(ConfigureApps.RT_ANAK, c.getString(3));
                params.put(ConfigureApps.RW_ANAK, c.getString(4));
                params.put(ConfigureApps.TEMPAT_LAHIR_ANAK, c.getString(5));
                params.put(ConfigureApps.TANGGAL_LAHIR_ANAK, c.getString(6));
                params.put(ConfigureApps.NO_BPJS, c.getString(7));
                params.put(ConfigureApps.ID_DUSUN_ANAK, c.getString(8));
                params.put(ConfigureApps.ID_KIA_IBU, c.getString(9));
                params.put(ConfigureApps.ID_IBU, c.getString(10));
                params.put(ConfigureApps.ANAK_KE, c.getString(11));

                result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("s", s);
            if (pengenal.equalsIgnoreCase(EditDataInit.EDIT)) {

            } else {
                uploadFile(id_kia);
            }
            try {

                if (!s.equals(null)) {
                    jsonObject = new JSONObject(s);

                    Log.i("s", s);

                    Log.d("SUCCESS", jsonObject.getString(SUCCESS));
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        try {
                            deleteQuery.DeletDataAnak();
                            deleteQuery.DeletDataIbu();
                            deleteQuery.DeletDataPemilik();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                        context.finish();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public int uploadFile(final String idPeserta) {
            String SERVER_URL = "http://sdeveloper.esy.es/web_services/uploads.php";
            String root = Environment.getExternalStorageDirectory().toString();
            String selectedFilePath = root + "/android/data/" + context.getPackageName() + "/" + idPeserta + ".png";
            Log.i("", selectedFilePath);
            int serverResponseCode = 0;

            HttpURLConnection connection;
            DataOutputStream dataOutputStream;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024; /*Ukuran File maksimal*/
            File selectedFile = new File(selectedFilePath);

            if (!selectedFile.isFile()) {
                Log.i("message", "Source File Doesn't Exist: ");
                return 0;
            } else {
                try {
                    FileInputStream fileInputStream = new FileInputStream(selectedFile);
                    URL url = new URL(SERVER_URL);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod(ConfigureApps.METHOD);
                    connection.setRequestProperty("Connection", "Keep-Alive");
                    connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                    connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    connection.setRequestProperty("uploaded_file", selectedFilePath);

                    dataOutputStream = new DataOutputStream(connection.getOutputStream());

                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                            + selectedFilePath + "\"" + lineEnd);

                    dataOutputStream.writeBytes(lineEnd);

                    bytesAvailable = fileInputStream.available();

                    //selecting the buffer size as minimum of available bytes or 1 MB
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);

                    buffer = new byte[bufferSize];

                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dataOutputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    serverResponseCode = connection.getResponseCode();
                    String serverResponseMessage = connection.getResponseMessage();

                    Log.d("response", serverResponseMessage);
                    if (serverResponseCode == 200) {

                    }

                    fileInputStream.close();
                    dataOutputStream.flush();
                    dataOutputStream.close();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i("Message", "Berkas Tidak Ditemukan");
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                    Log.i("message", "URL tidak valid!");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("message", "Cannot Read/Write File!");
                }

                selectedFile.delete();
                return serverResponseCode;
            }
        }
    }


    public class sendDataKesehatanIbuHamilToServer extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan data...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.KES_IBU);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            c = selectQuery.getKesBumil();
            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    params.put(ConfigureApps.ID_IBU_KES_BUMIL, c.getString(0));
                    params.put(ConfigureApps.TANGGAL_KUNJUNGAN, c.getString(1));
                    params.put(ConfigureApps.TANGGAL_HAID_TERAKHIR, c.getString(2));
                    params.put(ConfigureApps.TANGGAL_TAKSIRAN_KELAHIRAN, c.getString(3));
                    params.put(ConfigureApps.LINGKAR_LENGAN_ATAS, c.getString(4));
                    params.put(ConfigureApps.TINGGI_BADAN, c.getString(5));
                    params.put(ConfigureApps.PENGGUNAAN_KONTRASEPSI, c.getString(6));
                    params.put(ConfigureApps.RIWAYAT_PENYAKIT, c.getString(7));
                    params.put(ConfigureApps.RIWAYAT_ALERGI, c.getString(8));
                    params.put(ConfigureApps.HAMIL_KE, c.getString(9));
                    params.put(ConfigureApps.JUMLAH_PERSALINAN, c.getString(10));
                    params.put(ConfigureApps.JUMLAH_KEGUGURAN, c.getString(11));
                    params.put(ConfigureApps.JUMLAH_ANAK_HIDUP, c.getString(12));
                    params.put(ConfigureApps.JUMLAH_ANAK_MATI, c.getString(13));
                    params.put(ConfigureApps.JUMLAH_ANAK_PREMATUR, c.getString(14));
                    params.put(ConfigureApps.JUMLAH_KEHAMILAN_TERAKHIR, c.getString(15));
                    params.put(ConfigureApps.STATUS_IMUNISASI_TT, c.getString(16));
                    params.put(ConfigureApps.IMUNISASI_TT_TERAKHIR, c.getString(17));
                    params.put(ConfigureApps.PENOLONG_PERSALINAN_TERAKHIR, c.getString(18));
                    params.put(ConfigureApps.CARA_PERSALINAN_TERAKHIR, c.getString(19));
                    params.put(ConfigureApps.TANGGAL_PEMERIKSAAN_TERAKHIR, c.getString(20));
                    params.put(ConfigureApps.KETERANGAN_TERAKHIR, c.getString(21));

                    result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
                }
            }


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", jsonObject.getString(SUCCESS));
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataStatusGiziToServer extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan data...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.STAT_GIZI);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.BULAN_KE,
                    DatabaseVariable.modelInputStatusGizi.getBulan_ke());
            params.put(ConfigureApps.TANGGAL,
                    DatabaseVariable.modelInputStatusGizi.getTanggal());
            params.put(ConfigureApps.BERAT_BADAN,
                    DatabaseVariable.modelInputStatusGizi.getBerat_badan());
            params.put(ConfigureApps.NORMAL_TIDAK,
                    DatabaseVariable.modelInputStatusGizi.getNormal_tidak());
            params.put(ConfigureApps.STATUS_GIZI,
                    DatabaseVariable.modelInputStatusGizi.getStatus_gizi());
            params.put(ConfigureApps.KETERANGAN,
                    DatabaseVariable.modelInputStatusGizi.getKeterangan());
            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputStatusGizi.getId_anak());

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", jsonObject.getString(SUCCESS));
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataImun_0_12 extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan data...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_0_12);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputImunisasi0_12.getId_anak());
            params.put(ConfigureApps.BULAN_KE,
                    DatabaseVariable.modelInputImunisasi0_12.getBulan_ke());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputImunisasi0_12.getTanggal_pemberian());
            params.put(ConfigureApps.COORDINATE_X,
                    DatabaseVariable.modelInputImunisasi0_12.getCoordinate_x());
            params.put(ConfigureApps.COORDINATE_Y,
                    DatabaseVariable.modelInputImunisasi0_12.getCoordinate_y());

            insertQuery.insertImun012();
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataImunDiatas1Tahun extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan Data...");
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_DIATAS_1_TAHUN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputImunDiatas1Tahun.getId_anak());
            params.put(ConfigureApps.BULAN_KE,
                    DatabaseVariable.modelInputImunDiatas1Tahun.getBulan_ke());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputImunDiatas1Tahun.getTanggal_pemberian());
            params.put(ConfigureApps.COORDINATE_X,
                    DatabaseVariable.modelInputImunDiatas1Tahun.getCoordinate_x());
            params.put(ConfigureApps.COORDINATE_Y,
                    DatabaseVariable.modelInputImunDiatas1Tahun.getCoordinate_y());

            insertQuery.insertImun1TahunPlus();
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataBias extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan data...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.BIAS);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputBIAS.getId_anak());
            params.put(ConfigureApps.TINGKATAN,
                    DatabaseVariable.modelInputBIAS.getTingkatan());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputBIAS.getTanggal_pemberian());
            params.put(ConfigureApps.COORDINATE_X,
                    DatabaseVariable.modelInputBIAS.getCoordinate_x());
            params.put(ConfigureApps.COORDINATE_Y,
                    DatabaseVariable.modelInputBIAS.getCoordinate_y());

            insertQuery.insertImunBIAS();
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataImunTambahan extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan data...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_TAMBAHAN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputImunTambahan.getId_anak());
            params.put(ConfigureApps.NAMA_VAKSIN,
                    DatabaseVariable.modelInputImunTambahan.getNama_vaksin());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputImunTambahan.getTanggal_pemberian());
            params.put("coordinate",
                    DatabaseVariable.modelInputImunTambahan.getcoordinate());

            UpdateQuery updateQuery = new UpdateQuery(context);
            updateQuery.updateImunTambahan();
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (!s.equals(null)) {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class sendDataImunVaksiLain extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Mengirimkan data...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_VAKSIN_LAIN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputVaksinLain.getId_anak());
            params.put(ConfigureApps.NAMA_VAKSIN,
                    DatabaseVariable.modelInputVaksinLain.getNama_vaksin());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputVaksinLain.getTanggal_pemberian());
            params.put("coordinate", DatabaseVariable.modelInputVaksinLain.getCoordinate());

            UpdateQuery updateQuery = new UpdateQuery(context);
            updateQuery.updateImunLain();
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (!s.equals(null)) {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public static class sendDataKesehatanAnak extends AsyncTask<String, JSONObject, String> {

        ProgressDialog progressDialog;
        Activity context;
        HashMap<String, String> params;
        String result;
        JSONObject jsonObject;
        JSONHander jsonHander;
        AsyncResponse asyncResponse = null;

        public interface AsyncResponse {
            void onProcessFinish(Boolean status, String message);
        }

        public sendDataKesehatanAnak(Activity context, AsyncResponse asyncResponse) {
            this.asyncResponse = asyncResponse;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                if (progressDialog != null) {
                    progressDialog = null;
                }
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Mengirimkan data...");
                progressDialog.setCancelable(true);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.KES_ANAK);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelInputKesehatanAnak.getId_anak());
            params.put(ConfigureApps.ID_VITAMIN,
                    DatabaseVariable.modelInputKesehatanAnak.getId_vitamin());
            params.put(ConfigureApps.WAKTU_PEMBERIAN,
                    DatabaseVariable.modelInputKesehatanAnak.getWaktu_pemberian());
            params.put(ConfigureApps.DOSIS,
                    DatabaseVariable.modelInputKesehatanAnak.getDosis());
            params.put(ConfigureApps.TANGGAL_PEMBERIAN,
                    DatabaseVariable.modelInputKesehatanAnak.getTanggal_pemberian());
            params.put(ConfigureApps.PEMBERIAN_KE,
                    DatabaseVariable.modelInputKesehatanAnak.getPemberian_ke());
            params.put(ConfigureApps.KETERANGAN,
                    DatabaseVariable.modelInputKesehatanAnak.getKeterangan());

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
//                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "Pilih Kembali Nama Anak Untuk Melihat Hasil Input", Toast.LENGTH_SHORT).show();
                        asyncResponse.onProcessFinish(true, s);
                    } else {
                        asyncResponse.onProcessFinish(false, s);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataKeteranganImunisasi extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.KET_IMUNISASI);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getId_anak());
            params.put("tanggal",
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getTanggal());
            params.put("hasil",
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil());
            params.put("keterangan",
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getKeterangan());

            deleteQuery.DeletKetImun();
            insertQuery.insertKetImun();

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class sendDataKeteranganGizi extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();

            params.put(ConfigureApps.TYPE, ConfigureApps.KET_GIZI);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_INSERT);

            params.put(ConfigureApps.ID_ANAK,
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getId_anak());
            params.put("tanggal",
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getTanggal());
            params.put("hasil",
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil());
            params.put("keterangan",
                    DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getKeterangan());


            deleteQuery.DeletKetGizi();
            insertQuery.insertKetGizi();
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    Log.d("SUCCESS", s);
                    if (s.contains("\"success\":0")) {
                        progressDialog.dismiss();
                        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                        context.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

