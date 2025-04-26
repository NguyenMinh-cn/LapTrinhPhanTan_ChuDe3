package service.impl;

import daos.PhienLamBaiDAO;
import entities.PhienLamBai;
import service.PhienLamBaiService;

import java.rmi.RemoteException;

public class PhienLamBaiServiceImpl extends GenericServiceImpl<PhienLamBai, String> implements PhienLamBaiService {
    private PhienLamBaiDAO phienLamBaiDAO;

    public PhienLamBaiServiceImpl(PhienLamBaiDAO phienLamBaiDAO) throws RemoteException {
        super(phienLamBaiDAO);
        this.phienLamBaiDAO = phienLamBaiDAO;
    }
}
