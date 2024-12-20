package com.yubi.yuaccessjourney.service;

import com.yubi.yuaccessjourney.model.Journey;
import java.util.List;
import java.util.Optional;

public interface JourneyService {
    Journey saveJourney(Journey journey, String email);
    Optional<Journey> getJourneyById(Long id);
    List<Journey> getAllJourneys();
    List<Journey> getAllJourneysByUserEmail(String email);
    Journey updateJourney(Long id, Journey journey);
    boolean deleteJourney(Long id);
}