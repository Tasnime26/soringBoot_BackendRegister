package com.tasnime.users.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tasnime.users.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface UserRepository extends JpaRepository<User, Long> {
User findByUsername(String username);
Optional<User> findByEmail(String email);//jautorise pas 2utilisateur avec le mem email et optinal il va retourne un user si il exist si non null
//gestion user
@Modifying
@Query("DELETE FROM User u WHERE u.user_id = :userId")
void deleteByUserId(@Param("userId") Long userId);

@Query("select u from User u where u.user_id = ?1")
User findUserById(Long id);
}

