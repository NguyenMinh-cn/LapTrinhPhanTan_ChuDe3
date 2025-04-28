package service.impl;

import daos.CauHoiDAO;
import entities.CauHoi;
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

}
