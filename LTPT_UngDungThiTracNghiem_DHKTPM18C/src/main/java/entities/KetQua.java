package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@ToString
@Table(name = "KetQua")
public class KetQua implements Serializable {
    @Id
    @Column(name = "MaPhienLamBai")
    @EqualsAndHashCode.Include
    private String maPhienLamBai; // Sử dụng MaPhien làm khóa chính để đảm bảo mối qh 1-1

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MaPhien", nullable = false)
    private PhienLamBai phienLamBai;

    @Column(nullable = false)
    private double diem;

    @Column(nullable = false)
    private int soCauDung;

    @Column(nullable = false)
    private int soCauSai;

    @Column(nullable = false)
    private int tongThoiGianLamBai;
}

//@Entity
//public class KetQua {
//    @Id
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "MaPhien")
//
//    private int soCauDung;
//    private double diem;
//}
