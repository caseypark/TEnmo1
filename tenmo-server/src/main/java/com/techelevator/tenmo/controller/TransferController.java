package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Accounts;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao transferDao) {this.transferDao = transferDao;
    }

    @RequestMapping(path = "/history/{userId}", method = RequestMethod.GET)
    public Transfer[] viewTransferHistory(@PathVariable int userId){
        return transferDao.viewTransferHistory(userId).toArray(new Transfer[0]);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public User[] viewUsers(){
        User[] users = transferDao.viewUsers().toArray(new User[0]);
        return users;
    }
//{receiverId}/{inputAmount} @PathVariable Long receiverId, @PathVariable double inputAmount
    @RequestMapping(path = "/transfer/{userId}/{receiverId}/{amount}", method = RequestMethod.POST)
    public double sendTEBucks(@Valid @PathVariable Long userId, @PathVariable Long receiverId, @PathVariable double amount) {
        return transferDao.sendTEBucks(userId, receiverId, amount);
    }
}
