package service.impl;

import daos.HocSinhDAO;
import entities.HocSinh;
import service.HocSinhService;

import java.rmi.RemoteException;

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
}