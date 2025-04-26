package rmi;

import daos.*;
import entities.*;
import service.*;
import service.impl.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws Exception{
        Context context = new InitialContext();
        LocateRegistry.createRegistry(8081);

        BaiThiDAO baiThiDAO = new BaiThiDAO(BaiThi.class);
        BaiThiService baiThiService = new BaiThiServiceImpl(baiThiDAO);
        context.bind("rmi://localhost:8081/baiThiService", baiThiService);

        CauHoiDAO cauHoiDAO = new CauHoiDAO(CauHoi.class);
        CauHoiService cauHoiService = new CauHoiServiceImpl(cauHoiDAO);
        context.bind("rmi://localhost:8081/cauHoiService", cauHoiService);

        CauTraLoiDAO cauTraLoiDAO = new CauTraLoiDAO(CauTraLoi.class);
        CauTraLoiService cauTraLoiService = new CauTraLoiServiceImpl(cauTraLoiDAO);
        context.bind("rmi://localhost:8081/cauTraLoiService", cauTraLoiService);

        ChuDeDAO chuDeDAO = new ChuDeDAO(ChuDe.class);
        ChuDeService chuDeService = new ChuDeServiceImpl(chuDeDAO);
        context.bind("rmi://localhost:8081/chuDeService", chuDeService);

        GiaoVienDAO giaoVienDAO = new GiaoVienDAO(GiaoVien.class);
        GiaoVienService giaoVienService = new GiaoVienServiceImpl(giaoVienDAO);
        context.bind("rmi://localhost:8081/giaoVienService", giaoVienService);

        HocSinhDAO hocSinhDAO = new HocSinhDAO(HocSinh.class);
        HocSinhService hocSinhService = new HocSinhServiceImpl(hocSinhDAO);
        context.bind("rmi://localhost:8081/hocSinhService", hocSinhService);

        LopDAO lopDAO = new LopDAO(Lop.class);
        LopService lopService = new LopServiceImpl(lopDAO);
        context.bind("rmi://localhost:8081/lopService", lopService);

        MonHocDAO  monHocDAO = new MonHocDAO(MonHoc.class);
        MonHocService monHocService = new MonHocServiceImpl(monHocDAO);
        context.bind("rmi://localhost:8081/monHocService", monHocService);

        PhienLamBaiDAO phienLamBaiDAO = new PhienLamBaiDAO(PhienLamBai.class);
        PhienLamBaiService phienLamBaiService = new PhienLamBaiServiceImpl(phienLamBaiDAO);
        context.bind("rmi://localhost:8081/phienLamBaiService", phienLamBaiService);

        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(TaiKhoan.class);
        TaiKhoanService taiKhoanService = new TaiKhoanServiceImpl(taiKhoanDAO);
        context.bind("rmi://localhost:8081/taiKhoanService", taiKhoanService);

        System.out.println("RMI Server is running...");
    }
}
