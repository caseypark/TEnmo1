package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<Transfer> viewTransferHistory(int userId) {
        String sql = "SELECT t.transfer_id, t.account_from, t.account_to, t.amount, t.transfer_type_id, t.transfer_status_id FROM transfers t " +
                "JOIN accounts a ON a.account_id = t.account_from OR a.account_id = t.account_to " +
                "WHERE a.user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        String sql2 = "SELECT username FROM users u " +
                "JOIN accounts a ON u.user_id = a.user_id " +
                "JOIN transfers t ON t.account_from = a.account_id " +
                " WHERE ? = t.account_from";
        SqlRowSet senderUsername = null;

        String sql3 = "SELECT username FROM users u " +
                "JOIN accounts a ON u.user_id = a.user_id " +
                "JOIN transfers t ON t.account_to = a.account_id " +
                " WHERE ? = t.account_to";
        SqlRowSet receiverUsername = null;
        List<Transfer> transfers = new ArrayList<>();
        try {
            while (result.next()) {
                Transfer transfer = new Transfer();
                transfer = mapRowToTransfer(result);
                transfers.add(transfer);
                receiverUsername = jdbcTemplate.queryForRowSet(sql3, transfer.getAccount_to());
                senderUsername= jdbcTemplate.queryForRowSet(sql2, transfer.getAccount_from());
                senderUsername.beforeFirst();
                while(senderUsername.next()) {
                    transfer.setSenderUsername(senderUsername.getString("username"));
               }
               receiverUsername.beforeFirst();
               while (receiverUsername.next()) {
                    transfer.setReceiverUsername(receiverUsername.getString("username"));
               }
            }
        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
        }

        return transfers;
    }

    private Transfer mapRowToTransfer(SqlRowSet row) {
        Transfer transfers = new Transfer();

        transfers.setTransfer_id(row.getLong("transfer_id"));
        transfers.setAccount_from(row.getLong("account_from"));
        transfers.setAccount_to(row.getLong("account_to"));
        transfers.setAmount(row.getDouble("amount"));
        transfers.setTransferType(row.getLong("transfer_type_id"));
        transfers.setTransferStatus(row.getLong("transfer_status_id"));

        return transfers;
    }

    public List<User> viewUsers() {
        String sql = "SELECT username, user_id FROM users;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        List<User> users = new ArrayList<User>();
        try {
            while(results.next()) {
                User user = new User();
                user.setUsername(results.getString("username"));
                user.setId(results.getLong("user_id"));
                users.add(user);
            }
        } catch(DataAccessException ex) {
            System.out.println("Couldn't access database.");
        } catch(NullPointerException ex) {
            System.out.println("Could not access database.");
        }
        return users;
    }

    @Override
    public double sendTEBucks(Long senderId, Long receiverId, double amount) {
        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        Double senderBalance, receiverBalance;
        try {
            senderBalance = (Double) jdbcTemplate.queryForObject(sql, new Object[]{senderId}, Double.class);
            receiverBalance = (Double) jdbcTemplate.queryForObject(sql, new Object[]{receiverId}, Double.class);
            if (senderBalance >= amount) {
                senderBalance -= amount;
                receiverBalance += amount;

                sql = "UPDATE accounts SET balance = ? WHERE user_id = ?; " +
                        "UPDATE accounts SET balance = ? WHERE user_id = ?; ";

                jdbcTemplate.update(sql, senderBalance, senderId, receiverBalance, receiverId);

                sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)" +
                        "VALUES (2, 2, (SELECT account_id FROM accounts WHERE user_id = ?), " +
                        "(SELECT account_id FROM accounts WHERE user_id = ?), ?)";

                jdbcTemplate.update(sql, senderId, receiverId, amount);

            }
        } catch(NullPointerException ex) {
            return 0;
        }

        return 0;
    }
}
