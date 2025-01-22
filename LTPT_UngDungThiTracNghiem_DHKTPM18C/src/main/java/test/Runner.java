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
            System.out.println("Tạo bảng thành công");

            InsertMonHoc insertMonHoc = new InsertMonHoc();
            insertMonHoc.themDSMonHoc();

            InsertLopHoc insertLopHoc = new InsertLopHoc();
            insertLopHoc.themDSLopHoc();

            InsertHocSinh insertHocSinh = new InsertHocSinh();
            insertHocSinh.themDSHocSinh();

            InsertGiaoVien insertGiaoVien = new InsertGiaoVien();
            insertGiaoVien.themDSGiaoVien();

            InsertCauHoi insertCauHoi = new InsertCauHoi();
            insertCauHoi.themDSCauHoi();

            InsertBaiThi insertBaiThi = new InsertBaiThi();
            insertBaiThi.themDSBaiThi();

            InsertPhienLamBai insertPhienLamBai = new InsertPhienLamBai();
            insertPhienLamBai.themDSPhienLamBai();

            InsertCauTraLoi insertCauTraLoi = new InsertCauTraLoi();
            insertCauTraLoi.themDSCauTraLoi();

            InsertKetQua insertKetQua = new InsertKetQua();
            insertKetQua.themDSKetQua();
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo EntityManager: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
