package com.example.springtest.repository;

import com.example.springtest.model.StudyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyPlanRepository extends JpaRepository<StudyPlan, Integer> {
}
