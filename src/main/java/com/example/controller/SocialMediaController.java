package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ConflictException;
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

  @PostMapping(value = "/register")
  public ResponseEntity<Account> register(@RequestBody Account acct) throws ConflictException {
    try {
      Account temp = accountService.registerUser(acct);
      if (temp != null) {
        return ResponseEntity.ok().body(temp);
      }
      return ResponseEntity.status(400).build();
    } catch (ConflictException e) {
      return ResponseEntity.status(409).build();
    }
  }

  @PostMapping(value = "/login")
  public ResponseEntity<Account> login(@RequestBody Account acct) {
    Account temp = accountService.login(acct);
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    } else {
      return ResponseEntity.status(401).body(null);
    }
  }

  @PostMapping(value = "/messages")
  public ResponseEntity<Message> createMessage(@RequestBody Message msg) {
    Message temp = messageService.createMessage(msg);
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    } else {
      return ResponseEntity.status(400).build();
    }
  }

  @GetMapping(value = "/messages")
  public ResponseEntity<List<Message>> getAllMessages() {
    List<Message> temp = messageService.getAllMessages();
    return ResponseEntity.ok().body(temp);
  }

  @GetMapping(value = "/messages/{message_id}")
  public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
    Message temp = messageService.getMessageById(message_id);
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    }
    return ResponseEntity.ok().build();
  }

  @DeleteMapping(value = "/messages/{message_id}")
  public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
    Integer temp = messageService.deleteMessage(message_id);
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    }
    return ResponseEntity.ok().build();
  }

  @PatchMapping(value = "/messages/{message_id}")
  public ResponseEntity<Integer> updateMessage(@RequestBody Message msg, @PathVariable int message_id) {
    Integer temp = messageService.updateMessage(message_id, msg.getMessageText());
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    }
    return ResponseEntity.status(400).build();
  }

  @GetMapping(value = "/accounts/{account_id}/messages")
  public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int account_id) {
    List<Message> temp = messageService.getAllMessagesByAccountId(account_id);
    if (temp != null) {
      return ResponseEntity.ok().body(temp);
    }
    return ResponseEntity.ok().build();
  }

  @ExceptionHandler({ Exception.class })
  public String handleException() {
    return "Generic exception";
  }
}
