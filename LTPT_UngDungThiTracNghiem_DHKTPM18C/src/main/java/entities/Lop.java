package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Lop")
public class Lop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int maLop;

    @Column(nullable = false)
    private String tenLop;

    @OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
    private Set<HocSinh> danhSachHocSinh;

    @OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
    private Set<BaiThi> danhSachBaiThi;

}
