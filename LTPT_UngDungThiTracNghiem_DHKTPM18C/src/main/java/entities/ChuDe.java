package entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ChuDe")
@ToString
public class ChuDe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaChuDe")
    @EqualsAndHashCode.Include
    private int maChuDe;

    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String tenChuDe;

    //18/4
//    @ManyToOne(cascade = CascadeType.ALL) //??
    @ManyToOne
    @JoinColumn(name = "maMon") // Tham chiếu tới khóa chính của bảng PhienLamBai
    private MonHoc monHoc;

    //18-4
    @OneToMany(mappedBy = "chuDe", cascade = CascadeType.ALL) //ok
    private List<CauHoi> danhSachCauHoi = new ArrayList<>();
}
