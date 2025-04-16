package rmi;

import daos.MonHocDAO;
import entities.MonHoc;
import service.MonHocService;
import service.impl.MonHocServiceImpl;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    public static void main(String[] args) throws Exception{

        Context context = new InitialContext();
        LocateRegistry.createRegistry(9090);
        MonHocDAO  monHocDAO = new MonHocDAO(MonHoc.class);
        MonHocService monHocService = new MonHocServiceImpl(monHocDAO);
        context.bind("rmi://localhost:9090/monHocService", monHocService);

        System.out.println("RMI Server is running...");
    }
}
