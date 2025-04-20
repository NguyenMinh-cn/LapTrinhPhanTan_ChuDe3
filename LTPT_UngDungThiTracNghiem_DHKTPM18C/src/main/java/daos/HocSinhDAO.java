package daos;

import entities.HocSinh;
import jakarta.persistence.EntityManager;

public class HocSinhDAO extends GenericDAO<HocSinh, String> {
    public HocSinhDAO(Class<HocSinh> clazz) {
        super(clazz);
    }

    public HocSinhDAO(EntityManager em, Class<HocSinh> clazz) {
        super(em, clazz);
    }
}