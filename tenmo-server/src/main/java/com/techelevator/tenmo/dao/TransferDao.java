package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

   List<Transfer> viewTransferHistory(int userId);

   List<User> viewUsers();

   double sendTEBucks(Long senderId, Long receiverId, double amount);
}
