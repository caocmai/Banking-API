package dev.mai.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private static Connection conn = null;

	public static Connection getConnection(String endpoint, String inputUsername, String inputPassword) {

		if (conn == null) {
			// then will establish connection
//			String endpoint = "caorevaturedb.c39sfp4pzzjr.us-east-2.rds.amazonaws.com";
			

			String url = "jdbc:postgresql://" + endpoint + "/postgres";
			String username = inputUsername;
			String password = inputPassword;

			try {
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return conn;

	}

}
