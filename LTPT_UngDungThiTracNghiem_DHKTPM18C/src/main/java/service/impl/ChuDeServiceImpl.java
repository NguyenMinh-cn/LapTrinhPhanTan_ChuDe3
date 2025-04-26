package service.impl;

import daos.ChuDeDAO;
import entities.ChuDe;
import service.ChuDeService;

import java.rmi.RemoteException;

public class ChuDeServiceImpl extends GenericServiceImpl<ChuDe, Integer> implements ChuDeService {
    private ChuDeDAO chuDeDAO;

    public ChuDeServiceImpl(ChuDeDAO chuDeDAO) throws RemoteException {
        super(chuDeDAO);
        this.chuDeDAO = chuDeDAO;
    }
}
