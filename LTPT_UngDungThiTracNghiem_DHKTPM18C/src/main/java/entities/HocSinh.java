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
    @Column(columnDefinition = "varchar(15)", nullable = false)
    private String maHocSinh;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(15)",nullable = false)
    private String soDienThoai;

    //18/4
//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "maLop")
    private Lop lop;

    @OneToMany(mappedBy = "hocSinh", cascade = CascadeType.ALL, orphanRemoval = true)
//    tự động xóa các "orphan" (đối tượng mồ côi)
    private List<PhienLamBai> danhSachPhienLamBai = new ArrayList<>();

    //18/4
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "tenDangNhap", insertable = false, updatable = false)
    private TaiKhoan taiKhoan;

}
//@Entity
//public class HocSinh {
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
//    @ManyToOne
//    @JoinColumn(name = "lop_id")
//    private Lop lop;
//}