package asia.sigitsuryono.kialibrary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.ow2.util.base64.Base64;

public class BitmapUtils {

    // convert from bitmap to byte array
    public static char[] encodeImage(byte[] imageByteArray) {
        return Base64.encode(imageByteArray);
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}