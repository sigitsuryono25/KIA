package asia.sayateam.kiaclient.library;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by sigit on 29/07/17.
 */

public class TitleToobarCustom {


    public Spannable TitleToobarCustom(String title, int color) {


        Spannable titleCustom = new SpannableString(title);
        titleCustom.setSpan(new ForegroundColorSpan(color), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return titleCustom;
    }

    public Spannable TitleToobarCustom(String title, String color) {


        Spannable titleCustom = new SpannableString(title);
        titleCustom.setSpan(new ForegroundColorSpan(Color.parseColor(color)), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return titleCustom;
    }
}
