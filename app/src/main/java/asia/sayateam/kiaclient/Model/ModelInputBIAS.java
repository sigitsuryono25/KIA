package asia.sayateam.kiaclient.Model;

/**
 * Created by Sigit Suryono on 13-Aug-17.
 */

public class ModelInputBIAS {

    String id_anak, tingkatan, tanggal_pemberian;
    String coordinate_x, coordinate_y;
    String id_jenis_imunisasi;

    public String getId_jenis_imunisasi() {
        return id_jenis_imunisasi;
    }

    public void setId_jenis_imunisasi(String id_jenis_imunisasi) {
        this.id_jenis_imunisasi = id_jenis_imunisasi;
    }

    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getTingkatan() {
        return tingkatan;
    }

    public void setTingkatan(String tingkatan) {
        this.tingkatan = tingkatan;
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
