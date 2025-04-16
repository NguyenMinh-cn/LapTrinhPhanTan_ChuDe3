package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TaiKhoan")
public class TaiKhoan implements Serializable {
    @Id
    @Column(name = "TenDangNhap", unique = true, nullable = false) // Email là khóa chính và là tên đăng nhập
    @EqualsAndHashCode.Include
    private String tenDangNhap;// Sử dụng email làm tên đăng nhập

    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String loaiTaiKhoan;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String matKhau;


}