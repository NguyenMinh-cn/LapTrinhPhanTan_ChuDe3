package service.impl;

import daos.PhienLamBaiDAO;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import java.rmi.RemoteException;
import java.util.List;

public class PhienLamBaiServiceImpl extends GenericServiceImpl<PhienLamBai, String> implements PhienLamBaiService {
    private PhienLamBaiDAO phienLamBaiDAO;

    public PhienLamBaiServiceImpl(PhienLamBaiDAO phienLamBaiDAO) throws RemoteException {
        super(phienLamBaiDAO);
        this.phienLamBaiDAO = phienLamBaiDAO;
    }

    @Override
    public List<Object[]> layKetQuaChiTietPhienLamBai(String maPhienLamBai) throws RemoteException {
        return phienLamBaiDAO.layKetQuaChiTietPhienLamBai(maPhienLamBai);
    }

    @Override
    public Object[] tinhDiemVaSoCau(String maPhienLamBai) throws RemoteException {
        return phienLamBaiDAO.tinhDiemVaSoCau(maPhienLamBai);
    }

    @Override
    public List<PhienLamBai> findByMaHocSinh(long maHocSinh) throws RemoteException {
        return phienLamBaiDAO.findByMaHocSinh(maHocSinh);
    }

    @Override
    public PhienLamBai layThongTinChiTietPhienLamBai(String maPhienLamBai) throws RemoteException {
        return phienLamBaiDAO.layThongTinChiTietPhienLamBai(maPhienLamBai);
    }

    @Override
    public PhienLamBai layThongTinPhienLamBaiVaCauTraLoi(String maPhienLamBai) throws RemoteException {
        return phienLamBaiDAO.layThongTinPhienLamBaiVaCauTraLoi(maPhienLamBai);
    }

    @Override
    public List<PhienLamBai> layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(int maBaiThi) throws RemoteException {
        return phienLamBaiDAO.layDanhSachPhienLamBaiVaCauTraLoiTheoBaiThi(maBaiThi);
    }
}
