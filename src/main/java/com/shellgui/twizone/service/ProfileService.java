package com.shellgui.twizone.service;

import com.shellgui.twizone.model.Profile;
import com.shellgui.twizone.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    public Profile getOne(Integer id) {
        return profileRepository.getOne(id);
    }

    public Profile getOneByUserId(Integer userId) {
        return profileRepository.findByUserId(userId);
    }

    public int updatePosition(Integer profileId, String position) {
        return profileRepository.updatePositionById(profileId, position);
    }

    public int updateStatusById(Integer profileId, Integer status) {
        return profileRepository.updateStatusById(profileId, status);
    }

    public int updateAvailableDateById(Integer profileId, Date date) {
        return profileRepository.updateAvailableDateById(profileId, date);
    }

    /**
     * @param professionId int
     * @return List of Profiles with the given profession
     */
    public List<Profile> getProfilesByProfessionId(Integer professionId) {
        return profileRepository.findAllByProfessionId(professionId);
    }

}
