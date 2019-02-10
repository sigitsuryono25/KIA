package asia.sayateam.kiaadmin.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import asia.sayateam.kiaadmin.Adapter.AdapterSubMenuTambah;
import asia.sayateam.kiaadmin.Config.ConfigureApps;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseHadler;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.DeleteQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.InsertQuery;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.EditDataInit;
import asia.sayateam.kiaadmin.FragmentTambah.TambahAnakFragment;
import asia.sayateam.kiaadmin.FragmentTambah.TambahHanyaAnakFragment;
import asia.sayateam.kiaadmin.FragmentTambah.TambahPemilikFragment;
import asia.sayateam.kiaadmin.SubMenuEdit;
import asia.sayateam.kiaadmin.SubMenuInput;
import asia.sigitsuryono.kialibrary.JSONHander;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 09-Aug-17.
 */

public class GetDataToServerHandler {

    public static final String NEXT_ID = "next_id";
    public static final String NEXT_ID_IBU = "next_id_ibu";

    Activity context;
    JSONHander jsonHander;
    JSONObject jsonObject;
    String result;
    HashMap<String, String> params;
    DatabaseHadler databaseHadler;
    SQLiteDatabase database;
    Cursor c;
    SelectQuery selectQuery;
    InsertQuery insertQuery;
    ProgressDialog progressDialog;
    DeleteQuery deleteQuery;
    SecurePreferences preferences;

    public GetDataToServerHandler(Activity context) {
        this.context = context;
        jsonHander = new JSONHander(context);
        databaseHadler = new DatabaseHadler(context);
        database = databaseHadler.OpenDatabase();
        selectQuery = new SelectQuery(context);
        insertQuery = new InsertQuery(context);
        deleteQuery = new DeleteQuery(context);

        preferences = new SecurePreferences(context, context.getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY
                , true);
    }

    public void DismissProgressDialog(){
        progressDialog.dismiss();
        progressDialog = null;
    }

    public class GetPemilikKIA extends AsyncTask<String, JSONObject, String> {

        String id_kia;

        public GetPemilikKIA() {
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.LOGIN_KIA);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);

            params.put(ConfigureApps.ID_KIA, strings[0]);
            id_kia = strings[0];
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONArray detailPemilik;
            String TAG_SUCCESS_PEMILIK = "success_pemilik";

