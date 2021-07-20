package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDao implements AccountsDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public double viewCurrentBalance(int userId) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        double balance = 0.0;
        try {
            if (result.next()) {
                balance = result.getDouble("balance");
            }
        } catch (DataAccessException ex){
            System.out.println(ex.getMessage());
        }
        return balance;
    }
    /*
    public double getBalance(int userId) {
    String sql = "SELECT balance FROM accounts WHERE user_id = ?;";
    SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
    double balance = 0.0;
    try {
        if (result.next()) {
            balance = result.getDouble("balance");
        }
    } catch (DataAccessException ex){
        System.out.println(ex.getMessage());
    }
        return balance;
}
     */



    private Accounts mapRowToAccount(SqlRowSet row) {
        Accounts accounts = new Accounts();

        accounts.setAccountId(row.getLong("account_id"));
        accounts.setUserId(row.getLong("user_id"));
        accounts.setBalance(row.getBigDecimal("balance"));

        return accounts;
    }
}
