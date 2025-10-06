package com.georeport.repository;

import com.georeport.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByStatus(String status);
    List<Issue> findByCategory(String category);
    List<Issue> findByPriority(String priority);
    List<Issue> findByAssignedWorkerId(Long workerId);
    
    @Query("SELECT COUNT(i) FROM Issue i WHERE i.status = ?1")
    Long countByStatus(String status);
    
    @Query("SELECT i.category, COUNT(i) FROM Issue i GROUP BY i.category")
    List<Object[]> countByCategory();
}
