package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity

@Table(name = "MonHoc")
public class MonHoc implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaMon")
    @EqualsAndHashCode.Include
    private int maMon;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String tenMon;

    public void setTenMon(String tenMon) {
        if (tenMon == null || tenMon.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên môn không được để trống.");
        }
        // Cập nhật điều kiện kiểm tra, cho phép ký tự có dấu
        if (!tenMon.matches("[a-zA-Z0-9\\s\\p{IsLatin}]+")) {
            throw new IllegalArgumentException("Tên môn sai định dạng.");
        }
        this.tenMon = tenMon.trim(); // Lưu lại bỏ khoảng trắng thừa nếu có
    }


    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL)
    private List<BaiThi> danhSachBaiThiTheoMon = new ArrayList();

    @OneToMany(mappedBy = "monHoc", cascade = CascadeType.ALL)
    private List<ChuDe> danhSachChuDe = new ArrayList<>();

    @Override
    public String toString() {
        return "MonHoc{" +
                "maMon=" + maMon +
                ", tenMon='" + tenMon + '\'' +
                ", soChuDe=" + (danhSachChuDe != null ? danhSachChuDe.size() : 0) +
                ", soBaiThi=" + (danhSachBaiThiTheoMon != null ? danhSachBaiThiTheoMon.size() : 0) +
                '}';
    }
}