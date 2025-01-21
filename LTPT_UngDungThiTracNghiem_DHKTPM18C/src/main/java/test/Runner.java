package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import entities.BaiThi;
import net.datafaker.Faker;

import java.time.LocalDateTime;
public class Runner {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb")
                .createEntityManager();
        Faker faker = new Faker();

        // Tạo đối tượng BaiThi với dữ liệu mẫu
        BaiThi baiThi = new BaiThi();
        baiThi.setTenBaiThi(faker.lorem().sentence());
        baiThi.setThoiGianBatDau(LocalDateTime.now());
        baiThi.setThoiGianKetThuc(LocalDateTime.now().plusHours(1));

        // Bắt đầu giao dịch
        EntityTransaction transaction = em.getTransaction();
        try {
            // Bắt đầu giao dịch
            transaction.begin();

            // Lưu đối tượng BaiThi vào cơ sở dữ liệu
            em.persist(baiThi);

            // Commit giao dịch
            transaction.commit();
            System.out.println("Đã thêm bài thi vào cơ sở dữ liệu: " + baiThi.getTenBaiThi());
        } catch (RuntimeException e) {
            // Nếu có lỗi, rollback giao dịch
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Có lỗi xảy ra: " + e.getMessage());
        } finally {
            // Đóng EntityManager
            em.close();
        }

    }
}
