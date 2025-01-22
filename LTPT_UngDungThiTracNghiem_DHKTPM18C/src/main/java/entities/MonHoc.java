package entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "MonHoc")
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaMon")
    @EqualsAndHashCode.Include
    private int maMon;

    @Column(columnDefinition = "varchar(100)", nullable = false)
    private String tenMon;
}