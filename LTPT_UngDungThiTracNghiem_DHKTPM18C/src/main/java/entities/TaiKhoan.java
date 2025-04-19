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
@Table(name = "TaiKhoan")
public class TaiKhoan implements Serializable {
    //18/4
    @Id
    @Column(name = "TenDangNhap", unique = true, nullable = false) // Email là khóa chính và là tên đăng nhập
    @EqualsAndHashCode.Include
    private String tenDangNhap;// Sử dụng email làm tên đăng nhập
    //18/4
    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String loaiTaiKhoan;
    //18/4
    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String matKhau;

    //18/4
    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", loaiTaiKhoan='" + loaiTaiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}

