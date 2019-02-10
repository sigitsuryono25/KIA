package asia.sayateam.kiaclient.Model;

import android.util.Log;

/**
 * Created by Sigit Suryono on 13-Aug-17.
 */

public class ModelInputImunisasi0_12 {
    String id_anak, bulan_ke, tanggal_pemberian;
    String coordinate_x, coordinate_y;

    public String getId_imunisasi() {
        return id_imunisasi;
    }

    public void setId_imunisasi(String id_imunisasi) {
        this.id_imunisasi = id_imunisasi;
    }

    String id_imunisasi;

    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
        Log.i("id_anak", this.id_anak);
    }

    public String getBulan_ke() {
        return bulan_ke;
    }

    public void setBulan_ke(String bulan_ke) {
        this.bulan_ke = bulan_ke;
    }

    public String getTanggal_pemberian() {
        return tanggal_pemberian;
    }

    public void setTanggal_pemberian(String tanggal_pemberian) {
        this.tanggal_pemberian = tanggal_pemberian;
    }

    public String getCoordinate_x() {
        return coordinate_x;
    }

    public void setCoordinate_x(String coordinate_x) {
        this.coordinate_x = coordinate_x;
    }

    public String getCoordinate_y() {
        return coordinate_y;
    }

    public void setCoordinate_y(String coordinate_y) {
        this.coordinate_y = coordinate_y;
    }
}
