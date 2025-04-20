//package test;
//
//import entities.BaiThi;
//import entities.HocSinh;
//import entities.PhienLamBai;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//import net.datafaker.Faker;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class InsertPhienLamBai {
//    public void themDSPhienLamBai() {
//        EntityManagerFactory emf = null;
//        EntityManager em = null;
//        try {
//            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
//            emf = Persistence.createEntityManagerFactory("mariadb");
//            em = emf.createEntityManager();
//            Faker faker = new Faker();
//
//            // Lấy danh sách bài thi và học sinh từ cơ sở dữ liệu
//            List<BaiThi> baiThiList = em.createQuery("SELECT b FROM BaiThi b", BaiThi.class).getResultList();
//            List<HocSinh> hocSinhList = em.createQuery("SELECT h FROM HocSinh h", HocSinh.class).getResultList();
//
//            em.getTransaction().begin();
//
//            for (int i = 0; i < 10; i++) {
//                String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//                HocSinh hocSinh = hocSinhList.get(i % hocSinhList.size());
//                String maPhien = currentTime + hocSinh.getMaHocSinh();
//                PhienLamBai phienLamBai = new PhienLamBai();
//                phienLamBai.setMaPhien(maPhien);
//                phienLamBai.setHocSinh(hocSinh);
//                phienLamBai.setBaiThi(baiThiList.get(i % baiThiList.size()));
//                phienLamBai.setThoiGianBatDau(LocalDateTime.now().plusMinutes(i * 10));
//                phienLamBai.setThoiGianKetThuc(LocalDateTime.now().plusMinutes(i * 10 + 30));
//
//                em.persist(phienLamBai);
//            }
//
//            em.getTransaction().commit();
//            System.out.println("Dữ liệu đã được chèn thành công vào bảng PhienLamBai!");
//
//        } catch (Exception e) {
//            if (em != null && em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            e.printStackTrace();
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
