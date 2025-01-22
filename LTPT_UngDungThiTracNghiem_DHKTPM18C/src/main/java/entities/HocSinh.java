package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "HocSinh")
public class HocSinh {
    @Id
    @Column(name = "MaHocSinh")
    @EqualsAndHashCode.Include
    private String maHocSinh;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MaLop")
    private Lop lop;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String soDienThoai;

    @OneToMany(mappedBy="hocSinh", cascade = CascadeType.ALL)//tên thuộc tính, không phải tên bảng
    private Set<PhienLamBai> danhSachPhienLamBai;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "tenDangNhap", insertable = false, updatable = false)
    private TaiKhoan taiKhoan;
}
