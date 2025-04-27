package service.impl;

import daos.BaiThiDAO;
import daos.GiaoVienDAO;
import entities.BaiThi;
import service.BaiThiService;

import java.rmi.RemoteException;
import java.util.List;

public class BaiThiServiceImpl extends GenericServiceImpl<BaiThi, Integer> implements BaiThiService {
    private BaiThiDAO baiThiDAO;

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

}
