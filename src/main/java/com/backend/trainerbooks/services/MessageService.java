package com.backend.trainerbooks.services;

import com.backend.trainerbooks.entitys.ContactsDAO;
import com.backend.trainerbooks.repositorys.ContactsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ContactsRepository contactsRepository;

    public ContactsDAO saveContact(ContactsDAO contactsDAO) {
        return contactsRepository.save(contactsDAO);
    }

    public List<ContactsDAO> getAllContactsByUidAndOrderByDate(Long uid) {
        return contactsRepository.findAllByUserReceiverIdOrderByDateDesc(uid);
    }

    public void setSeenMessage(Long contactId) {
        contactsRepository.updateSeenMessage(contactId);
    }

    public Optional<ContactsDAO> findById(Long contactId) {
        return contactsRepository.findById(contactId);
    }
}
