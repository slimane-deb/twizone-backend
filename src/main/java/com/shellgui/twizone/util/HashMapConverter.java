package com.shellgui.twizone.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;

@Converter
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> profileInfo) {
        String profileInfoJson = null;
        try {
            profileInfoJson = objectMapper.writeValueAsString(profileInfo);
        } catch (final JsonProcessingException e) {
            System.out.println(e.getMessage());
            // TODO logger
           // logger.error("JSON writing error", e);
        }
        return profileInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String profileInfoJSON) {
        Map<String, Object> profileInfo = null;
        try {
            profileInfo = objectMapper.readValue(profileInfoJSON, Map.class);
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            // TODO logger
          //  logger.error("JSON reading error", e);
        }
        return profileInfo;
    }

}
