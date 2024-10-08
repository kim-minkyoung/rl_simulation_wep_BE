package com.example.rl_simulation_wep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RlSimulationWepApplication {

    @RequestMapping("/")
    String home() {
        return "hello world";
    }

    public static void main(String[] args) {
        SpringApplication.run(RlSimulationWepApplication.class, args);
    }

}
