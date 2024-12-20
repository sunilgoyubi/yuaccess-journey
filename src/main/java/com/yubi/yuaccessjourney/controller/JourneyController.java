package com.yubi.yuaccessjourney.controller;

import com.yubi.yuaccessjourney.model.Journey;
import com.yubi.yuaccessjourney.model.User;
import com.yubi.yuaccessjourney.service.JourneyService;
import com.yubi.yuaccessjourney.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    private final JourneyService journeyService;
    private final UserService userService;

    public JourneyController(JourneyService journeyService, UserService userService) {
        this.journeyService = journeyService;
        this.userService = userService;
    }

    // Get journey by ID
    @GetMapping("/{id}")
    public ResponseEntity<Journey> getJourneyById(@PathVariable Long id) {
        Optional<Journey> journey = journeyService.getJourneyById(id);
        return journey.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new journey
    @PostMapping
    public ResponseEntity<Journey> createJourney(@RequestBody Journey journey) {
        Optional<User> user = userService.findByEmail(journey.getUserEmail());

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        journey.setUser(user.get());
        Journey createdJourney = journeyService.saveJourney(journey);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJourney);
    }

    // Update a journey
    @PutMapping("/{id}")
    public ResponseEntity<Journey> updateJourney(@PathVariable Long id, @RequestBody Journey journey) {
        Journey updatedJourney = journeyService.updateJourney(id, journey);
        if (updatedJourney != null) {
            return ResponseEntity.ok(updatedJourney);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a journey by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJourney(@PathVariable Long id) {
        boolean isDeleted = journeyService.deleteJourney(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}