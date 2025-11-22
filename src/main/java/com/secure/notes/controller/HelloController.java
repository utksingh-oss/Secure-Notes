package com.secure.notes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import static com.secure.notes.constant.ControllerConstant.*;

@RestController
public class HelloController {

    @GetMapping("/hello/{name}")
    public HashMap<String, String> sayHelloEndpoint(@PathVariable("name") String name) {
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put(MESSAGE, String.format(HELLO_MESSAGE, name));
        responseMap.put(STATUS, HttpStatus.OK.toString());
        return responseMap;
    }

}
