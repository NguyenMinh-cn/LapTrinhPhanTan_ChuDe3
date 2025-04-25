package service.impl;

import daos.BaiThiDAO;
import daos.CauHoiDAO;
import daos.GenericDAO;
import entities.BaiThi;
import entities.CauHoi;
import service.BaiThiService;
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

}
