package entities;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany
    @JoinTable(
            name = "BaiThi_Lop",
            joinColumns = @JoinColumn(name = "maBaiThi"),
            inverseJoinColumns = @JoinColumn(name = "maLop")
    )
    private List<Lop> danhSachLop = new ArrayList<>();

    @Column(nullable = true)
    private String matKhau;

    // Quan hệ Many-to-Many với CauHoi
//    @ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany
    @JoinTable(
            name = "BaiThi_CauHoi",
            joinColumns = @JoinColumn(name = "maBaiThi"),
            inverseJoinColumns = @JoinColumn(name = "maCauHoi")
    )
    private List<CauHoi> danhSachCauHoi = new ArrayList<>();

    @OneToMany(mappedBy="baiThi")
    private List<PhienLamBai> danhSachPhienLamBaiCuaBaiThi= new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "maGiaoVien")
    private GiaoVien giaoVien;
}

//@Entity
//public class BaiThi {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String tenBaiThi;
//    private LocalDateTime thoiGianBatDau;
//    private int thoiLuongPhut;
//
//    @ManyToOne
//    private MonHoc monHoc;
//
//    @ManyToOne
//    private GiaoVien giaoVienTao;
//
//    @ManyToMany
//    @JoinTable(
//            name = "bai_thi_cau_hoi",
//            joinColumns = @JoinColumn(name = "baiThi_id"),
//            inverseJoinColumns = @JoinColumn(name = "cauHoi_id")
//    )
//    private List<CauHoi> danhSachCauHoi;
//@ManyToMany
//@JoinTable(
//        name = "bai_thi_lop",
//        joinColumns = @JoinColumn(name = "bai_thi_id"),
//        inverseJoinColumns = @JoinColumn(name = "lop_id")
//)
//private List<Lop> danhSachLop;

//}