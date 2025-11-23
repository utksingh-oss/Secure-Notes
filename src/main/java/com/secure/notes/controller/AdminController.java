package com.secure.notes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.secure.notes.constant.ControllerConstant.*;

/*
 * This particular controller is under maintenance
 */
@RestController
@RequestMapping("/v1/admin")
public class AdminController {
    @GetMapping
    public Map<String, String> getAdmin() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(MESSAGE, String.format(HELLO_MESSAGE, ADMIN));
        responseMap.put(STATUS, HttpStatus.OK.toString());
        return responseMap;
    }
}
