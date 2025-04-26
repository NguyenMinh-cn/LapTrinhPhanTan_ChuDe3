package daos;

import entities.CauHoi;
import entities.ChuDe;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CauHoiDAO extends GenericDAO<CauHoi, Integer>{
    public CauHoiDAO(Class<CauHoi> clazz) {
        super(clazz);
    }

    public CauHoiDAO(EntityManager em, Class<CauHoi> clazz) {
        super(em, clazz);
    }

    //Kiểm tra câu hỏi có trong bài thi (BaiThi) hay không
    public boolean inBaiThi(int maCauHoi) {
        String jpql = "SELECT COUNT(bt) FROM BaiThi bt WHERE :maCauHoi MEMBER OF bt.danhSachCauHoi";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("maCauHoi", maCauHoi)
                .getSingleResult();
        return count > 0;
    }
}
