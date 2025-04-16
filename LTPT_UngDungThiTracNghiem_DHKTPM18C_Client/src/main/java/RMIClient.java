import service.MonHocService;

import java.rmi.Naming;

public class RMIClient {

    public static void main(String[] args) throws Exception {
        MonHocService  monHocService = (MonHocService) Naming.lookup("rmi://localhost:9090/monHocService");
        monHocService.getAll()
                .forEach(monHoc -> System.out.println(monHoc));

    }
}
