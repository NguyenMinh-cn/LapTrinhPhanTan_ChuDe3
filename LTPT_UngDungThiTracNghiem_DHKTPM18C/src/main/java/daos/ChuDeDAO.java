package daos;

import entities.ChuDe;
import entities.MonHoc;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import util.JPAUtil;

import java.rmi.RemoteException;
import java.util.List;

public class ChuDeDAO extends GenericDAO<ChuDe, Integer> {
    public ChuDeDAO(Class<ChuDe> clazz) {
        super(clazz);
    }

    public ChuDeDAO(EntityManager em, Class<ChuDe> clazz) {
        super(em, clazz);
    }

    public List<ChuDe> findByTenMonHoc(String tenMon) {
        String jpql = "SELECT cd FROM ChuDe cd WHERE cd.monHoc.tenMon = :tenMon";
        return em.createQuery(jpql, ChuDe.class)
                .setParameter("tenMon", tenMon)
                .getResultList();
    }

    //tìm theo tên môn học và tên chủ đề
    public ChuDe findByTenMonHocAndTenChuDe(String tenMon, String tenChuDe) {
        try{
            String jpql = "SELECT cd FROM ChuDe cd WHERE cd.monHoc.tenMon = :tenMon AND cd.tenChuDe = :tenChuDe";
            return em.createQuery(jpql, ChuDe.class)
                    .setParameter("tenMon", tenMon)
                    .setParameter("tenChuDe", tenChuDe)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null; // Không tìm thấy, return null
        }
    }

    //Kiểm tra chủ đề có chứa câu hỏi hay không
    public boolean hasCauHoi(int maChuDe) {
        String jpql = "SELECT COUNT(ch) FROM CauHoi ch WHERE ch.chuDe.maChuDe = :maChuDe";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("maChuDe", maChuDe)
                .getSingleResult();
        return count > 0;
    }

    //Kiểm tra trùng tên
    public boolean isDuplicate(String tenChuDe, String tenMon) {
        String jpql = "SELECT COUNT(cd) FROM ChuDe cd WHERE cd.tenChuDe = :tenChuDe AND cd.monHoc.tenMon = :tenMon";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("tenChuDe", tenChuDe)
                .setParameter("tenMon", tenMon)
                .getSingleResult();
        return count > 0;
    }
}
