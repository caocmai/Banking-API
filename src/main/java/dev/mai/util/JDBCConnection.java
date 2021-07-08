package dev.mai.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

	private static Connection conn = null;

	public static Connection getConnection() {

		if (conn == null) {
			// then will establish connection
			

			
			try {
				conn = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return conn;

	}

}
