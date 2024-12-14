package com.yubi.yuaccessjourney.service;

import com.yubi.yuaccessjourney.model.Journey;
import java.util.List;
import java.util.Optional;

public interface JourneyService {
    Journey saveJourney(Journey journey);
    Optional<Journey> getJourneyById(Long id);
    List<Journey> getAllJourneys();
    Journey updateJourney(Long id, Journey journey);
    boolean deleteJourney(Long id);  // Change the return type to boolean
}
