package asia.sayateam.kiaadmin.QrCodeGenerator;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by sigit on 01/08/17.
 */

public class TextToImageEncoder {

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    public final static int QRWidth = 500;

    public Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.QR_CODE, QRWidth, QRWidth, null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int i = 0; i < bitMatrixHeight; i++) {
            int offset = i * bitMatrixWidth;

            for (int j = 0; j < bitMatrixWidth; j++) {
                pixels[offset + j] = bitMatrix.get(i, j) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
