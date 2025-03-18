package com.example.webapphkh.repositories;

import com.example.webapphkh.models.History;
import com.example.webapphkh.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);
    List<History> findByUserAndStatus(User user, String status);
}
