package entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Data
@Entity
@Table(name = "BaiThi")
public class BaiThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maBaiThi;

    @Column(nullable = false)
    private String tenBaiThi;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @Column(nullable = false)
    private int thoiLuong;

    private String matKhau;

    // Quan hệ Many-to-Many với CauHoi
    @ManyToMany
    @JoinTable(
            name = "BaiThi_CauHoi",
            joinColumns = @JoinColumn(name = "maBaiThi"),
            inverseJoinColumns = @JoinColumn(name = "maCauHoi")
    )
    private Set<CauHoi> danhSachCauHoi;

    @OneToMany(mappedBy="baiThi")
    private Set<PhienLamBai> danhSachPhienLamBaiCuaBaiThi;

    @ManyToOne
    @JoinColumn(name = "maGiaoVien")
    private GiaoVien giaoVien;
}
