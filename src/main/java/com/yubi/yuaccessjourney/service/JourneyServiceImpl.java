package com.yubi.yuaccessjourney.service;

import com.yubi.yuaccessjourney.model.Journey;
import com.yubi.yuaccessjourney.repository.JourneyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyServiceImpl implements JourneyService {

    private final JourneyRepository journeyRepository;

    public JourneyServiceImpl(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public Journey saveJourney(Journey journey) {
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
    public Journey updateJourney(Long id, Journey journey) {
        return journeyRepository.findById(id)
                .map(existingJourney -> {
                    existingJourney.setJourneyName(journey.getJourneyName());
                    existingJourney.setPrompt(journey.getPrompt());
                    existingJourney.setFileType(journey.getFileType());
                    existingJourney.setOutputJson(journey.getOutputJson());
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