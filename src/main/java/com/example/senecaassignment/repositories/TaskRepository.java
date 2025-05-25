package com.example.senecaassignment.repositories;

import com.example.senecaassignment.models.Status;
import com.example.senecaassignment.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:userId IS NULL OR t.user.id = :userId)")
    List<Task> findByStatusAndUser(@Param("status") Status status, @Param("userId") Long userId);

    @Query("SELECT t FROM Task t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(t.description) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<Task> searchByText(@Param("text") String text);
}
