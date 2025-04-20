//package test;
//
//import entities.Lop;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//public class InsertLopHoc {
//    public void themDSLopHoc() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
//        EntityManager em = emf.createEntityManager();
//
//        try {
//            em.getTransaction().begin();
//
//            // Chèn 10 lớp học với tên lớp từ 12A1 đến 12A10
//            for (int i = 1; i <= 10; i++) {
//                Lop lop = new Lop();
//                lop.setTenLop("12A" + i); // Tên lớp sẽ là 12A1, 12A2, ..., 12A10
//                em.persist(lop);
//            }
//
//            em.getTransaction().commit();
//            System.out.println("Đã chèn 10 lớp thành công!");
//
//        } catch (Exception e) {
//            if (em.getTransaction().isActive()) {
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
