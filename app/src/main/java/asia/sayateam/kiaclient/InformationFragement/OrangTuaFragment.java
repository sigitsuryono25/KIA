package asia.sayateam.kiaclient.InformationFragement;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.InformasiActivity;
import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 25/07/17.
 */

public class OrangTuaFragment extends Fragment {

    SelectQuery selectQuery;
    Cursor cursor;
    TextView
            id, nama, ttl, alamat, pekerjaan, nobpjs, pendidikan;
    AppBarLayout image_name_container;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_orang_tua_fragment_ayah, container, false);

        ((InformasiActivity) getActivity()).nama.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).id.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).icon.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).containerPilihAnak.setVisibility(View.GONE);

        ((InformasiActivity) getActivity()).icon.setImageResource(R.mipmap.ic_woman);
//        new BackgroundSettings().BackgroundSettings(getActivity(), ((InformasiActivity) getActivity()).icon, BackgroundSettings.PROFIL);

        selectQuery = new SelectQuery(getActivity());

        ttl = view.findViewById(R.id.tempatdantanggalLahir);
        alamat = view.findViewById(R.id.alamat_lengkap);
        pekerjaan = view.findViewById(R.id.pekerjaan);
        nobpjs = view.findViewById(R.id.noBPJS);
        pendidikan = view.findViewById(R.id.pendidikan);

        loadData();

        return view;
    }

    public void loadData() {
        cursor = selectQuery.getDataFor_Info_Ibu();

        while (cursor.moveToNext()) {
            ((InformasiActivity) getActivity()).nama.setText(cursor.getString(0));
            ((InformasiActivity) getActivity()).id.setText(cursor.getString(11));

            String tgllahir = cursor.getString(2);
            String[] spliter = tgllahir.split("-");

            ttl.setText("Tempat dan Tanggal Lahir : \n\n" + cursor.getString(1) + ", " + spliter[2] + "-" + spliter[1]
                    + "-" + spliter[0]);

            alamat.setText("Alamat : \n\n" + cursor.getString(3) + " RT " + cursor.getString(4) + " RW "
                    + cursor.getString(5) + " Dusun " + cursor.getString(6));

            pekerjaan.setText("Pekerjaan : \n\n" + cursor.getString(7));
            nobpjs.setText("Nomor BPJS : \n\n" + cursor.getString(8));
            pendidikan.setText("Pendidikan : \n\n" + cursor.getString(9));
        }
    }
}
