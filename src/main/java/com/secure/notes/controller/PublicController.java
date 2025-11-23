package com.secure.notes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.secure.notes.constant.ControllerConstant.*;

@RestController
@RequestMapping("/v1/public")
public class PublicController {

    @GetMapping("/copyright-info")
    public Map<String, String> getCopyrightInfo() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(MESSAGE, COPYRIGHT_INFO);
        responseMap.put(STATUS, HttpStatus.OK.toString());
        return responseMap;
    }
}
