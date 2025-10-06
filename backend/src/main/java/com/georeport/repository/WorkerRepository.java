package com.georeport.repository;

import com.georeport.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> findByDepartment(String department);
    List<Worker> findByIsAvailable(Boolean isAvailable);
    boolean existsByEmail(String email);
}
