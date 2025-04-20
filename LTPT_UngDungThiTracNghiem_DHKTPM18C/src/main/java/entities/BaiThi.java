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

    @Override
    public String toString() {
        return "BaiThi{" +
                "maBaiThi=" + maBaiThi +
                ", tenBaiThi='" + tenBaiThi + '\'' +
                ", monHoc=" + monHoc +
                ", thoiGianBatDau=" + thoiGianBatDau +
                ", thoiGianKetThuc=" + thoiGianKetThuc +
                ", thoiLuong=" + thoiLuong +
                ", danhSachLop=" + danhSachLop +
                ", matKhau='" + matKhau + '\'' +
                ", danhSachCauHoi=" + danhSachCauHoi +
                ", danhSachPhienLamBaiCuaBaiThi=" + danhSachPhienLamBaiCuaBaiThi +
                ", giaoVien=" + giaoVien +
                '}';
    }
}

