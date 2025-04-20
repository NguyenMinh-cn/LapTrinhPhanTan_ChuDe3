package daos;

import entities.GiaoVien;
import entities.MonHoc;
import jakarta.persistence.EntityManager;

public class GiaoVienDAO extends GenericDAO<GiaoVien, Integer> {
    public GiaoVienDAO(Class<GiaoVien> clazz) {
        super(clazz);
    }

    public GiaoVienDAO(EntityManager em, Class<GiaoVien> clazz) {
        super(em, clazz);
    }
}
