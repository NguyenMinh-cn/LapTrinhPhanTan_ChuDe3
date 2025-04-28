package service.impl;

import daos.LopDAO;
import entities.Lop;
import service.LopService;

import java.rmi.RemoteException;
import java.util.List;

public class LopServiceImpl extends GenericServiceImpl<Lop, Integer> implements LopService {
    private LopDAO lopDAO;

    public LopServiceImpl(LopDAO lopDAO) throws RemoteException {
        super(lopDAO);
        this.lopDAO = lopDAO;
    }

    @Override
    public List<Lop> getAllLop() throws RemoteException {
        return lopDAO.getAllLop();
    }


}