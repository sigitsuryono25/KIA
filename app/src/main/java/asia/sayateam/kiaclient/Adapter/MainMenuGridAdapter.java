package asia.sayateam.kiaclient.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 29/07/17.
 */

public class MainMenuGridAdapter extends BaseAdapter {

    Context context;
    String[] menu_name;
    String[] color;
    int[] icon_menu;

    public MainMenuGridAdapter(Context context, String[] menu_name, int[] icon_menu, String[] color) {
        this.context = context;
        this.menu_name = menu_name;
        this.color = color;
        this.icon_menu = icon_menu;

    }

    @Override
    public int getCount() {
        return menu_name.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.main_menu_grid_item, null);
            gridView.setBackgroundColor(Color.parseColor(color[i]));
            TextView menuname = (TextView) gridView.findViewById(R.id.menuItemName);
            ImageView iconmenu = (ImageView) gridView.findViewById(R.id.iconMenuItem);

            menuname.setText(menu_name[i]);

            Log.d("Menu", menu_name[i] + " " + String.valueOf(i) + " " + String.valueOf(menu_name.length));

            menuname.setGravity(View.TEXT_ALIGNMENT_CENTER);
            iconmenu.setImageResource(icon_menu[i]);
        } else {
            gridView = view;
        }
        return gridView;
    }
}
