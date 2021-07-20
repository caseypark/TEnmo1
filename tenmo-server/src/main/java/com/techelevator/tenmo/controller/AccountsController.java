package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountsDao;
import com.techelevator.tenmo.model.Accounts;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
public class AccountsController {

    private AccountsDao accountsDao;

    public AccountsController(AccountsDao accountsDao) {
        this.accountsDao = accountsDao;
    }

    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public double getBalance(@PathVariable int userId){
        return accountsDao.viewCurrentBalance(userId);
    }



}
