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
        String jpql = "SELECT COUNT(bt) FROM BaiThi bt JOIN bt.danhSachCauHoi ch WHERE ch.maCauHoi = :maCauHoi";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("maCauHoi", maCauHoi)
                .getSingleResult();
        return count > 0;
    }


    //Câu hỏi trong bài thi đã diễn ra (đã qua thoiGianBatDau)
    public boolean inBaiThiDaDienRa(int maCauHoi) {
        String jpql = "SELECT COUNT(bt) FROM BaiThi bt JOIN bt.danhSachCauHoi ch WHERE ch.id = :maCauHoi AND bt.thoiGianBatDau < CURRENT_TIMESTAMP";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("maCauHoi", maCauHoi)
                .getSingleResult();
        return count > 0;
    }

    //Lấy danh sách câu hỏi có chủ đề chủ đề (chủ đề không null)
    public List<CauHoi> getCauHoiCoChuDe() {
        String jpql = "SELECT ch FROM CauHoi ch WHERE ch.chuDe IS NOT NULL";
        return em.createQuery(jpql, CauHoi.class).getResultList();
    }
}
