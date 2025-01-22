package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CauHoi")
public class CauHoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int maCauHoi;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String noiDung;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnA;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnB;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnC;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnD;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnDung;
    // Quan hệ Many-to-Many với BaiThi
    @ManyToMany(mappedBy = "danhSachCauHoi", cascade = CascadeType.ALL)
    private Set<BaiThi> danhSachBaiThi;

    @OneToMany(mappedBy = "cauHoi", cascade = CascadeType.ALL)
    private Set<CauTraLoi> danhSachCauTraLoi;

    public CauHoi() {}
    public CauHoi(String noiDung, String dapAnA, String dapAnB, String dapAnC, String dapAnD, String dapAnDung) {
        this.noiDung = noiDung;
        this.dapAnA = dapAnA;
        this.dapAnB = dapAnB;
        this.dapAnC = dapAnC;
        this.dapAnD = dapAnD;
        this.dapAnDung = dapAnDung;
    }

}
