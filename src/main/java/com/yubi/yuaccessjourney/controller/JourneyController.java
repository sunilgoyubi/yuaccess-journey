package com.yubi.yuaccessjourney.controller;

import com.yubi.yuaccessjourney.model.Journey;
import com.yubi.yuaccessjourney.security.JwtTokenProvider;
import com.yubi.yuaccessjourney.service.JourneyService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journeys")
public class JourneyController {

    private final JourneyService journeyService;
    private final JwtTokenProvider jwtTokenProvider;

    public JourneyController(JourneyService journeyService, JwtTokenProvider jwtTokenProvider) {
        this.journeyService = journeyService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Create a new journey
    @PostMapping
    public ResponseEntity<Journey> createJourney(@RequestBody Journey journey, HttpServletRequest request) {
        try {
            // Extract the email from the JWT token
            String token = jwtTokenProvider.resolveToken(request);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            String email = jwtTokenProvider.getEmailFromToken(token);

            // Set the email on the journey object, if not already set
            journey.setUserEmail(email);

            // Call the service method to save the journey with the user email
            Journey createdJourney = journeyService.saveJourney(journey, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdJourney);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);  // Invalid token
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // User not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Other errors
        }
    }

    // Get all journeys for the authenticated user
    @GetMapping
    public ResponseEntity<List<Journey>> getAllJourneysForUser(HttpServletRequest request) {
        try {
            // Extract the email from the JWT token
            String token = jwtTokenProvider.resolveToken(request);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            String email = jwtTokenProvider.getEmailFromToken(token);

            // Fetch all journeys for the user
            List<Journey> journeys = journeyService.getAllJourneysByUserEmail(email);

            // Return the list of journeys
            return ResponseEntity.ok(journeys);
        } catch (ParseException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  // Invalid token
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // Other errors
        }
    }


    // Get journey by ID
    @GetMapping("/{id}")
    public ResponseEntity<Journey> getJourneyById(@PathVariable Long id) {
        Optional<Journey> journey = journeyService.getJourneyById(id);
        return journey.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
