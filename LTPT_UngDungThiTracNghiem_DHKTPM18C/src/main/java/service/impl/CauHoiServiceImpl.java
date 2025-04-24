package service.impl;

import daos.CauHoiDAO;
import entities.CauHoi;
import service.CauHoiService;

import java.rmi.RemoteException;

public class CauHoiServiceImpl extends GenericServiceImpl<CauHoi, Integer> implements CauHoiService {
    private CauHoiDAO cauHoiDAO;
    public CauHoiServiceImpl(CauHoiDAO cauHoiDAO) throws RemoteException {
        super(cauHoiDAO);
        this.cauHoiDAO = cauHoiDAO;
    }
}
