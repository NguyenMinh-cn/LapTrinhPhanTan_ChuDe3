package daos;

import entities.CauTraLoi;
import entities.PhienLamBai;
import jakarta.persistence.EntityManager;

public class PhienLamBaiDAO extends GenericDAO<PhienLamBai, String>{
    public PhienLamBaiDAO(Class<PhienLamBai> clazz) {
        super(clazz);
    }
    public PhienLamBaiDAO(EntityManager em, Class<PhienLamBai> clazz) {
        super(em, clazz);
    }
}
