package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private MessageService messageService;

  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
  @PostMapping(value = "/register")
  public ResponseEntity<Account> register(@RequestBody Account acct) throws Exception {
    Account temp = accountService.registerUser(acct);
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    }
    return ResponseEntity.status(400).build();
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Account> login(@RequestBody Account account) {
    Account accountLogged = accountService.login(account);
    if (accountLogged != null) {
      return ResponseEntity.ok().body(accountLogged);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
  }

  @ExceptionHandler({ Exception.class })
  public String handleException() {
    return "Generic exception";
  }
}
