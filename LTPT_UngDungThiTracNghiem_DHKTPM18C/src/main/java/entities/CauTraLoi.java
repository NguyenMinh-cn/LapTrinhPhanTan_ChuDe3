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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauTraLoi")
    @EqualsAndHashCode.Include
    private int maCauTraLoi;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String noiDungCauHoi;

    @ElementCollection
    private List<String> danhSachDapAn = new ArrayList<>();

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnDaChon;

    boolean ketQua;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "maPhien") // Tham chiếu tới khóa chính của bảng PhienLamBai
    private PhienLamBai phienLamBai;

}
//@Entity
//public class CauTraLoi {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @ManyToOne
//    private CauHoi cauHoi;
//
//    private int dapAnDuocChon;
//    boolean ketQua;
//    @ManyToOne
//    private PhienLamBai phienLamBai;
//}