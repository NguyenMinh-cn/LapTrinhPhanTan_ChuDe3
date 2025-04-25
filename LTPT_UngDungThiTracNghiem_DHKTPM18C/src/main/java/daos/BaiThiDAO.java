package daos;

import entities.BaiThi;
import entities.GiaoVien;
import jakarta.persistence.EntityManager;

import java.util.List;


public class BaiThiDAO extends GenericDAO<BaiThi, Integer>{
    public BaiThiDAO(Class<BaiThi> clazz) {
        super(clazz);
    }
    public BaiThiDAO(EntityManager em, Class<BaiThi> clazz) {
        super(em, clazz);
    }

    public List<BaiThi> timDSBaiTHiTheoMaGiaoVien(int maGiaoVien) {


        String jpql = "SELECT DISTINCT bt FROM BaiThi bt " +
                "JOIN FETCH bt.monHoc mh " +
                "JOIN FETCH bt.danhSachCauHoi ch " +
                "WHERE bt.giaoVien.maGiaoVien = :maGiaoVien";

        return em.createQuery(jpql, BaiThi.class)
                .setParameter("maGiaoVien", maGiaoVien)
                .getResultList();
    }

//    public static void main(String[] args) {
//        BaiThiDAO baiThiDAO = new BaiThiDAO(BaiThi.class);
//        List<BaiThi> list = baiThiDAO.timDSBaiTHiTheoMaGiaoVien(1);
//        for (BaiThi baiThi : list) {
//            System.out.println("Tên bài thi: " + baiThi.getTenBaiThi());
//            System.out.println("Môn học: " + baiThi.getMonHoc().getTenMon());
//            System.out.println("Thời gian bắt đầu: " + baiThi.getThoiGianBatDau());
//            System.out.println("Thời gian kết thúc: " + baiThi.getThoiGianKetThuc());
//            System.out.println("Thời lượng làm bài: " + baiThi.getThoiLuong() + " phút");
//            System.out.println("Số câu hỏi: " + baiThi.getDanhSachCauHoi().size());
//            System.out.println("Giáo viên tạo bài thi: " + baiThi.getGiaoVien().getHoTen());
//            System.out.println("-----");
//        }
//    }
}
