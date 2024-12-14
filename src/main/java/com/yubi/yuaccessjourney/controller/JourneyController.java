package com.yubi.yuaccessjourney.controller;

import com.yubi.yuaccessjourney.model.Journey;
import com.yubi.yuaccessjourney.service.JourneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    private final JourneyService journeyService;

    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    // Get journey by ID
    @GetMapping("/{id}")
    public ResponseEntity<Journey> getJourneyById(@PathVariable Long id) {
        Optional<Journey> journey = journeyService.getJourneyById(id);
        return journey.map(ResponseEntity::ok) // If journey is found, return it
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not, return 404
    }

    // Create a new journey
    // Correcting the @PostMapping mapping
    @PostMapping
    public ResponseEntity<Journey> createJourney(@RequestBody Journey journey) {
        Journey createdJourney = journeyService.saveJourney(journey);
        return ResponseEntity
                .status(HttpStatus.CREATED) // Return 201 status code
                .body(createdJourney); // Return the created journey object
    }


    // Update a journey
    @PutMapping("/{id}")
    public ResponseEntity<Journey> updateJourney(@PathVariable Long id, @RequestBody Journey journey) {
        Journey updatedJourney = journeyService.updateJourney(id, journey);
        if (updatedJourney != null) {
            return ResponseEntity.ok(updatedJourney); // Return the updated journey
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if the journey is not found
        }
    }



    // Delete a journey by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJourney(@PathVariable Long id) {
        boolean isDeleted = journeyService.deleteJourney(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content if deletion was successful
        } else {
            return ResponseEntity.notFound().build(); // 404 if the journey is not found
        }
    }
}
