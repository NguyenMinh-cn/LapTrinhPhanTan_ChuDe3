//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

@Entity
@Table(
        name = "monhoc"
)
public class MonHoc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(
            name = "MaMon"
    )
    private int maMon;
    @Column(
            columnDefinition = "varchar(100)",
            nullable = false
    )
    private String tenMon;
    @OneToMany(
            mappedBy = "monHoc",
            cascade = {CascadeType.ALL}
    )
    private List<BaiThi> danhSachBaiThiTheoMon = new ArrayList();
    @OneToMany(
            mappedBy = "monHoc",
            cascade = {CascadeType.ALL}
    )
    private List<ChuDe> danhSachChuDe = new ArrayList();

    public String toString() {
        int var10000 = this.maMon;
        return "MonHoc{maMon=" + var10000 + ", tenMon='" + this.tenMon + "', soChuDe=" + (this.danhSachChuDe != null ? this.danhSachChuDe.size() : 0) + ", soBaiThi=" + (this.danhSachBaiThiTheoMon != null ? this.danhSachBaiThiTheoMon.size() : 0) + "}";
    }

    @Generated
    public int getMaMon() {
        return this.maMon;
    }

    @Generated
    public String getTenMon() {
        return this.tenMon;
    }

    @Generated
    public List<BaiThi> getDanhSachBaiThiTheoMon() {
        return this.danhSachBaiThiTheoMon;
    }

    @Generated
    public List<ChuDe> getDanhSachChuDe() {
        return this.danhSachChuDe;
    }

    @Generated
    public void setMaMon(int maMon) {
        this.maMon = maMon;
    }

    @Generated
    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    @Generated
    public void setDanhSachBaiThiTheoMon(List<BaiThi> danhSachBaiThiTheoMon) {
        this.danhSachBaiThiTheoMon = danhSachBaiThiTheoMon;
    }

    @Generated
    public void setDanhSachChuDe(List<ChuDe> danhSachChuDe) {
        this.danhSachChuDe = danhSachChuDe;
    }

    @Generated
    public MonHoc() {
    }

    @Generated
    public MonHoc(int maMon, String tenMon, List<BaiThi> danhSachBaiThiTheoMon, List<ChuDe> danhSachChuDe) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.danhSachBaiThiTheoMon = danhSachBaiThiTheoMon;
        this.danhSachChuDe = danhSachChuDe;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MonHoc)) {
            return false;
        } else {
            MonHoc other = (MonHoc)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                return this.getMaMon() == other.getMaMon();
            }
        }
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof MonHoc;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getMaMon();
        return result;
    }
}