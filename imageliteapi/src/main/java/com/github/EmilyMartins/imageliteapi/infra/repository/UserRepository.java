package com.github.EmilyMartins.imageliteapi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.EmilyMartins.imageliteapi.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
    User findByEmail(String email);
    
} 