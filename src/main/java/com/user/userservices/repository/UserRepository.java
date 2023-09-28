package com.user.userservices.repository;

import com.user.userservices.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserName(String userName);
    boolean existsByUserEmail(String userEmail);
}
