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
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TenDangNhap", unique = true, nullable = false) // Email là khóa chính và là tên đăng nhập
    @EqualsAndHashCode.Include
    private String tenDangNhap;// Sử dụng email làm tên đăng nhập

    @Column(columnDefinition = "varchar(20)", nullable = false)
    private String loaiTaiKhoan;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String matKhau;

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "tenDangNhap='" + tenDangNhap + '\'' +
                ", loaiTaiKhoan='" + loaiTaiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                '}';
    }
}

