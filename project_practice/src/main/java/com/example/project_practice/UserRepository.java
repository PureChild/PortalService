package com.example.project_practice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Page<User> findAllByNameAndPassword(@Param("name") String name, @Param("password") String password, PageRequest pageRequest);
}
