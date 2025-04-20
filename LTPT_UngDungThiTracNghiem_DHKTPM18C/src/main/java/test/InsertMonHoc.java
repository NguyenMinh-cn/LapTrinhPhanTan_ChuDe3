//package test;
//
//import entities.MonHoc;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class InsertMonHoc {
//
//    public void themDSMonHoc() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
//        EntityManager em = emf.createEntityManager();
//
//        String[] tenMonHocs = {
//                "Toán học", "Văn học", "Vật lý", "Hóa học",
//                "Sinh học", "Lịch sử", "Địa lý", "Ngoại ngữ",
//                "Giáo dục công dân", "Giáo dục thể chất"
//        };
//
//        try {
//            em.getTransaction().begin();
//
//            for (String tenMon : tenMonHocs) {
//                MonHoc monHoc = new MonHoc();
//                monHoc.setTenMon(tenMon);
//                em.persist(monHoc);
//            }
//
//            em.getTransaction().commit();
//            System.out.println("Dữ liệu môn học đã được ghi thành công!");
//        } catch (Exception e) {
//            em.getTransaction().rollback();
//            e.printStackTrace();
//        } finally {
//            em.close();
//            emf.close();
//        }
//    }
//
//}
