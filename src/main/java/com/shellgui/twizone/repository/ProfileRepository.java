package com.shellgui.twizone.repository;

import com.shellgui.twizone.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    List<Profile> findAll();

    List<Profile> findAllByProfessionId(Integer professionId);

    @Override
    Profile getOne(Integer integer);

    Profile findByUserId(Integer id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO profiles " +
            "(user_id, email, first_name, last_name, phone_number, profession_id, registration_date) " +
            "VALUES (?1, ?2, ?3, ?4, ?5, ?6, NOW())",
            nativeQuery = true)
    void save(
            Integer userId,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            Integer professionId,
            String position
    );

    // TODO available_from = NULL ? figure it out
    @Modifying
    @Transactional
    @Query("UPDATE Profile c SET c.status = ?2, available_from = NULL WHERE c.id = ?1")
    int updateStatusById(Integer profileId, Integer status);

    @Modifying
    @Transactional
    @Query("UPDATE Profile c SET c.availableFrom = ?2 WHERE c.id = ?1")
    int updateAvailableDateById(Integer profileId, Date date);

    @Modifying
    @Transactional
    @Query(value = "UPDATE profiles SET position = CAST(?2 AS json) WHERE id = ?1", nativeQuery = true)
    int updatePositionById(Integer profileId, String position);

}
