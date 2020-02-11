package com.example.demo.db;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Student;

//@Configuration
//@PropertySource("classpath:application.properties")
public class DBHandler {

	private static final String url = "jdbc:postgresql://localhost/postgres";
	private static final String user = "postgres";
	private static final String password = "root";
	private static Connection conn = null;
	private static DBHandler handler = null;
//	@Value("${csvPath}")
//	private String csvPath = null;

	/**
	 * preliminary requirement for being Singleton
	 */
	private DBHandler() {
		super();
		connect();
	}

	/**
	 * Connect to the PostgreSQL database
	 *
	 * @return a Connection object
	 */
	private static Connection connect() {

		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	public static synchronized DBHandler getInstance() {
		if (handler == null) {
			handler = new DBHandler();
		}
		return handler;
	}

	public void getAllStudentsAndSavetoFile(String filePath) {
		List<Student> studentRecords = new ArrayList<Student>();
		Statement statement = null;
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ResultSet result = statement.executeQuery("select * from public.mystudents");
			while (result.next()) {
				int id = result.getInt(1);
				String name = result.getString(2);
				String course = result.getString(3);

				Student student = new Student(id, name, course);

				System.out.println(id + " " + name + " " + course);
				studentRecords.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
//		return null;
		try {
			printToCsv(studentRecords, filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void printToCsv(List<Student> students, String fileNameWithPath) throws Exception {

		File csvOutputFile = new File(fileNameWithPath);
		if (!csvOutputFile.exists()) {
			csvOutputFile.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(csvOutputFile, false);

		for (Student mapping : students) {
			fileWriter.write(mapping + "\n");
		}

		fileWriter.close();

	}

//	public static void main(String[] args) {
//
//		DBHandler handler = new DBHandler();
//		handler.getAllStudents();
//	}

}
