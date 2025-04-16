package entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
@Data
@Entity
@Table(name = "BaiThi")
@ToString
public class BaiThi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maBaiThi;

    @Column(nullable = false)
    private String tenBaiThi;

    @ManyToOne
    @JoinColumn(name = "maMon")
    private MonHoc monHoc;


    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    @Column(nullable = false)
    private int thoiLuong;

    @ManyToOne
    @JoinColumn(name = "maLop")
    private Lop lop;

    @Column(nullable = true)
    private String matKhau;

    // Quan hệ Many-to-Many với CauHoi
    @ManyToMany(cascade = CascadeType.ALL)
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
