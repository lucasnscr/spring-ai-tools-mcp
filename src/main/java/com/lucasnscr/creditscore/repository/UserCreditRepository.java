package com.lucasnscr.creditscore.repository;

import com.lucasnscr.creditscore.model.UserCredit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCreditRepository extends CrudRepository<UserCredit, Integer> {
}
