package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Lop")
public class Lop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int maLop;

    @Column(nullable = false)
    private String tenLop;

    @OneToMany(mappedBy = "lop")
    private List<HocSinh> danhSachHocSinh = new ArrayList<>();

    //18/4
    @ManyToMany(mappedBy = "danhSachLop")
    private List<BaiThi> danhSachBaiThi = new ArrayList<>();

    @Override
    public String toString() {
        return "Lop{" +
                "maLop=" + maLop +
                ", tenLop='" + tenLop + '\'' +
                ", danhSachHocSinh=" + danhSachHocSinh +
                ", danhSachBaiThi=" + danhSachBaiThi +
                '}';
    }
}

