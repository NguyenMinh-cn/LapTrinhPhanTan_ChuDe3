package test;

import entities.CauHoi;
import entities.GiaoVien;
import entities.MonHoc;
import entities.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class InsertGiaoVien {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            emf = Persistence.createEntityManagerFactory("mariadb");
            em = emf.createEntityManager();
            Faker faker = new Faker();

            List<MonHoc> monHocList = em.createQuery("SELECT m FROM MonHoc m", MonHoc.class).getResultList();
            System.out.println("Danh sách môn học:");
            for (MonHoc monHoc : monHocList) {
                System.out.println("Mã môn: " + monHoc.getMaMon() + ", Tên môn: " + monHoc.getTenMon());
            }

            em.getTransaction().begin();
            for (int i = 0; i < 10; i++) {
                GiaoVien giaoVien = new GiaoVien();
                giaoVien.setHoTen(faker.name().fullName());
                giaoVien.setEmail(faker.internet().emailAddress());
                giaoVien.setSoDienThoai(faker.phoneNumber().subscriberNumber(10));

                TaiKhoan taiKhoan = new TaiKhoan();
                taiKhoan.setTenDangNhap(giaoVien.getEmail());
                taiKhoan.setMatKhau(faker.internet().password());
                taiKhoan.setLoaiTaiKhoan("giaovien");

                giaoVien.setTaiKhoan(taiKhoan);

                List<MonHoc> monHocDangDay = new ArrayList<>();
                monHocDangDay.add(monHocList.get(i % monHocList.size()));
                giaoVien.setMonHocGiaoVienPhuTrach(monHocDangDay);

                em.persist(taiKhoan);
                em.persist(giaoVien);
            }

            em.getTransaction().commit();
            System.out.println("Dữ liệu đã được ghi thành công!");

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
