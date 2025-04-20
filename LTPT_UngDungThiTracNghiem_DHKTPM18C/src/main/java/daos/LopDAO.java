package daos;

import entities.Lop;
import entities.MonHoc;
import jakarta.persistence.EntityManager;

public class LopDAO extends GenericDAO<Lop, Integer> {
    public LopDAO(Class<Lop> clazz) {
        super(clazz);
    }

    public LopDAO(EntityManager em, Class<Lop> clazz) {
        super(em, clazz);
    }
}
