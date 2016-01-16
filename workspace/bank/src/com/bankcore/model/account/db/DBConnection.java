package com.bankcore.model.account.db;


import java.sql.*;

public class DBConnection {
	private String className = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String userName = "sys as sysdba";
	private String passWord = "wh012607";
	private Connection connection;
	
	public DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName(className);
		connection = DriverManager.getConnection(url, userName, passWord);
		System.out.println("数据库连接成功！");
		String df;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void closeDBConn() throws SQLException {
		if (!connection.isClosed())
			connection.close();
	}
}

