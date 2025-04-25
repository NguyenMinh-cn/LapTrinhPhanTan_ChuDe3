package daos;

import entities.ChuDe;
import jakarta.persistence.EntityManager;

public class ChuDeDAO extends GenericDAO<ChuDe, Integer>{
    public ChuDeDAO(Class<ChuDe> clazz) {
        super(clazz);
    }
    public ChuDeDAO(EntityManager em, Class<ChuDe> clazz) {
        super(em, clazz);
    }
}
