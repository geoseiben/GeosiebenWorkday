package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geosieben.gsbworkday.dto.CandidateAssessmentDTO;
import com.geosieben.gsbworkday.entity.Candidate;
public interface CandidiateRepository extends JpaRepository<Candidate,Integer>{
@Query(name = "find_candidate_details_dto", nativeQuery = true)
    List<CandidateAssessmentDTO> findAllCandidateAssessments();

}
