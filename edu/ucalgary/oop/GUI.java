package edu.ucalgary.oop;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel animalNicknameLabel, animalSpeciesLabel, taskLabel, startHourLabel;
    private JTextField animalNicknameField, animalSpeciesField, taskField, startHourField;
    private JButton submitButton;
    private Connection connection;
    private Statement statement1, statement2;
    private ResultSet resultSet;
    private JButton showValidValuesButton;
    private JButton showTreatmentsButton;
    private ConnectDatabase connectDatabase;

    public GUI() {
        frame = new JFrame("Animal Treatment Information");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        animalNicknameLabel = new JLabel("Animal Nickname:");
        animalNicknameField = new JTextField();
        animalSpeciesLabel = new JLabel("Animal Species:");
        animalSpeciesField = new JTextField();
        taskLabel = new JLabel("Task:");
        taskField = new JTextField();
        startHourLabel = new JLabel("Start Hour:");
        startHourField = new JTextField();

        panel.add(animalNicknameLabel);
        panel.add(animalNicknameField);
        panel.add(animalSpeciesLabel);
        panel.add(animalSpeciesField);
        panel.add(taskLabel);
        panel.add(taskField);
        panel.add(startHourLabel);
        panel.add(startHourField);

        try {
            connectDatabase = new ConnectDatabase();
            connectDatabase.createConnection();
            connection = connectDatabase.getConnection(); // Get the Connection object from ConnectDatabase
            statement1 = connection.createStatement();
            statement2 = connection.createStatement();
            JOptionPane.showMessageDialog(null, "Successfully connected to the database");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database: " + ex.getMessage());
        }

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String animalNickname = animalNicknameField.getText();
                    String animalSpecies = animalSpeciesField.getText();
                    String task = taskField.getText();
                    int startHour = Integer.parseInt(startHourField.getText());

                    resultSet = statement1.executeQuery("SELECT AnimalID FROM ANIMALS WHERE AnimalNickname = '"
                            + animalNickname + "' AND AnimalSpecies = '" + animalSpecies + "'");
                    if (!resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "No animal with the given nickname and species was found");
                        return;
                    }
                    int animalID = resultSet.getInt("AnimalID");

                    resultSet = statement2.executeQuery("SELECT TaskID FROM TASKS WHERE Description = '" + task + "'");
                    if (!resultSet.next()) {
                        JOptionPane.showMessageDialog(null, "The task does not exist");
                        return;
                    }
                    int taskID = resultSet.getInt("TaskID");

                    String sql = "INSERT INTO TREATMENTS (AnimalID, TaskID, StartHour) VALUES (?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, animalID);
                    preparedStatement.setInt(2, taskID);
                    preparedStatement.setInt(3, startHour);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record added successfully");

                    animalNicknameField.setText("");
                    animalSpeciesField.setText("");
                    taskField.setText("");
                    startHourField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        showValidValuesButton = new JButton("Show Valid Values");
        showValidValuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame validValuesFrame = new JFrame("Valid Values");
                    validValuesFrame.setSize(400, 400);
                    validValuesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    validValuesFrame.setLayout(new GridLayout(3, 1));

                    DefaultListModel<String> nicknamesListModel = new DefaultListModel<>();
                    DefaultListModel<String> speciesListModel = new DefaultListModel<>();
                    DefaultListModel<String> tasksListModel = new DefaultListModel<>();

                    resultSet = statement1.executeQuery("SELECT DISTINCT AnimalNickname FROM ANIMALS");
                    while (resultSet.next()) {
                        nicknamesListModel.addElement(resultSet.getString("AnimalNickname"));
                    }

                    resultSet = statement1.executeQuery("SELECT DISTINCT AnimalSpecies FROM ANIMALS");
                    while (resultSet.next()) {
                        speciesListModel.addElement(resultSet.getString("AnimalSpecies"));
                    }

                    resultSet = statement2.executeQuery("SELECT DISTINCT Description FROM TASKS");
                    while (resultSet.next()) {
                        tasksListModel.addElement(resultSet.getString("Description"));
                    }

                    JLabel nicknamesHeader = new JLabel("Animal Nicknames");
                    JLabel speciesHeader = new JLabel("Animal Species");
                    JLabel tasksHeader = new JLabel("Tasks");

                    JList<String> nicknamesList = new JList<>(nicknamesListModel);
                    JList<String> speciesList = new JList<>(speciesListModel);
                    JList<String> tasksList = new JList<>(tasksListModel);

                    validValuesFrame.add(nicknamesHeader);
                    validValuesFrame.add(new JScrollPane(nicknamesList));
                    validValuesFrame.add(speciesHeader);
                    validValuesFrame.add(new JScrollPane(speciesList));
                    validValuesFrame.add(tasksHeader);
                    validValuesFrame.add(new JScrollPane(tasksList));

                    validValuesFrame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        });

        showTreatmentsButton = new JButton("Show Treatments");
        showTreatmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTreatments();
            }
        });

        panel.add(showTreatmentsButton);

        panel.add(submitButton);
        panel.add(showValidValuesButton);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void showTreatments() {
        try {
            JFrame treatmentsFrame = new JFrame("Treatments");
            treatmentsFrame.setSize(600, 400);
            treatmentsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            String[] columnNames = { "AnimalID", "TaskID", "StartHour" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            resultSet = statement1.executeQuery("SELECT * FROM TREATMENTS");
            while (resultSet.next()) {
                int animalID = resultSet.getInt("AnimalID");
                int taskID = resultSet.getInt("TaskID");
                int startHour = resultSet.getInt("StartHour");

                Object[] rowData = { animalID, taskID, startHour };
                model.addRow(rowData);
            }

            JTable treatmentsTable = new JTable(model);
            treatmentsFrame.add(new JScrollPane(treatmentsTable));

            treatmentsFrame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        printSchedule();
    }

    private void printSchedule() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            System.out.println("Schedule for " + currentDate);

            resultSet = statement1
                    .executeQuery("SELECT t.StartHour, ts.Description, a.AnimalNickname FROM TREATMENTS t " +
                            "JOIN TASKS ts ON t.TaskID = ts.TaskID " +
                            "JOIN ANIMALS a ON t.AnimalID = a.AnimalID " +
                            "ORDER BY t.StartHour, ts.Description, a.AnimalNickname");

            int currentHour = -1;
            while (resultSet.next()) {
                int startHour = resultSet.getInt("StartHour");
                String taskDescription = resultSet.getString("Description");
                String animalNickname = resultSet.getString("AnimalNickname");

                if (startHour != currentHour) {
                    System.out.printf("%02d:00\n", startHour);
                    currentHour = startHour;
                }

                System.out.println("* " + taskDescription + " (" + animalNickname + ")");
            }
        } catch (Exception ex) {
            System.err.println("Error while printing the schedule: " + ex.getMessage());
        }
    }

    public static void main(String args[]) {
        new GUI();
    }
}