package service.impl;

import daos.HocSinhDAO;
import entities.HocSinh;
import service.HocSinhService;

import java.rmi.RemoteException;
import java.util.List;

public class HocSinhServiceImpl extends GenericServiceImpl<HocSinh, Integer> implements HocSinhService {
    private HocSinhDAO hocSinhDAO;
    public HocSinhServiceImpl(HocSinhDAO hocSinhDAO) throws RemoteException {
        super(hocSinhDAO);
        this.hocSinhDAO = hocSinhDAO;
    }
    @Override
    public HocSinh timHocSinhTheoEmail(String email) throws RemoteException {
        return hocSinhDAO.timHocSinhTheoEmail(email);
    }


    public boolean update(HocSinh hocSinh) throws RemoteException {
        return hocSinhDAO.update(hocSinh);
    }

    @Override
    public boolean delete(int maHocSinh) throws RemoteException {
        return false;
    }


    public List<HocSinh> getAll() throws RemoteException {
        return hocSinhDAO.getAll();
    }

}