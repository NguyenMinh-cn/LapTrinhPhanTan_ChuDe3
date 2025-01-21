package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CauTraLoi")
public class CauTraLoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauTraLoi")
    @EqualsAndHashCode.Include
    private int maCauTraLoi;


    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnA;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnB;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnC;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnD;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String dapAnDaChon;

    @ManyToOne
    @JoinColumn(name = "maPhien") // Tham chiếu tới khóa chính của bảng PhienLamBai
    private PhienLamBai phienLamBai;

}
