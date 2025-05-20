package com.fcamara.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class IndexController {

    @GetMapping(path = "/is-server-live")
    public HashMap isServerLive() {
        return new HashMap() {{
            put("status", "Server is live!");
        }};
    }

    @GetMapping(path = "/secure-data-1")
    public String secureData1() {
       return "secure-data-1-autenticated-response";
    }

    @GetMapping(path = "/secure-data-2")
    public String secureData2() {
        return "secure-data-2-autenticated-response";
    }
}
