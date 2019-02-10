package asia.sayateam.kiaadmin.Model;

/**
 * Created by Sigit Suryono on 17-Aug-17.
 */

public class ModelInputKesehatanAnak {
    String id_anak;
    String id_vitamin;
    String waktu_pemberian;
    String dosis;
    String tanggal_pemberian;
    String tanggal_pemberian_terakhir;

    public String getPemberian_ke() {
        return pemberian_ke;
    }

    public void setPemberian_ke(String pemberian_ke) {
        this.pemberian_ke = pemberian_ke;
    }

    String pemberian_ke;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    String keterangan;


    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getId_vitamin() {
        return id_vitamin;
    }

    public void setId_vitamin(String id_vitamin) {
        this.id_vitamin = id_vitamin;
    }

    public String getWaktu_pemberian() {
        return waktu_pemberian;
    }

    public void setWaktu_pemberian(String waktu_pemberian) {
        this.waktu_pemberian = waktu_pemberian;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getTanggal_pemberian() {
        return tanggal_pemberian;
    }

    public void setTanggal_pemberian(String tanggal_pemberian) {
        this.tanggal_pemberian = tanggal_pemberian;
    }

    public String getTanggal_pemberian_terakhir() {
        return tanggal_pemberian_terakhir;
    }

    public void setTanggal_pemberian_terakhir(String tanggal_pemberian_terakhir) {
        this.tanggal_pemberian_terakhir = tanggal_pemberian_terakhir;
    }
}
