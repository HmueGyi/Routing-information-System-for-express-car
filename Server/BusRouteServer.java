import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class BusRouteServer {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on port 2096
            LocateRegistry.createRegistry(2096);

            // Create an instance of the service implementation
            BusRouteService busRouteService = new BusRouteServiceImpl();

            // Bind the service to the RMI registry with the correct name and port
            Naming.rebind("//192.168.100.1:2096/BusRouteService", busRouteService);

            System.out.println("BusRouteService is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}