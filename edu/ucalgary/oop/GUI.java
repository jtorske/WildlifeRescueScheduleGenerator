package edu.ucalgary.oop;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class GUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel animalNicknameLabel, animalSpeciesLabel, taskLabel, startHourLabel;
    private JTextField animalNicknameField, animalSpeciesField, taskField, startHourField;
    private JButton submitButton;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public GUI() {
        frame = new JFrame("Animal Treatment Information");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

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

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/EWR?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                            "root", "password");
                    statement = connection.createStatement();

                    String animalNickname = animalNicknameField.getText();
                    String animalSpecies = animalSpeciesField.getText();
                    String task = taskField.getText();
                    int startHour = Integer.parseInt(startHourField.getText());

                    resultSet = statement.executeQuery("SELECT AnimalID FROM ANIMALS WHERE AnimalNickname = '"
                            + animalNickname + "' AND AnimalSpecies = '" + animalSpecies + "'");
                    resultSet.next();
                    int animalID = resultSet.getInt("AnimalID");

                    resultSet = statement.executeQuery("SELECT TaskID FROM TASKS WHERE Description = '" + task + "'");
                    resultSet.next();
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
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String args[]) {
        new GUI();
    }
}