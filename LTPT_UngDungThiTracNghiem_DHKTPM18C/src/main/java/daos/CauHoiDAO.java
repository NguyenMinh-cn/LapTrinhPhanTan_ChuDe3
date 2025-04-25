package daos;

import entities.BaiThi;
import entities.CauHoi;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CauHoiDAO extends GenericDAO<CauHoi, Integer>{
    public CauHoiDAO(Class<CauHoi> clazz) {
        super(clazz);
    }
    public CauHoiDAO(EntityManager em, Class<CauHoi> clazz) {
        super(em, clazz);
    }
    public List<CauHoi> timCauHoiTheoMaBaiThi(int maBaiThi) {
        String jpql = """
        SELECT DISTINCT ch FROM BaiThi bt
        JOIN bt.danhSachCauHoi ch
        LEFT JOIN FETCH ch.chuDe
        WHERE bt.maBaiThi = :maBaiThi
    """;

        return em.createQuery(jpql, CauHoi.class)
                .setParameter("maBaiThi", maBaiThi)
                .getResultList();
    }
//    public static void main(String[] args) {
//        CauHoiDAO chDAO = new CauHoiDAO(CauHoi.class);
//        List<CauHoi> list = chDAO.timCauHoiTheoMaBaiThi(1);
//        for (CauHoi ch : list) {
//            System.out.println(ch+"/n");
//
//        }
//    }
}
