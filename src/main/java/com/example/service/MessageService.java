package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.Message;

@Service
public class MessageService {
  MessageRepository msgRepo;
  AccountRepository acctRepo;

  @Autowired
  public MessageService(MessageRepository msgRepo, AccountRepository acctRepo) {
    this.acctRepo = acctRepo;
    this.msgRepo = msgRepo;
  }

  public Message createMessage(Message msg) {
    String msg_txt = msg.getMessageText();
    if (msg_txt == null || msg_txt.isEmpty() || msg_txt.length() >= 255) {
      return null;
    }
    if (!acctRepo.findById(msg.getPostedBy()).isPresent()) {
      return msgRepo.save(msg);
    }
    return null;
  }

  public List<Message> getAllMessages() {
    return msgRepo.findAll();
  }

  public Message getMessageById(int id) {
    return msgRepo.findById(id).orElse(null);
  }

  public Message deleteMessage(int id) {
    Optional<Message> msg = msgRepo.findById(id);
    if (msg.isPresent()) {
      msgRepo.deleteById(id);
      return msg.get();
    }
    return null;
  }

  public Integer updateMessage(int id, String msg_txt) {
    if (msg_txt == null || msg_txt.isEmpty() || msg_txt.length() >= 255) {
      return null;
    }
    Message msg = msgRepo.findById(id).get();
    Integer rows_updated = msgRepo.updateMessageText(id, msg_txt);
    msgRepo.save(msg);
    return rows_updated;
  }

  public List<Message> getAllMessagesByAccountId(int id) {
    return msgRepo.findByPosted_by(id);
  }
}
