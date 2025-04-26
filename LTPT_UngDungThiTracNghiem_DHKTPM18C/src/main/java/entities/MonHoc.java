package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

@Entity
@Table(name = "monhoc")
public class MonHoc implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MaMon")
    private int maMon;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String tenMon;

    @OneToMany(mappedBy = "monHoc", cascade = {CascadeType.ALL})
    private List<BaiThi> danhSachBaiThiTheoMon = new ArrayList<>();

    @OneToMany(mappedBy = "monHoc", cascade = {CascadeType.ALL})
    private List<ChuDe> danhSachChuDe = new ArrayList<>();

    public MonHoc() {
    }

    public MonHoc(int maMon, String tenMon, List<BaiThi> danhSachBaiThiTheoMon, List<ChuDe> danhSachChuDe) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.danhSachBaiThiTheoMon = danhSachBaiThiTheoMon;
        this.danhSachChuDe = danhSachChuDe;
    }

    public int getMaMon() {
        return maMon;
    }

    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public List<BaiThi> getDanhSachBaiThiTheoMon() {
        return danhSachBaiThiTheoMon;
    }

    public void setDanhSachBaiThiTheoMon(List<BaiThi> danhSachBaiThiTheoMon) {
        this.danhSachBaiThiTheoMon = danhSachBaiThiTheoMon;
    }

    public List<ChuDe> getDanhSachChuDe() {
        return danhSachChuDe;
    }

    public void setDanhSachChuDe(List<ChuDe> danhSachChuDe) {
        this.danhSachChuDe = danhSachChuDe;
    }

    @Override
    public String toString() {
        return "MonHoc{maMon=" + maMon + ", tenMon='" + tenMon + "', soChuDe=" + (danhSachChuDe != null ? danhSachChuDe.size() : 0) + ", soBaiThi=" + (danhSachBaiThiTheoMon != null ? danhSachBaiThiTheoMon.size() : 0) + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonHoc)) return false;
        MonHoc other = (MonHoc) o;
        return maMon == other.maMon;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(maMon);
    }

    protected boolean canEqual(Object other) {
        return other instanceof MonHoc;
    }
}
