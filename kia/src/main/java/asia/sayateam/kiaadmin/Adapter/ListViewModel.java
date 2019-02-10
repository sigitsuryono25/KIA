package asia.sayateam.kiaadmin.Adapter;

import android.util.Log;

/**
 * Created by Sigit Suryono on 06-Aug-17.
 */

public class ListViewModel {

    String header, child;
    int sequence;

    public ListViewModel(String header, String child, int sequence) {
        this.header = header;
        this.child = child;
        this.sequence = sequence;

        Log.i("HEADER", header);

    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
}
