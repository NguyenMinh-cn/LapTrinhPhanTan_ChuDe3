package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GenericService<T, ID> extends Remote {

    T finByID(ID id) throws RemoteException;
    boolean save(T t) throws RemoteException;
    List<T> getAll() throws RemoteException;
    boolean update(T t) throws RemoteException;
    boolean delete(ID id) throws RemoteException;

}
