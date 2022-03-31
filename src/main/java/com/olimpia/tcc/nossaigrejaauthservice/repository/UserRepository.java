package com.olimpia.tcc.nossaigrejaauthservice.repository;

import com.olimpia.tcc.nossaigrejaauthservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
