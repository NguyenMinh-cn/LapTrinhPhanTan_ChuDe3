package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "KET_QUA")
public class KetQua {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKetQua")
    @EqualsAndHashCode.Include
    private int maKetQua;

    @OneToOne
    @JoinColumn(name = "MaPhienLamBai", nullable = false)
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