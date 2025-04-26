package service.impl;


import daos.GenericDAO;
import service.GenericService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public abstract class GenericServiceImpl<T, ID> extends UnicastRemoteObject implements GenericService<T, ID> {

    protected GenericDAO<T, ID> genericDAO;

    public GenericServiceImpl(GenericDAO<T, ID> genericDAO) throws RemoteException{
        this.genericDAO = genericDAO;
    }

    @Override
    public T finByID(ID id) throws RemoteException {

        return genericDAO.findByID(id);
    }

    @Override
    public boolean save(T t) throws RemoteException {
        return genericDAO.save(t);
    }

    @Override
    public List<T> getAll() throws RemoteException {
        return genericDAO.getAll();
    }

    @Override
    public boolean update(T t) throws RemoteException {
        return genericDAO.update(t);
    }

    @Override
    public boolean delete(ID id) throws RemoteException {
        return genericDAO.delete(id);
    }
}
