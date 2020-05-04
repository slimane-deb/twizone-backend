package com.shellgui.twizone.controller;

import com.shellgui.twizone.model.Profile;
import com.shellgui.twizone.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @RequestMapping(path = "/profiles",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Profile> getUsers() {
        return profileService.getAll();
    }

    @RequestMapping(path = "/profiles/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Profile getUserById(@PathVariable("id") Integer id) {
        return profileService.getOneByUserId(id);
    }

    @RequestMapping(path = "/profiles/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateProfileStatus(@PathVariable("id") Integer id, @RequestBody Map<String, Integer> req) {
        Integer status = req.get("status");
        return profileService.updateStatusById(id, status);
    }

    @RequestMapping(path = "/profiles/{id}/date",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updateProfileAvailableDate(@PathVariable("id") Integer id, @RequestBody Map<String, String> req) {
        String dateString = req.get("availableFrom");
        Calendar parsedDate = javax.xml.bind.DatatypeConverter.parseDate(dateString);
        Date date = parsedDate.getTime();
        return profileService.updateAvailableDateById(id, date);
    }

    @RequestMapping(path = "/profiles/{id}/position",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public int updatePosition(@PathVariable("id") Integer id, @RequestBody Map<String, String> req) {
        String position = req.get("position");
        return profileService.updatePosition(id, position);
    }

    @RequestMapping(path = "/profiles/profession",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Profile> getProfilesByProfession(@RequestBody Map<String, String> req) {
        Integer professionId = Integer.parseInt(req.get("profession"));
        return profileService.getProfilesByProfessionId(professionId);
    }

}
