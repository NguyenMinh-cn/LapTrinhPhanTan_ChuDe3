package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Lop")
public class Lop implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int maLop;

    @Column(nullable = false)
    private String tenLop;

    // One-to-many relationship with HocSinh
    @OneToMany(mappedBy = "lop")
    private List<HocSinh> danhSachHocSinh = new ArrayList<>();

    // Many-to-many relationship with BaiThi
    @ManyToMany(mappedBy = "danhSachLop")
    private List<BaiThi> danhSachBaiThi = new ArrayList<>();

    @Override
    public String toString() {
        return "Lop{" +
                "maLop=" + maLop +
                ", tenLop='" + tenLop + '\'' +
                ", soHocSinh=" + (danhSachHocSinh != null ? danhSachHocSinh.size() : 0) +
                ", soBaiThi=" + (danhSachBaiThi != null ? danhSachBaiThi.size() : 0) +
                '}';
    }
}
