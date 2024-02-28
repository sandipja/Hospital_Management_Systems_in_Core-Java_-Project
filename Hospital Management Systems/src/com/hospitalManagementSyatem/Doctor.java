package com.hospitalManagementSyatem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {

	
		private Connection connection;
//		private Scanner scanner;

		public Doctor(Connection connection) {
			this.connection = connection;
//			this.scanner = scanner;

		}

//		public void addPatient() {
//			System.out.println("Enter Patient Name:");
//			String name = scanner.next();
//			System.out.println("Enter Patient Age:");
//			int age = scanner.nextInt();
//			System.out.println("Enter patient Gender:");
//			String gender = scanner.next();
//
//			String query = "insert into patients(name,age,gender) values(?,?,?)";
//			try {
//				PreparedStatement preparedStatement = connection.prepareStatement(query);
//				preparedStatement.setString(1, name);
//				preparedStatement.setInt(2, age);
//				preparedStatement.setString(2, gender);
//				int affectdRows = preparedStatement.executeUpdate();
//				if (affectdRows > 0) {
//					System.out.println("Patient Added Succrssfully...!");
//				} else {
//					System.out.println("Failed to add Patiend...!");
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
		public void viewDoctors() {
			String query = "select *from doctors";

			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery();
				System.out.println("Viwe Doctors");
				System.out.println("+----------+-----------+--------------+");
				System.out.println("|Doctor id | Name      |Specilization |");
				System.out.println("+----------+-----------+--------------+");
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String name = resultSet.getString("name");
					String specialization = resultSet.getString("specialization");
			
					System.out.printf("| %-10s|%-11s|%-14s|\n",id,name, specialization);
					System.out.println("+----------+-----------+--------------+");

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public boolean getdoctorById(int id) {
			String query = "select * from doctors where id= ?";

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

