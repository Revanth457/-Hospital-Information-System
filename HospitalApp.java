import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Objects;

public class HospitalApp {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/hospital";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password"; // Replace with your MySQL password

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HospitalApp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Hospital Information System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridLayout(3, 1));

        JPanel patientPanel = new JPanel();
        JPanel appointmentPanel = new JPanel();
        JPanel historyPanel = new JPanel();

        // Patient Panel
        patientPanel.setLayout(new GridLayout(6, 2));
        patientPanel.add(new JLabel("First Name:"));
        JTextField firstNameField = new JTextField();
        patientPanel.add(firstNameField);
        patientPanel.add(new JLabel("Last Name:"));
        JTextField lastNameField = new JTextField();
        patientPanel.add(lastNameField);
        patientPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        JTextField dobField = new JTextField();
        patientPanel.add(dobField);
        patientPanel.add(new JLabel("Gender (M/F):"));
        JTextField genderField = new JTextField();
        patientPanel.add(genderField);
        patientPanel.add(new JLabel("Phone Number:"));
        JTextField phoneField = new JTextField();
        patientPanel.add(phoneField);
        patientPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        patientPanel.add(emailField);

        JButton addPatientButton = new JButton("Add Patient");
        patientPanel.add(addPatientButton);
        addPatientButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dob = dobField.getText();
            String gender = genderField.getText();
            String phoneNumber = phoneField.getText();
            String email = emailField.getText();
            addPatient(firstName, lastName, dob, gender, phoneNumber, email);
            JOptionPane.showMessageDialog(frame, "Patient added successfully!");
        });

        // Appointment Panel
        appointmentPanel.setLayout(new GridLayout(4, 2));
        appointmentPanel.add(new JLabel("Patient ID:"));
        JTextField patientIdField = new JTextField();
        appointmentPanel.add(patientIdField);
        appointmentPanel.add(new JLabel("Appointment Date (YYYY-MM-DD HH:MM:SS):"));
        JTextField appointmentDateField = new JTextField();
        appointmentPanel.add(appointmentDateField);
        appointmentPanel.add(new JLabel("Reason:"));
        JTextField reasonField = new JTextField();
        appointmentPanel.add(reasonField);

        JButton addAppointmentButton = new JButton("Add Appointment");
        appointmentPanel.add(addAppointmentButton);
        addAppointmentButton.addActionListener(e -> {
            int patientId = Integer.parseInt(patientIdField.getText());
            String appointmentDate = appointmentDateField.getText();
            String reason = reasonField.getText();
            addAppointment(patientId, appointmentDate, reason);
            JOptionPane.showMessageDialog(frame, "Appointment added successfully!");
        });

        // Medical History Panel
        historyPanel.setLayout(new GridLayout(4, 2));
        historyPanel.add(new JLabel("Patient ID:"));
        JTextField historyPatientIdField = new JTextField();
        historyPanel.add(historyPatientIdField);
        historyPanel.add(new JLabel("Entry Date (YYYY-MM-DD):"));
        JTextField entryDateField = new JTextField();
        historyPanel.add(entryDateField);
        historyPanel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        historyPanel.add(descriptionField);

        JButton addHistoryButton = new JButton("Add Medical History");
        historyPanel.add(addHistoryButton);
        addHistoryButton.addActionListener(e -> {
            int patientId = Integer.parseInt(historyPatientIdField.getText());
            String entryDate = entryDateField.getText();
            String description = descriptionField.getText();
            addMedicalHistory(patientId, entryDate, description);
            JOptionPane.showMessageDialog(frame, "Medical history added successfully!");
        });

        frame.add(patientPanel);
        frame.add(appointmentPanel);
        frame.add(historyPanel);
        frame.setVisible(true);
    }

    private static void addPatient(String firstName, String lastName, String dob, String gender, String phoneNumber, String email) {
        String sql = "INSERT INTO Patients (first_name, last_name, date_of_birth, gender, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setDate(3, Date.valueOf(dob));
            stmt.setString(4, gender);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addAppointment(int patientId, String appointmentDate, String reason) {
        String sql = "INSERT INTO Appointments (patient_id, appointment_date, reason) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setTimestamp(2, Timestamp.valueOf(appointmentDate));
            stmt.setString(3, reason);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addMedicalHistory(int patientId, String entryDate, String description) {
        String sql = "INSERT INTO MedicalHistory (patient_id, entry_date, description) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            stmt.setDate(2, Date.valueOf(entryDate));
            stmt.setString(3, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}