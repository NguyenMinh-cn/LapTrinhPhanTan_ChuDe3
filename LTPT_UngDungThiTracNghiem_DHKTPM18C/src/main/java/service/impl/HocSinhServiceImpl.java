package service.impl;

import daos.HocSinhDAO;
import entities.HocSinh;
import service.HocSinhService;

import java.rmi.RemoteException;
import java.util.List;

public class HocSinhServiceImpl extends GenericServiceImpl<HocSinh, Long> implements HocSinhService {
    private HocSinhDAO hocSinhDAO;
    public HocSinhServiceImpl(HocSinhDAO hocSinhDAO) throws RemoteException {
        super(hocSinhDAO);
        this.hocSinhDAO = hocSinhDAO;
    }
    @Override
    public HocSinh timHocSinhTheoEmail(String email) throws RemoteException {
        return hocSinhDAO.timHocSinhTheoEmail(email);
    }

    @Override
    public List<HocSinh> findHocSinhChuaCoLop() throws RemoteException {
        return hocSinhDAO.findHocSinhChuaCoLop();
    }
}