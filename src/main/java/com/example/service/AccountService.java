package com.example.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.ConflictException;

@Service
public class AccountService {
  AccountRepository acctRepo;

  @Autowired
  public AccountService(AccountRepository acctRepo) {
    this.acctRepo = acctRepo;
  }

  public Account registerUser(Account acct) throws ConflictException {
    if (acct.getUsername() == null | acct.getUsername().isEmpty() | acct.getPassword().length() < 4) {
      return null;
    }
    if (acctRepo.findByUsername(acct.getUsername()) != null) {
      throw new ConflictException();
    }
    return acctRepo.save(acct);
  }

  public Account login(Account acct) {
    return acctRepo.findByUsernameAndPassword(acct.getUsername(), acct.getPassword());
  }
}
