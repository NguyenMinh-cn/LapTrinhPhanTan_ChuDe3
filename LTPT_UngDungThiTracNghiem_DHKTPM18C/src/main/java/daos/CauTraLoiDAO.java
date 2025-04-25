package daos;

import entities.CauTraLoi;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CauTraLoiDAO extends GenericDAO<CauTraLoi, Long> {

    public CauTraLoiDAO(Class<CauTraLoi> clazz) {
        super(clazz);
    }

    public CauTraLoiDAO(EntityManager em, Class<CauTraLoi> clazz) {
        super(em, clazz);
    }

    // Lấy danh sách câu trả lời theo mã phiên làm bài
    public List<CauTraLoi> findByPhienLamBai(String maPhienLamBai) {
        try {
            return em.createQuery("SELECT ct FROM CauTraLoi ct " +
                            "WHERE ct.phienLamBai.maPhienLamBai = :maPhienLamBai", CauTraLoi.class)
                    .setParameter("maPhienLamBai", maPhienLamBai)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}