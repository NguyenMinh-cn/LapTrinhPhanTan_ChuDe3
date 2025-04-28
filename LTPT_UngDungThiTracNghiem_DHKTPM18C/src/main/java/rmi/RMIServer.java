package rmi;

import daos.*;
import entities.*;
import service.*;
import service.impl.*;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        LocateRegistry.createRegistry(8081);

        try {
            MonHocDAO monHocDAO = new MonHocDAO(MonHoc.class);
            MonHocService monHocService = new MonHocServiceImpl(monHocDAO);
            Naming.rebind("rmi://localhost:8081/monHocService", monHocService);
            System.out.println("Dịch vụ RMI đã sẵn sàng trên port 8081.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi khởi tạo dịch vụ RMI: " + e.getMessage());
        }


//        MonHocDAO monHocDAO = new MonHocDAO(MonHoc.class);
//        MonHocService monHocService = new MonHocServiceImpl(monHocDAO);
//        Naming.rebind("rmi://localhost:8081/monHocService", monHocService);

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(TaiKhoan.class);
        TaiKhoanService taiKhoanService = new TaiKhoanServiceImpl(taiKhoanDAO);
        Naming.rebind("rmi://localhost:8081/taiKhoanService", taiKhoanService);

        HocSinhDAO hocSinhDAO = new HocSinhDAO(HocSinh.class);
        HocSinhService hocSinhService = new HocSinhServiceImpl(hocSinhDAO);
        Naming.rebind("rmi://localhost:8081/hocSinhService", hocSinhService);

        LopDAO lopDAO = new LopDAO(Lop.class);
        LopService lopService = new LopServiceImpl(lopDAO);
        Naming.rebind("rmi://localhost:8081/lopService", lopService);

        GiaoVienDAO giaoVienDAO = new GiaoVienDAO(GiaoVien.class);
        GiaoVienService giaoVienService = new GiaoVienServiceImpl(giaoVienDAO);
        Naming.rebind("rmi://localhost:8081/lopService", giaoVienService);

        System.out.println("RMI Server is running...");
    }
}
