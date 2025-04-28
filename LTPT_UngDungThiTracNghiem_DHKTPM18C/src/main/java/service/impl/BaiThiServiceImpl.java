package service.impl;

import daos.BaiThiDAO;
import daos.GiaoVienDAO;
import entities.BaiThi;
import entities.Lop;
import service.BaiThiCallback;
import service.BaiThiService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class BaiThiServiceImpl extends GenericServiceImpl<BaiThi, Integer> implements BaiThiService {
    private BaiThiDAO baiThiDAO;
    private final List<BaiThiCallback> danhSachClientDangKy = new ArrayList<>();

    public BaiThiServiceImpl(BaiThiDAO baiThiDAO) throws RemoteException {
        super(baiThiDAO);
        this.baiThiDAO = baiThiDAO;
    }

    @Override
    public List<BaiThi> timDSBaiTHiTheoMaGiaoVien(int maGiaoVien) throws RemoteException {
        return baiThiDAO.timDSBaiTHiTheoMaGiaoVien(maGiaoVien);
    }

    @Override
    public BaiThi layThongTinChiTietBaiThi(int maBaiThi) throws RemoteException {
        return baiThiDAO.layThongTinChiTietBaiThi(maBaiThi);
    }

    @Override
    public boolean delete(int maBaiThi) throws RemoteException {
        return baiThiDAO.delete(maBaiThi);
    }

    @Override
    public List<BaiThi> getAllBaiThiForHocSinh(Long maHocSinh) throws RemoteException {
        return baiThiDAO.getAllBaiThiForHocSinh(maHocSinh);
    }

    @Override
    public BaiThi layThongTinBaiThiVaCauHoi(int maBaiThi) throws RemoteException {
        return baiThiDAO.layThongTinBaiThiVaCauHoi(maBaiThi);
    }

    @Override
    public List<Lop> timLopTheoBaiThi(int maBaiThi) throws RemoteException {
        return baiThiDAO.timLopTheoBaiThi(maBaiThi);
    }

    @Override
    public boolean xoaBaiThiKhongCoPhienLamBaiTheoMaJPQL(int maBaiThi) throws RemoteException {
        return baiThiDAO.xoaBaiThiKhongCoPhienLamBaiTheoMaJPQL(maBaiThi);
    }
    @Override
    public void dangKyClientNhanThongBao(BaiThiCallback callback) throws RemoteException {
        if (!danhSachClientDangKy.contains(callback)) {
            danhSachClientDangKy.add(callback);
        }
    }
    @Override
    public void huyDangKyClientNhanThongBao(BaiThiCallback callback) throws RemoteException {
        danhSachClientDangKy.remove(callback);
    }
    @Override
    public void thongBaoBaiThiMoi(BaiThi baiThi) throws RemoteException {
        // Thông báo cho tất cả clients đã đăng ký
        List<BaiThiCallback> danhSachClientLoi = new ArrayList<>();

        for (BaiThiCallback callback : danhSachClientDangKy) {
            try {
                callback.nhanThongBaoBaiThiMoi(baiThi);
            } catch (RemoteException e) {
                // Nếu client không phản hồi, thêm vào danh sách để xóa
                danhSachClientLoi.add(callback);
            }
        }

        // Xóa các callbacks không hoạt động
        danhSachClientDangKy.removeAll(danhSachClientLoi);
    }
}
