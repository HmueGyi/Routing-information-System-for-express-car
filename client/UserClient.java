import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class UserClient extends JFrame {

    private static final long serialVersionUID = 1L;
    private BusRouteService busRouteService;
    private static final Font UNICODE_FONT = new Font("Pyidaungsu", Font.PLAIN, 16);
    private static final Font ARIAL_FONT = new Font("arial", Font.PLAIN, 16); // Adjust the font size as needed
    
    
    public UserClient() {
        try {
            busRouteService = (BusRouteService) Naming.lookup("//192.168.100.1:2096/BusRouteService");
            System.out.println("Connected to BusRouteService");
            initUI();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the service.");
        }
    }

    private void initUI() {
        setTitle("User Client");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().removeAll();
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x00A5E3)); // Aqua background color

        JLabel titleLabel = new JLabel("Routing information System for Express Car", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setForeground(Color.BLACK);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.setBorder(new EmptyBorder(30,0,0,0));//padding

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0x00A5E3)); // Aqua background color
        buttonPanel.setLayout(new GridLayout(2, 2, 60, 60)); // 2 rows, 2 columns, 20px spacing
        buttonPanel.setBorder(new EmptyBorder(50,20,30,30));//padding
        
        // Customizing buttons with smaller size
        JButton searchWithDateButton = createButton("Search Route with Date", new Color(0x0046B2), e -> showSearchWithDatePanel());
        JButton searchWithoutDateButton = createButton("Search Route without Date", new Color(0x0046B2), e -> showSearchWithoutDatePanel());
        JButton searchByCarNameButton = createButton("Search Car Name ", new Color(0x0046B2), e -> showSearchByCarNamePanel());
        JButton searchByCarBrandNameButton = createButton("Search Car Name in Burmese ", new Color(0x0046B2), e -> showSearchByCarBNamePanel());

        buttonPanel.add(searchWithDateButton);
        buttonPanel.add(searchWithoutDateButton);
        buttonPanel.add(searchByCarNameButton);
        buttonPanel.add(searchByCarBrandNameButton);
        
        buttonPanel.setForeground(Color.BLACK);
        

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Separate panel for the exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(new Color(0x00A5E3)); // Aqua background color

        JButton exitButton = createButton("Exit", new Color(0x2a4d69), e -> System.exit(0));
        exitPanel.setBorder(new EmptyBorder(10,20,20,30));//padding
        exitPanel.add(exitButton);

        mainPanel.add(exitPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private JButton createButton(String text, Color color, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(ARIAL_FONT); // Set the Unicode font for the button
        button.setFont(new Font("Aria", Font.BOLD, 16)); // Smaller font size
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(80, 30)); // Smaller button size
        button.addActionListener(actionListener);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    
    
    
    
    //select
    private void showSearchWithDatePanel() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Bold and increase font size for labels
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16); // Increase font size for text fields
        Font dateFieldFont = new Font("Arial", Font.PLAIN, 16); // Font for date field
        Font buttonFont = new Font("Arial", Font.BOLD, 15); // Bold and increase font size for buttons

        JTextField sourceField = new JTextField(15);
        sourceField.setFont(textFieldFont); // Set font for source text field
        JTextField destinationField = new JTextField(15);
        destinationField.setFont(textFieldFont); // Set font for destination text field

        // Increase the size of the date field
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 30)); // Increase size of date field
        dateChooser.setFont(dateFieldFont); // Set font for date chooser

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel sourceLabel = new JLabel("Source:", JLabel.RIGHT);
        sourceLabel.setFont(labelFont); // Set bold font for source label
        panel.add(sourceLabel, gbc);

        gbc.gridx = 1;
        panel.add(sourceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel destinationLabel = new JLabel("Destination:", JLabel.RIGHT);
        destinationLabel.setFont(labelFont); // Set bold font for destination label
        panel.add(destinationLabel, gbc);

        gbc.gridx = 1;
        panel.add(destinationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel dateLabel = new JLabel("Date:", JLabel.RIGHT);
        dateLabel.setFont(labelFont); // Set bold font for date label
        panel.add(dateLabel, gbc);

        gbc.gridx = 1;
        panel.add(dateChooser, gbc);

        gbc.gridwidth = 1; // Reset grid width
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 2;
        gbc.gridy = 3;
        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont); // Bold font for back button
        panel.add(backButton, gbc);

        gbc.gridx = 0;
        JButton viewRoutesButton = new JButton("View Routes");
        viewRoutesButton.setFont(buttonFont); // Bold font for view routes button
        panel.add(viewRoutesButton, gbc);

        viewRoutesButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            java.util.Date selectedDate = dateChooser.getDate();
            String date = (selectedDate != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(selectedDate) : "";
            System.out.println("Source: " + source + ", Destination: " + destination + ", Date: " + date);
            viewRoutesForCustomer(source, destination, date);
        });

        backButton.addActionListener(e -> initUI());

        add(panel);
        revalidate();
        repaint();
    }



    private void showSearchWithoutDatePanel() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Bold and increase font size for labels
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16); // Increase font size for text fields
        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Bold and increase font size for buttons

        JTextField sourceField = new JTextField(15);
        sourceField.setFont(textFieldFont); // Set font for source text field
        JTextField destinationField = new JTextField(15);
        destinationField.setFont(textFieldFont); // Set font for destination text field

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel sourceLabel = new JLabel("Source:", JLabel.RIGHT);
        sourceLabel.setFont(labelFont); // Set bold font for source label
        panel.add(sourceLabel, gbc);

        gbc.gridx = 1;
        panel.add(sourceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel destinationLabel = new JLabel("Destination:", JLabel.RIGHT);
        destinationLabel.setFont(labelFont); // Set bold font for destination label
        panel.add(destinationLabel, gbc);

        gbc.gridx = 1;
        panel.add(destinationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton viewRoutesButton = new JButton("View Routes");
        viewRoutesButton.setFont(buttonFont); // Set bold font for view routes button

        viewRoutesButton.addActionListener(e -> {
            String source = sourceField.getText();
            String destination = destinationField.getText();
            viewRoutesWithoutDate(source, destination);
        });

        panel.add(viewRoutesButton, gbc);

        // Update constraints for the back button
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont); // Set bold font for back button
        backButton.addActionListener(e -> initUI());
        panel.add(backButton, gbc);

        add(panel);
        revalidate();
        repaint();
    }



    private void showSearchByCarNamePanel() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Bold and increase font size for labels
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16); // Increase font size for text fields
        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Bold and increase font size for buttons

        JTextField carNameField = new JTextField(15);
        carNameField.setFont(textFieldFont); // Set font for the car name text field

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel carNameLabel = new JLabel("Car Name in English:", JLabel.RIGHT);
        carNameLabel.setFont(labelFont); // Set bold font for the label
        panel.add(carNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(carNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JButton viewRoutesButton = new JButton("View Routes");
        viewRoutesButton.setFont(buttonFont); // Set bold font for the view routes button

        viewRoutesButton.addActionListener(e -> {
            String carName = carNameField.getText();
            viewRoutesByCarName(carName);
        });

        panel.add(viewRoutesButton, gbc);

        // Update constraints for the back button
        gbc.gridx = 2;
        gbc.gridy = 1; // Move to the next row
        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont); // Set bold font for the back button
        backButton.addActionListener(e -> initUI());
        panel.add(backButton, gbc);

        add(panel);
        revalidate();
        repaint();
    }
    private void showSearchByCarBNamePanel() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Increase font sizes and make them bold
        Font labelFont = new Font("Pyidaungsu", Font.BOLD, 16); // Bold and increase font size for labels
        Font textFieldFont = new Font("Pyidaungsu", Font.PLAIN, 16); // Increase font size for text fields
        Font buttonFont = new Font("Pyidaungsu", Font.BOLD, 16); // Bold and increase font size for buttons

        JTextField carNameField = new JTextField(15);
        carNameField.setFont(textFieldFont); // Set font for the car name text field

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel carNameLabel = new JLabel("Car Name in Burmese:", JLabel.RIGHT);
        carNameLabel.setFont(labelFont); // Set bold font for the label
        panel.add(carNameLabel, gbc);

        gbc.gridx = 1;
        panel.add(carNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        
        JButton viewRoutesButton = new JButton("View Routes");
        viewRoutesButton.setFont(buttonFont); // Set bold font for the view routes button
        
        viewRoutesButton.addActionListener(e -> {
            String carName = carNameField.getText();
            viewRoutesByCarBName(carName);
        });

        panel.add(viewRoutesButton, gbc);

        // Update constraints for the back button
        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont); // Set bold font for the back button
        backButton.addActionListener(e -> initUI());
        panel.add(backButton, gbc);

        add(panel);
        revalidate();
        repaint();
    }




    
    
    
    
    
    
    
    
    //method
    private void viewRoutesForCustomer(String source, String destination, String date) {
        if (source.trim().isEmpty() || destination.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Source and Destination fields cannot be empty.");
            return;
        }

        try {
            List<String> routes = busRouteService.getRoutesForCustomer(source, destination, date);
            System.out.println("Retrieved routes: " + routes);
            if (routes == null || routes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No routes available.");
                return;
            }

            // Define columns
            String[] columns = {"Car Name", "Car Name in Burmese", "Stops", "Departure Time", "Arrival Time", "Travel Date", "Cost Per Seat", "Ph_No"};
            String[][] data = new String[routes.size()][columns.length];

            for (int i = 0; i < routes.size(); i++) {
                String route = routes.get(i);
                String[] details = route.split(",\\s*");
                
                // Ensure the details array length matches columns length
                if (details.length == columns.length) {
                    for (int j = 0; j < columns.length; j++) {
                        data[i][j] = details[j].trim();
                    }
                } else {
                    System.out.println("Skipping improperly formatted route: " + route);
                }
                
            }

            
            // Create table model and table
            DefaultTableModel model = new DefaultTableModel(data, columns);
            JTable routesTable = new JTable(model);

            // Set font for the table columns and rows
            Font tableFont = new Font("Pyidaungsu", Font.PLAIN, 16); // Font size for table rows
            routesTable.setFont(tableFont);
            routesTable.setRowHeight(35);

            // Set font for the table header (column names) and make it bold
            JTableHeader tableHeader = routesTable.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 18)); // Bold font size for headers

            JScrollPane scrollPane = new JScrollPane(routesTable);

            // Create and configure frame to display table
            JFrame frame = new JFrame("Available Routes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TableColumnModel columnModel = routesTable.getColumnModel();
            for (int i = 0; i < columns.length; i++) {
                TableColumn column = columnModel.getColumn(i);
                if (i == 2) { // Example: Set width for 'Stops' column (index 2)
                    column.setPreferredWidth(500);
                } 
                else if(i == 1){
                	column.setPreferredWidth(280);
                }
                	else {
                	
                }
                    column.setPreferredWidth(220);
                }
            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving routes.");
        }
    }


    private void viewRoutesWithoutDate(String source, String destination) {
        if (source.trim().isEmpty() || destination.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Source and Destination fields cannot be empty.");
            return;
        }

        try {
            List<String> routes = busRouteService.getRoutesForCustomerNodate(source, destination);
            System.out.println("Retrieved routes: " + routes);
            if (routes == null || routes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No routes available.");
                return;
            }

            String[] columns = {"Car Name", "Car Name in Burmese", "Stops", "Departure Time", "Arrival Time", "Travel Date", "Cost Per Seat", "Ph_No"};
            String[][] data = new String[routes.size()][columns.length];

            for (int i = 0; i < routes.size(); i++) {
                String route = routes.get(i);
                String[] details = route.split(",\\s*");

                if (details.length >= columns.length) {
                    for (int j = 0; j < columns.length; j++) {
                        data[i][j] = details[j].trim();
                    }
                } else {
                    System.out.println("Skipping improperly formatted route: " + route);
                }
            }

            JTable routesTable = new JTable(new DefaultTableModel(data, columns));

            // Set font for the table header (column names) and make it bold
            JTableHeader tableHeader = routesTable.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 18)); // Adjust font size and make it bold

            routesTable.setFont(new Font("Pyidaungsu", Font.PLAIN, 16)); // Set font for the table rows
            routesTable.setRowHeight(35);

            JScrollPane scrollPane = new JScrollPane(routesTable);
            JFrame frame = new JFrame("Available Routes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TableColumnModel columnModel = routesTable.getColumnModel();
            for (int i = 0; i < columns.length; i++) {
                TableColumn column = columnModel.getColumn(i);
                if (i == 2) { // Example: Set width for 'Stops' column (index 2)
                    column.setPreferredWidth(500);
                } 
                else if(i == 1){
                	column.setPreferredWidth(280);
                }
                	else {
                	
                }
                    column.setPreferredWidth(220);
                }

            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving routes.");
        }
    }



    private void viewRoutesByCarName(String carName) {
        if (carName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Car Name field cannot be empty.");
            return;
        }

        try {
            List<String> routes = busRouteService.getRoutesByCarName(carName);
            System.out.println("Retrieved routes: " + routes);
            if (routes == null || routes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No routes available.");
                return;
            }

            // Define columns and data
            String[] columns = {"Car Name", "Car Name in Burmese", "Stops", "Departure Time", "Arrival Time", "Travel Date", "Cost Per Seat", "Ph_No"};
            String[][] data = new String[routes.size()][columns.length];

            for (int i = 0; i < routes.size(); i++) {
                String route = routes.get(i);
                String[] details = route.split(",\\s*");

                if (details.length >= columns.length) {
                    for (int j = 0; j < columns.length; j++) {
                        data[i][j] = details[j].trim();
                    }
                } else {
                    System.out.println("Skipping improperly formatted route: " + route);
                }
            }

            // Create table model and table
            DefaultTableModel model = new DefaultTableModel(data, columns);
            JTable routesTable = new JTable(model);

            // Set font for the table columns and rows
            Font tableFont = new Font("Pyidaungsu", Font.PLAIN, 16); // Adjust font size here
            routesTable.setFont(tableFont);
            routesTable.setRowHeight(35);

            // Set font for the table header (column names) and make it bold
            JTableHeader tableHeader = routesTable.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 18)); // Bold font size for headers

            JScrollPane scrollPane = new JScrollPane(routesTable);

            // Create and configure frame to display table
            JFrame frame = new JFrame("Available Routes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TableColumnModel columnModel = routesTable.getColumnModel();
            for (int i = 0; i < columns.length; i++) {
                TableColumn column = columnModel.getColumn(i);
                if (i == 2) { // Example: Set width for 'Stops' column (index 2)
                    column.setPreferredWidth(500);
                } 
                else if(i == 1){
                	column.setPreferredWidth(280);
                }
                	else {
                	
                }
                    column.setPreferredWidth(220);
                }

            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving routes.");
        }
    }


    private void viewRoutesByCarBName(String carBName) {
        if (carBName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Car Name field cannot be empty.");
            return;
        }

        try {
            List<String> routes = busRouteService.getRoutesByCarBName(carBName);
            System.out.println("Retrieved routes: " + routes);
            if (routes == null || routes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No routes available.");
                return;
            }

            // Define columns and data
            String[] columns = {"Car Name", "Car Name in Burmese", "Stops", "Departure Time", "Arrival Time", "Travel Date", "Cost Per Seat", "Ph_No"};
            String[][] data = new String[routes.size()][columns.length];

            for (int i = 0; i < routes.size(); i++) {
                String route = routes.get(i);
                String[] details = route.split(",\\s*");

                if (details.length >= columns.length) {
                    for (int j = 0; j < columns.length; j++) {
                        data[i][j] = details[j].trim();
                    }
                } else {
                    System.out.println("Skipping improperly formatted route: " + route);
                }
            }

            // Create table model and table
            DefaultTableModel model = new DefaultTableModel(data, columns);
            JTable routesTable = new JTable(model);

            // Set font for the table columns and rows
            Font tableFont = new Font("Pyidaungsu", Font.PLAIN, 16); // Adjust font size here
            routesTable.setFont(tableFont);
            routesTable.setRowHeight(35);

            // Set font for the table header (column names) and make it bold
            JTableHeader tableHeader = routesTable.getTableHeader();
            tableHeader.setFont(new Font("Arial", Font.BOLD, 18)); // Bold font size for headers

            JScrollPane scrollPane = new JScrollPane(routesTable);

            // Create and configure frame to display table
            JFrame frame = new JFrame("Available Routes");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            TableColumnModel columnModel = routesTable.getColumnModel();
            for (int i = 0; i < columns.length; i++) {
                TableColumn column = columnModel.getColumn(i);
                if (i == 2) { // Example: Set width for 'Stops' column (index 2)
                    column.setPreferredWidth(500);
                } 
                else if(i == 1){
                	column.setPreferredWidth(280);
                }
                	else {
                	
                }
                    column.setPreferredWidth(220);
                }

            frame.add(scrollPane);
            frame.pack();
            frame.setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving routes.");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            UserClient client = new UserClient();
            client.setVisible(true);
        });
    }
}
