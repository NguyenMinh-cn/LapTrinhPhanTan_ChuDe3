package service.impl;

import daos.MonHocDAO;
import entities.MonHoc;
import service.MonHocService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class MonHocServiceImpl extends UnicastRemoteObject implements MonHocService {
    private MonHocDAO monHocDAO;

    public MonHocServiceImpl(MonHocDAO monHocDAO) throws RemoteException {
        this.monHocDAO = monHocDAO;
    }

    @Override
    public boolean save(MonHoc monHoc) throws RemoteException {
        return monHocDAO.save(monHoc);
    }

    @Override
    public boolean update(MonHoc monHoc) throws RemoteException {
        return monHocDAO.update(monHoc);
    }

    @Override
    public boolean delete(int maMon) throws RemoteException {
        return monHocDAO.delete(maMon);
    }

    @Override
    public List<MonHoc> getAllMonHoc() throws RemoteException {
        return monHocDAO.getAllMonHoc();
    }

    @Override
    public MonHoc findById(int maMon) throws RemoteException {
        return monHocDAO.findById(maMon);
    }
}
