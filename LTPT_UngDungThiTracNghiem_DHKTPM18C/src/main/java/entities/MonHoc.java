package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

@Table(name = "MonHoc")
@ToString(of = "tenMon")
public class MonHoc implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaMon")
    @EqualsAndHashCode.Include
    private int maMon;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String tenMon;

    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL)
    private Set<BaiThi> danhSachBaiThiTheoMon;

}