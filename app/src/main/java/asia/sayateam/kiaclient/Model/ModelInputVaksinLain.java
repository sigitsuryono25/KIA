package asia.sayateam.kiaclient.Model;

/**
 * Created by Sigit Suryono on 13-Aug-17.
 */

public class ModelInputVaksinLain {
    String id_anak, nama_vaksin, tanggal_pemberian, coordinate;

    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getNama_vaksin() {
        return nama_vaksin;
    }

    public void setNama_vaksin(String nama_vaksin) {
        this.nama_vaksin = nama_vaksin;
    }

    public String getTanggal_pemberian() {
        return tanggal_pemberian;
    }

    public void setTanggal_pemberian(String tanggal_pemberian) {
        this.tanggal_pemberian = tanggal_pemberian;
    }

    public String getcoordinate() {
        return coordinate;
    }

    public void setcoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

}