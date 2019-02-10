package asia.sayateam.kiaclient.InformationFragement;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.InformasiActivity;
import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 25/07/17.
 */

public class ProfileFragment extends Fragment {

    ImageButton icon;
    SelectQuery selectQuery;
    Cursor cursor;
    TextView ttl, alamat, pekerjaan, noBPJS, pendidikan, jumlahAnak;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile_fragment, container, false);

        ((InformasiActivity) getActivity()).nama.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).id.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).icon.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).containerPilihAnak.setVisibility(View.GONE);

        selectQuery = new SelectQuery(getActivity());

        ((InformasiActivity) getActivity()).icon.setImageResource(R.mipmap.ic_profile);
//        new BackgroundSettings().BackgroundSettings(getActivity(), ((InformasiActivity) getActivity()).icon, BackgroundSettings.PROFIL);

        ttl = view.findViewById(R.id.tempatdantanggalLahir);
        alamat = view.findViewById(R.id.alamat_lengkap);
        pekerjaan = view.findViewById(R.id.pekerjaan);
        noBPJS = view.findViewById(R.id.noBPJS);
        pendidikan = view.findViewById(R.id.pendidikan);
        jumlahAnak = view.findViewById(R.id.jumlahANak);

        loadData();

        return view;
    }

    public void loadData() {
        cursor = selectQuery.getDataFor_Info_Pemilik();

        while (cursor.moveToNext()) {
            ((InformasiActivity) getActivity()).id.setText(cursor.getString(0));
            ((InformasiActivity) getActivity()).nama.setText(cursor.getString(1));

            String tgllahir = cursor.getString(3);
            String[] spliter = tgllahir.split("-");

            ttl.setText("Tempat dan Tanggal Lahir : \n\n" + cursor.getString(2) + ", " + spliter[2] + "-" + spliter[1]
                    + "-" + spliter[0]);

            alamat.setText("Alamat : \n\n" + cursor.getString(4) + " RT " + cursor.getString(5) + " RW "
                    + cursor.getString(6) + " Dusun " + cursor.getString(7));

            pekerjaan.setText("Pekerjaan : \n\n" + cursor.getString(8));
            noBPJS.setText("Nomor BPJS : \n\n" + cursor.getString(9));
            pendidikan.setText("Pendidikan : \n\n" + cursor.getString(10));
            jumlahAnak.setText("Jumlah Anak : \n\n" + cursor.getString(12));
        }
    }
}
