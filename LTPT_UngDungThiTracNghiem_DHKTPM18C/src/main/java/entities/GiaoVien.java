package entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "GiaoVien")

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
    private List<BaiThi> danhSachBaiThi = new ArrayList<BaiThi>();

//18/4
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "tenDangNhap", insertable = false, updatable = false)
    private TaiKhoan taiKhoan;


}
//@Entity
//public class GiaoVien {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String hoTen;
//    private String email;
//    private String soDienThoai;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tenDangNhap")
//    private TaiKhoan taiKhoan;
//
//    @ManyToMany
//    @JoinTable(
//            name = "giao_vien_mon",
//            joinColumns = @JoinColumn(name = "giaoVien_id"),
//            inverseJoinColumns = @JoinColumn(name = "monHoc_id")
//    )
//    private List<MonHoc> monPhuTrach;
//}