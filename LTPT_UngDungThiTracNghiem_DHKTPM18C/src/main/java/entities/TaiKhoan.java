package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTaiKhoan")
    @EqualsAndHashCode.Include
    private int maTaiKhoan;

    @Column(columnDefinition = "varchar(50)", nullable = false, unique = true)
    private String tenDangNhap;

    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String loaiTaiKhoan;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String matKhau;
}