package service.impl;

import daos.CauTraLoiDAO;
import entities.CauTraLoi;
import service.CauTraLoiService;

import java.rmi.RemoteException;
import java.util.List;


public class CauTraLoiServiceImpl extends GenericServiceImpl<CauTraLoi, Integer> implements CauTraLoiService {
    private CauTraLoiDAO cauTraLoiDAO;

    public CauTraLoiServiceImpl(CauTraLoiDAO cauTraLoiDAO) throws RemoteException {
        super(cauTraLoiDAO);
        this.cauTraLoiDAO = cauTraLoiDAO;
    }

    @Override
    public List<CauTraLoi> getCauTraLoiByPhienLamBai(String maPhienLamBai) throws RemoteException {
        return cauTraLoiDAO.findByPhienLamBai(maPhienLamBai);
    }
}