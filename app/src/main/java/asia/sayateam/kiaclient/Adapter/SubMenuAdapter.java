package asia.sayateam.kiaclient.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import asia.sayateam.kiaclient.R;

/**
 * Created by Sigit Suryono on 028, 28-Aug-17.
 */

public class SubMenuAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public SubMenuAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item(" ", R.mipmap.nol_12));
        mItems.add(new Item(" ", R.mipmap.satu_plus));
        mItems.add(new Item(" ", R.mipmap.bi_as));
        mItems.add(new Item(" ", R.mipmap.imun_plus));
        mItems.add(new Item(" ", R.mipmap.vak_lain));
        mItems.add(new Item(" ", R.mipmap.back));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;

        if (v == null) {
            v = mInflater.inflate(R.layout.sub_menu_imun_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
        }

        picture = (ImageView) v.getTag(R.id.picture);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}