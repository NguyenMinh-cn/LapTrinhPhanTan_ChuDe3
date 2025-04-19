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
@ToString
@Table(name = "Lop")
public class Lop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int maLop;

    @Column(nullable = false)
    private String tenLop;
    //18/4
    @OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
    private List<HocSinh> danhSachHocSinh = new ArrayList<>();
    //18/4
    @ManyToMany(mappedBy = "danhSachLop")
    private List<BaiThi> danhSachBaiThi = new ArrayList<>();

}
//@Entity
//public class Lop {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String tenLop;
//
//    @OneToMany(mappedBy = "lop")
//    private List<HocSinh> danhSachHocSinh;
//@ManyToMany(mappedBy = "danhSachLop")
//private List<BaiThi> danhSachBaiThi;

//}