            String TAG_DETAIL_PEMILIK = "detail_pemilik";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_PEMILIK).equalsIgnoreCase("0")) {
                        detailPemilik = jsonObject.getJSONArray(TAG_DETAIL_PEMILIK);
                        for (int i = 0; i < detailPemilik.length(); i++) {
                            JSONObject pemilikDetail = detailPemilik.getJSONObject(i);
                            DatabaseVariable.tambahDataModelPemilik.setId_kia(pemilikDetail.getString("id_kia"));
                            DatabaseVariable.tambahDataModelPemilik.setNamaPesertaValue(pemilikDetail.getString("nama"));
                            DatabaseVariable.tambahDataModelPemilik.setTempatLahirPeserta(pemilikDetail.getString("tempat_lahir"));
                            DatabaseVariable.tambahDataModelPemilik.setTanggalLahirPeserta(pemilikDetail.getString("tanggal_lahir"));
                            DatabaseVariable.tambahDataModelPemilik.setAlamatPeserta(pemilikDetail.getString("alamat"));
                            DatabaseVariable.tambahDataModelPemilik.setRT(pemilikDetail.getString("rt"));
                            DatabaseVariable.tambahDataModelPemilik.setRW(pemilikDetail.getString("rw"));
                            DatabaseVariable.tambahDataModelPemilik.setIdDusun(pemilikDetail.getString("id_dusun"));
                            DatabaseVariable.tambahDataModelPemilik.setPekerjaanPeserta(pemilikDetail.getString("pekerjaan"));
                            DatabaseVariable.tambahDataModelPemilik.setNoBPJS(pemilikDetail.getString("no_bpjs"));
                            DatabaseVariable.tambahDataModelPemilik.setPendidikan(pemilikDetail.getString("pendidikan"));
                            DatabaseVariable.tambahDataModelPemilik.setTanggalRegistrasi(pemilikDetail.getString("tanggal_registrasi"));
                            DatabaseVariable.tambahDataModelPemilik.setJumlahAnak(pemilikDetail.getString("jumlah_anak"));
                            insertQuery.InsertDataInfoPemilik();
                        }
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class GetIbu extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IBU);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);

            params.put(ConfigureApps.ID_KIA, strings[0]);
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONArray detailIbu;
            String TAG_SUCCESS_IBU = "success_ibu";

            String TAG_DETAIL_IBU = "detail_ibu";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IBU).equalsIgnoreCase("0")) {
                        detailIbu = jsonObject.getJSONArray(TAG_DETAIL_IBU);
                        for (int i = 0; i < detailIbu.length(); i++) {
                            JSONObject ibuDetail = detailIbu.getJSONObject(i);
                            DatabaseVariable.tambahDataModelIbu.setNama_ibu(ibuDetail.getString("nama"));
                            DatabaseVariable.tambahDataModelIbu.setTempat_lahir(ibuDetail.getString("tempat_lahir"));
                            DatabaseVariable.tambahDataModelIbu.setTanggal_lahir(ibuDetail.getString("tanggal_lahir"));
                            DatabaseVariable.tambahDataModelIbu.setAlamat(ibuDetail.getString("alamat"));
                            DatabaseVariable.tambahDataModelIbu.setRt(ibuDetail.getString("rt"));
                            DatabaseVariable.tambahDataModelIbu.setRw(ibuDetail.getString("rw"));
                            DatabaseVariable.tambahDataModelIbu.setDusun(ibuDetail.getString("id_dusun"));
                            DatabaseVariable.tambahDataModelIbu.setPekerjaan(ibuDetail.getString("pekerjaan"));
                            DatabaseVariable.tambahDataModelIbu.setNo_bpjs(ibuDetail.getString("no_bpjs"));
                            DatabaseVariable.tambahDataModelIbu.setPendidikan(ibuDetail.getString("pendidikan"));
                            DatabaseVariable.tambahDataModelIbu.setId_ibu(ibuDetail.getString("id_ibu"));
                            DatabaseVariable.tambahDataModelIbu.setId_kia(ibuDetail.getString("id_kia"));
                            insertQuery.InsertDataInfoIbu();
//                            new GetCatatanKesBuMil().execute(new String[]{id_ibu});
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetAnak extends AsyncTask<String, JSONObject, String> {
        SecurePreferences securePreferences;

        public GetAnak() {
            securePreferences = new SecurePreferences(context,
                    context.getPackageName().toLowerCase(), PrefencesTambahData.ENCRYPT_KEY, true);
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
            params.put(ConfigureApps.TYPE, ConfigureApps.ANAK);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);

            params.put(ConfigureApps.ID_KIA, strings[0]);
            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONArray detailAnak;
            String TAG_SUCCESS_ANAK = "success_anak";

            String TAG_DETAIL_ANAK = "detail_anak";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                progressDialog.dismiss();
                try {Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_ANAK).equalsIgnoreCase("0")) {
                        detailAnak = jsonObject.getJSONArray(TAG_DETAIL_ANAK);
                        for (int i = 0; i < detailAnak.length(); i++) {
                            JSONObject anakDetail = detailAnak.getJSONObject(i);
                            DatabaseVariable.tambahModelAnak.setId_anak(anakDetail.getString("id_anak"));
                            DatabaseVariable.tambahModelAnak.setNama_anak(anakDetail.getString("nama_anak"));
                            DatabaseVariable.tambahModelAnak.setAlamat(anakDetail.getString("alamat"));
                            DatabaseVariable.tambahModelAnak.setRt(anakDetail.getString("rt"));
                            DatabaseVariable.tambahModelAnak.setRw(anakDetail.getString("rw"));
                            DatabaseVariable.tambahModelAnak.setTempat_lahir(anakDetail.getString("tempat_lahir"));
                            DatabaseVariable.tambahModelAnak.setTanggal_lahir(anakDetail.getString("tanggal_lahir"));
                            DatabaseVariable.tambahModelAnak.setDusun(anakDetail.getString("id_dusun"));
                            DatabaseVariable.tambahModelAnak.setId_ibu(anakDetail.getString("id_ibu"));
                            DatabaseVariable.tambahModelAnak.setId_kia(anakDetail.getString("id_kia"));
                            DatabaseVariable.tambahModelAnak.setAnakke(anakDetail.getString("anak_ke"));
                            DatabaseVariable.tambahModelAnak.setNo_bpjs(anakDetail.getString("no_bpjs"));
                            insertQuery.InsertDataInfoAnak();
                        }
                        if (preferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.EDIT)) {
                            Intent i = new Intent(context, SubMenuEdit.class);
                            context.startActivity(i);
                            context.finish();
                        } else if (preferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.TAMBAH_ANAK)) {
                            Intent i = new Intent(context, TambahHanyaAnakFragment.class);
                            i.putExtra(EditDataInit.PENGENAL, EditDataInit.EDIT);
                            context.startActivity(i);
                            context.finish();
                        } else if (preferences.getString(AdapterSubMenuTambah.STATUS).equalsIgnoreCase(AdapterSubMenuTambah.INPUT)) {
                            context.startActivity(new Intent(context, SubMenuInput.class));
                            context.finish();
                        }
                    } else {
                        Toast.makeText(context, "Tidak ada data ditemukan", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog = null;
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetDusun extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
//            String URL = ConfigureApps.HOST + "Tugas_Galau/Project/web_services/get_dusun.php";
            String URL = ConfigureApps.HOST + "get_dusun.php";
            params = new HashMap<>();
            params.put("dusun", "dusun");
            result = jsonHander.makeHttpRequest(URL, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            JSONArray detail_dusun;
            super.onPostExecute(s);
            try {
                if (!s.equalsIgnoreCase(null)) {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);
                    if (!s.contains("\"success_admin\":1,\"message\":\"user not found\"")) {
                        detail_dusun = jsonObject.getJSONArray("detail_dusun");
                        for (int i = 0; i < detail_dusun.length(); i++) {
                            JSONObject dusun_detail = detail_dusun.getJSONObject(i);
                            DatabaseVariable.modelGetDusun.setNamaDusun(dusun_detail.getString("nama_dusun"));
                            DatabaseVariable.modelGetDusun.setId(dusun_detail.getString("id_dusun"));
                            insertQuery.insertDusun();
                        }
                    }
                } else {

                }
            } catch (Exception e) {

            }
        }

    }

    public class GetJenisImunisasi extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
