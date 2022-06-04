package com.backend.trainerbooks.entitys;

import com.backend.trainerbooks.DTOS.AccountDTO;
import com.backend.trainerbooks.DTOS.UserDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Data
@Entity(name = "contacts")
public class ContactsDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserDAO userSender;
    @OneToOne
    private UserDAO userReceiver;
    @OneToOne
    private AccountDAO accountReceiver;
    private ZonedDateTime date;
    @NotBlank
    private String senderFullName;

    @NotBlank
    private String senderPhoneNumber;

    @Length(max = 300)
    @Nullable
    private String textMessage;


    private Boolean seen;
}
