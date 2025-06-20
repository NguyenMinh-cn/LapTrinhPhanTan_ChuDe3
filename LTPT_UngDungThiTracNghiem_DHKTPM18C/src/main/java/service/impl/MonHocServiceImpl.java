package service.impl;

import daos.MonHocDAO;
import entities.MonHoc;
import service.MonHocService;


import java.rmi.RemoteException;

public class MonHocServiceImpl extends GenericServiceImpl<MonHoc, Integer> implements MonHocService {
    private MonHocDAO monHocDAO;


    public MonHocServiceImpl(MonHocDAO monHocDAO) throws RemoteException {
        super(monHocDAO);
        this.monHocDAO = monHocDAO;
    }

    @Override
    public MonHoc findByTenMon(String tenMon) throws RemoteException {
        return monHocDAO.findByTenMon(tenMon);
    }

    @Override
    public boolean update(MonHoc monHoc) throws RemoteException {
        return monHocDAO.update(monHoc);
    }

    @Override
    public boolean delete(int maMon) throws RemoteException {
        return monHocDAO.delete(maMon);
    }
}