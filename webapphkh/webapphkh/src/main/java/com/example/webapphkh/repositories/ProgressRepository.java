package com.example.webapphkh.repositories;

import com.example.webapphkh.models.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findByUser_Id(Long userId);
    List<Progress> findByUser_IdAndStatus(Long userId, String status);
}
