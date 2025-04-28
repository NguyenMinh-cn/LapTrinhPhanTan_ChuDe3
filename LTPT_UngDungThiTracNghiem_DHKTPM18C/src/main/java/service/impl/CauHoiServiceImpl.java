package service.impl;

import daos.CauHoiDAO;
import entities.CauHoi;
import jakarta.transaction.Transactional;
import service.CauHoiService;

import java.rmi.RemoteException;
import java.util.List;

public class CauHoiServiceImpl extends GenericServiceImpl<CauHoi, Integer> implements CauHoiService {
    private CauHoiDAO cauHoiDAO;
    public CauHoiServiceImpl(CauHoiDAO cauHoiDAO) throws RemoteException {
        super(cauHoiDAO);
        this.cauHoiDAO = cauHoiDAO;
    }

    @Override
    public List<CauHoi> timCauHoiTheoMaBaiThi(int maBaiThi) throws RemoteException {
        return cauHoiDAO.timCauHoiTheoMaBaiThi(maBaiThi);
    }

    @Override
    public List<String> timDSDapAnTheoCauHoi(int maCH) throws RemoteException {
        return cauHoiDAO.timDSDapAnTheoCauHoi(maCH);
    }

    @Override
    public CauHoi luuVaTraVeMa(CauHoi cauHoi) throws RemoteException {
        return cauHoiDAO.luuVaTraVeMa(cauHoi);
    }
    @Override
    public List<CauHoi> luuNhieuVaTraVeMa(List<CauHoi> danhSachCauHoi) throws RemoteException{
        return cauHoiDAO.luuNhieuVaTraVeMa(danhSachCauHoi);
    }
    public boolean inBaiThi(int maCauHoi) throws RemoteException {
        return cauHoiDAO.inBaiThi(maCauHoi);
    }

    @Override
    public boolean inBaiThiDaDienRa(int maCauHoi) throws RemoteException {
        return cauHoiDAO.inBaiThiDaDienRa(maCauHoi);
    }

    @Override
    public List<CauHoi> getCauHoiCoChuDe() throws RemoteException {
        return cauHoiDAO.getCauHoiCoChuDe();
    }

    @Override
    public List<CauHoi> findByMon(String tenMon) throws RemoteException {
        return cauHoiDAO.findByMon(tenMon);
    }
}
