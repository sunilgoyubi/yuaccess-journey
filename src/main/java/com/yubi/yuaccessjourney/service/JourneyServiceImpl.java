package com.yubi.yuaccessjourney.service;

import com.yubi.yuaccessjourney.model.Journey;
import com.yubi.yuaccessjourney.model.User;
import com.yubi.yuaccessjourney.repository.JourneyRepository;
import com.yubi.yuaccessjourney.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyServiceImpl implements JourneyService {

    private final JourneyRepository journeyRepository;
    private final UserRepository userRepository;

    public JourneyServiceImpl(JourneyRepository journeyRepository, UserRepository userRepository) {
        this.journeyRepository = journeyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Journey saveJourney(Journey journey, String email) {
        // Find the user by email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            // Associate the user with the journey
            journey.setUser(user.get());
        } else {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        // Save the journey
        return journeyRepository.save(journey);
    }

    @Override
    public Optional<Journey> getJourneyById(Long id) {
        return journeyRepository.findById(id);
    }

    @Override
    public List<Journey> getAllJourneys() {
        return journeyRepository.findAll();
    }

    @Override
    public List<Journey> getAllJourneysByUserEmail(String email) {
        // Find the user by email
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            // Return journeys associated with the user
            return user.get().getJourneys();
        } else {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
    }

    @Override
    public Journey updateJourney(Long id, Journey journey) {
        return journeyRepository.findById(id)
                .map(existingJourney -> {
                    // Update existing journey fields
                    existingJourney.setJourneyName(journey.getJourneyName());
                    existingJourney.setPrompt(journey.getPrompt());
                    existingJourney.setFileType(journey.getFileType());
                    existingJourney.setOutputJson(journey.getOutputJson());
                    existingJourney.setOutputType(journey.getOutputType());
                    existingJourney.setJourneyDescription(journey.getJourneyDescription());
                    existingJourney.setDocumentSection(journey.getDocumentSection());
                    existingJourney.setConfigurations(journey.getConfigurations());
                    existingJourney.setErrorConfiguration(journey.getErrorConfiguration());
                    existingJourney.setValidation(journey.getValidation());
                    existingJourney.setVerification(journey.getVerification());

                    // Save updated journey
                    return journeyRepository.save(existingJourney);
                })
                .orElseThrow(() -> new IllegalArgumentException("Journey not found with ID: " + id));
    }

    @Override
    public boolean deleteJourney(Long id) {
        if (journeyRepository.existsById(id)) {
            journeyRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
