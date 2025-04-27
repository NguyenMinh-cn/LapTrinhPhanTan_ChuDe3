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
public class CauHoi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long maCauHoi;
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String noiDung;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> danhSachDapAn = new ArrayList<>();

    private String dapAnDung;


    @ManyToMany(mappedBy = "danhSachCauHoi")
    private List<BaiThi> danhSachBaiThi = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "maChuDe", nullable = true)
    private ChuDe chuDe;

    @Override
    public String toString() {
        return "CauHoi{" +
                "maCauHoi=" + maCauHoi +
                ", noiDung='" + noiDung + '\'' +
                ", danhSachDapAn=" + danhSachDapAn +
                ", dapAnDung='" + dapAnDung + '\'' +
                ", soBaiThiThamGia=" + (danhSachBaiThi != null ? danhSachBaiThi.size() : 0) +
                ", chuDe=" + (chuDe != null ? chuDe.getTenChuDe() : "null") +
                '}';
    }
}
