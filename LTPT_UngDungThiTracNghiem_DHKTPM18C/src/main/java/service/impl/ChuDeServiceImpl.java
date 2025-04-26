package service.impl;

import daos.ChuDeDAO;
import entities.ChuDe;
import jakarta.persistence.EntityManager;
import service.ChuDeService;

import java.rmi.RemoteException;
import java.util.List;

public class ChuDeServiceImpl extends GenericServiceImpl<ChuDe, Integer> implements ChuDeService {
    private ChuDeDAO chuDeDAO;
    private EntityManager em;
    public ChuDeServiceImpl(ChuDeDAO chuDeDAO) throws RemoteException {
        super(chuDeDAO);
        this.chuDeDAO = chuDeDAO;
    }


    @Override
    public ChuDe findByTenMonHocAndTenChuDe(String tenMon, String tenChuDe) throws RemoteException {
        return chuDeDAO.findByTenMonHocAndTenChuDe(tenMon, tenChuDe);
    }

    @Override
    public List<ChuDe> findByTenMonHoc(String tenMon) throws RemoteException {
        return chuDeDAO.findByTenMonHoc(tenMon);
    }

    @Override
    public boolean hasCauHoi(int maChuDe) throws RemoteException {
        return chuDeDAO.hasCauHoi(maChuDe);
    }

    @Override
    public boolean isDuplicate(String tenChuDe, String tenMon) throws RemoteException {
        return chuDeDAO.isDuplicate(tenChuDe, tenMon);
    }
}
