package com.ioco.bots.tebello.robot_apocalypse.controller;

import com.ioco.bots.tebello.robot_apocalypse.entity.Survivor;
import com.ioco.bots.tebello.robot_apocalypse.repository.SurvivorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SurvivorController {
    @Autowired
    private SurvivorRepository repository;

    @GetMapping("/survivors")
    public List<Survivor> all() {
        return repository.findAll();
    }
}
