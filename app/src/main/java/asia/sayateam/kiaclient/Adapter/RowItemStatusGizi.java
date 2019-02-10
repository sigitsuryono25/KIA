package asia.sayateam.kiaclient.Adapter;

/**
 * Created by sigit on 29/07/17.
 */

public class RowItemStatusGizi {
    String bulanPenimbangan, bb, NormalOrNot, StatGizi, keterangan;
    String semester, bulan;

    public RowItemStatusGizi(String bulanPenimbangan, String bb, String normalOrNot, String statGizi, String keterangan, String semester, String bulan) {
        this.bulanPenimbangan = bulanPenimbangan;
        this.bb = bb;
        NormalOrNot = normalOrNot;
        StatGizi = statGizi;
        this.keterangan = keterangan;
        this.semester = semester;
        this.bulan = bulan;
    }

    public String getBulanPenimbangan() {
        return bulanPenimbangan;
    }

    public void setBulanPenimbangan(String bulanPenimbangan) {
        this.bulanPenimbangan = bulanPenimbangan;
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb;
    }

    public String getNormalOrNot() {
        return NormalOrNot;
    }

    public void setNormalOrNot(String normalOrNot) {
        NormalOrNot = normalOrNot;
    }

    public String getStatGizi() {
        return StatGizi;
    }

    public void setStatGizi(String statGizi) {
        StatGizi = statGizi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }
}

