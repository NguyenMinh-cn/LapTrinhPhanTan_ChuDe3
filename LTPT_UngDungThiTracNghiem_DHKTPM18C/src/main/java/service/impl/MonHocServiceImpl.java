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


}
