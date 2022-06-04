package com.backend.trainerbooks.controllers;

import com.backend.trainerbooks.DTOS.ContactsDTO;
import com.backend.trainerbooks.DTOS.NotificationDTO;
import com.backend.trainerbooks.annotations.SecuredEndPoint;
import com.backend.trainerbooks.entitys.AccountDAO;
import com.backend.trainerbooks.entitys.ContactsDAO;
import com.backend.trainerbooks.entitys.UserDAO;
import com.backend.trainerbooks.exceptions.NoPermissionException;
import com.backend.trainerbooks.exceptions.NotFoundEntityException;
import com.backend.trainerbooks.jwt.JWTUtils;
import com.backend.trainerbooks.mappers.IMapDAOToDTOAccounts;
import com.backend.trainerbooks.mappers.IMapDTOToDAOAccounts;
import com.backend.trainerbooks.services.AccountService;
import com.backend.trainerbooks.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.backend.trainerbooks.enums.JWTEnum.AUTHORIZATION;

@RequiredArgsConstructor
@RequestMapping("/messages")
@RestController
public class MessageControllers {
    private final AccountService accountService;
    private final IMapDTOToDAOAccounts mapDTOToDAOAccounts;
    private final IMapDAOToDTOAccounts mapDAOToDTOAccounts;
    private final MessageService messageService;
    private final JWTUtils jwtUtils;


    @SecuredEndPoint
    @PostMapping("/add-contact-message")
    public NotificationDTO postContactMessage(HttpServletRequest request, @Valid @RequestBody ContactsDTO contactsDTO) throws NotFoundEntityException {
        Long userId = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        ContactsDAO contactsDAO = mapDTOToDAOAccounts.map(contactsDTO);
        Optional<AccountDAO> accountDAO = accountService.findById(contactsDAO.getAccountReceiver().getId());
        if(accountDAO.isPresent()) {
            contactsDAO.setUserReceiver(new UserDAO());
            contactsDAO.setUserSender(new UserDAO());
            contactsDAO.getUserSender().setId(userId);
            contactsDAO.getUserReceiver().setId(accountDAO.get().getUserDAO().getId());
            contactsDAO.setSeen(false);
            contactsDAO.setDate(ZonedDateTime.now());
        } else {
            throw new NotFoundEntityException("Account Receiver Not Found." + contactsDAO.getAccountReceiver().getId());
        }
        contactsDAO = messageService.saveContact(contactsDAO);
        contactsDTO = mapDAOToDTOAccounts.map(contactsDAO);
        return NotificationDTO.builder().date(ZonedDateTime.now()).notificationMessage(contactsDTO).build();
    }

    @SecuredEndPoint
    @GetMapping("/get-all-contacts-by-uid/{uid}")
    public List<ContactsDTO> getAllContactsByAccountId(HttpServletRequest request, @PathVariable String uid) throws NoPermissionException {
        Long userIdAuthorization = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        List<ContactsDTO> contactsDTOList = new LinkedList<>();
        if(Long.parseLong(uid) == userIdAuthorization) {
            List<ContactsDAO> contactsDAOS = messageService.getAllContactsByUidAndOrderByDate(Long.parseLong(uid));
            contactsDTOList = mapDAOToDTOAccounts.mapDAOToDTOContacts(contactsDAOS);
        } else {
            throw new NoPermissionException("No Permission to get data about" + uid);
        }

        return contactsDTOList;
    }

    @SecuredEndPoint
    @PutMapping("/update-seen-message-by-id/{contactId}")
    public ResponseEntity<?> setSeenMessageByContactId(HttpServletRequest request,@PathVariable String contactId) {
        Long userIdAuthorization = jwtUtils.getIdFromToken(request.getHeader(AUTHORIZATION.getValue()));
        Long contactIdLong = Long.parseLong(contactId);
        Optional<ContactsDAO> contactsDAO = messageService.findById(contactIdLong);
        if(contactsDAO.isPresent() && Objects.equals(userIdAuthorization, contactsDAO.get().getUserReceiver().getId())) {
            messageService.setSeenMessage(contactIdLong);
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK);
        }
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
