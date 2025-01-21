package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "CauHoi")
public class CauHoi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCauHoi")
    @EqualsAndHashCode.Include
    private String maCauHoi;
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
}
