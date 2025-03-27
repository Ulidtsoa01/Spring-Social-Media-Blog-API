package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.postedBy = ?1")
    List<Message> findByPosted_by(Integer accountId);

    @Modifying
    @Query("UPDATE Message m SET m.messageText = ?2 WHERE m.messageId = ?1")
    int updateMessageText(int messageId, String messageText);
}
