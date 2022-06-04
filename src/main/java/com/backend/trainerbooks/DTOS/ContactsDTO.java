package com.backend.trainerbooks.DTOS;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@Data
public class ContactsDTO {
    private Long id;
    private UserDTO userSender;
    private UserDTO userReceiver;
    private AccountDTO accountReceiver;
    private ZonedDateTime date;

    @NotBlank
    private String senderFullName;
    @NotBlank
    @Size(min = 10)
    private String senderPhoneNumber;

    @Length(max = 300)
    @Nullable
    private String textMessage;

    private Boolean seen;
}
