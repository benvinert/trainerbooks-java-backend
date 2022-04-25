package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
@Table(indexes = @Index(name = "email_index_unique", columnList = "email", unique = true))
public class UserDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(length = 15)
    private String username;
    private String password;

    private String email;
    private Boolean isActive;
    private ZonedDateTime createdDate;
    private String roles;//ROLE_USER,ROLE_ADMIN
}
