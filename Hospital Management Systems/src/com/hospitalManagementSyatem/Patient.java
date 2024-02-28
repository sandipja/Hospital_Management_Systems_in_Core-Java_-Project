package com.hospitalManagementSyatem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner scanner;

	public Patient(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;

	}

	public void addPatient() {
		System.out.println("Enter Patient Name:");
		String name = scanner.next();
		System.out.println("Enter Patient Age:");
		int age = scanner.nextInt();
		System.out.println("Enter patient Gender:");
		String gender = scanner.next();

		String query = "insert into patients(name,age,gender) values(?,?,?)";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			int affectdRows = preparedStatement.executeUpdate();
			if (affectdRows > 0) {
				System.out.println("Patient Added Succrssfully...!");
			} else {
				System.out.println("Failed to add Patiend...!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void viewPatients() {
		String query = "select *from patients";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Viwe Patients");
			System.out.println("+-----------+---------+--------+---------+");
			System.out.println("|patient id | Name    |  Age   | Gender  |");
			System.out.println("+-----------+---------+--------+---------+");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				System.out.printf("|%-11s|%-8s |%-8s|%9s|\n",id,name,age,gender);
				System.out.println("+-----------+---------+--------+---------+");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean getPatientById(int id) {
		String query = "select * from patients where id= ?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle the exception, maybe log it or throw a custom one
			 // Ensure a return value
		}
		return false;
	}

}
