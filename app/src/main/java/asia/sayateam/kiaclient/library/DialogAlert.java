package asia.sayateam.kiaclient.library;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Sigit Suryono on 23-Aug-17.
 */

public class DialogAlert {

    public DialogAlert(final Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle("Kesalahan")
                .setMessage("Tidak ada koneksi internet.\n" +
                        "Silahkan aktifkan data seluler anda " +
                        "atau hubungkan ke wifi")
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        activity.
                                finish();
                    }
                }).create().show();
    }
}
