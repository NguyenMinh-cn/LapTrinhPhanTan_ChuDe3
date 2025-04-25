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
    private static final long serialVersionUID = 1L;

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

    @OneToMany(mappedBy = "giaoVien") // Tham chiếu thuộc tính giaoVien trong BaiThi
    private List<BaiThi> danhSachBaiThi = new ArrayList<BaiThi>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "tenDangNhap", insertable = false, updatable = false)
    private TaiKhoan taiKhoan;

    @Override
    public String toString() {
        return "GiaoVien{" +
                "maGiaoVien=" + maGiaoVien +
                ", hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", taiKhoan=" + (taiKhoan != null ? taiKhoan.getTenDangNhap() : "null") +
                ", soLuongBaiThi=" + (danhSachBaiThi != null ? danhSachBaiThi.size() : 0) +
                '}';
    }
}
