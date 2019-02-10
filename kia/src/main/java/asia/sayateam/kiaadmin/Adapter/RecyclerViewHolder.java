package asia.sayateam.kiaadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import asia.sayateam.kiaadmin.R;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public static final String VIEW_TYPE_MENU = "menu";
    public static final String VIEW_TYPE_SUB_MENU = "sub_menu";


    ImageView iconListItem;
    TextView titleList;

    public  RecyclerViewHolder(View itemView, String type) {
        super(itemView);

        if (type.equalsIgnoreCase(VIEW_TYPE_SUB_MENU)) {

            titleList = (TextView) itemView.findViewById(R.id.titleItem);

        } else if (type.equalsIgnoreCase(VIEW_TYPE_MENU)) {

            iconListItem = (ImageView) itemView.findViewById(R.id.iconItem);

            titleList = (TextView) itemView.findViewById(R.id.titleItem);
        }
    }
}
