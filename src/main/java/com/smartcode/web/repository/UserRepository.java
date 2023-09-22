package com.smartcode.web.repository;

import com.smartcode.web.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

    UserEntity findByEmailAndCode(String email, String code);

    void deleteById(Integer id);

}
