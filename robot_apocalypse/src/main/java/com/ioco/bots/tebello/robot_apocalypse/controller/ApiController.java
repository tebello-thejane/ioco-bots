package com.ioco.bots.tebello.robot_apocalypse.controller;

import com.ioco.bots.tebello.robot_apocalypse.model.Location;
import com.ioco.bots.tebello.robot_apocalypse.model.Survivor;
import com.ioco.bots.tebello.robot_apocalypse.exception.ResourceNotFoundException;
import com.ioco.bots.tebello.robot_apocalypse.repository.SurvivorRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private SurvivorRepository survivorRepository;

    @GetMapping("/survivors")
    @Operation(summary = "Retrieve all survivors", tags = {"survivors"})
    public List<Survivor> getAllSurvivors() {
        return survivorRepository.findAll();
    }

    @GetMapping("/survivors/{id}")
    @Operation(summary = "Retrieve survivor by ID", tags = {"survivors"})
    public Survivor getSurvivorById(@PathVariable("id") Long survivorId)
            throws ResourceNotFoundException {
        return survivorRepository
                .findById(survivorId)
                .orElseThrow(() -> new ResourceNotFoundException("Survivor not found by ID: " + survivorId));
    }

    @PostMapping("/survivors")
    @Operation(summary = "Create a new survivor", tags = {"survivors"})
    public Survivor newSurvivor(@RequestBody Survivor newSurvivor) {
        return survivorRepository.save(newSurvivor);
    }

    @PatchMapping("/survivors/{id}/infected")
    @Operation(summary = "Mark specified survivor as infected", tags = {"survivors"})
    public Survivor updateSurvivorInfected(@PathVariable("id") Long survivorId)
            throws ResourceNotFoundException {
        final Survivor theSurvivor = survivorRepository
                .findById(survivorId)
                .orElseThrow(() -> new ResourceNotFoundException("Survivor not found by ID: " + survivorId));

        theSurvivor.setInfected(true);

        return survivorRepository.save(theSurvivor);
    }

    @PatchMapping("/survivors/{id}/location/{long}/{lat}")
    @Operation(summary = "Update location of survivor", tags = {"survivors"})
    public Survivor updateSurvivorLocation(
            @PathVariable("id") Long survivorId,
            @PathVariable("long") float longitude,
            @PathVariable("lat") float latitude
    )
            throws ResourceNotFoundException {
        final Survivor theSurvivor = survivorRepository
                .findById(survivorId)
                .orElseThrow(() -> new ResourceNotFoundException("Survivor not found by ID: " + survivorId));

        theSurvivor.setLocation(new Location(longitude, latitude));

        return survivorRepository.save(theSurvivor);
    }
}
