package asia.sayateam.kiaclient.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 29/07/17.
 */

public class RowItem extends RecyclerView.ViewHolder {

    TextView title, subTitle;
    EditText bulanPenimbangan, bb, nOrt, statGizi, ket;
    public static final String STAT_GIZI = "stat_gizi";
    public static final String KES_BUMIL = "kes_bumil";

    public RowItem(View itemView, String pengenal) {
        super(itemView);

        if (pengenal.equalsIgnoreCase(KES_BUMIL)) {
            title = itemView.findViewById(R.id.titleItem);
            subTitle = itemView.findViewById(R.id.subTitle);
        } else if (pengenal.equalsIgnoreCase(STAT_GIZI)) {
            title = itemView.findViewById(R.id.semesterdanbulan);
            bulanPenimbangan = itemView.findViewById(R.id.tgl_pemberian);
            bb = itemView.findViewById(R.id.berat_badan_status_gizi);
            nOrt = itemView.findViewById(R.id.pilihNormalAtauTidak);
            statGizi = itemView.findViewById(R.id.pilihStatusGizi);
            ket = itemView.findViewById(R.id.keteranganPerSemester);
        }
    }
}
