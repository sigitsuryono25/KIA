package asia.sayateam.kiaadmin.InputData;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import asia.sayateam.kiaadmin.Config.DateSplitter;
import asia.sayateam.kiaadmin.Config.PrefencesTambahData;
import asia.sayateam.kiaadmin.DatabaseHandle.DatabaseVariable;
import asia.sayateam.kiaadmin.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaadmin.R;
import asia.sigitsuryono.kialibrary.SecurePreferences;

/**
 * Created by Sigit Suryono on 010, 10-09-2017.
 */

public class FragmentKeteranganImunisasi {
    public static Time now = new Time();
    public static String NowDate;
    String id_anak;
    Context context;
    TextView hasilTerakhir;

    TextInputEditText tanggal, keterangan, oleh;

    public FragmentKeteranganImunisasi(final Context context, final String id_anak) {
        this.id_anak = id_anak;
        this.context = context;

        SecurePreferences securePreferences = new SecurePreferences(context, "asia.sayateam.kiaadmin",
                PrefencesTambahData.ENCRYPT_KEY, true);

        now.setToNow();
        NowDate = now.format("%Y-%m-%d");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_keterangan_imunisasi, null);
        builder.setView(view);

        tanggal = view.findViewById(R.id.tanggal);
        tanggal.setText(NowDate);

        hasilTerakhir = view.findViewById(R.id.keteranganTerakhir);

        SelectQuery selectQuery = new SelectQuery(context);
        Cursor c = selectQuery.getKetImun(Integer.parseInt(id_anak));
        while (c.moveToNext()) {
            hasilTerakhir.setText(
                    "Tanggal : " + new DateSplitter().DateSplitter(c.getString(1)) + "\n" +
                            "Hasil : " + c.getString(2) + "\n" +
                            c.getString(3)
            );
        }

        keterangan = view.findViewById(R.id.Hasil);
        oleh = view.findViewById(R.id.admin);
        oleh.setText("Petugas Input:" + securePreferences.getString("nama_admin") + "| ");


        builder.setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setId_anak(id_anak);
                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setTanggal(tanggal.getText().toString());
                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setHasil(keterangan.getText().toString());
                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.setKeterangan(oleh.getText().toString());

//                Toast.makeText(context,
//                        DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getId_anak() + ", " +
//                                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getTanggal() + ", " +
//                                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getHasil() + ", " +
//                                DatabaseVariable.modelGetKeteranganImunisasiDanGizi.getKeterangan()
//                        , Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void loadData() {

    }

}
