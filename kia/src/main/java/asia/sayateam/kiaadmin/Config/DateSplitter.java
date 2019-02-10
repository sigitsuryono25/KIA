package asia.sayateam.kiaadmin.Config;

import android.app.Application;

/**
 * Created by Sigit Suryono on 008, 08-09-2017.
 */

public class DateSplitter{

    public String DateSplitter(String date) {
        String[] spliter = date.split("-");

        return spliter[2] + "-" + spliter[1] + "-" + spliter[0];
    }
}