package com.shellgui.twizone.service;

import com.shellgui.twizone.dto.ProfessionDTO;
import com.shellgui.twizone.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessionService {

    @Autowired
    private ProfessionRepository professionRepository;

    public List<ProfessionDTO> getProfessions() {
        return professionRepository.findAll();
    }

}
