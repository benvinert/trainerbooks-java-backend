package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.ZonedDateTime;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Cache(region = "users", usage = READ_ONLY)
@Cacheable
@Entity(name="users")
@Table(indexes = @Index(name = "email_index_unique", columnList = "email", unique = true))
public class UserDAO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private ZonedDateTime birthdate;

    private String email;
    private Boolean isActive;
    private ZonedDateTime createdDate;
    private String roles;//ROLE_USER,ROLE_ADMIN
    private String city;
}
