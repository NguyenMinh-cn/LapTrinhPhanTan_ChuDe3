//package test;
//
//import entities.BaiThi;
//import entities.CauHoi;
//import entities.GiaoVien;
//import entities.Lop;
//import entities.MonHoc;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import net.datafaker.Faker;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//
//public class InsertBaiThi {
//    public void themDSBaiThi() {
//        EntityManagerFactory emf = null;
//        EntityManager em = null;
//        try {
//            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
//            emf = Persistence.createEntityManagerFactory("mariadb");
//            em = emf.createEntityManager();
//            Faker faker = new Faker();
//
//            List<MonHoc> monHocList = em.createQuery("SELECT m FROM MonHoc m", MonHoc.class).getResultList();
//            List<Lop> lopList = em.createQuery("SELECT l FROM Lop l", Lop.class).getResultList();
//            List<GiaoVien> giaoVienList = em.createQuery("SELECT g FROM GiaoVien g", GiaoVien.class).getResultList();
//            List<CauHoi> cauHoiList = em.createQuery("SELECT c FROM CauHoi c", CauHoi.class).getResultList();
//
//            System.out.println("Danh sách môn học:");
//            for (MonHoc monHoc : monHocList) {
//                System.out.println("Mã môn: " + monHoc.getMaMon() + ", Tên môn: " + monHoc.getTenMon());
//            }
//
//            System.out.println("Danh sách lớp học:");
//            for (Lop lop : lopList) {
//                System.out.println("Mã lớp: " + lop.getMaLop() + ", Tên lớp: " + lop.getTenLop());
//            }
//
//            System.out.println("Danh sách giáo viên:");
//            for (GiaoVien giaoVien : giaoVienList) {
//                System.out.println("Mã giáo viên: " + giaoVien.getMaGiaoVien() + ", Tên giáo viên: " + giaoVien.getHoTen());
//            }
//
//            em.getTransaction().begin();
//
//            for (int i = 0; i < 5; i++) {
//                BaiThi baiThi = new BaiThi();
//                baiThi.setTenBaiThi("Bài thi " + (i + 1));
//                baiThi.setMonHoc(monHocList.get(i % monHocList.size()));
//                baiThi.setLop(lopList.get(i % lopList.size()));
//                baiThi.setThoiGianBatDau(LocalDateTime.now().plusHours(i));
//                baiThi.setThoiGianKetThuc(baiThi.getThoiGianBatDau().plusMinutes(60));
//                baiThi.setThoiLuong(60);
//                baiThi.setGiaoVien(giaoVienList.get(i % giaoVienList.size()));
//
//                Set<CauHoi> danhSachCauHoi = Set.copyOf(cauHoiList.subList(i % cauHoiList.size(), Math.min(i + 3, cauHoiList.size())));
//                baiThi.setDanhSachCauHoi(danhSachCauHoi);
//
//                em.persist(baiThi);
//            }
//
//            em.getTransaction().commit();
//            System.out.println("Dữ liệu bài thi đã được ghi thành công!");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//            if (emf != null) {
//                emf.close();
//            }
//        }
//    }
//}
