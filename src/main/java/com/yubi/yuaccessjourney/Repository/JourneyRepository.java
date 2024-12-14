package com.yubi.yuaccessjourney.repository;

import com.yubi.yuaccessjourney.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {
}