package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import entities.BaiThi;
import net.datafaker.Faker;

import java.time.LocalDateTime;
public class Runner {
    public static void main(String[] args) {
        try {
            EntityManager em = Persistence.createEntityManagerFactory("mariadb")
                    .createEntityManager();
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo EntityManager: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
