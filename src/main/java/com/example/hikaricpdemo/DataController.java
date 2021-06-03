package com.example.hikaricpdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DataController {

    private final CpService service;

    @GetMapping("/aggregate")
    public String greeting() {
        service.aggregateOwnerData();
        return "Success";
    }
}
