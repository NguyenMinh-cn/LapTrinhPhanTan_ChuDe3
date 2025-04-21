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
@Table(name = "CauTraLoi")

public class CauTraLoi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauTraLoi")
    @EqualsAndHashCode.Include
    private long maCauTraLoi;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String noiDungCauHoi;

    @ElementCollection
    private List<String> danhSachDapAn = new ArrayList<>();

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnDaChon;

    boolean ketQua;

    @ManyToOne
    @JoinColumn(name = "maPhien") // Tham chiếu tới khóa chính của bảng PhienLamBai
    private PhienLamBai phienLamBai;

    @Override
    public String toString() {
        return "CauTraLoi{" +
                "maCauTraLoi=" + maCauTraLoi +
                ", noiDungCauHoi='" + noiDungCauHoi + '\'' +
                ", danhSachDapAn=" + danhSachDapAn +
                ", dapAnDaChon='" + dapAnDaChon + '\'' +
                ", ketQua=" + ketQua +
                ", maPhien=" + (phienLamBai != null ? phienLamBai.getMaPhien() : "null") +
                '}';
    }
}
