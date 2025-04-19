package entities;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PhienLamBai")
public class PhienLamBai implements Serializable {

    @Id
    @Column(name = "MaPhien")
    private String maPhien;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;

    private double diem;

    //18/4
//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "maHocSinh") // Tham chiếu tới khóa chính của bảng HocSinh
    private HocSinh hocSinh;

//    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "maBaiThi") // Tham chiếu tới khóa chính của bảng BaiThi
    private BaiThi baiThi;

    //18/4
    @OneToMany(mappedBy="phienLamBai", cascade = CascadeType.ALL)//tên thuộc tính, không phải tên bảng
    private List<CauTraLoi> danhSachCauTraLoi = new ArrayList<>();


}
//@Entity
//public class PhienLamBai {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private double diem;

//    @ManyToOne
//    private HocSinh hocSinh;
//
//    @ManyToOne
//    private BaiThi baiThi;
//
//    private LocalDateTime batDau;
//    private LocalDateTime ketThuc;
//
//    @OneToMany(mappedBy = "phienLamBai", cascade = CascadeType.ALL)
//    private List<CauTraLoi> cauTraLoi;
//}