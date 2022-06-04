package com.backend.trainerbooks.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;


@Data
@Entity(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
@Cache(region = "accounts", usage = READ_WRITE)
@Cacheable
@Builder
public class AccountDAO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime createdDate;
    private String category;
    private Long rankAccount;

    @ManyToOne(cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    @Cache(usage= READ_WRITE, region = "users" )
    private UserDAO userDAO;

    //isActive
    //isPublic
}
