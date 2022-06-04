package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.ContactsDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactsRepository extends JpaRepository<ContactsDAO,Long> {
    List<ContactsDAO> findAllByUserReceiverIdOrderByDateDesc(Long uid);

    @Modifying
    @Transactional
    @Query("update contacts c set c.seen = 1 where c.id = :contactId")
    void updateSeenMessage(Long contactId);
}
