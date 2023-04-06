package edu.ucalgary.oop;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.*;
import java.util.Map;
import java.util.Vector;

public class GUI {
    private ConnectDatabase connectDatabase;
    private Connection connection;
    private Statement statement1, statement2;
    private ResultSet resultSet;

    private JFrame mainFrame;
    private JButton foodInputButton, cleaningInputButton, showButton;

    public GUI() {
        try {
            connectDatabase = new ConnectDatabase();
            connectDatabase.createConnection();
            connection = connectDatabase.getConnection();
            statement1 = connection.createStatement();
            statement2 = connection.createStatement();
            JOptionPane.showMessageDialog(null, "Successfully connected to the database");

            createAndPopulateLogTable();

            createAndShowGUI();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database: " + ex.getMessage());
        }

    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("Zoo Management");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 200);
        mainFrame.setLayout(new FlowLayout());

        foodInputButton = new JButton("Food Input");
        foodInputButton.addActionListener(new FoodInputListener());
        mainFrame.add(foodInputButton);

        cleaningInputButton = new JButton("Cleaning Input");
        cleaningInputButton.addActionListener(new CleaningInputListener());
        mainFrame.add(cleaningInputButton);

        showButton = new JButton("Show");
        showButton.addActionListener(new ShowListener());
        mainFrame.add(showButton);

        mainFrame.setVisible(true);
    }

    private class FoodInputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            openInputWindow("Food Input");
        }
    }

    private class CleaningInputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            openInputWindow("Cleaning Input");
        }
    }

    private class ShowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayLogTable();
        }
    }

    private void displayLogTable() {
        try {
            String queryLogTable = "SELECT * FROM log";
            resultSet = statement1.executeQuery(queryLogTable);

            JTable logTable = new JTable(buildTableModel(resultSet));

            JFrame logTableFrame = new JFrame("Log Table");
            logTableFrame.setSize(600, 300);
            logTableFrame.add(new JScrollPane(logTable));
            logTableFrame.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Failed to display log table: " + ex.getMessage());
        }
    }

    private static TableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Vector<String> columnNames = new Vector<String>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(resultSet.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    private void openInputWindow(String title) {
        JFrame inputFrame = new JFrame(title);
        inputFrame.setSize(300, 150);
        inputFrame.setLayout(new GridLayout(3, 2));

        JLabel idLabel = new JLabel("Animal Id:");
        JTextField idField = new JTextField();
        JLabel hourLabel = new JLabel("Hour:");
        JTextField hourField = new JTextField();

        inputFrame.add(idLabel);
        inputFrame.add(idField);
        inputFrame.add(hourLabel);
        inputFrame.add(hourField);

        JButton submitButton = new JButton("Submit");
        inputFrame.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the code to save the input to the database
            }
        });

        inputFrame.setVisible(true);
    }

    private void createAndPopulateLogTable() throws SQLException {
        // Check if the log table exists
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, "log", null);
        if (tables.next()) {
            // The log table already exists, don't create a new one
            return;
        }

        String createTableSQL = "CREATE TABLE log (" +
                "Time VARCHAR(255), " +
                "Task VARCHAR(255), " +
                "Quantity VARCHAR(255), " +
                "TimeSpent INTEGER, " +
                "TimeAvailable INTEGER)";
        statement1.execute(createTableSQL);

        String queryTasksTreatments = "SELECT treatments.StartHour, tasks.Description, tasks.Duration " +
                "FROM tasks " +
                "INNER JOIN treatments ON tasks.TaskID = treatments.TaskID";
        resultSet = statement1.executeQuery(queryTasksTreatments);

        Map<String, List<LogEntry>> scheduleMap = new HashMap<>();

        while (resultSet.next()) {
            String startHour = resultSet.getString("StartHour");
            String taskDescription = resultSet.getString("Description");
            int duration = resultSet.getInt("Duration");

            LogEntry logEntry = new LogEntry(taskDescription, "-", duration, 60 - duration);
            scheduleMap.computeIfAbsent(startHour, k -> new ArrayList<>()).add(logEntry);
        }

        for (Map.Entry<String, List<LogEntry>> entry : scheduleMap.entrySet()) {
            String startHour = entry.getKey();
            List<LogEntry> logEntries = entry.getValue();
            int timeSpent = logEntries.stream().mapToInt(LogEntry::getTimeSpent).sum();
            int timeAvailable = 60 - timeSpent;

            for (LogEntry logEntry : logEntries) {
                String insertLogSQL = "INSERT INTO log (Time, Task, Quantity, TimeSpent, TimeAvailable) " +
                        "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertLogSQL);
                preparedStatement.setString(1, startHour);
                preparedStatement.setString(2, logEntry.getTask());
                preparedStatement.setString(3, logEntry.getQuantity());
                preparedStatement.setInt(4, logEntry.getTimeSpent());
                preparedStatement.setInt(5, timeAvailable);

                preparedStatement.executeUpdate();
            }
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
    }
}
