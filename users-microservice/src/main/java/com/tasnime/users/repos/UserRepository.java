package com.tasnime.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tasnime.users.entities.User;
public interface UserRepository extends JpaRepository<User, Long> {
User findByUsername(String username);
}

