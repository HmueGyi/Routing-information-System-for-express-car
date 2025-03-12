import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class BusRouteServiceImpl extends UnicastRemoteObject implements BusRouteService {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/bus_routedb?useUnicode=true&characterEncoding=utf8";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";
    private static final Logger logger = Logger.getLogger(BusRouteServiceImpl.class.getName());

    protected BusRouteServiceImpl() throws RemoteException {
        super();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Create or modify bus_routes table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS bus_routes ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "source VARCHAR(255) NOT NULL, "
                    + "destination VARCHAR(255) NOT NULL, "
                    + "car_name VARCHAR(255), "
                    + "car_bname VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci, "
                    + "stops VARCHAR(255) NOT NULL, "
                    + "departure_time TIME NOT NULL, "
                    + "arrival_time TIME NOT NULL, "
                    + "travel_date DATE NOT NULL, "
                    + "cost_per_seat DECIMAL(10, 2) NOT NULL, "
                    + "Ph_No VARCHAR(255) NOT NULL, "
                    + "PRIMARY KEY (id))";
            conn.createStatement().execute(createTableSQL);

            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RemoteException("Error initializing database", e);
        }
    }

    @Override
    public void addRoute(String source, String destination, String busName, String carName, String stops, String departureTime, String arrivalTime, String travelDate, String Ph_No, double costPerSeat) throws RemoteException {
        String insertSQL = "INSERT INTO bus_routes (source, destination, car_name, car_bname, stops, departure_time, arrival_time, travel_date, Ph_No, cost_per_seat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            pstmt.setString(3, busName);
            pstmt.setString(4, carName);
            pstmt.setString(5, stops);
            pstmt.setString(6, departureTime);
            pstmt.setString(7, arrivalTime);
            pstmt.setString(8, travelDate);
            pstmt.setString(9, Ph_No);
            pstmt.setDouble(10, costPerSeat);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoteException("Error inserting data into database", e);
        }
    }

    @Override
    public void updateRoute(int id, String source, String destination, String busName, String carName, String stops, String departureTime, String arrivalTime, String travelDate, String phNo, double costPerSeat) throws RemoteException {
        String updateSQL = "UPDATE bus_routes SET source = ?, destination = ?, car_name = ?, car_bname = ?, stops = ?, departure_time = ?, arrival_time = ?, travel_date = ?, cost_per_seat = ?, Ph_No = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            pstmt.setString(3, busName);
            pstmt.setString(4, carName);
            pstmt.setString(5, stops);
            pstmt.setString(6, departureTime);
            pstmt.setString(7, arrivalTime);
            pstmt.setString(8, travelDate);
            pstmt.setDouble(9, costPerSeat);
            pstmt.setString(10, phNo);
            pstmt.setInt(11, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoteException("Error updating data in database", e);
        }
    }

    
    

    @Override
    public void deleteRoute(int id) throws RemoteException {
        String deleteSQL = "DELETE FROM bus_routes WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RemoteException("Error deleting data from database", e);
        }
    }

    @Override
    public List<String> getDetailedRoute(String source, String destination) throws RemoteException {
        List<String> routeDetails = new ArrayList<>();
        String query = "SELECT * FROM bus_routes WHERE source = ? AND destination = ? order by travel_date and car_name";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                routeDetails.add("ID: " + rs.getInt("id"));
                routeDetails.add("Bus Name: " + rs.getString("car_name"));
                routeDetails.add("Car Name: " + rs.getString("car_bname"));
                routeDetails.add("Stops: " + rs.getString("stops"));
                routeDetails.add("Departure Time: " + rs.getString("departure_time"));
                routeDetails.add("Arrival Time: " + rs.getString("arrival_time"));
                routeDetails.add("Travel Date: " + rs.getString("travel_date"));
                routeDetails.add("Cost per Seat: " + rs.getDouble("cost_per_seat"));
                routeDetails.add("Ph_No: " + rs.getString("Ph_No"));
            }
        } catch (SQLException e) {
            throw new RemoteException("Error querying database", e);
        }

        return routeDetails;
    }

    @Override
    public List<String> getAllRoutes() throws RemoteException {
        List<String> routes = new ArrayList<>();
        String query = "SELECT id, source, destination, car_name, car_bname, stops, departure_time, arrival_time, travel_date, cost_per_seat, Ph_No FROM bus_routes order by travel_date and car_name";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int routeId = rs.getInt("id");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                String busName = rs.getString("car_name");
                String carBname = rs.getString("car_bname");
                String stops = rs.getString("stops");
                String departureTime = rs.getString("departure_time");
                String arrivalTime = rs.getString("arrival_time");
                String travelDate = rs.getString("travel_date");
                double costPerSeat = rs.getDouble("cost_per_seat");
                String Ph_No = rs.getString("Ph_No");

                routes.add(String.format("%d, %s, %s, %s, %s, %s, %s, %s, %s, %.0f, %s",
                        routeId, source, destination, busName, carBname, stops, departureTime, arrivalTime, travelDate, costPerSeat, Ph_No));
            }
        } catch (SQLException e) {
            throw new RemoteException("Error querying database", e);
        }

        return routes;
    }

    @Override
    public List<String> getRoutes(String source, String destination) throws RemoteException {
        List<String> routes = new ArrayList<>();
        String query = "SELECT * FROM bus_routes WHERE source = ? AND destination = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int routeId = rs.getInt("id");
                String busName = rs.getString("car_name");
                String carName = rs.getString("car_bname");
                String stops = rs.getString("stops");
                String departureTime = rs.getString("departure_time");
                String arrivalTime = rs.getString("arrival_time");
                String travelDate = rs.getString("travel_date");
                double costPerSeat = rs.getDouble("cost_per_seat");
                String Ph_No = rs.getString("Ph_No");

                routes.add(String.format("ID: %d, Bus Name: %s, Car Name: %s, Stops: %s, Departure: %s, Arrival: %s, Date: %s, Cost per Seat: %.0f, Ph_No: %s",
                        routeId, busName, carName, stops, departureTime, arrivalTime, travelDate, costPerSeat, Ph_No));
            }
        } catch (SQLException e) {
            throw new RemoteException("Error querying database", e);
        }

        return routes;
    }
    
    
    
    public List<String> getRouteById(int id) throws RemoteException {
        List<String> routeDetails = new ArrayList<>();
        String query = "SELECT * FROM bus_routes WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    routeDetails.add("" + rs.getInt("id"));
                    routeDetails.add("" + rs.getString("source"));
                    routeDetails.add("" + rs.getString("destination"));
                    routeDetails.add("" + rs.getString("car_name"));
                    routeDetails.add("" + rs.getString("car_bname"));
                    routeDetails.add("" + rs.getString("stops"));
                    routeDetails.add("" + rs.getString("departure_time"));
                    routeDetails.add("" + rs.getString("arrival_time"));
                    routeDetails.add("" + rs.getString("travel_date"));
                    routeDetails.add("" + rs.getDouble("cost_per_seat"));
                    routeDetails.add("" + rs.getString("Ph_No"));
                }
            } catch (SQLException e) {
                logger.severe("SQL Exception while processing result set: " + e.getMessage());
                throw new RemoteException("Error processing query result", e);
            }
        } catch (SQLException e) {
            logger.severe("SQL Exception: " + e.getMessage());
            throw new RemoteException("Error querying database", e);
        }

        return routeDetails;
    }

    
    
    
    
    //user
    @Override
    public List<String> getRoutesForCustomer(String source, String destination, String travelDate) throws RemoteException {
        List<String> routes = new ArrayList<>();

        // Get the current local date and time
        LocalDate localDate = LocalDate.now();
        LocalTime currentTimeMinusOneHour = LocalTime.now().plusHours(2);

        String query;

        // Check if the travel date is the same as or before the local date
        if (LocalDate.parse(travelDate).isBefore(localDate)) {
            // If travel date is before the local date, return an empty list (no routes to show)
            return routes;
        } else if (LocalDate.parse(travelDate).isEqual(localDate)) {
            // If travel date is the same as local date, filter by current time minus 1 hour
            query = "SELECT car_name, car_bname, stops, departure_time, arrival_time, travel_date, cost_per_seat, Ph_No "
                  + "FROM bus_routes WHERE source = ? AND destination = ? AND travel_date = ? AND departure_time > ? "
                  + "ORDER BY travel_date, car_name";
        } else {
            // If travel date is in the future, no need to filter by time
            query = "SELECT car_name, car_bname, stops, departure_time, arrival_time, travel_date, cost_per_seat, Ph_No "
                  + "FROM bus_routes WHERE source = ? AND destination = ? AND travel_date = ? "
                  + "ORDER BY travel_date, car_name";
        }

        logger.info("Fetching routes for source: " + source + ", destination: " + destination + ", travel date: " + travelDate);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            pstmt.setString(3, travelDate);

            if (LocalDate.parse(travelDate).isEqual(localDate)) {
                pstmt.setString(4, currentTimeMinusOneHour.toString()); // Bind the local time minus 1 hour if travel date is today
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String carName = rs.getString("car_name");
                    String carBname = rs.getString("car_bname");
                    String stops = rs.getString("stops");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");
                    String date = rs.getString("travel_date");
                    double costPerSeat = rs.getDouble("cost_per_seat");
                    String phNo = rs.getString("Ph_No");

                    // Prepare the route string
                    String routeString = String.format("%s, %s, %s, %s, %s, %s, %.0f, %s",
                            carName, carBname, stops, departureTime, arrivalTime, date, costPerSeat, phNo);
                    routes.add(routeString);
                }
            } catch (SQLException e) {
                logger.severe("SQL Exception while processing result set: " + e.getMessage());
                throw new RemoteException("Error processing query result", e);
            }
        } catch (SQLException e) {
            logger.severe("SQL Exception: " + e.getMessage());
            throw new RemoteException("Error querying database", e);
        }

        return routes;
    }




    
    @Override
    public List<String> getRoutesForCustomerNodate(String source, String destination) throws RemoteException {
        List<String> routes = new ArrayList<>();
        
        String query = "SELECT car_name, car_bname, stops, departure_time, arrival_time, travel_date, cost_per_seat, Ph_No "
                     + "FROM bus_routes WHERE source = ? AND destination = ? ORDER BY travel_date, car_name";

        logger.info("Fetching routes for source: " + source + ", destination: " + destination);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, source);
            pstmt.setString(2, destination);

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now().plusHours(2);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String carName = rs.getString("car_name");
                    String carBname = rs.getString("car_bname");
                    String stops = rs.getString("stops");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");
                    String date = rs.getString("travel_date");
                    double costPerSeat = rs.getDouble("cost_per_seat");
                    String phNo = rs.getString("Ph_No");
                    
                    // Convert the travel date and departure time from the database
                    LocalDate travelDate = LocalDate.parse(date);
                    LocalTime depTime = LocalTime.parse(departureTime);

                    // If the travel date is today, ensure the departure time is not behind the local time (minus 1 hour)
                    if ( ( !travelDate.isBefore(currentDate)&&!travelDate.equals(currentDate) ||  depTime.isAfter(currentTime)    )) {
                             String routeString = String.format("%s, %s, %s, %s, %s, %s, %.0f, %s",
                                     carName, carBname, stops, departureTime, arrivalTime, date, costPerSeat, phNo);
                             routes.add(routeString);
                         }
                }
            } catch (SQLException e) {
                logger.severe("SQL Exception while processing result set: " + e.getMessage());
                throw new RemoteException("Error processing query result", e);
            }
        } catch (SQLException e) {
            logger.severe("SQL Exception: " + e.getMessage());
            throw new RemoteException("Error querying database", e);
        }

        return routes;
    }


    
    public List<String> getRoutesByCarName(String carname) throws RemoteException {
    	 List<String> routes = new ArrayList<>();
         String query = "SELECT car_name, car_bname, stops, departure_time, arrival_time, travel_date, cost_per_seat, Ph_No "
                      + "FROM bus_routes WHERE car_name = ? order by travel_date,car_name ";

         logger.info("Fetching routes for source: " + carname );

         try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
              PreparedStatement pstmt = conn.prepareStatement(query)) {

             pstmt.setString(1, carname);

             LocalDate currentDate = LocalDate.now();
             LocalTime currentTime = LocalTime.now().plusHours(2);
             
             try (ResultSet rs = pstmt.executeQuery()) {
                 while (rs.next()) {
                     String carName = rs.getString("car_name");
                     String carBname = rs.getString("car_bname");
                     String stops = rs.getString("stops");
                     String departureTime = rs.getString("departure_time");
                     String arrivalTime = rs.getString("arrival_time");
                     String date = rs.getString("travel_date");
                     double costPerSeat = rs.getDouble("cost_per_seat");
                     String phNo = rs.getString("Ph_No");

                     // Convert the travel date and departure time from the database
                     LocalDate travelDate = LocalDate.parse(date);
                     LocalTime depTime = LocalTime.parse(departureTime);

                     // If the travel date is today, ensure the departure time is not behind the local time (minus 1 hour)
                     if ( ( !travelDate.isBefore(currentDate)&&!travelDate.equals(currentDate) ||  depTime.isAfter(currentTime)    )) {
                              String routeString = String.format("%s, %s, %s, %s, %s, %s, %.0f, %s",
                                      carName, carBname, stops, departureTime, arrivalTime, date, costPerSeat, phNo);
                              routes.add(routeString);
                          }
                 }
             } catch (SQLException e) {
                 logger.severe("SQL Exception while processing result set: " + e.getMessage());
                 throw new RemoteException("Error processing query result", e);
             }
         } catch (SQLException e) {
             logger.severe("SQL Exception: " + e.getMessage());
             throw new RemoteException("Error querying database", e);
         }

         return routes;
     }
    
    public List<String> getRoutesByCarBName(String carbname) throws RemoteException {
   	 List<String> routes = new ArrayList<>();
        String query = "SELECT car_name, car_bname, stops, departure_time, arrival_time, travel_date, cost_per_seat, Ph_No "
                     + "FROM bus_routes WHERE car_bname = ? order by travel_date,car_name";

        logger.info("Fetching routes for source: " + carbname );

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, carbname);

            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now().plusHours(2);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String carName = rs.getString("car_name");
                    String carBname = rs.getString("car_bname");
                    String stops = rs.getString("stops");
                    String departureTime = rs.getString("departure_time");
                    String arrivalTime = rs.getString("arrival_time");
                    String date = rs.getString("travel_date");
                    double costPerSeat = rs.getDouble("cost_per_seat");
                    String phNo = rs.getString("Ph_No");

                    // Convert the travel date and departure time from the database
                    LocalDate travelDate = LocalDate.parse(date);
                    LocalTime depTime = LocalTime.parse(departureTime);

                    // If the travel date is today, ensure the departure time is not behind the local time (minus 1 hour)
                    if ( ( !travelDate.isBefore(currentDate)&&!travelDate.equals(currentDate) ||  depTime.isAfter(currentTime)    )) {
                             String routeString = String.format("%s, %s, %s, %s, %s, %s, %.0f, %s",
                                     carName, carBname, stops, departureTime, arrivalTime, date, costPerSeat, phNo);
                             routes.add(routeString);
                         }
                }
            } catch (SQLException e) {
                logger.severe("SQL Exception while processing result set: " + e.getMessage());
                throw new RemoteException("Error processing query result", e);
            }
        } catch (SQLException e) {
            logger.severe("SQL Exception: " + e.getMessage());
            throw new RemoteException("Error querying database", e);
        }

        return routes;
    }

	
}
