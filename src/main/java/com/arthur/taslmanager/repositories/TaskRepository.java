package com.arthur.taslmanager.repositories;

import com.arthur.taslmanager.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorUsername(String username);
    List<Task> findByPerformerUsername(String username);
}
