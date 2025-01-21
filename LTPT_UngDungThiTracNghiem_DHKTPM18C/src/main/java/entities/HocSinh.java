package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "HocSinh")
public class HocSinh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHocSinh")
    @EqualsAndHashCode.Include
    private int maHocSinh;
    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;
    @ManyToOne
    @JoinColumn(name = "MaLop")
    private Lop lop;
    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;
    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String soDienThoai;
}
