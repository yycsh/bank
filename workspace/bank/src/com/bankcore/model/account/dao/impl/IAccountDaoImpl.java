package com.bankcore.model.account.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.bankcore.model.account.bean.AccountBean;
import com.bankcore.model.account.dao.IAccountDao;
import com.bankcore.model.account.db.DBConnection;

public class IAccountDaoImpl implements IAccountDao {

	@Override
	public boolean addAccount(AccountBean newUser) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql = "INSERT INTO Accounts VALUES(user_seq.NEXTVAL,'"
						+newUser.getUserName()+"','"
						+newUser.getUserRealName()+"','"
						+newUser.getUserPassWord()+"',"
						+"0.00)";
			int result = statement.executeUpdate(sql);
			dbConn.closeDBConn();
			if (result == 1)
				return true;
			else {
				//System.out.println(result);
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isRegisted(String userName) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql =
				"SELECT * FROM Accounts WHERE UserName='"+ userName +"'";
			ResultSet result = statement.executeQuery(sql);
			if (result.next()) {
				dbConn.closeDBConn();
				return true;
			} else {
				dbConn.closeDBConn();
				return false;
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isValidAccount(String userName, String passWord) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql =
				"SELECT * FROM Accounts WHERE userName='"+ userName +"'";
			ResultSet result = statement.executeQuery(sql);
			
			result.next();
			if (result.getString("userpassword").equals(passWord)) {
				dbConn.closeDBConn();
				return true;
			}
			dbConn.closeDBConn();
			return false;
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addLog(int userID, String content) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql = "INSERT INTO Logs (logid, userid, logcontent) VALUES(log_seq.NEXTVAL,"
						+ userID +",'"
						+content+"')";
			int result = statement.executeUpdate(sql);
			dbConn.closeDBConn();
			if (result == 1)
				return true;
			else {
				System.out.println(result);
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> readLog(int userID) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			System.out.println("ID"+userID);
			String sql =
				"SELECT logcontent||'--'||TO_CHAR(logdate,'yyyy-mm-dd hh24:mi:ss') AS log, logdate FROM logs WHERE userid="
				+ userID
				+ " ORDER BY logdate";
			ResultSet result = statement.executeQuery(sql);
			List<String> logs = new ArrayList<String>();
			String log;
			System.out.println("read log statement operated");
			while (result.next()) {
				log = result.getString("log");
				System.out.println(log);
				logs.add(log);
			}
			dbConn.closeDBConn();
			return logs;
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getUserIDByUserName(String userName) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql =
				"SELECT userid FROM Accounts WHERE userName='"+ userName +"'";
			ResultSet result = statement.executeQuery(sql);
			int userID;
			if (result.next())
					userID = result.getInt("userid");
			else userID = 0;
			dbConn.closeDBConn();
			return userID;
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean withdrawMoney(int userID, BigDecimal amount) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql = "UPDATE Accounts SET AccountBalance="
						+ "-" + amount.setScale(4, BigDecimal.ROUND_HALF_UP)
						+ "+(SELECT AccountBalance FROM Accounts WHERE userid="
						+ userID
						+ ") WHERE userid="
						+ userID;
			int result = statement.executeUpdate(sql);
			dbConn.closeDBConn();
			if (result == 1)
				return true;
			else {
				System.out.println(result);
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveMoney(int userID, BigDecimal amount) {
		// TODO Auto-generated method stub
		try {
			DBConnection dbConn = new DBConnection();
			Connection conn = dbConn.getConnection();
			Statement statement = conn.createStatement();
			String sql = "UPDATE Accounts SET AccountBalance="
						+ amount.setScale(4, BigDecimal.ROUND_HALF_UP)
						+ "+(SELECT AccountBalance FROM Accounts WHERE userid="
						+ userID
						+ ") WHERE userid="
						+ userID;
			int result = statement.executeUpdate(sql);
			dbConn.closeDBConn();
			if (result == 1)
				return true;
			else {
				System.out.println(result);
				return false;
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public BigDecimal getBalanceByuserID(int userID) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				try {
					DBConnection dbConn = new DBConnection();
					Connection conn = dbConn.getConnection();
					Statement statement = conn.createStatement();
					String sql =
						"SELECT accountbalance FROM Accounts WHERE userID="+ userID;
					ResultSet result = statement.executeQuery(sql);
					BigDecimal balance = null;
					if (result.next())
						balance = result.getBigDecimal("accountbalance").setScale(4, BigDecimal.ROUND_HALF_UP);
					else userID = 0;
					dbConn.closeDBConn();
					return balance;
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
	}

}
