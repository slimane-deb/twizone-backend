package com.shellgui.twizone.controller;

import com.shellgui.twizone.dto.ProfessionDTO;
import com.shellgui.twizone.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    @RequestMapping(path = "/professions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<ProfessionDTO> getProfessions() {
        return professionService.getProfessions();
    }

}
