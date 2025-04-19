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
@Table(name = "CauHoi")
@ToString
public class CauHoi implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int maCauHoi;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String noiDung;

    //18-4
    @ElementCollection
    private List<String> danhSachDapAn = new ArrayList<>();

    private String dapAnDung;

    // Quan hệ Many-to-Many với BaiThi
    @ManyToMany(mappedBy = "danhSachCauHoi", cascade = CascadeType.ALL)
    private List<BaiThi> danhSachBaiThi = new ArrayList<>();

//    @OneToMany(mappedBy = "cauHoi", cascade = CascadeType.ALL)
//    private List<CauTraLoi> danhSachCauTraLoi = new ArrayList<>();

    //18-4
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "maChuDe", nullable = true)
    private ChuDe chuDe;

}
