package asia.sayateam.kiaadmin.Model;

/**
 * Created by Sigit Suryono on 10-Aug-17.
 */

public class TambahDataModelIbu {
    String id_kia, id_ibu, nama_ibu, tempat_lahir, tanggal_lahir, rt, rw,
            dusun, pekerjaan, no_bpjs, pendidikan, alamat;

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getId_kia() {
        return id_kia;
    }

    public void setId_kia(String id_kia) {
        this.id_kia = id_kia;
    }

    public String getId_ibu() {
        return id_ibu;
    }

    public void setId_ibu(String id_ibu) {
        this.id_ibu = id_ibu;
    }

    public String getNama_ibu() {
        return nama_ibu;
    }

    public void setNama_ibu(String nama_ibu) {
        this.nama_ibu = nama_ibu;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getNo_bpjs() {
        return no_bpjs;
    }

    public void setNo_bpjs(String no_bpjs) {
        this.no_bpjs = no_bpjs;
    }
}
