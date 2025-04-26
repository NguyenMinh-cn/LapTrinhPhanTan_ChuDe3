package daos;

import entities.MonHoc;
import jakarta.persistence.EntityManager;

public class MonHocDAO extends GenericDAO<MonHoc, Integer> {
    public MonHocDAO(Class<MonHoc> clazz) {
        super(clazz);
    }

    public MonHocDAO(EntityManager em, Class<MonHoc> clazz) {
        super(em, clazz);
    }

}
