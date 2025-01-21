package entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "GiangVien")

public class GiaoVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGiangVien")
    @EqualsAndHashCode.Include
    private int maGiangVien;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String soDienThoai;

    // Quan hệ OneToMany với BaiThi
    @OneToMany(mappedBy = "giaoVien") // Tham chiếu thuộc tính giaoVien trong BaiThi
    private Set<BaiThi> danhSachBaiThi;
}
