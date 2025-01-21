package entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
@Table(name = "BaiThi")
public class BaiThi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maBaiThi;

    @Column(nullable = false)
    private String tenBaiThi;

    @Column(nullable = false)
    private LocalDateTime thoiGianBatDau;

    @Column(nullable = false)
    private LocalDateTime thoiGianKetThuc;
}
