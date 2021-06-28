package com.example.demo.UserData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT t FROM UserData t WHERE t.userId = ?1")
    User findByUserId(String userId);
    
    @Query("SELECT t FROM UserData t WHERE t.sessionKey = ?1")
    User findBySessionKey(String sessionKey);
}
