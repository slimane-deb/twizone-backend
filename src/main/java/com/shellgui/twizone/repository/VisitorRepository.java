package com.shellgui.twizone.repository;

import com.shellgui.twizone.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Integer> {

    List<Visitor> findAll();

    Visitor findByUserId(Integer id);

    @Override
    <S extends Visitor> S save(S s);
}
