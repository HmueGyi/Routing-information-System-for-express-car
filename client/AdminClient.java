import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DateFormatter;
import com.toedter.calendar.JDateChooser; // Import JDateChooser

public class AdminClient extends JFrame {

    private static final long serialVersionUID = 1L;
    private BusRouteService busRouteService;
    private static final Font UNICODE_FONT = new Font("Pyidaungsu", Font.PLAIN, 18); // Adjust the font size as needed
    private static final Font ARIAL_FONT = new Font("Arial", Font.PLAIN, 18); // Adjust the font size as needed
    
    public AdminClient() {
        if (showLoginDialog()) {
            try {
                busRouteService = (BusRouteService) Naming.lookup("//192.168.100.1:2096/BusRouteService");
                initUI();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error connecting to the service.");
            }
        } else {
            System.exit(0); // Exit the application if login fails
        }
    }

    private boolean showLoginDialog() {
        Font fieldFont = new Font("Arial", Font.PLAIN, 15); // Font for text fields
        Font labelFont = new Font("Arial", Font.PLAIN, 18); // Increased font size for labels

        JTextField usernameField = new JTextField(12);
        usernameField.setFont(fieldFont); // Apply font to the text field
        JPasswordField passwordField = new JPasswordField(12);
        passwordField.setFont(fieldFont); // Apply font to the text field

        JPanel panel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont); // Set font for the username label
        panel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont); // Set font for the password label
        panel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        int option = JOptionPane.showConfirmDialog(this, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            return validateLogin(username, password);
        }
        return false;
    }


    private boolean validateLogin(String username, String password) {
        // Replace with actual validation logic, e.g., checking against a database or a predefined list
        return "admin".equals(username) && "pass123".equals(password);
    }

    private void initUI() {
        setTitle("Admin Client");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x63ace5)); // Light blue background color

        JLabel titleLabel = new JLabel("Welcome Admin", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        titleLabel.setForeground(Color.RED);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.setBorder(new EmptyBorder(30,0,0,0));//padding

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x63ace5)); // Light blue background color
        buttonPanel.setLayout(new GridLayout(2, 2, 70, 70)); // 2 rows, 2 columns, 20px spacing
        buttonPanel.setBorder(new EmptyBorder(60,70,30,70));//padding
        
        // Customizing buttons
        JButton addButton = createButton("Add Route", new Color(0x0046B2), e -> addRoute());
        JButton updateButton = createButton("Update Route", new Color(0x0046B2), e -> updateRoute());
        JButton deleteButton = createButton("Delete Route", new Color(0x0046B2), e -> deleteRoute());
        JButton viewAllButton = createButton("View All Routes", new Color(0x0046B2), e -> viewAllRoutes());

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Separate panel for the exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(new Color(0x63ace5)); // Light blue background color

        JButton exitButton = createButton("Exit", new Color(0x2a4d69), e -> System.exit(0));
        exitButton.setPreferredSize(new Dimension(80, 30)); // Make the exit button smaller
        exitPanel.setBorder(new EmptyBorder(10,40,20,40)); // padding
        exitPanel.add(exitButton);

        mainPanel.add(exitPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private JButton createButton(String text, Color color, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(ARIAL_FONT); // Set the Unicode font for the button
        button.setFont(new Font("Helvetica", Font.PLAIN, 22)); // Smaller font size
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(listener);
        button.setPreferredSize(new Dimension(150, 40)); // Adjusted button size
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    private void addRoute() {
        JPanel panel = new JPanel(new GridLayout(10, 2)); // Adjust grid layout for 10 rows
        panel.setBackground(new Color(0xE6E6FA)); // Lavender background color
        
        JTextField sourceField = createInputFieldeng(panel, "Source:");
        JTextField destinationField = createInputFieldeng(panel, "Destination:");
        JTextField busNameField = createInputFieldeng(panel, "Car Name:");
        JTextField carNameField = createInputField(panel, "Car Name in Burmese:");
        JTextField stopsField = createInputFieldeng(panel, "Stops (Dash(-) separated):");

        // Time Input for Departure Time
        JFormattedTextField departureTimeField = createFormattedTextField("HH:mm");
        addFormattedInputField(panel, "Departure Time (HH:MM):", departureTimeField);

        // Time Input for Arrival Time
        JFormattedTextField arrivalTimeField = createFormattedTextField("HH:mm");
        addFormattedInputField(panel, "Arrival Time (HH:MM):", arrivalTimeField);

        // Date Input for Travel Date
        JDateChooser travelDateChooser = new JDateChooser();
        travelDateChooser.setDateFormatString("yyyy-MM-dd"); // Set the format for JDateChooser
        addFormattedInputField(panel, "Travel Date (YYYY-MM-DD):", travelDateChooser);

        JTextField costPerSeatField = createInputFieldeng(panel, "Cost Per Seat:");
        JTextField phNoField = createInputFieldeng(panel, "Ph Number:");

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Route", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String departureTime = departureTimeField.getText();
                String arrivalTime = arrivalTimeField.getText();
                java.util.Date travelDate = travelDateChooser.getDate();

                // Validate time formats
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                timeFormat.setLenient(false);
                timeFormat.parse(departureTime);
                timeFormat.parse(arrivalTime);

                // Convert java.util.Date to String for storage
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String travelDateString = dateFormat.format(travelDate);

                busRouteService.addRoute(
                    sourceField.getText(),
                    destinationField.getText(),
                    busNameField.getText(),
                    carNameField.getText(),
                    stopsField.getText(),
                    departureTime,
                    arrivalTime,
                    travelDateString,
                    phNoField.getText(), // Phone number as string
                    Double.parseDouble(costPerSeatField.getText())
                );
                JOptionPane.showMessageDialog(this, "Route added successfully.");
            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Invalid date or time format.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding route.");
            }
        }
    }

    private void addFormattedInputField(JPanel panel, String label, JComponent component) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(ARIAL_FONT); // Set the Unicode font for the label
        panel.add(jLabel);
        component.setFont(UNICODE_FONT); // Set the Unicode font for the component
        panel.add(component);
    }

    private JTextField createInputField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(ARIAL_FONT); // Set the Unicode font for the label
        panel.add(jLabel);
        JTextField textField = new JTextField();
        Font textFieldFont = new Font("Pyidaungsu", Font.PLAIN, 16); // Increase font size for text fields
        textField.setFont(textFieldFont); // Set the Unicode font for the text field
        panel.add(textField);
        return textField;
    }
    
    private JTextField createInputFieldeng(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(ARIAL_FONT); // Set the Arial font for the label
        panel.add(jLabel);
        JTextField textField = new JTextField();
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16); // Increase font size for text fields
        textField.setFont(textFieldFont);  panel.add(textField);
        return textField;
    }

    private JFormattedTextField createFormattedTextField(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        DateFormatter dateFormatter = new DateFormatter(dateFormat);
        JFormattedTextField formattedTextField = new JFormattedTextField(dateFormatter);
        formattedTextField.setColumns(10);
        formattedTextField.setFont(ARIAL_FONT); // Set the Unicode font for the formatted text field
        return formattedTextField;
    }

    private void updateRoute() {
    // Step 1: Prompt for Route ID with an increased input field size
    JTextField routeIdInputField = new JTextField();
    routeIdInputField.setPreferredSize(new Dimension(200, 30)); // Increase width and height
    Font font = new Font("Pyidaungsu", Font.BOLD, 16); // Set the font size
    routeIdInputField.setFont(font);

    int option = JOptionPane.showConfirmDialog(this, routeIdInputField, "Enter Route ID to update:", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        String routeIdInput = routeIdInputField.getText();
        if (routeIdInput != null && !routeIdInput.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(routeIdInput.trim());

                // Step 2: Get current route details
                List<String> details = busRouteService.getRouteById(id);
                if (details != null && !details.isEmpty()) {
                    // Set up panel with increased size and font
                    JPanel panel = new JPanel(new GridLayout(11, 2));
                    panel.setBackground(new Color(0xE6E6FA)); // Lavender background color

                    JTextField idField = createInputFieldeng(panel, "Route ID:");
                    idField.setText(String.valueOf(id));
                    idField.setEditable(false);
                    idField.setForeground(Color.BLUE);
                    idField.setFont(font);

                    JTextField sourceField = createInputFieldeng(panel, "Source:");
                    sourceField.setText(details.get(1));
                    sourceField.setFont(font);

                    JTextField destinationField = createInputFieldeng(panel, "Destination:");
                    destinationField.setText(details.get(2));
                    destinationField.setFont(font);

                    JTextField busNameField = createInputFieldeng(panel, "Car Name:");
                    busNameField.setText(details.get(3));
                    busNameField.setEditable(false);
                    busNameField.setForeground(Color.BLUE);
                    busNameField.setFont(font);

                    JTextField carNameField = createInputField(panel, "Car Name in Burmese:");
                    carNameField.setText(details.get(4));
                    carNameField.setEditable(false);
                    carNameField.setForeground(Color.BLUE);
                    carNameField.setFont(font);

                    JTextField stopsField = createInputFieldeng(panel, "Stops (Dash(-) separated):");
                    stopsField.setText(details.get(5));
                    stopsField.setFont(font);

                    // Time Input for Departure Time
                    JFormattedTextField departureTimeField = createFormattedTextField("HH:mm");
                    departureTimeField.setText(extractTime(details.get(6)));
                    departureTimeField.setFont(font);
                    addFormattedInputField(panel, "Departure Time (HH:MM):", departureTimeField);

                    // Time Input for Arrival Time
                    JFormattedTextField arrivalTimeField = createFormattedTextField("HH:mm");
                    arrivalTimeField.setText(extractTime(details.get(7)));
                    arrivalTimeField.setFont(font);
                    addFormattedInputField(panel, "Arrival Time (HH:MM):", arrivalTimeField);

                    // Date Input for Travel Date
                    JDateChooser travelDateChooser = new JDateChooser();
                    travelDateChooser.setDateFormatString("yyyy-MM-dd");
                    travelDateChooser.setFont(font);
                    try {
                        travelDateChooser.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(extractDate(details.get(8))));
                    } catch (ParseException e) {
                        JOptionPane.showMessageDialog(this, "Invalid travel date format. Expected format: yyyy-MM-dd");
                        return;
                    }
                    addFormattedInputField(panel, "Travel Date (YYYY-MM-DD):", travelDateChooser);

                    JTextField costPerSeatField = createInputFieldeng(panel, "Cost Per Seat:");
                    costPerSeatField.setText(details.get(9));
                    costPerSeatField.setFont(font);

                    JTextField phNoField = createInputFieldeng(panel, "Phone Number:");
                    phNoField.setText(details.get(10));
                    phNoField.setFont(font);

                    // Increase the frame size
                    int result = JOptionPane.showConfirmDialog(this, panel, "Update Route", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            String source = sourceField.getText();
                            String destination = destinationField.getText();
                            String busName = busNameField.getText();
                            String carName = carNameField.getText();
                            String stops = stopsField.getText();
                            String departureTimeStr = departureTimeField.getText();
                            String arrivalTimeStr = arrivalTimeField.getText();
                            String travelDateString = new SimpleDateFormat("yyyy-MM-dd").format(travelDateChooser.getDate());
                            String phNo = phNoField.getText();
                            double costPerSeat = Double.parseDouble(costPerSeatField.getText());

                            // Validate time formats
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm"); // Use HH:mm instead of HH:MM
                            timeFormat.setLenient(false);
                            timeFormat.parse(departureTimeStr);
                            timeFormat.parse(arrivalTimeStr);

                            // Update the route
                            busRouteService.updateRoute(id, source, destination, busName, carName, stops, departureTimeStr, arrivalTimeStr, travelDateString, phNo, costPerSeat);
                            JOptionPane.showMessageDialog(this, "Route updated successfully.");
                        } catch (ParseException e) {
                            JOptionPane.showMessageDialog(this, "Invalid date or time format.");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(this, "Invalid cost per seat format.");
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Error updating route.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No route found with the provided ID.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Route ID format.");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error retrieving route details.");
            }
        }
    }
}




    // Utility method to extract date part from string
    private String extractDate(String dateDetail) {
        // Assuming dateDetail contains "Travel Date: yyyy-MM-dd" or similar
        // Modify this method if the format is different
        return dateDetail.replaceAll(".*: ", "").trim();
    }

    // Utility method to extract time part from string
    private String extractTime(String timeDetail) {
        // Assuming timeDetail contains "Arrival Time: HH:MM" or similar
        // Modify this method if the format is different
        return timeDetail.replaceAll(".*: ", "").trim();
    }





    private void deleteRoute() {
        // Create a larger input field for the Route ID
        JTextField routeIdInputField = new JTextField();
        routeIdInputField.setPreferredSize(new Dimension(200, 30)); // Increase width and height
        Font font = new Font("Arial", Font.BOLD, 16); // Set the font size
        routeIdInputField.setFont(font);

        int option = JOptionPane.showConfirmDialog(this, routeIdInputField, "Enter Route ID to delete:", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String routeId = routeIdInputField.getText();
            if (routeId != null && !routeId.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(routeId.trim());
                    busRouteService.deleteRoute(id);
                    JOptionPane.showMessageDialog(this, "Route deleted successfully.");
                } catch (RemoteException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting route.");
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid Route ID.");
                }
            }
        }
    }


    
    private void viewAllRoutes() {
        try {
            List<String> routes = busRouteService.getAllRoutes();
            String[] columns = {"Route ID", "Source", "Destination", "Car Name", "Car Name in Burmese", "Stops", "Departure Time", "Arrival Time", "Travel Date",  "Cost Per Seat(MMK)","Ph_No"};
            String[][] data = new String[routes.size()][columns.length];

            for (int i = 0; i < routes.size(); i++) {
                String route = routes.get(i);
                String[] details = route.split(",\\s*"); // Split by comma followed by optional spaces

                if (details.length >= columns.length) {
                    for (int j = 0; j < columns.length; j++) {
                        data[i][j] = details[j].trim();
                    }
                } else {
                    System.out.println("Skipping improperly formatted route: " + route);
                }
            }

            JTable routesTable = new JTable(data, columns);
            // Set font for the table columns and rows
            Font tableFont = new Font("Arial", Font.PLAIN, 18); // Adjust font size here
            routesTable.setFont(tableFont);
            routesTable.setRowHeight(35);

            // Set font for the table header (column names)
            JTableHeader tableHeader = routesTable.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size for headers here

            
            routesTable.setFont(UNICODE_FONT); // Set the Unicode font for the table
            routesTable.setRowHeight(35);
            JScrollPane scrollPane = new JScrollPane(routesTable);
            JFrame frame = new JFrame("All Routes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Set column widths
            TableColumnModel columnModel = routesTable.getColumnModel();
            for (int i = 0; i < columns.length; i++) {
                TableColumn column = columnModel.getColumn(i);
                if (i == 5) { // Example: Set width for 'Stops' column (index 5)
                    column.setPreferredWidth(450);
                } else if (i == 4) { // Example: Set width for 'car name' column (index 9)
                    column.setPreferredWidth(280);
                } else if (i == 9) { // Example: Set width for 'Cost Per Seat' column (index 10)
                    column.setPreferredWidth(280);
                } else if (i == 0) { // Example: Set width for 'Cost Per Seat' column (index 10)
                    column.setPreferredWidth(120);
                }else if (i == 10) { // Example: Set width for 'Cost Per Seat' column (index 10)
                    column.setPreferredWidth(220);
                 
                } else {
                    column.setPreferredWidth(200);
                }
            }

            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            AdminClient client = new AdminClient();
            client.setVisible(true);
        });
    }
}

