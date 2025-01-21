package entities;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "PhienLamBai")
public class PhienLamBai {
    @Id
    @Column(name = "MaPhien")
    private String maPhien;

    @ManyToOne
    @JoinColumn(name = "maHocSinh") // Tham chiếu tới khóa chính của bảng HocSinh
    private HocSinh hocSinh;

    @ManyToOne
    @JoinColumn(name = "maBaiThi") // Tham chiếu tới khóa chính của bảng BaiThi
    private BaiThi baiThi;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @OneToMany(mappedBy="phienLamBai")//tên thuộc tính, không phải tên bảng
    private Set<CauTraLoi> danhSachCauTraLoi;
}
