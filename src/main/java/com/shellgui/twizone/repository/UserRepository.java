package com.shellgui.twizone.repository;

import com.shellgui.twizone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    User findByUsername(String username);

    @Override
    <S extends User> S save(S s);
}
