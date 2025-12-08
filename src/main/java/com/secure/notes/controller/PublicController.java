package com.secure.notes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.secure.notes.constant.ControllerConstant.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @GetMapping("/info")
    public Map<String, String> getAppInfo() {
        Map<String, String> response = new HashMap<>();
        response.put(MESSAGE, APP_INFO);
        response.put(STATUS, String.valueOf(HttpStatus.OK.value()));
        return response;
    }
}
