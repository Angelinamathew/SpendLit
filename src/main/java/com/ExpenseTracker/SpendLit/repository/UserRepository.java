package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Role;
import com.ExpenseTracker.SpendLit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findByRole(Role role);
}
