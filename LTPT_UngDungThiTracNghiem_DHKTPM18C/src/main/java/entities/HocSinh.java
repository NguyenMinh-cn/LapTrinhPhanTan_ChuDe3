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
@Table(name = "HocSinh")
public class HocSinh implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maHocSinh", unique = true, nullable = false)
    private long maHocSinh;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(15)",nullable = false)
    private String soDienThoai;

    @ManyToOne
    @JoinColumn(name = "maLop")
    private Lop lop;

    @OneToMany(mappedBy = "hocSinh", cascade = CascadeType.ALL, orphanRemoval = true)
//    tự động xóa các "orphan" (đối tượng mồ côi)
    private List<PhienLamBai> danhSachPhienLamBai = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "tenDangNhap", insertable = false, updatable = false)
    private TaiKhoan taiKhoan;

    @Override
    public String toString() {
        return "HocSinh{" +
                "maHocSinh='" + maHocSinh + '\'' +
                ", hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", lop=" + lop +
                ", danhSachPhienLamBai=" + danhSachPhienLamBai +
                ", taiKhoan=" + taiKhoan +
                '}';
    }
}
