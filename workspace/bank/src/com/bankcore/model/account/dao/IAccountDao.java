package com.bankcore.model.account.dao;

import java.math.BigDecimal;
import java.util.List;

import com.bankcore.model.account.bean.AccountBean;

public interface IAccountDao {
	public boolean addAccount(AccountBean newUser);
	public boolean isRegisted(String userName);
	public int getUserIDByUserName(String userName);
	public boolean isValidAccount(String userName, String passWord);
	public boolean addLog(int userID, String content);
	public List<String> readLog(int userID);
	public boolean withdrawMoney(int userID, BigDecimal amount);
	public boolean saveMoney(int userID, BigDecimal amount);
	public BigDecimal getBalanceByuserID(int userID);
}
