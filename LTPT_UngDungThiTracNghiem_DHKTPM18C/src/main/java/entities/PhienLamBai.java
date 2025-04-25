package entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PhienLamBai")
public class PhienLamBai implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MaPhien", nullable = false, unique = true)
    private String maPhien;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    private double diem;

    @ManyToOne
    @JoinColumn(name = "maHocSinh") // Tham chiếu tới khóa chính của bảng HocSinh
    private HocSinh hocSinh;

    @ManyToOne
    @JoinColumn(name = "maBaiThi") // Tham chiếu tới khóa chính của bảng BaiThi
    private BaiThi baiThi;

    @OneToMany(mappedBy="phienLamBai", cascade = CascadeType.ALL)//tên thuộc tính, không phải tên bảng
    private List<CauTraLoi> danhSachCauTraLoi = new ArrayList<>();

    @Override
    public String toString() {
        return "PhienLamBai{" +
                "maPhien='" + maPhien + '\'' +
                ", thoiGianBatDau=" + thoiGianBatDau +
                ", thoiGianKetThuc=" + thoiGianKetThuc +
                ", diem=" + diem +
                ", hocSinhId=" + (hocSinh != null ? hocSinh.getMaHocSinh() : "null") +
                ", baiThiId=" + (baiThi != null ? baiThi.getMaBaiThi() : "null") +
                ", soCauTraLoi=" + (danhSachCauTraLoi != null ? danhSachCauTraLoi.size() : 0) +
                '}';
    }
}
