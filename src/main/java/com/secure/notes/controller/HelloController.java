package com.secure.notes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static com.secure.notes.constant.ControllerConstant.*;

@RestController
@RequestMapping("/v1/hello")
public class HelloController {

    @GetMapping("/{name}")
    public Map<String, String> sayHelloEndpoint(@PathVariable("name") String name) {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(MESSAGE, String.format(HELLO_MESSAGE, name.toUpperCase()));
        responseMap.put(STATUS, HttpStatus.OK.toString());
        return responseMap;
    }

    @GetMapping("/about")
    public Map<String, String> getAboutDetails() {
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(MESSAGE, ABOUT_MESSAGE);
        responseMap.put(STATUS, HttpStatus.OK.toString());
        return responseMap;
    }
}
