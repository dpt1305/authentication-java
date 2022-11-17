package com.tungjj.user.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tungjj.user.exception.InvalidRequestException;
import com.tungjj.user.utils.ObjectValidator;

public abstract class AbstractService<r> {
    @Autowired
    protected r repository;

    protected ObjectMapper objectMapper;

    @Autowired
    protected ObjectValidator objectValidator;

    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected <T> void validate(T request) {
        boolean isError = false;
        Map<String, String> errors = objectValidator.validateRequestThenReturnMessage(generateError(request.getClass()),
                request);
        for (Map.Entry<String, String> items : errors.entrySet()) {
            if (items.getValue().length() > 0) {
                isError = true;
                break;
            }
        }
        if (isError) {
            throw new InvalidRequestException(errors, "Invalid request");
        }
    }

    protected Map<String, String> generateError(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> result = new HashMap<>();
        for (Field field : fields) {
            result.put(field.getName(), "");
        }
        return result;
    }

}
