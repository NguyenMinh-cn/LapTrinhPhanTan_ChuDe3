package test;

import entities.Lop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class InsertLopHoc {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            emf = Persistence.createEntityManagerFactory("mariadb");
            em = emf.createEntityManager();

            em.getTransaction().begin();

            for (int i = 1; i <= 10; i++) {
                Lop lop = new Lop();
                lop.setTenLop("12A" + i); // Tên lớp sẽ là 12A1, 12A2, ..., 12A10
                em.persist(lop);
            }

            em.getTransaction().commit();
            System.out.println("Đã chèn 10 lớp thành công!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
