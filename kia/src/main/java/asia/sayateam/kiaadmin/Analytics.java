package asia.sayateam.kiaadmin;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Sigit Suryono on 004, 04-Sep-17.
 */

public class Analytics {

    private FirebaseAnalytics mFirebaseAnalytics;

    public Analytics(Context context, String itemId, String itemName, String contentType) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
