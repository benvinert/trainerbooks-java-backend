package com.backend.trainerbooks.repositorys;

import com.backend.trainerbooks.entitys.AccountDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountDAO,Long> {

}
