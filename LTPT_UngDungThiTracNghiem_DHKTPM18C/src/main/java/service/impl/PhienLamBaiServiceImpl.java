package service.impl;

import daos.PhienLamBaiDAO;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import java.rmi.RemoteException;
import java.util.List;

public class PhienLamBaiServiceImpl implements PhienLamBaiService {
    private PhienLamBaiDAO phienLamBaiDAO;

    public PhienLamBaiServiceImpl(PhienLamBaiDAO phienLamBaiDAO) {
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
    public List<PhienLamBai> findByMaHocSinh(String maHocSinh) throws RemoteException {
        return phienLamBaiDAO.findByMaHocSinh(maHocSinh);
    }
}
