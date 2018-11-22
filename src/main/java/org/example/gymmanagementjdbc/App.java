package org.example.gymmanagementjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

import org.example.gymmanagementjdbc.model.Trainer;

public class App {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/gym";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";
	
	static Connection connection;
	
	public static void main(String[] args) {
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			// select all classse
			String sql = "SELECT class_id, name FROM classes";
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()){
				int id = resultSet.getInt("class_id");
				String name = resultSet.getString("name");
				
				System.out.print("class id = " + id);
				System.out.println(" name = " + name);
			}
			
			resultSet.close();
			statement.close();
			
			//select all 
			sql = "SELECT c.client_id, first_name, last_name, phone, email, end_date FROM clients c INNER JOIN memberships m ON c.client_id = m.client_id";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()){
				int clientId = resultSet.getInt("client_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String phone = resultSet.getString("phone");
				String email = resultSet.getString("email");
				Date endDate = resultSet.getDate("end_date");
				
				System.out.print("client id = " + clientId);
				System.out.print(" firstName = " + firstName);
				System.out.print(" lastName = " + lastName);
				System.out.print(" phone = " + phone);
				System.out.print(" email = " + email);
				System.out.println(" endDate = " + endDate);
			}
			
			resultSet.close();
			statement.close();
			
			sql = "UPDATE trainers SET experience = 5 WHERE trainer_id=3";
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			statement.close();
			
			sql = "SELECT trainer_id, first_name, last_name, experience FROM trainers WHERE trainer_id=3";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()){
				int trainerId = resultSet.getInt("trainer_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				int experience = resultSet.getInt("experience");
				
				System.out.print("trainer id = " + trainerId);
				System.out.print(" firstName = " + firstName);
				System.out.print(" lastName = " + lastName);
				System.out.println(" experience = " + experience);
			}
			
			resultSet.close();
			statement.close();
			//10.11.2018
			//18.11.2018
			//01.03.2018
			updateExperienceForTrainer(3, 2);
			getTrainerById(3);
			
			insertClientAndMembership(21, "Transaction", "Rollback", "1123458", "rolback@gm.com", 3, new GregorianCalendar(2018, 10, 10).getTime(), new GregorianCalendar(2018,11,10).getTime(), new GregorianCalendar(18, 10, 2018).getTime());
			
			Trainer trainer = getTrainerObjectById(3);
			if(trainer == null) {
				System.out.println("Trainer does not exist or there was an error in connection to DB");
			} else {
				System.out.println(trainer.toString());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private static void updateExperienceForTrainer(int trainerId, int experience) {
		String sql = "UPDATE trainers SET experience = ? WHERE trainer_id = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, experience);
			preparedStatement.setInt(2, trainerId);
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void getTrainerById(int trainerId) {
		String sql = "SELECT trainer_id, first_name, last_name, experience FROM trainers WHERE trainer_id = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, trainerId);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()){
				int id = resultSet.getInt("trainer_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				int experience = resultSet.getInt("experience");
				
				System.out.print("trainer id = " + id);
				System.out.print(" firstName = " + firstName);
				System.out.print(" lastName = " + lastName);
				System.out.println(" experience = " + experience);
			}
			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static Trainer getTrainerObjectById(int trainerId) {
		String sql = "SELECT trainer_id, first_name, last_name, experience FROM trainers WHERE trainer_id = ?";
		Trainer trainer = null;
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, trainerId);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()){
				trainer = new Trainer();
				trainer.setTrainerId(resultSet.getInt("trainer_id"));
				trainer.setFirstName(resultSet.getString("first_name"));
				trainer.setLastName(resultSet.getString("last_name"));
				trainer.setExperience(resultSet.getInt("experience"));
			}
			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return trainer;
	}
	
	private static void insertClientAndMembership(int clientId, String firstName, String lastName, String phone, String email, int membershipTypeId, Date startDate, Date endDate, Date transactionDate) {
		try {
			connection.setAutoCommit(false);
			String saveClient = "INSERT INTO clients(client_id, first_name, last_name, phone, email) VALUES (?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(saveClient);
			preparedStatement.setInt(1, clientId);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, phone);
			preparedStatement.setString(5, email);
			
			preparedStatement.executeUpdate();
			
			String saveMembership = "INSERT INTO memberships(client_id, membership_type_id, start_date, end_date, transaction_date) VALUES (?,?,?,?,?)";
			PreparedStatement preparedStatement2 = connection.prepareStatement(saveMembership);
			preparedStatement2.setInt(1, clientId);
			preparedStatement2.setInt(2, membershipTypeId);
			long startDateMillis = startDate.getTime();
			preparedStatement2.setDate(3, new java.sql.Date(startDateMillis));
			preparedStatement2.setDate(4, new java.sql.Date(endDate.getTime()));
			preparedStatement2.setDate(5, new java.sql.Date(transactionDate.getTime()));
			
			preparedStatement2.executeUpdate();
			
			connection.commit();
			preparedStatement.close();
			preparedStatement2.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				connection.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
