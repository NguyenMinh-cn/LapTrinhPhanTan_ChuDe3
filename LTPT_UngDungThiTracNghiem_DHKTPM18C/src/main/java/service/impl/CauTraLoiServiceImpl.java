package service.impl;

import daos.CauTraLoiDAO;
import daos.GenericDAO;
import entities.CauTraLoi;
import service.CauTraLoiService;

import java.rmi.RemoteException;

public class CauTraLoiServiceImpl extends GenericServiceImpl<CauTraLoi, Integer> implements CauTraLoiService {
    private CauTraLoiDAO cauTraLoiDAO;


    public CauTraLoiServiceImpl(CauTraLoiDAO cauTraLoiDAO) throws RemoteException {
        super(cauTraLoiDAO);
        this.cauTraLoiDAO = cauTraLoiDAO;
    }
}
