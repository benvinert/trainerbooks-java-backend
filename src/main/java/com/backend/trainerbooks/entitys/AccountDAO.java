package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;


@Data
@Entity(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDAO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime createdDate;
    private String description;
    private String category;
    private Long rank;

    @ManyToOne
    private UserDAO userDAO;

    //isActive
    //isPublic
}
