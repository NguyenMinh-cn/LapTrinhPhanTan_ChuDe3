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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maHocSinh") // Tham chiếu tới khóa chính của bảng HocSinh
    private HocSinh hocSinh;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maBaiThi") // Tham chiếu tới khóa chính của bảng BaiThi
    private BaiThi baiThi;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @OneToMany(mappedBy="phienLamBai", cascade = CascadeType.ALL)//tên thuộc tính, không phải tên bảng
    private Set<CauTraLoi> danhSachCauTraLoi;

}