//            String URL = ConfigureApps.HOST + "Tugas_Galau/Project/web_services/get_jenis_imunisasi.php";
            String URL = ConfigureApps.HOST + "get_jenis_imunisasi.php";
            params = new HashMap<>();
            params.put("dusun", "dusun");
            result = jsonHander.makeHttpRequest(URL, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            JSONArray detail_jenis_imunisasi;
            super.onPostExecute(s);
            try {
                Log.i("s", s);
                if (!s.equalsIgnoreCase(null)) {
                    jsonObject = new JSONObject(s);
                    if (!s.contains("\"success_admin\":1,\"message\":\"user not found\"")) {
                        detail_jenis_imunisasi = jsonObject.getJSONArray("detail_jenis_imunisasi");
                        for (int i = 0; i < detail_jenis_imunisasi.length(); i++) {
                            JSONObject imunisasi = detail_jenis_imunisasi.getJSONObject(i);

                            DatabaseVariable.modelGetJenisImunisasi.
                                    setId_jenis_imunisasi(imunisasi.getString("id_jenis_imunisasi"));
                            DatabaseVariable.modelGetJenisImunisasi.
                                    setNama_imunisasi(imunisasi.getString("nama_imunisasi"));

                            insertQuery.insertJenisImunisasi();
                        }
                    }
                } else {

                }
            } catch (Exception e) {

            }
        }


    }

    public class GetCatatanKesBuMil extends AsyncTask<String, JSONObject, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Sedang Mengunduh data\nMembutuhkan waktu beberapa detik...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.KES_IBU);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_IBU, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_KES_IBU = "detail_kes_ibu";
            JSONArray detailkes_ibu;
            String TAG_SUCCESS_KES_IBU = "success_kes_ibu";
            super.onPostExecute(s);
            try {
                Log.i("s", s);
                jsonObject = new JSONObject(s);

                if (jsonObject.getString(TAG_SUCCESS_KES_IBU).equalsIgnoreCase("0")) {
                    detailkes_ibu = jsonObject.getJSONArray(TAG_DETAIL_KES_IBU);
                    for (int i = 0; i < detailkes_ibu.length(); i++) {
                        JSONObject ibuDetail = detailkes_ibu.getJSONObject(i);
                        DatabaseVariable.modelInputCatatanBuMil.setId_ibu(ibuDetail.getString("id_ibu"));
                        DatabaseVariable.modelInputCatatanBuMil.setTanggalKunjungan(ibuDetail.getString("tanggal_kunjungan"));
                        DatabaseVariable.modelInputCatatanBuMil.setHphtValue(ibuDetail.getString("tanggal_haid_terakhir"));
                        DatabaseVariable.modelInputCatatanBuMil.setHtpValue(ibuDetail.getString("tanggal_taksiran_kelahiran"));
                        DatabaseVariable.modelInputCatatanBuMil.setLingkarLenganValue(ibuDetail.getString("lingkar_lengan_atas"));
                        DatabaseVariable.modelInputCatatanBuMil.setTinggiBadanValue(ibuDetail.getString("tinggi_badan"));
                        DatabaseVariable.modelInputCatatanBuMil.setPenggunaanKontrasepsiValue(ibuDetail.getString("penggunaan_kontrasepsi"));
                        DatabaseVariable.modelInputCatatanBuMil.setRiwayatPenyakitValue(ibuDetail.getString("riwayat_penyakit"));
                        DatabaseVariable.modelInputCatatanBuMil.setRiwayatAlergiValue(ibuDetail.getString("riwayat_alergi"));
                        DatabaseVariable.modelInputCatatanBuMil.setHamilKeValue(ibuDetail.getString("hamil_ke"));
                        DatabaseVariable.modelInputCatatanBuMil.setJumlahPersalinanValue(ibuDetail.getString("jumlah_persalinan"));
                        DatabaseVariable.modelInputCatatanBuMil.setJumlahKeguguranValue(ibuDetail.getString("jumlah_keguguran"));
                        DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakHidupValue(ibuDetail.getString("jumlah_anak_hidup"));
                        DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakMatiValue(ibuDetail.getString("jumlah_anak_mati"));
                        DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakLahirKurangBulanValue(ibuDetail.getString("jumlah_anak_prematur"));
                        DatabaseVariable.modelInputCatatanBuMil.setJumlahKehamilaniniValue(ibuDetail.getString("jumlah_kehamilan_terakhir"));
                        DatabaseVariable.modelInputCatatanBuMil.setStatusImunisasiTTValue(ibuDetail.getString("status_imunisasi_tt"));
                        DatabaseVariable.modelInputCatatanBuMil.setImunisasiTTValue(ibuDetail.getString("imunisasi_tt_terakhir"));
                        DatabaseVariable.modelInputCatatanBuMil.setPenolongPersalinanTerakhirValue(ibuDetail.getString("penolong_persalinan_terakhir"));
                        DatabaseVariable.modelInputCatatanBuMil.setCaraPersalinanTerakhirValue(ibuDetail.getString("cara_persalinan_terakhir"));
                        DatabaseVariable.modelInputCatatanBuMil.setTanggalPemeriksaanTerkahir(ibuDetail.getString("tanggal_pemeriksaan_terakhir"));
                        DatabaseVariable.modelInputCatatanBuMil.setKeterangan(ibuDetail.getString("keterangan_terakhir"));
                        insertQuery.insertKesehatanIbuHamil();
                    }
                } else {
//                        Toast.makeText(context, "Tidak ada Data Ibu untuk id :" + id_kia, Toast.LENGTH_SHORT).show();
                }
                DatabaseVariable.modelInputCatatanBuMil.setId_ibu("");
                DatabaseVariable.modelInputCatatanBuMil.setTanggalKunjungan("");
                DatabaseVariable.modelInputCatatanBuMil.setHphtValue("");
                DatabaseVariable.modelInputCatatanBuMil.setHtpValue("");
                DatabaseVariable.modelInputCatatanBuMil.setLingkarLenganValue("");
                DatabaseVariable.modelInputCatatanBuMil.setTinggiBadanValue("");
                DatabaseVariable.modelInputCatatanBuMil.setPenggunaanKontrasepsiValue("");
                DatabaseVariable.modelInputCatatanBuMil.setRiwayatPenyakitValue("");
                DatabaseVariable.modelInputCatatanBuMil.setRiwayatAlergiValue("");
                DatabaseVariable.modelInputCatatanBuMil.setHamilKeValue("");
                DatabaseVariable.modelInputCatatanBuMil.setJumlahPersalinanValue("");
                DatabaseVariable.modelInputCatatanBuMil.setJumlahKeguguranValue("");
                DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakHidupValue("");
                DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakMatiValue("");
                DatabaseVariable.modelInputCatatanBuMil.setJumlahAnakLahirKurangBulanValue("");
                DatabaseVariable.modelInputCatatanBuMil.setJumlahKehamilaniniValue("");
                DatabaseVariable.modelInputCatatanBuMil.setStatusImunisasiTTValue("");
                DatabaseVariable.modelInputCatatanBuMil.setImunisasiTTValue("");
                DatabaseVariable.modelInputCatatanBuMil.setPenolongPersalinanTerakhirValue("");
                DatabaseVariable.modelInputCatatanBuMil.setCaraPersalinanTerakhirValue("");
                DatabaseVariable.modelInputCatatanBuMil.setTanggalPemeriksaanTerkahir("");
                DatabaseVariable.modelInputCatatanBuMil.setKeterangan("");

                DismissProgressDialog();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class GetCatatanKesehatanAnak extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.KES_ANAK);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_KES_ANAK = "detail_kes_anak";
            JSONArray detailkes_anak;
            String TAG_SUCCESS_KES_ANAK = "success_detail_kes_anak";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_KES_ANAK).equalsIgnoreCase("0")) {
                        detailkes_anak = jsonObject.getJSONArray(TAG_DETAIL_KES_ANAK);
                        for (int i = 0; i < detailkes_anak.length(); i++) {
                            JSONObject anakDetail = detailkes_anak.getJSONObject(i);
                            DatabaseVariable.modelInputKesehatanAnak.setId_anak(anakDetail.getString("id_anak"));
                            DatabaseVariable.modelInputKesehatanAnak.setId_vitamin(anakDetail.getString("id_vitamin"));
                            DatabaseVariable.modelInputKesehatanAnak.setWaktu_pemberian(anakDetail.getString("waktu_pemberian"));
                            DatabaseVariable.modelInputKesehatanAnak.setDosis(anakDetail.getString("dosis"));
                            DatabaseVariable.modelInputKesehatanAnak.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));
                            DatabaseVariable.modelInputKesehatanAnak.setPemberian_ke(anakDetail.getString("pemberian_ke"));
                            DatabaseVariable.modelInputKesehatanAnak.setKeterangan(anakDetail.getString("keterangan"));
                            insertQuery.insertKesehatanAnak();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetImunisasi0_12 extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_0_12);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_0_12 = "detail_imun_0_12";
            JSONArray detailimun_0_12;
            String TAG_SUCCESS_IMUN_0_12 = "success_detail_imun_0_12";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IMUN_0_12).equalsIgnoreCase("0")) {
                        detailimun_0_12 = jsonObject.getJSONArray(TAG_DETAIL_IMUN_0_12);
                        for (int i = 0; i < detailimun_0_12.length(); i++) {
                            JSONObject anakDetail = detailimun_0_12.getJSONObject(i);
                            DatabaseVariable.modelInputImunisasi0_12.setId_anak(anakDetail.getString("id_anak"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");

                            DatabaseVariable.modelInputImunisasi0_12.setCoordinate_x(params[0]);
                            DatabaseVariable.modelInputImunisasi0_12.setCoordinate_y(params[1]);
                            DatabaseVariable.modelInputImunisasi0_12.setBulan_ke(anakDetail.getString("bulan_ke"));
                            DatabaseVariable.modelInputImunisasi0_12.setTanggal_pemberian(anakDetail.getString("tanggal"));
                            DatabaseVariable.modelInputImunisasi0_12.setId_imunisasi(anakDetail.getString("id_imunisasi"));
                            insertQuery.insertImun012();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetImunisasi1TahunPlus extends AsyncTask<String, JSONObject, String> {
        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_DIATAS_1_TAHUN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            String TAG_DETAIL_IMUN_1_TAHUN_PLUS = "detail_imun_imun_satu_tahun_plus";
            JSONArray detailimun_1_tahun_plus;
            String TAG_SUCCESS_IMUN_1_TAHUN_PLUS = "success_detail_imun_satu_tahun_plus";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IMUN_1_TAHUN_PLUS).equalsIgnoreCase("0")) {
                        detailimun_1_tahun_plus = jsonObject.getJSONArray(TAG_DETAIL_IMUN_1_TAHUN_PLUS);
                        for (int i = 0; i < detailimun_1_tahun_plus.length(); i++) {
                            JSONObject anakDetail = detailimun_1_tahun_plus.getJSONObject(i);
                            DatabaseVariable.modelInputImunDiatas1Tahun.setId_anak(anakDetail.getString("id_anak"));

                            DatabaseVariable.modelInputImunDiatas1Tahun.setId_imunisasi(anakDetail.getString("id_jenis_imunisasi"));

                            DatabaseVariable.modelInputImunDiatas1Tahun.setBulan_ke(anakDetail.getString("bulan_ke"));

                            DatabaseVariable.modelInputImunDiatas1Tahun.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");
                            DatabaseVariable.modelInputImunDiatas1Tahun.setCoordinate_x(params[0]);
                            DatabaseVariable.modelInputImunDiatas1Tahun.setCoordinate_y(params[1]);

                            insertQuery.insertImun1TahunPlus();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetBIAS extends AsyncTask<String, JSONObject, String> {


        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.BIAS);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_BIAS = "detail_imun_bias";
            JSONArray detail_imun_bias;
            String TAG_SUCCESS_IMUN_BIAS = "success_imun_bias";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_IMUN_BIAS).equalsIgnoreCase("0")) {
                        detail_imun_bias = jsonObject.getJSONArray(TAG_DETAIL_IMUN_BIAS);
                        for (int i = 0; i < detail_imun_bias.length(); i++) {
                            JSONObject anakDetail = detail_imun_bias.getJSONObject(i);

                            DatabaseVariable.modelInputBIAS.setId_anak(anakDetail.getString("id_anak"));

                            DatabaseVariable.modelInputBIAS.setId_jenis_imunisasi(anakDetail.getString("id_jenis_imunisasi"));

                            DatabaseVariable.modelInputBIAS.setTingkatan(anakDetail.getString("tingkatan_kelas"));

                            DatabaseVariable.modelInputBIAS.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");
                            DatabaseVariable.modelInputBIAS.setCoordinate_x(params[0]);
                            DatabaseVariable.modelInputBIAS.setCoordinate_y(params[1]);

                            insertQuery.insertImunBIAS();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetImunisasiLain extends AsyncTask<String, JSONObject, String> {


        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_VAKSIN_LAIN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_LAIN = "detail_imun_lain";
            JSONArray detailimun_lain;
            String TAG_SUCCESS_IMUN_LAIN = "success_detail_imun_lain";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (s.contains("\"success_detail_imun_lain\":0,\"message\":\"imun_lain found\"")) {
                        detailimun_lain = jsonObject.getJSONArray(TAG_DETAIL_IMUN_LAIN);
                        for (int i = 0; i < detailimun_lain.length(); i++) {
                            JSONObject anakDetail = detailimun_lain.getJSONObject(i);
                            DatabaseVariable.modelInputVaksinLain.setId_anak(anakDetail.getString("id_anak"));
                            DatabaseVariable.modelInputVaksinLain.setNama_vaksin(anakDetail.getString("nama_vaksin"));

                            DatabaseVariable.modelInputVaksinLain.setCoordinate(anakDetail.getString("coordinate"));
                            DatabaseVariable.modelInputVaksinLain.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));
                            insertQuery.insertImunLain();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetImunisasiTambahan extends AsyncTask<String, JSONObject, String> {


        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.IMUN_TAMBAHAN);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_IMUN_TAMBAHAN = "detail_imun_tambahan";
            JSONArray detailimun_tambahan;
            String TAG_SUCCESS_IMUN_TAMBAHAN = "success_detail_imun_tambahan";

            super.onPostExecute(s);
            Log.d("s", s);
            if (!s.equals(null)) {
                try {
                    jsonObject = new JSONObject(s);

                    if (s.contains("\"success_detail_imun_tambahan\":0,\"message\":\"imun_tambahan found\"")) {
                        detailimun_tambahan = jsonObject.getJSONArray("detail_imun_tambahan");
                        for (int i = 0; i < detailimun_tambahan.length(); i++) {
                            JSONObject anakDetail = detailimun_tambahan.getJSONObject(i);
                            DatabaseVariable.modelInputImunTambahan.setId_anak(anakDetail.getString("id_anak"));
                            DatabaseVariable.modelInputImunTambahan.setNama_vaksin(anakDetail.getString("nama_vaksin"));

                            String coodinate = anakDetail.getString("coordinate");
                            String[] params = coodinate.split("\\.");

                            DatabaseVariable.modelInputImunTambahan.setcoordinate(coodinate);
                            DatabaseVariable.modelInputImunTambahan.setTanggal_pemberian(anakDetail.getString("tanggal_pemberian"));
                            insertQuery.insertImunTambahan();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetStatusGizi extends AsyncTask<String, JSONObject, String> {


        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.STAT_GIZI);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_STAT_GIZI = "detail_stat_gizi";
            JSONArray detailstat_gizi;
            String TAG_SUCCESS_STAT_GIZI = "success_detail_stat_gizi";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_STAT_GIZI).equalsIgnoreCase("0")) {
                        detailstat_gizi = jsonObject.getJSONArray(TAG_DETAIL_STAT_GIZI);
                        for (int i = 0; i < detailstat_gizi.length(); i++) {
                            JSONObject anakDetail = detailstat_gizi.getJSONObject(i);
                            DatabaseVariable.modelInputStatusGizi.setId_anak(anakDetail.getString("id_anak"));
                            DatabaseVariable.modelInputStatusGizi.setBulan_ke(anakDetail.getString("umur_bulan"));
                            DatabaseVariable.modelInputStatusGizi.setTanggal(anakDetail.getString("tanggal_penimbangan"));
                            DatabaseVariable.modelInputStatusGizi.setBerat_badan(anakDetail.getString("berat_badan"));
                            DatabaseVariable.modelInputStatusGizi.setNormal_tidak(anakDetail.getString("status_normal"));
                            DatabaseVariable.modelInputStatusGizi.setStatus_gizi(anakDetail.getString("status_gizi"));
                            DatabaseVariable.modelInputStatusGizi.setKeterangan(anakDetail.getString("keterangan"));
                            insertQuery.insertStatusGizi();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetKeteranganImunisasi extends AsyncTask<String, JSONObject, String> {
        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.KET_IMUNISASI);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_KET_IMUNISASI = "detail_ket_imunisasi";
            JSONArray detail_ket_imunisasi;
            String TAG_SUCCESS_KET_IMUNISASI = "success_detail_ket_imunisasi";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_KET_IMUNISASI).equalsIgnoreCase("0")) {
                        detail_ket_imunisasi = jsonObject.getJSONArray(TAG_DETAIL_KET_IMUNISASI);
                        for (int i = 0; i < detail_ket_imunisasi.length(); i++) {
                            JSONObject ketImun = detail_ket_imunisasi.getJSONObject(i);
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setId_anak(ketImun.getString("id_anak"));
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setTanggal(ketImun.getString("tanggal"));
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setHasil(ketImun.getString("hasil"));
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setKeterangan(ketImun.getString("keterangan"));
                            insertQuery.insertKetImun();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class GetKeteranganGizi extends AsyncTask<String, JSONObject, String> {
        @Override
        protected String doInBackground(String... strings) {
            params = new HashMap<>();
            params.put(ConfigureApps.TYPE, ConfigureApps.KET_GIZI);
            params.put(ConfigureApps.CODE, ConfigureApps.CODE_GET);
            params.put(ConfigureApps.ID_ANAK, strings[0]);

            result = jsonHander.makeHttpRequest(ConfigureApps.URL_LOCALHOST, ConfigureApps.METHOD, params);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            String TAG_DETAIL_KET_GIZI = "detail_stat_gizi";
            JSONArray detail_stat_gizi;
            String TAG_SUCCESS_KET_GIZI = "success_detail_stat_gizi";

            super.onPostExecute(s);
            if (!s.equals(null)) {
                try {
                    Log.i("s", s);
                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString(TAG_SUCCESS_KET_GIZI).equalsIgnoreCase("0")) {
                        detail_stat_gizi = jsonObject.getJSONArray(TAG_DETAIL_KET_GIZI);
                        for (int i = 0; i < detail_stat_gizi.length(); i++) {
                            JSONObject ketGizi = detail_stat_gizi.getJSONObject(i);
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setId_anak(ketGizi.getString("id_anak"));
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setTanggal(ketGizi.getString("tanggal_gizi"));
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setHasil(ketGizi.getString("hasil"));
                            DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setKeterangan(ketGizi.getString("keterangan"));
                            insertQuery.insertKetGizi();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
