package entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PhienLamBai")
public class PhienLamBai {
    @Id
    @Column(name = "MaPhien")
    private String maPhien;

    @Column(name = "ThoiGianBatDau")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "ThoiGianKetThuc")
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "Diem")
    private Double diem;

    @ManyToOne
    @JoinColumn(name = "MaHocSinh")
    private HocSinh hocSinh;

    @ManyToOne
    @JoinColumn(name = "MaBaiThi")
    private BaiThi baiThi;

    @OneToMany(mappedBy = "phienLamBai", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CauTraLoi> danhSachCauTraLoi;

    // Constructors
    public PhienLamBai() {}

    public PhienLamBai(String maPhien, LocalDateTime thoiGianBatDau, LocalDateTime thoiGianKetThuc, Double diem, HocSinh hocSinh, BaiThi baiThi, List<CauTraLoi> danhSachCauTraLoi) {
        this.maPhien = maPhien;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.diem = diem;
        this.hocSinh = hocSinh;
        this.baiThi = baiThi;
        this.danhSachCauTraLoi = danhSachCauTraLoi;
    }

    // Getters and Setters
    public String getMaPhien() {
        return maPhien;
    }

    public void setMaPhien(String maPhien) {
        this.maPhien = maPhien;
    }

    public LocalDateTime getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(LocalDateTime thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public LocalDateTime getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(LocalDateTime thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public Double getDiem() {
        return diem;
    }

    public void setDiem(Double diem) {
        this.diem = diem;
    }

    public HocSinh getHocSinh() {
        return hocSinh;
    }

    public void setHocSinh(HocSinh hocSinh) {
        this.hocSinh = hocSinh;
    }

    public BaiThi getBaiThi() {
        return baiThi;
    }

    public void setBaiThi(BaiThi baiThi) {
        this.baiThi = baiThi;
    }

    public List<CauTraLoi> getDanhSachCauTraLoi() {
        return danhSachCauTraLoi;
    }

    public void setDanhSachCauTraLoi(List<CauTraLoi> danhSachCauTraLoi) {
        this.danhSachCauTraLoi = danhSachCauTraLoi;
    }

    @Override
    public String toString() {
        return "PhienLamBai{" +
                "maPhien='" + maPhien + '\'' +
                ", thoiGianBatDau=" + thoiGianBatDau +
                ", thoiGianKetThuc=" + thoiGianKetThuc +
                ", diem=" + diem +
                ", hocSinh=" + (hocSinh != null ? hocSinh.getMaHocSinh() : null) +
                ", baiThi=" + (baiThi != null ? baiThi.getMaBaiThi() : null) +
                '}';
    }
}
