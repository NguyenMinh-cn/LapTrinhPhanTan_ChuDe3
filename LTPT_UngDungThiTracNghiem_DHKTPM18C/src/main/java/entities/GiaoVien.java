package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "GiangVien")
public class GiangVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaGiangVien")
    @EqualsAndHashCode.Include
    private int maGiangVien;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String hoTen;

    @Column(columnDefinition = "varchar(60)", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String soDienThoai;

    @ManyToMany
    @JoinTable(
            name = "GiangVien_MonHoc",
            joinColumns = @JoinColumn(name = "MaGiangVien"),
            inverseJoinColumns = @JoinColumn(name = "MaMonHoc")
    )
    private List<MonHoc> monHocGiangPhuTrach;

}