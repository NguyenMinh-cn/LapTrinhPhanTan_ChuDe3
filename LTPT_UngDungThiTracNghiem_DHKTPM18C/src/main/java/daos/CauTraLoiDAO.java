package daos;

import entities.CauTraLoi;
import jakarta.persistence.EntityManager;

public class CauTraLoiDAO extends GenericDAO<CauTraLoi, Integer>{
    public CauTraLoiDAO(Class<CauTraLoi> clazz) {
        super(clazz);
    }
    public CauTraLoiDAO(EntityManager em, Class<CauTraLoi> clazz) {
        super(em, clazz);
    }
}
