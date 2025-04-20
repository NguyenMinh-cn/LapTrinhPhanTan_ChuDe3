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

@Table(name = "MonHoc")
public class MonHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaMon")
    @EqualsAndHashCode.Include
    private int maMon;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String tenMon;

    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL)
    private List<BaiThi> danhSachBaiThiTheoMon = new ArrayList();

    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL)
    private List<ChuDe> danhSachChuDe = new ArrayList<>();

    @Override
    public String toString() {
        return "MonHoc{" +
                "maMon=" + maMon +
                ", tenMon='" + tenMon + '\'' +

                ", danhSachChuDe=" + danhSachChuDe +
                '}';
    }
}
