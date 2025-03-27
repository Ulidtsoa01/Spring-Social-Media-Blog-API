package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.AccountRepository;
import com.example.entity.Account;

public class AccountService {
  AccountRepository acctRepo;

  @Autowired
  public AccountService(AccountRepository acctRepo) {
    this.acctRepo = acctRepo;
  }

  public Account registerUser(Account acct) {
    if (acct.getUsername() == null | acct.getUsername().isEmpty() | acct.getPassword().length() < 4) {
      return null;
    }
    if (acctRepo.findByUsername(acct.getUsername()) == null) {
      return acctRepo.save(acct);
    }
    return null;
  }

  public Account login(Account acct) {
    return acctRepo.findByUsernameAndPassword(acct.getUsername(), acct.getPassword());
  }
}
