package rmi;

import daos.GiaoVienDAO;
import daos.HocSinhDAO;
import daos.MonHocDAO;
import daos.TaiKhoanDAO;
import entities.GiaoVien;
import entities.HocSinh;
import entities.MonHoc;
import entities.TaiKhoan;
import service.GiaoVienService;
import service.HocSinhService;
import service.MonHocService;
import service.TaiKhoanService;
import service.impl.GiaoVienServiceImpl;
import service.impl.HocSinhServiceImpl;
import service.impl.MonHocServiceImpl;
import service.impl.TaiKhoanServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public static void main(String[] args) throws Exception{
        Context context = new InitialContext();
        LocateRegistry.createRegistry(9090);
        MonHocDAO  monHocDAO = new MonHocDAO(MonHoc.class);
        MonHocService monHocService = new MonHocServiceImpl(monHocDAO);
        context.bind("rmi://localhost:9090/monHocService", monHocService);

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(TaiKhoan.class);
        TaiKhoanService taiKhoanService = new TaiKhoanServiceImpl(taiKhoanDAO);
        context.bind("rmi://localhost:9090/taiKhoanService", taiKhoanService);

        // Tạo và đăng ký service cho HocSinh
        HocSinhDAO hocSinhDAO = new HocSinhDAO(HocSinh.class);
        HocSinhService hocSinhService = new HocSinhServiceImpl(hocSinhDAO);
        context.bind("rmi://localhost:9090/hocSinhService", hocSinhService);

        GiaoVienDAO giaoVienDAO = new GiaoVienDAO(GiaoVien.class);
        GiaoVienService giaoVienService = new GiaoVienServiceImpl(giaoVienDAO);
        context.bind("rmi://localhost:9090/giaoVienService", giaoVienService);


        System.out.println("RMI Server is running...");
    }
}
