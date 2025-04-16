package entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "GiaoVien")
@ToString
public class GiaoVien implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGiaoVien")
    @EqualsAndHashCode.Include
    private int maGiaoVien;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String soDienThoai;

    // Quan hệ OneToMany với BaiThi
    @OneToMany(mappedBy = "giaoVien") // Tham chiếu thuộc tính giaoVien trong BaiThi
    private Set<BaiThi> danhSachBaiThi;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "GiaoVien_MonHoc",
            joinColumns = @JoinColumn(name = "MaGiaoVien"),
            inverseJoinColumns = @JoinColumn(name = "MaMonHoc")
    )
    private List<MonHoc> monHocGiaoVienPhuTrach;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "tenDangNhap", insertable = false, updatable = false)
    private TaiKhoan taiKhoan;

}
