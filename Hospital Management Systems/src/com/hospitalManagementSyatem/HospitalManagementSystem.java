package com.hospitalManagementSyatem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url = "jdbc:mysql://localhost:3306/hospital";
	private static final String username = "root";
	private static final String password = "Sandip@123";

	public static void main(String[] args) {
		Connection connection = null;
		Scanner scanner = new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			Patient patient = new Patient(connection, scanner);
			Doctor doctor = new Doctor(connection);
			while (true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1.Add Patients");
				System.out.println("2.Viwe Patients");
				System.out.println("3.Add Doctors");
				System.out.println("4.Viwe Doctors");
				System.out.println("5.Book Appointment");
				System.out.println("6.Exist");
				System.out.println("Enter your Choice:");
				int choice = scanner.nextInt();
				switch (choice) {
				case 1:
					patient.addPatient();
					System.out.println();
					break;
				case 2:
					patient.viewPatients();
					System.out.println();
					break;
				case 3:
					doctor.viewDoctors();
					System.out.println();
					break;

				case 4:
					bookAppointment(patient, doctor, connection, scanner);
					System.out.println();
				case 5:
                    System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                    return;
                    
                default:
                    System.out.println("Enter valid choice!!!");
                    break;

				}

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (scanner != null) {
				scanner.close();
			}
		}

	}

	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.println("Enter Patient Id:");
		int patientId = scanner.nextInt();
		System.out.println("Enter Doctor Id:");
		int doctorId = scanner.nextInt();
		System.out.println("Enter appointment date (YYYY-MM-DD)");
		String appointmentdate = scanner.next();
		if (patient.getPatientById(patientId) && (doctor.getdoctorById(doctorId))) {
			if (checkDoctorAvailability(doctorId, appointmentdate, connection)) {
				String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentdate);
					int rowsAffected = preparedStatement.executeUpdate();
					if (rowsAffected > 0) {
						System.out.println("Appointment Bookrd!");
					} else {
						System.out.println("Failed to Book Appointment!");
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Doctor not Available on this date");
			}
		} else {
			System.out.println("Either doctor or patient doent exits");
		}
	}

	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
