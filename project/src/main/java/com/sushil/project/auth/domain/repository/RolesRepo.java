package com.sushil.project.auth.domain.repository;

import com.sushil.project.auth.domain.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolesRepo extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);

}