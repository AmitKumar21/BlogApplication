package com.amit.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amit.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
