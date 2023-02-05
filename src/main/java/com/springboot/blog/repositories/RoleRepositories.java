package com.springboot.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entities.Role;

public interface RoleRepositories extends JpaRepository<Role, Integer> {

}
