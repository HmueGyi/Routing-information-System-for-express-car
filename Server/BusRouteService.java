import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface BusRouteService extends Remote {
    void addRoute(String source, String destination, String busName, String carName, String stops, String departureTime, String arrivalTime, String travelDate, String phNo, double costPerSeat) throws RemoteException;
   // void updateRoute(int id, String source, String destination, String busName, String carName, String stops, String departureTime, String arrivalTime, Date travelDate, String phNo, double costPerSeat) throws RemoteException;
    void deleteRoute(int id) throws RemoteException;
    List<String> getDetailedRoute(String source, String destination) throws RemoteException;
    List<String> getAllRoutes() throws RemoteException;
    List<String> getRoutes(String source, String destination) throws RemoteException;
    List<String> getRoutesForCustomer(String source, String destination, String date) throws RemoteException;
    List<String> getRoutesForCustomerNodate(String source, String destination) throws RemoteException;
    List<String> getRoutesByCarName(String carName) throws RemoteException;
    List<String> getRoutesByCarBName(String carBName) throws RemoteException;
	List<String> getRouteById(int id) throws RemoteException;
	void updateRoute(int id, String source, String destination, String busName, String carName, String stops,
			String departureTime, String arrivalTime, String travelDate, String phNo, double costPerSeat)
			throws RemoteException;
}

