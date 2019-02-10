package asia.sayateam.kiaclient.config;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.widget.ImageButton;

import asia.sayateam.kiaclient.R;

/**
 * Created by sigit on 31/07/17.
 */

public class BackgroundSettings {

    public static final String BUMIL = "kesehatan_ibu_hamil";
    public static final String PROFIL = "peserta";
    public static final String KESEHATAN_ANAK = "kesehatan_anak";
    public static final String STATUS_GIZI = "status_gizi";
    public static final String STATUS_IMUN= "status_imunisasi";
    public static final String LAIN_LAIN= "lain_lain";


    GradientDrawable gradientDrawable;

    public void BackgroundSettings(Context context, ImageButton imageButton, String getTag) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageButton.setBackgroundResource(R.drawable.profile_circular_v23);
            imageButton.setPadding(25, 25, 25, 25);
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            switch (getTag){
                case PROFIL:
                    imageButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.profile_circular_v13));
                    gradientDrawable = (GradientDrawable) imageButton.getBackground().getCurrent();
                    gradientDrawable.setColor(context.getResources().getColor(R.color.circleProfil));
                    gradientDrawable.setStroke(3, context.getResources().getColor(R.color.circleProfil));
                    imageButton.setPadding(25, 25, 25, 25);
                    break;
                case BUMIL:
                    imageButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.profile_circular_v13));
                    gradientDrawable = (GradientDrawable) imageButton.getBackground().getCurrent();
                    gradientDrawable.setColor(context.getResources().getColor(R.color.circleBumil));
                    gradientDrawable.setStroke(3, context.getResources().getColor(R.color.circleBumil));
                    imageButton.setPadding(25, 25, 25, 25);
                    break;
                case KESEHATAN_ANAK:
                    imageButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.profile_circular_v13));
                    gradientDrawable = (GradientDrawable) imageButton.getBackground().getCurrent();
                    gradientDrawable.setColor(context.getResources().getColor(R.color.circleKesAnak));
                    gradientDrawable.setStroke(3, context.getResources().getColor(R.color.circleKesAnak));
                    imageButton.setPadding(25, 25, 25, 25);
                    break;
                case STATUS_GIZI:
                    imageButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.profile_circular_v13));
                    gradientDrawable = (GradientDrawable) imageButton.getBackground().getCurrent();
                    gradientDrawable.setColor(context.getResources().getColor(R.color.circleStatus_gizi));
                    gradientDrawable.setStroke(3, context.getResources().getColor(R.color.circleStatus_gizi));
                    imageButton.setPadding(25, 25, 25, 25);
                    break;
                case STATUS_IMUN:
                    imageButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.profile_circular_v13));
                    gradientDrawable = (GradientDrawable) imageButton.getBackground().getCurrent();
                    gradientDrawable.setColor(context.getResources().getColor(R.color.circleStatus_imunisasi));
                    gradientDrawable.setStroke(3, context.getResources().getColor(R.color.circleStatus_imunisasi));
                    imageButton.setPadding(25, 25, 25, 25);
                    break;
                case LAIN_LAIN:
                    imageButton.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.profile_circular_v13));
                    gradientDrawable = (GradientDrawable) imageButton.getBackground().getCurrent();
                    gradientDrawable.setColor(context.getResources().getColor(R.color.circleLain_lain));
                    gradientDrawable.setStroke(3, context.getResources().getColor(R.color.circleLain_lain));
                    imageButton.setPadding(25, 25, 25, 25);
                    break;

            }

        }
    }
}
