package asia.sayateam.kiaclient.Adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 29/07/17.
 */

public class CustomRecylerViewAdapter extends RecyclerView.Adapter<RowItem> {

    View view;
    LayoutInflater layoutInflater;
    List<String> primaryInfo;
    List<String> secondaryInfo;
    String pengenal;

    public CustomRecylerViewAdapter(AppCompatActivity activity, List<String> primaryInfo, List<String> secondaryInfo, String pengenal) {
        this.primaryInfo = primaryInfo;
        this.secondaryInfo = secondaryInfo;
        layoutInflater = LayoutInflater.from(activity);
        this.pengenal = pengenal;
    }

    @Override
    public RowItem onCreateViewHolder(ViewGroup parent, int viewType) {
        view = layoutInflater.inflate(R.layout.card_view_items, parent, false);
        RowItem holder = new RowItem(view, pengenal);
        return holder;
    }

    @Override
    public void onBindViewHolder(RowItem holder, int position) {
//        for(String item:primaryInfo){
//            holder.title.setText(item + " :");
//            System.out.println(item);
//        }
//        for (int i = 0; i < primaryInfo.size(); i++) {
        try {
            holder.title.setText(capitalizeFirstLetter(primaryInfo.get(position).replace("_", " ")));
            holder.subTitle.setText(capitalizeFirstLetter(secondaryInfo.get(position)));
        } catch (Exception e) {
            e.printStackTrace();
        }

//            Log.i("Colom Ke - " + i, primaryInfo.get(i));
//        }
    }

    @Override
    public int getItemCount() {
        return primaryInfo.size();
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
