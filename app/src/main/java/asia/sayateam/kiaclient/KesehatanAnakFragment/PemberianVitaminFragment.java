package asia.sayateam.kiaclient.KesehatanAnakFragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import asia.sayateam.kiaclient.Adapter.ExpandaleListAdapter;
import asia.sayateam.kiaclient.CatatanKesehatanAnakActivity;
import asia.sayateam.kiaclient.DatabaseHandle.SelectQuery;
import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 29/07/17.
 */

public class PemberianVitaminFragment extends Fragment {

    ExpandaleListAdapter vitaminAdapter;
    ExpandableListView pemberianVitaminExpandable;
    RelativeLayout pemberianVitaminParent;

    List<String> header;
    HashMap<String, List<String>> child;
    View view;
    SelectQuery selectQuery;
    Cursor cursor;
    int a = 1;
    String dummyDate = "0000-00-00";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_detail_pemberian_vitamin, container, false);
        pemberianVitaminExpandable = view.findViewById(R.id.expandable_detail_pemberian_vitamin);
        pemberianVitaminParent = view.findViewById(R.id.pemberianVitaminParent);
        selectQuery = new SelectQuery(getActivity());

        loadData();

        ((CatatanKesehatanAnakActivity) getActivity()).anakke.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (((CatatanKesehatanAnakActivity) getActivity()).anakke.getSelectedItem().toString().equalsIgnoreCase("--pilih anak ke--")) {
                    ((CatatanKesehatanAnakActivity) getActivity()).info_dasar.setVisibility(View.GONE);
                    pemberianVitaminParent.setVisibility(View.GONE);
                    ((CatatanKesehatanAnakActivity) getActivity()).noData.setVisibility(View.GONE);
                } else {
                    String[] nama = ((CatatanKesehatanAnakActivity) getActivity()).anakke.getSelectedItem().toString().split("--");
                    loadDataDetailPemberianVitamin(nama[0].replaceAll("\\s+", ""));
                    ((CatatanKesehatanAnakActivity) getActivity()).namaanak.setText(nama[1].replaceAll("\\s+", ""));
                    ((CatatanKesehatanAnakActivity) getActivity()).info_dasar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void loadData() {
        List<String> jenisVitaminContent = new ArrayList<>();
        jenisVitaminContent.add("Vitamin A");
        jenisVitaminContent.add("Vitamin D");
        ArrayAdapter arrayAdapter;

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, jenisVitaminContent);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ((CatatanKesehatanAnakActivity) getActivity()).jenisVitamin.setAdapter(arrayAdapter);

        List<String> anak = new ArrayList<>();
        anak.add("--pilih anak ke--");
        cursor = selectQuery.getDataFor_Info_Anak();
        while (cursor.moveToNext()) {
            anak.add(cursor.getString(0) + " -- " + cursor.getString(1));
        }

        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, anak);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ((CatatanKesehatanAnakActivity) getActivity()).anakke.setAdapter(arrayAdapter);

    }

    public void loadDataDetailPemberianVitamin(String anak) {
        String[] waktu = {
                "6-11 Bulan",
                "12-23 Bulan",
                "24-35 Bulan",
                "36-47 Bulan",
                "48-59 Bulan"
        };
        int size = 0;
        header = new ArrayList<>();
        child = new HashMap<>();
        List<String> childDataBulan6till11 = new ArrayList<>();
        List<String> childDataBulan12till23 = new ArrayList<>();
        List<String> childDataBulan24till35 = new ArrayList<>();
        List<String> childDataBulan36till47 = new ArrayList<>();
        List<String> childDataBulan48till59 = new ArrayList<>();


        cursor = selectQuery.getDetailAnakKe(anak);
        String id_anak = null;
        while (cursor.moveToNext()) {
            ((CatatanKesehatanAnakActivity) getActivity()).id_anak.setText("ID : " + cursor.getString(0));
            id_anak = cursor.getString(0);
        }
        cursor = selectQuery.getKesAnak(id_anak);
        if (cursor.getCount() > 0) {
            ((CatatanKesehatanAnakActivity) getActivity()).noData.setVisibility(View.GONE);
            pemberianVitaminParent.setVisibility(View.VISIBLE);

            a = 1;
            cursor = selectQuery.getKesAnakTanggal(waktu[0], id_anak, String.valueOf(a));
            while (cursor.moveToNext()) {
                if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                    childDataBulan6till11.add("Tidak ada data");
                    break;
                } else {
                    childDataBulan6till11.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                    childDataBulan6till11.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                    childDataBulan6till11.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                }
            }
            //fetch data for 12-23
            a = 1;
            while (a <= 2) {
                cursor = selectQuery.getKesAnakTanggal(waktu[1], id_anak, String.valueOf(a));

                while (cursor.moveToNext()) {
                    if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                        childDataBulan12till23.add("Tidak ada data");
                        break;
                    } else {
                        childDataBulan12till23.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                        childDataBulan12till23.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                        childDataBulan12till23.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                    }
                    Log.i("i", String.valueOf(a));
                }
                a++;
            }

            //fetch data for 24-35
            a = 1;
            while (a <= 2) {
                cursor = selectQuery.getKesAnakTanggal(waktu[2], id_anak, String.valueOf(a));
                while (cursor.moveToNext()) {
                    if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                        childDataBulan24till35.add("Tidak ada data");
                        break;
                    } else {
                        childDataBulan24till35.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                        childDataBulan24till35.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                        childDataBulan24till35.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                    }
                }
                a++;
            }
            //fetch data for 36-47
            a = 1;
            while (a <= 2) {
                cursor = selectQuery.getKesAnakTanggal(waktu[3], id_anak, String.valueOf(a));
                while (cursor.moveToNext()) {
                    if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                        childDataBulan36till47.add("Tidak ada data");
                        break;
                    } else {
                        childDataBulan36till47.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                        childDataBulan36till47.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                        childDataBulan36till47.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                    }
                }
                a++;
            }
            //fetch data for 48-59

            a = 1;
            while (a <= 2) {
                cursor = selectQuery.getKesAnakTanggal(waktu[4], id_anak, String.valueOf(a));
                while (cursor.moveToNext()) {
                    if (cursor.getString(0).equalsIgnoreCase("") || cursor.getString(0).equalsIgnoreCase(dummyDate)) {
                        childDataBulan48till59.add("Tidak ada data");
                        break;
                    } else {
                        childDataBulan48till59.add("Dosisi : <b>" + cursor.getString(1) + "</b>");
                        childDataBulan48till59.add("Tanggal Pemberian ke " + String.valueOf(a) + " : <b>" + dateSpliter(cursor.getString(0)) + "</b>");
                        childDataBulan48till59.add("Keterangan : <b>" + cursor.getString(2) + "</b>");
                    }
                }
                a++;
            }

            for (int i = 0; i < waktu.length; i++) {
                header.add(waktu[i]);
                if (i == 0) {
                    child.put(header.get(0), childDataBulan6till11);
                } else if (i == 1) {
                    child.put(header.get(1), childDataBulan12till23);
                } else if (i == 2) {
                    child.put(header.get(2), childDataBulan24till35);
                } else if (i == 3) {
                    child.put(header.get(3), childDataBulan36till47);
                } else if (i == 4) {
                    child.put(header.get(4), childDataBulan48till59);
                }
            }

            vitaminAdapter = new ExpandaleListAdapter(getActivity(), header, child);
            pemberianVitaminExpandable.setAdapter(vitaminAdapter);
            for (int i = 0; i < waktu.length; i++) {
                pemberianVitaminExpandable.expandGroup(i);
            }
        } else {
            ((CatatanKesehatanAnakActivity) getActivity()).noData.setVisibility(View.VISIBLE);
            pemberianVitaminParent.setVisibility(View.GONE);

        }
    }

    public String dateSpliter(String value) {
        String[] spliter = value.split("-");
        return spliter[2] + "-" + spliter[1] + "-" + spliter[0];
    }


}
