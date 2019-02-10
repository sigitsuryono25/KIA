package asia.sayateam.kiaclient.InformationFragement;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.InformasiActivity;
import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 25/07/17.
 */

public class AnakFragment extends Fragment {

    ImageButton icon;
    SelectQuery selectQuery;
    Cursor cursor;
    TextView ttl, alamat, nama_ibu, no_bpjs;
    NestedScrollView containerInformasiAnak;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_anak_fragment, container, false);


        ((InformasiActivity) getActivity()).containerPilihAnak.setVisibility(View.VISIBLE);
        ((InformasiActivity) getActivity()).nama.setVisibility(View.GONE);
        ((InformasiActivity) getActivity()).id.setVisibility(View.GONE);
        ((InformasiActivity) getActivity()).icon.setVisibility(View.GONE);

        ((InformasiActivity) getActivity()).icon.setImageResource(R.mipmap.ic_boy);
//        new BackgroundSettings().BackgroundSettings(getActivity(), ((InformasiActivity) getActivity()).icon, BackgroundSettings.PROFIL);
        selectQuery = new SelectQuery(getActivity());

        ttl = view.findViewById(R.id.tempatdantanggalLahir);
        alamat = view.findViewById(R.id.alamat_lengkap);
        nama_ibu = view.findViewById(R.id.namaIbu);
        no_bpjs = view.findViewById(R.id.no_bpjs_anak);
        containerInformasiAnak = view.findViewById(R.id.containerInfoAnak);


        loadDataSpinner();

        ((InformasiActivity) getActivity()).anakke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((InformasiActivity) getActivity()).anakke.getSelectedItem().toString().equalsIgnoreCase("--Pilih Anak--")) {
                    containerInformasiAnak.setVisibility(View.GONE);
                    ((InformasiActivity) getActivity()).nama.setVisibility(View.GONE);
                    ((InformasiActivity) getActivity()).id.setVisibility(View.GONE);
                    ((InformasiActivity) getActivity()).icon.setVisibility(View.GONE);
                } else {
                    ((InformasiActivity) getActivity()).namaAnak = ((InformasiActivity) getActivity()).anakke.getSelectedItem().toString();
                    loadData(((InformasiActivity) getActivity()).anakke.getSelectedItem().toString());
                    containerInformasiAnak.setVisibility(View.VISIBLE);
                    ((InformasiActivity) getActivity()).nama.setVisibility(View.VISIBLE);
                    ((InformasiActivity) getActivity()).id.setVisibility(View.VISIBLE);
                    ((InformasiActivity) getActivity()).icon.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void loadDataSpinner() {
        List<String> strings = new ArrayList<>();
        strings.add("--Pilih Anak--");
        cursor = selectQuery.getDataFor_Info_Anak();
        while (cursor.moveToNext()) {
            strings.add(cursor.getString(1));
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ((InformasiActivity) getActivity()).anakke.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    public void loadData(String anakke) {
        String id_ibu = null;
        cursor = selectQuery.getDetailAnakKeByName(anakke);

        if (cursor.moveToFirst()) {
            do {
                for (int i = 0; i <= 11; i++) {
                    ((InformasiActivity) getActivity()).id.setText(cursor.getString(0));
                    ((InformasiActivity) getActivity()).nama.setText(cursor.getString(1));

                    String tgllahir = cursor.getString(6);
                    String[] spliter = tgllahir.split("-");

                    ttl.setText("Tempat dan Tanggal Lahir : \n\n" + cursor.getString(5) + ", " + spliter[2] + "-" + spliter[1]
                            + "-" + spliter[0]);
//
                    alamat.setText("Alamat : \n\n" + cursor.getString(2) + " RT " + cursor.getString(3) + " RW "
                            + cursor.getString(4) + " Dusun " + cursor.getString(8));
//
                    no_bpjs.setText("No BPJS : \n\n" + cursor.getString(7));
                    id_ibu = cursor.getString(10);
                }
            } while (cursor.moveToNext());
        }

        loadIbu(id_ibu);
    }

    public void loadIbu(String id_ibu) {
        String nama_Ibu = "TIdak ada";
        cursor = selectQuery.getNamaIbu(id_ibu);
        while (cursor.moveToNext()) {
            nama_Ibu = cursor.getString(0);
        }
        nama_ibu.setText("Nama Ibu : \n\n" + nama_Ibu);
    }
}
